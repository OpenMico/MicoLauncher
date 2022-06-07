package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.b;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes2.dex */
public final class ProgressiveMediaSource extends BaseMediaSource implements b.AbstractC0067b {
    public static final int DEFAULT_LOADING_CHECK_INTERVAL_BYTES = 1048576;
    private final MediaItem a;
    private final MediaItem.PlaybackProperties b;
    private final DataSource.Factory c;
    private final ProgressiveMediaExtractor.Factory d;
    private final DrmSessionManager e;
    private final LoadErrorHandlingPolicy f;
    private final int g;
    private boolean h;
    private long i;
    private boolean j;
    private boolean k;
    @Nullable
    private TransferListener l;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements MediaSourceFactory {
        private final DataSource.Factory a;
        private ProgressiveMediaExtractor.Factory b;
        private boolean c;
        private DrmSessionManagerProvider d;
        private LoadErrorHandlingPolicy e;
        private int f;
        @Nullable
        private String g;
        @Nullable
        private Object h;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ DrmSessionManager a(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        public Factory(DataSource.Factory factory) {
            this(factory, new DefaultExtractorsFactory());
        }

        public Factory(DataSource.Factory factory, final ExtractorsFactory extractorsFactory) {
            this(factory, new ProgressiveMediaExtractor.Factory() { // from class: com.google.android.exoplayer2.source.-$$Lambda$ProgressiveMediaSource$Factory$8_dKOz2dxps8ovgHNs0c-UqGgFU
                @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
                public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
                    ProgressiveMediaExtractor b;
                    b = ProgressiveMediaSource.Factory.b(ExtractorsFactory.this);
                    return b;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ ProgressiveMediaExtractor b(ExtractorsFactory extractorsFactory) {
            return new BundledExtractorsAdapter(extractorsFactory);
        }

        public Factory(DataSource.Factory factory, ProgressiveMediaExtractor.Factory factory2) {
            this.a = factory;
            this.b = factory2;
            this.d = new DefaultDrmSessionManagerProvider();
            this.e = new DefaultLoadErrorHandlingPolicy();
            this.f = 1048576;
        }

        @Deprecated
        public Factory setExtractorsFactory(@Nullable final ExtractorsFactory extractorsFactory) {
            this.b = new ProgressiveMediaExtractor.Factory() { // from class: com.google.android.exoplayer2.source.-$$Lambda$ProgressiveMediaSource$Factory$s98CO-AlNxyWx9uemn3DCuZJ5lo
                @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
                public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
                    ProgressiveMediaExtractor a;
                    a = ProgressiveMediaSource.Factory.a(ExtractorsFactory.this);
                    return a;
                }
            };
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ ProgressiveMediaExtractor a(ExtractorsFactory extractorsFactory) {
            if (extractorsFactory == null) {
                extractorsFactory = new DefaultExtractorsFactory();
            }
            return new BundledExtractorsAdapter(extractorsFactory);
        }

        @Deprecated
        public Factory setCustomCacheKey(@Nullable String str) {
            this.g = str;
            return this;
        }

        @Deprecated
        public Factory setTag(@Nullable Object obj) {
            this.h = obj;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.e = loadErrorHandlingPolicy;
            return this;
        }

        public Factory setContinueLoadingCheckIntervalBytes(int i) {
            this.f = i;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            if (drmSessionManagerProvider != null) {
                this.d = drmSessionManagerProvider;
                this.c = true;
            } else {
                this.d = new DefaultDrmSessionManagerProvider();
                this.c = false;
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManager(@Nullable final DrmSessionManager drmSessionManager) {
            if (drmSessionManager == null) {
                setDrmSessionManagerProvider((DrmSessionManagerProvider) null);
            } else {
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.-$$Lambda$ProgressiveMediaSource$Factory$LeYWgB9flnASkP-Lrnd9VMBprNE
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        DrmSessionManager a;
                        a = ProgressiveMediaSource.Factory.a(DrmSessionManager.this, mediaItem);
                        return a;
                    }
                });
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            if (!this.c) {
                ((DefaultDrmSessionManagerProvider) this.d).setDrmHttpDataSourceFactory(factory);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmUserAgent(@Nullable String str) {
            if (!this.c) {
                ((DefaultDrmSessionManagerProvider) this.d).setDrmUserAgent(str);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public ProgressiveMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public ProgressiveMediaSource createMediaSource(MediaItem mediaItem) {
            MediaItem mediaItem2;
            Assertions.checkNotNull(mediaItem.playbackProperties);
            boolean z = true;
            boolean z2 = mediaItem.playbackProperties.tag == null && this.h != null;
            if (mediaItem.playbackProperties.customCacheKey != null || this.g == null) {
                z = false;
            }
            if (z2 && z) {
                mediaItem2 = mediaItem.buildUpon().setTag(this.h).setCustomCacheKey(this.g).build();
            } else if (z2) {
                mediaItem2 = mediaItem.buildUpon().setTag(this.h).build();
            } else {
                mediaItem2 = z ? mediaItem.buildUpon().setCustomCacheKey(this.g).build() : mediaItem;
            }
            return new ProgressiveMediaSource(mediaItem2, this.a, this.b, this.d.get(mediaItem2), this.e, this.f);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{4};
        }
    }

    private ProgressiveMediaSource(MediaItem mediaItem, DataSource.Factory factory, ProgressiveMediaExtractor.Factory factory2, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, int i) {
        this.b = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.a = mediaItem;
        this.c = factory;
        this.d = factory2;
        this.e = drmSessionManager;
        this.f = loadErrorHandlingPolicy;
        this.g = i;
        this.h = true;
        this.i = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.l = transferListener;
        this.e.prepare();
        a();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        DataSource createDataSource = this.c.createDataSource();
        TransferListener transferListener = this.l;
        if (transferListener != null) {
            createDataSource.addTransferListener(transferListener);
        }
        return new b(this.b.uri, createDataSource, this.d.createProgressiveMediaExtractor(), this.e, createDrmEventDispatcher(mediaPeriodId), this.f, createEventDispatcher(mediaPeriodId), this, allocator, this.b.customCacheKey, this.g);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((b) mediaPeriod).a();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.e.release();
    }

    @Override // com.google.android.exoplayer2.source.b.AbstractC0067b
    public void onSourceInfoRefreshed(long j, boolean z, boolean z2) {
        if (j == C.TIME_UNSET) {
            j = this.i;
        }
        if (this.h || this.i != j || this.j != z || this.k != z2) {
            this.i = j;
            this.j = z;
            this.k = z2;
            this.h = false;
            a();
        }
    }

    private void a() {
        SinglePeriodTimeline singlePeriodTimeline = new SinglePeriodTimeline(this.i, this.j, false, this.k, (Object) null, this.a);
        refreshSourceInfo(this.h ? new ForwardingTimeline(this, singlePeriodTimeline) { // from class: com.google.android.exoplayer2.source.ProgressiveMediaSource.1
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
