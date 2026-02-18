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

        if (key.contains("machine_casing")) {
            list.add("Базовый корпус для сборки машин");
            return;
        }

        if (key.contains("crusher") || key.contains("macerator")) {
            list.add("20 EU/t • 120 тиков");
        } else if (key.contains("compressor")) {
            list.add("28 EU/t • 160 тиков");
        } else if (key.contains("electric_furnace")) {
            list.add("24 EU/t • 140 тиков");
        } else if (key.contains("alloy_smelter")) {
            list.add("36 EU/t • 180 тиков");
        } else if (key.contains("wiremill")) {
            list.add("16 EU/t • 100 тиков");
        } else if (key.contains("extractor")) {
            list.add("44 EU/t • режим МАКС");
        } else if (key.contains("assembler")) {
            list.add("48 EU/t • режим МАКС");
        } else if (key.contains("charger")) {
            list.add("900 EU/t • быстрый заряд");
        } else if (key.contains("generator")) {
            list.add("Генерация с усилением от модулей");
        } else if (key.contains("batbox")) {
            list.add("БатБокс: малый буфер для стабилизации сети");
            list.add("Принимает/отдаёт низкое напряжение");
        } else if (key.contains("mfe")) {
            list.add("МФЭ: средний буфер для цеха");
            list.add("Выравнивает питание нескольких машин");
        } else if (key.contains("mfsu")) {
            list.add("МФСУ: крупный энергонакопитель");
            list.add("Хранит запас для пиковых нагрузок");
        } else if (key.contains("solar")) {
            list.add("Генерация днём • зависит от уровня");
        }

        if (key.contains("crusher") || key.contains("compressor") || key.contains("electric_furnace") || key.contains("alloy_smelter")
                || key.contains("wiremill") || key.contains("extractor") || key.contains("assembler") || key.contains("charger")) {
            list.add("Работает только при подключении к сети");
        }
    }
}
