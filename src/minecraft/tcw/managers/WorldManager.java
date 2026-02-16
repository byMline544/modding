package tcw.managers;

import tcw.world.WorldGenOres;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldManager {

    public static void registerWorldGen() {
        GameRegistry.registerWorldGenerator(new WorldGenOres());
    }
}
