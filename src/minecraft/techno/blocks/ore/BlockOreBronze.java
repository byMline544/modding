package techno.blocks.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockOreBronze extends Block {
    private Icon icon;
    public BlockOreBronze(int id) { super(id, Material.rock); setUnlocalizedName("ore_bronze"); }
    public void registerIcons(IconRegister register) { icon = register.registerIcon("technocloud:ore_bronze"); blockIcon = icon; }
    public Icon getIcon(int side, int meta) { return icon; }
}
