package tcw.blocks;

import net.minecraft.world.World;

public class BlockGenerator extends BlockBaseMachine {

    public BlockGenerator(int id) {
        super(id, "generator", 2);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, int metadata) {
        return new tcw.tiles.TileEntityGenerator();
    }
}
