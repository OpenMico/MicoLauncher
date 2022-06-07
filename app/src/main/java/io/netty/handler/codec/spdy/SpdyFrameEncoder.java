package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.nio.ByteOrder;
import java.util.Set;

/* loaded from: classes4.dex */
public class SpdyFrameEncoder {
    private final int a;

    public SpdyFrameEncoder(SpdyVersion spdyVersion) {
        if (spdyVersion != null) {
            this.a = spdyVersion.a();
            return;
        }
        throw new NullPointerException("spdyVersion");
    }

    private void a(ByteBuf byteBuf, int i, byte b, int i2) {
        byteBuf.writeShort(this.a | 32768);
        byteBuf.writeShort(i);
        byteBuf.writeByte(b);
        byteBuf.writeMedium(i2);
    }

    public ByteBuf encodeDataFrame(ByteBufAllocator byteBufAllocator, int i, boolean z, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        ByteBuf order = byteBufAllocator.ioBuffer(readableBytes + 8).order(ByteOrder.BIG_ENDIAN);
        order.writeInt(i & Integer.MAX_VALUE);
        order.writeByte(z ? 1 : 0);
        order.writeMedium(readableBytes);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ByteBuf encodeSynStreamFrame(ByteBufAllocator byteBufAllocator, int i, int i2, byte b, boolean z, boolean z2, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        byte b2 = z;
        if (z2) {
            b2 = (byte) (z | 2);
        }
        int i3 = readableBytes + 10;
        ByteBuf order = byteBufAllocator.ioBuffer(i3 + 8).order(ByteOrder.BIG_ENDIAN);
        a(order, 1, b2 == 1 ? (byte) 1 : (byte) 0, i3);
        order.writeInt(i);
        order.writeInt(i2);
        order.writeShort((b & 255) << 13);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }

    public ByteBuf encodeSynReplyFrame(ByteBufAllocator byteBufAllocator, int i, boolean z, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        int i2 = readableBytes + 4;
        ByteBuf order = byteBufAllocator.ioBuffer(i2 + 8).order(ByteOrder.BIG_ENDIAN);
        a(order, 2, z ? (byte) 1 : (byte) 0, i2);
        order.writeInt(i);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }

    public ByteBuf encodeRstStreamFrame(ByteBufAllocator byteBufAllocator, int i, int i2) {
        ByteBuf order = byteBufAllocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        a(order, 3, (byte) 0, 8);
        order.writeInt(i);
        order.writeInt(i2);
        return order;
    }

    public ByteBuf encodeSettingsFrame(ByteBufAllocator byteBufAllocator, SpdySettingsFrame spdySettingsFrame) {
        Set<Integer> ids = spdySettingsFrame.ids();
        int size = ids.size();
        boolean clearPreviouslyPersistedSettings = spdySettingsFrame.clearPreviouslyPersistedSettings();
        int i = (size * 8) + 4;
        ByteBuf order = byteBufAllocator.ioBuffer(i + 8).order(ByteOrder.BIG_ENDIAN);
        a(order, 4, clearPreviouslyPersistedSettings ? (byte) 1 : (byte) 0, i);
        order.writeInt(size);
        for (Integer num : ids) {
            byte b = 0;
            if (spdySettingsFrame.isPersistValue(num.intValue())) {
                b = (byte) 1;
            }
            if (spdySettingsFrame.isPersisted(num.intValue())) {
                b = (byte) (b | 2);
            }
            order.writeByte(b);
            order.writeMedium(num.intValue());
            order.writeInt(spdySettingsFrame.getValue(num.intValue()));
        }
        return order;
    }

    public ByteBuf encodePingFrame(ByteBufAllocator byteBufAllocator, int i) {
        ByteBuf order = byteBufAllocator.ioBuffer(12).order(ByteOrder.BIG_ENDIAN);
        a(order, 6, (byte) 0, 4);
        order.writeInt(i);
        return order;
    }

    public ByteBuf encodeGoAwayFrame(ByteBufAllocator byteBufAllocator, int i, int i2) {
        ByteBuf order = byteBufAllocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        a(order, 7, (byte) 0, 8);
        order.writeInt(i);
        order.writeInt(i2);
        return order;
    }

    public ByteBuf encodeHeadersFrame(ByteBufAllocator byteBufAllocator, int i, boolean z, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        int i2 = readableBytes + 4;
        ByteBuf order = byteBufAllocator.ioBuffer(i2 + 8).order(ByteOrder.BIG_ENDIAN);
        a(order, 8, z ? (byte) 1 : (byte) 0, i2);
        order.writeInt(i);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }

    public ByteBuf encodeWindowUpdateFrame(ByteBufAllocator byteBufAllocator, int i, int i2) {
        ByteBuf order = byteBufAllocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        a(order, 9, (byte) 0, 8);
        order.writeInt(i);
        order.writeInt(i2);
        return order;
    }
}
