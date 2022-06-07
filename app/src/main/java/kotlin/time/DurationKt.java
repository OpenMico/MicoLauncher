package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: Duration.kt */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0005H\u0003ø\u0001\u0000¢\u0006\u0002\u0010&\u001a\u0018\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0010\u0010/\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0001H\u0002\u001a\u0010\u00100\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u0001H\u0002\u001a\u001f\u00101\u001a\u00020\u0007*\u00020\b2\u0006\u00102\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u00104\u001a\u001f\u00101\u001a\u00020\u0007*\u00020\u00052\u0006\u00102\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a \u00107\u001a\u00020\u0007*\u00020\b2\n\u00108\u001a\u000609j\u0002`:H\u0007ø\u0001\u0000¢\u0006\u0002\u0010;\u001a \u00107\u001a\u00020\u0007*\u00020\u00052\n\u00108\u001a\u000609j\u0002`:H\u0007ø\u0001\u0000¢\u0006\u0002\u0010<\u001a \u00107\u001a\u00020\u0007*\u00020\u00012\n\u00108\u001a\u000609j\u0002`:H\u0007ø\u0001\u0000¢\u0006\u0002\u0010=\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\r\u001a\u0004\b\u000b\u0010\u000e\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u000f\u001a\u0004\b\u000b\u0010\u0010\"!\u0010\u0011\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\n\u001a\u0004\b\u0013\u0010\f\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000e\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0010\"!\u0010\u0014\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\n\u001a\u0004\b\u0016\u0010\f\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\r\u001a\u0004\b\u0016\u0010\u000e\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\u000f\u001a\u0004\b\u0016\u0010\u0010\"!\u0010\u0017\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\n\u001a\u0004\b\u0019\u0010\f\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\r\u001a\u0004\b\u0019\u0010\u000e\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0019\u0010\u0010\"!\u0010\u001a\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\n\u001a\u0004\b\u001c\u0010\f\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\r\u001a\u0004\b\u001c\u0010\u000e\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0010\"!\u0010\u001d\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\n\u001a\u0004\b\u001f\u0010\f\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\r\u001a\u0004\b\u001f\u0010\u000e\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\u000f\u001a\u0004\b\u001f\u0010\u0010\"!\u0010 \u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\n\u001a\u0004\b\"\u0010\f\"!\u0010 \u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\r\u001a\u0004\b\"\u0010\u000e\"!\u0010 \u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\u000f\u001a\u0004\b\"\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006>"}, d2 = {"MAX_MILLIS", "", "MAX_NANOS", "MAX_NANOS_IN_MILLIS", "NANOS_IN_MILLIS", "", "days", "Lkotlin/time/Duration;", "", "getDays$annotations", "(D)V", "getDays", "(D)J", "(I)V", "(I)J", "(J)V", "(J)J", "hours", "getHours$annotations", "getHours", "microseconds", "getMicroseconds$annotations", "getMicroseconds", "milliseconds", "getMilliseconds$annotations", "getMilliseconds", "minutes", "getMinutes$annotations", "getMinutes", "nanoseconds", "getNanoseconds$annotations", "getNanoseconds", "seconds", "getSeconds$annotations", "getSeconds", "durationOf", "normalValue", "unitDiscriminator", "(JI)J", "durationOfMillis", "normalMillis", "durationOfMillisNormalized", "millis", "durationOfNanos", "normalNanos", "durationOfNanosNormalized", "nanos", "millisToNanos", "nanosToMillis", "times", "duration", "times-kIfJnKk", "(DJ)J", "times-mvk6XK0", "(IJ)J", "toDuration", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "(DLjava/util/concurrent/TimeUnit;)J", "(ILjava/util/concurrent/TimeUnit;)J", "(JLjava/util/concurrent/TimeUnit;)J", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class DurationKt {
    public static final long MAX_MILLIS = 4611686018427387903L;
    public static final long MAX_NANOS = 4611686018426999999L;
    public static final int NANOS_IN_MILLIS = 1000000;

    /* JADX INFO: Access modifiers changed from: private */
    public static final long b(long j) {
        return j * 1000000;
    }

    @Deprecated(message = "Use Duration.days() function instead.", replaceWith = @ReplaceWith(expression = "Duration.days(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getDays$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.days() function instead.", replaceWith = @ReplaceWith(expression = "Duration.days(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getDays$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.days() function instead.", replaceWith = @ReplaceWith(expression = "Duration.days(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getDays$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.hours() function instead.", replaceWith = @ReplaceWith(expression = "Duration.hours(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getHours$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.hours() function instead.", replaceWith = @ReplaceWith(expression = "Duration.hours(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getHours$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.hours() function instead.", replaceWith = @ReplaceWith(expression = "Duration.hours(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getHours$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.microseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.microseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMicroseconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.microseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.microseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMicroseconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.microseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.microseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMicroseconds$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.milliseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.milliseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMilliseconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.milliseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.milliseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMilliseconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.milliseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.milliseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMilliseconds$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.minutes() function instead.", replaceWith = @ReplaceWith(expression = "Duration.minutes(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMinutes$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.minutes() function instead.", replaceWith = @ReplaceWith(expression = "Duration.minutes(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMinutes$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.minutes() function instead.", replaceWith = @ReplaceWith(expression = "Duration.minutes(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getMinutes$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.nanoseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.nanoseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getNanoseconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.nanoseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.nanoseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getNanoseconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.nanoseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.nanoseconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getNanoseconds$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.seconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.seconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getSeconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.seconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.seconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getSeconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.seconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.seconds(this)", imports = {"kotlin.time.Duration"}))
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static /* synthetic */ void getSeconds$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static final long toDuration(int i, @NotNull TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (unit.compareTo(TimeUnit.SECONDS) <= 0) {
            return c(DurationUnitKt.convertDurationUnitOverflow(i, unit, TimeUnit.NANOSECONDS));
        }
        return toDuration(i, unit);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static final long toDuration(long j, @NotNull TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        long convertDurationUnitOverflow = DurationUnitKt.convertDurationUnitOverflow(MAX_NANOS, TimeUnit.NANOSECONDS, unit);
        if ((-convertDurationUnitOverflow) <= j && convertDurationUnitOverflow >= j) {
            return c(DurationUnitKt.convertDurationUnitOverflow(j, unit, TimeUnit.NANOSECONDS));
        }
        return d(RangesKt.coerceIn(DurationUnitKt.convertDurationUnit(j, unit, TimeUnit.MILLISECONDS), -4611686018427387903L, (long) MAX_MILLIS));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    public static final long toDuration(double d, @NotNull TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        double convertDurationUnit = DurationUnitKt.convertDurationUnit(d, unit, TimeUnit.NANOSECONDS);
        if (!Double.isNaN(convertDurationUnit)) {
            long j = (long) convertDurationUnit;
            if (-4611686018426999999L <= j && MAX_NANOS >= j) {
                return c(j);
            }
            return f((long) DurationUnitKt.convertDurationUnit(d, unit, TimeUnit.MILLISECONDS));
        }
        throw new IllegalArgumentException("Duration value cannot be NaN.".toString());
    }

    public static final long getNanoseconds(int i) {
        return toDuration(i, TimeUnit.NANOSECONDS);
    }

    public static final long getNanoseconds(long j) {
        return toDuration(j, TimeUnit.NANOSECONDS);
    }

    public static final long getNanoseconds(double d) {
        return toDuration(d, TimeUnit.NANOSECONDS);
    }

    public static final long getMicroseconds(int i) {
        return toDuration(i, TimeUnit.MICROSECONDS);
    }

    public static final long getMicroseconds(long j) {
        return toDuration(j, TimeUnit.MICROSECONDS);
    }

    public static final long getMicroseconds(double d) {
        return toDuration(d, TimeUnit.MICROSECONDS);
    }

    public static final long getMilliseconds(int i) {
        return toDuration(i, TimeUnit.MILLISECONDS);
    }

    public static final long getMilliseconds(long j) {
        return toDuration(j, TimeUnit.MILLISECONDS);
    }

    public static final long getMilliseconds(double d) {
        return toDuration(d, TimeUnit.MILLISECONDS);
    }

    public static final long getSeconds(int i) {
        return toDuration(i, TimeUnit.SECONDS);
    }

    public static final long getSeconds(long j) {
        return toDuration(j, TimeUnit.SECONDS);
    }

    public static final long getSeconds(double d) {
        return toDuration(d, TimeUnit.SECONDS);
    }

    public static final long getMinutes(int i) {
        return toDuration(i, TimeUnit.MINUTES);
    }

    public static final long getMinutes(long j) {
        return toDuration(j, TimeUnit.MINUTES);
    }

    public static final long getMinutes(double d) {
        return toDuration(d, TimeUnit.MINUTES);
    }

    public static final long getHours(int i) {
        return toDuration(i, TimeUnit.HOURS);
    }

    public static final long getHours(long j) {
        return toDuration(j, TimeUnit.HOURS);
    }

    public static final long getHours(double d) {
        return toDuration(d, TimeUnit.HOURS);
    }

    public static final long getDays(int i) {
        return toDuration(i, TimeUnit.DAYS);
    }

    public static final long getDays(long j) {
        return toDuration(j, TimeUnit.DAYS);
    }

    public static final long getDays(double d) {
        return toDuration(d, TimeUnit.DAYS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long a(long j) {
        return j / 1000000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ExperimentalTime
    public static final long c(long j) {
        return Duration.m1671constructorimpl(j << 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ExperimentalTime
    public static final long d(long j) {
        return Duration.m1671constructorimpl((j << 1) + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ExperimentalTime
    public static final long a(long j, int i) {
        return Duration.m1671constructorimpl((j << 1) + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ExperimentalTime
    public static final long e(long j) {
        if (-4611686018426999999L <= j && MAX_NANOS >= j) {
            return c(j);
        }
        return d(a(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ExperimentalTime
    public static final long f(long j) {
        if (-4611686018426L <= j && 4611686018426L >= j) {
            return c(b(j));
        }
        return d(RangesKt.coerceIn(j, -4611686018427387903L, (long) MAX_MILLIS));
    }
}
