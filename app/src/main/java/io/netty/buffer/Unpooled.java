package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public final class Unpooled {
    static final /* synthetic */ boolean a = !Unpooled.class.desiredAssertionStatus();
    private static final ByteBufAllocator b = UnpooledByteBufAllocator.DEFAULT;
    public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
    public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
    public static final ByteBuf EMPTY_BUFFER = b.buffer(0, 0);

    static {
        if (!a && !(EMPTY_BUFFER instanceof EmptyByteBuf)) {
            throw new AssertionError("EMPTY_BUFFER must be an EmptyByteBuf.");
        }
    }

    public static ByteBuf buffer() {
        return b.heapBuffer();
    }

    public static ByteBuf directBuffer() {
        return b.directBuffer();
    }

    public static ByteBuf buffer(int i) {
        return b.heapBuffer(i);
    }

    public static ByteBuf directBuffer(int i) {
        return b.directBuffer(i);
    }

    public static ByteBuf buffer(int i, int i2) {
        return b.heapBuffer(i, i2);
    }

    public static ByteBuf directBuffer(int i, int i2) {
        return b.directBuffer(i, i2);
    }

    public static ByteBuf wrappedBuffer(byte[] bArr) {
        if (bArr.length == 0) {
            return EMPTY_BUFFER;
        }
        return new UnpooledHeapByteBuf(b, bArr, bArr.length);
    }

    public static ByteBuf wrappedBuffer(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return EMPTY_BUFFER;
        }
        if (i == 0 && i2 == bArr.length) {
            return wrappedBuffer(bArr);
        }
        return wrappedBuffer(bArr).slice(i, i2);
    }

    public static ByteBuf wrappedBuffer(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return EMPTY_BUFFER;
        }
        if (byteBuffer.hasArray()) {
            return wrappedBuffer(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()).order(byteBuffer.order());
        }
        if (PlatformDependent.hasUnsafe()) {
            if (!byteBuffer.isReadOnly()) {
                return new UnpooledUnsafeDirectByteBuf(b, byteBuffer, byteBuffer.remaining());
            }
            if (byteBuffer.isDirect()) {
                return new u(b, byteBuffer);
            }
            return new t(b, byteBuffer);
        } else if (byteBuffer.isReadOnly()) {
            return new t(b, byteBuffer);
        } else {
            return new UnpooledDirectByteBuf(b, byteBuffer, byteBuffer.remaining());
        }
    }

    public static ByteBuf wrappedBuffer(ByteBuf byteBuf) {
        if (byteBuf.isReadable()) {
            return byteBuf.slice();
        }
        byteBuf.release();
        return EMPTY_BUFFER;
    }

    public static ByteBuf wrappedBuffer(byte[]... bArr) {
        return wrappedBuffer(16, bArr);
    }

    public static ByteBuf wrappedBuffer(ByteBuf... byteBufArr) {
        return wrappedBuffer(16, byteBufArr);
    }

    public static ByteBuf wrappedBuffer(ByteBuffer... byteBufferArr) {
        return wrappedBuffer(16, byteBufferArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static io.netty.buffer.ByteBuf wrappedBuffer(int r6, byte[]... r7) {
        /*
            int r0 = r7.length
            r1 = 0
            switch(r0) {
                case 0: goto L_0x003c;
                case 1: goto L_0x000e;
                default: goto L_0x0005;
            }
        L_0x0005:
            java.util.ArrayList r0 = new java.util.ArrayList
            int r2 = r7.length
            r0.<init>(r2)
            int r2 = r7.length
            r3 = r1
            goto L_0x001a
        L_0x000e:
            r6 = r7[r1]
            int r6 = r6.length
            if (r6 == 0) goto L_0x003c
            r6 = r7[r1]
            io.netty.buffer.ByteBuf r6 = wrappedBuffer(r6)
            return r6
        L_0x001a:
            if (r3 >= r2) goto L_0x002e
            r4 = r7[r3]
            if (r4 != 0) goto L_0x0021
            goto L_0x002e
        L_0x0021:
            int r5 = r4.length
            if (r5 <= 0) goto L_0x002b
            io.netty.buffer.ByteBuf r4 = wrappedBuffer(r4)
            r0.add(r4)
        L_0x002b:
            int r3 = r3 + 1
            goto L_0x001a
        L_0x002e:
            boolean r7 = r0.isEmpty()
            if (r7 != 0) goto L_0x003c
            io.netty.buffer.CompositeByteBuf r7 = new io.netty.buffer.CompositeByteBuf
            io.netty.buffer.ByteBufAllocator r2 = io.netty.buffer.Unpooled.b
            r7.<init>(r2, r1, r6, r0)
            return r7
        L_0x003c:
            io.netty.buffer.ByteBuf r6 = io.netty.buffer.Unpooled.EMPTY_BUFFER
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.Unpooled.wrappedBuffer(int, byte[][]):io.netty.buffer.ByteBuf");
    }

    public static ByteBuf wrappedBuffer(int i, ByteBuf... byteBufArr) {
        switch (byteBufArr.length) {
            case 0:
                break;
            case 1:
                ByteBuf byteBuf = byteBufArr[0];
                if (!byteBuf.isReadable()) {
                    byteBuf.release();
                    break;
                } else {
                    return wrappedBuffer(byteBuf.order(BIG_ENDIAN));
                }
            default:
                for (ByteBuf byteBuf2 : byteBufArr) {
                    if (byteBuf2.isReadable()) {
                        return new CompositeByteBuf(b, false, i, byteBufArr);
                    }
                    byteBuf2.release();
                }
                break;
        }
        return EMPTY_BUFFER;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static io.netty.buffer.ByteBuf wrappedBuffer(int r6, java.nio.ByteBuffer... r7) {
        /*
            int r0 = r7.length
            r1 = 0
            switch(r0) {
                case 0: goto L_0x004e;
                case 1: goto L_0x000e;
                default: goto L_0x0005;
            }
        L_0x0005:
            java.util.ArrayList r0 = new java.util.ArrayList
            int r2 = r7.length
            r0.<init>(r2)
            int r2 = r7.length
            r3 = r1
            goto L_0x0023
        L_0x000e:
            r6 = r7[r1]
            boolean r6 = r6.hasRemaining()
            if (r6 == 0) goto L_0x004e
            r6 = r7[r1]
            java.nio.ByteOrder r7 = io.netty.buffer.Unpooled.BIG_ENDIAN
            java.nio.ByteBuffer r6 = r6.order(r7)
            io.netty.buffer.ByteBuf r6 = wrappedBuffer(r6)
            return r6
        L_0x0023:
            if (r3 >= r2) goto L_0x0040
            r4 = r7[r3]
            if (r4 != 0) goto L_0x002a
            goto L_0x0040
        L_0x002a:
            int r5 = r4.remaining()
            if (r5 <= 0) goto L_0x003d
            java.nio.ByteOrder r5 = io.netty.buffer.Unpooled.BIG_ENDIAN
            java.nio.ByteBuffer r4 = r4.order(r5)
            io.netty.buffer.ByteBuf r4 = wrappedBuffer(r4)
            r0.add(r4)
        L_0x003d:
            int r3 = r3 + 1
            goto L_0x0023
        L_0x0040:
            boolean r7 = r0.isEmpty()
            if (r7 != 0) goto L_0x004e
            io.netty.buffer.CompositeByteBuf r7 = new io.netty.buffer.CompositeByteBuf
            io.netty.buffer.ByteBufAllocator r2 = io.netty.buffer.Unpooled.b
            r7.<init>(r2, r1, r6, r0)
            return r7
        L_0x004e:
            io.netty.buffer.ByteBuf r6 = io.netty.buffer.Unpooled.EMPTY_BUFFER
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.Unpooled.wrappedBuffer(int, java.nio.ByteBuffer[]):io.netty.buffer.ByteBuf");
    }

    public static CompositeByteBuf compositeBuffer() {
        return compositeBuffer(16);
    }

    public static CompositeByteBuf compositeBuffer(int i) {
        return new CompositeByteBuf(b, false, i);
    }

    public static ByteBuf copiedBuffer(byte[] bArr) {
        if (bArr.length == 0) {
            return EMPTY_BUFFER;
        }
        return wrappedBuffer((byte[]) bArr.clone());
    }

    public static ByteBuf copiedBuffer(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return EMPTY_BUFFER;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return wrappedBuffer(bArr2);
    }

    public static ByteBuf copiedBuffer(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        if (remaining == 0) {
            return EMPTY_BUFFER;
        }
        byte[] bArr = new byte[remaining];
        ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.get(bArr);
        return wrappedBuffer(bArr).order(duplicate.order());
    }

    public static ByteBuf copiedBuffer(ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes <= 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(readableBytes);
        buffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return buffer;
    }

    public static ByteBuf copiedBuffer(byte[]... bArr) {
        switch (bArr.length) {
            case 0:
                return EMPTY_BUFFER;
            case 1:
                if (bArr[0].length == 0) {
                    return EMPTY_BUFFER;
                }
                return copiedBuffer(bArr[0]);
            default:
                int i = 0;
                for (byte[] bArr2 : bArr) {
                    if (Integer.MAX_VALUE - i >= bArr2.length) {
                        i += bArr2.length;
                    } else {
                        throw new IllegalArgumentException("The total length of the specified arrays is too big.");
                    }
                }
                if (i == 0) {
                    return EMPTY_BUFFER;
                }
                byte[] bArr3 = new byte[i];
                int i2 = 0;
                for (byte[] bArr4 : bArr) {
                    System.arraycopy(bArr4, 0, bArr3, i2, bArr4.length);
                    i2 += bArr4.length;
                }
                return wrappedBuffer(bArr3);
        }
    }

    public static ByteBuf copiedBuffer(ByteBuf... byteBufArr) {
        switch (byteBufArr.length) {
            case 0:
                return EMPTY_BUFFER;
            case 1:
                return copiedBuffer(byteBufArr[0]);
            default:
                ByteOrder byteOrder = null;
                int i = 0;
                for (ByteBuf byteBuf : byteBufArr) {
                    int readableBytes = byteBuf.readableBytes();
                    if (readableBytes > 0) {
                        if (Integer.MAX_VALUE - i >= readableBytes) {
                            i += readableBytes;
                            if (byteOrder == null) {
                                byteOrder = byteBuf.order();
                            } else if (!byteOrder.equals(byteBuf.order())) {
                                throw new IllegalArgumentException("inconsistent byte order");
                            }
                        } else {
                            throw new IllegalArgumentException("The total length of the specified buffers is too big.");
                        }
                    }
                }
                if (i == 0) {
                    return EMPTY_BUFFER;
                }
                byte[] bArr = new byte[i];
                int i2 = 0;
                for (ByteBuf byteBuf2 : byteBufArr) {
                    int readableBytes2 = byteBuf2.readableBytes();
                    byteBuf2.getBytes(byteBuf2.readerIndex(), bArr, i2, readableBytes2);
                    i2 += readableBytes2;
                }
                return wrappedBuffer(bArr).order(byteOrder);
        }
    }

    public static ByteBuf copiedBuffer(ByteBuffer... byteBufferArr) {
        switch (byteBufferArr.length) {
            case 0:
                return EMPTY_BUFFER;
            case 1:
                return copiedBuffer(byteBufferArr[0]);
            default:
                ByteOrder byteOrder = null;
                int i = 0;
                for (ByteBuffer byteBuffer : byteBufferArr) {
                    int remaining = byteBuffer.remaining();
                    if (remaining > 0) {
                        if (Integer.MAX_VALUE - i >= remaining) {
                            i += remaining;
                            if (byteOrder == null) {
                                byteOrder = byteBuffer.order();
                            } else if (!byteOrder.equals(byteBuffer.order())) {
                                throw new IllegalArgumentException("inconsistent byte order");
                            }
                        } else {
                            throw new IllegalArgumentException("The total length of the specified buffers is too big.");
                        }
                    }
                }
                if (i == 0) {
                    return EMPTY_BUFFER;
                }
                byte[] bArr = new byte[i];
                int i2 = 0;
                for (ByteBuffer byteBuffer2 : byteBufferArr) {
                    ByteBuffer duplicate = byteBuffer2.duplicate();
                    int remaining2 = duplicate.remaining();
                    duplicate.get(bArr, i2, remaining2);
                    i2 += remaining2;
                }
                return wrappedBuffer(bArr).order(byteOrder);
        }
    }

    public static ByteBuf copiedBuffer(CharSequence charSequence, Charset charset) {
        if (charSequence == null) {
            throw new NullPointerException("string");
        } else if (charSequence instanceof CharBuffer) {
            return a((CharBuffer) charSequence, charset);
        } else {
            return a(CharBuffer.wrap(charSequence), charset);
        }
    }

    public static ByteBuf copiedBuffer(CharSequence charSequence, int i, int i2, Charset charset) {
        if (charSequence == null) {
            throw new NullPointerException("string");
        } else if (i2 == 0) {
            return EMPTY_BUFFER;
        } else {
            if (!(charSequence instanceof CharBuffer)) {
                return a(CharBuffer.wrap(charSequence, i, i2 + i), charset);
            }
            CharBuffer charBuffer = (CharBuffer) charSequence;
            if (charBuffer.hasArray()) {
                return copiedBuffer(charBuffer.array(), charBuffer.arrayOffset() + charBuffer.position() + i, i2, charset);
            }
            CharBuffer slice = charBuffer.slice();
            slice.limit(i2);
            slice.position(i);
            return a(slice, charset);
        }
    }

    public static ByteBuf copiedBuffer(char[] cArr, Charset charset) {
        if (cArr != null) {
            return copiedBuffer(cArr, 0, cArr.length, charset);
        }
        throw new NullPointerException("array");
    }

    public static ByteBuf copiedBuffer(char[] cArr, int i, int i2, Charset charset) {
        if (cArr == null) {
            throw new NullPointerException("array");
        } else if (i2 == 0) {
            return EMPTY_BUFFER;
        } else {
            return a(CharBuffer.wrap(cArr, i, i2), charset);
        }
    }

    private static ByteBuf a(CharBuffer charBuffer, Charset charset) {
        return ByteBufUtil.a(b, true, charBuffer, charset, 0);
    }

    @Deprecated
    public static ByteBuf unmodifiableBuffer(ByteBuf byteBuf) {
        ByteOrder order = byteBuf.order();
        ByteOrder byteOrder = BIG_ENDIAN;
        if (order == byteOrder) {
            return new ReadOnlyByteBuf(byteBuf);
        }
        return new ReadOnlyByteBuf(byteBuf.order(byteOrder)).order(LITTLE_ENDIAN);
    }

    public static ByteBuf copyInt(int i) {
        ByteBuf buffer = buffer(4);
        buffer.writeInt(i);
        return buffer;
    }

    public static ByteBuf copyInt(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(iArr.length * 4);
        for (int i : iArr) {
            buffer.writeInt(i);
        }
        return buffer;
    }

    public static ByteBuf copyShort(int i) {
        ByteBuf buffer = buffer(2);
        buffer.writeShort(i);
        return buffer;
    }

    public static ByteBuf copyShort(short... sArr) {
        if (sArr == null || sArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(sArr.length * 2);
        for (short s : sArr) {
            buffer.writeShort(s);
        }
        return buffer;
    }

    public static ByteBuf copyShort(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(iArr.length * 2);
        for (int i : iArr) {
            buffer.writeShort(i);
        }
        return buffer;
    }

    public static ByteBuf copyMedium(int i) {
        ByteBuf buffer = buffer(3);
        buffer.writeMedium(i);
        return buffer;
    }

    public static ByteBuf copyMedium(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(iArr.length * 3);
        for (int i : iArr) {
            buffer.writeMedium(i);
        }
        return buffer;
    }

    public static ByteBuf copyLong(long j) {
        ByteBuf buffer = buffer(8);
        buffer.writeLong(j);
        return buffer;
    }

    public static ByteBuf copyLong(long... jArr) {
        if (jArr == null || jArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(jArr.length * 8);
        for (long j : jArr) {
            buffer.writeLong(j);
        }
        return buffer;
    }

    public static ByteBuf copyBoolean(boolean z) {
        ByteBuf buffer = buffer(1);
        buffer.writeBoolean(z);
        return buffer;
    }

    public static ByteBuf copyBoolean(boolean... zArr) {
        if (zArr == null || zArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(zArr.length);
        for (boolean z : zArr) {
            buffer.writeBoolean(z);
        }
        return buffer;
    }

    public static ByteBuf copyFloat(float f) {
        ByteBuf buffer = buffer(4);
        buffer.writeFloat(f);
        return buffer;
    }

    public static ByteBuf copyFloat(float... fArr) {
        if (fArr == null || fArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(fArr.length * 4);
        for (float f : fArr) {
            buffer.writeFloat(f);
        }
        return buffer;
    }

    public static ByteBuf copyDouble(double d) {
        ByteBuf buffer = buffer(8);
        buffer.writeDouble(d);
        return buffer;
    }

    public static ByteBuf copyDouble(double... dArr) {
        if (dArr == null || dArr.length == 0) {
            return EMPTY_BUFFER;
        }
        ByteBuf buffer = buffer(dArr.length * 8);
        for (double d : dArr) {
            buffer.writeDouble(d);
        }
        return buffer;
    }

    public static ByteBuf unreleasableBuffer(ByteBuf byteBuf) {
        return new aa(byteBuf);
    }

    public static ByteBuf unmodifiableBuffer(ByteBuf... byteBufArr) {
        return new f(b, byteBufArr);
    }

    private Unpooled() {
    }
}
