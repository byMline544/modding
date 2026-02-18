package techno.api.energy;

public interface IEnergyConsumer extends IEnergyNode {
    int getDemandRate();
    boolean wantsEnergy();
}
