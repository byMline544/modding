package tcw;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tcw.managers.ItemManager;

public class TCWCreativeTab {

    public static final CreativeTabs TAB = new CreativeTabs(CreativeTabs.getNextID(), "technocloud") {
        @Override
        public int getTabIconItemIndex() {
            if (ItemManager.techBook != null) {
                return ItemManager.techBook.itemID;
            }
            return Item.redstone.itemID;
        }
    };

    private TCWCreativeTab() {
    }
}
