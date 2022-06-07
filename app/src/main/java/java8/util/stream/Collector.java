package java8.util.stream;

import java.util.Set;
import java8.util.function.BiConsumer;
import java8.util.function.BinaryOperator;
import java8.util.function.Function;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
public interface Collector<T, A, R> {

    /* loaded from: classes5.dex */
    public enum Characteristics {
        CONCURRENT,
        UNORDERED,
        IDENTITY_FINISH
    }

    BiConsumer<A, T> accumulator();

    Set<Characteristics> characteristics();

    BinaryOperator<A> combiner();

    Function<A, R> finisher();

    Supplier<A> supplier();
}
