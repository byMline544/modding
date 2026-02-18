package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.tiles.TileEntityCharger;

public class ContainerCharger extends Container {

    private final TileEntityCharger machine;
    private int lastProgress;
    private int lastEnergy;
    private int lastMode;

    public ContainerCharger(InventoryPlayer playerInventory, TileEntityCharger machine) {
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
        int energyScaled = machine.getStorage().getEnergyStored() * 10000 / machine.getStorage().getMaxEnergyStored();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting c = (ICrafting) this.crafters.get(i);
            if (lastProgress != machine.getProgress()) c.sendProgressBarUpdate(this, 0, machine.getProgress());
            if (lastEnergy != energyScaled) c.sendProgressBarUpdate(this, 1, energyScaled);
            int mode = machine.isFastMode() ? 1 : 0;
            if (lastMode != mode) c.sendProgressBarUpdate(this, 2, mode);
        }
        lastProgress = machine.getProgress();
        lastEnergy = energyScaled;
        lastMode = machine.isFastMode() ? 1 : 0;
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) machine.setClientProgress(value);
        else if (id == 1) machine.setClientEnergyScaled(value);
        else if (id == 2) machine.setClientFastMode(value);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) { return machine.isUseableByPlayer(player); }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack ret = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack in = slot.getStack();
            ret = in.copy();
            if (index < 2 + machine.getModuleSlotCount()) {
                if (!mergeItemStack(in, 2, inventorySlots.size(), true)) return null;
            } else if (!mergeItemStack(in, 0, 1, false)) return null;
            if (in.stackSize <= 0) slot.putStack((ItemStack) null); else slot.onSlotChanged();
        }
        return ret;
    }
}
