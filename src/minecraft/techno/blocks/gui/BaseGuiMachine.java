package techno.blocks.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class BaseGuiMachine extends GuiContainer {
    protected static final int WIDTH = 176;
    protected static final int HEIGHT = 166;

    protected BaseGuiMachine(Container c) {
        super(c);
        xSize = WIDTH;
        ySize = HEIGHT;
    }
}
