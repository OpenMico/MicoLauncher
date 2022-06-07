package com.google.android.exoplayer2;

import android.os.Handler;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ImmutableList;

/* compiled from: MediaPeriodQueue.java */
/* loaded from: classes.dex */
final class e {
    private final Timeline.Period a = new Timeline.Period();
    private final Timeline.Window b = new Timeline.Window();
    @Nullable
    private final AnalyticsCollector c;
    private final Handler d;
    private long e;
    private int f;
    private boolean g;
    @Nullable
    private b h;
    @Nullable
    private b i;
    @Nullable
    private b j;
    private int k;
    @Nullable
    private Object l;
    private long m;

    private boolean a(long j, long j2) {
        return j == C.TIME_UNSET || j == j2;
    }

    public e(@Nullable AnalyticsCollector analyticsCollector, Handler handler) {
        this.c = analyticsCollector;
        this.d = handler;
    }

    public boolean a(Timeline timeline, int i) {
        this.f = i;
        return a(timeline);
    }

    public boolean a(Timeline timeline, boolean z) {
        this.g = z;
        return a(timeline);
    }

    public boolean a(MediaPeriod mediaPeriod) {
        b bVar = this.j;
        return bVar != null && bVar.a == mediaPeriod;
    }

    public void a(long j) {
        b bVar = this.j;
        if (bVar != null) {
            bVar.d(j);
        }
    }

    public boolean a() {
        b bVar = this.j;
        return bVar == null || (!bVar.f.i && this.j.c() && this.j.f.e != C.TIME_UNSET && this.k < 100);
    }

    @Nullable
    public d a(long j, g gVar) {
        if (this.j == null) {
            return a(gVar);
        }
        return a(gVar.a, this.j, j);
    }

    public b a(RendererCapabilities[] rendererCapabilitiesArr, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, d dVar, TrackSelectorResult trackSelectorResult) {
        long j;
        b bVar = this.j;
        if (bVar == null) {
            j = (!dVar.a.isAd() || dVar.c == C.TIME_UNSET) ? 0L : dVar.c;
        } else {
            j = (bVar.a() + this.j.f.e) - dVar.b;
        }
        b bVar2 = new b(rendererCapabilitiesArr, j, trackSelector, allocator, mediaSourceList, dVar, trackSelectorResult);
        b bVar3 = this.j;
        if (bVar3 != null) {
            bVar3.a(bVar2);
        } else {
            this.h = bVar2;
            this.i = bVar2;
        }
        this.l = null;
        this.j = bVar2;
        this.k++;
        h();
        return bVar2;
    }

    @Nullable
    public b b() {
        return this.j;
    }

    @Nullable
    public b c() {
        return this.h;
    }

    @Nullable
    public b d() {
        return this.i;
    }

    public b e() {
        b bVar = this.i;
        Assertions.checkState((bVar == null || bVar.g() == null) ? false : true);
        this.i = this.i.g();
        h();
        return this.i;
    }

    @Nullable
    public b f() {
        b bVar = this.h;
        if (bVar == null) {
            return null;
        }
        if (bVar == this.i) {
            this.i = bVar.g();
        }
        this.h.f();
        this.k--;
        if (this.k == 0) {
            this.j = null;
            this.l = this.h.b;
            this.m = this.h.f.a.windowSequenceNumber;
        }
        this.h = this.h.g();
        h();
        return this.h;
    }

    public boolean a(b bVar) {
        boolean z = false;
        Assertions.checkState(bVar != null);
        if (bVar.equals(this.j)) {
            return false;
        }
        this.j = bVar;
        while (bVar.g() != null) {
            bVar = bVar.g();
            if (bVar == this.i) {
                this.i = this.h;
                z = true;
            }
            bVar.f();
            this.k--;
        }
        this.j.a((b) null);
        h();
        return z;
    }

    public void g() {
        if (this.k != 0) {
            b bVar = (b) Assertions.checkStateNotNull(this.h);
            this.l = bVar.b;
            this.m = bVar.f.a.windowSequenceNumber;
            while (bVar != null) {
                bVar.f();
                bVar = bVar.g();
            }
            this.h = null;
            this.j = null;
            this.i = null;
            this.k = 0;
            h();
        }
    }

