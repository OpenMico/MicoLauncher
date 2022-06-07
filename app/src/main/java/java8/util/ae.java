package java8.util;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java8.util.function.Consumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PBQueueSpliterator.java */
/* loaded from: classes5.dex */
public final class ae<E> implements Spliterator<E> {
    private final PriorityBlockingQueue<E> a;
    private Object[] b;
    private int c;
    private int d;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 16704;
    }

    private ae(PriorityBlockingQueue<E> priorityBlockingQueue, Object[] objArr, int i, int i2) {
        this.a = priorityBlockingQueue;
        this.b = objArr;
        this.c = i;
        this.d = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(PriorityBlockingQueue<T> priorityBlockingQueue) {
        return new ae(priorityBlockingQueue, null, 0, -1);
    }

    private int b() {
        if (this.b == null) {
            Object[] array = this.a.toArray();
            this.b = array;
            this.d = array.length;
        }
        return this.d;
    }

    /* renamed from: a */
    public ae<E> trySplit() {
        int b = b();
        int i = this.c;
        int i2 = (b + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        PriorityBlockingQueue<E> priorityBlockingQueue = this.a;
        Object[] objArr = this.b;
        this.c = i2;
        return new ae<>(priorityBlockingQueue, objArr, i, i2);
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        int b = b();
        Object[] objArr = this.b;
        this.c = b;
        for (int i = this.c; i < b; i++) {
            consumer.accept(objArr[i]);
        }
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        int b = b();
        int i = this.c;
        if (b <= i || i < 0) {
            return false;
        }
        Object[] objArr = this.b;
        this.c = i + 1;
        consumer.accept(objArr[i]);
        return true;
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return b() - this.c;
    }

    @Override // java8.util.Spliterator
    public Comparator<? super E> getComparator() {
        return Spliterators.getComparator(this);
    }

    @Override // java8.util.Spliterator
    public long getExactSizeIfKnown() {
        return Spliterators.getExactSizeIfKnown(this);
    }

    @Override // java8.util.Spliterator
    public boolean hasCharacteristics(int i) {
        return Spliterators.hasCharacteristics(this, i);
    }
}
