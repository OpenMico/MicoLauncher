package java8.util.stream;

import java8.util.Spliterator;
import java8.util.concurrent.CountedCompleter;
import java8.util.function.IntFunction;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.df;
import java8.util.stream.eg;
import java8.util.stream.eu;
import java8.util.stream.gd;
import java8.util.stream.gr;

/* compiled from: SliceOps.java */
/* loaded from: classes5.dex */
final class gk {
    /* JADX INFO: Access modifiers changed from: private */
    public static long b(long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        if (j3 >= 0) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long b(long j, long j2, long j3) {
        if (j >= 0) {
            return Math.max(-1L, Math.min(j - j2, j3));
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <P_IN> Spliterator<P_IN> b(gq gqVar, Spliterator<P_IN> spliterator, long j, long j2) {
        long b = b(j, j2);
        switch (gqVar) {
            case REFERENCE:
                return new gr.i.e(spliterator, j, b);
            case INT_VALUE:
                return new gr.i.b((Spliterator.OfInt) spliterator, j, b);
            case LONG_VALUE:
                return new gr.i.c((Spliterator.OfLong) spliterator, j, b);
            case DOUBLE_VALUE:
                return new gr.i.a((Spliterator.OfDouble) spliterator, j, b);
            default:
                throw new IllegalStateException("Unknown shape " + gqVar);
        }
    }

    public static <T> Stream<T> a(a<?, T, ?> aVar, final long j, final long j2) {
        if (j >= 0) {
            return new gd.b<T, T>(aVar, gq.REFERENCE, a(j2)) { // from class: java8.util.stream.gk.1
                Spliterator<T> a(Spliterator<T> spliterator, long j3, long j4, long j5) {
                    long j6;
                    long j7 = 0;
                    if (j3 <= j5) {
                        j6 = j4 >= 0 ? Math.min(j4, j5 - j3) : j5 - j3;
                    } else {
                        j7 = j3;
                        j6 = j4;
                    }
                    return new gr.j.e(spliterator, j7, j6);
                }

                @Override // java8.util.stream.a
                <P_IN> Spliterator<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        Spliterator b = gbVar.b(spliterator);
                        long j3 = j;
                        return new gr.i.e(b, j3, gk.b(j3, j2));
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return a(gbVar.b(spliterator), j, j2, a2);
                    }
                    return new a(this, gbVar, spliterator, fn.d(), j, j2).invoke().f_();
                }

                @Override // java8.util.stream.gd.b, java8.util.stream.a
                <P_IN> Node<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator, IntFunction<T[]> intFunction) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        return fn.a((gb) gbVar, gk.b(gbVar.b(), spliterator, j, j2), true, (IntFunction) intFunction);
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return fn.a((gb) this, a(gbVar.b(spliterator), j, j2, a2), true, (IntFunction) intFunction);
                    }
                    return (Node) new a(this, gbVar, spliterator, intFunction, j, j2).invoke();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // java8.util.stream.a
                public Sink<T> a(int i, Sink<T> sink) {
                    return new Sink.ChainedReference<T, T>(sink) { // from class: java8.util.stream.gk.1.1
                        long a;
                        long b;

                        {
                            this.a = j;
                            this.b = j2 >= 0 ? j2 : Long.MAX_VALUE;
                        }

                        @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                        public void begin(long j3) {
                            this.downstream.begin(gk.b(j3, j, this.b));
                        }

                        @Override // java8.util.function.Consumer
                        public void accept(T t) {
                            long j3 = this.a;
                            if (j3 == 0) {
                                long j4 = this.b;
                                if (j4 > 0) {
                                    this.b = j4 - 1;
                                    this.downstream.accept((Sink) t);
                                    return;
                                }
                                return;
                            }
                            this.a = j3 - 1;
                        }

                        @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                        public boolean cancellationRequested() {
                            return this.b == 0 || this.downstream.cancellationRequested();
                        }
                    };
                }
            };
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static IntStream b(a<?, Integer, ?> aVar, final long j, final long j2) {
        if (j >= 0) {
            return new eg.b<Integer>(aVar, gq.INT_VALUE, a(j2)) { // from class: java8.util.stream.gk.2
                Spliterator.OfInt a(Spliterator.OfInt ofInt, long j3, long j4, long j5) {
                    long j6;
                    long j7 = 0;
                    if (j3 <= j5) {
                        j6 = j4 >= 0 ? Math.min(j4, j5 - j3) : j5 - j3;
                    } else {
                        j7 = j3;
                        j6 = j4;
                    }
                    return new gr.j.b(ofInt, j7, j6);
                }

                @Override // java8.util.stream.a
                <P_IN> Spliterator<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        long j3 = j;
                        return new gr.i.b((Spliterator.OfInt) gbVar.b(spliterator), j3, gk.b(j3, j2));
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return a((Spliterator.OfInt) gbVar.b(spliterator), j, j2, a2);
                    }
                    return new a(this, gbVar, spliterator, hi.c, j, j2).invoke().f_();
                }

                @Override // java8.util.stream.eg.b, java8.util.stream.a
                <P_IN> Node<Integer> a(gb<Integer> gbVar, Spliterator<P_IN> spliterator, IntFunction<Integer[]> intFunction) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        return fn.a(gbVar, gk.b(gbVar.b(), spliterator, j, j2), true);
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return fn.a((gb<Integer>) this, (Spliterator) a((Spliterator.OfInt) gbVar.b(spliterator), j, j2, a2), true);
                    }
                    return (Node) new a(this, gbVar, spliterator, intFunction, j, j2).invoke();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // java8.util.stream.a
                public Sink<Integer> a(int i, Sink<Integer> sink) {
                    return new Sink.ChainedInt<Integer>(sink) { // from class: java8.util.stream.gk.2.1
                        long a;
                        long b;

                        {
                            this.a = j;
                            this.b = j2 >= 0 ? j2 : Long.MAX_VALUE;
                        }

                        @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
                        public void begin(long j3) {
                            this.downstream.begin(gk.b(j3, j, this.b));
                        }

                        @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                        public void accept(int i2) {
                            long j3 = this.a;
                            if (j3 == 0) {
                                long j4 = this.b;
                                if (j4 > 0) {
                                    this.b = j4 - 1;
                                    this.downstream.accept(i2);
                                    return;
                                }
                                return;
                            }
                            this.a = j3 - 1;
                        }

                        @Override // java8.util.stream.Sink.ChainedInt, java8.util.stream.Sink
                        public boolean cancellationRequested() {
                            return this.b == 0 || this.downstream.cancellationRequested();
                        }
                    };
                }
            };
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static LongStream c(a<?, Long, ?> aVar, final long j, final long j2) {
        if (j >= 0) {
            return new eu.b<Long>(aVar, gq.LONG_VALUE, a(j2)) { // from class: java8.util.stream.gk.3
                Spliterator.OfLong a(Spliterator.OfLong ofLong, long j3, long j4, long j5) {
                    long j6;
                    long j7 = 0;
                    if (j3 <= j5) {
                        j6 = j4 >= 0 ? Math.min(j4, j5 - j3) : j5 - j3;
                    } else {
                        j7 = j3;
                        j6 = j4;
                    }
                    return new gr.j.c(ofLong, j7, j6);
                }

                @Override // java8.util.stream.a
                <P_IN> Spliterator<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        long j3 = j;
                        return new gr.i.c((Spliterator.OfLong) gbVar.b(spliterator), j3, gk.b(j3, j2));
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return a((Spliterator.OfLong) gbVar.b(spliterator), j, j2, a2);
                    }
                    return new a(this, gbVar, spliterator, hi.d, j, j2).invoke().f_();
                }

                @Override // java8.util.stream.eu.b, java8.util.stream.a
                <P_IN> Node<Long> a(gb<Long> gbVar, Spliterator<P_IN> spliterator, IntFunction<Long[]> intFunction) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        return fn.b(gbVar, gk.b(gbVar.b(), spliterator, j, j2), true);
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return fn.b(this, a((Spliterator.OfLong) gbVar.b(spliterator), j, j2, a2), true);
                    }
                    return (Node) new a(this, gbVar, spliterator, intFunction, j, j2).invoke();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // java8.util.stream.a
                public Sink<Long> a(int i, Sink<Long> sink) {
                    return new Sink.ChainedLong<Long>(sink) { // from class: java8.util.stream.gk.3.1
                        long a;
                        long b;

                        {
                            this.a = j;
                            this.b = j2 >= 0 ? j2 : Long.MAX_VALUE;
                        }

                        @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
                        public void begin(long j3) {
                            this.downstream.begin(gk.b(j3, j, this.b));
                        }

                        @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                        public void accept(long j3) {
                            long j4 = this.a;
                            if (j4 == 0) {
                                long j5 = this.b;
                                if (j5 > 0) {
                                    this.b = j5 - 1;
                                    this.downstream.accept(j3);
                                    return;
                                }
                                return;
                            }
                            this.a = j4 - 1;
                        }

                        @Override // java8.util.stream.Sink.ChainedLong, java8.util.stream.Sink
                        public boolean cancellationRequested() {
                            return this.b == 0 || this.downstream.cancellationRequested();
                        }
                    };
                }
            };
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static DoubleStream d(a<?, Double, ?> aVar, final long j, final long j2) {
        if (j >= 0) {
            return new df.b<Double>(aVar, gq.DOUBLE_VALUE, a(j2)) { // from class: java8.util.stream.gk.4
                Spliterator.OfDouble a(Spliterator.OfDouble ofDouble, long j3, long j4, long j5) {
                    long j6;
                    long j7 = 0;
                    if (j3 <= j5) {
                        j6 = j4 >= 0 ? Math.min(j4, j5 - j3) : j5 - j3;
                    } else {
                        j7 = j3;
                        j6 = j4;
                    }
                    return new gr.j.a(ofDouble, j7, j6);
                }

                @Override // java8.util.stream.a
                <P_IN> Spliterator<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        long j3 = j;
                        return new gr.i.a((Spliterator.OfDouble) gbVar.b(spliterator), j3, gk.b(j3, j2));
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return a((Spliterator.OfDouble) gbVar.b(spliterator), j, j2, a2);
                    }
                    return new a(this, gbVar, spliterator, hi.e, j, j2).invoke().f_();
                }

                @Override // java8.util.stream.df.b, java8.util.stream.a
                <P_IN> Node<Double> a(gb<Double> gbVar, Spliterator<P_IN> spliterator, IntFunction<Double[]> intFunction) {
                    long a2 = gbVar.a(spliterator);
                    if (a2 > 0 && spliterator.hasCharacteristics(16384)) {
                        return fn.c(gbVar, gk.b(gbVar.b(), spliterator, j, j2), true);
                    }
                    if (!gp.ORDERED.a(gbVar.c())) {
                        return fn.c(this, a((Spliterator.OfDouble) gbVar.b(spliterator), j, j2, a2), true);
                    }
                    return (Node) new a(this, gbVar, spliterator, intFunction, j, j2).invoke();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // java8.util.stream.a
                public Sink<Double> a(int i, Sink<Double> sink) {
                    return new Sink.ChainedDouble<Double>(sink) { // from class: java8.util.stream.gk.4.1
                        long a;
                        long b;

                        {
                            this.a = j;
                            this.b = j2 >= 0 ? j2 : Long.MAX_VALUE;
                        }

                        @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
                        public void begin(long j3) {
                            this.downstream.begin(gk.b(j3, j, this.b));
                        }

                        @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                        public void accept(double d) {
                            long j3 = this.a;
                            if (j3 == 0) {
                                long j4 = this.b;
                                if (j4 > 0) {
                                    this.b = j4 - 1;
                                    this.downstream.accept(d);
                                    return;
                                }
                                return;
                            }
                            this.a = j3 - 1;
                        }

                        @Override // java8.util.stream.Sink.ChainedDouble, java8.util.stream.Sink
                        public boolean cancellationRequested() {
                            return this.b == 0 || this.downstream.cancellationRequested();
                        }
                    };
                }
            };
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    private static int a(long j) {
        return (j != -1 ? gp.t : 0) | gp.s;
    }

    /* compiled from: SliceOps.java */
    /* loaded from: classes5.dex */
    private static final class a<P_IN, P_OUT> extends e<P_IN, P_OUT, Node<P_OUT>, a<P_IN, P_OUT>> {
        private volatile boolean completed;
        private final IntFunction<P_OUT[]> generator;
        private final a<P_OUT, P_OUT, ?> op;
        private final long targetOffset;
        private final long targetSize;
        private long thisNodeSize;

        a(a<P_OUT, P_OUT, ?> aVar, gb<P_OUT> gbVar, Spliterator<P_IN> spliterator, IntFunction<P_OUT[]> intFunction, long j, long j2) {
            super(gbVar, spliterator);
            this.op = aVar;
            this.generator = intFunction;
            this.targetOffset = j;
            this.targetSize = j2;
        }

        a(a<P_IN, P_OUT> aVar, Spliterator<P_IN> spliterator) {
            super(aVar, spliterator);
            this.op = aVar.op;
            this.generator = aVar.generator;
            this.targetOffset = aVar.targetOffset;
            this.targetSize = aVar.targetSize;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public a<P_IN, P_OUT> a(Spliterator<P_IN> spliterator) {
            return new a<>(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: n */
        public final Node<P_OUT> a() {
            return fn.a(this.op.e());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: o */
        public final Node<P_OUT> i() {
            long j = -1;
            if (k()) {
                if (gp.SIZED.b(this.op.a)) {
                    j = this.op.a(this.spliterator);
                }
                Node.Builder<P_OUT> a = this.op.a(j, this.generator);
                this.helper.c(this.helper.a((Sink) this.op.a(this.helper.c(), a)), this.spliterator);
                return a.build();
            }
            Node.Builder<P_OUT> a2 = this.op.a(-1L, this.generator);
            if (this.targetOffset == 0) {
                this.helper.c(this.helper.a((Sink) this.op.a(this.helper.c(), a2)), this.spliterator);
            } else {
                this.helper.a((gb) a2, (Spliterator) this.spliterator);
            }
            Node<P_OUT> build = a2.build();
            this.thisNodeSize = build.a();
            this.completed = true;
            this.spliterator = null;
            return build;
        }

        @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter<?> countedCompleter) {
            Node<P_OUT> node;
            if (!j()) {
                this.thisNodeSize = ((a) this.leftChild).thisNodeSize + ((a) this.rightChild).thisNodeSize;
                if (this.canceled) {
                    this.thisNodeSize = 0L;
                    node = a();
                } else if (this.thisNodeSize == 0) {
                    node = a();
                } else if (((a) this.leftChild).thisNodeSize == 0) {
                    node = ((a) this.rightChild).b();
                } else {
                    node = fn.a(this.op.e(), ((a) this.leftChild).b(), ((a) this.rightChild).b());
                }
                if (k()) {
                    node = a((Node) node);
                }
                b((a<P_IN, P_OUT>) node);
                this.completed = true;
            }
            if (this.targetSize >= 0 && !k() && d(this.targetOffset + this.targetSize)) {
                g();
            }
            super.onCompletion(countedCompleter);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java8.util.stream.e
        public void c() {
            super.c();
            if (this.completed) {
                b((a<P_IN, P_OUT>) a());
            }
        }

        private Node<P_OUT> a(Node<P_OUT> node) {
            return node.a(this.targetOffset, this.targetSize >= 0 ? Math.min(node.a(), this.targetOffset + this.targetSize) : this.thisNodeSize, this.generator);
        }

        private boolean d(long j) {
            a aVar;
            long e = this.completed ? this.thisNodeSize : e(j);
            if (e >= j) {
                return true;
            }
            a<P_IN, P_OUT> aVar2 = (a) l();
            a<P_IN, P_OUT> aVar3 = this;
            while (aVar2 != null) {
                if (aVar3 == aVar2.rightChild && (aVar = (a) aVar2.leftChild) != null) {
                    e += aVar.e(j);
                    if (e >= j) {
                        return true;
                    }
                }
                aVar2 = (a) aVar2.l();
                aVar3 = aVar2;
            }
            return e >= j;
        }

        private long e(long j) {
            if (this.completed) {
                return this.thisNodeSize;
            }
            a aVar = (a) this.leftChild;
            a aVar2 = (a) this.rightChild;
            if (aVar == null || aVar2 == null) {
                return this.thisNodeSize;
            }
            long e = aVar.e(j);
            return e >= j ? e : e + aVar2.e(j);
        }
    }
}
