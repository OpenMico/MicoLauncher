package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

/* compiled from: TimeSource.kt */
@SinceKotlin(version = "1.3")
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u0000 \u00042\u00020\u0001:\u0002\u0004\u0005J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0006"}, d2 = {"Lkotlin/time/TimeSource;", "", "markNow", "Lkotlin/time/TimeMark;", "Companion", "Monotonic", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
@ExperimentalTime
/* loaded from: classes5.dex */
public interface TimeSource {
    @NotNull
    public static final Companion Companion = Companion.a;

    @NotNull
    TimeMark markNow();

    /* compiled from: TimeSource.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0004H\u0096\u0001J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lkotlin/time/TimeSource$Monotonic;", "Lkotlin/time/TimeSource;", "()V", "markNow", "Lkotlin/time/TimeMark;", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class Monotonic implements TimeSource {
        @NotNull
        public static final Monotonic INSTANCE = new Monotonic();
        private final /* synthetic */ MonotonicTimeSource a = MonotonicTimeSource.INSTANCE;

        @Override // kotlin.time.TimeSource
        @NotNull
        public TimeMark markNow() {
            return this.a.markNow();
        }

        private Monotonic() {
        }

        @NotNull
        public String toString() {
            return MonotonicTimeSource.INSTANCE.toString();
        }
    }

    /* compiled from: TimeSource.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lkotlin/time/TimeSource$Companion;", "", "()V", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class Companion {
        static final /* synthetic */ Companion a = new Companion();

        private Companion() {
        }
    }
}
