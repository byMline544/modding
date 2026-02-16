package tcw.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import tcw.book.BookPageRepository;

public class GuiTechBook extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/book_tech.png";
    private int page = 0;
    private GuiButton prevButton;
    private GuiButton nextButton;

    public GuiTechBook(InventoryPlayer inventory) {
        super(new tcw.book.ContainerTechBook(inventory));
        this.xSize = 220;
        this.ySize = 180;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        prevButton = new GuiButton(0, x + 10, y + 154, 45, 16, "< Назад");
        nextButton = new GuiButton(1, x + xSize - 55, y + 154, 45, 16, "Вперёд >");
        buttonList.add(prevButton);
        buttonList.add(nextButton);
        updateButtons();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0 && page > 0) {
            page--;
        } else if (button.id == 1 && page < BookPageRepository.PAGES.length - 1) {
            page++;
        }
        updateButtons();
    }

    private void updateButtons() {
        if (prevButton != null) {
            prevButton.enabled = page > 0;
            nextButton.enabled = page < BookPageRepository.PAGES.length - 1;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Справочник TechnoCloud", 10, 8, 0x404040);
        fontRenderer.drawString("Стр. " + (page + 1) + " / " + BookPageRepository.PAGES.length, xSize - 60, 8, 0x5b5b5b);
        fontRenderer.drawSplitString(BookPageRepository.PAGES[page], 12, 24, xSize - 24, 0x2b2b2b);
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
