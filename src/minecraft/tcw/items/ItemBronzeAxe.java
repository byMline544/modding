package tcw.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

public class ItemBronzeAxe extends ItemAxe {

    public ItemBronzeAxe(int id, EnumToolMaterial material, String textureKey) {
        super(id, material);
        setUnlocalizedName(textureKey);
        setTextureName("technocloud:" + textureKey);
    }
}
