package java8.util;

import build.IgnoreJava8API;
import java.util.Comparator;
import java.util.Spliterator;
import java8.util.function.Consumer;
import java8.util.function.Consumers;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DelegatingSpliterator.java */
@IgnoreJava8API
/* loaded from: classes5.dex */
public final class o<T> implements Spliterator<T> {
    private final Spliterator<T> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(Spliterator<T> spliterator) {
        Objects.requireNonNull(spliterator);
        this.a = spliterator;
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super T> consumer) {
        return this.a.tryAdvance(new a(consumer));
    }

    @Override // java8.util.Spliterator
    public Spliterator<T> trySplit() {
        Spliterator<T> trySplit = this.a.trySplit();
        if (trySplit == null) {
            return null;
        }
        return new o(trySplit);
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return this.a.estimateSize();
    }

    @Override // java8.util.Spliterator
    public int characteristics() {
        return this.a.characteristics();
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super T> consumer) {
        this.a.forEachRemaining(new a(consumer));
    }

    @Override // java8.util.Spliterator
    public long getExactSizeIfKnown() {
        return this.a.getExactSizeIfKnown();
    }

    @Override // java8.util.Spliterator
    public boolean hasCharacteristics(int i) {
        return this.a.hasCharacteristics(i);
    }

    @Override // java8.util.Spliterator
    public Comparator<? super T> getComparator() {
        return this.a.getComparator();
    }

    /* compiled from: DelegatingSpliterator.java */
    /* loaded from: classes5.dex */
    private static final class a<T> implements java.util.function.Consumer<T> {
        private final Consumer<T> a;

        a(Consumer<T> consumer) {
            Objects.requireNonNull(consumer);
            this.a = consumer;
        }

        @Override // java.util.function.Consumer
        public void accept(T t) {
            this.a.accept(t);
        }

        @Override // java.util.function.Consumer
        @IgnoreJava8API
        public java.util.function.Consumer<T> andThen(final java.util.function.Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            return new a(Consumers.andThen(this.a, new Consumer<T>() { // from class: java8.util.o.a.1
                @Override // java8.util.function.Consumer
                public void accept(T t) {
                    consumer.accept(t);
                }
            }));
        }
    }
}
