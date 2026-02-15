package tcw.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {

    public static final String KEY = "tcw_player";

    private int discoveredMachines;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setInteger("DiscoveredMachines", discoveredMachines);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        discoveredMachines = compound.getInteger("DiscoveredMachines");
    }

    @Override
    public void init(EntityPlayer player, net.minecraft.world.World world) {
        discoveredMachines = 0;
    }
}
