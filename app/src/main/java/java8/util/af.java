package java8.util;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.PriorityQueue;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PQueueSpliterator.java */
/* loaded from: classes5.dex */
public final class af<E> implements Spliterator<E> {
    private static final boolean e = Spliterators.d;
    private static final Unsafe f = al.a;
    private static final long g;
    private static final long h;
    private static final long i;
    private final PriorityQueue<E> a;
    private int b;
    private int c;
    private int d;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 16704;
    }

    private af(PriorityQueue<E> priorityQueue, int i2, int i3, int i4) {
        this.a = priorityQueue;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(PriorityQueue<T> priorityQueue) {
        return new af(priorityQueue, 0, -1, 0);
    }

    private int b() {
        int i2 = this.c;
        if (i2 >= 0) {
            return i2;
        }
        this.d = c(this.a);
        int b = b(this.a);
        this.c = b;
        return b;
    }

    /* renamed from: a */
    public af<E> trySplit() {
        int b = b();
        int i2 = this.b;
        int i3 = (b + i2) >>> 1;
        if (i2 >= i3) {
            return null;
        }
        PriorityQueue<E> priorityQueue = this.a;
        this.b = i3;
        return new af<>(priorityQueue, i2, i3, this.d);
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        PriorityQueue<E> priorityQueue = this.a;
        if (this.c < 0) {
            this.c = b(priorityQueue);
            this.d = c(priorityQueue);
        }
        Object[] d = d(priorityQueue);
        int i2 = this.c;
        this.b = i2;
        for (int i3 = this.b; i3 < i2; i3++) {
            Object obj = d[i3];
            if (obj == null) {
                break;
            }
            consumer.accept(obj);
        }
        if (c(priorityQueue) != this.d) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        PriorityQueue<E> priorityQueue = this.a;
        if (this.c < 0) {
            this.c = b(priorityQueue);
            this.d = c(priorityQueue);
        }
        int i2 = this.b;
        if (i2 >= this.c) {
            return false;
        }
        this.b = i2 + 1;
        Object obj = d(priorityQueue)[i2];
        if (obj == null || c(priorityQueue) != this.d) {
            throw new ConcurrentModificationException();
        }
        consumer.accept(obj);
        return true;
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return b() - this.b;
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
    public boolean hasCharacteristics(int i2) {
        return Spliterators.hasCharacteristics(this, i2);
    }

    private static <T> int b(PriorityQueue<T> priorityQueue) {
        return f.getInt(priorityQueue, g);
    }

    private static <T> int c(PriorityQueue<T> priorityQueue) {
        if (e) {
            return 0;
        }
        return f.getInt(priorityQueue, h);
    }

    private static <T> Object[] d(PriorityQueue<T> priorityQueue) {
        return (Object[]) f.getObject(priorityQueue, i);
    }

    static {
        try {
            g = f.objectFieldOffset(PriorityQueue.class.getDeclaredField("size"));
            if (!e) {
                h = f.objectFieldOffset(PriorityQueue.class.getDeclaredField("modCount"));
            } else {
                h = 0L;
            }
            i = f.objectFieldOffset(PriorityQueue.class.getDeclaredField(e ? "elements" : "queue"));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }
}
