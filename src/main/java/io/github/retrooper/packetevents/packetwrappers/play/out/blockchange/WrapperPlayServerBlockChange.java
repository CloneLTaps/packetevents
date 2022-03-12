package io.github.retrooper.packetevents.packetwrappers.play.out.blockchange;

import io.github.retrooper.packetevents.packettype.PacketWrapper;
import io.github.retrooper.packetevents.utils.vector.Vector3i;

public class WrapperPlayServerBlockChange extends PacketWrapper {
    private final Vector3i blockPosition;
    private final int blockID;

    public WrapperPlayServerBlockChange(Vector3i blockPosition, int combinedID) {
        super(35);
        this.blockPosition = blockPosition;
        blockID = (combinedID);
    }

    @Override
    public void writeData() {
        writeBlockPosition(blockPosition);
        writeVarInt(blockID);
    }

    public Vector3i getBlockPosition() {
        return blockPosition;
    }

    public int getBlockId() {
        return blockID;
    }
}
