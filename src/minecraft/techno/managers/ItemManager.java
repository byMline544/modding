package techno.managers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import techno.items.BaseItem;

public class ItemManager {
    public static Item ingotBronze;
    public static Item ingotTin;
    public static Item dustBronze;
    public static Item dustTin;

    public static void init() {
        ingotBronze = reg(new BaseItem(29000, "ingot_bronze"), "ingot_bronze", "Бронзовый слиток");
        ingotTin = reg(new BaseItem(29001, "ingot_tin"), "ingot_tin", "Оловянный слиток");
        dustBronze = reg(new BaseItem(29002, "dust_bronze"), "dust_bronze", "Бронзовая пыль");
        dustTin = reg(new BaseItem(29003, "dust_tin"), "dust_tin", "Оловянная пыль");
    }

    private static Item reg(Item item, String key, String ru) {
        GameRegistry.registerItem(item, key);
        LanguageRegistry.addName(item, ru);
        return item;
    }
}
