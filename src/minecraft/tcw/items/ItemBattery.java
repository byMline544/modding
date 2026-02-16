package tcw.items;

public class ItemBattery extends TCWItem {

    private final int maxEnergy;

    public ItemBattery(int id, String name, int maxEnergy) {
        super(id, name);
        this.maxEnergy = maxEnergy;
        setMaxStackSize(1);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
}
