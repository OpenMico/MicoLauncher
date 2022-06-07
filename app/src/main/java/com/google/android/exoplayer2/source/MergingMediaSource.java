package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public final class MergingMediaSource extends CompositeMediaSource<Integer> {
    private static final MediaItem a = new MediaItem.Builder().setMediaId("MergingMediaSource").build();
    private final boolean b;
    private final boolean c;
    private final MediaSource[] d;
    private final Timeline[] e;
    private final ArrayList<MediaSource> f;
    private final CompositeSequenceableLoaderFactory g;
    private final Map<Object, Long> h;
    private final Multimap<Object, ClippingMediaPeriod> i;
    private int j;
    private long[][] k;
    @Nullable
    private IllegalMergeException l;

    /* loaded from: classes2.dex */
    public static final class IllegalMergeException extends IOException {
        public static final int REASON_PERIOD_COUNT_MISMATCH = 0;
        public final int reason;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface Reason {
        }

        public IllegalMergeException(int i) {
            this.reason = i;
        }
    }

    public MergingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    public MergingMediaSource(boolean z, MediaSource... mediaSourceArr) {
        this(z, false, mediaSourceArr);
    }

    public MergingMediaSource(boolean z, boolean z2, MediaSource... mediaSourceArr) {
        this(z, z2, new DefaultCompositeSequenceableLoaderFactory(), mediaSourceArr);
    }

    public MergingMediaSource(boolean z, boolean z2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, MediaSource... mediaSourceArr) {
        this.b = z;
        this.c = z2;
        this.d = mediaSourceArr;
        this.g = compositeSequenceableLoaderFactory;
        this.f = new ArrayList<>(Arrays.asList(mediaSourceArr));
        this.j = -1;
        this.e = new Timeline[mediaSourceArr.length];
        this.k = new long[0];
        this.h = new HashMap();
        this.i = MultimapBuilder.hashKeys().arrayListValues().build();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        MediaSource[] mediaSourceArr = this.d;
        return mediaSourceArr.length > 0 ? mediaSourceArr[0].getMediaItem() : a;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        for (int i = 0; i < this.d.length; i++) {
            prepareChildSource(Integer.valueOf(i), this.d[i]);
        }
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalMergeException illegalMergeException = this.l;
        if (illegalMergeException == null) {
            super.maybeThrowSourceInfoRefreshError();
            return;
        }
        throw illegalMergeException;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        MediaPeriod[] mediaPeriodArr = new MediaPeriod[this.d.length];
        int indexOfPeriod = this.e[0].getIndexOfPeriod(mediaPeriodId.periodUid);
        for (int i = 0; i < mediaPeriodArr.length; i++) {
            mediaPeriodArr[i] = this.d[i].createPeriod(mediaPeriodId.copyWithPeriodUid(this.e[i].getUidOfPeriod(indexOfPeriod)), allocator, j - this.k[indexOfPeriod][i]);
        }
        a aVar = new a(this.g, this.k[indexOfPeriod], mediaPeriodArr);
        if (!this.c) {
            return aVar;
        }
        ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(aVar, true, 0L, ((Long) Assertions.checkNotNull(this.h.get(mediaPeriodId.periodUid))).longValue());
        this.i.put(mediaPeriodId.periodUid, clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        if (this.c) {
            ClippingMediaPeriod clippingMediaPeriod = (ClippingMediaPeriod) mediaPeriod;
            Iterator<Map.Entry<Object, ClippingMediaPeriod>> it = this.i.entries().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<Object, ClippingMediaPeriod> next = it.next();
                if (next.getValue().equals(clippingMediaPeriod)) {
                    this.i.remove(next.getKey(), next.getValue());
                    break;
                }
            }
            mediaPeriod = clippingMediaPeriod.mediaPeriod;
        }
        a aVar = (a) mediaPeriod;
        int i = 0;
        while (true) {
            MediaSource[] mediaSourceArr = this.d;
            if (i < mediaSourceArr.length) {
                mediaSourceArr[i].releasePeriod(aVar.a(i));
                i++;
            } else {
                return;
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        super.releaseSourceInternal();
        Arrays.fill(this.e, (Object) null);
        this.j = -1;
        this.l = null;
        this.f.clear();
        Collections.addAll(this.f, this.d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(Integer num, MediaSource mediaSource, Timeline timeline) {
        if (this.l == null) {
            if (this.j == -1) {
                this.j = timeline.getPeriodCount();
            } else if (timeline.getPeriodCount() != this.j) {
                this.l = new IllegalMergeException(0);
                return;
            }
            if (this.k.length == 0) {
                this.k = (long[][]) Array.newInstance(long.class, this.j, this.e.length);
            }
            this.f.remove(mediaSource);
            this.e[num.intValue()] = timeline;
            if (this.f.isEmpty()) {
                if (this.b) {
                    a();
                }
                a aVar = this.e[0];
                if (this.c) {
                    b();
                    aVar = new a(aVar, this.h);
                }
                refreshSourceInfo(aVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Integer num, MediaSource.MediaPeriodId mediaPeriodId) {
        if (num.intValue() == 0) {
            return mediaPeriodId;
        }
        return null;
    }

    private void a() {
        Timeline.Period period = new Timeline.Period();
        for (int i = 0; i < this.j; i++) {
            long j = -this.e[0].getPeriod(i, period).getPositionInWindowUs();
            int i2 = 1;
            while (true) {
                Timeline[] timelineArr = this.e;
                if (i2 < timelineArr.length) {
                    this.k[i][i2] = j - (-timelineArr[i2].getPeriod(i, period).getPositionInWindowUs());
                    i2++;
                }
            }
        }
    }

    private void b() {
        Timeline[] timelineArr;
        Timeline.Period period = new Timeline.Period();
        for (int i = 0; i < this.j; i++) {
            int i2 = 0;
            long j = Long.MIN_VALUE;
            while (true) {
                timelineArr = this.e;
                if (i2 >= timelineArr.length) {
                    break;
                }
                long durationUs = timelineArr[i2].getPeriod(i, period).getDurationUs();
                if (durationUs != C.TIME_UNSET) {
                    long j2 = durationUs + this.k[i][i2];
                    if (j == Long.MIN_VALUE || j2 < j) {
                        j = j2;
                    }
                }
                i2++;
            }
            Object uidOfPeriod = timelineArr[0].getUidOfPeriod(i);
            this.h.put(uidOfPeriod, Long.valueOf(j));
            for (ClippingMediaPeriod clippingMediaPeriod : this.i.get(uidOfPeriod)) {
                clippingMediaPeriod.updateClipping(0L, j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends ForwardingTimeline {
        private final long[] a;
        private final long[] b;

        public a(Timeline timeline, Map<Object, Long> map) {
            super(timeline);
            int windowCount = timeline.getWindowCount();
            this.b = new long[timeline.getWindowCount()];
            Timeline.Window window = new Timeline.Window();
            for (int i = 0; i < windowCount; i++) {
                this.b[i] = timeline.getWindow(i, window).durationUs;
            }
            int periodCount = timeline.getPeriodCount();
            this.a = new long[periodCount];
            Timeline.Period period = new Timeline.Period();
            for (int i2 = 0; i2 < periodCount; i2++) {
                timeline.getPeriod(i2, period, true);
                long longValue = ((Long) Assertions.checkNotNull(map.get(period.uid))).longValue();
                this.a[i2] = longValue == Long.MIN_VALUE ? period.durationUs : longValue;
                if (period.durationUs != C.TIME_UNSET) {
                    long[] jArr = this.b;
                    int i3 = period.windowIndex;
                    jArr[i3] = jArr[i3] - (period.durationUs - this.a[i2]);
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            long j2;
            super.getWindow(i, window, j);
            window.durationUs = this.b[i];
            if (window.durationUs == C.TIME_UNSET || window.defaultPositionUs == C.TIME_UNSET) {
                j2 = window.defaultPositionUs;
            } else {
                j2 = Math.min(window.defaultPositionUs, window.durationUs);
            }
            window.defaultPositionUs = j2;
            return window;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            super.getPeriod(i, period, z);
            period.durationUs = this.a[i];
            return period;
        }
    }
}
