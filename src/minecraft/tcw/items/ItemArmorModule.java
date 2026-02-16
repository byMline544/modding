package tcw.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemArmorModule extends TCWItem {

    public static final int TYPE_NIGHT_VISION = 0;
    public static final int TYPE_FAST_RUN = 1;

    private final int moduleType;

    public ItemArmorModule(int id, String textureKey, int moduleType) {
        super(id, textureKey);
        this.moduleType = moduleType;
        setMaxStackSize(1);
    }

    public int getModuleType() {
        return moduleType;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (moduleType == TYPE_NIGHT_VISION) {
            list.add("Модуль брони: Ночное зрение");
            list.add("Устанавливается в шлем");
        } else if (moduleType == TYPE_FAST_RUN) {
            list.add("Модуль брони: Быстрый бег");
            list.add("Устанавливается в поножи");
        }
    }
}
