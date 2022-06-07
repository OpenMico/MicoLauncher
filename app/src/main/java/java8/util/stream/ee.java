package java8.util.stream;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.concurrent.CountedCompleter;
import java8.util.concurrent.ForkJoinPool;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;
import java8.util.function.LongConsumer;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.gj;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ForEachOps.java */
/* loaded from: classes5.dex */
public final class ee {
    public static <T> hg<T, Void> a(Consumer<? super T> consumer, boolean z) {
        Objects.requireNonNull(consumer);
        return new a.d(consumer, z);
    }

    public static hg<Integer, Void> a(IntConsumer intConsumer, boolean z) {
        Objects.requireNonNull(intConsumer);
        return new a.b(intConsumer, z);
    }

    public static hg<Long, Void> a(LongConsumer longConsumer, boolean z) {
        Objects.requireNonNull(longConsumer);
        return new a.c(longConsumer, z);
    }

    public static hg<Double, Void> a(DoubleConsumer doubleConsumer, boolean z) {
        Objects.requireNonNull(doubleConsumer);
        return new a.C0355a(doubleConsumer, z);
    }

    /* compiled from: ForEachOps.java */
    /* loaded from: classes5.dex */
    static abstract class a<T> implements hg<T, Void>, hh<T, Void> {
        private final boolean a;

