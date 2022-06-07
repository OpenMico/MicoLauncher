package com.google.android.exoplayer2.source.ads;

import android.os.Handler;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class ServerSideInsertedAdsMediaSource extends BaseMediaSource implements DrmSessionEventListener, MediaSource.MediaSourceCaller, MediaSourceEventListener {
    private final MediaSource a;
    @Nullable
    @GuardedBy("this")
    private Handler e;
    @Nullable
    private d f;
    @Nullable
    private Timeline g;
    private final ListMultimap<Long, d> b = ArrayListMultimap.create();
    private AdPlaybackState h = AdPlaybackState.NONE;
    private final MediaSourceEventListener.EventDispatcher c = createEventDispatcher(null);
    private final DrmSessionEventListener.EventDispatcher d = createDrmEventDispatcher(null);

    public ServerSideInsertedAdsMediaSource(MediaSource mediaSource) {
        this.a = mediaSource;
    }

    public void setAdPlaybackState(final AdPlaybackState adPlaybackState) {
        Assertions.checkArgument(adPlaybackState.adGroupCount >= this.h.adGroupCount);
        for (int i = adPlaybackState.removedAdGroupCount; i < adPlaybackState.adGroupCount; i++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
            Assertions.checkArgument(adGroup.isServerSideInserted);
            if (i < this.h.adGroupCount) {
                Assertions.checkArgument(ServerSideInsertedAdsUtil.getAdCountInGroup(adPlaybackState, i) >= ServerSideInsertedAdsUtil.getAdCountInGroup(this.h, i));
            }
            if (adGroup.timeUs == Long.MIN_VALUE) {
                Assertions.checkArgument(ServerSideInsertedAdsUtil.getAdCountInGroup(adPlaybackState, i) == 0);
            }
        }
        synchronized (this) {
            if (this.e == null) {
                this.h = adPlaybackState;
            } else {
                this.e.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.-$$Lambda$ServerSideInsertedAdsMediaSource$MeXAL4dNUT8F4dBp_t6HdAGxI80
                    @Override // java.lang.Runnable
                    public final void run() {
                        ServerSideInsertedAdsMediaSource.this.a(adPlaybackState);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AdPlaybackState adPlaybackState) {
        for (d dVar : this.b.values()) {
            dVar.a(adPlaybackState);
        }
        d dVar2 = this.f;
        if (dVar2 != null) {
            dVar2.a(adPlaybackState);
        }
        this.h = adPlaybackState;
        Timeline timeline = this.g;
        if (timeline != null) {
            refreshSourceInfo(new c(timeline, adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.a.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        Handler createHandlerForCurrentLooper = Util.createHandlerForCurrentLooper();
        synchronized (this) {
            this.e = createHandlerForCurrentLooper;
        }
        this.a.addEventListener(createHandlerForCurrentLooper, this);
        this.a.addDrmEventListener(createHandlerForCurrentLooper, this);
        this.a.prepareSource(this, transferListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.a.maybeThrowSourceInfoRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void enableInternal() {
        this.a.enable(this);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void disableInternal() {
        a();
        this.a.disable(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
    public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
        this.g = timeline;
        if (!AdPlaybackState.NONE.equals(this.h)) {
            refreshSourceInfo(new c(timeline, this.h));
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        a();
        this.g = null;
        synchronized (this) {
            this.e = null;
        }
        this.a.releaseSource(this);
        this.a.removeEventListener(this);
        this.a.removeDrmEventListener(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        d dVar = this.f;
        if (dVar != null) {
            this.f = null;
            this.b.put(Long.valueOf(mediaPeriodId.windowSequenceNumber), dVar);
        } else {
            dVar = (d) Iterables.getLast(this.b.get((ListMultimap<Long, d>) Long.valueOf(mediaPeriodId.windowSequenceNumber)), null);
            if (dVar == null || !dVar.a(mediaPeriodId, j)) {
                dVar = new d(this.a.createPeriod(new MediaSource.MediaPeriodId(mediaPeriodId.periodUid, mediaPeriodId.windowSequenceNumber), allocator, ServerSideInsertedAdsUtil.getStreamPositionUs(j, mediaPeriodId, this.h)), this.h);
                this.b.put(Long.valueOf(mediaPeriodId.windowSequenceNumber), dVar);
            }
        }
        a aVar = new a(dVar, mediaPeriodId, createEventDispatcher(mediaPeriodId), createDrmEventDispatcher(mediaPeriodId));
        dVar.a(aVar);
        return aVar;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        a aVar = (a) mediaPeriod;
        aVar.a.b(aVar);
        if (aVar.a.a()) {
            this.b.remove(Long.valueOf(aVar.b.windowSequenceNumber), aVar.a);
            if (this.b.isEmpty()) {
                this.f = aVar.a;
            } else {
                aVar.a.a(this.a);
            }
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i2) {
        a a2 = a(mediaPeriodId, (MediaLoadData) null, true);
        if (a2 == null) {
            this.d.drmSessionAcquired(i2);
        } else {
            a2.d.drmSessionAcquired(i2);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        a a2 = a(mediaPeriodId, (MediaLoadData) null, false);
        if (a2 == null) {
            this.d.drmKeysLoaded();
        } else {
            a2.d.drmKeysLoaded();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
        a a2 = a(mediaPeriodId, (MediaLoadData) null, false);
        if (a2 == null) {
            this.d.drmSessionManagerError(exc);
        } else {
            a2.d.drmSessionManagerError(exc);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        a a2 = a(mediaPeriodId, (MediaLoadData) null, false);
        if (a2 == null) {
            this.d.drmKeysRestored();
        } else {
            a2.d.drmKeysRestored();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        a a2 = a(mediaPeriodId, (MediaLoadData) null, false);
        if (a2 == null) {
            this.d.drmKeysRemoved();
        } else {
            a2.d.drmKeysRemoved();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        a a2 = a(mediaPeriodId, (MediaLoadData) null, false);
        if (a2 == null) {
            this.d.drmSessionReleased();
        } else {
            a2.d.drmSessionReleased();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        a a2 = a(mediaPeriodId, mediaLoadData, true);
        if (a2 == null) {
            this.c.loadStarted(loadEventInfo, mediaLoadData);
            return;
        }
        a2.a.a(loadEventInfo, mediaLoadData);
        a2.c.loadStarted(loadEventInfo, b(a2, mediaLoadData, this.h));
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        a a2 = a(mediaPeriodId, mediaLoadData, true);
        if (a2 == null) {
            this.c.loadCompleted(loadEventInfo, mediaLoadData);
            return;
        }
        a2.a.a(loadEventInfo);
        a2.c.loadCompleted(loadEventInfo, b(a2, mediaLoadData, this.h));
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadCanceled(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        a a2 = a(mediaPeriodId, mediaLoadData, true);
        if (a2 == null) {
            this.c.loadCanceled(loadEventInfo, mediaLoadData);
            return;
        }
        a2.a.a(loadEventInfo);
        a2.c.loadCanceled(loadEventInfo, b(a2, mediaLoadData, this.h));
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        a a2 = a(mediaPeriodId, mediaLoadData, true);
        if (a2 == null) {
            this.c.loadError(loadEventInfo, mediaLoadData, iOException, z);
            return;
        }
        if (z) {
            a2.a.a(loadEventInfo);
        }
        a2.c.loadError(loadEventInfo, b(a2, mediaLoadData, this.h), iOException, z);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onUpstreamDiscarded(int i, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        a a2 = a(mediaPeriodId, mediaLoadData, false);
        if (a2 == null) {
            this.c.upstreamDiscarded(mediaLoadData);
        } else {
            a2.c.upstreamDiscarded(b(a2, mediaLoadData, this.h));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        a a2 = a(mediaPeriodId, mediaLoadData, false);
        if (a2 == null) {
            this.c.downstreamFormatChanged(mediaLoadData);
            return;
        }
        a2.a.a(a2, mediaLoadData);
        a2.c.downstreamFormatChanged(b(a2, mediaLoadData, this.h));
    }

    private void a() {
        d dVar = this.f;
        if (dVar != null) {
            dVar.a(this.a);
            this.f = null;
        }
    }

    @Nullable
    private a a(@Nullable MediaSource.MediaPeriodId mediaPeriodId, @Nullable MediaLoadData mediaLoadData, boolean z) {
        if (mediaPeriodId == null) {
            return null;
        }
        List<d> list = this.b.get((ListMultimap<Long, d>) Long.valueOf(mediaPeriodId.windowSequenceNumber));
        if (list.isEmpty()) {
            return null;
        }
        if (z) {
            d dVar = (d) Iterables.getLast(list);
            return dVar.h != null ? dVar.h : (a) Iterables.getLast(dVar.e);
        }
        for (int i = 0; i < list.size(); i++) {
            a a2 = list.get(i).a(mediaLoadData);
            if (a2 != null) {
                return a2;
            }
        }
        return (a) list.get(0).e.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long b(a aVar, AdPlaybackState adPlaybackState) {
        MediaSource.MediaPeriodId mediaPeriodId = aVar.b;
        if (mediaPeriodId.isAd()) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(mediaPeriodId.adGroupIndex);
            if (adGroup.count == -1) {
                return 0L;
            }
            return adGroup.durationsUs[mediaPeriodId.adIndexInAdGroup];
        } else if (mediaPeriodId.nextAdGroupIndex == -1) {
            return Long.MAX_VALUE;
        } else {
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(mediaPeriodId.nextAdGroupIndex);
            if (adGroup2.timeUs == Long.MIN_VALUE) {
                return Long.MAX_VALUE;
            }
            return adGroup2.timeUs;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaLoadData b(a aVar, MediaLoadData mediaLoadData, AdPlaybackState adPlaybackState) {
        return new MediaLoadData(mediaLoadData.dataType, mediaLoadData.trackType, mediaLoadData.trackFormat, mediaLoadData.trackSelectionReason, mediaLoadData.trackSelectionData, a(mediaLoadData.mediaStartTimeMs, aVar, adPlaybackState), a(mediaLoadData.mediaEndTimeMs, aVar, adPlaybackState));
    }

    private static long a(long j, a aVar, AdPlaybackState adPlaybackState) {
        long j2;
        if (j == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long msToUs = C.msToUs(j);
        MediaSource.MediaPeriodId mediaPeriodId = aVar.b;
        if (mediaPeriodId.isAd()) {
            j2 = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForAd(msToUs, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        } else {
            j2 = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(msToUs, -1, adPlaybackState);
        }
        return C.usToMs(j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class d implements MediaPeriod.Callback {
        private final MediaPeriod d;
        private AdPlaybackState g;
        @Nullable
        private a h;
        private boolean i;
        private boolean j;
        private final List<a> e = new ArrayList();
        private final Map<Long, Pair<LoadEventInfo, MediaLoadData>> f = new HashMap();
        public ExoTrackSelection[] a = new ExoTrackSelection[0];
        public SampleStream[] b = new SampleStream[0];
        public MediaLoadData[] c = new MediaLoadData[0];

        public d(MediaPeriod mediaPeriod, AdPlaybackState adPlaybackState) {
            this.d = mediaPeriod;
            this.g = adPlaybackState;
        }

        public void a(AdPlaybackState adPlaybackState) {
            this.g = adPlaybackState;
        }

        public void a(a aVar) {
            this.e.add(aVar);
        }

        public void b(a aVar) {
            if (aVar.equals(this.h)) {
                this.h = null;
                this.f.clear();
            }
            this.e.remove(aVar);
        }

        public boolean a() {
            return this.e.isEmpty();
        }

        public void a(MediaSource mediaSource) {
            mediaSource.releasePeriod(this.d);
        }

        public boolean a(MediaSource.MediaPeriodId mediaPeriodId, long j) {
            a aVar = (a) Iterables.getLast(this.e);
            return ServerSideInsertedAdsUtil.getStreamPositionUs(j, mediaPeriodId, this.g) == ServerSideInsertedAdsUtil.getStreamPositionUs(ServerSideInsertedAdsMediaSource.b(aVar, this.g), aVar.b, this.g);
        }

        @Nullable
        public a a(@Nullable MediaLoadData mediaLoadData) {
            if (mediaLoadData == null || mediaLoadData.mediaStartTimeMs == C.TIME_UNSET) {
                return null;
            }
            for (int i = 0; i < this.e.size(); i++) {
                a aVar = this.e.get(i);
                long mediaPeriodPositionUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(C.msToUs(mediaLoadData.mediaStartTimeMs), aVar.b, this.g);
                long b = ServerSideInsertedAdsMediaSource.b(aVar, this.g);
                if (mediaPeriodPositionUs >= 0 && mediaPeriodPositionUs < b) {
                    return aVar;
                }
            }
            return null;
        }

        public void a(a aVar, long j) {
            aVar.f = j;
            if (!this.i) {
                this.i = true;
                this.d.prepare(this, ServerSideInsertedAdsUtil.getStreamPositionUs(j, aVar.b, this.g));
            } else if (this.j) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(aVar.e)).onPrepared(aVar);
            }
        }

        public void b() throws IOException {
            this.d.maybeThrowPrepareError();
        }

        public TrackGroupArray c() {
            return this.d.getTrackGroups();
        }

        public List<StreamKey> a(List<ExoTrackSelection> list) {
            return this.d.getStreamKeys(list);
        }

        public boolean b(a aVar, long j) {
            a aVar2 = this.h;
            if (aVar2 != null && !aVar.equals(aVar2)) {
                for (Pair<LoadEventInfo, MediaLoadData> pair : this.f.values()) {
                    aVar2.c.loadCompleted((LoadEventInfo) pair.first, ServerSideInsertedAdsMediaSource.b(aVar2, (MediaLoadData) pair.second, this.g));
                    aVar.c.loadStarted((LoadEventInfo) pair.first, ServerSideInsertedAdsMediaSource.b(aVar, (MediaLoadData) pair.second, this.g));
                }
            }
            this.h = aVar;
            return this.d.continueLoading(e(aVar, j));
        }

        public boolean c(a aVar) {
            return aVar.equals(this.h) && this.d.isLoading();
        }

        public long d(a aVar) {
            return f(aVar, this.d.getBufferedPositionUs());
        }

        public long e(a aVar) {
            return f(aVar, this.d.getNextLoadPositionUs());
        }

        public long c(a aVar, long j) {
            return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(this.d.seekToUs(ServerSideInsertedAdsUtil.getStreamPositionUs(j, aVar.b, this.g)), aVar.b, this.g);
        }

        public long a(a aVar, long j, SeekParameters seekParameters) {
            return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(this.d.getAdjustedSeekPositionUs(ServerSideInsertedAdsUtil.getStreamPositionUs(j, aVar.b, this.g), seekParameters), aVar.b, this.g);
        }

        public void a(a aVar, long j, boolean z) {
            this.d.discardBuffer(ServerSideInsertedAdsUtil.getStreamPositionUs(j, aVar.b, this.g), z);
        }

        public void d(a aVar, long j) {
            this.d.reevaluateBuffer(e(aVar, j));
        }

        public long f(a aVar) {
            if (!aVar.equals(this.e.get(0))) {
                return C.TIME_UNSET;
            }
            long readDiscontinuity = this.d.readDiscontinuity();
            return readDiscontinuity == C.TIME_UNSET ? C.TIME_UNSET : ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(readDiscontinuity, aVar.b, this.g);
        }

        public long a(a aVar, ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
            SampleStream sampleStream;
            SampleStream[] sampleStreamArr2;
            aVar.f = j;
            if (aVar.equals(this.e.get(0))) {
                this.a = (ExoTrackSelection[]) Arrays.copyOf(exoTrackSelectionArr, exoTrackSelectionArr.length);
                long streamPositionUs = ServerSideInsertedAdsUtil.getStreamPositionUs(j, aVar.b, this.g);
                SampleStream[] sampleStreamArr3 = this.b;
                if (sampleStreamArr3.length == 0) {
                    sampleStreamArr2 = new SampleStream[exoTrackSelectionArr.length];
                } else {
                    sampleStreamArr2 = (SampleStream[]) Arrays.copyOf(sampleStreamArr3, sampleStreamArr3.length);
                }
                long selectTracks = this.d.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr2, zArr2, streamPositionUs);
                this.b = (SampleStream[]) Arrays.copyOf(sampleStreamArr2, sampleStreamArr2.length);
                this.c = (MediaLoadData[]) Arrays.copyOf(this.c, sampleStreamArr2.length);
                for (int i = 0; i < sampleStreamArr2.length; i++) {
                    if (sampleStreamArr2[i] == null) {
                        sampleStreamArr[i] = null;
                        this.c[i] = null;
                    } else if (sampleStreamArr[i] == null || zArr2[i]) {
                        sampleStreamArr[i] = new b(aVar, i);
                        this.c[i] = null;
                    }
                }
                return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(selectTracks, aVar.b, this.g);
            }
            for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
                boolean z = true;
                if (exoTrackSelectionArr[i2] != null) {
                    if (zArr[i2] && sampleStreamArr[i2] != null) {
                        z = false;
                    }
                    zArr2[i2] = z;
                    if (zArr2[i2]) {
                        if (Util.areEqual(this.a[i2], exoTrackSelectionArr[i2])) {
                            sampleStream = new b(aVar, i2);
                        } else {
                            sampleStream = new EmptySampleStream();
                        }
                        sampleStreamArr[i2] = sampleStream;
                    }
                } else {
                    sampleStreamArr[i2] = null;
                    zArr2[i2] = true;
                }
            }
            return j;
        }

        public int a(a aVar, int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            int readData = ((SampleStream) Util.castNonNull(this.b[i])).readData(formatHolder, decoderInputBuffer, i2 | 1 | 4);
            long f = f(aVar, decoderInputBuffer.timeUs);
            if ((readData == -4 && f == Long.MIN_VALUE) || (readData == -3 && d(aVar) == Long.MIN_VALUE && !decoderInputBuffer.waitingForKeys)) {
                a(aVar, i);
                decoderInputBuffer.clear();
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            if (readData == -4) {
                a(aVar, i);
                ((SampleStream) Util.castNonNull(this.b[i])).readData(formatHolder, decoderInputBuffer, i2);
                decoderInputBuffer.timeUs = f;
            }
            return readData;
        }

        public int a(a aVar, int i, long j) {
            return ((SampleStream) Util.castNonNull(this.b[i])).skipData(ServerSideInsertedAdsUtil.getStreamPositionUs(j, aVar.b, this.g));
        }

        public boolean a(int i) {
            return ((SampleStream) Util.castNonNull(this.b[i])).isReady();
        }

        public void b(int i) throws IOException {
            ((SampleStream) Util.castNonNull(this.b[i])).maybeThrowError();
        }

        public void a(a aVar, MediaLoadData mediaLoadData) {
            int b = b(mediaLoadData);
            if (b != -1) {
                this.c[b] = mediaLoadData;
                aVar.g[b] = true;
            }
        }

        public void a(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            this.f.put(Long.valueOf(loadEventInfo.loadTaskId), Pair.create(loadEventInfo, mediaLoadData));
        }

        public void a(LoadEventInfo loadEventInfo) {
            this.f.remove(Long.valueOf(loadEventInfo.loadTaskId));
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            this.j = true;
            for (int i = 0; i < this.e.size(); i++) {
                a aVar = this.e.get(i);
                if (aVar.e != null) {
                    aVar.e.onPrepared(aVar);
                }
            }
        }

        /* renamed from: a */
        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            a aVar = this.h;
            if (aVar != null) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(aVar.e)).onContinueLoadingRequested(this.h);
            }
        }

        private long e(a aVar, long j) {
            if (j < aVar.f) {
                return ServerSideInsertedAdsUtil.getStreamPositionUs(aVar.f, aVar.b, this.g) - (aVar.f - j);
            }
            return ServerSideInsertedAdsUtil.getStreamPositionUs(j, aVar.b, this.g);
        }

        private long f(a aVar, long j) {
            if (j == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            long mediaPeriodPositionUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(j, aVar.b, this.g);
            if (mediaPeriodPositionUs >= ServerSideInsertedAdsMediaSource.b(aVar, this.g)) {
                return Long.MIN_VALUE;
            }
            return mediaPeriodPositionUs;
        }

        private int b(MediaLoadData mediaLoadData) {
            if (mediaLoadData.trackFormat == null) {
                return -1;
            }
            int i = 0;
            loop0: while (true) {
                ExoTrackSelection[] exoTrackSelectionArr = this.a;
                if (i >= exoTrackSelectionArr.length) {
                    return -1;
                }
                if (exoTrackSelectionArr[i] != null) {
                    TrackGroup trackGroup = exoTrackSelectionArr[i].getTrackGroup();
                    boolean z = mediaLoadData.trackType == 0 && trackGroup.equals(c().get(0));
                    for (int i2 = 0; i2 < trackGroup.length; i2++) {
                        Format format = trackGroup.getFormat(i2);
                        if (format.equals(mediaLoadData.trackFormat) || (z && format.id != null && format.id.equals(mediaLoadData.trackFormat.id))) {
                            break loop0;
                        }
                    }
                    continue;
                }
                i++;
            }
            return i;
        }

        private void a(a aVar, int i) {
            if (!aVar.g[i] && this.c[i] != null) {
                aVar.g[i] = true;
                aVar.c.downstreamFormatChanged(ServerSideInsertedAdsMediaSource.b(aVar, this.c[i], this.g));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c extends ForwardingTimeline {
        private final AdPlaybackState a;

        public c(Timeline timeline, AdPlaybackState adPlaybackState) {
            super(timeline);
            boolean z = false;
            Assertions.checkState(timeline.getPeriodCount() == 1);
            Assertions.checkState(timeline.getWindowCount() == 1 ? true : z);
            this.a = adPlaybackState;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            super.getWindow(i, window, j);
            long mediaPeriodPositionUsForContent = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(window.positionInFirstPeriodUs, -1, this.a);
            if (window.durationUs != C.TIME_UNSET) {
                window.durationUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(window.positionInFirstPeriodUs + window.durationUs, -1, this.a) - mediaPeriodPositionUsForContent;
            } else if (this.a.contentDurationUs != C.TIME_UNSET) {
                window.durationUs = this.a.contentDurationUs - mediaPeriodPositionUsForContent;
            }
            window.positionInFirstPeriodUs = mediaPeriodPositionUsForContent;
            return window;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            long j;
            super.getPeriod(i, period, z);
            long j2 = period.durationUs;
            if (j2 == C.TIME_UNSET) {
                j = this.a.contentDurationUs;
            } else {
                j = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(j2, -1, this.a);
            }
            period.set(period.id, period.uid, period.windowIndex, j, -ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(-period.getPositionInWindowUs(), -1, this.a), this.a, period.isPlaceholder);
            return period;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a implements MediaPeriod {
        public final d a;
        public final MediaSource.MediaPeriodId b;
        public final MediaSourceEventListener.EventDispatcher c;
        public final DrmSessionEventListener.EventDispatcher d;
        public MediaPeriod.Callback e;
        public long f;
        public boolean[] g = new boolean[0];

        public a(d dVar, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.EventDispatcher eventDispatcher, DrmSessionEventListener.EventDispatcher eventDispatcher2) {
            this.a = dVar;
            this.b = mediaPeriodId;
            this.c = eventDispatcher;
            this.d = eventDispatcher2;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long j) {
            this.e = callback;
            this.a.a(this, j);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() throws IOException {
            this.a.b();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return this.a.c();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
            return this.a.a(list);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
            if (this.g.length == 0) {
                this.g = new boolean[sampleStreamArr.length];
            }
            return this.a.a(this, exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long j, boolean z) {
            this.a.a(this, j, z);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            return this.a.f(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long j) {
            return this.a.c(this, j);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
            return this.a.a(this, j, seekParameters);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            return this.a.d(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            return this.a.e(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long j) {
            return this.a.b(this, j);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return this.a.c(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long j) {
            this.a.d(this, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b implements SampleStream {
        private final a a;
        private final int b;

        public b(a aVar, int i) {
            this.a = aVar;
            this.b = i;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return this.a.a.a(this.b);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            this.a.a.b(this.b);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            return this.a.a.a(this.a, this.b, formatHolder, decoderInputBuffer, i);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j) {
            return this.a.a.a(this.a, this.b, j);
        }
    }
}
