package tcw.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tcw.energy.CableTier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCable extends BlockContainer {

    private final CableTier tier;

    public BlockCable(int id, CableTier tier) {
        super(id, Material.circuits);
        this.tier = tier;
        setBlockName("energy_cable_" + tier.textureSuffix);
        setHardness(0.6F);
        setResistance(1.0F);
        setCreativeTab(tcw.TCWCreativeTab.TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("technocloud:energy_cable_" + tier.textureSuffix);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityCable(tier);
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getAABBPool().getAABB(x + 0.25D, y + 0.25D, z + 0.25D, x + 0.75D, y + 0.75D, z + 0.75D);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
    }
}
