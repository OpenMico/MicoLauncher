package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class SynchronousMediaCodecAdapter implements MediaCodecAdapter {
    private final MediaCodec a;
    @Nullable
    private ByteBuffer[] b;
    @Nullable
    private ByteBuffer[] c;

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public boolean needsReconfiguration() {
        return false;
    }

    /* loaded from: classes2.dex */
    public static class Factory implements MediaCodecAdapter.Factory {
        /* JADX WARN: Removed duplicated region for block: B:11:0x0033  */
        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.google.android.exoplayer2.mediacodec.MediaCodecAdapter createAdapter(com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Configuration r6) throws java.io.IOException {
            /*
                r5 = this;
                r0 = 0
                android.media.MediaCodec r1 = r5.createCodec(r6)     // Catch: IOException -> 0x002f, RuntimeException -> 0x002d
                java.lang.String r2 = "configureCodec"
                com.google.android.exoplayer2.util.TraceUtil.beginSection(r2)     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                android.media.MediaFormat r2 = r6.mediaFormat     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                android.view.Surface r3 = r6.surface     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                android.media.MediaCrypto r4 = r6.crypto     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                int r6 = r6.flags     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                r1.configure(r2, r3, r4, r6)     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                java.lang.String r6 = "startCodec"
                com.google.android.exoplayer2.util.TraceUtil.beginSection(r6)     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                r1.start()     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter r6 = new com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                r6.<init>(r1)     // Catch: IOException -> 0x002b, RuntimeException -> 0x0029
                return r6
            L_0x0029:
                r6 = move-exception
                goto L_0x0031
            L_0x002b:
                r6 = move-exception
                goto L_0x0031
            L_0x002d:
                r6 = move-exception
                goto L_0x0030
            L_0x002f:
                r6 = move-exception
            L_0x0030:
                r1 = r0
            L_0x0031:
                if (r1 == 0) goto L_0x0036
                r1.release()
            L_0x0036:
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter.Factory.createAdapter(com.google.android.exoplayer2.mediacodec.MediaCodecAdapter$Configuration):com.google.android.exoplayer2.mediacodec.MediaCodecAdapter");
        }

        protected MediaCodec createCodec(MediaCodecAdapter.Configuration configuration) throws IOException {
            Assertions.checkNotNull(configuration.codecInfo);
            String str = configuration.codecInfo.name;
            String valueOf = String.valueOf(str);
            TraceUtil.beginSection(valueOf.length() != 0 ? "createCodec:".concat(valueOf) : new String("createCodec:"));
            MediaCodec createByCodecName = MediaCodec.createByCodecName(str);
            TraceUtil.endSection();
            return createByCodecName;
        }
    }

    private SynchronousMediaCodecAdapter(MediaCodec mediaCodec) {
        this.a = mediaCodec;
        if (Util.SDK_INT < 21) {
            this.b = this.a.getInputBuffers();
            this.c = this.a.getOutputBuffers();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueInputBufferIndex() {
        return this.a.dequeueInputBuffer(0L);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        int dequeueOutputBuffer;
        do {
            dequeueOutputBuffer = this.a.dequeueOutputBuffer(bufferInfo, 0L);
            if (dequeueOutputBuffer == -3 && Util.SDK_INT < 21) {
                this.c = this.a.getOutputBuffers();
                continue;
            }
        } while (dequeueOutputBuffer == -3);
        return dequeueOutputBuffer;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public MediaFormat getOutputFormat() {
        return this.a.getOutputFormat();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getInputBuffer(int i) {
        if (Util.SDK_INT >= 21) {
            return this.a.getInputBuffer(i);
        }
        return ((ByteBuffer[]) Util.castNonNull(this.b))[i];
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getOutputBuffer(int i) {
        if (Util.SDK_INT >= 21) {
            return this.a.getOutputBuffer(i);
        }
        return ((ByteBuffer[]) Util.castNonNull(this.c))[i];
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueInputBuffer(int i, int i2, int i3, long j, int i4) {
        this.a.queueInputBuffer(i, i2, i3, j, i4);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueSecureInputBuffer(int i, int i2, CryptoInfo cryptoInfo, long j, int i3) {
        this.a.queueSecureInputBuffer(i, i2, cryptoInfo.getFrameworkCryptoInfo(), j, i3);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int i, boolean z) {
        this.a.releaseOutputBuffer(i, z);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(21)
    public void releaseOutputBuffer(int i, long j) {
        this.a.releaseOutputBuffer(i, j);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void flush() {
        this.a.flush();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void release() {
        this.b = null;
        this.c = null;
        this.a.release();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(23)
    public void setOnFrameRenderedListener(final MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        this.a.setOnFrameRenderedListener(new MediaCodec.OnFrameRenderedListener() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$SynchronousMediaCodecAdapter$spWI8q0tYIVzsBxMzPEVvGKiEbE
            @Override // android.media.MediaCodec.OnFrameRenderedListener
            public final void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
                SynchronousMediaCodecAdapter.this.a(onFrameRenderedListener, mediaCodec, j, j2);
            }
        }, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, MediaCodec mediaCodec, long j, long j2) {
        onFrameRenderedListener.onFrameRendered(this, j, j2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(23)
    public void setOutputSurface(Surface surface) {
        this.a.setOutputSurface(surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(19)
    public void setParameters(Bundle bundle) {
        this.a.setParameters(bundle);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setVideoScalingMode(int i) {
        this.a.setVideoScalingMode(i);
    }
}
