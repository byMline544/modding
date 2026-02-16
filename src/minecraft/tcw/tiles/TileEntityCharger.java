package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tcw.managers.ItemManager;
import tcw.items.ElectricItemHelper;
import tcw.items.IElectricItemTCW;

public class TileEntityCharger extends TileEntityInventoryMachine {

    private int progress;
    private boolean fastMode = true;

    public TileEntityCharger() {
        super(180000, 2);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;

        int cost = getEnergyCostWithModules(900);
        boolean linked = ensurePowerLinkOrDropEnergy();
        boolean canRun = linked && canCharge() && storage.getEnergyStored() >= cost;
        tickMachineEffects(canRun);
        if (canRun) {
            storage.extractEnergy(cost, false);
            progress += getProgressStepWithModules();
            int workTime = isElectricRepairMode() ? 4 : 60;
            if (progress >= workTime) {
                progress = 0;
                charge();
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else if (progress > 0) {
            progress = 0;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    /**
     * Проверка доступного режима зарядки:
     * 1) Конверсия батареи basic -> advanced.
     * 2) Подзарядка электро-предмета через уменьшение его износа.
     */
    private boolean canCharge() {
        if (inventory[0] == null) return false;

        if (inventory[0].itemID == ItemManager.batteryBasic.itemID) {
            if (inventory[1] == null) return true;
            if (inventory[1].itemID != ItemManager.batteryAdvanced.itemID) return false;
            return inventory[1].stackSize < inventory[1].getMaxStackSize();
        }

        return inventory[0].getItem() instanceof IElectricItemTCW
                && ElectricItemHelper.getEnergy(inventory[0]) < ElectricItemHelper.getMaxEnergy(inventory[0]);
    }

    private boolean isElectricRepairMode() {
        return inventory[0] != null && inventory[0].getItem() instanceof IElectricItemTCW;
    }

    private void charge() {
        if (!canCharge()) return;

        if (isElectricRepairMode()) {
            // Заряд электро-предметов по NBT-энергии.
            ElectricItemHelper.addEnergy(inventory[0], 6000);
            onInventoryChanged();
            return;
        }

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
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        if (slot != 0 || stack == null) {
            return false;
        }
        return stack.itemID == ItemManager.batteryBasic.itemID || stack.getItem() instanceof IElectricItemTCW;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) { super.readFromNBT(nbt); progress = nbt.getInteger("Progress"); fastMode = true; }
    @Override
    public void writeToNBT(NBTTagCompound nbt) { super.writeToNBT(nbt); nbt.setInteger("Progress", progress); nbt.setBoolean("FastMode", fastMode); }


    public boolean isFastMode() { return fastMode; }
    public void toggleFastMode() { fastMode = true; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void setClientFastMode(int mode) { fastMode = true; }

    public int getProgress() { return progress; }
    public void setClientProgress(int value) { this.progress = value; }
    public void setClientEnergyScaled(int scaled) { this.storage.setEnergy(this.storage.getMaxEnergyStored() * scaled / 10000); }
    public int getScaledProgress(int scale) { return progress * scale / 100; }
}
