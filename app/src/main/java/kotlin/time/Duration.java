package kotlin.time;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.b;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: Duration.kt */
@SinceKotlin(version = "1.3")
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b4\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0087@\u0018\u0000 ¥\u00012\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002¥\u0001B\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J%\u0010K\u001a\u00020\u00002\u0006\u0010L\u001a\u00020\u00032\u0006\u0010M\u001a\u00020\u0003H\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bN\u0010OJ\u001b\u0010P\u001a\u00020\t2\u0006\u0010Q\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\bR\u0010SJ\u001e\u0010T\u001a\u00020\u00002\u0006\u0010U\u001a\u00020\u000fH\u0086\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bV\u0010WJ\u001e\u0010T\u001a\u00020\u00002\u0006\u0010U\u001a\u00020\tH\u0086\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bV\u0010XJ\u001b\u0010T\u001a\u00020\u000f2\u0006\u0010Q\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bY\u0010ZJ\u001a\u0010[\u001a\u00020\\2\b\u0010Q\u001a\u0004\u0018\u00010]HÖ\u0003¢\u0006\u0004\b^\u0010_J\u0010\u0010`\u001a\u00020\tHÖ\u0001¢\u0006\u0004\ba\u0010\rJ\r\u0010b\u001a\u00020\\¢\u0006\u0004\bc\u0010dJ\u000f\u0010e\u001a\u00020\\H\u0002¢\u0006\u0004\bf\u0010dJ\u000f\u0010g\u001a\u00020\\H\u0002¢\u0006\u0004\bh\u0010dJ\r\u0010i\u001a\u00020\\¢\u0006\u0004\bj\u0010dJ\r\u0010k\u001a\u00020\\¢\u0006\u0004\bl\u0010dJ\r\u0010m\u001a\u00020\\¢\u0006\u0004\bn\u0010dJ\u001b\u0010o\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bp\u0010qJ\u001b\u0010r\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bs\u0010qJ\u0017\u0010t\u001a\u00020\t2\u0006\u0010I\u001a\u00020\u000fH\u0002¢\u0006\u0004\bu\u0010vJ\u001e\u0010w\u001a\u00020\u00002\u0006\u0010U\u001a\u00020\u000fH\u0086\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bx\u0010WJ\u001e\u0010w\u001a\u00020\u00002\u0006\u0010U\u001a\u00020\tH\u0086\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bx\u0010XJ£\u0001\u0010y\u001a\u0002Hz\"\u0004\b\u0000\u0010z2y\u0010{\u001au\u0012\u0013\u0012\u00110\t¢\u0006\f\b}\u0012\b\b~\u0012\u0004\b\b(\u007f\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0080\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0081\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0082\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0083\u0001\u0012\u0004\u0012\u0002Hz0|H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0006\b\u0084\u0001\u0010\u0085\u0001J\u008f\u0001\u0010y\u001a\u0002Hz\"\u0004\b\u0000\u0010z2e\u0010{\u001aa\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0080\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0081\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0082\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0083\u0001\u0012\u0004\u0012\u0002Hz0\u0086\u0001H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0006\b\u0084\u0001\u0010\u0087\u0001Jy\u0010y\u001a\u0002Hz\"\u0004\b\u0000\u0010z2O\u0010{\u001aK\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0081\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0082\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0083\u0001\u0012\u0004\u0012\u0002Hz0\u0088\u0001H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0006\b\u0084\u0001\u0010\u0089\u0001Jc\u0010y\u001a\u0002Hz\"\u0004\b\u0000\u0010z29\u0010{\u001a5\u0012\u0014\u0012\u00120\u0003¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0082\u0001\u0012\u0014\u0012\u00120\t¢\u0006\r\b}\u0012\t\b~\u0012\u0005\b\b(\u0083\u0001\u0012\u0004\u0012\u0002Hz0\u008a\u0001H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0006\b\u0084\u0001\u0010\u008b\u0001J\u001e\u0010\u008c\u0001\u001a\u00020\u000f2\f\u0010\u008d\u0001\u001a\u00070Dj\u0003`\u008e\u0001¢\u0006\u0006\b\u008f\u0001\u0010\u0090\u0001J\u001e\u0010\u0091\u0001\u001a\u00020\t2\f\u0010\u008d\u0001\u001a\u00070Dj\u0003`\u008e\u0001¢\u0006\u0006\b\u0092\u0001\u0010\u0093\u0001J\u0011\u0010\u0094\u0001\u001a\u00030\u0095\u0001¢\u0006\u0006\b\u0096\u0001\u0010\u0097\u0001J\u001e\u0010\u0098\u0001\u001a\u00020\u00032\f\u0010\u008d\u0001\u001a\u00070Dj\u0003`\u008e\u0001¢\u0006\u0006\b\u0099\u0001\u0010\u009a\u0001J\u0011\u0010\u009b\u0001\u001a\u00020\u0003H\u0007¢\u0006\u0005\b\u009c\u0001\u0010\u0005J\u0011\u0010\u009d\u0001\u001a\u00020\u0003H\u0007¢\u0006\u0005\b\u009e\u0001\u0010\u0005J\u0013\u0010\u009f\u0001\u001a\u00030\u0095\u0001H\u0016¢\u0006\u0006\b \u0001\u0010\u0097\u0001J*\u0010\u009f\u0001\u001a\u00030\u0095\u00012\f\u0010\u008d\u0001\u001a\u00070Dj\u0003`\u008e\u00012\t\b\u0002\u0010¡\u0001\u001a\u00020\t¢\u0006\u0006\b \u0001\u0010¢\u0001J\u0018\u0010£\u0001\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0005\b¤\u0001\u0010\u0005R\u0017\u0010\u0006\u001a\u00020\u00008Fø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0015\u0010\u0012R\u001a\u0010\u0016\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\u0012R\u001a\u0010\u001c\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\u001f\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\u0012R\u001a\u0010\"\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\u0012R\u001a\u0010%\u001a\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b&\u0010\u000b\u001a\u0004\b'\u0010\u0005R\u001a\u0010(\u001a\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b)\u0010\u000b\u001a\u0004\b*\u0010\u0005R\u001a\u0010+\u001a\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b,\u0010\u000b\u001a\u0004\b-\u0010\u0005R\u001a\u0010.\u001a\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b/\u0010\u000b\u001a\u0004\b0\u0010\u0005R\u001a\u00101\u001a\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b2\u0010\u000b\u001a\u0004\b3\u0010\u0005R\u001a\u00104\u001a\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b5\u0010\u000b\u001a\u0004\b6\u0010\u0005R\u001a\u00107\u001a\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b8\u0010\u000b\u001a\u0004\b9\u0010\u0005R\u001a\u0010:\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b;\u0010\u000b\u001a\u0004\b<\u0010\rR\u001a\u0010=\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b>\u0010\u000b\u001a\u0004\b?\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\bA\u0010\u000b\u001a\u0004\bB\u0010\rR\u0014\u0010C\u001a\u00020D8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0015\u0010G\u001a\u00020\t8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\bH\u0010\rR\u0014\u0010I\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010\u0005\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001\u0000\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u009920\u0001¨\u0006¦\u0001"}, d2 = {"Lkotlin/time/Duration;", "", "rawValue", "", "constructor-impl", "(J)J", "absoluteValue", "getAbsoluteValue-UwyO8pc", "hoursComponent", "", "getHoursComponent$annotations", "()V", "getHoursComponent-impl", "(J)I", "inDays", "", "getInDays$annotations", "getInDays-impl", "(J)D", "inHours", "getInHours$annotations", "getInHours-impl", "inMicroseconds", "getInMicroseconds$annotations", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds$annotations", "getInMilliseconds-impl", "inMinutes", "getInMinutes$annotations", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds$annotations", "getInNanoseconds-impl", "inSeconds", "getInSeconds$annotations", "getInSeconds-impl", "inWholeDays", "getInWholeDays$annotations", "getInWholeDays-impl", "inWholeHours", "getInWholeHours$annotations", "getInWholeHours-impl", "inWholeMicroseconds", "getInWholeMicroseconds$annotations", "getInWholeMicroseconds-impl", "inWholeMilliseconds", "getInWholeMilliseconds$annotations", "getInWholeMilliseconds-impl", "inWholeMinutes", "getInWholeMinutes$annotations", "getInWholeMinutes-impl", "inWholeNanoseconds", "getInWholeNanoseconds$annotations", "getInWholeNanoseconds-impl", "inWholeSeconds", "getInWholeSeconds$annotations", "getInWholeSeconds-impl", "minutesComponent", "getMinutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "getNanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "getSecondsComponent$annotations", "getSecondsComponent-impl", "storageUnit", "Ljava/util/concurrent/TimeUnit;", "getStorageUnit-impl", "(J)Ljava/util/concurrent/TimeUnit;", "unitDiscriminator", "getUnitDiscriminator-impl", b.p, "getValue-impl", "addValuesMixedRanges", "thisMillis", "otherNanos", "addValuesMixedRanges-UwyO8pc", "(JJJ)J", "compareTo", "other", "compareTo-LRDsOJo", "(JJ)I", "div", "scale", "div-UwyO8pc", "(JD)J", "(JI)J", "div-LRDsOJo", "(JJ)D", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "isFinite", "isFinite-impl", "(J)Z", "isInMillis", "isInMillis-impl", "isInNanos", "isInNanos-impl", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "(JJ)J", "plus", "plus-LRDsOJo", "precision", "precision-impl", "(JD)I", "times", "times-UwyO8pc", "toComponents", ExifInterface.GPS_DIRECTION_TRUE, "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(JLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(JLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(JLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "Lkotlin/time/DurationUnit;", "toDouble-impl", "(JLjava/util/concurrent/TimeUnit;)D", "toInt", "toInt-impl", "(JLjava/util/concurrent/TimeUnit;)I", "toIsoString", "", "toIsoString-impl", "(J)Ljava/lang/String;", "toLong", "toLong-impl", "(JLjava/util/concurrent/TimeUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(JLjava/util/concurrent/TimeUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-UwyO8pc", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
@JvmInline
@ExperimentalTime
/* loaded from: classes5.dex */
public final class Duration implements Comparable<Duration> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final long b = m1671constructorimpl(0);
    private static final long c;
    private static final long d;
    private final long a;

    private static final int a(long j, double d2) {
        if (d2 < 1) {
            return 3;
        }
        if (d2 < 10) {
            return 2;
        }
        return d2 < ((double) 100) ? 1 : 0;
    }

    private static final long a(long j) {
        return j >> 1;
    }

    private static final boolean b(long j) {
        return (((int) j) & 1) == 0;
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ Duration m1669boximpl(long j) {
        return new Duration(j);
    }

    private static final boolean c(long j) {
        return (((int) j) & 1) == 1;
    }

    /* renamed from: equals-impl */
    public static boolean m1675equalsimpl(long j, Object obj) {
        return (obj instanceof Duration) && j == ((Duration) obj).m1720unboximpl();
    }

    /* renamed from: equals-impl0 */
    public static final boolean m1676equalsimpl0(long j, long j2) {
        return j == j2;
    }

    @PublishedApi
    public static /* synthetic */ void getHoursComponent$annotations() {
    }

    @Deprecated(message = "Use inWholeDays property instead or convert toDouble(DAYS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.DAYS)", imports = {}))
    public static /* synthetic */ void getInDays$annotations() {
    }

    @Deprecated(message = "Use inWholeHours property instead or convert toDouble(HOURS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.HOURS)", imports = {}))
    public static /* synthetic */ void getInHours$annotations() {
    }

    @Deprecated(message = "Use inWholeMicroseconds property instead or convert toDouble(MICROSECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MICROSECONDS)", imports = {}))
    public static /* synthetic */ void getInMicroseconds$annotations() {
    }

    @Deprecated(message = "Use inWholeMilliseconds property instead or convert toDouble(MILLISECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MILLISECONDS)", imports = {}))
    public static /* synthetic */ void getInMilliseconds$annotations() {
    }

    @Deprecated(message = "Use inWholeMinutes property instead or convert toDouble(MINUTES) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MINUTES)", imports = {}))
    public static /* synthetic */ void getInMinutes$annotations() {
    }

    @Deprecated(message = "Use inWholeNanoseconds property instead or convert toDouble(NANOSECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.NANOSECONDS)", imports = {}))
    public static /* synthetic */ void getInNanoseconds$annotations() {
    }

    @Deprecated(message = "Use inWholeSeconds property instead or convert toDouble(SECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.SECONDS)", imports = {}))
    public static /* synthetic */ void getInSeconds$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void getInWholeDays$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void getInWholeHours$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void getInWholeMicroseconds$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void getInWholeMilliseconds$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void getInWholeMinutes$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void getInWholeNanoseconds$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void getInWholeSeconds$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getMinutesComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getNanosecondsComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getSecondsComponent$annotations() {
    }

    /* renamed from: hashCode-impl */
    public static int m1696hashCodeimpl(long j) {
        return (int) (j ^ (j >>> 32));
    }

    /* renamed from: isNegative-impl */
    public static final boolean m1699isNegativeimpl(long j) {
        return j < 0;
    }

    /* renamed from: isPositive-impl */
    public static final boolean m1700isPositiveimpl(long j) {
        return j > 0;
    }

    /* renamed from: compareTo-LRDsOJo */
    public int m1719compareToLRDsOJo(long j) {
        return m1670compareToLRDsOJo(this.a, j);
    }

    public boolean equals(Object obj) {
        return m1675equalsimpl(this.a, obj);
    }

    public int hashCode() {
        return m1696hashCodeimpl(this.a);
    }

    @NotNull
    public String toString() {
        return m1715toStringimpl(this.a);
    }

    /* renamed from: unbox-impl */
    public final /* synthetic */ long m1720unboximpl() {
        return this.a;
    }

    private /* synthetic */ Duration(long j) {
        this.a = j;
    }

    @Override // java.lang.Comparable
    public /* synthetic */ int compareTo(Duration duration) {
        return m1719compareToLRDsOJo(duration.m1720unboximpl());
    }

    private static final TimeUnit d(long j) {
        return b(j) ? TimeUnit.NANOSECONDS : TimeUnit.MILLISECONDS;
    }

    /* renamed from: constructor-impl */
    public static long m1671constructorimpl(long j) {
        if (b(j)) {
            long a = a(j);
            if (-4611686018426999999L > a || DurationKt.MAX_NANOS < a) {
                throw new AssertionError(a(j) + " ns is out of nanoseconds range");
            }
        } else {
            long a2 = a(j);
            if (-4611686018427387903L > a2 || DurationKt.MAX_MILLIS < a2) {
                throw new AssertionError(a(j) + " ms is out of milliseconds range");
            }
            long a3 = a(j);
            if (-4611686018426L <= a3 && 4611686018426L >= a3) {
                throw new AssertionError(a(j) + " ms is denormalized");
            }
        }
        return j;
    }

    /* compiled from: Duration.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u000e\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\n\u0010\u000f\u001a\u00060\u0010j\u0002`\u00112\n\u0010\u0012\u001a\u00060\u0010j\u0002`\u0011J\u001d\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0014\u0010\u0015J\u001d\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0016H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0014\u0010\u0017J\u001d\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0018H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0014\u0010\u0019J\u001d\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001b\u0010\u0015J\u001d\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0016H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001b\u0010\u0017J\u001d\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0018H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001b\u0010\u0019J\u001d\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001d\u0010\u0015J\u001d\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0016H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001d\u0010\u0017J\u001d\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0018H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001d\u0010\u0019J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001f\u0010\u0015J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0016H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001f\u0010\u0017J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0018H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001f\u0010\u0019J\u001d\u0010 \u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b!\u0010\u0015J\u001d\u0010 \u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0016H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b!\u0010\u0017J\u001d\u0010 \u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0018H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b!\u0010\u0019J\u001d\u0010\"\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b#\u0010\u0015J\u001d\u0010\"\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0016H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b#\u0010\u0017J\u001d\u0010\"\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0018H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b#\u0010\u0019J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\rH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b%\u0010\u0015J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0016H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b%\u0010\u0017J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0018H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b%\u0010\u0019R\u0019\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\b\u001a\u00020\u0004X\u0080\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006R\u0019\u0010\n\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u000b\u0010\u0006\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006&"}, d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE-UwyO8pc", "()J", "J", "NEG_INFINITE", "getNEG_INFINITE-UwyO8pc$kotlin_stdlib", "ZERO", "getZERO-UwyO8pc", "convert", "", b.p, "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "days", "days-UwyO8pc", "(D)J", "", "(I)J", "", "(J)J", "hours", "hours-UwyO8pc", "microseconds", "microseconds-UwyO8pc", "milliseconds", "milliseconds-UwyO8pc", "minutes", "minutes-UwyO8pc", "nanoseconds", "nanoseconds-UwyO8pc", "seconds", "seconds-UwyO8pc", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getZERO-UwyO8pc */
        public final long m1726getZEROUwyO8pc() {
            return Duration.b;
        }

        /* renamed from: getINFINITE-UwyO8pc */
        public final long m1724getINFINITEUwyO8pc() {
            return Duration.c;
        }

        /* renamed from: getNEG_INFINITE-UwyO8pc$kotlin_stdlib */
        public final long m1725getNEG_INFINITEUwyO8pc$kotlin_stdlib() {
            return Duration.d;
        }

        public final double convert(double d, @NotNull TimeUnit sourceUnit, @NotNull TimeUnit targetUnit) {
            Intrinsics.checkNotNullParameter(sourceUnit, "sourceUnit");
            Intrinsics.checkNotNullParameter(targetUnit, "targetUnit");
            return DurationUnitKt.convertDurationUnit(d, sourceUnit, targetUnit);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: nanoseconds-UwyO8pc */
        public final long m1740nanosecondsUwyO8pc(int i) {
            return DurationKt.toDuration(i, TimeUnit.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: nanoseconds-UwyO8pc */
        public final long m1741nanosecondsUwyO8pc(long j) {
            return DurationKt.toDuration(j, TimeUnit.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: nanoseconds-UwyO8pc */
        public final long m1739nanosecondsUwyO8pc(double d) {
            return DurationKt.toDuration(d, TimeUnit.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: microseconds-UwyO8pc */
        public final long m1731microsecondsUwyO8pc(int i) {
            return DurationKt.toDuration(i, TimeUnit.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: microseconds-UwyO8pc */
        public final long m1732microsecondsUwyO8pc(long j) {
            return DurationKt.toDuration(j, TimeUnit.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: microseconds-UwyO8pc */
        public final long m1730microsecondsUwyO8pc(double d) {
            return DurationKt.toDuration(d, TimeUnit.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: milliseconds-UwyO8pc */
        public final long m1734millisecondsUwyO8pc(int i) {
            return DurationKt.toDuration(i, TimeUnit.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: milliseconds-UwyO8pc */
        public final long m1735millisecondsUwyO8pc(long j) {
            return DurationKt.toDuration(j, TimeUnit.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: milliseconds-UwyO8pc */
        public final long m1733millisecondsUwyO8pc(double d) {
            return DurationKt.toDuration(d, TimeUnit.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: seconds-UwyO8pc */
        public final long m1743secondsUwyO8pc(int i) {
            return DurationKt.toDuration(i, TimeUnit.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: seconds-UwyO8pc */
        public final long m1744secondsUwyO8pc(long j) {
            return DurationKt.toDuration(j, TimeUnit.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: seconds-UwyO8pc */
        public final long m1742secondsUwyO8pc(double d) {
            return DurationKt.toDuration(d, TimeUnit.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: minutes-UwyO8pc */
        public final long m1737minutesUwyO8pc(int i) {
            return DurationKt.toDuration(i, TimeUnit.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: minutes-UwyO8pc */
        public final long m1738minutesUwyO8pc(long j) {
            return DurationKt.toDuration(j, TimeUnit.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: minutes-UwyO8pc */
        public final long m1736minutesUwyO8pc(double d) {
            return DurationKt.toDuration(d, TimeUnit.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: hours-UwyO8pc */
        public final long m1728hoursUwyO8pc(int i) {
            return DurationKt.toDuration(i, TimeUnit.HOURS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: hours-UwyO8pc */
        public final long m1729hoursUwyO8pc(long j) {
            return DurationKt.toDuration(j, TimeUnit.HOURS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: hours-UwyO8pc */
        public final long m1727hoursUwyO8pc(double d) {
            return DurationKt.toDuration(d, TimeUnit.HOURS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: days-UwyO8pc */
        public final long m1722daysUwyO8pc(int i) {
            return DurationKt.toDuration(i, TimeUnit.DAYS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: days-UwyO8pc */
        public final long m1723daysUwyO8pc(long j) {
            return DurationKt.toDuration(j, TimeUnit.DAYS);
        }

        @SinceKotlin(version = "1.5")
        /* renamed from: days-UwyO8pc */
        public final long m1721daysUwyO8pc(double d) {
            return DurationKt.toDuration(d, TimeUnit.DAYS);
        }
    }

    static {
        long d2;
        long d3;
        d2 = DurationKt.d(DurationKt.MAX_MILLIS);
        c = d2;
        d3 = DurationKt.d(-4611686018427387903L);
        d = d3;
    }

    /* renamed from: unaryMinus-UwyO8pc */
    public static final long m1718unaryMinusUwyO8pc(long j) {
        long a;
        a = DurationKt.a(-a(j), ((int) j) & 1);
        return a;
    }

    /* renamed from: plus-LRDsOJo */
    public static final long m1702plusLRDsOJo(long j, long j2) {
        long f;
        long e;
        if (m1698isInfiniteimpl(j)) {
            if (m1697isFiniteimpl(j2) || (j2 ^ j) >= 0) {
                return j;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        } else if (m1698isInfiniteimpl(j2)) {
            return j2;
        } else {
            if ((((int) j) & 1) == (((int) j2) & 1)) {
                long a = a(j) + a(j2);
                if (b(j)) {
                    e = DurationKt.e(a);
                    return e;
                }
                f = DurationKt.f(a);
                return f;
            } else if (c(j)) {
                return a(j, a(j), a(j2));
            } else {
                return a(j, a(j2), a(j));
            }
        }
    }

    private static final long a(long j, long j2, long j3) {
        long a;
        long d2;
        long b2;
        long b3;
        long c2;
        a = DurationKt.a(j3);
        long j4 = j2 + a;
        if (-4611686018426L <= j4 && 4611686018426L >= j4) {
            b2 = DurationKt.b(a);
            long j5 = j3 - b2;
            b3 = DurationKt.b(j4);
            c2 = DurationKt.c(b3 + j5);
            return c2;
        }
        d2 = DurationKt.d(RangesKt.coerceIn(j4, -4611686018427387903L, (long) DurationKt.MAX_MILLIS));
        return d2;
    }

    /* renamed from: minus-LRDsOJo */
    public static final long m1701minusLRDsOJo(long j, long j2) {
        return m1702plusLRDsOJo(j, m1718unaryMinusUwyO8pc(j2));
    }

    /* renamed from: times-UwyO8pc */
    public static final long m1704timesUwyO8pc(long j, int i) {
        long d2;
        long a;
        long b2;
        long a2;
        long d3;
        long e;
        long c2;
        if (m1698isInfiniteimpl(j)) {
            if (i != 0) {
                return i > 0 ? j : m1718unaryMinusUwyO8pc(j);
            }
            throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
        } else if (i == 0) {
            return b;
        } else {
            long a3 = a(j);
            long j2 = i;
            long j3 = a3 * j2;
            if (b(j)) {
                if (-2147483647L <= a3 && 2147483647L >= a3) {
                    c2 = DurationKt.c(j3);
                    return c2;
                } else if (j3 / j2 == a3) {
                    e = DurationKt.e(j3);
                    return e;
                } else {
                    a = DurationKt.a(a3);
                    b2 = DurationKt.b(a);
                    long j4 = a * j2;
                    a2 = DurationKt.a((a3 - b2) * j2);
                    long j5 = a2 + j4;
                    if (j4 / j2 != a || (j5 ^ j4) < 0) {
                        return MathKt.getSign(a3) * MathKt.getSign(i) > 0 ? c : d;
                    }
                    d3 = DurationKt.d(RangesKt.coerceIn(j5, new LongRange(-4611686018427387903L, DurationKt.MAX_MILLIS)));
                    return d3;
                }
            } else if (j3 / j2 != a3) {
                return MathKt.getSign(a3) * MathKt.getSign(i) > 0 ? c : d;
            } else {
                d2 = DurationKt.d(RangesKt.coerceIn(j3, new LongRange(-4611686018427387903L, DurationKt.MAX_MILLIS)));
                return d2;
            }
        }
    }

    /* renamed from: times-UwyO8pc */
    public static final long m1703timesUwyO8pc(long j, double d2) {
        int roundToInt = MathKt.roundToInt(d2);
        if (roundToInt == d2) {
            return m1704timesUwyO8pc(j, roundToInt);
        }
        TimeUnit d3 = d(j);
        return DurationKt.toDuration(m1709toDoubleimpl(j, d3) * d2, d3);
    }

    /* renamed from: div-UwyO8pc */
    public static final long m1674divUwyO8pc(long j, int i) {
        long d2;
        long b2;
        long b3;
        long c2;
        long c3;
        if (i == 0) {
            if (m1700isPositiveimpl(j)) {
                return c;
            }
            if (m1699isNegativeimpl(j)) {
                return d;
            }
            throw new IllegalArgumentException("Dividing zero duration by zero yields an undefined result.");
        } else if (b(j)) {
            c3 = DurationKt.c(a(j) / i);
            return c3;
        } else if (m1698isInfiniteimpl(j)) {
            return m1704timesUwyO8pc(j, MathKt.getSign(i));
        } else {
            long j2 = i;
            long a = a(j) / j2;
            if (-4611686018426L <= a && 4611686018426L >= a) {
                b2 = DurationKt.b(a(j) - (a * j2));
                b3 = DurationKt.b(a);
                c2 = DurationKt.c(b3 + (b2 / j2));
                return c2;
            }
            d2 = DurationKt.d(a);
            return d2;
        }
    }

    /* renamed from: div-UwyO8pc */
    public static final long m1673divUwyO8pc(long j, double d2) {
        int roundToInt = MathKt.roundToInt(d2);
        if (roundToInt == d2 && roundToInt != 0) {
            return m1674divUwyO8pc(j, roundToInt);
        }
        TimeUnit d3 = d(j);
        return DurationKt.toDuration(m1709toDoubleimpl(j, d3) / d2, d3);
    }

    /* renamed from: div-LRDsOJo */
    public static final double m1672divLRDsOJo(long j, long j2) {
        TimeUnit timeUnit = (TimeUnit) ComparisonsKt.maxOf(d(j), d(j2));
        return m1709toDoubleimpl(j, timeUnit) / m1709toDoubleimpl(j2, timeUnit);
    }

    /* renamed from: isInfinite-impl */
    public static final boolean m1698isInfiniteimpl(long j) {
        return j == c || j == d;
    }

    /* renamed from: isFinite-impl */
    public static final boolean m1697isFiniteimpl(long j) {
        return !m1698isInfiniteimpl(j);
    }

    /* renamed from: getAbsoluteValue-UwyO8pc */
    public static final long m1677getAbsoluteValueUwyO8pc(long j) {
        return m1699isNegativeimpl(j) ? m1718unaryMinusUwyO8pc(j) : j;
    }

    /* renamed from: compareTo-LRDsOJo */
    public static int m1670compareToLRDsOJo(long j, long j2) {
        long j3 = j ^ j2;
        if (j3 < 0 || (((int) j3) & 1) == 0) {
            return (j > j2 ? 1 : (j == j2 ? 0 : -1));
        }
        int i = (((int) j) & 1) - (((int) j2) & 1);
        return m1699isNegativeimpl(j) ? -i : i;
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m1708toComponentsimpl(long j, @NotNull Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return (T) action.invoke(Integer.valueOf(m1710toIntimpl(j, TimeUnit.DAYS)), Integer.valueOf(m1678getHoursComponentimpl(j)), Integer.valueOf(m1693getMinutesComponentimpl(j)), Integer.valueOf(m1695getSecondsComponentimpl(j)), Integer.valueOf(m1694getNanosecondsComponentimpl(j)));
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m1707toComponentsimpl(long j, @NotNull Function4<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return (T) action.invoke(Integer.valueOf(m1710toIntimpl(j, TimeUnit.HOURS)), Integer.valueOf(m1693getMinutesComponentimpl(j)), Integer.valueOf(m1695getSecondsComponentimpl(j)), Integer.valueOf(m1694getNanosecondsComponentimpl(j)));
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m1706toComponentsimpl(long j, @NotNull Function3<? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return (T) action.invoke(Integer.valueOf(m1710toIntimpl(j, TimeUnit.MINUTES)), Integer.valueOf(m1695getSecondsComponentimpl(j)), Integer.valueOf(m1694getNanosecondsComponentimpl(j)));
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m1705toComponentsimpl(long j, @NotNull Function2<? super Long, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return (T) action.invoke(Long.valueOf(m1692getInWholeSecondsimpl(j)), Integer.valueOf(m1694getNanosecondsComponentimpl(j)));
    }

    /* renamed from: getHoursComponent-impl */
    public static final int m1678getHoursComponentimpl(long j) {
        if (m1698isInfiniteimpl(j)) {
            return 0;
        }
        return (int) (m1687getInWholeHoursimpl(j) % 24);
    }

    /* renamed from: getMinutesComponent-impl */
    public static final int m1693getMinutesComponentimpl(long j) {
        if (m1698isInfiniteimpl(j)) {
            return 0;
        }
        return (int) (m1690getInWholeMinutesimpl(j) % 60);
    }

    /* renamed from: getSecondsComponent-impl */
    public static final int m1695getSecondsComponentimpl(long j) {
        if (m1698isInfiniteimpl(j)) {
            return 0;
        }
        return (int) (m1692getInWholeSecondsimpl(j) % 60);
    }

    /* renamed from: getNanosecondsComponent-impl */
    public static final int m1694getNanosecondsComponentimpl(long j) {
        long b2;
        if (m1698isInfiniteimpl(j)) {
            return 0;
        }
        if (!c(j)) {
            return (int) (a(j) % 1000000000);
        }
        b2 = DurationKt.b(a(j) % 1000);
        return (int) b2;
    }

    /* renamed from: toDouble-impl */
    public static final double m1709toDoubleimpl(long j, @NotNull TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j == c) {
            return Double.POSITIVE_INFINITY;
        }
        if (j == d) {
            return Double.NEGATIVE_INFINITY;
        }
        return DurationUnitKt.convertDurationUnit(a(j), d(j), unit);
    }

    /* renamed from: toLong-impl */
    public static final long m1712toLongimpl(long j, @NotNull TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j == c) {
            return Long.MAX_VALUE;
        }
        if (j == d) {
            return Long.MIN_VALUE;
        }
        return DurationUnitKt.convertDurationUnit(a(j), d(j), unit);
    }

    /* renamed from: toInt-impl */
    public static final int m1710toIntimpl(long j, @NotNull TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        return (int) RangesKt.coerceIn(m1712toLongimpl(j, unit), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /* renamed from: getInDays-impl */
    public static final double m1679getInDaysimpl(long j) {
        return m1709toDoubleimpl(j, TimeUnit.DAYS);
    }

    /* renamed from: getInHours-impl */
    public static final double m1680getInHoursimpl(long j) {
        return m1709toDoubleimpl(j, TimeUnit.HOURS);
    }

    /* renamed from: getInMinutes-impl */
    public static final double m1683getInMinutesimpl(long j) {
        return m1709toDoubleimpl(j, TimeUnit.MINUTES);
    }

    /* renamed from: getInSeconds-impl */
    public static final double m1685getInSecondsimpl(long j) {
        return m1709toDoubleimpl(j, TimeUnit.SECONDS);
    }

    /* renamed from: getInMilliseconds-impl */
    public static final double m1682getInMillisecondsimpl(long j) {
        return m1709toDoubleimpl(j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: getInMicroseconds-impl */
    public static final double m1681getInMicrosecondsimpl(long j) {
        return m1709toDoubleimpl(j, TimeUnit.MICROSECONDS);
    }

    /* renamed from: getInNanoseconds-impl */
    public static final double m1684getInNanosecondsimpl(long j) {
        return m1709toDoubleimpl(j, TimeUnit.NANOSECONDS);
    }

    /* renamed from: getInWholeDays-impl */
    public static final long m1686getInWholeDaysimpl(long j) {
        return m1712toLongimpl(j, TimeUnit.DAYS);
    }

    /* renamed from: getInWholeHours-impl */
    public static final long m1687getInWholeHoursimpl(long j) {
        return m1712toLongimpl(j, TimeUnit.HOURS);
    }

    /* renamed from: getInWholeMinutes-impl */
    public static final long m1690getInWholeMinutesimpl(long j) {
        return m1712toLongimpl(j, TimeUnit.MINUTES);
    }

    /* renamed from: getInWholeSeconds-impl */
    public static final long m1692getInWholeSecondsimpl(long j) {
        return m1712toLongimpl(j, TimeUnit.SECONDS);
    }

    /* renamed from: getInWholeMilliseconds-impl */
    public static final long m1689getInWholeMillisecondsimpl(long j) {
        return (!c(j) || !m1697isFiniteimpl(j)) ? m1712toLongimpl(j, TimeUnit.MILLISECONDS) : a(j);
    }

    /* renamed from: getInWholeMicroseconds-impl */
    public static final long m1688getInWholeMicrosecondsimpl(long j) {
        return m1712toLongimpl(j, TimeUnit.MICROSECONDS);
    }

    /* renamed from: getInWholeNanoseconds-impl */
    public static final long m1691getInWholeNanosecondsimpl(long j) {
        long b2;
        long a = a(j);
        if (b(j)) {
            return a;
        }
        if (a > 9223372036854L) {
            return Long.MAX_VALUE;
        }
        if (a < -9223372036854L) {
            return Long.MIN_VALUE;
        }
        b2 = DurationKt.b(a);
        return b2;
    }

    @Deprecated(message = "Use inWholeNanoseconds property instead.", replaceWith = @ReplaceWith(expression = "this.inWholeNanoseconds", imports = {}))
    /* renamed from: toLongNanoseconds-impl */
    public static final long m1714toLongNanosecondsimpl(long j) {
        return m1691getInWholeNanosecondsimpl(j);
    }

    @Deprecated(message = "Use inWholeMilliseconds property instead.", replaceWith = @ReplaceWith(expression = "this.inWholeMilliseconds", imports = {}))
    /* renamed from: toLongMilliseconds-impl */
    public static final long m1713toLongMillisecondsimpl(long j) {
        return m1689getInWholeMillisecondsimpl(j);
    }

    @NotNull
    /* renamed from: toString-impl */
    public static String m1715toStringimpl(long j) {
        int i;
        TimeUnit timeUnit;
        String str;
        if (j == 0) {
            return "0s";
        }
        if (j == c) {
            return "Infinity";
        }
        if (j == d) {
            return "-Infinity";
        }
        double d2 = m1709toDoubleimpl(m1677getAbsoluteValueUwyO8pc(j), TimeUnit.NANOSECONDS);
        boolean z = false;
        if (d2 < 1.0E-6d) {
            timeUnit = TimeUnit.SECONDS;
            i = 0;
            z = true;
        } else if (d2 < 1) {
            timeUnit = TimeUnit.NANOSECONDS;
            i = 7;
        } else if (d2 < 1000.0d) {
            timeUnit = TimeUnit.NANOSECONDS;
            i = 0;
        } else if (d2 < 1000000.0d) {
            timeUnit = TimeUnit.MICROSECONDS;
            i = 0;
        } else if (d2 < 1.0E9d) {
            timeUnit = TimeUnit.MILLISECONDS;
            i = 0;
        } else if (d2 < 1.0E12d) {
            timeUnit = TimeUnit.SECONDS;
            i = 0;
        } else if (d2 < 6.0E13d) {
            timeUnit = TimeUnit.MINUTES;
            i = 0;
        } else if (d2 < 3.6E15d) {
            timeUnit = TimeUnit.HOURS;
            i = 0;
        } else if (d2 < 8.64E20d) {
            timeUnit = TimeUnit.DAYS;
            i = 0;
        } else {
            timeUnit = TimeUnit.DAYS;
            i = 0;
            z = true;
        }
        double d3 = m1709toDoubleimpl(j, timeUnit);
        StringBuilder sb = new StringBuilder();
        if (z) {
            str = FormatToDecimalsKt.formatScientific(d3);
        } else if (i > 0) {
            str = FormatToDecimalsKt.formatUpToDecimals(d3, i);
        } else {
            str = FormatToDecimalsKt.formatToExactDecimals(d3, a(j, Math.abs(d3)));
        }
        sb.append(str);
        sb.append(DurationUnitKt.shortName(timeUnit));
        return sb.toString();
    }

    /* renamed from: toString-impl$default */
    public static /* synthetic */ String m1717toStringimpl$default(long j, TimeUnit timeUnit, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return m1716toStringimpl(j, timeUnit, i);
    }

    @NotNull
    /* renamed from: toString-impl */
    public static final String m1716toStringimpl(long j, @NotNull TimeUnit unit, int i) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (i >= 0) {
            double d2 = m1709toDoubleimpl(j, unit);
            if (Double.isInfinite(d2)) {
                return String.valueOf(d2);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(Math.abs(d2) < 1.0E14d ? FormatToDecimalsKt.formatToExactDecimals(d2, RangesKt.coerceAtMost(i, 12)) : FormatToDecimalsKt.formatScientific(d2));
            sb.append(DurationUnitKt.shortName(unit));
            return sb.toString();
        }
        throw new IllegalArgumentException(("decimals must be not negative, but was " + i).toString());
    }

    @NotNull
    /* renamed from: toIsoString-impl */
    public static final String m1711toIsoStringimpl(long j) {
        StringBuilder sb = new StringBuilder();
        if (m1699isNegativeimpl(j)) {
            sb.append('-');
        }
        sb.append("PT");
        long j2 = m1677getAbsoluteValueUwyO8pc(j);
        int i = m1710toIntimpl(j2, TimeUnit.HOURS);
        int i2 = m1693getMinutesComponentimpl(j2);
        int i3 = m1695getSecondsComponentimpl(j2);
        int i4 = m1694getNanosecondsComponentimpl(j2);
        boolean z = true;
        boolean z2 = i != 0;
        boolean z3 = (i3 == 0 && i4 == 0) ? false : true;
        if (i2 == 0 && (!z3 || !z2)) {
            z = false;
        }
        if (z2) {
            sb.append(i);
            sb.append('H');
        }
        if (z) {
            sb.append(i2);
            sb.append('M');
        }
        if (z3 || (!z2 && !z)) {
            sb.append(i3);
            if (i4 != 0) {
                sb.append('.');
                String padStart = StringsKt.padStart(String.valueOf(i4), 9, '0');
                if (i4 % 1000000 == 0) {
                    sb.append((CharSequence) padStart, 0, 3);
                    Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
                } else if (i4 % 1000 == 0) {
                    sb.append((CharSequence) padStart, 0, 6);
                    Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
                } else {
                    sb.append(padStart);
                }
            }
            sb.append('S');
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
