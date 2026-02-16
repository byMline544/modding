package tcw.managers;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
import tcw.items.ItemBattery;
import tcw.items.ItemBronzeArmor;
import tcw.items.ItemBronzeAxe;
import tcw.items.ItemBronzeHoe;
import tcw.items.ItemBronzePickaxe;
import tcw.items.ItemBronzeShovel;
import tcw.items.ItemBronzeSword;
import tcw.items.ItemElectricArmor;
import tcw.items.ItemElectricPickaxe;
import tcw.items.ItemElectricSword;
import tcw.items.ItemTechBook;
import tcw.items.ItemJetpack;
import tcw.items.ItemMachineModule;
import tcw.items.ItemVajra;
import tcw.items.ItemWrench;
import tcw.items.TCWItem;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemManager {

    public static final EnumToolMaterial BRONZE_TOOL = EnumHelper.addToolMaterial("TCW_BRONZE", 2, 350, 6.5F, 2, 12);
    public static final EnumArmorMaterial BRONZE_ARMOR = EnumHelper.addArmorMaterial("TCW_BRONZE", 18, new int[] { 2, 6, 5, 2 }, 12);
    public static final EnumToolMaterial ELECTRO_TOOL = EnumHelper.addToolMaterial("TCW_ELECTRO", 3, 2200, 10.0F, 4, 18);
    public static final EnumToolMaterial QUANTUM_TOOL = EnumHelper.addToolMaterial("TCW_QUANTUM", 4, 3800, 13.0F, 6, 24);
    public static final EnumToolMaterial VAJRA_TOOL = EnumHelper.addToolMaterial("TCW_VAJRA", 5, 1, 20.0F, 10, 28);
    public static final EnumArmorMaterial NANO_ARMOR = EnumHelper.addArmorMaterial("TCW_NANO", 33, new int[] { 3, 8, 6, 3 }, 18);
    public static final EnumArmorMaterial QUANTUM_ARMOR = EnumHelper.addArmorMaterial("TCW_QUANTUM", 44, new int[] { 4, 9, 7, 4 }, 22);

    public static Item copperIngot;
    public static Item tinIngot;
    public static Item nickelIngot;
    public static Item bronzeIngot;
    public static Item silverIngot;
    public static Item uraniumIngot;
    public static Item steelIngot;

    public static Item copperDust;
    public static Item tinDust;
    public static Item nickelDust;
    public static Item bronzeDust;
    public static Item silverDust;
    public static Item uraniumDust;
    public static Item steelDust;

    public static Item mixedDust;
    public static Item carbonPlate;
    public static Item advancedCircuit;
    public static Item lens;
    public static Item coil;
    public static Item cable;
    public static Item nanoFiber;
    public static Item quantumCore;
    public static Item energyMatrix;
    public static Item coolingCell;

    public static Item crystalRuby;
    public static Item crystalSapphire;
    public static Item reinforcedPlate;
    public static Item superconductiveCore;

    public static Item moduleOverclocker;
    public static Item moduleTransformer;
    public static Item moduleCapacity;

    public static Item batteryBasic;
    public static Item batteryAdvanced;
    public static Item techBook;
    public static Item wrench;

    public static Item bronzeSword;
    public static Item bronzePickaxe;
    public static Item bronzeAxe;
    public static Item bronzeShovel;
    public static Item bronzeHoe;

    public static Item bronzeHelmet;
    public static Item bronzeChestplate;
    public static Item bronzeLeggings;
    public static Item bronzeBoots;

    public static Item nanoSword;
    public static Item nanoPickaxe;
    public static Item quantumSword;
    public static Item quantumPickaxe;
    public static Item vajra;

    public static Item jetpackBasic;
    public static Item jetpackAdvanced;
    public static Item jetpackQuantum;

    public static Item nanoHelmet;
    public static Item nanoChestplate;
    public static Item nanoLeggings;
    public static Item nanoBoots;

    public static Item quantumHelmet;
    public static Item quantumChestplate;
    public static Item quantumLeggings;
    public static Item quantumBoots;

    public static void initItems() {
        copperIngot = registerSimple(28000, "copper_ingot", "Медный слиток");
        tinIngot = registerSimple(28001, "tin_ingot", "Оловянный слиток");
        nickelIngot = registerSimple(28002, "nickel_ingot", "Никелевый слиток");
        bronzeIngot = registerSimple(28003, "bronze_ingot", "Бронзовый слиток");
        silverIngot = registerSimple(28004, "silver_ingot", "Серебряный слиток");
        uraniumIngot = registerSimple(28005, "uranium_ingot", "Урановый слиток");
        steelIngot = registerSimple(28006, "steel_ingot", "Стальной слиток");

        copperDust = registerSimple(28007, "copper_dust", "Медная пыль");
        tinDust = registerSimple(28008, "tin_dust", "Оловянная пыль");
        nickelDust = registerSimple(28009, "nickel_dust", "Никелевая пыль");
        bronzeDust = registerSimple(28010, "bronze_dust", "Бронзовая пыль");
        silverDust = registerSimple(28011, "silver_dust", "Серебряная пыль");
        uraniumDust = registerSimple(28012, "uranium_dust", "Урановая пыль");
        steelDust = registerSimple(28013, "steel_dust", "Стальная пыль");

        mixedDust = registerSimple(28014, "mixed_dust", "Смешанная пыль");
        carbonPlate = registerSimple(28015, "carbon_plate", "Карбоновая пластина");
        advancedCircuit = registerSimple(28016, "advanced_circuit", "Продвинутая схема");
        lens = registerSimple(28017, "lens", "Энергетическая линза");
        coil = registerSimple(28018, "coil", "Медная катушка");
        cable = registerSimple(28019, "cable", "Изолированный кабель");
        nanoFiber = registerSimple(28020, "nano_fiber", "Нано-волокно");
        quantumCore = registerSimple(28021, "quantum_core", "Квантовое ядро");
        energyMatrix = registerSimple(28022, "energy_matrix", "Энергоматрица");
        coolingCell = registerSimple(28023, "cooling_cell", "Охлаждающая ячейка");
        crystalRuby = registerSimple(28049, "crystal_ruby", "Рубиновый кристалл");
        crystalSapphire = registerSimple(28050, "crystal_sapphire", "Сапфировый кристалл");
        reinforcedPlate = registerSimple(28051, "reinforced_plate", "Усиленная пластина");
        superconductiveCore = registerSimple(28052, "superconductive_core", "Сверхпроводящее ядро");

        moduleOverclocker = registerTool(new ItemMachineModule(28057, "module_overclocker", ItemMachineModule.TYPE_OVERCLOCKER), "module_overclocker", "Модуль-ускоритель");
        moduleTransformer = registerTool(new ItemMachineModule(28058, "module_transformer", ItemMachineModule.TYPE_TRANSFORMER), "module_transformer", "Модуль-трансформатор");
        moduleCapacity = registerTool(new ItemMachineModule(28059, "module_capacity", ItemMachineModule.TYPE_CAPACITY), "module_capacity", "Модуль-ёмкость");

        batteryBasic = registerTool(new ItemBattery(28024, "battery_basic", 50000), "battery_basic", "Базовая батарея");
        batteryAdvanced = registerTool(new ItemBattery(28025, "battery_advanced", 250000), "battery_advanced", "Улучшенная батарея");

        techBook = registerTool(new ItemTechBook(28026), "tech_book", "Справочник TechnoCloud");
        wrench = registerTool(new ItemWrench(28027), "wrench", "Гаечный ключ");

        bronzeSword = registerTool(new ItemBronzeSword(28028, BRONZE_TOOL, "bronze_sword"), "bronze_sword", "Бронзовый меч");
        bronzePickaxe = registerTool(new ItemBronzePickaxe(28029, BRONZE_TOOL, "bronze_pickaxe"), "bronze_pickaxe", "Бронзовая кирка");
        bronzeAxe = registerTool(new ItemBronzeAxe(28030, BRONZE_TOOL, "bronze_axe"), "bronze_axe", "Бронзовый топор");
        bronzeShovel = registerTool(new ItemBronzeShovel(28031, BRONZE_TOOL, "bronze_shovel"), "bronze_shovel", "Бронзовая лопата");
        bronzeHoe = registerTool(new ItemBronzeHoe(28032, BRONZE_TOOL, "bronze_hoe"), "bronze_hoe", "Бронзовая мотыга");

        bronzeHelmet = registerTool(new ItemBronzeArmor(28033, BRONZE_ARMOR, 0, 0, "bronze_helmet"), "bronze_helmet", "Бронзовый шлем");
        bronzeChestplate = registerTool(new ItemBronzeArmor(28034, BRONZE_ARMOR, 0, 1, "bronze_chestplate"), "bronze_chestplate", "Бронзовый нагрудник");
        bronzeLeggings = registerTool(new ItemBronzeArmor(28035, BRONZE_ARMOR, 0, 2, "bronze_leggings"), "bronze_leggings", "Бронзовые поножи");
        bronzeBoots = registerTool(new ItemBronzeArmor(28036, BRONZE_ARMOR, 0, 3, "bronze_boots"), "bronze_boots", "Бронзовые ботинки");

        nanoSword = registerTool(new ItemElectricSword(28037, ELECTRO_TOOL, "nano_sword"), "nano_sword", "Нано-меч");
        nanoPickaxe = registerTool(new ItemElectricPickaxe(28038, ELECTRO_TOOL, "nano_pickaxe"), "nano_pickaxe", "Нано-кирка");
        quantumSword = registerTool(new ItemElectricSword(28039, QUANTUM_TOOL, "quantum_sword"), "quantum_sword", "Квантовый меч");
        quantumPickaxe = registerTool(new ItemElectricPickaxe(28040, QUANTUM_TOOL, "quantum_pickaxe"), "quantum_pickaxe", "Квантовая кирка");
        vajra = registerTool(new ItemVajra(28053, VAJRA_TOOL, 2200000), "vajra", "Ваджра-разрушитель");

        nanoHelmet = registerTool(new ItemElectricArmor(28041, NANO_ARMOR, 0, 0, "nano_helmet", "nano"), "nano_helmet", "Нано-шлем");
        nanoChestplate = registerTool(new ItemElectricArmor(28042, NANO_ARMOR, 0, 1, "nano_chestplate", "nano"), "nano_chestplate", "Нано-нагрудник");
        nanoLeggings = registerTool(new ItemElectricArmor(28043, NANO_ARMOR, 0, 2, "nano_leggings", "nano"), "nano_leggings", "Нано-поножи");
        nanoBoots = registerTool(new ItemElectricArmor(28044, NANO_ARMOR, 0, 3, "nano_boots", "nano"), "nano_boots", "Нано-ботинки");

        quantumHelmet = registerTool(new ItemElectricArmor(28045, QUANTUM_ARMOR, 0, 0, "quantum_helmet", "quantum"), "quantum_helmet", "Квантовый шлем");
        quantumChestplate = registerTool(new ItemElectricArmor(28046, QUANTUM_ARMOR, 0, 1, "quantum_chestplate", "quantum"), "quantum_chestplate", "Квантовый нагрудник");
        quantumLeggings = registerTool(new ItemElectricArmor(28047, QUANTUM_ARMOR, 0, 2, "quantum_leggings", "quantum"), "quantum_leggings", "Квантовые поножи");
        quantumBoots = registerTool(new ItemElectricArmor(28048, QUANTUM_ARMOR, 0, 3, "quantum_boots", "quantum"), "quantum_boots", "Квантовые ботинки");

        jetpackBasic = registerTool(new ItemJetpack(28054, "jetpack_basic", 400000, 0.09D, 18), "jetpack_basic", "Базовый джетпак");
        jetpackAdvanced = registerTool(new ItemJetpack(28055, "jetpack_advanced", 900000, 0.12D, 28), "jetpack_advanced", "Продвинутый джетпак");
        jetpackQuantum = registerTool(new ItemJetpack(28056, "jetpack_quantum", 1800000, 0.15D, 42), "jetpack_quantum", "Квантовый джетпак");
    }

    private static Item registerSimple(int id, String key, String ruName) {
        Item item = new TCWItem(id, key);
        GameRegistry.registerItem(item, key);
        LanguageRegistry.addName(item, ruName);
        return item;
    }

    private static Item registerTool(Item item, String key, String ruName) {
        GameRegistry.registerItem(item, key);
        LanguageRegistry.addName(item, ruName);
        return item;
    }
}
