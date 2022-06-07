package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.IcyDataSource;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ProgressiveMediaPeriod.java */
/* loaded from: classes2.dex */
public final class b implements ExtractorOutput, MediaPeriod, SampleQueue.UpstreamFormatChangedListener, Loader.Callback<a>, Loader.ReleaseCallback {
    private static final Map<String, String> a = m();
    private static final Format b = new Format.Builder().setId("icy").setSampleMimeType(MimeTypes.APPLICATION_ICY).build();
    private SeekMap A;
    private boolean C;
    private boolean E;
    private boolean F;
    private int G;
    private long I;
    private boolean K;
    private int L;
    private boolean M;
    private boolean N;
    private final Uri c;
    private final DataSource d;
    private final DrmSessionManager e;
    private final LoadErrorHandlingPolicy f;
    private final MediaSourceEventListener.EventDispatcher g;
    private final DrmSessionEventListener.EventDispatcher h;
    private final AbstractC0067b i;
    private final Allocator j;
    @Nullable
    private final String k;
    private final long l;
    private final ProgressiveMediaExtractor n;
    @Nullable
    private MediaPeriod.Callback s;
    @Nullable
    private IcyHeaders t;
    private boolean w;
    private boolean x;
    private boolean y;
    private e z;
    private final Loader m = new Loader("ProgressiveMediaPeriod");
    private final ConditionVariable o = new ConditionVariable();
    private final Runnable p = new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$b$vsMDS5waXDLSfsibBBoTyiiZf0U
        @Override // java.lang.Runnable
        public final void run() {
            b.this.g();
        }
    };
    private final Runnable q = new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$b$g8EYAt29i5VXFCrR2PU_A4mvPyM
        @Override // java.lang.Runnable
        public final void run() {
            b.this.n();
        }
    };
    private final Handler r = Util.createHandlerForCurrentLooper();
    private d[] v = new d[0];
    private SampleQueue[] u = new SampleQueue[0];
    private long J = C.TIME_UNSET;
    private long H = -1;
    private long B = C.TIME_UNSET;
    private int D = 1;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ProgressiveMediaPeriod.java */
    /* renamed from: com.google.android.exoplayer2.source.b$b  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public interface AbstractC0067b {
        void onSourceInfoRefreshed(long j, boolean z, boolean z2);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
    }

    public b(Uri uri, DataSource dataSource, ProgressiveMediaExtractor progressiveMediaExtractor, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, AbstractC0067b bVar, Allocator allocator, @Nullable String str, int i) {
        this.c = uri;
        this.d = dataSource;
        this.e = drmSessionManager;
        this.h = eventDispatcher;
        this.f = loadErrorHandlingPolicy;
        this.g = eventDispatcher2;
        this.i = bVar;
        this.j = allocator;
        this.k = str;
        this.l = i;
        this.n = progressiveMediaExtractor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void n() {
        if (!this.N) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.s)).onContinueLoadingRequested(this);
        }
    }

    public void a() {
        if (this.x) {
            for (SampleQueue sampleQueue : this.u) {
                sampleQueue.preRelease();
            }
        }
        this.m.release(this);
        this.r.removeCallbacksAndMessages(null);
        this.s = null;
        this.N = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.ReleaseCallback
    public void onLoaderReleased() {
        for (SampleQueue sampleQueue : this.u) {
            sampleQueue.release();
        }
        this.n.release();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.s = callback;
        this.o.open();
        h();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        b();
        if (this.M && !this.x) {
            throw ParserException.createForMalformedContainer("Loading finished before preparation is complete.", null);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        l();
        return this.z.a;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        l();
        TrackGroupArray trackGroupArray = this.z.a;
        boolean[] zArr3 = this.z.c;
        int i = this.G;
        int i2 = 0;
        for (int i3 = 0; i3 < exoTrackSelectionArr.length; i3++) {
            if (sampleStreamArr[i3] != null && (exoTrackSelectionArr[i3] == null || !zArr[i3])) {
                int i4 = ((c) sampleStreamArr[i3]).b;
                Assertions.checkState(zArr3[i4]);
                this.G--;
                zArr3[i4] = false;
                sampleStreamArr[i3] = null;
            }
        }
        boolean z = !this.E ? j != 0 : i == 0;
        for (int i5 = 0; i5 < exoTrackSelectionArr.length; i5++) {
            if (sampleStreamArr[i5] == null && exoTrackSelectionArr[i5] != null) {
                ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i5];
                Assertions.checkState(exoTrackSelection.length() == 1);
                Assertions.checkState(exoTrackSelection.getIndexInTrackGroup(0) == 0);
                int indexOf = trackGroupArray.indexOf(exoTrackSelection.getTrackGroup());
                Assertions.checkState(!zArr3[indexOf]);
                this.G++;
                zArr3[indexOf] = true;
                sampleStreamArr[i5] = new c(indexOf);
                zArr2[i5] = true;
                if (!z) {
                    SampleQueue sampleQueue = this.u[indexOf];
                    z = !sampleQueue.seekTo(j, true) && sampleQueue.getReadIndex() != 0;
                }
            }
        }
        if (this.G == 0) {
            this.K = false;
            this.F = false;
            if (this.m.isLoading()) {
                SampleQueue[] sampleQueueArr = this.u;
                int length = sampleQueueArr.length;
                while (i2 < length) {
                    sampleQueueArr[i2].discardToEnd();
                    i2++;
                }
                this.m.cancelLoading();
            } else {
                SampleQueue[] sampleQueueArr2 = this.u;
                int length2 = sampleQueueArr2.length;
                while (i2 < length2) {
                    sampleQueueArr2[i2].reset();
                    i2++;
                }
            }
        } else if (z) {
            j = seekToUs(j);
            while (i2 < sampleStreamArr.length) {
                if (sampleStreamArr[i2] != null) {
                    zArr2[i2] = true;
                }
                i2++;
            }
        }
        this.E = true;
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        l();
        if (!k()) {
            boolean[] zArr = this.z.c;
            int length = this.u.length;
            for (int i = 0; i < length; i++) {
                this.u[i].discardTo(j, z, zArr[i]);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        if (this.M || this.m.hasFatalError() || this.K) {
            return false;
        }
        if (this.x && this.G == 0) {
            return false;
        }
        boolean open = this.o.open();
        if (this.m.isLoading()) {
            return open;
        }
        h();
        return true;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.m.isLoading() && this.o.isOpen();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        if (this.G == 0) {
            return Long.MIN_VALUE;
        }
        return getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        if (!this.F) {
            return C.TIME_UNSET;
        }
        if (!this.M && i() <= this.L) {
            return C.TIME_UNSET;
        }
        this.F = false;
        return this.I;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        long j;
        l();
        boolean[] zArr = this.z.b;
        if (this.M) {
            return Long.MIN_VALUE;
        }
        if (k()) {
            return this.J;
        }
        if (this.y) {
            int length = this.u.length;
            j = Long.MAX_VALUE;
            for (int i = 0; i < length; i++) {
                if (zArr[i] && !this.u[i].isLastSampleQueued()) {
                    j = Math.min(j, this.u[i].getLargestQueuedTimestampUs());
                }
            }
        } else {
            j = Long.MAX_VALUE;
        }
        if (j == Long.MAX_VALUE) {
            j = j();
        }
        return j == Long.MIN_VALUE ? this.I : j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        l();
        boolean[] zArr = this.z.b;
        if (!this.A.isSeekable()) {
            j = 0;
        }
        int i = 0;
        this.F = false;
        this.I = j;
        if (k()) {
            this.J = j;
            return j;
        } else if (this.D != 7 && a(zArr, j)) {
            return j;
        } else {
            this.K = false;
            this.J = j;
            this.M = false;
            if (this.m.isLoading()) {
                SampleQueue[] sampleQueueArr = this.u;
                int length = sampleQueueArr.length;
                while (i < length) {
                    sampleQueueArr[i].discardToEnd();
                    i++;
                }
                this.m.cancelLoading();
            } else {
                this.m.clearFatalError();
                SampleQueue[] sampleQueueArr2 = this.u;
                int length2 = sampleQueueArr2.length;
                while (i < length2) {
                    sampleQueueArr2[i].reset();
                    i++;
                }
            }
            return j;
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        l();
        if (!this.A.isSeekable()) {
            return 0L;
        }
        SeekMap.SeekPoints seekPoints = this.A.getSeekPoints(j);
        return seekParameters.resolveSeekPositionUs(j, seekPoints.first.timeUs, seekPoints.second.timeUs);
    }

    boolean a(int i) {
        return !f() && this.u[i].isReady(this.M);
    }

    void b(int i) throws IOException {
        this.u[i].maybeThrowError();
        b();
    }

    void b() throws IOException {
        this.m.maybeThrowError(this.f.getMinimumLoadableRetryCount(this.D));
    }

    int a(int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
        if (f()) {
            return -3;
        }
        c(i);
        int read = this.u[i].read(formatHolder, decoderInputBuffer, i2, this.M);
        if (read == -3) {
            d(i);
        }
        return read;
    }

    int a(int i, long j) {
        if (f()) {
            return 0;
        }
        c(i);
        SampleQueue sampleQueue = this.u[i];
        int skipCount = sampleQueue.getSkipCount(j, this.M);
        sampleQueue.skip(skipCount);
        if (skipCount == 0) {
            d(i);
        }
        return skipCount;
    }

    private void c(int i) {
        l();
        boolean[] zArr = this.z.d;
        if (!zArr[i]) {
            Format format = this.z.a.get(i).getFormat(0);
            this.g.downstreamFormatChanged(MimeTypes.getTrackType(format.sampleMimeType), format, 0, null, this.I);
            zArr[i] = true;
        }
    }

    private void d(int i) {
        l();
        boolean[] zArr = this.z.b;
        if (this.K && zArr[i]) {
            if (!this.u[i].isReady(false)) {
                this.J = 0L;
                this.K = false;
                this.F = true;
                this.I = 0L;
                this.L = 0;
                for (SampleQueue sampleQueue : this.u) {
                    sampleQueue.reset();
                }
                ((MediaPeriod.Callback) Assertions.checkNotNull(this.s)).onContinueLoadingRequested(this);
            }
        }
    }

    private boolean f() {
        return this.F || k();
    }

    /* renamed from: a */
    public void onLoadCompleted(a aVar, long j, long j2) {
        SeekMap seekMap;
        if (this.B == C.TIME_UNSET && (seekMap = this.A) != null) {
            boolean isSeekable = seekMap.isSeekable();
            long j3 = j();
            this.B = j3 == Long.MIN_VALUE ? 0L : j3 + 10000;
            this.i.onSourceInfoRefreshed(this.B, isSeekable, this.C);
        }
        StatsDataSource statsDataSource = aVar.d;
        LoadEventInfo loadEventInfo = new LoadEventInfo(aVar.b, aVar.l, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j, j2, statsDataSource.getBytesRead());
        this.f.onLoadTaskConcluded(aVar.b);
        this.g.loadCompleted(loadEventInfo, 1, -1, null, 0, null, aVar.k, this.B);
        a(aVar);
        this.M = true;
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.s)).onContinueLoadingRequested(this);
    }

    /* renamed from: a */
    public void onLoadCanceled(a aVar, long j, long j2, boolean z) {
        StatsDataSource statsDataSource = aVar.d;
        LoadEventInfo loadEventInfo = new LoadEventInfo(aVar.b, aVar.l, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j, j2, statsDataSource.getBytesRead());
        this.f.onLoadTaskConcluded(aVar.b);
        this.g.loadCanceled(loadEventInfo, 1, -1, null, 0, null, aVar.k, this.B);
        if (!z) {
            a(aVar);
            for (SampleQueue sampleQueue : this.u) {
                sampleQueue.reset();
            }
            if (this.G > 0) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(this.s)).onContinueLoadingRequested(this);
            }
        }
    }

    /* renamed from: a */
    public Loader.LoadErrorAction onLoadError(a aVar, long j, long j2, IOException iOException, int i) {
        Loader.LoadErrorAction loadErrorAction;
        a aVar2;
        a(aVar);
        StatsDataSource statsDataSource = aVar.d;
        LoadEventInfo loadEventInfo = new LoadEventInfo(aVar.b, aVar.l, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j, j2, statsDataSource.getBytesRead());
        long retryDelayMsFor = this.f.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(1, -1, null, 0, null, C.usToMs(aVar.k), C.usToMs(this.B)), iOException, i));
        boolean z = true;
        if (retryDelayMsFor == C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            int i2 = i();
            if (i2 > this.L) {
                aVar2 = aVar;
            } else {
                z = false;
                aVar2 = aVar;
            }
            if (a(aVar2, i2)) {
                loadErrorAction = Loader.createRetryAction(z, retryDelayMsFor);
            } else {
                loadErrorAction = Loader.DONT_RETRY;
            }
        }
        boolean z2 = !loadErrorAction.isRetry();
        this.g.loadError(loadEventInfo, 1, -1, null, 0, null, aVar.k, this.B, iOException, z2);
        if (z2) {
            this.f.onLoadTaskConcluded(aVar.b);
        }
        return loadErrorAction;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int i, int i2) {
        return a(new d(i, false));
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        this.w = true;
        this.r.post(this.p);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(final SeekMap seekMap) {
        this.r.post(new Runnable() { // from class: com.google.android.exoplayer2.source.-$$Lambda$b$t03AwB2-Zo25XfJmUp67hg2tq7w
            @Override // java.lang.Runnable
            public final void run() {
                b.this.b(seekMap);
            }
        });
    }

    TrackOutput c() {
        return a(new d(0, true));
    }

    @Override // com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener
    public void onUpstreamFormatChanged(Format format) {
        this.r.post(this.p);
    }

    private TrackOutput a(d dVar) {
        int length = this.u.length;
        for (int i = 0; i < length; i++) {
            if (dVar.equals(this.v[i])) {
                return this.u[i];
            }
        }
        SampleQueue createWithDrm = SampleQueue.createWithDrm(this.j, this.r.getLooper(), this.e, this.h);
        createWithDrm.setUpstreamFormatChangeListener(this);
        int i2 = length + 1;
        d[] dVarArr = (d[]) Arrays.copyOf(this.v, i2);
        dVarArr[length] = dVar;
        this.v = (d[]) Util.castNonNullTypeArray(dVarArr);
        SampleQueue[] sampleQueueArr = (SampleQueue[]) Arrays.copyOf(this.u, i2);
        sampleQueueArr[length] = createWithDrm;
        this.u = (SampleQueue[]) Util.castNonNullTypeArray(sampleQueueArr);
        return createWithDrm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void b(SeekMap seekMap) {
        this.A = this.t == null ? seekMap : new SeekMap.Unseekable(C.TIME_UNSET);
        this.B = seekMap.getDurationUs();
        int i = 1;
        this.C = this.H == -1 && seekMap.getDurationUs() == C.TIME_UNSET;
        if (this.C) {
            i = 7;
        }
        this.D = i;
        this.i.onSourceInfoRefreshed(this.B, seekMap.isSeekable(), this.C);
        if (!this.x) {
            g();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        if (!(this.N || this.x || !this.w || this.A == null)) {
            for (SampleQueue sampleQueue : this.u) {
                if (sampleQueue.getUpstreamFormat() == null) {
                    return;
                }
            }
            this.o.close();
            int length = this.u.length;
            TrackGroup[] trackGroupArr = new TrackGroup[length];
            boolean[] zArr = new boolean[length];
            for (int i = 0; i < length; i++) {
                Format format = (Format) Assertions.checkNotNull(this.u[i].getUpstreamFormat());
                String str = format.sampleMimeType;
                boolean isAudio = MimeTypes.isAudio(str);
                boolean z = isAudio || MimeTypes.isVideo(str);
                zArr[i] = z;
                this.y = z | this.y;
                IcyHeaders icyHeaders = this.t;
                if (icyHeaders != null) {
                    if (isAudio || this.v[i].b) {
                        Metadata metadata = format.metadata;
                        format = format.buildUpon().setMetadata(metadata == null ? new Metadata(icyHeaders) : metadata.copyWithAppendedEntries(icyHeaders)).build();
                    }
                    if (isAudio && format.averageBitrate == -1 && format.peakBitrate == -1 && icyHeaders.bitrate != -1) {
                        format = format.buildUpon().setAverageBitrate(icyHeaders.bitrate).build();
                    }
                }
                trackGroupArr[i] = new TrackGroup(format.copyWithExoMediaCryptoType(this.e.getExoMediaCryptoType(format)));
            }
            this.z = new e(new TrackGroupArray(trackGroupArr), zArr);
            this.x = true;
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.s)).onPrepared(this);
        }
    }

    private void a(a aVar) {
        if (this.H == -1) {
            this.H = aVar.m;
        }
    }

    private void h() {
        a aVar = new a(this.c, this.d, this.n, this, this.o);
        if (this.x) {
            Assertions.checkState(k());
            long j = this.B;
            if (j == C.TIME_UNSET || this.J <= j) {
                aVar.a(((SeekMap) Assertions.checkNotNull(this.A)).getSeekPoints(this.J).first.position, this.J);
                for (SampleQueue sampleQueue : this.u) {
                    sampleQueue.setStartTimeUs(this.J);
                }
                this.J = C.TIME_UNSET;
            } else {
                this.M = true;
                this.J = C.TIME_UNSET;
                return;
            }
        }
        this.L = i();
        this.g.loadStarted(new LoadEventInfo(aVar.b, aVar.l, this.m.startLoading(aVar, this, this.f.getMinimumLoadableRetryCount(this.D))), 1, -1, null, 0, null, aVar.k, this.B);
    }

    private boolean a(a aVar, int i) {
        SeekMap seekMap;
        if (this.H == -1 && ((seekMap = this.A) == null || seekMap.getDurationUs() == C.TIME_UNSET)) {
            if (!this.x || f()) {
                this.F = this.x;
                this.I = 0L;
                this.L = 0;
                for (SampleQueue sampleQueue : this.u) {
                    sampleQueue.reset();
                }
                aVar.a(0L, 0L);
                return true;
            }
            this.K = true;
            return false;
        }
        this.L = i;
        return true;
    }

    private boolean a(boolean[] zArr, long j) {
        int length = this.u.length;
        for (int i = 0; i < length; i++) {
            if (!this.u[i].seekTo(j, false) && (zArr[i] || !this.y)) {
                return false;
            }
        }
        return true;
    }

    private int i() {
        int i = 0;
        for (SampleQueue sampleQueue : this.u) {
            i += sampleQueue.getWriteIndex();
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long j() {
        long j = Long.MIN_VALUE;
        for (SampleQueue sampleQueue : this.u) {
            j = Math.max(j, sampleQueue.getLargestQueuedTimestampUs());
        }
        return j;
    }

    private boolean k() {
        return this.J != C.TIME_UNSET;
    }

    @EnsuresNonNull({"trackState", "seekMap"})
    private void l() {
        Assertions.checkState(this.x);
        Assertions.checkNotNull(this.z);
        Assertions.checkNotNull(this.A);
    }

    /* compiled from: ProgressiveMediaPeriod.java */
    /* loaded from: classes2.dex */
    private final class c implements SampleStream {
        private final int b;

        public c(int i) {
            this.b = i;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return b.this.a(this.b);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            b.this.b(this.b);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            return b.this.a(this.b, formatHolder, decoderInputBuffer, i);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j) {
            return b.this.a(this.b, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ProgressiveMediaPeriod.java */
    /* loaded from: classes2.dex */
    public final class a implements IcyDataSource.Listener, Loader.Loadable {
        private final Uri c;
        private final StatsDataSource d;
        private final ProgressiveMediaExtractor e;
        private final ExtractorOutput f;
        private final ConditionVariable g;
        private volatile boolean i;
        private long k;
        @Nullable
        private TrackOutput n;
        private boolean o;
        private final PositionHolder h = new PositionHolder();
        private boolean j = true;
        private long m = -1;
        private final long b = LoadEventInfo.getNewId();
        private DataSpec l = a(0);

        public a(Uri uri, DataSource dataSource, ProgressiveMediaExtractor progressiveMediaExtractor, ExtractorOutput extractorOutput, ConditionVariable conditionVariable) {
            this.c = uri;
            this.d = new StatsDataSource(dataSource);
            this.e = progressiveMediaExtractor;
            this.f = extractorOutput;
            this.g = conditionVariable;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
            this.i = true;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            DataReader dataReader;
            int i = 0;
            while (i == 0 && !this.i) {
                try {
                    long j = this.h.position;
                    this.l = a(j);
                    this.m = this.d.open(this.l);
                    if (this.m != -1) {
                        this.m += j;
                    }
                    b.this.t = IcyHeaders.parse(this.d.getResponseHeaders());
                    DataReader dataReader2 = this.d;
                    if (b.this.t == null || b.this.t.metadataInterval == -1) {
                        dataReader = dataReader2;
                    } else {
                        DataReader icyDataSource = new IcyDataSource(this.d, b.this.t.metadataInterval, this);
                        this.n = b.this.c();
                        this.n.format(b.b);
                        dataReader = icyDataSource;
                    }
                    long j2 = j;
                    this.e.init(dataReader, this.c, this.d.getResponseHeaders(), j, this.m, this.f);
                    if (b.this.t != null) {
                        this.e.disableSeekingOnMp3Streams();
                    }
                    if (this.j) {
                        this.e.seek(j2, this.k);
                        this.j = false;
                    }
                    while (i == 0 && !this.i) {
                        try {
                            this.g.block();
                            i = this.e.read(this.h);
                            long currentInputPosition = this.e.getCurrentInputPosition();
                            if (currentInputPosition > b.this.l + j2) {
                                this.g.close();
                                b.this.r.post(b.this.q);
                                j2 = currentInputPosition;
                            }
                        } catch (InterruptedException unused) {
                            throw new InterruptedIOException();
                        }
                    }
                    if (i == 1) {
                        i = 0;
                    } else if (this.e.getCurrentInputPosition() != -1) {
                        this.h.position = this.e.getCurrentInputPosition();
                    }
                    Util.closeQuietly(this.d);
                } catch (Throwable th) {
                    if (!(i == 1 || this.e.getCurrentInputPosition() == -1)) {
                        this.h.position = this.e.getCurrentInputPosition();
                    }
                    Util.closeQuietly(this.d);
                    throw th;
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.IcyDataSource.Listener
        public void onIcyMetadata(ParsableByteArray parsableByteArray) {
            long max = !this.o ? this.k : Math.max(b.this.j(), this.k);
            int bytesLeft = parsableByteArray.bytesLeft();
            TrackOutput trackOutput = (TrackOutput) Assertions.checkNotNull(this.n);
            trackOutput.sampleData(parsableByteArray, bytesLeft);
            trackOutput.sampleMetadata(max, 1, bytesLeft, 0, null);
            this.o = true;
        }

        private DataSpec a(long j) {
            return new DataSpec.Builder().setUri(this.c).setPosition(j).setKey(b.this.k).setFlags(6).setHttpRequestHeaders(b.a).build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(long j, long j2) {
            this.h.position = j;
            this.k = j2;
            this.j = true;
            this.o = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ProgressiveMediaPeriod.java */
    /* loaded from: classes2.dex */
    public static final class e {
        public final TrackGroupArray a;
        public final boolean[] b;
        public final boolean[] c;
        public final boolean[] d;

        public e(TrackGroupArray trackGroupArray, boolean[] zArr) {
            this.a = trackGroupArray;
            this.b = zArr;
            this.c = new boolean[trackGroupArray.length];
            this.d = new boolean[trackGroupArray.length];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ProgressiveMediaPeriod.java */
    /* loaded from: classes2.dex */
    public static final class d {
        public final int a;
        public final boolean b;

        public d(int i, boolean z) {
            this.a = i;
            this.b = z;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            d dVar = (d) obj;
            return this.a == dVar.a && this.b == dVar.b;
        }

        public int hashCode() {
            return (this.a * 31) + (this.b ? 1 : 0);
        }
    }

    private static Map<String, String> m() {
        HashMap hashMap = new HashMap();
        hashMap.put(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_NAME, "1");
        return Collections.unmodifiableMap(hashMap);
    }
}
