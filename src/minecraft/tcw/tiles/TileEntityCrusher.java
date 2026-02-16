package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tcw.crafting.CrusherRecipes;

public class TileEntityCrusher extends TileEntityInventoryMachine {

    public int progress;

    public TileEntityCrusher() {
        super(120000, 2);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        boolean linked = ensurePowerLinkOrDropEnergy();
        int cost = getEnergyCostWithModules(20);
        boolean canRun = linked && canProcess() && storage.getEnergyStored() >= cost;
        tickMachineEffects(canRun);
        if (canRun) {
            storage.extractEnergy(cost, false);
            progress += getProgressStepWithModules();
            if (progress >= 120) {
                progress = 0;
                processItem();
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else if (progress != 0) {
            progress = 0;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    private boolean canProcess() {
        ItemStack result = CrusherRecipes.instance().getResult(inventory[0]);
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

    private void processItem() {
        if (!canProcess()) {
            return;
        }
        ItemStack result = CrusherRecipes.instance().getResult(inventory[0]);
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

    @Override
    public String getInvName() {
        return "container.tcwCrusher";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && CrusherRecipes.instance().getResult(stack) != null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
    }

    public int getProgress() {
        return progress;
    }

    public void setClientProgress(int value) {
        this.progress = value;
    }

    public void setClientEnergyScaled(int scaled) {
        int energy = this.storage.getMaxEnergyStored() * scaled / 10000;
        this.storage.setEnergy(energy);
    }

    public int getScaledProgress(int scale) {
        return progress * scale / 120;
    }

    public int getScaledEnergy(int scale) {
        return storage.getEnergyStored() * scale / storage.getMaxEnergyStored();
    }
}
