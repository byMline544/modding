package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import tcw.energy.EnergyNetHelper;

public class TileEntityGenerator extends TileEntityInventoryMachine {

    public int burnTime;
    private int currentItemBurnTime;
    private boolean ecoMode;

    public TileEntityGenerator() {
        super(160000, 1);
    }

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }

        int outputPerTick = ecoMode ? 3 : 5;
        boolean hadBurning = burnTime > 0;

        if (burnTime > 0 && storage.getEnergyStored() + outputPerTick <= storage.getMaxEnergyStored()) {
            burnTime--;
            storage.receiveEnergy(outputPerTick, false);
        }

        if (burnTime <= 0 && inventory[0] != null && storage.getEnergyStored() + outputPerTick <= storage.getMaxEnergyStored()) {
            int itemBurn = TileEntityFurnace.getItemBurnTime(inventory[0]);
            if (itemBurn > 0) {
                currentItemBurnTime = Math.max(20, itemBurn / 16);
                burnTime = currentItemBurnTime;
                inventory[0].stackSize--;
                if (inventory[0].stackSize <= 0) {
                    inventory[0] = null;
                }
                onInventoryChanged();
            }
        }

        int reserve = ecoMode ? 400 : 800;
        int sendPerTick = ecoMode ? 18 : 26;
        if (storage.getEnergyStored() > reserve) {
            EnergyNetHelper.pushToNeighbors(this, sendPerTick, 0, reserve, sendPerTick);
        }

        if (worldObj.getWorldTime() % 10 == 0 || hadBurning != (burnTime > 0)) {
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
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        burnTime = nbt.getInteger("BurnTime");
        currentItemBurnTime = nbt.getInteger("CurrentItemBurnTime");
        ecoMode = nbt.getBoolean("EcoMode");
        if (currentItemBurnTime <= 0) {
            currentItemBurnTime = 200;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("BurnTime", burnTime);
        nbt.setInteger("CurrentItemBurnTime", currentItemBurnTime);
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
        int max = currentItemBurnTime <= 0 ? 200 : currentItemBurnTime;
        return burnTime * scale / max;
    }
}
