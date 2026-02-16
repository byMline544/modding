package tcw.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tcw.tiles.TileEntityModificationTable;

public class GuiModificationTable extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/modification_table.png";
    private final TileEntityModificationTable table;

    public GuiModificationTable(InventoryPlayer playerInventory, TileEntityModificationTable table) {
        super(new ContainerModificationTable(playerInventory, table));
        this.table = table;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Стол модификации", 8, 6, 4210752);
        fontRenderer.drawString("Прогресс", 74, 22, 0x2f6f2f);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        super.mc.renderEngine.bindTexture(super.mc.renderEngine.getTexture(TEXTURE_PATH));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int progress = table.getScaledProgress(24);
        drawTexturedModalRect(x + 76, y + 34, 176, 0, progress + 1, 16);
    }
}
