package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.AbstractConcatenatedTimeline;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;
import java.util.Map;

@Deprecated
/* loaded from: classes2.dex */
public final class LoopingMediaSource extends CompositeMediaSource<Void> {
    private final MaskingMediaSource a;
    private final int b;
    private final Map<MediaSource.MediaPeriodId, MediaSource.MediaPeriodId> c;
    private final Map<MediaPeriod, MediaSource.MediaPeriodId> d;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public boolean isSingleWindow() {
        return false;
    }

    public LoopingMediaSource(MediaSource mediaSource) {
        this(mediaSource, Integer.MAX_VALUE);
    }

    public LoopingMediaSource(MediaSource mediaSource, int i) {
        Assertions.checkArgument(i > 0);
        this.a = new MaskingMediaSource(mediaSource, false);
        this.b = i;
        this.c = new HashMap();
        this.d = new HashMap();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.a.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    @Nullable
    public Timeline getInitialTimeline() {
        if (this.b != Integer.MAX_VALUE) {
            return new b(this.a.getTimeline(), this.b);
        }
        return new a(this.a.getTimeline());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        prepareChildSource(null, this.a);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        if (this.b == Integer.MAX_VALUE) {
            return this.a.createPeriod(mediaPeriodId, allocator, j);
        }
        MediaSource.MediaPeriodId copyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(b.getChildPeriodUidFromConcatenatedUid(mediaPeriodId.periodUid));
        this.c.put(copyWithPeriodUid, mediaPeriodId);
        MaskingMediaPeriod createPeriod = this.a.createPeriod(copyWithPeriodUid, allocator, j);
        this.d.put(createPeriod, copyWithPeriodUid);
        return createPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.a.releasePeriod(mediaPeriod);
        MediaSource.MediaPeriodId remove = this.d.remove(mediaPeriod);
        if (remove != null) {
            this.c.remove(remove);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(Void r1, MediaSource mediaSource, Timeline timeline) {
        Timeline timeline2;
        int i = this.b;
        if (i != Integer.MAX_VALUE) {
            timeline2 = new b(timeline, i);
        } else {
            timeline2 = new a(timeline);
        }
        refreshSourceInfo(timeline2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Void r2, MediaSource.MediaPeriodId mediaPeriodId) {
        return this.b != Integer.MAX_VALUE ? this.c.get(mediaPeriodId) : mediaPeriodId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b extends AbstractConcatenatedTimeline {
        private final Timeline a;
        private final int b;
        private final int c;
        private final int d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(Timeline timeline, int i) {
            super(false, new ShuffleOrder.UnshuffledShuffleOrder(i));
            boolean z = false;
            this.a = timeline;
            this.b = timeline.getPeriodCount();
            this.c = timeline.getWindowCount();
            this.d = i;
            int i2 = this.b;
            if (i2 > 0) {
                Assertions.checkState(i <= Integer.MAX_VALUE / i2 ? true : z, "LoopingMediaSource contains too many periods");
            }
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.c * this.d;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.b * this.d;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByPeriodIndex(int i) {
            return i / this.b;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByWindowIndex(int i) {
            return i / this.c;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getChildIndexByChildUid(Object obj) {
            if (!(obj instanceof Integer)) {
                return -1;
            }
            return ((Integer) obj).intValue();
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected Timeline getTimelineByChildIndex(int i) {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getFirstPeriodIndexByChildIndex(int i) {
            return i * this.b;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected int getFirstWindowIndexByChildIndex(int i) {
            return i * this.c;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        protected Object getChildUidByChildIndex(int i) {
            return Integer.valueOf(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends ForwardingTimeline {
        public a(Timeline timeline) {
            super(timeline);
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getNextWindowIndex(int i, int i2, boolean z) {
            int nextWindowIndex = this.timeline.getNextWindowIndex(i, i2, z);
            return nextWindowIndex == -1 ? getFirstWindowIndex(z) : nextWindowIndex;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getPreviousWindowIndex(int i, int i2, boolean z) {
            int previousWindowIndex = this.timeline.getPreviousWindowIndex(i, i2, z);
            return previousWindowIndex == -1 ? getLastWindowIndex(z) : previousWindowIndex;
        }
    }
}
