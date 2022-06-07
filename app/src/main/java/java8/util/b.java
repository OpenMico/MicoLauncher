package java8.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ArrayListSpliterator.java */
/* loaded from: classes5.dex */
public final class b<E> implements Spliterator<E> {
    private static final Unsafe e = al.a;
    private static final long f;
    private static final long g;
    private static final long h;
    private final ArrayList<E> a;
    private int b;
    private int c;
    private int d;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 16464;
    }

    private b(ArrayList<E> arrayList, int i, int i2, int i3) {
        this.a = arrayList;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(ArrayList<T> arrayList) {
        return new b(arrayList, 0, -1, 0);
    }

    private int b() {
        int i = this.c;
        if (i >= 0) {
            return i;
        }
        ArrayList<E> arrayList = this.a;
        this.d = c(arrayList);
        int b = b(arrayList);
        this.c = b;
        return b;
    }

    /* renamed from: a */
    public b<E> trySplit() {
        int b = b();
        int i = this.b;
        int i2 = (b + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        ArrayList<E> arrayList = this.a;
        this.b = i2;
        return new b<>(arrayList, i, i2, this.d);
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        int b = b();
        int i = this.b;
        if (i >= b) {
            return false;
        }
        this.b = i + 1;
        consumer.accept(d(this.a)[i]);
        if (this.d == c(this.a)) {
            return true;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        int i;
        Objects.requireNonNull(consumer);
        ArrayList<E> arrayList = this.a;
        Object[] d = d(arrayList);
        if (d != null) {
            int i2 = this.c;
            if (i2 < 0) {
                i = c(arrayList);
                i2 = b(arrayList);
            } else {
                i = this.d;
            }
            int i3 = this.b;
            if (i3 >= 0) {
                this.b = i2;
                if (i2 <= d.length) {
                    while (i3 < i2) {
                        consumer.accept(d[i3]);
                        i3++;
                    }
                    if (i == c(arrayList)) {
                        return;
                    }
                }
            }
        }
        throw new ConcurrentModificationException();
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
    public boolean hasCharacteristics(int i) {
        return Spliterators.hasCharacteristics(this, i);
    }

    private static <T> int b(ArrayList<T> arrayList) {
        return e.getInt(arrayList, f);
    }

    private static <T> int c(ArrayList<T> arrayList) {
        return e.getInt(arrayList, g);
    }

    private static <T> Object[] d(ArrayList<T> arrayList) {
        return (Object[]) e.getObject(arrayList, h);
    }

    static {
        try {
            g = e.objectFieldOffset(AbstractList.class.getDeclaredField("modCount"));
            f = e.objectFieldOffset(ArrayList.class.getDeclaredField("size"));
            h = e.objectFieldOffset(ArrayList.class.getDeclaredField(Spliterators.d ? "array" : "elementData"));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }
}
