package java8.util.stream;

import java8.util.Optional;
import java8.util.OptionalDouble;
import java8.util.OptionalInt;
import java8.util.OptionalLong;
import java8.util.Spliterator;
import java8.util.concurrent.CountedCompleter;
import java8.util.function.Predicate;
import java8.util.function.Supplier;
import java8.util.stream.Sink;

/* compiled from: FindOps.java */
/* loaded from: classes5.dex */
final class dv {
    private static final Predicate<Optional<Object>> a = dw.a();
    private static final Predicate<OptionalInt> b = dx.a();
    private static final Predicate<OptionalLong> c = dy.a();
    private static final Predicate<OptionalDouble> d = dz.a();
    private static final Supplier<hh<Object, Optional<Object>>> e = ea.a();
    private static final Supplier<hh<Integer, OptionalInt>> f = eb.a();
    private static final Supplier<hh<Long, OptionalLong>> g = ec.a();
    private static final Supplier<hh<Double, OptionalDouble>> h = ed.a();
    private static final hg i = new a(true, gq.REFERENCE, Optional.empty(), a, e);
    private static final hg j = new a(false, gq.REFERENCE, Optional.empty(), a, e);
    private static final hg<Integer, OptionalInt> k = new a(true, gq.INT_VALUE, OptionalInt.empty(), b, f);
    private static final hg<Integer, OptionalInt> l = new a(false, gq.INT_VALUE, OptionalInt.empty(), b, f);
    private static final hg<Long, OptionalLong> m = new a(true, gq.LONG_VALUE, OptionalLong.empty(), c, g);
    private static final hg<Long, OptionalLong> n = new a(false, gq.LONG_VALUE, OptionalLong.empty(), c, g);
    private static final hg<Double, OptionalDouble> o = new a(true, gq.DOUBLE_VALUE, OptionalDouble.empty(), d, h);
    private static final hg<Double, OptionalDouble> p = new a(false, gq.DOUBLE_VALUE, OptionalDouble.empty(), d, h);

    public static <T> hg<T, Optional<T>> a(boolean z) {
        return z ? i : j;
    }

    public static hg<Integer, OptionalInt> b(boolean z) {
        return z ? k : l;
    }

    public static hg<Long, OptionalLong> c(boolean z) {
        return z ? m : n;
    }

