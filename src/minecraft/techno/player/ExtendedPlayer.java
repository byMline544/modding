package techno.player;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {
    public static final String KEY = "techno.player";
    private int firstJoin = 1;

    public void saveNBTData(NBTTagCompound compound) {
        compound.setInteger("FirstJoin", firstJoin);
    }

    public void loadNBTData(NBTTagCompound compound) {
        firstJoin = compound.getInteger("FirstJoin");
    }

    public void init(Entity entity, World world) {}

    public boolean isFirstJoin() { return firstJoin == 1; }
    public void markJoined() { firstJoin = 0; }
}
