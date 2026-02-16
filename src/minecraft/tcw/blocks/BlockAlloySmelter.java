package tcw.blocks;

import net.minecraft.world.World;

public class BlockAlloySmelter extends BlockBaseMachine {

    public BlockAlloySmelter(int id) {
        super(id, "alloy_smelter", 5);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityAlloySmelter();
    }
}
