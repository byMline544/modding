package tcw.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tcw.tiles.TileEntityWiremill;

public class GuiWiremill extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/wiremill.png";
    private final TileEntityWiremill machine;

    public GuiWiremill(InventoryPlayer playerInventory, TileEntityWiremill machine) {
        super(new ContainerWiremill(playerInventory, machine));
        this.machine = machine;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Проволочный стан", 8, 6, 4210752);
        boolean linked = machine.isNetworkLinked();
        fontRenderer.drawString("Подключение к сети: " + (linked ? "✓" : "✗"), 8, 16, linked ? 0x2f8f2f : 0xAA2222);
        if (machine.getStorage().getEnergyStored() <= 0) fontRenderer.drawString("Не хватает энергии!", 8, 26, 0xCC2222);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        super.mc.renderEngine.bindTexture(super.mc.renderEngine.getTexture(TEXTURE_PATH));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int progress = machine.getScaledProgress(24);
        drawTexturedModalRect(x + 79, y + 34, 176, 0, progress + 1, 16);
    }
}
