package tcw.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class CrusherRecipes {

    private static final CrusherRecipes INSTANCE = new CrusherRecipes();

    private final Map<Integer, ItemStack> outputMap = new HashMap<Integer, ItemStack>();
    private boolean initialized;

    public static CrusherRecipes instance() {
        return INSTANCE;
    }

    private CrusherRecipes() {
    }

    private void ensureInit() {
        if (initialized) {
            return;
        }
        initialized = true;

        if (BlockManager.oreCopper != null) {
            outputMap.put(BlockManager.oreCopper.blockID, new ItemStack(ItemManager.copperDust, 2));
        }
        if (BlockManager.oreTin != null) {
            outputMap.put(BlockManager.oreTin.blockID, new ItemStack(ItemManager.tinDust, 2));
        }
        if (BlockManager.oreNickel != null) {
            outputMap.put(BlockManager.oreNickel.blockID, new ItemStack(ItemManager.nickelDust, 2));
        }

        outputMap.put(ItemManager.copperIngot.itemID, new ItemStack(ItemManager.copperDust, 1));
        outputMap.put(ItemManager.tinIngot.itemID, new ItemStack(ItemManager.tinDust, 1));
        outputMap.put(ItemManager.nickelIngot.itemID, new ItemStack(ItemManager.nickelDust, 1));
        outputMap.put(ItemManager.bronzeIngot.itemID, new ItemStack(ItemManager.bronzeDust, 1));

        outputMap.put(Block.oreIron.blockID, new ItemStack(ItemManager.mixedDust, 1));
        outputMap.put(Block.oreGold.blockID, new ItemStack(ItemManager.mixedDust, 1));
    }

    public ItemStack getResult(ItemStack input) {
        if (input == null) {
            return null;
        }
        ensureInit();
        ItemStack output = outputMap.get(input.itemID);
        return output == null ? null : output.copy();
    }
}
