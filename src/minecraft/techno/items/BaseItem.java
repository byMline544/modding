package techno.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BaseItem extends Item {
    protected final String key;

    public BaseItem(int id, String key) {
        super(id);
        this.key = key;
        setUnlocalizedName(key);
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:" + key);
    }
}
