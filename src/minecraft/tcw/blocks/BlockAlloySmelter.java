package tcw.blocks;

import net.minecraft.world.World;

public class BlockAlloySmelter extends BlockBaseMachine {

    public BlockAlloySmelter(int id) {
        super(id, "alloy_smelter", 5);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, int metadata) {
        return new tcw.tiles.TileEntityAlloySmelter();
    }
}
