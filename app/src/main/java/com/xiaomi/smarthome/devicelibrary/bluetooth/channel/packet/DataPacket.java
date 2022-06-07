package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class DataPacket extends Packet {
    private final byte[] a;
    private int b;
    private Packet.a c;

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return "data";
    }

    public DataPacket(int i, Packet.a aVar) {
        this.a = new byte[20];
        this.b = i;
        this.c = aVar;
    }

    public DataPacket(int i, byte[] bArr, int i2, int i3) {
        this(i, new Packet.a(bArr, i2, i3));
    }

    public int getSeq() {
        return this.b;
    }

    public int getDataLength() {
        return this.c.a();
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer byteBuffer;
        int dataLength = getDataLength() + 2;
        if (dataLength == 20) {
            Arrays.fill(this.a, (byte) 0);
            byteBuffer = ByteBuffer.wrap(this.a).order(ByteOrder.LITTLE_ENDIAN);
        } else {
            byteBuffer = ByteBuffer.allocate(dataLength).order(ByteOrder.LITTLE_ENDIAN);
        }
        byteBuffer.putShort((short) this.b);
        fillByteBuffer(byteBuffer);
        return byteBuffer.array();
    }

    public void fillByteBuffer(ByteBuffer byteBuffer) {
        byteBuffer.put(this.c.a, this.c.b, getDataLength());
    }

    public String toString() {
        return "DataPacket{seq=" + this.b + ", size=" + this.c.a() + '}';
    }
}
