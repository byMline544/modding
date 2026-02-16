package tcw.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import tcw.TCWCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemBronzeShovel extends ItemSpade {

    private final String textureKey;

    public ItemBronzeShovel(int id, EnumToolMaterial material, String textureKey) {
        super(id, material);
        this.textureKey = textureKey;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB_EQUIPMENT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:" + textureKey);
    }
}
