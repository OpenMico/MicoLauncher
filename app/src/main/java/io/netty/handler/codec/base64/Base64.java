package io.netty.handler.codec.base64;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/* loaded from: classes4.dex */
public final class Base64 {
    private static byte[] a(Base64Dialect base64Dialect) {
        if (base64Dialect != null) {
            return base64Dialect.alphabet;
        }
        throw new NullPointerException("dialect");
    }

    private static byte[] b(Base64Dialect base64Dialect) {
        if (base64Dialect != null) {
            return base64Dialect.decodabet;
        }
        throw new NullPointerException("dialect");
    }

    private static boolean c(Base64Dialect base64Dialect) {
        if (base64Dialect != null) {
            return base64Dialect.breakLinesByDefault;
        }
        throw new NullPointerException("dialect");
    }

    public static ByteBuf encode(ByteBuf byteBuf) {
        return encode(byteBuf, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, Base64Dialect base64Dialect) {
        return encode(byteBuf, c(base64Dialect), base64Dialect);
    }

    public static ByteBuf encode(ByteBuf byteBuf, boolean z) {
        return encode(byteBuf, z, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, boolean z, Base64Dialect base64Dialect) {
        if (byteBuf != null) {
            ByteBuf encode = encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), z, base64Dialect);
            byteBuf.readerIndex(byteBuf.writerIndex());
            return encode;
        }
        throw new NullPointerException("src");
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2) {
        return encode(byteBuf, i, i2, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, Base64Dialect base64Dialect) {
        return encode(byteBuf, i, i2, c(base64Dialect), base64Dialect);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, boolean z) {
        return encode(byteBuf, i, i2, z, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, boolean z, Base64Dialect base64Dialect) {
        return encode(byteBuf, i, i2, z, base64Dialect, byteBuf.alloc());
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, boolean z, Base64Dialect base64Dialect, ByteBufAllocator byteBufAllocator) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        } else if (base64Dialect != null) {
            int i3 = (i2 * 4) / 3;
            ByteBuf order = byteBufAllocator.buffer((i2 % 3 > 0 ? 4 : 0) + i3 + (z ? i3 / 76 : 0)).order(byteBuf.order());
            int i4 = i2 - 2;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            while (i5 < i4) {
                a(byteBuf, i5 + i, 3, order, i6, base64Dialect);
                i7 += 4;
                if (z && i7 == 76) {
                    order.setByte(i6 + 4, 10);
                    i6++;
                    i7 = 0;
                }
                i5 += 3;
                i6 += 4;
            }
            if (i5 < i2) {
                a(byteBuf, i5 + i, i2 - i5, order, i6, base64Dialect);
                i6 += 4;
            }
            if (i6 > 1 && order.getByte(i6 - 1) == 10) {
                i6--;
            }
            return order.slice(0, i6);
        } else {
            throw new NullPointerException("dialect");
        }
    }

    private static void a(ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2, int i3, Base64Dialect base64Dialect) {
        byte[] a = a(base64Dialect);
        int i4 = 0;
        int i5 = (i2 > 0 ? (byteBuf.getByte(i) << 24) >>> 8 : 0) | (i2 > 1 ? (byteBuf.getByte(i + 1) << 24) >>> 16 : 0);
        if (i2 > 2) {
            i4 = (byteBuf.getByte(i + 2) << 24) >>> 24;
        }
        int i6 = i5 | i4;
        switch (i2) {
            case 1:
                byteBuf2.setByte(i3, a[i6 >>> 18]);
                byteBuf2.setByte(i3 + 1, a[(i6 >>> 12) & 63]);
                byteBuf2.setByte(i3 + 2, 61);
                byteBuf2.setByte(i3 + 3, 61);
                return;
            case 2:
                byteBuf2.setByte(i3, a[i6 >>> 18]);
                byteBuf2.setByte(i3 + 1, a[(i6 >>> 12) & 63]);
                byteBuf2.setByte(i3 + 2, a[(i6 >>> 6) & 63]);
                byteBuf2.setByte(i3 + 3, 61);
                return;
            case 3:
                byteBuf2.setByte(i3, a[i6 >>> 18]);
                byteBuf2.setByte(i3 + 1, a[(i6 >>> 12) & 63]);
                byteBuf2.setByte(i3 + 2, a[(i6 >>> 6) & 63]);
                byteBuf2.setByte(i3 + 3, a[i6 & 63]);
                return;
            default:
                return;
        }
    }

