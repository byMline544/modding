package tcw.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tcw.blocks.BlockBaseMachine;
import tcw.blocks.BlockCable;

public class ItemWrench extends TCWItem {

    public ItemWrench(int id) {
        super(id, "wrench");
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY,
            float hitZ) {
        if (world.isRemote) {
            return false;
        }

        int id = world.getBlockId(x, y, z);
        Block block = Block.blocksList[id];
        if (!(block instanceof BlockBaseMachine) && !(block instanceof BlockCable)) {
            return false;
        }

        int meta = world.getBlockMetadata(x, y, z);
        // На ПКМ снимаем блок без порчи и с сохранением блока, как ключ в IC2.
        BlockBaseMachine.dropInventory(world, x, y, z);
        world.setBlockToAir(x, y, z);

        ItemStack drop = new ItemStack(id, 1, meta);
        if (!player.inventory.addItemStackToInventory(drop)) {
            player.dropPlayerItem(drop);
        }
        return true;
    }
}
