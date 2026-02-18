package tcw.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemMachineModule extends TCWItem {

    public static final int TYPE_OVERCLOCKER = 0;
    public static final int TYPE_TRANSFORMER = 1;
    public static final int TYPE_CAPACITY = 2;

    private final int moduleType;

    public ItemMachineModule(int id, String textureKey, int moduleType) {
        super(id, textureKey);
        this.moduleType = moduleType;
        setMaxStackSize(16);
    }

    public int getModuleType() {
        return moduleType;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (moduleType == TYPE_OVERCLOCKER) {
            list.add("Модуль: ускоритель");
            list.add("Ускоряет прогресс, увеличивает расход энергии");
        } else if (moduleType == TYPE_TRANSFORMER) {
            list.add("Модуль: трансформатор");
            list.add("Повышает предел входящей мощности");
        } else {
            list.add("Модуль: ёмкость");
            list.add("Увеличивает внутренний буфер энергии");
        }
        list.add("Лимит установки: x8 на механизм");
    }
}
