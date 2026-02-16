package tcw.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMachineInfo extends ItemBlock {

    public ItemBlockMachineInfo(int id) {
        super(id);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        String key = Block.blocksList[this.blockID].getUnlocalizedName();
        if (key == null) {
            return;
        }

        // Краткая тех.карта машины в подсказке предмета.
        if (key.contains("crusher") || key.contains("macerator")) {
            list.add("Потребление: 20 EU/t");
            list.add("Скорость: 120 тиков/операция");
            list.add("Требует: подключение к энергосети");
        } else if (key.contains("compressor")) {
            list.add("Потребление: 28 EU/t");
            list.add("Скорость: 160 тиков/операция");
            list.add("Требует: подключение к энергосети");
        } else if (key.contains("electric_furnace")) {
            list.add("Потребление: 24 EU/t");
            list.add("Скорость: 140 тиков/операция");
            list.add("Требует: подключение к энергосети");
        } else if (key.contains("alloy_smelter")) {
            list.add("Потребление: 36 EU/t");
            list.add("Скорость: 180 тиков/операция");
            list.add("Требует: подключение к энергосети");
        } else if (key.contains("wiremill")) {
            list.add("Потребление: 16 EU/t");
            list.add("Скорость: 100 тиков/операция");
            list.add("Требует: подключение к энергосети");
        } else if (key.contains("extractor")) {
            list.add("Потребление: 26/40 EU/t");
            list.add("Режимы: обычный / turbo");
            list.add("Требует: подключение к энергосети");
        } else if (key.contains("assembler")) {
            list.add("Потребление: 30/48 EU/t");
            list.add("Режимы: обычный / precision");
            list.add("Требует: подключение к энергосети");
        } else if (key.contains("charger")) {
            list.add("Потребление: 500/900 EU/t");
            list.add("Режимы: normal / fast");
            list.add("Заряжает электрические предметы");
        } else if (key.contains("generator")) {
            list.add("Выработка: до 80 EU/t");
            list.add("Топливо: уголь и совместимое");
            list.add("Отдача: в соседние кабели/машины");
        } else if (key.contains("solar")) {
            list.add("Работает: только днём");
            list.add("Выход: зависит от уровня панели");
            list.add("Отдача: в соседнюю энергосеть");
        }
    }
}
