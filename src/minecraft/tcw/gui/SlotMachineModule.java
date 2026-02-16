package tcw.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.items.ItemMachineModule;

public class SlotMachineModule extends Slot {

    public SlotMachineModule(IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemMachineModule;
    }

    @Override
    public int getSlotStackLimit() {
        return 8;
    }
}
