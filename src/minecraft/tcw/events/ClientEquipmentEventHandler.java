package tcw.events;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import tcw.items.ElectricItemHelper;
import tcw.items.ItemJetpack;

public class ClientEquipmentEventHandler {

    private boolean nPressedLastTick;
    private boolean helmetVisionEnabled;

    @ForgeSubscribe
    public void onLivingUpdate(LivingUpdateEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.thePlayer == null || mc.currentScreen != null) {
            return;
        }

        if (!(event.entityLiving instanceof EntityPlayer) || event.entityLiving != mc.thePlayer) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entityLiving;
        ItemStack helmet = player.inventory.armorInventory[3];
        ItemStack chest = player.inventory.armorInventory[2];

        handleHelmetVisionToggle(mc, helmet);
        handleJetpack(player, chest);
    }

    private void handleHelmetVisionToggle(Minecraft mc, ItemStack helmet) {
        boolean nPressed = Keyboard.isKeyDown(Keyboard.KEY_N);
        if (nPressed && !nPressedLastTick) {
            helmetVisionEnabled = !helmetVisionEnabled;
        }
        nPressedLastTick = nPressed;

        if (helmet == null || helmet.getItem() == null || helmet.getItem().getUnlocalizedName() == null
                || !helmet.getItem().getUnlocalizedName().contains("quantum_helmet")
                || !hasNightVisionModule(helmet)) {
            helmetVisionEnabled = false;
            if (mc.gameSettings.gammaSetting > 1.01F) {
                mc.gameSettings.gammaSetting = 1.0F;
            }
            return;
        }

        if (!helmetVisionEnabled || ElectricItemHelper.getEnergy(helmet) <= 0) {
            if (mc.gameSettings.gammaSetting > 1.01F) {
                mc.gameSettings.gammaSetting = 1.0F;
            }
            return;
        }

        ElectricItemHelper.addEnergy(helmet, -4);
        mc.gameSettings.gammaSetting = 8.0F;
    }


    private boolean hasNightVisionModule(ItemStack helmet) {
        return helmet != null && helmet.hasTagCompound() && helmet.getTagCompound().getBoolean("TCW_NightVisionModule");
    }

    private void handleJetpack(EntityPlayer player, ItemStack chest) {
        if (chest == null || !(chest.getItem() instanceof ItemJetpack)) {
            return;
        }

        ItemJetpack jetpack = (ItemJetpack) chest.getItem();
        int energy = ElectricItemHelper.getEnergy(chest);
        if (energy <= 0) {
            return;
        }

        boolean jump = Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.keyCode);
        if (jump) {
            player.motionY = Math.min(0.6D, player.motionY + jetpack.getThrust());
            player.fallDistance = 0.0F;
            ElectricItemHelper.addEnergy(chest, -jetpack.getEnergyPerTick());
        }
    }
}
