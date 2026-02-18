package tcw.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import tcw.achievements.AchievementManager;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class AchievementEventHandler {

    @ForgeSubscribe
    public void onItemPickup(EntityItemPickupEvent event) {
        if (event == null || event.item == null || event.item.getEntityItem() == null || event.entityPlayer == null) {
            return;
        }

        EntityPlayer player = event.entityPlayer;
        ItemStack stack = event.item.getEntityItem();

        if (stack.itemID == BlockManager.oreNickel.blockID) {
            player.addStat(AchievementManager.oreHunter, 1);
        }
        if (stack.itemID == BlockManager.oreSilver.blockID) {
            player.addStat(AchievementManager.silverAge, 1);
        }
        if (stack.itemID == BlockManager.oreUranium.blockID) {
            player.addStat(AchievementManager.uraniumAge, 1);
        }
        if (stack.itemID == ItemManager.steelIngot.itemID) {
            player.addStat(AchievementManager.steelMind, 1);
        }
        if (stack.itemID == ItemManager.nanoFiber.itemID) {
            player.addStat(AchievementManager.nanoCrafter, 1);
        }
        if (stack.itemID == ItemManager.energyMatrix.itemID) {
            player.addStat(AchievementManager.matrixBuilder, 1);
        }
        if (stack.itemID == ItemManager.quantumCore.itemID) {
            player.addStat(AchievementManager.quantumLeap, 1);
        }
    }
}
