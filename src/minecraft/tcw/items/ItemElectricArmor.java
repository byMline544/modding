package tcw.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import tcw.TCWCreativeTab;

public class ItemElectricArmor extends ItemArmor implements IElectricItemTCW {

    private final String textureKey;
    private final String setName;
    private final int maxEnergy;

    public ItemElectricArmor(int id, EnumArmorMaterial material, int renderIndex, int armorType, String textureKey, String setName) {
        super(id, material, renderIndex, armorType);
        this.textureKey = textureKey;
        this.setName = setName;
        this.maxEnergy = "quantum".equals(setName) ? 1400000 : 220000;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB_EQUIPMENT);
        setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:" + textureKey);
    }

    @Override
    public int getMaxEnergy(ItemStack stack) {
        return maxEnergy;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    public double getDurabilityForDisplay(ItemStack stack) {
        int max = getMaxEnergy(stack);
        int energy = ElectricItemHelper.getEnergy(stack);
        if (max <= 0) {
            return 1.0D;
        }
        return 1.0D - ((double) energy / (double) max);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        int energy = ElectricItemHelper.getEnergy(stack);
        list.add("Комплект: " + setName);
        list.add("Электро-броня");
        list.add("Энергия: " + energy + " / " + getMaxEnergy(stack));
        if (armorType == 0 && stack.hasTagCompound() && stack.getTagCompound().getBoolean("TCW_NightVisionModule")) {
            list.add("МОДУЛЬ: установлен модуль ночного видения");
        }
        if (armorType == 2 && stack.hasTagCompound() && stack.getTagCompound().getBoolean("TCW_FastRunModule")) {
            list.add("МОДУЛЬ: установлен модуль быстрого бега");
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        ItemStack charged = new ItemStack(id, 1, 0);
        ElectricItemHelper.setEnergy(charged, getMaxEnergy(charged));
        list.add(charged);

        ItemStack empty = new ItemStack(id, 1, 0);
        ElectricItemHelper.setEnergy(empty, 0);
        list.add(empty);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
        if (armorType == 2) {
            return "/mods/technocloud/textures/armor/" + setName + "_layer_2.png";
        }
        return "/mods/technocloud/textures/armor/" + setName + "_layer_1.png";
    }
}
