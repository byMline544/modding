package tcw.blocks;

import net.minecraft.world.World;

public class BlockCrusher extends BlockBaseMachine {

    public BlockCrusher(int id) {
        super(id, "crusher", 1);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityCrusher();
    }
}
