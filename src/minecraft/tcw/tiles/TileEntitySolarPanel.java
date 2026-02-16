package tcw.tiles;

import net.minecraft.nbt.NBTTagCompound;
import tcw.energy.EnergyStorageTCW;

public class TileEntitySolarPanel extends TileEntityMachine {

    private int generation;
    private int capacity;

    public TileEntitySolarPanel() {
        this(150000, 8);
    }

    public TileEntitySolarPanel(int capacity, int generation) {
        super(capacity);
        this.capacity = capacity;
        this.generation = generation;
    }

    public int getGenerationRate() {
        return generation;
    }

    public int getScaledEnergy(int scale) {
        return storage.getEnergyStored() * scale / storage.getMaxEnergyStored();
    }

    public void setClientEnergyScaled(int scaled) {
        int energy = storage.getMaxEnergyStored() * scaled / 10000;
        storage.setEnergy(energy);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote && worldObj.isDaytime() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
            storage.receiveEnergy(generation, false);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        capacity = nbt.getInteger("Capacity");
        generation = nbt.getInteger("Generation");
        if (capacity <= 0) {
            capacity = 150000;
        }
        if (generation <= 0) {
            generation = 8;
        }
        storage = new EnergyStorageTCW(capacity);
        super.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Capacity", capacity);
        nbt.setInteger("Generation", generation);
    }
}
