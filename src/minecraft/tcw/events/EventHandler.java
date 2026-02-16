package tcw.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.event.ForgeSubscribe;
import tcw.items.ElectricItemHelper;
import tcw.items.IElectricItemTCW;

public class EventHandler {

    @ForgeSubscribe
    public void OverlayEvent(Post post) {
        if (post.type != ElementType.HOTBAR) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.thePlayer == null) {
            return;
        }

        EntityPlayer player = mc.thePlayer;
        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int x = sr.getScaledWidth() - 72;
        int y = sr.getScaledHeight() - 74;
        FontRenderer fr = mc.fontRenderer;

        for (int i = 0; i < 4; i++) {
            ItemStack armor = player.inventory.armorInventory[i];
            if (armor == null || !(armor.getItem() instanceof IElectricItemTCW)) {
                continue;
            }

            int energy = ElectricItemHelper.getEnergy(armor);
            mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
            fr.drawStringWithShadow("Заряд: " + energy, x - 52, y + 4 + i * 16, 0xFFE44D);
            mc.ingameGUI.drawTexturedModalRect(x + 14, y + i * 16, 0, 0, 16, 16);
        }
    }
}
