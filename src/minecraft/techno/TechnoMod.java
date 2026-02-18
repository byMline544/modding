package techno;

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
import techno.event.AchievementEventHandler;
import techno.event.ClientEquipmentEventHandler;
import techno.event.EventHandler;
import techno.event.PlayerCombatEventHandler;
import techno.event.PlayerEquipmentEventHandler;
import techno.managers.AchievementManager;
import techno.managers.BlockManager;
import techno.managers.GuiContainerManager;
import techno.managers.ItemManager;
import techno.managers.RecipeManager;
import techno.managers.TileManager;
import techno.managers.WorldManager;
import techno.packets.PacketHandler;
import techno.proxy.ServerProxy;

@Mod(modid = "newindustrialtechno", name = "New Industrial Techno", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class, channels = { "NIT_MAIN" })
public class TechnoMod {

    @Instance("newindustrialtechno")
    public static TechnoMod instance;

    @SidedProxy(clientSide = "techno.proxy.ClientProxy", serverSide = "techno.proxy.ServerProxy")
    public static ServerProxy proxy;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        ItemManager.init();
        BlockManager.init();
        TileManager.init();
        proxy.registerRenderers();
    }

    @Init
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.instance().registerGuiHandler(this, new GuiContainerManager());
        RecipeManager.init();
        WorldManager.init();
        AchievementManager.init();

        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerCombatEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEquipmentEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEquipmentEventHandler());
        MinecraftForge.EVENT_BUS.register(new AchievementEventHandler());
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    }
}
