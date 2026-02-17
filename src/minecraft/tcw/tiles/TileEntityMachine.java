package tcw.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tcw.energy.EnergyStorageTCW;

public class TileEntityMachine extends TileEntity {

    protected EnergyStorageTCW storage;
    protected int baseCapacity;

    private int overclockerModules;
    private int transformerModules;
    private int capacityModules;

    public TileEntityMachine() {
        this(32000);
    }

    public TileEntityMachine(int capacity) {
        this.baseCapacity = capacity;
        storage = new EnergyStorageTCW(capacity);
    }

    public EnergyStorageTCW getStorage() {
        return storage;
    }

    public int getOverclockerModules() {
        return overclockerModules;
    }

    public int getTransformerModules() {
        return transformerModules;
    }

    public int getCapacityModules() {
        return capacityModules;
    }

    public int getSafeInputPerTick() {
        return 128 + getTransformerModules() * 128;
    }

    public int getDesiredReceivePerTick() {
        return 32 + getOverclockerModules() * 24;
    }

    public boolean installModule(int moduleType) {
        if (moduleType == 0 && overclockerModules < 16) {
            overclockerModules++;
            return true;
        }
        if (moduleType == 1 && transformerModules < 16) {
            transformerModules++;
            return true;
        }
        if (moduleType == 2 && capacityModules < 16) {
            capacityModules++;
            updateStorageCapacity();
            return true;
        }
        return false;
    }

    public void onOvervoltage(int incoming) {
        if (worldObj == null || worldObj.isRemote) {
            return;
        }
        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        worldObj.createExplosion(null, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 1.0F, false);
    }

    protected void updateStorageCapacity() {
        int cap = baseCapacity + getCapacityModules() * 50000;
        int energy = storage.getEnergyStored();
        storage = new EnergyStorageTCW(cap);
        storage.setEnergy(energy);
    }

    public static void resetNearbyMachineEnergy(net.minecraft.world.World world, int x, int y, int z, int radius) {
        if (world == null || world.isRemote) {
            return;
        }

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    net.minecraft.tileentity.TileEntity te = world.getBlockTileEntity(x + dx, y + dy, z + dz);
                    if (te instanceof TileEntityMachine) {
                        TileEntityMachine machine = (TileEntityMachine) te;
                        if (machine.storage.getEnergyStored() > 0) {
                            machine.storage.setEnergy(0);
                            world.markBlockForUpdate(x + dx, y + dy, z + dz);
                        }
                    }
                }
            }
        }
    }


    public boolean isConnectedToEnergyNet() {
        if (worldObj == null) {
            return false;
        }
        int[][] o = new int[][] { {1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}, {0,0,1}, {0,0,-1} };
        for (int i = 0; i < o.length; i++) {
            net.minecraft.tileentity.TileEntity tile = worldObj.getBlockTileEntity(xCoord + o[i][0], yCoord + o[i][1], zCoord + o[i][2]);
            if (tile instanceof TileEntityMachine || tile instanceof TileEntityCable) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateEntity() {
        // Базовые машины сами не раздают энергию.
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        baseCapacity = nbt.getInteger("BaseCapacity") > 0 ? nbt.getInteger("BaseCapacity") : baseCapacity;
        overclockerModules = nbt.getInteger("ModOver");
        transformerModules = nbt.getInteger("ModTr");
        capacityModules = nbt.getInteger("ModCap");
        updateStorageCapacity();
        storage.setEnergy(nbt.getInteger("Energy"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Energy", storage.getEnergyStored());
        nbt.setInteger("BaseCapacity", baseCapacity);
        nbt.setInteger("ModOver", overclockerModules);
        nbt.setInteger("ModTr", transformerModules);
        nbt.setInteger("ModCap", capacityModules);
    }
}
