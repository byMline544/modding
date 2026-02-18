package techno.blocks.container;

import net.minecraft.entity.player.InventoryPlayer;
import techno.blocks.tile.TileGenerator;

public class ContainerGenerator extends BaseContainerEnergySource {
    public final TileGenerator tile;
    public ContainerGenerator(InventoryPlayer inv, TileGenerator tile) { this.tile = tile; }
}
