package techno.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public abstract class BaseContainerMachine extends Container {
    public boolean canInteractWith(EntityPlayer player) { return true; }
}
