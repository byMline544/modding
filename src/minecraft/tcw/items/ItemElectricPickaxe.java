package tcw.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
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
        this.maxEnergy = textureKey.contains("quantum") ? 3200000 : 1000000;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB_EQUIPMENT);
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

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        int max = getMaxEnergy(stack);
        int energy = ElectricItemHelper.getEnergy(stack);
        if (max <= 0) return 1.0D;
        return 1.0D - ((double) energy / (double) max);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int blockId, int x, int y, int z, net.minecraft.entity.EntityLivingBase entity) {
        Block b = Block.blocksList[blockId];
        if (b != null && b.getBlockHardness(world, x, y, z) > 0.0F) {
            ElectricItemHelper.addEnergy(stack, -250);
        }
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
