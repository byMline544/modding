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

    private static final int ENERGY_PER_DAMAGE = 2800;

    @ForgeSubscribe
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entityLiving;
        if (player.worldObj.isRemote || event.ammount <= 0.0F) {
            return;
        }

        List<ItemStack> electricArmor = new ArrayList<ItemStack>();
        int totalEnergy = 0;
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
            totalEnergy += energy;
        }

        if (electricArmor.isEmpty() || totalEnergy <= 0) {
            return;
        }

        int requiredEnergy = Math.max(1, Math.round(event.ammount * ENERGY_PER_DAMAGE));
        int spent = Math.min(requiredEnergy, totalEnergy);

        int pieces = electricArmor.size();
        int baseDrain = spent / pieces;
        int remainder = spent % pieces;
        for (int i = 0; i < pieces; i++) {
            ItemStack armor = electricArmor.get(i);
            int targetDrain = baseDrain + (i < remainder ? 1 : 0);
            int energy = ElectricItemHelper.getEnergy(armor);
            ElectricItemHelper.addEnergy(armor, -Math.min(energy, targetDrain));
        }

        if (spent >= requiredEnergy) {
            event.ammount = (int) 0.0F;
            return;
        }

        float blockedPart = (float) spent / (float) requiredEnergy;
        event.ammount = (int) Math.max(0.0F, event.ammount * (1.0F - blockedPart));
    }
}
