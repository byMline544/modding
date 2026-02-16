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
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "TechnoCloud", name = "TechnoCloud", version = "0.2.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class, channels = { "TCW_MAIN" })
public class MainLoader {

    public static final String MOD_ID = "TechnoCloud";
    public static final String MOD_NAME = "TechnoCloud";
    public static final String VERSION = "0.2.0";

    @Instance(MOD_ID)
    public static MainLoader instance;

    @SidedProxy(clientSide = "tcw.proxy.ClientProxy", serverSide = "tcw.proxy.CommonProxy")
    public static CommonProxy proxy;

    @PreInit
    public void preInit() {
        ItemManager.initItems();
        BlockManager.initBlocks();
        WorldManager.registerWorldGen();
        TileManager.registerTiles();
        proxy.registerRenderers();
    }

    @Init
    public void init() {
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        RecipeManager.registerRecipes();
        AchievementManager.registerAchievements();
    }

    @PostInit
    public void postInit() {
        // Хуки совместимости с другими модами будут тут.
    }
}
