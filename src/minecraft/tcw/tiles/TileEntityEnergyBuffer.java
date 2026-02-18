package tcw.tiles;

import net.minecraft.nbt.NBTTagCompound;
import tcw.energy.EnergyNetHelper;

public class TileEntityEnergyBuffer extends TileEntityMachine {

    public static enum BufferTier {
        BATBOX(40000, 64, 200),
        MFE(600000, 256, 1000),
        MFSU(4000000, 1024, 8000);

        public final int capacity;
        public final int transfer;
        public final int reserve;

        BufferTier(int capacity, int transfer, int reserve) {
            this.capacity = capacity;
            this.transfer = transfer;
            this.reserve = reserve;
        }
    }

    private BufferTier tier;

    public TileEntityEnergyBuffer() {
        this(BufferTier.BATBOX);
    }

    public TileEntityEnergyBuffer(BufferTier tier) {
        super(tier.capacity);
        this.tier = tier;
    }

    @Override
    public void updateEntity() {
        if (worldObj == null || worldObj.isRemote) {
            return;
        }

        if (storage.getEnergyStored() > tier.reserve) {
            EnergyNetHelper.pushToNeighbors(this, tier.transfer, 0, tier.reserve, tier.transfer);
        }

        if (worldObj.getWorldTime() % 20 == 0) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public int getSafeInputPerTick() {
        return tier.transfer;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        int t = nbt.getInteger("BufferTier");
        if (t >= 0 && t < BufferTier.values().length) {
            tier = BufferTier.values()[t];
            updateStorageCapacity();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("BufferTier", tier.ordinal());
    }
}
