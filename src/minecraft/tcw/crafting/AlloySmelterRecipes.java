package tcw.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import tcw.managers.ItemManager;

public class AlloySmelterRecipes {

    private static final AlloySmelterRecipes INSTANCE = new AlloySmelterRecipes();

    private final Map<String, ItemStack> recipes = new HashMap<String, ItemStack>();

    public static AlloySmelterRecipes instance() {
        return INSTANCE;
    }

    private AlloySmelterRecipes() {
        addRecipe(ItemManager.copperIngot.itemID, ItemManager.tinIngot.itemID, new ItemStack(ItemManager.bronzeIngot, 2));
        addRecipe(ItemManager.copperDust.itemID, ItemManager.tinDust.itemID, new ItemStack(ItemManager.bronzeDust, 2));
    }

    private void addRecipe(int firstId, int secondId, ItemStack output) {
        recipes.put(makeKey(firstId, secondId), output);
        recipes.put(makeKey(secondId, firstId), output);
    }

    private String makeKey(int firstId, int secondId) {
        return firstId + ":" + secondId;
    }

    public ItemStack getResult(ItemStack first, ItemStack second) {
        if (first == null || second == null) {
            return null;
        }
        ItemStack result = recipes.get(makeKey(first.itemID, second.itemID));
        return result == null ? null : result.copy();
    }
}
