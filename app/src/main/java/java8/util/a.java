package java8.util;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ArrayDequeSpliterator.java */
/* loaded from: classes5.dex */
public final class a<E> implements Spliterator<E> {
    private static final Unsafe d = al.a;
    private static final long e;
    private static final long f;
    private static final long g;
    private final ArrayDeque<E> a;
    private int b;
    private int c;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 16720;
    }

    private a(ArrayDeque<E> arrayDeque, int i, int i2) {
        this.a = arrayDeque;
        this.c = i;
        this.b = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(ArrayDeque<T> arrayDeque) {
        return new a(arrayDeque, -1, -1);
    }

    private int b() {
        int i = this.b;
        if (i >= 0) {
            return i;
        }
        int b = b(this.a);
        this.b = b;
        this.c = c(this.a);
        return b;
    }

    /* renamed from: a */
    public a<E> trySplit() {
        int b = b();
        int i = this.c;
        int length = d(this.a).length;
        if (i == b) {
            return null;
        }
        int i2 = length - 1;
        if (((i + 1) & i2) == b) {
            return null;
        }
        if (i > b) {
            b += length;
        }
        int i3 = ((b + i) >>> 1) & i2;
        ArrayDeque<E> arrayDeque = this.a;
        this.c = i3;
        return new a<>(arrayDeque, i, i3);
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        Object[] d2 = d(this.a);
        int length = d2.length - 1;
        int b = b();
        int i = this.c;
        this.c = b;
        while (i != b) {
            Object obj = d2[i];
            i = (i + 1) & length;
            if (obj != null) {
                consumer.accept(obj);
            } else {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        Object[] d2 = d(this.a);
        int length = d2.length - 1;
        b();
        int i = this.c;
        if (i == this.b) {
            return false;
        }
        Object obj = d2[i];
        this.c = length & (i + 1);
        if (obj != null) {
            consumer.accept(obj);
            return true;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        int b = b() - this.c;
        if (b < 0) {
            b += d(this.a).length;
        }
        return b;
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

    private static <T> int b(ArrayDeque<T> arrayDeque) {
        return d.getInt(arrayDeque, e);
    }

    private static <T> int c(ArrayDeque<T> arrayDeque) {
        return d.getInt(arrayDeque, f);
    }

    private static <T> Object[] d(ArrayDeque<T> arrayDeque) {
        return (Object[]) d.getObject(arrayDeque, g);
    }

    static {
        try {
            e = d.objectFieldOffset(ArrayDeque.class.getDeclaredField("tail"));
            f = d.objectFieldOffset(ArrayDeque.class.getDeclaredField("head"));
            g = d.objectFieldOffset(ArrayDeque.class.getDeclaredField("elements"));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }
}
