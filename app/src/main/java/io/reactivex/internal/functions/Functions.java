package io.reactivex.internal.functions;

import io.reactivex.Notification;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Function6;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.functions.Function9;
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    public static final Consumer<Throwable> ON_ERROR_MISSING = new ag();
    public static final LongConsumer EMPTY_LONG_CONSUMER = new p();
    static final Predicate<Object> c = new al();
    static final Predicate<Object> d = new t();
    static final Callable<Object> e = new af();
    static final Comparator<Object> f = new ab();
    public static final Consumer<Subscription> REQUEST_MAX = new z();

    private Functions() {
        throw new IllegalStateException("No instances!");
    }

    public static <T1, T2, R> Function<Object[], R> toFunction(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(biFunction, "f is null");
        return new b(biFunction);
    }

    public static <T1, T2, T3, R> Function<Object[], R> toFunction(Function3<T1, T2, T3, R> function3) {
        ObjectHelper.requireNonNull(function3, "f is null");
        return new c(function3);
    }

    public static <T1, T2, T3, T4, R> Function<Object[], R> toFunction(Function4<T1, T2, T3, T4, R> function4) {
        ObjectHelper.requireNonNull(function4, "f is null");
        return new d(function4);
    }

    public static <T1, T2, T3, T4, T5, R> Function<Object[], R> toFunction(Function5<T1, T2, T3, T4, T5, R> function5) {
        ObjectHelper.requireNonNull(function5, "f is null");
        return new e(function5);
    }

    public static <T1, T2, T3, T4, T5, T6, R> Function<Object[], R> toFunction(Function6<T1, T2, T3, T4, T5, T6, R> function6) {
        ObjectHelper.requireNonNull(function6, "f is null");
        return new f(function6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Function<Object[], R> toFunction(Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
        ObjectHelper.requireNonNull(function7, "f is null");
        return new g(function7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function<Object[], R> toFunction(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
        ObjectHelper.requireNonNull(function8, "f is null");
        return new h(function8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function<Object[], R> toFunction(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
        ObjectHelper.requireNonNull(function9, "f is null");
        return new i(function9);
    }

    public static <T> Function<T, T> identity() {
        return (Function<T, T>) a;
    }

    public static <T> Consumer<T> emptyConsumer() {
        return (Consumer<T>) b;
    }

    public static <T> Predicate<T> alwaysTrue() {
        return (Predicate<T>) c;
    }

    public static <T> Predicate<T> alwaysFalse() {
        return (Predicate<T>) d;
    }

    public static <T> Callable<T> nullSupplier() {
        return (Callable<T>) e;
    }

    public static <T> Comparator<T> naturalOrder() {
        return (Comparator<T>) f;
    }

    /* loaded from: classes4.dex */
    public static final class u implements Action {
        final Future<?> a;

        u(Future<?> future) {
            this.a = future;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
            this.a.get();
        }
    }

    public static Action futureAction(Future<?> future) {
        return new u(future);
    }

    /* loaded from: classes4.dex */
    public static final class x<T, U> implements Function<T, U>, Callable<U> {
        final U a;

        x(U u) {
            this.a = u;
        }

        @Override // java.util.concurrent.Callable
        public U call() throws Exception {
            return this.a;
        }

        @Override // io.reactivex.functions.Function
        public U apply(T t) throws Exception {
            return this.a;
        }
    }

    public static <T> Callable<T> justCallable(T t2) {
        return new x(t2);
    }

    public static <T, U> Function<T, U> justFunction(U u2) {
        return new x(u2);
    }

    /* loaded from: classes4.dex */
    public static final class l<T, U> implements Function<T, U> {
        final Class<U> a;

        l(Class<U> cls) {
            this.a = cls;
        }

        @Override // io.reactivex.functions.Function
        public U apply(T t) throws Exception {
            return this.a.cast(t);
        }
    }

    public static <T, U> Function<T, U> castFunction(Class<U> cls) {
        return new l(cls);
    }

    /* loaded from: classes4.dex */
    public static final class j<T> implements Callable<List<T>> {
        final int a;

        j(int i) {
            this.a = i;
        }

        /* renamed from: a */
        public List<T> call() throws Exception {
            return new ArrayList(this.a);
        }
    }

    public static <T> Callable<List<T>> createArrayList(int i2) {
        return new j(i2);
    }

    /* loaded from: classes4.dex */
    public static final class r<T> implements Predicate<T> {
        final T a;

        r(T t) {
            this.a = t;
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(T t) throws Exception {
            return ObjectHelper.equals(t, this.a);
        }
    }

    public static <T> Predicate<T> equalsWith(T t2) {
        return new r(t2);
    }

    /* loaded from: classes4.dex */
    public enum v implements Callable<Set<Object>> {
        INSTANCE;

        /* renamed from: a */
        public Set<Object> call() throws Exception {
            return new HashSet();
        }
    }

    public static <T> Callable<Set<T>> createHashSet() {
        return v.INSTANCE;
    }

    /* loaded from: classes4.dex */
    public static final class ae<T> implements Consumer<T> {
        final Consumer<? super Notification<T>> a;

        ae(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
            this.a.accept(Notification.createOnNext(t));
        }
    }

    /* loaded from: classes4.dex */
    public static final class ad<T> implements Consumer<Throwable> {
        final Consumer<? super Notification<T>> a;

        ad(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public void accept(Throwable th) throws Exception {
            this.a.accept(Notification.createOnError(th));
        }
    }

    /* loaded from: classes4.dex */
    public static final class ac<T> implements Action {
        final Consumer<? super Notification<T>> a;

        ac(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
            this.a.accept(Notification.createOnComplete());
        }
    }

    public static <T> Consumer<T> notificationOnNext(Consumer<? super Notification<T>> consumer) {
        return new ae(consumer);
    }

    public static <T> Consumer<Throwable> notificationOnError(Consumer<? super Notification<T>> consumer) {
        return new ad(consumer);
    }

    public static <T> Action notificationOnComplete(Consumer<? super Notification<T>> consumer) {
        return new ac(consumer);
    }

    /* loaded from: classes4.dex */
    public static final class a<T> implements Consumer<T> {
        final Action a;

        a(Action action) {
            this.a = action;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
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

        @Override // io.reactivex.functions.Predicate
        public boolean test(T t) throws Exception {
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

        @Override // io.reactivex.functions.Predicate
        public boolean test(T t) throws Exception {
            return !this.a.getAsBoolean();
        }
    }

    public static <T> Predicate<T> predicateReverseFor(BooleanSupplier booleanSupplier) {
        return new k(booleanSupplier);
    }

    /* loaded from: classes4.dex */
    public static final class ah<T> implements Function<T, Timed<T>> {
        final TimeUnit a;
        final Scheduler b;

        ah(TimeUnit timeUnit, Scheduler scheduler) {
            this.a = timeUnit;
            this.b = scheduler;
        }

        /* renamed from: a */
        public Timed<T> apply(T t) throws Exception {
            return new Timed<>(t, this.b.now(this.a), this.a);
        }
    }

    public static <T> Function<T, Timed<T>> timestampWith(TimeUnit timeUnit, Scheduler scheduler) {
        return new ah(timeUnit, scheduler);
    }

    /* loaded from: classes4.dex */
    public static final class ai<K, T> implements BiConsumer<Map<K, T>, T> {
        private final Function<? super T, ? extends K> a;

        ai(Function<? super T, ? extends K> function) {
            this.a = function;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void accept(Map<K, T> map, T t) throws Exception {
            map.put(this.a.apply(t), t);
        }
    }

    public static <T, K> BiConsumer<Map<K, T>, T> toMapKeySelector(Function<? super T, ? extends K> function) {
        return new ai(function);
    }

    /* loaded from: classes4.dex */
    public static final class aj<K, V, T> implements BiConsumer<Map<K, V>, T> {
        private final Function<? super T, ? extends V> a;
        private final Function<? super T, ? extends K> b;

        aj(Function<? super T, ? extends V> function, Function<? super T, ? extends K> function2) {
            this.a = function;
            this.b = function2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void accept(Map<K, V> map, T t) throws Exception {
            map.put(this.b.apply(t), this.a.apply(t));
        }
    }

    public static <T, K, V> BiConsumer<Map<K, V>, T> toMapKeyValueSelector(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return new aj(function2, function);
    }

    /* loaded from: classes4.dex */
    public static final class ak<K, V, T> implements BiConsumer<Map<K, Collection<V>>, T> {
        private final Function<? super K, ? extends Collection<? super V>> a;
        private final Function<? super T, ? extends V> b;
        private final Function<? super T, ? extends K> c;

        ak(Function<? super K, ? extends Collection<? super V>> function, Function<? super T, ? extends V> function2, Function<? super T, ? extends K> function3) {
            this.a = function;
            this.b = function2;
            this.c = function3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void accept(Map<K, Collection<V>> map, T t) throws Exception {
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
        return new ak(function3, function2, function);
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
        public R apply(Object[] objArr) throws Exception {
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
        public R apply(Object[] objArr) throws Exception {
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
        public R apply(Object[] objArr) throws Exception {
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
        public R apply(Object[] objArr) throws Exception {
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
        public R apply(Object[] objArr) throws Exception {
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
        public R apply(Object[] objArr) throws Exception {
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
        public R apply(Object[] objArr) throws Exception {
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
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 9) {
                return (R) this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            }
            throw new IllegalArgumentException("Array of size 9 expected but got " + objArr.length);
        }
    }

    /* loaded from: classes4.dex */
    static final class w implements Function<Object, Object> {
        @Override // io.reactivex.functions.Function
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
        @Override // io.reactivex.functions.Action
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
        @Override // io.reactivex.functions.Consumer
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
    static final class ag implements Consumer<Throwable> {
        ag() {
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(th));
        }
    }

    /* loaded from: classes4.dex */
    static final class p implements LongConsumer {
        @Override // io.reactivex.functions.LongConsumer
        public void accept(long j) {
        }

        p() {
        }
    }

    /* loaded from: classes4.dex */
    static final class al implements Predicate<Object> {
        @Override // io.reactivex.functions.Predicate
        public boolean test(Object obj) {
            return true;
        }

        al() {
        }
    }

    /* loaded from: classes4.dex */
    static final class t implements Predicate<Object> {
        @Override // io.reactivex.functions.Predicate
        public boolean test(Object obj) {
            return false;
        }

        t() {
        }
    }

    /* loaded from: classes4.dex */
    static final class af implements Callable<Object> {
        @Override // java.util.concurrent.Callable
        public Object call() {
            return null;
        }

        af() {
        }
    }

    /* loaded from: classes4.dex */
    static final class ab implements Comparator<Object> {
        ab() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    /* loaded from: classes4.dex */
    static final class z implements Consumer<Subscription> {
        z() {
        }

        /* renamed from: a */
        public void accept(Subscription subscription) throws Exception {
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

        public void accept(Subscription subscription) throws Exception {
            subscription.request(this.a);
        }
    }
}
