package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tcw.book.ContainerTechBook;
import tcw.tiles.TileEntityAlloySmelter;
import tcw.tiles.TileEntityAssembler;
import tcw.tiles.TileEntityCharger;
import tcw.tiles.TileEntityCompressor;
import tcw.tiles.TileEntityCrusher;
import tcw.tiles.TileEntityElectricFurnace;
import tcw.tiles.TileEntityExtractor;
import tcw.tiles.TileEntityGenerator;
import tcw.tiles.TileEntityWiremill;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) {
            return new ContainerTechBook(player.inventory);
        }

        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (id == 1 && tile instanceof TileEntityCrusher) return new ContainerCrusher(player.inventory, (TileEntityCrusher) tile);
        if (id == 2 && tile instanceof TileEntityGenerator) return new ContainerGenerator(player.inventory, (TileEntityGenerator) tile);
        if (id == 3 && tile instanceof TileEntityCompressor) return new ContainerCompressor(player.inventory, (TileEntityCompressor) tile);
        if (id == 4 && tile instanceof TileEntityElectricFurnace) return new ContainerElectricFurnace(player.inventory, (TileEntityElectricFurnace) tile);
        if (id == 5 && tile instanceof TileEntityAlloySmelter) return new ContainerAlloySmelter(player.inventory, (TileEntityAlloySmelter) tile);
        if (id == 6 && tile instanceof TileEntityWiremill) return new ContainerWiremill(player.inventory, (TileEntityWiremill) tile);
        if (id == 7 && tile instanceof TileEntityExtractor) return new ContainerExtractor(player.inventory, (TileEntityExtractor) tile);
        if (id == 8 && tile instanceof TileEntityAssembler) return new ContainerAssembler(player.inventory, (TileEntityAssembler) tile);
        if (id == 9 && tile instanceof TileEntityCharger) return new ContainerCharger(player.inventory, (TileEntityCharger) tile);
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) {
            return new GuiTechBook(player.inventory);
        }

        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (id == 1 && tile instanceof TileEntityCrusher) return new GuiCrusher(player.inventory, (TileEntityCrusher) tile);
        if (id == 2 && tile instanceof TileEntityGenerator) return new GuiGenerator(player.inventory, (TileEntityGenerator) tile);
        if (id == 3 && tile instanceof TileEntityCompressor) return new GuiCompressor(player.inventory, (TileEntityCompressor) tile);
        if (id == 4 && tile instanceof TileEntityElectricFurnace) return new GuiElectricFurnace(player.inventory, (TileEntityElectricFurnace) tile);
        if (id == 5 && tile instanceof TileEntityAlloySmelter) return new GuiAlloySmelter(player.inventory, (TileEntityAlloySmelter) tile);
        if (id == 6 && tile instanceof TileEntityWiremill) return new GuiWiremill(player.inventory, (TileEntityWiremill) tile);
        if (id == 7 && tile instanceof TileEntityExtractor) return new GuiExtractor(player.inventory, (TileEntityExtractor) tile);
        if (id == 8 && tile instanceof TileEntityAssembler) return new GuiAssembler(player.inventory, (TileEntityAssembler) tile);
        if (id == 9 && tile instanceof TileEntityCharger) return new GuiCharger(player.inventory, (TileEntityCharger) tile);
        return null;
    }
}
