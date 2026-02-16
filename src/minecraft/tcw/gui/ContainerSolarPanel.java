package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import tcw.tiles.TileEntitySolarPanel;

public class ContainerSolarPanel extends Container {

    private final TileEntitySolarPanel panel;
    private int lastEnergyScaled;

    public ContainerSolarPanel(InventoryPlayer playerInventory, TileEntitySolarPanel panel) {
        this.panel = panel;

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
        int scaled = panel.getStorage().getEnergyStored() * 10000 / panel.getStorage().getMaxEnergyStored();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);
            if (lastEnergyScaled != scaled) {
                crafting.sendProgressBarUpdate(this, 0, scaled);
            }
        }
        lastEnergyScaled = scaled;
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            panel.setClientEnergyScaled(value);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return panel.worldObj.getBlockTileEntity(panel.xCoord, panel.yCoord, panel.zCoord) == panel
                && player.getDistanceSq((double) panel.xCoord + 0.5D, (double) panel.yCoord + 0.5D, (double) panel.zCoord + 0.5D) <= 64.0D;
    }
}
