package tcw.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import tcw.managers.BlockManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBaseMachine extends BlockContainer {

    protected Icon[] idleIcons = new Icon[6];
    protected Icon[] activeIcons = new Icon[6];
    private final String textureKey;
    private final int guiId;

    public BlockBaseMachine(int id, String textureKey) {
        this(id, textureKey, -1);
    }

    public BlockBaseMachine(int id, String textureKey, int guiId) {
        super(id, Material.iron);
        this.textureKey = textureKey;
        this.guiId = guiId;
        setUnlocalizedName(textureKey);
        setHardness(3.5F);
        setResistance(8.0F);
        setCreativeTab(tcw.TCWCreativeTab.TAB_MACHINES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < 6; i++) {
            idleIcons[i] = register.registerIcon("technocloud:block" + toTextureSuffix(textureKey) + "_" + i);
            activeIcons[i] = register.registerIcon("technocloud:block" + toTextureSuffix(textureKey) + "_" + (i + 6));
        }
        this.blockIcon = idleIcons[3];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return idleIcons[side % 6] != null ? idleIcons[side % 6] : blockIcon;
    }

    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        net.minecraft.tileentity.TileEntity te = world.getBlockTileEntity(x, y, z);
        boolean active = isMachineActive(te);
        int face = world.getBlockMetadata(x, y, z);
        int local = mapSideByFace(side, face);
        Icon icon = (active ? activeIcons[local] : idleIcons[local]);
        return icon != null ? icon : blockIcon;
    }


    protected String toTextureSuffix(String key) {
        String[] parts = key.split("_");
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() == 0) continue;
            String part = parts[i].toLowerCase();
            if (i == 0) {
                b.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
            } else {
                b.append(part);
            }
        }
        return b.toString();
    }

    protected boolean isMachineActive(TileEntity te) {
        if (te instanceof tcw.tiles.TileEntityGenerator) {
            return ((tcw.tiles.TileEntityGenerator) te).getBurnTime() > 0;
        }
        if (te instanceof tcw.tiles.TileEntityCrusher) return ((tcw.tiles.TileEntityCrusher) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityMacerator) return ((tcw.tiles.TileEntityMacerator) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityCompressor) return ((tcw.tiles.TileEntityCompressor) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityElectricFurnace) return ((tcw.tiles.TileEntityElectricFurnace) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityAlloySmelter) return ((tcw.tiles.TileEntityAlloySmelter) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityWiremill) return ((tcw.tiles.TileEntityWiremill) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityExtractor) return ((tcw.tiles.TileEntityExtractor) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityAssembler) return ((tcw.tiles.TileEntityAssembler) te).getProgress() > 0;
        if (te instanceof tcw.tiles.TileEntityCharger) return ((tcw.tiles.TileEntityCharger) te).getProgress() > 0;
        return false;
    }

    protected int mapSideByFace(int side, int face) {
        // default orientation assumes front=north(2)
        if (face == 3) return side;
        if (face == 2) {
            if (side == 2) return 3;
            if (side == 3) return 2;
            return side;
        }
        if (face == 4) {
            if (side == 4) return 3;
            if (side == 5) return 2;
            if (side == 2) return 4;
            if (side == 3) return 5;
            return side;
        }
        if (face == 5) {
            if (side == 5) return 3;
            if (side == 4) return 2;
            if (side == 2) return 5;
            if (side == 3) return 4;
            return side;
        }
        return side;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, net.minecraft.entity.EntityLiving entity, ItemStack stack) {
        int face = 3;
        int dir = net.minecraft.util.MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (dir == 0) face = 2;
        else if (dir == 1) face = 5;
        else if (dir == 2) face = 3;
        else if (dir == 3) face = 4;
        world.setBlockMetadataWithNotify(x, y, z, face, 2);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    protected ItemStack createStackedBlock(int meta) {
        return new ItemStack(this);
    }

    @Override
    public int idDropped(int meta, Random random, int fortune) {
        return BlockManager.machineCasing != null ? BlockManager.machineCasing.blockID : this.blockID;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new tcw.tiles.TileEntityMachine();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && guiId >= 0) {
            player.openGui(tcw.MainLoader.instance, guiId, world, x, y, z);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int blockId, int meta) {
        dropInventory(world, x, y, z);
        tcw.tiles.TileEntityMachine.resetNearbyMachineEnergy(world, x, y, z, 6);
        super.breakBlock(world, x, y, z, blockId, meta);
    }

    public static void dropInventory(World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (!(tile instanceof IInventory)) {
            return;
        }

        IInventory inv = (IInventory) tile;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) {
                continue;
            }

            float dx = world.rand.nextFloat() * 0.8F + 0.1F;
            float dy = world.rand.nextFloat() * 0.8F + 0.1F;
            float dz = world.rand.nextFloat() * 0.8F + 0.1F;
            EntityItem item = new EntityItem(world, x + dx, y + dy, z + dz, stack.copy());
            float impulse = 0.05F;
            item.motionX = (float) world.rand.nextGaussian() * impulse;
            item.motionY = (float) world.rand.nextGaussian() * impulse + 0.2F;
            item.motionZ = (float) world.rand.nextGaussian() * impulse;
            world.spawnEntityInWorld(item);
            inv.setInventorySlotContents(i, null);
        }
    }
}
