package java8.util.stream;

import java8.util.Spliterator;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;
import java8.util.function.LongConsumer;
import java8.util.stream.Sink;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public interface Node<T> {

    /* loaded from: classes5.dex */
    public interface Builder<T> extends Sink<T> {

        /* loaded from: classes5.dex */
        public interface OfDouble extends Builder<Double>, Sink.OfDouble {
            @Override // java8.util.stream.Node.Builder
            Node<Double> build();
        }

        /* loaded from: classes5.dex */
        public interface OfInt extends Builder<Integer>, Sink.OfInt {
            @Override // java8.util.stream.Node.Builder
            Node<Integer> build();
        }

        /* loaded from: classes5.dex */
        public interface OfLong extends Builder<Long>, Sink.OfLong {
            @Override // java8.util.stream.Node.Builder
            Node<Long> build();
        }

        Node<T> build();
    }

    /* loaded from: classes5.dex */
    public interface OfDouble extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, OfDouble> {
        void copyInto(Double[] dArr, int i);

        @Override // java8.util.stream.Node
        void forEach(Consumer<? super Double> consumer);

        gq getShape();

        @Override // java8.util.stream.Node.OfPrimitive
        double[] newArray(int i);

        @Override // java8.util.stream.Node.OfPrimitive
        OfDouble truncate(long j, long j2, IntFunction<Double[]> intFunction);
    }

    /* loaded from: classes5.dex */
    public interface OfInt extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, OfInt> {
        void copyInto(Integer[] numArr, int i);

        @Override // java8.util.stream.Node
        void forEach(Consumer<? super Integer> consumer);

        gq getShape();

        @Override // java8.util.stream.Node.OfPrimitive
        int[] newArray(int i);

        @Override // java8.util.stream.Node.OfPrimitive
        OfInt truncate(long j, long j2, IntFunction<Integer[]> intFunction);
    }

    /* loaded from: classes5.dex */
    public interface OfLong extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, OfLong> {
        void copyInto(Long[] lArr, int i);

        @Override // java8.util.stream.Node
        void forEach(Consumer<? super Long> consumer);

        gq getShape();

        @Override // java8.util.stream.Node.OfPrimitive
        long[] newArray(int i);

        @Override // java8.util.stream.Node.OfPrimitive
        OfLong truncate(long j, long j2, IntFunction<Long[]> intFunction);
    }

    /* loaded from: classes5.dex */
    public interface OfPrimitive<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_NODE extends OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>> extends Node<T> {
        @Override // java8.util.stream.Node
        T[] asArray(IntFunction<T[]> intFunction);

        T_ARR asPrimitiveArray();

        void copyInto(T_ARR t_arr, int i);

        void forEach(T_CONS t_cons);

        T_NODE getChild(int i);

        T_ARR newArray(int i);

        T_SPLITR spliterator();

        T_NODE truncate(long j, long j2, IntFunction<T[]> intFunction);
    }

    long a();

    Node<T> a(long j, long j2, IntFunction<T[]> intFunction);

    void a(T[] tArr, int i);

    Node<T> a_(int i);

    T[] asArray(IntFunction<T[]> intFunction);

    int c();

    Spliterator<T> f_();

    void forEach(Consumer<? super T> consumer);
}
