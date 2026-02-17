package tcw.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tcw.TCWCreativeTab;

public class ItemElectricPickaxe extends ItemPickaxe implements IElectricItemTCW {

    private final String textureKey;
    private final int maxEnergy;

    public ItemElectricPickaxe(int id, EnumToolMaterial material, String textureKey) {
        super(id, material);
        this.textureKey = textureKey;
        this.maxEnergy = textureKey.contains("quantum") ? 240000 : 75000;
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

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int blockId, int x, int y, int z, EntityLiving entity) {
        Block block = Block.blocksList[blockId];
        if (block != null) {
            float hardness = block.getBlockHardness(world, x, y, z);
            if (hardness > 0.0F) {
                int cost = Math.max(80, Math.round(120.0F + hardness * 180.0F));
                ElectricItemHelper.addEnergy(stack, -cost);
            }
        }
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLiving target, EntityLiving attacker) {
        ElectricItemHelper.addEnergy(stack, -220);
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        int energy = ElectricItemHelper.getEnergy(stack);
        list.add("Электро-инструмент");
        list.add("Зарядка: в Заряднике");
        list.add("Энергия: " + energy + " / " + getMaxEnergy(stack));
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
}
