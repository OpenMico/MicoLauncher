package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ClippingMediaSource extends CompositeMediaSource<Void> {
    private final MediaSource a;
    private final long b;
    private final long c;
    private final boolean d;
    private final boolean e;
    private final boolean f;
    private final ArrayList<ClippingMediaPeriod> g;
    private final Timeline.Window h;
    @Nullable
    private a i;
    @Nullable
    private IllegalClippingException j;
    private long k;
    private long l;

    /* loaded from: classes2.dex */
    public static final class IllegalClippingException extends IOException {
        public static final int REASON_INVALID_PERIOD_COUNT = 0;
        public static final int REASON_NOT_SEEKABLE_TO_START = 1;
        public static final int REASON_START_EXCEEDS_END = 2;
        public final int reason;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface Reason {
        }

        private static String a(int i) {
            switch (i) {
                case 0:
                    return "invalid period count";
                case 1:
                    return "not seekable to start";
                case 2:
                    return "start exceeds end";
                default:
                    return "unknown";
            }
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public IllegalClippingException(int r4) {
            /*
                r3 = this;
                java.lang.String r0 = "Illegal clipping: "
                java.lang.String r1 = a(r4)
                java.lang.String r1 = java.lang.String.valueOf(r1)
                int r2 = r1.length()
                if (r2 == 0) goto L_0x0015
                java.lang.String r0 = r0.concat(r1)
                goto L_0x001b
            L_0x0015:
                java.lang.String r1 = new java.lang.String
                r1.<init>(r0)
                r0 = r1
            L_0x001b:
                r3.<init>(r0)
                r3.reason = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ClippingMediaSource.IllegalClippingException.<init>(int):void");
        }
    }

    public ClippingMediaSource(MediaSource mediaSource, long j, long j2) {
        this(mediaSource, j, j2, true, false, false);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j) {
        this(mediaSource, 0L, j, true, false, true);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j, long j2, boolean z, boolean z2, boolean z3) {
        Assertions.checkArgument(j >= 0);
        this.a = (MediaSource) Assertions.checkNotNull(mediaSource);
        this.b = j;
        this.c = j2;
        this.d = z;
        this.e = z2;
        this.f = z3;
        this.g = new ArrayList<>();
        this.h = new Timeline.Window();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.a.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        prepareChildSource(null, this.a);
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalClippingException illegalClippingException = this.j;
        if (illegalClippingException == null) {
            super.maybeThrowSourceInfoRefreshError();
            return;
        }
        throw illegalClippingException;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(this.a.createPeriod(mediaPeriodId, allocator, j), this.d, this.k, this.l);
        this.g.add(clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        Assertions.checkState(this.g.remove(mediaPeriod));
        this.a.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
        if (this.g.isEmpty() && !this.e) {
            a(((a) Assertions.checkNotNull(this.i)).timeline);
        }
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.j = null;
        this.i = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(Void r1, MediaSource mediaSource, Timeline timeline) {
        if (this.j == null) {
            a(timeline);
        }
    }

    private void a(Timeline timeline) {
        long j;
        timeline.getWindow(0, this.h);
        long positionInFirstPeriodUs = this.h.getPositionInFirstPeriodUs();
        long j2 = Long.MIN_VALUE;
        if (this.i == null || this.g.isEmpty() || this.e) {
            long j3 = this.b;
            long j4 = this.c;
            if (this.f) {
                long defaultPositionUs = this.h.getDefaultPositionUs();
                j3 += defaultPositionUs;
                j4 += defaultPositionUs;
            }
            this.k = positionInFirstPeriodUs + j3;
            if (this.c != Long.MIN_VALUE) {
                j2 = positionInFirstPeriodUs + j4;
            }
            this.l = j2;
            int size = this.g.size();
            for (int i = 0; i < size; i++) {
                this.g.get(i).updateClipping(this.k, this.l);
            }
            j = j3;
            j2 = j4;
        } else {
            j = this.k - positionInFirstPeriodUs;
            if (this.c != Long.MIN_VALUE) {
                j2 = this.l - positionInFirstPeriodUs;
            }
        }
        try {
            this.i = new a(timeline, j, j2);
            refreshSourceInfo(this.i);
        } catch (IllegalClippingException e) {
            this.j = e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends ForwardingTimeline {
        private final long a;
        private final long b;
        private final long c;
        private final boolean d;

        public a(Timeline timeline, long j, long j2) throws IllegalClippingException {
            super(timeline);
            boolean z = false;
            if (timeline.getPeriodCount() == 1) {
                Timeline.Window window = timeline.getWindow(0, new Timeline.Window());
                long max = Math.max(0L, j);
                if (window.isPlaceholder || max == 0 || window.isSeekable) {
                    long max2 = j2 == Long.MIN_VALUE ? window.durationUs : Math.max(0L, j2);
                    if (window.durationUs != C.TIME_UNSET) {
                        max2 = max2 > window.durationUs ? window.durationUs : max2;
                        if (max > max2) {
                            throw new IllegalClippingException(2);
                        }
                    }
                    this.a = max;
                    this.b = max2;
                    int i = (max2 > C.TIME_UNSET ? 1 : (max2 == C.TIME_UNSET ? 0 : -1));
                    this.c = i == 0 ? -9223372036854775807L : max2 - max;
                    if (window.isDynamic && (i == 0 || (window.durationUs != C.TIME_UNSET && max2 == window.durationUs))) {
                        z = true;
                    }
                    this.d = z;
                    return;
                }
                throw new IllegalClippingException(1);
            }
            throw new IllegalClippingException(0);
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            this.timeline.getWindow(0, window, 0L);
            window.positionInFirstPeriodUs += this.a;
            window.durationUs = this.c;
            window.isDynamic = this.d;
            if (window.defaultPositionUs != C.TIME_UNSET) {
                window.defaultPositionUs = Math.max(window.defaultPositionUs, this.a);
                window.defaultPositionUs = this.b == C.TIME_UNSET ? window.defaultPositionUs : Math.min(window.defaultPositionUs, this.b);
                window.defaultPositionUs -= this.a;
            }
            long usToMs = C.usToMs(this.a);
            if (window.presentationStartTimeMs != C.TIME_UNSET) {
                window.presentationStartTimeMs += usToMs;
            }
            if (window.windowStartTimeMs != C.TIME_UNSET) {
                window.windowStartTimeMs += usToMs;
            }
            return window;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            this.timeline.getPeriod(0, period, z);
            long positionInWindowUs = period.getPositionInWindowUs() - this.a;
            long j = this.c;
            return period.set(period.id, period.uid, 0, j == C.TIME_UNSET ? -9223372036854775807L : j - positionInWindowUs, positionInWindowUs);
        }
    }
}
