package tcw.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tcw.energy.CableTier;
import tcw.energy.EnergyNetHelper;
import tcw.energy.EnergyStorageTCW;

public class TileEntityCable extends TileEntity {

    private CableTier tier;
    private EnergyStorageTCW storage;

    public TileEntityCable() {
        this(CableTier.BASIC);
    }

    public TileEntityCable(CableTier tier) {
        this.tier = tier;
        this.storage = new EnergyStorageTCW(tier.storage);
    }

    public EnergyStorageTCW getStorage() {
        return storage;
    }

    public CableTier getTier() {
        return tier;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            EnergyNetHelper.pushToNeighbors(this, tier.transferRate, tier.lossPerTransfer);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        int tierId = nbt.getInteger("Tier");
        if (tierId >= 0 && tierId < CableTier.values().length) {
            tier = CableTier.values()[tierId];
            storage = new EnergyStorageTCW(tier.storage);
        }
        storage.setEnergy(nbt.getInteger("Energy"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Tier", tier.ordinal());
        nbt.setInteger("Energy", storage.getEnergyStored());
    }
}
