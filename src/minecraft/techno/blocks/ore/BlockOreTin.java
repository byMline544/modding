package techno.blocks.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import techno.TechnoCreativeTabs;

public class BlockOreTin extends Block {
    private Icon icon;
    public BlockOreTin(int id) { super(id, Material.rock); setUnlocalizedName("ore_tin"); setCreativeTab(TechnoCreativeTabs.TAB_BLOCKS); }
    public void registerIcons(IconRegister register) { icon = register.registerIcon("technocloud:ore_tin"); blockIcon = icon; }
    public Icon getIcon(int side, int meta) { return icon; }
}
