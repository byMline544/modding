package techno.blocks.slot;

public class OutputSlot implements ISlots {
    public boolean isInput() { return false; }
    public boolean isOutput() { return true; }
    public boolean isUpgrade() { return false; }
}
