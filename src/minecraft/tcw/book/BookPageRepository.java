package tcw.book;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tcw.blocks.MachineTier;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class BookPageRepository {

    public static class Entry {
        public final String title;
        public final String text;
        public final ItemStack[] inputs;
        public final ItemStack output;

        public Entry(String title, String text, ItemStack[] inputs, ItemStack output) {
            this.title = title;
            this.text = text;
            this.inputs = inputs;
            this.output = output;
        }
    }

    public static Entry[] getEntries() {
        return new Entry[] {
                new Entry("Добро пожаловать", "TechnoCloud: промышленность и энергия.\n\nСоберите первую линию: руды -> измельчение -> переплавка -> машины.",
                        new ItemStack[] {new ItemStack(Item.book), new ItemStack(Item.paper)}, new ItemStack(ItemManager.techBook)),

                new Entry("Добыча руд", "Базовый цикл: медь, олово, никель.\nРуды спавнятся в обычном мире и перерабатываются в пыль.",
                        new ItemStack[] {new ItemStack(BlockManager.oreCopper), new ItemStack(BlockManager.oreTin), new ItemStack(BlockManager.oreNickel)},
                        new ItemStack(ItemManager.mixedDust)),

                new Entry("Измельчитель", "Измельчитель (бывш. дробилка) превращает руды и слитки в пыль.\n\nЧем стабильнее питание, тем быстрее поток ресурсов.",
                        new ItemStack[] {new ItemStack(BlockManager.machines[MachineTier.MACERATOR.ordinal()]), new ItemStack(BlockManager.oreCopper)},
                        new ItemStack(ItemManager.copperDust, 2)),

                new Entry("Компрессор", "Компрессор уплотняет материалы и участвует в крафтах продвинутых компонентов.",
                        new ItemStack[] {new ItemStack(BlockManager.machines[MachineTier.COMPRESSOR.ordinal()]), new ItemStack(ItemManager.carbonPlate)},
                        new ItemStack(ItemManager.advancedCircuit)),

                new Entry("Генератор", "Генератор теперь выдаёт энергию равномерно.\nТурбо: быстрее, ЭКО: экономнее.\n\nУголь расходуется плавно, без резких скачков энергии.",
                        new ItemStack[] {new ItemStack(BlockManager.machines[MachineTier.GENERATOR.ordinal()]), new ItemStack(Item.coal)},
                        new ItemStack(ItemManager.batteryBasic)),

                new Entry("Солнечные панели", "Солнечные панели генерируют энергию днём и отдают её в сеть через кабели.\nОткрой GUI панели, чтобы видеть накопление и генерацию.",
                        new ItemStack[] {new ItemStack(BlockManager.solarBasic), new ItemStack(Block.glass), new ItemStack(ItemManager.lens)},
                        new ItemStack(BlockManager.solarImproved)),

                new Entry("Кабели", "Кабели соединяют узлы сети.\nБазовый/Усиленный/Крио отличаются пропускной способностью и потерями.",
                        new ItemStack[] {new ItemStack(BlockManager.energyCable), new ItemStack(BlockManager.energyCableReinforced), new ItemStack(BlockManager.energyCableCryo)},
                        new ItemStack(ItemManager.cable, 4)),

                new Entry("Сборщик и Экстрактор", "Автологистика: IN/OUT стороны, фильтры и приоритет входов.\nИспользуйте кнопки GUI для маршрутизации цепочек.",
                        new ItemStack[] {new ItemStack(BlockManager.machines[MachineTier.EXTRACTOR.ordinal()]),
                                new ItemStack(BlockManager.machines[MachineTier.ASSEMBLER.ordinal()])},
                        new ItemStack(ItemManager.advancedCircuit)),

                new Entry("Печь сплавов", "Собирайте бронзу и другие композиты.\nСначала подготовьте пыль, затем плавьте и комбинируйте.",
                        new ItemStack[] {new ItemStack(BlockManager.machines[MachineTier.ALLOY_SMELTER.ordinal()]), new ItemStack(ItemManager.mixedDust)},
                        new ItemStack(ItemManager.bronzeIngot)),

                new Entry("Новые руды 0.2.2", "Теперь доступны серебро и уран.\nСеребро - для электроцепей, уран - для квантовых компонентов.",
                        new ItemStack[] {new ItemStack(BlockManager.oreSilver), new ItemStack(BlockManager.oreUranium)},
                        new ItemStack(ItemManager.uraniumIngot)),

                new Entry("Электро-броня", "Нано и Квантовый комплекты требуют заряд.\nИспользуйте Зарядник: он восстанавливает ресурс электро-предметов.",
                        new ItemStack[] {new ItemStack(ItemManager.nanoChestplate), new ItemStack(ItemManager.quantumChestplate),
                                new ItemStack(BlockManager.machines[MachineTier.CHARGER.ordinal()])},
                        new ItemStack(ItemManager.quantumCore)),

                new Entry("Электро-инструменты", "Нано/Квантовый меч и кирка - повышенная эффективность.\nРесурс инструмента = внутренний заряд, пополняется в Заряднике.",
                        new ItemStack[] {new ItemStack(ItemManager.nanoPickaxe), new ItemStack(ItemManager.quantumPickaxe)},
                        new ItemStack(ItemManager.energyMatrix)),

                new Entry("Компоненты 2.0", "Цепочка крафта: стальная пыль -> сталь -> нано-волокно -> энергоматрица -> квантовое ядро.",
                        new ItemStack[] {new ItemStack(ItemManager.steelDust), new ItemStack(ItemManager.nanoFiber),
                                new ItemStack(ItemManager.energyMatrix)},
                        new ItemStack(ItemManager.quantumCore)),

                new Entry("Цель прогресса", "Постройте стабильную сеть: генерация -> хранение -> переработка -> автоматизация.\n\nПереходите к продвинутым панелям и полной автоматике.",
                        new ItemStack[] {new ItemStack(BlockManager.solarAdvanced), new ItemStack(ItemManager.batteryAdvanced),
                                new ItemStack(BlockManager.machines[MachineTier.CHARGER.ordinal()])},
                        new ItemStack(BlockManager.solarUltimate))};
    }

    private BookPageRepository() {
    }
}
