package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.uc.crashsdk.export.LogType;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class DtsUtil {
    public static final int DTS_HD_MAX_RATE_BYTES_PER_SECOND = 2250000;
    public static final int DTS_MAX_RATE_BYTES_PER_SECOND = 192000;
    private static final int[] a = {1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 6, 6, 6, 7, 8, 8};
    private static final int[] b = {-1, 8000, 16000, 32000, -1, -1, 11025, 22050, 44100, -1, -1, 12000, 24000, OpusUtil.SAMPLE_RATE, -1, -1};
    private static final int[] c = {64, 112, 128, 192, 224, 256, 384, 448, 512, 640, LogType.UNEXP_OTHER, 896, 1024, 1152, 1280, 1536, 1920, 2048, LogType.UNEXP_LOW_MEMORY, 2560, 2688, 2816, 2823, 2944, 3072, 3840, 4096, 6144, 7680};

    public static boolean isSyncWord(int i) {
        return i == 2147385345 || i == -25230976 || i == 536864768 || i == -14745368;
    }

    public static Format parseDtsFormat(byte[] bArr, @Nullable String str, @Nullable String str2, @Nullable DrmInitData drmInitData) {
        ParsableBitArray a2 = a(bArr);
        a2.skipBits(60);
        int i = a[a2.readBits(6)];
        int i2 = b[a2.readBits(4)];
        int readBits = a2.readBits(5);
        int[] iArr = c;
        int i3 = readBits >= iArr.length ? -1 : (iArr[readBits] * 1000) / 2;
        a2.skipBits(10);
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.AUDIO_DTS).setAverageBitrate(i3).setChannelCount(i + (a2.readBits(2) > 0 ? 1 : 0)).setSampleRate(i2).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseDtsAudioSampleCount(byte[] bArr) {
        int i;
        byte b2 = bArr[0];
        if (b2 != 31) {
            switch (b2) {
                case -2:
                    i = ((bArr[4] & 252) >> 2) | ((bArr[5] & 1) << 6);
                    break;
                case -1:
                    i = ((bArr[7] & 60) >> 2) | ((bArr[4] & 7) << 4);
                    break;
                default:
                    i = ((bArr[5] & 252) >> 2) | ((bArr[4] & 1) << 6);
                    break;
            }
        } else {
            i = ((bArr[6] & 60) >> 2) | ((bArr[5] & 7) << 4);
        }
        return (i + 1) * 32;
    }

    public static int parseDtsAudioSampleCount(ByteBuffer byteBuffer) {
        int i;
        int position = byteBuffer.position();
        byte b2 = byteBuffer.get(position);
        if (b2 != 31) {
            switch (b2) {
                case -2:
                    i = ((byteBuffer.get(position + 4) & 252) >> 2) | ((byteBuffer.get(position + 5) & 1) << 6);
                    break;
                case -1:
                    i = ((byteBuffer.get(position + 7) & 60) >> 2) | ((byteBuffer.get(position + 4) & 7) << 4);
                    break;
                default:
                    i = ((byteBuffer.get(position + 5) & 252) >> 2) | ((byteBuffer.get(position + 4) & 1) << 6);
                    break;
            }
        } else {
            i = ((byteBuffer.get(position + 6) & 60) >> 2) | ((byteBuffer.get(position + 5) & 7) << 4);
        }
        return (i + 1) * 32;
    }

    public static int getDtsFrameSize(byte[] bArr) {
        int i;
        boolean z = false;
        byte b2 = bArr[0];
        if (b2 != 31) {
            switch (b2) {
                case -2:
                    i = (((bArr[6] & 240) >> 4) | ((bArr[4] & 3) << 12) | ((bArr[7] & 255) << 4)) + 1;
                    break;
                case -1:
                    i = (((bArr[9] & 60) >> 2) | ((bArr[7] & 3) << 12) | ((bArr[6] & 255) << 4)) + 1;
                    z = true;
                    break;
                default:
                    i = (((bArr[7] & 240) >> 4) | ((bArr[5] & 3) << 12) | ((bArr[6] & 255) << 4)) + 1;
                    break;
            }
        } else {
            i = (((bArr[8] & 60) >> 2) | ((bArr[6] & 3) << 12) | ((bArr[7] & 255) << 4)) + 1;
            z = true;
        }
        return z ? (i * 16) / 14 : i;
    }

    private static ParsableBitArray a(byte[] bArr) {
        if (bArr[0] == Byte.MAX_VALUE) {
            return new ParsableBitArray(bArr);
        }
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length);
        if (b(copyOf)) {
            for (int i = 0; i < copyOf.length - 1; i += 2) {
                byte b2 = copyOf[i];
                int i2 = i + 1;
                copyOf[i] = copyOf[i2];
                copyOf[i2] = b2;
            }
        }
        ParsableBitArray parsableBitArray = new ParsableBitArray(copyOf);
        if (copyOf[0] == 31) {
            ParsableBitArray parsableBitArray2 = new ParsableBitArray(copyOf);
            while (parsableBitArray2.bitsLeft() >= 16) {
                parsableBitArray2.skipBits(2);
                parsableBitArray.putInt(parsableBitArray2.readBits(14), 14);
            }
        }
        parsableBitArray.reset(copyOf);
        return parsableBitArray;
    }

    private static boolean b(byte[] bArr) {
        return bArr[0] == -2 || bArr[0] == -1;
    }

    private DtsUtil() {
    }
}
