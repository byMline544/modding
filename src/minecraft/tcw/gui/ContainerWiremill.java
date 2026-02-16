package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.tiles.TileEntityWiremill;

public class ContainerWiremill extends Container {

    private final TileEntityWiremill machine;
    private int lastProgress;
    private int lastEnergy;

    public ContainerWiremill(InventoryPlayer playerInventory, TileEntityWiremill machine) {
        this.machine = machine;
        addSlotToContainer(new Slot(machine, 0, 56, 35));
        addSlotToContainer(new Slot(machine, 1, 116, 35));

        for (int i = 0; i < machine.getModuleSlotCount(); i++) {
            addSlotToContainer(new SlotMachineModule(machine, machine.getModuleSlotStart() + i, 152, 8 + i * 16));
        }

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
            if (lastProgress != machine.getProgress()) {
                crafting.sendProgressBarUpdate(this, 0, machine.getProgress());
            }
            int energyScaled = machine.getStorage().getEnergyStored() * 10000 / machine.getStorage().getMaxEnergyStored();
            if (lastEnergy != energyScaled) {
                crafting.sendProgressBarUpdate(this, 1, energyScaled);
            }
        }
        lastProgress = machine.getProgress();
        lastEnergy = machine.getStorage().getEnergyStored() * 10000 / machine.getStorage().getMaxEnergyStored();
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            machine.setClientProgress(value);
        } else if (id == 1) {
            machine.setClientEnergyScaled(value);
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
            if (index < 2 + machine.getModuleSlotCount()) {
                if (!this.mergeItemStack(inSlot, 2, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(inSlot, 0, 1, false)) {
                return null;
            }

            if (inSlot.stackSize <= 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
