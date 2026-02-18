package techno.managers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import techno.blocks.BlockElectricFurnace;
import techno.blocks.BlockGenerator;
import techno.blocks.BlockSolarPanel;
import techno.blocks.ore.BlockOreBronze;
import techno.blocks.ore.BlockOreTin;

public class BlockManager {
    public static Block generator;
    public static Block solar;
    public static Block electricFurnace;
    public static Block oreBronze;
    public static Block oreTin;

    public static void init() {
        generator = reg(new BlockGenerator(2900), "generator", "Угольный генератор");
        solar = reg(new BlockSolarPanel(2901), "solar_panel", "Солнечная панель");
        electricFurnace = reg(new BlockElectricFurnace(2902), "electric_furnace", "Электропечь");
        oreBronze = reg(new BlockOreBronze(2903), "ore_bronze", "Бронзовая руда");
        oreTin = reg(new BlockOreTin(2904), "ore_tin", "Оловянная руда");
        MinecraftForge.setBlockHarvestLevel(oreBronze, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(oreTin, "pickaxe", 1);
    }

    private static Block reg(Block b, String key, String ru) {
        GameRegistry.registerBlock(b, key);
        LanguageRegistry.addName(b, ru);
        return b;
    }
}
