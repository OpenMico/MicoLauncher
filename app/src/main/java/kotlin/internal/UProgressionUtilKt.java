package kotlin.internal;

import com.umeng.analytics.pro.ai;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;

/* compiled from: UProgressionUtil.kt */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a*\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0006\u001a*\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"differenceModulo", "Lkotlin/UInt;", ai.at, "b", ai.aD, "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class UProgressionUtilKt {
    private static final int a(int i, int i2, int i3) {
        int i4 = UnsignedKt.m1330uintRemainderJ1ME1BU(i, i3);
        int i5 = UnsignedKt.m1330uintRemainderJ1ME1BU(i2, i3);
        return UInt.m1255constructorimpl(UnsignedKt.uintCompare(i4, i5) >= 0 ? i4 - i5 : UInt.m1255constructorimpl(i4 - i5) + i3);
    }

    private static final long a(long j, long j2, long j3) {
        long j4 = UnsignedKt.m1332ulongRemaindereb3DHEI(j, j3);
        long j5 = UnsignedKt.m1332ulongRemaindereb3DHEI(j2, j3);
        return ULong.m1280constructorimpl(UnsignedKt.ulongCompare(j4, j5) >= 0 ? j4 - j5 : ULong.m1280constructorimpl(j4 - j5) + j3);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-Nkh28Cs */
    public static final int m1602getProgressionLastElementNkh28Cs(int i, int i2, int i3) {
        if (i3 > 0) {
            return UnsignedKt.uintCompare(i, i2) >= 0 ? i2 : UInt.m1255constructorimpl(i2 - a(i2, i, UInt.m1255constructorimpl(i3)));
        }
        if (i3 < 0) {
            return UnsignedKt.uintCompare(i, i2) <= 0 ? i2 : UInt.m1255constructorimpl(i2 + a(i, i2, UInt.m1255constructorimpl(-i3)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-7ftBX0g */
    public static final long m1601getProgressionLastElement7ftBX0g(long j, long j2, long j3) {
        int i = (j3 > 0L ? 1 : (j3 == 0L ? 0 : -1));
        if (i > 0) {
            return UnsignedKt.ulongCompare(j, j2) >= 0 ? j2 : ULong.m1280constructorimpl(j2 - a(j2, j, ULong.m1280constructorimpl(j3)));
        }
        if (i < 0) {
            return UnsignedKt.ulongCompare(j, j2) <= 0 ? j2 : ULong.m1280constructorimpl(j2 + a(j, j2, ULong.m1280constructorimpl(-j3)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}
