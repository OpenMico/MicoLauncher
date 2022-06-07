package com.google.android.exoplayer2.extractor;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class BinarySearchSeeker {
    private final int a;
    protected final BinarySearchSeekMap seekMap;
    @Nullable
    protected SeekOperationParams seekOperationParams;
    protected final TimestampSeeker timestampSeeker;

    /* loaded from: classes2.dex */
    public static final class DefaultSeekTimestampConverter implements SeekTimestampConverter {
        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.SeekTimestampConverter
        public long timeUsToTargetTime(long j) {
            return j;
        }
    }

    /* loaded from: classes2.dex */
    public interface SeekTimestampConverter {
        long timeUsToTargetTime(long j);
    }

    /* loaded from: classes2.dex */
    public interface TimestampSeeker {
        default void onSeekFinished() {
        }

        TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException;
    }

    protected void onSeekOperationFinished(boolean z, long j) {
    }

    public BinarySearchSeeker(SeekTimestampConverter seekTimestampConverter, TimestampSeeker timestampSeeker, long j, long j2, long j3, long j4, long j5, long j6, int i) {
        this.timestampSeeker = timestampSeeker;
        this.a = i;
        this.seekMap = new BinarySearchSeekMap(seekTimestampConverter, j, j2, j3, j4, j5, j6);
    }

    public final SeekMap getSeekMap() {
        return this.seekMap;
    }

    public final void setSeekTargetUs(long j) {
        SeekOperationParams seekOperationParams = this.seekOperationParams;
        if (seekOperationParams == null || seekOperationParams.d() != j) {
            this.seekOperationParams = createSeekParamsForTargetTimeUs(j);
        }
    }

    public final boolean isSeeking() {
        return this.seekOperationParams != null;
    }

    public int handlePendingSeek(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        while (true) {
            SeekOperationParams seekOperationParams = (SeekOperationParams) Assertions.checkStateNotNull(this.seekOperationParams);
            long a = seekOperationParams.a();
            long b = seekOperationParams.b();
            long e = seekOperationParams.e();
            if (b - a <= this.a) {
                markSeekOperationFinished(false, a);
                return seekToPosition(extractorInput, a, positionHolder);
            } else if (!skipInputUntilPosition(extractorInput, e)) {
                return seekToPosition(extractorInput, e, positionHolder);
            } else {
                extractorInput.resetPeekPosition();
                TimestampSearchResult searchForTimestamp = this.timestampSeeker.searchForTimestamp(extractorInput, seekOperationParams.c());
                switch (searchForTimestamp.a) {
                    case -3:
                        markSeekOperationFinished(false, e);
                        return seekToPosition(extractorInput, e, positionHolder);
                    case -2:
                        seekOperationParams.a(searchForTimestamp.b, searchForTimestamp.c);
                        break;
                    case -1:
                        seekOperationParams.b(searchForTimestamp.b, searchForTimestamp.c);
                        break;
                    case 0:
                        skipInputUntilPosition(extractorInput, searchForTimestamp.c);
                        markSeekOperationFinished(true, searchForTimestamp.c);
                        return seekToPosition(extractorInput, searchForTimestamp.c, positionHolder);
                    default:
                        throw new IllegalStateException("Invalid case");
                }
            }
        }
    }

    protected SeekOperationParams createSeekParamsForTargetTimeUs(long j) {
        return new SeekOperationParams(j, this.seekMap.timeUsToTargetTime(j), this.seekMap.c, this.seekMap.d, this.seekMap.e, this.seekMap.f, this.seekMap.g);
    }

    protected final void markSeekOperationFinished(boolean z, long j) {
        this.seekOperationParams = null;
        this.timestampSeeker.onSeekFinished();
        onSeekOperationFinished(z, j);
    }

    protected final boolean skipInputUntilPosition(ExtractorInput extractorInput, long j) throws IOException {
        long position = j - extractorInput.getPosition();
        if (position < 0 || position > PlaybackStateCompat.ACTION_SET_REPEAT_MODE) {
            return false;
        }
        extractorInput.skipFully((int) position);
        return true;
    }

    protected final int seekToPosition(ExtractorInput extractorInput, long j, PositionHolder positionHolder) {
        if (j == extractorInput.getPosition()) {
            return 0;
        }
        positionHolder.position = j;
        return 1;
    }

    /* loaded from: classes2.dex */
    public static class SeekOperationParams {
        private final long a;
        private final long b;
        private final long c;
        private long d;
        private long e;
        private long f;
        private long g;
        private long h;

        protected static long calculateNextSearchBytePosition(long j, long j2, long j3, long j4, long j5, long j6) {
            if (j4 + 1 >= j5 || j2 + 1 >= j3) {
                return j4;
            }
            long j7 = ((float) (j - j2)) * (((float) (j5 - j4)) / ((float) (j3 - j2)));
            return Util.constrainValue(((j7 + j4) - j6) - (j7 / 20), j4, j5 - 1);
        }

        protected SeekOperationParams(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
            this.a = j;
            this.b = j2;
            this.d = j3;
            this.e = j4;
            this.f = j5;
            this.g = j6;
            this.c = j7;
            this.h = calculateNextSearchBytePosition(j2, j3, j4, j5, j6, j7);
        }

        public long a() {
            return this.f;
        }

        public long b() {
            return this.g;
        }

        public long c() {
            return this.b;
        }

        public long d() {
            return this.a;
        }

        public void a(long j, long j2) {
            this.d = j;
            this.f = j2;
            f();
        }

        public void b(long j, long j2) {
            this.e = j;
            this.g = j2;
            f();
        }

        public long e() {
            return this.h;
        }

        private void f() {
            this.h = calculateNextSearchBytePosition(this.b, this.d, this.e, this.f, this.g, this.c);
        }
    }

    /* loaded from: classes2.dex */
    public static final class TimestampSearchResult {
        public static final TimestampSearchResult NO_TIMESTAMP_IN_RANGE_RESULT = new TimestampSearchResult(-3, C.TIME_UNSET, -1);
        public static final int TYPE_NO_TIMESTAMP = -3;
        public static final int TYPE_POSITION_OVERESTIMATED = -1;
        public static final int TYPE_POSITION_UNDERESTIMATED = -2;
        public static final int TYPE_TARGET_TIMESTAMP_FOUND = 0;
        private final int a;
        private final long b;
        private final long c;

        private TimestampSearchResult(int i, long j, long j2) {
            this.a = i;
            this.b = j;
            this.c = j2;
        }

        public static TimestampSearchResult overestimatedResult(long j, long j2) {
            return new TimestampSearchResult(-1, j, j2);
        }

        public static TimestampSearchResult underestimatedResult(long j, long j2) {
            return new TimestampSearchResult(-2, j, j2);
        }

        public static TimestampSearchResult targetFoundResult(long j) {
            return new TimestampSearchResult(0, C.TIME_UNSET, j);
        }
    }

    /* loaded from: classes2.dex */
    public static class BinarySearchSeekMap implements SeekMap {
        private final SeekTimestampConverter a;
        private final long b;
        private final long c;
        private final long d;
        private final long e;
        private final long f;
        private final long g;

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }

        public BinarySearchSeekMap(SeekTimestampConverter seekTimestampConverter, long j, long j2, long j3, long j4, long j5, long j6) {
            this.a = seekTimestampConverter;
            this.b = j;
            this.c = j2;
            this.d = j3;
            this.e = j4;
            this.f = j5;
            this.g = j6;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long j) {
            return new SeekMap.SeekPoints(new SeekPoint(j, SeekOperationParams.calculateNextSearchBytePosition(this.a.timeUsToTargetTime(j), this.c, this.d, this.e, this.f, this.g)));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return this.b;
        }

        public long timeUsToTargetTime(long j) {
            return this.a.timeUsToTargetTime(j);
        }
    }
}
