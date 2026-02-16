package tcw.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import tcw.items.ElectricItemHelper;

public class PlayerEquipmentEventHandler {

    @ForgeSubscribe
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entityLiving;
        if (player.worldObj.isRemote) {
            return;
        }

        ItemStack legs = player.inventory.armorInventory[1];
        applyLeggingsSprintBoost(player, legs);
    }

    @ForgeSubscribe
    public void onLivingFall(LivingFallEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entityLiving;
        ItemStack boots = player.inventory.armorInventory[0];
        if (boots == null || boots.getItem() == null) {
            return;
        }

        String name = boots.getItem().getUnlocalizedName();
        if (name == null || (!name.contains("nano_boots") && !name.contains("quantum_boots"))) {
            return;
        }

        int energy = ElectricItemHelper.getEnergy(boots);
        if (energy <= 0) {
            return;
        }

        int cost = Math.max(80, (int) (event.distance * 70.0F));
        int spend = Math.min(cost, energy);
        ElectricItemHelper.addEnergy(boots, -spend);
        if (spend >= cost) {
            event.setCanceled(true);
        }
    }

    private void applyLeggingsSprintBoost(EntityPlayer player, ItemStack legs) {
        if (legs == null || legs.getItem() == null || !hasFastRunModule(legs)) {
            return;
        }

        String name = legs.getItem().getUnlocalizedName();
        if (name == null) {
            return;
        }

        float boost = 0.0F;
        int sprintCost = 0;
        int jumpCost = 0;
        if (name.contains("quantum_leggings")) {
            boost = 3.0F;
            sprintCost = 26;
            jumpCost = 200;
        } else if (name.contains("nano_leggings")) {
            boost = 1.8F;
            sprintCost = 16;
            jumpCost = 140;
        }

        if (boost <= 0.0F || ElectricItemHelper.getEnergy(legs) <= 0) {
            return;
        }

        if (player.onGround && player.isSprinting()) {
            player.motionX *= (1.0D + boost);
            player.motionZ *= (1.0D + boost);
            ElectricItemHelper.addEnergy(legs, -sprintCost);
        }

        if (!player.onGround && player.motionY > 0.15D) {
            ElectricItemHelper.addEnergy(legs, -jumpCost);
        }
    }

    private boolean hasFastRunModule(ItemStack stack) {
        return stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean("TCW_FastRunModule");
    }
}
