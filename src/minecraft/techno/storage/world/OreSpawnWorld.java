package techno.storage.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import techno.managers.BlockManager;

public class OreSpawnWorld implements IWorldGenerator {
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId != 0) return;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        for (int i = 0; i < 4; i++) {
            new WorldGenMinable(BlockManager.oreTin.blockID, 6).generate(world, random, x + random.nextInt(16), 16 + random.nextInt(40), z + random.nextInt(16));
            new WorldGenMinable(BlockManager.oreBronze.blockID, 6).generate(world, random, x + random.nextInt(16), 16 + random.nextInt(40), z + random.nextInt(16));
        }
    }
}
