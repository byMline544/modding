package tcw.proxy;

import net.minecraftforge.common.MinecraftForge;
import tcw.events.EventHandler;
import tcw.events.ClientEquipmentEventHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        // Регистрация рендеров и GUI текстур.
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEquipmentEventHandler());
    }
}
