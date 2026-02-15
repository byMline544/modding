package tcw.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TCWItem extends Item {

    public TCWItem(int id, String textureKey) {
        super(id);
        setUnlocalizedName(textureKey);
        setTextureName("technocloud:" + textureKey);
        setCreativeTab(CreativeTabs.tabMaterials);
    }
}
