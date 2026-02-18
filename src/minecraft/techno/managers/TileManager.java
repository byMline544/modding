package techno.managers;

import cpw.mods.fml.common.registry.GameRegistry;
import techno.blocks.tile.TileElectricFurnace;
import techno.blocks.tile.TileGenerator;
import techno.blocks.tile.TileSolarPanel;

public class TileManager {
    public static void init() {
        GameRegistry.registerTileEntity(TileGenerator.class, "techno.tile.generator");
        GameRegistry.registerTileEntity(TileSolarPanel.class, "techno.tile.solar");
        GameRegistry.registerTileEntity(TileElectricFurnace.class, "techno.tile.electricfurnace");
    }
}
