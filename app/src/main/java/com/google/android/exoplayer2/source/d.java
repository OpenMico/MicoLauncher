package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SingleSampleMediaPeriod.java */
/* loaded from: classes2.dex */
public final class d implements MediaPeriod, Loader.Callback<b> {
    final Format b;
    final boolean c;
    boolean d;
    byte[] e;
    int f;
    private final DataSpec g;
    private final DataSource.Factory h;
    @Nullable
    private final TransferListener i;
    private final LoadErrorHandlingPolicy j;
    private final MediaSourceEventListener.EventDispatcher k;
    private final TrackGroupArray l;
    private final long n;
    private final ArrayList<a> m = new ArrayList<>();
    final Loader a = new Loader("SingleSampleMediaPeriod");

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() {
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
    }

    public d(DataSpec dataSpec, DataSource.Factory factory, @Nullable TransferListener transferListener, Format format, long j, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher, boolean z) {
        this.g = dataSpec;
        this.h = factory;
        this.i = transferListener;
        this.b = format;
        this.n = j;
        this.j = loadErrorHandlingPolicy;
        this.k = eventDispatcher;
        this.c = z;
        this.l = new TrackGroupArray(new TrackGroup(format));
    }

    public void a() {
        this.a.release();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        callback.onPrepared(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.l;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            if (sampleStreamArr[i] != null && (exoTrackSelectionArr[i] == null || !zArr[i])) {
                this.m.remove(sampleStreamArr[i]);
                sampleStreamArr[i] = null;
            }
            if (sampleStreamArr[i] == null && exoTrackSelectionArr[i] != null) {
                a aVar = new a();
                this.m.add(aVar);
                sampleStreamArr[i] = aVar;
                zArr2[i] = true;
            }
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        if (this.d || this.a.isLoading() || this.a.hasFatalError()) {
            return false;
        }
        DataSource createDataSource = this.h.createDataSource();
        TransferListener transferListener = this.i;
        if (transferListener != null) {
            createDataSource.addTransferListener(transferListener);
        }
        b bVar = new b(this.g, createDataSource);
        this.k.loadStarted(new LoadEventInfo(bVar.a, this.g, this.a.startLoading(bVar, this, this.j.getMinimumLoadableRetryCount(1))), 1, -1, this.b, 0, null, 0L, this.n);
        return true;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.a.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return (this.d || this.a.isLoading()) ? Long.MIN_VALUE : 0L;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.d ? Long.MIN_VALUE : 0L;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        for (int i = 0; i < this.m.size(); i++) {
            this.m.get(i).a();
        }
        return j;
    }

    /* renamed from: a */
    public void onLoadCompleted(b bVar, long j, long j2) {
        this.f = (int) bVar.c.getBytesRead();
        this.e = (byte[]) Assertions.checkNotNull(bVar.d);
        this.d = true;
        StatsDataSource statsDataSource = bVar.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(bVar.a, bVar.b, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j, j2, this.f);
        this.j.onLoadTaskConcluded(bVar.a);
        this.k.loadCompleted(loadEventInfo, 1, -1, this.b, 0, null, 0L, this.n);
    }

    /* renamed from: a */
    public void onLoadCanceled(b bVar, long j, long j2, boolean z) {
        StatsDataSource statsDataSource = bVar.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(bVar.a, bVar.b, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j, j2, statsDataSource.getBytesRead());
        this.j.onLoadTaskConcluded(bVar.a);
        this.k.loadCanceled(loadEventInfo, 1, -1, null, 0, null, 0L, this.n);
    }

    /* renamed from: a */
    public Loader.LoadErrorAction onLoadError(b bVar, long j, long j2, IOException iOException, int i) {
        Loader.LoadErrorAction loadErrorAction;
        StatsDataSource statsDataSource = bVar.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(bVar.a, bVar.b, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j, j2, statsDataSource.getBytesRead());
        long retryDelayMsFor = this.j.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(1, -1, this.b, 0, null, 0L, C.usToMs(this.n)), iOException, i));
        int i2 = (retryDelayMsFor > C.TIME_UNSET ? 1 : (retryDelayMsFor == C.TIME_UNSET ? 0 : -1));
        boolean z = i2 == 0 || i >= this.j.getMinimumLoadableRetryCount(1);
        if (this.c && z) {
            Log.w("SingleSampleMediaPeriod", "Loading failed, treating as end-of-stream.", iOException);
            this.d = true;
            loadErrorAction = Loader.DONT_RETRY;
        } else if (i2 != 0) {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
        } else {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        }
        boolean z2 = !loadErrorAction.isRetry();
        this.k.loadError(loadEventInfo, 1, -1, this.b, 0, null, 0L, this.n, iOException, z2);
        if (z2) {
            this.j.onLoadTaskConcluded(bVar.a);
        }
        return loadErrorAction;
    }

    /* compiled from: SingleSampleMediaPeriod.java */
    /* loaded from: classes2.dex */
    private final class a implements SampleStream {
        private int b;
        private boolean c;

        private a() {
        }

        public void a() {
            if (this.b == 2) {
                this.b = 1;
            }
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return d.this.d;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            if (!d.this.c) {
                d.this.a.maybeThrowError();
            }
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            b();
            if (d.this.d && d.this.e == null) {
                this.b = 2;
            }
            int i2 = this.b;
            if (i2 == 2) {
                decoderInputBuffer.addFlag(4);
                return -4;
            } else if ((i & 2) != 0 || i2 == 0) {
                formatHolder.format = d.this.b;
                this.b = 1;
                return -5;
            } else if (!d.this.d) {
                return -3;
            } else {
                Assertions.checkNotNull(d.this.e);
                decoderInputBuffer.addFlag(1);
                decoderInputBuffer.timeUs = 0L;
                if ((i & 4) == 0) {
                    decoderInputBuffer.ensureSpaceForWrite(d.this.f);
                    decoderInputBuffer.data.put(d.this.e, 0, d.this.f);
                }
                if ((i & 1) == 0) {
                    this.b = 2;
                }
                return -4;
            }
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j) {
            b();
            if (j <= 0 || this.b == 2) {
                return 0;
            }
            this.b = 2;
            return 1;
        }

        private void b() {
            if (!this.c) {
                d.this.k.downstreamFormatChanged(MimeTypes.getTrackType(d.this.b.sampleMimeType), d.this.b, 0, null, 0L);
                this.c = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SingleSampleMediaPeriod.java */
    /* loaded from: classes2.dex */
    public static final class b implements Loader.Loadable {
        public final long a = LoadEventInfo.getNewId();
        public final DataSpec b;
        private final StatsDataSource c;
        @Nullable
        private byte[] d;

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
        }

        public b(DataSpec dataSpec, DataSource dataSource) {
            this.b = dataSpec;
            this.c = new StatsDataSource(dataSource);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            this.c.resetBytesRead();
            try {
                this.c.open(this.b);
                int i = 0;
                while (i != -1) {
                    int bytesRead = (int) this.c.getBytesRead();
                    if (this.d == null) {
                        this.d = new byte[1024];
                    } else if (bytesRead == this.d.length) {
                        this.d = Arrays.copyOf(this.d, this.d.length * 2);
                    }
                    i = this.c.read(this.d, bytesRead, this.d.length - bytesRead);
                }
            } finally {
                Util.closeQuietly(this.c);
            }
        }
    }
}
