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
import tcw.items.ItemTechBook;
import tcw.items.TCWItem;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemManager {

    public static final EnumToolMaterial BRONZE_TOOL = EnumHelper.addToolMaterial("TCW_BRONZE", 2, 350, 6.5F, 2, 12);
    public static final EnumArmorMaterial BRONZE_ARMOR = EnumHelper.addArmorMaterial("TCW_BRONZE", 18, new int[] { 2, 6, 5, 2 }, 12);

    public static Item copperIngot;
    public static Item tinIngot;
    public static Item nickelIngot;
    public static Item bronzeIngot;

    public static Item copperDust;
    public static Item tinDust;
    public static Item nickelDust;
    public static Item bronzeDust;

    public static Item mixedDust;
    public static Item carbonPlate;
    public static Item advancedCircuit;
    public static Item lens;
    public static Item coil;
    public static Item cable;

    public static Item batteryBasic;
    public static Item batteryAdvanced;
    public static Item techBook;

    public static Item bronzeSword;
    public static Item bronzePickaxe;
    public static Item bronzeAxe;
    public static Item bronzeShovel;
    public static Item bronzeHoe;

    public static Item bronzeHelmet;
    public static Item bronzeChestplate;
    public static Item bronzeLeggings;
    public static Item bronzeBoots;

    public static void initItems() {
        copperIngot = registerSimple(28000, "copper_ingot", "Медный слиток");
        tinIngot = registerSimple(28001, "tin_ingot", "Оловянный слиток");
        nickelIngot = registerSimple(28002, "nickel_ingot", "Никелевый слиток");
        bronzeIngot = registerSimple(28003, "bronze_ingot", "Бронзовый слиток");

        copperDust = registerSimple(28004, "copper_dust", "Медная пыль");
        tinDust = registerSimple(28005, "tin_dust", "Оловянная пыль");
        nickelDust = registerSimple(28006, "nickel_dust", "Никелевая пыль");
        bronzeDust = registerSimple(28007, "bronze_dust", "Бронзовая пыль");

        mixedDust = registerSimple(28008, "mixed_dust", "Смешанная пыль");
        carbonPlate = registerSimple(28009, "carbon_plate", "Карбоновая пластина");
        advancedCircuit = registerSimple(28010, "advanced_circuit", "Продвинутая схема");
        lens = registerSimple(28011, "lens", "Энергетическая линза");
        coil = registerSimple(28012, "coil", "Медная катушка");
        cable = registerSimple(28013, "cable", "Изолированный кабель");

        batteryBasic = registerTool(new ItemBattery(28014, "battery_basic", 50000), "battery_basic", "Базовая батарея");
        batteryAdvanced = registerTool(new ItemBattery(28015, "battery_advanced", 250000), "battery_advanced", "Улучшенная батарея");

        techBook = registerTool(new ItemTechBook(28016), "tech_book", "Справочник TechnoCloud");

        bronzeSword = registerTool(new ItemBronzeSword(28017, BRONZE_TOOL, "bronze_sword"), "bronze_sword", "Бронзовый меч");
        bronzePickaxe = registerTool(new ItemBronzePickaxe(28018, BRONZE_TOOL, "bronze_pickaxe"), "bronze_pickaxe", "Бронзовая кирка");
        bronzeAxe = registerTool(new ItemBronzeAxe(28019, BRONZE_TOOL, "bronze_axe"), "bronze_axe", "Бронзовый топор");
        bronzeShovel = registerTool(new ItemBronzeShovel(28020, BRONZE_TOOL, "bronze_shovel"), "bronze_shovel", "Бронзовая лопата");
        bronzeHoe = registerTool(new ItemBronzeHoe(28021, BRONZE_TOOL, "bronze_hoe"), "bronze_hoe", "Бронзовая мотыга");

        bronzeHelmet = registerTool(new ItemBronzeArmor(28022, BRONZE_ARMOR, 0, 0, "bronze_helmet"), "bronze_helmet", "Бронзовый шлем");
        bronzeChestplate = registerTool(new ItemBronzeArmor(28023, BRONZE_ARMOR, 0, 1, "bronze_chestplate"), "bronze_chestplate", "Бронзовый нагрудник");
        bronzeLeggings = registerTool(new ItemBronzeArmor(28024, BRONZE_ARMOR, 0, 2, "bronze_leggings"), "bronze_leggings", "Бронзовые поножи");
        bronzeBoots = registerTool(new ItemBronzeArmor(28025, BRONZE_ARMOR, 0, 3, "bronze_boots"), "bronze_boots", "Бронзовые ботинки");
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
