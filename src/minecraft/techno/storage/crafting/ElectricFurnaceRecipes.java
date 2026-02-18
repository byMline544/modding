package techno.storage.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class ElectricFurnaceRecipes {
    private static final Map<Integer, ItemStack> RECIPES = new HashMap<Integer, ItemStack>();

    public static void register(ItemStack in, ItemStack out) {
        if (in != null && out != null) RECIPES.put(Integer.valueOf(in.itemID), out.copy());
    }

    public static ItemStack getResult(ItemStack in) {
        if (in == null) return null;
        ItemStack out = RECIPES.get(Integer.valueOf(in.itemID));
        return out == null ? null : out.copy();
    }
}
