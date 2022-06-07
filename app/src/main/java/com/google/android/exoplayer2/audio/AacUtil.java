package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.xiaomi.ai.core.AivsConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class AacUtil {
    public static final int AAC_ELD_MAX_RATE_BYTES_PER_SECOND = 8000;
    public static final int AAC_HE_AUDIO_SAMPLE_COUNT = 2048;
    public static final int AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND = 16000;
    public static final int AAC_HE_V2_MAX_RATE_BYTES_PER_SECOND = 7000;
    public static final int AAC_LC_AUDIO_SAMPLE_COUNT = 1024;
    public static final int AAC_LC_MAX_RATE_BYTES_PER_SECOND = 100000;
    public static final int AAC_LD_AUDIO_SAMPLE_COUNT = 512;
    public static final int AAC_XHE_AUDIO_SAMPLE_COUNT = 1024;
    public static final int AAC_XHE_MAX_RATE_BYTES_PER_SECOND = 256000;
    public static final int AUDIO_OBJECT_TYPE_AAC_ELD = 23;
    public static final int AUDIO_OBJECT_TYPE_AAC_ER_BSAC = 22;
    public static final int AUDIO_OBJECT_TYPE_AAC_LC = 2;
    public static final int AUDIO_OBJECT_TYPE_AAC_PS = 29;
    public static final int AUDIO_OBJECT_TYPE_AAC_SBR = 5;
    public static final int AUDIO_OBJECT_TYPE_AAC_XHE = 42;
    private static final int[] a = {96000, 88200, AivsConfig.Asr.OPUS_BITRATE_64K, OpusUtil.SAMPLE_RATE, 44100, 32000, 24000, 22050, 16000, 12000, 11025, 8000, 7350};
    private static final int[] b = {0, 1, 2, 3, 4, 5, 6, 8, -1, -1, -1, 7, 8, -1, 8, -1};

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface AacAudioObjectType {
    }

    public static int getEncodingForAudioObjectType(int i) {
        if (i == 2) {
            return 10;
        }
        if (i == 5) {
            return 11;
        }
        if (i == 29) {
            return 12;
        }
        if (i == 42) {
            return 16;
        }
        switch (i) {
            case 22:
                return 1073741824;
            case 23:
                return 15;
            default:
                return 0;
        }
    }

    /* loaded from: classes.dex */
    public static final class Config {
        public final int channelCount;
        public final String codecs;
        public final int sampleRateHz;

        private Config(int i, int i2, String str) {
            this.sampleRateHz = i;
            this.channelCount = i2;
            this.codecs = str;
        }
    }

    public static Config parseAudioSpecificConfig(byte[] bArr) throws ParserException {
        return parseAudioSpecificConfig(new ParsableBitArray(bArr), false);
    }

    public static Config parseAudioSpecificConfig(ParsableBitArray parsableBitArray, boolean z) throws ParserException {
        int a2 = a(parsableBitArray);
        int b2 = b(parsableBitArray);
        int readBits = parsableBitArray.readBits(4);
        StringBuilder sb = new StringBuilder(19);
        sb.append("mp4a.40.");
        sb.append(a2);
        String sb2 = sb.toString();
        if (a2 == 5 || a2 == 29) {
            b2 = b(parsableBitArray);
            a2 = a(parsableBitArray);
            if (a2 == 22) {
                readBits = parsableBitArray.readBits(4);
            }
        }
        if (z) {
            if (a2 != 17) {
                switch (a2) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        break;
                    default:
                        switch (a2) {
                            case 6:
                            case 7:
                                break;
                            default:
                                switch (a2) {
                                    case 19:
                                    case 20:
                                    case 21:
                                    case 22:
                                    case 23:
                                        break;
                                    default:
                                        StringBuilder sb3 = new StringBuilder(42);
                                        sb3.append("Unsupported audio object type: ");
                                        sb3.append(a2);
                                        throw ParserException.createForUnsupportedContainerFeature(sb3.toString());
                                }
                        }
                }
            }
            a(parsableBitArray, a2, readBits);
            switch (a2) {
                case 17:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    int readBits2 = parsableBitArray.readBits(2);
                    if (readBits2 == 2 || readBits2 == 3) {
                        StringBuilder sb4 = new StringBuilder(33);
                        sb4.append("Unsupported epConfig: ");
                        sb4.append(readBits2);
                        throw ParserException.createForUnsupportedContainerFeature(sb4.toString());
                    }
                    break;
            }
        }
        int i = b[readBits];
        if (i != -1) {
            return new Config(b2, i, sb2);
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    public static byte[] buildAacLcAudioSpecificConfig(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        int i5 = -1;
        while (true) {
            int[] iArr = a;
            if (i4 >= iArr.length) {
                break;
            }
            if (i == iArr[i4]) {
                i5 = i4;
            }
            i4++;
        }
        int i6 = -1;
        while (true) {
            int[] iArr2 = b;
            if (i3 >= iArr2.length) {
                break;
            }
            if (i2 == iArr2[i3]) {
                i6 = i3;
            }
            i3++;
        }
        if (i != -1 && i6 != -1) {
            return buildAudioSpecificConfig(2, i5, i6);
        }
        StringBuilder sb = new StringBuilder(67);
        sb.append("Invalid sample rate or number of channels: ");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    public static byte[] buildAudioSpecificConfig(int i, int i2, int i3) {
        return new byte[]{(byte) (((i << 3) & 248) | ((i2 >> 1) & 7)), (byte) (((i2 << 7) & 128) | ((i3 << 3) & 120))};
    }

    private static int a(ParsableBitArray parsableBitArray) {
        int readBits = parsableBitArray.readBits(5);
        return readBits == 31 ? parsableBitArray.readBits(6) + 32 : readBits;
    }

    private static int b(ParsableBitArray parsableBitArray) throws ParserException {
        int readBits = parsableBitArray.readBits(4);
        if (readBits == 15) {
            return parsableBitArray.readBits(24);
        }
        if (readBits < 13) {
            return a[readBits];
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    private static void a(ParsableBitArray parsableBitArray, int i, int i2) {
        if (parsableBitArray.readBit()) {
            Log.w("AacUtil", "Unexpected frameLengthFlag = 1");
        }
        if (parsableBitArray.readBit()) {
            parsableBitArray.skipBits(14);
        }
        boolean readBit = parsableBitArray.readBit();
        if (i2 != 0) {
            if (i == 6 || i == 20) {
                parsableBitArray.skipBits(3);
            }
            if (readBit) {
                if (i == 22) {
                    parsableBitArray.skipBits(16);
                }
                if (i == 17 || i == 19 || i == 20 || i == 23) {
                    parsableBitArray.skipBits(3);
                }
                parsableBitArray.skipBits(1);
                return;
            }
            return;
        }
        throw new UnsupportedOperationException();
    }

    private AacUtil() {
    }
}
