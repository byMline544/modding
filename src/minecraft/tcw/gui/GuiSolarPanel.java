package tcw.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tcw.tiles.TileEntitySolarPanel;

public class GuiSolarPanel extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/generator.png";
    private final TileEntitySolarPanel panel;

    public GuiSolarPanel(InventoryPlayer playerInventory, TileEntitySolarPanel panel) {
        super(new ContainerSolarPanel(playerInventory, panel));
        this.panel = panel;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Солнечная панель", 8, 6, 4210752);
        fontRenderer.drawString("Генерация: " + panel.getGenerationRate() + " EUC/t", 8, 18, 0x2f6f2f);
        fontRenderer.drawString("Энергия: " + panel.getStorage().getEnergyStored() + " / " + panel.getStorage().getMaxEnergyStored(), 8, 28, 0x2f6f2f);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        super.mc.renderEngine.bindTexture(super.mc.renderEngine.getTexture(TEXTURE_PATH));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int energy = panel.getScaledEnergy(52);
        drawTexturedModalRect(x + 8, y + 70 - energy, 176, 16, 8, energy);
    }
}
