package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.source.rtsp.e;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class RtspMediaSource extends BaseMediaSource {
    public static final long DEFAULT_TIMEOUT_MS = 8000;
    private final MediaItem a;
    private final RtpDataChannel.Factory b;
    private final String c;
    private final Uri d;
    private boolean f;
    private boolean g;
    private long e = C.TIME_UNSET;
    private boolean h = true;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.rtsp");
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements MediaSourceFactory {
        private long a = RtspMediaSource.DEFAULT_TIMEOUT_MS;
        private String b = ExoPlayerLibraryInfo.VERSION_SLASHY;
        private boolean c;

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmUserAgent(@Nullable String str) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            return this;
        }

        public Factory setForceUseRtpTcp(boolean z) {
            this.c = z;
            return this;
        }

        public Factory setUserAgent(String str) {
            this.b = str;
            return this;
        }

        public Factory setTimeoutMs(@IntRange(from = 1) long j) {
            Assertions.checkArgument(j > 0);
            this.a = j;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{3};
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public RtspMediaSource createMediaSource(MediaItem mediaItem) {
            RtpDataChannel.Factory factory;
            Assertions.checkNotNull(mediaItem.playbackProperties);
            if (this.c) {
                factory = new o(this.a);
            } else {
                factory = new q(this.a);
            }
            return new RtspMediaSource(mediaItem, factory, this.b);
        }
    }

    /* loaded from: classes2.dex */
    public static final class RtspPlaybackException extends IOException {
        public RtspPlaybackException(String str) {
            super(str);
        }

        public RtspPlaybackException(Throwable th) {
            super(th);
        }

        public RtspPlaybackException(String str, Throwable th) {
            super(str, th);
        }
    }

    @VisibleForTesting
    RtspMediaSource(MediaItem mediaItem, RtpDataChannel.Factory factory, String str) {
        this.a = mediaItem;
        this.b = factory;
        this.c = str;
        this.d = ((MediaItem.PlaybackProperties) Assertions.checkNotNull(this.a.playbackProperties)).uri;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        a();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        return new e(allocator, this.b, this.d, new e.b() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$RtspMediaSource$rAb0qU1e75nd_nj1qFYd-QMz-pk
            @Override // com.google.android.exoplayer2.source.rtsp.e.b
            public final void onSourceInfoRefreshed(j jVar) {
                RtspMediaSource.this.a(jVar);
            }
        }, this.c);
    }

    public /* synthetic */ void a(j jVar) {
        this.e = C.msToUs(jVar.b());
        this.f = !jVar.a();
        this.g = jVar.a();
        this.h = false;
        a();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((e) mediaPeriod).a();
    }

    private void a() {
        SinglePeriodTimeline singlePeriodTimeline = new SinglePeriodTimeline(this.e, this.f, false, this.g, (Object) null, this.a);
        refreshSourceInfo(this.h ? new ForwardingTimeline(this, singlePeriodTimeline) { // from class: com.google.android.exoplayer2.source.rtsp.RtspMediaSource.1
            @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
            public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
                super.getWindow(i, window, j);
                window.isPlaceholder = true;
                return window;
            }

            @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
            public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
                super.getPeriod(i, period, z);
                period.isPlaceholder = true;
                return period;
            }
        } : singlePeriodTimeline);
    }
}
