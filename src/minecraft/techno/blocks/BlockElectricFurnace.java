package techno.blocks;

import net.minecraft.world.World;
import techno.blocks.tile.TileElectricFurnace;
import techno.managers.GuiContainerManager;

public class BlockElectricFurnace extends BaseBlockMachine {
    public BlockElectricFurnace(int id) { super(id, "blockElectricfurnace", GuiContainerManager.GUI_FURNACE); }
    public TileElectricFurnace createNewTileEntity(World world) { return new TileElectricFurnace(); }
}
