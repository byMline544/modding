package tcw.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockOreTCW extends Block {

    public BlockOreTCW(int id, String name) {
        super(id, Material.rock);
        setBlockName(name);
        setTextureName("technocloud:" + name);
        setHardness(3.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(CreativeTabs.tabBlock);
    }
}
