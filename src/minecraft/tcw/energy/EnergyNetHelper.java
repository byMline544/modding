package tcw.energy;

import java.util.WeakHashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import tcw.tiles.TileEntityCable;
import tcw.tiles.TileEntityGenerator;
import tcw.tiles.TileEntityMachine;
import tcw.tiles.TileEntitySolarPanel;

public class EnergyNetHelper {

    private static final WeakHashMap<TileEntity, Integer> ROUND_ROBIN_INDEX = new WeakHashMap<TileEntity, Integer>();

    private EnergyNetHelper() {
    }

    public static void pushToNeighbors(TileEntity source, int maxPerSide) {
        pushToNeighbors(source, maxPerSide, 0, 0, Integer.MAX_VALUE);
    }

    public static void pushToNeighbors(TileEntity source, int maxPerSide, int lossPerTransfer) {
        pushToNeighbors(source, maxPerSide, lossPerTransfer, 0, Integer.MAX_VALUE);
    }

    public static void pushToNeighbors(TileEntity source, int maxPerSide, int lossPerTransfer, int keepReserve, int maxTotalPerTick) {
        if (source.worldObj == null || source.worldObj.isRemote) {
            return;
        }

        IEnergyNode sourceNode = getNode(source);
        if (sourceNode == null || sourceNode.getEnergyStored() <= keepReserve) {
            return;
        }

        ForgeDirection[] dirs = ForgeDirection.VALID_DIRECTIONS;
        int start = 0;
        Integer idx = ROUND_ROBIN_INDEX.get(source);
        if (idx != null) {
            start = idx.intValue() % dirs.length;
        }

        int remainingBudget = Math.max(0, maxTotalPerTick);

        for (int step = 0; step < dirs.length; step++) {
            if (remainingBudget <= 0) {
                break;
            }

            ForgeDirection dir = dirs[(start + step) % dirs.length];
            TileEntity target = source.worldObj.getBlockTileEntity(source.xCoord + dir.offsetX, source.yCoord + dir.offsetY,
                    source.zCoord + dir.offsetZ);
            IEnergyNode targetNode = getNode(target);
            if (targetNode == null || targetNode == sourceNode) {
                continue;
            }

            if (!canTransfer(source, target, sourceNode, targetNode)) {
                continue;
            }

            int available = sourceNode.getEnergyStored() - keepReserve - Math.max(0, lossPerTransfer);
            if (available <= 0) {
                break;
            }

            int canSend = Math.min(maxPerSide, available);
            canSend = Math.min(canSend, remainingBudget);
            if (canSend <= 0) {
                continue;
            }

            int accepted = targetNode.receiveEnergy(canSend, false);
            if (accepted > 0) {
                sourceNode.extractEnergy(accepted + Math.max(0, lossPerTransfer), false);
                remainingBudget -= accepted;
            }
        }

        ROUND_ROBIN_INDEX.put(source, Integer.valueOf((start + 1) % dirs.length));
    }

    private static boolean canTransfer(TileEntity source, TileEntity target, IEnergyNode sourceNode, IEnergyNode targetNode) {
        // панели и генераторы не заряжают друг друга
        if ((source instanceof TileEntitySolarPanel && target instanceof TileEntitySolarPanel)
                || (source instanceof TileEntityGenerator && target instanceof TileEntityGenerator)
                || (source instanceof TileEntitySolarPanel && target instanceof TileEntityGenerator)
                || (source instanceof TileEntityGenerator && target instanceof TileEntitySolarPanel)) {
            return false;
        }

        // панели и генераторы не принимают энергию от сети (только собственная генерация).
        if (target instanceof TileEntitySolarPanel || target instanceof TileEntityGenerator) {
            return false;
        }

        // чтобы убрать «высасывание в пустоту» на длинных линиях:
        // кабель не отправляет в кабель с равной/большей энергией.
        if (source instanceof TileEntityCable && target instanceof TileEntityCable) {
            return sourceNode.getEnergyStored() > targetNode.getEnergyStored();
        }

        return true;
    }

    private static IEnergyNode getNode(TileEntity tile) {
        if (tile instanceof TileEntityMachine) {
            return ((TileEntityMachine) tile).getStorage();
        }
        if (tile instanceof TileEntityCable) {
            return ((TileEntityCable) tile).getStorage();
        }
        return null;
    }
}
