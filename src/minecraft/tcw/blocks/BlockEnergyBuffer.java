package tcw.blocks;

import net.minecraft.world.World;
import tcw.tiles.TileEntityEnergyBuffer;

public class BlockEnergyBuffer extends BlockBaseMachine {

    private final TileEntityEnergyBuffer.BufferTier tier;

    public BlockEnergyBuffer(int id, String textureKey, TileEntityEnergyBuffer.BufferTier tier) {
        super(id, textureKey);
        this.tier = tier;
    }

    @Override
    public net.minecraft.tileentity.TileEntity createNewTileEntity(World world) {
        return new TileEntityEnergyBuffer(tier);
    }
}
