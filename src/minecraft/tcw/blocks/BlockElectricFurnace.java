package tcw.blocks;

import net.minecraft.world.World;

public class BlockElectricFurnace extends BlockBaseMachine {

    public BlockElectricFurnace(int id) {
        super(id, "electric_furnace", 4);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, int metadata) {
        return new tcw.tiles.TileEntityElectricFurnace();
    }
}
