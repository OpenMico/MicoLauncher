package okio.internal;

import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.SegmentedByteString;
import okio.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SegmentedByteString.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0000\u001a\u0017\u0010\u0006\u001a\u00020\u0007*\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0080\b\u001a\r\u0010\u000b\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\r\u0010\f\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001a\u001d\u0010\u0016\u001a\u00020\u0015*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001H\u0080\b\u001a\r\u0010\u0019\u001a\u00020\u0012*\u00020\bH\u0080\b\u001a%\u0010\u001a\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001aZ\u0010\u001e\u001a\u00020\u001b*\u00020\b2K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\u0080\b\u001aj\u0010\u001e\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00012K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\u0082\b\u001a\u0014\u0010$\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\u0000¨\u0006%"}, d2 = {"binarySearch", "", "", b.p, "fromIndex", "toIndex", "commonEquals", "", "Lokio/SegmentedByteString;", "other", "", "commonGetSize", "commonHashCode", "commonInternalGet", "", "pos", "commonRangeEquals", "offset", "", "otherOffset", "byteCount", "Lokio/ByteString;", "commonSubstring", "beginIndex", "endIndex", "commonToByteArray", "commonWrite", "", "buffer", "Lokio/Buffer;", "forEachSegment", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "segment", "okio"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class SegmentedByteStringKt {
    public static final int binarySearch(@NotNull int[] binarySearch, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            int i6 = binarySearch[i5];
            if (i6 < i) {
                i2 = i5 + 1;
            } else if (i6 <= i) {
                return i5;
            } else {
                i4 = i5 - 1;
            }
        }
        return (-i2) - 1;
    }

    public static final int segment(@NotNull SegmentedByteString segment, int i) {
        Intrinsics.checkParameterIsNotNull(segment, "$this$segment");
        int binarySearch = binarySearch(segment.getDirectory$okio(), i + 1, 0, segment.getSegments$okio().length);
        return binarySearch >= 0 ? binarySearch : ~binarySearch;
    }

    public static final void forEachSegment(@NotNull SegmentedByteString forEachSegment, @NotNull Function3<? super byte[], ? super Integer, ? super Integer, Unit> action) {
        Intrinsics.checkParameterIsNotNull(forEachSegment, "$this$forEachSegment");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int length = forEachSegment.getSegments$okio().length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = forEachSegment.getDirectory$okio()[length + i];
            int i4 = forEachSegment.getDirectory$okio()[i];
            action.invoke(forEachSegment.getSegments$okio()[i], Integer.valueOf(i3), Integer.valueOf(i4 - i2));
            i++;
            i2 = i4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void forEachSegment(@NotNull SegmentedByteString segmentedByteString, int i, int i2, Function3<? super byte[], ? super Integer, ? super Integer, Unit> function3) {
        int segment = segment(segmentedByteString, i);
        while (i < i2) {
            int i3 = segment == 0 ? 0 : segmentedByteString.getDirectory$okio()[segment - 1];
            int i4 = segmentedByteString.getDirectory$okio()[segmentedByteString.getSegments$okio().length + segment];
            int min = Math.min(i2, (segmentedByteString.getDirectory$okio()[segment] - i3) + i3) - i;
            function3.invoke(segmentedByteString.getSegments$okio()[segment], Integer.valueOf(i4 + (i - i3)), Integer.valueOf(min));
            i += min;
            segment++;
        }
    }

    @NotNull
    public static final ByteString commonSubstring(@NotNull SegmentedByteString commonSubstring, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(commonSubstring, "$this$commonSubstring");
        int i3 = 0;
        if (i >= 0) {
            if (i2 <= commonSubstring.size()) {
                int i4 = i2 - i;
                if (!(i4 >= 0)) {
                    throw new IllegalArgumentException(("endIndex=" + i2 + " < beginIndex=" + i).toString());
                } else if (i == 0 && i2 == commonSubstring.size()) {
                    return commonSubstring;
                } else {
                    if (i == i2) {
                        return ByteString.EMPTY;
                    }
                    int segment = segment(commonSubstring, i);
                    int segment2 = segment(commonSubstring, i2 - 1);
                    byte[][] bArr = (byte[][]) ArraysKt.copyOfRange(commonSubstring.getSegments$okio(), segment, segment2 + 1);
                    byte[][] bArr2 = bArr;
                    int[] iArr = new int[bArr2.length * 2];
                    if (segment <= segment2) {
                        int i5 = 0;
                        int i6 = segment;
                        while (true) {
                            iArr[i5] = Math.min(commonSubstring.getDirectory$okio()[i6] - i, i4);
                            i5++;
                            iArr[i5 + bArr2.length] = commonSubstring.getDirectory$okio()[commonSubstring.getSegments$okio().length + i6];
                            if (i6 == segment2) {
                                break;
                            }
                            i6++;
                        }
                    }
                    if (segment != 0) {
                        i3 = commonSubstring.getDirectory$okio()[segment - 1];
                    }
                    int length = bArr2.length;
                    iArr[length] = iArr[length] + (i - i3);
                    return new SegmentedByteString(bArr, iArr);
                }
            } else {
                throw new IllegalArgumentException(("endIndex=" + i2 + " > length(" + commonSubstring.size() + ')').toString());
            }
        } else {
            throw new IllegalArgumentException(("beginIndex=" + i + " < 0").toString());
        }
    }

    public static final byte commonInternalGet(@NotNull SegmentedByteString commonInternalGet, int i) {
        Intrinsics.checkParameterIsNotNull(commonInternalGet, "$this$commonInternalGet");
        Util.checkOffsetAndCount(commonInternalGet.getDirectory$okio()[commonInternalGet.getSegments$okio().length - 1], i, 1L);
        int segment = segment(commonInternalGet, i);
        return commonInternalGet.getSegments$okio()[segment][(i - (segment == 0 ? 0 : commonInternalGet.getDirectory$okio()[segment - 1])) + commonInternalGet.getDirectory$okio()[commonInternalGet.getSegments$okio().length + segment]];
    }

    public static final int commonGetSize(@NotNull SegmentedByteString commonGetSize) {
        Intrinsics.checkParameterIsNotNull(commonGetSize, "$this$commonGetSize");
        return commonGetSize.getDirectory$okio()[commonGetSize.getSegments$okio().length - 1];
    }

    @NotNull
    public static final byte[] commonToByteArray(@NotNull SegmentedByteString commonToByteArray) {
        Intrinsics.checkParameterIsNotNull(commonToByteArray, "$this$commonToByteArray");
        byte[] bArr = new byte[commonToByteArray.size()];
        int length = commonToByteArray.getSegments$okio().length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int i4 = commonToByteArray.getDirectory$okio()[length + i];
            int i5 = commonToByteArray.getDirectory$okio()[i];
            int i6 = i5 - i2;
            ArraysKt.copyInto(commonToByteArray.getSegments$okio()[i], bArr, i3, i4, i4 + i6);
            i3 += i6;
            i++;
            i2 = i5;
        }
        return bArr;
    }

    public static final boolean commonRangeEquals(@NotNull SegmentedByteString commonRangeEquals, int i, @NotNull ByteString other, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (i < 0 || i > commonRangeEquals.size() - i3) {
            return false;
        }
        int i4 = i3 + i;
        int segment = segment(commonRangeEquals, i);
        while (i < i4) {
            int i5 = segment == 0 ? 0 : commonRangeEquals.getDirectory$okio()[segment - 1];
            int i6 = commonRangeEquals.getDirectory$okio()[commonRangeEquals.getSegments$okio().length + segment];
            int min = Math.min(i4, (commonRangeEquals.getDirectory$okio()[segment] - i5) + i5) - i;
            if (!other.rangeEquals(i2, commonRangeEquals.getSegments$okio()[segment], i6 + (i - i5), min)) {
                return false;
            }
            i2 += min;
            i += min;
            segment++;
        }
        return true;
    }

    public static final boolean commonRangeEquals(@NotNull SegmentedByteString commonRangeEquals, int i, @NotNull byte[] other, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (i < 0 || i > commonRangeEquals.size() - i3 || i2 < 0 || i2 > other.length - i3) {
            return false;
        }
        int i4 = i3 + i;
        int segment = segment(commonRangeEquals, i);
        while (i < i4) {
            int i5 = segment == 0 ? 0 : commonRangeEquals.getDirectory$okio()[segment - 1];
            int i6 = commonRangeEquals.getDirectory$okio()[commonRangeEquals.getSegments$okio().length + segment];
            int min = Math.min(i4, (commonRangeEquals.getDirectory$okio()[segment] - i5) + i5) - i;
            if (!Util.arrayRangeEquals(commonRangeEquals.getSegments$okio()[segment], i6 + (i - i5), other, i2, min)) {
                return false;
            }
            i2 += min;
            i += min;
            segment++;
        }
        return true;
    }

    public static final boolean commonEquals(@NotNull SegmentedByteString commonEquals, @Nullable Object obj) {
        Intrinsics.checkParameterIsNotNull(commonEquals, "$this$commonEquals");
        if (obj == commonEquals) {
            return true;
        }
        if (!(obj instanceof ByteString)) {
            return false;
        }
        ByteString byteString = (ByteString) obj;
        return byteString.size() == commonEquals.size() && commonEquals.rangeEquals(0, byteString, 0, commonEquals.size());
    }

    public static final int commonHashCode(@NotNull SegmentedByteString commonHashCode) {
        Intrinsics.checkParameterIsNotNull(commonHashCode, "$this$commonHashCode");
        int hashCode$okio = commonHashCode.getHashCode$okio();
        if (hashCode$okio != 0) {
            return hashCode$okio;
        }
        int length = commonHashCode.getSegments$okio().length;
        int i = 0;
        int i2 = 1;
        int i3 = 0;
        while (i < length) {
            int i4 = commonHashCode.getDirectory$okio()[length + i];
            int i5 = commonHashCode.getDirectory$okio()[i];
            byte[] bArr = commonHashCode.getSegments$okio()[i];
            int i6 = (i5 - i3) + i4;
            while (i4 < i6) {
                i2 = (i2 * 31) + bArr[i4];
                i4++;
            }
            i++;
            i3 = i5;
        }
        commonHashCode.setHashCode$okio(i2);
        return i2;
    }

    public static final void commonWrite(@NotNull SegmentedByteString commonWrite, @NotNull Buffer buffer, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(commonWrite, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        int i3 = i2 + i;
        int segment = segment(commonWrite, i);
        while (i < i3) {
            int i4 = segment == 0 ? 0 : commonWrite.getDirectory$okio()[segment - 1];
            int i5 = commonWrite.getDirectory$okio()[commonWrite.getSegments$okio().length + segment];
            int min = Math.min(i3, (commonWrite.getDirectory$okio()[segment] - i4) + i4) - i;
            int i6 = i5 + (i - i4);
            Segment segment2 = new Segment(commonWrite.getSegments$okio()[segment], i6, i6 + min, true, false);
            if (buffer.head == null) {
                segment2.prev = segment2;
                segment2.next = segment2.prev;
                buffer.head = segment2.next;
            } else {
                Segment segment3 = buffer.head;
                if (segment3 == null) {
                    Intrinsics.throwNpe();
                }
                Segment segment4 = segment3.prev;
                if (segment4 == null) {
                    Intrinsics.throwNpe();
                }
                segment4.push(segment2);
            }
            i += min;
            segment++;
        }
        buffer.setSize$okio(buffer.size() + commonWrite.size());
    }
}
