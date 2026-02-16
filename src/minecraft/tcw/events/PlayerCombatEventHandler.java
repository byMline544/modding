package tcw.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import tcw.items.ElectricItemHelper;
import tcw.items.IElectricItemTCW;

public class PlayerCombatEventHandler {

    private static final float NANO_ABSORB = 0.10F;
    private static final float QUANTUM_ABSORB = 0.16F;
    private static final int ENERGY_PER_DAMAGE = 2200;

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
        List<ItemStack> electricArmor = new ArrayList<ItemStack>();
        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            ItemStack armor = player.inventory.armorInventory[i];
            if (armor == null || !(armor.getItem() instanceof IElectricItemTCW)) {
                continue;
            }

            int energy = ElectricItemHelper.getEnergy(armor);
            if (energy <= 0) {
                continue;
            }
            electricArmor.add(armor);

            String name = armor.getItem().getUnlocalizedName();
            absorbFactor += (name != null && name.contains("quantum")) ? QUANTUM_ABSORB : NANO_ABSORB;
        }

        if (absorbFactor <= 0.0F || electricArmor.isEmpty()) {
            return;
        }

        absorbFactor = Math.min(absorbFactor, 0.75F);
        float blockedDamage = event.ammount * absorbFactor;
        int requiredEnergy = Math.max(1, Math.round(blockedDamage * ENERGY_PER_DAMAGE));

        int totalEnergy = 0;
        for (ItemStack armor : electricArmor) {
            totalEnergy += ElectricItemHelper.getEnergy(armor);
        }
        if (totalEnergy <= 0) {
            return;
        }

        if (requiredEnergy > totalEnergy) {
            blockedDamage = blockedDamage * ((float) totalEnergy / (float) requiredEnergy);
            requiredEnergy = totalEnergy;
        }

        int pieces = electricArmor.size();
        int baseDrain = requiredEnergy / pieces;
        int remainder = requiredEnergy % pieces;
        for (int i = 0; i < pieces; i++) {
            ItemStack armor = electricArmor.get(i);
            int targetDrain = baseDrain + (i < remainder ? 1 : 0);
            int energy = ElectricItemHelper.getEnergy(armor);
            ElectricItemHelper.addEnergy(armor, -Math.min(energy, targetDrain));
        }

        event.ammount = Math.max(0, (int) (event.ammount - blockedDamage));
    }
}
