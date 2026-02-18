package techno.blocks.tile;

import techno.api.energy.IEnergyProducer;

public abstract class BaseTileEnergySource extends BaseTileMachine implements IEnergyProducer {
    protected BaseTileEnergySource(int capacity) {
        super(capacity);
    }

    public int getDemandRate() {
        return 0;
    }

    public boolean wantsEnergy() {
        return false;
    }
}
