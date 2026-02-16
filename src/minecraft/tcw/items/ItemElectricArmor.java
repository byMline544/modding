package tcw.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import tcw.TCWCreativeTab;

public class ItemElectricArmor extends ItemArmor {

    private final String textureKey;
    private final String setName;

    public ItemElectricArmor(int id, EnumArmorMaterial material, int renderIndex, int armorType, String textureKey, String setName) {
        super(id, material, renderIndex, armorType);
        this.textureKey = textureKey;
        this.setName = setName;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:" + textureKey);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        list.add("Комплект: " + setName);
        list.add("Электро-броня");
        list.add("Зарядка: в Заряднике");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
        if (armorType == 2) {
            return "/mods/technocloud/textures/armor/" + setName + "_layer_2.png";
        }
        return "/mods/technocloud/textures/armor/" + setName + "_layer_1.png";
    }
}
