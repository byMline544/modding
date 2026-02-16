package tcw.achievements;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import cpw.mods.fml.common.registry.LanguageRegistry;
import tcw.blocks.MachineTier;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;

public class AchievementManager {

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
        addLoc("achievement.tcw.openBook", "ТехноCloud: Начало", "TechnoCloud: Start");
        addLoc("achievement.tcw.openBook.desc", "Открыть справочник TechnoCloud", "Open TechnoCloud guide book");
        addLoc("achievement.tcw.castBronze", "Первая бронза", "First bronze");
        addLoc("achievement.tcw.castBronze.desc", "Получить бронзовый слиток", "Obtain a bronze ingot");
        addLoc("achievement.tcw.firstMachine", "Машиностроение", "Machine building");
        addLoc("achievement.tcw.firstMachine.desc", "Собрать первую машину", "Craft your first machine");
        addLoc("achievement.tcw.electricAge", "Эра энергии", "Age of energy");
        addLoc("achievement.tcw.electricAge.desc", "Построить генератор", "Build a generator");
        addLoc("achievement.tcw.firstSolar", "Сила солнца", "Sun power");
        addLoc("achievement.tcw.firstSolar.desc", "Собрать солнечную панель", "Craft a solar panel");
        addLoc("achievement.tcw.brightFuture", "Яркое будущее", "Bright future");
        addLoc("achievement.tcw.brightFuture.desc", "Собрать ультимативную солнечную панель", "Craft ultimate solar panel");
        addLoc("achievement.tcw.oreHunter", "Рудокоп", "Ore hunter");
        addLoc("achievement.tcw.oreHunter.desc", "Добыть никелевую руду", "Mine nickel ore");
        addLoc("achievement.tcw.armored", "Бронзовый доспех", "Bronze armor");
        addLoc("achievement.tcw.armored.desc", "Скрафтить бронзовый нагрудник", "Craft bronze chestplate");
        addLoc("achievement.tcw.energyMaster", "Энергоинженер", "Energy engineer");
        addLoc("achievement.tcw.energyMaster.desc", "Создать улучшенную батарею", "Craft advanced battery");
        addLoc("achievement.tcw.cloudEngineer", "Инженер облаков", "Cloud engineer");
        addLoc("achievement.tcw.cloudEngineer.desc", "Собрать зарядник и завершить ветку развития", "Craft charger and finish base chain");

        addLoc("achievement.tcw.silverAge", "Серебряный век", "Silver age");
        addLoc("achievement.tcw.silverAge.desc", "Добыть серебряную руду", "Mine silver ore");
        addLoc("achievement.tcw.uraniumAge", "Урановый рубеж", "Uranium threshold");
        addLoc("achievement.tcw.uraniumAge.desc", "Добыть урановую руду", "Mine uranium ore");
        addLoc("achievement.tcw.steelMind", "Стальная логика", "Steel logic");
        addLoc("achievement.tcw.steelMind.desc", "Выплавить стальной слиток", "Smelt steel ingot");
        addLoc("achievement.tcw.nanoCrafter", "Нано-конструктор", "Nano crafter");
        addLoc("achievement.tcw.nanoCrafter.desc", "Создать нано-волокно", "Craft nano fiber");
        addLoc("achievement.tcw.matrixBuilder", "Матрица энергии", "Energy matrix");
        addLoc("achievement.tcw.matrixBuilder.desc", "Собрать энергоматрицу", "Craft energy matrix");
        addLoc("achievement.tcw.quantumLeap", "Квантовый скачок", "Quantum leap");
        addLoc("achievement.tcw.quantumLeap.desc", "Создать квантовое ядро", "Craft quantum core");
        addLoc("achievement.tcw.nanoWarrior", "Нано-воин", "Nano warrior");
        addLoc("achievement.tcw.nanoWarrior.desc", "Скрафтить нано-нагрудник", "Craft nano chestplate");
        addLoc("achievement.tcw.quantumWarrior", "Квантовый страж", "Quantum guardian");
        addLoc("achievement.tcw.quantumWarrior.desc", "Скрафтить квантовый нагрудник", "Craft quantum chestplate");
        addLoc("achievement.tcw.precisionAssembler", "Точная сборка", "Precision assembly");
        addLoc("achievement.tcw.precisionAssembler.desc", "Построить сборщик", "Build assembler");
        addLoc("achievement.tcw.cableArchitect", "Архитектор сети", "Grid architect");
        addLoc("achievement.tcw.cableArchitect.desc", "Создать крио-кабель", "Craft cryo cable");

