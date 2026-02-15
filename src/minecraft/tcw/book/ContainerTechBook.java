package tcw.book;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerTechBook extends Container {

    public ContainerTechBook(InventoryPlayer inventory) {
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
