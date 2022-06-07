package com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet;

import com.xiaomi.smarthome.devicelibrary.common.util.ByteUtils;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class Packet {
    public static final String ACK = "ack";
    public static final String CTR = "ctr";
    public static final String DATA = "data";
    public static final String MNG_ACK = "mng_ack";
    public static final String MNG_CMD = "mng";
    public static final String SINGLE_ACK = "single_ack";
    public static final String SINGLE_CMD = "single_ctr";
    public static final int TYPE_ACK = 1;
    public static final int TYPE_CMD = 0;
    public static final int TYPE_MNG = 4;
    public static final int TYPE_MNG_ACK = 5;
    public static final int TYPE_SINGLE_ACK = 3;
    public static final int TYPE_SINGLE_CMD = 2;

    public abstract String getName();

    public abstract byte[] toBytes();

    /* loaded from: classes4.dex */
    public static class b {
        int a;
        int b;
        int c;
        byte[] d;
        List<Short> e;

        private b() {
            this.e = new ArrayList();
        }
    }

    /* loaded from: classes4.dex */
    public static class a {
        byte[] a;
        int b;
        int c;

        a(byte[] bArr, int i) {
            this(bArr, i, bArr.length);
        }

        public a(byte[] bArr, int i, int i2) {
            this.a = bArr;
            this.b = i;
            this.c = i2;
        }

        public int a() {
            return this.c - this.b;
        }
    }

    private static b a(byte[] bArr) throws BufferUnderflowException {
        b bVar = new b();
        ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        bVar.a = order.getShort();
        bVar.d = bArr;
        if (bVar.a == 0) {
            bVar.b = order.get();
            bVar.c = order.get();
            while (order.hasRemaining()) {
                try {
                    bVar.e.add(Short.valueOf(order.getShort()));
                } catch (BufferUnderflowException unused) {
                }
            }
        }
        return bVar;
    }

    public static Packet getPacket(byte[] bArr) {
        b bVar;
        try {
            bVar = a(bArr);
        } catch (BufferUnderflowException e) {
            e.printStackTrace();
            bVar = null;
        }
        if (bVar == null) {
            return new InvalidPacket();
        }
        if (bVar.a != 0) {
            return b(bVar);
        }
        return a(bVar);
    }

    private static Packet a(b bVar) {
        List<Short> list = bVar.e;
        switch (bVar.b) {
            case 0:
                return new CTRPacket(list.get(0).shortValue(), bVar.c);
            case 1:
                return new ACKPacket(bVar.c, list);
            case 2:
                return new SinglePacket(bVar.c, ByteUtils.get(bVar.d, 4));
            case 3:
                return new SingleACKPacket(bVar.c);
            case 4:
                return new MNGPacket(bVar.c, ByteUtils.get(bVar.d, 4));
            case 5:
                return new MNGAckPacket(bVar.c, ByteUtils.get(bVar.d, 4));
            default:
                return new InvalidPacket();
        }
    }

    private static Packet b(b bVar) {
        return new DataPacket(bVar.a, new a(bVar.d, 2));
    }
}
