package tcw.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import tcw.tiles.TileEntityAssembler;
import tcw.tiles.TileEntityCharger;
import tcw.tiles.TileEntityExtractor;
import tcw.tiles.TileEntityGenerator;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        try {
            DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
            int discriminator = data.readInt();
            int x = data.readInt();
            int y = data.readInt();
            int z = data.readInt();

            EntityPlayer entityPlayer = (EntityPlayer) player;
            TileEntity tile = entityPlayer.worldObj.getBlockTileEntity(x, y, z);

            if (discriminator == PacketIds.TOGGLE_GENERATOR_MODE && tile instanceof TileEntityGenerator) {
                ((TileEntityGenerator) tile).toggleEcoMode();
            }
            if (discriminator == PacketIds.TOGGLE_CHARGER_MODE && tile instanceof TileEntityCharger) {
                ((TileEntityCharger) tile).toggleFastMode();
            }
            if (discriminator == PacketIds.TOGGLE_EXTRACTOR_MODE && tile instanceof TileEntityExtractor) {
                ((TileEntityExtractor) tile).toggleOverclockMode();
            }
            if (discriminator == PacketIds.TOGGLE_ASSEMBLER_MODE && tile instanceof TileEntityAssembler) {
                ((TileEntityAssembler) tile).togglePrecisionMode();
            }
            if (discriminator == PacketIds.TOGGLE_EXTRACTOR_AUTO_INPUT && tile instanceof TileEntityExtractor) {
                ((TileEntityExtractor) tile).toggleAutoInput();
            }
            if (discriminator == PacketIds.TOGGLE_EXTRACTOR_AUTO_OUTPUT && tile instanceof TileEntityExtractor) {
                ((TileEntityExtractor) tile).toggleAutoOutput();
            }
            if (discriminator == PacketIds.TOGGLE_ASSEMBLER_AUTO_INPUT && tile instanceof TileEntityAssembler) {
                ((TileEntityAssembler) tile).toggleAutoInput();
            }
            if (discriminator == PacketIds.TOGGLE_ASSEMBLER_AUTO_OUTPUT && tile instanceof TileEntityAssembler) {
                ((TileEntityAssembler) tile).toggleAutoOutput();
            }
        } catch (Exception ignored) {
            // Не валим сервер на кривом пакете.
        }
    }
}
