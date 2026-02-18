package techno.blocks;

import net.minecraft.world.World;
import techno.blocks.tile.TileGenerator;
import techno.managers.GuiContainerManager;

public class BlockGenerator extends BaseBlockEnergySource {
    public BlockGenerator(int id) { super(id, "blockGenerator", GuiContainerManager.GUI_GENERATOR); }
    public TileGenerator createNewTileEntity(World world) { return new TileGenerator(); }
}