    public static ByteBuf decode(ByteBuf byteBuf) {
        return decode(byteBuf, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf byteBuf, Base64Dialect base64Dialect) {
        if (byteBuf != null) {
            ByteBuf decode = decode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), base64Dialect);
            byteBuf.readerIndex(byteBuf.writerIndex());
            return decode;
        }
        throw new NullPointerException("src");
    }

    public static ByteBuf decode(ByteBuf byteBuf, int i, int i2) {
        return decode(byteBuf, i, i2, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf byteBuf, int i, int i2, Base64Dialect base64Dialect) {
        return decode(byteBuf, i, i2, base64Dialect, byteBuf.alloc());
    }

    public static ByteBuf decode(ByteBuf byteBuf, int i, int i2, Base64Dialect base64Dialect, ByteBufAllocator byteBufAllocator) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        } else if (base64Dialect != null) {
            byte[] b = b(base64Dialect);
            ByteBuf order = byteBufAllocator.buffer((i2 * 3) / 4).order(byteBuf.order());
            byte[] bArr = new byte[4];
            int i3 = 0;
            int i4 = 0;
            for (int i5 = i; i5 < i + i2; i5++) {
                byte b2 = (byte) (byteBuf.getByte(i5) & Byte.MAX_VALUE);
                byte b3 = b[b2];
                if (b3 >= -5) {
                    if (b3 >= -1) {
                        int i6 = i3 + 1;
                        bArr[i3] = b2;
                        if (i6 > 3) {
                            i4 += a(bArr, 0, order, i4, base64Dialect);
                            if (b2 == 61) {
                                break;
                            }
                            i3 = 0;
                        } else {
                            i3 = i6;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("bad Base64 input character at " + i5 + ": " + ((int) byteBuf.getUnsignedByte(i5)) + " (decimal)");
                }
            }
            return order.slice(0, i4);
        } else {
            throw new NullPointerException("dialect");
        }
    }

    private static int a(byte[] bArr, int i, ByteBuf byteBuf, int i2, Base64Dialect base64Dialect) {
        byte[] b = b(base64Dialect);
        int i3 = i + 2;
        if (bArr[i3] == 61) {
            byteBuf.setByte(i2, (byte) ((((b[bArr[i + 1]] & 255) << 12) | ((b[bArr[i]] & 255) << 18)) >>> 16));
            return 1;
        }
        int i4 = i + 3;
        if (bArr[i4] == 61) {
            int i5 = (b[bArr[i + 1]] & 255) << 12;
            int i6 = ((b[bArr[i3]] & 255) << 6) | i5 | ((b[bArr[i]] & 255) << 18);
            byteBuf.setByte(i2, (byte) (i6 >>> 16));
            byteBuf.setByte(i2 + 1, (byte) (i6 >>> 8));
            return 2;
        }
        try {
            int i7 = (b[bArr[i4]] & 255) | ((b[bArr[i + 1]] & 255) << 12) | ((b[bArr[i]] & 255) << 18) | ((b[bArr[i3]] & 255) << 6);
            byteBuf.setByte(i2, (byte) (i7 >> 16));
            byteBuf.setByte(i2 + 1, (byte) (i7 >> 8));
            byteBuf.setByte(i2 + 2, (byte) i7);
            return 3;
        } catch (IndexOutOfBoundsException unused) {
            throw new IllegalArgumentException("not encoded in Base64");
        }
    }

    private Base64() {
    }
}
