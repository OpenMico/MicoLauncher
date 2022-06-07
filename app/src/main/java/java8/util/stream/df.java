package java8.util.stream;

import java.util.Iterator;
import java8.util.DoubleSummaryStatistics;
import java8.util.Objects;
import java8.util.OptionalDouble;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.BiConsumer;
import java8.util.function.DoubleBinaryOperator;
import java8.util.function.DoubleConsumer;
import java8.util.function.DoubleFunction;
import java8.util.function.DoublePredicate;
import java8.util.function.DoubleToIntFunction;
import java8.util.function.DoubleToLongFunction;
import java8.util.function.DoubleUnaryOperator;
import java8.util.function.IntFunction;
import java8.util.function.ObjDoubleConsumer;
import java8.util.function.Supplier;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.eg;
import java8.util.stream.eu;
import java8.util.stream.fi;
import java8.util.stream.gd;
import java8.util.stream.gr;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DoublePipeline.java */
/* loaded from: classes5.dex */
public abstract class df<E_IN> extends a<E_IN, Double, DoubleStream> implements DoubleStream {
    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    public /* bridge */ /* synthetic */ DoubleStream parallel() {
        return (DoubleStream) super.parallel();
    }

    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    public /* bridge */ /* synthetic */ DoubleStream sequential() {
        return (DoubleStream) super.sequential();
    }

