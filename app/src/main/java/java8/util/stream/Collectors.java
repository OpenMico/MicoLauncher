package java8.util.stream;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java8.util.DoubleSummaryStatistics;
import java8.util.IntSummaryStatistics;
import java8.util.LongSummaryStatistics;
import java8.util.Maps;
import java8.util.Objects;
import java8.util.Optional;
import java8.util.StringJoiner;
import java8.util.concurrent.ConcurrentMaps;
import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;
import java8.util.function.BinaryOperator;
import java8.util.function.BinaryOperators;
import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.function.Functions;
import java8.util.function.Predicate;
import java8.util.function.Supplier;
import java8.util.function.ToDoubleFunction;
import java8.util.function.ToIntFunction;
import java8.util.function.ToLongFunction;
import java8.util.stream.Collector;

/* loaded from: classes5.dex */
public final class Collectors {
    static final Set<Collector.Characteristics> a = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> b = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED));
    static final Set<Collector.Characteristics> c = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> d = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> e = Collections.emptySet();
    static final Set<Collector.Characteristics> f = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));
    static final Supplier<DoubleSummaryStatistics> g = ct.a();
    static final Supplier<IntSummaryStatistics> h = cu.a();
    static final Supplier<LongSummaryStatistics> i = cv.a();
    private static final Function<Map<?, ?>, Map<?, ?>> j = cw.a();
    private static final BiConsumer<List<Object>, ?> k = cx.a();
    private static final BiConsumer<Set<Object>, ?> l = cy.a();

    public static /* synthetic */ long b(Object obj) {
        return 1L;
    }

    private Collectors() {
    }

    private static <K, V> Supplier<Map<K, V>> g() {
        return h.a();
    }

    private static <K, V> Supplier<ConcurrentMap<K, V>> h() {
        return s.a();
    }

    private static <K, V> Supplier<Map<K, V>> i() {
        return h();
    }

    private static <T> Supplier<List<T>> j() {
        return ad.a();
    }

    private static <T> Supplier<Set<T>> k() {
        return ao.a();
    }

    private static final <K, U> Function<Map<K, U>, Map<K, U>> l() {
        return (Function<Map<K, U>, Map<K, U>>) j;
    }

    private static final <T> BiConsumer<List<T>, T> m() {
        return (BiConsumer<List<T>, T>) k;
    }

    private static final <T> BiConsumer<Set<T>, T> n() {
        return (BiConsumer<Set<T>, T>) l;
    }

    public static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> biConsumer, BinaryOperator<R> binaryOperator, Collector.Characteristics... characteristicsArr) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(binaryOperator);
        Objects.requireNonNull(characteristicsArr);
        return new c(supplier, biConsumer, binaryOperator, characteristicsArr.length == 0 ? c : Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH, characteristicsArr)));
    }

    public static <T, A, R> Collector<T, A, R> of(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Function<A, R> function, Collector.Characteristics... characteristicsArr) {
        Set<Collector.Characteristics> set;
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(binaryOperator);
        Objects.requireNonNull(function);
        Objects.requireNonNull(characteristicsArr);
        Set<Collector.Characteristics> set2 = e;
        if (characteristicsArr.length > 0) {
            EnumSet noneOf = EnumSet.noneOf(Collector.Characteristics.class);
            Collections.addAll(noneOf, characteristicsArr);
            set = Collections.unmodifiableSet(noneOf);
        } else {
            set = set2;
        }
        return new c(supplier, biConsumer, binaryOperator, function, set);
    }

    private static IllegalStateException a(Object obj, Object obj2, Object obj3) {
        return new IllegalStateException(String.format("Duplicate key %s (attempted merging values %s and %s)", obj, obj2, obj3));
    }

    private static <K, V, M extends Map<K, V>> BinaryOperator<M> o() {
        return az.a();
    }

    public static /* synthetic */ Map a(Map map, Map map2) {
        Object obj;
        for (Map.Entry entry : map2.entrySet()) {
            Object key = entry.getKey();
            Object requireNonNull = Objects.requireNonNull(entry.getValue());
            if (map instanceof ConcurrentMap) {
                obj = ((ConcurrentMap) map).putIfAbsent(key, requireNonNull);
                continue;
            } else {
                obj = Maps.putIfAbsent(map, key, requireNonNull);
                continue;
            }
            if (obj != null) {
                throw a(key, obj, requireNonNull);
            }
        }
        return map;
    }

    private static <T, K, V> BiConsumer<Map<K, V>, T> a(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return bk.a(function, function2);
    }

    public static /* synthetic */ void a(Function function, Function function2, Map map, Object obj) {
        Object obj2;
        Object apply = function.apply(obj);
        Object requireNonNull = Objects.requireNonNull(function2.apply(obj));
        if (map instanceof ConcurrentMap) {
            obj2 = ((ConcurrentMap) map).putIfAbsent(apply, requireNonNull);
        } else {
            obj2 = Maps.putIfAbsent(map, apply, requireNonNull);
        }
        if (obj2 != null) {
            throw a(apply, obj2, requireNonNull);
        }
    }

    /* loaded from: classes5.dex */
    public static class c<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> a;
        private final BiConsumer<A, T> b;
        private final BinaryOperator<A> c;
        private final Function<A, R> d;
        private final Set<Collector.Characteristics> e;

        public static /* synthetic */ Object a(Object obj) {
            return obj;
        }

        c(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Function<A, R> function, Set<Collector.Characteristics> set) {
            this.a = supplier;
            this.b = biConsumer;
            this.c = binaryOperator;
            this.d = function;
            this.e = set;
        }

        c(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Set<Collector.Characteristics> set) {
            this(supplier, biConsumer, binaryOperator, cz.a(), set);
        }

        @Override // java8.util.stream.Collector
        public BiConsumer<A, T> accumulator() {
            return this.b;
        }

        @Override // java8.util.stream.Collector
        public Supplier<A> supplier() {
            return this.a;
        }

        @Override // java8.util.stream.Collector
        public BinaryOperator<A> combiner() {
            return this.c;
        }

        @Override // java8.util.stream.Collector
        public Function<A, R> finisher() {
            return this.d;
        }

        @Override // java8.util.stream.Collector
        public Set<Collector.Characteristics> characteristics() {
            return this.e;
        }
    }

    public static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> supplier) {
        return new c(supplier, bv.a(), cg.a(), c);
    }

    public static <T> Collector<T, ?, List<T>> toList() {
        return new c(j(), m(), cr.a(), c);
    }

    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return new c(j(), m(), i.a(), j.a(), e);
    }

    public static <T> Collector<T, ?, Set<T>> toSet() {
        return new c(k(), n(), k.a(), d);
    }

    public static /* synthetic */ Set b(Set set, Set set2) {
        if (set.size() < set2.size()) {
            set2.addAll(set);
            return set2;
        }
        set.addAll(set2);
        return set;
    }

    public static <T> Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return new c(k(), n(), l.a(), m.a(), f);
    }

    public static /* synthetic */ Set a(Set set, Set set2) {
        if (set.size() < set2.size()) {
            set2.addAll(set);
            return set2;
        }
        set.addAll(set2);
        return set;
    }

    public static Collector<CharSequence, ?, String> joining() {
        return new c(n.a(), o.a(), p.a(), q.a(), e);
    }

    public static Collector<CharSequence, ?, String> joining(CharSequence charSequence) {
        return joining(charSequence, "", "");
    }

    public static Collector<CharSequence, ?, String> joining(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new c(r.a(charSequence, charSequence2, charSequence3), t.a(), u.a(), v.a(), e);
    }

    public static /* synthetic */ StringJoiner a(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new StringJoiner(charSequence, charSequence2, charSequence3);
    }

    private static <K, V, M extends Map<K, V>> BinaryOperator<M> b(BinaryOperator<V> binaryOperator) {
        return w.a(binaryOperator);
    }

    public static /* synthetic */ Map a(BinaryOperator binaryOperator, Map map, Map map2) {
        for (Map.Entry entry : map2.entrySet()) {
            Maps.merge(map, entry.getKey(), entry.getValue(), binaryOperator);
        }
        return map;
    }

    private static <K, V, M extends ConcurrentMap<K, V>> BinaryOperator<M> c(BinaryOperator<V> binaryOperator) {
        return x.a(binaryOperator);
    }

    public static /* synthetic */ ConcurrentMap a(BinaryOperator binaryOperator, ConcurrentMap concurrentMap, ConcurrentMap concurrentMap2) {
        for (Map.Entry entry : concurrentMap2.entrySet()) {
            ConcurrentMaps.merge(concurrentMap, entry.getKey(), entry.getValue(), binaryOperator);
        }
        return concurrentMap;
    }

    public static <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> function, Collector<? super U, A, R> collector) {
        return new c(collector.supplier(), y.a(collector.accumulator(), function), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    public static <T, U, A, R> Collector<T, ?, R> flatMapping(Function<? super T, ? extends Stream<? extends U>> function, Collector<? super U, A, R> collector) {
        return new c(collector.supplier(), z.a(function, collector.accumulator()), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    public static /* synthetic */ void a(Function function, BiConsumer biConsumer, Object obj, Object obj2) {
        Throwable th;
        Stream stream;
        try {
            stream = (Stream) function.apply(obj2);
            if (stream != null) {
                try {
                    stream.sequential().forEach(cs.a(biConsumer, obj));
                } catch (Throwable th2) {
                    th = th2;
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception unused2) {
                }
            }
        } catch (Throwable th3) {
            th = th3;
            stream = null;
        }
    }

    public static <T, A, R> Collector<T, ?, R> filtering(Predicate<? super T> predicate, Collector<? super T, A, R> collector) {
        return new c(collector.supplier(), aa.a(predicate, collector.accumulator()), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    public static /* synthetic */ void a(Predicate predicate, BiConsumer biConsumer, Object obj, Object obj2) {
        if (predicate.test(obj2)) {
            biConsumer.accept(obj, obj2);
        }
    }

    public static <T, A, R, RR> Collector<T, A, RR> collectingAndThen(Collector<T, A, R> collector, Function<R, RR> function) {
        Set<Collector.Characteristics> set;
        Set<Collector.Characteristics> characteristics = collector.characteristics();
        if (!characteristics.contains(Collector.Characteristics.IDENTITY_FINISH)) {
            set = characteristics;
        } else if (characteristics.size() == 1) {
            set = e;
        } else {
            EnumSet copyOf = EnumSet.copyOf(characteristics);
            copyOf.remove(Collector.Characteristics.IDENTITY_FINISH);
            set = Collections.unmodifiableSet(copyOf);
        }
        return new c(collector.supplier(), collector.accumulator(), collector.combiner(), Functions.andThen(collector.finisher(), function), set);
    }

    public static <T> Collector<T, ?, Long> counting() {
        return summingLong(ab.a());
    }

    public static <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator) {
        return reducing(BinaryOperators.minBy(comparator));
    }

    public static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator) {
        return reducing(BinaryOperators.maxBy(comparator));
    }

    public static <T> Collector<T, ?, Integer> summingInt(ToIntFunction<? super T> toIntFunction) {
        return new c(ac.a(), ae.a(toIntFunction), af.a(), ag.a(), e);
    }

    public static /* synthetic */ int[] f() {
        return new int[1];
    }

    public static /* synthetic */ void a(ToIntFunction toIntFunction, int[] iArr, Object obj) {
        iArr[0] = iArr[0] + toIntFunction.applyAsInt(obj);
    }

    public static /* synthetic */ int[] a(int[] iArr, int[] iArr2) {
        iArr[0] = iArr[0] + iArr2[0];
        return iArr;
    }

    public static <T> Collector<T, ?, Long> summingLong(ToLongFunction<? super T> toLongFunction) {
        return new c(ah.a(), ai.a(toLongFunction), aj.a(), ak.a(), e);
    }

    public static /* synthetic */ long[] e() {
        return new long[1];
    }

    public static /* synthetic */ void b(ToLongFunction toLongFunction, long[] jArr, Object obj) {
        jArr[0] = jArr[0] + toLongFunction.applyAsLong(obj);
    }

    public static /* synthetic */ long[] c(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        return jArr;
    }

    public static <T> Collector<T, ?, Double> summingDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        return new c(al.a(), am.a(toDoubleFunction), an.a(), ap.a(), e);
    }

    public static /* synthetic */ double[] d() {
        return new double[3];
    }

    public static /* synthetic */ void b(ToDoubleFunction toDoubleFunction, double[] dArr, Object obj) {
        double applyAsDouble = toDoubleFunction.applyAsDouble(obj);
        a(dArr, applyAsDouble);
        dArr[2] = dArr[2] + applyAsDouble;
    }

    public static /* synthetic */ double[] b(double[] dArr, double[] dArr2) {
        a(dArr, dArr2[0]);
        dArr[2] = dArr[2] + dArr2[2];
        return a(dArr, -dArr2[1]);
    }

    public static double[] a(double[] dArr, double d2) {
        double d3 = d2 - dArr[1];
        double d4 = dArr[0];
        double d5 = d4 + d3;
        dArr[1] = (d5 - d4) - d3;
        dArr[0] = d5;
        return dArr;
    }

    public static double a(double[] dArr) {
        double d2 = dArr[0] - dArr[1];
        double d3 = dArr[dArr.length - 1];
        return (!Double.isNaN(d2) || !Double.isInfinite(d3)) ? d2 : d3;
    }

    public static <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> toIntFunction) {
        return new c(aq.a(), ar.a(toIntFunction), as.a(), at.a(), e);
    }

    public static /* synthetic */ long[] c() {
        return new long[2];
    }

    public static /* synthetic */ void a(ToIntFunction toIntFunction, long[] jArr, Object obj) {
        jArr[0] = jArr[0] + toIntFunction.applyAsInt(obj);
        jArr[1] = jArr[1] + 1;
    }

    public static /* synthetic */ long[] b(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
        return jArr;
    }

    public static <T> Collector<T, ?, Double> averagingLong(ToLongFunction<? super T> toLongFunction) {
        return new c(au.a(), av.a(toLongFunction), aw.a(), ax.a(), e);
    }

    public static /* synthetic */ long[] b() {
        return new long[2];
    }

    public static /* synthetic */ void a(ToLongFunction toLongFunction, long[] jArr, Object obj) {
        jArr[0] = jArr[0] + toLongFunction.applyAsLong(obj);
        jArr[1] = jArr[1] + 1;
    }

    public static /* synthetic */ long[] a(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
        return jArr;
    }

    public static <T> Collector<T, ?, Double> averagingDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        return new c(ay.a(), ba.a(toDoubleFunction), bb.a(), bc.a(), e);
    }

    public static /* synthetic */ double[] a() {
        return new double[4];
    }

    public static /* synthetic */ void a(ToDoubleFunction toDoubleFunction, double[] dArr, Object obj) {
        double applyAsDouble = toDoubleFunction.applyAsDouble(obj);
        a(dArr, applyAsDouble);
        dArr[2] = dArr[2] + 1.0d;
        dArr[3] = dArr[3] + applyAsDouble;
    }

    public static /* synthetic */ double[] a(double[] dArr, double[] dArr2) {
        a(dArr, dArr2[0]);
        a(dArr, -dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
        dArr[3] = dArr[3] + dArr2[3];
        return dArr;
    }

    public static /* synthetic */ Double b(double[] dArr) {
        double d2 = 0.0d;
        if (dArr[2] != 0.0d) {
            d2 = a(dArr) / dArr[2];
        }
        return Double.valueOf(d2);
    }

    public static <T> Collector<T, ?, T> reducing(T t, BinaryOperator<T> binaryOperator) {
        return new c(c(t), bd.a(binaryOperator), be.a(binaryOperator), bf.a(), e);
    }

    public static /* synthetic */ void a(BinaryOperator binaryOperator, Object[] objArr, Object obj) {
        objArr[0] = binaryOperator.apply(objArr[0], obj);
    }

    public static /* synthetic */ Object[] b(BinaryOperator binaryOperator, Object[] objArr, Object[] objArr2) {
        objArr[0] = binaryOperator.apply(objArr[0], objArr2[0]);
        return objArr;
    }

    public static /* synthetic */ Object b(Object[] objArr) {
        return objArr[0];
    }

    public static /* synthetic */ Object[] a(Object obj) {
        return new Object[]{obj};
    }

    private static <T> Supplier<T[]> c(T t) {
        return bg.a(t);
    }

    /* loaded from: classes5.dex */
    public class a implements Consumer<T> {
        T a = null;
        boolean b = false;
        final /* synthetic */ BinaryOperator c;

        a(BinaryOperator binaryOperator) {
            this.c = binaryOperator;
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            if (this.b) {
                this.a = this.c.apply(this.a, t);
                return;
            }
            this.a = t;
            this.b = true;
        }
    }

    public static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> binaryOperator) {
        return new c(bh.a(binaryOperator), bi.a(), bj.a(), bl.a(), e);
    }

    public static /* synthetic */ a a(BinaryOperator binaryOperator) {
        return new a(binaryOperator);
    }

    public static /* synthetic */ a a(a aVar, a aVar2) {
        if (aVar2.b) {
            aVar.accept(aVar2.a);
        }
        return aVar;
    }

    public static <T, U> Collector<T, ?, U> reducing(U u, Function<? super T, ? extends U> function, BinaryOperator<U> binaryOperator) {
        return new c(c(u), bm.a(binaryOperator, function), bn.a(binaryOperator), bo.a(), e);
    }

    public static /* synthetic */ void a(BinaryOperator binaryOperator, Function function, Object[] objArr, Object obj) {
        objArr[0] = binaryOperator.apply(objArr[0], function.apply(obj));
    }

    public static /* synthetic */ Object[] a(BinaryOperator binaryOperator, Object[] objArr, Object[] objArr2) {
        objArr[0] = binaryOperator.apply(objArr[0], objArr2[0]);
        return objArr;
    }

    public static /* synthetic */ Object a(Object[] objArr) {
        return objArr[0];
    }

    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> function) {
        return groupingBy(function, toList());
    }

    public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> function, Collector<? super T, A, D> collector) {
        return groupingBy(function, g(), collector);
    }

    public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> function, Supplier<M> supplier, Collector<? super T, A, D> collector) {
        BiConsumer a2 = bp.a(function, collector.supplier(), collector.accumulator());
        BinaryOperator b2 = b((BinaryOperator) collector.combiner());
        if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new c(supplier, a2, b2, c);
        }
        return new c(supplier, a2, b2, bq.a(collector.finisher()), e);
    }

    public static <T, K> Collector<T, ?, ConcurrentMap<K, List<T>>> groupingByConcurrent(Function<? super T, ? extends K> function) {
        return groupingByConcurrent(function, h(), toList());
    }

    public static <T, K, A, D> Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> function, Collector<? super T, A, D> collector) {
        return groupingByConcurrent(function, h(), collector);
    }

    public static <T, K, A, D, M extends ConcurrentMap<K, D>> Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> function, Supplier<M> supplier, Collector<? super T, A, D> collector) {
        BiConsumer biConsumer;
        Supplier<A> supplier2 = collector.supplier();
        BiConsumer<A, ? super T> accumulator = collector.accumulator();
        BinaryOperator c2 = c((BinaryOperator) collector.combiner());
        if (collector.characteristics().contains(Collector.Characteristics.CONCURRENT)) {
            biConsumer = br.a(function, supplier2, accumulator);
        } else {
            biConsumer = bs.a(function, supplier2, accumulator);
        }
        if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new c(supplier, biConsumer, c2, a);
        }
        return new c(supplier, biConsumer, c2, bt.a(collector.finisher()), b);
    }

    public static /* synthetic */ void a(Function function, Supplier supplier, BiConsumer biConsumer, ConcurrentMap concurrentMap, Object obj) {
        Object computeIfAbsent = ConcurrentMaps.computeIfAbsent(concurrentMap, Objects.requireNonNull(function.apply(obj), "element cannot be mapped to a null key"), cn.a(supplier));
        synchronized (computeIfAbsent) {
            biConsumer.accept(computeIfAbsent, obj);
        }
    }

    public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate) {
        return partitioningBy(predicate, toList());
    }

    public static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> collector) {
        BiConsumer a2 = bu.a(collector.accumulator(), predicate);
        BinaryOperator a3 = bw.a(collector.combiner());
        Supplier a4 = bx.a(collector);
        if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new c(a4, a2, a3, c);
        }
        return new c(a4, a2, a3, by.a(collector), e);
    }

    public static /* synthetic */ d a(BinaryOperator binaryOperator, d dVar, d dVar2) {
        return new d(binaryOperator.apply(dVar.a, dVar2.a), binaryOperator.apply(dVar.b, dVar2.b));
    }

    public static /* synthetic */ d a(Collector collector) {
        return new d(collector.supplier().get(), collector.supplier().get());
    }

    public static /* synthetic */ Map a(Collector collector, d dVar) {
        return new d(collector.finisher().apply(dVar.a), collector.finisher().apply(dVar.b));
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2) {
        return new c(g(), a(function, function2), o(), c);
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toUnmodifiableMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2) {
        Objects.requireNonNull(function, "keyMapper");
        Objects.requireNonNull(function2, "valueMapper");
        return collectingAndThen(toMap(function, function2), l());
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator) {
        return toMap(function, function2, binaryOperator, g());
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toUnmodifiableMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator) {
        Objects.requireNonNull(function, "keyMapper");
        Objects.requireNonNull(function2, "valueMapper");
        Objects.requireNonNull(binaryOperator, "mergeFunction");
        return collectingAndThen(toMap(function, function2, binaryOperator, g()), l());
    }

    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator, Supplier<M> supplier) {
        return new c(supplier, bz.a(function, function2, binaryOperator), b((BinaryOperator) binaryOperator), c);
    }

    public static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2) {
        return new c(i(), a(function, function2), o(), a);
    }

    public static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator) {
        return toConcurrentMap(function, function2, binaryOperator, h());
    }

    public static <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator, Supplier<M> supplier) {
        return new c(supplier, ca.a(function, function2, binaryOperator), c((BinaryOperator) binaryOperator), a);
    }

    public static <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> toIntFunction) {
        return new c(h, cb.a(toIntFunction), cc.a(), c);
    }

    public static <T> Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> toLongFunction) {
        return new c(i, cd.a(toLongFunction), ce.a(), c);
    }

    public static <T> Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        return new c(g, cf.a(toDoubleFunction), ch.a(), c);
    }

    public static <T, R1, R2, R> Collector<T, ?, R> teeing(Collector<? super T, ?, R1> collector, Collector<? super T, ?, R2> collector2, BiFunction<? super R1, ? super R2, R> biFunction) {
        return a((Collector) collector, (Collector) collector2, (BiFunction) biFunction);
    }

    private static <T, A1, A2, R1, R2, R> Collector<T, ?, R> a(Collector<? super T, A1, R1> collector, Collector<? super T, A2, R2> collector2, BiFunction<? super R1, ? super R2, R> biFunction) {
        Set<Collector.Characteristics> set;
        Objects.requireNonNull(collector, "downstream1");
        Objects.requireNonNull(collector2, "downstream2");
        Objects.requireNonNull(biFunction, "merger");
        Supplier supplier = (Supplier) Objects.requireNonNull(collector.supplier(), "downstream1 supplier");
        Supplier supplier2 = (Supplier) Objects.requireNonNull(collector2.supplier(), "downstream2 supplier");
        BiConsumer biConsumer = (BiConsumer) Objects.requireNonNull(collector.accumulator(), "downstream1 accumulator");
        BiConsumer biConsumer2 = (BiConsumer) Objects.requireNonNull(collector2.accumulator(), "downstream2 accumulator");
        BinaryOperator binaryOperator = (BinaryOperator) Objects.requireNonNull(collector.combiner(), "downstream1 combiner");
        BinaryOperator binaryOperator2 = (BinaryOperator) Objects.requireNonNull(collector2.combiner(), "downstream2 combiner");
        Function function = (Function) Objects.requireNonNull(collector.finisher(), "downstream1 finisher");
        Function function2 = (Function) Objects.requireNonNull(collector2.finisher(), "downstream2 finisher");
        Set<Collector.Characteristics> characteristics = collector.characteristics();
        Set<Collector.Characteristics> characteristics2 = collector2.characteristics();
        if (c.containsAll(characteristics) || c.containsAll(characteristics2)) {
            set = e;
        } else {
            EnumSet noneOf = EnumSet.noneOf(Collector.Characteristics.class);
            noneOf.addAll(characteristics);
            noneOf.retainAll(characteristics2);
            noneOf.remove(Collector.Characteristics.IDENTITY_FINISH);
            set = Collections.unmodifiableSet(noneOf);
        }
        return new c(ci.a(supplier, supplier2, biConsumer, biConsumer2, binaryOperator, binaryOperator2, function, function2, biFunction), cj.a(), ck.a(), cl.a(), set);
    }

    /* loaded from: classes5.dex */
    public class b {
        A1 a;
        A2 b;
        final /* synthetic */ Supplier c;
        final /* synthetic */ Supplier d;
        final /* synthetic */ BiConsumer e;
        final /* synthetic */ BiConsumer f;
        final /* synthetic */ BinaryOperator g;
        final /* synthetic */ BinaryOperator h;
        final /* synthetic */ Function i;
        final /* synthetic */ Function j;
        final /* synthetic */ BiFunction k;

        /* JADX WARN: Type inference failed for: r1v2, types: [A1, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r1v4, types: [A2, java.lang.Object] */
        b(Supplier supplier, Supplier supplier2, BiConsumer biConsumer, BiConsumer biConsumer2, BinaryOperator binaryOperator, BinaryOperator binaryOperator2, Function function, Function function2, BiFunction biFunction) {
            this.c = supplier;
            this.d = supplier2;
            this.e = biConsumer;
            this.f = biConsumer2;
            this.g = binaryOperator;
            this.h = binaryOperator2;
            this.i = function;
            this.j = function2;
            this.k = biFunction;
            this.a = this.c.get();
            this.b = this.d.get();
        }

        public void a(T t) {
            this.e.accept(this.a, t);
            this.f.accept(this.b, t);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [A1, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r4v2, types: [A2, java.lang.Object] */
        public b a(b bVar) {
            this.a = this.g.apply(this.a, bVar.a);
            this.b = this.h.apply(this.b, bVar.b);
            return this;
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [R, java.lang.Object] */
        public R a() {
            return this.k.apply(this.i.apply(this.a), this.j.apply(this.b));
        }
    }

    public static /* synthetic */ b a(Supplier supplier, Supplier supplier2, BiConsumer biConsumer, BiConsumer biConsumer2, BinaryOperator binaryOperator, BinaryOperator binaryOperator2, Function function, Function function2, BiFunction biFunction) {
        return new b(supplier, supplier2, biConsumer, biConsumer2, binaryOperator, binaryOperator2, function, function2, biFunction);
    }

    /* loaded from: classes5.dex */
    public static final class d<T> extends AbstractMap<Boolean, T> implements Map<Boolean, T> {
        final T a;
        final T b;

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return 2;
        }

        d(T t, T t2) {
            this.a = t;
            this.b = t2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<Boolean, T>> entrySet() {
            return new AbstractSet<Map.Entry<Boolean, T>>() { // from class: java8.util.stream.Collectors.d.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return 2;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<Boolean, T>> iterator() {
                    return Arrays.asList(new AbstractMap.SimpleImmutableEntry(false, d.this.b), new AbstractMap.SimpleImmutableEntry(true, d.this.a)).iterator();
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return obj instanceof Boolean;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public T get(Object obj) {
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue() ? this.a : this.b;
            }
            return null;
        }
    }
}
