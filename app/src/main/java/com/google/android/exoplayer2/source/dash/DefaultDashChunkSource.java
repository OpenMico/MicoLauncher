package com.google.android.exoplayer2.source.dash;

import android.os.SystemClock;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.SingleSampleMediaChunk;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.BaseUrl;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DefaultDashChunkSource implements DashChunkSource {
    private final LoaderErrorThrower a;
    private final BaseUrlExclusionList b;
    private final int[] c;
    private final int d;
    private final DataSource e;
    private final long f;
    private final int g;
    @Nullable
    private final PlayerEmsgHandler.PlayerTrackEmsgHandler h;
    private ExoTrackSelection i;
    private DashManifest j;
    private int k;
    @Nullable
    private IOException l;
    private boolean m;
    protected final RepresentationHolder[] representationHolders;

    /* loaded from: classes2.dex */
    public static final class Factory implements DashChunkSource.Factory {
        private final DataSource.Factory a;
        private final int b;
        private final ChunkExtractor.Factory c;

        public Factory(DataSource.Factory factory) {
            this(factory, 1);
        }

        public Factory(DataSource.Factory factory, int i) {
            this(BundledChunkExtractor.FACTORY, factory, i);
        }

        public Factory(ChunkExtractor.Factory factory, DataSource.Factory factory2, int i) {
            this.c = factory;
            this.a = factory2;
            this.b = i;
        }

        @Override // com.google.android.exoplayer2.source.dash.DashChunkSource.Factory
        public DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i, int[] iArr, ExoTrackSelection exoTrackSelection, int i2, long j, boolean z, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler, @Nullable TransferListener transferListener) {
            DataSource createDataSource = this.a.createDataSource();
            if (transferListener != null) {
                createDataSource.addTransferListener(transferListener);
            }
            return new DefaultDashChunkSource(this.c, loaderErrorThrower, dashManifest, baseUrlExclusionList, i, iArr, exoTrackSelection, i2, createDataSource, j, this.b, z, list, playerTrackEmsgHandler);
        }
    }

    public DefaultDashChunkSource(ChunkExtractor.Factory factory, LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i, int[] iArr, ExoTrackSelection exoTrackSelection, int i2, DataSource dataSource, long j, int i3, boolean z, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler) {
        this.a = loaderErrorThrower;
        this.j = dashManifest;
        this.b = baseUrlExclusionList;
        this.c = iArr;
        this.i = exoTrackSelection;
        this.d = i2;
        this.e = dataSource;
        this.k = i;
        this.f = j;
        this.g = i3;
        this.h = playerTrackEmsgHandler;
        long periodDurationUs = dashManifest.getPeriodDurationUs(i);
        ArrayList<Representation> a = a();
        this.representationHolders = new RepresentationHolder[exoTrackSelection.length()];
        for (int i4 = 0; i4 < this.representationHolders.length; i4++) {
            Representation representation = a.get(exoTrackSelection.getIndexInTrackGroup(i4));
            BaseUrl selectBaseUrl = baseUrlExclusionList.selectBaseUrl(representation.baseUrls);
            RepresentationHolder[] representationHolderArr = this.representationHolders;
            if (selectBaseUrl == null) {
                selectBaseUrl = representation.baseUrls.get(0);
            }
            representationHolderArr[i4] = new RepresentationHolder(periodDurationUs, representation, selectBaseUrl, BundledChunkExtractor.FACTORY.createProgressiveMediaExtractor(i2, representation.format, z, list, playerTrackEmsgHandler), 0L, representation.getIndex());
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        RepresentationHolder[] representationHolderArr = this.representationHolders;
        for (RepresentationHolder representationHolder : representationHolderArr) {
            if (representationHolder.segmentIndex != null) {
                long segmentNum = representationHolder.getSegmentNum(j);
                long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                long segmentCount = representationHolder.getSegmentCount();
                return seekParameters.resolveSeekPositionUs(j, segmentStartTimeUs, (segmentStartTimeUs >= j || (segmentCount != -1 && segmentNum >= (representationHolder.getFirstSegmentNum() + segmentCount) - 1)) ? segmentStartTimeUs : representationHolder.getSegmentStartTimeUs(segmentNum + 1));
            }
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateManifest(DashManifest dashManifest, int i) {
        try {
            this.j = dashManifest;
            this.k = i;
            long periodDurationUs = this.j.getPeriodDurationUs(this.k);
            ArrayList<Representation> a = a();
            for (int i2 = 0; i2 < this.representationHolders.length; i2++) {
                this.representationHolders[i2] = this.representationHolders[i2].a(periodDurationUs, a.get(this.i.getIndexInTrackGroup(i2)));
            }
        } catch (BehindLiveWindowException e) {
            this.l = e;
        }
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateTrackSelection(ExoTrackSelection exoTrackSelection) {
        this.i = exoTrackSelection;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void maybeThrowError() throws IOException {
        IOException iOException = this.l;
        if (iOException == null) {
            this.a.maybeThrowError();
            return;
        }
        throw iOException;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public int getPreferredQueueSize(long j, List<? extends MediaChunk> list) {
        if (this.l != null || this.i.length() < 2) {
            return list.size();
        }
        return this.i.evaluateQueueSize(j, list);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long j, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.l != null) {
            return false;
        }
        return this.i.shouldCancelChunkLoad(j, chunk, list);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void getNextChunk(long j, long j2, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        int i;
        int i2;
        DefaultDashChunkSource defaultDashChunkSource = this;
        if (defaultDashChunkSource.l == null) {
            long j3 = j2 - j;
            long msToUs = C.msToUs(defaultDashChunkSource.j.availabilityStartTimeMs) + C.msToUs(defaultDashChunkSource.j.getPeriod(defaultDashChunkSource.k).startMs) + j2;
            PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = defaultDashChunkSource.h;
            if (playerTrackEmsgHandler == null || !playerTrackEmsgHandler.maybeRefreshManifestBeforeLoadingNextChunk(msToUs)) {
                long msToUs2 = C.msToUs(Util.getNowUnixTimeMs(defaultDashChunkSource.f));
                long a = defaultDashChunkSource.a(msToUs2);
                boolean z = true;
                MediaChunk mediaChunk = list.isEmpty() ? null : (MediaChunk) list.get(list.size() - 1);
                MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[defaultDashChunkSource.i.length()];
                int i3 = 0;
                while (i3 < mediaChunkIteratorArr.length) {
                    RepresentationHolder representationHolder = defaultDashChunkSource.representationHolders[i3];
                    if (representationHolder.segmentIndex == null) {
                        mediaChunkIteratorArr[i3] = MediaChunkIterator.EMPTY;
                        mediaChunkIteratorArr = mediaChunkIteratorArr;
                        i2 = i3;
                        msToUs2 = msToUs2;
                    } else {
                        long firstAvailableSegmentNum = representationHolder.getFirstAvailableSegmentNum(msToUs2);
                        long lastAvailableSegmentNum = representationHolder.getLastAvailableSegmentNum(msToUs2);
                        mediaChunkIteratorArr = mediaChunkIteratorArr;
                        i2 = i3;
                        msToUs2 = msToUs2;
                        long a2 = a(representationHolder, mediaChunk, j2, firstAvailableSegmentNum, lastAvailableSegmentNum);
                        if (a2 < firstAvailableSegmentNum) {
                            mediaChunkIteratorArr[i2] = MediaChunkIterator.EMPTY;
                        } else {
                            mediaChunkIteratorArr[i2] = new RepresentationSegmentIterator(representationHolder, a2, lastAvailableSegmentNum, a);
                        }
                    }
                    i3 = i2 + 1;
                    z = true;
                    defaultDashChunkSource = this;
                }
                defaultDashChunkSource.i.updateSelectedTrack(j, j3, defaultDashChunkSource.a(msToUs2, j), list, mediaChunkIteratorArr);
                RepresentationHolder a3 = defaultDashChunkSource.a(defaultDashChunkSource.i.getSelectedIndex());
                if (a3.a != null) {
                    Representation representation = a3.representation;
                    RangedUri initializationUri = a3.a.getSampleFormats() == null ? representation.getInitializationUri() : null;
                    RangedUri indexUri = a3.segmentIndex == null ? representation.getIndexUri() : null;
                    if (!(initializationUri == null && indexUri == null)) {
                        chunkHolder.chunk = newInitializationChunk(a3, defaultDashChunkSource.e, defaultDashChunkSource.i.getSelectedFormat(), defaultDashChunkSource.i.getSelectionReason(), defaultDashChunkSource.i.getSelectionData(), initializationUri, indexUri);
                        return;
                    }
                }
                long j4 = a3.b;
                long j5 = C.TIME_UNSET;
                int i4 = (j4 > C.TIME_UNSET ? 1 : (j4 == C.TIME_UNSET ? 0 : -1));
                boolean z2 = i4 != 0;
                if (a3.getSegmentCount() == 0) {
                    chunkHolder.endOfStream = z2;
                    return;
                }
                long firstAvailableSegmentNum2 = a3.getFirstAvailableSegmentNum(msToUs2);
                long lastAvailableSegmentNum2 = a3.getLastAvailableSegmentNum(msToUs2);
                long a4 = a(a3, mediaChunk, j2, firstAvailableSegmentNum2, lastAvailableSegmentNum2);
                if (a4 < firstAvailableSegmentNum2) {
                    defaultDashChunkSource.l = new BehindLiveWindowException();
                    return;
                }
                int i5 = (a4 > lastAvailableSegmentNum2 ? 1 : (a4 == lastAvailableSegmentNum2 ? 0 : -1));
                if (i5 > 0 || (defaultDashChunkSource.m && i5 >= 0)) {
                    chunkHolder.endOfStream = z2;
                } else if (!z2 || a3.getSegmentStartTimeUs(a4) < j4) {
                    int min = (int) Math.min(defaultDashChunkSource.g, (lastAvailableSegmentNum2 - a4) + 1);
                    if (i4 != 0) {
                        while (min > 1 && a3.getSegmentStartTimeUs((min + a4) - 1) >= j4) {
                            min--;
                        }
                        i = min;
                    } else {
                        i = min;
                    }
                    if (list.isEmpty()) {
                        j5 = j2;
                    }
                    chunkHolder.chunk = newMediaChunk(a3, defaultDashChunkSource.e, defaultDashChunkSource.d, defaultDashChunkSource.i.getSelectedFormat(), defaultDashChunkSource.i.getSelectionReason(), defaultDashChunkSource.i.getSelectionData(), a4, i, j5, a);
                } else {
                    chunkHolder.endOfStream = true;
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
        ChunkIndex chunkIndex;
        if (chunk instanceof InitializationChunk) {
            int indexOf = this.i.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.representationHolders[indexOf];
            if (representationHolder.segmentIndex == null && (chunkIndex = representationHolder.a.getChunkIndex()) != null) {
                this.representationHolders[indexOf] = representationHolder.a(new DashWrappingSegmentIndex(chunkIndex, representationHolder.representation.presentationTimeOffsetUs));
            }
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.h;
        if (playerTrackEmsgHandler != null) {
            playerTrackEmsgHandler.onChunkLoadCompleted(chunk);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean z, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor;
        if (!z) {
            return false;
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.h;
        if (playerTrackEmsgHandler != null && playerTrackEmsgHandler.onChunkLoadError(chunk)) {
            return true;
        }
        if (!this.j.dynamic && (chunk instanceof MediaChunk) && (loadErrorInfo.exception instanceof HttpDataSource.InvalidResponseCodeException) && ((HttpDataSource.InvalidResponseCodeException) loadErrorInfo.exception).responseCode == 404) {
            RepresentationHolder representationHolder = this.representationHolders[this.i.indexOf(chunk.trackFormat)];
            long segmentCount = representationHolder.getSegmentCount();
            if (!(segmentCount == -1 || segmentCount == 0)) {
                if (((MediaChunk) chunk).getNextChunkIndex() > (representationHolder.getFirstSegmentNum() + segmentCount) - 1) {
                    this.m = true;
                    return true;
                }
            }
        }
        RepresentationHolder representationHolder2 = this.representationHolders[this.i.indexOf(chunk.trackFormat)];
        BaseUrl selectBaseUrl = this.b.selectBaseUrl(representationHolder2.representation.baseUrls);
        if (selectBaseUrl != null && !representationHolder2.selectedBaseUrl.equals(selectBaseUrl)) {
            return true;
        }
        LoadErrorHandlingPolicy.FallbackOptions a = a(this.i, representationHolder2.representation.baseUrls);
        if ((!a.isFallbackAvailable(2) && !a.isFallbackAvailable(1)) || (fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(a, loadErrorInfo)) == null || !a.isFallbackAvailable(fallbackSelectionFor.type)) {
            return false;
        }
        if (fallbackSelectionFor.type == 2) {
            ExoTrackSelection exoTrackSelection = this.i;
            return exoTrackSelection.blacklist(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs);
        } else if (fallbackSelectionFor.type != 1) {
            return false;
        } else {
            this.b.exclude(representationHolder2.selectedBaseUrl, fallbackSelectionFor.exclusionDurationMs);
            return true;
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void release() {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            ChunkExtractor chunkExtractor = representationHolder.a;
            if (chunkExtractor != null) {
                chunkExtractor.release();
            }
        }
    }

    private LoadErrorHandlingPolicy.FallbackOptions a(ExoTrackSelection exoTrackSelection, List<BaseUrl> list) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int length = exoTrackSelection.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (exoTrackSelection.isBlacklisted(i2, elapsedRealtime)) {
                i++;
            }
        }
        int priorityCount = BaseUrlExclusionList.getPriorityCount(list);
        return new LoadErrorHandlingPolicy.FallbackOptions(priorityCount, priorityCount - this.b.getPriorityCountAfterExclusion(list), length, i);
    }

    private long a(RepresentationHolder representationHolder, @Nullable MediaChunk mediaChunk, long j, long j2, long j3) {
        if (mediaChunk != null) {
            return mediaChunk.getNextChunkIndex();
        }
        return Util.constrainValue(representationHolder.getSegmentNum(j), j2, j3);
    }

    private ArrayList<Representation> a() {
        List<AdaptationSet> list = this.j.getPeriod(this.k).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList<>();
        for (int i : this.c) {
            arrayList.addAll(list.get(i).representations);
        }
        return arrayList;
    }

    private long a(long j, long j2) {
        if (!this.j.dynamic) {
            return C.TIME_UNSET;
        }
        return Math.max(0L, Math.min(a(j), this.representationHolders[0].getSegmentEndTimeUs(this.representationHolders[0].getLastAvailableSegmentNum(j))) - j2);
    }

    private long a(long j) {
        return this.j.availabilityStartTimeMs == C.TIME_UNSET ? C.TIME_UNSET : j - C.msToUs(this.j.availabilityStartTimeMs + this.j.getPeriod(this.k).startMs);
    }

    protected Chunk newInitializationChunk(RepresentationHolder representationHolder, DataSource dataSource, Format format, int i, Object obj, @Nullable RangedUri rangedUri, @Nullable RangedUri rangedUri2) {
        RangedUri rangedUri3 = rangedUri;
        Representation representation = representationHolder.representation;
        if (rangedUri3 != null) {
            RangedUri attemptMerge = rangedUri3.attemptMerge(rangedUri2, representationHolder.selectedBaseUrl.url);
            if (attemptMerge != null) {
                rangedUri3 = attemptMerge;
            }
        } else {
            rangedUri3 = rangedUri2;
        }
        return new InitializationChunk(dataSource, DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, rangedUri3, 0), format, i, obj, representationHolder.a);
    }

    protected Chunk newMediaChunk(RepresentationHolder representationHolder, DataSource dataSource, int i, Format format, int i2, Object obj, long j, int i3, long j2, long j3) {
        Representation representation = representationHolder.representation;
        long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(j);
        RangedUri segmentUrl = representationHolder.getSegmentUrl(j);
        int i4 = 0;
        if (representationHolder.a == null) {
            long segmentEndTimeUs = representationHolder.getSegmentEndTimeUs(j);
            if (!representationHolder.isSegmentAvailableAtFullNetworkSpeed(j, j3)) {
                i4 = 8;
            }
            return new SingleSampleMediaChunk(dataSource, DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, segmentUrl, i4), format, i2, obj, segmentStartTimeUs, segmentEndTimeUs, j, i, format);
        }
        int i5 = 1;
        RangedUri rangedUri = segmentUrl;
        int i6 = 1;
        while (i5 < i3) {
            RangedUri attemptMerge = rangedUri.attemptMerge(representationHolder.getSegmentUrl(i5 + j), representationHolder.selectedBaseUrl.url);
            if (attemptMerge == null) {
                break;
            }
            i6++;
            i5++;
            rangedUri = attemptMerge;
        }
        long j4 = (i6 + j) - 1;
        long segmentEndTimeUs2 = representationHolder.getSegmentEndTimeUs(j4);
        long j5 = representationHolder.b;
        long j6 = (j5 == C.TIME_UNSET || j5 > segmentEndTimeUs2) ? -9223372036854775807L : j5;
        if (!representationHolder.isSegmentAvailableAtFullNetworkSpeed(j4, j3)) {
            i4 = 8;
        }
        return new ContainerMediaChunk(dataSource, DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, rangedUri, i4), format, i2, obj, segmentStartTimeUs, segmentEndTimeUs2, j2, j6, j, i6, -representation.presentationTimeOffsetUs, representationHolder.a);
    }

    private RepresentationHolder a(int i) {
        RepresentationHolder representationHolder = this.representationHolders[i];
        BaseUrl selectBaseUrl = this.b.selectBaseUrl(representationHolder.representation.baseUrls);
        if (selectBaseUrl == null || selectBaseUrl.equals(representationHolder.selectedBaseUrl)) {
            return representationHolder;
        }
        RepresentationHolder a = representationHolder.a(selectBaseUrl);
        this.representationHolders[i] = a;
        return a;
    }

    /* loaded from: classes2.dex */
    protected static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final RepresentationHolder a;
        private final long b;

        public RepresentationSegmentIterator(RepresentationHolder representationHolder, long j, long j2, long j3) {
            super(j, j2);
            this.a = representationHolder;
            this.b = j3;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            long currentIndex = getCurrentIndex();
            return DashUtil.buildDataSpec(this.a.representation, this.a.selectedBaseUrl.url, this.a.getSegmentUrl(currentIndex), this.a.isSegmentAvailableAtFullNetworkSpeed(currentIndex, this.b) ? 0 : 8);
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.a.getSegmentStartTimeUs(getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            checkInBounds();
            return this.a.getSegmentEndTimeUs(getCurrentIndex());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static final class RepresentationHolder {
        @Nullable
        final ChunkExtractor a;
        private final long b;
        private final long c;
        public final Representation representation;
        @Nullable
        public final DashSegmentIndex segmentIndex;
        public final BaseUrl selectedBaseUrl;

        RepresentationHolder(long j, Representation representation, BaseUrl baseUrl, @Nullable ChunkExtractor chunkExtractor, long j2, @Nullable DashSegmentIndex dashSegmentIndex) {
            this.b = j;
            this.representation = representation;
            this.selectedBaseUrl = baseUrl;
            this.c = j2;
            this.a = chunkExtractor;
            this.segmentIndex = dashSegmentIndex;
        }

        @CheckResult
        RepresentationHolder a(long j, Representation representation) throws BehindLiveWindowException {
            long j2;
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = representation.getIndex();
            if (index == null) {
                return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.a, this.c, index);
            }
            if (!index.isExplicit()) {
                return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.a, this.c, index2);
            }
            long segmentCount = index.getSegmentCount(j);
            if (segmentCount == 0) {
                return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.a, this.c, index2);
            }
            long firstSegmentNum = index.getFirstSegmentNum();
            long timeUs = index.getTimeUs(firstSegmentNum);
            long j3 = (segmentCount + firstSegmentNum) - 1;
            long firstSegmentNum2 = index2.getFirstSegmentNum();
            long timeUs2 = index2.getTimeUs(firstSegmentNum2);
            long j4 = this.c;
            int i = ((index.getTimeUs(j3) + index.getDurationUs(j3, j)) > timeUs2 ? 1 : ((index.getTimeUs(j3) + index.getDurationUs(j3, j)) == timeUs2 ? 0 : -1));
            if (i == 0) {
                j2 = j4 + ((j3 + 1) - firstSegmentNum2);
            } else if (i < 0) {
                throw new BehindLiveWindowException();
            } else if (timeUs2 < timeUs) {
                j2 = j4 - (index2.getSegmentNum(timeUs, j) - firstSegmentNum);
            } else {
                j2 = j4 + (index.getSegmentNum(timeUs2, j) - firstSegmentNum2);
            }
            return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.a, j2, index2);
        }

        @CheckResult
        RepresentationHolder a(DashSegmentIndex dashSegmentIndex) {
            return new RepresentationHolder(this.b, this.representation, this.selectedBaseUrl, this.a, this.c, dashSegmentIndex);
        }

        @CheckResult
        RepresentationHolder a(BaseUrl baseUrl) {
            return new RepresentationHolder(this.b, this.representation, baseUrl, this.a, this.c, this.segmentIndex);
        }

        public long getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.c;
        }

        public long getFirstAvailableSegmentNum(long j) {
            return this.segmentIndex.getFirstAvailableSegmentNum(this.b, j) + this.c;
        }

        public long getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.b);
        }

        public long getSegmentStartTimeUs(long j) {
            return this.segmentIndex.getTimeUs(j - this.c);
        }

        public long getSegmentEndTimeUs(long j) {
            return getSegmentStartTimeUs(j) + this.segmentIndex.getDurationUs(j - this.c, this.b);
        }

        public long getSegmentNum(long j) {
            return this.segmentIndex.getSegmentNum(j, this.b) + this.c;
        }

        public RangedUri getSegmentUrl(long j) {
            return this.segmentIndex.getSegmentUrl(j - this.c);
        }

        public long getLastAvailableSegmentNum(long j) {
            return (getFirstAvailableSegmentNum(j) + this.segmentIndex.getAvailableSegmentCount(this.b, j)) - 1;
        }

        public boolean isSegmentAvailableAtFullNetworkSpeed(long j, long j2) {
            return this.segmentIndex.isExplicit() || j2 == C.TIME_UNSET || getSegmentEndTimeUs(j) <= j2;
        }
    }
}
