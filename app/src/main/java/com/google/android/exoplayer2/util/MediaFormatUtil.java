package com.google.android.exoplayer2.util;

import android.annotation.SuppressLint;
import android.media.MediaFormat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.video.ColorInfo;
import com.umeng.analytics.pro.ai;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes2.dex */
public final class MediaFormatUtil {
    public static final String KEY_EXO_PCM_ENCODING = "exo-pcm-encoding-int";
    public static final String KEY_EXO_PIXEL_WIDTH_HEIGHT_RATIO_FLOAT = "exo-pixel-width-height-ratio-float";

    @SuppressLint({"InlinedApi"})
    public static MediaFormat createMediaFormatFromFormat(Format format) {
        MediaFormat mediaFormat = new MediaFormat();
        maybeSetInteger(mediaFormat, "bitrate", format.bitrate);
        maybeSetInteger(mediaFormat, "channel-count", format.channelCount);
        maybeSetColorInfo(mediaFormat, format.colorInfo);
        maybeSetString(mediaFormat, "mime", format.sampleMimeType);
        maybeSetString(mediaFormat, "codecs-string", format.codecs);
        maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        maybeSetInteger(mediaFormat, "width", format.width);
        maybeSetInteger(mediaFormat, "height", format.height);
        setCsdBuffers(mediaFormat, format.initializationData);
        a(mediaFormat, format.pcmEncoding);
        maybeSetString(mediaFormat, ai.N, format.language);
        maybeSetInteger(mediaFormat, "max-input-size", format.maxInputSize);
        maybeSetInteger(mediaFormat, "sample-rate", format.sampleRate);
        maybeSetInteger(mediaFormat, "caption-service-number", format.accessibilityChannel);
        mediaFormat.setInteger("rotation-degrees", format.rotationDegrees);
        int i = format.selectionFlags;
        a(mediaFormat, "is-autoselect", i & 4);
        a(mediaFormat, "is-default", i & 1);
        a(mediaFormat, "is-forced-subtitle", i & 2);
        mediaFormat.setInteger("encoder-delay", format.encoderDelay);
        mediaFormat.setInteger("encoder-padding", format.encoderPadding);
        a(mediaFormat, format.pixelWidthHeightRatio);
        return mediaFormat;
    }

    public static void maybeSetString(MediaFormat mediaFormat, String str, @Nullable String str2) {
        if (str2 != null) {
            mediaFormat.setString(str, str2);
        }
    }

    public static void setCsdBuffers(MediaFormat mediaFormat, List<byte[]> list) {
        for (int i = 0; i < list.size(); i++) {
            StringBuilder sb = new StringBuilder(15);
            sb.append("csd-");
            sb.append(i);
            mediaFormat.setByteBuffer(sb.toString(), ByteBuffer.wrap(list.get(i)));
        }
    }

    public static void maybeSetInteger(MediaFormat mediaFormat, String str, int i) {
        if (i != -1) {
            mediaFormat.setInteger(str, i);
        }
    }

    public static void maybeSetFloat(MediaFormat mediaFormat, String str, float f) {
        if (f != -1.0f) {
            mediaFormat.setFloat(str, f);
        }
    }

    public static void maybeSetByteBuffer(MediaFormat mediaFormat, String str, @Nullable byte[] bArr) {
        if (bArr != null) {
            mediaFormat.setByteBuffer(str, ByteBuffer.wrap(bArr));
        }
    }

    public static void maybeSetColorInfo(MediaFormat mediaFormat, @Nullable ColorInfo colorInfo) {
        if (colorInfo != null) {
            maybeSetInteger(mediaFormat, "color-transfer", colorInfo.colorTransfer);
            maybeSetInteger(mediaFormat, "color-standard", colorInfo.colorSpace);
            maybeSetInteger(mediaFormat, "color-range", colorInfo.colorRange);
            maybeSetByteBuffer(mediaFormat, "hdr-static-info", colorInfo.hdrStaticInfo);
        }
    }

    private static void a(MediaFormat mediaFormat, String str, int i) {
        mediaFormat.setInteger(str, i != 0 ? 1 : 0);
    }

    @SuppressLint({"InlinedApi"})
    private static void a(MediaFormat mediaFormat, float f) {
        mediaFormat.setFloat(KEY_EXO_PIXEL_WIDTH_HEIGHT_RATIO_FLOAT, f);
        int i = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
        int i2 = 1073741824;
        int i3 = 1;
        if (i < 0) {
            i3 = (int) (f * 1073741824);
        } else if (f > 1.0f) {
            i2 = (int) (1073741824 / f);
            i3 = 1073741824;
        } else {
            i2 = 1;
        }
        mediaFormat.setInteger("sar-width", i3);
        mediaFormat.setInteger("sar-height", i2);
    }

    @SuppressLint({"InlinedApi"})
    private static void a(MediaFormat mediaFormat, int i) {
        int i2;
        if (i != -1) {
            maybeSetInteger(mediaFormat, KEY_EXO_PCM_ENCODING, i);
            switch (i) {
                case 2:
                    i2 = 2;
                    break;
                case 3:
                    i2 = 3;
                    break;
                case 4:
                    i2 = 4;
                    break;
                default:
                    return;
            }
            mediaFormat.setInteger("pcm-encoding", i2);
        }
    }

    private MediaFormatUtil() {
    }
}
