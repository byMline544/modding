package tcw.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockBaseMachine {

    private final int storage;
    private final int generation;

    public BlockSolarPanel(int id, String name, int storage, int generation) {
        super(id, name);
        this.storage = storage;
        this.generation = generation;
        setStepSound(soundGlassFootstep);
        setHardness(2.5F);
        setResistance(4.0F);
    }

    public int getStorage() {
        return storage;
    }

    public int getGeneration() {
        return generation;
    }

    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, int metadata) {
        return new tcw.tiles.TileEntitySolarPanel(storage, generation);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 0;
    }
}
