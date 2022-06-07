package java8.util.stream;

import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.function.DoublePredicate;
import java8.util.function.IntPredicate;
import java8.util.function.LongPredicate;
import java8.util.function.Predicate;
import java8.util.function.Supplier;
import java8.util.stream.Sink;
import java8.util.stream.gj;

/* compiled from: MatchOps.java */
/* loaded from: classes5.dex */
final class fi {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public enum f {
        ANY(true, true),
        ALL(false, false),
        NONE(true, false);
        
        private final boolean shortCircuitResult;
        private final boolean stopOnPredicateMatches;

        f(boolean z, boolean z2) {
            this.stopOnPredicateMatches = z;
            this.shortCircuitResult = z2;
        }
    }

    public static <T> hg<T, Boolean> a(Predicate<? super T> predicate, f fVar) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(fVar);
        return new g(gq.REFERENCE, fVar, fj.a(fVar, predicate));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public class a extends e<T> {
        final /* synthetic */ f a;
        final /* synthetic */ Predicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(f fVar, Predicate predicate) {
            super(fVar);
            this.a = fVar;
            this.b = predicate;
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            if (!this.c && this.b.test(t) == this.a.stopOnPredicateMatches) {
                this.c = true;
                this.d = this.a.shortCircuitResult;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ e a(f fVar, Predicate predicate) {
        return new a(fVar, predicate);
    }

    public static hg<Integer, Boolean> a(IntPredicate intPredicate, f fVar) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(fVar);
        return new g(gq.INT_VALUE, fVar, fk.a(fVar, intPredicate));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public class b extends e<Integer> implements Sink.OfInt {
        final /* synthetic */ f a;
        final /* synthetic */ IntPredicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(f fVar, IntPredicate intPredicate) {
            super(fVar);
            this.a = fVar;
            this.b = intPredicate;
        }

        @Override // java8.util.stream.fi.e, java8.util.stream.Sink
        public void accept(int i) {
            if (!this.c && this.b.test(i) == this.a.stopOnPredicateMatches) {
                this.c = true;
                this.d = this.a.shortCircuitResult;
            }
        }

        @Override // java8.util.stream.Sink.OfInt
        public void accept(Integer num) {
            gj.b.a(this, num);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ e a(f fVar, IntPredicate intPredicate) {
        return new b(fVar, intPredicate);
    }

    public static hg<Long, Boolean> a(LongPredicate longPredicate, f fVar) {
        Objects.requireNonNull(longPredicate);
        Objects.requireNonNull(fVar);
        return new g(gq.LONG_VALUE, fVar, fl.a(fVar, longPredicate));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public class c extends e<Long> implements Sink.OfLong {
        final /* synthetic */ f a;
        final /* synthetic */ LongPredicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(f fVar, LongPredicate longPredicate) {
            super(fVar);
            this.a = fVar;
            this.b = longPredicate;
        }

        @Override // java8.util.stream.fi.e, java8.util.stream.Sink
        public void accept(long j) {
            if (!this.c && this.b.test(j) == this.a.stopOnPredicateMatches) {
                this.c = true;
                this.d = this.a.shortCircuitResult;
            }
        }

        @Override // java8.util.stream.Sink.OfLong
        public void accept(Long l) {
            gj.c.a(this, l);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ e a(f fVar, LongPredicate longPredicate) {
        return new c(fVar, longPredicate);
    }

    public static hg<Double, Boolean> a(DoublePredicate doublePredicate, f fVar) {
        Objects.requireNonNull(doublePredicate);
        Objects.requireNonNull(fVar);
        return new g(gq.DOUBLE_VALUE, fVar, fm.a(fVar, doublePredicate));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public class d extends e<Double> implements Sink.OfDouble {
        final /* synthetic */ f a;
        final /* synthetic */ DoublePredicate b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(f fVar, DoublePredicate doublePredicate) {
            super(fVar);
            this.a = fVar;
            this.b = doublePredicate;
        }

        @Override // java8.util.stream.fi.e, java8.util.stream.Sink
        public void accept(double d) {
            if (!this.c && this.b.test(d) == this.a.stopOnPredicateMatches) {
                this.c = true;
                this.d = this.a.shortCircuitResult;
            }
        }

        @Override // java8.util.stream.Sink.OfDouble
        public void accept(Double d) {
            gj.a.a(this, d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ e a(f fVar, DoublePredicate doublePredicate) {
        return new d(fVar, doublePredicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public static final class g<T> implements hg<T, Boolean> {
        final f a;
        final Supplier<e<T>> b;
        private final gq c;

        g(gq gqVar, f fVar, Supplier<e<T>> supplier) {
            this.c = gqVar;
            this.a = fVar;
            this.b = supplier;
        }

        @Override // java8.util.stream.hg
        public int a() {
            return gp.t | gp.q;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: c */
        public <S> Boolean a(gb<T> gbVar, Spliterator<S> spliterator) {
            return Boolean.valueOf(((e) gbVar.a((gb<T>) this.b.get(), (Spliterator) spliterator)).a());
        }

        /* renamed from: d */
        public <S> Boolean b(gb<T> gbVar, Spliterator<S> spliterator) {
            return new h(this, gbVar, spliterator).invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public static abstract class e<T> implements Sink<T> {
        boolean c;
        boolean d;

        @Override // java8.util.stream.Sink
        public void begin(long j) {
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        e(f fVar) {
            this.d = !fVar.shortCircuitResult;
        }

        public boolean a() {
            return this.d;
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return this.c;
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MatchOps.java */
    /* loaded from: classes5.dex */
    public static final class h<P_IN, P_OUT> extends e<P_IN, P_OUT, Boolean, h<P_IN, P_OUT>> {
        private final g<P_OUT> op;

        h(g<P_OUT> gVar, gb<P_OUT> gbVar, Spliterator<P_IN> spliterator) {
            super(gbVar, spliterator);
            this.op = gVar;
        }

        h(h<P_IN, P_OUT> hVar, Spliterator<P_IN> spliterator) {
            super(hVar, spliterator);
            this.op = hVar.op;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public h<P_IN, P_OUT> a(Spliterator<P_IN> spliterator) {
            return new h<>(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: n */
        public Boolean i() {
            boolean a = ((e) this.helper.a((gb) this.op.b.get(), (Spliterator) this.spliterator)).a();
            if (a != this.op.a.shortCircuitResult) {
                return null;
            }
            a((h<P_IN, P_OUT>) Boolean.valueOf(a));
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: o */
        public Boolean a() {
            return Boolean.valueOf(!this.op.a.shortCircuitResult);
        }
    }
}
