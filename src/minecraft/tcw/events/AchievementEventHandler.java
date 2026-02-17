package tcw.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import tcw.achievements.AchievementManager;
import tcw.blocks.MachineTier;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class AchievementEventHandler {

    @ForgeSubscribe
    public void onItemCrafted(ItemCraftedEvent event) {
        if (event == null || event.player == null || event.crafting == null) {
            return;
        }

        EntityPlayer player = event.player;
        ItemStack stack = event.crafting;

        if (stack.itemID == ItemManager.techBook.itemID) {
            player.addStat(AchievementManager.openBook, 1);
        }
        if (stack.itemID == ItemManager.bronzeIngot.itemID) {
            player.addStat(AchievementManager.castBronze, 1);
        }
        if (stack.itemID == BlockManager.machines[MachineTier.CRUSHER.ordinal()].blockID) {
            player.addStat(AchievementManager.firstMachine, 1);
        }
        if (stack.itemID == BlockManager.machines[MachineTier.GENERATOR.ordinal()].blockID) {
            player.addStat(AchievementManager.electricAge, 1);
        }
        if (stack.itemID == BlockManager.solarBasic.blockID) {
            player.addStat(AchievementManager.firstSolar, 1);
        }
        if (stack.itemID == BlockManager.solarUltimate.blockID) {
            player.addStat(AchievementManager.brightFuture, 1);
        }
        if (stack.itemID == ItemManager.nanoChestplate.itemID) {
            player.addStat(AchievementManager.nanoWarrior, 1);
        }
        if (stack.itemID == ItemManager.quantumChestplate.itemID) {
            player.addStat(AchievementManager.quantumWarrior, 1);
        }
        if (stack.itemID == BlockManager.energyCableCryo.blockID) {
            player.addStat(AchievementManager.cableArchitect, 1);
        }
    }

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
