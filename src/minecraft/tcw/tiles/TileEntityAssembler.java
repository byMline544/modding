package tcw.tiles;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tcw.crafting.AssemblerRecipes;

public class TileEntityAssembler extends TileEntityInventoryMachine {

    private int progress;
    private boolean precisionMode;
    private boolean autoInput;
    private boolean autoOutput;

    public TileEntityAssembler() {
        super(260000, 3);
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

        int cost = precisionMode ? 48 : 30;
        boolean canRun = canProcess() && storage.getEnergyStored() >= cost;
        if (canRun) {
            storage.extractEnergy(cost, false);
            progress++;
            if (progress >= (precisionMode ? 110 : 160)) {
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
        ItemStack result = AssemblerRecipes.instance().getResult(inventory[0], inventory[1]);
        if (result == null) return false;
        if (inventory[2] == null) return true;
        if (!inventory[2].isItemEqual(result)) return false;
        int next = inventory[2].stackSize + result.stackSize;
        return next <= getInventoryStackLimit() && next <= result.getMaxStackSize();
    }

    private void process() {
        if (!canProcess()) return;
        ItemStack result = AssemblerRecipes.instance().getResult(inventory[0], inventory[1]);
        if (inventory[2] == null) inventory[2] = result;
        else inventory[2].stackSize += result.stackSize;

        inventory[0].stackSize--;
        inventory[1].stackSize--;
        if (inventory[0].stackSize <= 0) inventory[0] = null;
        if (inventory[1].stackSize <= 0) inventory[1] = null;
        onInventoryChanged();
    }

    private void tryAutoInput() {
        IInventory source = getAdjacentInventory(xCoord - 1, yCoord, zCoord);
        if (source == null) {
            return;
        }
        if (pullIntoSlot(source, 0)) {
            return;
        }
        pullIntoSlot(source, 1);
    }

    private boolean pullIntoSlot(IInventory source, int targetSlot) {
        for (int i = 0; i < source.getSizeInventory(); i++) {
            ItemStack candidate = source.getStackInSlot(i);
            if (candidate == null || !isItemValidForSlot(targetSlot, candidate)) {
                continue;
            }
            if (inventory[targetSlot] != null
                    && (!inventory[targetSlot].isItemEqual(candidate)
                    || inventory[targetSlot].stackSize >= inventory[targetSlot].getMaxStackSize())) {
                continue;
            }
            ItemStack taken = source.decrStackSize(i, 1);
            if (taken == null) {
                continue;
            }
            if (inventory[targetSlot] == null) {
                taken.stackSize = 1;
                inventory[targetSlot] = taken;
            } else {
                inventory[targetSlot].stackSize++;
            }
            source.onInventoryChanged();
            onInventoryChanged();
            return true;
        }
        return false;
    }

    private void tryAutoOutput() {
        if (inventory[2] == null) {
            return;
        }
        IInventory target = getAdjacentInventory(xCoord + 1, yCoord, zCoord);
        if (target == null) {
            return;
        }

        ItemStack one = inventory[2].copy();
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

            inventory[2].stackSize--;
            if (inventory[2].stackSize <= 0) {
                inventory[2] = null;
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
    public String getInvName() { return "container.tcwAssembler"; }
    @Override
    public boolean isInvNameLocalized() { return false; }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 2) return false;
        if (slot == 0) return inventory[1] == null || AssemblerRecipes.instance().getResult(stack, inventory[1]) != null;
        return inventory[0] == null || AssemblerRecipes.instance().getResult(inventory[0], stack) != null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        precisionMode = nbt.getBoolean("PrecisionMode");
        autoInput = nbt.getBoolean("AutoInput");
        autoOutput = nbt.getBoolean("AutoOutput");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        nbt.setBoolean("PrecisionMode", precisionMode);
        nbt.setBoolean("AutoInput", autoInput);
        nbt.setBoolean("AutoOutput", autoOutput);
    }

    public int getProgress() { return progress; }
    public void setClientProgress(int value) { this.progress = value; }
    public void setClientEnergyScaled(int scaled) { this.storage.setEnergy(this.storage.getMaxEnergyStored() * scaled / 10000); }
    public boolean isPrecisionMode() { return precisionMode; }
    public void togglePrecisionMode() { precisionMode = !precisionMode; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void setClientPrecisionMode(int mode) { precisionMode = mode == 1; }
    public int getScaledProgress(int scale) { return progress * scale / (precisionMode ? 110 : 160); }
    public boolean isAutoInput() { return autoInput; }
    public boolean isAutoOutput() { return autoOutput; }
    public void toggleAutoInput() { autoInput = !autoInput; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void toggleAutoOutput() { autoOutput = !autoOutput; if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void setClientAutoInput(int mode) { autoInput = mode == 1; }
    public void setClientAutoOutput(int mode) { autoOutput = mode == 1; }
}
