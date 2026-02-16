package tcw.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tcw.energy.CableTier;
import tcw.tiles.TileEntityCable;
import tcw.tiles.TileEntityMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCable extends BlockContainer {

    private final CableTier tier;

    public BlockCable(int id, CableTier tier) {
        super(id, Material.circuits);
        this.tier = tier;
        setUnlocalizedName("energy_cable_" + tier.textureSuffix);
        setHardness(0.6F);
        setResistance(1.0F);
        setCreativeTab(tcw.TCWCreativeTab.TAB_MACHINES);
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
    public void setBlockBoundsForItemRender() {
        setBlockBounds(0.3125F, 0.3125F, 0.3125F, 0.6875F, 0.6875F, 0.6875F);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    private boolean canConnectTo(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        return tile instanceof TileEntityCable || tile instanceof TileEntityMachine;
    }

    private float[] getBounds(IBlockAccess world, int x, int y, int z) {
        boolean down = canConnectTo(world, x, y - 1, z);
        boolean up = canConnectTo(world, x, y + 1, z);
        boolean north = canConnectTo(world, x, y, z - 1);
        boolean south = canConnectTo(world, x, y, z + 1);
        boolean west = canConnectTo(world, x - 1, y, z);
        boolean east = canConnectTo(world, x + 1, y, z);

        int connections = (down ? 1 : 0) + (up ? 1 : 0) + (north ? 1 : 0) + (south ? 1 : 0) + (west ? 1 : 0) + (east ? 1 : 0);

        float minX = 0.3125F;
        float minY = 0.3125F;
        float minZ = 0.3125F;
        float maxX = 0.6875F;
        float maxY = 0.6875F;
        float maxZ = 0.6875F;

        if (connections <= 2) {
            minX = west ? 0.0F : minX;
            maxX = east ? 1.0F : maxX;
            minY = down ? 0.0F : minY;
            maxY = up ? 1.0F : maxY;
            minZ = north ? 0.0F : minZ;
            maxZ = south ? 1.0F : maxZ;
        } else {
            // Для перекрестков/колец не даём кабелю схлопываться в “полублок”.
            minX = west ? 0.1875F : minX;
            maxX = east ? 0.8125F : maxX;
            minY = down ? 0.1875F : minY;
            maxY = up ? 0.8125F : maxY;
            minZ = north ? 0.1875F : minZ;
            maxZ = south ? 0.8125F : maxZ;
        }

        return new float[] {minX, minY, minZ, maxX, maxY, maxZ};
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        float[] b = getBounds(world, x, y, z);
        return AxisAlignedBB.getAABBPool().getAABB(x + b[0], y + b[1], z + b[2], x + b[3], y + b[4], z + b[5]);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        float[] b = getBounds(world, x, y, z);
        setBlockBounds(b[0], b[1], b[2], b[3], b[4], b[5]);
    }
}
