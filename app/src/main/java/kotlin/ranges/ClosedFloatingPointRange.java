package kotlin.ranges;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.api.b;
import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Ranges.kt */
@SinceKotlin(version = "1.1")
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\bg\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0005H\u0016J\u001d\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lkotlin/ranges/ClosedFloatingPointRange;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlin/ranges/ClosedRange;", "contains", "", b.p, "(Ljava/lang/Comparable;)Z", "isEmpty", "lessThanOrEquals", ai.at, "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Z", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public interface ClosedFloatingPointRange<T extends Comparable<? super T>> extends ClosedRange<T> {
    @Override // kotlin.ranges.ClosedRange
    boolean contains(@NotNull T t);

    @Override // kotlin.ranges.ClosedRange
    boolean isEmpty();

    boolean lessThanOrEquals(@NotNull T t, @NotNull T t2);

    /* compiled from: Ranges.kt */
    @Metadata(k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class DefaultImpls {
        public static <T extends Comparable<? super T>> boolean contains(@NotNull ClosedFloatingPointRange<T> closedFloatingPointRange, @NotNull T value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return closedFloatingPointRange.lessThanOrEquals(closedFloatingPointRange.getStart(), value) && closedFloatingPointRange.lessThanOrEquals(value, closedFloatingPointRange.getEndInclusive());
        }

        public static <T extends Comparable<? super T>> boolean isEmpty(@NotNull ClosedFloatingPointRange<T> closedFloatingPointRange) {
            return !closedFloatingPointRange.lessThanOrEquals(closedFloatingPointRange.getStart(), closedFloatingPointRange.getEndInclusive());
        }
    }
}
