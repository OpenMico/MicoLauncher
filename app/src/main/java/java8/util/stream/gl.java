package java8.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java8.lang.Iterables;
import java8.util.Comparators;
import java8.util.J8Arrays;
import java8.util.Lists;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.function.IntFunction;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.df;
import java8.util.stream.eg;
import java8.util.stream.eu;
import java8.util.stream.gd;
import java8.util.stream.gn;

/* compiled from: SortedOps.java */
/* loaded from: classes5.dex */
final class gl {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Stream<T> a(a<?, T, ?> aVar) {
        return new k(aVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Stream<T> a(a<?, T, ?> aVar, Comparator<? super T> comparator) {
        return new k(aVar, comparator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> IntStream b(a<?, Integer, ?> aVar) {
        return new i(aVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> LongStream c(a<?, Long, ?> aVar) {
        return new j(aVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> DoubleStream d(a<?, Double, ?> aVar) {
        return new h(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    public static final class k<T> extends gd.b<T, T> {
        private final boolean b;
        private final Comparator<? super T> c;

        k(a<?, T, ?> aVar) {
            super(aVar, gq.REFERENCE, gp.p | gp.n);
            this.b = true;
            this.c = Comparators.naturalOrder();
        }

        k(a<?, T, ?> aVar, Comparator<? super T> comparator) {
            super(aVar, gq.REFERENCE, gp.p | gp.o);
            this.b = false;
            this.c = (Comparator) Objects.requireNonNull(comparator);
        }

        @Override // java8.util.stream.a
        public Sink<T> a(int i, Sink<T> sink) {
            Objects.requireNonNull(sink);
            if (gp.SORTED.a(i) && this.b) {
                return sink;
            }
            if (gp.SIZED.a(i)) {
                return new p(sink, this.c);
            }
            return new l(sink, this.c);
        }

        @Override // java8.util.stream.gd.b, java8.util.stream.a
        public <P_IN> Node<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator, IntFunction<T[]> intFunction) {
            if (gp.SORTED.a(gbVar.c()) && this.b) {
                return gbVar.a(spliterator, false, intFunction);
            }
            T[] asArray = gbVar.a(spliterator, true, intFunction).asArray(intFunction);
            J8Arrays.parallelSort(asArray, this.c);
            return fn.a((Object[]) asArray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    public static final class i extends eg.b<Integer> {
        i(a<?, Integer, ?> aVar) {
            super(aVar, gq.INT_VALUE, gp.p | gp.n);
        }

        @Override // java8.util.stream.a
        public Sink<Integer> a(int i, Sink<Integer> sink) {
            Objects.requireNonNull(sink);
            if (gp.SORTED.a(i)) {
                return sink;
            }
            if (gp.SIZED.a(i)) {
                return new n(sink);
            }
            return new f(sink);
        }

        @Override // java8.util.stream.eg.b, java8.util.stream.a
        public <P_IN> Node<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator, IntFunction<Integer[]> intFunction) {
            if (gp.SORTED.a(gbVar.c())) {
                return gbVar.a(spliterator, false, intFunction);
            }
            int[] asPrimitiveArray = ((Node.OfInt) gbVar.a(spliterator, true, intFunction)).asPrimitiveArray();
            J8Arrays.parallelSort(asPrimitiveArray);
            return fn.a(asPrimitiveArray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    public static final class j extends eu.b<Long> {
        j(a<?, Long, ?> aVar) {
            super(aVar, gq.LONG_VALUE, gp.p | gp.n);
        }

        @Override // java8.util.stream.a
        public Sink<Long> a(int i, Sink<Long> sink) {
            Objects.requireNonNull(sink);
            if (gp.SORTED.a(i)) {
                return sink;
            }
            if (gp.SIZED.a(i)) {
                return new o(sink);
            }
            return new g(sink);
        }

        @Override // java8.util.stream.eu.b, java8.util.stream.a
        public <P_IN> Node<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator, IntFunction<Long[]> intFunction) {
            if (gp.SORTED.a(gbVar.c())) {
                return gbVar.a(spliterator, false, intFunction);
            }
            long[] asPrimitiveArray = ((Node.OfLong) gbVar.a(spliterator, true, intFunction)).asPrimitiveArray();
            J8Arrays.parallelSort(asPrimitiveArray);
            return fn.a(asPrimitiveArray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    public static final class h extends df.b<Double> {
        h(a<?, Double, ?> aVar) {
            super(aVar, gq.DOUBLE_VALUE, gp.p | gp.n);
        }

        @Override // java8.util.stream.a
        public Sink<Double> a(int i, Sink<Double> sink) {
            Objects.requireNonNull(sink);
            if (gp.SORTED.a(i)) {
                return sink;
            }
            if (gp.SIZED.a(i)) {
                return new m(sink);
            }
            return new e(sink);
        }

        @Override // java8.util.stream.df.b, java8.util.stream.a
        public <P_IN> Node<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator, IntFunction<Double[]> intFunction) {
            if (gp.SORTED.a(gbVar.c())) {
                return gbVar.a(spliterator, false, intFunction);
            }
            double[] asPrimitiveArray = ((Node.OfDouble) gbVar.a(spliterator, true, intFunction)).asPrimitiveArray();
            J8Arrays.parallelSort(asPrimitiveArray);
            return fn.a(asPrimitiveArray);
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static abstract class d<T> extends Sink.ChainedReference<T, T> {
        protected final Comparator<? super T> a;
        protected boolean b;

        d(Sink<? super T> sink, Comparator<? super T> comparator) {
            super(sink);
            this.a = comparator;
        }

        @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
        public final boolean cancellationRequested() {
            this.b = true;
            return false;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class p<T> extends d<T> {
        private T[] c;
        private int d;

        p(Sink<? super T> sink, Comparator<? super T> comparator) {
            super(sink, comparator);
        }

        @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.c = (T[]) new Object[(int) j];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
        public void end() {
            int i = 0;
            Arrays.sort(this.c, 0, this.d, this.a);
            this.downstream.begin(this.d);
            if (!this.b) {
                while (i < this.d) {
                    this.downstream.accept((Sink) this.c[i]);
                    i++;
                }
            } else {
                while (i < this.d && !this.downstream.cancellationRequested()) {
                    this.downstream.accept((Sink) this.c[i]);
                    i++;
                }
            }
            this.downstream.end();
            this.c = null;
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            T[] tArr = this.c;
            int i = this.d;
            this.d = i + 1;
            tArr[i] = t;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class l<T> extends d<T> {
        private ArrayList<T> c;

        l(Sink<? super T> sink, Comparator<? super T> comparator) {
            super(sink, comparator);
        }

        @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.c = j >= 0 ? new ArrayList<>((int) j) : new ArrayList<>();
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
        public void end() {
            Lists.sort(this.c, this.a);
            this.downstream.begin(this.c.size());
            if (!this.b) {
                ArrayList<T> arrayList = this.c;
                Sink sink = this.downstream;
                sink.getClass();
                Iterables.forEach(arrayList, gm.a(sink));
            } else {
                Iterator<T> it = this.c.iterator();
                while (it.hasNext()) {
                    T next = it.next();
                    if (this.downstream.cancellationRequested()) {
                        break;
                    }
                    this.downstream.accept((Sink) next);
                }
            }
            this.downstream.end();
            this.c = null;
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            this.c.add(t);
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static abstract class b extends Sink.ChainedInt<Integer> {
        protected boolean a;

        b(Sink<? super Integer> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
        public final boolean cancellationRequested() {
            this.a = true;
            return false;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class n extends b {
        private int[] b;
        private int c;

        n(Sink<? super Integer> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.b = new int[(int) j];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
        public void end() {
            int i = 0;
            Arrays.sort(this.b, 0, this.c);
            this.downstream.begin(this.c);
            if (!this.a) {
                while (i < this.c) {
                    this.downstream.accept(this.b[i]);
                    i++;
                }
            } else {
                while (i < this.c && !this.downstream.cancellationRequested()) {
                    this.downstream.accept(this.b[i]);
                    i++;
                }
            }
            this.downstream.end();
            this.b = null;
        }

        @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
        public void accept(int i) {
            int[] iArr = this.b;
            int i2 = this.c;
            this.c = i2 + 1;
            iArr[i2] = i;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class f extends b {
        private gn.c b;

        f(Sink<? super Integer> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.b = j > 0 ? new gn.c((int) j) : new gn.c();
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
        public void end() {
            int[] asPrimitiveArray = this.b.asPrimitiveArray();
            Arrays.sort(asPrimitiveArray);
            this.downstream.begin(asPrimitiveArray.length);
            int i = 0;
            if (!this.a) {
                int length = asPrimitiveArray.length;
                while (i < length) {
                    this.downstream.accept(asPrimitiveArray[i]);
                    i++;
                }
            } else {
                int length2 = asPrimitiveArray.length;
                while (i < length2) {
                    int i2 = asPrimitiveArray[i];
                    if (this.downstream.cancellationRequested()) {
                        break;
                    }
                    this.downstream.accept(i2);
                    i++;
                }
            }
            this.downstream.end();
        }

        @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
        public void accept(int i) {
            this.b.accept(i);
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static abstract class c extends Sink.ChainedLong<Long> {
        protected boolean a;

        c(Sink<? super Long> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
        public final boolean cancellationRequested() {
            this.a = true;
            return false;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class o extends c {
        private long[] b;
        private int c;

        o(Sink<? super Long> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.b = new long[(int) j];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
        public void end() {
            int i = 0;
            Arrays.sort(this.b, 0, this.c);
            this.downstream.begin(this.c);
            if (!this.a) {
                while (i < this.c) {
                    this.downstream.accept(this.b[i]);
                    i++;
                }
            } else {
                while (i < this.c && !this.downstream.cancellationRequested()) {
                    this.downstream.accept(this.b[i]);
                    i++;
                }
            }
            this.downstream.end();
            this.b = null;
        }

        @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
        public void accept(long j) {
            long[] jArr = this.b;
            int i = this.c;
            this.c = i + 1;
            jArr[i] = j;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class g extends c {
        private gn.d b;

        g(Sink<? super Long> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.b = j > 0 ? new gn.d((int) j) : new gn.d();
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
        public void end() {
            long[] asPrimitiveArray = this.b.asPrimitiveArray();
            Arrays.sort(asPrimitiveArray);
            this.downstream.begin(asPrimitiveArray.length);
            int i = 0;
            if (!this.a) {
                int length = asPrimitiveArray.length;
                while (i < length) {
                    this.downstream.accept(asPrimitiveArray[i]);
                    i++;
                }
            } else {
                int length2 = asPrimitiveArray.length;
                while (i < length2) {
                    long j = asPrimitiveArray[i];
                    if (this.downstream.cancellationRequested()) {
                        break;
                    }
                    this.downstream.accept(j);
                    i++;
                }
            }
            this.downstream.end();
        }

        @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
        public void accept(long j) {
            this.b.accept(j);
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static abstract class a extends Sink.ChainedDouble<Double> {
        protected boolean a;

        a(Sink<? super Double> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
        public final boolean cancellationRequested() {
            this.a = true;
            return false;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class m extends a {
        private double[] b;
        private int c;

        m(Sink<? super Double> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.b = new double[(int) j];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
        public void end() {
            int i = 0;
            Arrays.sort(this.b, 0, this.c);
            this.downstream.begin(this.c);
            if (!this.a) {
                while (i < this.c) {
                    this.downstream.accept(this.b[i]);
                    i++;
                }
            } else {
                while (i < this.c && !this.downstream.cancellationRequested()) {
                    this.downstream.accept(this.b[i]);
                    i++;
                }
            }
            this.downstream.end();
            this.b = null;
        }

        @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
        public void accept(double d) {
            double[] dArr = this.b;
            int i = this.c;
            this.c = i + 1;
            dArr[i] = d;
        }
    }

    /* compiled from: SortedOps.java */
    /* loaded from: classes5.dex */
    private static final class e extends a {
        private gn.b b;

        e(Sink<? super Double> sink) {
            super(sink);
        }

        @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
        public void begin(long j) {
            if (j < 2147483639) {
                this.b = j > 0 ? new gn.b((int) j) : new gn.b();
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
        public void end() {
            double[] asPrimitiveArray = this.b.asPrimitiveArray();
            Arrays.sort(asPrimitiveArray);
            this.downstream.begin(asPrimitiveArray.length);
            int i = 0;
            if (!this.a) {
                int length = asPrimitiveArray.length;
                while (i < length) {
                    this.downstream.accept(asPrimitiveArray[i]);
                    i++;
                }
            } else {
                int length2 = asPrimitiveArray.length;
                while (i < length2) {
                    double d = asPrimitiveArray[i];
                    if (this.downstream.cancellationRequested()) {
                        break;
                    }
                    this.downstream.accept(d);
                    i++;
                }
            }
            this.downstream.end();
        }

        @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
        public void accept(double d) {
            this.b.accept(d);
        }
    }
}
