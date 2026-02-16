package tcw.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class ItemBronzeShovel extends ItemSpade {

    public ItemBronzeShovel(int id, EnumToolMaterial material, String textureKey) {
        super(id, material);
        setUnlocalizedName(textureKey);
        setTextureName("technocloud:" + textureKey);
    }
}
