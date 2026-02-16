package tcw.blocks;

import net.minecraft.world.World;

public class BlockCompressor extends BlockBaseMachine {

    public BlockCompressor(int id) {
        super(id, "compressor", 3);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityCompressor();
    }
}