        /* renamed from: b */
        public Void get() {
            return null;
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        protected a(boolean z) {
            this.a = z;
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

        @Override // java8.util.stream.hg
        public int a() {
            if (this.a) {
                return 0;
            }
            return gp.q;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: c */
        public <S> Void a(gb<T> gbVar, Spliterator<S> spliterator) {
            return ((a) gbVar.a((gb<T>) this, (Spliterator) spliterator)).get();
        }

        /* renamed from: d */
        public <S> Void b(gb<T> gbVar, Spliterator<S> spliterator) {
            if (this.a) {
                new b(gbVar, spliterator, this).invoke();
                return null;
            }
            new c(gbVar, spliterator, gbVar.a((Sink<T>) this)).invoke();
            return null;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: ForEachOps.java */
        /* loaded from: classes5.dex */
        public static final class d<T> extends a<T> {
            final Consumer<? super T> a;

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void a(gb gbVar, Spliterator spliterator) {
                return super.a(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void b(gb gbVar, Spliterator spliterator) {
                return super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            d(Consumer<? super T> consumer, boolean z) {
                super(z);
                this.a = consumer;
            }

            @Override // java8.util.function.Consumer
            public void accept(T t) {
                this.a.accept(t);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: ForEachOps.java */
        /* loaded from: classes5.dex */
        public static final class b extends a<Integer> implements Sink.OfInt {
            final IntConsumer a;

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void a(gb gbVar, Spliterator spliterator) {
                return super.a(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void b(gb gbVar, Spliterator spliterator) {
                return super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            b(IntConsumer intConsumer, boolean z) {
                super(z);
                this.a = intConsumer;
            }

            @Override // java8.util.stream.ee.a, java8.util.stream.Sink
            public void accept(int i) {
                this.a.accept(i);
            }

            @Override // java8.util.stream.Sink.OfInt
            public void accept(Integer num) {
                gj.b.a(this, num);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: ForEachOps.java */
        /* loaded from: classes5.dex */
        public static final class c extends a<Long> implements Sink.OfLong {
            final LongConsumer a;

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void a(gb gbVar, Spliterator spliterator) {
                return super.a(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void b(gb gbVar, Spliterator spliterator) {
                return super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            c(LongConsumer longConsumer, boolean z) {
                super(z);
                this.a = longConsumer;
            }

            @Override // java8.util.stream.ee.a, java8.util.stream.Sink
            public void accept(long j) {
                this.a.accept(j);
            }

            @Override // java8.util.stream.Sink.OfLong
            public void accept(Long l) {
                gj.c.a(this, l);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: ForEachOps.java */
        /* renamed from: java8.util.stream.ee$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0355a extends a<Double> implements Sink.OfDouble {
            final DoubleConsumer a;

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void a(gb gbVar, Spliterator spliterator) {
                return super.a(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.stream.hg
            public /* synthetic */ Void b(gb gbVar, Spliterator spliterator) {
                return super.b(gbVar, spliterator);
            }

            @Override // java8.util.stream.ee.a, java8.util.function.Supplier
            public /* synthetic */ Object get() {
                return super.get();
            }

            C0355a(DoubleConsumer doubleConsumer, boolean z) {
                super(z);
                this.a = doubleConsumer;
            }

            @Override // java8.util.stream.ee.a, java8.util.stream.Sink
            public void accept(double d) {
                this.a.accept(d);
            }

            @Override // java8.util.stream.Sink.OfDouble
            public void accept(Double d) {
                gj.a.a(this, d);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ForEachOps.java */
    /* loaded from: classes5.dex */
    public static final class c<S, T> extends CountedCompleter<Void> {
        private final gb<T> helper;
        private final Sink<S> sink;
        private Spliterator<S> spliterator;
        private long targetSize;

        c(gb<T> gbVar, Spliterator<S> spliterator, Sink<S> sink) {
            super(null);
            this.sink = sink;
            this.helper = gbVar;
            this.spliterator = spliterator;
            this.targetSize = 0L;
        }

        c(c<S, T> cVar, Spliterator<S> spliterator) {
            super(cVar);
            this.spliterator = spliterator;
            this.sink = cVar.sink;
            this.targetSize = cVar.targetSize;
            this.helper = cVar.helper;
        }

        @Override // java8.util.concurrent.CountedCompleter
        public void compute() {
            Spliterator<S> trySplit;
            Spliterator<S> spliterator = this.spliterator;
            long estimateSize = spliterator.estimateSize();
            long j = this.targetSize;
            if (j == 0) {
                j = g.b(estimateSize);
                this.targetSize = j;
            }
            boolean a = gp.SHORT_CIRCUIT.a(this.helper.c());
            boolean z = false;
            Sink<S> sink = this.sink;
            long j2 = estimateSize;
            Spliterator<S> spliterator2 = spliterator;
            c<S, T> cVar = this;
            while (true) {
                if (a && sink.cancellationRequested()) {
                    break;
                } else if (j2 <= j || (trySplit = spliterator2.trySplit()) == null) {
                    break;
                } else {
                    cVar = new c<>(cVar, trySplit);
                    cVar.addToPendingCount(1);
                    if (z) {
                        spliterator2 = trySplit;
                    } else {
                        cVar = cVar;
                        cVar = cVar;
                    }
                    z = !z;
                    cVar.fork();
                    j2 = spliterator2.estimateSize();
                }
            }
            cVar.helper.b(sink, spliterator2);
            cVar.spliterator = null;
            cVar.propagateCompletion();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ForEachOps.java */
    /* loaded from: classes5.dex */
    public static final class b<S, T> extends CountedCompleter<Void> {
        private final Sink<T> action;
        private final ConcurrentMap<b<S, T>, b<S, T>> completionMap;
        private final gb<T> helper;
        private final b<S, T> leftPredecessor;
        private Node<T> node;
        private Spliterator<S> spliterator;
        private final long targetSize;

        protected b(gb<T> gbVar, Spliterator<S> spliterator, Sink<T> sink) {
            super(null);
            this.helper = gbVar;
            this.spliterator = spliterator;
            this.targetSize = g.b(spliterator.estimateSize());
            this.completionMap = new ConcurrentHashMap(Math.max(16, g.h() << 1), 0.75f, ForkJoinPool.getCommonPoolParallelism() + 1);
            this.action = sink;
            this.leftPredecessor = null;
        }

        b(b<S, T> bVar, Spliterator<S> spliterator, b<S, T> bVar2) {
            super(bVar);
            this.helper = bVar.helper;
            this.spliterator = spliterator;
            this.targetSize = bVar.targetSize;
            this.completionMap = bVar.completionMap;
            this.action = bVar.action;
            this.leftPredecessor = bVar2;
        }

        @Override // java8.util.concurrent.CountedCompleter
        public final void compute() {
            a((b) this);
        }

        private static <S, T> void a(b<S, T> bVar) {
            Spliterator<S> trySplit;
            Spliterator<S> spliterator = ((b) bVar).spliterator;
            long j = ((b) bVar).targetSize;
            boolean z = false;
            while (spliterator.estimateSize() > j && (trySplit = spliterator.trySplit()) != null) {
                b<S, T> bVar2 = new b<>(bVar, trySplit, ((b) bVar).leftPredecessor);
                b<S, T> bVar3 = new b<>(bVar, spliterator, bVar2);
                bVar.addToPendingCount(1);
                bVar3.addToPendingCount(1);
                ((b) bVar).completionMap.put(bVar2, bVar3);
                if (((b) bVar).leftPredecessor != null) {
                    bVar2.addToPendingCount(1);
                    if (((b) bVar).completionMap.replace(((b) bVar).leftPredecessor, bVar, bVar2)) {
                        bVar.addToPendingCount(-1);
                    } else {
                        bVar2.addToPendingCount(-1);
                    }
                }
                if (z) {
                    spliterator = trySplit;
                    bVar = bVar2;
                    bVar2 = bVar3;
                } else {
                    bVar = bVar3;
                }
                z = !z;
                bVar2.fork();
            }
            if (bVar.getPendingCount() > 0) {
                IntFunction<T[]> a = ef.a();
                gb<T> gbVar = ((b) bVar).helper;
                ((b) bVar).node = ((Node.Builder) ((b) bVar).helper.a((gb<T>) gbVar.a(gbVar.a(spliterator), a), (Spliterator) spliterator)).build();
                ((b) bVar).spliterator = null;
            }
            bVar.tryComplete();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ Object[] a(int i) {
            return new Object[i];
        }

        @Override // java8.util.concurrent.CountedCompleter
        public void onCompletion(CountedCompleter<?> countedCompleter) {
            Node<T> node = this.node;
            if (node != null) {
                node.forEach(this.action);
                this.node = null;
            } else {
                Spliterator<S> spliterator = this.spliterator;
                if (spliterator != null) {
                    this.helper.a((gb<T>) this.action, (Spliterator) spliterator);
                    this.spliterator = null;
                }
            }
            b<S, T> remove = this.completionMap.remove(this);
            if (remove != null) {
                remove.tryComplete();
            }
        }
    }
}
