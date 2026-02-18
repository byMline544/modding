package tcw.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tcw.TCWCreativeTab;

public class ItemVajra extends ItemPickaxe implements IElectricItemTCW {

    private final int maxEnergy;

    public ItemVajra(int id, EnumToolMaterial material, int maxEnergy) {
        super(id, material);
        this.maxEnergy = maxEnergy;
        setUnlocalizedName("vajra");
        setCreativeTab(TCWCreativeTab.TAB_EQUIPMENT);
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:vajra");
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

    @Override
    public float getStrVsBlock(ItemStack stack, Block block) {
        if (ElectricItemHelper.getEnergy(stack) <= 0) {
            return 1.0F;
        }
        return 24.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int blockId, int x, int y, int z, EntityLiving entity) {
        Block block = Block.blocksList[blockId];
        if (block != null) {
            float hardness = block.getBlockHardness(world, x, y, z);
            if (hardness > 0.0F) {
                int cost = Math.max(140, Math.round(220.0F + hardness * 230.0F));
                ElectricItemHelper.addEnergy(stack, -cost);
            }
        }
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLiving target, EntityLiving attacker) {
        ElectricItemHelper.addEnergy(stack, -420);
        return true;
    }

    @Override
    public int getDamageVsEntity(Entity entity) {
        return 16;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        int energy = ElectricItemHelper.getEnergy(stack);
        list.add("Разрушитель класса Ваджра");
        list.add("Быстро копает и наносит высокий урон");
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
}
