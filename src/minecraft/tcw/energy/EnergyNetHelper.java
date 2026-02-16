package tcw.energy;

import java.util.WeakHashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import tcw.tiles.TileEntityCable;
import tcw.tiles.TileEntityMachine;

public class EnergyNetHelper {

    private static final WeakHashMap<TileEntity, Integer> ROUND_ROBIN_INDEX = new WeakHashMap<TileEntity, Integer>();

    private EnergyNetHelper() {
    }

    public static void pushToNeighbors(TileEntity source, int maxPerSide) {
        pushToNeighbors(source, maxPerSide, 0);
    }

    public static void pushToNeighbors(TileEntity source, int maxPerSide, int lossPerTransfer) {
        if (source.worldObj == null || source.worldObj.isRemote) {
            return;
        }

        IEnergyNode sourceNode = getNode(source);
        if (sourceNode == null || sourceNode.getEnergyStored() <= 0) {
            return;
        }

        ForgeDirection[] dirs = ForgeDirection.VALID_DIRECTIONS;
        int start = 0;
        Integer idx = ROUND_ROBIN_INDEX.get(source);
        if (idx != null) {
            start = idx.intValue() % dirs.length;
        }

        for (int step = 0; step < dirs.length; step++) {
            ForgeDirection dir = dirs[(start + step) % dirs.length];
            TileEntity target = source.worldObj.getBlockTileEntity(source.xCoord + dir.offsetX, source.yCoord + dir.offsetY,
                    source.zCoord + dir.offsetZ);
            IEnergyNode targetNode = getNode(target);
            if (targetNode == null || targetNode == sourceNode) {
                continue;
            }

            int available = sourceNode.getEnergyStored() - Math.max(0, lossPerTransfer);
            if (available <= 0) {
                break;
            }

            int canSend = Math.min(maxPerSide, available);
            if (canSend <= 0) {
                continue;
            }

            int accepted = targetNode.receiveEnergy(canSend, false);
            if (accepted > 0) {
                sourceNode.extractEnergy(accepted + Math.max(0, lossPerTransfer), false);
            }
        }

        ROUND_ROBIN_INDEX.put(source, Integer.valueOf((start + 1) % dirs.length));
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
