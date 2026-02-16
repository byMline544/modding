package tcw.blocks;

import net.minecraft.world.World;

public class BlockGenerator extends BlockBaseMachine {

    public BlockGenerator(int id) {
        super(id, "generator", 2);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityGenerator();
    }
}
