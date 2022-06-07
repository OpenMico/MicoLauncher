package kotlin.random;

import com.xiaomi.infra.galaxy.fds.Common;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.WasExperimental;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;

/* compiled from: URandom.kt */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\"\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0000ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u001e\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a2\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001b\u001a&\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001c\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\b*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\"\u001a\u001e\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a&\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a\u001c\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006)"}, d2 = {"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", Common.RANGE, "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class URandomKt {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    public static final int nextUInt(@NotNull Random nextUInt) {
        Intrinsics.checkNotNullParameter(nextUInt, "$this$nextUInt");
        return UInt.m1255constructorimpl(nextUInt.nextInt());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    /* renamed from: nextUInt-qCasIEU  reason: not valid java name */
    public static final int m1609nextUIntqCasIEU(@NotNull Random nextUInt, int i) {
        Intrinsics.checkNotNullParameter(nextUInt, "$this$nextUInt");
        return m1608nextUInta8DCA5k(nextUInt, 0, i);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    /* renamed from: nextUInt-a8DCA5k  reason: not valid java name */
    public static final int m1608nextUInta8DCA5k(@NotNull Random nextUInt, int i, int i2) {
        Intrinsics.checkNotNullParameter(nextUInt, "$this$nextUInt");
        m1603checkUIntRangeBoundsJ1ME1BU(i, i2);
        return UInt.m1255constructorimpl(nextUInt.nextInt(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) ^ Integer.MIN_VALUE);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    public static final int nextUInt(@NotNull Random nextUInt, @NotNull UIntRange range) {
        Intrinsics.checkNotNullParameter(nextUInt, "$this$nextUInt");
        Intrinsics.checkNotNullParameter(range, "range");
        if (!range.isEmpty()) {
            return UnsignedKt.uintCompare(range.m1613getLastpVg5ArA(), -1) < 0 ? m1608nextUInta8DCA5k(nextUInt, range.m1612getFirstpVg5ArA(), UInt.m1255constructorimpl(range.m1613getLastpVg5ArA() + 1)) : UnsignedKt.uintCompare(range.m1612getFirstpVg5ArA(), 0) > 0 ? UInt.m1255constructorimpl(m1608nextUInta8DCA5k(nextUInt, UInt.m1255constructorimpl(range.m1612getFirstpVg5ArA() - 1), range.m1613getLastpVg5ArA()) + 1) : nextUInt(nextUInt);
        }
        throw new IllegalArgumentException("Cannot get random in empty range: " + range);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    public static final long nextULong(@NotNull Random nextULong) {
        Intrinsics.checkNotNullParameter(nextULong, "$this$nextULong");
        return ULong.m1280constructorimpl(nextULong.nextLong());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    /* renamed from: nextULong-V1Xi4fY  reason: not valid java name */
    public static final long m1610nextULongV1Xi4fY(@NotNull Random nextULong, long j) {
        Intrinsics.checkNotNullParameter(nextULong, "$this$nextULong");
        return m1611nextULongjmpaWc(nextULong, 0L, j);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    /* renamed from: nextULong-jmpaW-c  reason: not valid java name */
    public static final long m1611nextULongjmpaWc(@NotNull Random nextULong, long j, long j2) {
        Intrinsics.checkNotNullParameter(nextULong, "$this$nextULong");
        m1604checkULongRangeBoundseb3DHEI(j, j2);
        return ULong.m1280constructorimpl(nextULong.nextLong(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) ^ Long.MIN_VALUE);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
    public static final long nextULong(@NotNull Random nextULong, @NotNull ULongRange range) {
        Intrinsics.checkNotNullParameter(nextULong, "$this$nextULong");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot get random in empty range: " + range);
        } else if (UnsignedKt.ulongCompare(range.m1619getLastsVKNKU(), -1L) < 0) {
            return m1611nextULongjmpaWc(nextULong, range.m1618getFirstsVKNKU(), ULong.m1280constructorimpl(range.m1619getLastsVKNKU() + ULong.m1280constructorimpl(1 & 4294967295L)));
        } else {
            if (UnsignedKt.ulongCompare(range.m1618getFirstsVKNKU(), 0L) <= 0) {
                return nextULong(nextULong);
            }
            long j = 1 & 4294967295L;
            return ULong.m1280constructorimpl(m1611nextULongjmpaWc(nextULong, ULong.m1280constructorimpl(range.m1618getFirstsVKNKU() - ULong.m1280constructorimpl(j)), range.m1619getLastsVKNKU()) + ULong.m1280constructorimpl(j));
        }
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: nextUBytes-EVgfTAA  reason: not valid java name */
    public static final byte[] m1605nextUBytesEVgfTAA(@NotNull Random nextUBytes, @NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(nextUBytes, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        nextUBytes.nextBytes(array);
        return array;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final byte[] nextUBytes(@NotNull Random nextUBytes, int i) {
        Intrinsics.checkNotNullParameter(nextUBytes, "$this$nextUBytes");
        return UByteArray.m1238constructorimpl(nextUBytes.nextBytes(i));
    }

    /* renamed from: nextUBytes-Wvrt4B4$default  reason: not valid java name */
    public static /* synthetic */ byte[] m1607nextUBytesWvrt4B4$default(Random random, byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = UByteArray.m1244getSizeimpl(bArr);
        }
        return m1606nextUBytesWvrt4B4(random, bArr, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: nextUBytes-Wvrt4B4  reason: not valid java name */
    public static final byte[] m1606nextUBytesWvrt4B4(@NotNull Random nextUBytes, @NotNull byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(nextUBytes, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        nextUBytes.nextBytes(array, i, i2);
        return array;
    }

    /* renamed from: checkUIntRangeBounds-J1ME1BU  reason: not valid java name */
    public static final void m1603checkUIntRangeBoundsJ1ME1BU(int i, int i2) {
        if (!(UnsignedKt.uintCompare(i2, i) > 0)) {
            throw new IllegalArgumentException(RandomKt.boundsErrorMessage(UInt.m1254boximpl(i), UInt.m1254boximpl(i2)).toString());
        }
    }

    /* renamed from: checkULongRangeBounds-eb3DHEI  reason: not valid java name */
    public static final void m1604checkULongRangeBoundseb3DHEI(long j, long j2) {
        if (!(UnsignedKt.ulongCompare(j2, j) > 0)) {
            throw new IllegalArgumentException(RandomKt.boundsErrorMessage(ULong.m1279boximpl(j), ULong.m1279boximpl(j2)).toString());
        }
    }
}