    df(Supplier<? extends Spliterator<Double>> supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    df(Spliterator<Double> spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    df(a<?, E_IN, ?> aVar, int i) {
        super(aVar, i);
    }

    private static DoubleConsumer b(Sink<Double> sink) {
        if (sink instanceof DoubleConsumer) {
            return (DoubleConsumer) sink;
        }
        sink.getClass();
        return dg.a(sink);
    }

    public static Spliterator.OfDouble e(Spliterator<Double> spliterator) {
        if (spliterator instanceof Spliterator.OfDouble) {
            return (Spliterator.OfDouble) spliterator;
        }
        throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.a
    public final gq e() {
        return gq.DOUBLE_VALUE;
    }

    @Override // java8.util.stream.a
    final <P_IN> Node<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator, boolean z, IntFunction<Double[]> intFunction) {
        return fn.c(gbVar, spliterator, z);
    }

    @Override // java8.util.stream.a
    final <P_IN> Spliterator<Double> a(gb<Double> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
        return new gr.e(gbVar, supplier, z);
    }

    /* renamed from: b */
    public final Spliterator.OfDouble a(Supplier<? extends Spliterator<Double>> supplier) {
        return new gr.c.a(supplier);
    }

    @Override // java8.util.stream.a
    final boolean a(Spliterator<Double> spliterator, Sink<Double> sink) {
        boolean cancellationRequested;
        Spliterator.OfDouble e = e(spliterator);
        DoubleConsumer b2 = b(sink);
        do {
            cancellationRequested = sink.cancellationRequested();
            if (cancellationRequested) {
                break;
            }
        } while (e.tryAdvance(b2));
        return cancellationRequested;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.a, java8.util.stream.gb
    public final Node.Builder<Double> a(long j, IntFunction<Double[]> intFunction) {
        return fn.c(j);
    }

    private <U> Stream<U> a(final DoubleFunction<? extends U> doubleFunction, int i) {
        return new gd.c<Double, U>(this, gq.DOUBLE_VALUE, i) { // from class: java8.util.stream.df.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i2, Sink<U> sink) {
                return new Sink.ChainedDouble<U>(sink) { // from class: java8.util.stream.df.1.1
                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    public void accept(double d) {
                        this.downstream.accept((Sink) doubleFunction.apply(d));
                    }
                };
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java8.util.Spliterator$OfDouble] */
    @Override // java8.util.stream.BaseStream
    public final Iterator<Double> iterator() {
        return Spliterators.iterator((Spliterator.OfDouble) spliterator2());
    }

    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    /* renamed from: spliterator */
    public final Spliterator<Double> spliterator2() {
        return e(super.spliterator());
    }

    @Override // java8.util.stream.DoubleStream
    public final Stream<Double> boxed() {
        return a(dm.a(), 0);
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream map(final DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        return new c<Double>(this, gq.DOUBLE_VALUE, gp.o | gp.m) { // from class: java8.util.stream.df.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink) { // from class: java8.util.stream.df.2.1
                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    public void accept(double d) {
                        this.downstream.accept(doubleUnaryOperator.applyAsDouble(d));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.DoubleStream
    public final <U> Stream<U> mapToObj(DoubleFunction<? extends U> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return a(doubleFunction, gp.o | gp.m);
    }

    @Override // java8.util.stream.DoubleStream
    public final IntStream mapToInt(final DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        return new eg.c<Double>(this, gq.DOUBLE_VALUE, gp.o | gp.m) { // from class: java8.util.stream.df.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedDouble<Integer>(sink) { // from class: java8.util.stream.df.3.1
                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    public void accept(double d) {
                        this.downstream.accept(doubleToIntFunction.applyAsInt(d));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.DoubleStream
    public final LongStream mapToLong(final DoubleToLongFunction doubleToLongFunction) {
        Objects.requireNonNull(doubleToLongFunction);
        return new eu.c<Double>(this, gq.DOUBLE_VALUE, gp.o | gp.m) { // from class: java8.util.stream.df.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i, Sink<Long> sink) {
                return new Sink.ChainedDouble<Long>(sink) { // from class: java8.util.stream.df.4.1
                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    public void accept(double d) {
                        this.downstream.accept(doubleToLongFunction.applyAsLong(d));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream flatMap(final DoubleFunction<? extends DoubleStream> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return new c<Double>(this, gq.DOUBLE_VALUE, gp.o | gp.m | gp.s) { // from class: java8.util.stream.df.5
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink) { // from class: java8.util.stream.df.5.1
                    boolean a;
                    DoubleConsumer b;

                    {
                        AnonymousClass5.this = this;
                        Sink sink2 = this.downstream;
                        sink2.getClass();
                        this.b = du.a(sink2);
                    }

                    @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    /* JADX WARN: Type inference failed for: r4v5, types: [java8.util.Spliterator$OfDouble] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void accept(double r3) {
                        /*
                            r2 = this;
                            r0 = 0
                            java8.util.stream.df$5 r1 = java8.util.stream.df.AnonymousClass5.this     // Catch: all -> 0x003c
                            java8.util.function.DoubleFunction r1 = r5     // Catch: all -> 0x003c
                            java.lang.Object r3 = r1.apply(r3)     // Catch: all -> 0x003c
                            java8.util.stream.DoubleStream r3 = (java8.util.stream.DoubleStream) r3     // Catch: all -> 0x003c
                            if (r3 == 0) goto L_0x0036
                            boolean r4 = r2.a     // Catch: all -> 0x0034
                            if (r4 != 0) goto L_0x001b
                            java8.util.stream.DoubleStream r4 = r3.sequential()     // Catch: all -> 0x0034
                            java8.util.function.DoubleConsumer r0 = r2.b     // Catch: all -> 0x0034
                            r4.forEach(r0)     // Catch: all -> 0x0034
                            goto L_0x0036
                        L_0x001b:
                            java8.util.stream.DoubleStream r4 = r3.sequential()     // Catch: all -> 0x0034
                            java8.util.Spliterator$OfDouble r4 = r4.spliterator()     // Catch: all -> 0x0034
                        L_0x0023:
                            java8.util.stream.Sink r0 = r2.downstream     // Catch: all -> 0x0034
                            boolean r0 = r0.cancellationRequested()     // Catch: all -> 0x0034
                            if (r0 != 0) goto L_0x0036
                            java8.util.function.DoubleConsumer r0 = r2.b     // Catch: all -> 0x0034
                            boolean r0 = r4.tryAdvance(r0)     // Catch: all -> 0x0034
                            if (r0 != 0) goto L_0x0023
                            goto L_0x0036
                        L_0x0034:
                            r4 = move-exception
                            goto L_0x003e
                        L_0x0036:
                            if (r3 == 0) goto L_0x003b
                            r3.close()
                        L_0x003b:
                            return
                        L_0x003c:
                            r4 = move-exception
                            r3 = r0
                        L_0x003e:
                            if (r3 == 0) goto L_0x0043
                            r3.close()
                        L_0x0043:
                            throw r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.df.AnonymousClass5.AnonymousClass1.accept(double):void");
                    }

                    @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.a = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    /* renamed from: g */
    public DoubleStream unordered() {
        return !d() ? this : new c<Double>(this, gq.DOUBLE_VALUE, gp.q) { // from class: java8.util.stream.df.6
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i, Sink<Double> sink) {
                return sink;
            }
        };
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream filter(final DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new c<Double>(this, gq.DOUBLE_VALUE, gp.s) { // from class: java8.util.stream.df.7
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink) { // from class: java8.util.stream.df.7.1
                    @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    public void accept(double d) {
                        if (doublePredicate.test(d)) {
                            this.downstream.accept(d);
                        }
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream peek(final DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new c<Double>(this, gq.DOUBLE_VALUE, 0) { // from class: java8.util.stream.df.8
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Double> a(int i, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink) { // from class: java8.util.stream.df.8.1
                    @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                    public void accept(double d) {
                        doubleConsumer.accept(d);
                        this.downstream.accept(d);
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream limit(long j) {
        if (j >= 0) {
            return gk.d(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream skip(long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : gk.d(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream takeWhile(DoublePredicate doublePredicate) {
        return hi.a(this, doublePredicate);
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream dropWhile(DoublePredicate doublePredicate) {
        return hi.b(this, doublePredicate);
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream sorted() {
        return gl.d(this);
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleStream distinct() {
        return boxed().distinct().mapToDouble(dn.a());
    }

    @Override // java8.util.stream.DoubleStream
    public void forEach(DoubleConsumer doubleConsumer) {
        a(ee.a(doubleConsumer, false));
    }

    @Override // java8.util.stream.DoubleStream
    public void forEachOrdered(DoubleConsumer doubleConsumer) {
        a(ee.a(doubleConsumer, true));
    }

    public static /* synthetic */ double[] i() {
        return new double[3];
    }

    @Override // java8.util.stream.DoubleStream
    public final double sum() {
        return Collectors.a((double[]) collect(Cdo.a(), dp.a(), dq.a()));
    }

    public static /* synthetic */ void b(double[] dArr, double d) {
        Collectors.a(dArr, d);
        dArr[2] = dArr[2] + d;
    }

    public static /* synthetic */ void b(double[] dArr, double[] dArr2) {
        Collectors.a(dArr, dArr2[0]);
        Collectors.a(dArr, -dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
    }

    @Override // java8.util.stream.DoubleStream
    public final OptionalDouble min() {
        return reduce(dr.a());
    }

    @Override // java8.util.stream.DoubleStream
    public final OptionalDouble max() {
        return reduce(ds.a());
    }

    public static /* synthetic */ double[] h() {
        return new double[4];
    }

    @Override // java8.util.stream.DoubleStream
    public final OptionalDouble average() {
        double[] dArr = (double[]) collect(dt.a(), dh.a(), di.a());
        if (dArr[2] > 0.0d) {
            return OptionalDouble.of(Collectors.a(dArr) / dArr[2]);
        }
        return OptionalDouble.empty();
    }

    public static /* synthetic */ void a(double[] dArr, double d) {
        dArr[2] = dArr[2] + 1.0d;
        Collectors.a(dArr, d);
        dArr[3] = dArr[3] + d;
    }

    public static /* synthetic */ void a(double[] dArr, double[] dArr2) {
        Collectors.a(dArr, dArr2[0]);
        Collectors.a(dArr, -dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
        dArr[3] = dArr[3] + dArr2[3];
    }

    @Override // java8.util.stream.DoubleStream
    public final long count() {
        return ((Long) a(gc.d())).longValue();
    }

    @Override // java8.util.stream.DoubleStream
    public final DoubleSummaryStatistics summaryStatistics() {
        return (DoubleSummaryStatistics) collect(Collectors.g, dj.a(), dk.a());
    }

    @Override // java8.util.stream.DoubleStream
    public final double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return ((Double) a(gc.a(d, doubleBinaryOperator))).doubleValue();
    }

    @Override // java8.util.stream.DoubleStream
    public final OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
        return (OptionalDouble) a(gc.a(doubleBinaryOperator));
    }

    @Override // java8.util.stream.DoubleStream
    public final <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> objDoubleConsumer, BiConsumer<R, R> biConsumer) {
        Objects.requireNonNull(biConsumer);
        return (R) a(gc.a(supplier, objDoubleConsumer, dl.a(biConsumer)));
    }

    @Override // java8.util.stream.DoubleStream
    public final boolean anyMatch(DoublePredicate doublePredicate) {
        return ((Boolean) a(fi.a(doublePredicate, fi.f.ANY))).booleanValue();
    }

    @Override // java8.util.stream.DoubleStream
    public final boolean allMatch(DoublePredicate doublePredicate) {
        return ((Boolean) a(fi.a(doublePredicate, fi.f.ALL))).booleanValue();
    }

    @Override // java8.util.stream.DoubleStream
    public final boolean noneMatch(DoublePredicate doublePredicate) {
        return ((Boolean) a(fi.a(doublePredicate, fi.f.NONE))).booleanValue();
    }

    @Override // java8.util.stream.DoubleStream
    public final OptionalDouble findFirst() {
        return (OptionalDouble) a(dv.d(true));
    }

    @Override // java8.util.stream.DoubleStream
    public final OptionalDouble findAny() {
        return (OptionalDouble) a(dv.d(false));
    }

    @Override // java8.util.stream.DoubleStream
    public final double[] toArray() {
        return fn.a((Node.OfDouble) a(hi.e)).asPrimitiveArray();
    }

    /* compiled from: DoublePipeline.java */
    /* loaded from: classes5.dex */
    public static class a<E_IN> extends df<E_IN> {
        @Override // java8.util.stream.df, java8.util.stream.a
        /* synthetic */ Spliterator<Double> a(Supplier<? extends Spliterator<Double>> supplier) {
            return df.super.a(supplier);
        }

        @Override // java8.util.stream.df, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Double> iterator() {
            return df.super.iterator();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) df.super.parallel();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) df.super.sequential();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Double> spliterator() {
            return df.super.spliterator2();
        }

        @Override // java8.util.stream.df, java8.util.stream.BaseStream
        public /* synthetic */ DoubleStream unordered() {
            return df.super.unordered();
        }

        public a(Supplier<? extends Spliterator<Double>> supplier, int i, boolean z) {
            super(supplier, i, z);
        }

        public a(Spliterator<Double> spliterator, int i, boolean z) {
            super(spliterator, i, z);
        }

        @Override // java8.util.stream.a
        final boolean f() {
            throw new UnsupportedOperationException();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // java8.util.stream.a
        public final Sink<E_IN> a(int i, Sink<Double> sink) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.stream.df, java8.util.stream.DoubleStream
        public void forEach(DoubleConsumer doubleConsumer) {
            if (!isParallel()) {
                df.e(a()).forEachRemaining(doubleConsumer);
            } else {
                df.super.forEach(doubleConsumer);
            }
        }

        @Override // java8.util.stream.df, java8.util.stream.DoubleStream
        public void forEachOrdered(DoubleConsumer doubleConsumer) {
            if (!isParallel()) {
                df.e(a()).forEachRemaining(doubleConsumer);
            } else {
                df.super.forEachOrdered(doubleConsumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: DoublePipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class c<E_IN> extends df<E_IN> {
        @Override // java8.util.stream.a
        final boolean f() {
            return false;
        }

        @Override // java8.util.stream.df, java8.util.stream.a
        /* synthetic */ Spliterator<Double> a(Supplier<? extends Spliterator<Double>> supplier) {
            return df.super.a(supplier);
        }

        @Override // java8.util.stream.df, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Double> iterator() {
            return df.super.iterator();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) df.super.parallel();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) df.super.sequential();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Double> spliterator() {
            return df.super.spliterator2();
        }

        @Override // java8.util.stream.df, java8.util.stream.BaseStream
        public /* synthetic */ DoubleStream unordered() {
            return df.super.unordered();
        }

        public c(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: DoublePipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class b<E_IN> extends df<E_IN> {
        @Override // java8.util.stream.a
        abstract <P_IN> Node<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator, IntFunction<Double[]> intFunction);

        @Override // java8.util.stream.a
        final boolean f() {
            return true;
        }

        @Override // java8.util.stream.df, java8.util.stream.a
        /* synthetic */ Spliterator<Double> a(Supplier<? extends Spliterator<Double>> supplier) {
            return df.super.a(supplier);
        }

        @Override // java8.util.stream.df, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Double> iterator() {
            return df.super.iterator();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) df.super.parallel();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) df.super.sequential();
        }

        @Override // java8.util.stream.df, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Double> spliterator() {
            return df.super.spliterator2();
        }

        @Override // java8.util.stream.df, java8.util.stream.BaseStream
        public /* synthetic */ DoubleStream unordered() {
            return df.super.unordered();
        }

        public b(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }
}
