package techno.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import techno.TechnoCreativeTabs;
import techno.TechnoMod;

public abstract class BaseBlockMachine extends BlockContainer {
    protected final String textureKey;
    protected final int guiId;
    protected Icon icon;

    public BaseBlockMachine(int id, String textureKey, int guiId) {
        super(id, Material.iron);
        this.textureKey = textureKey;
        this.guiId = guiId;
        setUnlocalizedName(textureKey);
        setHardness(3.0F);
        setCreativeTab(TechnoCreativeTabs.TAB_BLOCKS);
    }

    public void registerIcons(IconRegister register) {
        icon = register.registerIcon("technocloud:" + textureKey);
        blockIcon = icon;
    }

    public Icon getIcon(int side, int meta) {
        return icon;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
        if (!world.isRemote) {
            player.openGui(TechnoMod.instance, guiId, world, x, y, z);
        }
        return true;
    }
}
