package java8.util.stream;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.concurrent.ForkJoinPool;
import java8.util.function.BooleanSupplier;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.DoubleSupplier;
import java8.util.function.IntConsumer;
import java8.util.function.IntSupplier;
import java8.util.function.LongConsumer;
import java8.util.function.LongSupplier;
import java8.util.function.Supplier;
import java8.util.stream.Sink;
import java8.util.stream.gn;

/* compiled from: StreamSpliterators.java */
/* loaded from: classes5.dex */
class gr {

    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static abstract class a<P_IN, P_OUT, T_BUFFER extends f> implements Spliterator<P_OUT> {
        final boolean a;
        final gb<P_OUT> b;
        Spliterator<P_IN> c;
        Sink<P_IN> d;
        BooleanSupplier e;
        long f;
        T_BUFFER g;
        boolean h;
        private Supplier<Spliterator<P_IN>> i;

        abstract a<P_IN, P_OUT, ?> a(Spliterator<P_IN> spliterator);

        abstract void c();

        a(gb<P_OUT> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
            this.b = gbVar;
            this.i = supplier;
            this.c = null;
            this.a = z;
        }

        a(gb<P_OUT> gbVar, Spliterator<P_IN> spliterator, boolean z) {
            this.b = gbVar;
            this.i = null;
            this.c = spliterator;
            this.a = z;
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return Spliterators.hasCharacteristics(this, i);
        }

        final void a() {
            if (this.c == null) {
                this.c = this.i.get();
                this.i = null;
            }
        }

        final boolean b() {
            T_BUFFER t_buffer = this.g;
            boolean z = false;
            if (t_buffer != null) {
                this.f++;
                if (this.f < t_buffer.a()) {
                    z = true;
                }
                if (z) {
                    return z;
                }
                this.f = 0L;
                this.g.b();
                return d();
            } else if (this.h) {
                return false;
            } else {
                a();
                c();
                this.f = 0L;
                this.d.begin(this.c.getExactSizeIfKnown());
                return d();
            }
        }

        @Override // java8.util.Spliterator
        public Spliterator<P_OUT> trySplit() {
            if (!this.a || this.g != null || this.h) {
                return null;
            }
            a();
            Spliterator<P_IN> trySplit = this.c.trySplit();
            if (trySplit == null) {
                return null;
            }
            return a(trySplit);
        }

        private boolean d() {
            while (this.g.a() == 0) {
                if (this.d.cancellationRequested() || !this.e.getAsBoolean()) {
                    if (this.h) {
                        return false;
                    }
                    this.d.end();
                    this.h = true;
                }
            }
            return true;
        }

        @Override // java8.util.Spliterator
        public final long estimateSize() {
            a();
            return this.c.estimateSize();
        }

        @Override // java8.util.Spliterator
        public final long getExactSizeIfKnown() {
            a();
            if (gp.SIZED.a(this.b.c())) {
                return this.c.getExactSizeIfKnown();
            }
            return -1L;
        }

        @Override // java8.util.Spliterator
        public final int characteristics() {
            a();
            int d = gp.d(gp.c(this.b.c()));
            return (d & 64) != 0 ? (d & (-16449)) | (this.c.characteristics() & 16448) : d;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super P_OUT> getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        public final String toString() {
            return String.format("%s[%s]", getClass().getName(), this.c);
        }
    }

    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class k<P_IN, P_OUT> extends a<P_IN, P_OUT, gn<P_OUT>> {
        public k(gb<P_OUT> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
            super(gbVar, supplier, z);
        }

        k(gb<P_OUT> gbVar, Spliterator<P_IN> spliterator, boolean z) {
            super(gbVar, spliterator, z);
        }

        /* renamed from: b */
        public k<P_IN, P_OUT> a(Spliterator<P_IN> spliterator) {
            return new k<>(this.b, spliterator, this.a);
        }

        @Override // java8.util.stream.gr.a
        void c() {
            gn gnVar = new gn();
            this.g = gnVar;
            gb gbVar = this.b;
            gnVar.getClass();
            this.d = gbVar.a(hb.a(gnVar));
            this.e = hc.a(this);
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super P_OUT> consumer) {
            Objects.requireNonNull(consumer);
            boolean b = b();
            if (b) {
                consumer.accept((Object) ((gn) this.g).b(this.f));
            }
            return b;
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super P_OUT> consumer) {
            if (this.g != null || this.h) {
                do {
                } while (tryAdvance(consumer));
                return;
            }
            Objects.requireNonNull(consumer);
            a();
            gb gbVar = this.b;
            consumer.getClass();
            gbVar.a((gb) hd.a(consumer), (Spliterator) this.c);
            this.h = true;
        }
    }

    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class g<P_IN> extends a<P_IN, Integer, gn.c> implements Spliterator.OfInt {
        public g(gb<Integer> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
            super(gbVar, supplier, z);
        }

        g(gb<Integer> gbVar, Spliterator<P_IN> spliterator, boolean z) {
            super(gbVar, spliterator, z);
        }

        @Override // java8.util.stream.gr.a
        a<P_IN, Integer, ?> a(Spliterator<P_IN> spliterator) {
            return new g(this.b, spliterator, this.a);
        }

        @Override // java8.util.stream.gr.a
        void c() {
            final gn.c cVar = new gn.c();
            this.g = cVar;
            this.d = this.b.a((Sink) new Sink.OfInt() { // from class: java8.util.stream.gr.g.1
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

                @Override // java8.util.stream.Sink
                public void accept(double d) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink
                public void accept(long j) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink.OfInt
                public void accept(Integer num) {
                    accept(num.intValue());
                }

                @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                public void accept(int i) {
                    cVar.accept(i);
                }
            });
            this.e = gu.a(this);
        }

        @Override // java8.util.stream.gr.a, java8.util.Spliterator
        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt) super.trySplit();
        }

