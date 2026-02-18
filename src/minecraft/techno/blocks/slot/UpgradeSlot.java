package techno.blocks.slot;

public class UpgradeSlot implements ISlots {
    public boolean isInput() { return false; }
    public boolean isOutput() { return false; }
    public boolean isUpgrade() { return true; }
}
