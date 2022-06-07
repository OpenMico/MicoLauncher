package io.netty.buffer;

import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.Recycler;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class ByteBufUtil {
    static final ByteBufAllocator a;
    private static final int d;
    private static final int e;
    private static final InternalLogger b = InternalLoggerFactory.getInstance(ByteBufUtil.class);
    private static final FastThreadLocal<CharBuffer> c = new FastThreadLocal<CharBuffer>() { // from class: io.netty.buffer.ByteBufUtil.1
        /* renamed from: a */
        public CharBuffer initialValue() throws Exception {
            return CharBuffer.allocate(1024);
        }
    };
    private static final int f = (int) CharsetUtil.encoder(CharsetUtil.UTF_8).maxBytesPerChar();

    public static int swapMedium(int i) {
        int i2 = ((i >>> 16) & 255) | ((i << 16) & 16711680) | (65280 & i);
        return (8388608 & i2) != 0 ? i2 | ViewCompat.MEASURED_STATE_MASK : i2;
    }

    static {
        ByteBufAllocator byteBufAllocator;
        String trim = SystemPropertyUtil.get("io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled").toLowerCase(Locale.US).trim();
        if ("unpooled".equals(trim)) {
            byteBufAllocator = UnpooledByteBufAllocator.DEFAULT;
            b.debug("-Dio.netty.allocator.type: {}", trim);
        } else if ("pooled".equals(trim)) {
            byteBufAllocator = PooledByteBufAllocator.DEFAULT;
            b.debug("-Dio.netty.allocator.type: {}", trim);
        } else {
            byteBufAllocator = PooledByteBufAllocator.DEFAULT;
            b.debug("-Dio.netty.allocator.type: pooled (unknown: {})", trim);
        }
        a = byteBufAllocator;
        e = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 65536);
        b.debug("-Dio.netty.threadLocalDirectBufferSize: {}", Integer.valueOf(e));
        d = SystemPropertyUtil.getInt("io.netty.maxThreadLocalCharBufferSize", 16384);
        b.debug("-Dio.netty.maxThreadLocalCharBufferSize: {}", Integer.valueOf(d));
    }

    public static String hexDump(ByteBuf byteBuf) {
        return hexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static String hexDump(ByteBuf byteBuf, int i, int i2) {
        return a.c(byteBuf, i, i2);
    }

    public static String hexDump(byte[] bArr) {
        return hexDump(bArr, 0, bArr.length);
    }

    public static String hexDump(byte[] bArr, int i, int i2) {
        return a.b(bArr, i, i2);
    }

    public static int hashCode(ByteBuf byteBuf) {
        int i;
        int i2;
        int readableBytes = byteBuf.readableBytes();
        int i3 = readableBytes >>> 2;
        int readerIndex = byteBuf.readerIndex();
        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
            i = readerIndex;
            i2 = 1;
            while (i3 > 0) {
                i2 = (i2 * 31) + byteBuf.getInt(i);
                i += 4;
                i3--;
            }
        } else {
            i = readerIndex;
            i2 = 1;
            while (i3 > 0) {
                i2 = (i2 * 31) + swapInt(byteBuf.getInt(i));
                i += 4;
                i3--;
            }
        }
        for (int i4 = readableBytes & 3; i4 > 0; i4--) {
            i++;
            i2 = (i2 * 31) + byteBuf.getByte(i);
        }
        if (i2 == 0) {
            return 1;
        }
        return i2;
    }

    public static boolean equals(ByteBuf byteBuf, int i, ByteBuf byteBuf2, int i2, int i3) {
        if (i < 0 || i2 < 0 || i3 < 0) {
            throw new IllegalArgumentException("All indexes and lengths must be non-negative");
        } else if (byteBuf.writerIndex() - i3 < i || byteBuf2.writerIndex() - i3 < i2) {
            return false;
        } else {
            int i4 = i3 >>> 3;
            if (byteBuf.order() == byteBuf2.order()) {
                while (i4 > 0) {
                    if (byteBuf.getLong(i) != byteBuf2.getLong(i2)) {
                        return false;
                    }
                    i += 8;
                    i2 += 8;
                    i4--;
                }
            } else {
                while (i4 > 0) {
                    if (byteBuf.getLong(i) != swapLong(byteBuf2.getLong(i2))) {
                        return false;
                    }
                    i += 8;
                    i2 += 8;
                    i4--;
                }
            }
            for (int i5 = i3 & 7; i5 > 0; i5--) {
                if (byteBuf.getByte(i) != byteBuf2.getByte(i2)) {
                    return false;
                }
                i++;
                i2++;
            }
            return true;
        }
    }

    public static boolean equals(ByteBuf byteBuf, ByteBuf byteBuf2) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != byteBuf2.readableBytes()) {
            return false;
        }
        return equals(byteBuf, byteBuf.readerIndex(), byteBuf2, byteBuf2.readerIndex(), readableBytes);
    }

    public static int compare(ByteBuf byteBuf, ByteBuf byteBuf2) {
        int readableBytes = byteBuf.readableBytes();
        int readableBytes2 = byteBuf2.readableBytes();
        int min = Math.min(readableBytes, readableBytes2);
        int i = min >>> 2;
        int readerIndex = byteBuf.readerIndex();
        int readerIndex2 = byteBuf2.readerIndex();
        if (byteBuf.order() == byteBuf2.order()) {
            while (i > 0) {
                int i2 = (byteBuf.getUnsignedInt(readerIndex) > byteBuf2.getUnsignedInt(readerIndex2) ? 1 : (byteBuf.getUnsignedInt(readerIndex) == byteBuf2.getUnsignedInt(readerIndex2) ? 0 : -1));
                if (i2 > 0) {
                    return 1;
                }
                if (i2 < 0) {
                    return -1;
                }
                readerIndex += 4;
                readerIndex2 += 4;
                i--;
            }
        } else {
            while (i > 0) {
                int i3 = (byteBuf.getUnsignedInt(readerIndex) > (swapInt(byteBuf2.getInt(readerIndex2)) & 4294967295L) ? 1 : (byteBuf.getUnsignedInt(readerIndex) == (swapInt(byteBuf2.getInt(readerIndex2)) & 4294967295L) ? 0 : -1));
                if (i3 > 0) {
                    return 1;
                }
                if (i3 < 0) {
                    return -1;
                }
                readerIndex += 4;
                readerIndex2 += 4;
                i--;
            }
        }
        for (int i4 = min & 3; i4 > 0; i4--) {
            short unsignedByte = byteBuf.getUnsignedByte(readerIndex);
            short unsignedByte2 = byteBuf2.getUnsignedByte(readerIndex2);
            if (unsignedByte > unsignedByte2) {
                return 1;
            }
            if (unsignedByte < unsignedByte2) {
                return -1;
            }
            readerIndex++;
            readerIndex2++;
        }
        return readableBytes - readableBytes2;
    }

    public static int indexOf(ByteBuf byteBuf, int i, int i2, byte b2) {
        if (i <= i2) {
            return a(byteBuf, i, i2, b2);
        }
        return b(byteBuf, i, i2, b2);
    }

    public static short swapShort(short s) {
        return Short.reverseBytes(s);
    }

    public static int swapInt(int i) {
        return Integer.reverseBytes(i);
    }

    public static long swapLong(long j) {
        return Long.reverseBytes(j);
    }

    public static ByteBuf readBytes(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, int i) {
        ByteBuf buffer = byteBufAllocator.buffer(i);
        try {
            byteBuf.readBytes(buffer);
            return buffer;
        } catch (Throwable th) {
            buffer.release();
            throw th;
        }
    }

    private static int a(ByteBuf byteBuf, int i, int i2, byte b2) {
        int max = Math.max(i, 0);
        if (max >= i2 || byteBuf.capacity() == 0) {
            return -1;
        }
        return byteBuf.forEachByte(max, i2 - max, new ByteProcessor.IndexOfProcessor(b2));
    }

    private static int b(ByteBuf byteBuf, int i, int i2, byte b2) {
        int min = Math.min(i, byteBuf.capacity());
        if (min < 0 || byteBuf.capacity() == 0) {
            return -1;
        }
        return byteBuf.forEachByteDesc(i2, min - i2, new ByteProcessor.IndexOfProcessor(b2));
    }

    public static ByteBuf writeUtf8(ByteBufAllocator byteBufAllocator, CharSequence charSequence) {
        ByteBuf buffer = byteBufAllocator.buffer(utf8MaxBytes(charSequence));
        writeUtf8(buffer, charSequence);
        return buffer;
    }

    public static int writeUtf8(ByteBuf byteBuf, CharSequence charSequence) {
        int length = charSequence.length();
        byteBuf.ensureWritable(utf8MaxBytes(charSequence));
        while (!(byteBuf instanceof AbstractByteBuf)) {
            if (byteBuf instanceof ae) {
                byteBuf = byteBuf.unwrap();
            } else {
                byte[] bytes = charSequence.toString().getBytes(CharsetUtil.UTF_8);
                byteBuf.writeBytes(bytes);
                return bytes.length;
            }
        }
        AbstractByteBuf abstractByteBuf = (AbstractByteBuf) byteBuf;
        int a2 = a(abstractByteBuf, abstractByteBuf.c, charSequence, length);
        abstractByteBuf.c += a2;
        return a2;
    }

    public static int a(AbstractByteBuf abstractByteBuf, int i, CharSequence charSequence, int i2) {
        int i3;
        int i4 = 0;
        int i5 = i;
        while (i4 < i2) {
            char charAt = charSequence.charAt(i4);
            if (charAt < 128) {
                i5++;
                abstractByteBuf._setByte(i5, (byte) charAt);
            } else if (charAt < 2048) {
                int i6 = i5 + 1;
                abstractByteBuf._setByte(i5, (byte) ((charAt >> 6) | 192));
                i5 = i6 + 1;
                abstractByteBuf._setByte(i6, (byte) ((charAt & '?') | 128));
            } else if (!StringUtil.isSurrogate(charAt)) {
                int i7 = i5 + 1;
                abstractByteBuf._setByte(i5, (byte) ((charAt >> '\f') | 224));
                int i8 = i7 + 1;
                abstractByteBuf._setByte(i7, (byte) ((63 & (charAt >> 6)) | 128));
                i5 = i8 + 1;
                abstractByteBuf._setByte(i8, (byte) ((charAt & '?') | 128));
            } else if (!Character.isHighSurrogate(charAt)) {
                i5++;
                abstractByteBuf._setByte(i5, 63);
            } else {
                i4++;
                try {
                    char charAt2 = charSequence.charAt(i4);
                    if (!Character.isLowSurrogate(charAt2)) {
                        int i9 = i5 + 1;
                        abstractByteBuf._setByte(i5, 63);
                        i5 = i9 + 1;
                        if (Character.isHighSurrogate(charAt2)) {
                            charAt2 = '?';
                        }
                        abstractByteBuf._setByte(i9, charAt2);
                    } else {
                        int codePoint = Character.toCodePoint(charAt, charAt2);
                        int i10 = i5 + 1;
                        abstractByteBuf._setByte(i5, (byte) ((codePoint >> 18) | PsExtractor.VIDEO_STREAM_MASK));
                        int i11 = i10 + 1;
                        abstractByteBuf._setByte(i10, (byte) (((codePoint >> 12) & 63) | 128));
                        int i12 = i11 + 1;
                        abstractByteBuf._setByte(i11, (byte) (((codePoint >> 6) & 63) | 128));
                        i5 = i12 + 1;
                        abstractByteBuf._setByte(i12, (byte) ((codePoint & 63) | 128));
                    }
                } catch (IndexOutOfBoundsException unused) {
                    i3 = i5 + 1;
                    abstractByteBuf._setByte(i5, 63);
                }
            }
            i4++;
        }
        i3 = i5;
        return i3 - i;
    }

    public static int utf8MaxBytes(CharSequence charSequence) {
        return charSequence.length() * f;
    }

    public static ByteBuf writeAscii(ByteBufAllocator byteBufAllocator, CharSequence charSequence) {
        ByteBuf buffer = byteBufAllocator.buffer(charSequence.length());
        writeAscii(buffer, charSequence);
        return buffer;
    }

    public static int writeAscii(ByteBuf byteBuf, CharSequence charSequence) {
        int length = charSequence.length();
        byteBuf.ensureWritable(length);
        if (charSequence instanceof AsciiString) {
            AsciiString asciiString = (AsciiString) charSequence;
            byteBuf.writeBytes(asciiString.array(), asciiString.arrayOffset(), asciiString.length());
            return length;
        }
        while (!(byteBuf instanceof AbstractByteBuf)) {
            if (byteBuf instanceof ae) {
                byteBuf = byteBuf.unwrap();
            } else {
                byteBuf.writeBytes(charSequence.toString().getBytes(CharsetUtil.US_ASCII));
            }
        }
        AbstractByteBuf abstractByteBuf = (AbstractByteBuf) byteBuf;
        int b2 = b(abstractByteBuf, abstractByteBuf.c, charSequence, length);
        abstractByteBuf.c += b2;
        return b2;
    }

    public static int b(AbstractByteBuf abstractByteBuf, int i, CharSequence charSequence, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            i++;
            abstractByteBuf._setByte(i, (byte) charSequence.charAt(i3));
        }
        return i2;
    }

    public static ByteBuf encodeString(ByteBufAllocator byteBufAllocator, CharBuffer charBuffer, Charset charset) {
        return a(byteBufAllocator, false, charBuffer, charset, 0);
    }

    public static ByteBuf encodeString(ByteBufAllocator byteBufAllocator, CharBuffer charBuffer, Charset charset, int i) {
        return a(byteBufAllocator, false, charBuffer, charset, i);
    }

    public static ByteBuf a(ByteBufAllocator byteBufAllocator, boolean z, CharBuffer charBuffer, Charset charset, int i) {
        ByteBuf byteBuf;
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        int remaining = ((int) (charBuffer.remaining() * encoder.maxBytesPerChar())) + i;
        if (z) {
            byteBuf = byteBufAllocator.heapBuffer(remaining);
        } else {
            byteBuf = byteBufAllocator.buffer(remaining);
        }
        try {
            try {
                ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(0, remaining);
                int position = internalNioBuffer.position();
                CoderResult encode = encoder.encode(charBuffer, internalNioBuffer, true);
                if (!encode.isUnderflow()) {
                    encode.throwException();
                }
                CoderResult flush = encoder.flush(internalNioBuffer);
                if (!flush.isUnderflow()) {
                    flush.throwException();
                }
                byteBuf.writerIndex((byteBuf.writerIndex() + internalNioBuffer.position()) - position);
                return byteBuf;
            } catch (CharacterCodingException e2) {
                throw new IllegalStateException(e2);
            }
        } catch (Throwable th) {
            byteBuf.release();
            throw th;
        }
    }

    public static String a(ByteBuf byteBuf, int i, int i2, Charset charset) {
        if (i2 == 0) {
            return "";
        }
        CharsetDecoder decoder = CharsetUtil.decoder(charset);
        int maxCharsPerByte = (int) (i2 * decoder.maxCharsPerByte());
        CharBuffer charBuffer = c.get();
        if (charBuffer.length() < maxCharsPerByte) {
            charBuffer = CharBuffer.allocate(maxCharsPerByte);
            if (maxCharsPerByte <= d) {
                c.set(charBuffer);
            }
        } else {
            charBuffer.clear();
        }
        if (byteBuf.nioBufferCount() == 1) {
            a(decoder, byteBuf.internalNioBuffer(i, i2), charBuffer);
        } else {
            ByteBuf heapBuffer = byteBuf.alloc().heapBuffer(i2);
            try {
                heapBuffer.writeBytes(byteBuf, i, i2);
                a(decoder, heapBuffer.internalNioBuffer(0, i2), charBuffer);
            } finally {
                heapBuffer.release();
            }
        }
        return charBuffer.flip().toString();
    }

    private static void a(CharsetDecoder charsetDecoder, ByteBuffer byteBuffer, CharBuffer charBuffer) {
        try {
            CoderResult decode = charsetDecoder.decode(byteBuffer, charBuffer, true);
            if (!decode.isUnderflow()) {
                decode.throwException();
            }
            CoderResult flush = charsetDecoder.flush(charBuffer);
            if (!flush.isUnderflow()) {
                flush.throwException();
            }
        } catch (CharacterCodingException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public static ByteBuf threadLocalDirectBuffer() {
        if (e <= 0) {
            return null;
        }
        if (PlatformDependent.hasUnsafe()) {
            return c.b();
        }
        return b.b();
    }

    public static byte[] getBytes(ByteBuf byteBuf) {
        return getBytes(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static byte[] getBytes(ByteBuf byteBuf, int i, int i2) {
        return getBytes(byteBuf, i, i2, true);
    }

    public static byte[] getBytes(ByteBuf byteBuf, int i, int i2, boolean z) {
        if (MathUtil.isOutOfBounds(i, i2, byteBuf.capacity())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= start + length(" + i2 + ") <= buf.capacity(" + byteBuf.capacity() + ')');
        } else if (!byteBuf.hasArray()) {
            byte[] bArr = new byte[i2];
            byteBuf.getBytes(i, bArr);
            return bArr;
        } else if (!z && i == 0 && i2 == byteBuf.capacity()) {
            return byteBuf.array();
        } else {
            int arrayOffset = byteBuf.arrayOffset() + i;
            return Arrays.copyOfRange(byteBuf.array(), arrayOffset, i2 + arrayOffset);
        }
    }

    public static void copy(AsciiString asciiString, int i, ByteBuf byteBuf, int i2, int i3) {
        if (!MathUtil.isOutOfBounds(i, i3, asciiString.length())) {
            ((ByteBuf) ObjectUtil.checkNotNull(byteBuf, "dst")).setBytes(i2, asciiString.array(), i + asciiString.arrayOffset(), i3);
            return;
        }
        throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + i + ") <= srcIdx + length(" + i3 + ") <= srcLen(" + asciiString.length() + ')');
    }

    public static void copy(AsciiString asciiString, int i, ByteBuf byteBuf, int i2) {
        if (!MathUtil.isOutOfBounds(i, i2, asciiString.length())) {
            ((ByteBuf) ObjectUtil.checkNotNull(byteBuf, "dst")).writeBytes(asciiString.array(), i + asciiString.arrayOffset(), i2);
            return;
        }
        throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + i + ") <= srcIdx + length(" + i2 + ") <= srcLen(" + asciiString.length() + ')');
    }

    public static String prettyHexDump(ByteBuf byteBuf) {
        return prettyHexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static String prettyHexDump(ByteBuf byteBuf, int i, int i2) {
        return a.d(byteBuf, i, i2);
    }

    public static void appendPrettyHexDump(StringBuilder sb, ByteBuf byteBuf) {
        appendPrettyHexDump(sb, byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static void appendPrettyHexDump(StringBuilder sb, ByteBuf byteBuf, int i, int i2) {
        a.b(sb, byteBuf, i, i2);
    }

    /* loaded from: classes4.dex */
    public static final class a {
        private static final char[] a = new char[256];
        private static final char[] b = new char[1024];
        private static final String[] c = new String[16];
        private static final String[] d = new String[4096];
        private static final String[] e = new String[256];
        private static final String[] f = new String[16];

        static {
            char[] charArray = "0123456789abcdef".toCharArray();
            int i = 0;
            for (int i2 = 0; i2 < 256; i2++) {
                char[] cArr = b;
                int i3 = i2 << 1;
                cArr[i3] = charArray[(i2 >>> 4) & 15];
                cArr[i3 + 1] = charArray[i2 & 15];
            }
            int i4 = 0;
            while (true) {
                String[] strArr = c;
                if (i4 >= strArr.length) {
                    break;
                }
                int length = strArr.length - i4;
                StringBuilder sb = new StringBuilder(length * 3);
                for (int i5 = 0; i5 < length; i5++) {
                    sb.append("   ");
                }
                c[i4] = sb.toString();
                i4++;
            }
            for (int i6 = 0; i6 < d.length; i6++) {
                StringBuilder sb2 = new StringBuilder(12);
                sb2.append(StringUtil.NEWLINE);
                sb2.append(Long.toHexString(((i6 << 4) & 4294967295L) | 4294967296L));
                sb2.setCharAt(sb2.length() - 9, '|');
                sb2.append('|');
                d[i6] = sb2.toString();
            }
            int i7 = 0;
            while (true) {
                String[] strArr2 = e;
                if (i7 >= strArr2.length) {
                    break;
                }
                strArr2[i7] = ' ' + StringUtil.byteToHexStringPadded(i7);
                i7++;
            }
            int i8 = 0;
            while (true) {
                String[] strArr3 = f;
                if (i8 < strArr3.length) {
                    int length2 = strArr3.length - i8;
                    StringBuilder sb3 = new StringBuilder(length2);
                    for (int i9 = 0; i9 < length2; i9++) {
                        sb3.append(' ');
                    }
                    f[i8] = sb3.toString();
                    i8++;
                }
            }
            while (true) {
                char[] cArr2 = a;
                if (i < cArr2.length) {
                    if (i <= 31 || i >= 127) {
                        a[i] = '.';
                    } else {
                        cArr2[i] = (char) i;
                    }
                    i++;
                } else {
                    return;
                }
            }
        }

        public static String c(ByteBuf byteBuf, int i, int i2) {
            if (i2 < 0) {
                throw new IllegalArgumentException("length: " + i2);
            } else if (i2 == 0) {
                return "";
            } else {
                int i3 = i + i2;
                char[] cArr = new char[i2 << 1];
                int i4 = 0;
                while (i < i3) {
                    System.arraycopy(b, byteBuf.getUnsignedByte(i) << 1, cArr, i4, 2);
                    i++;
                    i4 += 2;
                }
                return new String(cArr);
            }
        }

        public static String b(byte[] bArr, int i, int i2) {
            if (i2 < 0) {
                throw new IllegalArgumentException("length: " + i2);
            } else if (i2 == 0) {
                return "";
            } else {
                int i3 = i + i2;
                char[] cArr = new char[i2 << 1];
                int i4 = 0;
                while (i < i3) {
                    System.arraycopy(b, (bArr[i] & 255) << 1, cArr, i4, 2);
                    i++;
                    i4 += 2;
                }
                return new String(cArr);
            }
        }

        public static String d(ByteBuf byteBuf, int i, int i2) {
            if (i2 == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder(((i2 / 16) + (i2 % 15 == 0 ? 0 : 1) + 4) * 80);
            b(sb, byteBuf, i, i2);
            return sb.toString();
        }

        public static void b(StringBuilder sb, ByteBuf byteBuf, int i, int i2) {
            if (MathUtil.isOutOfBounds(i, i2, byteBuf.capacity())) {
                throw new IndexOutOfBoundsException("expected: 0 <= offset(" + i + ") <= offset + length(" + i2 + ") <= buf.capacity(" + byteBuf.capacity() + ')');
            } else if (i2 != 0) {
                sb.append("         +-------------------------------------------------+" + StringUtil.NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" + StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
                int i3 = i2 >>> 4;
                int i4 = i2 & 15;
                for (int i5 = 0; i5 < i3; i5++) {
                    int i6 = (i5 << 4) + i;
                    a(sb, i5, i6);
                    int i7 = i6 + 16;
                    for (int i8 = i6; i8 < i7; i8++) {
                        sb.append(e[byteBuf.getUnsignedByte(i8)]);
                    }
                    sb.append(" |");
                    while (i6 < i7) {
                        sb.append(a[byteBuf.getUnsignedByte(i6)]);
                        i6++;
                    }
                    sb.append('|');
                }
                if (i4 != 0) {
                    int i9 = (i3 << 4) + i;
                    a(sb, i3, i9);
                    int i10 = i9 + i4;
                    for (int i11 = i9; i11 < i10; i11++) {
                        sb.append(e[byteBuf.getUnsignedByte(i11)]);
                    }
                    sb.append(c[i4]);
                    sb.append(" |");
                    while (i9 < i10) {
                        sb.append(a[byteBuf.getUnsignedByte(i9)]);
                        i9++;
                    }
                    sb.append(f[i4]);
                    sb.append('|');
                }
                sb.append(StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
            }
        }

        private static void a(StringBuilder sb, int i, int i2) {
            String[] strArr = d;
            if (i < strArr.length) {
                sb.append(strArr[i]);
                return;
            }
            sb.append(StringUtil.NEWLINE);
            sb.append(Long.toHexString((i2 & 4294967295L) | 4294967296L));
            sb.setCharAt(sb.length() - 9, '|');
            sb.append('|');
        }
    }

    /* loaded from: classes4.dex */
    public static final class c extends UnpooledUnsafeDirectByteBuf {
        private static final Recycler<c> e = new Recycler<c>() { // from class: io.netty.buffer.ByteBufUtil.c.1
            /* renamed from: a */
            public c newObject(Recycler.Handle<c> handle) {
                return new c(handle);
            }
        };
        private final Recycler.Handle<c> f;

        static c b() {
            c cVar = e.get();
            cVar.setRefCnt(1);
            return cVar;
        }

        private c(Recycler.Handle<c> handle) {
            super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.f = handle;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.buffer.UnpooledUnsafeDirectByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf
        public void deallocate() {
            if (capacity() > ByteBufUtil.e) {
                super.deallocate();
                return;
            }
            clear();
            this.f.recycle(this);
        }
    }

    /* loaded from: classes4.dex */
    public static final class b extends UnpooledDirectByteBuf {
        private static final Recycler<b> d = new Recycler<b>() { // from class: io.netty.buffer.ByteBufUtil.b.1
            /* renamed from: a */
            public b newObject(Recycler.Handle<b> handle) {
                return new b(handle);
            }
        };
        private final Recycler.Handle<b> e;

        static b b() {
            b bVar = d.get();
            bVar.setRefCnt(1);
            return bVar;
        }

        private b(Recycler.Handle<b> handle) {
            super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.e = handle;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.buffer.UnpooledDirectByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf
        public void deallocate() {
            if (capacity() > ByteBufUtil.e) {
                super.deallocate();
                return;
            }
            clear();
            this.e.recycle(this);
        }
    }

    private ByteBufUtil() {
    }
}
