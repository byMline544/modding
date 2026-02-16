package tcw.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import tcw.TCWCreativeTab;

public class BlockMachineCasing extends Block {

    private final String textureKey;

    public BlockMachineCasing(int id, String textureKey) {
        super(id, Material.iron);
        this.textureKey = textureKey;
        setUnlocalizedName(textureKey);
        setHardness(2.8F);
        setResistance(7.0F);
        setCreativeTab(TCWCreativeTab.TAB_MACHINES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("technocloud:" + textureKey);
    }
}
