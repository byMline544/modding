package tcw.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import tcw.TCWCreativeTab;

public class BlockOreTCW extends Block {

    private final String textureKey;

    public BlockOreTCW(int id, String name) {
        super(id, Material.rock);
        this.textureKey = name;
        setUnlocalizedName(name);
        setHardness(3.0F);
        setResistance(5.0F);
        setCreativeTab(TCWCreativeTab.TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("technocloud:" + textureKey);
    }
}
