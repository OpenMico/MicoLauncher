package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.ai.core.AivsConfig;

/* loaded from: classes.dex */
public final class MpegAudioUtil {
    public static final int MAX_FRAME_SIZE_BYTES = 4096;
    private static final String[] a = {MimeTypes.AUDIO_MPEG_L1, MimeTypes.AUDIO_MPEG_L2, "audio/mpeg"};
    private static final int[] b = {44100, OpusUtil.SAMPLE_RATE, 32000};
    private static final int[] c = {32000, AivsConfig.Asr.OPUS_BITRATE_64K, 96000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 288000, 320000, 352000, 384000, 416000, 448000};
    private static final int[] d = {32000, OpusUtil.SAMPLE_RATE, 56000, AivsConfig.Asr.OPUS_BITRATE_64K, 80000, 96000, 112000, 128000, 144000, 160000, 176000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND};
    private static final int[] e = {32000, OpusUtil.SAMPLE_RATE, 56000, AivsConfig.Asr.OPUS_BITRATE_64K, 80000, 96000, 112000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 320000, 384000};
    public static final int MAX_RATE_BYTES_PER_SECOND = 40000;
    private static final int[] f = {32000, MAX_RATE_BYTES_PER_SECOND, OpusUtil.SAMPLE_RATE, 56000, AivsConfig.Asr.OPUS_BITRATE_64K, 80000, 96000, 112000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 320000};
    private static final int[] g = {8000, 16000, 24000, 32000, MAX_RATE_BYTES_PER_SECOND, OpusUtil.SAMPLE_RATE, 56000, AivsConfig.Asr.OPUS_BITRATE_64K, 80000, 96000, 112000, 128000, 144000, 160000};

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(int i) {
        return (i & (-2097152)) == -2097152;
    }

    /* loaded from: classes.dex */
    public static final class Header {
        public int bitrate;
        public int channels;
        public int frameSize;
        @Nullable
        public String mimeType;
        public int sampleRate;
        public int samplesPerFrame;
        public int version;

        public boolean setForHeaderData(int i) {
            int i2;
            int i3;
            int i4;
            int i5;
            if (!MpegAudioUtil.b(i) || (i2 = (i >>> 19) & 3) == 1 || (i3 = (i >>> 17) & 3) == 0 || (i4 = (i >>> 12) & 15) == 0 || i4 == 15 || (i5 = (i >>> 10) & 3) == 3) {
                return false;
            }
            this.version = i2;
            this.mimeType = MpegAudioUtil.a[3 - i3];
            this.sampleRate = MpegAudioUtil.b[i5];
            int i6 = 2;
            if (i2 == 2) {
                this.sampleRate /= 2;
            } else if (i2 == 0) {
                this.sampleRate /= 4;
            }
            int i7 = (i >>> 9) & 1;
            this.samplesPerFrame = MpegAudioUtil.b(i2, i3);
            if (i3 == 3) {
                this.bitrate = i2 == 3 ? MpegAudioUtil.c[i4 - 1] : MpegAudioUtil.d[i4 - 1];
                this.frameSize = (((this.bitrate * 12) / this.sampleRate) + i7) * 4;
            } else {
                int i8 = 144;
                if (i2 == 3) {
                    this.bitrate = i3 == 2 ? MpegAudioUtil.e[i4 - 1] : MpegAudioUtil.f[i4 - 1];
                    this.frameSize = ((this.bitrate * 144) / this.sampleRate) + i7;
                } else {
                    this.bitrate = MpegAudioUtil.g[i4 - 1];
                    if (i3 == 1) {
                        i8 = 72;
                    }
                    this.frameSize = ((i8 * this.bitrate) / this.sampleRate) + i7;
                }
            }
            if (((i >> 6) & 3) == 3) {
                i6 = 1;
            }
            this.channels = i6;
            return true;
        }
    }

    public static int getFrameSize(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (!b(i) || (i2 = (i >>> 19) & 3) == 1 || (i3 = (i >>> 17) & 3) == 0 || (i4 = (i >>> 12) & 15) == 0 || i4 == 15 || (i5 = (i >>> 10) & 3) == 3) {
            return -1;
        }
        int i7 = b[i5];
        if (i2 == 2) {
            i7 /= 2;
        } else if (i2 == 0) {
            i7 /= 4;
        }
        int i8 = (i >>> 9) & 1;
        if (i3 == 3) {
            return ((((i2 == 3 ? c[i4 - 1] : d[i4 - 1]) * 12) / i7) + i8) * 4;
        }
        if (i2 == 3) {
            i6 = i3 == 2 ? e[i4 - 1] : f[i4 - 1];
        } else {
            i6 = g[i4 - 1];
        }
        int i9 = 144;
        if (i2 == 3) {
            return ((i6 * 144) / i7) + i8;
        }
        if (i3 == 1) {
            i9 = 72;
        }
        return ((i9 * i6) / i7) + i8;
    }

    public static int parseMpegAudioFrameSampleCount(int i) {
        int i2;
        int i3;
        if (!b(i) || (i2 = (i >>> 19) & 3) == 1 || (i3 = (i >>> 17) & 3) == 0) {
            return -1;
        }
        int i4 = (i >>> 12) & 15;
        int i5 = (i >>> 10) & 3;
        if (i4 == 0 || i4 == 15 || i5 == 3) {
            return -1;
        }
        return b(i2, i3);
    }

    private MpegAudioUtil() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(int i, int i2) {
        switch (i2) {
            case 1:
                return i == 3 ? 1152 : 576;
            case 2:
                return 1152;
            case 3:
                return 384;
            default:
                throw new IllegalArgumentException();
        }
    }
}
