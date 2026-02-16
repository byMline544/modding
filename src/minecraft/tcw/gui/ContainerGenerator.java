package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.tiles.TileEntityGenerator;

public class ContainerGenerator extends Container {

    private final TileEntityGenerator machine;
    private int lastBurn;
    private int lastEnergy;
    private int lastMode;

    public ContainerGenerator(InventoryPlayer playerInventory, TileEntityGenerator machine) {
        this.machine = machine;
        addSlotToContainer(new Slot(machine, 0, 80, 35));

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
        int energyScaled = machine.getStorage().getEnergyStored() * 10000 / machine.getStorage().getMaxEnergyStored();
        int mode = machine.isEcoMode() ? 1 : 0;
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);
            if (lastBurn != machine.getBurnTime()) {
                crafting.sendProgressBarUpdate(this, 0, machine.getBurnTime());
            }
            if (lastEnergy != energyScaled) {
                crafting.sendProgressBarUpdate(this, 1, energyScaled);
            }
            if (lastMode != mode) {
                crafting.sendProgressBarUpdate(this, 2, mode);
            }
        }

        lastBurn = machine.getBurnTime();
        lastEnergy = energyScaled;
        lastMode = mode;
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            machine.setClientBurnTime(value);
        } else if (id == 1) {
            machine.setClientEnergyScaled(value);
        } else if (id == 2) {
            machine.setClientEcoMode(value);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return machine.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack inSlot = slot.getStack();
            itemstack = inSlot.copy();

            if (index == 0) {
                if (!this.mergeItemStack(inSlot, 1, this.inventorySlots.size(), true)) {
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
