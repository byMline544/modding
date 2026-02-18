package techno.managers;

import cpw.mods.fml.common.registry.GameRegistry;
import techno.storage.world.OreSpawnWorld;

public class WorldManager {
    public static void init() {
        GameRegistry.registerWorldGenerator(new OreSpawnWorld());
    }
}
