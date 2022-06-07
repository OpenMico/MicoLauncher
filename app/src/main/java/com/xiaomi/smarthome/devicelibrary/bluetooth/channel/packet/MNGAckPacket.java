package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public class MNGAckPacket extends MNGPacket {
    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.MNGPacket, com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return Packet.MNG_ACK;
    }

    public MNGAckPacket(int i, byte[] bArr) {
        super(i, bArr);
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.MNGPacket, com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer order = ByteBuffer.allocate(this.mValues.length + 4).order(ByteOrder.LITTLE_ENDIAN);
        order.putShort((short) 0);
        order.put((byte) 5);
        order.put((byte) this.mCmdType);
        order.put(this.mValues);
        return order.array();
    }
}
