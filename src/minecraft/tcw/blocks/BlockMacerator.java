package tcw.blocks;

import net.minecraft.world.World;

public class BlockMacerator extends BlockBaseMachine {

    public BlockMacerator(int id) {
        super(id, "macerator", 1);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityCrusher();
    }
}