        openBook = new Achievement(5000, "tcw.openBook", 0, 0, ItemManager.techBook, AchievementList.openInventory).registerAchievement();
        castBronze = new Achievement(5001, "tcw.castBronze", 2, 0, ItemManager.bronzeIngot, openBook).registerAchievement();
        firstMachine = new Achievement(5002, "tcw.firstMachine", 4, 0, BlockManager.machines[MachineTier.CRUSHER.ordinal()], castBronze).registerAchievement();
        electricAge = new Achievement(5003, "tcw.electricAge", 6, 0, BlockManager.machines[MachineTier.GENERATOR.ordinal()], firstMachine).registerAchievement();
        firstSolar = new Achievement(5004, "tcw.firstSolar", 8, 0, BlockManager.solarBasic, electricAge).registerAchievement();
        brightFuture = new Achievement(5005, "tcw.brightFuture", 10, 0, BlockManager.solarUltimate, firstSolar).registerAchievement();
        oreHunter = new Achievement(5006, "tcw.oreHunter", 2, 2, BlockManager.oreNickel, openBook).registerAchievement();
        armored = new Achievement(5007, "tcw.armored", 4, 2, ItemManager.bronzeChestplate, castBronze).registerAchievement();
        energyMaster = new Achievement(5008, "tcw.energyMaster", 8, 2, ItemManager.batteryAdvanced, firstMachine).registerAchievement();
        cloudEngineer = new Achievement(5009, "tcw.cloudEngineer", 10, 2, BlockManager.machines[MachineTier.CHARGER.ordinal()], brightFuture).setSpecial().registerAchievement();

        silverAge = new Achievement(5010, "tcw.silverAge", 1, 4, BlockManager.oreSilver, oreHunter).registerAchievement();
        uraniumAge = new Achievement(5011, "tcw.uraniumAge", 3, 4, BlockManager.oreUranium, silverAge).registerAchievement();
        steelMind = new Achievement(5012, "tcw.steelMind", 5, 4, ItemManager.steelIngot, uraniumAge).registerAchievement();
        nanoCrafter = new Achievement(5013, "tcw.nanoCrafter", 7, 4, ItemManager.nanoFiber, steelMind).registerAchievement();
        matrixBuilder = new Achievement(5014, "tcw.matrixBuilder", 9, 4, ItemManager.energyMatrix, nanoCrafter).registerAchievement();
        quantumLeap = new Achievement(5015, "tcw.quantumLeap", 11, 4, ItemManager.quantumCore, matrixBuilder).setSpecial().registerAchievement();
        nanoWarrior = new Achievement(5016, "tcw.nanoWarrior", 7, 6, ItemManager.nanoChestplate, nanoCrafter).registerAchievement();
        quantumWarrior = new Achievement(5017, "tcw.quantumWarrior", 11, 6, ItemManager.quantumChestplate, quantumLeap).setSpecial().registerAchievement();
        precisionAssembler = new Achievement(5018, "tcw.precisionAssembler", 5, 6, BlockManager.machines[MachineTier.ASSEMBLER.ordinal()], firstMachine).registerAchievement();
        cableArchitect = new Achievement(5019, "tcw.cableArchitect", 9, 6, BlockManager.energyCableCryo, precisionAssembler).registerAchievement();

        AchievementPage.registerAchievementPage(new AchievementPage("TechnoCloud",
                openBook, castBronze, firstMachine, electricAge, firstSolar, brightFuture, oreHunter, armored, energyMaster, cloudEngineer,
                silverAge, uraniumAge, steelMind, nanoCrafter, matrixBuilder, quantumLeap, nanoWarrior, quantumWarrior, precisionAssembler,
                cableArchitect));
    }

    private static void addLoc(String key, String ru, String en) {
        LanguageRegistry.instance().addStringLocalization(key, "ru_RU", ru);
        LanguageRegistry.instance().addStringLocalization(key, "en_US", en);
    }
}
