package tcw.player;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
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
    public void init(Entity entity, World world) {
        discoveredMachines = 0;
    }
}
