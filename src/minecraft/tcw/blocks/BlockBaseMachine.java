package tcw.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import tcw.items.ItemMachineModule;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tcw.managers.BlockManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBaseMachine extends BlockContainer {

    protected Icon frontIcon;
    protected Icon sideIcon;
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
        this.frontIcon = register.registerIcon("technocloud:" + textureKey + "_front");
        this.sideIcon = register.registerIcon("technocloud:" + textureKey + "_side");
        this.blockIcon = sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return side == 3 ? frontIcon : sideIcon;
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
        if (!world.isRemote) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            ItemStack held = player.getCurrentEquippedItem();
            if (held != null && held.getItem() instanceof ItemMachineModule && tile instanceof tcw.tiles.TileEntityMachine
                    && !(tile instanceof tcw.tiles.TileEntitySolarPanel)) {
                ItemMachineModule module = (ItemMachineModule) held.getItem();
                if (((tcw.tiles.TileEntityMachine) tile).installModule(module.getModuleType())) {
                    if (!player.capabilities.isCreativeMode) {
                        held.stackSize--;
                        if (held.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                        }
                    }
                    world.markBlockForUpdate(x, y, z);
                    return true;
                }
            }
            if (guiId >= 0) {
                player.openGui(tcw.MainLoader.instance, guiId, world, x, y, z);
            }
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
