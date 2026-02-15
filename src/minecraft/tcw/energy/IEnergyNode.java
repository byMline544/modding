package tcw.energy;

public interface IEnergyNode {
    int getEnergyStored();
    int getMaxEnergyStored();
    int receiveEnergy(int amount, boolean simulate);
    int extractEnergy(int amount, boolean simulate);
}
