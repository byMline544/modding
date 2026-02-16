package tcw.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tcw.packets.PacketDispatcherTCW;
import tcw.packets.PacketIds;
import tcw.tiles.TileEntityExtractor;

public class GuiExtractor extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/extractor.png";
    private final TileEntityExtractor machine;
    private GuiButton modeButton;
    private GuiButton autoInputButton;
    private GuiButton autoOutputButton;

    public GuiExtractor(InventoryPlayer playerInventory, TileEntityExtractor machine) {
        super(new ContainerExtractor(playerInventory, machine));
        this.machine = machine;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        modeButton = new GuiButton(0, x + 8, y + 6, 74, 20, getModeText());
        autoInputButton = new GuiButton(1, x + 84, y + 6, 42, 20, getAutoInputText());
        autoOutputButton = new GuiButton(2, x + 126, y + 6, 42, 20, getAutoOutputText());
        buttonList.add(modeButton);
        buttonList.add(autoInputButton);
        buttonList.add(autoOutputButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int discriminator = -1;
        if (button.id == 0) {
            discriminator = PacketIds.TOGGLE_EXTRACTOR_MODE;
        } else if (button.id == 1) {
            discriminator = PacketIds.TOGGLE_EXTRACTOR_AUTO_INPUT;
        } else if (button.id == 2) {
            discriminator = PacketIds.TOGGLE_EXTRACTOR_AUTO_OUTPUT;
        }
        if (discriminator != -1) {
            net.minecraft.network.packet.Packet250CustomPayload packet = PacketDispatcherTCW.makeTogglePacket(discriminator,
                    machine.xCoord, machine.yCoord, machine.zCoord);
            if (packet != null) {
                PacketDispatcher.sendPacketToServer(packet);
            }
        }
    }

    private String getModeText() {
        return machine.isOverclockMode() ? "Режим: OVC" : "Режим: STD";
    }

    private String getAutoInputText() {
        return machine.isAutoInput() ? "IN:A" : "IN:-";
    }

    private String getAutoOutputText() {
        return machine.isAutoOutput() ? "OUT:A" : "OUT:-";
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (modeButton != null) {
            modeButton.displayString = getModeText();
            autoInputButton.displayString = getAutoInputText();
            autoOutputButton.displayString = getAutoOutputText();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Экстрактор", 8, 30, 4210752);
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
