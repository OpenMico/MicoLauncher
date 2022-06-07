package java8.util.stream;

import java.util.Iterator;
import java8.util.DoubleSummaryStatistics;
import java8.util.OptionalDouble;
import java8.util.Spliterator;
import java8.util.function.BiConsumer;
import java8.util.function.DoubleBinaryOperator;
import java8.util.function.DoubleConsumer;
import java8.util.function.DoubleFunction;
import java8.util.function.DoublePredicate;
import java8.util.function.DoubleToIntFunction;
import java8.util.function.DoubleToLongFunction;
import java8.util.function.DoubleUnaryOperator;
import java8.util.function.ObjDoubleConsumer;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
public interface DoubleStream extends BaseStream<Double, DoubleStream> {

    /* loaded from: classes5.dex */
    public interface Builder extends DoubleConsumer {
        @Override // java8.util.function.DoubleConsumer
        void accept(double d);

        Builder add(double d);

        DoubleStream build();
    }

    boolean allMatch(DoublePredicate doublePredicate);

    boolean anyMatch(DoublePredicate doublePredicate);

    OptionalDouble average();

    Stream<Double> boxed();

    <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> objDoubleConsumer, BiConsumer<R, R> biConsumer);

    long count();

    DoubleStream distinct();

    DoubleStream dropWhile(DoublePredicate doublePredicate);

    DoubleStream filter(DoublePredicate doublePredicate);

    OptionalDouble findAny();

    OptionalDouble findFirst();

    DoubleStream flatMap(DoubleFunction<? extends DoubleStream> doubleFunction);

    void forEach(DoubleConsumer doubleConsumer);

    void forEachOrdered(DoubleConsumer doubleConsumer);

    @Override // java8.util.stream.BaseStream
    Iterator<Double> iterator();

    DoubleStream limit(long j);

    DoubleStream map(DoubleUnaryOperator doubleUnaryOperator);

    IntStream mapToInt(DoubleToIntFunction doubleToIntFunction);

    LongStream mapToLong(DoubleToLongFunction doubleToLongFunction);

    <U> Stream<U> mapToObj(DoubleFunction<? extends U> doubleFunction);

    OptionalDouble max();

    OptionalDouble min();

    boolean noneMatch(DoublePredicate doublePredicate);

    @Override // java8.util.stream.BaseStream
    DoubleStream parallel();

    DoubleStream peek(DoubleConsumer doubleConsumer);

    double reduce(double d, DoubleBinaryOperator doubleBinaryOperator);

    OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator);

    @Override // java8.util.stream.BaseStream
    DoubleStream sequential();

    DoubleStream skip(long j);

    DoubleStream sorted();

    @Override // java8.util.stream.BaseStream
    Spliterator<Double> spliterator();

    double sum();

    DoubleSummaryStatistics summaryStatistics();

    DoubleStream takeWhile(DoublePredicate doublePredicate);

    double[] toArray();
}
