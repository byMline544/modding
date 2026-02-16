package tcw.gui;

import net.minecraft.entity.player.InventoryPlayer;
import tcw.tiles.TileEntityMacerator;

public class ContainerMacerator extends ContainerCrusher {

    public ContainerMacerator(InventoryPlayer playerInventory, TileEntityMacerator macerator) {
        super(playerInventory, macerator);
    }
}
