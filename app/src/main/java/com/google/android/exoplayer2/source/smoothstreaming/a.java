package com.google.android.exoplayer2.source.smoothstreaming;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: SsMediaPeriod.java */
/* loaded from: classes2.dex */
final class a implements MediaPeriod, SequenceableLoader.Callback<ChunkSampleStream<SsChunkSource>> {
    private final SsChunkSource.Factory a;
    @Nullable
    private final TransferListener b;
    private final LoaderErrorThrower c;
    private final DrmSessionManager d;
    private final DrmSessionEventListener.EventDispatcher e;
    private final LoadErrorHandlingPolicy f;
    private final MediaSourceEventListener.EventDispatcher g;
    private final Allocator h;
    private final TrackGroupArray i;
    private final CompositeSequenceableLoaderFactory j;
    @Nullable
    private MediaPeriod.Callback k;
    private SsManifest l;
    private ChunkSampleStream<SsChunkSource>[] m = a(0);
    private SequenceableLoader n;

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    public a(SsManifest ssManifest, SsChunkSource.Factory factory, @Nullable TransferListener transferListener, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, LoaderErrorThrower loaderErrorThrower, Allocator allocator) {
        this.l = ssManifest;
        this.a = factory;
        this.b = transferListener;
        this.c = loaderErrorThrower;
        this.d = drmSessionManager;
        this.e = eventDispatcher;
        this.f = loadErrorHandlingPolicy;
        this.g = eventDispatcher2;
        this.h = allocator;
        this.j = compositeSequenceableLoaderFactory;
        this.i = a(ssManifest, drmSessionManager);
        this.n = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.m);
    }

    public void a(SsManifest ssManifest) {
        this.l = ssManifest;
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.m) {
            chunkSampleStream.getChunkSource().updateManifest(ssManifest);
        }
        this.k.onContinueLoadingRequested(this);
    }

    public void a() {
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.m) {
            chunkSampleStream.release();
        }
        this.k = null;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.k = callback;
        callback.onPrepared(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        this.c.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.i;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            if (sampleStreamArr[i] != null) {
                ChunkSampleStream chunkSampleStream = (ChunkSampleStream) sampleStreamArr[i];
                if (exoTrackSelectionArr[i] == null || !zArr[i]) {
                    chunkSampleStream.release();
                    sampleStreamArr[i] = null;
                } else {
                    ((SsChunkSource) chunkSampleStream.getChunkSource()).updateTrackSelection(exoTrackSelectionArr[i]);
                    arrayList.add(chunkSampleStream);
                }
            }
            if (sampleStreamArr[i] == null && exoTrackSelectionArr[i] != null) {
                ChunkSampleStream<SsChunkSource> a = a(exoTrackSelectionArr[i], j);
                arrayList.add(a);
                sampleStreamArr[i] = a;
                zArr2[i] = true;
            }
        }
        this.m = a(arrayList.size());
        arrayList.toArray(this.m);
        this.n = this.j.createCompositeSequenceableLoader(this.m);
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            ExoTrackSelection exoTrackSelection = list.get(i);
            int indexOf = this.i.indexOf(exoTrackSelection.getTrackGroup());
            for (int i2 = 0; i2 < exoTrackSelection.length(); i2++) {
                arrayList.add(new StreamKey(indexOf, exoTrackSelection.getIndexInTrackGroup(i2)));
            }
        }
        return arrayList;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.m) {
            chunkSampleStream.discardBuffer(j, z);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        this.n.reevaluateBuffer(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        return this.n.continueLoading(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.n.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.n.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.n.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.m) {
            chunkSampleStream.seekToUs(j);
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        ChunkSampleStream<SsChunkSource>[] chunkSampleStreamArr = this.m;
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : chunkSampleStreamArr) {
            if (chunkSampleStream.primaryTrackType == 2) {
                return chunkSampleStream.getAdjustedSeekPositionUs(j, seekParameters);
            }
        }
        return j;
    }

    /* renamed from: a */
    public void onContinueLoadingRequested(ChunkSampleStream<SsChunkSource> chunkSampleStream) {
        this.k.onContinueLoadingRequested(this);
    }

    private ChunkSampleStream<SsChunkSource> a(ExoTrackSelection exoTrackSelection, long j) {
        int indexOf = this.i.indexOf(exoTrackSelection.getTrackGroup());
        return new ChunkSampleStream<>(this.l.streamElements[indexOf].type, null, null, this.a.createChunkSource(this.c, this.l, indexOf, exoTrackSelection, this.b), this, this.h, j, this.d, this.e, this.f, this.g);
    }

    private static TrackGroupArray a(SsManifest ssManifest, DrmSessionManager drmSessionManager) {
        TrackGroup[] trackGroupArr = new TrackGroup[ssManifest.streamElements.length];
        for (int i = 0; i < ssManifest.streamElements.length; i++) {
            Format[] formatArr = ssManifest.streamElements[i].formats;
            Format[] formatArr2 = new Format[formatArr.length];
            for (int i2 = 0; i2 < formatArr.length; i2++) {
                Format format = formatArr[i2];
                formatArr2[i2] = format.copyWithExoMediaCryptoType(drmSessionManager.getExoMediaCryptoType(format));
            }
            trackGroupArr[i] = new TrackGroup(formatArr2);
        }
        return new TrackGroupArray(trackGroupArr);
    }

    private static ChunkSampleStream<SsChunkSource>[] a(int i) {
        return new ChunkSampleStream[i];
    }
}
