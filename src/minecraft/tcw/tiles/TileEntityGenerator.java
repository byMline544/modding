package tcw.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import tcw.energy.EnergyNetHelper;

public class TileEntityGenerator extends TileEntityInventoryMachine {

    public int burnTime;
    private int currentItemBurnTime;
    private int burnTickAccumulator;

    public TileEntityGenerator() {
        super(160000, 1);
    }

    @Override
    public int getDesiredReceivePerTick() {
        return 0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }

        int beforeEnergy = storage.getEnergyStored();
        int beforeBurn = burnTime;

        int outputPerTick = 24;
        boolean hadBurning = burnTime > 0;
        int fuelSaveDivider = 1 + getOverclockerModules();

        if (burnTime > 0 && storage.getEnergyStored() + outputPerTick <= storage.getMaxEnergyStored()) {
            burnTickAccumulator++;
            if (burnTickAccumulator >= fuelSaveDivider) {
                burnTime--;
                burnTickAccumulator = 0;
            }
            storage.receiveEnergy(outputPerTick + getOverclockerModules(), false);
        }

        if (burnTime <= 0 && inventory[0] != null && storage.getEnergyStored() + outputPerTick <= storage.getMaxEnergyStored()) {
            int itemBurn = TileEntityFurnace.getItemBurnTime(inventory[0]);
            if (itemBurn > 0) {
                currentItemBurnTime = Math.max(12, itemBurn / 24);
                burnTime = currentItemBurnTime;
                burnTickAccumulator = 0;
                inventory[0].stackSize--;
                if (inventory[0].stackSize <= 0) {
                    inventory[0] = null;
                }
                onInventoryChanged();
            }
        }

        int reserve = 0;
        int sendPerTick = 96;
        sendPerTick += getTransformerModules() * 12;
        if (storage.getEnergyStored() > reserve) {
            EnergyNetHelper.pushToNeighbors(this, sendPerTick, 0, reserve, sendPerTick);
        }

        if (worldObj.getWorldTime() % 10 == 0 || hadBurning != (burnTime > 0)) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        if (beforeEnergy != storage.getEnergyStored() || beforeBurn != burnTime) {
            onInventoryChanged();
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
        burnTickAccumulator = nbt.getInteger("BurnAcc");
        if (currentItemBurnTime <= 0) {
            currentItemBurnTime = 200;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("BurnTime", burnTime);
        nbt.setInteger("CurrentItemBurnTime", currentItemBurnTime);
        nbt.setInteger("BurnAcc", burnTickAccumulator);
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
    public int getScaledBurnTime(int scale) {
        int max = currentItemBurnTime <= 0 ? 200 : currentItemBurnTime;
        return burnTime * scale / max;
    }
}