    public boolean a(Timeline timeline, long j, long j2) {
        d dVar;
        b bVar = this.h;
        bVar = null;
        while (bVar != null) {
            d dVar2 = bVar.f;
            if (bVar == null) {
                dVar = a(timeline, dVar2);
            } else {
                d a = a(timeline, bVar, j);
                if (a == null) {
                    return !a(bVar);
                }
                if (!a(dVar2, a)) {
                    return !a(bVar);
                }
                dVar = a;
            }
            bVar.f = dVar.b(dVar2.c);
            if (!a(dVar2.e, dVar.e)) {
                bVar.j();
                return !a(bVar) && !(bVar == this.i && !bVar.f.f && ((j2 > Long.MIN_VALUE ? 1 : (j2 == Long.MIN_VALUE ? 0 : -1)) == 0 || (j2 > ((dVar.e > C.TIME_UNSET ? 1 : (dVar.e == C.TIME_UNSET ? 0 : -1)) == 0 ? Long.MAX_VALUE : bVar.a(dVar.e)) ? 1 : (j2 == ((dVar.e > C.TIME_UNSET ? 1 : (dVar.e == C.TIME_UNSET ? 0 : -1)) == 0 ? Long.MAX_VALUE : bVar.a(dVar.e)) ? 0 : -1)) >= 0));
            }
            bVar = bVar.g();
        }
        return true;
    }

    public d a(Timeline timeline, d dVar) {
        long j;
        boolean z;
        MediaSource.MediaPeriodId mediaPeriodId = dVar.a;
        boolean a = a(mediaPeriodId);
        boolean a2 = a(timeline, mediaPeriodId);
        boolean a3 = a(timeline, mediaPeriodId, a);
        timeline.getPeriodByUid(dVar.a.periodUid, this.a);
        long adGroupTimeUs = (mediaPeriodId.isAd() || mediaPeriodId.nextAdGroupIndex == -1) ? -9223372036854775807L : this.a.getAdGroupTimeUs(mediaPeriodId.nextAdGroupIndex);
        if (mediaPeriodId.isAd()) {
            j = this.a.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        } else {
            j = (adGroupTimeUs == C.TIME_UNSET || adGroupTimeUs == Long.MIN_VALUE) ? this.a.getDurationUs() : adGroupTimeUs;
        }
        if (mediaPeriodId.isAd()) {
            z = this.a.isServerSideInsertedAdGroup(mediaPeriodId.adGroupIndex);
        } else {
            z = mediaPeriodId.nextAdGroupIndex != -1 && this.a.isServerSideInsertedAdGroup(mediaPeriodId.nextAdGroupIndex);
        }
        return new d(mediaPeriodId, dVar.b, dVar.c, adGroupTimeUs, j, z, a, a2, a3);
    }

    public MediaSource.MediaPeriodId a(Timeline timeline, Object obj, long j) {
        return a(timeline, obj, j, a(timeline, obj), this.a);
    }

