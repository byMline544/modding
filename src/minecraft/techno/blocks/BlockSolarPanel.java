package techno.blocks;

import net.minecraft.world.World;
import techno.blocks.tile.TileSolarPanel;
import techno.managers.GuiContainerManager;

public class BlockSolarPanel extends BaseBlockEnergySource {
    public BlockSolarPanel(int id) { super(id, "solar_panel_6sides", GuiContainerManager.GUI_SOLAR); }
    public TileSolarPanel createNewTileEntity(World world) { return new TileSolarPanel(); }
}
