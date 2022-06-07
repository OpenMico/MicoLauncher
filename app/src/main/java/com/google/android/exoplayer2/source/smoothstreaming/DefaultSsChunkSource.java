package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Track;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;

/* loaded from: classes2.dex */
public class DefaultSsChunkSource implements SsChunkSource {
    private final LoaderErrorThrower a;
    private final int b;
    private final ChunkExtractor[] c;
    private final DataSource d;
    private ExoTrackSelection e;
    private SsManifest f;
    private int g;
    @Nullable
    private IOException h;

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements SsChunkSource.Factory {
        private final DataSource.Factory a;

        public Factory(DataSource.Factory factory) {
            this.a = factory;
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory
        public SsChunkSource createChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i, ExoTrackSelection exoTrackSelection, @Nullable TransferListener transferListener) {
            DataSource createDataSource = this.a.createDataSource();
            if (transferListener != null) {
                createDataSource.addTransferListener(transferListener);
            }
            return new DefaultSsChunkSource(loaderErrorThrower, ssManifest, i, exoTrackSelection, createDataSource);
        }
    }

    public DefaultSsChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i, ExoTrackSelection exoTrackSelection, DataSource dataSource) {
        this.a = loaderErrorThrower;
        this.f = ssManifest;
        this.b = i;
        this.e = exoTrackSelection;
        this.d = dataSource;
        SsManifest.StreamElement streamElement = ssManifest.streamElements[i];
        this.c = new ChunkExtractor[exoTrackSelection.length()];
        for (int i2 = 0; i2 < this.c.length; i2++) {
            int indexInTrackGroup = exoTrackSelection.getIndexInTrackGroup(i2);
            Format format = streamElement.formats[indexInTrackGroup];
            TrackEncryptionBox[] trackEncryptionBoxArr = null;
            if (format.drmInitData != null) {
                trackEncryptionBoxArr = ((SsManifest.ProtectionElement) Assertions.checkNotNull(ssManifest.protectionElement)).trackEncryptionBoxes;
            }
            this.c[i2] = new BundledChunkExtractor(new FragmentedMp4Extractor(3, null, new Track(indexInTrackGroup, streamElement.type, streamElement.timescale, C.TIME_UNSET, ssManifest.durationUs, format, 0, trackEncryptionBoxArr, streamElement.type == 2 ? 4 : 0, null, null)), streamElement.type, format);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        SsManifest.StreamElement streamElement = this.f.streamElements[this.b];
        int chunkIndex = streamElement.getChunkIndex(j);
        long startTimeUs = streamElement.getStartTimeUs(chunkIndex);
        return seekParameters.resolveSeekPositionUs(j, startTimeUs, (startTimeUs >= j || chunkIndex >= streamElement.chunkCount + (-1)) ? startTimeUs : streamElement.getStartTimeUs(chunkIndex + 1));
    }

    @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource
    public void updateManifest(SsManifest ssManifest) {
        SsManifest.StreamElement streamElement = this.f.streamElements[this.b];
        int i = streamElement.chunkCount;
        SsManifest.StreamElement streamElement2 = ssManifest.streamElements[this.b];
        if (i == 0 || streamElement2.chunkCount == 0) {
            this.g += i;
        } else {
            int i2 = i - 1;
            long startTimeUs = streamElement2.getStartTimeUs(0);
            if (streamElement.getStartTimeUs(i2) + streamElement.getChunkDurationUs(i2) <= startTimeUs) {
                this.g += i;
            } else {
                this.g += streamElement.getChunkIndex(startTimeUs);
            }
        }
        this.f = ssManifest;
    }

    @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource
    public void updateTrackSelection(ExoTrackSelection exoTrackSelection) {
        this.e = exoTrackSelection;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void maybeThrowError() throws IOException {
        IOException iOException = this.h;
        if (iOException == null) {
            this.a.maybeThrowError();
            return;
        }
        throw iOException;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public int getPreferredQueueSize(long j, List<? extends MediaChunk> list) {
        if (this.h != null || this.e.length() < 2) {
            return list.size();
        }
        return this.e.evaluateQueueSize(j, list);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long j, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.h != null) {
            return false;
        }
        return this.e.shouldCancelChunkLoad(j, chunk, list);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public final void getNextChunk(long j, long j2, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        int i;
        long j3 = j2;
        if (this.h == null) {
            SsManifest.StreamElement streamElement = this.f.streamElements[this.b];
            if (streamElement.chunkCount == 0) {
                chunkHolder.endOfStream = !this.f.isLive;
                return;
            }
            if (list.isEmpty()) {
                i = streamElement.getChunkIndex(j3);
            } else {
                i = (int) (((MediaChunk) list.get(list.size() - 1)).getNextChunkIndex() - this.g);
                if (i < 0) {
                    this.h = new BehindLiveWindowException();
                    return;
                }
            }
            if (i >= streamElement.chunkCount) {
                chunkHolder.endOfStream = !this.f.isLive;
                return;
            }
            long j4 = j3 - j;
            long a2 = a(j);
            MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[this.e.length()];
            for (int i2 = 0; i2 < mediaChunkIteratorArr.length; i2++) {
                mediaChunkIteratorArr[i2] = new a(streamElement, this.e.getIndexInTrackGroup(i2), i);
            }
            this.e.updateSelectedTrack(j, j4, a2, list, mediaChunkIteratorArr);
            long startTimeUs = streamElement.getStartTimeUs(i);
            long chunkDurationUs = startTimeUs + streamElement.getChunkDurationUs(i);
            if (!list.isEmpty()) {
                j3 = C.TIME_UNSET;
            }
            int i3 = i + this.g;
            int selectedIndex = this.e.getSelectedIndex();
            ChunkExtractor chunkExtractor = this.c[selectedIndex];
            chunkHolder.chunk = a(this.e.getSelectedFormat(), this.d, streamElement.buildRequestUri(this.e.getIndexInTrackGroup(selectedIndex), i), i3, startTimeUs, chunkDurationUs, j3, this.e.getSelectionReason(), this.e.getSelectionData(), chunkExtractor);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean z, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.e), loadErrorInfo);
        if (z && fallbackSelectionFor != null && fallbackSelectionFor.type == 2) {
            ExoTrackSelection exoTrackSelection = this.e;
            if (exoTrackSelection.blacklist(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void release() {
        for (ChunkExtractor chunkExtractor : this.c) {
            chunkExtractor.release();
        }
    }

    private static MediaChunk a(Format format, DataSource dataSource, Uri uri, int i, long j, long j2, long j3, int i2, @Nullable Object obj, ChunkExtractor chunkExtractor) {
        return new ContainerMediaChunk(dataSource, new DataSpec(uri), format, i2, obj, j, j2, j3, C.TIME_UNSET, i, 1, j, chunkExtractor);
    }

    private long a(long j) {
        if (!this.f.isLive) {
            return C.TIME_UNSET;
        }
        SsManifest.StreamElement streamElement = this.f.streamElements[this.b];
        int i = streamElement.chunkCount - 1;
        return (streamElement.getStartTimeUs(i) + streamElement.getChunkDurationUs(i)) - j;
    }

    /* loaded from: classes2.dex */
    private static final class a extends BaseMediaChunkIterator {
        private final SsManifest.StreamElement a;
        private final int b;

        public a(SsManifest.StreamElement streamElement, int i, int i2) {
            super(i2, streamElement.chunkCount - 1);
            this.a = streamElement;
            this.b = i;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            return new DataSpec(this.a.buildRequestUri(this.b, (int) getCurrentIndex()));
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.a.getStartTimeUs((int) getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            return getChunkStartTimeUs() + this.a.getChunkDurationUs((int) getCurrentIndex());
        }
    }
}
