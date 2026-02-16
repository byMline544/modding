package tcw.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tcw.blocks.MachineTier;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeManager {

    public static void registerRecipes() {
        registerMaterialChain();
        registerBronzeEquipment();
        registerMachines();
        registerSolarPanels();
        registerAdvancedComponents();
        registerElectricEquipment();
    }

    private static void registerMaterialChain() {
        GameRegistry.addSmelting(BlockManager.oreCopper.blockID, new ItemStack(ItemManager.copperIngot), 0.8F);
        GameRegistry.addSmelting(BlockManager.oreTin.blockID, new ItemStack(ItemManager.tinIngot), 0.8F);
        GameRegistry.addSmelting(BlockManager.oreNickel.blockID, new ItemStack(ItemManager.nickelIngot), 1.0F);
        GameRegistry.addSmelting(BlockManager.oreSilver.blockID, new ItemStack(ItemManager.silverIngot), 0.9F);
        GameRegistry.addSmelting(BlockManager.oreUranium.blockID, new ItemStack(ItemManager.uraniumIngot), 1.2F);

        GameRegistry.addRecipe(new ItemStack(ItemManager.mixedDust, 2), "CTC", "TNT", "CTC", 'C', ItemManager.copperDust, 'T',
                ItemManager.tinDust, 'N', ItemManager.nickelDust);
        GameRegistry.addSmelting(ItemManager.mixedDust.itemID, new ItemStack(ItemManager.bronzeIngot), 0.6F);

        GameRegistry.addRecipe(new ItemStack(ItemManager.cable, 4), "CCC", "RIR", "CCC", 'C', ItemManager.copperIngot, 'I',
                Item.ingotIron, 'R', Item.redstone);
        GameRegistry.addRecipe(new ItemStack(BlockManager.energyCable, 6), " R ", "CCC", " R ", 'C', ItemManager.cable, 'R', Item.redstone);
        GameRegistry.addRecipe(new ItemStack(BlockManager.energyCableReinforced, 4), "ICI", "CBC", "ICI", 'I', Item.ingotIron, 'C', BlockManager.energyCable, 'B', ItemManager.batteryBasic);
        GameRegistry.addRecipe(new ItemStack(BlockManager.energyCableCryo, 2), "DCD", "ABA", "DCD", 'D', Item.diamond, 'C', BlockManager.energyCableReinforced, 'A', ItemManager.advancedCircuit, 'B', ItemManager.batteryAdvanced);
        GameRegistry.addRecipe(new ItemStack(ItemManager.coil, 2), "CCC", "R R", "CCC", 'C', ItemManager.copperIngot, 'R', Item.redstone);
        GameRegistry.addRecipe(new ItemStack(ItemManager.lens), " G ", "GDG", " G ", 'G', Block.glass, 'D', Item.diamond);
        GameRegistry.addRecipe(new ItemStack(ItemManager.advancedCircuit), "RGR", "CBC", "RGR", 'R', Item.redstone, 'G', Item.goldNugget,
                'C', ItemManager.cable, 'B', ItemManager.batteryBasic);

        GameRegistry.addRecipe(new ItemStack(ItemManager.batteryBasic), " T ", "RCR", " T ", 'T', ItemManager.tinIngot, 'R', Item.redstone,
                'C', ItemManager.cable);
        GameRegistry.addRecipe(new ItemStack(ItemManager.batteryAdvanced), "ACA", "BEB", "ACA", 'A', ItemManager.advancedCircuit, 'C',
                ItemManager.coil, 'B', ItemManager.batteryBasic, 'E', Item.enderPearl);

        GameRegistry.addRecipe(new ItemStack(ItemManager.techBook), "PPP", "PBP", "PPP", 'P', Item.paper, 'B', Item.book);
    }

    private static void registerBronzeEquipment() {
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeSword), " B ", " B ", " S ", 'B', ItemManager.bronzeIngot, 'S', Item.stick);
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzePickaxe), "BBB", " S ", " S ", 'B', ItemManager.bronzeIngot, 'S', Item.stick);
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeAxe), "BB ", "BS ", " S ", 'B', ItemManager.bronzeIngot, 'S', Item.stick);
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeShovel), " B ", " S ", " S ", 'B', ItemManager.bronzeIngot, 'S', Item.stick);
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeHoe), "BB ", " S ", " S ", 'B', ItemManager.bronzeIngot, 'S', Item.stick);

        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeHelmet), "BBB", "B B", 'B', ItemManager.bronzeIngot);
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeChestplate), "B B", "BBB", "BBB", 'B', ItemManager.bronzeIngot);
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeLeggings), "BBB", "B B", "B B", 'B', ItemManager.bronzeIngot);
        GameRegistry.addRecipe(new ItemStack(ItemManager.bronzeBoots), "B B", "B B", 'B', ItemManager.bronzeIngot);
    }

    private static void registerMachines() {
        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.CRUSHER.ordinal()]), "CFC", "PMP", "CBC", 'C',
                ItemManager.cable, 'F', Item.flint, 'P', Block.pistonBase, 'M', Block.blockIron, 'B', ItemManager.batteryBasic);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.GENERATOR.ordinal()]), "CCC", "FMB", "CCC", 'C',
                ItemManager.cable, 'F', Block.furnaceIdle, 'M', Block.blockIron, 'B', ItemManager.batteryBasic);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.COMPRESSOR.ordinal()]), "CCC", "PMP", "ABA", 'C',
                ItemManager.cable, 'P', Block.pistonBase, 'M', Block.blockIron, 'A', ItemManager.advancedCircuit, 'B', ItemManager.batteryBasic);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.FURNACE_ELECTRIC.ordinal()]), "CCC", "FMF", "ABA", 'C',
                ItemManager.cable, 'F', Block.furnaceIdle, 'M', Block.blockIron, 'A', ItemManager.advancedCircuit, 'B', ItemManager.batteryBasic);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.ALLOY_SMELTER.ordinal()]), "CBC", "AMA", "BFB", 'C',
                ItemManager.cable, 'A', ItemManager.advancedCircuit, 'M', Block.blockIron, 'B', ItemManager.batteryBasic, 'F', Block.furnaceIdle);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.WIREMILL.ordinal()]), "CBC", "RMR", "ABA", 'C',
                ItemManager.cable, 'R', Item.redstone, 'M', Block.blockIron, 'A', ItemManager.advancedCircuit, 'B', ItemManager.batteryBasic);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.EXTRACTOR.ordinal()]), "CBC", "DMD", "ABA", 'C',
                ItemManager.cable, 'D', Item.diamond, 'M', Block.blockIron, 'A', ItemManager.advancedCircuit, 'B', ItemManager.batteryBasic);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.ASSEMBLER.ordinal()]), "CBC", "RMR", "ABA", 'C',
                ItemManager.cable, 'R', Item.redstone, 'M', Block.blockIron, 'A', ItemManager.advancedCircuit, 'B', ItemManager.batteryAdvanced);

        GameRegistry.addRecipe(new ItemStack(BlockManager.machines[MachineTier.CHARGER.ordinal()]), "CBC", "BMB", "ACA", 'C',
                ItemManager.cable, 'B', ItemManager.batteryBasic, 'M', Block.blockIron, 'A', ItemManager.advancedCircuit);

        for (int i = 0; i < MachineTier.values().length; i++) {
            if (i == MachineTier.CRUSHER.ordinal() || i == MachineTier.GENERATOR.ordinal() || i == MachineTier.COMPRESSOR.ordinal()
                    || i == MachineTier.FURNACE_ELECTRIC.ordinal() || i == MachineTier.ALLOY_SMELTER.ordinal()
                    || i == MachineTier.WIREMILL.ordinal() || i == MachineTier.EXTRACTOR.ordinal()
                    || i == MachineTier.ASSEMBLER.ordinal() || i == MachineTier.CHARGER.ordinal()) {
                continue;
            }
            Block machine = BlockManager.machines[i];
            GameRegistry.addRecipe(new ItemStack(machine), "CBC", "AMA", "CIC", 'C', ItemManager.cable, 'B', ItemManager.batteryBasic,
                    'A', ItemManager.advancedCircuit, 'M', Block.blockIron, 'I', Item.ingotIron);
        }
    }

    private static void registerSolarPanels() {
        GameRegistry.addRecipe(new ItemStack(BlockManager.solarBasic), "GLG", "CBC", "AIA", 'G', Block.glass, 'L', ItemManager.lens, 'C',
                ItemManager.cable, 'B', ItemManager.batteryBasic, 'A', ItemManager.advancedCircuit, 'I', Block.blockIron);

        GameRegistry.addRecipe(new ItemStack(BlockManager.solarImproved), "PLP", "SBS", "ACA", 'P', BlockManager.solarBasic, 'L',
                ItemManager.lens, 'S', Item.netherStar, 'B', ItemManager.batteryAdvanced, 'A', ItemManager.advancedCircuit, 'C', ItemManager.coil);

        GameRegistry.addRecipe(new ItemStack(BlockManager.solarAdvanced), "PLP", "CBC", "AEA", 'P', BlockManager.solarImproved, 'L',
                ItemManager.lens, 'C', ItemManager.coil, 'B', ItemManager.batteryAdvanced, 'A', ItemManager.advancedCircuit, 'E', Item.eyeOfEnder);

        GameRegistry.addRecipe(new ItemStack(BlockManager.solarUltimate), "PLP", "NBN", "ACA", 'P', BlockManager.solarAdvanced, 'L',
                ItemManager.lens, 'N', Item.netherStar, 'B', ItemManager.batteryAdvanced, 'A', ItemManager.advancedCircuit, 'C', Block.blockDiamond);
    }
    private static void registerAdvancedComponents() {
        GameRegistry.addRecipe(new ItemStack(ItemManager.steelDust, 2), "ICI", "CCC", "ICI", 'I', Item.ingotIron, 'C', Item.coal);
        GameRegistry.addSmelting(ItemManager.steelDust.itemID, new ItemStack(ItemManager.steelIngot), 0.8F);

        GameRegistry.addRecipe(new ItemStack(ItemManager.nanoFiber, 2), "CSC", "SAS", "CSC", 'C', ItemManager.carbonPlate, 'S', Item.silk, 'A', ItemManager.advancedCircuit);
        GameRegistry.addRecipe(new ItemStack(ItemManager.energyMatrix), "ACA", "CBC", "ACA", 'A', ItemManager.advancedCircuit, 'C', ItemManager.coil, 'B', ItemManager.batteryAdvanced);
        GameRegistry.addRecipe(new ItemStack(ItemManager.coolingCell), "STS", "WAW", "STS", 'S', ItemManager.steelIngot, 'T', ItemManager.tinIngot, 'W', Item.bucketWater, 'A', ItemManager.advancedCircuit);
        GameRegistry.addRecipe(new ItemStack(ItemManager.quantumCore), "ENE", "CMC", "ENE", 'E', Item.eyeOfEnder, 'N', Item.netherStar, 'C', ItemManager.energyMatrix, 'M', ItemManager.coolingCell);
    }

    private static void registerElectricEquipment() {
        GameRegistry.addRecipe(new ItemStack(ItemManager.nanoSword), " N ", "NCN", " B ", 'N', ItemManager.nanoFiber, 'C', ItemManager.advancedCircuit, 'B', ItemManager.batteryAdvanced);
        GameRegistry.addRecipe(new ItemStack(ItemManager.nanoPickaxe), "NNN", " C ", " B ", 'N', ItemManager.nanoFiber, 'C', ItemManager.advancedCircuit, 'B', ItemManager.batteryAdvanced);

        GameRegistry.addRecipe(new ItemStack(ItemManager.quantumSword), " Q ", "QCQ", " M ", 'Q', ItemManager.quantumCore, 'C', ItemManager.energyMatrix, 'M', ItemManager.coolingCell);
        GameRegistry.addRecipe(new ItemStack(ItemManager.quantumPickaxe), "QQQ", " C ", " M ", 'Q', ItemManager.quantumCore, 'C', ItemManager.energyMatrix, 'M', ItemManager.coolingCell);

        GameRegistry.addRecipe(new ItemStack(ItemManager.nanoHelmet), "NNN", "NCN", 'N', ItemManager.nanoFiber, 'C', ItemManager.advancedCircuit);
        GameRegistry.addRecipe(new ItemStack(ItemManager.nanoChestplate), "N N", "NCN", "NNN", 'N', ItemManager.nanoFiber, 'C', ItemManager.energyMatrix);
        GameRegistry.addRecipe(new ItemStack(ItemManager.nanoLeggings), "NNN", "N N", "N N", 'N', ItemManager.nanoFiber);
        GameRegistry.addRecipe(new ItemStack(ItemManager.nanoBoots), "N N", "N N", 'N', ItemManager.nanoFiber);

        GameRegistry.addRecipe(new ItemStack(ItemManager.quantumHelmet), "QQQ", "QCQ", 'Q', ItemManager.quantumCore, 'C', ItemManager.energyMatrix);
        GameRegistry.addRecipe(new ItemStack(ItemManager.quantumChestplate), "Q Q", "QMQ", "QQQ", 'Q', ItemManager.quantumCore, 'M', ItemManager.energyMatrix);
        GameRegistry.addRecipe(new ItemStack(ItemManager.quantumLeggings), "QQQ", "Q Q", "Q Q", 'Q', ItemManager.quantumCore);
        GameRegistry.addRecipe(new ItemStack(ItemManager.quantumBoots), "Q Q", "Q Q", 'Q', ItemManager.quantumCore);
    }

}
