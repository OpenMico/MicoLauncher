package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class SsMediaSource extends BaseMediaSource implements Loader.Callback<ParsingLoadable<SsManifest>> {
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    private final boolean a;
    private final Uri b;
    private final MediaItem.PlaybackProperties c;
    private final MediaItem d;
    private final DataSource.Factory e;
    private final SsChunkSource.Factory f;
    private final CompositeSequenceableLoaderFactory g;
    private final DrmSessionManager h;
    private final LoadErrorHandlingPolicy i;
    private final long j;
    private final MediaSourceEventListener.EventDispatcher k;
    private final ParsingLoadable.Parser<? extends SsManifest> l;
    private final ArrayList<a> m;
    private DataSource n;
    private Loader o;
    private LoaderErrorThrower p;
    @Nullable
    private TransferListener q;
    private long r;
    private SsManifest s;
    private Handler t;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.smoothstreaming");
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements MediaSourceFactory {
        private final SsChunkSource.Factory a;
        @Nullable
        private final DataSource.Factory b;
        private CompositeSequenceableLoaderFactory c;
        private boolean d;
        private DrmSessionManagerProvider e;
        private LoadErrorHandlingPolicy f;
        private long g;
        @Nullable
        private ParsingLoadable.Parser<? extends SsManifest> h;
        private List<StreamKey> i;
        @Nullable
        private Object j;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ DrmSessionManager a(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        public Factory(DataSource.Factory factory) {
            this(new DefaultSsChunkSource.Factory(factory), factory);
        }

        public Factory(SsChunkSource.Factory factory, @Nullable DataSource.Factory factory2) {
            this.a = (SsChunkSource.Factory) Assertions.checkNotNull(factory);
            this.b = factory2;
            this.e = new DefaultDrmSessionManagerProvider();
            this.f = new DefaultLoadErrorHandlingPolicy();
            this.g = 30000L;
            this.c = new DefaultCompositeSequenceableLoaderFactory();
            this.i = Collections.emptyList();
        }

        @Deprecated
        public Factory setTag(@Nullable Object obj) {
            this.j = obj;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.f = loadErrorHandlingPolicy;
            return this;
        }

        public Factory setLivePresentationDelayMs(long j) {
            this.g = j;
            return this;
        }

        public Factory setManifestParser(@Nullable ParsingLoadable.Parser<? extends SsManifest> parser) {
            this.h = parser;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(@Nullable CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            if (compositeSequenceableLoaderFactory == null) {
                compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            }
            this.c = compositeSequenceableLoaderFactory;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            if (drmSessionManagerProvider != null) {
                this.e = drmSessionManagerProvider;
                this.d = true;
            } else {
                this.e = new DefaultDrmSessionManagerProvider();
                this.d = false;
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManager(@Nullable final DrmSessionManager drmSessionManager) {
            if (drmSessionManager == null) {
                setDrmSessionManagerProvider((DrmSessionManagerProvider) null);
            } else {
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.smoothstreaming.-$$Lambda$SsMediaSource$Factory$BcWCo2ot37ddX7mHhWiVFUigT8s
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        DrmSessionManager a;
                        a = SsMediaSource.Factory.a(DrmSessionManager.this, mediaItem);
                        return a;
                    }
                });
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            if (!this.d) {
                ((DefaultDrmSessionManagerProvider) this.e).setDrmHttpDataSourceFactory(factory);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmUserAgent(@Nullable String str) {
            if (!this.d) {
                ((DefaultDrmSessionManagerProvider) this.e).setDrmUserAgent(str);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setStreamKeys(@Nullable List<StreamKey> list) {
            if (list == null) {
                list = Collections.emptyList();
            }
            this.i = list;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public SsMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).build());
        }

        public SsMediaSource createMediaSource(SsManifest ssManifest) {
            return createMediaSource(ssManifest, MediaItem.fromUri(Uri.EMPTY));
        }

        public SsMediaSource createMediaSource(SsManifest ssManifest, MediaItem mediaItem) {
            List<StreamKey> list;
            Object obj;
            boolean z = true;
            Assertions.checkArgument(!ssManifest.isLive);
            if (mediaItem.playbackProperties == null || mediaItem.playbackProperties.streamKeys.isEmpty()) {
                list = this.i;
            } else {
                list = mediaItem.playbackProperties.streamKeys;
            }
            SsManifest copy = !list.isEmpty() ? ssManifest.copy(list) : ssManifest;
            boolean z2 = mediaItem.playbackProperties != null;
            if (!z2 || mediaItem.playbackProperties.tag == null) {
                z = false;
            }
            MediaItem.Builder uri = mediaItem.buildUpon().setMimeType(MimeTypes.APPLICATION_SS).setUri(z2 ? mediaItem.playbackProperties.uri : Uri.EMPTY);
            if (z) {
                obj = mediaItem.playbackProperties.tag;
            } else {
                obj = this.j;
            }
            MediaItem build = uri.setTag(obj).setStreamKeys(list).build();
            return new SsMediaSource(build, copy, null, null, this.a, this.c, this.e.get(build), this.f, this.g);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public SsMediaSource createMediaSource(MediaItem mediaItem) {
            List<StreamKey> list;
            Assertions.checkNotNull(mediaItem.playbackProperties);
            ParsingLoadable.Parser parser = this.h;
            if (parser == null) {
                parser = new SsManifestParser();
            }
            if (!mediaItem.playbackProperties.streamKeys.isEmpty()) {
                list = mediaItem.playbackProperties.streamKeys;
            } else {
                list = this.i;
            }
            FilteringManifestParser filteringManifestParser = !list.isEmpty() ? new FilteringManifestParser(parser, list) : parser;
            boolean z = true;
            boolean z2 = mediaItem.playbackProperties.tag == null && this.j != null;
            if (!mediaItem.playbackProperties.streamKeys.isEmpty() || list.isEmpty()) {
                z = false;
            }
            MediaItem build = (!z2 || !z) ? z2 ? mediaItem.buildUpon().setTag(this.j).build() : z ? mediaItem.buildUpon().setStreamKeys(list).build() : mediaItem : mediaItem.buildUpon().setTag(this.j).setStreamKeys(list).build();
            return new SsMediaSource(build, null, this.b, filteringManifestParser, this.a, this.c, this.e.get(build), this.f, this.g);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{1};
        }
    }

    private SsMediaSource(MediaItem mediaItem, @Nullable SsManifest ssManifest, @Nullable DataSource.Factory factory, @Nullable ParsingLoadable.Parser<? extends SsManifest> parser, SsChunkSource.Factory factory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j) {
        boolean z = false;
        Assertions.checkState(ssManifest == null || !ssManifest.isLive);
        this.d = mediaItem;
        this.c = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.s = ssManifest;
        this.b = this.c.uri.equals(Uri.EMPTY) ? null : Util.fixSmoothStreamingIsmManifestUri(this.c.uri);
        this.e = factory;
        this.l = parser;
        this.f = factory2;
        this.g = compositeSequenceableLoaderFactory;
        this.h = drmSessionManager;
        this.i = loadErrorHandlingPolicy;
        this.j = j;
        this.k = createEventDispatcher(null);
        this.a = ssManifest != null ? true : z;
        this.m = new ArrayList<>();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.q = transferListener;
        this.h.prepare();
        if (this.a) {
            this.p = new LoaderErrorThrower.Dummy();
            a();
            return;
        }
        this.n = this.e.createDataSource();
        this.o = new Loader("SsMediaSource");
        this.p = this.o;
        this.t = Util.createHandlerForCurrentLooper();
        c();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.p.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        MediaSourceEventListener.EventDispatcher createEventDispatcher = createEventDispatcher(mediaPeriodId);
        a aVar = new a(this.s, this.f, this.q, this.g, this.h, createDrmEventDispatcher(mediaPeriodId), this.i, createEventDispatcher, this.p, allocator);
        this.m.add(aVar);
        return aVar;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((a) mediaPeriod).a();
        this.m.remove(mediaPeriod);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.s = this.a ? this.s : null;
        this.n = null;
        this.r = 0L;
        Loader loader = this.o;
        if (loader != null) {
            loader.release();
            this.o = null;
        }
        Handler handler = this.t;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.t = null;
        }
        this.h.release();
    }

    public void onLoadCompleted(ParsingLoadable<SsManifest> parsingLoadable, long j, long j2) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.i.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.k.loadCompleted(loadEventInfo, parsingLoadable.type);
        this.s = parsingLoadable.getResult();
        this.r = j - j2;
        a();
        b();
    }

    public void onLoadCanceled(ParsingLoadable<SsManifest> parsingLoadable, long j, long j2, boolean z) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.i.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.k.loadCanceled(loadEventInfo, parsingLoadable.type);
    }

    public Loader.LoadErrorAction onLoadError(ParsingLoadable<SsManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
        Loader.LoadErrorAction loadErrorAction;
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        long retryDelayMsFor = this.i.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(parsingLoadable.type), iOException, i));
        if (retryDelayMsFor == C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
        }
        boolean z = !loadErrorAction.isRetry();
        this.k.loadError(loadEventInfo, parsingLoadable.type, iOException, z);
        if (z) {
            this.i.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        }
        return loadErrorAction;
    }

    private void a() {
        SinglePeriodTimeline singlePeriodTimeline;
        for (int i = 0; i < this.m.size(); i++) {
            this.m.get(i).a(this.s);
        }
        long j = Long.MIN_VALUE;
        SsManifest.StreamElement[] streamElementArr = this.s.streamElements;
        long j2 = Long.MAX_VALUE;
        for (SsManifest.StreamElement streamElement : streamElementArr) {
            if (streamElement.chunkCount > 0) {
                j2 = Math.min(j2, streamElement.getStartTimeUs(0));
                j = Math.max(j, streamElement.getStartTimeUs(streamElement.chunkCount - 1) + streamElement.getChunkDurationUs(streamElement.chunkCount - 1));
            }
        }
        if (j2 == Long.MAX_VALUE) {
            singlePeriodTimeline = new SinglePeriodTimeline(this.s.isLive ? -9223372036854775807L : 0L, 0L, 0L, 0L, true, this.s.isLive, this.s.isLive, (Object) this.s, this.d);
        } else if (this.s.isLive) {
            long max = (this.s.dvrWindowLengthUs == C.TIME_UNSET || this.s.dvrWindowLengthUs <= 0) ? j2 : Math.max(j2, j - this.s.dvrWindowLengthUs);
            long j3 = j - max;
            long msToUs = j3 - C.msToUs(this.j);
            singlePeriodTimeline = new SinglePeriodTimeline((long) C.TIME_UNSET, j3, max, msToUs < 5000000 ? Math.min(5000000L, j3 / 2) : msToUs, true, true, true, (Object) this.s, this.d);
        } else {
            long j4 = this.s.durationUs != C.TIME_UNSET ? this.s.durationUs : j - j2;
            singlePeriodTimeline = new SinglePeriodTimeline(j2 + j4, j4, j2, 0L, true, false, false, (Object) this.s, this.d);
        }
        refreshSourceInfo(singlePeriodTimeline);
    }

    private void b() {
        if (this.s.isLive) {
            this.t.postDelayed(new Runnable() { // from class: com.google.android.exoplayer2.source.smoothstreaming.-$$Lambda$SsMediaSource$0amQHjrf3tH0AHgA0N2-ViKA0qI
                @Override // java.lang.Runnable
                public final void run() {
                    SsMediaSource.this.c();
                }
            }, Math.max(0L, (this.r + 5000) - SystemClock.elapsedRealtime()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (!this.o.hasFatalError()) {
            ParsingLoadable parsingLoadable = new ParsingLoadable(this.n, this.b, 4, this.l);
            this.k.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.o.startLoading(parsingLoadable, this, this.i.getMinimumLoadableRetryCount(parsingLoadable.type))), parsingLoadable.type);
        }
    }
}
