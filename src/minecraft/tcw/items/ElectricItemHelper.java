package tcw.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ElectricItemHelper {

    private ElectricItemHelper() {
    }

    public static int getEnergy(ItemStack stack) {
        if (stack == null || stack.stackTagCompound == null) {
            return 0;
        }
        return stack.stackTagCompound.getInteger("Energy");
    }

    public static int getMaxEnergy(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof IElectricItemTCW)) {
            return 0;
        }
        return ((IElectricItemTCW) stack.getItem()).getMaxEnergy(stack);
    }

    public static void setEnergy(ItemStack stack, int energy) {
        if (stack == null) {
            return;
        }
        int max = getMaxEnergy(stack);
        if (max <= 0) {
            return;
        }
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        stack.stackTagCompound.setInteger("Energy", Math.max(0, Math.min(max, energy)));
    }

    public static int addEnergy(ItemStack stack, int amount) {
        int before = getEnergy(stack);
        setEnergy(stack, before + amount);
        return getEnergy(stack) - before;
    }
}
