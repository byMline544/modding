package tcw.tiles;

public class TileEntitySolarPanel extends TileEntityMachine {

    private final int generation;

    public TileEntitySolarPanel(int capacity, int generation) {
        super(capacity);
        this.generation = generation;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote && worldObj.isDaytime() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
            storage.receiveEnergy(generation, false);
        }
    }
}
