package techno.blocks.slot;

public class InputSlot implements ISlots {
    public boolean isInput() { return true; }
    public boolean isOutput() { return false; }
    public boolean isUpgrade() { return false; }
}
