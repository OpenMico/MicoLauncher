package com.google.android.exoplayer2.video;

import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* loaded from: classes2.dex */
public interface VideoRendererEventListener {
    default void onDroppedFrames(int i, long j) {
    }

    default void onRenderedFirstFrame(Object obj, long j) {
    }

    default void onVideoCodecError(Exception exc) {
    }

    default void onVideoDecoderInitialized(String str, long j, long j2) {
    }

    default void onVideoDecoderReleased(String str) {
    }

    default void onVideoDisabled(DecoderCounters decoderCounters) {
    }

    default void onVideoEnabled(DecoderCounters decoderCounters) {
    }

    default void onVideoFrameProcessingOffset(long j, int i) {
    }

    @Deprecated
    default void onVideoInputFormatChanged(Format format) {
    }

    default void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
    }

    default void onVideoSizeChanged(VideoSize videoSize) {
    }

    /* loaded from: classes2.dex */
    public static final class EventDispatcher {
        @Nullable
        private final Handler a;
        @Nullable
        private final VideoRendererEventListener b;

        public EventDispatcher(@Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener) {
            this.a = videoRendererEventListener != null ? (Handler) Assertions.checkNotNull(handler) : null;
            this.b = videoRendererEventListener;
        }

        public void enabled(final DecoderCounters decoderCounters) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$qVH95EiSHcj8t8SCKd76fLpLJhA
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.b(decoderCounters);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(DecoderCounters decoderCounters) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoEnabled(decoderCounters);
        }

        public void decoderInitialized(final String str, final long j, final long j2) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$sQ6oyHPc-EIDAZq0aU4vhaHVh60
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(str, j, j2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str, long j, long j2) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoDecoderInitialized(str, j, j2);
        }

        public void inputFormatChanged(final Format format, @Nullable final DecoderReuseEvaluation decoderReuseEvaluation) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$MponszpdBDt5TyUTjUJMaFO7jaM
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(format, decoderReuseEvaluation);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoInputFormatChanged(format);
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoInputFormatChanged(format, decoderReuseEvaluation);
        }

        public void droppedFrames(final int i, final long j) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$qOSCe2rwwRsf1Bnjam4F-q-xXXY
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(i, j);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(int i, long j) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onDroppedFrames(i, j);
        }

        public void reportVideoFrameProcessingOffset(final long j, final int i) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$fxGaBgtFl76rsLy1V8Qx4B3yYfA
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(j, i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(long j, int i) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoFrameProcessingOffset(j, i);
        }

        public void videoSizeChanged(final VideoSize videoSize) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$-GTpJ8Ngzzmay_OGOJ930XFbajk
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(videoSize);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(VideoSize videoSize) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoSizeChanged(videoSize);
        }

        public void renderedFirstFrame(final Object obj) {
            if (this.a != null) {
                final long elapsedRealtime = SystemClock.elapsedRealtime();
                this.a.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$4y6d98fvyCtbvuXcJgywFCWbF-4
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(obj, elapsedRealtime);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(Object obj, long j) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onRenderedFirstFrame(obj, j);
        }

        public void decoderReleased(final String str) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$tlyGi_sedlkKmaq5o0TdYFgk8dg
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(str);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoDecoderReleased(str);
        }

        public void disabled(final DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$gFF7yauzxYgwft5IlTYb8Kf4ts8
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(decoderCounters);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoDisabled(decoderCounters);
        }

        public void videoCodecError(final Exception exc) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoRendererEventListener$EventDispatcher$ZXVefyUor7LCFaGiCiQ3_-RjqCY
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.a(exc);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(Exception exc) {
            ((VideoRendererEventListener) Util.castNonNull(this.b)).onVideoCodecError(exc);
        }
    }
}
