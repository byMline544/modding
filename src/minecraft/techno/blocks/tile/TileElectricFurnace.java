package techno.blocks.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import techno.storage.crafting.ElectricFurnaceRecipes;

public class TileElectricFurnace extends BaseTileMachine {
    public ItemStack input;
    public ItemStack output;
    private int progress;

    public TileElectricFurnace() {
        super(20000);
    }

    public boolean wantsEnergy() {
        return storage.getEnergyStored() < storage.getMaxEnergyStored() && input != null;
    }

    public void updateEntity() {
        if (worldObj.isRemote) return;
        ItemStack result = ElectricFurnaceRecipes.getResult(input);
        if (result == null || storage.getEnergyStored() < 16) {
            progress = 0;
            return;
        }
        storage.extractEnergy(16, false);
        progress++;
        if (progress >= 120) {
            progress = 0;
            if (output == null) output = result.copy();
            else output.stackSize += result.stackSize;
            input.stackSize--;
            if (input.stackSize <= 0) input = null;
            onInventoryChanged();
        }
    }

    public int getProgress() { return progress; }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        if (nbt.hasKey("Input")) input = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Input"));
        if (nbt.hasKey("Output")) output = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Output"));
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        if (input != null) { NBTTagCompound t = new NBTTagCompound(); input.writeToNBT(t); nbt.setTag("Input", t); }
        if (output != null) { NBTTagCompound t = new NBTTagCompound(); output.writeToNBT(t); nbt.setTag("Output", t); }
    }
}
