package tcw.blocks;

import net.minecraft.world.World;

public class BlockWiremill extends BlockBaseMachine {

    public BlockWiremill(int id) {
        super(id, "wiremill", 6);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityWiremill();
    }
}
