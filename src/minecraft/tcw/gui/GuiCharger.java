package tcw.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tcw.packets.PacketDispatcherTCW;
import tcw.packets.PacketIds;
import tcw.tiles.TileEntityCharger;

public class GuiCharger extends GuiContainer {

    private static final String TEXTURE_PATH = "/mods/technocloud/textures/gui/charger.png";
    private final TileEntityCharger machine;
    private GuiButton modeButton;

    public GuiCharger(InventoryPlayer playerInventory, TileEntityCharger machine) {
        super(new ContainerCharger(playerInventory, machine));
        this.machine = machine;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        modeButton = new GuiButton(0, x + 8, y + 58, 74, 20, getModeText());
        buttonList.add(modeButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            net.minecraft.network.packet.Packet250CustomPayload packet = PacketDispatcherTCW.makeTogglePacket(PacketIds.TOGGLE_CHARGER_MODE, machine.xCoord,
                    machine.yCoord, machine.zCoord);
            if (packet != null) {
                PacketDispatcher.sendPacketToServer(packet);
            }
        }
    }

    private String getModeText() {
        return machine.isFastMode() ? "Режим: БЫСТР" : "Режим: БЕЗОП";
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (modeButton != null) {
            modeButton.displayString = getModeText();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Зарядник", 8, 6, 4210752);
        fontRenderer.drawString("Энергия: " + machine.getStorage().getEnergyStored(), 8, 16, 0x2f6f2f);
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
