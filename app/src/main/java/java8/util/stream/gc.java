package java8.util.stream;

import java8.util.Objects;
import java8.util.Optional;
import java8.util.OptionalDouble;
import java8.util.OptionalInt;
import java8.util.OptionalLong;
import java8.util.Spliterator;
import java8.util.concurrent.CountedCompleter;
import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;
import java8.util.function.BinaryOperator;
import java8.util.function.DoubleBinaryOperator;
import java8.util.function.IntBinaryOperator;
import java8.util.function.LongBinaryOperator;
import java8.util.function.ObjDoubleConsumer;
import java8.util.function.ObjIntConsumer;
import java8.util.function.ObjLongConsumer;
import java8.util.function.Supplier;
import java8.util.stream.Collector;
import java8.util.stream.Sink;
import java8.util.stream.gj;

/* compiled from: ReduceOps.java */
/* loaded from: classes5.dex */
public final class gc {

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public interface n<T, R, K extends n<T, R, K>> extends hh<T, R> {
        void a(K k);
    }

    public static <T, U> hg<T, U> a(final U u, final BiFunction<U, ? super T, U> biFunction, final BinaryOperator<U> binaryOperator) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(binaryOperator);
        return new q<T, U, e>(gq.REFERENCE) { // from class: java8.util.stream.gc.1
            /* renamed from: b */
            public e c() {
                return new e(u, biFunction, binaryOperator);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class e extends o<U> implements n<T, U, e> {
        final /* synthetic */ Object a;
        final /* synthetic */ BiFunction b;
        final /* synthetic */ BinaryOperator c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        e(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
            this.a = obj;
            this.b = biFunction;
            this.c = binaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.d = this.a;
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            this.d = this.b.apply(this.d, t);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        public void a(e eVar) {
            this.d = this.c.apply(this.d, eVar.d);
        }
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class f implements n<T, Optional<T>, f> {
        final /* synthetic */ BinaryOperator a;
        private boolean b;
        private T c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        f(BinaryOperator binaryOperator) {
            this.a = binaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.b = true;
            this.c = null;
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            if (this.b) {
                this.b = false;
                this.c = t;
                return;
            }
            this.c = this.a.apply(this.c, t);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        /* renamed from: a */
        public Optional<T> get() {
            return this.b ? Optional.empty() : Optional.of(this.c);
        }

        public void a(f fVar) {
            if (!fVar.b) {
                accept((f) fVar.c);
            }
        }
    }

    public static <T> hg<T, Optional<T>> a(final BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        return new q<T, Optional<T>, f>(gq.REFERENCE) { // from class: java8.util.stream.gc.10
            /* renamed from: b */
            public f c() {
                return new f(binaryOperator);
            }
        };
    }

    public static <T, I> hg<T, I> a(final Collector<? super T, I, ?> collector) {
        final Supplier supplier = ((Collector) Objects.requireNonNull(collector)).supplier();
        final BiConsumer<I, ? super T> accumulator = collector.accumulator();
        final BinaryOperator<I> combiner = collector.combiner();
        return new q<T, I, g>(gq.REFERENCE) { // from class: java8.util.stream.gc.11
            /* renamed from: b */
            public g c() {
                return new g(supplier, accumulator, combiner);
            }

            @Override // java8.util.stream.gc.q, java8.util.stream.hg
            public int a() {
                if (collector.characteristics().contains(Collector.Characteristics.UNORDERED)) {
                    return gp.q;
                }
                return 0;
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class g extends o<I> implements n<T, I, g> {
        final /* synthetic */ Supplier a;
        final /* synthetic */ BiConsumer b;
        final /* synthetic */ BinaryOperator c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        g(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator) {
            this.a = supplier;
            this.b = biConsumer;
            this.c = binaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.d = this.a.get();
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            this.b.accept(this.d, t);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        public void a(g gVar) {
            this.d = this.c.apply(this.d, gVar.d);
        }
    }

    public static <T, R> hg<T, R> a(final Supplier<R> supplier, final BiConsumer<R, ? super T> biConsumer, final BiConsumer<R, R> biConsumer2) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return new q<T, R, h>(gq.REFERENCE) { // from class: java8.util.stream.gc.12
            /* renamed from: b */
            public h c() {
                return new h(supplier, biConsumer, biConsumer2);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class h extends o<R> implements n<T, R, h> {
        final /* synthetic */ Supplier a;
        final /* synthetic */ BiConsumer b;
        final /* synthetic */ BiConsumer c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        h(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
            this.a = supplier;
            this.b = biConsumer;
            this.c = biConsumer2;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.d = this.a.get();
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            this.b.accept(this.d, t);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        public void a(h hVar) {
            this.c.accept(this.d, hVar.d);
        }
    }

    public static <T> hg<T, Long> a() {
        return new q<T, Long, p<T>>(gq.REFERENCE) { // from class: java8.util.stream.gc.13
            /* renamed from: b */
            public p<T> c() {
                return new p.d();
            }

            /* renamed from: c */
            public <P_IN> Long a(gb<T> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.a(gbVar, spliterator);
            }

            /* renamed from: d */
            public <P_IN> Long b(gb<T> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.gc.q, java8.util.stream.hg
            public int a() {
                return gp.q;
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class i implements Sink.OfInt, n<Integer, Integer, i> {
        final /* synthetic */ int a;
        final /* synthetic */ IntBinaryOperator b;
        private int c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        i(int i, IntBinaryOperator intBinaryOperator) {
            this.a = i;
            this.b = intBinaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.c = this.a;
        }

        @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
        public void accept(int i) {
            this.c = this.b.applyAsInt(this.c, i);
        }

        @Override // java8.util.stream.Sink.OfInt
        public void accept(Integer num) {
            gj.b.a(this, num);
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        /* renamed from: a */
        public Integer get() {
            return Integer.valueOf(this.c);
        }

        public void a(i iVar) {
            accept(iVar.c);
        }
    }

    public static hg<Integer, Integer> a(final int i2, final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return new q<Integer, Integer, i>(gq.INT_VALUE) { // from class: java8.util.stream.gc.14
            /* renamed from: b */
            public i c() {
                return new i(i2, intBinaryOperator);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class j implements Sink.OfInt, n<Integer, OptionalInt, j> {
        final /* synthetic */ IntBinaryOperator a;
        private boolean b;
        private int c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        j(IntBinaryOperator intBinaryOperator) {
            this.a = intBinaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.b = true;
            this.c = 0;
        }

        @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
        public void accept(int i) {
            if (this.b) {
                this.b = false;
                this.c = i;
                return;
            }
            this.c = this.a.applyAsInt(this.c, i);
        }

        @Override // java8.util.stream.Sink.OfInt
        public void accept(Integer num) {
            gj.b.a(this, num);
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        /* renamed from: a */
        public OptionalInt get() {
            return this.b ? OptionalInt.empty() : OptionalInt.of(this.c);
        }

        public void a(j jVar) {
            if (!jVar.b) {
                accept(jVar.c);
            }
        }
    }

    public static hg<Integer, OptionalInt> a(final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return new q<Integer, OptionalInt, j>(gq.INT_VALUE) { // from class: java8.util.stream.gc.15
            /* renamed from: b */
            public j c() {
                return new j(intBinaryOperator);
            }
        };
    }

    public static <R> hg<Integer, R> a(final Supplier<R> supplier, final ObjIntConsumer<R> objIntConsumer, final BinaryOperator<R> binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objIntConsumer);
        Objects.requireNonNull(binaryOperator);
        return new q<Integer, R, k>(gq.INT_VALUE) { // from class: java8.util.stream.gc.16
            /* renamed from: b */
            public k c() {
                return new k(supplier, objIntConsumer, binaryOperator);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class k extends o<R> implements Sink.OfInt, n<Integer, R, k> {
        final /* synthetic */ Supplier a;
        final /* synthetic */ ObjIntConsumer b;
        final /* synthetic */ BinaryOperator c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        k(Supplier supplier, ObjIntConsumer objIntConsumer, BinaryOperator binaryOperator) {
            this.a = supplier;
            this.b = objIntConsumer;
            this.c = binaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.d = this.a.get();
        }

        @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
        public void accept(int i) {
            this.b.accept(this.d, i);
        }

        @Override // java8.util.stream.Sink.OfInt
        public void accept(Integer num) {
            gj.b.a(this, num);
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        public void a(k kVar) {
            this.d = this.c.apply(this.d, kVar.d);
        }
    }

    public static hg<Integer, Long> b() {
        return new q<Integer, Long, p<Integer>>(gq.INT_VALUE) { // from class: java8.util.stream.gc.17
            /* renamed from: b */
            public p<Integer> c() {
                return new p.b();
            }

            /* renamed from: c */
            public <P_IN> Long a(gb<Integer> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.a(gbVar, spliterator);
            }

            /* renamed from: d */
            public <P_IN> Long b(gb<Integer> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.gc.q, java8.util.stream.hg
            public int a() {
                return gp.q;
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class l implements Sink.OfLong, n<Long, Long, l> {
        final /* synthetic */ long a;
        final /* synthetic */ LongBinaryOperator b;
        private long c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        l(long j, LongBinaryOperator longBinaryOperator) {
            this.a = j;
            this.b = longBinaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.c = this.a;
        }

        @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
        public void accept(long j) {
            this.c = this.b.applyAsLong(this.c, j);
        }

        @Override // java8.util.stream.Sink.OfLong
        public void accept(Long l) {
            gj.c.a(this, l);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        /* renamed from: a */
        public Long get() {
            return Long.valueOf(this.c);
        }

        public void a(l lVar) {
            accept(lVar.c);
        }
    }

    public static hg<Long, Long> a(final long j2, final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return new q<Long, Long, l>(gq.LONG_VALUE) { // from class: java8.util.stream.gc.2
            /* renamed from: b */
            public l c() {
                return new l(j2, longBinaryOperator);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class m implements Sink.OfLong, n<Long, OptionalLong, m> {
        final /* synthetic */ LongBinaryOperator a;
        private boolean b;
        private long c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        m(LongBinaryOperator longBinaryOperator) {
            this.a = longBinaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.b = true;
            this.c = 0L;
        }

        @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
        public void accept(long j) {
            if (this.b) {
                this.b = false;
                this.c = j;
                return;
            }
            this.c = this.a.applyAsLong(this.c, j);
        }

        @Override // java8.util.stream.Sink.OfLong
        public void accept(Long l) {
            gj.c.a(this, l);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        /* renamed from: a */
        public OptionalLong get() {
            return this.b ? OptionalLong.empty() : OptionalLong.of(this.c);
        }

        public void a(m mVar) {
            if (!mVar.b) {
                accept(mVar.c);
            }
        }
    }

    public static hg<Long, OptionalLong> a(final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return new q<Long, OptionalLong, m>(gq.LONG_VALUE) { // from class: java8.util.stream.gc.3
            /* renamed from: b */
            public m c() {
                return new m(longBinaryOperator);
            }
        };
    }

    public static <R> hg<Long, R> a(final Supplier<R> supplier, final ObjLongConsumer<R> objLongConsumer, final BinaryOperator<R> binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objLongConsumer);
        Objects.requireNonNull(binaryOperator);
        return new q<Long, R, a>(gq.LONG_VALUE) { // from class: java8.util.stream.gc.4
            /* renamed from: b */
            public a c() {
                return new a(supplier, objLongConsumer, binaryOperator);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class a extends o<R> implements Sink.OfLong, n<Long, R, a> {
        final /* synthetic */ Supplier a;
        final /* synthetic */ ObjLongConsumer b;
        final /* synthetic */ BinaryOperator c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        a(Supplier supplier, ObjLongConsumer objLongConsumer, BinaryOperator binaryOperator) {
            this.a = supplier;
            this.b = objLongConsumer;
            this.c = binaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.d = this.a.get();
        }

        @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
        public void accept(long j) {
            this.b.accept(this.d, j);
        }

        @Override // java8.util.stream.Sink.OfLong
        public void accept(Long l) {
            gj.c.a(this, l);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        public void a(a aVar) {
            this.d = this.c.apply(this.d, aVar.d);
        }
    }

    public static hg<Long, Long> c() {
        return new q<Long, Long, p<Long>>(gq.LONG_VALUE) { // from class: java8.util.stream.gc.5
            /* renamed from: b */
            public p<Long> c() {
                return new p.c();
            }

            /* renamed from: c */
            public <P_IN> Long a(gb<Long> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.a(gbVar, spliterator);
            }

            /* renamed from: d */
            public <P_IN> Long b(gb<Long> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.gc.q, java8.util.stream.hg
            public int a() {
                return gp.q;
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class b implements Sink.OfDouble, n<Double, Double, b> {
        final /* synthetic */ double a;
        final /* synthetic */ DoubleBinaryOperator b;
        private double c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        b(double d, DoubleBinaryOperator doubleBinaryOperator) {
            this.a = d;
            this.b = doubleBinaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.c = this.a;
        }

        @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
        public void accept(double d) {
            this.c = this.b.applyAsDouble(this.c, d);
        }

        @Override // java8.util.stream.Sink.OfDouble
        public void accept(Double d) {
            gj.a.a(this, d);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        /* renamed from: a */
        public Double get() {
            return Double.valueOf(this.c);
        }

        public void a(b bVar) {
            accept(bVar.c);
        }
    }

    public static hg<Double, Double> a(final double d2, final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return new q<Double, Double, b>(gq.DOUBLE_VALUE) { // from class: java8.util.stream.gc.6
            /* renamed from: b */
            public b c() {
                return new b(d2, doubleBinaryOperator);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class c implements Sink.OfDouble, n<Double, OptionalDouble, c> {
        final /* synthetic */ DoubleBinaryOperator a;
        private boolean b;
        private double c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        c(DoubleBinaryOperator doubleBinaryOperator) {
            this.a = doubleBinaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.b = true;
            this.c = 0.0d;
        }

        @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
        public void accept(double d) {
            if (this.b) {
                this.b = false;
                this.c = d;
                return;
            }
            this.c = this.a.applyAsDouble(this.c, d);
        }

        @Override // java8.util.stream.Sink.OfDouble
        public void accept(Double d) {
            gj.a.a(this, d);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        /* renamed from: a */
        public OptionalDouble get() {
            return this.b ? OptionalDouble.empty() : OptionalDouble.of(this.c);
        }

        public void a(c cVar) {
            if (!cVar.b) {
                accept(cVar.c);
            }
        }
    }

    public static hg<Double, OptionalDouble> a(final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return new q<Double, OptionalDouble, c>(gq.DOUBLE_VALUE) { // from class: java8.util.stream.gc.7
            /* renamed from: b */
            public c c() {
                return new c(doubleBinaryOperator);
            }
        };
    }

    public static <R> hg<Double, R> a(final Supplier<R> supplier, final ObjDoubleConsumer<R> objDoubleConsumer, final BinaryOperator<R> binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objDoubleConsumer);
        Objects.requireNonNull(binaryOperator);
        return new q<Double, R, d>(gq.DOUBLE_VALUE) { // from class: java8.util.stream.gc.8
            /* renamed from: b */
            public d c() {
                return new d(supplier, objDoubleConsumer, binaryOperator);
            }
        };
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public class d extends o<R> implements Sink.OfDouble, n<Double, R, d> {
        final /* synthetic */ Supplier a;
        final /* synthetic */ ObjDoubleConsumer b;
        final /* synthetic */ BinaryOperator c;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        d(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BinaryOperator binaryOperator) {
            this.a = supplier;
            this.b = objDoubleConsumer;
            this.c = binaryOperator;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.d = this.a.get();
        }

        @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
        public void accept(double d) {
            this.b.accept(this.d, d);
        }

        @Override // java8.util.stream.Sink.OfDouble
        public void accept(Double d) {
            gj.a.a(this, d);
        }

        public void a(d dVar) {
            this.d = this.c.apply(this.d, dVar.d);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }
    }

    public static hg<Double, Long> d() {
        return new q<Double, Long, p<Double>>(gq.DOUBLE_VALUE) { // from class: java8.util.stream.gc.9
            /* renamed from: b */
            public p<Double> c() {
                return new p.a();
            }

            /* renamed from: c */
            public <P_IN> Long a(gb<Double> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.a(gbVar, spliterator);
            }

            /* renamed from: d */
            public <P_IN> Long b(gb<Double> gbVar, Spliterator<P_IN> spliterator) {
                if (gp.SIZED.a(gbVar.c())) {
                    return Long.valueOf(spliterator.getExactSizeIfKnown());
                }
                return (Long) super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.gc.q, java8.util.stream.hg
            public int a() {
                return gp.q;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public static abstract class p<T> extends o<Long> implements n<T, Long, p<T>> {
        long a;

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        p() {
        }

        @Override // java8.util.stream.gc.n
        public /* bridge */ /* synthetic */ void a(n nVar) {
            a((p) ((p) nVar));
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.a = 0L;
        }

        /* renamed from: a */
        public Long get() {
            return Long.valueOf(this.a);
        }

        public void a(p<T> pVar) {
            this.a += pVar.a;
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d2) {
            gj.a();
        }

        /* compiled from: ReduceOps.java */
        /* loaded from: classes5.dex */
        public static final class d<T> extends p<T> {
            d() {
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.n
            public /* bridge */ /* synthetic */ void a(n nVar) {
                super.a((p) ((p) nVar));
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.o, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            @Override // java8.util.function.Consumer
            public void accept(T t) {
                this.a++;
            }
        }

        /* compiled from: ReduceOps.java */
        /* loaded from: classes5.dex */
        public static final class b extends p<Integer> implements Sink.OfInt {
            b() {
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.n
            public /* bridge */ /* synthetic */ void a(n nVar) {
                super.a((p) ((p) nVar));
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.o, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.Sink
            public void accept(int i) {
                this.a++;
            }

            @Override // java8.util.stream.Sink.OfInt
            public void accept(Integer num) {
                gj.b.a(this, num);
            }
        }

        /* compiled from: ReduceOps.java */
        /* loaded from: classes5.dex */
        public static final class c extends p<Long> implements Sink.OfLong {
            c() {
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.n
            public /* bridge */ /* synthetic */ void a(n nVar) {
                super.a((p) ((p) nVar));
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.o, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.Sink
            public void accept(long j) {
                this.a++;
            }

            @Override // java8.util.stream.Sink.OfLong
            public void accept(Long l) {
                gj.c.a(this, l);
            }
        }

        /* compiled from: ReduceOps.java */
        /* loaded from: classes5.dex */
        public static final class a extends p<Double> implements Sink.OfDouble {
            a() {
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.n
            public /* bridge */ /* synthetic */ void a(n nVar) {
                super.a((p) ((p) nVar));
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.gc.o, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            @Override // java8.util.stream.gc.p, java8.util.stream.Sink
            public void accept(double d) {
                this.a++;
            }

            @Override // java8.util.stream.Sink.OfDouble
            public void accept(Double d) {
                gj.a.a(this, d);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public static abstract class o<U> {
        U d;

        o() {
        }

        public U get() {
            return this.d;
        }
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public static abstract class q<T, R, S extends n<T, R, S>> implements hg<T, R> {
        private final gq a;

        @Override // java8.util.stream.hg
        public int a() {
            return 0;
        }

        public abstract S c();

        q(gq gqVar) {
            this.a = gqVar;
        }

        @Override // java8.util.stream.hg
        public <P_IN> R a(gb<T> gbVar, Spliterator<P_IN> spliterator) {
            return ((n) gbVar.a((gb<T>) c(), spliterator)).get();
        }

        @Override // java8.util.stream.hg
        public <P_IN> R b(gb<T> gbVar, Spliterator<P_IN> spliterator) {
            return ((n) new r(this, gbVar, spliterator).invoke()).get();
        }
    }

    /* compiled from: ReduceOps.java */
    /* loaded from: classes5.dex */
    public static final class r<P_IN, P_OUT, R, S extends n<P_OUT, R, S>> extends g<P_IN, P_OUT, S, r<P_IN, P_OUT, R, S>> {
        private final q<P_OUT, R, S> op;

        r(q<P_OUT, R, S> qVar, gb<P_OUT> gbVar, Spliterator<P_IN> spliterator) {
            super(gbVar, spliterator);
            this.op = qVar;
        }

        r(r<P_IN, P_OUT, R, S> rVar, Spliterator<P_IN> spliterator) {
            super(rVar, spliterator);
            this.op = rVar.op;
        }

        /* renamed from: b */
        public r<P_IN, P_OUT, R, S> a(Spliterator<P_IN> spliterator) {
            return new r<>(this, spliterator);
        }

        /* renamed from: a */
        public S i() {
            return (S) ((n) this.helper.a((gb) this.op.c(), (Spliterator) this.spliterator));
        }

        @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter
        public void onCompletion(CountedCompleter<?> countedCompleter) {
            if (!j()) {
                n nVar = (n) ((r) this.leftChild).b();
                nVar.a((n) ((r) this.rightChild).b());
                b((r<P_IN, P_OUT, R, S>) nVar);
            }
            super.onCompletion(countedCompleter);
        }
    }
}
