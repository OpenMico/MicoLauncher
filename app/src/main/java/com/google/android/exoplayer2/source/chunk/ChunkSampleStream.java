package com.google.android.exoplayer2.source.chunk;

import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.chunk.ChunkSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class ChunkSampleStream<T extends ChunkSource> implements SampleStream, SequenceableLoader, Loader.Callback<Chunk>, Loader.ReleaseCallback {
    boolean a;
    private final int[] b;
    private final Format[] c;
    private final boolean[] d;
    private final T e;
    private final SequenceableLoader.Callback<ChunkSampleStream<T>> f;
    private final MediaSourceEventListener.EventDispatcher g;
    private final LoadErrorHandlingPolicy h;
    private final Loader i;
    private final ChunkHolder j;
    private final ArrayList<BaseMediaChunk> k;
    private final List<BaseMediaChunk> l;
    private final SampleQueue m;
    private final SampleQueue[] n;
    private final BaseMediaChunkOutput o;
    @Nullable
    private Chunk p;
    public final int primaryTrackType;
    private Format q;
    @Nullable
    private ReleaseCallback<T> r;
    private long s;
    private long t;
    private int u;
    @Nullable
    private BaseMediaChunk v;

    /* loaded from: classes2.dex */
    public interface ReleaseCallback<T extends ChunkSource> {
        void onSampleStreamReleased(ChunkSampleStream<T> chunkSampleStream);
    }

    public ChunkSampleStream(int i, @Nullable int[] iArr, @Nullable Format[] formatArr, T t, SequenceableLoader.Callback<ChunkSampleStream<T>> callback, Allocator allocator, long j, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2) {
        this.primaryTrackType = i;
        int i2 = 0;
        this.b = iArr == null ? new int[0] : iArr;
        this.c = formatArr == null ? new Format[0] : formatArr;
        this.e = t;
        this.f = callback;
        this.g = eventDispatcher2;
        this.h = loadErrorHandlingPolicy;
        this.i = new Loader("ChunkSampleStream");
        this.j = new ChunkHolder();
        this.k = new ArrayList<>();
        this.l = Collections.unmodifiableList(this.k);
        int length = this.b.length;
        this.n = new SampleQueue[length];
        this.d = new boolean[length];
        int i3 = length + 1;
        int[] iArr2 = new int[i3];
        SampleQueue[] sampleQueueArr = new SampleQueue[i3];
        this.m = SampleQueue.createWithDrm(allocator, (Looper) Assertions.checkNotNull(Looper.myLooper()), drmSessionManager, eventDispatcher);
        iArr2[0] = i;
        sampleQueueArr[0] = this.m;
        while (i2 < length) {
            SampleQueue createWithoutDrm = SampleQueue.createWithoutDrm(allocator);
            this.n[i2] = createWithoutDrm;
            int i4 = i2 + 1;
            sampleQueueArr[i4] = createWithoutDrm;
            iArr2[i4] = this.b[i2];
            i2 = i4;
        }
        this.o = new BaseMediaChunkOutput(iArr2, sampleQueueArr);
        this.s = j;
        this.t = j;
    }

    public void discardBuffer(long j, boolean z) {
        if (!a()) {
            int firstIndex = this.m.getFirstIndex();
            this.m.discardTo(j, z, true);
            int firstIndex2 = this.m.getFirstIndex();
            if (firstIndex2 > firstIndex) {
                long firstTimestampUs = this.m.getFirstTimestampUs();
                int i = 0;
                while (true) {
                    SampleQueue[] sampleQueueArr = this.n;
                    if (i >= sampleQueueArr.length) {
                        break;
                    }
                    sampleQueueArr[i].discardTo(firstTimestampUs, z, this.d[i]);
                    i++;
                }
            }
            c(firstIndex2);
        }
    }

    public ChunkSampleStream<T>.EmbeddedSampleStream selectEmbeddedTrack(long j, int i) {
        for (int i2 = 0; i2 < this.n.length; i2++) {
            if (this.b[i2] == i) {
                Assertions.checkState(!this.d[i2]);
                this.d[i2] = true;
                this.n[i2].seekTo(j, true);
                return new EmbeddedSampleStream(this, this.n[i2], i2);
            }
        }
        throw new IllegalStateException();
    }

    public T getChunkSource() {
        return this.e;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        if (this.a) {
            return Long.MIN_VALUE;
        }
        if (a()) {
            return this.s;
        }
        long j = this.t;
        BaseMediaChunk d = d();
        if (!d.isLoadCompleted()) {
            if (this.k.size() > 1) {
                ArrayList<BaseMediaChunk> arrayList = this.k;
                d = arrayList.get(arrayList.size() - 2);
            } else {
                d = null;
            }
        }
        if (d != null) {
            j = Math.max(j, d.endTimeUs);
        }
        return Math.max(j, this.m.getLargestQueuedTimestampUs());
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return this.e.getAdjustedSeekPositionUs(j, seekParameters);
    }

    public void seekToUs(long j) {
        boolean z;
        this.t = j;
        if (a()) {
            this.s = j;
            return;
        }
        BaseMediaChunk baseMediaChunk = null;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= this.k.size()) {
                break;
            }
            BaseMediaChunk baseMediaChunk2 = this.k.get(i2);
            int i3 = (baseMediaChunk2.startTimeUs > j ? 1 : (baseMediaChunk2.startTimeUs == j ? 0 : -1));
            if (i3 == 0 && baseMediaChunk2.clippedStartTimeUs == C.TIME_UNSET) {
                baseMediaChunk = baseMediaChunk2;
                break;
            } else if (i3 > 0) {
                break;
            } else {
                i2++;
            }
        }
        if (baseMediaChunk != null) {
            z = this.m.seekTo(baseMediaChunk.getFirstSampleIndex(0));
        } else {
            z = this.m.seekTo(j, j < getNextLoadPositionUs());
        }
        if (z) {
            this.u = a(this.m.getReadIndex(), 0);
            SampleQueue[] sampleQueueArr = this.n;
            int length = sampleQueueArr.length;
            while (i < length) {
                sampleQueueArr[i].seekTo(j, true);
                i++;
            }
            return;
        }
        this.s = j;
        this.a = false;
        this.k.clear();
        this.u = 0;
        if (this.i.isLoading()) {
            this.m.discardToEnd();
            SampleQueue[] sampleQueueArr2 = this.n;
            int length2 = sampleQueueArr2.length;
            while (i < length2) {
                sampleQueueArr2[i].discardToEnd();
                i++;
            }
            this.i.cancelLoading();
            return;
        }
        this.i.clearFatalError();
        b();
    }

    public void release() {
        release(null);
    }

    public void release(@Nullable ReleaseCallback<T> releaseCallback) {
        this.r = releaseCallback;
        this.m.preRelease();
        for (SampleQueue sampleQueue : this.n) {
            sampleQueue.preRelease();
        }
        this.i.release(this);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.ReleaseCallback
    public void onLoaderReleased() {
        this.m.release();
        for (SampleQueue sampleQueue : this.n) {
            sampleQueue.release();
        }
        this.e.release();
        ReleaseCallback<T> releaseCallback = this.r;
        if (releaseCallback != null) {
            releaseCallback.onSampleStreamReleased(this);
        }
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public boolean isReady() {
        return !a() && this.m.isReady(this.a);
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public void maybeThrowError() throws IOException {
        this.i.maybeThrowError();
        this.m.maybeThrowError();
        if (!this.i.isLoading()) {
            this.e.maybeThrowError();
        }
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
        if (a()) {
            return -3;
        }
        BaseMediaChunk baseMediaChunk = this.v;
        if (baseMediaChunk != null && baseMediaChunk.getFirstSampleIndex(0) <= this.m.getReadIndex()) {
            return -3;
        }
        c();
        return this.m.read(formatHolder, decoderInputBuffer, i, this.a);
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int skipData(long j) {
        if (a()) {
            return 0;
        }
        int skipCount = this.m.getSkipCount(j, this.a);
        BaseMediaChunk baseMediaChunk = this.v;
        if (baseMediaChunk != null) {
            skipCount = Math.min(skipCount, baseMediaChunk.getFirstSampleIndex(0) - this.m.getReadIndex());
        }
        this.m.skip(skipCount);
        c();
        return skipCount;
    }

    public void onLoadCompleted(Chunk chunk, long j, long j2) {
        this.p = null;
        this.e.onChunkLoadCompleted(chunk);
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j, j2, chunk.bytesLoaded());
        this.h.onLoadTaskConcluded(chunk.loadTaskId);
        this.g.loadCompleted(loadEventInfo, chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
        this.f.onContinueLoadingRequested(this);
    }

    public void onLoadCanceled(Chunk chunk, long j, long j2, boolean z) {
        this.p = null;
        this.v = null;
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j, j2, chunk.bytesLoaded());
        this.h.onLoadTaskConcluded(chunk.loadTaskId);
        this.g.loadCanceled(loadEventInfo, chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
        if (!z) {
            if (a()) {
                b();
            } else if (a(chunk)) {
                e(this.k.size() - 1);
                if (this.k.isEmpty()) {
                    this.s = this.t;
                }
            }
            this.f.onContinueLoadingRequested(this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.exoplayer2.upstream.Loader.LoadErrorAction onLoadError(com.google.android.exoplayer2.source.chunk.Chunk r31, long r32, long r34, java.io.IOException r36, int r37) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.chunk.ChunkSampleStream.onLoadError(com.google.android.exoplayer2.source.chunk.Chunk, long, long, java.io.IOException, int):com.google.android.exoplayer2.upstream.Loader$LoadErrorAction");
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        List<BaseMediaChunk> list;
        long j2;
        if (this.a || this.i.isLoading() || this.i.hasFatalError()) {
            return false;
        }
        boolean a = a();
        if (a) {
            list = Collections.emptyList();
            j2 = this.s;
        } else {
            List<BaseMediaChunk> list2 = this.l;
            j2 = d().endTimeUs;
            list = list2;
        }
        this.e.getNextChunk(j, j2, list, this.j);
        boolean z = this.j.endOfStream;
        Chunk chunk = this.j.chunk;
        this.j.clear();
        if (z) {
            this.s = C.TIME_UNSET;
            this.a = true;
            return true;
        } else if (chunk == null) {
            return false;
        } else {
            this.p = chunk;
            if (a(chunk)) {
                BaseMediaChunk baseMediaChunk = (BaseMediaChunk) chunk;
                if (a) {
                    long j3 = baseMediaChunk.startTimeUs;
                    long j4 = this.s;
                    if (j3 != j4) {
                        this.m.setStartTimeUs(j4);
                        for (SampleQueue sampleQueue : this.n) {
                            sampleQueue.setStartTimeUs(this.s);
                        }
                    }
                    this.s = C.TIME_UNSET;
                }
                baseMediaChunk.init(this.o);
                this.k.add(baseMediaChunk);
            } else if (chunk instanceof InitializationChunk) {
                ((InitializationChunk) chunk).init(this.o);
            }
            this.g.loadStarted(new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, this.i.startLoading(chunk, this, this.h.getMinimumLoadableRetryCount(chunk.type))), chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
            return true;
        }
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.i.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        if (a()) {
            return this.s;
        }
        if (this.a) {
            return Long.MIN_VALUE;
        }
        return d().endTimeUs;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        if (!this.i.hasFatalError() && !a()) {
            if (this.i.isLoading()) {
                Chunk chunk = (Chunk) Assertions.checkNotNull(this.p);
                if ((!a(chunk) || !b(this.k.size() - 1)) && this.e.shouldCancelLoad(j, chunk, this.l)) {
                    this.i.cancelLoading();
                    if (a(chunk)) {
                        this.v = (BaseMediaChunk) chunk;
                        return;
                    }
                    return;
                }
                return;
            }
            int preferredQueueSize = this.e.getPreferredQueueSize(j, this.l);
            if (preferredQueueSize < this.k.size()) {
                a(preferredQueueSize);
            }
        }
    }

    private void a(int i) {
        Assertions.checkState(!this.i.isLoading());
        int size = this.k.size();
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (!b(i)) {
                break;
            } else {
                i++;
            }
        }
        if (i != -1) {
            long j = d().endTimeUs;
            BaseMediaChunk e = e(i);
            if (this.k.isEmpty()) {
                this.s = this.t;
            }
            this.a = false;
            this.g.upstreamDiscarded(this.primaryTrackType, e.startTimeUs, j);
        }
    }

    private boolean a(Chunk chunk) {
        return chunk instanceof BaseMediaChunk;
    }

    private void b() {
        this.m.reset();
        for (SampleQueue sampleQueue : this.n) {
            sampleQueue.reset();
        }
    }

    private boolean b(int i) {
        int readIndex;
        BaseMediaChunk baseMediaChunk = this.k.get(i);
        if (this.m.getReadIndex() > baseMediaChunk.getFirstSampleIndex(0)) {
            return true;
        }
        int i2 = 0;
        do {
            SampleQueue[] sampleQueueArr = this.n;
            if (i2 >= sampleQueueArr.length) {
                return false;
            }
            readIndex = sampleQueueArr[i2].getReadIndex();
            i2++;
        } while (readIndex <= baseMediaChunk.getFirstSampleIndex(i2));
        return true;
    }

    boolean a() {
        return this.s != C.TIME_UNSET;
    }

    private void c(int i) {
        int min = Math.min(a(i, 0), this.u);
        if (min > 0) {
            Util.removeRange(this.k, 0, min);
            this.u -= min;
        }
    }

    private void c() {
        int a = a(this.m.getReadIndex(), this.u - 1);
        while (true) {
            int i = this.u;
            if (i <= a) {
                this.u = i + 1;
                d(i);
            } else {
                return;
            }
        }
    }

    private void d(int i) {
        BaseMediaChunk baseMediaChunk = this.k.get(i);
        Format format = baseMediaChunk.trackFormat;
        if (!format.equals(this.q)) {
            this.g.downstreamFormatChanged(this.primaryTrackType, format, baseMediaChunk.trackSelectionReason, baseMediaChunk.trackSelectionData, baseMediaChunk.startTimeUs);
        }
        this.q = format;
    }

    private int a(int i, int i2) {
        do {
            i2++;
            if (i2 >= this.k.size()) {
                return this.k.size() - 1;
            }
        } while (this.k.get(i2).getFirstSampleIndex(0) <= i);
        return i2 - 1;
    }

    private BaseMediaChunk d() {
        ArrayList<BaseMediaChunk> arrayList = this.k;
        return arrayList.get(arrayList.size() - 1);
    }

    private BaseMediaChunk e(int i) {
        BaseMediaChunk baseMediaChunk = this.k.get(i);
        ArrayList<BaseMediaChunk> arrayList = this.k;
        Util.removeRange(arrayList, i, arrayList.size());
        this.u = Math.max(this.u, this.k.size());
        int i2 = 0;
        this.m.discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(0));
        while (true) {
            SampleQueue[] sampleQueueArr = this.n;
            if (i2 >= sampleQueueArr.length) {
                return baseMediaChunk;
            }
            SampleQueue sampleQueue = sampleQueueArr[i2];
            i2++;
            sampleQueue.discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(i2));
        }
    }

    /* loaded from: classes2.dex */
    public final class EmbeddedSampleStream implements SampleStream {
        private final SampleQueue b;
        private final int c;
        private boolean d;
        public final ChunkSampleStream<T> parent;

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() {
        }

        public EmbeddedSampleStream(ChunkSampleStream<T> chunkSampleStream, SampleQueue sampleQueue, int i) {
            this.parent = chunkSampleStream;
            this.b = sampleQueue;
            this.c = i;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return !ChunkSampleStream.this.a() && this.b.isReady(ChunkSampleStream.this.a);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j) {
            if (ChunkSampleStream.this.a()) {
                return 0;
            }
            int skipCount = this.b.getSkipCount(j, ChunkSampleStream.this.a);
            if (ChunkSampleStream.this.v != null) {
                skipCount = Math.min(skipCount, ChunkSampleStream.this.v.getFirstSampleIndex(this.c + 1) - this.b.getReadIndex());
            }
            this.b.skip(skipCount);
            if (skipCount > 0) {
                a();
            }
            return skipCount;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            if (ChunkSampleStream.this.a()) {
                return -3;
            }
            if (ChunkSampleStream.this.v != null && ChunkSampleStream.this.v.getFirstSampleIndex(this.c + 1) <= this.b.getReadIndex()) {
                return -3;
            }
            a();
            return this.b.read(formatHolder, decoderInputBuffer, i, ChunkSampleStream.this.a);
        }

        public void release() {
            Assertions.checkState(ChunkSampleStream.this.d[this.c]);
            ChunkSampleStream.this.d[this.c] = false;
        }

        private void a() {
            if (!this.d) {
                ChunkSampleStream.this.g.downstreamFormatChanged(ChunkSampleStream.this.b[this.c], ChunkSampleStream.this.c[this.c], 0, null, ChunkSampleStream.this.t);
                this.d = true;
            }
        }
    }
}
