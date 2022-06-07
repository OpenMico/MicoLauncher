package java8.util.stream;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.concurrent.CountedCompleter;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.DoublePredicate;
import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;
import java8.util.function.IntPredicate;
import java8.util.function.LongConsumer;
import java8.util.function.LongPredicate;
import java8.util.function.Predicate;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.df;
import java8.util.stream.eg;
import java8.util.stream.eu;
import java8.util.stream.gd;

/* compiled from: WhileOps.java */
/* loaded from: classes5.dex */
final class hi {
    static final int a = gp.s | gp.t;
    static final int b = gp.s;
    static final IntFunction<Integer[]> c = hj.a();
    static final IntFunction<Long[]> d = hk.a();
    static final IntFunction<Double[]> e = hl.a();

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public interface e<T> {
        f<T> a(Sink<T> sink, boolean z);
    }

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public interface f<T> extends Sink<T> {
        long a();
    }

    public static /* synthetic */ Integer[] c(int i2) {
        return new Integer[i2];
    }

    public static /* synthetic */ Long[] b(int i2) {
        return new Long[i2];
    }

    public static /* synthetic */ Double[] a(int i2) {
        return new Double[i2];
    }

    public static <T> Stream<T> a(a<?, T, ?> aVar, final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return new gd.b<T, T>(aVar, gq.REFERENCE, a) { // from class: java8.util.stream.hi.1
            @Override // java8.util.stream.a
            <P_IN> Spliterator<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.ORDERED.a(gbVar.c())) {
                    return a(gbVar, spliterator, fn.d()).f_();
                }
                return new i.d.b(gbVar.b(spliterator), false, predicate);
            }

