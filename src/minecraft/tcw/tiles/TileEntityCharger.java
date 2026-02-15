package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tcw.managers.ItemManager;

public class TileEntityCharger extends TileEntityInventoryMachine {

    private int progress;
    private boolean fastMode;

    public TileEntityCharger() {
        super(300000, 2);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;

        int cost = fastMode ? 900 : 500;
        boolean canRun = canCharge() && storage.getEnergyStored() >= cost;
        if (canRun) {
            storage.extractEnergy(cost, false);
            progress++;
            if (progress >= (fastMode ? 60 : 100)) {
                progress = 0;
                charge();
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else if (progress > 0) {
            progress = 0;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    private boolean canCharge() {
        if (inventory[0] == null || inventory[0].itemID != ItemManager.batteryBasic.itemID) return false;
        if (inventory[1] == null) return true;
        if (inventory[1].itemID != ItemManager.batteryAdvanced.itemID) return false;
        return inventory[1].stackSize < inventory[1].getMaxStackSize();
    }

    private void charge() {
        if (!canCharge()) return;
        if (inventory[1] == null) inventory[1] = new ItemStack(ItemManager.batteryAdvanced, 1);
        else inventory[1].stackSize++;

        inventory[0].stackSize--;
        if (inventory[0].stackSize <= 0) inventory[0] = null;
        onInventoryChanged();
    }

    @Override
    public String getInvName() { return "container.tcwCharger"; }
    @Override
    public boolean isInvNameLocalized() { return false; }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && stack.itemID == ItemManager.batteryBasic.itemID;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) { super.readFromNBT(nbt); progress = nbt.getInteger("Progress"); fastMode = nbt.getBoolean("FastMode"); }
    @Override
    public void writeToNBT(NBTTagCompound nbt) { super.writeToNBT(nbt); nbt.setInteger("Progress", progress); nbt.setBoolean("FastMode", fastMode); }


    public boolean isFastMode() { return fastMode; }
    public void toggleFastMode() { fastMode = !fastMode; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void setClientFastMode(int mode) { fastMode = mode == 1; }

    public int getProgress() { return progress; }
    public void setClientProgress(int value) { this.progress = value; }
    public void setClientEnergyScaled(int scaled) { this.storage.setEnergy(this.storage.getMaxEnergyStored() * scaled / 10000); }
    public int getScaledProgress(int scale) { return progress * scale / 100; }
}
