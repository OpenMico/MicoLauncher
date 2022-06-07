package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.common.base.Supplier;
import com.xiaomi.ai.api.AIApiConstants;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

/* JADX INFO: Access modifiers changed from: package-private */
@RequiresApi(23)
/* loaded from: classes2.dex */
public final class AsynchronousMediaCodecAdapter implements MediaCodecAdapter {
    private final MediaCodec a;
    private final b b;
    private final a c;
    private final boolean d;
    private boolean e;
    private int f;

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public boolean needsReconfiguration() {
        return false;
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements MediaCodecAdapter.Factory {
        private final Supplier<HandlerThread> a;
        private final Supplier<HandlerThread> b;
        private final boolean c;
        private final boolean d;

        public Factory(int i) {
            this(i, false, false);
        }

        public Factory(final int i, boolean z, boolean z2) {
            this(new Supplier() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$AsynchronousMediaCodecAdapter$Factory$B9OVjQnINm8O3UZwhq8dIYuZx9o
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    HandlerThread b;
                    b = AsynchronousMediaCodecAdapter.Factory.b(i);
                    return b;
                }
            }, new Supplier() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$AsynchronousMediaCodecAdapter$Factory$jPEEZ55MVtNy61XNze4_KF_5kpA
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    HandlerThread a;
                    a = AsynchronousMediaCodecAdapter.Factory.a(i);
                    return a;
                }
            }, z, z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ HandlerThread b(int i) {
            return new HandlerThread(AsynchronousMediaCodecAdapter.c(i));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ HandlerThread a(int i) {
            return new HandlerThread(AsynchronousMediaCodecAdapter.d(i));
        }

        @VisibleForTesting
        Factory(Supplier<HandlerThread> supplier, Supplier<HandlerThread> supplier2, boolean z, boolean z2) {
            this.a = supplier;
            this.b = supplier2;
            this.c = z;
            this.d = z2;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
        public AsynchronousMediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws IOException {
            Exception e;
            MediaCodec mediaCodec;
            String str = configuration.codecInfo.name;
            AsynchronousMediaCodecAdapter asynchronousMediaCodecAdapter = null;
            try {
                String valueOf = String.valueOf(str);
                TraceUtil.beginSection(valueOf.length() != 0 ? "createCodec:".concat(valueOf) : new String("createCodec:"));
                mediaCodec = MediaCodec.createByCodecName(str);
                try {
                    AsynchronousMediaCodecAdapter asynchronousMediaCodecAdapter2 = new AsynchronousMediaCodecAdapter(mediaCodec, this.a.get(), this.b.get(), this.c, this.d);
                    try {
                        TraceUtil.endSection();
                        asynchronousMediaCodecAdapter2.a(configuration.mediaFormat, configuration.surface, configuration.crypto, configuration.flags);
                        return asynchronousMediaCodecAdapter2;
                    } catch (Exception e2) {
                        e = e2;
                        asynchronousMediaCodecAdapter = asynchronousMediaCodecAdapter2;
                        if (asynchronousMediaCodecAdapter != null) {
                            asynchronousMediaCodecAdapter.release();
                        } else if (mediaCodec != null) {
                            mediaCodec.release();
                        }
                        throw e;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                mediaCodec = null;
            }
        }
    }

    private AsynchronousMediaCodecAdapter(MediaCodec mediaCodec, HandlerThread handlerThread, HandlerThread handlerThread2, boolean z, boolean z2) {
        this.a = mediaCodec;
        this.b = new b(handlerThread);
        this.c = new a(mediaCodec, handlerThread2, z);
        this.d = z2;
        this.f = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@Nullable MediaFormat mediaFormat, @Nullable Surface surface, @Nullable MediaCrypto mediaCrypto, int i) {
        this.b.a(this.a);
        TraceUtil.beginSection("configureCodec");
        this.a.configure(mediaFormat, surface, mediaCrypto, i);
        TraceUtil.endSection();
        this.c.a();
        TraceUtil.beginSection("startCodec");
        this.a.start();
        TraceUtil.endSection();
        this.f = 1;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueInputBuffer(int i, int i2, int i3, long j, int i4) {
        this.c.a(i, i2, i3, j, i4);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueSecureInputBuffer(int i, int i2, CryptoInfo cryptoInfo, long j, int i3) {
        this.c.a(i, i2, cryptoInfo, j, i3);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int i, boolean z) {
        this.a.releaseOutputBuffer(i, z);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int i, long j) {
        this.a.releaseOutputBuffer(i, j);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueInputBufferIndex() {
        return this.b.b();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        return this.b.a(bufferInfo);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public MediaFormat getOutputFormat() {
        return this.b.c();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getInputBuffer(int i) {
        return this.a.getInputBuffer(i);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getOutputBuffer(int i) {
        return this.a.getOutputBuffer(i);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void flush() {
        this.c.b();
        this.a.flush();
        b bVar = this.b;
        final MediaCodec mediaCodec = this.a;
        Objects.requireNonNull(mediaCodec);
        bVar.a(new Runnable() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$izPR8Lzfsy3-jbfJFz3Zg9j84Yw
            @Override // java.lang.Runnable
            public final void run() {
                mediaCodec.start();
            }
        });
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void release() {
        try {
            if (this.f == 1) {
                this.c.c();
                this.b.a();
            }
            this.f = 2;
        } finally {
            if (!this.e) {
                this.a.release();
                this.e = true;
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOnFrameRenderedListener(final MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        a();
        this.a.setOnFrameRenderedListener(new MediaCodec.OnFrameRenderedListener() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$AsynchronousMediaCodecAdapter$vnAdYFzlFXFSLwlsgKhmk2Ty5aM
            @Override // android.media.MediaCodec.OnFrameRenderedListener
            public final void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
                AsynchronousMediaCodecAdapter.this.a(onFrameRenderedListener, mediaCodec, j, j2);
            }
        }, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, MediaCodec mediaCodec, long j, long j2) {
        onFrameRenderedListener.onFrameRendered(this, j, j2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOutputSurface(Surface surface) {
        a();
        this.a.setOutputSurface(surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setParameters(Bundle bundle) {
        a();
        this.a.setParameters(bundle);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setVideoScalingMode(int i) {
        a();
        this.a.setVideoScalingMode(i);
    }

    private void a() {
        if (this.d) {
            try {
                this.c.d();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(int i) {
        return a(i, "ExoPlayer:MediaCodecAsyncAdapter:");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(int i) {
        return a(i, "ExoPlayer:MediaCodecQueueingThread:");
    }

    private static String a(int i, String str) {
        StringBuilder sb = new StringBuilder(str);
        if (i == 1) {
            sb.append("Audio");
        } else if (i == 2) {
            sb.append(AIApiConstants.Video.NAME);
        } else {
            sb.append("Unknown(");
            sb.append(i);
            sb.append(")");
        }
        return sb.toString();
    }
}
