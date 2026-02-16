package tcw.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;
import tcw.TCWCreativeTab;

public class ItemBronzeSword extends ItemSword {

    private final String textureKey;

    public ItemBronzeSword(int id, EnumToolMaterial material, String textureKey) {
        super(id, material);
        this.textureKey = textureKey;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:" + textureKey);
    }
}
