package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.source.rtsp.RtpDataLoadable;
import com.google.android.exoplayer2.source.rtsp.RtspClient;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.source.rtsp.e;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RtspMediaPeriod.java */
/* loaded from: classes2.dex */
public final class e implements MediaPeriod {
    private final Allocator a;
    private final RtspClient d;
    private final b g;
    private final RtpDataChannel.Factory h;
    private MediaPeriod.Callback i;
    private ImmutableList<TrackGroup> j;
    @Nullable
    private IOException k;
    @Nullable
    private RtspMediaSource.RtspPlaybackException l;
    private long m;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private int s;
    private boolean t;
    private final Handler b = Util.createHandlerForCurrentLooper();
    private final a c = new a();
    private final List<d> e = new ArrayList();
    private final List<c> f = new ArrayList();
    private long n = C.TIME_UNSET;

    /* compiled from: RtspMediaPeriod.java */
    /* loaded from: classes2.dex */
    interface b {
        void onSourceInfoRefreshed(j jVar);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
    }

    static /* synthetic */ int f(e eVar) {
        int i = eVar.s;
        eVar.s = i + 1;
        return i;
    }

    public e(Allocator allocator, RtpDataChannel.Factory factory, Uri uri, b bVar, String str) {
        this.a = allocator;
        this.h = factory;
        this.g = bVar;
        a aVar = this.c;
        this.d = new RtspClient(aVar, aVar, str, uri);
    }

