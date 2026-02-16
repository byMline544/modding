package tcw.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTechBook extends TCWItem {

    public ItemTechBook(int id) {
        super(id, "tech_book");
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(tcw.MainLoader.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }
}
