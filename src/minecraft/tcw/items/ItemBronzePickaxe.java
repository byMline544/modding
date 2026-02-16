package tcw.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;

public class ItemBronzePickaxe extends ItemPickaxe {

    public ItemBronzePickaxe(int id, EnumToolMaterial material, String textureKey) {
        super(id, material);
        setUnlocalizedName(textureKey);
        setTextureName("technocloud:" + textureKey);
    }
}
