package com.alibaba.fastjson.util;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import okio.Utf8;

/* loaded from: classes.dex */
public class UTF8Decoder extends CharsetDecoder {
    private static final Charset charset = Charset.forName("UTF-8");

    private static boolean isMalformed2(int i, int i2) {
        return (i & 30) == 0 || (i2 & 192) != 128;
    }

    private static boolean isMalformed3(int i, int i2, int i3) {
        return ((i != -32 || (i2 & 224) != 128) && (i2 & 192) == 128 && (i3 & 192) == 128) ? false : true;
    }

    private static boolean isMalformed4(int i, int i2, int i3) {
        return ((i & 192) == 128 && (i2 & 192) == 128 && (i3 & 192) == 128) ? false : true;
    }

    private static boolean isNotContinuation(int i) {
        return (i & 192) != 128;
    }

    public UTF8Decoder() {
        super(charset, 1.0f, 1.0f);
    }

    private static CoderResult lookupN(ByteBuffer byteBuffer, int i) {
        for (int i2 = 1; i2 < i; i2++) {
            if (isNotContinuation(byteBuffer.get())) {
                return CoderResult.malformedForLength(i2);
            }
        }
        return CoderResult.malformedForLength(i);
    }

    public static CoderResult malformedN(ByteBuffer byteBuffer, int i) {
        int i2 = 2;
        switch (i) {
            case 1:
                byte b = byteBuffer.get();
                if ((b >> 2) == -2) {
                    return byteBuffer.remaining() < 4 ? CoderResult.UNDERFLOW : lookupN(byteBuffer, 5);
                }
                if ((b >> 1) != -2) {
                    return CoderResult.malformedForLength(1);
                }
                if (byteBuffer.remaining() < 5) {
                    return CoderResult.UNDERFLOW;
                }
                return lookupN(byteBuffer, 6);
            case 2:
                return CoderResult.malformedForLength(1);
            case 3:
                byte b2 = byteBuffer.get();
                byte b3 = byteBuffer.get();
                if ((b2 == -32 && (b3 & 224) == 128) || isNotContinuation(b3)) {
                    i2 = 1;
                }
                return CoderResult.malformedForLength(i2);
            case 4:
                int i3 = byteBuffer.get() & 255;
                int i4 = byteBuffer.get() & 255;
                if (i3 > 244 || ((i3 == 240 && (i4 < 144 || i4 > 191)) || ((i3 == 244 && (i4 & PsExtractor.VIDEO_STREAM_MASK) != 128) || isNotContinuation(i4)))) {
                    return CoderResult.malformedForLength(1);
                }
                return isNotContinuation(byteBuffer.get()) ? CoderResult.malformedForLength(2) : CoderResult.malformedForLength(3);
            default:
                throw new IllegalStateException();
        }
    }

    private static CoderResult malformed(ByteBuffer byteBuffer, int i, CharBuffer charBuffer, int i2, int i3) {
        byteBuffer.position(i - byteBuffer.arrayOffset());
        CoderResult malformedN = malformedN(byteBuffer, i3);
        byteBuffer.position(i);
        charBuffer.position(i2);
        return malformedN;
    }

    private static CoderResult xflow(Buffer buffer, int i, int i2, Buffer buffer2, int i3, int i4) {
        buffer.position(i);
        buffer2.position(i3);
        return (i4 == 0 || i2 - i < i4) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
    }

    private CoderResult decodeArrayLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
        byte[] array = byteBuffer.array();
        int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
        int arrayOffset2 = byteBuffer.arrayOffset() + byteBuffer.limit();
        char[] array2 = charBuffer.array();
        int arrayOffset3 = charBuffer.arrayOffset() + charBuffer.position();
        int arrayOffset4 = charBuffer.arrayOffset() + charBuffer.limit();
        int min = Math.min(arrayOffset2 - arrayOffset, arrayOffset4 - arrayOffset3) + arrayOffset3;
        while (arrayOffset3 < min && array[arrayOffset] >= 0) {
            arrayOffset3++;
            arrayOffset++;
            array2[arrayOffset3] = (char) array[arrayOffset];
        }
        int i = arrayOffset;
        int i2 = arrayOffset3;
        while (i < arrayOffset2) {
            byte b = array[i];
            if (b >= 0) {
                if (i2 >= arrayOffset4) {
                    return xflow(byteBuffer, i, arrayOffset2, charBuffer, i2, 1);
                }
                i2++;
                array2[i2] = (char) b;
                i++;
            } else if ((b >> 5) == -2) {
                if (arrayOffset2 - i < 2 || i2 >= arrayOffset4) {
                    return xflow(byteBuffer, i, arrayOffset2, charBuffer, i2, 2);
                }
                byte b2 = array[i + 1];
                if (isMalformed2(b, b2)) {
                    return malformed(byteBuffer, i, charBuffer, i2, 2);
                }
                i2++;
                array2[i2] = (char) (((b << 6) ^ b2) ^ Utf8.MASK_2BYTES);
                i += 2;
            } else if ((b >> 4) == -2) {
                if (arrayOffset2 - i < 3 || i2 >= arrayOffset4) {
                    return xflow(byteBuffer, i, arrayOffset2, charBuffer, i2, 3);
                }
                byte b3 = array[i + 1];
                byte b4 = array[i + 2];
                if (isMalformed3(b, b3, b4)) {
                    return malformed(byteBuffer, i, charBuffer, i2, 3);
                }
                i2++;
                array2[i2] = (char) ((((b << 12) ^ (b3 << 6)) ^ b4) ^ 8064);
                i += 3;
            } else if ((b >> 3) != -2) {
                return malformed(byteBuffer, i, charBuffer, i2, 1);
            } else {
                if (arrayOffset2 - i < 4 || arrayOffset4 - i2 < 2) {
                    return xflow(byteBuffer, i, arrayOffset2, charBuffer, i2, 4);
                }
                byte b5 = array[i + 1];
                byte b6 = array[i + 2];
                byte b7 = array[i + 3];
                int i3 = ((b & 7) << 18) | ((b5 & Utf8.REPLACEMENT_BYTE) << 12) | ((b6 & Utf8.REPLACEMENT_BYTE) << 6) | (b7 & Utf8.REPLACEMENT_BYTE);
                if (isMalformed4(b5, b6, b7) || i3 < 65536 || i3 > 1114111) {
                    return malformed(byteBuffer, i, charBuffer, i2, 4);
                }
                int i4 = i2 + 1;
                int i5 = i3 - 65536;
                array2[i2] = (char) (((i5 >> 10) & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | GeneratorBase.SURR1_FIRST);
                i2 = i4 + 1;
                array2[i4] = (char) ((i5 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320);
                i += 4;
            }
        }
        return xflow(byteBuffer, i, arrayOffset2, charBuffer, i2, 0);
    }

    @Override // java.nio.charset.CharsetDecoder
    protected CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
        return decodeArrayLoop(byteBuffer, charBuffer);
    }
}
