package techno.packets;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    public static final Map<Integer, Class<?>> PACKETS = new HashMap<Integer, Class<?>>();
    static {
        PACKETS.put(Integer.valueOf(1), PacketHandler.class);
    }
}
