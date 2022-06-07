package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public final class Snappy {
    private a a = a.READY;
    private byte b;
    private int c;

    /* loaded from: classes4.dex */
    private enum a {
        READY,
        READING_PREAMBLE,
        READING_TAG,
        READING_LITERAL,
        READING_COPY
    }

    static int a(int i) {
        return ((i << 17) | (i >> 15)) - 1568478504;
    }

    public void reset() {
        this.a = a.READY;
        this.b = (byte) 0;
        this.c = 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0069, code lost:
        a(r17, r18, r7 - r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x006e, code lost:
        r8 = a(r17, r14 + 4, r7 + 4, r19) + 4;
        r9 = r7 + r8;
        d(r18, r7 - r14, r8);
        r17.readerIndex(r17.readerIndex() + r8);
        r7 = r9 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0088, code lost:
        if (r9 < r12) goto L_0x008b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x008b, code lost:
        r10 = r9 - r3;
        r4[b(r17, r7, r5)] = (short) (r10 - 1);
        r8 = r7 + 1;
        r11 = b(r17, r8, r5);
        r14 = r3 + r4[r11];
        r4[r11] = (short) r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00ab, code lost:
        if (r17.getInt(r8) == r17.getInt(r14)) goto L_0x00b6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00b6, code lost:
        r7 = r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void encode(io.netty.buffer.ByteBuf r17, io.netty.buffer.ByteBuf r18, int r19) {
        /*
            r16 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = 0
        L_0x0007:
            int r4 = r3 * 7
            int r4 = r2 >>> r4
            r5 = r4 & (-128(0xffffffffffffff80, float:NaN))
            if (r5 == 0) goto L_0x0019
            r4 = r4 & 127(0x7f, float:1.78E-43)
            r4 = r4 | 128(0x80, float:1.794E-43)
            r1.writeByte(r4)
            int r3 = r3 + 1
            goto L_0x0007
        L_0x0019:
            r1.writeByte(r4)
            int r3 = r17.readerIndex()
            short[] r4 = b(r19)
            int r5 = r4.length
            double r5 = (double) r5
            double r5 = java.lang.Math.log(r5)
            r7 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r7 = java.lang.Math.log(r7)
            double r5 = r5 / r7
            double r5 = java.lang.Math.floor(r5)
            int r5 = (int) r5
            r6 = 32
            int r5 = 32 - r5
            int r7 = r2 - r3
            r8 = 15
            if (r7 < r8) goto L_0x00bc
            int r7 = r3 + 1
            int r8 = b(r0, r7, r5)
            r9 = r3
        L_0x0047:
            r10 = r6
        L_0x0048:
            int r11 = r10 + 1
            int r10 = r10 >> 5
            int r10 = r10 + r7
            int r12 = r2 + (-4)
            if (r10 <= r12) goto L_0x0053
            goto L_0x00bd
        L_0x0053:
            int r13 = b(r0, r10, r5)
            short r14 = r4[r8]
            int r14 = r14 + r3
            int r15 = r7 - r3
            short r15 = (short) r15
            r4[r8] = r15
            int r8 = r0.getInt(r7)
            int r15 = r0.getInt(r14)
            if (r8 != r15) goto L_0x00b8
            int r8 = r7 - r9
            a(r0, r1, r8)
        L_0x006e:
            int r8 = r14 + 4
            int r9 = r7 + 4
            int r8 = a(r0, r8, r9, r2)
            int r8 = r8 + 4
            int r9 = r7 + r8
            int r7 = r7 - r14
            d(r1, r7, r8)
            int r7 = r17.readerIndex()
            int r7 = r7 + r8
            r0.readerIndex(r7)
            int r7 = r9 + (-1)
            if (r9 < r12) goto L_0x008b
            goto L_0x00bd
        L_0x008b:
            int r8 = b(r0, r7, r5)
            int r10 = r9 - r3
            int r11 = r10 + (-1)
            short r11 = (short) r11
            r4[r8] = r11
            int r8 = r7 + 1
            int r11 = b(r0, r8, r5)
            short r13 = r4[r11]
            int r14 = r3 + r13
            short r10 = (short) r10
            r4[r11] = r10
            int r8 = r0.getInt(r8)
            int r10 = r0.getInt(r14)
            if (r8 == r10) goto L_0x00b6
            int r7 = r7 + 2
            int r8 = b(r0, r7, r5)
            int r7 = r9 + 1
            goto L_0x0047
        L_0x00b6:
            r7 = r9
            goto L_0x006e
        L_0x00b8:
            r7 = r10
            r10 = r11
            r8 = r13
            goto L_0x0048
        L_0x00bc:
            r9 = r3
        L_0x00bd:
            if (r9 >= r2) goto L_0x00c3
            int r2 = r2 - r9
            a(r0, r1, r2)
        L_0x00c3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Snappy.encode(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf, int):void");
    }

    private static int b(ByteBuf byteBuf, int i, int i2) {
        return (byteBuf.getInt(i) + 506832829) >>> i2;
    }

    private static short[] b(int i) {
        int i2 = 256;
        while (i2 < 16384 && i2 < i) {
            i2 <<= 1;
        }
        if (i2 <= 256) {
            return new short[256];
        }
        return new short[16384];
    }

    private static int a(ByteBuf byteBuf, int i, int i2, int i3) {
        int i4 = 0;
        while (i2 <= i3 - 4 && byteBuf.getInt(i2) == byteBuf.getInt(i + i4)) {
            i2 += 4;
            i4 += 4;
        }
        while (i2 < i3 && byteBuf.getByte(i + i4) == byteBuf.getByte(i2)) {
            i2++;
            i4++;
        }
        return i4;
    }

    private static int c(int i) {
        int highestOneBit = Integer.highestOneBit(i);
        int i2 = 0;
        while (true) {
            highestOneBit >>= 1;
            if (highestOneBit == 0) {
                return i2;
            }
            i2++;
        }
    }

    static void a(ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (i < 61) {
            byteBuf2.writeByte((i - 1) << 2);
        } else {
            int i2 = i - 1;
            int c = (c(i2) / 8) + 1;
            byteBuf2.writeByte((c + 59) << 2);
            for (int i3 = 0; i3 < c; i3++) {
                byteBuf2.writeByte((i2 >> (i3 * 8)) & 255);
            }
        }
        byteBuf2.writeBytes(byteBuf, i);
    }

    private static void c(ByteBuf byteBuf, int i, int i2) {
        if (i2 >= 12 || i >= 2048) {
            byteBuf.writeByte(((i2 - 1) << 2) | 2);
            byteBuf.writeByte(i & 255);
            byteBuf.writeByte((i >> 8) & 255);
            return;
        }
        byteBuf.writeByte(((i2 - 4) << 2) | 1 | ((i >> 8) << 5));
        byteBuf.writeByte(i & 255);
    }

    private static void d(ByteBuf byteBuf, int i, int i2) {
        while (i2 >= 68) {
            c(byteBuf, i, 64);
            i2 -= 64;
        }
        if (i2 > 64) {
            c(byteBuf, i, 60);
            i2 -= 60;
        }
        c(byteBuf, i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0073 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void decode(io.netty.buffer.ByteBuf r4, io.netty.buffer.ByteBuf r5) {
        /*
            r3 = this;
        L_0x0000:
            boolean r0 = r4.isReadable()
            if (r0 == 0) goto L_0x00a4
            int[] r0 = io.netty.handler.codec.compression.Snappy.AnonymousClass1.a
            io.netty.handler.codec.compression.Snappy$a r1 = r3.a
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = -1
            switch(r0) {
                case 1: goto L_0x0069;
                case 2: goto L_0x006d;
                case 3: goto L_0x0082;
                case 4: goto L_0x0056;
                case 5: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0000
        L_0x0015:
            byte r0 = r3.b
            r2 = r0 & 3
            switch(r2) {
                case 1: goto L_0x0043;
                case 2: goto L_0x0030;
                case 3: goto L_0x001d;
                default: goto L_0x001c;
            }
        L_0x001c:
            goto L_0x0000
        L_0x001d:
            int r2 = r3.c
            int r0 = c(r0, r4, r5, r2)
            if (r0 == r1) goto L_0x002f
            io.netty.handler.codec.compression.Snappy$a r1 = io.netty.handler.codec.compression.Snappy.a.READING_TAG
            r3.a = r1
            int r1 = r3.c
            int r1 = r1 + r0
            r3.c = r1
            goto L_0x0000
        L_0x002f:
            return
        L_0x0030:
            int r2 = r3.c
            int r0 = b(r0, r4, r5, r2)
            if (r0 == r1) goto L_0x0042
            io.netty.handler.codec.compression.Snappy$a r1 = io.netty.handler.codec.compression.Snappy.a.READING_TAG
            r3.a = r1
            int r1 = r3.c
            int r1 = r1 + r0
            r3.c = r1
            goto L_0x0000
        L_0x0042:
            return
        L_0x0043:
            int r2 = r3.c
            int r0 = a(r0, r4, r5, r2)
            if (r0 == r1) goto L_0x0055
            io.netty.handler.codec.compression.Snappy$a r1 = io.netty.handler.codec.compression.Snappy.a.READING_TAG
            r3.a = r1
            int r1 = r3.c
            int r1 = r1 + r0
            r3.c = r1
            goto L_0x0000
        L_0x0055:
            return
        L_0x0056:
            byte r0 = r3.b
            int r0 = a(r0, r4, r5)
            if (r0 == r1) goto L_0x0068
            io.netty.handler.codec.compression.Snappy$a r1 = io.netty.handler.codec.compression.Snappy.a.READING_TAG
            r3.a = r1
            int r1 = r3.c
            int r1 = r1 + r0
            r3.c = r1
            goto L_0x0000
        L_0x0068:
            return
        L_0x0069:
            io.netty.handler.codec.compression.Snappy$a r0 = io.netty.handler.codec.compression.Snappy.a.READING_PREAMBLE
            r3.a = r0
        L_0x006d:
            int r0 = b(r4)
            if (r0 != r1) goto L_0x0074
            return
        L_0x0074:
            if (r0 != 0) goto L_0x007b
            io.netty.handler.codec.compression.Snappy$a r4 = io.netty.handler.codec.compression.Snappy.a.READY
            r3.a = r4
            return
        L_0x007b:
            r5.ensureWritable(r0)
            io.netty.handler.codec.compression.Snappy$a r0 = io.netty.handler.codec.compression.Snappy.a.READING_TAG
            r3.a = r0
        L_0x0082:
            boolean r0 = r4.isReadable()
            if (r0 != 0) goto L_0x0089
            return
        L_0x0089:
            byte r0 = r4.readByte()
            r3.b = r0
            byte r0 = r3.b
            r0 = r0 & 3
            switch(r0) {
                case 0: goto L_0x009e;
                case 1: goto L_0x0098;
                case 2: goto L_0x0098;
                case 3: goto L_0x0098;
                default: goto L_0x0096;
            }
        L_0x0096:
            goto L_0x0000
        L_0x0098:
            io.netty.handler.codec.compression.Snappy$a r0 = io.netty.handler.codec.compression.Snappy.a.READING_COPY
            r3.a = r0
            goto L_0x0000
        L_0x009e:
            io.netty.handler.codec.compression.Snappy$a r0 = io.netty.handler.codec.compression.Snappy.a.READING_LITERAL
            r3.a = r0
            goto L_0x0000
        L_0x00a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Snappy.decode(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):void");
    }

    private static int b(ByteBuf byteBuf) {
        int i = 0;
        int i2 = 0;
        while (byteBuf.isReadable()) {
            short readUnsignedByte = byteBuf.readUnsignedByte();
            int i3 = i2 + 1;
            i |= (readUnsignedByte & 127) << (i2 * 7);
            if ((readUnsignedByte & 128) == 0) {
                return i;
            }
            if (i3 < 4) {
                i2 = i3;
            } else {
                throw new DecompressionException("Preamble is greater than 4 bytes");
            }
        }
        return 0;
    }

    static int a(byte b, ByteBuf byteBuf, ByteBuf byteBuf2) {
        byteBuf.markReaderIndex();
        int i = (b >> 2) & 63;
        switch (i) {
            case 60:
                if (byteBuf.isReadable()) {
                    i = byteBuf.readUnsignedByte();
                    break;
                } else {
                    return -1;
                }
            case 61:
                if (byteBuf.readableBytes() >= 2) {
                    i = byteBuf.readShortLE();
                    break;
                } else {
                    return -1;
                }
            case 62:
                if (byteBuf.readableBytes() >= 3) {
                    i = byteBuf.readUnsignedMediumLE();
                    break;
                } else {
                    return -1;
                }
            case 63:
                if (byteBuf.readableBytes() >= 4) {
                    i = byteBuf.readIntLE();
                    break;
                } else {
                    return -1;
                }
        }
        int i2 = i + 1;
        if (byteBuf.readableBytes() < i2) {
            byteBuf.resetReaderIndex();
            return -1;
        }
        byteBuf2.writeBytes(byteBuf, i2);
        return i2;
    }

    private static int a(byte b, ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (!byteBuf.isReadable()) {
            return -1;
        }
        int writerIndex = byteBuf2.writerIndex();
        int i2 = ((b & 28) >> 2) + 4;
        int readUnsignedByte = (((b & 224) << 8) >> 5) | byteBuf.readUnsignedByte();
        a(readUnsignedByte, i);
        byteBuf2.markReaderIndex();
        if (readUnsignedByte < i2) {
            for (int i3 = i2 / readUnsignedByte; i3 > 0; i3--) {
                byteBuf2.readerIndex(writerIndex - readUnsignedByte);
                byteBuf2.readBytes(byteBuf2, readUnsignedByte);
            }
            int i4 = i2 % readUnsignedByte;
            if (i4 != 0) {
                byteBuf2.readerIndex(writerIndex - readUnsignedByte);
                byteBuf2.readBytes(byteBuf2, i4);
            }
        } else {
            byteBuf2.readerIndex(writerIndex - readUnsignedByte);
            byteBuf2.readBytes(byteBuf2, i2);
        }
        byteBuf2.resetReaderIndex();
        return i2;
    }

    private static int b(byte b, ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (byteBuf.readableBytes() < 2) {
            return -1;
        }
        int writerIndex = byteBuf2.writerIndex();
        int i2 = ((b >> 2) & 63) + 1;
        short readShortLE = byteBuf.readShortLE();
        a(readShortLE, i);
        byteBuf2.markReaderIndex();
        if (readShortLE < i2) {
            for (int i3 = i2 / readShortLE; i3 > 0; i3--) {
                byteBuf2.readerIndex(writerIndex - readShortLE);
                byteBuf2.readBytes(byteBuf2, readShortLE);
            }
            int i4 = i2 % readShortLE;
            if (i4 != 0) {
                byteBuf2.readerIndex(writerIndex - readShortLE);
                byteBuf2.readBytes(byteBuf2, i4);
            }
        } else {
            byteBuf2.readerIndex(writerIndex - readShortLE);
            byteBuf2.readBytes(byteBuf2, i2);
        }
        byteBuf2.resetReaderIndex();
        return i2;
    }

    private static int c(byte b, ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (byteBuf.readableBytes() < 4) {
            return -1;
        }
        int writerIndex = byteBuf2.writerIndex();
        int i2 = ((b >> 2) & 63) + 1;
        int readIntLE = byteBuf.readIntLE();
        a(readIntLE, i);
        byteBuf2.markReaderIndex();
        if (readIntLE < i2) {
            for (int i3 = i2 / readIntLE; i3 > 0; i3--) {
                byteBuf2.readerIndex(writerIndex - readIntLE);
                byteBuf2.readBytes(byteBuf2, readIntLE);
            }
            int i4 = i2 % readIntLE;
            if (i4 != 0) {
                byteBuf2.readerIndex(writerIndex - readIntLE);
                byteBuf2.readBytes(byteBuf2, i4);
            }
        } else {
            byteBuf2.readerIndex(writerIndex - readIntLE);
            byteBuf2.readBytes(byteBuf2, i2);
        }
        byteBuf2.resetReaderIndex();
        return i2;
    }

    private static void a(int i, int i2) {
        if (i > 32767) {
            throw new DecompressionException("Offset exceeds maximum permissible value");
        } else if (i <= 0) {
            throw new DecompressionException("Offset is less than minimum permissible value");
        } else if (i > i2) {
            throw new DecompressionException("Offset exceeds size of chunk");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(ByteBuf byteBuf) {
        return a(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    static int a(ByteBuf byteBuf, int i, int i2) {
        m mVar = new m();
        try {
            if (byteBuf.hasArray()) {
                mVar.update(byteBuf.array(), byteBuf.arrayOffset() + i, i2);
            } else {
                byte[] bArr = new byte[i2];
                byteBuf.getBytes(i, bArr);
                mVar.update(bArr, 0, i2);
            }
            return a((int) mVar.getValue());
        } finally {
            mVar.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(int i, ByteBuf byteBuf, int i2, int i3) {
        int a2 = a(byteBuf, i2, i3);
        if (a2 != i) {
            throw new DecompressionException("mismatching checksum: " + Integer.toHexString(a2) + " (expected: " + Integer.toHexString(i) + ')');
        }
    }
}
