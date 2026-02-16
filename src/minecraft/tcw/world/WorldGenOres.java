package tcw.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import tcw.managers.BlockManager;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenOres implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
        case -1:
            generateNether(world, random, chunkX * 16, chunkZ * 16);
            break;
        case 0:
            generateSurface(world, random, chunkX * 16, chunkZ * 16);
            break;
        default:
            break;
        }
    }

    private void generateSurface(World world, Random random, int x, int z) {
        addOreSpawn(BlockManager.oreCopper.blockID, world, random, x, z, 8, 28, 20, 10, 64);
        addOreSpawn(BlockManager.oreTin.blockID, world, random, x, z, 7, 24, 16, 8, 48);
        addOreSpawn(BlockManager.oreNickel.blockID, world, random, x, z, 6, 16, 10, 6, 34);
        addOreSpawn(BlockManager.oreSilver.blockID, world, random, x, z, 6, 14, 8, 4, 20);
        addOreSpawn(BlockManager.oreUranium.blockID, world, random, x, z, 5, 10, 6, 2, 8);
    }

    private void generateNether(World world, Random random, int x, int z) {
        addOreSpawn(BlockManager.oreNickel.blockID, world, random, x, z, 5, 12, 6, 16, 80);
    }

    private void addOreSpawn(int blockId, World world, Random random, int blockXPos, int blockZPos, int maxX,
            int maxZ, int maxVeinSize, int chancesToSpawn, int minY) {
        int diff = maxX - minY;
        for (int i = 0; i < chancesToSpawn; i++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(diff);
            int posZ = blockZPos + random.nextInt(maxZ);
            new WorldGenMinable(blockId, maxVeinSize).generate(world, random, posX, posY, posZ);
        }
    }
}