            @Override // java8.util.stream.gd.b, java8.util.stream.a
            <P_IN> Node<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator, IntFunction<T[]> intFunction) {
                return (Node) new h(this, gbVar, spliterator, intFunction).invoke();
            }

            @Override // java8.util.stream.a
            public Sink<T> a(int i2, Sink<T> sink) {
                return new Sink.ChainedReference<T, T>(sink) { // from class: java8.util.stream.hi.1.1
                    boolean a = true;

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.function.Consumer
                    public void accept(T t) {
                        if (this.a) {
                            boolean test = predicate.test(t);
                            this.a = test;
                            if (test) {
                                this.downstream.accept((Sink) t);
                            }
                        }
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        return !this.a || this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    public static IntStream a(a<?, Integer, ?> aVar, final IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new eg.b<Integer>(aVar, gq.INT_VALUE, a) { // from class: java8.util.stream.hi.2
            @Override // java8.util.stream.a
            <P_IN> Spliterator<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.ORDERED.a(gbVar.c())) {
                    return a(gbVar, spliterator, hi.c).f_();
                }
                return new i.b.C0366b((Spliterator.OfInt) gbVar.b(spliterator), false, intPredicate);
            }

            @Override // java8.util.stream.eg.b, java8.util.stream.a
            <P_IN> Node<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator, IntFunction<Integer[]> intFunction) {
                return (Node) new h(this, gbVar, spliterator, intFunction).invoke();
            }

            @Override // java8.util.stream.a
            public Sink<Integer> a(int i2, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink) { // from class: java8.util.stream.hi.2.1
                    boolean a = true;

                    @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i3) {
                        if (this.a) {
                            boolean test = intPredicate.test(i3);
                            this.a = test;
                            if (test) {
                                this.downstream.accept(i3);
                            }
                        }
                    }

                    @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        return !this.a || this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    public static LongStream a(a<?, Long, ?> aVar, final LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new eu.b<Long>(aVar, gq.LONG_VALUE, a) { // from class: java8.util.stream.hi.3
            @Override // java8.util.stream.a
            <P_IN> Spliterator<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.ORDERED.a(gbVar.c())) {
                    return a(gbVar, spliterator, hi.d).f_();
                }
                return new i.c.b((Spliterator.OfLong) gbVar.b(spliterator), false, longPredicate);
            }

            @Override // java8.util.stream.eu.b, java8.util.stream.a
            <P_IN> Node<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator, IntFunction<Long[]> intFunction) {
                return (Node) new h(this, gbVar, spliterator, intFunction).invoke();
            }

            @Override // java8.util.stream.a
            public Sink<Long> a(int i2, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink) { // from class: java8.util.stream.hi.3.1
                    boolean a = true;

                    @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        if (this.a) {
                            boolean test = longPredicate.test(j);
                            this.a = test;
                            if (test) {
                                this.downstream.accept(j);
                            }
                        }
                    }

                    @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        return !this.a || this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    public static DoubleStream a(a<?, Double, ?> aVar, final DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new df.b<Double>(aVar, gq.DOUBLE_VALUE, a) { // from class: java8.util.stream.hi.4
            @Override // java8.util.stream.a
            <P_IN> Spliterator<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.ORDERED.a(gbVar.c())) {
                    return a(gbVar, spliterator, hi.e).f_();
                }
                return new i.a.b((Spliterator.OfDouble) gbVar.b(spliterator), false, doublePredicate);
            }

            @Override // java8.util.stream.df.b, java8.util.stream.a
            <P_IN> Node<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator, IntFunction<Double[]> intFunction) {
                return (Node) new h(this, gbVar, spliterator, intFunction).invoke();
            }

            @Override // java8.util.stream.a
            public Sink<Double> a(int i2, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink) { // from class: java8.util.stream.hi.4.1
                    boolean a = true;

                    @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    public void accept(double d2) {
                        if (this.a) {
                            boolean test = doublePredicate.test(d2);
                            this.a = test;
                            if (test) {
                                this.downstream.accept(d2);
                            }
                        }
                    }

                    @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        return !this.a || this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    public static <T> Stream<T> b(a<?, T, ?> aVar, Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return new a(aVar, gq.REFERENCE, b, predicate);
    }

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public class a extends gd.b<T, T> implements e<T> {
        final /* synthetic */ Predicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public a(a aVar, a<?, T, ?> aVar2, gq gqVar, int i) {
            super(aVar, aVar2, gqVar);
            this.b = i;
        }

        @Override // java8.util.stream.a
        <P_IN> Spliterator<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator) {
            if (gp.ORDERED.a(gbVar.c())) {
                return a(gbVar, spliterator, fn.d()).f_();
            }
            return new i.d.a(gbVar.b(spliterator), false, this.b);
        }

        @Override // java8.util.stream.gd.b, java8.util.stream.a
        <P_IN> Node<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator, IntFunction<T[]> intFunction) {
            return (Node) new g(this, gbVar, spliterator, intFunction).invoke();
        }

        @Override // java8.util.stream.a
        public Sink<T> a(int i, Sink<T> sink) {
            return a((Sink) sink, false);
        }

        /* compiled from: WhileOps.java */
        /* renamed from: java8.util.stream.hi$a$a */
        /* loaded from: classes5.dex */
        public class C0364a extends Sink.ChainedReference<T, T> implements f<T> {
            long a;
            boolean b;
            final /* synthetic */ Sink c;
            final /* synthetic */ boolean d;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C0364a(Sink sink, boolean z) {
                super(sink);
                a.this = r1;
                this.c = sink;
                this.d = z;
            }

            @Override // java8.util.function.Consumer
            public void accept(T t) {
                boolean z = true;
                if (!this.b) {
                    boolean z2 = !a.this.b.test(t);
                    this.b = z2;
                    if (!z2) {
                        z = false;
                    }
                }
                if (this.d && !z) {
                    this.a++;
                }
                if (this.d || z) {
                    this.downstream.accept((Sink) t);
                }
            }

            @Override // java8.util.stream.hi.f
            public long a() {
                return this.a;
            }
        }

        @Override // java8.util.stream.hi.e
        public f<T> a(Sink<T> sink, boolean z) {
            return new C0364a(sink, z);
        }
    }

    public static IntStream b(a<?, Integer, ?> aVar, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new b(aVar, gq.INT_VALUE, b, intPredicate);
    }

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public class b extends eg.b<Integer> implements e<Integer> {
        final /* synthetic */ IntPredicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public b(a aVar, a<?, Integer, ?> aVar2, gq gqVar, int i) {
            super(aVar, aVar2, gqVar);
            this.b = i;
        }

        @Override // java8.util.stream.a
        <P_IN> Spliterator<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator) {
            if (gp.ORDERED.a(gbVar.c())) {
                return a(gbVar, spliterator, hi.c).f_();
            }
            return new i.b.a((Spliterator.OfInt) gbVar.b(spliterator), false, this.b);
        }

        @Override // java8.util.stream.eg.b, java8.util.stream.a
        <P_IN> Node<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator, IntFunction<Integer[]> intFunction) {
            return (Node) new g(this, gbVar, spliterator, intFunction).invoke();
        }

        @Override // java8.util.stream.a
        public Sink<Integer> a(int i, Sink<Integer> sink) {
            return a(sink, false);
        }

        /* compiled from: WhileOps.java */
        /* loaded from: classes5.dex */
        public class a extends Sink.ChainedInt<Integer> implements f<Integer> {
            long a;
            boolean b;
            final /* synthetic */ Sink c;
            final /* synthetic */ boolean d;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(Sink sink, boolean z) {
                super(sink);
                b.this = r1;
                this.c = sink;
                this.d = z;
            }

            @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
            public void accept(int i) {
                boolean z = true;
                if (!this.b) {
                    boolean z2 = !b.this.b.test(i);
                    this.b = z2;
                    if (!z2) {
                        z = false;
                    }
                }
                if (this.d && !z) {
                    this.a++;
                }
                if (this.d || z) {
                    this.downstream.accept(i);
                }
            }

            @Override // java8.util.stream.hi.f
            public long a() {
                return this.a;
            }
        }

        @Override // java8.util.stream.hi.e
        public f<Integer> a(Sink<Integer> sink, boolean z) {
            return new a(sink, z);
        }
    }

    public static LongStream b(a<?, Long, ?> aVar, LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new c(aVar, gq.LONG_VALUE, b, longPredicate);
    }

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public class c extends eu.b<Long> implements e<Long> {
        final /* synthetic */ LongPredicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public c(a aVar, a<?, Long, ?> aVar2, gq gqVar, int i) {
            super(aVar, aVar2, gqVar);
            this.b = i;
        }

        @Override // java8.util.stream.a
        <P_IN> Spliterator<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator) {
            if (gp.ORDERED.a(gbVar.c())) {
                return a(gbVar, spliterator, hi.d).f_();
            }
            return new i.c.a((Spliterator.OfLong) gbVar.b(spliterator), false, this.b);
        }

        @Override // java8.util.stream.eu.b, java8.util.stream.a
        <P_IN> Node<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator, IntFunction<Long[]> intFunction) {
            return (Node) new g(this, gbVar, spliterator, intFunction).invoke();
        }

        @Override // java8.util.stream.a
        public Sink<Long> a(int i, Sink<Long> sink) {
            return a(sink, false);
        }

        /* compiled from: WhileOps.java */
        /* loaded from: classes5.dex */
        public class a extends Sink.ChainedLong<Long> implements f<Long> {
            long a;
            boolean b;
            final /* synthetic */ Sink c;
            final /* synthetic */ boolean d;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(Sink sink, boolean z) {
                super(sink);
                c.this = r1;
                this.c = sink;
                this.d = z;
            }

            @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
            public void accept(long j) {
                boolean z = true;
                if (!this.b) {
                    boolean z2 = !c.this.b.test(j);
                    this.b = z2;
                    if (!z2) {
                        z = false;
                    }
                }
                if (this.d && !z) {
                    this.a++;
                }
                if (this.d || z) {
                    this.downstream.accept(j);
                }
            }

            @Override // java8.util.stream.hi.f
            public long a() {
                return this.a;
            }
        }

        @Override // java8.util.stream.hi.e
        public f<Long> a(Sink<Long> sink, boolean z) {
            return new a(sink, z);
        }
    }

    public static DoubleStream b(a<?, Double, ?> aVar, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new d(aVar, gq.DOUBLE_VALUE, b, doublePredicate);
    }

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public class d extends df.b<Double> implements e<Double> {
        final /* synthetic */ DoublePredicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public d(a aVar, a<?, Double, ?> aVar2, gq gqVar, int i) {
            super(aVar, aVar2, gqVar);
            this.b = i;
        }

        @Override // java8.util.stream.a
        <P_IN> Spliterator<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator) {
            if (gp.ORDERED.a(gbVar.c())) {
                return a(gbVar, spliterator, hi.e).f_();
            }
            return new i.a.C0365a((Spliterator.OfDouble) gbVar.b(spliterator), false, this.b);
        }

        @Override // java8.util.stream.df.b, java8.util.stream.a
        <P_IN> Node<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator, IntFunction<Double[]> intFunction) {
            return (Node) new g(this, gbVar, spliterator, intFunction).invoke();
        }

        @Override // java8.util.stream.a
        public Sink<Double> a(int i, Sink<Double> sink) {
            return a(sink, false);
        }

        /* compiled from: WhileOps.java */
        /* loaded from: classes5.dex */
        public class a extends Sink.ChainedDouble<Double> implements f<Double> {
            long a;
            boolean b;
            final /* synthetic */ Sink c;
            final /* synthetic */ boolean d;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(Sink sink, boolean z) {
                super(sink);
                d.this = r1;
                this.c = sink;
                this.d = z;
            }

            @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
            public void accept(double d) {
                boolean z = true;
                if (!this.b) {
                    boolean z2 = !d.this.b.test(d);
                    this.b = z2;
                    if (!z2) {
                        z = false;
                    }
                }
                if (this.d && !z) {
                    this.a++;
                }
                if (this.d || z) {
                    this.downstream.accept(d);
                }
            }

            @Override // java8.util.stream.hi.f
            public long a() {
                return this.a;
            }
        }

        @Override // java8.util.stream.hi.e
        public f<Double> a(Sink<Double> sink, boolean z) {
            return new a(sink, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public static abstract class i<T, T_SPLITR extends Spliterator<T>> implements Spliterator<T> {
        final T_SPLITR a;
        final boolean b;
        final AtomicBoolean c;
        boolean d;
        int e;

        abstract T_SPLITR a(T_SPLITR t_splitr);

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            return -1L;
        }

        i(T_SPLITR t_splitr, boolean z) {
            this.d = true;
            this.a = t_splitr;
            this.b = z;
            this.c = new AtomicBoolean();
        }

        i(T_SPLITR t_splitr, i<T, T_SPLITR> iVar) {
            this.d = true;
            this.a = t_splitr;
            this.b = iVar.b;
            this.c = iVar.c;
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.a.estimateSize();
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return this.a.characteristics() & (-16449);
        }

        @Override // java8.util.Spliterator
        public Comparator<? super T> getComparator() {
            return this.a.getComparator();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.Spliterator
        public T_SPLITR trySplit() {
            Spliterator<T> trySplit = this.b ? null : this.a.trySplit();
            if (trySplit != null) {
                return (T_SPLITR) a(trySplit);
            }
            return null;
        }

        boolean a() {
            return this.e != 0 || !this.c.get();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: WhileOps.java */
        /* loaded from: classes5.dex */
        public static abstract class d<T> extends i<T, Spliterator<T>> implements Consumer<T> {
            final Predicate<? super T> f;
            T g;

            d(Spliterator<T> spliterator, boolean z, Predicate<? super T> predicate) {
                super(spliterator, z);
                this.f = predicate;
            }

            d(Spliterator<T> spliterator, d<T> dVar) {
                super(spliterator, dVar);
                this.f = dVar.f;
            }

            @Override // java8.util.function.Consumer
            public void accept(T t) {
                this.e = (this.e + 1) & 63;
                this.g = t;
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super T> consumer) {
                Spliterators.forEachRemaining(this, consumer);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            /* compiled from: WhileOps.java */
            /* loaded from: classes5.dex */
            static final class b<T> extends d<T> {
                public b(Spliterator<T> spliterator, boolean z, Predicate<? super T> predicate) {
                    super(spliterator, z, predicate);
                }

                b(Spliterator<T> spliterator, b<T> bVar) {
                    super(spliterator, bVar);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // java8.util.Spliterator
                public boolean tryAdvance(Consumer<? super T> consumer) {
                    boolean z;
                    if (!this.d || !a() || !this.a.tryAdvance(this)) {
                        z = true;
                    } else {
                        z = this.f.test(this.g);
                        if (z) {
                            consumer.accept((Object) this.g);
                            return true;
                        }
                    }
                    this.d = false;
                    if (!z) {
                        this.c.set(true);
                    }
                    return false;
                }

                @Override // java8.util.stream.hi.i, java8.util.Spliterator
                public Spliterator<T> trySplit() {
                    if (this.c.get()) {
                        return null;
                    }
                    return super.trySplit();
                }

                @Override // java8.util.stream.hi.i
                Spliterator<T> a(Spliterator<T> spliterator) {
                    return new b(spliterator, this);
                }
            }

            /* compiled from: WhileOps.java */
            /* loaded from: classes5.dex */
            static final class a<T> extends d<T> {
                public a(Spliterator<T> spliterator, boolean z, Predicate<? super T> predicate) {
                    super(spliterator, z, predicate);
                }

                a(Spliterator<T> spliterator, a<T> aVar) {
                    super(spliterator, aVar);
                }

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0024, code lost:
                    if (r0 == false) goto L_0x002b;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:14:0x0026, code lost:
                    r5.c.set(true);
                 */
                /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
                    r6.accept((java.lang.Object) r5.g);
                 */
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java8.util.Spliterator
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public boolean tryAdvance(java8.util.function.Consumer<? super T> r6) {
                    /*
                        r5 = this;
                        boolean r0 = r5.d
                        if (r0 == 0) goto L_0x0031
                        r0 = 0
                        r5.d = r0
                        r1 = 1
                    L_0x0008:
                        java8.util.Spliterator r2 = r5.a
                        boolean r2 = r2.tryAdvance(r5)
                        if (r2 == 0) goto L_0x0022
                        boolean r3 = r5.a()
                        if (r3 == 0) goto L_0x0022
                        java8.util.function.Predicate r3 = r5.f
                        java.lang.Object r4 = r5.g
                        boolean r3 = r3.test(r4)
                        if (r3 == 0) goto L_0x0022
                        r0 = r1
                        goto L_0x0008
                    L_0x0022:
                        if (r2 == 0) goto L_0x0030
                        if (r0 == 0) goto L_0x002b
                        java.util.concurrent.atomic.AtomicBoolean r0 = r5.c
                        r0.set(r1)
                    L_0x002b:
                        java.lang.Object r0 = r5.g
                        r6.accept(r0)
                    L_0x0030:
                        return r2
                    L_0x0031:
                        java8.util.Spliterator r0 = r5.a
                        boolean r6 = r0.tryAdvance(r6)
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.hi.i.d.a.tryAdvance(java8.util.function.Consumer):boolean");
                }

                @Override // java8.util.stream.hi.i
                Spliterator<T> a(Spliterator<T> spliterator) {
                    return new a(spliterator, this);
                }
            }
        }

        /* compiled from: WhileOps.java */
        /* loaded from: classes5.dex */
        public static abstract class b extends i<Integer, Spliterator.OfInt> implements Spliterator.OfInt, IntConsumer {
            final IntPredicate f;
            int g;

            b(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                super(ofInt, z);
                this.f = intPredicate;
            }

            b(Spliterator.OfInt ofInt, b bVar) {
                super(ofInt, bVar);
                this.f = bVar.f;
            }

            @Override // java8.util.function.IntConsumer
            public void accept(int i) {
                this.e = (this.e + 1) & 63;
                this.g = i;
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator.OfInt
            public void forEachRemaining(IntConsumer intConsumer) {
                Spliterators.OfInt.forEachRemaining(this, intConsumer);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Integer> consumer) {
                return Spliterators.OfInt.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Integer> consumer) {
                Spliterators.forEachRemaining(this, consumer);
            }

            /* compiled from: WhileOps.java */
            /* renamed from: java8.util.stream.hi$i$b$b */
            /* loaded from: classes5.dex */
            static final class C0366b extends b {
                @Override // java8.util.stream.hi.i.b, java8.util.Spliterator.OfPrimitive
                public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                    super.forEachRemaining(intConsumer);
                }

                public C0366b(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                    super(ofInt, z, intPredicate);
                }

                C0366b(Spliterator.OfInt ofInt, b bVar) {
                    super(ofInt, bVar);
                }

                @Override // java8.util.Spliterator.OfInt
                public boolean tryAdvance(IntConsumer intConsumer) {
                    boolean z;
                    if (!this.d || !a() || !((Spliterator.OfInt) this.a).tryAdvance((IntConsumer) this)) {
                        z = true;
                    } else {
                        z = this.f.test(this.g);
                        if (z) {
                            intConsumer.accept(this.g);
                            return true;
                        }
                    }
                    this.d = false;
                    if (!z) {
                        this.c.set(true);
                    }
                    return false;
                }

                @Override // java8.util.stream.hi.i.b, java8.util.stream.hi.i, java8.util.Spliterator
                public Spliterator.OfInt trySplit() {
                    if (this.c.get()) {
                        return null;
                    }
                    return (Spliterator.OfInt) super.trySplit();
                }

                public Spliterator.OfInt a(Spliterator.OfInt ofInt) {
                    return new C0366b(ofInt, this);
                }
            }

            /* compiled from: WhileOps.java */
            /* loaded from: classes5.dex */
            static final class a extends b {
                @Override // java8.util.stream.hi.i.b, java8.util.Spliterator.OfPrimitive
                public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                    super.forEachRemaining(intConsumer);
                }

                public a(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                    super(ofInt, z, intPredicate);
                }

                a(Spliterator.OfInt ofInt, b bVar) {
                    super(ofInt, bVar);
                }

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
                    if (r0 == false) goto L_0x002d;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
                    r5.c.set(true);
                 */
                /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:
                    r6.accept(r5.g);
                 */
                @Override // java8.util.Spliterator.OfInt
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public boolean tryAdvance(java8.util.function.IntConsumer r6) {
                    /*
                        r5 = this;
                        boolean r0 = r5.d
                        if (r0 == 0) goto L_0x0033
                        r0 = 0
                        r5.d = r0
                        r1 = 1
                    L_0x0008:
                        java8.util.Spliterator r2 = r5.a
                        java8.util.Spliterator$OfInt r2 = (java8.util.Spliterator.OfInt) r2
                        boolean r2 = r2.tryAdvance(r5)
                        if (r2 == 0) goto L_0x0024
                        boolean r3 = r5.a()
                        if (r3 == 0) goto L_0x0024
                        java8.util.function.IntPredicate r3 = r5.f
                        int r4 = r5.g
                        boolean r3 = r3.test(r4)
                        if (r3 == 0) goto L_0x0024
                        r0 = r1
                        goto L_0x0008
                    L_0x0024:
                        if (r2 == 0) goto L_0x0032
                        if (r0 == 0) goto L_0x002d
                        java.util.concurrent.atomic.AtomicBoolean r0 = r5.c
                        r0.set(r1)
                    L_0x002d:
                        int r0 = r5.g
                        r6.accept(r0)
                    L_0x0032:
                        return r2
                    L_0x0033:
                        java8.util.Spliterator r0 = r5.a
                        java8.util.Spliterator$OfInt r0 = (java8.util.Spliterator.OfInt) r0
                        boolean r6 = r0.tryAdvance(r6)
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.hi.i.b.a.tryAdvance(java8.util.function.IntConsumer):boolean");
                }

                public Spliterator.OfInt a(Spliterator.OfInt ofInt) {
                    return new a(ofInt, this);
                }
            }
        }

        /* compiled from: WhileOps.java */
        /* loaded from: classes5.dex */
        public static abstract class c extends i<Long, Spliterator.OfLong> implements Spliterator.OfLong, LongConsumer {
            final LongPredicate f;
            long g;

            c(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                super(ofLong, z);
                this.f = longPredicate;
            }

            c(Spliterator.OfLong ofLong, c cVar) {
                super(ofLong, cVar);
                this.f = cVar.f;
            }

            @Override // java8.util.function.LongConsumer
            public void accept(long j) {
                this.e = (this.e + 1) & 63;
                this.g = j;
            }

            public Spliterator.OfLong a(Spliterator.OfLong ofLong) {
                return new a(ofLong, this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator.OfLong
            public void forEachRemaining(LongConsumer longConsumer) {
                Spliterators.OfLong.forEachRemaining(this, longConsumer);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Long> consumer) {
                return Spliterators.OfLong.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Long> consumer) {
                Spliterators.OfLong.forEachRemaining(this, consumer);
            }

            /* compiled from: WhileOps.java */
            /* loaded from: classes5.dex */
            static final class b extends c {
                @Override // java8.util.stream.hi.i.c, java8.util.Spliterator.OfPrimitive
                public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                    super.forEachRemaining(longConsumer);
                }

                public b(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                    super(ofLong, z, longPredicate);
                }

                b(Spliterator.OfLong ofLong, c cVar) {
                    super(ofLong, cVar);
                }

                @Override // java8.util.Spliterator.OfLong
                public boolean tryAdvance(LongConsumer longConsumer) {
                    boolean z;
                    if (!this.d || !a() || !((Spliterator.OfLong) this.a).tryAdvance((LongConsumer) this)) {
                        z = true;
                    } else {
                        z = this.f.test(this.g);
                        if (z) {
                            longConsumer.accept(this.g);
                            return true;
                        }
                    }
                    this.d = false;
                    if (!z) {
                        this.c.set(true);
                    }
                    return false;
                }

                @Override // java8.util.stream.hi.i.c, java8.util.stream.hi.i, java8.util.Spliterator
                public Spliterator.OfLong trySplit() {
                    if (this.c.get()) {
                        return null;
                    }
                    return (Spliterator.OfLong) super.trySplit();
                }

                @Override // java8.util.stream.hi.i.c
                public Spliterator.OfLong a(Spliterator.OfLong ofLong) {
                    return new b(ofLong, this);
                }
            }

            /* compiled from: WhileOps.java */
            /* loaded from: classes5.dex */
            public static final class a extends c {
                @Override // java8.util.stream.hi.i.c, java8.util.stream.hi.i
                /* bridge */ /* synthetic */ Spliterator.OfLong a(Spliterator.OfLong ofLong) {
                    return super.a(ofLong);
                }

                @Override // java8.util.stream.hi.i.c, java8.util.Spliterator.OfPrimitive
                public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                    super.forEachRemaining(longConsumer);
                }

                public a(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                    super(ofLong, z, longPredicate);
                }

                a(Spliterator.OfLong ofLong, c cVar) {
                    super(ofLong, cVar);
                }

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
                    if (r0 == false) goto L_0x002d;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
                    r6.c.set(true);
                 */
                /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:
                    r7.accept(r6.g);
                 */
                @Override // java8.util.Spliterator.OfLong
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public boolean tryAdvance(java8.util.function.LongConsumer r7) {
                    /*
                        r6 = this;
                        boolean r0 = r6.d
                        if (r0 == 0) goto L_0x0033
                        r0 = 0
                        r6.d = r0
                        r1 = 1
                    L_0x0008:
                        java8.util.Spliterator r2 = r6.a
                        java8.util.Spliterator$OfLong r2 = (java8.util.Spliterator.OfLong) r2
                        boolean r2 = r2.tryAdvance(r6)
                        if (r2 == 0) goto L_0x0024
                        boolean r3 = r6.a()
                        if (r3 == 0) goto L_0x0024
                        java8.util.function.LongPredicate r3 = r6.f
                        long r4 = r6.g
                        boolean r3 = r3.test(r4)
                        if (r3 == 0) goto L_0x0024
                        r0 = r1
                        goto L_0x0008
                    L_0x0024:
                        if (r2 == 0) goto L_0x0032
                        if (r0 == 0) goto L_0x002d
                        java.util.concurrent.atomic.AtomicBoolean r0 = r6.c
                        r0.set(r1)
                    L_0x002d:
                        long r0 = r6.g
                        r7.accept(r0)
                    L_0x0032:
                        return r2
                    L_0x0033:
                        java8.util.Spliterator r0 = r6.a
                        java8.util.Spliterator$OfLong r0 = (java8.util.Spliterator.OfLong) r0
                        boolean r7 = r0.tryAdvance(r7)
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.hi.i.c.a.tryAdvance(java8.util.function.LongConsumer):boolean");
                }
            }
        }

        /* compiled from: WhileOps.java */
        /* loaded from: classes5.dex */
        public static abstract class a extends i<Double, Spliterator.OfDouble> implements Spliterator.OfDouble, DoubleConsumer {
            final DoublePredicate f;
            double g;

            a(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                super(ofDouble, z);
                this.f = doublePredicate;
            }

            a(Spliterator.OfDouble ofDouble, a aVar) {
                super(ofDouble, aVar);
                this.f = aVar.f;
            }

            @Override // java8.util.function.DoubleConsumer
            public void accept(double d) {
                this.e = (this.e + 1) & 63;
                this.g = d;
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator.OfDouble
            public void forEachRemaining(DoubleConsumer doubleConsumer) {
                Spliterators.OfDouble.forEachRemaining(this, doubleConsumer);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Double> consumer) {
                return Spliterators.OfDouble.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Double> consumer) {
                Spliterators.OfDouble.forEachRemaining(this, consumer);
            }

            /* compiled from: WhileOps.java */
            /* loaded from: classes5.dex */
            static final class b extends a {
                @Override // java8.util.stream.hi.i.a, java8.util.Spliterator.OfPrimitive
                public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                    super.forEachRemaining(doubleConsumer);
                }

                public b(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                    super(ofDouble, z, doublePredicate);
                }

                b(Spliterator.OfDouble ofDouble, a aVar) {
                    super(ofDouble, aVar);
                }

                @Override // java8.util.Spliterator.OfDouble
                public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                    boolean z;
                    if (!this.d || !a() || !((Spliterator.OfDouble) this.a).tryAdvance((DoubleConsumer) this)) {
                        z = true;
                    } else {
                        z = this.f.test(this.g);
                        if (z) {
                            doubleConsumer.accept(this.g);
                            return true;
                        }
                    }
                    this.d = false;
                    if (!z) {
                        this.c.set(true);
                    }
                    return false;
                }

                @Override // java8.util.stream.hi.i.a, java8.util.stream.hi.i, java8.util.Spliterator
                public Spliterator.OfDouble trySplit() {
                    if (this.c.get()) {
                        return null;
                    }
                    return (Spliterator.OfDouble) super.trySplit();
                }

                public Spliterator.OfDouble a(Spliterator.OfDouble ofDouble) {
                    return new b(ofDouble, this);
                }
            }

            /* compiled from: WhileOps.java */
            /* renamed from: java8.util.stream.hi$i$a$a */
            /* loaded from: classes5.dex */
            static final class C0365a extends a {
                @Override // java8.util.stream.hi.i.a, java8.util.Spliterator.OfPrimitive
                public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                    super.forEachRemaining(doubleConsumer);
                }

                public C0365a(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                    super(ofDouble, z, doublePredicate);
                }

                C0365a(Spliterator.OfDouble ofDouble, a aVar) {
                    super(ofDouble, aVar);
                }

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
                    if (r0 == false) goto L_0x002d;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
                    r6.c.set(true);
                 */
                /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:
                    r7.accept(r6.g);
                 */
                @Override // java8.util.Spliterator.OfDouble
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public boolean tryAdvance(java8.util.function.DoubleConsumer r7) {
                    /*
                        r6 = this;
                        boolean r0 = r6.d
                        if (r0 == 0) goto L_0x0033
                        r0 = 0
                        r6.d = r0
                        r1 = 1
                    L_0x0008:
                        java8.util.Spliterator r2 = r6.a
                        java8.util.Spliterator$OfDouble r2 = (java8.util.Spliterator.OfDouble) r2
                        boolean r2 = r2.tryAdvance(r6)
                        if (r2 == 0) goto L_0x0024
                        boolean r3 = r6.a()
                        if (r3 == 0) goto L_0x0024
                        java8.util.function.DoublePredicate r3 = r6.f
                        double r4 = r6.g
                        boolean r3 = r3.test(r4)
                        if (r3 == 0) goto L_0x0024
                        r0 = r1
                        goto L_0x0008
                    L_0x0024:
                        if (r2 == 0) goto L_0x0032
                        if (r0 == 0) goto L_0x002d
                        java.util.concurrent.atomic.AtomicBoolean r0 = r6.c
                        r0.set(r1)
                    L_0x002d:
                        double r0 = r6.g
                        r7.accept(r0)
                    L_0x0032:
                        return r2
                    L_0x0033:
                        java8.util.Spliterator r0 = r6.a
                        java8.util.Spliterator$OfDouble r0 = (java8.util.Spliterator.OfDouble) r0
                        boolean r7 = r0.tryAdvance(r7)
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.hi.i.a.C0365a.tryAdvance(java8.util.function.DoubleConsumer):boolean");
                }

                public Spliterator.OfDouble a(Spliterator.OfDouble ofDouble) {
                    return new C0365a(ofDouble, this);
                }
            }
        }
    }

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public static final class h<P_IN, P_OUT> extends e<P_IN, P_OUT, Node<P_OUT>, h<P_IN, P_OUT>> {
        private volatile boolean completed;
        private final IntFunction<P_OUT[]> generator;
        private final boolean isOrdered;
        private final a<P_OUT, P_OUT, ?> op;
        private boolean shortCircuited;
        private long thisNodeSize;

        h(a<P_OUT, P_OUT, ?> aVar, gb<P_OUT> gbVar, Spliterator<P_IN> spliterator, IntFunction<P_OUT[]> intFunction) {
            super(gbVar, spliterator);
            this.op = aVar;
            this.generator = intFunction;
            this.isOrdered = gp.ORDERED.a(gbVar.c());
        }

        h(h<P_IN, P_OUT> hVar, Spliterator<P_IN> spliterator) {
            super(hVar, spliterator);
            this.op = hVar.op;
            this.generator = hVar.generator;
            this.isOrdered = hVar.isOrdered;
        }

        /* renamed from: b */
        public h<P_IN, P_OUT> a(Spliterator<P_IN> spliterator) {
            return new h<>(this, spliterator);
        }

        /* renamed from: n */
        public final Node<P_OUT> a() {
            return fn.a(this.op.e());
        }

        /* renamed from: o */
        public final Node<P_OUT> i() {
            Node.Builder<P_OUT> a = this.helper.a(-1L, this.generator);
            boolean c = this.helper.c(this.helper.a((Sink) this.op.a(this.helper.c(), a)), this.spliterator);
            this.shortCircuited = c;
            if (c) {
                g();
            }
            Node<P_OUT> build = a.build();
            this.thisNodeSize = build.a();
            return build;
        }

        @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter<?> countedCompleter) {
            Node<P_OUT> node;
            if (!j()) {
                this.shortCircuited = ((h) this.leftChild).shortCircuited | ((h) this.rightChild).shortCircuited;
                if (this.isOrdered && this.canceled) {
                    this.thisNodeSize = 0L;
                    node = a();
                } else if (!this.isOrdered || !((h) this.leftChild).shortCircuited) {
                    this.thisNodeSize = ((h) this.leftChild).thisNodeSize + ((h) this.rightChild).thisNodeSize;
                    node = p();
                } else {
                    this.thisNodeSize = ((h) this.leftChild).thisNodeSize;
                    node = ((h) this.leftChild).b();
                }
                b((h<P_IN, P_OUT>) node);
            }
            this.completed = true;
            super.onCompletion(countedCompleter);
        }

        Node<P_OUT> p() {
            if (((h) this.leftChild).thisNodeSize == 0) {
                return ((h) this.rightChild).b();
            }
            if (((h) this.rightChild).thisNodeSize == 0) {
                return ((h) this.leftChild).b();
            }
            return fn.a(this.op.e(), ((h) this.leftChild).b(), ((h) this.rightChild).b());
        }

        @Override // java8.util.stream.e
        public void c() {
            super.c();
            if (this.isOrdered && this.completed) {
                b((h<P_IN, P_OUT>) a());
            }
        }
    }

    /* compiled from: WhileOps.java */
    /* loaded from: classes5.dex */
    public static final class g<P_IN, P_OUT> extends g<P_IN, P_OUT, Node<P_OUT>, g<P_IN, P_OUT>> {
        private final IntFunction<P_OUT[]> generator;
        private long index;
        private final boolean isOrdered;
        private final a<P_OUT, P_OUT, ?> op;
        private long thisNodeSize;

        g(a<P_OUT, P_OUT, ?> aVar, gb<P_OUT> gbVar, Spliterator<P_IN> spliterator, IntFunction<P_OUT[]> intFunction) {
            super(gbVar, spliterator);
            this.op = aVar;
            this.generator = intFunction;
            this.isOrdered = gp.ORDERED.a(gbVar.c());
        }

        g(g<P_IN, P_OUT> gVar, Spliterator<P_IN> spliterator) {
            super(gVar, spliterator);
            this.op = gVar.op;
            this.generator = gVar.generator;
            this.isOrdered = gVar.isOrdered;
        }

        /* renamed from: b */
        public g<P_IN, P_OUT> a(Spliterator<P_IN> spliterator) {
            return new g<>(this, spliterator);
        }

        /* renamed from: a */
        public final Node<P_OUT> i() {
            boolean z = true;
            boolean z2 = !k();
            Node.Builder<P_OUT> a = this.helper.a((!z2 || !this.isOrdered || !gp.SIZED.b(this.op.a)) ? -1L : this.op.a(this.spliterator), this.generator);
            e eVar = (e) this.op;
            if (!this.isOrdered || !z2) {
                z = false;
            }
            f a2 = eVar.a(a, z);
            this.helper.a((gb) a2, (Spliterator) this.spliterator);
            Node<P_OUT> build = a.build();
            this.thisNodeSize = build.a();
            this.index = a2.a();
            return build;
        }

        @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter<?> countedCompleter) {
            if (!j()) {
                if (this.isOrdered) {
                    this.index = ((g) this.leftChild).index;
                    if (this.index == ((g) this.leftChild).thisNodeSize) {
                        this.index += ((g) this.rightChild).index;
                    }
                }
                this.thisNodeSize = ((g) this.leftChild).thisNodeSize + ((g) this.rightChild).thisNodeSize;
                Node<P_OUT> c = c();
                if (k()) {
                    c = a(c);
                }
                b((g<P_IN, P_OUT>) c);
            }
            super.onCompletion(countedCompleter);
        }

        private Node<P_OUT> c() {
            if (((g) this.leftChild).thisNodeSize == 0) {
                return ((g) this.rightChild).b();
            }
            if (((g) this.rightChild).thisNodeSize == 0) {
                return ((g) this.leftChild).b();
            }
            return fn.a(this.op.e(), ((g) this.leftChild).b(), ((g) this.rightChild).b());
        }

        private Node<P_OUT> a(Node<P_OUT> node) {
            return this.isOrdered ? node.a(this.index, node.a(), this.generator) : node;
        }
    }
}
