package tcw.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tcw.energy.EnergyStorageTCW;

public class TileEntityMachine extends TileEntity {

    protected EnergyStorageTCW storage;

    public TileEntityMachine() {
        this(32000);
    }

    public TileEntityMachine(int capacity) {
        storage = new EnergyStorageTCW(capacity);
    }

    public EnergyStorageTCW getStorage() {
        return storage;
    }


    public static void resetNearbyMachineEnergy(net.minecraft.world.World world, int x, int y, int z, int radius) {
        if (world == null || world.isRemote) {
            return;
        }

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    net.minecraft.tileentity.TileEntity te = world.getBlockTileEntity(x + dx, y + dy, z + dz);
                    if (te instanceof TileEntityMachine) {
                        TileEntityMachine machine = (TileEntityMachine) te;
                        if (machine.storage.getEnergyStored() > 0) {
                            machine.storage.setEnergy(0);
                            world.markBlockForUpdate(x + dx, y + dy, z + dz);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity() {
        // Базовые машины сами не раздают энергию, чтобы не возникал пинг-понг по сети.
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.setEnergy(nbt.getInteger("Energy"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Energy", storage.getEnergyStored());
    }
}
