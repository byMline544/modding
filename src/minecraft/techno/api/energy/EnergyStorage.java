package techno.api.energy;

public class EnergyStorage implements IEnergyNode {
    private int energy;
    private int capacity;

    public EnergyStorage(int capacity) {
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        if (energy > capacity) energy = capacity;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(capacity, energy));
    }

    public int getEnergyStored() { return energy; }

    public int getMaxEnergyStored() { return capacity; }

    public int receiveEnergy(int amount, boolean simulate) {
        int accepted = Math.min(capacity - energy, Math.max(0, amount));
        if (!simulate) energy += accepted;
        return accepted;
    }

    public int extractEnergy(int amount, boolean simulate) {
        int taken = Math.min(energy, Math.max(0, amount));
        if (!simulate) energy -= taken;
        return taken;
    }
}
