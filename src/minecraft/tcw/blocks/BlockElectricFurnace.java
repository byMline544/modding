package tcw.blocks;

import net.minecraft.world.World;

public class BlockElectricFurnace extends BlockBaseMachine {

    public BlockElectricFurnace(int id) {
        super(id, "electric_furnace", 4);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityElectricFurnace();
    }
}
