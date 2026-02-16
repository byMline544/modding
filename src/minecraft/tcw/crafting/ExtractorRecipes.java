package tcw.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class ExtractorRecipes {

    private static final ExtractorRecipes INSTANCE = new ExtractorRecipes();

    private final Map<Integer, ItemStack> recipes = new HashMap<Integer, ItemStack>();

    public static ExtractorRecipes instance() {
        return INSTANCE;
    }

    private ExtractorRecipes() {
        recipes.put(BlockManager.oreCopper.blockID, new ItemStack(ItemManager.copperDust, 3));
        recipes.put(BlockManager.oreTin.blockID, new ItemStack(ItemManager.tinDust, 3));
        recipes.put(BlockManager.oreNickel.blockID, new ItemStack(ItemManager.nickelDust, 3));
    }

    public ItemStack getResult(ItemStack input) {
        if (input == null) {
            return null;
        }
        ItemStack output = recipes.get(input.itemID);
        return output == null ? null : output.copy();
    }
}
