package com.google.android.exoplayer2.source.dash.manifest;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;
import com.google.common.math.BigIntegerMath;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class SegmentBase {
    @Nullable
    final RangedUri a;
    final long b;
    final long c;

    public SegmentBase(@Nullable RangedUri rangedUri, long j, long j2) {
        this.a = rangedUri;
        this.b = j;
        this.c = j2;
    }

    @Nullable
    public RangedUri getInitialization(Representation representation) {
        return this.a;
    }

    public long getPresentationTimeOffsetUs() {
        return Util.scaleLargeTimestamp(this.c, 1000000L, this.b);
    }

    /* loaded from: classes2.dex */
    public static class SingleSegmentBase extends SegmentBase {
        final long d;
        final long e;

        public SingleSegmentBase(@Nullable RangedUri rangedUri, long j, long j2, long j3, long j4) {
            super(rangedUri, j, j2);
            this.d = j3;
            this.e = j4;
        }

        public SingleSegmentBase() {
            this(null, 1L, 0L, 0L, 0L);
        }

        @Nullable
        public RangedUri getIndex() {
            long j = this.e;
            if (j <= 0) {
                return null;
            }
            return new RangedUri(null, this.d, j);
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class MultiSegmentBase extends SegmentBase {
        final long d;
        final long e;
        @Nullable
        final List<SegmentTimelineElement> f;
        @VisibleForTesting
        final long g;
        private final long h;
        private final long i;

        public abstract long getSegmentCount(long j);

        public abstract RangedUri getSegmentUrl(Representation representation, long j);

        public MultiSegmentBase(@Nullable RangedUri rangedUri, long j, long j2, long j3, long j4, @Nullable List<SegmentTimelineElement> list, long j5, long j6, long j7) {
            super(rangedUri, j, j2);
            this.d = j3;
            this.e = j4;
            this.f = list;
            this.g = j5;
            this.h = j6;
            this.i = j7;
        }

        public long getSegmentNum(long j, long j2) {
            long firstSegmentNum = getFirstSegmentNum();
            long segmentCount = getSegmentCount(j2);
            if (segmentCount == 0) {
                return firstSegmentNum;
            }
            if (this.f == null) {
                long j3 = (j / ((this.e * 1000000) / this.b)) + this.d;
                return j3 < firstSegmentNum ? firstSegmentNum : segmentCount == -1 ? j3 : Math.min(j3, (firstSegmentNum + segmentCount) - 1);
            }
            long j4 = (segmentCount + firstSegmentNum) - 1;
            long j5 = firstSegmentNum;
            while (j5 <= j4) {
                long j6 = ((j4 - j5) / 2) + j5;
                int i = (getSegmentTimeUs(j6) > j ? 1 : (getSegmentTimeUs(j6) == j ? 0 : -1));
                if (i < 0) {
                    j5 = j6 + 1;
                } else if (i <= 0) {
                    return j6;
                } else {
                    j4 = j6 - 1;
                }
            }
            return j5 == firstSegmentNum ? j5 : j4;
        }

        public final long getSegmentDurationUs(long j, long j2) {
            List<SegmentTimelineElement> list = this.f;
            if (list != null) {
                return (list.get((int) (j - this.d)).b * 1000000) / this.b;
            }
            long segmentCount = getSegmentCount(j2);
            if (segmentCount == -1 || j != (getFirstSegmentNum() + segmentCount) - 1) {
                return (this.e * 1000000) / this.b;
            }
            return j2 - getSegmentTimeUs(j);
        }

        public final long getSegmentTimeUs(long j) {
            long j2;
            List<SegmentTimelineElement> list = this.f;
            if (list != null) {
                j2 = list.get((int) (j - this.d)).a - this.c;
            } else {
                j2 = (j - this.d) * this.e;
            }
            return Util.scaleLargeTimestamp(j2, 1000000L, this.b);
        }

        public long getFirstSegmentNum() {
            return this.d;
        }

        public long getFirstAvailableSegmentNum(long j, long j2) {
            if (getSegmentCount(j) == -1) {
                long j3 = this.h;
                if (j3 != C.TIME_UNSET) {
                    return Math.max(getFirstSegmentNum(), getSegmentNum((j2 - this.i) - j3, j));
                }
            }
            return getFirstSegmentNum();
        }

        public long getAvailableSegmentCount(long j, long j2) {
            long segmentCount = getSegmentCount(j);
            return segmentCount != -1 ? segmentCount : (int) (getSegmentNum((j2 - this.i) + this.g, j) - getFirstAvailableSegmentNum(j, j2));
        }

        public long getNextSegmentAvailableTimeUs(long j, long j2) {
            if (this.f != null) {
                return C.TIME_UNSET;
            }
            long firstAvailableSegmentNum = getFirstAvailableSegmentNum(j, j2) + getAvailableSegmentCount(j, j2);
            return (getSegmentTimeUs(firstAvailableSegmentNum) + getSegmentDurationUs(firstAvailableSegmentNum, j)) - this.g;
        }

        public boolean isExplicit() {
            return this.f != null;
        }
    }

    /* loaded from: classes2.dex */
    public static final class SegmentList extends MultiSegmentBase {
        @Nullable
        final List<RangedUri> h;

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public boolean isExplicit() {
            return true;
        }

        public SegmentList(RangedUri rangedUri, long j, long j2, long j3, long j4, @Nullable List<SegmentTimelineElement> list, long j5, @Nullable List<RangedUri> list2, long j6, long j7) {
            super(rangedUri, j, j2, j3, j4, list, j5, j6, j7);
            this.h = list2;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j) {
            return this.h.get((int) (j - this.d));
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j) {
            return this.h.size();
        }
    }

    /* loaded from: classes2.dex */
    public static final class SegmentTemplate extends MultiSegmentBase {
        @Nullable
        final UrlTemplate h;
        @Nullable
        final UrlTemplate i;
        final long j;

        public SegmentTemplate(RangedUri rangedUri, long j, long j2, long j3, long j4, long j5, @Nullable List<SegmentTimelineElement> list, long j6, @Nullable UrlTemplate urlTemplate, @Nullable UrlTemplate urlTemplate2, long j7, long j8) {
            super(rangedUri, j, j2, j3, j5, list, j6, j7, j8);
            this.h = urlTemplate;
            this.i = urlTemplate2;
            this.j = j4;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase
        @Nullable
        public RangedUri getInitialization(Representation representation) {
            UrlTemplate urlTemplate = this.h;
            if (urlTemplate != null) {
                return new RangedUri(urlTemplate.buildUri(representation.format.id, 0L, representation.format.bitrate, 0L), 0L, -1L);
            }
            return super.getInitialization(representation);
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j) {
            return new RangedUri(this.i.buildUri(representation.format.id, j, representation.format.bitrate, this.f != null ? ((SegmentTimelineElement) this.f.get((int) (j - this.d))).a : (j - this.d) * this.e), 0L, -1L);
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j) {
            if (this.f != null) {
                return this.f.size();
            }
            long j2 = this.j;
            if (j2 != -1) {
                return (j2 - this.d) + 1;
            }
            if (j != C.TIME_UNSET) {
                return BigIntegerMath.divide(BigInteger.valueOf(j).multiply(BigInteger.valueOf(this.b)), BigInteger.valueOf(this.e).multiply(BigInteger.valueOf(1000000L)), RoundingMode.CEILING).longValue();
            }
            return -1L;
        }
    }

    /* loaded from: classes2.dex */
    public static final class SegmentTimelineElement {
        final long a;
        final long b;

        public SegmentTimelineElement(long j, long j2) {
            this.a = j;
            this.b = j2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SegmentTimelineElement segmentTimelineElement = (SegmentTimelineElement) obj;
            return this.a == segmentTimelineElement.a && this.b == segmentTimelineElement.b;
        }

        public int hashCode() {
            return (((int) this.a) * 31) + ((int) this.b);
        }
    }
}
