package tcw.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import tcw.items.ElectricItemHelper;
import tcw.items.ItemJetpack;

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

        ItemStack helmet = player.inventory.armorInventory[3];
        ItemStack chest = player.inventory.armorInventory[2];
        ItemStack legs = player.inventory.armorInventory[1];

        applyQuantumNightVision(player, helmet);
        applyLeggingsBoost(player, legs);
        applyJetpackFlight(player, chest, legs);
    }

    @ForgeSubscribe
    public void onLivingFall(LivingFallEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entityLiving;
        ItemStack legs = player.inventory.armorInventory[1];
        if (legs == null || legs.getItem() == null) {
            return;
        }

        String name = legs.getItem().getUnlocalizedName();
        if (name == null) {
            return;
        }

        if (!name.contains("nano_leggings") && !name.contains("quantum_leggings")) {
            return;
        }

        int energy = ElectricItemHelper.getEnergy(legs);
        if (energy <= 0) {
            return;
        }

        int cost = Math.max(80, (int) (event.distance * 70.0F));
        int spend = Math.min(cost, energy);
        ElectricItemHelper.addEnergy(legs, -spend);
        if (spend >= cost) {
            event.setCanceled(true);
        }
    }

    private void applyQuantumNightVision(EntityPlayer player, ItemStack helmet) {
        if (helmet == null || helmet.getItem() == null) {
            return;
        }

        String name = helmet.getItem().getUnlocalizedName();
        if (name == null || !name.contains("quantum_helmet")) {
            return;
        }

        if (player.ticksExisted % 20 != 0) {
            return;
        }

        int energy = ElectricItemHelper.getEnergy(helmet);
        if (energy < 120) {
            return;
        }

        ElectricItemHelper.addEnergy(helmet, -120);
        player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 240, 0, true));
    }

    private void applyLeggingsBoost(EntityPlayer player, ItemStack legs) {
        if (legs == null || legs.getItem() == null) {
            return;
        }

        String name = legs.getItem().getUnlocalizedName();
        if (name == null) {
            return;
        }

        int amp = -1;
        int jumpCost = 0;
        if (name.contains("quantum_leggings")) {
            amp = 1;
            jumpCost = 160;
        } else if (name.contains("nano_leggings")) {
            amp = 0;
            jumpCost = 100;
        }

        if (amp < 0) {
            return;
        }

        if (ElectricItemHelper.getEnergy(legs) > 0) {
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30, amp, true));
            if (!player.onGround && player.motionY > 0.15D) {
                ElectricItemHelper.addEnergy(legs, -jumpCost);
            }
        }
    }

    private void applyJetpackFlight(EntityPlayer player, ItemStack chest, ItemStack legs) {
        if (chest == null || !(chest.getItem() instanceof ItemJetpack)) {
            if (!player.capabilities.isCreativeMode) {
                player.capabilities.allowFlying = false;
                player.capabilities.isFlying = false;
            }
            return;
        }

        ItemJetpack jetpack = (ItemJetpack) chest.getItem();
        int energy = ElectricItemHelper.getEnergy(chest);
        if (energy <= 0) {
            if (!player.capabilities.isCreativeMode) {
                player.capabilities.isFlying = false;
                player.capabilities.allowFlying = false;
            }
            return;
        }

        player.capabilities.allowFlying = true;
        if (player.capabilities.isFlying) {
            ElectricItemHelper.addEnergy(chest, -jetpack.getEnergyPerTick());
        }

        if (player.capabilities.isFlying && legs != null && legs.getItem() != null) {
            String lName = legs.getItem().getUnlocalizedName();
            if ((lName != null && (lName.contains("nano_leggings") || lName.contains("quantum_leggings"))) && player.fallDistance > 0.0F) {
                player.fallDistance = 0.0F;
            }
        }
    }
}
