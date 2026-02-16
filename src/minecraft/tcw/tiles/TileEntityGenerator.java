package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityGenerator extends TileEntityInventoryMachine {

    public int burnTime;
    private boolean ecoMode;

    public TileEntityGenerator() {
        super(300000, 1);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        if (burnTime > 0) {
            burnTime--;
            storage.receiveEnergy(ecoMode ? 20 : 32, false);
        }

        if (burnTime <= 0 && inventory[0] != null) {
            int itemBurn = TileEntityFurnace.getItemBurnTime(inventory[0]);
            if (itemBurn > 0) {
                burnTime = itemBurn;
                inventory[0].stackSize--;
                if (inventory[0].stackSize <= 0) {
                    inventory[0] = null;
                }
                onInventoryChanged();
            }
        }

        if (worldObj.getWorldTime() % 20 == 0) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public String getInvName() {
        return "container.tcwGenerator";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        burnTime = nbt.getInteger("BurnTime");
        ecoMode = nbt.getBoolean("EcoMode");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("BurnTime", burnTime);
        nbt.setBoolean("EcoMode", ecoMode);
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setClientBurnTime(int value) {
        this.burnTime = value;
    }

    public void setClientEnergyScaled(int scaled) {
        int energy = this.storage.getMaxEnergyStored() * scaled / 10000;
        this.storage.setEnergy(energy);
    }


    public boolean isEcoMode() {
        return ecoMode;
    }

    public void toggleEcoMode() {
        ecoMode = !ecoMode;
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void setClientEcoMode(int mode) {
        ecoMode = mode == 1;
    }

    public int getScaledBurnTime(int scale) {
        int max = 1600;
        return burnTime * scale / max;
    }
}
