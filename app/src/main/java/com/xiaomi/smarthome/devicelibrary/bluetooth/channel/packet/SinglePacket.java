package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public class SinglePacket extends Packet {
    public static final int SIG_CMD_APP_CONFIRMMATION = 10;
    public static final int SIG_CMD_APP_RANDOM = 11;
    public static final int SIG_CMD_BIND_KEY = 14;
    public static final int SIG_CMD_COMMON_DATA = 0;
    public static final int SIG_CMD_DEVICE_LOGIN_INFO = 5;
    public static final int SIG_CMD_DEVICE_SIGATURE = 4;
    public static final int SIG_CMD_DEV_CONFIRMATION = 12;
    public static final int SIG_CMD_DEV_RANDOM = 13;
    public static final int SIG_CMD_MESH_CONFIG = 9;
    public static final int SIG_CMD_PUBLIC_KEY = 3;
    public static final int SIG_CMD_SERVER_SIGNATURE = 8;
    public static final int SIG_CMD_SHARE_INFO = 6;
    byte[] a;
    int b;

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return Packet.SINGLE_CMD;
    }

    public SinglePacket(int i, byte[] bArr) {
        this.a = bArr;
        this.b = i;
    }

    public byte[] getData() {
        return this.a;
    }

    public int getPackageType() {
        return this.b;
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer order = ByteBuffer.allocate(this.a.length + 4).order(ByteOrder.LITTLE_ENDIAN);
        order.putShort((short) 0);
        order.put((byte) 2);
        order.put((byte) this.b);
        order.put(this.a);
        return order.array();
    }
}
