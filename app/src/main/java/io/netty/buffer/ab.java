package io.netty.buffer;

import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import kotlin.UShort;

/* compiled from: UnsafeByteBufUtil.java */
/* loaded from: classes4.dex */
public final class ab {
    private static final boolean a = PlatformDependent.isUnaligned();

    public static byte a(long j) {
        return PlatformDependent.getByte(j);
    }

    public static short b(long j) {
        if (a) {
            short s = PlatformDependent.getShort(j);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? s : Short.reverseBytes(s);
        }
        return (short) ((PlatformDependent.getByte(j + 1) & 255) | (PlatformDependent.getByte(j) << 8));
    }

    public static short c(long j) {
        if (a) {
            short s = PlatformDependent.getShort(j);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(s) : s;
        }
        return (short) ((PlatformDependent.getByte(j + 1) << 8) | (PlatformDependent.getByte(j) & 255));
    }

    public static int d(long j) {
        if (!a) {
            return (PlatformDependent.getByte(j + 2) & 255) | ((PlatformDependent.getByte(j) & 255) << 16) | ((PlatformDependent.getByte(1 + j) & 255) << 8);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return ((PlatformDependent.getShort(j + 1) & UShort.MAX_VALUE) << 8) | (PlatformDependent.getByte(j) & 255);
        } else {
            return (PlatformDependent.getByte(j + 2) & 255) | ((Short.reverseBytes(PlatformDependent.getShort(j)) & UShort.MAX_VALUE) << 8);
        }
    }

    public static int e(long j) {
        if (!a) {
            long j2 = j + 1;
            return ((PlatformDependent.getByte(j2) & 255) << 16) | (PlatformDependent.getByte(j) & 255) | ((PlatformDependent.getByte(j2) & 255) << 8);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return (PlatformDependent.getByte(j + 2) & 255) | ((Short.reverseBytes(PlatformDependent.getShort(j)) & UShort.MAX_VALUE) << 8);
        } else {
            return ((PlatformDependent.getShort(j + 1) & UShort.MAX_VALUE) << 8) | (PlatformDependent.getByte(j) & 255);
        }
    }

    public static int f(long j) {
        if (a) {
            int i = PlatformDependent.getInt(j);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? i : Integer.reverseBytes(i);
        }
        return (PlatformDependent.getByte(j + 3) & 255) | (PlatformDependent.getByte(j) << 24) | ((PlatformDependent.getByte(1 + j) & 255) << 16) | ((PlatformDependent.getByte(2 + j) & 255) << 8);
    }

    public static int g(long j) {
        if (a) {
            int i = PlatformDependent.getInt(j);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(i) : i;
        }
        return (PlatformDependent.getByte(j + 3) << 24) | (PlatformDependent.getByte(j) & 255) | ((PlatformDependent.getByte(1 + j) & 255) << 8) | ((PlatformDependent.getByte(2 + j) & 255) << 16);
    }

    public static long h(long j) {
        if (a) {
            long j2 = PlatformDependent.getLong(j);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? j2 : Long.reverseBytes(j2);
        }
        return (PlatformDependent.getByte(j + 7) & 255) | (PlatformDependent.getByte(j) << 56) | ((PlatformDependent.getByte(1 + j) & 255) << 48) | ((PlatformDependent.getByte(2 + j) & 255) << 40) | ((PlatformDependent.getByte(3 + j) & 255) << 32) | ((PlatformDependent.getByte(4 + j) & 255) << 24) | ((PlatformDependent.getByte(5 + j) & 255) << 16) | ((PlatformDependent.getByte(6 + j) & 255) << 8);
    }

