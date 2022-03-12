package io.github.retrooper.packetevents.packettype;

import io.github.retrooper.packetevents.utils.vector.Vector3i;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketWrapper<T extends PacketWrapper> {
    public ByteBuf buffer;
    //protected ClientVersion clientVersion;
    private final int packetID;
    private boolean hasPreparedForSending;

    public PacketWrapper(int packetID) {
        this.packetID = packetID;
    }

    public void prepareForSend() {
        if (!hasPreparedForSending) {
            this.buffer = Unpooled.buffer();
            writeVarInt(packetID);
            writeData();
            hasPreparedForSending = true;
        }
    }

    public void writeData() {
    }

    public void writeBlockPosition(Vector3i pos) {
        long val = pos.getSerializedPosition();
        writeLong(val);
    }

    public void writeLong(long value) {
        buffer.writeLong(value);
    }

    public void writeVarInt(int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                writeByte(value);
                return;
            }
            writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }

    public void writeByte(int value) {
        buffer.writeByte(value);
    }


}
