package techno.blocks.container;

import net.minecraft.entity.player.InventoryPlayer;
import techno.blocks.tile.TileElectricFurnace;

public class ContainerElectricFurnace extends BaseContainerMachine {
    public final TileElectricFurnace tile;
    public ContainerElectricFurnace(InventoryPlayer inv, TileElectricFurnace tile) { this.tile = tile; }
}
