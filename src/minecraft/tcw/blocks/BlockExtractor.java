package tcw.blocks;

import net.minecraft.world.World;

public class BlockExtractor extends BlockBaseMachine {

    public BlockExtractor(int id) {
        super(id, "extractor", 7);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityExtractor();
    }
}
