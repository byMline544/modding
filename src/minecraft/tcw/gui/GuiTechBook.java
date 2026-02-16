package tcw.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import tcw.book.BookPageRepository;

public class GuiTechBook extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/book_tech.png";
    private final RenderItem renderItem = new RenderItem();
    private int page = 0;
    private GuiButton prevButton;
    private GuiButton nextButton;

    public GuiTechBook(InventoryPlayer inventory) {
        super(new tcw.book.ContainerTechBook(inventory));
        this.xSize = 256;
        this.ySize = 210;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        prevButton = new GuiButton(0, x + 10, y + ySize - 20, 60, 16, "< Назад");
        nextButton = new GuiButton(1, x + xSize - 70, y + ySize - 20, 60, 16, "Вперёд >");
        buttonList.add(prevButton);
        buttonList.add(nextButton);
        updateButtons();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        BookPageRepository.Entry[] entries = BookPageRepository.getEntries();
        if (button.id == 0 && page > 0) {
            page--;
        } else if (button.id == 1 && page < entries.length - 1) {
            page++;
        }
        updateButtons();
    }

    private void updateButtons() {
        BookPageRepository.Entry[] entries = BookPageRepository.getEntries();
        if (prevButton != null) {
            prevButton.enabled = page > 0;
            nextButton.enabled = page < entries.length - 1;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        BookPageRepository.Entry[] entries = BookPageRepository.getEntries();
        BookPageRepository.Entry entry = entries[page];

        fontRenderer.drawString("Справочник TechnoCloud", 10, 8, 0x404040);
        fontRenderer.drawString(entry.title, 10, 22, 0x2b2b2b);
        fontRenderer.drawString("Стр. " + (page + 1) + " / " + entries.length, xSize - 70, 8, 0x5b5b5b);
        fontRenderer.drawSplitString(entry.text, 10, 36, 152, 0x333333);

        fontRenderer.drawString("Вход:", 170, 36, 0x3f3f3f);
        for (int i = 0; i < entry.inputs.length && i < 6; i++) {
            drawStack(entry.inputs[i], 170 + (i % 3) * 20, 48 + (i / 3) * 20);
        }

        fontRenderer.drawString("Результат:", 170, 92, 0x3f3f3f);
        drawStack(entry.output, 170, 104);

        fontRenderer.drawString("Подсказка:", 10, 162, 0x3f3f3f);
        fontRenderer.drawSplitString("Используй генератор + панели + кабели, затем автоматизируй цепочки через Экстрактор и Сборщик.", 10, 174, 236,
                0x4b4b4b);
    }

    private void drawStack(ItemStack stack, int x, int y) {
        if (stack == null) {
            return;
        }
        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, stack, x, y);
        renderItem.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, stack, x, y);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
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
