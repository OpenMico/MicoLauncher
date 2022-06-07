package java8.util;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Vector;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: VectorSpliterator.java */
/* loaded from: classes5.dex */
public final class am<E> implements Spliterator<E> {
    private static final Unsafe f = al.a;
    private static final long g;
    private static final long h;
    private static final long i;
    private final Vector<E> a;
    private Object[] b;
    private int c;
    private int d;
    private int e;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 16464;
    }

    private am(Vector<E> vector, Object[] objArr, int i2, int i3, int i4) {
        this.a = vector;
        this.b = objArr;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(Vector<T> vector) {
        return new am(vector, null, 0, -1, 0);
    }

    private int a() {
        int i2 = this.d;
        if (i2 < 0) {
            synchronized (this.a) {
                this.b = d(this.a);
                this.e = c(this.a);
                i2 = b(this.a);
                this.d = i2;
            }
        }
        return i2;
    }

    @Override // java8.util.Spliterator
    public Spliterator<E> trySplit() {
        int a = a();
        int i2 = this.c;
        int i3 = (a + i2) >>> 1;
        if (i2 >= i3) {
            return null;
        }
        Vector<E> vector = this.a;
        Object[] objArr = this.b;
        this.c = i3;
        return new am(vector, objArr, i2, i3, this.e);
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        int a = a();
        int i2 = this.c;
        if (a <= i2) {
            return false;
        }
        this.c = i2 + 1;
        consumer.accept(this.b[i2]);
        if (this.e == c(this.a)) {
            return true;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        int a = a();
        Object[] objArr = this.b;
        this.c = a;
        for (int i2 = this.c; i2 < a; i2++) {
            consumer.accept(objArr[i2]);
        }
        if (c(this.a) != this.e) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return a() - this.c;
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

    private static <T> int b(Vector<T> vector) {
        return f.getInt(vector, g);
    }

    private static <T> int c(Vector<T> vector) {
        return f.getInt(vector, h);
    }

    private static <T> Object[] d(Vector<T> vector) {
        return (Object[]) f.getObject(vector, i);
    }

    static {
        try {
            h = f.objectFieldOffset(AbstractList.class.getDeclaredField("modCount"));
            g = f.objectFieldOffset(Vector.class.getDeclaredField("elementCount"));
            i = f.objectFieldOffset(Vector.class.getDeclaredField("elementData"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