        @Override // java8.util.Spliterator.OfInt
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            boolean b = b();
            if (b) {
                intConsumer.accept(((gn.c) this.g).a(this.f));
            }
            return b;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return Spliterators.OfInt.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(final IntConsumer intConsumer) {
            if (this.g != null || this.h) {
                do {
                } while (tryAdvance(intConsumer));
                return;
            }
            Objects.requireNonNull(intConsumer);
            a();
            this.b.a((gb) new Sink.OfInt() { // from class: java8.util.stream.gr.g.2
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

                @Override // java8.util.stream.Sink
                public void accept(double d) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink
                public void accept(long j) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink.OfInt
                public void accept(Integer num) {
                    accept(num.intValue());
                }

                @Override // java8.util.stream.Sink.OfInt, java8.util.function.IntConsumer
                public void accept(int i) {
                    intConsumer.accept(i);
                }
            }, (Spliterator) this.c);
            this.h = true;
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            Spliterators.OfInt.forEachRemaining(this, consumer);
        }
    }

    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class h<P_IN> extends a<P_IN, Long, gn.d> implements Spliterator.OfLong {
        public h(gb<Long> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
            super(gbVar, supplier, z);
        }

        h(gb<Long> gbVar, Spliterator<P_IN> spliterator, boolean z) {
            super(gbVar, spliterator, z);
        }

        @Override // java8.util.stream.gr.a
        a<P_IN, Long, ?> a(Spliterator<P_IN> spliterator) {
            return new h(this.b, spliterator, this.a);
        }

        @Override // java8.util.stream.gr.a
        void c() {
            final gn.d dVar = new gn.d();
            this.g = dVar;
            this.d = this.b.a((Sink) new Sink.OfLong() { // from class: java8.util.stream.gr.h.1
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

                @Override // java8.util.stream.Sink
                public void accept(double d) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink
                public void accept(int i) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink.OfLong
                public void accept(Long l) {
                    accept(l.longValue());
                }

                @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                public void accept(long j) {
                    dVar.accept(j);
                }
            });
            this.e = gv.a(this);
        }

        @Override // java8.util.stream.gr.a, java8.util.Spliterator
        public Spliterator.OfLong trySplit() {
            return (Spliterator.OfLong) super.trySplit();
        }

        @Override // java8.util.Spliterator.OfLong
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            boolean b = b();
            if (b) {
                longConsumer.accept(((gn.d) this.g).a(this.f));
            }
            return b;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Long> consumer) {
            return Spliterators.OfLong.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfLong
        public void forEachRemaining(final LongConsumer longConsumer) {
            if (this.g != null || this.h) {
                do {
                } while (tryAdvance(longConsumer));
                return;
            }
            Objects.requireNonNull(longConsumer);
            a();
            this.b.a((gb) new Sink.OfLong() { // from class: java8.util.stream.gr.h.2
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

                @Override // java8.util.stream.Sink
                public void accept(double d) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink
                public void accept(int i) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink.OfLong
                public void accept(Long l) {
                    accept(l.longValue());
                }

                @Override // java8.util.stream.Sink.OfLong, java8.util.function.LongConsumer
                public void accept(long j) {
                    longConsumer.accept(j);
                }
            }, (Spliterator) this.c);
            this.h = true;
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            Spliterators.OfLong.forEachRemaining(this, consumer);
        }
    }

    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class e<P_IN> extends a<P_IN, Double, gn.b> implements Spliterator.OfDouble {
        public e(gb<Double> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z) {
            super(gbVar, supplier, z);
        }

        e(gb<Double> gbVar, Spliterator<P_IN> spliterator, boolean z) {
            super(gbVar, spliterator, z);
        }

        @Override // java8.util.stream.gr.a
        a<P_IN, Double, ?> a(Spliterator<P_IN> spliterator) {
            return new e(this.b, spliterator, this.a);
        }

        @Override // java8.util.stream.gr.a
        void c() {
            final gn.b bVar = new gn.b();
            this.g = bVar;
            this.d = this.b.a((Sink) new Sink.OfDouble() { // from class: java8.util.stream.gr.e.1
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

                @Override // java8.util.stream.Sink
                public void accept(int i) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink
                public void accept(long j) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                public void accept(double d) {
                    bVar.accept(d);
                }

                @Override // java8.util.stream.Sink.OfDouble
                public void accept(Double d) {
                    accept(d.doubleValue());
                }
            });
            this.e = gt.a(this);
        }

        @Override // java8.util.stream.gr.a, java8.util.Spliterator
        public Spliterator.OfDouble trySplit() {
            return (Spliterator.OfDouble) super.trySplit();
        }

        @Override // java8.util.Spliterator.OfDouble
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            boolean b = b();
            if (b) {
                doubleConsumer.accept(((gn.b) this.g).a(this.f));
            }
            return b;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return Spliterators.OfDouble.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfDouble
        public void forEachRemaining(final DoubleConsumer doubleConsumer) {
            if (this.g != null || this.h) {
                do {
                } while (tryAdvance(doubleConsumer));
                return;
            }
            Objects.requireNonNull(doubleConsumer);
            a();
            this.b.a((gb) new Sink.OfDouble() { // from class: java8.util.stream.gr.e.2
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

                @Override // java8.util.stream.Sink
                public void accept(int i) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink
                public void accept(long j) {
                    gj.a();
                }

                @Override // java8.util.stream.Sink.OfDouble, java8.util.function.DoubleConsumer
                public void accept(double d) {
                    doubleConsumer.accept(d);
                }

                @Override // java8.util.stream.Sink.OfDouble
                public void accept(Double d) {
                    accept(d.doubleValue());
                }
            }, (Spliterator) this.c);
            this.h = true;
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Double> consumer) {
            Spliterators.OfDouble.forEachRemaining(this, consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static class c<T, T_SPLITR extends Spliterator<T>> implements Spliterator<T> {
        private final Supplier<? extends T_SPLITR> a;
        private T_SPLITR b;

        public c(Supplier<? extends T_SPLITR> supplier) {
            this.a = supplier;
        }

        T_SPLITR a() {
            if (this.b == null) {
                this.b = (T_SPLITR) ((Spliterator) this.a.get());
            }
            return this.b;
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return Spliterators.hasCharacteristics(this, i);
        }

        @Override // java8.util.Spliterator
        public T_SPLITR trySplit() {
            return (T_SPLITR) a().trySplit();
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super T> consumer) {
            return a().tryAdvance(consumer);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            a().forEachRemaining(consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return a().estimateSize();
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return a().characteristics();
        }

        @Override // java8.util.Spliterator
        public Comparator<? super T> getComparator() {
            return a().getComparator();
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            return a().getExactSizeIfKnown();
        }

        public String toString() {
            return getClass().getName() + "[" + a() + "]";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static class d<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>> extends c<T, T_SPLITR> implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            @Override // java8.util.stream.gr.c, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            d(Supplier<? extends T_SPLITR> supplier) {
                super(supplier);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public boolean tryAdvance(T_CONS t_cons) {
                return ((Spliterator.OfPrimitive) a()).tryAdvance((Spliterator.OfPrimitive) t_cons);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public void forEachRemaining(T_CONS t_cons) {
                ((Spliterator.OfPrimitive) a()).forEachRemaining((Spliterator.OfPrimitive) t_cons);
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class b extends d<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {
            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((b) intConsumer);
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((b) intConsumer);
            }

            @Override // java8.util.stream.gr.c.d, java8.util.stream.gr.c, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            public b(Supplier<Spliterator.OfInt> supplier) {
                super(supplier);
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* renamed from: java8.util.stream.gr$c$c */
        /* loaded from: classes5.dex */
        public static final class C0361c extends d<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {
            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((C0361c) longConsumer);
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((C0361c) longConsumer);
            }

            @Override // java8.util.stream.gr.c.d, java8.util.stream.gr.c, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            public C0361c(Supplier<Spliterator.OfLong> supplier) {
                super(supplier);
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class a extends d<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {
            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((a) doubleConsumer);
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((a) doubleConsumer);
            }

            @Override // java8.util.stream.gr.c.d, java8.util.stream.gr.c, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            public a(Supplier<Spliterator.OfDouble> supplier) {
                super(supplier);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static abstract class i<T, T_SPLITR extends Spliterator<T>> {
        final long a;
        final long b;
        T_SPLITR c;
        long d;
        long e;

        protected abstract T_SPLITR a(T_SPLITR t_splitr, long j, long j2, long j3, long j4);

        i(T_SPLITR t_splitr, long j, long j2, long j3, long j4) {
            this.c = t_splitr;
            this.a = j;
            this.b = j2;
            this.d = j3;
            this.e = j4;
        }

        public T_SPLITR trySplit() {
            long j = this.a;
            long j2 = this.e;
            if (j >= j2 || this.d >= j2) {
                return null;
            }
            while (true) {
                T_SPLITR t_splitr = (T_SPLITR) this.c.trySplit();
                if (t_splitr == null) {
                    return null;
                }
                long estimateSize = this.d + t_splitr.estimateSize();
                long min = Math.min(estimateSize, this.b);
                long j3 = this.a;
                if (j3 >= min) {
                    this.d = min;
                } else {
                    long j4 = this.b;
                    if (min >= j4) {
                        this.c = t_splitr;
                        this.e = min;
                    } else if (this.d < j3 || estimateSize > j4) {
                        long j5 = this.a;
                        long j6 = this.b;
                        long j7 = this.d;
                        this.d = min;
                        return a(t_splitr, j5, j6, j7, min);
                    } else {
                        this.d = min;
                        return t_splitr;
                    }
                }
            }
        }

        public long estimateSize() {
            long j = this.a;
            long j2 = this.e;
            if (j < j2) {
                return j2 - Math.max(j, this.d);
            }
            return 0L;
        }

        public int characteristics() {
            return this.c.characteristics();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class e<T> extends i<T, Spliterator<T>> implements Spliterator<T> {
            public static /* synthetic */ void a(Object obj) {
            }

            public static /* synthetic */ void b(Object obj) {
            }

            public e(Spliterator<T> spliterator, long j, long j2) {
                this(spliterator, j, j2, 0L, Math.min(spliterator.estimateSize(), j2));
            }

            private e(Spliterator<T> spliterator, long j, long j2, long j3, long j4) {
                super(spliterator, j, j2, j3, j4);
            }

            @Override // java8.util.stream.gr.i
            protected Spliterator<T> a(Spliterator<T> spliterator, long j, long j2, long j3, long j4) {
                return new e(spliterator, j, j2, j3, j4);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (this.a >= this.e) {
                    return false;
                }
                while (this.a > this.d) {
                    this.c.tryAdvance(gz.a());
                    this.d++;
                }
                if (this.d >= this.e) {
                    return false;
                }
                this.d++;
                return this.c.tryAdvance(consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (this.a >= this.e || this.d >= this.e) {
                    return;
                }
                if (this.d < this.a || this.d + this.c.estimateSize() > this.b) {
                    while (this.a > this.d) {
                        this.c.tryAdvance(ha.a());
                        this.d++;
                    }
                    while (this.d < this.e) {
                        this.c.tryAdvance(consumer);
                        this.d++;
                    }
                    return;
                }
                this.c.forEachRemaining(consumer);
                this.d = this.e;
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
                return Spliterators.getComparator(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static abstract class d<T, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_CONS> extends i<T, T_SPLITR> implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            protected abstract T_CONS b();

            @Override // java8.util.stream.gr.i, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            d(T_SPLITR t_splitr, long j, long j2) {
                this(t_splitr, j, j2, 0L, Math.min(t_splitr.estimateSize(), j2));
            }

            d(T_SPLITR t_splitr, long j, long j2, long j3, long j4) {
                super(t_splitr, j, j2, j3, j4);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public boolean tryAdvance(T_CONS t_cons) {
                Objects.requireNonNull(t_cons);
                if (this.a >= this.e) {
                    return false;
                }
                while (this.a > this.d) {
                    ((Spliterator.OfPrimitive) this.c).tryAdvance((Spliterator.OfPrimitive) b());
                    this.d++;
                }
                if (this.d >= this.e) {
                    return false;
                }
                this.d++;
                return ((Spliterator.OfPrimitive) this.c).tryAdvance((Spliterator.OfPrimitive) t_cons);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public void forEachRemaining(T_CONS t_cons) {
                Objects.requireNonNull(t_cons);
                if (this.a >= this.e || this.d >= this.e) {
                    return;
                }
                if (this.d < this.a || this.d + ((Spliterator.OfPrimitive) this.c).estimateSize() > this.b) {
                    while (this.a > this.d) {
                        ((Spliterator.OfPrimitive) this.c).tryAdvance((Spliterator.OfPrimitive) b());
                        this.d++;
                    }
                    while (this.d < this.e) {
                        ((Spliterator.OfPrimitive) this.c).tryAdvance((Spliterator.OfPrimitive) t_cons);
                        this.d++;
                    }
                    return;
                }
                ((Spliterator.OfPrimitive) this.c).forEachRemaining((Spliterator.OfPrimitive) t_cons);
                this.d = this.e;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class b extends d<Integer, Spliterator.OfInt, IntConsumer> implements Spliterator.OfInt {
            public static /* synthetic */ void a(int i) {
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((b) intConsumer);
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((b) intConsumer);
            }

            @Override // java8.util.stream.gr.i.d, java8.util.stream.gr.i, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            public b(Spliterator.OfInt ofInt, long j, long j2) {
                super(ofInt, j, j2);
            }

            b(Spliterator.OfInt ofInt, long j, long j2, long j3, long j4) {
                super(ofInt, j, j2, j3, j4);
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
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Integer> consumer) {
                return Spliterators.OfInt.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Integer> consumer) {
                Spliterators.OfInt.forEachRemaining(this, consumer);
            }

            public Spliterator.OfInt a(Spliterator.OfInt ofInt, long j, long j2, long j3, long j4) {
                return new b(ofInt, j, j2, j3, j4);
            }

            /* renamed from: a */
            public IntConsumer b() {
                return gx.a();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class c extends d<Long, Spliterator.OfLong, LongConsumer> implements Spliterator.OfLong {
            public static /* synthetic */ void a(long j) {
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((c) longConsumer);
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((c) longConsumer);
            }

            @Override // java8.util.stream.gr.i.d, java8.util.stream.gr.i, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            public c(Spliterator.OfLong ofLong, long j, long j2) {
                super(ofLong, j, j2);
            }

            c(Spliterator.OfLong ofLong, long j, long j2, long j3, long j4) {
                super(ofLong, j, j2, j3, j4);
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
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Long> consumer) {
                return Spliterators.OfLong.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Long> consumer) {
                Spliterators.OfLong.forEachRemaining(this, consumer);
            }

            public Spliterator.OfLong a(Spliterator.OfLong ofLong, long j, long j2, long j3, long j4) {
                return new c(ofLong, j, j2, j3, j4);
            }

            /* renamed from: a */
            public LongConsumer b() {
                return gy.a();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class a extends d<Double, Spliterator.OfDouble, DoubleConsumer> implements Spliterator.OfDouble {
            public static /* synthetic */ void a(double d) {
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((a) doubleConsumer);
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((a) doubleConsumer);
            }

            @Override // java8.util.stream.gr.i.d, java8.util.stream.gr.i, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            public a(Spliterator.OfDouble ofDouble, long j, long j2) {
                super(ofDouble, j, j2);
            }

            a(Spliterator.OfDouble ofDouble, long j, long j2, long j3, long j4) {
                super(ofDouble, j, j2, j3, j4);
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
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Double> consumer) {
                return Spliterators.OfDouble.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Double> consumer) {
                Spliterators.OfDouble.forEachRemaining(this, consumer);
            }

            public Spliterator.OfDouble a(Spliterator.OfDouble ofDouble, long j, long j2, long j3, long j4) {
                return new a(ofDouble, j, j2, j3, j4);
            }

            /* renamed from: a */
            public DoubleConsumer b() {
                return gw.a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static abstract class j<T, T_SPLITR extends Spliterator<T>> {
        protected final T_SPLITR a;
        protected final boolean b;
        protected final int c;
        private final long d;
        private final AtomicLong e;

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public enum f {
            NO_MORE,
            MAYBE_MORE,
            UNLIMITED
        }

        protected abstract T_SPLITR a(T_SPLITR t_splitr);

        j(T_SPLITR t_splitr, long j, long j2) {
            this.a = t_splitr;
            long j3 = 0;
            int i = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
            this.b = i < 0;
            this.d = i >= 0 ? j2 : j3;
            this.c = i >= 0 ? (int) Math.min(128L, ((j + j2) / g.h()) + 1) : 128;
            this.e = new AtomicLong(i >= 0 ? j + j2 : j);
        }

        j(T_SPLITR t_splitr, j<T, T_SPLITR> jVar) {
            this.a = t_splitr;
            this.b = jVar.b;
            this.e = jVar.e;
            this.d = jVar.d;
            this.c = jVar.c;
        }

        protected final long a(long j) {
            long j2;
            long min;
            do {
                j2 = this.e.get();
                if (j2 != 0) {
                    min = Math.min(j2, j);
                    if (min <= 0) {
                        break;
                    }
                } else if (this.b) {
                    return j;
                } else {
                    return 0L;
                }
            } while (!this.e.compareAndSet(j2, j2 - min));
            if (this.b) {
                return Math.max(j - min, 0L);
            }
            long j3 = this.d;
            return j2 > j3 ? Math.max(min - (j2 - j3), 0L) : min;
        }

        protected final f a() {
            if (this.e.get() > 0) {
                return f.MAYBE_MORE;
            }
            return this.b ? f.UNLIMITED : f.NO_MORE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T_SPLITR trySplit() {
            Spliterator<T> trySplit;
            if (this.e.get() == 0 || (trySplit = this.a.trySplit()) == null) {
                return null;
            }
            return (T_SPLITR) a((j<T, T_SPLITR>) trySplit);
        }

        public final long estimateSize() {
            return this.a.estimateSize();
        }

        public final int characteristics() {
            return this.a.characteristics() & (-16465);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class e<T> extends j<T, Spliterator<T>> implements Spliterator<T>, Consumer<T> {
            T d;

            public e(Spliterator<T> spliterator, long j, long j2) {
                super(spliterator, j, j2);
            }

            e(Spliterator<T> spliterator, e<T> eVar) {
                super(spliterator, eVar);
            }

            @Override // java8.util.function.Consumer
            public final void accept(T t) {
                this.d = t;
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                while (a() != f.NO_MORE && this.a.tryAdvance(this)) {
                    if (a(1L) == 1) {
                        consumer.accept((T) this.d);
                        this.d = null;
                        return true;
                    }
                }
                return false;
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                b.e eVar = null;
                while (true) {
                    f a = a();
                    if (a == f.NO_MORE) {
                        return;
                    }
                    if (a == f.MAYBE_MORE) {
                        if (eVar == null) {
                            eVar = new b.e(this.c);
                        } else {
                            eVar.a();
                        }
                        long j = 0;
                        while (this.a.tryAdvance(eVar)) {
                            j++;
                            if (j >= this.c) {
                                break;
                            }
                        }
                        if (j != 0) {
                            eVar.a(consumer, a(j));
                        } else {
                            return;
                        }
                    } else {
                        this.a.forEachRemaining(consumer);
                        return;
                    }
                }
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
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.stream.gr.j
            protected Spliterator<T> a(Spliterator<T> spliterator) {
                return new e(spliterator, this);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static abstract class d<T, T_CONS, T_BUFF extends b.d<T_CONS>, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>> extends j<T, T_SPLITR> implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            protected abstract void a(T_CONS t_cons);

            protected abstract T_BUFF b(int i);

            @Override // java8.util.stream.gr.j, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            d(T_SPLITR t_splitr, long j, long j2) {
                super(t_splitr, j, j2);
            }

            d(T_SPLITR t_splitr, d<T, T_CONS, T_BUFF, T_SPLITR> dVar) {
                super(t_splitr, dVar);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public boolean tryAdvance(T_CONS t_cons) {
                Objects.requireNonNull(t_cons);
                while (a() != f.NO_MORE && ((Spliterator.OfPrimitive) this.a).tryAdvance(this)) {
                    if (a(1L) == 1) {
                        a((d<T, T_CONS, T_BUFF, T_SPLITR>) t_cons);
                        return true;
                    }
                }
                return false;
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public void forEachRemaining(T_CONS t_cons) {
                Objects.requireNonNull(t_cons);
                T_BUFF t_buff = null;
                while (true) {
                    f a = a();
                    if (a == f.NO_MORE) {
                        return;
                    }
                    if (a == f.MAYBE_MORE) {
                        if (t_buff == null) {
                            t_buff = b(this.c);
                        } else {
                            t_buff.a();
                        }
                        long j = 0;
                        while (((Spliterator.OfPrimitive) this.a).tryAdvance((Spliterator.OfPrimitive) t_buff)) {
                            j++;
                            if (j >= this.c) {
                                break;
                            }
                        }
                        if (j != 0) {
                            t_buff.a(t_cons, a(j));
                        } else {
                            return;
                        }
                    } else {
                        ((Spliterator.OfPrimitive) this.a).forEachRemaining((Spliterator.OfPrimitive) t_cons);
                        return;
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class b extends d<Integer, IntConsumer, b.C0360b, Spliterator.OfInt> implements Spliterator.OfInt, IntConsumer {
            int d;

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((b) intConsumer);
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((b) intConsumer);
            }

            @Override // java8.util.stream.gr.j.d, java8.util.stream.gr.j, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            public b(Spliterator.OfInt ofInt, long j, long j2) {
                super(ofInt, j, j2);
            }

            b(Spliterator.OfInt ofInt, b bVar) {
                super(ofInt, bVar);
            }

            @Override // java8.util.function.IntConsumer
            public void accept(int i) {
                this.d = i;
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
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Integer> consumer) {
                return Spliterators.OfInt.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Integer> consumer) {
                Spliterators.OfInt.forEachRemaining(this, consumer);
            }

            public void a(IntConsumer intConsumer) {
                intConsumer.accept(this.d);
            }

            /* renamed from: a */
            public b.C0360b b(int i) {
                return new b.C0360b(i);
            }

            public Spliterator.OfInt a(Spliterator.OfInt ofInt) {
                return new b(ofInt, this);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class c extends d<Long, LongConsumer, b.c, Spliterator.OfLong> implements Spliterator.OfLong, LongConsumer {
            long d;

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((c) longConsumer);
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((c) longConsumer);
            }

            @Override // java8.util.stream.gr.j.d, java8.util.stream.gr.j, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            public c(Spliterator.OfLong ofLong, long j, long j2) {
                super(ofLong, j, j2);
            }

            c(Spliterator.OfLong ofLong, c cVar) {
                super(ofLong, cVar);
            }

            @Override // java8.util.function.LongConsumer
            public void accept(long j) {
                this.d = j;
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
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Long> consumer) {
                return Spliterators.OfLong.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Long> consumer) {
                Spliterators.OfLong.forEachRemaining(this, consumer);
            }

            public void a(LongConsumer longConsumer) {
                longConsumer.accept(this.d);
            }

            /* renamed from: a */
            public b.c b(int i) {
                return new b.c(i);
            }

            public Spliterator.OfLong a(Spliterator.OfLong ofLong) {
                return new c(ofLong, this);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class a extends d<Double, DoubleConsumer, b.a, Spliterator.OfDouble> implements Spliterator.OfDouble, DoubleConsumer {
            double d;

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((a) doubleConsumer);
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((a) doubleConsumer);
            }

            @Override // java8.util.stream.gr.j.d, java8.util.stream.gr.j, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            public a(Spliterator.OfDouble ofDouble, long j, long j2) {
                super(ofDouble, j, j2);
            }

            a(Spliterator.OfDouble ofDouble, a aVar) {
                super(ofDouble, aVar);
            }

            @Override // java8.util.function.DoubleConsumer
            public void accept(double d) {
                this.d = d;
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
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Double> consumer) {
                return Spliterators.OfDouble.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Double> consumer) {
                Spliterators.OfDouble.forEachRemaining(this, consumer);
            }

            public void a(DoubleConsumer doubleConsumer) {
                doubleConsumer.accept(this.d);
            }

            /* renamed from: a */
            public b.a b(int i) {
                return new b.a(i);
            }

            public Spliterator.OfDouble a(Spliterator.OfDouble ofDouble) {
                return new a(ofDouble, this);
            }
        }
    }

    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class d<T> implements Spliterator<T>, Consumer<T> {
        private static final Object a = new Object();
        private final Spliterator<T> b;
        private final ConcurrentMap<T, Boolean> c;
        private T d;

        public d(Spliterator<T> spliterator) {
            this(spliterator, new ConcurrentHashMap(512, 0.75f, ForkJoinPool.getCommonPoolParallelism() + 1));
        }

        private d(Spliterator<T> spliterator, ConcurrentMap<T, Boolean> concurrentMap) {
            this.b = spliterator;
            this.c = concurrentMap;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            return Spliterators.getExactSizeIfKnown(this);
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return Spliterators.hasCharacteristics(this, i);
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            this.d = t;
        }

        private T a(T t) {
            return t != null ? t : (T) a;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super T> consumer) {
            while (this.b.tryAdvance(this)) {
                if (this.c.putIfAbsent(a(this.d), Boolean.TRUE) == null) {
                    consumer.accept((T) this.d);
                    this.d = null;
                    return true;
                }
            }
            return false;
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            this.b.forEachRemaining(gs.a(this, consumer));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void a(d dVar, Consumer consumer, Object obj) {
            if (dVar.c.putIfAbsent(dVar.a(obj), Boolean.TRUE) == null) {
                consumer.accept(obj);
            }
        }

        @Override // java8.util.Spliterator
        public Spliterator<T> trySplit() {
            Spliterator<T> trySplit = this.b.trySplit();
            if (trySplit != null) {
                return new d(trySplit, this.c);
            }
            return null;
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b.estimateSize();
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return (this.b.characteristics() & (-16469)) | 1;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super T> getComparator() {
            return this.b.getComparator();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static abstract class f<T> implements Spliterator<T> {
        long a;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 1024;
        }

        protected f(long j) {
            this.a = j;
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.a;
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
            return Spliterators.getComparator(this);
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        static final class d<T> extends f<T> {
            final Supplier<? extends T> b;

            public d(long j, Supplier<? extends T> supplier) {
                super(j);
                this.b = supplier;
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super T> consumer) {
                Spliterators.forEachRemaining(this, consumer);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                consumer.accept((Object) this.b.get());
                return true;
            }

            @Override // java8.util.Spliterator
            public Spliterator<T> trySplit() {
                if (this.a == 0) {
                    return null;
                }
                long j = this.a >>> 1;
                this.a = j;
                return new d(j, this.b);
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        static final class b extends f<Integer> implements Spliterator.OfInt {
            final IntSupplier b;

            public b(long j, IntSupplier intSupplier) {
                super(j);
                this.b = intSupplier;
            }

            @Override // java8.util.Spliterator.OfInt
            public boolean tryAdvance(IntConsumer intConsumer) {
                Objects.requireNonNull(intConsumer);
                intConsumer.accept(this.b.getAsInt());
                return true;
            }

            @Override // java8.util.Spliterator
            public Spliterator.OfInt trySplit() {
                if (this.a == 0) {
                    return null;
                }
                long j = this.a >>> 1;
                this.a = j;
                return new b(j, this.b);
            }

            @Override // java8.util.Spliterator.OfInt
            public void forEachRemaining(IntConsumer intConsumer) {
                Spliterators.OfInt.forEachRemaining(this, intConsumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Integer> consumer) {
                Spliterators.OfInt.forEachRemaining(this, consumer);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Integer> consumer) {
                return Spliterators.OfInt.tryAdvance(this, consumer);
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        static final class c extends f<Long> implements Spliterator.OfLong {
            final LongSupplier b;

            public c(long j, LongSupplier longSupplier) {
                super(j);
                this.b = longSupplier;
            }

            @Override // java8.util.Spliterator.OfLong
            public boolean tryAdvance(LongConsumer longConsumer) {
                Objects.requireNonNull(longConsumer);
                longConsumer.accept(this.b.getAsLong());
                return true;
            }

            @Override // java8.util.Spliterator
            public Spliterator.OfLong trySplit() {
                if (this.a == 0) {
                    return null;
                }
                long j = this.a >>> 1;
                this.a = j;
                return new c(j, this.b);
            }

            @Override // java8.util.Spliterator.OfLong
            public void forEachRemaining(LongConsumer longConsumer) {
                Spliterators.OfLong.forEachRemaining(this, longConsumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Long> consumer) {
                Spliterators.OfLong.forEachRemaining(this, consumer);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Long> consumer) {
                return Spliterators.OfLong.tryAdvance(this, consumer);
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        static final class a extends f<Double> implements Spliterator.OfDouble {
            final DoubleSupplier b;

            public a(long j, DoubleSupplier doubleSupplier) {
                super(j);
                this.b = doubleSupplier;
            }

            @Override // java8.util.Spliterator.OfDouble
            public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                Objects.requireNonNull(doubleConsumer);
                doubleConsumer.accept(this.b.getAsDouble());
                return true;
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Double> consumer) {
                return Spliterators.OfDouble.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public Spliterator.OfDouble trySplit() {
                if (this.a == 0) {
                    return null;
                }
                long j = this.a >>> 1;
                this.a = j;
                return new a(j, this.b);
            }

            @Override // java8.util.Spliterator.OfDouble
            public void forEachRemaining(DoubleConsumer doubleConsumer) {
                Spliterators.OfDouble.forEachRemaining(this, doubleConsumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Double> consumer) {
                Spliterators.OfDouble.forEachRemaining(this, consumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StreamSpliterators.java */
    /* loaded from: classes5.dex */
    public static abstract class b {
        int a;

        b() {
        }

        void a() {
            this.a = 0;
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        static final class e<T> extends b implements Consumer<T> {
            final Object[] b;

            e(int i) {
                this.b = new Object[i];
            }

            @Override // java8.util.function.Consumer
            public void accept(T t) {
                Object[] objArr = this.b;
                int i = this.a;
                this.a = i + 1;
                objArr[i] = t;
            }

            public void a(Consumer<? super T> consumer, long j) {
                for (int i = 0; i < j; i++) {
                    consumer.accept(this.b[i]);
                }
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static abstract class d<T_CONS> extends b {
            int c;

            abstract void a(T_CONS t_cons, long j);

            d() {
            }

            @Override // java8.util.stream.gr.b
            void a() {
                this.c = 0;
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* renamed from: java8.util.stream.gr$b$b */
        /* loaded from: classes5.dex */
        public static final class C0360b extends d<IntConsumer> implements IntConsumer {
            final int[] b;

            C0360b(int i) {
                this.b = new int[i];
            }

            @Override // java8.util.function.IntConsumer
            public void accept(int i) {
                int[] iArr = this.b;
                int i2 = this.c;
                this.c = i2 + 1;
                iArr[i2] = i;
            }

            public void a(IntConsumer intConsumer, long j) {
                for (int i = 0; i < j; i++) {
                    intConsumer.accept(this.b[i]);
                }
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class c extends d<LongConsumer> implements LongConsumer {
            final long[] b;

            c(int i) {
                this.b = new long[i];
            }

            @Override // java8.util.function.LongConsumer
            public void accept(long j) {
                long[] jArr = this.b;
                int i = this.c;
                this.c = i + 1;
                jArr[i] = j;
            }

            public void a(LongConsumer longConsumer, long j) {
                for (int i = 0; i < j; i++) {
                    longConsumer.accept(this.b[i]);
                }
            }
        }

        /* compiled from: StreamSpliterators.java */
        /* loaded from: classes5.dex */
        public static final class a extends d<DoubleConsumer> implements DoubleConsumer {
            final double[] b;

            a(int i) {
                this.b = new double[i];
            }

            @Override // java8.util.function.DoubleConsumer
            public void accept(double d) {
                double[] dArr = this.b;
                int i = this.c;
                this.c = i + 1;
                dArr[i] = d;
            }

            public void a(DoubleConsumer doubleConsumer, long j) {
                for (int i = 0; i < j; i++) {
                    doubleConsumer.accept(this.b[i]);
                }
            }
        }
    }
}
