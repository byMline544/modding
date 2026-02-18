package tcw;

import tcw.achievements.AchievementManager;
import tcw.crafting.RecipeManager;
import tcw.gui.GuiHandler;
import tcw.managers.BlockManager;
import tcw.managers.ItemManager;
import tcw.managers.TileManager;
import tcw.managers.WorldManager;
import tcw.packets.PacketHandler;
import tcw.proxy.CommonProxy;
import tcw.events.PlayerCombatEventHandler;
import tcw.events.PlayerEquipmentEventHandler;
import tcw.events.AchievementEventHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "technocloud", name = "TechnoCloud", version = "0.2.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class, channels = { "TCW_MAIN" })
public class MainLoader {

    public static final String MOD_ID = "technocloud";
    public static final String MOD_NAME = "TechnoCloud";
    public static final String VERSION = "0.2.1";

    @Instance(MOD_ID)
    public static MainLoader instance;

    @SidedProxy(clientSide = "tcw.proxy.ClientProxy", serverSide = "tcw.proxy.CommonProxy")
    public static CommonProxy proxy;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud", "ru_RU", "TechnoCloud");
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud", "en_US", "TechnoCloud");
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud.machines", "ru_RU", "TC> Механизмы, блоки, руды");
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud.machines", "en_US", "TC> Machines, blocks, ores");
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud.equipment", "ru_RU", "TC> Оружие, инструменты, броня");
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud.equipment", "en_US", "TC> Weapons, tools, armor");
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud.components", "ru_RU", "TC> Компоненты и предметы");
        LanguageRegistry.instance().addStringLocalization("itemGroup.technocloud.components", "en_US", "TC> Components and items");
        ItemManager.initItems();
        BlockManager.initBlocks();
        WorldManager.registerWorldGen();
        TileManager.registerTiles();
        proxy.registerRenderers();
    }

    @Init
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        RecipeManager.registerRecipes();
        AchievementManager.registerAchievements();
        MinecraftForge.EVENT_BUS.register(new PlayerCombatEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEquipmentEventHandler());
        MinecraftForge.EVENT_BUS.register(new AchievementEventHandler());
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        // Хуки совместимости с другими модами будут тут.
    }
}
