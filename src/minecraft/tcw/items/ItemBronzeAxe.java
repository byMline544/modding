package tcw.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import tcw.TCWCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemBronzeAxe extends ItemAxe {

    private final String textureKey;

    public ItemBronzeAxe(int id, EnumToolMaterial material, String textureKey) {
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
