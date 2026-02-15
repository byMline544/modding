package tcw.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiTechBook extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/book_tech.png";

    public GuiTechBook(InventoryPlayer inventory) {
        super(new tcw.book.ContainerTechBook(inventory));
        this.xSize = 220;
        this.ySize = 180;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Справочник TechnoCloud", 10, 8, 0x404040);
        fontRenderer.drawString("Раздел: Энергия и машины", 10, 22, 0x5b5b5b);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        super.mc.renderEngine.bindTexture(super.mc.renderEngine.getTexture(TEXTURE_PATH));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