    private void h() {
        if (this.c != null) {
            final ImmutableList.Builder builder = ImmutableList.builder();
            for (b bVar = this.h; bVar != null; bVar = bVar.g()) {
                builder.add((ImmutableList.Builder) bVar.f.a);
            }
            b bVar2 = this.i;
            final MediaSource.MediaPeriodId mediaPeriodId = bVar2 == null ? null : bVar2.f.a;
            this.d.post(new Runnable() { // from class: com.google.android.exoplayer2.-$$Lambda$e$OVz3DKWc5FFPI5FO1j-dIWbakbU
                @Override // java.lang.Runnable
                public final void run() {
                    e.this.a(builder, mediaPeriodId);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ImmutableList.Builder builder, MediaSource.MediaPeriodId mediaPeriodId) {
        this.c.updateMediaPeriodQueueInfo(builder.build(), mediaPeriodId);
    }

    private static MediaSource.MediaPeriodId a(Timeline timeline, Object obj, long j, long j2, Timeline.Period period) {
        timeline.getPeriodByUid(obj, period);
        int adGroupIndexForPositionUs = period.getAdGroupIndexForPositionUs(j);
        if (adGroupIndexForPositionUs == -1) {
            return new MediaSource.MediaPeriodId(obj, j2, period.getAdGroupIndexAfterPositionUs(j));
        }
        return new MediaSource.MediaPeriodId(obj, adGroupIndexForPositionUs, period.getFirstAdIndexToPlay(adGroupIndexForPositionUs), j2);
    }

    private long a(Timeline timeline, Object obj) {
        int indexOfPeriod;
        int i = timeline.getPeriodByUid(obj, this.a).windowIndex;
        Object obj2 = this.l;
        if (!(obj2 == null || (indexOfPeriod = timeline.getIndexOfPeriod(obj2)) == -1 || timeline.getPeriod(indexOfPeriod, this.a).windowIndex != i)) {
            return this.m;
        }
        for (b bVar = this.h; bVar != null; bVar = bVar.g()) {
            if (bVar.b.equals(obj)) {
                return bVar.f.a.windowSequenceNumber;
            }
        }
        for (b bVar2 = this.h; bVar2 != null; bVar2 = bVar2.g()) {
            int indexOfPeriod2 = timeline.getIndexOfPeriod(bVar2.b);
            if (indexOfPeriod2 != -1 && timeline.getPeriod(indexOfPeriod2, this.a).windowIndex == i) {
                return bVar2.f.a.windowSequenceNumber;
            }
        }
        long j = this.e;
        this.e = 1 + j;
        if (this.h == null) {
            this.l = obj;
            this.m = j;
        }
        return j;
    }

    private boolean a(d dVar, d dVar2) {
        return dVar.b == dVar2.b && dVar.a.equals(dVar2.a);
    }

    private boolean a(Timeline timeline) {
        b bVar = this.h;
        if (bVar == null) {
            return true;
        }
        int indexOfPeriod = timeline.getIndexOfPeriod(bVar.b);
        while (true) {
            indexOfPeriod = timeline.getNextPeriodIndex(indexOfPeriod, this.a, this.b, this.f, this.g);
            while (bVar.g() != null && !bVar.f.g) {
                bVar = bVar.g();
            }
            b g = bVar.g();
            if (indexOfPeriod == -1 || g == null || timeline.getIndexOfPeriod(g.b) != indexOfPeriod) {
                break;
            }
            bVar = g;
        }
        boolean a = a(bVar);
        bVar.f = a(timeline, bVar.f);
        return !a;
    }

    @Nullable
    private d a(g gVar) {
        return a(gVar.a, gVar.b, gVar.c, gVar.s);
    }

    @Nullable
    private d a(Timeline timeline, b bVar, long j) {
        long j2;
        d dVar = bVar.f;
        long a = (bVar.a() + dVar.e) - j;
        if (dVar.g) {
            long j3 = 0;
            int nextPeriodIndex = timeline.getNextPeriodIndex(timeline.getIndexOfPeriod(dVar.a.periodUid), this.a, this.b, this.f, this.g);
            if (nextPeriodIndex == -1) {
                return null;
            }
            int i = timeline.getPeriod(nextPeriodIndex, this.a, true).windowIndex;
            Object obj = this.a.uid;
            long j4 = dVar.a.windowSequenceNumber;
            if (timeline.getWindow(i, this.b).firstPeriodIndex == nextPeriodIndex) {
                Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.b, this.a, i, C.TIME_UNSET, Math.max(0L, a));
                if (periodPosition == null) {
                    return null;
                }
                obj = periodPosition.first;
                j3 = ((Long) periodPosition.second).longValue();
                b g = bVar.g();
                if (g == null || !g.b.equals(obj)) {
                    j4 = this.e;
                    this.e = 1 + j4;
                } else {
                    j4 = g.f.a.windowSequenceNumber;
                }
                j2 = C.TIME_UNSET;
            } else {
                j2 = 0;
            }
            return a(timeline, a(timeline, obj, j3, j4, this.a), j2, j3);
        }
        MediaSource.MediaPeriodId mediaPeriodId = dVar.a;
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.a);
        if (mediaPeriodId.isAd()) {
            int i2 = mediaPeriodId.adGroupIndex;
            int adCountInAdGroup = this.a.getAdCountInAdGroup(i2);
            if (adCountInAdGroup == -1) {
                return null;
            }
            int nextAdIndexToPlay = this.a.getNextAdIndexToPlay(i2, mediaPeriodId.adIndexInAdGroup);
            if (nextAdIndexToPlay < adCountInAdGroup) {
                return a(timeline, mediaPeriodId.periodUid, i2, nextAdIndexToPlay, dVar.c, mediaPeriodId.windowSequenceNumber);
            }
            long j5 = dVar.c;
            if (j5 == C.TIME_UNSET) {
                Timeline.Window window = this.b;
                Timeline.Period period = this.a;
                Pair<Object, Long> periodPosition2 = timeline.getPeriodPosition(window, period, period.windowIndex, C.TIME_UNSET, Math.max(0L, a));
                if (periodPosition2 == null) {
                    return null;
                }
                j5 = ((Long) periodPosition2.second).longValue();
            }
            return a(timeline, mediaPeriodId.periodUid, Math.max(a(timeline, mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex), j5), dVar.c, mediaPeriodId.windowSequenceNumber);
        }
        int firstAdIndexToPlay = this.a.getFirstAdIndexToPlay(mediaPeriodId.nextAdGroupIndex);
        if (firstAdIndexToPlay != this.a.getAdCountInAdGroup(mediaPeriodId.nextAdGroupIndex)) {
            return a(timeline, mediaPeriodId.periodUid, mediaPeriodId.nextAdGroupIndex, firstAdIndexToPlay, dVar.e, mediaPeriodId.windowSequenceNumber);
        }
        return a(timeline, mediaPeriodId.periodUid, a(timeline, mediaPeriodId.periodUid, mediaPeriodId.nextAdGroupIndex), dVar.e, mediaPeriodId.windowSequenceNumber);
    }

    @Nullable
    private d a(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j, long j2) {
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.a);
        return mediaPeriodId.isAd() ? a(timeline, mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, j, mediaPeriodId.windowSequenceNumber) : a(timeline, mediaPeriodId.periodUid, j2, j, mediaPeriodId.windowSequenceNumber);
    }

    private d a(Timeline timeline, Object obj, int i, int i2, long j, long j2) {
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(obj, i, i2, j2);
        long adDurationUs = timeline.getPeriodByUid(mediaPeriodId.periodUid, this.a).getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        long adResumePositionUs = i2 == this.a.getFirstAdIndexToPlay(i) ? this.a.getAdResumePositionUs() : 0L;
        return new d(mediaPeriodId, (adDurationUs == C.TIME_UNSET || adResumePositionUs < adDurationUs) ? adResumePositionUs : Math.max(0L, adDurationUs - 1), j, C.TIME_UNSET, adDurationUs, this.a.isServerSideInsertedAdGroup(mediaPeriodId.adGroupIndex), false, false, false);
    }

    private d a(Timeline timeline, Object obj, long j, long j2, long j3) {
        timeline.getPeriodByUid(obj, this.a);
        int adGroupIndexAfterPositionUs = this.a.getAdGroupIndexAfterPositionUs(j);
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(obj, j3, adGroupIndexAfterPositionUs);
        boolean a = a(mediaPeriodId);
        boolean a2 = a(timeline, mediaPeriodId);
        boolean a3 = a(timeline, mediaPeriodId, a);
        boolean z = adGroupIndexAfterPositionUs != -1 && this.a.isServerSideInsertedAdGroup(adGroupIndexAfterPositionUs);
        long adGroupTimeUs = adGroupIndexAfterPositionUs != -1 ? this.a.getAdGroupTimeUs(adGroupIndexAfterPositionUs) : -9223372036854775807L;
        long j4 = (adGroupTimeUs == C.TIME_UNSET || adGroupTimeUs == Long.MIN_VALUE) ? this.a.durationUs : adGroupTimeUs;
        return new d(mediaPeriodId, (j4 == C.TIME_UNSET || j < j4) ? j : Math.max(0L, j4 - 1), j2, adGroupTimeUs, j4, z, a, a2, a3);
    }

    private boolean a(MediaSource.MediaPeriodId mediaPeriodId) {
        return !mediaPeriodId.isAd() && mediaPeriodId.nextAdGroupIndex == -1;
    }

    private boolean a(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        if (!a(mediaPeriodId)) {
            return false;
        }
        return timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.a).windowIndex, this.b).lastPeriodIndex == timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
    }

    private boolean a(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, boolean z) {
        int indexOfPeriod = timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        return !timeline.getWindow(timeline.getPeriod(indexOfPeriod, this.a).windowIndex, this.b).isDynamic && timeline.isLastPeriod(indexOfPeriod, this.a, this.b, this.f, this.g) && z;
    }

    private long a(Timeline timeline, Object obj, int i) {
        timeline.getPeriodByUid(obj, this.a);
        long adGroupTimeUs = this.a.getAdGroupTimeUs(i);
        if (adGroupTimeUs == Long.MIN_VALUE) {
            return this.a.durationUs;
        }
        return adGroupTimeUs + this.a.getContentResumeOffsetUs(i);
    }
}
