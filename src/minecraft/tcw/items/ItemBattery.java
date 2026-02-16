package tcw.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBattery extends Item {

    private final int maxEnergy;

    public ItemBattery(int id, String name, int maxEnergy) {
        super(id);
        this.maxEnergy = maxEnergy;
        setUnlocalizedName(name);
        setTextureName("technocloud:" + name);
        setCreativeTab(CreativeTabs.tabMaterials);
        setMaxStackSize(1);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
}
