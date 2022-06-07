package org.eclipse.jetty.io;

import com.google.android.exoplayer2.C;
import okhttp3.internal.connection.RealConnection;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes5.dex */
public class BufferUtil {
    static final byte MINUS = 45;
    static final byte SPACE = 32;
    static final byte[] DIGIT = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    private static final int[] decDivisors = {1000000000, 100000000, 10000000, 1000000, 100000, 10000, 1000, 100, 10, 1};
    private static final int[] hexDivisors = {268435456, 16777216, 1048576, 65536, 4096, 256, 16, 1};
    private static final long[] decDivisorsL = {1000000000000000000L, 100000000000000000L, 10000000000000000L, 1000000000000000L, 100000000000000L, 10000000000000L, 1000000000000L, 100000000000L, RealConnection.IDLE_CONNECTION_HEALTHY_NS, C.NANOS_PER_SECOND, 100000000, 10000000, 1000000, 100000, 10000, 1000, 100, 10, 1};

    public static int toInt(Buffer buffer) {
        boolean z = false;
        int i = 0;
        boolean z2 = false;
        for (int index = buffer.getIndex(); index < buffer.putIndex(); index++) {
            byte peek = buffer.peek(index);
            if (peek > 32) {
                if (peek < 48 || peek > 57) {
                    if (peek != 45 || z) {
                        break;
                    }
                    z2 = true;
                } else {
                    i = (i * 10) + (peek - 48);
                    z = true;
                }
            } else if (z) {
                break;
            }
        }
        if (z) {
            return z2 ? -i : i;
        }
        throw new NumberFormatException(buffer.toString());
    }

    public static long toLong(Buffer buffer) {
        boolean z = false;
        long j = 0;
        boolean z2 = false;
        for (int index = buffer.getIndex(); index < buffer.putIndex(); index++) {
            byte peek = buffer.peek(index);
            if (peek > 32) {
                if (peek < 48 || peek > 57) {
                    if (peek != 45 || z) {
                        break;
                    }
                    z2 = true;
                } else {
                    j = (j * 10) + (peek - 48);
                    z = true;
                }
            } else if (z) {
                break;
            }
        }
        if (z) {
            return z2 ? -j : j;
        }
        throw new NumberFormatException(buffer.toString());
    }

    public static void putHexInt(Buffer buffer, int i) {
        if (i < 0) {
            buffer.put(MINUS);
            if (i == Integer.MIN_VALUE) {
                buffer.put((byte) 56);
                buffer.put((byte) 48);
                buffer.put((byte) 48);
                buffer.put((byte) 48);
                buffer.put((byte) 48);
                buffer.put((byte) 48);
                buffer.put((byte) 48);
                buffer.put((byte) 48);
                return;
            }
            i = -i;
        }
        if (i < 16) {
            buffer.put(DIGIT[i]);
            return;
        }
        int i2 = 0;
        boolean z = false;
        while (true) {
            int[] iArr = hexDivisors;
            if (i2 < iArr.length) {
                if (i >= iArr[i2]) {
                    int i3 = i / iArr[i2];
                    buffer.put(DIGIT[i3]);
                    i -= i3 * hexDivisors[i2];
                    z = true;
                } else if (z) {
                    buffer.put((byte) 48);
                }
                i2++;
            } else {
                return;
            }
        }
    }

    public static void prependHexInt(Buffer buffer, int i) {
        if (i == 0) {
            int index = buffer.getIndex() - 1;
            buffer.poke(index, (byte) 48);
            buffer.setGetIndex(index);
            return;
        }
        boolean z = false;
        if (i < 0) {
            z = true;
            i = -i;
        }
        int index2 = buffer.getIndex();
        while (i > 0) {
            int i2 = i & 15;
            i >>= 4;
            index2--;
            buffer.poke(index2, DIGIT[i2]);
        }
        if (z) {
            index2--;
            buffer.poke(index2, MINUS);
        }
        buffer.setGetIndex(index2);
    }

    public static void putDecInt(Buffer buffer, int i) {
        if (i < 0) {
            buffer.put(MINUS);
            if (i == Integer.MIN_VALUE) {
                buffer.put((byte) 50);
                i = 147483648;
            } else {
                i = -i;
            }
        }
        if (i < 10) {
            buffer.put(DIGIT[i]);
            return;
        }
        int i2 = 0;
        boolean z = false;
        while (true) {
            int[] iArr = decDivisors;
            if (i2 < iArr.length) {
                if (i >= iArr[i2]) {
                    int i3 = i / iArr[i2];
                    buffer.put(DIGIT[i3]);
                    i -= i3 * decDivisors[i2];
                    z = true;
                } else if (z) {
                    buffer.put((byte) 48);
                }
                i2++;
            } else {
                return;
            }
        }
    }

    public static void putDecLong(Buffer buffer, long j) {
        if (j < 0) {
            buffer.put(MINUS);
            if (j == Long.MIN_VALUE) {
                buffer.put((byte) 57);
                j = 223372036854775808L;
            } else {
                j = -j;
            }
        }
        if (j < 10) {
            buffer.put(DIGIT[(int) j]);
            return;
        }
        int i = 0;
        boolean z = false;
        while (true) {
            long[] jArr = decDivisorsL;
            if (i < jArr.length) {
                if (j >= jArr[i]) {
                    long j2 = j / jArr[i];
                    buffer.put(DIGIT[(int) j2]);
                    j -= j2 * decDivisorsL[i];
                    z = true;
                } else if (z) {
                    buffer.put((byte) 48);
                }
                i++;
            } else {
                return;
            }
        }
    }

    public static Buffer toBuffer(long j) {
        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(32);
        putDecLong(byteArrayBuffer, j);
        return byteArrayBuffer;
    }

    public static void putCRLF(Buffer buffer) {
        buffer.put((byte) 13);
        buffer.put((byte) 10);
    }

    public static boolean isPrefix(Buffer buffer, Buffer buffer2) {
        if (buffer.length() > buffer2.length()) {
            return false;
        }
        int index = buffer2.getIndex();
        for (int index2 = buffer.getIndex(); index2 < buffer.putIndex(); index2++) {
            index++;
            if (buffer.peek(index2) != buffer2.peek(index)) {
                return false;
            }
        }
        return true;
    }

    public static String to8859_1_String(Buffer buffer) {
        if (buffer instanceof BufferCache.CachedBuffer) {
            return buffer.toString();
        }
        return buffer.toString(StringUtil.__ISO_8859_1_CHARSET);
    }
}
