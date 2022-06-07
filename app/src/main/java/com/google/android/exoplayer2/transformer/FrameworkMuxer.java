package com.google.android.exoplayer2.transformer;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.transformer.Muxer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/* JADX INFO: Access modifiers changed from: package-private */
@RequiresApi(18)
/* loaded from: classes2.dex */
public final class FrameworkMuxer implements Muxer {
    private final MediaMuxer a;
    private final String b;
    private final MediaCodec.BufferInfo c;
    private boolean d;

    /* loaded from: classes2.dex */
    public static final class Factory implements Muxer.Factory {
        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public FrameworkMuxer create(String str, String str2) throws IOException {
            return new FrameworkMuxer(new MediaMuxer(str, FrameworkMuxer.c(str2)), str2);
        }

        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        @RequiresApi(26)
        public FrameworkMuxer create(ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException {
            return new FrameworkMuxer(new MediaMuxer(parcelFileDescriptor.getFileDescriptor(), FrameworkMuxer.c(str)), str);
        }

        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public boolean supportsOutputMimeType(String str) {
            try {
                FrameworkMuxer.c(str);
                return true;
            } catch (IllegalStateException unused) {
                return false;
            }
        }
    }

    private FrameworkMuxer(MediaMuxer mediaMuxer, String str) {
        this.a = mediaMuxer;
        this.b = str;
        this.c = new MediaCodec.BufferInfo();
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public boolean a(@Nullable String str) {
        boolean isAudio = MimeTypes.isAudio(str);
        boolean isVideo = MimeTypes.isVideo(str);
        if (this.b.equals("video/mp4")) {
            if (isVideo) {
                if ("video/3gpp".equals(str) || MimeTypes.VIDEO_H264.equals(str) || MimeTypes.VIDEO_MP4V.equals(str)) {
                    return true;
                }
                return Util.SDK_INT >= 24 && MimeTypes.VIDEO_H265.equals(str);
            } else if (isAudio) {
                return MimeTypes.AUDIO_AAC.equals(str) || "audio/3gpp".equals(str) || MimeTypes.AUDIO_AMR_WB.equals(str);
            }
        } else if (this.b.equals(MimeTypes.VIDEO_WEBM) && Util.SDK_INT >= 21) {
            if (isVideo) {
                if (!MimeTypes.VIDEO_VP8.equals(str)) {
                    return Util.SDK_INT >= 24 && MimeTypes.VIDEO_VP9.equals(str);
                }
                return true;
            } else if (isAudio) {
                return MimeTypes.AUDIO_VORBIS.equals(str);
            }
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public int a(Format format) {
        MediaFormat mediaFormat;
        String str = (String) Assertions.checkNotNull(format.sampleMimeType);
        if (MimeTypes.isAudio(str)) {
            mediaFormat = MediaFormat.createAudioFormat((String) Util.castNonNull(str), format.sampleRate, format.channelCount);
        } else {
            mediaFormat = MediaFormat.createVideoFormat((String) Util.castNonNull(str), format.width, format.height);
            this.a.setOrientationHint(format.rotationDegrees);
        }
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        return this.a.addTrack(mediaFormat);
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public void a(int i, ByteBuffer byteBuffer, boolean z, long j) {
        if (!this.d) {
            this.d = true;
            this.a.start();
        }
        int position = byteBuffer.position();
        this.c.set(position, byteBuffer.limit() - position, j, z ? 1 : 0);
        this.a.writeSampleData(i, byteBuffer, this.c);
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public void a(boolean z) {
        if (this.d) {
            try {
                this.d = false;
                try {
                    this.a.stop();
                } catch (IllegalStateException e) {
                    if (Util.SDK_INT < 30) {
                        try {
                            Field declaredField = MediaMuxer.class.getDeclaredField("MUXER_STATE_STOPPED");
                            declaredField.setAccessible(true);
                            int intValue = ((Integer) Util.castNonNull((Integer) declaredField.get(this.a))).intValue();
                            Field declaredField2 = MediaMuxer.class.getDeclaredField("mState");
                            declaredField2.setAccessible(true);
                            declaredField2.set(this.a, Integer.valueOf(intValue));
                        } catch (Exception unused) {
                        }
                    }
                    if (!z) {
                        throw e;
                    }
                }
            } finally {
                this.a.release();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(String str) {
        if (str.equals("video/mp4")) {
            return 0;
        }
        if (Util.SDK_INT >= 21 && str.equals(MimeTypes.VIDEO_WEBM)) {
            return 1;
        }
        String valueOf = String.valueOf(str);
        throw new IllegalArgumentException(valueOf.length() != 0 ? "Unsupported output MIME type: ".concat(valueOf) : new String("Unsupported output MIME type: "));
    }
}
