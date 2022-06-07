package java8.util.stream;

import java.util.Collection;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.Supplier;
import java8.util.stream.df;
import java8.util.stream.eg;
import java8.util.stream.eu;
import java8.util.stream.gd;

/* loaded from: classes5.dex */
public final class StreamSupport {
    private StreamSupport() {
    }

    public static <T> Stream<T> stream(Collection<? extends T> collection) {
        return stream(Spliterators.spliterator(collection), false);
    }

    public static <T> Stream<T> parallelStream(Collection<? extends T> collection) {
        return stream(Spliterators.spliterator(collection), true);
    }

    public static <T> Stream<T> stream(Collection<? extends T> collection, int i) {
        return stream((Collection) collection, i, false);
    }

    public static <T> Stream<T> stream(Collection<? extends T> collection, int i, boolean z) {
        Objects.requireNonNull(collection);
        return stream(Spliterators.spliterator(collection, i), z);
    }

    public static <T> Stream<T> stream(Spliterator<T> spliterator, boolean z) {
        Objects.requireNonNull(spliterator);
        return new gd.a((Spliterator<?>) spliterator, gp.a((Spliterator<?>) spliterator), z);
    }

    public static <T> Stream<T> stream(Supplier<? extends Spliterator<T>> supplier, int i, boolean z) {
        Objects.requireNonNull(supplier);
        return new gd.a((Supplier<? extends Spliterator<?>>) supplier, gp.e(i), z);
    }

    public static IntStream intStream(Spliterator.OfInt ofInt, boolean z) {
        return new eg.a(ofInt, gp.a(ofInt), z);
    }

    public static IntStream intStream(Supplier<? extends Spliterator.OfInt> supplier, int i, boolean z) {
        return new eg.a(supplier, gp.e(i), z);
    }

    public static LongStream longStream(Spliterator.OfLong ofLong, boolean z) {
        return new eu.a(ofLong, gp.a(ofLong), z);
    }

    public static LongStream longStream(Supplier<? extends Spliterator.OfLong> supplier, int i, boolean z) {
        return new eu.a(supplier, gp.e(i), z);
    }

    public static DoubleStream doubleStream(Spliterator.OfDouble ofDouble, boolean z) {
        return new df.a(ofDouble, gp.a(ofDouble), z);
    }

    public static DoubleStream doubleStream(Supplier<? extends Spliterator.OfDouble> supplier, int i, boolean z) {
        return new df.a(supplier, gp.e(i), z);
    }

    public static Runnable a(BaseStream<?, ?> baseStream) {
        baseStream.getClass();
        return he.a(baseStream);
    }
}
