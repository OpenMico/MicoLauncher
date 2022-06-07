package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.Timeline;
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
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement;
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
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.SntpClient;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import com.google.common.math.LongMath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public final class DashMediaSource extends BaseMediaSource {
    public static final long DEFAULT_FALLBACK_TARGET_LIVE_OFFSET_MS = 30000;
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    public static final String DEFAULT_MEDIA_ID = "DashMediaSource";
    private DashManifest A;
    private boolean B;
    private long C;
    private long D;
    private long E;
    private int F;
    private long G;
    private int H;
    private final MediaItem a;
    private final boolean b;
    private final DataSource.Factory c;
    private final DashChunkSource.Factory d;
    private final CompositeSequenceableLoaderFactory e;
    private final DrmSessionManager f;
    private final LoadErrorHandlingPolicy g;
    private final BaseUrlExclusionList h;
    private final long i;
    private final MediaSourceEventListener.EventDispatcher j;
    private final ParsingLoadable.Parser<? extends DashManifest> k;
    private final d l;
    private final Object m;
    private final SparseArray<DashMediaPeriod> n;
    private final Runnable o;
    private final Runnable p;
    private final PlayerEmsgHandler.PlayerEmsgCallback q;
    private final LoaderErrorThrower r;
    private DataSource s;
    private Loader t;
    @Nullable
    private TransferListener u;
    private IOException v;
    private Handler w;
    private MediaItem.LiveConfiguration x;
    private Uri y;
    private Uri z;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.dash");
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements MediaSourceFactory {
        private final DashChunkSource.Factory a;
        @Nullable
        private final DataSource.Factory b;
        private boolean c;
        private DrmSessionManagerProvider d;
        private CompositeSequenceableLoaderFactory e;
        private LoadErrorHandlingPolicy f;
        private long g;
        private long h;
        @Nullable
        private ParsingLoadable.Parser<? extends DashManifest> i;
        private List<StreamKey> j;
        @Nullable
        private Object k;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ DrmSessionManager a(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        public Factory(DataSource.Factory factory) {
            this(new DefaultDashChunkSource.Factory(factory), factory);
        }

        public Factory(DashChunkSource.Factory factory, @Nullable DataSource.Factory factory2) {
            this.a = (DashChunkSource.Factory) Assertions.checkNotNull(factory);
            this.b = factory2;
            this.d = new DefaultDrmSessionManagerProvider();
            this.f = new DefaultLoadErrorHandlingPolicy();
            this.g = C.TIME_UNSET;
            this.h = 30000L;
            this.e = new DefaultCompositeSequenceableLoaderFactory();
            this.j = Collections.emptyList();
        }

        @Deprecated
        public Factory setTag(@Nullable Object obj) {
            this.k = obj;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setStreamKeys(@Nullable List<StreamKey> list) {
            if (list == null) {
                list = Collections.emptyList();
            }
            this.j = list;
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
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.dash.-$$Lambda$DashMediaSource$Factory$UQwgdPfvOClPqwbSrlEY-o-nkoU
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        DrmSessionManager a;
                        a = DashMediaSource.Factory.a(DrmSessionManager.this, mediaItem);
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
        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.f = loadErrorHandlingPolicy;
            return this;
        }

        @Deprecated
        public Factory setLivePresentationDelayMs(long j, boolean z) {
            this.g = z ? j : C.TIME_UNSET;
            if (!z) {
                setFallbackTargetLiveOffsetMs(j);
            }
            return this;
        }

        public Factory setFallbackTargetLiveOffsetMs(long j) {
            this.h = j;
            return this;
        }

        public Factory setManifestParser(@Nullable ParsingLoadable.Parser<? extends DashManifest> parser) {
            this.i = parser;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(@Nullable CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            if (compositeSequenceableLoaderFactory == null) {
                compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            }
            this.e = compositeSequenceableLoaderFactory;
            return this;
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest) {
            return createMediaSource(dashManifest, new MediaItem.Builder().setUri(Uri.EMPTY).setMediaId(DashMediaSource.DEFAULT_MEDIA_ID).setMimeType(MimeTypes.APPLICATION_MPD).setStreamKeys(this.j).setTag(this.k).build());
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest, MediaItem mediaItem) {
            List<StreamKey> list;
            Object obj;
            long j;
            boolean z = true;
            Assertions.checkArgument(!dashManifest.dynamic);
            if (mediaItem.playbackProperties == null || mediaItem.playbackProperties.streamKeys.isEmpty()) {
                list = this.j;
            } else {
                list = mediaItem.playbackProperties.streamKeys;
            }
            DashManifest copy = !list.isEmpty() ? dashManifest.copy(list) : dashManifest;
            boolean z2 = mediaItem.playbackProperties != null;
            boolean z3 = z2 && mediaItem.playbackProperties.tag != null;
            if (mediaItem.liveConfiguration.targetOffsetMs == C.TIME_UNSET) {
                z = false;
            }
            MediaItem.Builder uri = mediaItem.buildUpon().setMimeType(MimeTypes.APPLICATION_MPD).setUri(z2 ? mediaItem.playbackProperties.uri : Uri.EMPTY);
            if (z3) {
                obj = mediaItem.playbackProperties.tag;
            } else {
                obj = this.k;
            }
            MediaItem.Builder tag = uri.setTag(obj);
            if (z) {
                j = mediaItem.liveConfiguration.targetOffsetMs;
            } else {
                j = this.g;
            }
            MediaItem build = tag.setLiveTargetOffsetMs(j).setStreamKeys(list).build();
            return new DashMediaSource(build, copy, null, null, this.a, this.e, this.d.get(build), this.f, this.h);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public DashMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_MPD).setTag(this.k).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public DashMediaSource createMediaSource(MediaItem mediaItem) {
            List<StreamKey> list;
            MediaItem mediaItem2;
            Assertions.checkNotNull(mediaItem.playbackProperties);
            ParsingLoadable.Parser parser = this.i;
            if (parser == null) {
                parser = new DashManifestParser();
            }
            if (mediaItem.playbackProperties.streamKeys.isEmpty()) {
                list = this.j;
            } else {
                list = mediaItem.playbackProperties.streamKeys;
            }
            FilteringManifestParser filteringManifestParser = !list.isEmpty() ? new FilteringManifestParser(parser, list) : parser;
            boolean z = true;
            boolean z2 = mediaItem.playbackProperties.tag == null && this.k != null;
            boolean z3 = mediaItem.playbackProperties.streamKeys.isEmpty() && !list.isEmpty();
            if (mediaItem.liveConfiguration.targetOffsetMs != C.TIME_UNSET || this.g == C.TIME_UNSET) {
                z = false;
            }
            if (z2 || z3 || z) {
                MediaItem.Builder buildUpon = mediaItem.buildUpon();
                if (z2) {
                    buildUpon.setTag(this.k);
                }
                if (z3) {
                    buildUpon.setStreamKeys(list);
                }
                if (z) {
                    buildUpon.setLiveTargetOffsetMs(this.g);
                }
                mediaItem2 = buildUpon.build();
            } else {
                mediaItem2 = mediaItem;
            }
            return new DashMediaSource(mediaItem2, null, this.b, filteringManifestParser, this.a, this.e, this.d.get(mediaItem2), this.f, this.h);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{0};
        }
    }

    private DashMediaSource(MediaItem mediaItem, @Nullable DashManifest dashManifest, @Nullable DataSource.Factory factory, @Nullable ParsingLoadable.Parser<? extends DashManifest> parser, DashChunkSource.Factory factory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j) {
        this.a = mediaItem;
        this.x = mediaItem.liveConfiguration;
        this.y = ((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties)).uri;
        this.z = mediaItem.playbackProperties.uri;
        this.A = dashManifest;
        this.c = factory;
        this.k = parser;
        this.d = factory2;
        this.f = drmSessionManager;
        this.g = loadErrorHandlingPolicy;
        this.i = j;
        this.e = compositeSequenceableLoaderFactory;
        this.h = new BaseUrlExclusionList();
        this.b = dashManifest != null;
        this.j = createEventDispatcher(null);
        this.m = new Object();
        this.n = new SparseArray<>();
        this.q = new b();
        this.G = C.TIME_UNSET;
        this.E = C.TIME_UNSET;
        if (this.b) {
            Assertions.checkState(true ^ dashManifest.dynamic);
            this.l = null;
            this.o = null;
            this.p = null;
            this.r = new LoaderErrorThrower.Dummy();
            return;
        }
        this.l = new d();
        this.r = new e();
        this.o = new Runnable() { // from class: com.google.android.exoplayer2.source.dash.-$$Lambda$DashMediaSource$qC7eVlpwaC1wXpfiFOgOmOS41NA
            @Override // java.lang.Runnable
            public final void run() {
                DashMediaSource.this.c();
            }
        };
        this.p = new Runnable() { // from class: com.google.android.exoplayer2.source.dash.-$$Lambda$DashMediaSource$puzrJA__DeRH0o_EPYaasfr2u1E
            @Override // java.lang.Runnable
            public final void run() {
                DashMediaSource.this.e();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        a(false);
    }

    public void replaceManifestUri(Uri uri) {
        synchronized (this.m) {
            this.y = uri;
            this.z = uri;
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.u = transferListener;
        this.f.prepare();
        if (this.b) {
            a(false);
            return;
        }
        this.s = this.c.createDataSource();
        this.t = new Loader(DEFAULT_MEDIA_ID);
        this.w = Util.createHandlerForCurrentLooper();
        c();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.r.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        int intValue = ((Integer) mediaPeriodId.periodUid).intValue() - this.H;
        MediaSourceEventListener.EventDispatcher createEventDispatcher = createEventDispatcher(mediaPeriodId, this.A.getPeriod(intValue).startMs);
        DashMediaPeriod dashMediaPeriod = new DashMediaPeriod(intValue + this.H, this.A, this.h, intValue, this.d, this.u, this.f, createDrmEventDispatcher(mediaPeriodId), this.g, createEventDispatcher, this.E, this.r, allocator, this.e, this.q);
        this.n.put(dashMediaPeriod.a, dashMediaPeriod);
        return dashMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.a();
        this.n.remove(dashMediaPeriod.a);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.B = false;
        this.s = null;
        Loader loader = this.t;
        if (loader != null) {
            loader.release();
            this.t = null;
        }
        this.C = 0L;
        this.D = 0L;
        this.A = this.b ? this.A : null;
        this.y = this.z;
        this.v = null;
        Handler handler = this.w;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.w = null;
        }
        this.E = C.TIME_UNSET;
        this.F = 0;
        this.G = C.TIME_UNSET;
        this.H = 0;
        this.n.clear();
        this.h.reset();
        this.f.release();
    }

    void a() {
        this.w.removeCallbacks(this.p);
        c();
    }

    void a(long j) {
        long j2 = this.G;
        if (j2 == C.TIME_UNSET || j2 < j) {
            this.G = j;
        }
    }

    void a(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
        boolean z;
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.g.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.j.loadCompleted(loadEventInfo, parsingLoadable.type);
        DashManifest result = parsingLoadable.getResult();
        DashManifest dashManifest = this.A;
        boolean z2 = false;
        int periodCount = dashManifest == null ? 0 : dashManifest.getPeriodCount();
        long j3 = result.getPeriod(0).startMs;
        int i = 0;
        while (i < periodCount && this.A.getPeriod(i).startMs < j3) {
            i++;
        }
        if (result.dynamic) {
            if (periodCount - i > result.getPeriodCount()) {
                Log.w(DEFAULT_MEDIA_ID, "Loaded out of sync manifest");
                z = true;
            } else if (this.G == C.TIME_UNSET || result.publishTimeMs * 1000 > this.G) {
                z = false;
            } else {
                long j4 = result.publishTimeMs;
                long j5 = this.G;
                StringBuilder sb = new StringBuilder(73);
                sb.append("Loaded stale dynamic manifest: ");
                sb.append(j4);
                sb.append(", ");
                sb.append(j5);
                Log.w(DEFAULT_MEDIA_ID, sb.toString());
                z = true;
            }
            if (z) {
                int i2 = this.F;
                this.F = i2 + 1;
                if (i2 < this.g.getMinimumLoadableRetryCount(parsingLoadable.type)) {
                    c(d());
                    return;
                } else {
                    this.v = new DashManifestStaleException();
                    return;
                }
            } else {
                this.F = 0;
            }
        }
        this.A = result;
        this.B &= this.A.dynamic;
        this.C = j - j2;
        this.D = j;
        synchronized (this.m) {
            if (parsingLoadable.dataSpec.uri == this.y) {
                z2 = true;
            }
            if (z2) {
                this.y = this.A.location != null ? this.A.location : parsingLoadable.getUri();
            }
        }
        if (periodCount != 0) {
            this.H += i;
            a(true);
        } else if (!this.A.dynamic) {
            a(true);
        } else if (this.A.utcTiming != null) {
            a(this.A.utcTiming);
        } else {
            b();
        }
    }

    Loader.LoadErrorAction a(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
        Loader.LoadErrorAction loadErrorAction;
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        long retryDelayMsFor = this.g.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(parsingLoadable.type), iOException, i));
        if (retryDelayMsFor == C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
        }
        boolean z = !loadErrorAction.isRetry();
        this.j.loadError(loadEventInfo, parsingLoadable.type, iOException, z);
        if (z) {
            this.g.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        }
        return loadErrorAction;
    }

    void b(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.g.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.j.loadCompleted(loadEventInfo, parsingLoadable.type);
        b(parsingLoadable.getResult().longValue() - j);
    }

    Loader.LoadErrorAction a(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException) {
        this.j.loadError(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded()), parsingLoadable.type, iOException, true);
        this.g.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        a(iOException);
        return Loader.DONT_RETRY;
    }

    void c(ParsingLoadable<?> parsingLoadable, long j, long j2) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.g.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.j.loadCanceled(loadEventInfo, parsingLoadable.type);
    }

    private void a(UtcTimingElement utcTimingElement) {
        String str = utcTimingElement.schemeIdUri;
        if (Util.areEqual(str, "urn:mpeg:dash:utc:direct:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:direct:2012")) {
            b(utcTimingElement);
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2012")) {
            a(utcTimingElement, new c());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2012")) {
            a(utcTimingElement, new g());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2012")) {
            b();
        } else {
            a(new IOException("Unsupported UTC timing scheme"));
        }
    }

    private void b(UtcTimingElement utcTimingElement) {
        try {
            b(Util.parseXsDateTime(utcTimingElement.value) - this.D);
        } catch (ParserException e2) {
            a(e2);
        }
    }

    private void a(UtcTimingElement utcTimingElement, ParsingLoadable.Parser<Long> parser) {
        a(new ParsingLoadable(this.s, Uri.parse(utcTimingElement.value), 5, parser), new f(), 1);
    }

    private void b() {
        SntpClient.initialize(this.t, new SntpClient.InitializationCallback() { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource.1
            @Override // com.google.android.exoplayer2.util.SntpClient.InitializationCallback
            public void onInitialized() {
                DashMediaSource.this.b(SntpClient.getElapsedRealtimeOffsetMs());
            }

            @Override // com.google.android.exoplayer2.util.SntpClient.InitializationCallback
            public void onInitializationFailed(IOException iOException) {
                DashMediaSource.this.a(iOException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(long j) {
        this.E = j;
        a(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(IOException iOException) {
        Log.e(DEFAULT_MEDIA_ID, "Failed to resolve time offset.", iOException);
        a(true);
    }

    private void a(boolean z) {
        long j;
        long j2;
        boolean z2 = false;
        for (int i = 0; i < this.n.size(); i++) {
            int keyAt = this.n.keyAt(i);
            if (keyAt >= this.H) {
                this.n.valueAt(i).a(this.A, keyAt - this.H);
            }
        }
        Period period = this.A.getPeriod(0);
        int periodCount = this.A.getPeriodCount() - 1;
        Period period2 = this.A.getPeriod(periodCount);
        long periodDurationUs = this.A.getPeriodDurationUs(periodCount);
        long msToUs = C.msToUs(Util.getNowUnixTimeMs(this.E));
        long a2 = a(period, this.A.getPeriodDurationUs(0), msToUs);
        long b2 = b(period2, periodDurationUs, msToUs);
        boolean z3 = this.A.dynamic && !a(period2);
        if (z3 && this.A.timeShiftBufferDepthMs != C.TIME_UNSET) {
            a2 = Math.max(a2, b2 - C.msToUs(this.A.timeShiftBufferDepthMs));
        }
        long j3 = b2 - a2;
        if (this.A.dynamic) {
            if (this.A.availabilityStartTimeMs != C.TIME_UNSET) {
                z2 = true;
            }
            Assertions.checkState(z2);
            long msToUs2 = (msToUs - C.msToUs(this.A.availabilityStartTimeMs)) - a2;
            a(msToUs2, j3);
            j2 = this.A.availabilityStartTimeMs + C.usToMs(a2);
            long msToUs3 = msToUs2 - C.msToUs(this.x.targetOffsetMs);
            long min = Math.min(5000000L, j3 / 2);
            j = msToUs3 < min ? min : msToUs3;
        } else {
            j2 = C.TIME_UNSET;
            j = 0;
        }
        long msToUs4 = a2 - C.msToUs(period.startMs);
        long j4 = this.A.availabilityStartTimeMs;
        long j5 = this.E;
        int i2 = this.H;
        DashManifest dashManifest = this.A;
        refreshSourceInfo(new a(j4, j2, j5, i2, msToUs4, j3, j, dashManifest, this.a, dashManifest.dynamic ? this.x : null));
        if (!this.b) {
            this.w.removeCallbacks(this.p);
            if (z3) {
                this.w.postDelayed(this.p, a(this.A, Util.getNowUnixTimeMs(this.E)));
            }
            if (this.B) {
                c();
            } else if (z && this.A.dynamic && this.A.minUpdatePeriodMs != C.TIME_UNSET) {
                long j6 = this.A.minUpdatePeriodMs;
                if (j6 == 0) {
                    j6 = 5000;
                }
                c(Math.max(0L, (this.C + j6) - SystemClock.elapsedRealtime()));
            }
        }
    }

    private void a(long j, long j2) {
        long j3;
        long j4;
        long j5;
        float f2;
        float f3;
        if (this.a.liveConfiguration.maxOffsetMs != C.TIME_UNSET) {
            j3 = this.a.liveConfiguration.maxOffsetMs;
        } else if (this.A.serviceDescription == null || this.A.serviceDescription.maxOffsetMs == C.TIME_UNSET) {
            j3 = C.usToMs(j);
        } else {
            j3 = this.A.serviceDescription.maxOffsetMs;
        }
        if (this.a.liveConfiguration.minOffsetMs != C.TIME_UNSET) {
            j4 = this.a.liveConfiguration.minOffsetMs;
        } else if (this.A.serviceDescription == null || this.A.serviceDescription.minOffsetMs == C.TIME_UNSET) {
            long usToMs = C.usToMs(j - j2);
            if (usToMs < 0 && j3 > 0) {
                usToMs = 0;
            }
            j4 = this.A.minBufferTimeMs != C.TIME_UNSET ? Math.min(usToMs + this.A.minBufferTimeMs, j3) : usToMs;
        } else {
            j4 = this.A.serviceDescription.minOffsetMs;
        }
        if (this.x.targetOffsetMs != C.TIME_UNSET) {
            j5 = this.x.targetOffsetMs;
        } else if (this.A.serviceDescription != null && this.A.serviceDescription.targetOffsetMs != C.TIME_UNSET) {
            j5 = this.A.serviceDescription.targetOffsetMs;
        } else if (this.A.suggestedPresentationDelayMs != C.TIME_UNSET) {
            j5 = this.A.suggestedPresentationDelayMs;
        } else {
            j5 = this.i;
        }
        if (j5 < j4) {
            j5 = j4;
        }
        long constrainValue = j5 > j3 ? Util.constrainValue(C.usToMs(j - Math.min(5000000L, j2 / 2)), j4, j3) : j5;
        if (this.a.liveConfiguration.minPlaybackSpeed != -3.4028235E38f) {
            f2 = this.a.liveConfiguration.minPlaybackSpeed;
        } else {
            f2 = this.A.serviceDescription != null ? this.A.serviceDescription.minPlaybackSpeed : -3.4028235E38f;
        }
        if (this.a.liveConfiguration.maxPlaybackSpeed != -3.4028235E38f) {
            f3 = this.a.liveConfiguration.maxPlaybackSpeed;
        } else {
            f3 = this.A.serviceDescription != null ? this.A.serviceDescription.maxPlaybackSpeed : -3.4028235E38f;
        }
        this.x = new MediaItem.LiveConfiguration(constrainValue, j4, j3, f2, f3);
    }

    private void c(long j) {
        this.w.postDelayed(this.o, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        Uri uri;
        this.w.removeCallbacks(this.o);
        if (!this.t.hasFatalError()) {
            if (this.t.isLoading()) {
                this.B = true;
                return;
            }
            synchronized (this.m) {
                uri = this.y;
            }
            this.B = false;
            a(new ParsingLoadable(this.s, uri, 4, this.k), this.l, this.g.getMinimumLoadableRetryCount(4));
        }
    }

    private long d() {
        return Math.min((this.F - 1) * 1000, 5000);
    }

    private <T> void a(ParsingLoadable<T> parsingLoadable, Loader.Callback<ParsingLoadable<T>> callback, int i) {
        this.j.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.t.startLoading(parsingLoadable, callback, i)), parsingLoadable.type);
    }

    private static long a(DashManifest dashManifest, long j) {
        DashSegmentIndex index;
        int periodCount = dashManifest.getPeriodCount() - 1;
        Period period = dashManifest.getPeriod(periodCount);
        long msToUs = C.msToUs(period.startMs);
        long periodDurationUs = dashManifest.getPeriodDurationUs(periodCount);
        long msToUs2 = C.msToUs(j);
        long msToUs3 = C.msToUs(dashManifest.availabilityStartTimeMs);
        long msToUs4 = C.msToUs(5000L);
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            List<Representation> list = period.adaptationSets.get(i).representations;
            if (!list.isEmpty() && (index = list.get(0).getIndex()) != null) {
                long nextSegmentAvailableTimeUs = ((msToUs3 + msToUs) + index.getNextSegmentAvailableTimeUs(periodDurationUs, msToUs2)) - msToUs2;
                if (nextSegmentAvailableTimeUs < msToUs4 - 100000 || (nextSegmentAvailableTimeUs > msToUs4 && nextSegmentAvailableTimeUs < msToUs4 + 100000)) {
                    msToUs4 = nextSegmentAvailableTimeUs;
                }
            }
        }
        return LongMath.divide(msToUs4, 1000L, RoundingMode.CEILING);
    }

    private static long a(Period period, long j, long j2) {
        long msToUs = C.msToUs(period.startMs);
        boolean b2 = b(period);
        long j3 = msToUs;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            if ((!b2 || adaptationSet.type != 3) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null || index.getAvailableSegmentCount(j, j2) == 0) {
                    return msToUs;
                }
                j3 = Math.max(j3, index.getTimeUs(index.getFirstAvailableSegmentNum(j, j2)) + msToUs);
            }
        }
        return j3;
    }

    private static long b(Period period, long j, long j2) {
        long msToUs = C.msToUs(period.startMs);
        boolean b2 = b(period);
        long j3 = Long.MAX_VALUE;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            if ((!b2 || adaptationSet.type != 3) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null) {
                    return msToUs + j;
                }
                long availableSegmentCount = index.getAvailableSegmentCount(j, j2);
                if (availableSegmentCount == 0) {
                    return msToUs;
                }
                long firstAvailableSegmentNum = (index.getFirstAvailableSegmentNum(j, j2) + availableSegmentCount) - 1;
                j3 = Math.min(j3, index.getDurationUs(firstAvailableSegmentNum, j) + index.getTimeUs(firstAvailableSegmentNum) + msToUs);
            }
        }
        return j3;
    }

    private static boolean a(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            DashSegmentIndex index = period.adaptationSets.get(i).representations.get(0).getIndex();
            if (index == null || index.isExplicit()) {
                return true;
            }
        }
        return false;
    }

    private static boolean b(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            int i2 = period.adaptationSets.get(i).type;
            if (i2 == 1 || i2 == 2) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends Timeline {
        private final long a;
        private final long b;
        private final long c;
        private final int d;
        private final long e;
        private final long f;
        private final long g;
        private final DashManifest h;
        private final MediaItem i;
        @Nullable
        private final MediaItem.LiveConfiguration j;

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 1;
        }

        public a(long j, long j2, long j3, int i, long j4, long j5, long j6, DashManifest dashManifest, MediaItem mediaItem, @Nullable MediaItem.LiveConfiguration liveConfiguration) {
            Assertions.checkState(dashManifest.dynamic != (liveConfiguration != null) ? false : true);
            this.a = j;
            this.b = j2;
            this.c = j3;
            this.d = i;
            this.e = j4;
            this.f = j5;
            this.g = j6;
            this.h = dashManifest;
            this.i = mediaItem;
            this.j = liveConfiguration;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.h.getPeriodCount();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            Integer num = null;
            String str = z ? this.h.getPeriod(i).id : null;
            if (z) {
                num = Integer.valueOf(this.d + i);
            }
            return period.set(str, num, 0, this.h.getPeriodDurationUs(i), C.msToUs(this.h.getPeriod(i).startMs - this.h.getPeriod(0).startMs) - this.e);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            Assertions.checkIndex(i, 0, 1);
            long a = a(j);
            Object obj = Timeline.Window.SINGLE_WINDOW_UID;
            MediaItem mediaItem = this.i;
            DashManifest dashManifest = this.h;
            return window.set(obj, mediaItem, dashManifest, this.a, this.b, this.c, true, a(dashManifest), this.j, a, this.f, 0, getPeriodCount() - 1, this.e);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            if (!(obj instanceof Integer)) {
                return -1;
            }
            int intValue = ((Integer) obj).intValue() - this.d;
            if (intValue < 0 || intValue >= getPeriodCount()) {
                return -1;
            }
            return intValue;
        }

        private long a(long j) {
            DashSegmentIndex index;
            long j2 = this.g;
            if (!a(this.h)) {
                return j2;
            }
            if (j > 0) {
                j2 += j;
                if (j2 > this.f) {
                    return C.TIME_UNSET;
                }
            }
            long j3 = this.e + j2;
            long periodDurationUs = this.h.getPeriodDurationUs(0);
            int i = 0;
            while (i < this.h.getPeriodCount() - 1 && j3 >= periodDurationUs) {
                j3 -= periodDurationUs;
                i++;
                periodDurationUs = this.h.getPeriodDurationUs(i);
            }
            Period period = this.h.getPeriod(i);
            int adaptationSetIndex = period.getAdaptationSetIndex(2);
            return (adaptationSetIndex == -1 || (index = period.adaptationSets.get(adaptationSetIndex).representations.get(0).getIndex()) == null || index.getSegmentCount(periodDurationUs) == 0) ? j2 : (j2 + index.getTimeUs(index.getSegmentNum(j3, periodDurationUs))) - j3;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            return Integer.valueOf(this.d + i);
        }

        private static boolean a(DashManifest dashManifest) {
            return dashManifest.dynamic && dashManifest.minUpdatePeriodMs != C.TIME_UNSET && dashManifest.durationMs == C.TIME_UNSET;
        }
    }

    /* loaded from: classes2.dex */
    private final class b implements PlayerEmsgHandler.PlayerEmsgCallback {
        private b() {
        }

        @Override // com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestRefreshRequested() {
            DashMediaSource.this.a();
        }

        @Override // com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestPublishTimeExpired(long j) {
            DashMediaSource.this.a(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class d implements Loader.Callback<ParsingLoadable<DashManifest>> {
        private d() {
        }

        /* renamed from: a */
        public void onLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
            DashMediaSource.this.a(parsingLoadable, j, j2);
        }

        /* renamed from: a */
        public void onLoadCanceled(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.c(parsingLoadable, j, j2);
        }

        /* renamed from: a */
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.a(parsingLoadable, j, j2, iOException, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class f implements Loader.Callback<ParsingLoadable<Long>> {
        private f() {
        }

        /* renamed from: a */
        public void onLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
            DashMediaSource.this.b(parsingLoadable, j, j2);
        }

        /* renamed from: a */
        public void onLoadCanceled(ParsingLoadable<Long> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.c(parsingLoadable, j, j2);
        }

        /* renamed from: a */
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.a(parsingLoadable, j, j2, iOException);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class g implements ParsingLoadable.Parser<Long> {
        private g() {
        }

        /* renamed from: a */
        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class c implements ParsingLoadable.Parser<Long> {
        private static final Pattern a = Pattern.compile("(.+?)(Z|((\\+|-|−)(\\d\\d)(:?(\\d\\d))?))");

        c() {
        }

        /* renamed from: a */
        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            String readLine = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8)).readLine();
            try {
                Matcher matcher = a.matcher(readLine);
                if (!matcher.matches()) {
                    String valueOf = String.valueOf(readLine);
                    throw ParserException.createForMalformedManifest(valueOf.length() != 0 ? "Couldn't parse timestamp: ".concat(valueOf) : new String("Couldn't parse timestamp: "), null);
                }
                String group = matcher.group(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                long time = simpleDateFormat.parse(group).getTime();
                if (!"Z".equals(matcher.group(2))) {
                    long j = Marker.ANY_NON_NULL_MARKER.equals(matcher.group(4)) ? 1L : -1L;
                    long parseLong = Long.parseLong(matcher.group(5));
                    String group2 = matcher.group(7);
                    time -= j * ((((parseLong * 60) + (TextUtils.isEmpty(group2) ? 0L : Long.parseLong(group2))) * 60) * 1000);
                }
                return Long.valueOf(time);
            } catch (ParseException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }
    }

    /* loaded from: classes2.dex */
    final class e implements LoaderErrorThrower {
        e() {
        }

        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError() throws IOException {
            DashMediaSource.this.t.maybeThrowError();
            a();
        }

        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError(int i) throws IOException {
            DashMediaSource.this.t.maybeThrowError(i);
            a();
        }

        private void a() throws IOException {
            if (DashMediaSource.this.v != null) {
                throw DashMediaSource.this.v;
            }
        }
    }
}
