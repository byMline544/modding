package techno.blocks.tile;

import techno.api.energy.EnergyNet;

public class TileSolarPanel extends BaseTileEnergySource {
    public TileSolarPanel() {
        super(30000);
    }

    public int getOutputRate() { return 24; }

    public void updateEntity() {
        if (worldObj.isRemote) return;
        int before = storage.getEnergyStored();

        if (worldObj.isDaytime() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
            storage.receiveEnergy(12, false);
        }

        EnergyNet.tickSource(worldObj, this, this);

        if (before != storage.getEnergyStored()) onInventoryChanged();
    }

    public boolean wantsEnergy() { return false; }
}
