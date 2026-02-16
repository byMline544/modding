package tcw.managers;

import tcw.tiles.TileEntityAlloySmelter;
import tcw.tiles.TileEntityAssembler;
import tcw.tiles.TileEntityCable;
import tcw.tiles.TileEntityCharger;
import tcw.tiles.TileEntityCompressor;
import tcw.tiles.TileEntityCrusher;
import tcw.tiles.TileEntityElectricFurnace;
import tcw.tiles.TileEntityEnergyBuffer;
import tcw.tiles.TileEntityExtractor;
import tcw.tiles.TileEntityGenerator;
import tcw.tiles.TileEntityMachine;
import tcw.tiles.TileEntityMacerator;
import tcw.tiles.TileEntitySolarPanel;
import tcw.tiles.TileEntityWiremill;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileManager {

    public static void registerTiles() {
        GameRegistry.registerTileEntity(TileEntityMachine.class, "tcw.machine");
        GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "tcw.solar");
        GameRegistry.registerTileEntity(TileEntityCrusher.class, "tcw.crusher");
        GameRegistry.registerTileEntity(TileEntityGenerator.class, "tcw.generator");
        GameRegistry.registerTileEntity(TileEntityCompressor.class, "tcw.compressor");
        GameRegistry.registerTileEntity(TileEntityElectricFurnace.class, "tcw.electricFurnace");
        GameRegistry.registerTileEntity(TileEntityAlloySmelter.class, "tcw.alloySmelter");
        GameRegistry.registerTileEntity(TileEntityMacerator.class, "tcw.macerator");
        GameRegistry.registerTileEntity(TileEntityWiremill.class, "tcw.wiremill");
        GameRegistry.registerTileEntity(TileEntityExtractor.class, "tcw.extractor");
        GameRegistry.registerTileEntity(TileEntityAssembler.class, "tcw.assembler");
        GameRegistry.registerTileEntity(TileEntityCharger.class, "tcw.charger");
        GameRegistry.registerTileEntity(TileEntityCable.class, "tcw.cable");
        GameRegistry.registerTileEntity(TileEntityEnergyBuffer.class, "tcw.energyBuffer");
    }
}
