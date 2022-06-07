package java8.util.stream;

import java.util.Comparator;
import java.util.Iterator;
import java8.util.Objects;
import java8.util.Optional;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;
import java8.util.function.BinaryOperator;
import java8.util.function.BinaryOperators;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.Function;
import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;
import java8.util.function.LongConsumer;
import java8.util.function.Predicate;
import java8.util.function.Supplier;
import java8.util.function.ToDoubleFunction;
import java8.util.function.ToIntFunction;
import java8.util.function.ToLongFunction;
import java8.util.stream.Collector;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.df;
import java8.util.stream.eg;
import java8.util.stream.eu;
import java8.util.stream.fi;
import java8.util.stream.gr;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ReferencePipeline.java */
/* loaded from: classes5.dex */
public abstract class gd<P_IN, P_OUT> extends a<P_IN, P_OUT, Stream<P_OUT>> implements Stream<P_OUT> {
    gd(Supplier<? extends Spliterator<?>> supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    gd(Spliterator<?> spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    gd(a<?, P_IN, ?> aVar, int i) {
        super(aVar, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.a
    public final gq e() {
        return gq.REFERENCE;
    }

    @Override // java8.util.stream.a
    final <P_IN_> Node<P_OUT> a(gb<P_OUT> gbVar, Spliterator<P_IN_> spliterator, boolean z, IntFunction<P_OUT[]> intFunction) {
        return fn.a(gbVar, spliterator, z, intFunction);
    }

    @Override // java8.util.stream.a
    final <P_IN_> Spliterator<P_OUT> a(gb<P_OUT> gbVar, Supplier<Spliterator<P_IN_>> supplier, boolean z) {
        return new gr.k(gbVar, supplier, z);
    }

    @Override // java8.util.stream.a
    final Spliterator<P_OUT> a(Supplier<? extends Spliterator<P_OUT>> supplier) {
        return new gr.c(supplier);
    }

    @Override // java8.util.stream.a
    final boolean a(Spliterator<P_OUT> spliterator, Sink<P_OUT> sink) {
        boolean cancellationRequested;
        do {
            cancellationRequested = sink.cancellationRequested();
            if (cancellationRequested) {
                break;
            }
        } while (spliterator.tryAdvance(sink));
        return cancellationRequested;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.a, java8.util.stream.gb
    public final Node.Builder<P_OUT> a(long j, IntFunction<P_OUT[]> intFunction) {
        return fn.a(j, intFunction);
    }

    @Override // java8.util.stream.BaseStream
    public final Iterator<P_OUT> iterator() {
        return Spliterators.iterator(spliterator());
    }

    /* renamed from: g */
    public Stream<P_OUT> unordered() {
        return !d() ? this : new c<P_OUT, P_OUT>(this, gq.REFERENCE, gp.q) { // from class: java8.util.stream.gd.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<P_OUT> sink) {
                return sink;
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> filter(final Predicate<? super P_OUT> predicate) {
        Objects.requireNonNull(predicate);
        return new c<P_OUT, P_OUT>(this, gq.REFERENCE, gp.s) { // from class: java8.util.stream.gd.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<P_OUT> sink) {
                return new Sink.ChainedReference<P_OUT, P_OUT>(sink) { // from class: java8.util.stream.gd.4.1
                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.function.Consumer
                    public void accept(P_OUT p_out) {
                        if (predicate.test(p_out)) {
                            this.downstream.accept((Sink) p_out);
                        }
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final <R> Stream<R> map(final Function<? super P_OUT, ? extends R> function) {
        Objects.requireNonNull(function);
        return new c<P_OUT, R>(this, gq.REFERENCE, gp.o | gp.m) { // from class: java8.util.stream.gd.5
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<R> sink) {
                return new Sink.ChainedReference<P_OUT, R>(sink) { // from class: java8.util.stream.gd.5.1
                    @Override // java8.util.function.Consumer
                    public void accept(P_OUT p_out) {
                        this.downstream.accept((Sink) function.apply(p_out));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final IntStream mapToInt(final ToIntFunction<? super P_OUT> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return new eg.c<P_OUT>(this, gq.REFERENCE, gp.o | gp.m) { // from class: java8.util.stream.gd.6
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedReference<P_OUT, Integer>(sink) { // from class: java8.util.stream.gd.6.1
                    @Override // java8.util.function.Consumer
                    public void accept(P_OUT p_out) {
                        this.downstream.accept(toIntFunction.applyAsInt(p_out));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final LongStream mapToLong(final ToLongFunction<? super P_OUT> toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return new eu.c<P_OUT>(this, gq.REFERENCE, gp.o | gp.m) { // from class: java8.util.stream.gd.7
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<Long> sink) {
                return new Sink.ChainedReference<P_OUT, Long>(sink) { // from class: java8.util.stream.gd.7.1
                    @Override // java8.util.function.Consumer
                    public void accept(P_OUT p_out) {
                        this.downstream.accept(toLongFunction.applyAsLong(p_out));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final DoubleStream mapToDouble(final ToDoubleFunction<? super P_OUT> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return new df.c<P_OUT>(this, gq.REFERENCE, gp.o | gp.m) { // from class: java8.util.stream.gd.8
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<Double> sink) {
                return new Sink.ChainedReference<P_OUT, Double>(sink) { // from class: java8.util.stream.gd.8.1
                    @Override // java8.util.function.Consumer
                    public void accept(P_OUT p_out) {
                        this.downstream.accept(toDoubleFunction.applyAsDouble(p_out));
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final <R> Stream<R> flatMap(final Function<? super P_OUT, ? extends Stream<? extends R>> function) {
        Objects.requireNonNull(function);
        return new c<P_OUT, R>(this, gq.REFERENCE, gp.o | gp.m | gp.s) { // from class: java8.util.stream.gd.9
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<R> sink) {
                return new Sink.ChainedReference<P_OUT, R>(sink) { // from class: java8.util.stream.gd.9.1
                    boolean a;

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.function.Consumer
                    public void accept(P_OUT p_out) {
                        Stream stream;
                        Throwable th;
                        try {
                            stream = (Stream) function.apply(p_out);
                            if (stream != null) {
                                try {
                                    if (!this.a) {
                                        stream.sequential().forEach(this.downstream);
                                    } else {
                                        Spliterator<T> spliterator = stream.sequential().spliterator();
                                        while (!this.downstream.cancellationRequested() && spliterator.tryAdvance(this.downstream)) {
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (stream != null) {
                                        stream.close();
                                    }
                                    throw th;
                                }
                            }
                            if (stream != null) {
                                stream.close();
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            stream = null;
                        }
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.a = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final IntStream flatMapToInt(final Function<? super P_OUT, ? extends IntStream> function) {
        Objects.requireNonNull(function);
        return new eg.c<P_OUT>(this, gq.REFERENCE, gp.o | gp.m | gp.s) { // from class: java8.util.stream.gd.10
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<Integer> sink) {
                return new Sink.ChainedReference<P_OUT, Integer>(sink) { // from class: java8.util.stream.gd.10.1
                    boolean a;
                    IntConsumer b;

                    {
                        AnonymousClass10.this = this;
                        Sink sink2 = this.downstream;
                        sink2.getClass();
                        this.b = gh.a(sink2);
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    /* JADX WARN: Type inference failed for: r0v6, types: [java8.util.Spliterator$OfInt] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // java8.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void accept(P_OUT r4) {
                        /*
                            r3 = this;
                            r0 = 0
                            java8.util.stream.gd$10 r1 = java8.util.stream.gd.AnonymousClass10.this     // Catch: all -> 0x003c
                            java8.util.function.Function r1 = r5     // Catch: all -> 0x003c
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
                        throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.gd.AnonymousClass10.AnonymousClass1.accept(java.lang.Object):void");
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.a = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final DoubleStream flatMapToDouble(final Function<? super P_OUT, ? extends DoubleStream> function) {
        Objects.requireNonNull(function);
        return new df.c<P_OUT>(this, gq.REFERENCE, gp.o | gp.m | gp.s) { // from class: java8.util.stream.gd.11
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<Double> sink) {
                return new Sink.ChainedReference<P_OUT, Double>(sink) { // from class: java8.util.stream.gd.11.1
                    boolean a;
                    DoubleConsumer b;

                    {
                        AnonymousClass11.this = this;
                        Sink sink2 = this.downstream;
                        sink2.getClass();
                        this.b = gi.a(sink2);
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    /* JADX WARN: Type inference failed for: r0v6, types: [java8.util.Spliterator$OfDouble] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // java8.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void accept(P_OUT r4) {
                        /*
                            r3 = this;
                            r0 = 0
                            java8.util.stream.gd$11 r1 = java8.util.stream.gd.AnonymousClass11.this     // Catch: all -> 0x003c
                            java8.util.function.Function r1 = r5     // Catch: all -> 0x003c
                            java.lang.Object r4 = r1.apply(r4)     // Catch: all -> 0x003c
                            java8.util.stream.DoubleStream r4 = (java8.util.stream.DoubleStream) r4     // Catch: all -> 0x003c
                            if (r4 == 0) goto L_0x0036
                            boolean r0 = r3.a     // Catch: all -> 0x0034
                            if (r0 != 0) goto L_0x001b
                            java8.util.stream.DoubleStream r0 = r4.sequential()     // Catch: all -> 0x0034
                            java8.util.function.DoubleConsumer r1 = r3.b     // Catch: all -> 0x0034
                            r0.forEach(r1)     // Catch: all -> 0x0034
                            goto L_0x0036
                        L_0x001b:
                            java8.util.stream.DoubleStream r0 = r4.sequential()     // Catch: all -> 0x0034
                            java8.util.Spliterator$OfDouble r0 = r0.spliterator()     // Catch: all -> 0x0034
                        L_0x0023:
                            java8.util.stream.Sink r1 = r3.downstream     // Catch: all -> 0x0034
                            boolean r1 = r1.cancellationRequested()     // Catch: all -> 0x0034
                            if (r1 != 0) goto L_0x0036
                            java8.util.function.DoubleConsumer r1 = r3.b     // Catch: all -> 0x0034
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
                        throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.gd.AnonymousClass11.AnonymousClass1.accept(java.lang.Object):void");
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.a = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final LongStream flatMapToLong(final Function<? super P_OUT, ? extends LongStream> function) {
        Objects.requireNonNull(function);
        return new eu.c<P_OUT>(this, gq.REFERENCE, gp.o | gp.m | gp.s) { // from class: java8.util.stream.gd.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<Long> sink) {
                return new Sink.ChainedReference<P_OUT, Long>(sink) { // from class: java8.util.stream.gd.2.1
                    boolean a;
                    LongConsumer b;

                    {
                        AnonymousClass2.this = this;
                        Sink sink2 = this.downstream;
                        sink2.getClass();
                        this.b = gg.a(sink2);
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    /* JADX WARN: Type inference failed for: r0v6, types: [java8.util.Spliterator$OfLong] */
                    /* JADX WARN: Unknown variable types count: 1 */
                    @Override // java8.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void accept(P_OUT r4) {
                        /*
                            r3 = this;
                            r0 = 0
                            java8.util.stream.gd$2 r1 = java8.util.stream.gd.AnonymousClass2.this     // Catch: all -> 0x003c
                            java8.util.function.Function r1 = r5     // Catch: all -> 0x003c
                            java.lang.Object r4 = r1.apply(r4)     // Catch: all -> 0x003c
                            java8.util.stream.LongStream r4 = (java8.util.stream.LongStream) r4     // Catch: all -> 0x003c
                            if (r4 == 0) goto L_0x0036
                            boolean r0 = r3.a     // Catch: all -> 0x0034
                            if (r0 != 0) goto L_0x001b
                            java8.util.stream.LongStream r0 = r4.sequential()     // Catch: all -> 0x0034
                            java8.util.function.LongConsumer r1 = r3.b     // Catch: all -> 0x0034
                            r0.forEach(r1)     // Catch: all -> 0x0034
                            goto L_0x0036
                        L_0x001b:
                            java8.util.stream.LongStream r0 = r4.sequential()     // Catch: all -> 0x0034
                            java8.util.Spliterator$OfLong r0 = r0.spliterator()     // Catch: all -> 0x0034
                        L_0x0023:
                            java8.util.stream.Sink r1 = r3.downstream     // Catch: all -> 0x0034
                            boolean r1 = r1.cancellationRequested()     // Catch: all -> 0x0034
                            if (r1 != 0) goto L_0x0036
                            java8.util.function.LongConsumer r1 = r3.b     // Catch: all -> 0x0034
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
                        throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.gd.AnonymousClass2.AnonymousClass1.accept(java.lang.Object):void");
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.a = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> peek(final Consumer<? super P_OUT> consumer) {
        Objects.requireNonNull(consumer);
        return new c<P_OUT, P_OUT>(this, gq.REFERENCE, 0) { // from class: java8.util.stream.gd.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // java8.util.stream.a
            public Sink<P_OUT> a(int i, Sink<P_OUT> sink) {
                return new Sink.ChainedReference<P_OUT, P_OUT>(sink) { // from class: java8.util.stream.gd.3.1
                    @Override // java8.util.function.Consumer
                    public void accept(P_OUT p_out) {
                        consumer.accept(p_out);
                        this.downstream.accept((Sink) p_out);
                    }
                };
            }
        };
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> distinct() {
        return da.a(this);
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> sorted() {
        return gl.a(this);
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> sorted(Comparator<? super P_OUT> comparator) {
        return gl.a(this, comparator);
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> limit(long j) {
        if (j >= 0) {
            return gk.a(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> skip(long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : gk.a(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> takeWhile(Predicate<? super P_OUT> predicate) {
        return hi.a(this, predicate);
    }

    @Override // java8.util.stream.Stream
    public final Stream<P_OUT> dropWhile(Predicate<? super P_OUT> predicate) {
        return hi.b(this, predicate);
    }

    @Override // java8.util.stream.Stream
    public void forEach(Consumer<? super P_OUT> consumer) {
        a(ee.a((Consumer) consumer, false));
    }

    @Override // java8.util.stream.Stream
    public void forEachOrdered(Consumer<? super P_OUT> consumer) {
        a(ee.a((Consumer) consumer, true));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.stream.Stream
    public final <A> A[] toArray(IntFunction<A[]> intFunction) {
        return (A[]) fn.a(a(intFunction), intFunction).asArray(intFunction);
    }

    public static /* synthetic */ Object[] b(int i) {
        return new Object[i];
    }

    @Override // java8.util.stream.Stream
    public final Object[] toArray() {
        return toArray(ge.a());
    }

    @Override // java8.util.stream.Stream
    public final boolean anyMatch(Predicate<? super P_OUT> predicate) {
        return ((Boolean) a(fi.a(predicate, fi.f.ANY))).booleanValue();
    }

    @Override // java8.util.stream.Stream
    public final boolean allMatch(Predicate<? super P_OUT> predicate) {
        return ((Boolean) a(fi.a(predicate, fi.f.ALL))).booleanValue();
    }

    @Override // java8.util.stream.Stream
    public final boolean noneMatch(Predicate<? super P_OUT> predicate) {
        return ((Boolean) a(fi.a(predicate, fi.f.NONE))).booleanValue();
    }

    @Override // java8.util.stream.Stream
    public final Optional<P_OUT> findFirst() {
        return (Optional) a(dv.a(true));
    }

    @Override // java8.util.stream.Stream
    public final Optional<P_OUT> findAny() {
        return (Optional) a(dv.a(false));
    }

    @Override // java8.util.stream.Stream
    public final P_OUT reduce(P_OUT p_out, BinaryOperator<P_OUT> binaryOperator) {
        return (P_OUT) a(gc.a(p_out, binaryOperator, binaryOperator));
    }

    @Override // java8.util.stream.Stream
    public final Optional<P_OUT> reduce(BinaryOperator<P_OUT> binaryOperator) {
        return (Optional) a(gc.a(binaryOperator));
    }

    @Override // java8.util.stream.Stream
    public final <R> R reduce(R r, BiFunction<R, ? super P_OUT, R> biFunction, BinaryOperator<R> binaryOperator) {
        return (R) a(gc.a(r, (BiFunction<R, ? super T, R>) biFunction, binaryOperator));
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Object] */
    @Override // java8.util.stream.Stream
    public final <R, A> R collect(Collector<? super P_OUT, A, R> collector) {
        A a2;
        if (!isParallel() || !collector.characteristics().contains(Collector.Characteristics.CONCURRENT) || (d() && !collector.characteristics().contains(Collector.Characteristics.UNORDERED))) {
            a2 = (R) a(gc.a(collector));
        } else {
            a2 = collector.supplier().get();
            forEach(gf.a(collector.accumulator(), a2));
        }
        return collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH) ? (R) a2 : collector.finisher().apply(a2);
    }

    @Override // java8.util.stream.Stream
    public final <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super P_OUT> biConsumer, BiConsumer<R, R> biConsumer2) {
        return (R) a(gc.a(supplier, biConsumer, biConsumer2));
    }

    @Override // java8.util.stream.Stream
    public final Optional<P_OUT> max(Comparator<? super P_OUT> comparator) {
        return reduce(BinaryOperators.maxBy(comparator));
    }

    @Override // java8.util.stream.Stream
    public final Optional<P_OUT> min(Comparator<? super P_OUT> comparator) {
        return reduce(BinaryOperators.minBy(comparator));
    }

    @Override // java8.util.stream.Stream
    public final long count() {
        return ((Long) a(gc.a())).longValue();
    }

    /* compiled from: ReferencePipeline.java */
    /* loaded from: classes5.dex */
    public static class a<E_IN, E_OUT> extends gd<E_IN, E_OUT> {
        @Override // java8.util.stream.gd, java8.util.stream.BaseStream
        public /* synthetic */ BaseStream unordered() {
            return gd.super.unordered();
        }

        public a(Supplier<? extends Spliterator<?>> supplier, int i, boolean z) {
            super(supplier, i, z);
        }

        public a(Spliterator<?> spliterator, int i, boolean z) {
            super(spliterator, i, z);
        }

        @Override // java8.util.stream.a
        final boolean f() {
            throw new UnsupportedOperationException();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // java8.util.stream.a
        public final Sink<E_IN> a(int i, Sink<E_OUT> sink) {
            throw new UnsupportedOperationException();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.stream.gd, java8.util.stream.Stream
        public void forEach(Consumer<? super E_OUT> consumer) {
            if (!isParallel()) {
                a().forEachRemaining(consumer);
            } else {
                gd.super.forEach(consumer);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.stream.gd, java8.util.stream.Stream
        public void forEachOrdered(Consumer<? super E_OUT> consumer) {
            if (!isParallel()) {
                a().forEachRemaining(consumer);
            } else {
                gd.super.forEachOrdered(consumer);
            }
        }
    }

    /* compiled from: ReferencePipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class c<E_IN, E_OUT> extends gd<E_IN, E_OUT> {
        @Override // java8.util.stream.a
        final boolean f() {
            return false;
        }

        @Override // java8.util.stream.gd, java8.util.stream.BaseStream
        public /* synthetic */ BaseStream unordered() {
            return gd.super.unordered();
        }

        public c(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ReferencePipeline.java */
    /* loaded from: classes5.dex */
    public static abstract class b<E_IN, E_OUT> extends gd<E_IN, E_OUT> {
        @Override // java8.util.stream.a
        abstract <P_IN> Node<E_OUT> a(gb<E_OUT> gbVar, Spliterator<P_IN> spliterator, IntFunction<E_OUT[]> intFunction);

        @Override // java8.util.stream.a
        final boolean f() {
            return true;
        }

        @Override // java8.util.stream.gd, java8.util.stream.BaseStream
        public /* synthetic */ BaseStream unordered() {
            return gd.super.unordered();
        }

        public b(a<?, E_IN, ?> aVar, gq gqVar, int i) {
            super(aVar, i);
        }
    }
}
