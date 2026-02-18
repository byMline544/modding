package techno.managers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import techno.blocks.container.ContainerElectricFurnace;
import techno.blocks.container.ContainerGenerator;
import techno.blocks.container.ContainerSolarPanel;
import techno.blocks.gui.GuiElectricFurnace;
import techno.blocks.gui.GuiGenerator;
import techno.blocks.gui.GuiSolarPanel;
import techno.blocks.tile.TileElectricFurnace;
import techno.blocks.tile.TileGenerator;
import techno.blocks.tile.TileSolarPanel;

public class GuiContainerManager implements IGuiHandler {
    public static final int GUI_GENERATOR = 1;
    public static final int GUI_SOLAR = 2;
    public static final int GUI_FURNACE = 3;

    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == GUI_GENERATOR) return new ContainerGenerator(player.inventory, (TileGenerator) world.getBlockTileEntity(x, y, z));
        if (id == GUI_SOLAR) return new ContainerSolarPanel(player.inventory, (TileSolarPanel) world.getBlockTileEntity(x, y, z));
        if (id == GUI_FURNACE) return new ContainerElectricFurnace(player.inventory, (TileElectricFurnace) world.getBlockTileEntity(x, y, z));
        return null;
    }

    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == GUI_GENERATOR) return new GuiGenerator(player.inventory, (TileGenerator) world.getBlockTileEntity(x, y, z));
        if (id == GUI_SOLAR) return new GuiSolarPanel(player.inventory, (TileSolarPanel) world.getBlockTileEntity(x, y, z));
        if (id == GUI_FURNACE) return new GuiElectricFurnace(player.inventory, (TileElectricFurnace) world.getBlockTileEntity(x, y, z));
        return null;
    }
}
