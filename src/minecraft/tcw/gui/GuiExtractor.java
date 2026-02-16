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
    private GuiButton inputSideButton;
    private GuiButton outputSideButton;
    private GuiButton filterButton;

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
        modeButton = new GuiButton(0, x + 8, y + 18, 58, 16, getModeText());
        autoInputButton = new GuiButton(1, x + 68, y + 18, 50, 16, getAutoInputText());
        autoOutputButton = new GuiButton(2, x + 120, y + 18, 48, 16, getAutoOutputText());
        inputSideButton = new GuiButton(3, x + 8, y + 36, 80, 16, getInputSideText());
        outputSideButton = new GuiButton(4, x + 88, y + 36, 80, 16, getOutputSideText());
        filterButton = new GuiButton(5, x + 8, y + 54, 160, 16, getFilterText());
        buttonList.add(modeButton);
        buttonList.add(autoInputButton);
        buttonList.add(autoOutputButton);
        buttonList.add(inputSideButton);
        buttonList.add(outputSideButton);
        buttonList.add(filterButton);
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
        } else if (button.id == 3) {
            discriminator = PacketIds.CYCLE_EXTRACTOR_INPUT_SIDE;
        } else if (button.id == 4) {
            discriminator = PacketIds.CYCLE_EXTRACTOR_OUTPUT_SIDE;
        } else if (button.id == 5) {
            discriminator = PacketIds.CYCLE_EXTRACTOR_FILTER_MODE;
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
        return machine.isOverclockMode() ? "Режим: РАЗГОН" : "Режим: СТАНД";
    }

    private String getAutoInputText() {
        return machine.isAutoInput() ? "ВХ:АВТ" : "ВХ:ВЫКЛ";
    }

    private String getAutoOutputText() {
        return machine.isAutoOutput() ? "ВЫХ:АВТ" : "ВЫХ:ВЫКЛ";
    }

    private String getInputSideText() {
        return "ВХ< " + machine.getInputSideLabel();
    }

    private String getOutputSideText() {
        return "ВЫХ> " + machine.getOutputSideLabel();
    }

    private String getFilterText() {
        return "Фильтр: " + machine.getFilterModeLabel();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (modeButton != null) {
            modeButton.displayString = getModeText();
            autoInputButton.displayString = getAutoInputText();
            autoOutputButton.displayString = getAutoOutputText();
            inputSideButton.displayString = getInputSideText();
            outputSideButton.displayString = getOutputSideText();
            filterButton.displayString = getFilterText();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Экстрактор", 8, 6, 4210752);
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
