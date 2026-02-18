package tcw.tiles;

import net.minecraft.nbt.NBTTagCompound;
import tcw.energy.EnergyNetHelper;
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
        if (worldObj.isRemote) {
            return;
        }

        int beforeEnergy = storage.getEnergyStored();

        if (worldObj.isDaytime() && !worldObj.provider.hasNoSky && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
            storage.receiveEnergy(generation * 4, false);
        }

        int reserve = 0;
        int sendPerTick = Math.max(72, generation * 6);
        if (storage.getEnergyStored() > reserve) {
            EnergyNetHelper.pushToNeighbors(this, sendPerTick, 0, reserve, sendPerTick * 3);
        }

        if (worldObj.getWorldTime() % 20 == 0) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        if (beforeEnergy != storage.getEnergyStored()) {
            onInventoryChanged();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        int loadedCapacity = nbt.getInteger("Capacity");
        int loadedGeneration = nbt.getInteger("Generation");
        if (loadedCapacity > 0) {
            capacity = loadedCapacity;
        }
        if (loadedGeneration > 0) {
            generation = loadedGeneration;
        }

        int energy = storage.getEnergyStored();
        storage = new EnergyStorageTCW(capacity);
        storage.setEnergy(Math.min(energy, capacity));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Capacity", capacity);
        nbt.setInteger("Generation", generation);
    }
}
