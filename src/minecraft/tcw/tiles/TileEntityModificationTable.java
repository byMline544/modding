package tcw.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import tcw.items.ItemArmorModule;

public class TileEntityModificationTable extends TileEntityMachine implements IInventory {

    private final ItemStack[] inventory = new ItemStack[3]; // 0 armor in, 1 module, 2 result
    private int progress;

    public TileEntityModificationTable() {
        super(1);
    }

    @Override
    public void updateEntity() {
        if (worldObj == null || worldObj.isRemote) {
            return;
        }

        if (canCraft()) {
            progress++;
            if (progress >= 100) {
                progress = 0;
                craftModule();
            }
        } else if (progress > 0) {
            progress = 0;
        }

        if (worldObj.getWorldTime() % 10 == 0) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public int getProgress() {
        return progress;
    }

    public int getScaledProgress(int scale) {
        return progress * scale / 100;
    }

    private boolean canCraft() {
        if (inventory[0] == null || inventory[1] == null || inventory[2] != null) {
            return false;
        }
        if (!(inventory[1].getItem() instanceof ItemArmorModule)) {
            return false;
        }

        String armorName = inventory[0].getItem().getUnlocalizedName();
        int type = ((ItemArmorModule) inventory[1].getItem()).getModuleType();

        if (type == ItemArmorModule.TYPE_NIGHT_VISION) {
            return armorName != null && (armorName.contains("nano_helmet") || armorName.contains("quantum_helmet"));
        }
        if (type == ItemArmorModule.TYPE_FAST_RUN) {
            return armorName != null && (armorName.contains("nano_leggings") || armorName.contains("quantum_leggings"));
        }

        return false;
    }

    private void craftModule() {
        if (!canCraft()) {
            return;
        }

        ItemStack result = inventory[0].copy();
        if (!result.hasTagCompound()) {
            result.setTagCompound(new NBTTagCompound());
        }

        int type = ((ItemArmorModule) inventory[1].getItem()).getModuleType();
        if (type == ItemArmorModule.TYPE_NIGHT_VISION) {
            result.getTagCompound().setBoolean("TCW_NightVisionModule", true);
        } else if (type == ItemArmorModule.TYPE_FAST_RUN) {
            result.getTagCompound().setBoolean("TCW_FastRunModule", true);
        }

        inventory[0] = null;
        decrStackSize(1, 1);
        inventory[2] = result;
        onInventoryChanged();
    }

    @Override
    public int getSizeInventory() { return inventory.length; }

    @Override
    public ItemStack getStackInSlot(int slot) { return inventory[slot]; }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (inventory[slot] == null) return null;
        if (inventory[slot].stackSize <= count) {
            ItemStack stack = inventory[slot];
            inventory[slot] = null;
            return stack;
        }
        ItemStack split = inventory[slot].splitStack(count);
        if (inventory[slot].stackSize <= 0) inventory[slot] = null;
        return split;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack s = inventory[slot];
            inventory[slot] = null;
            return s;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() { return "container.tcwModificationTable"; }

    @Override
    public boolean isInvNameLocalized() { return false; }

    @Override
    public int getInventoryStackLimit() { return 1; }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 0) {
            if (stack == null || stack.getItem() == null || stack.getItem().getUnlocalizedName() == null) return false;
            String name = stack.getItem().getUnlocalizedName();
            return name.contains("nano_") || name.contains("quantum_");
        }
        return slot == 1 && stack != null && stack.getItem() instanceof ItemArmorModule;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        NBTTagList list = nbt.getTagList("Items");
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
            int slot = tag.getByte("Slot") & 255;
            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        nbt.setTag("Items", list);
    }
}
