package tcw.managers;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import tcw.blocks.BlockBaseMachine;
import tcw.blocks.BlockAlloySmelter;
import tcw.blocks.BlockAssembler;
import tcw.blocks.BlockCable;
import tcw.blocks.BlockCharger;
import tcw.blocks.BlockCompressor;
import tcw.blocks.BlockCrusher;
import tcw.blocks.BlockElectricFurnace;
import tcw.blocks.BlockExtractor;
import tcw.blocks.BlockGenerator;
import tcw.blocks.BlockOreTCW;
import tcw.blocks.BlockSolarPanel;
import tcw.blocks.BlockWiremill;
import tcw.blocks.MachineTier;
import tcw.energy.CableTier;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockManager {

    public static Block oreCopper;
    public static Block oreTin;
    public static Block oreNickel;

    public static Block solarBasic;
    public static Block solarImproved;
    public static Block solarAdvanced;
    public static Block solarUltimate;

    public static Block energyCable;
    public static Block energyCableReinforced;
    public static Block energyCableCryo;

    public static Block[] machines;

    public static void initBlocks() {
        oreCopper = registerBlock(new BlockOreTCW(3100, "ore_copper"), "ore_copper", "Медная руда");
        oreTin = registerBlock(new BlockOreTCW(3101, "ore_tin"), "ore_tin", "Оловянная руда");
        oreNickel = registerBlock(new BlockOreTCW(3102, "ore_nickel"), "ore_nickel", "Никелевая руда");
        MinecraftForge.setBlockHarvestLevel(oreCopper, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(oreTin, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(oreNickel, "pickaxe", 2);

        solarBasic = registerBlock(new BlockSolarPanel(3103, "solar_basic", 150000, 8), "solar_basic", "Легкая солнечная панель");
        solarImproved = registerBlock(new BlockSolarPanel(3104, "solar_improved", 500000, 22), "solar_improved", "Улучшенная солнечная панель");
        solarAdvanced = registerBlock(new BlockSolarPanel(3105, "solar_advanced", 1000000, 45), "solar_advanced", "Продвинутая солнечная панель");
        solarUltimate = registerBlock(new BlockSolarPanel(3106, "solar_ultimate", 4000000, 110), "solar_ultimate", "Усовершенствованная солнечная панель");

        energyCable = registerBlock(new BlockCable(3107, CableTier.BASIC), "energy_cable", "Энергетический кабель (базовый)");
        energyCableReinforced = registerBlock(new BlockCable(3108, CableTier.REINFORCED), "energy_cable_reinforced", "Энергетический кабель (усиленный)");
        energyCableCryo = registerBlock(new BlockCable(3109, CableTier.CRYO), "energy_cable_cryo", "Энергетический кабель (крио)");

        machines = new Block[MachineTier.values().length];
        for (int i = 0; i < MachineTier.values().length; i++) {
            MachineTier tier = MachineTier.values()[i];
            if (tier == MachineTier.CRUSHER) {
                machines[i] = registerBlock(new BlockCrusher(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.GENERATOR) {
                machines[i] = registerBlock(new BlockGenerator(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.COMPRESSOR) {
                machines[i] = registerBlock(new BlockCompressor(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.FURNACE_ELECTRIC) {
                machines[i] = registerBlock(new BlockElectricFurnace(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.ALLOY_SMELTER) {
                machines[i] = registerBlock(new BlockAlloySmelter(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.WIREMILL) {
                machines[i] = registerBlock(new BlockWiremill(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.EXTRACTOR) {
                machines[i] = registerBlock(new BlockExtractor(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.ASSEMBLER) {
                machines[i] = registerBlock(new BlockAssembler(3110 + i), tier.key, tier.ruName);
            } else if (tier == MachineTier.CHARGER) {
                machines[i] = registerBlock(new BlockCharger(3110 + i), tier.key, tier.ruName);
            } else {
                machines[i] = registerBlock(new BlockBaseMachine(3110 + i, tier.key), tier.key, tier.ruName);
            }
        }
    }

    private static Block registerBlock(Block block, String key, String ruName) {
        GameRegistry.registerBlock(block, key);
        LanguageRegistry.addName(block, ruName);
        return block;
    }
}
