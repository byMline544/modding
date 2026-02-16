package tcw.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tcw.managers.ItemManager;

public class CompressorRecipes {

    private static final CompressorRecipes INSTANCE = new CompressorRecipes();

    private final Map<Integer, ItemStack> outputMap = new HashMap<Integer, ItemStack>();

    public static CompressorRecipes instance() {
        return INSTANCE;
    }

    private CompressorRecipes() {
        outputMap.put(ItemManager.mixedDust.itemID, new ItemStack(ItemManager.bronzeIngot, 2));
        outputMap.put(Item.coal.itemID, new ItemStack(ItemManager.carbonPlate));
    }

    public ItemStack getResult(ItemStack input) {
        if (input == null) {
            return null;
        }
        ItemStack output = outputMap.get(input.itemID);
        return output == null ? null : output.copy();
    }
}
