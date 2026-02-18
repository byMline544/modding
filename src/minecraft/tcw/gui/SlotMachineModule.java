package tcw.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.items.ItemMachineModule;
import tcw.tiles.TileEntityInventoryMachine;

public class SlotMachineModule extends Slot {

    public SlotMachineModule(IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemMachineModule)) {
            return false;
        }

        if (!(inventory instanceof TileEntityInventoryMachine)) {
            return true;
        }

        TileEntityInventoryMachine machine = (TileEntityInventoryMachine) inventory;
        int type = ((ItemMachineModule) stack.getItem()).getModuleType();
        for (int i = machine.getModuleSlotStart(); i < machine.getModuleSlotStart() + machine.getModuleSlotCount(); i++) {
            if (i == getSlotIndex()) {
                continue;
            }
            ItemStack inSlot = machine.getStackInSlot(i);
            if (inSlot != null && inSlot.getItem() instanceof ItemMachineModule) {
                if (((ItemMachineModule) inSlot.getItem()).getModuleType() == type) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int getSlotStackLimit() {
        return 16;
    }
}
