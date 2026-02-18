package techno.event;

import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.player.EntityPlayer;

public class EventHandler implements IPlayerTracker {
    public void onPlayerLogin(EntityPlayer player) {}
    public void onPlayerLogout(EntityPlayer player) {}
    public void onPlayerChangedDimension(EntityPlayer player) {}
    public void onPlayerRespawn(EntityPlayer player) {}
}
