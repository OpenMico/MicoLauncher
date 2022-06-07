package java8.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java8.util.PrimitiveIterator;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.LongConsumer;

/* loaded from: classes5.dex */
public final class Iterators {
    public static <E> void forEachRemaining(Iterator<E> it, Consumer<? super E> consumer) {
        Objects.requireNonNull(it);
        Objects.requireNonNull(consumer);
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    public static void forEachRemaining(PrimitiveIterator.OfInt ofInt, IntConsumer intConsumer) {
        Objects.requireNonNull(ofInt);
        Objects.requireNonNull(intConsumer);
        while (ofInt.hasNext()) {
            intConsumer.accept(ofInt.nextInt());
        }
    }

    public static void forEachRemaining(PrimitiveIterator.OfLong ofLong, LongConsumer longConsumer) {
        Objects.requireNonNull(ofLong);
        Objects.requireNonNull(longConsumer);
        while (ofLong.hasNext()) {
            longConsumer.accept(ofLong.nextLong());
        }
    }

    public static void forEachRemaining(PrimitiveIterator.OfDouble ofDouble, DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(ofDouble);
        Objects.requireNonNull(doubleConsumer);
        while (ofDouble.hasNext()) {
            doubleConsumer.accept(ofDouble.nextDouble());
        }
    }

    public static <E> Iterator<E> asIterator(final Enumeration<E> enumeration) {
        Objects.requireNonNull(enumeration);
        return new b<E>() { // from class: java8.util.Iterators.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [E, java.lang.Object] */
            @Override // java.util.Iterator
            public E next() {
                return enumeration.nextElement();
            }
        };
    }

    public static <T> Iterator<T> emptyIterator() {
        return a.a;
    }

    /* loaded from: classes5.dex */
    static final class a<E> extends b<E> {
        static final a<Object> a = new a<>();

        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        a() {
        }

        @Override // java.util.Iterator
        public E next() {
            throw new NoSuchElementException();
        }
    }

    /* loaded from: classes5.dex */
    static abstract class b<T> implements Iterator<T> {
        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    private Iterators() {
    }
}
