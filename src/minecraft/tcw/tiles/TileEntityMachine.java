package tcw.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tcw.energy.EnergyNetHelper;
import tcw.energy.EnergyStorageTCW;

public class TileEntityMachine extends TileEntity {

    protected final EnergyStorageTCW storage;

    public TileEntityMachine() {
        this(32000);
    }

    public TileEntityMachine(int capacity) {
        storage = new EnergyStorageTCW(capacity);
    }

    public EnergyStorageTCW getStorage() {
        return storage;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            EnergyNetHelper.pushToNeighbors(this, 128);
        }
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
