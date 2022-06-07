package java8.util;

import build.IgnoreJava8API;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java8.util.PrimitiveIterator;
import java8.util.Spliterator;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.LongConsumer;

/* loaded from: classes5.dex */
public final class Spliterators {
    static final boolean d;
    static final boolean e;
    static final boolean f;
    static final boolean g;
    static final boolean h;
    private static final Spliterator<Object> n;
    private static final Spliterator.OfInt o;
    private static final Spliterator.OfLong p;
    private static final Spliterator.OfDouble q;
    private static final String i = Spliterators.class.getName() + ".assume.oracle.collections.impl";
    private static final String j = Spliterators.class.getName() + ".jre.delegation.enabled";
    private static final String k = Spliterators.class.getName() + ".randomaccess.spliterator.enabled";
    static final boolean a = a(i, true);
    static final boolean b = a(j, true);
    private static final boolean l = a(k, true);
    private static final boolean m = b();
    static final boolean c = c();

    static {
        boolean z = true;
        d = c && !a("android.opengl.GLES32$DebugProc");
        e = c && a("java.time.DateTimeException");
        if (c || !a()) {
            z = false;
        }
        f = z;
        g = d();
        h = a("java.lang.StackWalker$Option");
        n = new h.d();
        o = new h.b();
        p = new h.c();
        q = new h.a();
    }

    private Spliterators() {
    }

    public static <T> void forEachRemaining(Spliterator<T> spliterator, Consumer<? super T> consumer) {
        do {
        } while (spliterator.tryAdvance(consumer));
    }

    public static <T> long getExactSizeIfKnown(Spliterator<T> spliterator) {
        if ((spliterator.characteristics() & 64) == 0) {
            return -1L;
        }
        return spliterator.estimateSize();
    }

    public static <T> boolean hasCharacteristics(Spliterator<T> spliterator, int i2) {
        return (spliterator.characteristics() & i2) == i2;
    }

    public static <T> Comparator<? super T> getComparator(Spliterator<T> spliterator) {
        throw new IllegalStateException();
    }

    /* loaded from: classes5.dex */
    public static final class OfPrimitive {
        public static <T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>> void forEachRemaining(Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> ofPrimitive, T_CONS t_cons) {
            do {
            } while (ofPrimitive.tryAdvance((Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>) t_cons));
        }

        private OfPrimitive() {
        }
    }

    /* loaded from: classes5.dex */
    public static final class OfInt {
        public static void forEachRemaining(Spliterator.OfInt ofInt, IntConsumer intConsumer) {
            do {
            } while (ofInt.tryAdvance(intConsumer));
        }

        public static boolean tryAdvance(Spliterator.OfInt ofInt, Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                return ofInt.tryAdvance((IntConsumer) consumer);
            }
            return ofInt.tryAdvance(a(consumer));
        }

        private static IntConsumer a(Consumer<? super Integer> consumer) {
            consumer.getClass();
            return ai.a(consumer);
        }

