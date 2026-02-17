package tcw.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tcw.tiles.TileEntityGenerator;

public class GuiGenerator extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/generator.png";
    private final TileEntityGenerator machine;

    public GuiGenerator(InventoryPlayer playerInventory, TileEntityGenerator machine) {
        super(new ContainerGenerator(playerInventory, machine));
        this.machine = machine;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Генератор", 8, 6, 4210752);
        fontRenderer.drawString("Энергия: " + machine.getStorage().getEnergyStored() + " TC/t", 8, 16, 0x2f6f2f);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        super.mc.renderEngine.bindTexture(super.mc.renderEngine.getTexture(TEXTURE_PATH));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int burn = machine.getScaledBurnTime(14);
        drawTexturedModalRect(x + 81, y + 36 + 14 - burn, 176, 14 - burn, 14, burn);
    }
}
