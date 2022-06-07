package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.umeng.analytics.pro.ai;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.seamless.xhtml.XHTMLElement;

@GwtCompatible
/* loaded from: classes2.dex */
public final class Stopwatch {
    private final Ticker a;
    private boolean b;
    private long c;
    private long d;

    public static Stopwatch createUnstarted() {
        return new Stopwatch();
    }

    public static Stopwatch createUnstarted(Ticker ticker) {
        return new Stopwatch(ticker);
    }

    public static Stopwatch createStarted() {
        return new Stopwatch().start();
    }

    public static Stopwatch createStarted(Ticker ticker) {
        return new Stopwatch(ticker).start();
    }

    Stopwatch() {
        this.a = Ticker.systemTicker();
    }

    Stopwatch(Ticker ticker) {
        this.a = (Ticker) Preconditions.checkNotNull(ticker, "ticker");
    }

    public boolean isRunning() {
        return this.b;
    }

    @CanIgnoreReturnValue
    public Stopwatch start() {
        Preconditions.checkState(!this.b, "This stopwatch is already running.");
        this.b = true;
        this.d = this.a.read();
        return this;
    }

    @CanIgnoreReturnValue
    public Stopwatch stop() {
        long read = this.a.read();
        Preconditions.checkState(this.b, "This stopwatch is already stopped.");
        this.b = false;
        this.c += read - this.d;
        return this;
    }

    @CanIgnoreReturnValue
    public Stopwatch reset() {
        this.c = 0L;
        this.b = false;
        return this;
    }

    private long a() {
        return this.b ? (this.a.read() - this.d) + this.c : this.c;
    }

    public long elapsed(TimeUnit timeUnit) {
        return timeUnit.convert(a(), TimeUnit.NANOSECONDS);
    }

    public String toString() {
        long a = a();
        TimeUnit a2 = a(a);
        double convert = a / TimeUnit.NANOSECONDS.convert(1L, a2);
        return j.a(convert) + StringUtils.SPACE + a(a2);
    }

    private static TimeUnit a(long j) {
        if (TimeUnit.DAYS.convert(j, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.DAYS;
        }
        if (TimeUnit.HOURS.convert(j, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.HOURS;
        }
        if (TimeUnit.MINUTES.convert(j, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.MINUTES;
        }
        if (TimeUnit.SECONDS.convert(j, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.SECONDS;
        }
        if (TimeUnit.MILLISECONDS.convert(j, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.MILLISECONDS;
        }
        if (TimeUnit.MICROSECONDS.convert(j, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.MICROSECONDS;
        }
        return TimeUnit.NANOSECONDS;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.common.base.Stopwatch$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[TimeUnit.values().length];

        static {
            try {
                a[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[TimeUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[TimeUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[TimeUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private static String a(TimeUnit timeUnit) {
        switch (AnonymousClass1.a[timeUnit.ordinal()]) {
            case 1:
                return "ns";
            case 2:
                return "μs";
            case 3:
                return "ms";
            case 4:
                return ai.az;
            case 5:
                return "min";
            case 6:
                return XHTMLElement.XPATH_PREFIX;
            case 7:
                return "d";
            default:
                throw new AssertionError();
        }
    }
}
