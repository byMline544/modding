package tcw.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileEntityInventoryMachine extends TileEntityMachine implements IInventory {

    protected final ItemStack[] inventory;

    protected TileEntityInventoryMachine(int capacity, int slots) {
        super(capacity);
        this.inventory = new ItemStack[slots];
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (inventory[slot] == null) {
            return null;
        }
        if (inventory[slot].stackSize <= count) {
            ItemStack itemstack = inventory[slot];
            inventory[slot] = null;
            onInventoryChanged();
            return itemstack;
        }
        ItemStack itemstack = inventory[slot].splitStack(count);
        if (inventory[slot].stackSize <= 0) {
            inventory[slot] = null;
        }
        onInventoryChanged();
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack itemstack = inventory[slot];
            inventory[slot] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }


    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return isStackValidForSlot(slot, stack);
    }

    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    protected boolean hasExternalPowerLink() {
        if (worldObj == null) {
            return false;
        }
        int[][] o = new int[][] { {1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}, {0,0,1}, {0,0,-1} };
        for (int i = 0; i < o.length; i++) {
            net.minecraft.tileentity.TileEntity tile = worldObj.getBlockTileEntity(xCoord + o[i][0], yCoord + o[i][1], zCoord + o[i][2]);
            if (tile instanceof TileEntityCable || tile instanceof TileEntityGenerator || tile instanceof TileEntitySolarPanel) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
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
