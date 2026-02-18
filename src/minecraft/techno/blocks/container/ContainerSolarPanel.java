package techno.blocks.container;

import net.minecraft.entity.player.InventoryPlayer;
import techno.blocks.tile.TileSolarPanel;

public class ContainerSolarPanel extends BaseContainerEnergySource {
    public final TileSolarPanel tile;
    public ContainerSolarPanel(InventoryPlayer inv, TileSolarPanel tile) { this.tile = tile; }
}
