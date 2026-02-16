package tcw.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import tcw.items.ElectricItemHelper;
import tcw.items.IElectricItemTCW;

public class PlayerCombatEventHandler {

    private static final float NANO_ABSORB = 0.15F;
    private static final float QUANTUM_ABSORB = 0.24F;
    private static final int ENERGY_PER_DAMAGE = 1200;

    @ForgeSubscribe
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entityLiving;
        if (player.worldObj.isRemote || event.ammount <= 0.0F) {
            return;
        }

        float absorbFactor = 0.0F;
        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            ItemStack armor = player.inventory.armorInventory[i];
            if (armor == null || !(armor.getItem() instanceof IElectricItemTCW)) {
                continue;
            }

            int energy = ElectricItemHelper.getEnergy(armor);
            if (energy <= 0) {
                continue;
            }

            String name = armor.getItem().getUnlocalizedName();
            if (name != null && name.contains("quantum")) {
                absorbFactor += QUANTUM_ABSORB;
            } else {
                absorbFactor += NANO_ABSORB;
            }
        }

        if (absorbFactor <= 0.0F) {
            return;
        }

        absorbFactor = Math.min(absorbFactor, 0.92F);
        float requestedBlock = event.ammount * absorbFactor;
        int requiredEnergy = Math.max(1, Math.round(requestedBlock * ENERGY_PER_DAMAGE));
        int availableEnergy = 0;
        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            ItemStack armor = player.inventory.armorInventory[i];
            if (armor != null && armor.getItem() instanceof IElectricItemTCW) {
                availableEnergy += ElectricItemHelper.getEnergy(armor);
            }
        }

        if (availableEnergy <= 0) {
            return;
        }

        float realBlock = requestedBlock;
        if (requiredEnergy > availableEnergy) {
            realBlock = requestedBlock * ((float) availableEnergy / (float) requiredEnergy);
            requiredEnergy = availableEnergy;
        }

        int toDrain = requiredEnergy;
        for (int i = 0; i < player.inventory.armorInventory.length && toDrain > 0; i++) {
            ItemStack armor = player.inventory.armorInventory[i];
            if (armor == null || !(armor.getItem() instanceof IElectricItemTCW)) {
                continue;
            }

            int energy = ElectricItemHelper.getEnergy(armor);
            if (energy <= 0) {
                continue;
            }

            int drain = Math.min(energy, toDrain);
            ElectricItemHelper.addEnergy(armor, -drain);
            toDrain -= drain;
        }

        event.ammount = Math.max(0.0F, event.ammount - realBlock);
    }
}
