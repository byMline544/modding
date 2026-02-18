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

    public ContainerGenerator(InventoryPlayer playerInventory, TileEntityGenerator machine) {
        this.machine = machine;
        addSlotToContainer(new Slot(machine, 0, 56, 53));

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
        int energyScaled = machine.getStorage().getEnergyStored() * 10000 / machine.getStorage().getMaxEnergyStored();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);
            if (lastBurn != machine.getBurnTime()) {
                crafting.sendProgressBarUpdate(this, 0, machine.getBurnTime());
            }
            if (lastEnergy != energyScaled) {
                crafting.sendProgressBarUpdate(this, 1, energyScaled);
            }
        }
        lastBurn = machine.getBurnTime();
        lastEnergy = energyScaled;
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            machine.setClientBurnTime(value);
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
        ItemStack ret = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack in = slot.getStack();
            ret = in.copy();
            if (index < 1 + machine.getModuleSlotCount()) {
                if (!mergeItemStack(in, 1 + machine.getModuleSlotCount(), inventorySlots.size(), true)) return null;
            } else if (!mergeItemStack(in, 0, 1, false)) {
                return null;
            }
            if (in.stackSize <= 0) slot.putStack((ItemStack) null); else slot.onSlotChanged();
        }
        return ret;
    }
}
