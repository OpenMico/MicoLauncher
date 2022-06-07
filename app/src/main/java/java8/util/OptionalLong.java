package java8.util;

import java.util.NoSuchElementException;
import java8.lang.Longs;
import java8.util.function.LongConsumer;
import java8.util.function.LongSupplier;
import java8.util.function.Supplier;
import java8.util.stream.LongStream;
import java8.util.stream.LongStreams;

/* loaded from: classes5.dex */
public final class OptionalLong {
    private static final OptionalLong a = new OptionalLong();
    private final boolean b;
    private final long c;

    /* loaded from: classes5.dex */
    public static final class a {
        static final OptionalLong[] a = new OptionalLong[256];

        static {
            int i = 0;
            while (true) {
                OptionalLong[] optionalLongArr = a;
                if (i < optionalLongArr.length) {
                    optionalLongArr[i] = new OptionalLong(i - 128);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private OptionalLong() {
        this.b = false;
        this.c = 0L;
    }

    public static OptionalLong empty() {
        return a;
    }

    OptionalLong(long j) {
        this.b = true;
        this.c = j;
    }

    public static OptionalLong of(long j) {
        if (j < -128 || j > 127) {
            return new OptionalLong(j);
        }
        return a.a[((int) j) + 128];
    }

    public long getAsLong() {
        return orElseThrow();
    }

    public boolean isPresent() {
        return this.b;
    }

    public boolean isEmpty() {
        return !this.b;
    }

    public void ifPresent(LongConsumer longConsumer) {
        if (this.b) {
            longConsumer.accept(this.c);
        }
    }

    public void ifPresentOrElse(LongConsumer longConsumer, Runnable runnable) {
        if (this.b) {
            longConsumer.accept(this.c);
        } else {
            runnable.run();
        }
    }

    public LongStream stream() {
        if (this.b) {
            return LongStreams.of(this.c);
        }
        return LongStreams.empty();
    }

    public long orElse(long j) {
        return this.b ? this.c : j;
    }

    public long orElseGet(LongSupplier longSupplier) {
        return this.b ? this.c : longSupplier.getAsLong();
    }

    public long orElseThrow() {
        if (this.b) {
            return this.c;
        }
        throw new NoSuchElementException("No value present");
    }

    public <X extends Throwable> long orElseThrow(Supplier<? extends X> supplier) throws Throwable {
        if (this.b) {
            return this.c;
        }
        throw ((Throwable) supplier.get());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OptionalLong)) {
            return false;
        }
        OptionalLong optionalLong = (OptionalLong) obj;
        if (!this.b || !optionalLong.b) {
            if (this.b == optionalLong.b) {
                return true;
            }
        } else if (this.c == optionalLong.c) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.b) {
            return Longs.hashCode(this.c);
        }
        return 0;
    }

    public String toString() {
        return this.b ? String.format("OptionalLong[%s]", Long.valueOf(this.c)) : "OptionalLong.empty";
    }
}
