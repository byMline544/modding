package tcw.tiles;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tcw.crafting.ExtractorRecipes;

public class TileEntityExtractor extends TileEntityInventoryMachine {

    private int progress;
    private boolean overclockMode;
    private boolean autoInput;
    private boolean autoOutput;

    public TileEntityExtractor() {
        super(220000, 2);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        if (autoInput && worldObj.getWorldTime() % 8 == 0) {
            tryAutoInput();
        }

        int cost = overclockMode ? 44 : 26;
        boolean canRun = canProcess() && storage.getEnergyStored() >= cost;
        if (canRun) {
            storage.extractEnergy(cost, false);
            progress++;
            if (progress >= (overclockMode ? 70 : 120)) {
                progress = 0;
                process();
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else if (progress > 0) {
            progress = 0;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        if (autoOutput && worldObj.getWorldTime() % 8 == 0) {
            tryAutoOutput();
        }
    }

    private boolean canProcess() {
        ItemStack result = ExtractorRecipes.instance().getResult(inventory[0]);
        if (result == null) {
            return false;
        }
        if (inventory[1] == null) {
            return true;
        }
        if (!inventory[1].isItemEqual(result)) {
            return false;
        }
        int next = inventory[1].stackSize + result.stackSize;
        return next <= getInventoryStackLimit() && next <= result.getMaxStackSize();
    }

    private void process() {
        if (!canProcess()) {
            return;
        }
        ItemStack result = ExtractorRecipes.instance().getResult(inventory[0]);
        if (inventory[1] == null) {
            inventory[1] = result;
        } else {
            inventory[1].stackSize += result.stackSize;
        }
        inventory[0].stackSize--;
        if (inventory[0].stackSize <= 0) {
            inventory[0] = null;
        }
        onInventoryChanged();
    }

    private void tryAutoInput() {
        IInventory source = getAdjacentInventory(xCoord - 1, yCoord, zCoord);
        if (source == null) {
            return;
        }
        for (int i = 0; i < source.getSizeInventory(); i++) {
            ItemStack candidate = source.getStackInSlot(i);
            if (candidate == null || !isItemValidForSlot(0, candidate)) {
                continue;
            }
            if (inventory[0] != null && (!inventory[0].isItemEqual(candidate) || inventory[0].stackSize >= inventory[0].getMaxStackSize())) {
                continue;
            }
            ItemStack taken = source.decrStackSize(i, 1);
            if (taken == null) {
                continue;
            }
            if (inventory[0] == null) {
                taken.stackSize = 1;
                inventory[0] = taken;
            } else {
                inventory[0].stackSize++;
            }
            source.onInventoryChanged();
            onInventoryChanged();
            break;
        }
    }

    private void tryAutoOutput() {
        if (inventory[1] == null) {
            return;
        }
        IInventory target = getAdjacentInventory(xCoord + 1, yCoord, zCoord);
        if (target == null) {
            return;
        }

        ItemStack one = inventory[1].copy();
        one.stackSize = 1;
        for (int i = 0; i < target.getSizeInventory(); i++) {
            if (!target.isItemValidForSlot(i, one)) {
                continue;
            }
            ItemStack slot = target.getStackInSlot(i);
            if (slot == null) {
                target.setInventorySlotContents(i, one.copy());
            } else {
                if (!slot.isItemEqual(one)) {
                    continue;
                }
                int max = Math.min(slot.getMaxStackSize(), target.getInventoryStackLimit());
                if (slot.stackSize >= max) {
                    continue;
                }
                slot.stackSize++;
                target.setInventorySlotContents(i, slot);
            }

            inventory[1].stackSize--;
            if (inventory[1].stackSize <= 0) {
                inventory[1] = null;
            }
            target.onInventoryChanged();
            onInventoryChanged();
            break;
        }
    }

    private IInventory getAdjacentInventory(int x, int y, int z) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if (tile instanceof IInventory) {
            return (IInventory) tile;
        }
        return null;
    }

    @Override
    public String getInvName() {
        return "container.tcwExtractor";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && ExtractorRecipes.instance().getResult(stack) != null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        overclockMode = nbt.getBoolean("OverclockMode");
        autoInput = nbt.getBoolean("AutoInput");
        autoOutput = nbt.getBoolean("AutoOutput");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        nbt.setBoolean("OverclockMode", overclockMode);
        nbt.setBoolean("AutoInput", autoInput);
        nbt.setBoolean("AutoOutput", autoOutput);
    }

    public int getProgress() { return progress; }
    public void setClientProgress(int value) { this.progress = value; }
    public void setClientEnergyScaled(int scaled) { this.storage.setEnergy(this.storage.getMaxEnergyStored() * scaled / 10000); }
    public boolean isOverclockMode() { return overclockMode; }
    public void toggleOverclockMode() { overclockMode = !overclockMode; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void setClientOverclockMode(int mode) { overclockMode = mode == 1; }
    public int getScaledProgress(int scale) { return progress * scale / (overclockMode ? 70 : 120); }
    public boolean isAutoInput() { return autoInput; }
    public boolean isAutoOutput() { return autoOutput; }
    public void toggleAutoInput() { autoInput = !autoInput; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void toggleAutoOutput() { autoOutput = !autoOutput; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void setClientAutoInput(int mode) { autoInput = mode == 1; }
    public void setClientAutoOutput(int mode) { autoOutput = mode == 1; }
}
