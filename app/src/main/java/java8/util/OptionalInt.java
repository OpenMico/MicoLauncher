package java8.util;

import java.util.NoSuchElementException;
import java8.util.function.IntConsumer;
import java8.util.function.IntSupplier;
import java8.util.function.Supplier;
import java8.util.stream.IntStream;
import java8.util.stream.IntStreams;

/* loaded from: classes5.dex */
public final class OptionalInt {
    private static final OptionalInt a = new OptionalInt();
    private final boolean b;
    private final int c;

    /* loaded from: classes5.dex */
    public static final class a {
        static final OptionalInt[] a = new OptionalInt[256];

        static {
            int i = 0;
            while (true) {
                OptionalInt[] optionalIntArr = a;
                if (i < optionalIntArr.length) {
                    optionalIntArr[i] = new OptionalInt(i - 128);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private OptionalInt() {
        this.b = false;
        this.c = 0;
    }

    public static OptionalInt empty() {
        return a;
    }

    OptionalInt(int i) {
        this.b = true;
        this.c = i;
    }

    public static OptionalInt of(int i) {
        if (i < -128 || i > 127) {
            return new OptionalInt(i);
        }
        return a.a[i + 128];
    }

    public int getAsInt() {
        return orElseThrow();
    }

    public boolean isPresent() {
        return this.b;
    }

    public boolean isEmpty() {
        return !this.b;
    }

    public void ifPresent(IntConsumer intConsumer) {
        if (this.b) {
            intConsumer.accept(this.c);
        }
    }

    public void ifPresentOrElse(IntConsumer intConsumer, Runnable runnable) {
        if (this.b) {
            intConsumer.accept(this.c);
        } else {
            runnable.run();
        }
    }

    public IntStream stream() {
        if (this.b) {
            return IntStreams.of(this.c);
        }
        return IntStreams.empty();
    }

    public int orElse(int i) {
        return this.b ? this.c : i;
    }

    public int orElseGet(IntSupplier intSupplier) {
        return this.b ? this.c : intSupplier.getAsInt();
    }

    public int orElseThrow() {
        if (this.b) {
            return this.c;
        }
        throw new NoSuchElementException("No value present");
    }

    public <X extends Throwable> int orElseThrow(Supplier<? extends X> supplier) throws Throwable {
        if (this.b) {
            return this.c;
        }
        throw ((Throwable) supplier.get());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OptionalInt)) {
            return false;
        }
        OptionalInt optionalInt = (OptionalInt) obj;
        if (!this.b || !optionalInt.b) {
            if (this.b == optionalInt.b) {
                return true;
            }
        } else if (this.c == optionalInt.c) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.b) {
            return this.c;
        }
        return 0;
    }

    public String toString() {
        return this.b ? String.format("OptionalInt[%s]", Integer.valueOf(this.c)) : "OptionalInt.empty";
    }
}
