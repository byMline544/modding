package techno.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import techno.api.energy.EnergyStorage;
import techno.api.energy.IEnergyConsumer;

public abstract class BaseTileMachine extends TileEntity implements IEnergyConsumer {
    protected final EnergyStorage storage;

    protected BaseTileMachine(int capacity) {
        this.storage = new EnergyStorage(capacity);
    }

    public int getEnergyStored() { return storage.getEnergyStored(); }
    public int getMaxEnergyStored() { return storage.getMaxEnergyStored(); }
    public int receiveEnergy(int amount, boolean simulate) { return storage.receiveEnergy(amount, simulate); }
    public int extractEnergy(int amount, boolean simulate) { return storage.extractEnergy(amount, simulate); }
    public int getDemandRate() { return 64; }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.setEnergy(nbt.getInteger("Energy"));
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Energy", storage.getEnergyStored());
    }
}
