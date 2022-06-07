package kotlin.time;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: measureTime.kt */
@SinceKotlin(version = "1.3")
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0018\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u0016\u0010\u000e\u001a\u00020\u0005HÆ\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u000f\u0010\bJ-\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0019\u0010\u0004\u001a\u00020\u0005ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u001a"}, d2 = {"Lkotlin/time/TimedValue;", ExifInterface.GPS_DIRECTION_TRUE, "", b.p, "duration", "Lkotlin/time/Duration;", "(Ljava/lang/Object;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getDuration-UwyO8pc", "()J", "J", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "component2-UwyO8pc", "copy", "copy-RFiDyg4", "(Ljava/lang/Object;J)Lkotlin/time/TimedValue;", "equals", "", "other", "hashCode", "", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
@ExperimentalTime
/* loaded from: classes5.dex */
public final class TimedValue<T> {
    private final T a;
    private final long b;

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: copy-RFiDyg4$default  reason: not valid java name */
    public static /* synthetic */ TimedValue m1747copyRFiDyg4$default(TimedValue timedValue, Object obj, long j, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = timedValue.a;
        }
        if ((i & 2) != 0) {
            j = timedValue.b;
        }
        return timedValue.m1749copyRFiDyg4(obj, j);
    }

    public final T component1() {
        return this.a;
    }

    /* renamed from: component2-UwyO8pc  reason: not valid java name */
    public final long m1748component2UwyO8pc() {
        return this.b;
    }

    @NotNull
    /* renamed from: copy-RFiDyg4  reason: not valid java name */
    public final TimedValue<T> m1749copyRFiDyg4(T t, long j) {
        return new TimedValue<>(t, j);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimedValue)) {
            return false;
        }
        TimedValue timedValue = (TimedValue) obj;
        return Intrinsics.areEqual(this.a, timedValue.a) && this.b == timedValue.b;
    }

    public int hashCode() {
        T t = this.a;
        int hashCode = t != null ? t.hashCode() : 0;
        long j = this.b;
        return (hashCode * 31) + ((int) (j ^ (j >>> 32)));
    }

    @NotNull
    public String toString() {
        return "TimedValue(value=" + this.a + ", duration=" + Duration.m1715toStringimpl(this.b) + ")";
    }

    private TimedValue(T t, long j) {
        this.a = t;
        this.b = j;
    }

    public /* synthetic */ TimedValue(Object obj, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, j);
    }

    /* renamed from: getDuration-UwyO8pc  reason: not valid java name */
    public final long m1750getDurationUwyO8pc() {
        return this.b;
    }

    public final T getValue() {
        return this.a;
    }
}
