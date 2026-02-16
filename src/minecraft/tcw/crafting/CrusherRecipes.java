package tcw.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class CrusherRecipes {

    private static final CrusherRecipes INSTANCE = new CrusherRecipes();

    private final Map<Integer, ItemStack> outputMap = new HashMap<Integer, ItemStack>();

    public static CrusherRecipes instance() {
        return INSTANCE;
    }

    private CrusherRecipes() {
        outputMap.put(BlockManager.oreCopper.blockID, new ItemStack(ItemManager.copperDust, 2));
        outputMap.put(BlockManager.oreTin.blockID, new ItemStack(ItemManager.tinDust, 2));
        outputMap.put(BlockManager.oreNickel.blockID, new ItemStack(ItemManager.nickelDust, 2));
    }

    public ItemStack getResult(ItemStack input) {
        if (input == null) {
            return null;
        }
        ItemStack output = outputMap.get(input.itemID);
        return output == null ? null : output.copy();
    }
}
