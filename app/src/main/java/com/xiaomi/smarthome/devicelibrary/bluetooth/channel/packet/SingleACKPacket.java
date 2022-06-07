package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public class SingleACKPacket extends Packet {
    public static final int RESP_DEVICE_BUSY = 2;
    public static final int RESP_SUCCESS = 0;
    private final byte[] a = new byte[4];
    private int b;

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return Packet.SINGLE_ACK;
    }

    public SingleACKPacket(int i) {
        this.b = i;
    }

    public int getStatus() {
        return this.b;
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer order = ByteBuffer.wrap(this.a).order(ByteOrder.LITTLE_ENDIAN);
        order.putShort((short) 0);
        order.put((byte) 3);
        order.put((byte) this.b);
        return order.array();
    }
}
