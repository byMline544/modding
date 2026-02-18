package techno;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class Util {

    private Util() {}

    public static TileEntity getAdjacent(World world, int x, int y, int z, int side) {
        switch (side) {
            case 0: return world.getBlockTileEntity(x, y - 1, z);
            case 1: return world.getBlockTileEntity(x, y + 1, z);
            case 2: return world.getBlockTileEntity(x, y, z - 1);
            case 3: return world.getBlockTileEntity(x, y, z + 1);
            case 4: return world.getBlockTileEntity(x - 1, y, z);
            case 5: return world.getBlockTileEntity(x + 1, y, z);
            default: return null;
        }
    }
}
