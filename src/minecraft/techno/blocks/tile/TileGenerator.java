package techno.blocks.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import techno.api.energy.EnergyNet;

public class TileGenerator extends BaseTileEnergySource {

    private int burn;
    public ItemStack fuel;

    public TileGenerator() {
        super(40000);
    }

    public int getOutputRate() { return 48; }

    public void updateEntity() {
        if (worldObj.isRemote) return;
        int before = storage.getEnergyStored();

        if (burn <= 0 && fuel != null) {
            int b = TileEntityFurnace.getItemBurnTime(fuel);
            if (b > 0) {
                burn = Math.max(1, b / 12);
                fuel.stackSize--;
                if (fuel.stackSize <= 0) fuel = null;
                onInventoryChanged();
            }
        }

        if (burn > 0 && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
            burn--;
            storage.receiveEnergy(32, false);
        }

        EnergyNet.tickSource(worldObj, this, this);

        if (before != storage.getEnergyStored()) onInventoryChanged();
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        burn = nbt.getInteger("Burn");
        if (nbt.hasKey("Fuel")) fuel = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Fuel"));
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Burn", burn);
        if (fuel != null) {
            NBTTagCompound t = new NBTTagCompound();
            fuel.writeToNBT(t);
            nbt.setTag("Fuel", t);
        }
    }

    public boolean wantsEnergy() { return false; }
    public int getBurn() { return burn; }
}
