package com.google.android.exoplayer2.source;

import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;

/* loaded from: classes2.dex */
public interface MediaSource {

    /* loaded from: classes2.dex */
    public interface MediaSourceCaller {
        void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline);
    }

    void addDrmEventListener(Handler handler, DrmSessionEventListener drmSessionEventListener);

    void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener);

    MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j);

    void disable(MediaSourceCaller mediaSourceCaller);

    void enable(MediaSourceCaller mediaSourceCaller);

    @Nullable
    default Timeline getInitialTimeline() {
        return null;
    }

    MediaItem getMediaItem();

    default boolean isSingleWindow() {
        return true;
    }

    void maybeThrowSourceInfoRefreshError() throws IOException;

    void prepareSource(MediaSourceCaller mediaSourceCaller, @Nullable TransferListener transferListener);

    void releasePeriod(MediaPeriod mediaPeriod);

    void releaseSource(MediaSourceCaller mediaSourceCaller);

    void removeDrmEventListener(DrmSessionEventListener drmSessionEventListener);

    void removeEventListener(MediaSourceEventListener mediaSourceEventListener);

    /* loaded from: classes2.dex */
    public static final class MediaPeriodId extends MediaPeriodId {
        public MediaPeriodId(Object obj) {
            super(obj);
        }

        public MediaPeriodId(Object obj, long j) {
            super(obj, j);
        }

        public MediaPeriodId(Object obj, long j, int i) {
            super(obj, j, i);
        }

        public MediaPeriodId(Object obj, int i, int i2, long j) {
            super(obj, i, i2, j);
        }

        public MediaPeriodId(MediaPeriodId mediaPeriodId) {
            super(mediaPeriodId);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriodId
        public MediaPeriodId copyWithPeriodUid(Object obj) {
            return new MediaPeriodId(super.copyWithPeriodUid(obj));
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriodId
        public MediaPeriodId copyWithWindowSequenceNumber(long j) {
            return new MediaPeriodId(super.copyWithWindowSequenceNumber(j));
        }
    }
}
