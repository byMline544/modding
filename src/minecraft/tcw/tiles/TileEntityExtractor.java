package tcw.tiles;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tcw.crafting.ExtractorRecipes;

public class TileEntityExtractor extends TileEntityInventoryMachine {

    private static final int SIDE_DISABLED = -1;
    private static final int SIDE_DOWN = 0;
    private static final int SIDE_UP = 1;
    private static final int SIDE_NORTH = 2;
    private static final int SIDE_SOUTH = 3;
    private static final int SIDE_WEST = 4;
    private static final int SIDE_EAST = 5;

    private static final int FILTER_OFF = 0;
    private static final int FILTER_WHITELIST = 1;
    private static final int FILTER_BLACKLIST = 2;

    private int progress;
    private boolean overclockMode;
    private boolean autoInput;
    private boolean autoOutput;
    private int inputSide;
    private int outputSide;
    private int filterMode;

    public TileEntityExtractor() {
        super(160000, 2);
        inputSide = SIDE_WEST;
        outputSide = SIDE_EAST;
        filterMode = FILTER_OFF;
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
        boolean linked = ensurePowerLinkOrDropEnergy();
        boolean canRun = linked && canProcess() && storage.getEnergyStored() >= cost;
        tickMachineEffects(canRun);
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

    private boolean isAllowedByFilter(ItemStack candidate) {
        if (filterMode == FILTER_OFF) {
            return true;
        }
        if (inventory[0] == null) {
            return filterMode != FILTER_WHITELIST;
        }
        boolean matches = inventory[0].isItemEqual(candidate);
        if (filterMode == FILTER_WHITELIST) {
            return matches;
        }
        return !matches;
    }

    private void tryAutoInput() {
        IInventory source = getSideInventory(inputSide);
        if (source == null) {
            return;
        }
        for (int i = 0; i < source.getSizeInventory(); i++) {
            ItemStack candidate = source.getStackInSlot(i);
            if (candidate == null || !isStackValidForSlot(0, candidate) || !isAllowedByFilter(candidate)) {
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
        IInventory target = getSideInventory(outputSide);
        if (target == null) {
            return;
        }

        ItemStack one = inventory[1].copy();
        one.stackSize = 1;
        for (int i = 0; i < target.getSizeInventory(); i++) {
            if (!isTargetSlotValid(target, i, one)) {
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

    private boolean isTargetSlotValid(IInventory target, int slot, ItemStack stack) {
        if (target instanceof TileEntityInventoryMachine) {
            return ((TileEntityInventoryMachine) target).isStackValidForSlot(slot, stack);
        }
        try {
            java.lang.reflect.Method method = target.getClass().getMethod("isStackValidForSlot", Integer.TYPE, ItemStack.class);
            Object result = method.invoke(target, Integer.valueOf(slot), stack);
            return result instanceof Boolean ? ((Boolean) result).booleanValue() : false;
        } catch (Exception ignored) {
            try {
                java.lang.reflect.Method method = target.getClass().getMethod("isItemValidForSlot", Integer.TYPE, ItemStack.class);
                Object result = method.invoke(target, Integer.valueOf(slot), stack);
                return result instanceof Boolean ? ((Boolean) result).booleanValue() : false;
            } catch (Exception ignoredToo) {
                return true;
            }
        }
    }

    private IInventory getSideInventory(int side) {
        if (side == SIDE_DISABLED) {
            return null;
        }
        int[] offsets = getSideOffsets(side);
        return getAdjacentInventory(xCoord + offsets[0], yCoord + offsets[1], zCoord + offsets[2]);
    }

    private int[] getSideOffsets(int side) {
        if (side == SIDE_DOWN) {
            return new int[] {0, -1, 0};
        }
        if (side == SIDE_UP) {
            return new int[] {0, 1, 0};
        }
        if (side == SIDE_NORTH) {
            return new int[] {0, 0, -1};
        }
        if (side == SIDE_SOUTH) {
            return new int[] {0, 0, 1};
        }
        if (side == SIDE_WEST) {
            return new int[] {-1, 0, 0};
        }
        if (side == SIDE_EAST) {
            return new int[] {1, 0, 0};
        }
        return new int[] {0, 0, 0};
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
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && ExtractorRecipes.instance().getResult(stack) != null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        overclockMode = nbt.getBoolean("OverclockMode");
        autoInput = nbt.getBoolean("AutoInput");
        autoOutput = nbt.getBoolean("AutoOutput");
        inputSide = normalizeSide(nbt.getInteger("InputSide"), SIDE_WEST);
        outputSide = normalizeSide(nbt.getInteger("OutputSide"), SIDE_EAST);
        filterMode = normalizeFilterMode(nbt.getInteger("FilterMode"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        nbt.setBoolean("OverclockMode", overclockMode);
        nbt.setBoolean("AutoInput", autoInput);
        nbt.setBoolean("AutoOutput", autoOutput);
        nbt.setInteger("InputSide", inputSide);
        nbt.setInteger("OutputSide", outputSide);
        nbt.setInteger("FilterMode", filterMode);
    }

    private int normalizeSide(int value, int fallback) {
        if (value < SIDE_DISABLED || value > SIDE_EAST) {
            return fallback;
        }
        return value;
    }

    private int normalizeFilterMode(int value) {
        if (value < FILTER_OFF || value > FILTER_BLACKLIST) {
            return FILTER_OFF;
        }
        return value;
    }

    private int cycleSide(int current) {
        if (current >= SIDE_EAST) {
            return SIDE_DISABLED;
        }
        return current + 1;
    }

    public String getInputSideLabel() { return getSideLabel(inputSide); }
    public String getOutputSideLabel() { return getSideLabel(outputSide); }
    public String getFilterModeLabel() {
        if (filterMode == FILTER_WHITELIST) {
            return "БЕЛ";
        }
        if (filterMode == FILTER_BLACKLIST) {
            return "ЧЕР";
        }
        return "ВЫКЛ";
    }

    private String getSideLabel(int side) {
        if (side == SIDE_DISABLED) return "ВЫКЛ";
        if (side == SIDE_DOWN) return "НИЗ";
        if (side == SIDE_UP) return "ВЕРХ";
        if (side == SIDE_NORTH) return "СЕВ";
        if (side == SIDE_SOUTH) return "ЮГ";
        if (side == SIDE_WEST) return "ЗАП";
        return "ВОСТ";
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
    public void cycleInputSide() { inputSide = cycleSide(inputSide); if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void cycleOutputSide() { outputSide = cycleSide(outputSide); if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); }
    public void setClientInputSide(int side) { inputSide = normalizeSide(side, SIDE_WEST); }
    public void setClientOutputSide(int side) { outputSide = normalizeSide(side, SIDE_EAST); }
    public int getInputSide() { return inputSide; }
    public int getOutputSide() { return outputSide; }
    public int getFilterMode() { return filterMode; }
    public void cycleFilterMode() {
        filterMode++;
        if (filterMode > FILTER_BLACKLIST) {
            filterMode = FILTER_OFF;
        }
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }
    public void setClientFilterMode(int mode) { filterMode = normalizeFilterMode(mode); }
}
