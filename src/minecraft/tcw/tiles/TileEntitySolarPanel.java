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

        if (worldObj.isDaytime() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
            storage.receiveEnergy(generation, false);
        }

        if (storage.getEnergyStored() > 0) {
            EnergyNetHelper.pushToNeighbors(this, Math.max(32, generation * 4));
        }

        if (worldObj.getWorldTime() % 20 == 0) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        int loadedCapacity = nbt.getInteger("Capacity");
        int loadedGeneration = nbt.getInteger("Generation");
        if (loadedCapacity > 0 && loadedGeneration > 0 && loadedCapacity != storage.getMaxEnergyStored()) {
            int savedEnergy = storage.getEnergyStored();
            capacity = loadedCapacity;
            generation = loadedGeneration;
            storage = new EnergyStorageTCW(capacity);
            storage.setEnergy(savedEnergy);
        } else {
            if (loadedCapacity > 0) {
                capacity = loadedCapacity;
            } else if (capacity <= 0) {
                capacity = storage.getMaxEnergyStored();
            }
            if (loadedGeneration > 0) {
                generation = loadedGeneration;
            } else if (generation <= 0) {
                generation = 8;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Capacity", capacity);
        nbt.setInteger("Generation", generation);
    }
}
