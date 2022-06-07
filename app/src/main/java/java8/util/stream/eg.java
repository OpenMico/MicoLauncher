package java8.util.stream;

import java.util.Iterator;
import java8.util.IntSummaryStatistics;
import java8.util.Objects;
import java8.util.OptionalDouble;
import java8.util.OptionalInt;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.BiConsumer;
import java8.util.function.IntBinaryOperator;
import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;
import java8.util.function.IntPredicate;
import java8.util.function.IntToDoubleFunction;
import java8.util.function.IntToLongFunction;
import java8.util.function.IntUnaryOperator;
import java8.util.function.ObjIntConsumer;
import java8.util.function.Supplier;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.df;
import java8.util.stream.eu;
import java8.util.stream.fi;
import java8.util.stream.gd;
import java8.util.stream.gr;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IntPipeline.java */
/* loaded from: classes5.dex */
public abstract class eg<E_IN> extends a<E_IN, Integer, IntStream> implements IntStream {
    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    public /* bridge */ /* synthetic */ IntStream parallel() {
        return (IntStream) super.parallel();
    }

    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    public /* bridge */ /* synthetic */ IntStream sequential() {
        return (IntStream) super.sequential();
    }

    eg(Supplier<? extends Spliterator<Integer>> supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    eg(Spliterator<Integer> spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    eg(a<?, E_IN, ?> aVar, int i) {
        super(aVar, i);
    }

    private static IntConsumer b(Sink<Integer> sink) {
        if (sink instanceof IntConsumer) {
            return (IntConsumer) sink;
        }
        sink.getClass();
        return eh.a(sink);
    }

    public static Spliterator.OfInt e(Spliterator<Integer> spliterator) {
        if (spliterator instanceof Spliterator.OfInt) {
            return (Spliterator.OfInt) spliterator;
        }
        throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.a
    public final gq e() {
        return gq.INT_VALUE;
    }

    @Override // java8.util.stream.a
    final <P_IN> Node<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator, boolean z, IntFunction<Integer[]> intFunction) {
        return fn.a(gbVar, spliterator, z);
    }

    @Override // java8.util.stream.a
    final <P_IN> Spliterator<Integer> a(gb<Integer> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
        return new gr.g(gbVar, supplier, z);
    }

    /* renamed from: b */
    public final Spliterator.OfInt a(Supplier<? extends Spliterator<Integer>> supplier) {
        return new gr.c.b(supplier);
    }

    @Override // java8.util.stream.a
    final boolean a(Spliterator<Integer> spliterator, Sink<Integer> sink) {
        boolean cancellationRequested;
        Spliterator.OfInt e = e(spliterator);
        IntConsumer b2 = b(sink);
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
    public final Node.Builder<Integer> a(long j, IntFunction<Integer[]> intFunction) {
        return fn.a(j);
    }

    private <U> Stream<U> a(final IntFunction<? extends U> intFunction, int i) {
        return new gd.c<Integer, U>(this, gq.INT_VALUE, i) { // from class: java8.util.stream.eg.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i2, Sink<U> sink) {
                return new Sink.ChainedInt<U>(sink) { // from class: java8.util.stream.eg.1.1
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i3) {
                        this.downstream.accept((Sink) intFunction.apply(i3));
                    }
                };
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java8.util.Spliterator$OfInt] */
    @Override // java8.util.stream.BaseStream
    public final Iterator<Integer> iterator() {
        return Spliterators.iterator((Spliterator.OfInt) spliterator2());
    }

    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    /* renamed from: spliterator */
    public final Spliterator<Integer> spliterator2() {
        return e(super.spliterator());
    }

    @Override // java8.util.stream.IntStream
    public final LongStream asLongStream() {
        return new eu.c<Integer>(this, gq.INT_VALUE, 0) { // from class: java8.util.stream.eg.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Long> sink) {
                return new Sink.ChainedInt<Long>(sink) { // from class: java8.util.stream.eg.3.1
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i2) {
                        this.downstream.accept(i2);
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final DoubleStream asDoubleStream() {
        return new df.c<Integer>(this, gq.INT_VALUE, 0) { // from class: java8.util.stream.eg.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Double> sink) {
                return new Sink.ChainedInt<Double>(sink) { // from class: java8.util.stream.eg.4.1
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i2) {
                        this.downstream.accept(i2);
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final Stream<Integer> boxed() {
        return a(el.a(), 0);
    }

    @Override // java8.util.stream.IntStream
    public final IntStream map(final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new c<Integer>(this, gq.INT_VALUE, gp.o | gp.m) { // from class: java8.util.stream.eg.5
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink) { // from class: java8.util.stream.eg.5.1
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i2) {
                        this.downstream.accept(intUnaryOperator.applyAsInt(i2));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final <U> Stream<U> mapToObj(IntFunction<? extends U> intFunction) {
        Objects.requireNonNull(intFunction);
        return a(intFunction, gp.o | gp.m);
    }

    @Override // java8.util.stream.IntStream
    public final LongStream mapToLong(final IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        return new eu.c<Integer>(this, gq.INT_VALUE, gp.o | gp.m) { // from class: java8.util.stream.eg.6
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Long> sink) {
                return new Sink.ChainedInt<Long>(sink) { // from class: java8.util.stream.eg.6.1
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i2) {
                        this.downstream.accept(intToLongFunction.applyAsLong(i2));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final DoubleStream mapToDouble(final IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        return new df.c<Integer>(this, gq.INT_VALUE, gp.o | gp.m) { // from class: java8.util.stream.eg.7
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Double> sink) {
                return new Sink.ChainedInt<Double>(sink) { // from class: java8.util.stream.eg.7.1
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i2) {
                        this.downstream.accept(intToDoubleFunction.applyAsDouble(i2));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final IntStream flatMap(final IntFunction<? extends IntStream> intFunction) {
        Objects.requireNonNull(intFunction);
        return new c<Integer>(this, gq.INT_VALUE, gp.o | gp.m | gp.s) { // from class: java8.util.stream.eg.8
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink) { // from class: java8.util.stream.eg.8.1
                    boolean a;
                    IntConsumer b;

                    {
                        AnonymousClass8.this = this;
                        Sink sink2 = this.downstream;
                        sink2.getClass();
                        this.b = et.a(sink2);
                    }

                    @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    /* JADX WARN: Type inference failed for: r0v6, types: [java8.util.Spliterator$OfInt] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void accept(int r4) {
                        /*
                            r3 = this;
                            r0 = 0
                            java8.util.stream.eg$8 r1 = java8.util.stream.eg.AnonymousClass8.this     // Catch: all -> 0x003c
                            java8.util.function.IntFunction r1 = r5     // Catch: all -> 0x003c
                            java.lang.Object r4 = r1.apply(r4)     // Catch: all -> 0x003c
                            java8.util.stream.IntStream r4 = (java8.util.stream.IntStream) r4     // Catch: all -> 0x003c
                            if (r4 == 0) goto L_0x0036
                            boolean r0 = r3.a     // Catch: all -> 0x0034
                            if (r0 != 0) goto L_0x001b
                            java8.util.stream.IntStream r0 = r4.sequential()     // Catch: all -> 0x0034
                            java8.util.function.IntConsumer r1 = r3.b     // Catch: all -> 0x0034
                            r0.forEach(r1)     // Catch: all -> 0x0034
                            goto L_0x0036
                        L_0x001b:
                            java8.util.stream.IntStream r0 = r4.sequential()     // Catch: all -> 0x0034
                            java8.util.Spliterator$OfInt r0 = r0.spliterator()     // Catch: all -> 0x0034
                        L_0x0023:
                            java8.util.stream.Sink r1 = r3.downstream     // Catch: all -> 0x0034
                            boolean r1 = r1.cancellationRequested()     // Catch: all -> 0x0034
                            if (r1 != 0) goto L_0x0036
                            java8.util.function.IntConsumer r1 = r3.b     // Catch: all -> 0x0034
                            boolean r1 = r0.tryAdvance(r1)     // Catch: all -> 0x0034
                            if (r1 != 0) goto L_0x0023
                            goto L_0x0036
                        L_0x0034:
                            r0 = move-exception
                            goto L_0x0040
                        L_0x0036:
                            if (r4 == 0) goto L_0x003b
                            r4.close()
                        L_0x003b:
                            return
                        L_0x003c:
                            r4 = move-exception
                            r2 = r0
                            r0 = r4
                            r4 = r2
                        L_0x0040:
                            if (r4 == 0) goto L_0x0045
                            r4.close()
                        L_0x0045:
                            throw r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.eg.AnonymousClass8.AnonymousClass1.accept(int):void");
                    }

                    @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.a = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    /* renamed from: g */
    public IntStream unordered() {
        return !d() ? this : new c<Integer>(this, gq.INT_VALUE, gp.q) { // from class: java8.util.stream.eg.9
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Integer> sink) {
                return sink;
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final IntStream filter(final IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new c<Integer>(this, gq.INT_VALUE, gp.s) { // from class: java8.util.stream.eg.10
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink) { // from class: java8.util.stream.eg.10.1
                    @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i2) {
                        if (intPredicate.test(i2)) {
                            this.downstream.accept(i2);
                        }
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final IntStream peek(final IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new c<Integer>(this, gq.INT_VALUE, 0) { // from class: java8.util.stream.eg.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Integer> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink) { // from class: java8.util.stream.eg.2.1
                    @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                    public void accept(int i2) {
                        intConsumer.accept(i2);
                        this.downstream.accept(i2);
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.IntStream
    public final IntStream limit(long j) {
        if (j >= 0) {
            return gk.b(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.IntStream
    public final IntStream skip(long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : gk.b(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.IntStream
    public final IntStream takeWhile(IntPredicate intPredicate) {
        return hi.a(this, intPredicate);
    }

    @Override // java8.util.stream.IntStream
    public final IntStream dropWhile(IntPredicate intPredicate) {
        return hi.b(this, intPredicate);
    }

    @Override // java8.util.stream.IntStream
    public final IntStream sorted() {
        return gl.b(this);
    }

    @Override // java8.util.stream.IntStream
    public final IntStream distinct() {
        return boxed().distinct().mapToInt(em.a());
    }

    @Override // java8.util.stream.IntStream
    public void forEach(IntConsumer intConsumer) {
        a(ee.a(intConsumer, false));
    }

    @Override // java8.util.stream.IntStream
    public void forEachOrdered(IntConsumer intConsumer) {
        a(ee.a(intConsumer, true));
    }

    @Override // java8.util.stream.IntStream
    public final int sum() {
        return reduce(0, en.a());
    }

    @Override // java8.util.stream.IntStream
    public final OptionalInt min() {
        return reduce(eo.a());
    }

    @Override // java8.util.stream.IntStream
    public final OptionalInt max() {
        return reduce(ep.a());
    }

    @Override // java8.util.stream.IntStream
    public final long count() {
        return ((Long) a(gc.b())).longValue();
    }

    public static /* synthetic */ long[] h() {
        return new long[2];
    }

    @Override // java8.util.stream.IntStream
    public final OptionalDouble average() {
        long[] jArr = (long[]) collect(eq.a(), er.a(), es.a());
        if (jArr[0] > 0) {
            return OptionalDouble.of(jArr[1] / jArr[0]);
        }
        return OptionalDouble.empty();
    }

    public static /* synthetic */ void a(long[] jArr, int i) {
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + i;
    }

    public static /* synthetic */ void a(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
    }

    @Override // java8.util.stream.IntStream
    public final IntSummaryStatistics summaryStatistics() {
        return (IntSummaryStatistics) collect(Collectors.h, ei.a(), ej.a());
    }

    @Override // java8.util.stream.IntStream
    public final int reduce(int i, IntBinaryOperator intBinaryOperator) {
        return ((Integer) a(gc.a(i, intBinaryOperator))).intValue();
    }

    @Override // java8.util.stream.IntStream
    public final OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        return (OptionalInt) a(gc.a(intBinaryOperator));
    }

    @Override // java8.util.stream.IntStream
    public final <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> objIntConsumer, BiConsumer<R, R> biConsumer) {
        Objects.requireNonNull(biConsumer);
        return (R) a(gc.a(supplier, objIntConsumer, ek.a(biConsumer)));
    }

    @Override // java8.util.stream.IntStream
    public final boolean anyMatch(IntPredicate intPredicate) {
        return ((Boolean) a(fi.a(intPredicate, fi.f.ANY))).booleanValue();
    }

    @Override // java8.util.stream.IntStream
    public final boolean allMatch(IntPredicate intPredicate) {
        return ((Boolean) a(fi.a(intPredicate, fi.f.ALL))).booleanValue();
    }

    @Override // java8.util.stream.IntStream
    public final boolean noneMatch(IntPredicate intPredicate) {
        return ((Boolean) a(fi.a(intPredicate, fi.f.NONE))).booleanValue();
    }

    @Override // java8.util.stream.IntStream
    public final OptionalInt findFirst() {
        return (OptionalInt) a(dv.b(true));
    }

    @Override // java8.util.stream.IntStream
    public final OptionalInt findAny() {
        return (OptionalInt) a(dv.b(false));
    }

    @Override // java8.util.stream.IntStream
    public final int[] toArray() {
        return fn.a((Node.OfInt) a(hi.c)).asPrimitiveArray();
    }

    /* compiled from: IntPipeline.java */
    /* loaded from: classes5.dex */
    public static class a<E_IN> extends eg<E_IN> {
        @Override // java8.util.stream.eg, java8.util.stream.a
        /* synthetic */ Spliterator<Integer> a(Supplier<? extends Spliterator<Integer>> supplier) {
            return eg.super.a(supplier);
        }

        @Override // java8.util.stream.eg, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Integer> iterator() {
            return eg.super.iterator();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) eg.super.parallel();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) eg.super.sequential();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Integer> spliterator() {
            return eg.super.spliterator2();
        }

        @Override // java8.util.stream.eg, java8.util.stream.BaseStream
        public /* synthetic */ IntStream unordered() {
            return eg.super.unordered();
        }

        public a(Supplier<? extends Spliterator<Integer>> supplier, int i, boolean z) {
            super(supplier, i, z);
        }

        public a(Spliterator<Integer> spliterator, int i, boolean z) {
            super(spliterator, i, z);
        }

        @Override // java8.util.stream.a
        final boolean f() {
            throw new UnsupportedOperationException();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // java8.util.stream.a
        public final Sink<E_IN> a(int i, Sink<Integer> sink) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.stream.eg, java8.util.stream.IntStream
        public void forEach(IntConsumer intConsumer) {
            if (!isParallel()) {
                eg.e(a()).forEachRemaining(intConsumer);
            } else {
                eg.super.forEach(intConsumer);
            }
        }

        @Override // java8.util.stream.eg, java8.util.stream.IntStream
        public void forEachOrdered(IntConsumer intConsumer) {
            if (!isParallel()) {
                eg.e(a()).forEachRemaining(intConsumer);
            } else {
                eg.super.forEachOrdered(intConsumer);
            }
        }
    }

    /* compiled from: IntPipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class c<E_IN> extends eg<E_IN> {
        @Override // java8.util.stream.a
        final boolean f() {
            return false;
        }

        @Override // java8.util.stream.eg, java8.util.stream.a
        /* synthetic */ Spliterator<Integer> a(Supplier<? extends Spliterator<Integer>> supplier) {
            return eg.super.a(supplier);
        }

        @Override // java8.util.stream.eg, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Integer> iterator() {
            return eg.super.iterator();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) eg.super.parallel();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) eg.super.sequential();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Integer> spliterator() {
            return eg.super.spliterator2();
        }

        @Override // java8.util.stream.eg, java8.util.stream.BaseStream
        public /* synthetic */ IntStream unordered() {
            return eg.super.unordered();
        }

        public c(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: IntPipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class b<E_IN> extends eg<E_IN> {
        @Override // java8.util.stream.a
        abstract <P_IN> Node<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator, IntFunction<Integer[]> intFunction);

        @Override // java8.util.stream.a
        final boolean f() {
            return true;
        }

        @Override // java8.util.stream.eg, java8.util.stream.a
        /* synthetic */ Spliterator<Integer> a(Supplier<? extends Spliterator<Integer>> supplier) {
            return eg.super.a(supplier);
        }

        @Override // java8.util.stream.eg, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Integer> iterator() {
            return eg.super.iterator();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) eg.super.parallel();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) eg.super.sequential();
        }

        @Override // java8.util.stream.eg, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Integer> spliterator() {
            return eg.super.spliterator2();
        }

        @Override // java8.util.stream.eg, java8.util.stream.BaseStream
        public /* synthetic */ IntStream unordered() {
            return eg.super.unordered();
        }

        public b(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }
}
