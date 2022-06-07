package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class MaskingMediaSource extends CompositeMediaSource<Void> {
    private final MediaSource a;
    private final boolean b;
    private final Timeline.Window c;
    private final Timeline.Period d;
    private a e;
    @Nullable
    private MaskingMediaPeriod f;
    private boolean g;
    private boolean h;
    private boolean i;

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    public MaskingMediaSource(MediaSource mediaSource, boolean z) {
        this.a = mediaSource;
        this.b = z && mediaSource.isSingleWindow();
        this.c = new Timeline.Window();
        this.d = new Timeline.Period();
        Timeline initialTimeline = mediaSource.getInitialTimeline();
        if (initialTimeline != null) {
            this.e = a.a(initialTimeline, null, null);
            this.i = true;
            return;
        }
        this.e = a.a(mediaSource.getMediaItem());
    }

    public Timeline getTimeline() {
        return this.e;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        if (!this.b) {
            this.g = true;
            prepareChildSource(null, this.a);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.a.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MaskingMediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j);
        maskingMediaPeriod.setMediaSource(this.a);
        if (this.h) {
            maskingMediaPeriod.createPeriod(mediaPeriodId.copyWithPeriodUid(a(mediaPeriodId.periodUid)));
        } else {
            this.f = maskingMediaPeriod;
            if (!this.g) {
                this.g = true;
                prepareChildSource(null, this.a);
            }
        }
        return maskingMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((MaskingMediaPeriod) mediaPeriod).releasePeriod();
        if (mediaPeriod == this.f) {
            this.f = null;
        }
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void releaseSourceInternal() {
        this.h = false;
        this.g = false;
        super.releaseSourceInternal();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onChildSourceInfoRefreshed(java.lang.Void r11, com.google.android.exoplayer2.source.MediaSource r12, com.google.android.exoplayer2.Timeline r13) {
        /*
            r10 = this;
            boolean r11 = r10.h
            if (r11 == 0) goto L_0x0019
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = r10.e
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = r11.a(r13)
            r10.e = r11
            com.google.android.exoplayer2.source.MaskingMediaPeriod r11 = r10.f
            if (r11 == 0) goto L_0x00b0
            long r11 = r11.getPreparePositionOverrideUs()
            r10.a(r11)
            goto L_0x00b0
        L_0x0019:
            boolean r11 = r13.isEmpty()
            if (r11 == 0) goto L_0x0036
            boolean r11 = r10.i
            if (r11 == 0) goto L_0x002a
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = r10.e
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = r11.a(r13)
            goto L_0x0032
        L_0x002a:
            java.lang.Object r11 = com.google.android.exoplayer2.Timeline.Window.SINGLE_WINDOW_UID
            java.lang.Object r12 = com.google.android.exoplayer2.source.MaskingMediaSource.a.a
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = com.google.android.exoplayer2.source.MaskingMediaSource.a.a(r13, r11, r12)
        L_0x0032:
            r10.e = r11
            goto L_0x00b0
        L_0x0036:
            com.google.android.exoplayer2.Timeline$Window r11 = r10.c
            r12 = 0
            r13.getWindow(r12, r11)
            com.google.android.exoplayer2.Timeline$Window r11 = r10.c
            long r0 = r11.getDefaultPositionUs()
            com.google.android.exoplayer2.Timeline$Window r11 = r10.c
            java.lang.Object r11 = r11.uid
            com.google.android.exoplayer2.source.MaskingMediaPeriod r2 = r10.f
            if (r2 == 0) goto L_0x0074
            long r2 = r2.getPreparePositionUs()
            com.google.android.exoplayer2.source.MaskingMediaSource$a r4 = r10.e
            com.google.android.exoplayer2.source.MaskingMediaPeriod r5 = r10.f
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r5.id
            java.lang.Object r5 = r5.periodUid
            com.google.android.exoplayer2.Timeline$Period r6 = r10.d
            r4.getPeriodByUid(r5, r6)
            com.google.android.exoplayer2.Timeline$Period r4 = r10.d
            long r4 = r4.getPositionInWindowUs()
            long r2 = r2 + r4
            com.google.android.exoplayer2.source.MaskingMediaSource$a r4 = r10.e
            com.google.android.exoplayer2.Timeline$Window r5 = r10.c
            com.google.android.exoplayer2.Timeline$Window r12 = r4.getWindow(r12, r5)
            long r4 = r12.getDefaultPositionUs()
            int r12 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r12 == 0) goto L_0x0074
            r8 = r2
            goto L_0x0075
        L_0x0074:
            r8 = r0
        L_0x0075:
            com.google.android.exoplayer2.Timeline$Window r5 = r10.c
            com.google.android.exoplayer2.Timeline$Period r6 = r10.d
            r7 = 0
            r4 = r13
            android.util.Pair r12 = r4.getPeriodPosition(r5, r6, r7, r8)
            java.lang.Object r0 = r12.first
            java.lang.Object r12 = r12.second
            java.lang.Long r12 = (java.lang.Long) r12
            long r1 = r12.longValue()
            boolean r12 = r10.i
            if (r12 == 0) goto L_0x0094
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = r10.e
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = r11.a(r13)
            goto L_0x0098
        L_0x0094:
            com.google.android.exoplayer2.source.MaskingMediaSource$a r11 = com.google.android.exoplayer2.source.MaskingMediaSource.a.a(r13, r11, r0)
        L_0x0098:
            r10.e = r11
            com.google.android.exoplayer2.source.MaskingMediaPeriod r11 = r10.f
            if (r11 == 0) goto L_0x00b0
            r10.a(r1)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r12 = r11.id
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r11 = r11.id
            java.lang.Object r11 = r11.periodUid
            java.lang.Object r11 = r10.a(r11)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r11 = r12.copyWithPeriodUid(r11)
            goto L_0x00b1
        L_0x00b0:
            r11 = 0
        L_0x00b1:
            r12 = 1
            r10.i = r12
            r10.h = r12
            com.google.android.exoplayer2.source.MaskingMediaSource$a r12 = r10.e
            r10.refreshSourceInfo(r12)
            if (r11 == 0) goto L_0x00c8
            com.google.android.exoplayer2.source.MaskingMediaPeriod r12 = r10.f
            java.lang.Object r12 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r12)
            com.google.android.exoplayer2.source.MaskingMediaPeriod r12 = (com.google.android.exoplayer2.source.MaskingMediaPeriod) r12
            r12.createPeriod(r11)
        L_0x00c8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.MaskingMediaSource.onChildSourceInfoRefreshed(java.lang.Void, com.google.android.exoplayer2.source.MediaSource, com.google.android.exoplayer2.Timeline):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Void r1, MediaSource.MediaPeriodId mediaPeriodId) {
        return mediaPeriodId.copyWithPeriodUid(b(mediaPeriodId.periodUid));
    }

    private Object a(Object obj) {
        return (this.e.c == null || !obj.equals(a.a)) ? obj : this.e.c;
    }

    private Object b(Object obj) {
        return (this.e.c == null || !this.e.c.equals(obj)) ? obj : a.a;
    }

    @RequiresNonNull({"unpreparedMaskingMediaPeriod"})
    private void a(long j) {
        MaskingMediaPeriod maskingMediaPeriod = this.f;
        int indexOfPeriod = this.e.getIndexOfPeriod(maskingMediaPeriod.id.periodUid);
        if (indexOfPeriod != -1) {
            long j2 = this.e.getPeriod(indexOfPeriod, this.d).durationUs;
            if (j2 != C.TIME_UNSET && j >= j2) {
                j = Math.max(0L, j2 - 1);
            }
            maskingMediaPeriod.overridePreparePositionUs(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends ForwardingTimeline {
        public static final Object a = new Object();
        @Nullable
        private final Object b;
        @Nullable
        private final Object c;

        public static a a(MediaItem mediaItem) {
            return new a(new PlaceholderTimeline(mediaItem), Timeline.Window.SINGLE_WINDOW_UID, a);
        }

        public static a a(Timeline timeline, @Nullable Object obj, @Nullable Object obj2) {
            return new a(timeline, obj, obj2);
        }

        private a(Timeline timeline, @Nullable Object obj, @Nullable Object obj2) {
            super(timeline);
            this.b = obj;
            this.c = obj2;
        }

        public a a(Timeline timeline) {
            return new a(timeline, this.b, this.c);
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            this.timeline.getWindow(i, window, j);
            if (Util.areEqual(window.uid, this.b)) {
                window.uid = Timeline.Window.SINGLE_WINDOW_UID;
            }
            return window;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            this.timeline.getPeriod(i, period, z);
            if (Util.areEqual(period.uid, this.c) && z) {
                period.uid = a;
            }
            return period;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            Object obj2;
            Timeline timeline = this.timeline;
            if (a.equals(obj) && (obj2 = this.c) != null) {
                obj = obj2;
            }
            return timeline.getIndexOfPeriod(obj);
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i) {
            Object uidOfPeriod = this.timeline.getUidOfPeriod(i);
            return Util.areEqual(uidOfPeriod, this.c) ? a : uidOfPeriod;
        }
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static final class PlaceholderTimeline extends Timeline {
        private final MediaItem a;

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return 1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 1;
        }

        public PlaceholderTimeline(MediaItem mediaItem) {
            this.a = mediaItem;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            window.set(Timeline.Window.SINGLE_WINDOW_UID, this.a, null, C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, false, true, null, 0L, C.TIME_UNSET, 0, 0, 0L);
            window.isPlaceholder = true;
            return window;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            Object obj = null;
            Integer num = z ? 0 : null;
            if (z) {
                obj = a.a;
            }
            period.set(num, obj, 0, C.TIME_UNSET, 0L, AdPlaybackState.NONE, true);
            return period;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            return obj == a.a ? 0 : -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i) {
            return a.a;
        }
    }
}
