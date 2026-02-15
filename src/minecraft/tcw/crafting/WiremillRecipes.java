package tcw.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import tcw.managers.ItemManager;

public class WiremillRecipes {

    private static final WiremillRecipes INSTANCE = new WiremillRecipes();

    private final Map<Integer, ItemStack> recipes = new HashMap<Integer, ItemStack>();

    public static WiremillRecipes instance() {
        return INSTANCE;
    }

    private WiremillRecipes() {
        recipes.put(ItemManager.copperIngot.itemID, new ItemStack(ItemManager.cable, 3));
        recipes.put(ItemManager.tinIngot.itemID, new ItemStack(ItemManager.cable, 2));
    }

    public ItemStack getResult(ItemStack input) {
        if (input == null) {
            return null;
        }
        ItemStack result = recipes.get(input.itemID);
        return result == null ? null : result.copy();
    }
}
