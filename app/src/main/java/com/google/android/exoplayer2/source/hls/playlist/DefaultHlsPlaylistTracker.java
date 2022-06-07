package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public final class DefaultHlsPlaylistTracker implements HlsPlaylistTracker, Loader.Callback<ParsingLoadable<HlsPlaylist>> {
    public static final double DEFAULT_PLAYLIST_STUCK_TARGET_DURATION_COEFFICIENT = 3.5d;
    public static final HlsPlaylistTracker.Factory FACTORY = $$Lambda$lKTLOVxne0MoBOOliKH0gO2KDMM.INSTANCE;
    private final HlsDataSourceFactory a;
    private final HlsPlaylistParserFactory b;
    private final LoadErrorHandlingPolicy c;
    private final HashMap<Uri, b> d;
    private final CopyOnWriteArrayList<HlsPlaylistTracker.PlaylistEventListener> e;
    private final double f;
    @Nullable
    private MediaSourceEventListener.EventDispatcher g;
    @Nullable
    private Loader h;
    @Nullable
    private Handler i;
    @Nullable
    private HlsPlaylistTracker.PrimaryPlaylistListener j;
    @Nullable
    private HlsMasterPlaylist k;
    @Nullable
    private Uri l;
    @Nullable
    private HlsMediaPlaylist m;
    private boolean n;
    private long o;

    public DefaultHlsPlaylistTracker(HlsDataSourceFactory hlsDataSourceFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistParserFactory hlsPlaylistParserFactory) {
        this(hlsDataSourceFactory, loadErrorHandlingPolicy, hlsPlaylistParserFactory, 3.5d);
    }

    public DefaultHlsPlaylistTracker(HlsDataSourceFactory hlsDataSourceFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistParserFactory hlsPlaylistParserFactory, double d) {
        this.a = hlsDataSourceFactory;
        this.b = hlsPlaylistParserFactory;
        this.c = loadErrorHandlingPolicy;
        this.f = d;
        this.e = new CopyOnWriteArrayList<>();
        this.d = new HashMap<>();
        this.o = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void start(Uri uri, MediaSourceEventListener.EventDispatcher eventDispatcher, HlsPlaylistTracker.PrimaryPlaylistListener primaryPlaylistListener) {
        this.i = Util.createHandlerForCurrentLooper();
        this.g = eventDispatcher;
        this.j = primaryPlaylistListener;
        ParsingLoadable parsingLoadable = new ParsingLoadable(this.a.createDataSource(4), uri, 4, this.b.createPlaylistParser());
        Assertions.checkState(this.h == null);
        this.h = new Loader("DefaultHlsPlaylistTracker:MasterPlaylist");
        eventDispatcher.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.h.startLoading(parsingLoadable, this, this.c.getMinimumLoadableRetryCount(parsingLoadable.type))), parsingLoadable.type);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void stop() {
        this.l = null;
        this.m = null;
        this.k = null;
        this.o = C.TIME_UNSET;
        this.h.release();
        this.h = null;
        for (b bVar : this.d.values()) {
            bVar.e();
        }
        this.i.removeCallbacksAndMessages(null);
        this.i = null;
        this.d.clear();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void addListener(HlsPlaylistTracker.PlaylistEventListener playlistEventListener) {
        Assertions.checkNotNull(playlistEventListener);
        this.e.add(playlistEventListener);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void removeListener(HlsPlaylistTracker.PlaylistEventListener playlistEventListener) {
        this.e.remove(playlistEventListener);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    @Nullable
    public HlsMasterPlaylist getMasterPlaylist() {
        return this.k;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    @Nullable
    public HlsMediaPlaylist getPlaylistSnapshot(Uri uri, boolean z) {
        HlsMediaPlaylist a2 = this.d.get(uri).a();
        if (a2 != null && z) {
            a(uri);
        }
        return a2;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public long getInitialStartTimeUs() {
        return this.o;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public boolean isSnapshotValid(Uri uri) {
        return this.d.get(uri).b();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void maybeThrowPrimaryPlaylistRefreshError() throws IOException {
        Loader loader = this.h;
        if (loader != null) {
            loader.maybeThrowError();
        }
        Uri uri = this.l;
        if (uri != null) {
            maybeThrowPlaylistRefreshError(uri);
        }
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void maybeThrowPlaylistRefreshError(Uri uri) throws IOException {
        this.d.get(uri).d();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public void refreshPlaylist(Uri uri) {
        this.d.get(uri).c();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public boolean isLive() {
        return this.n;
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker
    public boolean excludeMediaPlaylist(Uri uri, long j) {
        b bVar = this.d.get(uri);
        if (bVar != null) {
            return !bVar.a(j);
        }
        return false;
    }

    public void onLoadCompleted(ParsingLoadable<HlsPlaylist> parsingLoadable, long j, long j2) {
        HlsMasterPlaylist hlsMasterPlaylist;
        HlsPlaylist result = parsingLoadable.getResult();
        boolean z = result instanceof HlsMediaPlaylist;
        if (z) {
            hlsMasterPlaylist = HlsMasterPlaylist.createSingleVariantMasterPlaylist(result.baseUri);
        } else {
            hlsMasterPlaylist = (HlsMasterPlaylist) result;
        }
        this.k = hlsMasterPlaylist;
        this.l = hlsMasterPlaylist.variants.get(0).url;
        this.e.add(new a());
        a(hlsMasterPlaylist.mediaPlaylistUrls);
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        b bVar = this.d.get(this.l);
        if (z) {
            bVar.a((HlsMediaPlaylist) result, loadEventInfo);
        } else {
            bVar.c();
        }
        this.c.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.g.loadCompleted(loadEventInfo, 4);
    }

    public void onLoadCanceled(ParsingLoadable<HlsPlaylist> parsingLoadable, long j, long j2, boolean z) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.c.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.g.loadCanceled(loadEventInfo, 4);
    }

    public Loader.LoadErrorAction onLoadError(ParsingLoadable<HlsPlaylist> parsingLoadable, long j, long j2, IOException iOException, int i) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        long retryDelayMsFor = this.c.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(parsingLoadable.type), iOException, i));
        boolean z = retryDelayMsFor == C.TIME_UNSET;
        this.g.loadError(loadEventInfo, parsingLoadable.type, iOException, z);
        if (z) {
            this.c.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        }
        if (z) {
            return Loader.DONT_RETRY_FATAL;
        }
        return Loader.createRetryAction(false, retryDelayMsFor);
    }

    public boolean a() {
        List<HlsMasterPlaylist.Variant> list = this.k.variants;
        int size = list.size();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (int i = 0; i < size; i++) {
            b bVar = (b) Assertions.checkNotNull(this.d.get(list.get(i).url));
            if (elapsedRealtime > bVar.i) {
                this.l = bVar.b;
                bVar.a(b(this.l));
                return true;
            }
        }
        return false;
    }

    private void a(Uri uri) {
        if (!uri.equals(this.l) && c(uri)) {
            HlsMediaPlaylist hlsMediaPlaylist = this.m;
            if (hlsMediaPlaylist == null || !hlsMediaPlaylist.hasEndTag) {
                this.l = uri;
                b bVar = this.d.get(this.l);
                HlsMediaPlaylist hlsMediaPlaylist2 = bVar.e;
                if (hlsMediaPlaylist2 == null || !hlsMediaPlaylist2.hasEndTag) {
                    bVar.a(b(uri));
                    return;
                }
                this.m = hlsMediaPlaylist2;
                this.j.onPrimaryPlaylistRefreshed(hlsMediaPlaylist2);
            }
        }
    }

    private Uri b(Uri uri) {
        HlsMediaPlaylist.RenditionReport renditionReport;
        HlsMediaPlaylist hlsMediaPlaylist = this.m;
        if (hlsMediaPlaylist == null || !hlsMediaPlaylist.serverControl.canBlockReload || (renditionReport = this.m.renditionReports.get(uri)) == null) {
            return uri;
        }
        Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.appendQueryParameter("_HLS_msn", String.valueOf(renditionReport.lastMediaSequence));
        if (renditionReport.lastPartIndex != -1) {
            buildUpon.appendQueryParameter("_HLS_part", String.valueOf(renditionReport.lastPartIndex));
        }
        return buildUpon.build();
    }

    private boolean c(Uri uri) {
        List<HlsMasterPlaylist.Variant> list = this.k.variants;
        for (int i = 0; i < list.size(); i++) {
            if (uri.equals(list.get(i).url)) {
                return true;
            }
        }
        return false;
    }

    private void a(List<Uri> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Uri uri = list.get(i);
            this.d.put(uri, new b(uri));
        }
    }

    public void a(Uri uri, HlsMediaPlaylist hlsMediaPlaylist) {
        if (uri.equals(this.l)) {
            if (this.m == null) {
                this.n = !hlsMediaPlaylist.hasEndTag;
                this.o = hlsMediaPlaylist.startTimeUs;
            }
            this.m = hlsMediaPlaylist;
            this.j.onPrimaryPlaylistRefreshed(hlsMediaPlaylist);
        }
        Iterator<HlsPlaylistTracker.PlaylistEventListener> it = this.e.iterator();
        while (it.hasNext()) {
            it.next().onPlaylistChanged();
        }
    }

    public boolean a(Uri uri, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean z) {
        Iterator<HlsPlaylistTracker.PlaylistEventListener> it = this.e.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            z2 |= !it.next().onPlaylistError(uri, loadErrorInfo, z);
        }
        return z2;
    }

    public HlsMediaPlaylist a(@Nullable HlsMediaPlaylist hlsMediaPlaylist, HlsMediaPlaylist hlsMediaPlaylist2) {
        if (!hlsMediaPlaylist2.isNewerThan(hlsMediaPlaylist)) {
            return hlsMediaPlaylist2.hasEndTag ? hlsMediaPlaylist.copyWithEndTag() : hlsMediaPlaylist;
        }
        return hlsMediaPlaylist2.copyWith(b(hlsMediaPlaylist, hlsMediaPlaylist2), c(hlsMediaPlaylist, hlsMediaPlaylist2));
    }

    private long b(@Nullable HlsMediaPlaylist hlsMediaPlaylist, HlsMediaPlaylist hlsMediaPlaylist2) {
        if (hlsMediaPlaylist2.hasProgramDateTime) {
            return hlsMediaPlaylist2.startTimeUs;
        }
        HlsMediaPlaylist hlsMediaPlaylist3 = this.m;
        long j = hlsMediaPlaylist3 != null ? hlsMediaPlaylist3.startTimeUs : 0L;
        if (hlsMediaPlaylist == null) {
            return j;
        }
        int size = hlsMediaPlaylist.segments.size();
        HlsMediaPlaylist.Segment d = d(hlsMediaPlaylist, hlsMediaPlaylist2);
        if (d != null) {
            return hlsMediaPlaylist.startTimeUs + d.relativeStartTimeUs;
        }
        return ((long) size) == hlsMediaPlaylist2.mediaSequence - hlsMediaPlaylist.mediaSequence ? hlsMediaPlaylist.getEndTimeUs() : j;
    }

    private int c(@Nullable HlsMediaPlaylist hlsMediaPlaylist, HlsMediaPlaylist hlsMediaPlaylist2) {
        HlsMediaPlaylist.Segment d;
        if (hlsMediaPlaylist2.hasDiscontinuitySequence) {
            return hlsMediaPlaylist2.discontinuitySequence;
        }
        HlsMediaPlaylist hlsMediaPlaylist3 = this.m;
        int i = hlsMediaPlaylist3 != null ? hlsMediaPlaylist3.discontinuitySequence : 0;
        return (hlsMediaPlaylist == null || (d = d(hlsMediaPlaylist, hlsMediaPlaylist2)) == null) ? i : (hlsMediaPlaylist.discontinuitySequence + d.relativeDiscontinuitySequence) - hlsMediaPlaylist2.segments.get(0).relativeDiscontinuitySequence;
    }

    private static HlsMediaPlaylist.Segment d(HlsMediaPlaylist hlsMediaPlaylist, HlsMediaPlaylist hlsMediaPlaylist2) {
        int i = (int) (hlsMediaPlaylist2.mediaSequence - hlsMediaPlaylist.mediaSequence);
        List<HlsMediaPlaylist.Segment> list = hlsMediaPlaylist.segments;
        if (i < list.size()) {
            return list.get(i);
        }
        return null;
    }

    /* loaded from: classes2.dex */
    public final class b implements Loader.Callback<ParsingLoadable<HlsPlaylist>> {
        private final Uri b;
        private final Loader c = new Loader("DefaultHlsPlaylistTracker:MediaPlaylist");
        private final DataSource d;
        @Nullable
        private HlsMediaPlaylist e;
        private long f;
        private long g;
        private long h;
        private long i;
        private boolean j;
        @Nullable
        private IOException k;

        public b(Uri uri) {
            DefaultHlsPlaylistTracker.this = r2;
            this.b = uri;
            this.d = r2.a.createDataSource(4);
        }

        @Nullable
        public HlsMediaPlaylist a() {
            return this.e;
        }

        public boolean b() {
            if (this.e == null) {
                return false;
            }
            return this.e.hasEndTag || this.e.playlistType == 2 || this.e.playlistType == 1 || this.f + Math.max(30000L, C.usToMs(this.e.durationUs)) > SystemClock.elapsedRealtime();
        }

        public void c() {
            a(this.b);
        }

        public void d() throws IOException {
            this.c.maybeThrowError();
            IOException iOException = this.k;
            if (iOException != null) {
                throw iOException;
            }
        }

        public void e() {
            this.c.release();
        }

        /* renamed from: a */
        public void onLoadCompleted(ParsingLoadable<HlsPlaylist> parsingLoadable, long j, long j2) {
            HlsPlaylist result = parsingLoadable.getResult();
            LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
            if (result instanceof HlsMediaPlaylist) {
                a((HlsMediaPlaylist) result, loadEventInfo);
                DefaultHlsPlaylistTracker.this.g.loadCompleted(loadEventInfo, 4);
            } else {
                this.k = ParserException.createForMalformedManifest("Loaded playlist has unexpected type.", null);
                DefaultHlsPlaylistTracker.this.g.loadError(loadEventInfo, 4, this.k, true);
            }
            DefaultHlsPlaylistTracker.this.c.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        }

        /* renamed from: a */
        public void onLoadCanceled(ParsingLoadable<HlsPlaylist> parsingLoadable, long j, long j2, boolean z) {
            LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
            DefaultHlsPlaylistTracker.this.c.onLoadTaskConcluded(parsingLoadable.loadTaskId);
            DefaultHlsPlaylistTracker.this.g.loadCanceled(loadEventInfo, 4);
        }

        /* renamed from: a */
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<HlsPlaylist> parsingLoadable, long j, long j2, IOException iOException, int i) {
            Loader.LoadErrorAction loadErrorAction;
            LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
            boolean z = iOException instanceof HlsPlaylistParser.DeltaUpdateException;
            if ((parsingLoadable.getUri().getQueryParameter("_HLS_msn") != null) || z) {
                int i2 = Integer.MAX_VALUE;
                if (iOException instanceof HttpDataSource.InvalidResponseCodeException) {
                    i2 = ((HttpDataSource.InvalidResponseCodeException) iOException).responseCode;
                }
                if (z || i2 == 400 || i2 == 503) {
                    this.h = SystemClock.elapsedRealtime();
                    c();
                    ((MediaSourceEventListener.EventDispatcher) Util.castNonNull(DefaultHlsPlaylistTracker.this.g)).loadError(loadEventInfo, parsingLoadable.type, iOException, true);
                    return Loader.DONT_RETRY;
                }
            }
            LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo = new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(parsingLoadable.type), iOException, i);
            if (DefaultHlsPlaylistTracker.this.a(this.b, loadErrorInfo, false)) {
                long retryDelayMsFor = DefaultHlsPlaylistTracker.this.c.getRetryDelayMsFor(loadErrorInfo);
                if (retryDelayMsFor != C.TIME_UNSET) {
                    loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
                } else {
                    loadErrorAction = Loader.DONT_RETRY_FATAL;
                }
            } else {
                loadErrorAction = Loader.DONT_RETRY;
            }
            boolean isRetry = true ^ loadErrorAction.isRetry();
            DefaultHlsPlaylistTracker.this.g.loadError(loadEventInfo, parsingLoadable.type, iOException, isRetry);
            if (isRetry) {
                DefaultHlsPlaylistTracker.this.c.onLoadTaskConcluded(parsingLoadable.loadTaskId);
            }
            return loadErrorAction;
        }

        public void a(final Uri uri) {
            this.i = 0L;
            if (!this.j && !this.c.isLoading() && !this.c.hasFatalError()) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime < this.h) {
                    this.j = true;
                    DefaultHlsPlaylistTracker.this.i.postDelayed(new Runnable() { // from class: com.google.android.exoplayer2.source.hls.playlist.-$$Lambda$DefaultHlsPlaylistTracker$b$fhmrg_kRU7HRFEuZWgnfcggR1BE
                        @Override // java.lang.Runnable
                        public final void run() {
                            DefaultHlsPlaylistTracker.b.this.c(uri);
                        }
                    }, this.h - elapsedRealtime);
                    return;
                }
                b(uri);
            }
        }

        public /* synthetic */ void c(Uri uri) {
            this.j = false;
            b(uri);
        }

        private void b(Uri uri) {
            ParsingLoadable parsingLoadable = new ParsingLoadable(this.d, uri, 4, DefaultHlsPlaylistTracker.this.b.createPlaylistParser(DefaultHlsPlaylistTracker.this.k, this.e));
            DefaultHlsPlaylistTracker.this.g.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.c.startLoading(parsingLoadable, this, DefaultHlsPlaylistTracker.this.c.getMinimumLoadableRetryCount(parsingLoadable.type))), parsingLoadable.type);
        }

        public void a(HlsMediaPlaylist hlsMediaPlaylist, LoadEventInfo loadEventInfo) {
            boolean z;
            HlsMediaPlaylist hlsMediaPlaylist2 = this.e;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.f = elapsedRealtime;
            this.e = DefaultHlsPlaylistTracker.this.a(hlsMediaPlaylist2, hlsMediaPlaylist);
            HlsMediaPlaylist hlsMediaPlaylist3 = this.e;
            boolean z2 = false;
            IOException iOException = null;
            if (hlsMediaPlaylist3 != hlsMediaPlaylist2) {
                this.k = null;
                this.g = elapsedRealtime;
                DefaultHlsPlaylistTracker.this.a(this.b, hlsMediaPlaylist3);
            } else if (!hlsMediaPlaylist3.hasEndTag) {
                if (hlsMediaPlaylist.mediaSequence + hlsMediaPlaylist.segments.size() < this.e.mediaSequence) {
                    iOException = new HlsPlaylistTracker.PlaylistResetException(this.b);
                    z = true;
                } else if (elapsedRealtime - this.g > C.usToMs(this.e.targetDurationUs) * DefaultHlsPlaylistTracker.this.f) {
                    iOException = new HlsPlaylistTracker.PlaylistStuckException(this.b);
                    z = false;
                } else {
                    z = false;
                }
                if (iOException != null) {
                    this.k = iOException;
                    DefaultHlsPlaylistTracker.this.a(this.b, new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(4), iOException, 1), z);
                }
            }
            long j = 0;
            if (!this.e.serverControl.canBlockReload) {
                HlsMediaPlaylist hlsMediaPlaylist4 = this.e;
                if (hlsMediaPlaylist4 != hlsMediaPlaylist2) {
                    j = hlsMediaPlaylist4.targetDurationUs;
                } else {
                    j = hlsMediaPlaylist4.targetDurationUs / 2;
                }
            }
            this.h = elapsedRealtime + C.usToMs(j);
            if (this.e.partTargetDurationUs != C.TIME_UNSET || this.b.equals(DefaultHlsPlaylistTracker.this.l)) {
                z2 = true;
            }
            if (z2 && !this.e.hasEndTag) {
                a(f());
            }
        }

        private Uri f() {
            HlsMediaPlaylist hlsMediaPlaylist = this.e;
            if (hlsMediaPlaylist == null || (hlsMediaPlaylist.serverControl.skipUntilUs == C.TIME_UNSET && !this.e.serverControl.canBlockReload)) {
                return this.b;
            }
            Uri.Builder buildUpon = this.b.buildUpon();
            if (this.e.serverControl.canBlockReload) {
                buildUpon.appendQueryParameter("_HLS_msn", String.valueOf(this.e.mediaSequence + this.e.segments.size()));
                if (this.e.partTargetDurationUs != C.TIME_UNSET) {
                    List<HlsMediaPlaylist.Part> list = this.e.trailingParts;
                    int size = list.size();
                    if (!list.isEmpty() && ((HlsMediaPlaylist.Part) Iterables.getLast(list)).isPreload) {
                        size--;
                    }
                    buildUpon.appendQueryParameter("_HLS_part", String.valueOf(size));
                }
            }
            if (this.e.serverControl.skipUntilUs != C.TIME_UNSET) {
                buildUpon.appendQueryParameter("_HLS_skip", this.e.serverControl.canSkipDateRanges ? "v2" : "YES");
            }
            return buildUpon.build();
        }

        public boolean a(long j) {
            this.i = SystemClock.elapsedRealtime() + j;
            return this.b.equals(DefaultHlsPlaylistTracker.this.l) && !DefaultHlsPlaylistTracker.this.a();
        }
    }

    /* loaded from: classes2.dex */
    public class a implements HlsPlaylistTracker.PlaylistEventListener {
        private a() {
            DefaultHlsPlaylistTracker.this = r1;
        }

        @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
        public void onPlaylistChanged() {
            DefaultHlsPlaylistTracker.this.e.remove(this);
        }

        @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
        public boolean onPlaylistError(Uri uri, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean z) {
            b bVar;
            if (DefaultHlsPlaylistTracker.this.m == null) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                List<HlsMasterPlaylist.Variant> list = ((HlsMasterPlaylist) Util.castNonNull(DefaultHlsPlaylistTracker.this.k)).variants;
                int i = 0;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    b bVar2 = (b) DefaultHlsPlaylistTracker.this.d.get(list.get(i2).url);
                    if (bVar2 != null && elapsedRealtime < bVar2.i) {
                        i++;
                    }
                }
                LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = DefaultHlsPlaylistTracker.this.c.getFallbackSelectionFor(new LoadErrorHandlingPolicy.FallbackOptions(1, 0, DefaultHlsPlaylistTracker.this.k.variants.size(), i), loadErrorInfo);
                if (!(fallbackSelectionFor == null || fallbackSelectionFor.type != 2 || (bVar = (b) DefaultHlsPlaylistTracker.this.d.get(uri)) == null)) {
                    bVar.a(fallbackSelectionFor.exclusionDurationMs);
                }
            }
            return false;
        }
    }
}
