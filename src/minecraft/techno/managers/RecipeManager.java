package techno.managers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techno.storage.crafting.ElectricFurnaceRecipes;

public class RecipeManager {
    public static void init() {
        GameRegistry.addSmelting(BlockManager.oreTin.blockID, new ItemStack(ItemManager.ingotTin), 0.6F);
        ElectricFurnaceRecipes.register(new ItemStack(Item.oreIron), new ItemStack(Item.ingotIron));
    }
}
