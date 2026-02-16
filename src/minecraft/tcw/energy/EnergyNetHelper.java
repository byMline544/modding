package tcw.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import tcw.tiles.TileEntityCable;
import tcw.tiles.TileEntityMachine;

public class EnergyNetHelper {

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

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity target = source.worldObj.getBlockTileEntity(source.xCoord + dir.offsetX, source.yCoord + dir.offsetY,
                    source.zCoord + dir.offsetZ);
            IEnergyNode targetNode = getNode(target);
            if (targetNode == null) {
                continue;
            }

            int canSend = Math.min(maxPerSide, sourceNode.getEnergyStored());
            if (canSend <= 0) {
                return;
            }

            int accepted = targetNode.receiveEnergy(canSend, false);
            if (accepted > 0) {
                int spent = accepted + lossPerTransfer;
                sourceNode.extractEnergy(Math.min(spent, sourceNode.getEnergyStored()), false);
            }
        }
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
