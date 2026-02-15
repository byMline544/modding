package tcw.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketDispatcherTCW {

    public static Packet250CustomPayload makeTogglePacket(int discriminator, int x, int y, int z) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(20);
            DataOutputStream out = new DataOutputStream(bos);
            out.writeInt(discriminator);
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(z);

            Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = "TCW_MAIN";
            packet.data = bos.toByteArray();
            packet.length = packet.data.length;
            packet.isChunkDataPacket = false;
            return packet;
        } catch (Exception e) {
            return null;
        }
    }
}