    public void a() {
        for (int i = 0; i < this.e.size(); i++) {
            this.e.get(i).e();
        }
        Util.closeQuietly(this.d);
        this.p = true;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.i = callback;
        try {
            this.d.a();
        } catch (IOException e) {
            this.k = e;
            Util.closeQuietly(this.d);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        IOException iOException = this.k;
        if (iOException != null) {
            throw iOException;
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        Assertions.checkState(this.q);
        return new TrackGroupArray((TrackGroup[]) ((ImmutableList) Assertions.checkNotNull(this.j)).toArray(new TrackGroup[0]));
    }

    /* renamed from: a */
    public ImmutableList<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        return ImmutableList.of();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            if (sampleStreamArr[i] != null && (exoTrackSelectionArr[i] == null || !zArr[i])) {
                sampleStreamArr[i] = null;
            }
        }
        this.f.clear();
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
            if (exoTrackSelection != null) {
                TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
                int indexOf = ((ImmutableList) Assertions.checkNotNull(this.j)).indexOf(trackGroup);
                this.f.add(((d) Assertions.checkNotNull(this.e.get(indexOf))).a);
                if (this.j.contains(trackGroup) && sampleStreamArr[i2] == null) {
                    sampleStreamArr[i2] = new C0068e(indexOf);
                    zArr2[i2] = true;
                }
            }
        }
        for (int i3 = 0; i3 < this.e.size(); i3++) {
            d dVar = this.e.get(i3);
            if (!this.f.contains(dVar.a)) {
                dVar.d();
            }
        }
        this.r = true;
        d();
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        if (!b()) {
            for (int i = 0; i < this.e.size(); i++) {
                d dVar = this.e.get(i);
                if (!dVar.e) {
                    dVar.d.discardTo(j, z, true);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        if (b()) {
            return this.n;
        }
        if (a(j)) {
            return j;
        }
        this.m = j;
        this.n = j;
        this.d.b(j);
        for (int i = 0; i < this.e.size(); i++) {
            this.e.get(i).a(j);
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        if (this.o || this.e.isEmpty()) {
            return Long.MIN_VALUE;
        }
        if (b()) {
            return this.n;
        }
        long j = Long.MAX_VALUE;
        boolean z = true;
        for (int i = 0; i < this.e.size(); i++) {
            d dVar = this.e.get(i);
            if (!dVar.e) {
                j = Math.min(j, dVar.a());
                z = false;
            }
        }
        return (z || j == Long.MIN_VALUE) ? this.m : j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        return isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return !this.o;
    }

    boolean a(int i) {
        return this.e.get(i).c();
    }

    int a(int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
        return this.e.get(i).a(formatHolder, decoderInputBuffer, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public RtpDataLoadable a(Uri uri) {
        for (int i = 0; i < this.e.size(); i++) {
            if (!this.e.get(i).e) {
                c cVar = this.e.get(i).a;
                if (cVar.c().equals(uri)) {
                    return cVar.c;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        return this.n != C.TIME_UNSET;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (!(this.p || this.q)) {
            for (int i = 0; i < this.e.size(); i++) {
                if (this.e.get(i).d.getUpstreamFormat() == null) {
                    return;
                }
            }
            this.q = true;
            this.j = a((ImmutableList<d>) ImmutableList.copyOf((Collection) this.e));
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.i)).onPrepared(this);
        }
    }

    private boolean a(long j) {
        for (int i = 0; i < this.e.size(); i++) {
            if (!this.e.get(i).d.seekTo(j, false)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        boolean z = true;
        for (int i = 0; i < this.f.size(); i++) {
            z &= this.f.get(i).a();
        }
        if (z && this.r) {
            this.d.a(this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.o = true;
        for (int i = 0; i < this.e.size(); i++) {
            this.o &= this.e.get(i).e;
        }
    }

    private static ImmutableList<TrackGroup> a(ImmutableList<d> immutableList) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i = 0; i < immutableList.size(); i++) {
            builder.add((ImmutableList.Builder) new TrackGroup((Format) Assertions.checkNotNull(immutableList.get(i).d.getUpstreamFormat())));
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RtspMediaPeriod.java */
    /* loaded from: classes2.dex */
    public final class a implements ExtractorOutput, SampleQueue.UpstreamFormatChangedListener, RtspClient.PlaybackEventListener, RtspClient.SessionInfoListener, Loader.Callback<RtpDataLoadable> {
        /* renamed from: a */
        public void onLoadCanceled(RtpDataLoadable rtpDataLoadable, long j, long j2, boolean z) {
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void seekMap(SeekMap seekMap) {
        }

        private a() {
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public TrackOutput track(int i, int i2) {
            return ((d) Assertions.checkNotNull((d) e.this.e.get(i))).d;
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void endTracks() {
            Handler handler = e.this.b;
            final e eVar = e.this;
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$e$a$gSXvGbtBd1VX8q8d0vOEQW2rIkU
                @Override // java.lang.Runnable
                public final void run() {
                    e.this.c();
                }
            });
        }

        /* renamed from: a */
        public void onLoadCompleted(RtpDataLoadable rtpDataLoadable, long j, long j2) {
            if (e.this.getBufferedPositionUs() != 0) {
                for (int i = 0; i < e.this.e.size(); i++) {
                    d dVar = (d) e.this.e.get(i);
                    if (dVar.a.c == rtpDataLoadable) {
                        dVar.d();
                        return;
                    }
                }
            } else if (!e.this.t) {
                e.this.f();
                e.this.t = true;
            }
        }

        /* renamed from: a */
        public Loader.LoadErrorAction onLoadError(RtpDataLoadable rtpDataLoadable, long j, long j2, IOException iOException, int i) {
            if (!e.this.q) {
                e.this.k = iOException;
            } else if (!(iOException.getCause() instanceof BindException)) {
                e.this.l = new RtspMediaSource.RtspPlaybackException(rtpDataLoadable.b.b.toString(), iOException);
            } else if (e.f(e.this) < 3) {
                return Loader.RETRY;
            }
            return Loader.DONT_RETRY;
        }

        @Override // com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener
        public void onUpstreamFormatChanged(Format format) {
            Handler handler = e.this.b;
            final e eVar = e.this;
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$e$a$9uOz4lvTp-feNV4H5uwUsTsPmAI
                @Override // java.lang.Runnable
                public final void run() {
                    e.this.c();
                }
            });
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.PlaybackEventListener
        public void onRtspSetupCompleted() {
            e.this.d.a(0L);
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.PlaybackEventListener
        public void onPlaybackStarted(long j, ImmutableList<l> immutableList) {
            ArrayList arrayList = new ArrayList(immutableList.size());
            for (int i = 0; i < immutableList.size(); i++) {
                arrayList.add((String) Assertions.checkNotNull(immutableList.get(i).c.getPath()));
            }
            for (int i2 = 0; i2 < e.this.f.size(); i2++) {
                c cVar = (c) e.this.f.get(i2);
                if (!arrayList.contains(cVar.c().getPath())) {
                    e eVar = e.this;
                    String valueOf = String.valueOf(cVar.c());
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 40);
                    sb.append("Server did not provide timing for track ");
                    sb.append(valueOf);
                    eVar.l = new RtspMediaSource.RtspPlaybackException(sb.toString());
                    return;
                }
            }
            for (int i3 = 0; i3 < immutableList.size(); i3++) {
                l lVar = immutableList.get(i3);
                RtpDataLoadable a = e.this.a(lVar.c);
                if (a != null) {
                    a.a(lVar.a);
                    a.a(lVar.b);
                    if (e.this.b()) {
                        a.a(j, lVar.a);
                    }
                }
            }
            if (e.this.b()) {
                e.this.n = C.TIME_UNSET;
            }
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.PlaybackEventListener
        public void onPlaybackError(RtspMediaSource.RtspPlaybackException rtspPlaybackException) {
            e.this.l = rtspPlaybackException;
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.SessionInfoListener
        public void onSessionTimelineUpdated(j jVar, ImmutableList<f> immutableList) {
            for (int i = 0; i < immutableList.size(); i++) {
                e eVar = e.this;
                d dVar = new d(immutableList.get(i), i, eVar.h);
                e.this.e.add(dVar);
                dVar.b();
            }
            e.this.g.onSourceInfoRefreshed(jVar);
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspClient.SessionInfoListener
        public void onSessionTimelineRequestFailed(String str, @Nullable Throwable th) {
            e.this.k = th == null ? new IOException(str) : new IOException(str, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        this.d.b();
        RtpDataChannel.Factory createFallbackDataChannelFactory = this.h.createFallbackDataChannelFactory();
        if (createFallbackDataChannelFactory == null) {
            this.l = new RtspMediaSource.RtspPlaybackException("No fallback data channel factory for TCP retry");
            return;
        }
        ArrayList arrayList = new ArrayList(this.e.size());
        ArrayList arrayList2 = new ArrayList(this.f.size());
        for (int i = 0; i < this.e.size(); i++) {
            d dVar = this.e.get(i);
            if (!dVar.e) {
                d dVar2 = new d(dVar.a.a, i, createFallbackDataChannelFactory);
                arrayList.add(dVar2);
                dVar2.b();
                if (this.f.contains(dVar.a)) {
                    arrayList2.add(dVar2.a);
                }
            } else {
                arrayList.add(dVar);
            }
        }
        ImmutableList copyOf = ImmutableList.copyOf((Collection) this.e);
        this.e.clear();
        this.e.addAll(arrayList);
        this.f.clear();
        this.f.addAll(arrayList2);
        for (int i2 = 0; i2 < copyOf.size(); i2++) {
            ((d) copyOf.get(i2)).d();
        }
    }

    /* compiled from: RtspMediaPeriod.java */
    /* renamed from: com.google.android.exoplayer2.source.rtsp.e$e  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private final class C0068e implements SampleStream {
        private final int b;

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j) {
            return 0;
        }

        public C0068e(int i) {
            this.b = i;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return e.this.a(this.b);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws RtspMediaSource.RtspPlaybackException {
            if (e.this.l != null) {
                throw e.this.l;
            }
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            return e.this.a(this.b, formatHolder, decoderInputBuffer, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RtspMediaPeriod.java */
    /* loaded from: classes2.dex */
    public final class d {
        public final c a;
        private final Loader c;
        private final SampleQueue d;
        private boolean e;
        private boolean f;

        public d(f fVar, int i, RtpDataChannel.Factory factory) {
            this.a = new c(fVar, i, factory);
            StringBuilder sb = new StringBuilder(55);
            sb.append("ExoPlayer:RtspMediaPeriod:RtspLoaderWrapper ");
            sb.append(i);
            this.c = new Loader(sb.toString());
            this.d = SampleQueue.createWithoutDrm(e.this.a);
            this.d.setUpstreamFormatChangeListener(e.this.c);
        }

        public long a() {
            return this.d.getLargestQueuedTimestampUs();
        }

        public void b() {
            this.c.startLoading(this.a.c, e.this.c, 0);
        }

        public boolean c() {
            return this.d.isReady(this.e);
        }

        public int a(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            return this.d.read(formatHolder, decoderInputBuffer, i, this.e);
        }

        public void d() {
            if (!this.e) {
                this.a.c.cancelLoad();
                this.e = true;
                e.this.e();
            }
        }

        public void a(long j) {
            if (!this.e) {
                this.a.c.a();
                this.d.reset();
                this.d.setStartTimeUs(j);
            }
        }

        public void e() {
            if (!this.f) {
                this.c.release();
                this.d.release();
                this.f = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RtspMediaPeriod.java */
    /* loaded from: classes2.dex */
    public final class c {
        public final f a;
        private final RtpDataLoadable c;
        @Nullable
        private String d;

        public c(f fVar, int i, RtpDataChannel.Factory factory) {
            this.a = fVar;
            this.c = new RtpDataLoadable(i, fVar, new RtpDataLoadable.EventListener() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$e$c$gTnJENbBjUVfOGNTDHjKfGWD5-k
                @Override // com.google.android.exoplayer2.source.rtsp.RtpDataLoadable.EventListener
                public final void onTransportReady(String str, RtpDataChannel rtpDataChannel) {
                    e.c.this.a(str, rtpDataChannel);
                }
            }, e.this.c, factory);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str, RtpDataChannel rtpDataChannel) {
            this.d = str;
            RtspMessageChannel.InterleavedBinaryDataListener c = rtpDataChannel.c();
            if (c != null) {
                e.this.d.a(rtpDataChannel.b(), c);
                e.this.t = true;
            }
            e.this.d();
        }

        public boolean a() {
            return this.d != null;
        }

        public String b() {
            Assertions.checkStateNotNull(this.d);
            return this.d;
        }

        public Uri c() {
            return this.c.b.b;
        }
    }
}
