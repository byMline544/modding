package tcw.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBaseMachine extends Block {

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
        setBlockName(textureKey);
        setHardness(3.5F);
        setResistance(8.0F);
        setCreativeTab(CreativeTabs.tabDecorations);
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
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, int metadata) {
        return new tcw.tiles.TileEntityMachine();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && guiId >= 0) {
            player.openGui(tcw.MainLoader.instance, guiId, world, x, y, z);
        }
        return true;
    }
}
