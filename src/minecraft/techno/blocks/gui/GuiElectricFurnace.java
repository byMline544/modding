package techno.blocks.gui;

import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import techno.blocks.container.ContainerElectricFurnace;
import techno.blocks.tile.TileElectricFurnace;

public class GuiElectricFurnace extends BaseGuiMachine {
    private final TileElectricFurnace tile;
    public GuiElectricFurnace(InventoryPlayer inv, TileElectricFurnace tile) {
        super(new ContainerElectricFurnace(inv, tile));
        this.tile = tile;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Электропечь", 8, 6, 0x404040);
        fontRenderer.drawString("Энергия: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored(), 8, 18, 0x2f6f2f);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/mods/technocloud/textures/gui/electric_furnace.png"));
        int gx = (width - xSize) / 2;
        int gy = (height - ySize) / 2;
        drawTexturedModalRect(gx, gy, 0, 0, xSize, ySize);

        int bar = tile.getProgress() * 24 / 120;
        drawTexturedModalRect(gx + 79, gy + 34, 176, 0, bar + 1, 16);
    }
}
