package tcw.blocks;

public enum MachineTier {
    CRUSHER("crusher", "Дробитель"),
    COMPRESSOR("compressor", "Компрессор"),
    GENERATOR("generator", "Генератор"),
    FURNACE_ELECTRIC("electric_furnace", "Электропечь"),
    ALLOY_SMELTER("alloy_smelter", "Печь сплавов"),
    EXTRACTOR("extractor", "Экстрактор"),
    MACERATOR("macerator", "Измельчитель"),
    WIREMILL("wiremill", "Проволочный стан"),
    ASSEMBLER("assembler", "Сборщик"),
    CHARGER("charger", "Зарядник");

    public final String key;
    public final String ruName;

    MachineTier(String key, String ruName) {
        this.key = key;
        this.ruName = ruName;
    }
}
