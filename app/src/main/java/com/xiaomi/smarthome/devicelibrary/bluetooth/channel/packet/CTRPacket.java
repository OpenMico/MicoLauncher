package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public class CTRPacket extends Packet {
    public static final int CMD_BIND_KEY = 14;
    public static final int CMD_COMMON_DATA = 0;
    public static final int CMD_DEVICE_CERT = 1;
    public static final int CMD_DEVICE_LOGIN_INFO = 5;
    public static final int CMD_DEVICE_PUBLIC_KEY = 3;
    public static final int CMD_DEVICE_SIGNATURE = 4;
    public static final int CMD_LOCK_APP_CONFIRMATION = 10;
    public static final int CMD_LOCK_APP_RANDOM = 11;
    public static final int CMD_LOCK_DEV_CONFIRMATION = 12;
    public static final int CMD_LOCK_DEV_RANDOM = 13;
    public static final int CMD_MANUFACTURE_CERT = 2;
    public static final int CMD_MESH_CONFIG = 9;
    public static final int CMD_SERVER_CERT = 7;
    public static final int CMD_SERVER_SIGNATURE = 8;
    public static final int CMD_SHARE_LOGIN_INFO = 6;
    public static final int CMD_WIFI_CONFIG = 15;
    private final byte[] a = new byte[6];
    private int b;
    private int c;

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return Packet.CTR;
    }

    public CTRPacket(int i, int i2) {
        this.b = i;
        this.c = i2;
    }

    public int getFrameCount() {
        return this.b;
    }

    public int getPackageType() {
        return this.c;
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer order = ByteBuffer.wrap(this.a).order(ByteOrder.LITTLE_ENDIAN);
        order.putShort((short) 0);
        order.put((byte) 0);
        order.put((byte) this.c);
        order.putShort((short) this.b);
        return order.array();
    }

    public String toString() {
        return "FlowPacket{frameCount=" + this.b + "packageType=" + this.c + '}';
    }
}
