package tcw.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
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
        RenderItem itemRenderer = new RenderItem();

        int[] order = new int[] { 3, 2, 1, 0 }; // helmet, chest, legs, boots
        for (int i = 0; i < 4; i++) {
            ItemStack armor = player.inventory.armorInventory[order[i]];
            if (armor == null || !(armor.getItem() instanceof IElectricItemTCW)) {
                continue;
            }

            int energy = ElectricItemHelper.getEnergy(armor);
            fr.drawStringWithShadow(String.valueOf(energy), x - 22, y + 4 + i * 16, 0xFFE44D);
            RenderHelper.enableStandardItemLighting();
            itemRenderer.renderItemIntoGUI(fr, mc.renderEngine, armor, x + 14, y + i * 16);
            RenderHelper.disableStandardItemLighting();
        }
    }
}