    public static long i(long j) {
        if (a) {
            long j2 = PlatformDependent.getLong(j);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(j2) : j2;
        }
        return (PlatformDependent.getByte(j + 7) << 56) | (PlatformDependent.getByte(j) & 255) | ((PlatformDependent.getByte(1 + j) & 255) << 8) | ((PlatformDependent.getByte(2 + j) & 255) << 16) | ((PlatformDependent.getByte(3 + j) & 255) << 24) | ((PlatformDependent.getByte(4 + j) & 255) << 32) | ((PlatformDependent.getByte(5 + j) & 255) << 40) | ((255 & PlatformDependent.getByte(6 + j)) << 48);
    }

    public static void a(long j, int i) {
        PlatformDependent.putByte(j, (byte) i);
    }

    public static void b(long j, int i) {
        if (a) {
            PlatformDependent.putShort(j, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short) i : Short.reverseBytes((short) i));
            return;
        }
        PlatformDependent.putByte(j, (byte) (i >>> 8));
        PlatformDependent.putByte(j + 1, (byte) i);
    }

    public static void c(long j, int i) {
        if (a) {
            PlatformDependent.putShort(j, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) i) : (short) i);
            return;
        }
        PlatformDependent.putByte(j, (byte) i);
        PlatformDependent.putByte(j + 1, (byte) (i >>> 8));
    }

    public static void d(long j, int i) {
        if (!a) {
            PlatformDependent.putByte(j, (byte) (i >>> 16));
            PlatformDependent.putByte(1 + j, (byte) (i >>> 8));
            PlatformDependent.putByte(j + 2, (byte) i);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putByte(j, (byte) i);
            PlatformDependent.putShort(j + 1, (short) (i >>> 8));
        } else {
            PlatformDependent.putShort(j, Short.reverseBytes((short) (i >>> 8)));
            PlatformDependent.putByte(j + 2, (byte) i);
        }
    }

    public static void e(long j, int i) {
        if (!a) {
            PlatformDependent.putByte(j, (byte) i);
            PlatformDependent.putByte(1 + j, (byte) (i >>> 8));
            PlatformDependent.putByte(j + 2, (byte) (i >>> 16));
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putShort(j, Short.reverseBytes((short) (i >>> 8)));
            PlatformDependent.putByte(j + 2, (byte) i);
        } else {
            PlatformDependent.putByte(j, (byte) i);
            PlatformDependent.putShort(j + 1, (short) (i >>> 8));
        }
    }

    public static void f(long j, int i) {
        if (a) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i = Integer.reverseBytes(i);
            }
            PlatformDependent.putInt(j, i);
            return;
        }
        PlatformDependent.putByte(j, (byte) (i >>> 24));
        PlatformDependent.putByte(1 + j, (byte) (i >>> 16));
        PlatformDependent.putByte(2 + j, (byte) (i >>> 8));
        PlatformDependent.putByte(j + 3, (byte) i);
    }

    public static void g(long j, int i) {
        if (a) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i = Integer.reverseBytes(i);
            }
            PlatformDependent.putInt(j, i);
            return;
        }
        PlatformDependent.putByte(j, (byte) i);
        PlatformDependent.putByte(1 + j, (byte) (i >>> 8));
        PlatformDependent.putByte(2 + j, (byte) (i >>> 16));
        PlatformDependent.putByte(j + 3, (byte) (i >>> 24));
    }

    public static void a(long j, long j2) {
        if (a) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j2 = Long.reverseBytes(j2);
            }
            PlatformDependent.putLong(j, j2);
            return;
        }
        PlatformDependent.putByte(j, (byte) (j2 >>> 56));
        PlatformDependent.putByte(1 + j, (byte) (j2 >>> 48));
        PlatformDependent.putByte(2 + j, (byte) (j2 >>> 40));
        PlatformDependent.putByte(3 + j, (byte) (j2 >>> 32));
        PlatformDependent.putByte(4 + j, (byte) (j2 >>> 24));
        PlatformDependent.putByte(5 + j, (byte) (j2 >>> 16));
        PlatformDependent.putByte(6 + j, (byte) (j2 >>> 8));
        PlatformDependent.putByte(j + 7, (byte) j2);
    }

    public static void b(long j, long j2) {
        if (a) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j2 = Long.reverseBytes(j2);
            }
            PlatformDependent.putLong(j, j2);
            return;
        }
        PlatformDependent.putByte(j, (byte) j2);
        PlatformDependent.putByte(1 + j, (byte) (j2 >>> 8));
        PlatformDependent.putByte(2 + j, (byte) (j2 >>> 16));
        PlatformDependent.putByte(3 + j, (byte) (j2 >>> 24));
        PlatformDependent.putByte(4 + j, (byte) (j2 >>> 32));
        PlatformDependent.putByte(5 + j, (byte) (j2 >>> 40));
        PlatformDependent.putByte(6 + j, (byte) (j2 >>> 48));
        PlatformDependent.putByte(j + 7, (byte) (j2 >>> 56));
    }

    public static byte a(byte[] bArr, int i) {
        return PlatformDependent.getByte(bArr, i);
    }

    public static short b(byte[] bArr, int i) {
        if (a) {
            short s = PlatformDependent.getShort(bArr, i);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? s : Short.reverseBytes(s);
        }
        return (short) ((PlatformDependent.getByte(bArr, i + 1) & 255) | (PlatformDependent.getByte(bArr, i) << 8));
    }

    public static short c(byte[] bArr, int i) {
        if (!a) {
            return (short) ((PlatformDependent.getByte(i) & 255) | (PlatformDependent.getByte(i + 1) << 8));
        }
        short s = PlatformDependent.getShort(bArr, i);
        return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(s) : s;
    }

    public static int d(byte[] bArr, int i) {
        if (!a) {
            return (PlatformDependent.getByte(bArr, i + 2) & 255) | ((PlatformDependent.getByte(bArr, i) & 255) << 16) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 8);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return ((PlatformDependent.getShort(bArr, i + 1) & UShort.MAX_VALUE) << 8) | (PlatformDependent.getByte(bArr, i) & 255);
        } else {
            return (PlatformDependent.getByte(bArr, i + 2) & 255) | ((Short.reverseBytes(PlatformDependent.getShort(bArr, i)) & UShort.MAX_VALUE) << 8);
        }
    }

    public static int e(byte[] bArr, int i) {
        if (!a) {
            return ((PlatformDependent.getByte(bArr, i + 2) & 255) << 16) | (PlatformDependent.getByte(bArr, i) & 255) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 8);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return (PlatformDependent.getByte(bArr, i + 2) & 255) | ((Short.reverseBytes(PlatformDependent.getShort(bArr, i)) & UShort.MAX_VALUE) << 8);
        } else {
            return ((PlatformDependent.getShort(bArr, i + 1) & UShort.MAX_VALUE) << 8) | (PlatformDependent.getByte(bArr, i) & 255);
        }
    }

    public static int f(byte[] bArr, int i) {
        if (a) {
            int i2 = PlatformDependent.getInt(bArr, i);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? i2 : Integer.reverseBytes(i2);
        }
        return (PlatformDependent.getByte(bArr, i + 3) & 255) | (PlatformDependent.getByte(bArr, i) << 24) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 16) | ((PlatformDependent.getByte(bArr, i + 2) & 255) << 8);
    }

    public static int g(byte[] bArr, int i) {
        if (a) {
            int i2 = PlatformDependent.getInt(bArr, i);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(i2) : i2;
        }
        return (PlatformDependent.getByte(bArr, i + 3) << 24) | (PlatformDependent.getByte(bArr, i) & 255) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 8) | ((PlatformDependent.getByte(bArr, i + 2) & 255) << 16);
    }

    public static long h(byte[] bArr, int i) {
        if (a) {
            long j = PlatformDependent.getLong(bArr, i);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? j : Long.reverseBytes(j);
        }
        return (PlatformDependent.getByte(bArr, i + 7) & 255) | (PlatformDependent.getByte(bArr, i) << 56) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 48) | ((PlatformDependent.getByte(bArr, i + 2) & 255) << 40) | ((PlatformDependent.getByte(bArr, i + 3) & 255) << 32) | ((PlatformDependent.getByte(bArr, i + 4) & 255) << 24) | ((PlatformDependent.getByte(bArr, i + 5) & 255) << 16) | ((PlatformDependent.getByte(bArr, i + 6) & 255) << 8);
    }

    public static long i(byte[] bArr, int i) {
        if (a) {
            long j = PlatformDependent.getLong(bArr, i);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(j) : j;
        }
        return (PlatformDependent.getByte(bArr, i + 7) << 56) | (PlatformDependent.getByte(bArr, i) & 255) | ((PlatformDependent.getByte(bArr, i + 1) & 255) << 8) | ((PlatformDependent.getByte(bArr, i + 2) & 255) << 16) | ((PlatformDependent.getByte(bArr, i + 3) & 255) << 24) | ((PlatformDependent.getByte(bArr, i + 4) & 255) << 32) | ((PlatformDependent.getByte(bArr, i + 5) & 255) << 40) | ((255 & PlatformDependent.getByte(bArr, i + 6)) << 48);
    }

    public static void a(byte[] bArr, int i, int i2) {
        PlatformDependent.putByte(bArr, i, (byte) i2);
    }

    public static void b(byte[] bArr, int i, int i2) {
        if (a) {
            PlatformDependent.putShort(bArr, i, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short) i2 : Short.reverseBytes((short) i2));
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 1, (byte) i2);
    }

    public static void c(byte[] bArr, int i, int i2) {
        if (a) {
            PlatformDependent.putShort(bArr, i, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) i2) : (short) i2);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) i2);
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
    }

    public static void d(byte[] bArr, int i, int i2) {
        if (!a) {
            PlatformDependent.putByte(bArr, i, (byte) (i2 >>> 16));
            PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
            PlatformDependent.putByte(bArr, i + 2, (byte) i2);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putByte(bArr, i, (byte) i2);
            PlatformDependent.putShort(bArr, i + 1, (short) (i2 >>> 8));
        } else {
            PlatformDependent.putShort(bArr, i, Short.reverseBytes((short) (i2 >>> 8)));
            PlatformDependent.putByte(bArr, i + 2, (byte) i2);
        }
    }

    public static void e(byte[] bArr, int i, int i2) {
        if (!a) {
            PlatformDependent.putByte(bArr, i, (byte) i2);
            PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
            PlatformDependent.putByte(bArr, i + 2, (byte) (i2 >>> 16));
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putShort(bArr, i, Short.reverseBytes((short) (i2 >>> 8)));
            PlatformDependent.putByte(bArr, i + 2, (byte) i2);
        } else {
            PlatformDependent.putByte(bArr, i, (byte) i2);
            PlatformDependent.putShort(bArr, i + 1, (short) (i2 >>> 8));
        }
    }

    public static void f(byte[] bArr, int i, int i2) {
        if (a) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i2 = Integer.reverseBytes(i2);
            }
            PlatformDependent.putInt(bArr, i, i2);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) (i2 >>> 24));
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 16));
        PlatformDependent.putByte(bArr, i + 2, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 3, (byte) i2);
    }

    public static void g(byte[] bArr, int i, int i2) {
        if (a) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                i2 = Integer.reverseBytes(i2);
            }
            PlatformDependent.putInt(bArr, i, i2);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) i2);
        PlatformDependent.putByte(bArr, i + 1, (byte) (i2 >>> 8));
        PlatformDependent.putByte(bArr, i + 2, (byte) (i2 >>> 16));
        PlatformDependent.putByte(bArr, i + 3, (byte) (i2 >>> 24));
    }

    public static void a(byte[] bArr, int i, long j) {
        if (a) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j = Long.reverseBytes(j);
            }
            PlatformDependent.putLong(bArr, i, j);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) (j >>> 56));
        PlatformDependent.putByte(bArr, i + 1, (byte) (j >>> 48));
        PlatformDependent.putByte(bArr, i + 2, (byte) (j >>> 40));
        PlatformDependent.putByte(bArr, i + 3, (byte) (j >>> 32));
        PlatformDependent.putByte(bArr, i + 4, (byte) (j >>> 24));
        PlatformDependent.putByte(bArr, i + 5, (byte) (j >>> 16));
        PlatformDependent.putByte(bArr, i + 6, (byte) (j >>> 8));
        PlatformDependent.putByte(bArr, i + 7, (byte) j);
    }

    public static void b(byte[] bArr, int i, long j) {
        if (a) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                j = Long.reverseBytes(j);
            }
            PlatformDependent.putLong(bArr, i, j);
            return;
        }
        PlatformDependent.putByte(bArr, i, (byte) j);
        PlatformDependent.putByte(bArr, i + 1, (byte) (j >>> 8));
        PlatformDependent.putByte(bArr, i + 2, (byte) (j >>> 16));
        PlatformDependent.putByte(bArr, i + 3, (byte) (j >>> 24));
        PlatformDependent.putByte(bArr, i + 4, (byte) (j >>> 32));
        PlatformDependent.putByte(bArr, i + 5, (byte) (j >>> 40));
        PlatformDependent.putByte(bArr, i + 6, (byte) (j >>> 48));
        PlatformDependent.putByte(bArr, i + 7, (byte) (j >>> 56));
    }

    public static void h(byte[] bArr, int i, int i2) {
        if (i2 != 0) {
            PlatformDependent.setMemory(bArr, i, i2, (byte) 0);
        }
    }

    public static ByteBuf a(AbstractByteBuf abstractByteBuf, long j, int i, int i2) {
        abstractByteBuf.checkIndex(i, i2);
        ByteBuf directBuffer = abstractByteBuf.alloc().directBuffer(i2, abstractByteBuf.maxCapacity());
        if (i2 != 0) {
            if (directBuffer.hasMemoryAddress()) {
                PlatformDependent.copyMemory(j, directBuffer.memoryAddress(), i2);
                directBuffer.setIndex(0, i2);
            } else {
                directBuffer.writeBytes(abstractByteBuf, i, i2);
            }
        }
        return directBuffer;
    }

    public static int a(AbstractByteBuf abstractByteBuf, long j, int i, InputStream inputStream, int i2) throws IOException {
        abstractByteBuf.checkIndex(i, i2);
        ByteBuf heapBuffer = abstractByteBuf.alloc().heapBuffer(i2);
        try {
            byte[] array = heapBuffer.array();
            int arrayOffset = heapBuffer.arrayOffset();
            int read = inputStream.read(array, arrayOffset, i2);
            if (read > 0) {
                PlatformDependent.copyMemory(array, arrayOffset, j, read);
            }
            return read;
        } finally {
            heapBuffer.release();
        }
    }

    public static void a(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuf byteBuf, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(byteBuf, "dst");
        if (MathUtil.isOutOfBounds(i2, i3, byteBuf.capacity())) {
            throw new IndexOutOfBoundsException("dstIndex: " + i2);
        } else if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(j, byteBuf.memoryAddress() + i2, i3);
        } else if (byteBuf.hasArray()) {
            PlatformDependent.copyMemory(j, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.setBytes(i2, abstractByteBuf, i, i3);
        }
    }

    public static void a(AbstractByteBuf abstractByteBuf, long j, int i, byte[] bArr, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(bArr, "dst");
        if (MathUtil.isOutOfBounds(i2, i3, bArr.length)) {
            throw new IndexOutOfBoundsException("dstIndex: " + i2);
        } else if (i3 != 0) {
            PlatformDependent.copyMemory(j, bArr, i2, i3);
        }
    }

    public static void a(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuffer byteBuffer) {
        abstractByteBuf.checkIndex(i);
        int min = Math.min(abstractByteBuf.capacity() - i, byteBuffer.remaining());
        if (min != 0) {
            if (byteBuffer.isDirect()) {
                if (!byteBuffer.isReadOnly()) {
                    PlatformDependent.copyMemory(j, PlatformDependent.directBufferAddress(byteBuffer) + byteBuffer.position(), min);
                    byteBuffer.position(byteBuffer.position() + min);
                    return;
                }
                throw new ReadOnlyBufferException();
            } else if (byteBuffer.hasArray()) {
                PlatformDependent.copyMemory(j, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), min);
                byteBuffer.position(byteBuffer.position() + min);
            } else {
                byteBuffer.put(abstractByteBuf.nioBuffer());
            }
        }
    }

    public static void b(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuf byteBuf, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        ObjectUtil.checkNotNull(byteBuf, "src");
        if (MathUtil.isOutOfBounds(i2, i3, byteBuf.capacity())) {
            throw new IndexOutOfBoundsException("srcIndex: " + i2);
        } else if (i3 == 0) {
        } else {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(byteBuf.memoryAddress() + i2, j, i3);
            } else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(byteBuf.array(), byteBuf.arrayOffset() + i2, j, i3);
            } else {
                byteBuf.getBytes(i2, abstractByteBuf, i, i3);
            }
        }
    }

    public static void b(AbstractByteBuf abstractByteBuf, long j, int i, byte[] bArr, int i2, int i3) {
        abstractByteBuf.checkIndex(i, i3);
        if (i3 != 0) {
            PlatformDependent.copyMemory(bArr, i2, j, i3);
        }
    }

    public static void b(AbstractByteBuf abstractByteBuf, long j, int i, ByteBuffer byteBuffer) {
        abstractByteBuf.checkIndex(i, byteBuffer.remaining());
        int remaining = byteBuffer.remaining();
        if (remaining != 0) {
            if (byteBuffer.isDirect()) {
                PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(byteBuffer) + byteBuffer.position(), j, byteBuffer.remaining());
                byteBuffer.position(byteBuffer.position() + remaining);
            } else if (byteBuffer.hasArray()) {
                PlatformDependent.copyMemory(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), j, remaining);
                byteBuffer.position(byteBuffer.position() + remaining);
            } else {
                ByteBuf heapBuffer = abstractByteBuf.alloc().heapBuffer(remaining);
                try {
                    byte[] array = heapBuffer.array();
                    byteBuffer.get(array, heapBuffer.arrayOffset(), remaining);
                    PlatformDependent.copyMemory(array, heapBuffer.arrayOffset(), j, remaining);
                } finally {
                    heapBuffer.release();
                }
            }
        }
    }

    public static void a(AbstractByteBuf abstractByteBuf, long j, int i, OutputStream outputStream, int i2) throws IOException {
        abstractByteBuf.checkIndex(i, i2);
        if (i2 != 0) {
            ByteBuf heapBuffer = abstractByteBuf.alloc().heapBuffer(i2);
            try {
                byte[] array = heapBuffer.array();
                int arrayOffset = heapBuffer.arrayOffset();
                PlatformDependent.copyMemory(j, array, arrayOffset, i2);
                outputStream.write(array, arrayOffset, i2);
            } finally {
                heapBuffer.release();
            }
        }
    }

    public static void b(AbstractByteBuf abstractByteBuf, long j, int i, int i2) {
        if (i2 != 0) {
            abstractByteBuf.checkIndex(i, i2);
            PlatformDependent.setMemory(j, i2, (byte) 0);
        }
    }

    public static UnpooledUnsafeDirectByteBuf a(ByteBufAllocator byteBufAllocator, int i, int i2) {
        if (PlatformDependent.useDirectBufferNoCleaner()) {
            return new z(byteBufAllocator, i, i2);
        }
        return new UnpooledUnsafeDirectByteBuf(byteBufAllocator, i, i2);
    }
}
