package techno.blocks.gui;

import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import techno.blocks.container.ContainerGenerator;
import techno.blocks.tile.TileGenerator;

public class GuiGenerator extends BaseGuiEnergySource {
    private final TileGenerator tile;
    public GuiGenerator(InventoryPlayer inv, TileGenerator tile) {
        super(new ContainerGenerator(inv, tile));
        this.tile = tile;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Генератор", 8, 6, 0x404040);
        fontRenderer.drawString("Энергия: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored(), 8, 18, 0x2f6f2f);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/mods/technocloud/textures/gui/generator.png"));
        int gx = (width - xSize) / 2;
        int gy = (height - ySize) / 2;
        drawTexturedModalRect(gx, gy, 0, 0, xSize, ySize);
    }
}
