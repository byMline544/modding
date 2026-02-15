package tcw.blocks;

import net.minecraft.world.World;

public class BlockExtractor extends BlockBaseMachine {

    public BlockExtractor(int id) {
        super(id, "extractor", 7);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, int metadata) {
        return new tcw.tiles.TileEntityExtractor();
    }
}
