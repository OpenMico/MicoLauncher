package io.reactivex.rxjava3.internal.functions;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.functions.Function4;
import io.reactivex.rxjava3.functions.Function5;
import io.reactivex.rxjava3.functions.Function6;
import io.reactivex.rxjava3.functions.Function7;
import io.reactivex.rxjava3.functions.Function8;
import io.reactivex.rxjava3.functions.Function9;
import io.reactivex.rxjava3.functions.LongConsumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class Functions {
    static final Function<Object, Object> a = new w();
    public static final Runnable EMPTY_RUNNABLE = new q();
    public static final Action EMPTY_ACTION = new n();
    static final Consumer<Object> b = new o();
    public static final Consumer<Throwable> ERROR_CONSUMER = new s();
    public static final Consumer<Throwable> ON_ERROR_MISSING = new af();
    public static final LongConsumer EMPTY_LONG_CONSUMER = new p();
    static final Predicate<Object> c = new ak();
    static final Predicate<Object> d = new t();
    static final Supplier<Object> e = new ae();
    public static final Consumer<Subscription> REQUEST_MAX = new z();

    private Functions() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static <T1, T2, R> Function<Object[], R> toFunction(@NonNull BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        return new b(biFunction);
    }

    @NonNull
    public static <T1, T2, T3, R> Function<Object[], R> toFunction(@NonNull Function3<T1, T2, T3, R> function3) {
        return new c(function3);
    }

    @NonNull
    public static <T1, T2, T3, T4, R> Function<Object[], R> toFunction(@NonNull Function4<T1, T2, T3, T4, R> function4) {
        return new d(function4);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, R> Function<Object[], R> toFunction(@NonNull Function5<T1, T2, T3, T4, T5, R> function5) {
        return new e(function5);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, R> Function<Object[], R> toFunction(@NonNull Function6<T1, T2, T3, T4, T5, T6, R> function6) {
        return new f(function6);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, T7, R> Function<Object[], R> toFunction(@NonNull Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
        return new g(function7);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function<Object[], R> toFunction(@NonNull Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
        return new h(function8);
    }

    @NonNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function<Object[], R> toFunction(@NonNull Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
        return new i(function9);
    }

    @NonNull
    public static <T> Function<T, T> identity() {
        return (Function<T, T>) a;
    }

    public static <T> Consumer<T> emptyConsumer() {
        return (Consumer<T>) b;
    }

    @NonNull
    public static <T> Predicate<T> alwaysTrue() {
        return (Predicate<T>) c;
    }

    @NonNull
    public static <T> Predicate<T> alwaysFalse() {
        return (Predicate<T>) d;
    }

    @NonNull
    public static <T> Supplier<T> nullSupplier() {
        return (Supplier<T>) e;
    }

    /* loaded from: classes4.dex */
    public static final class u implements Action {
        final Future<?> a;

        u(Future<?> future) {
            this.a = future;
        }

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() throws Exception {
            this.a.get();
        }
    }

    @NonNull
    public static Action futureAction(@NonNull Future<?> future) {
        return new u(future);
    }

    /* loaded from: classes4.dex */
    public static final class x<T, U> implements Function<T, U>, Supplier<U>, Callable<U> {
        final U a;

        x(U u) {
            this.a = u;
        }

        @Override // java.util.concurrent.Callable
        public U call() {
            return this.a;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public U apply(T t) {
            return this.a;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public U get() {
            return this.a;
        }
    }

    @NonNull
    public static <T> Callable<T> justCallable(@NonNull T t2) {
        return new x(t2);
    }

    @NonNull
    public static <T> Supplier<T> justSupplier(@NonNull T t2) {
        return new x(t2);
    }

    @NonNull
    public static <T, U> Function<T, U> justFunction(@NonNull U u2) {
        return new x(u2);
    }

    /* loaded from: classes4.dex */
    public static final class l<T, U> implements Function<T, U> {
        final Class<U> a;

        l(Class<U> cls) {
            this.a = cls;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public U apply(T t) {
            return this.a.cast(t);
        }
    }

    @NonNull
    public static <T, U> Function<T, U> castFunction(@NonNull Class<U> cls) {
        return new l(cls);
    }

    /* loaded from: classes4.dex */
    public static final class j<T> implements Supplier<List<T>> {
        final int a;

        j(int i) {
            this.a = i;
        }

        /* renamed from: a */
        public List<T> get() {
            return new ArrayList(this.a);
        }
    }

    public static <T> Supplier<List<T>> createArrayList(int i2) {
        return new j(i2);
    }

    /* loaded from: classes4.dex */
    public static final class r<T> implements Predicate<T> {
        final T a;

        r(T t) {
            this.a = t;
        }

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(T t) {
            return Objects.equals(t, this.a);
        }
    }

    public static <T> Predicate<T> equalsWith(T t2) {
        return new r(t2);
    }

    /* loaded from: classes4.dex */
    public enum v implements Supplier<Set<Object>> {
        INSTANCE;

        /* renamed from: a */
        public Set<Object> get() {
            return new HashSet();
        }
    }

    public static <T> Supplier<Set<T>> createHashSet() {
        return v.INSTANCE;
    }

    /* loaded from: classes4.dex */
    public static final class ad<T> implements Consumer<T> {
        final Consumer<? super Notification<T>> a;

        ad(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(T t) throws Throwable {
            this.a.accept(Notification.createOnNext(t));
        }
    }

    /* loaded from: classes4.dex */
    public static final class ac<T> implements Consumer<Throwable> {
        final Consumer<? super Notification<T>> a;

        ac(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public void accept(Throwable th) throws Throwable {
            this.a.accept(Notification.createOnError(th));
        }
    }

    /* loaded from: classes4.dex */
    public static final class ab<T> implements Action {
        final Consumer<? super Notification<T>> a;

        ab(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() throws Throwable {
            this.a.accept(Notification.createOnComplete());
        }
    }

    public static <T> Consumer<T> notificationOnNext(Consumer<? super Notification<T>> consumer) {
        return new ad(consumer);
    }

    public static <T> Consumer<Throwable> notificationOnError(Consumer<? super Notification<T>> consumer) {
        return new ac(consumer);
    }

    public static <T> Action notificationOnComplete(Consumer<? super Notification<T>> consumer) {
        return new ab(consumer);
    }

    /* loaded from: classes4.dex */
    public static final class a<T> implements Consumer<T> {
        final Action a;

        a(Action action) {
            this.a = action;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(T t) throws Throwable {
            this.a.run();
        }
    }

    public static <T> Consumer<T> actionConsumer(Action action) {
        return new a(action);
    }

    /* loaded from: classes4.dex */
    public static final class m<T, U> implements Predicate<T> {
        final Class<U> a;

        m(Class<U> cls) {
            this.a = cls;
        }

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(T t) {
            return this.a.isInstance(t);
        }
    }

    public static <T, U> Predicate<T> isInstanceOf(Class<U> cls) {
        return new m(cls);
    }

    /* loaded from: classes4.dex */
    public static final class k<T> implements Predicate<T> {
        final BooleanSupplier a;

        k(BooleanSupplier booleanSupplier) {
            this.a = booleanSupplier;
        }

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(T t) throws Throwable {
            return !this.a.getAsBoolean();
        }
    }

    public static <T> Predicate<T> predicateReverseFor(BooleanSupplier booleanSupplier) {
        return new k(booleanSupplier);
    }

    /* loaded from: classes4.dex */
    public static final class ag<T> implements Function<T, Timed<T>> {
        final TimeUnit a;
        final Scheduler b;

        ag(TimeUnit timeUnit, Scheduler scheduler) {
            this.a = timeUnit;
            this.b = scheduler;
        }

        /* renamed from: a */
        public Timed<T> apply(T t) {
            return new Timed<>(t, this.b.now(this.a), this.a);
        }
    }

    public static <T> Function<T, Timed<T>> timestampWith(TimeUnit timeUnit, Scheduler scheduler) {
        return new ag(timeUnit, scheduler);
    }

    /* loaded from: classes4.dex */
    public static final class ah<K, T> implements BiConsumer<Map<K, T>, T> {
        private final Function<? super T, ? extends K> a;

        ah(Function<? super T, ? extends K> function) {
            this.a = function;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void accept(Map<K, T> map, T t) throws Throwable {
            map.put(this.a.apply(t), t);
        }
    }

    public static <T, K> BiConsumer<Map<K, T>, T> toMapKeySelector(Function<? super T, ? extends K> function) {
        return new ah(function);
    }

    /* loaded from: classes4.dex */
    public static final class ai<K, V, T> implements BiConsumer<Map<K, V>, T> {
        private final Function<? super T, ? extends V> a;
        private final Function<? super T, ? extends K> b;

        ai(Function<? super T, ? extends V> function, Function<? super T, ? extends K> function2) {
            this.a = function;
            this.b = function2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void accept(Map<K, V> map, T t) throws Throwable {
            map.put(this.b.apply(t), this.a.apply(t));
        }
    }

    public static <T, K, V> BiConsumer<Map<K, V>, T> toMapKeyValueSelector(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return new ai(function2, function);
    }

    /* loaded from: classes4.dex */
    public static final class aj<K, V, T> implements BiConsumer<Map<K, Collection<V>>, T> {
        private final Function<? super K, ? extends Collection<? super V>> a;
        private final Function<? super T, ? extends V> b;
        private final Function<? super T, ? extends K> c;

        aj(Function<? super K, ? extends Collection<? super V>> function, Function<? super T, ? extends V> function2, Function<? super T, ? extends K> function3) {
            this.a = function;
            this.b = function2;
            this.c = function3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void accept(Map<K, Collection<V>> map, T t) throws Throwable {
            Object apply = this.c.apply(t);
            Collection collection = (Collection) map.get(apply);
            if (collection == null) {
                collection = (Collection) this.a.apply(apply);
                map.put(apply, collection);
            }
            collection.add(this.b.apply(t));
        }
    }

    public static <T, K, V> BiConsumer<Map<K, Collection<V>>, T> toMultimapKeyValueSelector(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, Function<? super K, ? extends Collection<? super V>> function3) {
        return new aj(function3, function2, function);
    }

    /* loaded from: classes4.dex */
    public enum aa implements Comparator<Object> {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    public static <T> Comparator<T> naturalComparator() {
        return aa.INSTANCE;
    }

    /* loaded from: classes4.dex */
    public static final class y<T> implements Function<List<T>, List<T>> {
        final Comparator<? super T> a;

        y(Comparator<? super T> comparator) {
            this.a = comparator;
        }

        /* renamed from: a */
        public List<T> apply(List<T> list) {
            Collections.sort(list, this.a);
            return list;
        }
    }

    public static <T> Function<List<T>, List<T>> listSorter(Comparator<? super T> comparator) {
        return new y(comparator);
    }

    /* loaded from: classes4.dex */
    public static final class b<T1, T2, R> implements Function<Object[], R> {
        final BiFunction<? super T1, ? super T2, ? extends R> a;

        b(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
            this.a = biFunction;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 2) {
                return (R) this.a.apply(objArr[0], objArr[1]);
            }
            throw new IllegalArgumentException("Array of size 2 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    public static final class c<T1, T2, T3, R> implements Function<Object[], R> {
        final Function3<T1, T2, T3, R> a;

        c(Function3<T1, T2, T3, R> function3) {
            this.a = function3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 3) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2]);
            }
            throw new IllegalArgumentException("Array of size 3 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    public static final class d<T1, T2, T3, T4, R> implements Function<Object[], R> {
        final Function4<T1, T2, T3, T4, R> a;

        d(Function4<T1, T2, T3, T4, R> function4) {
            this.a = function4;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 4) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3]);
            }
            throw new IllegalArgumentException("Array of size 4 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    public static final class e<T1, T2, T3, T4, T5, R> implements Function<Object[], R> {
        private final Function5<T1, T2, T3, T4, T5, R> a;

        e(Function5<T1, T2, T3, T4, T5, R> function5) {
            this.a = function5;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 5) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            }
            throw new IllegalArgumentException("Array of size 5 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    public static final class f<T1, T2, T3, T4, T5, T6, R> implements Function<Object[], R> {
        final Function6<T1, T2, T3, T4, T5, T6, R> a;

        f(Function6<T1, T2, T3, T4, T5, T6, R> function6) {
            this.a = function6;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 6) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            }
            throw new IllegalArgumentException("Array of size 6 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    public static final class g<T1, T2, T3, T4, T5, T6, T7, R> implements Function<Object[], R> {
        final Function7<T1, T2, T3, T4, T5, T6, T7, R> a;

        g(Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
            this.a = function7;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 7) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            }
            throw new IllegalArgumentException("Array of size 7 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    public static final class h<T1, T2, T3, T4, T5, T6, T7, T8, R> implements Function<Object[], R> {
        final Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> a;

        h(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
            this.a = function8;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 8) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7]);
            }
            throw new IllegalArgumentException("Array of size 8 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    public static final class i<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> implements Function<Object[], R> {
        final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> a;

        i(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
            this.a = function9;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public R apply(Object[] objArr) throws Throwable {
            if (objArr.length == 9) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            }
            throw new IllegalArgumentException("Array of size 9 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    static final class w implements Function<Object, Object> {
        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            return obj;
        }

        public String toString() {
            return "IdentityFunction";
        }

        w() {
        }
    }

    /* loaded from: classes4.dex */
    static final class q implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
        }

        public String toString() {
            return "EmptyRunnable";
        }

        q() {
        }
    }

    /* loaded from: classes4.dex */
    static final class n implements Action {
        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
        }

        public String toString() {
            return "EmptyAction";
        }

        n() {
        }
    }

    /* loaded from: classes4.dex */
    static final class o implements Consumer<Object> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Object obj) {
        }

        public String toString() {
            return "EmptyConsumer";
        }

        o() {
        }
    }

    /* loaded from: classes4.dex */
    static final class s implements Consumer<Throwable> {
        s() {
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            RxJavaPlugins.onError(th);
        }
    }

    /* loaded from: classes4.dex */
    static final class af implements Consumer<Throwable> {
        af() {
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(th));
        }
    }

    /* loaded from: classes4.dex */
    static final class p implements LongConsumer {
        @Override // io.reactivex.rxjava3.functions.LongConsumer
        public void accept(long j) {
        }

        p() {
        }
    }

    /* loaded from: classes4.dex */
    static final class ak implements Predicate<Object> {
        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return true;
        }

        ak() {
        }
    }

    /* loaded from: classes4.dex */
    static final class t implements Predicate<Object> {
        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return false;
        }

        t() {
        }
    }

    /* loaded from: classes4.dex */
    static final class ae implements Supplier<Object> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Object get() {
            return null;
        }

        ae() {
        }
    }

    /* loaded from: classes4.dex */
    static final class z implements Consumer<Subscription> {
        z() {
        }

        /* renamed from: a */
        public void accept(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public static <T> Consumer<T> boundedConsumer(int i2) {
        return new BoundedConsumer(i2);
    }

    /* loaded from: classes4.dex */
    public static class BoundedConsumer implements Consumer<Subscription> {
        final int a;

        BoundedConsumer(int i) {
            this.a = i;
        }

        public void accept(Subscription subscription) {
            subscription.request(this.a);
        }
    }
}
