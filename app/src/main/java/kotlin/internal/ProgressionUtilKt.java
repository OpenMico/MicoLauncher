package kotlin.internal;

import com.umeng.analytics.pro.ai;
import kotlin.Metadata;
import kotlin.PublishedApi;

/* compiled from: progressionUtil.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u001a \u0010\u0000\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0001\u001a \u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H\u0001\u001a\u0018\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0002¨\u0006\u000b"}, d2 = {"differenceModulo", "", ai.at, "b", ai.aD, "", "getProgressionLastElement", "start", "end", "step", "mod", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class ProgressionUtilKt {
    private static final int a(int i, int i2) {
        int i3 = i % i2;
        return i3 >= 0 ? i3 : i3 + i2;
    }

    private static final long a(long j, long j2) {
        long j3 = j % j2;
        return j3 >= 0 ? j3 : j3 + j2;
    }

    private static final int a(int i, int i2, int i3) {
        return a(a(i, i3) - a(i2, i3), i3);
    }

    private static final long a(long j, long j2, long j3) {
        return a(a(j, j3) - a(j2, j3), j3);
    }

    @PublishedApi
    public static final int getProgressionLastElement(int i, int i2, int i3) {
        if (i3 > 0) {
            return i >= i2 ? i2 : i2 - a(i2, i, i3);
        }
        if (i3 < 0) {
            return i <= i2 ? i2 : i2 + a(i, i2, -i3);
        }
        throw new IllegalArgumentException("Step is zero.");
    }

    @PublishedApi
    public static final long getProgressionLastElement(long j, long j2, long j3) {
        int i = (j3 > 0L ? 1 : (j3 == 0L ? 0 : -1));
        if (i > 0) {
            return j >= j2 ? j2 : j2 - a(j2, j, j3);
        }
        if (i < 0) {
            return j <= j2 ? j2 : j2 + a(j, j2, -j3);
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}
