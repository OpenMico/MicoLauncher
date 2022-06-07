package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public class MNGPacket extends Packet {
    public static final int TYPE_ASK_SUPPORT = 0;
    public static final int TYPE_SEND_TEST_PACKAGE = 1;
    protected int mCmdType;
    protected byte[] mValues;
    protected int maxDMTU;
    protected int maxPackgeNum;
    protected byte[] testBytes;

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public String getName() {
        return Packet.MNG_CMD;
    }

    public MNGPacket(int i, byte[] bArr) {
        this.mCmdType = i;
        this.mValues = bArr;
    }

    public int getMaxPackgeNum() {
        return this.maxPackgeNum;
    }

    public int getMaxDMTU() {
        return this.maxDMTU;
    }

    public int getPackageType() {
        return this.mCmdType;
    }

    public byte[] getTestBytes() {
        return this.testBytes;
    }

    public void parse() {
        ByteBuffer order = ByteBuffer.wrap(this.mValues).order(ByteOrder.LITTLE_ENDIAN);
        if (this.mCmdType == 0) {
            this.maxPackgeNum = order.get();
            this.maxDMTU = order.get() & 255;
            return;
        }
        this.testBytes = order.array();
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer order = ByteBuffer.allocate(this.mValues.length + 4).order(ByteOrder.LITTLE_ENDIAN);
        order.putShort((short) 0);
        order.put((byte) 4);
        order.put((byte) this.mCmdType);
        order.put(this.mValues);
        return order.array();
    }
}
