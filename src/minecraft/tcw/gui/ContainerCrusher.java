package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.tiles.TileEntityCrusher;

public class ContainerCrusher extends Container {

    private final TileEntityCrusher crusher;
    private int lastProgress;
    private int lastEnergy;

    public ContainerCrusher(InventoryPlayer playerInventory, TileEntityCrusher crusher) {
        this.crusher = crusher;

        addSlotToContainer(new Slot(crusher, 0, 56, 35));
        addSlotToContainer(new Slot(crusher, 1, 116, 35));

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);

            if (lastProgress != crusher.getProgress()) {
                crafting.sendProgressBarUpdate(this, 0, crusher.getProgress());
            }
            if (lastEnergy != crusher.getStorage().getEnergyStored() * 10000 / crusher.getStorage().getMaxEnergyStored()) {
                crafting.sendProgressBarUpdate(this, 1, crusher.getStorage().getEnergyStored() * 10000 / crusher.getStorage().getMaxEnergyStored());
            }
        }

        lastProgress = crusher.getProgress();
        lastEnergy = crusher.getStorage().getEnergyStored() * 10000 / crusher.getStorage().getMaxEnergyStored();
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            crusher.setClientProgress(value);
        } else if (id == 1) {
            crusher.setClientEnergyScaled(value);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return crusher.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack inSlot = slot.getStack();
            itemstack = inSlot.copy();

            if (index < 2) {
                if (!this.mergeItemStack(inSlot, 2, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(inSlot, 0, 1, false)) {
                return null;
            }

            if (inSlot.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