    public static hg<Double, OptionalDouble> d(boolean z) {
        return z ? o : p;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FindOps.java */
    /* loaded from: classes5.dex */
    public static final class a<T, O> implements hg<T, O> {
        final int a;
        final O b;
        final Predicate<O> c;
        final Supplier<hh<T, O>> d;
        private final gq e;

        a(boolean z, gq gqVar, O o, Predicate<O> predicate, Supplier<hh<T, O>> supplier) {
            this.a = (z ? 0 : gp.q) | gp.t;
            this.e = gqVar;
            this.b = o;
            this.c = predicate;
            this.d = supplier;
        }

        @Override // java8.util.stream.hg
        public int a() {
            return this.a;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.stream.hg
        public <S> O a(gb<T> gbVar, Spliterator<S> spliterator) {
            O o = (O) ((hh) gbVar.a((gb<T>) this.d.get(), (Spliterator) spliterator)).get();
            return o != null ? o : this.b;
        }

        @Override // java8.util.stream.hg
        public <P_IN> O b(gb<T> gbVar, Spliterator<P_IN> spliterator) {
            return new c(this, gp.ORDERED.a(gbVar.c()), gbVar, spliterator).invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FindOps.java */
    /* loaded from: classes5.dex */
    public static abstract class b<T, O> implements hh<T, O> {
        boolean a;
        T b;

        @Override // java8.util.stream.Sink
        public void begin(long j) {
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        b() {
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            if (!this.a) {
                this.a = true;
                this.b = t;
            }
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return this.a;
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

        /* compiled from: FindOps.java */
        /* loaded from: classes5.dex */
        static final class d<T> extends b<T, Optional<T>> {
            /* renamed from: a */
            public Optional<T> get() {
                if (this.a) {
                    return Optional.of(this.b);
                }
                return null;
            }
        }

        /* compiled from: FindOps.java */
        /* renamed from: java8.util.stream.dv$b$b */
        /* loaded from: classes5.dex */
        static final class C0354b extends b<Integer, OptionalInt> implements Sink.OfInt {
            @Override // java8.util.stream.Sink.OfInt
            public /* bridge */ /* synthetic */ void accept(Integer num) {
                super.accept((C0354b) num);
            }

            @Override // java8.util.stream.dv.b, java8.util.stream.Sink
            public void accept(int i) {
                accept((C0354b) Integer.valueOf(i));
            }

            /* renamed from: a */
            public OptionalInt get() {
                if (this.a) {
                    return OptionalInt.of(((Integer) this.b).intValue());
                }
                return null;
            }
        }

        /* compiled from: FindOps.java */
        /* loaded from: classes5.dex */
        static final class c extends b<Long, OptionalLong> implements Sink.OfLong {
            @Override // java8.util.stream.Sink.OfLong
            public /* bridge */ /* synthetic */ void accept(Long l) {
                super.accept((c) l);
            }

            @Override // java8.util.stream.dv.b, java8.util.stream.Sink
            public void accept(long j) {
                accept((c) Long.valueOf(j));
            }

            /* renamed from: a */
            public OptionalLong get() {
                if (this.a) {
                    return OptionalLong.of(((Long) this.b).longValue());
                }
                return null;
            }
        }

        /* compiled from: FindOps.java */
        /* loaded from: classes5.dex */
        static final class a extends b<Double, OptionalDouble> implements Sink.OfDouble {
            @Override // java8.util.stream.Sink.OfDouble
            public /* bridge */ /* synthetic */ void accept(Double d) {
                super.accept((a) d);
            }

            @Override // java8.util.stream.dv.b, java8.util.stream.Sink
            public void accept(double d) {
                accept((a) Double.valueOf(d));
            }

            /* renamed from: a */
            public OptionalDouble get() {
                if (this.a) {
                    return OptionalDouble.of(((Double) this.b).doubleValue());
                }
                return null;
            }
        }
    }

    /* compiled from: FindOps.java */
    /* loaded from: classes5.dex */
    private static final class c<P_IN, P_OUT, O> extends e<P_IN, P_OUT, O, c<P_IN, P_OUT, O>> {
        private final boolean mustFindFirst;
        private final a<P_OUT, O> op;

        c(a<P_OUT, O> aVar, boolean z, gb<P_OUT> gbVar, Spliterator<P_IN> spliterator) {
            super(gbVar, spliterator);
            this.mustFindFirst = z;
            this.op = aVar;
        }

        c(c<P_IN, P_OUT, O> cVar, Spliterator<P_IN> spliterator) {
            super(cVar, spliterator);
            this.mustFindFirst = cVar.mustFindFirst;
            this.op = cVar.op;
        }

        /* renamed from: b */
        public c<P_IN, P_OUT, O> a(Spliterator<P_IN> spliterator) {
            return new c<>(this, spliterator);
        }

        @Override // java8.util.stream.e
        protected O a() {
            return this.op.b;
        }

        private void c(O o) {
            if (m()) {
                a((c<P_IN, P_OUT, O>) o);
            } else {
                g();
            }
        }

        @Override // java8.util.stream.g
        protected O i() {
            O o = (O) ((hh) this.helper.a((gb) this.op.d.get(), (Spliterator) this.spliterator)).get();
            if (!this.mustFindFirst) {
                if (o != null) {
                    a((c<P_IN, P_OUT, O>) o);
                }
                return null;
            } else if (o == null) {
                return null;
            } else {
                c((c<P_IN, P_OUT, O>) o);
                return o;
            }
        }

        @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter
        public void onCompletion(CountedCompleter<?> countedCompleter) {
            if (this.mustFindFirst) {
                c cVar = (c) this.leftChild;
                c cVar2 = null;
                while (true) {
                    if (cVar != cVar2) {
                        O b = cVar.b();
                        if (b != null && this.op.c.test(b)) {
                            b((c<P_IN, P_OUT, O>) b);
                            c((c<P_IN, P_OUT, O>) b);
                            break;
                        }
                        cVar = (c) this.rightChild;
                        cVar2 = cVar;
                    } else {
                        break;
                    }
                }
            }
            super.onCompletion(countedCompleter);
        }
    }
}
