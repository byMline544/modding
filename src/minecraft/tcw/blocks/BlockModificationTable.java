package tcw.blocks;

import net.minecraft.world.World;
import tcw.tiles.TileEntityModificationTable;

public class BlockModificationTable extends BlockBaseMachine {

    public BlockModificationTable(int id) {
        super(id, "modification_table", 12);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new TileEntityModificationTable();
    }
}
