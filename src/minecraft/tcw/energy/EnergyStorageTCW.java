package tcw.energy;

public class EnergyStorageTCW implements IEnergyNode {

    public static final int GLOBAL_LIMIT = 6000000;

    private int energy;
    private final int maxEnergy;

    public EnergyStorageTCW(int maxEnergy) {
        this.maxEnergy = Math.min(maxEnergy, GLOBAL_LIMIT);
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(energy, maxEnergy));
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxEnergy;
    }

    @Override
    public int receiveEnergy(int amount, boolean simulate) {
        int accepted = Math.min(maxEnergy - energy, Math.max(amount, 0));
        if (!simulate) {
            energy += accepted;
        }
        return accepted;
    }

    @Override
    public int extractEnergy(int amount, boolean simulate) {
        int taken = Math.min(energy, Math.max(amount, 0));
        if (!simulate) {
            energy -= taken;
        }
        return taken;
    }
}
