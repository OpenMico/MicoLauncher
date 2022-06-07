package java8.util;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LinkedListSpliterator.java */
/* loaded from: classes5.dex */
public final class z<T> implements Spliterator<T> {
    private static final boolean g = Spliterators.d;
    private static final boolean h = Spliterators.f;
    private static final Unsafe i = al.a;
    private static final long j;
    private static final long k;
    private static final long l;
    private static final long m;
    private static final long n;
    private final LinkedList<T> a;
    private final Object b;
    private Object c;
    private int d;
    private int e;
    private int f;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 16464;
    }

    private z(LinkedList<T> linkedList, int i2, int i3) {
        this.a = linkedList;
        this.d = i2;
        this.e = i3;
        this.b = (h || g) ? b((LinkedList<?>) linkedList) : null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> Spliterator<E> a(LinkedList<E> linkedList) {
        return new z(linkedList, -1, 0);
    }

    private int a() {
        int i2 = this.d;
        if (i2 >= 0) {
            return i2;
        }
        LinkedList<T> linkedList = this.a;
        if (linkedList == null) {
            this.d = 0;
            return 0;
        }
        this.e = e(linkedList);
        this.c = c(linkedList);
        int d = d(linkedList);
        this.d = d;
        return d;
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return a();
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        Object obj = this.b;
        int a = a();
        if (a > 0 && (r2 = this.c) != obj) {
            this.c = obj;
            this.d = 0;
            do {
                Object obj2 = a(obj2);
                consumer.accept((Object) b(obj2));
                if (obj2 == obj) {
                    break;
                }
                a--;
            } while (a > 0);
        }
        if (this.e != e(this.a)) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java8.util.Spliterator
    public Comparator<? super T> getComparator() {
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

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super T> consumer) {
        Object obj;
        Objects.requireNonNull(consumer);
        Object obj2 = this.b;
        if (a() <= 0 || (obj = this.c) == obj2) {
            return false;
        }
        this.d--;
        this.c = a(obj);
        consumer.accept((Object) b(obj));
        if (this.e == e(this.a)) {
            return true;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java8.util.Spliterator
    public Spliterator<T> trySplit() {
        Object obj;
        int i2;
        Object obj2 = this.b;
        int a = a();
        if (a <= 1 || (obj = this.c) == obj2) {
            return null;
        }
        int i3 = this.f + 1024;
        if (i3 > a) {
            i3 = a;
        }
        if (i3 > 33554432) {
            i3 = 33554432;
        }
        Object[] objArr = new Object[i3];
        Object obj3 = obj;
        int i4 = 0;
        while (true) {
            i2 = i4 + 1;
            objArr[i4] = b(obj3);
            obj3 = a(obj3);
            if (obj3 == obj2 || i2 >= i3) {
                break;
            }
            i4 = i2;
        }
        this.c = obj3;
        this.f = i2;
        this.d = a - i2;
        return Spliterators.spliterator(objArr, 0, i2, 16);
    }

    private static Object b(LinkedList<?> linkedList) {
        if (linkedList == null) {
            return null;
        }
        return i.getObject(linkedList, l);
    }

    private Object c(LinkedList<?> linkedList) {
        if (h || g) {
            return a(this.b);
        }
        return i.getObject(linkedList, l);
    }

    private static Object a(Object obj) {
        if (obj != null) {
            return i.getObject(obj, n);
        }
        throw new ConcurrentModificationException();
    }

    private static <E> E b(Object obj) {
        if (obj != null) {
            return (E) i.getObject(obj, m);
        }
        throw new ConcurrentModificationException();
    }

    private static int d(LinkedList<?> linkedList) {
        return i.getInt(linkedList, j);
    }

    private static int e(LinkedList<?> linkedList) {
        return i.getInt(linkedList, k);
    }

    static {
        try {
            k = i.objectFieldOffset(AbstractList.class.getDeclaredField("modCount"));
            String str = g ? "voidLink" : h ? "header" : "first";
            String str2 = g ? "java.util.LinkedList$Link" : h ? "java.util.LinkedList$Entry" : "java.util.LinkedList$Node";
            String str3 = g ? "data" : h ? "element" : "item";
            Class<?> cls = Class.forName(str2);
            j = i.objectFieldOffset(LinkedList.class.getDeclaredField("size"));
            l = i.objectFieldOffset(LinkedList.class.getDeclaredField(str));
            m = i.objectFieldOffset(cls.getDeclaredField(str3));
            n = i.objectFieldOffset(cls.getDeclaredField("next"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
