package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tcw.crafting.CompressorRecipes;

public class TileEntityCompressor extends TileEntityInventoryMachine {

    public int progress;

    public TileEntityCompressor() {
        super(200000, 2);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        boolean canRun = canProcess() && storage.getEnergyStored() >= 28;
        if (canRun) {
            storage.extractEnergy(28, false);
            progress++;
            if (progress >= 160) {
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
        ItemStack result = CompressorRecipes.instance().getResult(inventory[0]);
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
        ItemStack result = CompressorRecipes.instance().getResult(inventory[0]);
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
        return "container.tcwCompressor";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && CompressorRecipes.instance().getResult(stack) != null;
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
        return progress * scale / 160;
    }
}
