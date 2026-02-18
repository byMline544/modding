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

public class ItemJetpack extends ItemArmor implements IElectricItemTCW {

    private final String textureKey;
    private final int maxEnergy;
    private final double thrust;
    private final int energyPerTick;

    public ItemJetpack(int id, String textureKey, int maxEnergy, double thrust, int energyPerTick) {
        super(id, EnumArmorMaterial.IRON, 0, 1);
        this.textureKey = textureKey;
        this.maxEnergy = maxEnergy;
        this.thrust = thrust;
        this.energyPerTick = energyPerTick;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB_EQUIPMENT);
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    public double getThrust() {
        return thrust;
    }

    public int getEnergyPerTick() {
        return energyPerTick;
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
        int energy = ElectricItemHelper.getEnergy(stack);
        return 1.0D - ((double) energy / (double) maxEnergy);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        int energy = ElectricItemHelper.getEnergy(stack);
        list.add("Электро-джетпак");
        list.add("Тяга: " + thrust);
        list.add("Энергия: " + energy + " / " + maxEnergy);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        ItemStack charged = new ItemStack(id, 1, 0);
        ElectricItemHelper.setEnergy(charged, maxEnergy);
        list.add(charged);

        ItemStack empty = new ItemStack(id, 1, 0);
        ElectricItemHelper.setEnergy(empty, 0);
        list.add(empty);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
        return "/mods/technocloud/textures/armor/jetpack_layer_1.png";
    }
}