        public static void forEachRemaining(Spliterator.OfInt ofInt, Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                ofInt.forEachRemaining((IntConsumer) consumer);
            } else {
                ofInt.forEachRemaining(a(consumer));
            }
        }

        private OfInt() {
        }
    }

    /* loaded from: classes5.dex */
    public static final class OfLong {
        public static void forEachRemaining(Spliterator.OfLong ofLong, LongConsumer longConsumer) {
            do {
            } while (ofLong.tryAdvance(longConsumer));
        }

        public static boolean tryAdvance(Spliterator.OfLong ofLong, Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                return ofLong.tryAdvance((LongConsumer) consumer);
            }
            return ofLong.tryAdvance(a(consumer));
        }

        private static LongConsumer a(Consumer<? super Long> consumer) {
            consumer.getClass();
            return aj.a(consumer);
        }

        public static void forEachRemaining(Spliterator.OfLong ofLong, Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                ofLong.forEachRemaining((LongConsumer) consumer);
            } else {
                ofLong.forEachRemaining(a(consumer));
            }
        }

        private OfLong() {
        }
    }

    /* loaded from: classes5.dex */
    public static final class OfDouble {
        public static void forEachRemaining(Spliterator.OfDouble ofDouble, DoubleConsumer doubleConsumer) {
            do {
            } while (ofDouble.tryAdvance(doubleConsumer));
        }

        public static boolean tryAdvance(Spliterator.OfDouble ofDouble, Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                return ofDouble.tryAdvance((DoubleConsumer) consumer);
            }
            return ofDouble.tryAdvance(a(consumer));
        }

        private static DoubleConsumer a(Consumer<? super Double> consumer) {
            consumer.getClass();
            return ah.a(consumer);
        }

        public static void forEachRemaining(Spliterator.OfDouble ofDouble, Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                ofDouble.forEachRemaining((DoubleConsumer) consumer);
            } else {
                ofDouble.forEachRemaining(a(consumer));
            }
        }

        private OfDouble() {
        }
    }

    public static <T> Spliterator<T> emptySpliterator() {
        return (Spliterator<T>) n;
    }

    public static Spliterator.OfInt emptyIntSpliterator() {
        return o;
    }

    public static Spliterator.OfLong emptyLongSpliterator() {
        return p;
    }

    public static Spliterator.OfDouble emptyDoubleSpliterator() {
        return q;
    }

    public static <T> Spliterator<T> spliterator(Object[] objArr, int i2) {
        return new e((Object[]) Objects.requireNonNull(objArr), i2);
    }

    public static <T> Spliterator<T> spliterator(Object[] objArr, int i2, int i3, int i4) {
        a(((Object[]) Objects.requireNonNull(objArr)).length, i2, i3);
        return new e(objArr, i2, i3, i4);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i2) {
        return new i((int[]) Objects.requireNonNull(iArr), i2);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i2, int i3, int i4) {
        a(((int[]) Objects.requireNonNull(iArr)).length, i2, i3);
        return new i(iArr, i2, i3, i4);
    }

    public static Spliterator.OfLong spliterator(long[] jArr, int i2) {
        return new l((long[]) Objects.requireNonNull(jArr), i2);
    }

    public static Spliterator.OfLong spliterator(long[] jArr, int i2, int i3, int i4) {
        a(((long[]) Objects.requireNonNull(jArr)).length, i2, i3);
        return new l(jArr, i2, i3, i4);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr, int i2) {
        return new f((double[]) Objects.requireNonNull(dArr), i2);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr, int i2, int i3, int i4) {
        a(((double[]) Objects.requireNonNull(dArr)).length, i2, i3);
        return new f(dArr, i2, i3, i4);
    }

    private static void a(int i2, int i3, int i4) {
        if (i3 > i4) {
            throw new ArrayIndexOutOfBoundsException("origin(" + i3 + ") > fence(" + i4 + ")");
        } else if (i3 < 0) {
            throw new ArrayIndexOutOfBoundsException(i3);
        } else if (i4 > i2) {
            throw new ArrayIndexOutOfBoundsException(i4);
        }
    }

    public static <T> Spliterator<T> spliterator(Collection<? extends T> collection) {
        Objects.requireNonNull(collection);
        if (g && ((b || h) && !b(collection))) {
            return a(collection);
        }
        String name = collection.getClass().getName();
        if (collection instanceof List) {
            return a((List) collection, name);
        }
        if (collection instanceof Set) {
            return a((Set) collection, name);
        }
        if (collection instanceof Queue) {
            return a((Queue) collection);
        }
        if (d || !a || !"java.util.HashMap$Values".equals(name)) {
            return spliterator(collection, 0);
        }
        return q.a(collection);
    }

    private static <T> Spliterator<T> a(List<? extends T> list, String str) {
        if (a || c) {
            if (list instanceof ArrayList) {
                return b.a((ArrayList) list);
            }
            if ("java.util.Arrays$ArrayList".equals(str)) {
                return d.a(list);
            }
            if (list instanceof CopyOnWriteArrayList) {
                return f.a((CopyOnWriteArrayList) list);
            }
            if (list instanceof LinkedList) {
                return z.a((LinkedList) list);
            }
            if (list instanceof Vector) {
                return am.a((Vector) list);
            }
        }
        if (l && (list instanceof RandomAccess)) {
            if (!(list instanceof AbstractList) && b(str)) {
                return spliterator(list, 16);
            }
            if (!(list instanceof CopyOnWriteArrayList)) {
                return ag.a(list);
            }
        }
        return spliterator(list, 16);
    }

    private static <T> Spliterator<T> a(final Set<? extends T> set, String str) {
        if (!d && a) {
            if ("java.util.HashMap$EntrySet".equals(str)) {
                return q.b((Set) set);
            }
            if ("java.util.HashMap$KeySet".equals(str)) {
                return q.a((Set) set);
            }
        }
        if (set instanceof LinkedHashSet) {
            return spliterator(set, 17);
        }
        if (!d && a && (set instanceof HashSet)) {
            return q.a((HashSet) set);
        }
        if (set instanceof SortedSet) {
            return new k<T>(set, 21) { // from class: java8.util.Spliterators.1
                @Override // java8.util.Spliterators.k, java8.util.Spliterator
                public Comparator<? super T> getComparator() {
                    return ((SortedSet) set).comparator();
                }
            };
        }
        if ((a || c) && (set instanceof CopyOnWriteArraySet)) {
            return g.a((CopyOnWriteArraySet) set);
        }
        return spliterator(set, 1);
    }

    private static <T> Spliterator<T> a(Queue<? extends T> queue) {
        if (queue instanceof ArrayBlockingQueue) {
            return spliterator(queue, 4368);
        }
        if (a || c) {
            if (queue instanceof LinkedBlockingQueue) {
                return y.a((LinkedBlockingQueue) queue);
            }
            if (queue instanceof ArrayDeque) {
                return a.a((ArrayDeque) queue);
            }
            if (queue instanceof LinkedBlockingDeque) {
                return x.a((LinkedBlockingDeque) queue);
            }
            if (queue instanceof PriorityBlockingQueue) {
                return ae.a((PriorityBlockingQueue) queue);
            }
            if (queue instanceof PriorityQueue) {
                return af.a((PriorityQueue) queue);
            }
        }
        if (!(queue instanceof Deque) && (!queue.getClass().getName().startsWith("java.util") || (queue instanceof PriorityBlockingQueue) || (queue instanceof PriorityQueue) || (queue instanceof DelayQueue) || (queue instanceof SynchronousQueue))) {
            return spliterator(queue, 0);
        }
        return spliterator(queue, queue instanceof ArrayDeque ? 272 : 16);
    }

    @IgnoreJava8API
    private static <T> Spliterator<T> a(Collection<? extends T> collection) {
        return new o(collection.spliterator());
    }

    public static <T> Spliterator<T> spliterator(Collection<? extends T> collection, int i2) {
        return new k((Collection) Objects.requireNonNull(collection), i2);
    }

    public static <T> Spliterator<T> spliterator(Iterator<? extends T> it, long j2, int i2) {
        return new k((Iterator) Objects.requireNonNull(it), j2, i2);
    }

    public static <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> it, int i2) {
        return new k((Iterator) Objects.requireNonNull(it), i2);
    }

    public static Spliterator.OfInt spliterator(PrimitiveIterator.OfInt ofInt, long j2, int i2) {
        return new j((PrimitiveIterator.OfInt) Objects.requireNonNull(ofInt), j2, i2);
    }

    public static Spliterator.OfInt spliteratorUnknownSize(PrimitiveIterator.OfInt ofInt, int i2) {
        return new j((PrimitiveIterator.OfInt) Objects.requireNonNull(ofInt), i2);
    }

    public static Spliterator.OfLong spliterator(PrimitiveIterator.OfLong ofLong, long j2, int i2) {
        return new m((PrimitiveIterator.OfLong) Objects.requireNonNull(ofLong), j2, i2);
    }

    public static Spliterator.OfLong spliteratorUnknownSize(PrimitiveIterator.OfLong ofLong, int i2) {
        return new m((PrimitiveIterator.OfLong) Objects.requireNonNull(ofLong), i2);
    }

    public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble ofDouble, long j2, int i2) {
        return new g((PrimitiveIterator.OfDouble) Objects.requireNonNull(ofDouble), j2, i2);
    }

    public static Spliterator.OfDouble spliteratorUnknownSize(PrimitiveIterator.OfDouble ofDouble, int i2) {
        return new g((PrimitiveIterator.OfDouble) Objects.requireNonNull(ofDouble), i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public class a implements Iterator<T>, Consumer<T> {
        boolean a = false;
        T b;
        final /* synthetic */ Spliterator c;

        a(Spliterator spliterator) {
            this.c = spliterator;
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            this.a = true;
            this.b = t;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.a) {
                this.c.tryAdvance(this);
            }
            return this.a;
        }

        @Override // java.util.Iterator
        public T next() {
            if (this.a || hasNext()) {
                this.a = false;
                return this.b;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static <T> Iterator<T> iterator(Spliterator<? extends T> spliterator) {
        Objects.requireNonNull(spliterator);
        return new a(spliterator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public class b implements PrimitiveIterator.OfInt, IntConsumer {
        boolean a = false;
        int b;
        final /* synthetic */ Spliterator.OfInt c;

        b(Spliterator.OfInt ofInt) {
            this.c = ofInt;
        }

        @Override // java8.util.function.IntConsumer
        public void accept(int i) {
            this.a = true;
            this.b = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.a) {
                this.c.tryAdvance((IntConsumer) this);
            }
            return this.a;
        }

        @Override // java8.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (this.a || hasNext()) {
                this.a = false;
                return this.b;
            }
            throw new NoSuchElementException();
        }

        @Override // java8.util.PrimitiveIterator.OfInt, java.util.Iterator
        public Integer next() {
            return Integer.valueOf(nextInt());
        }

        @Override // java8.util.PrimitiveIterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            Iterators.forEachRemaining(this, intConsumer);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static PrimitiveIterator.OfInt iterator(Spliterator.OfInt ofInt) {
        Objects.requireNonNull(ofInt);
        return new b(ofInt);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public class c implements PrimitiveIterator.OfLong, LongConsumer {
        boolean a = false;
        long b;
        final /* synthetic */ Spliterator.OfLong c;

        c(Spliterator.OfLong ofLong) {
            this.c = ofLong;
        }

        @Override // java8.util.function.LongConsumer
        public void accept(long j) {
            this.a = true;
            this.b = j;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.a) {
                this.c.tryAdvance((LongConsumer) this);
            }
            return this.a;
        }

        @Override // java8.util.PrimitiveIterator.OfLong
        public long nextLong() {
            if (this.a || hasNext()) {
                this.a = false;
                return this.b;
            }
            throw new NoSuchElementException();
        }

        @Override // java8.util.PrimitiveIterator.OfLong, java.util.Iterator
        public Long next() {
            return Long.valueOf(nextLong());
        }

        @Override // java8.util.PrimitiveIterator.OfLong
        public void forEachRemaining(LongConsumer longConsumer) {
            Iterators.forEachRemaining(this, longConsumer);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static PrimitiveIterator.OfLong iterator(Spliterator.OfLong ofLong) {
        Objects.requireNonNull(ofLong);
        return new c(ofLong);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public class d implements PrimitiveIterator.OfDouble, DoubleConsumer {
        boolean a = false;
        double b;
        final /* synthetic */ Spliterator.OfDouble c;

        d(Spliterator.OfDouble ofDouble) {
            this.c = ofDouble;
        }

        @Override // java8.util.function.DoubleConsumer
        public void accept(double d) {
            this.a = true;
            this.b = d;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.a) {
                this.c.tryAdvance((DoubleConsumer) this);
            }
            return this.a;
        }

        @Override // java8.util.PrimitiveIterator.OfDouble
        public double nextDouble() {
            if (this.a || hasNext()) {
                this.a = false;
                return this.b;
            }
            throw new NoSuchElementException();
        }

        @Override // java8.util.PrimitiveIterator.OfDouble, java.util.Iterator
        public Double next() {
            return Double.valueOf(nextDouble());
        }

        @Override // java8.util.PrimitiveIterator.OfDouble
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            Iterators.forEachRemaining(this, doubleConsumer);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static PrimitiveIterator.OfDouble iterator(Spliterator.OfDouble ofDouble) {
        Objects.requireNonNull(ofDouble);
        return new d(ofDouble);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static abstract class h<T, S extends Spliterator<T>, C> {
        public int characteristics() {
            return 16448;
        }

        public long estimateSize() {
            return 0L;
        }

        public S trySplit() {
            return null;
        }

        h() {
        }

        public boolean tryAdvance(C c2) {
            Objects.requireNonNull(c2);
            return false;
        }

        public void forEachRemaining(C c2) {
            Objects.requireNonNull(c2);
        }

        /* loaded from: classes5.dex */
        private static final class d<T> extends h<T, Spliterator<T>, Consumer<? super T>> implements Spliterator<T> {
            @Override // java8.util.Spliterator
            public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
                super.forEachRemaining((d<T>) consumer);
            }

            @Override // java8.util.Spliterator
            public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return super.tryAdvance((d<T>) consumer);
            }

            d() {
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
            public Comparator<? super T> getComparator() {
                throw new IllegalStateException();
            }
        }

        /* loaded from: classes5.dex */
        private static final class b extends h<Integer, Spliterator.OfInt, IntConsumer> implements Spliterator.OfInt {
            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((b) intConsumer);
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((b) intConsumer);
            }

            b() {
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
            public Comparator<? super Integer> getComparator() {
                throw new IllegalStateException();
            }

            @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Integer> consumer) {
                return OfInt.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Integer> consumer) {
                OfInt.forEachRemaining(this, consumer);
            }
        }

        /* loaded from: classes5.dex */
        private static final class c extends h<Long, Spliterator.OfLong, LongConsumer> implements Spliterator.OfLong {
            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((c) longConsumer);
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((c) longConsumer);
            }

            c() {
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
            public Comparator<? super Long> getComparator() {
                throw new IllegalStateException();
            }

            @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Long> consumer) {
                return OfLong.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Long> consumer) {
                OfLong.forEachRemaining(this, consumer);
            }
        }

        /* loaded from: classes5.dex */
        private static final class a extends h<Double, Spliterator.OfDouble, DoubleConsumer> implements Spliterator.OfDouble {
            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((a) doubleConsumer);
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((a) doubleConsumer);
            }

            a() {
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
            public Comparator<? super Double> getComparator() {
                throw new IllegalStateException();
            }

            @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Double> consumer) {
                return OfDouble.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Double> consumer) {
                OfDouble.forEachRemaining(this, consumer);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class e<T> implements Spliterator<T> {
        private final Object[] a;
        private int b;
        private final int c;
        private final int d;

        public e(Object[] objArr, int i) {
            this(objArr, 0, objArr.length, i);
        }

        public e(Object[] objArr, int i, int i2, int i3) {
            this.a = objArr;
            this.b = i;
            this.c = i2;
            this.d = i3 | 64 | 16384;
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
        public Spliterator<T> trySplit() {
            int i = this.b;
            int i2 = (this.c + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            Object[] objArr = this.a;
            this.b = i2;
            return new e(objArr, i, i2, this.d);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            int i;
            Objects.requireNonNull(consumer);
            Object[] objArr = this.a;
            int length = objArr.length;
            int i2 = this.c;
            if (length >= i2 && (i = this.b) >= 0) {
                this.b = i2;
                if (i < i2) {
                    do {
                        consumer.accept(objArr[i]);
                        i++;
                    } while (i < i2);
                }
            }
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            int i = this.b;
            if (i < 0 || i >= this.c) {
                return false;
            }
            Object[] objArr = this.a;
            this.b = i + 1;
            consumer.accept(objArr[i]);
            return true;
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c - this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.d;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super T> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes5.dex */
    public static final class i implements Spliterator.OfInt {
        private final int[] a;
        private int b;
        private final int c;
        private final int d;

        public i(int[] iArr, int i) {
            this(iArr, 0, iArr.length, i);
        }

        public i(int[] iArr, int i, int i2, int i3) {
            this.a = iArr;
            this.b = i;
            this.c = i2;
            this.d = i3 | 64 | 16384;
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfInt trySplit() {
            int i = this.b;
            int i2 = (this.c + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            int[] iArr = this.a;
            this.b = i2;
            return new i(iArr, i, i2, this.d);
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            int i;
            Objects.requireNonNull(intConsumer);
            int[] iArr = this.a;
            int length = iArr.length;
            int i2 = this.c;
            if (length >= i2 && (i = this.b) >= 0) {
                this.b = i2;
                if (i < i2) {
                    do {
                        intConsumer.accept(iArr[i]);
                        i++;
                    } while (i < i2);
                }
            }
        }

        @Override // java8.util.Spliterator.OfInt
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            int i = this.b;
            if (i < 0 || i >= this.c) {
                return false;
            }
            int[] iArr = this.a;
            this.b = i + 1;
            intConsumer.accept(iArr[i]);
            return true;
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return OfInt.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            OfInt.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c - this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.d;
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
        public Comparator<? super Integer> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes5.dex */
    public static final class l implements Spliterator.OfLong {
        private final long[] a;
        private int b;
        private final int c;
        private final int d;

        public l(long[] jArr, int i) {
            this(jArr, 0, jArr.length, i);
        }

        public l(long[] jArr, int i, int i2, int i3) {
            this.a = jArr;
            this.b = i;
            this.c = i2;
            this.d = i3 | 64 | 16384;
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfLong trySplit() {
            int i = this.b;
            int i2 = (this.c + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            long[] jArr = this.a;
            this.b = i2;
            return new l(jArr, i, i2, this.d);
        }

        @Override // java8.util.Spliterator.OfLong
        public void forEachRemaining(LongConsumer longConsumer) {
            int i;
            Objects.requireNonNull(longConsumer);
            long[] jArr = this.a;
            int length = jArr.length;
            int i2 = this.c;
            if (length >= i2 && (i = this.b) >= 0) {
                this.b = i2;
                if (i < i2) {
                    do {
                        longConsumer.accept(jArr[i]);
                        i++;
                    } while (i < i2);
                }
            }
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            OfLong.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator.OfLong
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            int i = this.b;
            if (i < 0 || i >= this.c) {
                return false;
            }
            long[] jArr = this.a;
            this.b = i + 1;
            longConsumer.accept(jArr[i]);
            return true;
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Long> consumer) {
            return OfLong.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c - this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.d;
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
        public Comparator<? super Long> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes5.dex */
    public static final class f implements Spliterator.OfDouble {
        private final double[] a;
        private int b;
        private final int c;
        private final int d;

        public f(double[] dArr, int i) {
            this(dArr, 0, dArr.length, i);
        }

        public f(double[] dArr, int i, int i2, int i3) {
            this.a = dArr;
            this.b = i;
            this.c = i2;
            this.d = i3 | 64 | 16384;
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfDouble trySplit() {
            int i = this.b;
            int i2 = (this.c + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            double[] dArr = this.a;
            this.b = i2;
            return new f(dArr, i, i2, this.d);
        }

        @Override // java8.util.Spliterator.OfDouble
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            int i;
            Objects.requireNonNull(doubleConsumer);
            double[] dArr = this.a;
            int length = dArr.length;
            int i2 = this.c;
            if (length >= i2 && (i = this.b) >= 0) {
                this.b = i2;
                if (i < i2) {
                    do {
                        doubleConsumer.accept(dArr[i]);
                        i++;
                    } while (i < i2);
                }
            }
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Double> consumer) {
            OfDouble.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator.OfDouble
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            int i = this.b;
            if (i < 0 || i >= this.c) {
                return false;
            }
            double[] dArr = this.a;
            this.b = i + 1;
            doubleConsumer.accept(dArr[i]);
            return true;
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return OfDouble.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c - this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.d;
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
        public Comparator<? super Double> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class AbstractSpliterator<T> implements Spliterator<T> {
        private final int a;
        private long b;
        private int c;

        public AbstractSpliterator(long j, int i) {
            this.b = j;
            this.a = (i & 64) != 0 ? i | 16384 : i;
        }

        /* loaded from: classes5.dex */
        static final class a<T> implements Consumer<T> {
            Object a;

            a() {
            }

            @Override // java8.util.function.Consumer
            public void accept(T t) {
                this.a = t;
            }
        }

        @Override // java8.util.Spliterator
        public Spliterator<T> trySplit() {
            a aVar = new a();
            long j = this.b;
            if (j <= 1 || !tryAdvance(aVar)) {
                return null;
            }
            int i = this.c + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            Object[] objArr = new Object[i2];
            int i3 = 0;
            do {
                objArr[i3] = aVar.a;
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (tryAdvance(aVar));
            this.c = i3;
            long j2 = this.b;
            if (j2 != Long.MAX_VALUE) {
                this.b = j2 - i3;
            }
            return new e(objArr, 0, i3, characteristics());
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.a;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            if ((characteristics() & 64) == 0) {
                return -1L;
            }
            return estimateSize();
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return (characteristics() & i) == i;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super T> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            do {
            } while (tryAdvance(consumer));
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class AbstractIntSpliterator implements Spliterator.OfInt {
        private final int a;
        private long b;
        private int c;

        public AbstractIntSpliterator(long j, int i) {
            this.b = j;
            this.a = (i & 64) != 0 ? i | 16384 : i;
        }

        /* loaded from: classes5.dex */
        public static final class a implements IntConsumer {
            int a;

            a() {
            }

            @Override // java8.util.function.IntConsumer
            public void accept(int i) {
                this.a = i;
            }
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfInt trySplit() {
            a aVar = new a();
            long j = this.b;
            if (j <= 1 || !tryAdvance((IntConsumer) aVar)) {
                return null;
            }
            int i = this.c + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            int[] iArr = new int[i2];
            int i3 = 0;
            do {
                iArr[i3] = aVar.a;
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (tryAdvance((IntConsumer) aVar));
            this.c = i3;
            long j2 = this.b;
            if (j2 != Long.MAX_VALUE) {
                this.b = j2 - i3;
            }
            return new i(iArr, 0, i3, characteristics());
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.a;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            if ((characteristics() & 64) == 0) {
                return -1L;
            }
            return estimateSize();
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return (characteristics() & i) == i;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super Integer> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            do {
            } while (tryAdvance(intConsumer));
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            OfInt.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return OfInt.tryAdvance(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class AbstractLongSpliterator implements Spliterator.OfLong {
        private final int a;
        private long b;
        private int c;

        public AbstractLongSpliterator(long j, int i) {
            this.b = j;
            this.a = (i & 64) != 0 ? i | 16384 : i;
        }

        /* loaded from: classes5.dex */
        public static final class a implements LongConsumer {
            long a;

            a() {
            }

            @Override // java8.util.function.LongConsumer
            public void accept(long j) {
                this.a = j;
            }
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfLong trySplit() {
            a aVar = new a();
            long j = this.b;
            if (j <= 1 || !tryAdvance((LongConsumer) aVar)) {
                return null;
            }
            int i = this.c + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            long[] jArr = new long[i2];
            int i3 = 0;
            do {
                jArr[i3] = aVar.a;
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (tryAdvance((LongConsumer) aVar));
            this.c = i3;
            long j2 = this.b;
            if (j2 != Long.MAX_VALUE) {
                this.b = j2 - i3;
            }
            return new l(jArr, 0, i3, characteristics());
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.a;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            if ((characteristics() & 64) == 0) {
                return -1L;
            }
            return estimateSize();
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return (characteristics() & i) == i;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super Long> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator.OfLong
        public void forEachRemaining(LongConsumer longConsumer) {
            do {
            } while (tryAdvance(longConsumer));
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            OfLong.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Long> consumer) {
            return OfLong.tryAdvance(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class AbstractDoubleSpliterator implements Spliterator.OfDouble {
        private final int a;
        private long b;
        private int c;

        public AbstractDoubleSpliterator(long j, int i) {
            this.b = j;
            this.a = (i & 64) != 0 ? i | 16384 : i;
        }

        /* loaded from: classes5.dex */
        public static final class a implements DoubleConsumer {
            double a;

            a() {
            }

            @Override // java8.util.function.DoubleConsumer
            public void accept(double d) {
                this.a = d;
            }
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfDouble trySplit() {
            a aVar = new a();
            long j = this.b;
            if (j <= 1 || !tryAdvance((DoubleConsumer) aVar)) {
                return null;
            }
            int i = this.c + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            double[] dArr = new double[i2];
            int i3 = 0;
            do {
                dArr[i3] = aVar.a;
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (tryAdvance((DoubleConsumer) aVar));
            this.c = i3;
            long j2 = this.b;
            if (j2 != Long.MAX_VALUE) {
                this.b = j2 - i3;
            }
            return new f(dArr, 0, i3, characteristics());
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.a;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            if ((characteristics() & 64) == 0) {
                return -1L;
            }
            return estimateSize();
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return (characteristics() & i) == i;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super Double> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator.OfDouble
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            do {
            } while (tryAdvance(doubleConsumer));
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Double> consumer) {
            OfDouble.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return OfDouble.tryAdvance(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    public static class k<T> implements Spliterator<T> {
        private final Collection<? extends T> a;
        private Iterator<? extends T> b;
        private final int c;
        private long d;
        private int e;

        public k(Collection<? extends T> collection, int i) {
            this.a = collection;
            this.b = null;
            this.c = (i & 4096) == 0 ? i | 64 | 16384 : i;
        }

        public k(Iterator<? extends T> it, long j, int i) {
            this.a = null;
            this.b = it;
            this.d = j;
            this.c = (i & 4096) == 0 ? i | 64 | 16384 : i;
        }

        public k(Iterator<? extends T> it, int i) {
            this.a = null;
            this.b = it;
            this.d = Long.MAX_VALUE;
            this.c = i & (-16449);
        }

        @Override // java8.util.Spliterator
        public Spliterator<T> trySplit() {
            long j;
            Iterator<? extends T> it = this.b;
            if (it == null) {
                it = this.a.iterator();
                this.b = it;
                j = this.a.size();
                this.d = j;
            } else {
                j = this.d;
            }
            if (j <= 1 || !it.hasNext()) {
                return null;
            }
            int i = this.e + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            Object[] objArr = new Object[i2];
            int i3 = 0;
            do {
                objArr[i3] = it.next();
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (it.hasNext());
            this.e = i3;
            long j2 = this.d;
            if (j2 != Long.MAX_VALUE) {
                this.d = j2 - i3;
            }
            return new e(objArr, 0, i3, this.c);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            Iterator<? extends T> it = this.b;
            if (it == null) {
                it = this.a.iterator();
                this.b = it;
                this.d = this.a.size();
            }
            Iterators.forEachRemaining(it, consumer);
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            if (this.b == null) {
                this.b = this.a.iterator();
                this.d = this.a.size();
            }
            if (!this.b.hasNext()) {
                return false;
            }
            consumer.accept((Object) this.b.next());
            return true;
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            if (this.b != null) {
                return this.d;
            }
            this.b = this.a.iterator();
            long size = this.a.size();
            this.d = size;
            return size;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.c;
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
        public Comparator<? super T> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes5.dex */
    static final class j implements Spliterator.OfInt {
        private PrimitiveIterator.OfInt a;
        private final int b;
        private long c;
        private int d;

        public j(PrimitiveIterator.OfInt ofInt, long j, int i) {
            this.a = ofInt;
            this.c = j;
            this.b = (i & 4096) == 0 ? i | 64 | 16384 : i;
        }

        public j(PrimitiveIterator.OfInt ofInt, int i) {
            this.a = ofInt;
            this.c = Long.MAX_VALUE;
            this.b = i & (-16449);
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfInt trySplit() {
            PrimitiveIterator.OfInt ofInt = this.a;
            long j = this.c;
            if (j <= 1 || !ofInt.hasNext()) {
                return null;
            }
            int i = this.d + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            int[] iArr = new int[i2];
            int i3 = 0;
            do {
                iArr[i3] = ofInt.nextInt();
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (ofInt.hasNext());
            this.d = i3;
            long j2 = this.c;
            if (j2 != Long.MAX_VALUE) {
                this.c = j2 - i3;
            }
            return new i(iArr, 0, i3, this.b);
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            Iterators.forEachRemaining(this.a, (IntConsumer) Objects.requireNonNull(intConsumer));
        }

        @Override // java8.util.Spliterator.OfInt
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (!this.a.hasNext()) {
                return false;
            }
            intConsumer.accept(this.a.nextInt());
            return true;
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.b;
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
        public Comparator<? super Integer> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return OfInt.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            OfInt.forEachRemaining(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    static final class m implements Spliterator.OfLong {
        private PrimitiveIterator.OfLong a;
        private final int b;
        private long c;
        private int d;

        public m(PrimitiveIterator.OfLong ofLong, long j, int i) {
            this.a = ofLong;
            this.c = j;
            this.b = (i & 4096) == 0 ? i | 64 | 16384 : i;
        }

        public m(PrimitiveIterator.OfLong ofLong, int i) {
            this.a = ofLong;
            this.c = Long.MAX_VALUE;
            this.b = i & (-16449);
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfLong trySplit() {
            PrimitiveIterator.OfLong ofLong = this.a;
            long j = this.c;
            if (j <= 1 || !ofLong.hasNext()) {
                return null;
            }
            int i = this.d + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            long[] jArr = new long[i2];
            int i3 = 0;
            do {
                jArr[i3] = ofLong.nextLong();
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (ofLong.hasNext());
            this.d = i3;
            long j2 = this.c;
            if (j2 != Long.MAX_VALUE) {
                this.c = j2 - i3;
            }
            return new l(jArr, 0, i3, this.b);
        }

        @Override // java8.util.Spliterator.OfLong
        public void forEachRemaining(LongConsumer longConsumer) {
            Iterators.forEachRemaining(this.a, (LongConsumer) Objects.requireNonNull(longConsumer));
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            OfLong.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator.OfLong
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            if (!this.a.hasNext()) {
                return false;
            }
            longConsumer.accept(this.a.nextLong());
            return true;
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Long> consumer) {
            return OfLong.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.b;
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
        public Comparator<? super Long> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes5.dex */
    static final class g implements Spliterator.OfDouble {
        private PrimitiveIterator.OfDouble a;
        private final int b;
        private long c;
        private int d;

        public g(PrimitiveIterator.OfDouble ofDouble, long j, int i) {
            this.a = ofDouble;
            this.c = j;
            this.b = (i & 4096) == 0 ? i | 64 | 16384 : i;
        }

        public g(PrimitiveIterator.OfDouble ofDouble, int i) {
            this.a = ofDouble;
            this.c = Long.MAX_VALUE;
            this.b = i & (-16449);
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfDouble trySplit() {
            PrimitiveIterator.OfDouble ofDouble = this.a;
            long j = this.c;
            if (j <= 1 || !ofDouble.hasNext()) {
                return null;
            }
            int i = this.d + 1024;
            if (i > j) {
                i = (int) j;
            }
            int i2 = 33554432;
            if (i <= 33554432) {
                i2 = i;
            }
            double[] dArr = new double[i2];
            int i3 = 0;
            do {
                dArr[i3] = ofDouble.nextDouble();
                i3++;
                if (i3 >= i2) {
                    break;
                }
            } while (ofDouble.hasNext());
            this.d = i3;
            long j2 = this.c;
            if (j2 != Long.MAX_VALUE) {
                this.c = j2 - i3;
            }
            return new f(dArr, 0, i3, this.b);
        }

        @Override // java8.util.Spliterator.OfDouble
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            Iterators.forEachRemaining(this.a, (DoubleConsumer) Objects.requireNonNull(doubleConsumer));
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Double> consumer) {
            OfDouble.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator.OfDouble
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            if (!this.a.hasNext()) {
                return false;
            }
            doubleConsumer.accept(this.a.nextDouble());
            return true;
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return OfDouble.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.b;
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
        public Comparator<? super Double> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    private static boolean a(final String str, final boolean z) {
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() { // from class: java8.util.Spliterators.2
            /* renamed from: a */
            public Boolean run() {
                boolean z2 = z;
                try {
                    z2 = Boolean.parseBoolean(System.getProperty(str, Boolean.toString(z2)).trim());
                } catch (IllegalArgumentException | NullPointerException unused) {
                }
                return Boolean.valueOf(z2);
            }
        })).booleanValue();
    }

    private static boolean a(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str, false, Spliterators.class.getClassLoader());
        } catch (Throwable unused) {
            cls = null;
        }
        return cls != null;
    }

    private static boolean a() {
        return a("java.class.version", 51.0d);
    }

    private static boolean b() {
        return a("org.robovm.rt.bro.Bro");
    }

    private static boolean c() {
        return a("android.util.DisplayMetrics") || m;
    }

    private static boolean d() {
        if (!c() && a("java.class.version", 52.0d)) {
            return false;
        }
        Method method = null;
        Class<?> cls = null;
        for (String str : new String[]{"java.util.function.Consumer", "java.util.Spliterator"}) {
            try {
                cls = Class.forName(str);
            } catch (Exception unused) {
                return false;
            }
        }
        if (cls != null) {
            try {
                method = Collection.class.getDeclaredMethod("spliterator", new Class[0]);
            } catch (Exception unused2) {
                return false;
            }
        }
        return method != null;
    }

    private static boolean a(String str, double d2) {
        try {
            String property = System.getProperty(str);
            if (property != null) {
                return Double.parseDouble(property) < d2;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean b(String str) {
        return str.startsWith("java.util.Collections$", 0) && str.endsWith("RandomAccessList");
    }

    @IgnoreJava8API
    private static boolean b(Collection<?> collection) {
        if (!c || d || e || !collection.getClass().getName().startsWith("java.util.HashMap$")) {
            return false;
        }
        return collection.spliterator().hasCharacteristics(16);
    }
}
