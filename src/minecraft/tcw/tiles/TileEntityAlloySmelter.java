package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tcw.crafting.AlloySmelterRecipes;

public class TileEntityAlloySmelter extends TileEntityInventoryMachine {

    private int progress;

    public TileEntityAlloySmelter() {
        super(180000, 3);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        boolean canRun = canProcess() && ensurePowerLinkOrDropEnergy() && storage.getEnergyStored() >= 36;
        if (canRun) {
            storage.extractEnergy(36, false);
            progress++;
            if (progress >= 180) {
                progress = 0;
                process();
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else if (progress > 0) {
            progress = 0;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    private boolean canProcess() {
        ItemStack result = AlloySmelterRecipes.instance().getResult(inventory[0], inventory[1]);
        if (result == null) {
            return false;
        }
        if (inventory[2] == null) {
            return true;
        }
        if (!inventory[2].isItemEqual(result)) {
            return false;
        }
        int next = inventory[2].stackSize + result.stackSize;
        return next <= getInventoryStackLimit() && next <= result.getMaxStackSize();
    }

    private void process() {
        if (!canProcess()) {
            return;
        }
        ItemStack result = AlloySmelterRecipes.instance().getResult(inventory[0], inventory[1]);
        if (inventory[2] == null) {
            inventory[2] = result;
        } else {
            inventory[2].stackSize += result.stackSize;
        }

        inventory[0].stackSize--;
        if (inventory[0].stackSize <= 0) {
            inventory[0] = null;
        }

        inventory[1].stackSize--;
        if (inventory[1].stackSize <= 0) {
            inventory[1] = null;
        }

        onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "container.tcwAlloySmelter";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        }
        if (slot == 0) {
            return AlloySmelterRecipes.instance().getResult(stack, inventory[1]) != null || inventory[1] == null;
        }
        return AlloySmelterRecipes.instance().getResult(inventory[0], stack) != null || inventory[0] == null;
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
        return progress * scale / 180;
    }
}
