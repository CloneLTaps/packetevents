package io.github.retrooper.packetevents.utils.server;

import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;;

public class PacketCreator {
    public static PacketDataSerializer createInUseEntity(final int entityId, final WrappedPacketInUseEntity.EntityUseAction action) {
        final ByteBuf buf = Unpooled.buffer();

        writeVarInt(buf, 0);  // No compression, uncompressed packet
        buf.writeByte(0x02);  // Packet ID for UseEntity in 1.8
        writeVarInt(buf, entityId);
        writeVarInt(buf, action == WrappedPacketInUseEntity.EntityUseAction.ATTACK ? 1 : (action == WrappedPacketInUseEntity.EntityUseAction.INTERACT ? 0 : 2));

        return new PacketDataSerializer(buf);
    }

    private static void writeVarInt(ByteBuf buf, int value) {
        while ((value & ~0x7F) != 0) { // While there are more than 7 bits left
            buf.writeByte((value & 0x7F) | 0x80); // Write 7 bits + continuation bit
            value >>>= 7; // Shift the remaining bits to the right
        }
        buf.writeByte(value); // Write the last 7 bits without a continuation bit
    }
}
