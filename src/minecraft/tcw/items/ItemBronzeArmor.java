package tcw.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import tcw.TCWCreativeTab;

public class ItemBronzeArmor extends ItemArmor {

    private final String textureKey;

    public ItemBronzeArmor(int id, EnumArmorMaterial material, int renderIndex, int armorType, String textureKey) {
        super(id, material, renderIndex, armorType);
        this.textureKey = textureKey;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:" + textureKey);
    }

    @Override
    public String getArmorTextureFile(net.minecraft.item.ItemStack stack) {
        if (armorType == 2) {
            return "/mods/technocloud/textures/armor/bronze_layer_2.png";
        }
        return "/mods/technocloud/textures/armor/bronze_layer_1.png";
    }
}
