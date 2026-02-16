package tcw;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class TCWCreativeTab {

    public static final CreativeTabs TAB_MACHINES = new CreativeTabs("technocloud.machines") {
        @Override
        public Item getTabIconItem() {
            if (BlockManager.machines != null && BlockManager.machines.length > 0 && BlockManager.machines[0] != null) {
                return Item.itemsList[BlockManager.machines[0].blockID];
            }
            return Item.redstone;
        }
    };

    public static final CreativeTabs TAB_EQUIPMENT = new CreativeTabs("technocloud.equipment") {
        @Override
        public Item getTabIconItem() {
            return ItemManager.nanoSword != null ? ItemManager.nanoSword : Item.diamondSword;
        }
    };

    public static final CreativeTabs TAB_COMPONENTS = new CreativeTabs("technocloud.components") {
        @Override
        public Item getTabIconItem() {
            return ItemManager.advancedCircuit != null ? ItemManager.advancedCircuit : Item.redstone;
        }
    };

    // Обратная совместимость со старым кодом.
    public static final CreativeTabs TAB = TAB_COMPONENTS;

    private TCWCreativeTab() {
    }
}
