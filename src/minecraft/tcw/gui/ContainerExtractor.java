package tcw.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tcw.tiles.TileEntityExtractor;

public class ContainerExtractor extends Container {

    private final TileEntityExtractor machine;
    private int lastProgress;
    private int lastEnergy;
    private int lastMode;
    private int lastAutoInput;
    private int lastAutoOutput;
    private int lastInputSide;
    private int lastOutputSide;
    private int lastFilterMode;

    public ContainerExtractor(InventoryPlayer playerInventory, TileEntityExtractor machine) {
        this.machine = machine;
        addSlotToContainer(new Slot(machine, 0, 56, 35));
        addSlotToContainer(new Slot(machine, 1, 116, 35));
        for (int i = 0; i < machine.getModuleSlotCount(); i++) {
            addSlotToContainer(new SlotMachineModule(machine, machine.getModuleSlotStart() + i, 152, 8 + i * 16));
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int energyScaled = machine.getStorage().getEnergyStored() * 10000 / machine.getStorage().getMaxEnergyStored();
        int mode = machine.isOverclockMode() ? 1 : 0;
        int autoInput = machine.isAutoInput() ? 1 : 0;
        int autoOutput = machine.isAutoOutput() ? 1 : 0;
        int inputSide = machine.getInputSide();
        int outputSide = machine.getOutputSide();
        int filterMode = machine.getFilterMode();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting c = (ICrafting) this.crafters.get(i);
            if (lastProgress != machine.getProgress()) c.sendProgressBarUpdate(this, 0, machine.getProgress());
            if (lastEnergy != energyScaled) c.sendProgressBarUpdate(this, 1, energyScaled);
            if (lastMode != mode) c.sendProgressBarUpdate(this, 2, mode);
            if (lastAutoInput != autoInput) c.sendProgressBarUpdate(this, 3, autoInput);
            if (lastAutoOutput != autoOutput) c.sendProgressBarUpdate(this, 4, autoOutput);
            if (lastInputSide != inputSide) c.sendProgressBarUpdate(this, 5, inputSide);
            if (lastOutputSide != outputSide) c.sendProgressBarUpdate(this, 6, outputSide);
            if (lastFilterMode != filterMode) c.sendProgressBarUpdate(this, 7, filterMode);
        }
        lastProgress = machine.getProgress();
        lastEnergy = energyScaled;
        lastMode = mode;
        lastAutoInput = autoInput;
        lastAutoOutput = autoOutput;
        lastInputSide = inputSide;
        lastOutputSide = outputSide;
        lastFilterMode = filterMode;
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) machine.setClientProgress(value);
        else if (id == 1) machine.setClientEnergyScaled(value);
        else if (id == 2) machine.setClientOverclockMode(value);
        else if (id == 3) machine.setClientAutoInput(value);
        else if (id == 4) machine.setClientAutoOutput(value);
        else if (id == 5) machine.setClientInputSide(value);
        else if (id == 6) machine.setClientOutputSide(value);
        else if (id == 7) machine.setClientFilterMode(value);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) { return machine.isUseableByPlayer(player); }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack ret = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack in = slot.getStack();
            ret = in.copy();
            if (index < 2 + machine.getModuleSlotCount()) {
                if (!mergeItemStack(in, 2, inventorySlots.size(), true)) return null;
            } else if (!mergeItemStack(in, 0, 1, false)) return null;
            if (in.stackSize <= 0) slot.putStack((ItemStack) null); else slot.onSlotChanged();
        }
        return ret;
    }
}
