package techno;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import techno.managers.BlockManager;
import techno.managers.ItemManager;

public final class TechnoCreativeTabs {

    public static final CreativeTabs TAB_BLOCKS = new CreativeTabs("industrialTechno.blocks") {
        public Item getTabIconItem() {
            return BlockManager.generator == null ? Item.ingotIron : Item.itemsList[BlockManager.generator.blockID];
        }
    };

    public static final CreativeTabs TAB_ITEMS = new CreativeTabs("industrialTechno.items") {
        public Item getTabIconItem() {
            return ItemManager.ingotBronze == null ? Item.ingotIron : ItemManager.ingotBronze;
        }
    };

    private TechnoCreativeTabs() {
    }
}
