package java8.util.stream;

import java.util.Iterator;
import java8.util.LongSummaryStatistics;
import java8.util.Objects;
import java8.util.OptionalDouble;
import java8.util.OptionalLong;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.BiConsumer;
import java8.util.function.IntFunction;
import java8.util.function.LongBinaryOperator;
import java8.util.function.LongConsumer;
import java8.util.function.LongFunction;
import java8.util.function.LongPredicate;
import java8.util.function.LongToDoubleFunction;
import java8.util.function.LongToIntFunction;
import java8.util.function.LongUnaryOperator;
import java8.util.function.ObjLongConsumer;
import java8.util.function.Supplier;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.df;
import java8.util.stream.eg;
import java8.util.stream.fi;
import java8.util.stream.gd;
import java8.util.stream.gr;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LongPipeline.java */
/* loaded from: classes5.dex */
public abstract class eu<E_IN> extends a<E_IN, Long, LongStream> implements LongStream {
    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    public /* bridge */ /* synthetic */ LongStream parallel() {
        return (LongStream) super.parallel();
    }

    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    public /* bridge */ /* synthetic */ LongStream sequential() {
        return (LongStream) super.sequential();
    }

    eu(Supplier<? extends Spliterator<Long>> supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    eu(Spliterator<Long> spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    eu(a<?, E_IN, ?> aVar, int i) {
        super(aVar, i);
    }

    private static LongConsumer b(Sink<Long> sink) {
        if (sink instanceof LongConsumer) {
            return (LongConsumer) sink;
        }
        sink.getClass();
        return ev.a(sink);
    }

    public static Spliterator.OfLong e(Spliterator<Long> spliterator) {
        if (spliterator instanceof Spliterator.OfLong) {
            return (Spliterator.OfLong) spliterator;
        }
        throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.a
    public final gq e() {
        return gq.LONG_VALUE;
    }

    @Override // java8.util.stream.a
    final <P_IN> Node<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator, boolean z, IntFunction<Long[]> intFunction) {
        return fn.b(gbVar, spliterator, z);
    }

    @Override // java8.util.stream.a
    final <P_IN> Spliterator<Long> a(gb<Long> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
        return new gr.h(gbVar, supplier, z);
    }

    /* renamed from: b */
    public final Spliterator.OfLong a(Supplier<? extends Spliterator<Long>> supplier) {
        return new gr.c.C0361c(supplier);
    }

    @Override // java8.util.stream.a
    final boolean a(Spliterator<Long> spliterator, Sink<Long> sink) {
        boolean cancellationRequested;
        Spliterator.OfLong e = e(spliterator);
        LongConsumer b2 = b(sink);
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
    public final Node.Builder<Long> a(long j, IntFunction<Long[]> intFunction) {
        return fn.b(j);
    }

    private <U> Stream<U> a(final LongFunction<? extends U> longFunction, int i) {
        return new gd.c<Long, U>(this, gq.LONG_VALUE, i) { // from class: java8.util.stream.eu.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i2, Sink<U> sink) {
                return new Sink.ChainedLong<U>(sink) { // from class: java8.util.stream.eu.1.1
                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        this.downstream.accept((Sink) longFunction.apply(j));
                    }
                };
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java8.util.Spliterator$OfLong] */
    @Override // java8.util.stream.BaseStream
    public final Iterator<Long> iterator() {
        return Spliterators.iterator((Spliterator.OfLong) spliterator2());
    }

    @Override // java8.util.stream.a, java8.util.stream.BaseStream
    /* renamed from: spliterator */
    public final Spliterator<Long> spliterator2() {
        return e(super.spliterator());
    }

    @Override // java8.util.stream.LongStream
    public final DoubleStream asDoubleStream() {
        return new df.c<Long>(this, gq.LONG_VALUE, gp.m) { // from class: java8.util.stream.eu.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Double> sink) {
                return new Sink.ChainedLong<Double>(sink) { // from class: java8.util.stream.eu.2.1
                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        this.downstream.accept(j);
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.LongStream
    public final Stream<Long> boxed() {
        return a(ez.a(), 0);
    }

    @Override // java8.util.stream.LongStream
    public final LongStream map(final LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        return new c<Long>(this, gq.LONG_VALUE, gp.o | gp.m) { // from class: java8.util.stream.eu.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink) { // from class: java8.util.stream.eu.3.1
                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        this.downstream.accept(longUnaryOperator.applyAsLong(j));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.LongStream
    public final <U> Stream<U> mapToObj(LongFunction<? extends U> longFunction) {
        Objects.requireNonNull(longFunction);
        return a(longFunction, gp.o | gp.m);
    }

    @Override // java8.util.stream.LongStream
    public final IntStream mapToInt(final LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        return new eg.c<Long>(this, gq.LONG_VALUE, gp.o | gp.m) { // from class: java8.util.stream.eu.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedLong<Integer>(sink) { // from class: java8.util.stream.eu.4.1
                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        this.downstream.accept(longToIntFunction.applyAsInt(j));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.LongStream
    public final DoubleStream mapToDouble(final LongToDoubleFunction longToDoubleFunction) {
        Objects.requireNonNull(longToDoubleFunction);
        return new df.c<Long>(this, gq.LONG_VALUE, gp.o | gp.m) { // from class: java8.util.stream.eu.5
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Double> sink) {
                return new Sink.ChainedLong<Double>(sink) { // from class: java8.util.stream.eu.5.1
                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        this.downstream.accept(longToDoubleFunction.applyAsDouble(j));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.LongStream
    public final LongStream flatMap(final LongFunction<? extends LongStream> longFunction) {
        Objects.requireNonNull(longFunction);
        return new c<Long>(this, gq.LONG_VALUE, gp.o | gp.m | gp.s) { // from class: java8.util.stream.eu.6
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink) { // from class: java8.util.stream.eu.6.1
                    boolean a;
                    LongConsumer b;

                    {
                        AnonymousClass6.this = this;
                        Sink sink2 = this.downstream;
                        sink2.getClass();
                        this.b = fh.a(sink2);
                    }

                    @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    /* JADX WARN: Type inference failed for: r4v5, types: [java8.util.Spliterator$OfLong] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void accept(long r3) {
                        /*
                            r2 = this;
                            r0 = 0
                            java8.util.stream.eu$6 r1 = java8.util.stream.eu.AnonymousClass6.this     // Catch: all -> 0x003c
                            java8.util.function.LongFunction r1 = r5     // Catch: all -> 0x003c
                            java.lang.Object r3 = r1.apply(r3)     // Catch: all -> 0x003c
                            java8.util.stream.LongStream r3 = (java8.util.stream.LongStream) r3     // Catch: all -> 0x003c
                            if (r3 == 0) goto L_0x0036
                            boolean r4 = r2.a     // Catch: all -> 0x0034
                            if (r4 != 0) goto L_0x001b
                            java8.util.stream.LongStream r4 = r3.sequential()     // Catch: all -> 0x0034
                            java8.util.function.LongConsumer r0 = r2.b     // Catch: all -> 0x0034
                            r4.forEach(r0)     // Catch: all -> 0x0034
                            goto L_0x0036
                        L_0x001b:
                            java8.util.stream.LongStream r4 = r3.sequential()     // Catch: all -> 0x0034
                            java8.util.Spliterator$OfLong r4 = r4.spliterator()     // Catch: all -> 0x0034
                        L_0x0023:
                            java8.util.stream.Sink r0 = r2.downstream     // Catch: all -> 0x0034
                            boolean r0 = r0.cancellationRequested()     // Catch: all -> 0x0034
                            if (r0 != 0) goto L_0x0036
                            java8.util.function.LongConsumer r0 = r2.b     // Catch: all -> 0x0034
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
                        throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.eu.AnonymousClass6.AnonymousClass1.accept(long):void");
                    }

                    @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.a = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    /* renamed from: g */
    public LongStream unordered() {
        return !d() ? this : new c<Long>(this, gq.LONG_VALUE, gp.q) { // from class: java8.util.stream.eu.7
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Long> sink) {
                return sink;
            }
        };
    }

    @Override // java8.util.stream.LongStream
    public final LongStream filter(final LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new c<Long>(this, gq.LONG_VALUE, gp.s) { // from class: java8.util.stream.eu.8
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink) { // from class: java8.util.stream.eu.8.1
                    @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        if (longPredicate.test(j)) {
                            this.downstream.accept(j);
                        }
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.LongStream
    public final LongStream peek(final LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new c<Long>(this, gq.LONG_VALUE, 0) { // from class: java8.util.stream.eu.9
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<Long> a(int i, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink) { // from class: java8.util.stream.eu.9.1
                    @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                    public void accept(long j) {
                        longConsumer.accept(j);
                        this.downstream.accept(j);
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.LongStream
    public final LongStream limit(long j) {
        if (j >= 0) {
            return gk.c(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.LongStream
    public final LongStream skip(long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : gk.c(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.LongStream
    public final LongStream takeWhile(LongPredicate longPredicate) {
        return hi.a(this, longPredicate);
    }

    @Override // java8.util.stream.LongStream
    public final LongStream dropWhile(LongPredicate longPredicate) {
        return hi.b(this, longPredicate);
    }

    @Override // java8.util.stream.LongStream
    public final LongStream sorted() {
        return gl.c(this);
    }

    @Override // java8.util.stream.LongStream
    public final LongStream distinct() {
        return boxed().distinct().mapToLong(fa.a());
    }

    @Override // java8.util.stream.LongStream
    public void forEach(LongConsumer longConsumer) {
        a(ee.a(longConsumer, false));
    }

    @Override // java8.util.stream.LongStream
    public void forEachOrdered(LongConsumer longConsumer) {
        a(ee.a(longConsumer, true));
    }

    @Override // java8.util.stream.LongStream
    public final long sum() {
        return reduce(0L, fb.a());
    }

    @Override // java8.util.stream.LongStream
    public final OptionalLong min() {
        return reduce(fc.a());
    }

    @Override // java8.util.stream.LongStream
    public final OptionalLong max() {
        return reduce(fd.a());
    }

    public static /* synthetic */ long[] h() {
        return new long[2];
    }

    @Override // java8.util.stream.LongStream
    public final OptionalDouble average() {
        long[] jArr = (long[]) collect(fe.a(), ff.a(), fg.a());
        if (jArr[0] > 0) {
            return OptionalDouble.of(jArr[1] / jArr[0]);
        }
        return OptionalDouble.empty();
    }

    public static /* synthetic */ void a(long[] jArr, long j) {
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + j;
    }

    public static /* synthetic */ void a(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
    }

    @Override // java8.util.stream.LongStream
    public final long count() {
        return ((Long) a(gc.c())).longValue();
    }

    @Override // java8.util.stream.LongStream
    public final LongSummaryStatistics summaryStatistics() {
        return (LongSummaryStatistics) collect(Collectors.i, ew.a(), ex.a());
    }

    @Override // java8.util.stream.LongStream
    public final long reduce(long j, LongBinaryOperator longBinaryOperator) {
        return ((Long) a(gc.a(j, longBinaryOperator))).longValue();
    }

    @Override // java8.util.stream.LongStream
    public final OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
        return (OptionalLong) a(gc.a(longBinaryOperator));
    }

    @Override // java8.util.stream.LongStream
    public final <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> objLongConsumer, BiConsumer<R, R> biConsumer) {
        Objects.requireNonNull(biConsumer);
        return (R) a(gc.a(supplier, objLongConsumer, ey.a(biConsumer)));
    }

    @Override // java8.util.stream.LongStream
    public final boolean anyMatch(LongPredicate longPredicate) {
        return ((Boolean) a(fi.a(longPredicate, fi.f.ANY))).booleanValue();
    }

    @Override // java8.util.stream.LongStream
    public final boolean allMatch(LongPredicate longPredicate) {
        return ((Boolean) a(fi.a(longPredicate, fi.f.ALL))).booleanValue();
    }

    @Override // java8.util.stream.LongStream
    public final boolean noneMatch(LongPredicate longPredicate) {
        return ((Boolean) a(fi.a(longPredicate, fi.f.NONE))).booleanValue();
    }

    @Override // java8.util.stream.LongStream
    public final OptionalLong findFirst() {
        return (OptionalLong) a(dv.c(true));
    }

    @Override // java8.util.stream.LongStream
    public final OptionalLong findAny() {
        return (OptionalLong) a(dv.c(false));
    }

    @Override // java8.util.stream.LongStream
    public final long[] toArray() {
        return fn.a((Node.OfLong) a(hi.d)).asPrimitiveArray();
    }

    /* compiled from: LongPipeline.java */
    /* loaded from: classes5.dex */
    public static class a<E_IN> extends eu<E_IN> {
        @Override // java8.util.stream.eu, java8.util.stream.a
        /* synthetic */ Spliterator<Long> a(Supplier<? extends Spliterator<Long>> supplier) {
            return eu.super.a(supplier);
        }

        @Override // java8.util.stream.eu, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Long> iterator() {
            return eu.super.iterator();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) eu.super.parallel();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) eu.super.sequential();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Long> spliterator() {
            return eu.super.spliterator2();
        }

        @Override // java8.util.stream.eu, java8.util.stream.BaseStream
        public /* synthetic */ LongStream unordered() {
            return eu.super.unordered();
        }

        public a(Supplier<? extends Spliterator<Long>> supplier, int i, boolean z) {
            super(supplier, i, z);
        }

        public a(Spliterator<Long> spliterator, int i, boolean z) {
            super(spliterator, i, z);
        }

        @Override // java8.util.stream.a
        final boolean f() {
            throw new UnsupportedOperationException();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // java8.util.stream.a
        public final Sink<E_IN> a(int i, Sink<Long> sink) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.stream.eu, java8.util.stream.LongStream
        public void forEach(LongConsumer longConsumer) {
            if (!isParallel()) {
                eu.e(a()).forEachRemaining(longConsumer);
            } else {
                eu.super.forEach(longConsumer);
            }
        }

        @Override // java8.util.stream.eu, java8.util.stream.LongStream
        public void forEachOrdered(LongConsumer longConsumer) {
            if (!isParallel()) {
                eu.e(a()).forEachRemaining(longConsumer);
            } else {
                eu.super.forEachOrdered(longConsumer);
            }
        }
    }

    /* compiled from: LongPipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class c<E_IN> extends eu<E_IN> {
        @Override // java8.util.stream.a
        final boolean f() {
            return false;
        }

        @Override // java8.util.stream.eu, java8.util.stream.a
        /* synthetic */ Spliterator<Long> a(Supplier<? extends Spliterator<Long>> supplier) {
            return eu.super.a(supplier);
        }

        @Override // java8.util.stream.eu, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Long> iterator() {
            return eu.super.iterator();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) eu.super.parallel();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) eu.super.sequential();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Long> spliterator() {
            return eu.super.spliterator2();
        }

        @Override // java8.util.stream.eu, java8.util.stream.BaseStream
        public /* synthetic */ LongStream unordered() {
            return eu.super.unordered();
        }

        public c(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LongPipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class b<E_IN> extends eu<E_IN> {
        @Override // java8.util.stream.a
        abstract <P_IN> Node<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator, IntFunction<Long[]> intFunction);

        @Override // java8.util.stream.a
        final boolean f() {
            return true;
        }

        @Override // java8.util.stream.eu, java8.util.stream.a
        /* synthetic */ Spliterator<Long> a(Supplier<? extends Spliterator<Long>> supplier) {
            return eu.super.a(supplier);
        }

        @Override // java8.util.stream.eu, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Long> iterator() {
            return eu.super.iterator();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) eu.super.parallel();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) eu.super.sequential();
        }

        @Override // java8.util.stream.eu, java8.util.stream.a, java8.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator<Long> spliterator() {
            return eu.super.spliterator2();
        }

        @Override // java8.util.stream.eu, java8.util.stream.BaseStream
        public /* synthetic */ LongStream unordered() {
            return eu.super.unordered();
        }

        public b(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }
}
