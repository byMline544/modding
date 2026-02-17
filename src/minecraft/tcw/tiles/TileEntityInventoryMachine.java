package tcw.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import tcw.items.ItemMachineModule;

public abstract class TileEntityInventoryMachine extends TileEntityMachine implements IInventory {

    protected final ItemStack[] inventory;

    protected TileEntityInventoryMachine(int capacity, int slots) {
        super(capacity);
        this.inventory = new ItemStack[slots + 4];
    }

    public int getModuleSlotStart() {
        return inventory.length - 4;
    }

    public int getModuleSlotCount() {
        return 4;
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
        if (slot >= getModuleSlotStart()) {
            return stack != null && stack.getItem() instanceof ItemMachineModule;
        }
        return isStackValidForSlot(slot, stack);
    }

    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        int expectedCapacity = baseCapacity + getCapacityModules() * 50000;
        if (storage.getMaxEnergyStored() != expectedCapacity) {
            int energy = storage.getEnergyStored();
            storage = new tcw.energy.EnergyStorageTCW(expectedCapacity);
            storage.setEnergy(Math.min(energy, expectedCapacity));
            if (worldObj != null && !worldObj.isRemote) {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    public boolean isNetworkLinked() {
        return hasExternalPowerLink();
    }

    protected boolean hasExternalPowerLink() {
        if (worldObj == null) {
            return false;
        }
        int[][] o = new int[][] { {1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}, {0,0,1}, {0,0,-1} };
        for (int i = 0; i < o.length; i++) {
            net.minecraft.tileentity.TileEntity tile = worldObj.getBlockTileEntity(xCoord + o[i][0], yCoord + o[i][1], zCoord + o[i][2]);
            if (tile instanceof TileEntityGenerator) {
                return true;
            }
            if (tile instanceof TileEntitySolarPanel) {
                return true;
            }
            if (tile instanceof TileEntityCable) {
                if (hasPoweredSourceInCableNetwork((TileEntityCable) tile, 32)) {
                    return true;
                }
            }
            if (tile instanceof TileEntityEnergyBuffer) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPoweredSourceInCableNetwork(TileEntityCable start, int maxDepth) {
        if (start == null || start.worldObj == null) {
            return false;
        }

        java.util.ArrayDeque<net.minecraft.tileentity.TileEntity> queue = new java.util.ArrayDeque<net.minecraft.tileentity.TileEntity>();
        java.util.ArrayDeque<Integer> depth = new java.util.ArrayDeque<Integer>();
        java.util.HashSet<String> seen = new java.util.HashSet<String>();

        queue.add(start);
        depth.add(Integer.valueOf(0));

        while (!queue.isEmpty()) {
            net.minecraft.tileentity.TileEntity tile = queue.poll();
            int d = depth.poll().intValue();
            String key = tile.xCoord + ":" + tile.yCoord + ":" + tile.zCoord;
            if (!seen.add(key)) {
                continue;
            }

            net.minecraftforge.common.ForgeDirection[] dirs = net.minecraftforge.common.ForgeDirection.VALID_DIRECTIONS;
            for (int i = 0; i < dirs.length; i++) {
                net.minecraftforge.common.ForgeDirection dir = dirs[i];
                net.minecraft.tileentity.TileEntity t = tile.worldObj.getBlockTileEntity(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY,
                        tile.zCoord + dir.offsetZ);
                if (t == null) {
                    continue;
                }

                if (t instanceof TileEntityGenerator && ((TileEntityGenerator) t).getStorage().getEnergyStored() > 0) {
                    return true;
                }
                if (t instanceof TileEntitySolarPanel && ((TileEntitySolarPanel) t).getStorage().getEnergyStored() > 0) {
                    return true;
                }
                if (t instanceof TileEntityEnergyBuffer && ((TileEntityEnergyBuffer) t).getStorage().getEnergyStored() > 0) {
                    return true;
                }
                if (t instanceof TileEntityCable && d < maxDepth) {
                    queue.add(t);
                    depth.add(Integer.valueOf(d + 1));
                }
            }
        }

        return false;
    }

    protected void tickMachineEffects(boolean active) {
        if (!active || worldObj == null) {
            return;
        }

        if (worldObj.rand.nextInt(4) == 0) {
            worldObj.spawnParticle("smoke", xCoord + 0.5D, yCoord + 1.02D, zCoord + 0.5D, 0.0D, 0.02D, 0.0D);
        }

        if (!worldObj.isRemote && worldObj.getWorldTime() % 40 == 0) {
            worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.fizz", 0.2F, 1.8F);
        }
    }

    protected int getEnergyCostWithModules(int baseCost) {
        int over = getOverclockerModules();
        int cost = baseCost;
        for (int i = 0; i < over; i++) {
            cost += Math.max(1, baseCost / 2);
        }
        return cost;
    }

    protected int getProgressStepWithModules() {
        return 1 + getOverclockerModules();
    }

    protected boolean ensurePowerLinkOrDropEnergy() {
        return hasExternalPowerLink();
    }


    @Override
    public int getOverclockerModules() {
        return getInstalledModuleCount(ItemMachineModule.TYPE_OVERCLOCKER);
    }

    @Override
    public int getTransformerModules() {
        return getInstalledModuleCount(ItemMachineModule.TYPE_TRANSFORMER);
    }

    @Override
    public int getCapacityModules() {
        return getInstalledModuleCount(ItemMachineModule.TYPE_CAPACITY);
    }

    private int getInstalledModuleCount(int moduleType) {
        int count = 0;
        for (int i = getModuleSlotStart(); i < inventory.length; i++) {
            ItemStack stack = inventory[i];
            if (stack != null && stack.getItem() instanceof ItemMachineModule) {
                if (((ItemMachineModule) stack.getItem()).getModuleType() == moduleType) {
                    count += stack.stackSize;
                }
            }
        }
        return Math.min(16, count);
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
