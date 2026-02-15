package tcw.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tcw.managers.ItemManager;

public class AssemblerRecipes {

    private static final AssemblerRecipes INSTANCE = new AssemblerRecipes();

    private final Map<String, ItemStack> recipes = new HashMap<String, ItemStack>();

    public static AssemblerRecipes instance() {
        return INSTANCE;
    }

    private AssemblerRecipes() {
        addRecipe(ItemManager.cable.itemID, ItemManager.coil.itemID, new ItemStack(ItemManager.advancedCircuit));
        addRecipe(ItemManager.bronzeIngot.itemID, Item.redstone.itemID, new ItemStack(ItemManager.coil));
    }

    private void addRecipe(int left, int right, ItemStack output) {
        recipes.put(key(left, right), output);
        recipes.put(key(right, left), output);
    }

    private String key(int left, int right) {
        return left + ":" + right;
    }

    public ItemStack getResult(ItemStack left, ItemStack right) {
        if (left == null || right == null) {
            return null;
        }
        ItemStack out = recipes.get(key(left.itemID, right.itemID));
        return out == null ? null : out.copy();
    }
}
