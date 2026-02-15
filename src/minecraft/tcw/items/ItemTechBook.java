package tcw.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTechBook extends Item {

    public ItemTechBook(int id) {
        super(id);
        setUnlocalizedName("tech_book");
        setTextureName("technocloud:tech_book");
        setCreativeTab(CreativeTabs.tabMisc);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(tcw.MainLoader.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }
}
