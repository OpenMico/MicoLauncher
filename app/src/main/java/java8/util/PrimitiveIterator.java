package java8.util;

import java.util.Iterator;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.LongConsumer;

/* loaded from: classes5.dex */
public interface PrimitiveIterator<T, T_CONS> extends Iterator<T> {

    /* loaded from: classes5.dex */
    public interface OfDouble extends PrimitiveIterator<Double, DoubleConsumer> {
        void forEachRemaining(DoubleConsumer doubleConsumer);

        @Override // java.util.Iterator
        Double next();

        double nextDouble();
    }

    /* loaded from: classes5.dex */
    public interface OfInt extends PrimitiveIterator<Integer, IntConsumer> {
        void forEachRemaining(IntConsumer intConsumer);

        @Override // java.util.Iterator
        Integer next();

        int nextInt();
    }

    /* loaded from: classes5.dex */
    public interface OfLong extends PrimitiveIterator<Long, LongConsumer> {
        void forEachRemaining(LongConsumer longConsumer);

        @Override // java.util.Iterator
        Long next();

        long nextLong();
    }

    void forEachRemaining(T_CONS t_cons);
}
