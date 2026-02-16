package tcw.achievements;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import cpw.mods.fml.common.registry.LanguageRegistry;
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

    public static void registerAchievements() {

        LanguageRegistry.instance().addStringLocalization("achievement.tcw.openBook", "ru_RU", "ТехноCloud: Начало");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.openBook.desc", "ru_RU", "Открыть справочник TechnoCloud");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.castBronze", "ru_RU", "Первая бронза");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.castBronze.desc", "ru_RU", "Получить бронзовый слиток");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.firstMachine", "ru_RU", "Машиностроение");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.firstMachine.desc", "ru_RU", "Собрать первую машину");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.electricAge", "ru_RU", "Эра энергии");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.electricAge.desc", "ru_RU", "Построить генератор");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.firstSolar", "ru_RU", "Сила солнца");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.firstSolar.desc", "ru_RU", "Собрать солнечную панель");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.brightFuture", "ru_RU", "Яркое будущее");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.brightFuture.desc", "ru_RU", "Собрать ультимативную солнечную панель");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.oreHunter", "ru_RU", "Рудокоп");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.oreHunter.desc", "ru_RU", "Добыть никелевую руду");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.armored", "ru_RU", "Бронзовый доспех");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.armored.desc", "ru_RU", "Скрафтить бронзовый нагрудник");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.energyMaster", "ru_RU", "Энергоинженер");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.energyMaster.desc", "ru_RU", "Создать улучшенную батарею");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.cloudEngineer", "ru_RU", "Инженер облаков");
        LanguageRegistry.instance().addStringLocalization("achievement.tcw.cloudEngineer.desc", "ru_RU", "Собрать зарядник и завершить ветку развития");
        openBook = new Achievement(5000, "tcw.openBook", 0, 0, ItemManager.techBook, AchievementList.openInventory).registerAchievement();
        castBronze = new Achievement(5001, "tcw.castBronze", 2, 0, ItemManager.bronzeIngot, openBook).registerAchievement();
        firstMachine = new Achievement(5002, "tcw.firstMachine", 4, 0, BlockManager.machines[0], castBronze).registerAchievement();
        electricAge = new Achievement(5003, "tcw.electricAge", 6, 0, BlockManager.machines[2], firstMachine).registerAchievement();
        firstSolar = new Achievement(5004, "tcw.firstSolar", 8, 0, BlockManager.solarBasic, electricAge).registerAchievement();
        brightFuture = new Achievement(5005, "tcw.brightFuture", 10, 0, BlockManager.solarUltimate, firstSolar).registerAchievement();
        oreHunter = new Achievement(5006, "tcw.oreHunter", 2, 2, BlockManager.oreNickel, openBook).registerAchievement();
        armored = new Achievement(5007, "tcw.armored", 4, 2, ItemManager.bronzeChestplate, castBronze).registerAchievement();
        energyMaster = new Achievement(5008, "tcw.energyMaster", 8, 2, ItemManager.batteryAdvanced, firstMachine).registerAchievement();
        cloudEngineer = new Achievement(5009, "tcw.cloudEngineer", 10, 2, BlockManager.machines[9], brightFuture).setSpecial().registerAchievement();

        AchievementPage.registerAchievementPage(new AchievementPage("TechnoCloud", openBook, castBronze, firstMachine, electricAge,
                firstSolar, brightFuture, oreHunter, armored, energyMaster, cloudEngineer));
    }
}
