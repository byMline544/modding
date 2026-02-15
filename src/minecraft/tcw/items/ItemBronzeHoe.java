package tcw.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;

public class ItemBronzeHoe extends ItemHoe {

    public ItemBronzeHoe(int id, EnumToolMaterial material, String textureKey) {
        super(id, material);
        setUnlocalizedName(textureKey);
        setTextureName("technocloud:" + textureKey);
    }
}
