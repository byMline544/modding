package tcw.blocks;

import net.minecraft.world.World;

public class BlockAssembler extends BlockBaseMachine {

    public BlockAssembler(int id) {
        super(id, "assembler", 8);
    }

    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, int metadata) {
        return new tcw.tiles.TileEntityAssembler();
    }
}
