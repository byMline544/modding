package tcw.blocks;

import net.minecraft.world.World;

public class BlockCharger extends BlockBaseMachine {

    public BlockCharger(int id) {
        super(id, "charger", 9);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityCharger();
    }
}
