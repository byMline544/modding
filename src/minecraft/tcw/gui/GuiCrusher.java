package tcw.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tcw.tiles.TileEntityCrusher;

public class GuiCrusher extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/crusher.png";
    private final TileEntityCrusher crusher;

    public GuiCrusher(InventoryPlayer playerInventory, TileEntityCrusher crusher) {
        super(new ContainerCrusher(playerInventory, crusher));
        this.crusher = crusher;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Дробитель", 8, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        fontRenderer.drawString("Энергия: " + crusher.getStorage().getEnergyStored(), 8, 16, 0x2f6f2f);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        super.mc.renderEngine.bindTexture(super.mc.renderEngine.getTexture(TEXTURE_PATH));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int progress = crusher.getScaledProgress(24);
        drawTexturedModalRect(x + 79, y + 34, 176, 0, progress + 1, 16);

        int energy = crusher.getScaledEnergy(52);
        drawTexturedModalRect(x + 8, y + 70 - energy, 176, 16, 8, energy);
    }
}
