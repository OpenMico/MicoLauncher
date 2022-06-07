package java8.util;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RASpliterator.java */
/* loaded from: classes5.dex */
public final class ag<E> implements Spliterator<E> {
    private static final Unsafe f = al.a;
    private static final long g;
    private final List<E> a;
    private int b;
    private int c;
    private final AbstractList<E> d;
    private int e;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 16464;
    }

    private ag(List<E> list, int i, int i2, int i3) {
        this.a = list;
        this.b = i;
        this.c = i2;
        this.d = list instanceof AbstractList ? (AbstractList) list : null;
        this.e = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(List<T> list) {
        return new ag(list, 0, -1, 0);
    }

    private int a() {
        List<E> list = this.a;
        int i = this.c;
        if (i >= 0) {
            return i;
        }
        AbstractList<E> abstractList = this.d;
        if (abstractList != null) {
            this.e = b(abstractList);
        }
        int size = list.size();
        this.c = size;
        return size;
    }

    @Override // java8.util.Spliterator
    public Spliterator<E> trySplit() {
        int a = a();
        int i = this.b;
        int i2 = (a + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        List<E> list = this.a;
        this.b = i2;
        return new ag(list, i, i2, this.e);
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        int a = a();
        int i = this.b;
        if (i >= a) {
            return false;
        }
        this.b = i + 1;
        consumer.accept((E) this.a.get(i));
        a(this.d, this.e);
        return true;
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        List<E> list = this.a;
        int a = a();
        this.b = a;
        for (int i = this.b; i < a; i++) {
            try {
                consumer.accept((E) list.get(i));
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
        a(this.d, this.e);
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return a() - this.b;
    }

    @Override // java8.util.Spliterator
    public long getExactSizeIfKnown() {
        return Spliterators.getExactSizeIfKnown(this);
    }

    @Override // java8.util.Spliterator
    public boolean hasCharacteristics(int i) {
        return Spliterators.hasCharacteristics(this, i);
    }

    @Override // java8.util.Spliterator
    public Comparator<? super E> getComparator() {
        return Spliterators.getComparator(this);
    }

    private static void a(AbstractList<?> abstractList, int i) {
        if (abstractList != null && b(abstractList) != i) {
            throw new ConcurrentModificationException();
        }
    }

    private static <T> int b(List<T> list) {
        return f.getInt(list, g);
    }

    static {
        try {
            g = f.objectFieldOffset(AbstractList.class.getDeclaredField("modCount"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
