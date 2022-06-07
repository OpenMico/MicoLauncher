package java8.util;

import java.util.NoSuchElementException;
import java8.lang.Doubles;
import java8.util.function.DoubleConsumer;
import java8.util.function.DoubleSupplier;
import java8.util.function.Supplier;
import java8.util.stream.DoubleStream;
import java8.util.stream.DoubleStreams;

/* loaded from: classes5.dex */
public final class OptionalDouble {
    private static final OptionalDouble a = new OptionalDouble();
    private final boolean b;
    private final double c;

    private OptionalDouble() {
        this.b = false;
        this.c = Double.NaN;
    }

    public static OptionalDouble empty() {
        return a;
    }

    private OptionalDouble(double d) {
        this.b = true;
        this.c = d;
    }

    public static OptionalDouble of(double d) {
        return new OptionalDouble(d);
    }

    public double getAsDouble() {
        return orElseThrow();
    }

    public boolean isPresent() {
        return this.b;
    }

    public boolean isEmpty() {
        return !this.b;
    }

    public void ifPresent(DoubleConsumer doubleConsumer) {
        if (this.b) {
            doubleConsumer.accept(this.c);
        }
    }

    public void ifPresentOrElse(DoubleConsumer doubleConsumer, Runnable runnable) {
        if (this.b) {
            doubleConsumer.accept(this.c);
        } else {
            runnable.run();
        }
    }

    public DoubleStream stream() {
        if (this.b) {
            return DoubleStreams.of(this.c);
        }
        return DoubleStreams.empty();
    }

    public double orElse(double d) {
        return this.b ? this.c : d;
    }

    public double orElseGet(DoubleSupplier doubleSupplier) {
        return this.b ? this.c : doubleSupplier.getAsDouble();
    }

    public double orElseThrow() {
        if (this.b) {
            return this.c;
        }
        throw new NoSuchElementException("No value present");
    }

    public <X extends Throwable> double orElseThrow(Supplier<? extends X> supplier) throws Throwable {
        if (this.b) {
            return this.c;
        }
        throw ((Throwable) supplier.get());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OptionalDouble)) {
            return false;
        }
        OptionalDouble optionalDouble = (OptionalDouble) obj;
        if (!this.b || !optionalDouble.b) {
            if (this.b == optionalDouble.b) {
                return true;
            }
        } else if (Double.compare(this.c, optionalDouble.c) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.b) {
            return Doubles.hashCode(this.c);
        }
        return 0;
    }

    public String toString() {
        return this.b ? String.format("OptionalDouble[%s]", Double.valueOf(this.c)) : "OptionalDouble.empty";
    }
}
