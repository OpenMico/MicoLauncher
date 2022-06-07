package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ACKPacket extends Packet {
    public static final int BUSY = 2;
    public static final int CANCEL = 4;
    public static final int READY = 1;
    public static final int SUCCESS = 0;
    public static final int SYNC = 5;
    public static final int TIMEOUT = 3;
    private int a;
    private List<Short> b;

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return Packet.ACK;
    }

    public ACKPacket(int i) {
        this.b = Collections.emptyList();
        this.a = i;
    }

    public ACKPacket(int i, List<Short> list) {
        this.b = Collections.emptyList();
        this.a = i;
        if (list != null && list.size() > 0) {
            this.b = list;
        }
    }

    public int getStatus() {
        return this.a;
    }

    public List<Short> getSeq() {
        return this.b;
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer order = ByteBuffer.allocate((this.b.size() * 2) + 4).order(ByteOrder.LITTLE_ENDIAN);
        order.putShort((short) 0);
        order.put((byte) 1);
        order.put((byte) this.a);
        if (this.b.size() > 0) {
            for (Short sh : this.b) {
                order.putShort(sh.shortValue());
            }
        }
        return order.array();
    }

    public String toString() {
        return "ACKPacket{status=" + this.a + ", seq=" + this.b.toString() + '}';
    }
}
