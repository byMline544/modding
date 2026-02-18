package tcw.achievements;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.AchievementPage;
import cpw.mods.fml.common.registry.LanguageRegistry;
import tcw.blocks.MachineTier;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class AchievementManager {

    private static boolean registered;

    public static Achievement openBook;
    public static Achievement castBronze;
    public static Achievement firstMachine;
    public static Achievement electricAge;
    public static Achievement firstSolar;
    public static Achievement brightFuture;
    public static Achievement oreHunter;
    public static Achievement armored;
    public static Achievement energyMaster;
    public static Achievement cloudEngineer;

    public static Achievement silverAge;
    public static Achievement uraniumAge;
    public static Achievement steelMind;
    public static Achievement nanoCrafter;
    public static Achievement matrixBuilder;
    public static Achievement quantumLeap;
    public static Achievement nanoWarrior;
    public static Achievement quantumWarrior;
    public static Achievement precisionAssembler;
    public static Achievement cableArchitect;

    public static void registerAchievements() {
        if (registered) {
            return;
        }
        registered = true;
        addLoc("achievement.tcw.openBook", "ТехноCloud: Начало", "ТехноCloud: Начало");
        addLoc("achievement.tcw.openBook.desc", "Открыть справочник TechnoCloud", "Открыть справочник TechnoCloud");
        addLoc("achievement.tcw.castBronze", "Первая бронза", "Первая бронза");
        addLoc("achievement.tcw.castBronze.desc", "Получить бронзовый слиток", "Получить бронзовый слиток");
        addLoc("achievement.tcw.firstMachine", "Машиностроение", "Машиностроение");
        addLoc("achievement.tcw.firstMachine.desc", "Собрать первую машину", "Собрать первую машину");
        addLoc("achievement.tcw.electricAge", "Эра энергии", "Эра энергии");
        addLoc("achievement.tcw.electricAge.desc", "Построить генератор", "Построить генератор");
        addLoc("achievement.tcw.firstSolar", "Сила солнца", "Сила солнца");
        addLoc("achievement.tcw.firstSolar.desc", "Собрать солнечную панель", "Собрать солнечную панель");
        addLoc("achievement.tcw.brightFuture", "Яркое будущее", "Яркое будущее");
        addLoc("achievement.tcw.brightFuture.desc", "Собрать ультимативную солнечную панель", "Собрать ультимативную солнечную панель");
        addLoc("achievement.tcw.oreHunter", "Рудокоп", "Рудокоп");
        addLoc("achievement.tcw.oreHunter.desc", "Добыть никелевую руду", "Добыть никелевую руду");
        addLoc("achievement.tcw.armored", "Бронзовый доспех", "Бронзовый доспех");
        addLoc("achievement.tcw.armored.desc", "Скрафтить бронзовый нагрудник", "Скрафтить бронзовый нагрудник");
        addLoc("achievement.tcw.energyMaster", "Энергоинженер", "Энергоинженер");
        addLoc("achievement.tcw.energyMaster.desc", "Создать улучшенную батарею", "Создать улучшенную батарею");
        addLoc("achievement.tcw.cloudEngineer", "Инженер облаков", "Инженер облаков");
        addLoc("achievement.tcw.cloudEngineer.desc", "Собрать зарядник и завершить ветку развития", "Собрать зарядник и завершить ветку развития");

        addLoc("achievement.tcw.silverAge", "Серебряный век", "Серебряный век");
        addLoc("achievement.tcw.silverAge.desc", "Добыть серебряную руду", "Добыть серебряную руду");
        addLoc("achievement.tcw.uraniumAge", "Урановый рубеж", "Урановый рубеж");
        addLoc("achievement.tcw.uraniumAge.desc", "Добыть урановую руду", "Добыть урановую руду");
        addLoc("achievement.tcw.steelMind", "Стальная логика", "Стальная логика");
        addLoc("achievement.tcw.steelMind.desc", "Выплавить стальной слиток", "Выплавить стальной слиток");
        addLoc("achievement.tcw.nanoCrafter", "Нано-конструктор", "Нано-конструктор");
        addLoc("achievement.tcw.nanoCrafter.desc", "Создать нано-волокно", "Создать нано-волокно");
        addLoc("achievement.tcw.matrixBuilder", "Матрица энергии", "Матрица энергии");
        addLoc("achievement.tcw.matrixBuilder.desc", "Собрать энергоматрицу", "Собрать энергоматрицу");
        addLoc("achievement.tcw.quantumLeap", "Квантовый скачок", "Квантовый скачок");
        addLoc("achievement.tcw.quantumLeap.desc", "Создать квантовое ядро", "Создать квантовое ядро");
        addLoc("achievement.tcw.nanoWarrior", "Нано-воин", "Нано-воин");
        addLoc("achievement.tcw.nanoWarrior.desc", "Скрафтить нано-нагрудник", "Скрафтить нано-нагрудник");
        addLoc("achievement.tcw.quantumWarrior", "Квантовый страж", "Квантовый страж");
        addLoc("achievement.tcw.quantumWarrior.desc", "Скрафтить квантовый нагрудник", "Скрафтить квантовый нагрудник");
        addLoc("achievement.tcw.precisionAssembler", "Точная сборка", "Точная сборка");
        addLoc("achievement.tcw.precisionAssembler.desc", "Построить сборщик", "Построить сборщик");
        addLoc("achievement.tcw.cableArchitect", "Архитектор сети", "Архитектор сети");
        addLoc("achievement.tcw.cableArchitect.desc", "Создать крио-кабель", "Создать крио-кабель");

        int id = nextFreeAchievementId();
        openBook = new Achievement(id++, "tcw.openBook", 0, 0, ItemManager.techBook, AchievementList.openInventory).registerAchievement();
        castBronze = new Achievement(id++, "tcw.castBronze", 2, 0, ItemManager.bronzeIngot, openBook).registerAchievement();
        firstMachine = new Achievement(id++, "tcw.firstMachine", 4, 0, BlockManager.machines[MachineTier.CRUSHER.ordinal()], castBronze).registerAchievement();
        electricAge = new Achievement(id++, "tcw.electricAge", 6, 0, BlockManager.machines[MachineTier.GENERATOR.ordinal()], firstMachine).registerAchievement();
        firstSolar = new Achievement(id++, "tcw.firstSolar", 8, 0, BlockManager.solarBasic, electricAge).registerAchievement();
        brightFuture = new Achievement(id++, "tcw.brightFuture", 10, 0, BlockManager.solarUltimate, firstSolar).registerAchievement();
        oreHunter = new Achievement(id++, "tcw.oreHunter", 2, 2, BlockManager.oreNickel, openBook).registerAchievement();
        armored = new Achievement(id++, "tcw.armored", 4, 2, ItemManager.bronzeChestplate, castBronze).registerAchievement();
        energyMaster = new Achievement(id++, "tcw.energyMaster", 8, 2, ItemManager.batteryAdvanced, firstMachine).registerAchievement();
        cloudEngineer = new Achievement(id++, "tcw.cloudEngineer", 10, 2, BlockManager.machines[MachineTier.CHARGER.ordinal()], brightFuture).setSpecial().registerAchievement();

        silverAge = new Achievement(id++, "tcw.silverAge", 1, 4, BlockManager.oreSilver, oreHunter).registerAchievement();
        uraniumAge = new Achievement(id++, "tcw.uraniumAge", 3, 4, BlockManager.oreUranium, silverAge).registerAchievement();
        steelMind = new Achievement(id++, "tcw.steelMind", 5, 4, ItemManager.steelIngot, uraniumAge).registerAchievement();
        nanoCrafter = new Achievement(id++, "tcw.nanoCrafter", 7, 4, ItemManager.nanoFiber, steelMind).registerAchievement();
        matrixBuilder = new Achievement(id++, "tcw.matrixBuilder", 9, 4, ItemManager.energyMatrix, nanoCrafter).registerAchievement();
        quantumLeap = new Achievement(id++, "tcw.quantumLeap", 11, 4, ItemManager.quantumCore, matrixBuilder).setSpecial().registerAchievement();
        nanoWarrior = new Achievement(id++, "tcw.nanoWarrior", 7, 6, ItemManager.nanoChestplate, nanoCrafter).registerAchievement();
        quantumWarrior = new Achievement(id++, "tcw.quantumWarrior", 11, 6, ItemManager.quantumChestplate, quantumLeap).setSpecial().registerAchievement();
        precisionAssembler = new Achievement(id++, "tcw.precisionAssembler", 5, 6, BlockManager.machines[MachineTier.ASSEMBLER.ordinal()], firstMachine).registerAchievement();
        cableArchitect = new Achievement(id++, "tcw.cableArchitect", 9, 6, BlockManager.energyCableCryo, precisionAssembler).registerAchievement();

        if (AchievementPage.getAchievementPage("TechnoCloud") == null) {
            AchievementPage.registerAchievementPage(new AchievementPage("TechnoCloud",
                    openBook, castBronze, firstMachine, electricAge, firstSolar, brightFuture, oreHunter, armored, energyMaster, cloudEngineer,
                    silverAge, uraniumAge, steelMind, nanoCrafter, matrixBuilder, quantumLeap, nanoWarrior, quantumWarrior, precisionAssembler,
                    cableArchitect));
        }
    }

    private static int nextFreeAchievementId() {
        int id = 12000;
        while (isAchievementIdBusy(id)) {
            id++;
        }
        return id;
    }

    private static boolean isAchievementIdBusy(int id) {
        int statId = 5242880 + id;
        for (int i = 0; i < StatList.allStats.size(); i++) {
            StatBase stat = (StatBase) StatList.allStats.get(i);
            if (stat != null && stat.statId == statId) {
                return true;
            }
        }
        return false;
    }

    private static void addLoc(String key, String ru, String en) {
        LanguageRegistry.instance().addStringLocalization(key, "ru_RU", ru);
        LanguageRegistry.instance().addStringLocalization(key, "en_US", en);
    }
}
