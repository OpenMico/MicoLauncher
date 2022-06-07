package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageDecoder;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.hls.HlsChunkSource;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class HlsSampleStreamWrapper implements ExtractorOutput, SampleQueue.UpstreamFormatChangedListener, SequenceableLoader, Loader.Callback<Chunk>, Loader.ReleaseCallback {
    private static final Set<Integer> a = Collections.unmodifiableSet(new HashSet(Arrays.asList(1, 2, 5)));
    private int A;
    private int B;
    private boolean C;
    private boolean D;
    private int E;
    private Format F;
    @Nullable
    private Format G;
    private boolean H;
    private TrackGroupArray I;
    private Set<TrackGroup> J;
    private int[] K;
    private int L;
    private boolean M;
    private long P;
    private long Q;
    private boolean R;
    private boolean S;
    private boolean T;
    private boolean U;
    private long V;
    @Nullable
    private DrmInitData W;
    @Nullable
    private c X;
    private final int b;
    private final Callback c;
    private final HlsChunkSource d;
    private final Allocator e;
    @Nullable
    private final Format f;
    private final DrmSessionManager g;
    private final DrmSessionEventListener.EventDispatcher h;
    private final LoadErrorHandlingPolicy i;
    private final MediaSourceEventListener.EventDispatcher k;
    private final int l;
    private final Map<String, DrmInitData> t;
    @Nullable
    private Chunk u;
    private TrackOutput z;
    private final Loader j = new Loader("Loader:HlsSampleStreamWrapper");
    private final HlsChunkSource.HlsChunkHolder m = new HlsChunkSource.HlsChunkHolder();
    private int[] w = new int[0];
    private Set<Integer> x = new HashSet(a.size());
    private SparseIntArray y = new SparseIntArray(a.size());
    private b[] v = new b[0];
    private boolean[] O = new boolean[0];
    private boolean[] N = new boolean[0];
    private final ArrayList<c> n = new ArrayList<>();
    private final List<c> o = Collections.unmodifiableList(this.n);
    private final ArrayList<d> s = new ArrayList<>();
    private final Runnable p = new Runnable() { // from class: com.google.android.exoplayer2.source.hls.-$$Lambda$HlsSampleStreamWrapper$4EDm0ncnynYo8njwZ25YSd3XpPE
        @Override // java.lang.Runnable
        public final void run() {
            HlsSampleStreamWrapper.this.k();
        }
    };
    private final Runnable q = new Runnable() { // from class: com.google.android.exoplayer2.source.hls.-$$Lambda$HlsSampleStreamWrapper$JiFDQMzYufl31X36HfiT5uXuWjg
        @Override // java.lang.Runnable
        public final void run() {
            HlsSampleStreamWrapper.this.j();
        }
    };
    private final Handler r = Util.createHandlerForCurrentLooper();

    /* loaded from: classes2.dex */
    public interface Callback extends SequenceableLoader.Callback<HlsSampleStreamWrapper> {
        void onPlaylistRefreshRequired(Uri uri);

        void onPrepared();
    }

    private static int h(int i) {
        switch (i) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 1;
            default:
                return 0;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(SeekMap seekMap) {
    }

    public HlsSampleStreamWrapper(int i, Callback callback, HlsChunkSource hlsChunkSource, Map<String, DrmInitData> map, Allocator allocator, long j, @Nullable Format format, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, int i2) {
        this.b = i;
        this.c = callback;
        this.d = hlsChunkSource;
        this.t = map;
        this.e = allocator;
        this.f = format;
        this.g = drmSessionManager;
        this.h = eventDispatcher;
        this.i = loadErrorHandlingPolicy;
        this.k = eventDispatcher2;
        this.l = i2;
        this.P = j;
        this.Q = j;
    }

    public void a() {
        if (!this.D) {
            continueLoading(this.P);
        }
    }

    public void a(TrackGroup[] trackGroupArr, int i, int... iArr) {
        this.I = a(trackGroupArr);
        this.J = new HashSet();
        for (int i2 : iArr) {
            this.J.add(this.I.get(i2));
        }
        this.L = i;
        Handler handler = this.r;
        final Callback callback = this.c;
        Objects.requireNonNull(callback);
        handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.hls.-$$Lambda$COgt3RAipLG3mXfQxbryzRjBpos
            @Override // java.lang.Runnable
            public final void run() {
                HlsSampleStreamWrapper.Callback.this.onPrepared();
            }
        });
        p();
    }

    public void b() throws IOException {
        g();
        if (this.T && !this.D) {
            throw ParserException.createForMalformedContainer("Loading finished before preparation is complete.", null);
        }
    }

    public TrackGroupArray c() {
        q();
        return this.I;
    }

    public int d() {
        return this.L;
    }

    public int a(int i) {
        q();
        Assertions.checkNotNull(this.K);
        int i2 = this.K[i];
        if (i2 == -1) {
            return this.J.contains(this.I.get(i)) ? -3 : -2;
        }
        boolean[] zArr = this.N;
        if (zArr[i2]) {
            return -2;
        }
        zArr[i2] = true;
        return i2;
    }

    public void b(int i) {
        q();
        Assertions.checkNotNull(this.K);
        int i2 = this.K[i];
        Assertions.checkState(this.N[i2]);
        this.N[i2] = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r20, boolean[] r21, com.google.android.exoplayer2.source.SampleStream[] r22, boolean[] r23, long r24, boolean r26) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.a(com.google.android.exoplayer2.trackselection.ExoTrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long, boolean):boolean");
    }

    public void a(long j, boolean z) {
        if (this.C && !o()) {
            int length = this.v.length;
            for (int i = 0; i < length; i++) {
                this.v[i].discardTo(j, z, this.N[i]);
            }
        }
    }

    public boolean b(long j, boolean z) {
        this.P = j;
        if (o()) {
            this.Q = j;
            return true;
        }
        if (this.C && !z && b(j)) {
            return false;
        }
        this.Q = j;
        this.T = false;
        this.n.clear();
        if (this.j.isLoading()) {
            if (this.C) {
                for (b bVar : this.v) {
                    bVar.discardToEnd();
                }
            }
            this.j.cancelLoading();
        } else {
            this.j.clearFatalError();
            i();
        }
        return true;
    }

    public void e() {
        if (!this.n.isEmpty()) {
            c cVar = (c) Iterables.getLast(this.n);
            int a2 = this.d.a(cVar);
            if (a2 == 1) {
                cVar.c();
            } else if (a2 == 2 && !this.T && this.j.isLoading()) {
                this.j.cancelLoading();
            }
        }
    }

    public void f() {
        if (this.D) {
            for (b bVar : this.v) {
                bVar.preRelease();
            }
        }
        this.j.release(this);
        this.r.removeCallbacksAndMessages(null);
        this.H = true;
        this.s.clear();
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.ReleaseCallback
    public void onLoaderReleased() {
        for (b bVar : this.v) {
            bVar.release();
        }
    }

    public void a(boolean z) {
        this.d.a(z);
    }

    public boolean a(Uri uri, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean z) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor;
        if (!this.d.a(uri)) {
            return true;
        }
        long j = (z || (fallbackSelectionFor = this.i.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.d.c()), loadErrorInfo)) == null || fallbackSelectionFor.type != 2) ? -9223372036854775807L : fallbackSelectionFor.exclusionDurationMs;
        return this.d.a(uri, j) && j != C.TIME_UNSET;
    }

    public boolean c(int i) {
        return !o() && this.v[i].isReady(this.T);
    }

    public void d(int i) throws IOException {
        g();
        this.v[i].maybeThrowError();
    }

    public void g() throws IOException {
        this.j.maybeThrowError();
        this.d.a();
    }

    public int a(int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
        Format format;
        if (o()) {
            return -3;
        }
        int i3 = 0;
        if (!this.n.isEmpty()) {
            int i4 = 0;
            while (i4 < this.n.size() - 1 && b(this.n.get(i4))) {
                i4++;
            }
            Util.removeRange(this.n, 0, i4);
            c cVar = this.n.get(0);
            Format format2 = cVar.trackFormat;
            if (!format2.equals(this.G)) {
                this.k.downstreamFormatChanged(this.b, format2, cVar.trackSelectionReason, cVar.trackSelectionData, cVar.startTimeUs);
            }
            this.G = format2;
        }
        if (!this.n.isEmpty() && !this.n.get(0).b()) {
            return -3;
        }
        int read = this.v[i].read(formatHolder, decoderInputBuffer, i2, this.T);
        if (read == -5) {
            Format format3 = (Format) Assertions.checkNotNull(formatHolder.format);
            if (i == this.B) {
                int peekSourceId = this.v[i].peekSourceId();
                while (i3 < this.n.size() && this.n.get(i3).a != peekSourceId) {
                    i3++;
                }
                if (i3 < this.n.size()) {
                    format = this.n.get(i3).trackFormat;
                } else {
                    format = (Format) Assertions.checkNotNull(this.F);
                }
                format3 = format3.withManifestFormatInfo(format);
            }
            formatHolder.format = format3;
        }
        return read;
    }

    public int a(int i, long j) {
        if (o()) {
            return 0;
        }
        b bVar = this.v[i];
        int skipCount = bVar.getSkipCount(j, this.T);
        c cVar = (c) Iterables.getLast(this.n, null);
        if (cVar != null && !cVar.b()) {
            skipCount = Math.min(skipCount, cVar.a(i) - bVar.getReadIndex());
        }
        bVar.skip(skipCount);
        return skipCount;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        if (this.T) {
            return Long.MIN_VALUE;
        }
        if (o()) {
            return this.Q;
        }
        long j = this.P;
        c n = n();
        if (!n.isLoadCompleted()) {
            if (this.n.size() > 1) {
                ArrayList<c> arrayList = this.n;
                n = arrayList.get(arrayList.size() - 2);
            } else {
                n = null;
            }
        }
        if (n != null) {
            j = Math.max(j, n.endTimeUs);
        }
        if (this.C) {
            for (b bVar : this.v) {
                j = Math.max(j, bVar.getLargestQueuedTimestampUs());
            }
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        if (o()) {
            return this.Q;
        }
        if (this.T) {
            return Long.MIN_VALUE;
        }
        return n().endTimeUs;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        List<c> list;
        long j2;
        if (this.T || this.j.isLoading() || this.j.hasFatalError()) {
            return false;
        }
        if (o()) {
            List<c> emptyList = Collections.emptyList();
            long j3 = this.Q;
            for (b bVar : this.v) {
                bVar.setStartTimeUs(this.Q);
            }
            list = emptyList;
            j2 = j3;
        } else {
            List<c> list2 = this.o;
            c n = n();
            if (n.isLoadCompleted()) {
                j2 = n.endTimeUs;
            } else {
                j2 = Math.max(this.P, n.startTimeUs);
            }
            list = list2;
        }
        this.m.clear();
        this.d.a(j, j2, list, this.D || !list.isEmpty(), this.m);
        boolean z = this.m.endOfStream;
        Chunk chunk = this.m.chunk;
        Uri uri = this.m.playlistUrl;
        if (z) {
            this.Q = C.TIME_UNSET;
            this.T = true;
            return true;
        } else if (chunk == null) {
            if (uri != null) {
                this.c.onPlaylistRefreshRequired(uri);
            }
            return false;
        } else {
            if (a(chunk)) {
                a((c) chunk);
            }
            this.u = chunk;
            this.k.loadStarted(new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, this.j.startLoading(chunk, this, this.i.getMinimumLoadableRetryCount(chunk.type))), chunk.type, this.b, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
            return true;
        }
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.j.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        if (!this.j.hasFatalError() && !o()) {
            if (this.j.isLoading()) {
                Assertions.checkNotNull(this.u);
                if (this.d.a(j, this.u, this.o)) {
                    this.j.cancelLoading();
                    return;
                }
                return;
            }
            int size = this.o.size();
            while (size > 0 && this.d.a(this.o.get(size - 1)) == 2) {
                size--;
            }
            if (size < this.o.size()) {
                e(size);
            }
            int a2 = this.d.a(j, this.o);
            if (a2 < this.n.size()) {
                e(a2);
            }
        }
    }

    /* renamed from: a */
    public void onLoadCompleted(Chunk chunk, long j, long j2) {
        this.u = null;
        this.d.a(chunk);
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j, j2, chunk.bytesLoaded());
        this.i.onLoadTaskConcluded(chunk.loadTaskId);
        this.k.loadCompleted(loadEventInfo, chunk.type, this.b, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
        if (!this.D) {
            continueLoading(this.P);
        } else {
            this.c.onContinueLoadingRequested(this);
        }
    }

    /* renamed from: a */
    public void onLoadCanceled(Chunk chunk, long j, long j2, boolean z) {
        this.u = null;
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j, j2, chunk.bytesLoaded());
        this.i.onLoadTaskConcluded(chunk.loadTaskId);
        this.k.loadCanceled(loadEventInfo, chunk.type, this.b, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
        if (!z) {
            if (o() || this.E == 0) {
                i();
            }
            if (this.E > 0) {
                this.c.onContinueLoadingRequested(this);
            }
        }
    }

    /* renamed from: a */
    public Loader.LoadErrorAction onLoadError(Chunk chunk, long j, long j2, IOException iOException, int i) {
        Loader.LoadErrorAction loadErrorAction;
        int i2;
        boolean a2 = a(chunk);
        if (a2 && !((c) chunk).b() && (iOException instanceof HttpDataSource.InvalidResponseCodeException) && ((i2 = ((HttpDataSource.InvalidResponseCodeException) iOException).responseCode) == 410 || i2 == 404)) {
            return Loader.RETRY;
        }
        long bytesLoaded = chunk.bytesLoaded();
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j, j2, bytesLoaded);
        LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo = new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(chunk.type, this.b, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, C.usToMs(chunk.startTimeUs), C.usToMs(chunk.endTimeUs)), iOException, i);
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = this.i.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.d.c()), loadErrorInfo);
        boolean z = false;
        boolean a3 = (fallbackSelectionFor == null || fallbackSelectionFor.type != 2) ? false : this.d.a(chunk, fallbackSelectionFor.exclusionDurationMs);
        if (a3) {
            if (a2 && bytesLoaded == 0) {
                ArrayList<c> arrayList = this.n;
                if (arrayList.remove(arrayList.size() - 1) == chunk) {
                    z = true;
                }
                Assertions.checkState(z);
                if (this.n.isEmpty()) {
                    this.Q = this.P;
                } else {
                    ((c) Iterables.getLast(this.n)).a();
                }
            }
            loadErrorAction = Loader.DONT_RETRY;
        } else {
            long retryDelayMsFor = this.i.getRetryDelayMsFor(loadErrorInfo);
            if (retryDelayMsFor != C.TIME_UNSET) {
                loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
            } else {
                loadErrorAction = Loader.DONT_RETRY_FATAL;
            }
        }
        boolean z2 = !loadErrorAction.isRetry();
        this.k.loadError(loadEventInfo, chunk.type, this.b, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, iOException, z2);
        if (z2) {
            this.u = null;
            this.i.onLoadTaskConcluded(chunk.loadTaskId);
        }
        if (a3) {
            if (!this.D) {
                continueLoading(this.P);
            } else {
                this.c.onContinueLoadingRequested(this);
            }
        }
        return loadErrorAction;
    }

    private void a(c cVar) {
        this.X = cVar;
        this.F = cVar.trackFormat;
        this.Q = C.TIME_UNSET;
        this.n.add(cVar);
        ImmutableList.Builder builder = ImmutableList.builder();
        for (b bVar : this.v) {
            builder.add((ImmutableList.Builder) Integer.valueOf(bVar.getWriteIndex()));
        }
        cVar.a(this, builder.build());
        b[] bVarArr = this.v;
        for (b bVar2 : bVarArr) {
            bVar2.a(cVar);
            if (cVar.d) {
                bVar2.splice();
            }
        }
    }

    private void e(int i) {
        Assertions.checkState(!this.j.isLoading());
        while (true) {
            if (i >= this.n.size()) {
                i = -1;
                break;
            } else if (f(i)) {
                break;
            } else {
                i++;
            }
        }
        if (i != -1) {
            long j = n().endTimeUs;
            c g = g(i);
            if (this.n.isEmpty()) {
                this.Q = this.P;
            } else {
                ((c) Iterables.getLast(this.n)).a();
            }
            this.T = false;
            this.k.upstreamDiscarded(this.A, g.startTimeUs, j);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int i, int i2) {
        TrackOutput trackOutput;
        if (!a.contains(Integer.valueOf(i2))) {
            int i3 = 0;
            while (true) {
                TrackOutput[] trackOutputArr = this.v;
                if (i3 >= trackOutputArr.length) {
                    trackOutput = null;
                    break;
                } else if (this.w[i3] == i) {
                    trackOutput = trackOutputArr[i3];
                    break;
                } else {
                    i3++;
                }
            }
        } else {
            trackOutput = a(i, i2);
        }
        if (trackOutput == null) {
            if (this.U) {
                return c(i, i2);
            }
            trackOutput = b(i, i2);
        }
        if (i2 != 5) {
            return trackOutput;
        }
        if (this.z == null) {
            this.z = new a(trackOutput, this.l);
        }
        return this.z;
    }

    @Nullable
    private TrackOutput a(int i, int i2) {
        Assertions.checkArgument(a.contains(Integer.valueOf(i2)));
        int i3 = this.y.get(i2, -1);
        if (i3 == -1) {
            return null;
        }
        if (this.x.add(Integer.valueOf(i2))) {
            this.w[i3] = i;
        }
        if (this.w[i3] == i) {
            return this.v[i3];
        }
        return c(i, i2);
    }

    private SampleQueue b(int i, int i2) {
        int length = this.v.length;
        boolean z = true;
        if (!(i2 == 1 || i2 == 2)) {
            z = false;
        }
        b bVar = new b(this.e, this.r.getLooper(), this.g, this.h, this.t);
        bVar.setStartTimeUs(this.P);
        if (z) {
            bVar.a(this.W);
        }
        bVar.setSampleOffsetUs(this.V);
        c cVar = this.X;
        if (cVar != null) {
            bVar.a(cVar);
        }
        bVar.setUpstreamFormatChangeListener(this);
        int i3 = length + 1;
        this.w = Arrays.copyOf(this.w, i3);
        this.w[length] = i;
        this.v = (b[]) Util.nullSafeArrayAppend(this.v, bVar);
        this.O = Arrays.copyOf(this.O, i3);
        boolean[] zArr = this.O;
        zArr[length] = z;
        this.M = zArr[length] | this.M;
        this.x.add(Integer.valueOf(i2));
        this.y.append(i2, length);
        if (h(i2) > h(this.A)) {
            this.B = length;
            this.A = i2;
        }
        this.N = Arrays.copyOf(this.N, i3);
        return bVar;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        this.U = true;
        this.r.post(this.q);
    }

    @Override // com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener
    public void onUpstreamFormatChanged(Format format) {
        this.r.post(this.p);
    }

    public void h() {
        this.x.clear();
    }

    public void a(long j) {
        if (this.V != j) {
            this.V = j;
            for (b bVar : this.v) {
                bVar.setSampleOffsetUs(j);
            }
        }
    }

    public void a(@Nullable DrmInitData drmInitData) {
        if (!Util.areEqual(this.W, drmInitData)) {
            this.W = drmInitData;
            int i = 0;
            while (true) {
                b[] bVarArr = this.v;
                if (i < bVarArr.length) {
                    if (this.O[i]) {
                        bVarArr[i].a(drmInitData);
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private void a(SampleStream[] sampleStreamArr) {
        this.s.clear();
        for (SampleStream sampleStream : sampleStreamArr) {
            if (sampleStream != null) {
                this.s.add((d) sampleStream);
            }
        }
    }

    private boolean b(c cVar) {
        int i = cVar.a;
        int length = this.v.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.N[i2] && this.v[i2].peekSourceId() == i) {
                return false;
            }
        }
        return true;
    }

    private boolean f(int i) {
        for (int i2 = i; i2 < this.n.size(); i2++) {
            if (this.n.get(i2).d) {
                return false;
            }
        }
        c cVar = this.n.get(i);
        for (int i3 = 0; i3 < this.v.length; i3++) {
            if (this.v[i3].getReadIndex() > cVar.a(i3)) {
                return false;
            }
        }
        return true;
    }

    private c g(int i) {
        c cVar = this.n.get(i);
        ArrayList<c> arrayList = this.n;
        Util.removeRange(arrayList, i, arrayList.size());
        for (int i2 = 0; i2 < this.v.length; i2++) {
            this.v[i2].discardUpstreamSamples(cVar.a(i2));
        }
        return cVar;
    }

    private void i() {
        for (b bVar : this.v) {
            bVar.reset(this.R);
        }
        this.R = false;
    }

    public void j() {
        this.C = true;
        k();
    }

    public void k() {
        if (!this.H && this.K == null && this.C) {
            for (b bVar : this.v) {
                if (bVar.getUpstreamFormat() == null) {
                    return;
                }
            }
            if (this.I != null) {
                l();
                return;
            }
            m();
            p();
            this.c.onPrepared();
        }
    }

    @EnsuresNonNull({"trackGroupToSampleQueueIndex"})
    @RequiresNonNull({"trackGroups"})
    private void l() {
        int i = this.I.length;
        this.K = new int[i];
        Arrays.fill(this.K, -1);
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = 0;
            while (true) {
                b[] bVarArr = this.v;
                if (i3 >= bVarArr.length) {
                    break;
                } else if (a((Format) Assertions.checkStateNotNull(bVarArr[i3].getUpstreamFormat()), this.I.get(i2).getFormat(0))) {
                    this.K[i2] = i3;
                    break;
                } else {
                    i3++;
                }
            }
        }
        Iterator<d> it = this.s.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    @EnsuresNonNull({"trackGroups", "optionalTrackGroups", "trackGroupToSampleQueueIndex"})
    private void m() {
        int length = this.v.length;
        boolean z = false;
        int i = 7;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            int i4 = 2;
            if (i3 >= length) {
                break;
            }
            String str = ((Format) Assertions.checkStateNotNull(this.v[i3].getUpstreamFormat())).sampleMimeType;
            if (!MimeTypes.isVideo(str)) {
                if (MimeTypes.isAudio(str)) {
                    i4 = 1;
                } else {
                    i4 = MimeTypes.isText(str) ? 3 : 7;
                }
            }
            if (h(i4) > h(i)) {
                i2 = i3;
                i = i4;
            } else if (i4 == i && i2 != -1) {
                i2 = -1;
            }
            i3++;
        }
        TrackGroup b2 = this.d.b();
        int i5 = b2.length;
        this.L = -1;
        this.K = new int[length];
        for (int i6 = 0; i6 < length; i6++) {
            this.K[i6] = i6;
        }
        TrackGroup[] trackGroupArr = new TrackGroup[length];
        for (int i7 = 0; i7 < length; i7++) {
            Format format = (Format) Assertions.checkStateNotNull(this.v[i7].getUpstreamFormat());
            if (i7 == i2) {
                Format[] formatArr = new Format[i5];
                if (i5 == 1) {
                    formatArr[0] = format.withManifestFormatInfo(b2.getFormat(0));
                } else {
                    for (int i8 = 0; i8 < i5; i8++) {
                        formatArr[i8] = a(b2.getFormat(i8), format, true);
                    }
                }
                trackGroupArr[i7] = new TrackGroup(formatArr);
                this.L = i7;
            } else {
                trackGroupArr[i7] = new TrackGroup(a((i != 2 || !MimeTypes.isAudio(format.sampleMimeType)) ? null : this.f, format, false));
            }
        }
        this.I = a(trackGroupArr);
        if (this.J == null) {
            z = true;
        }
        Assertions.checkState(z);
        this.J = Collections.emptySet();
    }

    private TrackGroupArray a(TrackGroup[] trackGroupArr) {
        for (int i = 0; i < trackGroupArr.length; i++) {
            TrackGroup trackGroup = trackGroupArr[i];
            Format[] formatArr = new Format[trackGroup.length];
            for (int i2 = 0; i2 < trackGroup.length; i2++) {
                Format format = trackGroup.getFormat(i2);
                formatArr[i2] = format.copyWithExoMediaCryptoType(this.g.getExoMediaCryptoType(format));
            }
            trackGroupArr[i] = new TrackGroup(formatArr);
        }
        return new TrackGroupArray(trackGroupArr);
    }

    private c n() {
        ArrayList<c> arrayList = this.n;
        return arrayList.get(arrayList.size() - 1);
    }

    private boolean o() {
        return this.Q != C.TIME_UNSET;
    }

    private boolean b(long j) {
        int length = this.v.length;
        for (int i = 0; i < length; i++) {
            if (!this.v[i].seekTo(j, false) && (this.O[i] || !this.M)) {
                return false;
            }
        }
        return true;
    }

    @RequiresNonNull({"trackGroups", "optionalTrackGroups"})
    private void p() {
        this.D = true;
    }

    @EnsuresNonNull({"trackGroups", "optionalTrackGroups"})
    private void q() {
        Assertions.checkState(this.D);
        Assertions.checkNotNull(this.I);
        Assertions.checkNotNull(this.J);
    }

    private static Format a(@Nullable Format format, Format format2, boolean z) {
        String str;
        String str2;
        if (format == null) {
            return format2;
        }
        int trackType = MimeTypes.getTrackType(format2.sampleMimeType);
        if (Util.getCodecCountOfType(format.codecs, trackType) == 1) {
            str2 = Util.getCodecsOfType(format.codecs, trackType);
            str = MimeTypes.getMediaMimeType(str2);
        } else {
            str2 = MimeTypes.getCodecsCorrespondingToMimeType(format.codecs, format2.sampleMimeType);
            str = format2.sampleMimeType;
        }
        Format.Builder codecs = format2.buildUpon().setId(format.id).setLabel(format.label).setLanguage(format.language).setSelectionFlags(format.selectionFlags).setRoleFlags(format.roleFlags).setAverageBitrate(z ? format.averageBitrate : -1).setPeakBitrate(z ? format.peakBitrate : -1).setCodecs(str2);
        if (trackType == 2) {
            codecs.setWidth(format.width).setHeight(format.height).setFrameRate(format.frameRate);
        }
        if (str != null) {
            codecs.setSampleMimeType(str);
        }
        if (format.channelCount != -1 && trackType == 1) {
            codecs.setChannelCount(format.channelCount);
        }
        if (format.metadata != null) {
            Metadata metadata = format.metadata;
            if (format2.metadata != null) {
                metadata = format2.metadata.copyWithAppendedEntriesFrom(metadata);
            }
            codecs.setMetadata(metadata);
        }
        return codecs.build();
    }

    private static boolean a(Chunk chunk) {
        return chunk instanceof c;
    }

    private static boolean a(Format format, Format format2) {
        String str = format.sampleMimeType;
        String str2 = format2.sampleMimeType;
        int trackType = MimeTypes.getTrackType(str);
        if (trackType != 3) {
            return trackType == MimeTypes.getTrackType(str2);
        }
        if (!Util.areEqual(str, str2)) {
            return false;
        }
        return (!MimeTypes.APPLICATION_CEA608.equals(str) && !MimeTypes.APPLICATION_CEA708.equals(str)) || format.accessibilityChannel == format2.accessibilityChannel;
    }

    private static DummyTrackOutput c(int i, int i2) {
        StringBuilder sb = new StringBuilder(54);
        sb.append("Unmapped track with id ");
        sb.append(i);
        sb.append(" of type ");
        sb.append(i2);
        Log.w("HlsSampleStreamWrapper", sb.toString());
        return new DummyTrackOutput();
    }

    /* loaded from: classes2.dex */
    public static final class b extends SampleQueue {
        private final Map<String, DrmInitData> a;
        @Nullable
        private DrmInitData b;

        private b(Allocator allocator, Looper looper, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, Map<String, DrmInitData> map) {
            super(allocator, looper, drmSessionManager, eventDispatcher);
            this.a = map;
        }

        public void a(c cVar) {
            sourceId(cVar.a);
        }

        public void a(@Nullable DrmInitData drmInitData) {
            this.b = drmInitData;
            invalidateUpstreamFormatAdjustment();
        }

        @Override // com.google.android.exoplayer2.source.SampleQueue
        public Format getAdjustedUpstreamFormat(Format format) {
            DrmInitData drmInitData;
            DrmInitData drmInitData2 = this.b;
            if (drmInitData2 == null) {
                drmInitData2 = format.drmInitData;
            }
            if (!(drmInitData2 == null || (drmInitData = this.a.get(drmInitData2.schemeType)) == null)) {
                drmInitData2 = drmInitData;
            }
            Metadata a = a(format.metadata);
            if (!(drmInitData2 == format.drmInitData && a == format.metadata)) {
                format = format.buildUpon().setDrmInitData(drmInitData2).setMetadata(a).build();
            }
            return super.getAdjustedUpstreamFormat(format);
        }

        @Nullable
        private Metadata a(@Nullable Metadata metadata) {
            if (metadata == null) {
                return null;
            }
            int length = metadata.length();
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                }
                Metadata.Entry entry = metadata.get(i2);
                if ((entry instanceof PrivFrame) && "com.apple.streaming.transportStreamTimestamp".equals(((PrivFrame) entry).owner)) {
                    break;
                }
                i2++;
            }
            if (i2 == -1) {
                return metadata;
            }
            if (length == 1) {
                return null;
            }
            Metadata.Entry[] entryArr = new Metadata.Entry[length - 1];
            while (i < length) {
                if (i != i2) {
                    entryArr[i < i2 ? i : i - 1] = metadata.get(i);
                }
                i++;
            }
            return new Metadata(entryArr);
        }

        @Override // com.google.android.exoplayer2.source.SampleQueue, com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long j, int i, int i2, int i3, @Nullable TrackOutput.CryptoData cryptoData) {
            super.sampleMetadata(j, i, i2, i3, cryptoData);
        }
    }

    /* loaded from: classes2.dex */
    private static class a implements TrackOutput {
        private static final Format a = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_ID3).build();
        private static final Format b = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_EMSG).build();
        private final EventMessageDecoder c = new EventMessageDecoder();
        private final TrackOutput d;
        private final Format e;
        private Format f;
        private byte[] g;
        private int h;

        public a(TrackOutput trackOutput, int i) {
            this.d = trackOutput;
            if (i == 1) {
                this.e = a;
            } else if (i == 3) {
                this.e = b;
            } else {
                StringBuilder sb = new StringBuilder(33);
                sb.append("Unknown metadataType: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            this.g = new byte[0];
            this.h = 0;
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void format(Format format) {
            this.f = format;
            this.d.format(this.e);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public int sampleData(DataReader dataReader, int i, boolean z, int i2) throws IOException {
            a(this.h + i);
            int read = dataReader.read(this.g, this.h, i);
            if (read != -1) {
                this.h += read;
                return read;
            } else if (z) {
                return -1;
            } else {
                throw new EOFException();
            }
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleData(ParsableByteArray parsableByteArray, int i, int i2) {
            a(this.h + i);
            parsableByteArray.readBytes(this.g, this.h, i);
            this.h += i;
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long j, int i, int i2, int i3, @Nullable TrackOutput.CryptoData cryptoData) {
            Assertions.checkNotNull(this.f);
            ParsableByteArray a2 = a(i2, i3);
            if (!Util.areEqual(this.f.sampleMimeType, this.e.sampleMimeType)) {
                if (MimeTypes.APPLICATION_EMSG.equals(this.f.sampleMimeType)) {
                    EventMessage decode = this.c.decode(a2);
                    if (!a(decode)) {
                        Log.w("EmsgUnwrappingTrackOutput", String.format("Ignoring EMSG. Expected it to contain wrapped %s but actual wrapped format: %s", this.e.sampleMimeType, decode.getWrappedMetadataFormat()));
                        return;
                    }
                    a2 = new ParsableByteArray((byte[]) Assertions.checkNotNull(decode.getWrappedMetadataBytes()));
                } else {
                    String valueOf = String.valueOf(this.f.sampleMimeType);
                    Log.w("EmsgUnwrappingTrackOutput", valueOf.length() != 0 ? "Ignoring sample for unsupported format: ".concat(valueOf) : new String("Ignoring sample for unsupported format: "));
                    return;
                }
            }
            int bytesLeft = a2.bytesLeft();
            this.d.sampleData(a2, bytesLeft);
            this.d.sampleMetadata(j, i, bytesLeft, i3, cryptoData);
        }

        private boolean a(EventMessage eventMessage) {
            Format wrappedMetadataFormat = eventMessage.getWrappedMetadataFormat();
            return wrappedMetadataFormat != null && Util.areEqual(this.e.sampleMimeType, wrappedMetadataFormat.sampleMimeType);
        }

        private void a(int i) {
            byte[] bArr = this.g;
            if (bArr.length < i) {
                this.g = Arrays.copyOf(bArr, i + (i / 2));
            }
        }

        private ParsableByteArray a(int i, int i2) {
            int i3 = this.h - i2;
            ParsableByteArray parsableByteArray = new ParsableByteArray(Arrays.copyOfRange(this.g, i3 - i, i3));
            byte[] bArr = this.g;
            System.arraycopy(bArr, i3, bArr, 0, i2);
            this.h = i2;
            return parsableByteArray;
        }
    }
}
