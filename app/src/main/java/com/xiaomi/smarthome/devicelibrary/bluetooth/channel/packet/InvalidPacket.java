package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

/* loaded from: classes4.dex */
public class InvalidPacket extends Packet {
    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return "invalid";
    }

    public String toString() {
        return "InvalidPacket{}";
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        return new byte[0];
    }
}
