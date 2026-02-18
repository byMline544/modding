package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.tiles.TileEntityModificationTable;

public class ContainerModificationTable extends Container {

    private final TileEntityModificationTable table;
    private int lastProgress;

    public ContainerModificationTable(InventoryPlayer playerInventory, TileEntityModificationTable table) {
        this.table = table;

        addSlotToContainer(new Slot(table, 0, 44, 35));
        addSlotToContainer(new Slot(table, 1, 80, 35));
        addSlotToContainer(new Slot(table, 2, 116, 35));

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
            ICrafting c = (ICrafting) this.crafters.get(i);
            if (lastProgress != table.getProgress()) {
                c.sendProgressBarUpdate(this, 0, table.getProgress());
            }
        }
        lastProgress = table.getProgress();
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            // client-only sync not critical for logic; GUI reads scaled from tile update
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return table.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack ret = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack in = slot.getStack();
            ret = in.copy();

            if (index < 3) {
                if (!mergeItemStack(in, 3, inventorySlots.size(), true)) return null;
            } else if (table.isStackValidForSlot(0, in)) {
                if (!mergeItemStack(in, 0, 1, false)) return null;
            } else if (table.isStackValidForSlot(1, in)) {
                if (!mergeItemStack(in, 1, 2, false)) return null;
            } else {
                return null;
            }

            if (in.stackSize <= 0) slot.putStack((ItemStack) null); else slot.onSlotChanged();
        }
        return ret;
    }
}
