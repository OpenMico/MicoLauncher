package java8.util.concurrent;

import com.umeng.analytics.pro.ai;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;
import java8.util.Objects;
import java8.util.concurrent.ForkJoinPool;
import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;
import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.function.Supplier;
import sun.misc.Unsafe;

/* loaded from: classes5.dex */
public class CompletableFuture<T> implements Future<T>, CompletionStage<T> {
    private static final boolean a;
    static final a d = new a(null);
    private static final Executor e;
    private static final Unsafe f;
    private static final long g;
    private static final long h;
    private static final long i;
    volatile Object b;
    volatile l c;

    /* loaded from: classes5.dex */
    public interface AsynchronousCompletionTask {
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<T> toCompletableFuture() {
        return this;
    }

    final boolean a(Object obj) {
        return f.compareAndSwapObject(this, g, (Object) null, obj);
    }

    final boolean a(l lVar, l lVar2) {
        return f.compareAndSwapObject(this, h, lVar, lVar2);
    }

    final boolean a(l lVar) {
        l lVar2 = this.c;
        b(lVar, lVar2);
        return f.compareAndSwapObject(this, h, lVar2, lVar);
    }

    final void b(l lVar) {
        do {
        } while (!a(lVar));
    }

    /* loaded from: classes5.dex */
    public static final class a {
        final Throwable a;

        a(Throwable th) {
            this.a = th;
        }
    }

    static {
        boolean z2 = true;
        if (ForkJoinPool.getCommonPoolParallelism() <= 1) {
            z2 = false;
        }
        a = z2;
        e = a ? ForkJoinPool.commonPool() : new v();
        f = d.a;
        try {
            g = f.objectFieldOffset(CompletableFuture.class.getDeclaredField("b"));
            h = f.objectFieldOffset(CompletableFuture.class.getDeclaredField(ai.aD));
            i = f.objectFieldOffset(l.class.getDeclaredField("next"));
        } catch (Exception e2) {
            throw new ExceptionInInitializerError(e2);
        }
    }

    final boolean a() {
        return f.compareAndSwapObject(this, g, (Object) null, d);
    }

    final Object b(T t2) {
        return t2 == null ? d : t2;
    }

    final boolean c(T t2) {
        Unsafe unsafe = f;
        long j2 = g;
        if (t2 == null) {
            t2 = (T) d;
        }
        return unsafe.compareAndSwapObject(this, j2, (Object) null, t2);
    }

    static a a(Throwable th) {
        if (!(th instanceof CompletionException)) {
            th = new CompletionException(th);
        }
        return new a(th);
    }

    final boolean b(Throwable th) {
        return f.compareAndSwapObject(this, g, (Object) null, a(th));
    }

    static Object a(Throwable th, Object obj) {
        if (!(th instanceof CompletionException)) {
            th = new CompletionException(th);
        } else if ((obj instanceof a) && th == ((a) obj).a) {
            return obj;
        }
        return new a(th);
    }

    final boolean b(Throwable th, Object obj) {
        return f.compareAndSwapObject(this, g, (Object) null, a(th, obj));
    }

    static Object d(Object obj) {
        Throwable th;
        return (!(obj instanceof a) || (th = ((a) obj).a) == null || (th instanceof CompletionException)) ? obj : new a(new CompletionException(th));
    }

    final boolean e(Object obj) {
        return f.compareAndSwapObject(this, g, (Object) null, d(obj));
    }

    private static Object f(Object obj) throws InterruptedException, ExecutionException {
        Throwable cause;
        if (obj == null) {
            throw new InterruptedException();
        } else if (!(obj instanceof a)) {
            return obj;
        } else {
            Throwable th = ((a) obj).a;
            if (th == null) {
                return null;
            }
            if (!(th instanceof CancellationException)) {
                if ((th instanceof CompletionException) && (cause = th.getCause()) != null) {
                    th = cause;
                }
                throw new ExecutionException(th);
            }
            throw ((CancellationException) th);
        }
    }

    private static Object g(Object obj) {
        if (!(obj instanceof a)) {
            return obj;
        }
        Throwable th = ((a) obj).a;
        if (th == null) {
            return null;
        }
        if (th instanceof CancellationException) {
            throw ((CancellationException) th);
        } else if (th instanceof CompletionException) {
            throw ((CompletionException) th);
        } else {
            throw new CompletionException(th);
        }
    }

    /* loaded from: classes5.dex */
    static final class v implements Executor {
        v() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    static Executor a(Executor executor) {
        if (a || executor != ForkJoinPool.commonPool()) {
            return (Executor) Objects.requireNonNull(executor);
        }
        return e;
    }

    /* loaded from: classes5.dex */
    public static abstract class l extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask {
        volatile l next;

        abstract CompletableFuture<?> a(int i);

        /* renamed from: a */
        public final void setRawResult(Void r1) {
        }

        abstract boolean a();

        /* renamed from: b */
        public final Void getRawResult() {
            return null;
        }

        l() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            a(1);
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final boolean exec() {
            a(1);
            return false;
        }
    }

    static void b(l lVar, l lVar2) {
        f.putOrderedObject(lVar, i, lVar2);
    }

    static boolean a(l lVar, l lVar2, l lVar3) {
        return f.compareAndSwapObject(lVar, i, lVar2, lVar3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    final void b() {
        CompletableFuture completableFuture = this;
        while (true) {
            l lVar = completableFuture.c;
            completableFuture = completableFuture;
            if (lVar == null) {
                if (completableFuture != this && (lVar = this.c) != null) {
                    completableFuture = this;
                } else {
                    return;
                }
            }
            l lVar2 = lVar.next;
            if (completableFuture.a(lVar, lVar2)) {
                if (lVar2 != null) {
                    if (completableFuture != this) {
                        b(lVar);
                    } else {
                        a(lVar, lVar2, (l) null);
                    }
                }
                completableFuture = lVar.a(-1);
                if (completableFuture == null) {
                    completableFuture = this;
                }
            }
        }
    }

    final void c() {
        l lVar;
        boolean z2 = false;
        while (true) {
            lVar = this.c;
            if (lVar == null || lVar.a()) {
                break;
            }
            z2 = a(lVar, lVar.next);
        }
        if (lVar != null && !z2) {
            l lVar2 = lVar.next;
            l lVar3 = lVar;
            while (lVar2 != null) {
                l lVar4 = lVar2.next;
                if (lVar2.a()) {
                    lVar3 = lVar2;
                    lVar2 = lVar4;
                } else {
                    a(lVar3, lVar2, lVar4);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static abstract class z<T, V> extends l {
        CompletableFuture<V> dep;
        Executor executor;
        CompletableFuture<T> src;

        z(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2) {
            this.executor = executor;
            this.dep = completableFuture;
            this.src = completableFuture2;
        }

        final boolean c() {
            Executor executor = this.executor;
            if (compareAndSetForkJoinTaskTag((short) 0, (short) 1)) {
                if (executor == null) {
                    return true;
                }
                this.executor = null;
                executor.execute(this);
            }
            return false;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final boolean a() {
            return this.dep != null;
        }
    }

    final void c(l lVar) {
        if (lVar != null) {
            while (true) {
                if (!a(lVar)) {
                    if (this.b != null) {
                        b(lVar, (l) null);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (this.b != null) {
                lVar.a(0);
            }
        }
    }

    final CompletableFuture<T> a(CompletableFuture<?> completableFuture, int i2) {
        if (!(completableFuture == null || completableFuture.c == null)) {
            Object obj = completableFuture.b;
            if (obj == null) {
                completableFuture.c();
            }
            if (i2 >= 0 && !(obj == null && completableFuture.b == null)) {
                completableFuture.b();
            }
        }
        if (this.b == null || this.c == null) {
            return null;
        }
        if (i2 < 0) {
            return this;
        }
        b();
        return null;
    }

    /* loaded from: classes5.dex */
    public static final class y<T, V> extends z<T, V> {
        Function<? super T, ? extends V> fn;

        y(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, Function<? super T, ? extends V> function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<V> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            Function<? super T, ? extends V> function;
            CompletableFuture completableFuture2 = this.src;
            if (completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == 0 || (function = this.fn) == null) {
                return null;
            }
            if (completableFuture.b == null) {
                if (obj instanceof a) {
                    Throwable th = ((a) obj).a;
                    if (th != null) {
                        completableFuture.b(th, obj);
                    } else {
                        obj = null;
                    }
                }
                if (i <= 0) {
                    try {
                        if (!c()) {
                            return null;
                        }
                    } catch (Throwable th2) {
                        completableFuture.b(th2);
                    }
                }
                completableFuture.c((CompletableFuture) function.apply(obj));
            }
            this.src = null;
            this.dep = null;
            this.fn = null;
            return completableFuture.a(completableFuture2, i);
        }
    }

    private <V> CompletableFuture<V> a(Executor executor, Function<? super T, ? extends V> function) {
        Objects.requireNonNull(function);
        Object obj = this.b;
        if (obj != null) {
            return a(obj, executor, function);
        }
        CompletableFuture<V> completableFuture = (CompletableFuture<V>) newIncompleteFuture();
        c((l) new y(executor, completableFuture, this, function));
        return completableFuture;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <V> CompletableFuture<V> a(Object obj, Executor executor, Function<? super T, ? extends V> function) {
        CompletableFuture<V> completableFuture = (CompletableFuture<V>) newIncompleteFuture();
        if (obj instanceof a) {
            Throwable th = ((a) obj).a;
            if (th != null) {
                completableFuture.b = a(th, obj);
                return completableFuture;
            }
            obj = null;
        }
        try {
            if (executor != null) {
                executor.execute(new y(null, completableFuture, this, function));
            } else {
                completableFuture.b = completableFuture.b((CompletableFuture<V>) function.apply(obj));
            }
        } catch (Throwable th2) {
            completableFuture.b = a(th2);
        }
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static final class x<T> extends z<T, Void> {
        Consumer<? super T> fn;

        x(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, Consumer<? super T> consumer) {
            super(executor, completableFuture, completableFuture2);
            this.fn = consumer;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Void> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            Consumer<? super T> consumer;
            CompletableFuture<?> completableFuture2 = this.src;
            if (completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (consumer = this.fn) == null) {
                return null;
            }
            if (completableFuture.b == null) {
                if (obj instanceof a) {
                    Throwable th = ((a) obj).a;
                    if (th != null) {
                        completableFuture.b(th, obj);
                    } else {
                        obj = null;
                    }
                }
                if (i <= 0) {
                    try {
                        if (!c()) {
                            return null;
                        }
                    } catch (Throwable th2) {
                        completableFuture.b(th2);
                    }
                }
                consumer.accept(obj);
                completableFuture.a();
            }
            this.src = null;
            this.dep = null;
            this.fn = null;
            return completableFuture.a(completableFuture2, i);
        }
    }

    private CompletableFuture<Void> a(Executor executor, Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        Object obj = this.b;
        if (obj != null) {
            return a(obj, executor, consumer);
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        c((l) new x(executor, newIncompleteFuture, this, consumer));
        return newIncompleteFuture;
    }

    private CompletableFuture<Void> a(Object obj, Executor executor, Consumer<? super T> consumer) {
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        if (obj instanceof a) {
            Throwable th = ((a) obj).a;
            if (th != null) {
                newIncompleteFuture.b = a(th, obj);
                return newIncompleteFuture;
            }
            obj = null;
        }
        try {
            if (executor != null) {
                executor.execute(new x(null, newIncompleteFuture, this, consumer));
            } else {
                consumer.accept(obj);
                newIncompleteFuture.b = d;
            }
        } catch (Throwable th2) {
            newIncompleteFuture.b = a(th2);
        }
        return newIncompleteFuture;
    }

    /* loaded from: classes5.dex */
    public static final class af<T> extends z<T, Void> {
        Runnable fn;

        af(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, Runnable runnable) {
            super(executor, completableFuture, completableFuture2);
            this.fn = runnable;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Void> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            Runnable runnable;
            Throwable th;
            CompletableFuture<?> completableFuture2 = this.src;
            if (completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (runnable = this.fn) == null) {
                return null;
            }
            if (completableFuture.b == null) {
                if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
                    if (i <= 0) {
                        try {
                            if (!c()) {
                                return null;
                            }
                        } catch (Throwable th2) {
                            completableFuture.b(th2);
                        }
                    }
                    runnable.run();
                    completableFuture.a();
                } else {
                    completableFuture.b(th, obj);
                }
            }
            this.src = null;
            this.dep = null;
            this.fn = null;
            return completableFuture.a(completableFuture2, i);
        }
    }

    private CompletableFuture<Void> b(Executor executor, Runnable runnable) {
        Objects.requireNonNull(runnable);
        Object obj = this.b;
        if (obj != null) {
            return a(obj, executor, runnable);
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        c((l) new af(executor, newIncompleteFuture, this, runnable));
        return newIncompleteFuture;
    }

    private CompletableFuture<Void> a(Object obj, Executor executor, Runnable runnable) {
        Throwable th;
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
            try {
                if (executor != null) {
                    executor.execute(new af(null, newIncompleteFuture, this, runnable));
                } else {
                    runnable.run();
                    newIncompleteFuture.b = d;
                }
            } catch (Throwable th2) {
                newIncompleteFuture.b = a(th2);
            }
        } else {
            newIncompleteFuture.b = a(th, obj);
        }
        return newIncompleteFuture;
    }

    /* loaded from: classes5.dex */
    public static final class ag<T> extends z<T, T> {
        BiConsumer<? super T, ? super Throwable> fn;

        ag(Executor executor, CompletableFuture<T> completableFuture, CompletableFuture<T> completableFuture2, BiConsumer<? super T, ? super Throwable> biConsumer) {
            super(executor, completableFuture, completableFuture2);
            this.fn = biConsumer;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<T> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            BiConsumer<? super T, ? super Throwable> biConsumer;
            CompletableFuture<?> completableFuture2 = this.src;
            if (!(completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (biConsumer = this.fn) == null)) {
                if (completableFuture.a(obj, biConsumer, i > 0 ? null : this)) {
                    this.src = null;
                    this.dep = null;
                    this.fn = null;
                    return completableFuture.a(completableFuture2, i);
                }
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x002d  */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final boolean a(java.lang.Object r3, java8.util.function.BiConsumer<? super T, ? super java.lang.Throwable> r4, java8.util.concurrent.CompletableFuture.ag<T> r5) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.b
            r1 = 1
            if (r0 != 0) goto L_0x0031
            r0 = 0
            if (r5 == 0) goto L_0x0010
            boolean r5 = r5.c()     // Catch: Throwable -> 0x0028
            if (r5 != 0) goto L_0x0010
            r3 = 0
            return r3
        L_0x0010:
            boolean r5 = r3 instanceof java8.util.concurrent.CompletableFuture.a     // Catch: Throwable -> 0x0028
            if (r5 == 0) goto L_0x001a
            r5 = r3
            java8.util.concurrent.CompletableFuture$a r5 = (java8.util.concurrent.CompletableFuture.a) r5     // Catch: Throwable -> 0x0028
            java.lang.Throwable r5 = r5.a     // Catch: Throwable -> 0x0028
            goto L_0x001c
        L_0x001a:
            r5 = r0
            r0 = r3
        L_0x001c:
            r4.accept(r0, r5)     // Catch: Throwable -> 0x0025
            if (r5 != 0) goto L_0x002e
            r2.a(r3)     // Catch: Throwable -> 0x0025
            return r1
        L_0x0025:
            r4 = move-exception
            r0 = r5
            goto L_0x0029
        L_0x0028:
            r4 = move-exception
        L_0x0029:
            r5 = r4
            if (r0 != 0) goto L_0x002d
            goto L_0x002e
        L_0x002d:
            r5 = r0
        L_0x002e:
            r2.b(r5, r3)
        L_0x0031:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.CompletableFuture.a(java.lang.Object, java8.util.function.BiConsumer, java8.util.concurrent.CompletableFuture$ag):boolean");
    }

    private CompletableFuture<T> a(Executor executor, BiConsumer<? super T, ? super Throwable> biConsumer) {
        Objects.requireNonNull(biConsumer);
        CompletableFuture<T> completableFuture = (CompletableFuture<T>) newIncompleteFuture();
        Object obj = this.b;
        if (obj == null) {
            c((l) new ag(executor, completableFuture, this, biConsumer));
        } else if (executor == null) {
            completableFuture.a(obj, biConsumer, (ag) null);
        } else {
            try {
                executor.execute(new ag(null, completableFuture, this, biConsumer));
            } catch (Throwable th) {
                completableFuture.b = a(th);
            }
        }
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static final class ad<T, V> extends z<T, V> {
        BiFunction<? super T, Throwable, ? extends V> fn;

        ad(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, BiFunction<? super T, Throwable, ? extends V> biFunction) {
            super(executor, completableFuture, completableFuture2);
            this.fn = biFunction;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<V> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            BiFunction<? super T, Throwable, ? extends V> biFunction;
            CompletableFuture<?> completableFuture2 = this.src;
            if (!(completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (biFunction = this.fn) == null)) {
                if (completableFuture.a(obj, biFunction, i > 0 ? null : this)) {
                    this.src = null;
                    this.dep = null;
                    this.fn = null;
                    return completableFuture.a(completableFuture2, i);
                }
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    final <S> boolean a(Object obj, BiFunction<? super S, Throwable, ? extends T> biFunction, ad<S, T> adVar) {
        if (this.b != null) {
            return true;
        }
        if (adVar != null) {
            try {
                if (!adVar.c()) {
                    return false;
                }
            } catch (Throwable th) {
                b(th);
                return true;
            }
        }
        Throwable th2 = null;
        if (obj instanceof a) {
            th2 = ((a) obj).a;
            obj = null;
        }
        c((CompletableFuture<T>) biFunction.apply(obj, th2));
        return true;
    }

    private <V> CompletableFuture<V> a(Executor executor, BiFunction<? super T, Throwable, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        CompletableFuture<V> completableFuture = (CompletableFuture<V>) newIncompleteFuture();
        Object obj = this.b;
        if (obj == null) {
            c((l) new ad(executor, completableFuture, this, biFunction));
        } else if (executor == null) {
            completableFuture.a(obj, (BiFunction<? super S, Throwable, ? extends V>) biFunction, (ad<S, V>) null);
        } else {
            try {
                executor.execute(new ad(null, completableFuture, this, biFunction));
            } catch (Throwable th) {
                completableFuture.b = a(th);
            }
        }
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static final class ac<T> extends z<T, T> {
        Function<? super Throwable, ? extends T> fn;

        ac(Executor executor, CompletableFuture<T> completableFuture, CompletableFuture<T> completableFuture2, Function<? super Throwable, ? extends T> function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<T> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            Function<? super Throwable, ? extends T> function;
            CompletableFuture<?> completableFuture2 = this.src;
            if (!(completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (function = this.fn) == null)) {
                if (completableFuture.a(obj, function, i > 0 ? null : this)) {
                    this.src = null;
                    this.dep = null;
                    this.fn = null;
                    return completableFuture.a(completableFuture2, i);
                }
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    final boolean a(Object obj, Function<? super Throwable, ? extends T> function, ac<T> acVar) {
        Throwable th;
        if (this.b != null) {
            return true;
        }
        if (acVar != null) {
            try {
                if (!acVar.c()) {
                    return false;
                }
            } catch (Throwable th2) {
                b(th2);
                return true;
            }
        }
        if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
            a(obj);
            return true;
        }
        c((CompletableFuture<T>) function.apply(th));
        return true;
    }

    private CompletableFuture<T> b(Executor executor, Function<Throwable, ? extends T> function) {
        Objects.requireNonNull(function);
        CompletableFuture<T> completableFuture = (CompletableFuture<T>) newIncompleteFuture();
        Object obj = this.b;
        if (obj == null) {
            c((l) new ac(executor, completableFuture, this, function));
        } else if (executor == null) {
            completableFuture.a(obj, function, (ac) null);
        } else {
            try {
                executor.execute(new ac(null, completableFuture, this, function));
            } catch (Throwable th) {
                completableFuture.b = a(th);
            }
        }
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static final class ab<T> extends z<T, T> {
        Function<Throwable, ? extends CompletionStage<T>> fn;

        ab(Executor executor, CompletableFuture<T> completableFuture, CompletableFuture<T> completableFuture2, Function<Throwable, ? extends CompletionStage<T>> function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<T> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            Function<Throwable, ? extends CompletionStage<T>> function;
            Throwable th;
            CompletableFuture<?> completableFuture2 = this.src;
            if (completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (function = this.fn) == null) {
                return null;
            }
            if (completableFuture.b == null) {
                if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
                    completableFuture.a(obj);
                } else {
                    if (i <= 0) {
                        try {
                            if (!c()) {
                                return null;
                            }
                        } catch (Throwable th2) {
                            completableFuture.b(th2);
                        }
                    }
                    CompletableFuture<T> completableFuture3 = ((CompletionStage) function.apply(th)).toCompletableFuture();
                    Object obj2 = completableFuture3.b;
                    if (obj2 != null) {
                        completableFuture.e(obj2);
                    } else {
                        completableFuture3.c((l) new ae(completableFuture, completableFuture3));
                        if (completableFuture.b == null) {
                            return null;
                        }
                    }
                }
            }
            this.src = null;
            this.dep = null;
            this.fn = null;
            return completableFuture.a(completableFuture2, i);
        }
    }

    private CompletableFuture<T> c(Executor executor, Function<Throwable, ? extends CompletionStage<T>> function) {
        Throwable th;
        Objects.requireNonNull(function);
        CompletableFuture<T> completableFuture = (CompletableFuture<T>) newIncompleteFuture();
        Object obj = this.b;
        if (obj == null) {
            c((l) new ab(executor, completableFuture, this, function));
        } else if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
            completableFuture.a(obj);
        } else {
            try {
                if (executor != null) {
                    executor.execute(new ab(null, completableFuture, this, function));
                } else {
                    CompletableFuture<T> completableFuture2 = ((CompletionStage) function.apply(th)).toCompletableFuture();
                    Object obj2 = completableFuture2.b;
                    if (obj2 != null) {
                        completableFuture.b = d(obj2);
                    } else {
                        completableFuture2.c((l) new ae(completableFuture, completableFuture2));
                    }
                }
            } catch (Throwable th2) {
                completableFuture.b = a(th2);
            }
        }
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static final class ae<U, T extends U> extends z<T, U> {
        ae(CompletableFuture<U> completableFuture, CompletableFuture<T> completableFuture2) {
            super(null, completableFuture, completableFuture2);
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<U> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            CompletableFuture<?> completableFuture2 = this.src;
            if (completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null) {
                return null;
            }
            if (completableFuture.b == null) {
                completableFuture.e(obj);
            }
            this.src = null;
            this.dep = null;
            return completableFuture.a(completableFuture2, i);
        }
    }

    private static <U, T extends U> CompletableFuture<U> a(CompletableFuture<T> completableFuture) {
        CompletableFuture<U> newIncompleteFuture = completableFuture.newIncompleteFuture();
        Object obj = completableFuture.b;
        if (obj != null) {
            newIncompleteFuture.b = d(obj);
        } else {
            completableFuture.c((l) new ae(newIncompleteFuture, completableFuture));
        }
        return newIncompleteFuture;
    }

    private p<T> d() {
        Object obj = this.b;
        if (obj != null) {
            return new p<>(d(obj));
        }
        p<T> pVar = new p<>();
        c((l) new ae(pVar, this));
        return pVar;
    }

    /* loaded from: classes5.dex */
    public static final class aa<T, V> extends z<T, V> {
        Function<? super T, ? extends CompletionStage<V>> fn;

        aa(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, Function<? super T, ? extends CompletionStage<V>> function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<V> a(int i) {
            Object obj;
            CompletableFuture completableFuture;
            Function<? super T, ? extends CompletionStage<V>> function;
            CompletableFuture<?> completableFuture2 = this.src;
            if (completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (function = this.fn) == null) {
                return null;
            }
            if (completableFuture.b == null) {
                if (obj instanceof a) {
                    Throwable th = ((a) obj).a;
                    if (th != null) {
                        completableFuture.b(th, obj);
                    } else {
                        obj = null;
                    }
                }
                if (i <= 0) {
                    try {
                        if (!c()) {
                            return null;
                        }
                    } catch (Throwable th2) {
                        completableFuture.b(th2);
                    }
                }
                CompletableFuture<T> completableFuture3 = ((CompletionStage) function.apply(obj)).toCompletableFuture();
                Object obj2 = completableFuture3.b;
                if (obj2 != null) {
                    completableFuture.e(obj2);
                } else {
                    completableFuture3.c((l) new ae(completableFuture, completableFuture3));
                    if (completableFuture.b == null) {
                        return null;
                    }
                }
            }
            this.src = null;
            this.dep = null;
            this.fn = null;
            return completableFuture.a(completableFuture2, i);
        }
    }

    private <V> CompletableFuture<V> d(Executor executor, Function<? super T, ? extends CompletionStage<V>> function) {
        Objects.requireNonNull(function);
        CompletableFuture<V> completableFuture = (CompletableFuture<V>) newIncompleteFuture();
        Object obj = (Object) this.b;
        if (obj == null) {
            c((l) new aa(executor, completableFuture, this, function));
        } else {
            if (obj instanceof a) {
                Throwable th = ((a) obj).a;
                if (th != null) {
                    completableFuture.b = a(th, obj);
                    return completableFuture;
                }
                obj = (Object) null;
            }
            try {
                if (executor != null) {
                    executor.execute(new aa(null, completableFuture, this, function));
                } else {
                    CompletableFuture<T> completableFuture2 = ((CompletionStage) function.apply(obj)).toCompletableFuture();
                    Object obj2 = completableFuture2.b;
                    if (obj2 != null) {
                        completableFuture.b = d(obj2);
                    } else {
                        completableFuture2.c((l) new ae(completableFuture, completableFuture2));
                    }
                }
            } catch (Throwable th2) {
                completableFuture.b = a(th2);
            }
        }
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static abstract class g<T, U, V> extends z<T, V> {
        CompletableFuture<U> snd;

        g(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3) {
            super(executor, completableFuture, completableFuture2);
            this.snd = completableFuture3;
        }
    }

    /* loaded from: classes5.dex */
    public static final class k extends l {
        g<?, ?, ?> base;

        k(g<?, ?, ?> gVar) {
            this.base = gVar;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<?> a(int i) {
            CompletableFuture<?> a;
            g<?, ?, ?> gVar = this.base;
            if (gVar == null || (a = gVar.a(i)) == null) {
                return null;
            }
            this.base = null;
            return a;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final boolean a() {
            g<?, ?, ?> gVar = this.base;
            return (gVar == null || gVar.dep == null) ? false : true;
        }
    }

    final void a(CompletableFuture<?> completableFuture, g<?, ?, ?> gVar) {
        if (gVar != null) {
            while (this.b == null) {
                if (a((l) gVar)) {
                    if (completableFuture.b == null) {
                        completableFuture.c((l) new k(gVar));
                        return;
                    } else if (this.b != null) {
                        gVar.a(0);
                        return;
                    } else {
                        return;
                    }
                }
            }
            completableFuture.c((l) gVar);
        }
    }

    final CompletableFuture<T> a(CompletableFuture<?> completableFuture, CompletableFuture<?> completableFuture2, int i2) {
        if (!(completableFuture2 == null || completableFuture2.c == null)) {
            Object obj = completableFuture2.b;
            if (obj == null) {
                completableFuture2.c();
            }
            if (i2 >= 0 && !(obj == null && completableFuture2.b == null)) {
                completableFuture2.b();
            }
        }
        return a(completableFuture, i2);
    }

    /* loaded from: classes5.dex */
    public static final class f<T, U, V> extends g<T, U, V> {
        BiFunction<? super T, ? super U, ? extends V> fn;

        f(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, BiFunction<? super T, ? super U, ? extends V> biFunction) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = biFunction;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<V> a(int i) {
            Object obj;
            CompletableFuture<?> completableFuture;
            Object obj2;
            CompletableFuture completableFuture2;
            BiFunction<? super T, ? super U, ? extends V> biFunction;
            CompletableFuture<?> completableFuture3 = this.src;
            if (!(completableFuture3 == null || (obj = completableFuture3.b) == null || (completableFuture = this.snd) == null || (obj2 = completableFuture.b) == null || (completableFuture2 = this.dep) == null || (biFunction = this.fn) == null)) {
                if (completableFuture2.a(obj, obj2, biFunction, i > 0 ? null : this)) {
                    this.src = null;
                    this.snd = null;
                    this.dep = null;
                    this.fn = null;
                    return completableFuture2.a(completableFuture3, completableFuture, i);
                }
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    final <R, S> boolean a(Object obj, Object obj2, BiFunction<? super R, ? super S, ? extends T> biFunction, f<R, S, T> fVar) {
        if (this.b != null) {
            return true;
        }
        if (obj instanceof a) {
            Throwable th = ((a) obj).a;
            if (th != null) {
                b(th, obj);
                return true;
            }
            obj = null;
        }
        if (obj2 instanceof a) {
            Throwable th2 = ((a) obj2).a;
            if (th2 != null) {
                b(th2, obj2);
                return true;
            }
            obj2 = null;
        }
        if (fVar != null) {
            try {
                if (!fVar.c()) {
                    return false;
                }
            } catch (Throwable th3) {
                b(th3);
                return true;
            }
        }
        c((CompletableFuture<T>) biFunction.apply(obj, obj2));
        return true;
    }

    private <U, V> CompletableFuture<V> a(Executor executor, CompletionStage<U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        CompletableFuture<U> completableFuture;
        Object obj;
        if (biFunction == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        CompletableFuture completableFuture2 = (CompletableFuture<U>) newIncompleteFuture();
        Object obj2 = this.b;
        if (obj2 == null || (obj = completableFuture.b) == null) {
            a((CompletableFuture<?>) completableFuture, (g<?, ?, ?>) new f(executor, completableFuture2, this, completableFuture, biFunction));
        } else if (executor == null) {
            completableFuture2.a(obj2, obj, biFunction, (f) null);
        } else {
            try {
                executor.execute(new f(null, completableFuture2, this, completableFuture, biFunction));
            } catch (Throwable th) {
                completableFuture2.b = a(th);
            }
        }
        return completableFuture2;
    }

    /* loaded from: classes5.dex */
    public static final class e<T, U> extends g<T, U, Void> {
        BiConsumer<? super T, ? super U> fn;

        e(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, BiConsumer<? super T, ? super U> biConsumer) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = biConsumer;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Void> a(int i) {
            Object obj;
            CompletableFuture<?> completableFuture;
            Object obj2;
            CompletableFuture completableFuture2;
            BiConsumer<? super T, ? super U> biConsumer;
            CompletableFuture<?> completableFuture3 = this.src;
            if (!(completableFuture3 == null || (obj = completableFuture3.b) == null || (completableFuture = this.snd) == null || (obj2 = completableFuture.b) == null || (completableFuture2 = this.dep) == null || (biConsumer = this.fn) == null)) {
                if (completableFuture2.a(obj, obj2, biConsumer, i > 0 ? null : this)) {
                    this.src = null;
                    this.snd = null;
                    this.dep = null;
                    this.fn = null;
                    return completableFuture2.a(completableFuture3, completableFuture, i);
                }
            }
            return null;
        }
    }

    final <R, S> boolean a(Object obj, Object obj2, BiConsumer<? super R, ? super S> biConsumer, e<R, S> eVar) {
        if (this.b != null) {
            return true;
        }
        if (obj instanceof a) {
            Throwable th = ((a) obj).a;
            if (th != null) {
                b(th, obj);
                return true;
            }
            obj = null;
        }
        if (obj2 instanceof a) {
            Throwable th2 = ((a) obj2).a;
            if (th2 != null) {
                b(th2, obj2);
                return true;
            }
            obj2 = null;
        }
        if (eVar != null) {
            try {
                if (!eVar.c()) {
                    return false;
                }
            } catch (Throwable th3) {
                b(th3);
                return true;
            }
        }
        biConsumer.accept(obj, obj2);
        a();
        return true;
    }

    private <U> CompletableFuture<Void> a(Executor executor, CompletionStage<U> completionStage, BiConsumer<? super T, ? super U> biConsumer) {
        CompletableFuture<U> completableFuture;
        Object obj;
        if (biConsumer == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        CompletableFuture completableFuture2 = (CompletableFuture<U>) newIncompleteFuture();
        Object obj2 = this.b;
        if (obj2 == null || (obj = completableFuture.b) == null) {
            a((CompletableFuture<?>) completableFuture, (g<?, ?, ?>) new e(executor, completableFuture2, this, completableFuture, biConsumer));
        } else if (executor == null) {
            completableFuture2.a(obj2, obj, biConsumer, (e) null);
        } else {
            try {
                executor.execute(new e(null, completableFuture2, this, completableFuture, biConsumer));
            } catch (Throwable th) {
                completableFuture2.b = a(th);
            }
        }
        return completableFuture2;
    }

    /* loaded from: classes5.dex */
    public static final class i<T, U> extends g<T, U, Void> {
        Runnable fn;

        i(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Runnable runnable) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = runnable;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Void> a(int i) {
            Object obj;
            CompletableFuture<?> completableFuture;
            Object obj2;
            CompletableFuture completableFuture2;
            Runnable runnable;
            CompletableFuture<?> completableFuture3 = this.src;
            if (!(completableFuture3 == null || (obj = completableFuture3.b) == null || (completableFuture = this.snd) == null || (obj2 = completableFuture.b) == null || (completableFuture2 = this.dep) == null || (runnable = this.fn) == null)) {
                if (completableFuture2.a(obj, obj2, runnable, (i<?, ?>) (i > 0 ? null : this))) {
                    this.src = null;
                    this.snd = null;
                    this.dep = null;
                    this.fn = null;
                    return completableFuture2.a(completableFuture3, completableFuture, i);
                }
            }
            return null;
        }
    }

    final boolean a(Object obj, Object obj2, Runnable runnable, i<?, ?> iVar) {
        Throwable th;
        if (this.b != null) {
            return true;
        }
        if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
            if (!(obj2 instanceof a) || (th = ((a) obj2).a) == null) {
                if (iVar != null) {
                    try {
                        if (!iVar.c()) {
                            return false;
                        }
                    } catch (Throwable th2) {
                        b(th2);
                        return true;
                    }
                }
                runnable.run();
                a();
                return true;
            }
            obj = obj2;
        }
        b(th, obj);
        return true;
    }

    private CompletableFuture<Void> a(Executor executor, CompletionStage<?> completionStage, Runnable runnable) {
        CompletableFuture<?> completableFuture;
        Object obj;
        if (runnable == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj2 = this.b;
        if (obj2 == null || (obj = completableFuture.b) == null) {
            a(completableFuture, new i(executor, newIncompleteFuture, this, completableFuture, runnable));
        } else if (executor == null) {
            newIncompleteFuture.a(obj2, obj, runnable, (i<?, ?>) null);
        } else {
            try {
                executor.execute(new i(null, newIncompleteFuture, this, completableFuture, runnable));
            } catch (Throwable th) {
                newIncompleteFuture.b = a(th);
            }
        }
        return newIncompleteFuture;
    }

    /* loaded from: classes5.dex */
    public static final class h<T, U> extends g<T, U, Void> {
        h(CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3) {
            super(null, completableFuture, completableFuture2, completableFuture3);
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Void> a(int i) {
            Object obj;
            CompletableFuture<?> completableFuture;
            Object obj2;
            CompletableFuture completableFuture2;
            Throwable th;
            CompletableFuture<?> completableFuture3 = this.src;
            if (completableFuture3 == null || (obj = completableFuture3.b) == null || (completableFuture = this.snd) == null || (obj2 = completableFuture.b) == null || (completableFuture2 = this.dep) == null) {
                return null;
            }
            if (completableFuture2.b == null) {
                if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
                    if (!(obj2 instanceof a) || (th = ((a) obj2).a) == null) {
                        completableFuture2.a();
                    } else {
                        obj = obj2;
                    }
                }
                completableFuture2.b(th, obj);
            }
            this.src = null;
            this.snd = null;
            this.dep = null;
            return completableFuture2.a(completableFuture3, completableFuture, i);
        }
    }

    static CompletableFuture<Void> a(CompletableFuture<?>[] completableFutureArr, int i2, int i3) {
        CompletableFuture<?> completableFuture;
        CompletableFuture<?> completableFuture2;
        Object obj;
        Throwable th;
        CompletableFuture<Void> completableFuture3 = new CompletableFuture<>();
        if (i2 > i3) {
            completableFuture3.b = d;
        } else {
            int i4 = (i2 + i3) >>> 1;
            if (i2 == i4) {
                completableFuture = completableFutureArr[i2];
            } else {
                completableFuture = a(completableFutureArr, i2, i4);
            }
            if (completableFuture != null) {
                if (i2 == i3) {
                    completableFuture2 = completableFuture;
                } else {
                    int i5 = i4 + 1;
                    completableFuture2 = i3 == i5 ? completableFutureArr[i3] : a(completableFutureArr, i5, i3);
                }
                if (completableFuture2 != null) {
                    Object obj2 = completableFuture.b;
                    if (obj2 == null || (obj = completableFuture2.b) == null) {
                        completableFuture.a(completableFuture2, new h(completableFuture3, completableFuture, completableFuture2));
                    } else {
                        if (!(obj2 instanceof a) || (th = ((a) obj2).a) == null) {
                            if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
                                completableFuture3.b = d;
                            } else {
                                obj2 = obj;
                            }
                        }
                        completableFuture3.b = a(th, obj2);
                    }
                }
            }
            throw new NullPointerException();
        }
        return completableFuture3;
    }

    final void b(CompletableFuture<?> completableFuture, g<?, ?, ?> gVar) {
        if (gVar != null) {
            while (true) {
                if (!a((l) gVar)) {
                    if (this.b != null) {
                        b(gVar, (l) null);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (this.b != null) {
                gVar.a(0);
            } else {
                completableFuture.c((l) new k(gVar));
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class r<T, U extends T, V> extends g<T, U, V> {
        Function<? super T, ? extends V> fn;

        r(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Function<? super T, ? extends V> function) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = function;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<V> a(int i) {
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2;
            Function<? super T, ? extends V> function;
            CompletableFuture completableFuture3 = this.src;
            if (completableFuture3 == null || (completableFuture = this.snd) == null || (((obj = completableFuture3.b) == null && (obj = completableFuture.b) == null) || (completableFuture2 = this.dep) == 0 || (function = this.fn) == null)) {
                return null;
            }
            if (completableFuture2.b == null) {
                if (i <= 0) {
                    try {
                        if (!c()) {
                            return null;
                        }
                    } catch (Throwable th) {
                        completableFuture2.b(th);
                    }
                }
                if (obj instanceof a) {
                    Throwable th2 = ((a) obj).a;
                    if (th2 != null) {
                        completableFuture2.b(th2, obj);
                    } else {
                        obj = null;
                    }
                }
                completableFuture2.c((CompletableFuture) function.apply(obj));
            }
            this.src = null;
            this.snd = null;
            this.dep = null;
            this.fn = null;
            return completableFuture2.a(completableFuture3, completableFuture, i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [java8.util.concurrent.CompletableFuture] */
    /* JADX WARN: Type inference failed for: r10v3 */
    private <U extends T, V> CompletableFuture<V> a(Executor executor, CompletionStage<U> completionStage, Function<? super T, ? extends V> function) {
        CompletableFuture<U> completableFuture;
        CompletableFuture<U> completableFuture2;
        if (function == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        Object obj = this.b;
        if (obj == null) {
            obj = completableFuture.b;
            completableFuture2 = completableFuture;
            if (obj == null) {
                CompletableFuture<V> completableFuture3 = (CompletableFuture<V>) newIncompleteFuture();
                b((CompletableFuture<?>) completableFuture, (g<?, ?, ?>) new r(executor, completableFuture3, this, completableFuture, function));
                return completableFuture3;
            }
        } else {
            completableFuture2 = this;
        }
        return completableFuture2.a(obj, executor, function);
    }

    /* loaded from: classes5.dex */
    public static final class q<T, U extends T> extends g<T, U, Void> {
        Consumer<? super T> fn;

        q(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Consumer<? super T> consumer) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = consumer;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Void> a(int i) {
            CompletableFuture<?> completableFuture;
            Object obj;
            CompletableFuture completableFuture2;
            Consumer<? super T> consumer;
            CompletableFuture<?> completableFuture3 = this.src;
            if (completableFuture3 == null || (completableFuture = this.snd) == null || (((obj = completableFuture3.b) == null && (obj = completableFuture.b) == null) || (completableFuture2 = this.dep) == null || (consumer = this.fn) == null)) {
                return null;
            }
            if (completableFuture2.b == null) {
                if (i <= 0) {
                    try {
                        if (!c()) {
                            return null;
                        }
                    } catch (Throwable th) {
                        completableFuture2.b(th);
                    }
                }
                if (obj instanceof a) {
                    Throwable th2 = ((a) obj).a;
                    if (th2 != null) {
                        completableFuture2.b(th2, obj);
                    } else {
                        obj = null;
                    }
                }
                consumer.accept(obj);
                completableFuture2.a();
            }
            this.src = null;
            this.snd = null;
            this.dep = null;
            this.fn = null;
            return completableFuture2.a(completableFuture3, completableFuture, i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [java8.util.concurrent.CompletableFuture] */
    /* JADX WARN: Type inference failed for: r10v3 */
    private <U extends T> CompletableFuture<Void> a(Executor executor, CompletionStage<U> completionStage, Consumer<? super T> consumer) {
        CompletableFuture<U> completableFuture;
        CompletableFuture<U> completableFuture2;
        if (consumer == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        Object obj = this.b;
        if (obj == null) {
            obj = completableFuture.b;
            completableFuture2 = completableFuture;
            if (obj == null) {
                CompletableFuture newIncompleteFuture = newIncompleteFuture();
                b((CompletableFuture<?>) completableFuture, (g<?, ?, ?>) new q(executor, newIncompleteFuture, this, completableFuture, consumer));
                return newIncompleteFuture;
            }
        } else {
            completableFuture2 = this;
        }
        return completableFuture2.a(obj, executor, consumer);
    }

    /* loaded from: classes5.dex */
    public static final class s<T, U> extends g<T, U, Void> {
        Runnable fn;

        s(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Runnable runnable) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = runnable;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Void> a(int i) {
            Runnable runnable;
            CompletableFuture<?> completableFuture;
            CompletableFuture<?> completableFuture2;
            Object obj;
            Throwable th;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 == null || (runnable = this.fn) == null || (completableFuture = this.src) == null || (completableFuture2 = this.snd) == null || ((obj = completableFuture.b) == null && (obj = completableFuture2.b) == null)) {
                return null;
            }
            if (completableFuture3.b == null) {
                if (i <= 0) {
                    try {
                        if (!c()) {
                            return null;
                        }
                    } catch (Throwable th2) {
                        completableFuture3.b(th2);
                    }
                }
                if (!(obj instanceof a) || (th = ((a) obj).a) == null) {
                    runnable.run();
                    completableFuture3.a();
                } else {
                    completableFuture3.b(th, obj);
                }
            }
            this.src = null;
            this.snd = null;
            this.dep = null;
            this.fn = null;
            return completableFuture3.a(completableFuture, completableFuture2, i);
        }
    }

    private CompletableFuture<Void> b(Executor executor, CompletionStage<?> completionStage, Runnable runnable) {
        CompletableFuture<?> completableFuture;
        if (runnable == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        Object obj = this.b;
        if (obj == null) {
            obj = completableFuture.b;
            if (obj == null) {
                CompletableFuture newIncompleteFuture = newIncompleteFuture();
                b(completableFuture, new s(executor, newIncompleteFuture, this, completableFuture, runnable));
                return newIncompleteFuture;
            }
        } else {
            completableFuture = this;
        }
        return completableFuture.a(obj, executor, runnable);
    }

    /* loaded from: classes5.dex */
    static class b extends l {
        CompletableFuture<Object> dep;
        CompletableFuture<?> src;
        CompletableFuture<?>[] srcs;

        b(CompletableFuture<Object> completableFuture, CompletableFuture<?> completableFuture2, CompletableFuture<?>[] completableFutureArr) {
            this.dep = completableFuture;
            this.src = completableFuture2;
            this.srcs = completableFutureArr;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<Object> a(int i) {
            Object obj;
            CompletableFuture<Object> completableFuture;
            CompletableFuture<?>[] completableFutureArr;
            CompletableFuture<?> completableFuture2 = this.src;
            if (completableFuture2 == null || (obj = completableFuture2.b) == null || (completableFuture = this.dep) == null || (completableFutureArr = this.srcs) == null) {
                return null;
            }
            this.src = null;
            this.dep = null;
            this.srcs = null;
            if (completableFuture.e(obj)) {
                for (CompletableFuture<?> completableFuture3 : completableFutureArr) {
                    if (completableFuture3 != completableFuture2) {
                        completableFuture3.c();
                    }
                }
                if (i < 0) {
                    return completableFuture;
                }
                completableFuture.b();
            }
            return null;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final boolean a() {
            CompletableFuture<Object> completableFuture = this.dep;
            return completableFuture != null && completableFuture.b == null;
        }
    }

    /* loaded from: classes5.dex */
    public static final class d<T> extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask {
        CompletableFuture<T> dep;
        Supplier<? extends T> fn;

        /* renamed from: a */
        public final Void getRawResult() {
            return null;
        }

        /* renamed from: a */
        public final void setRawResult(Void r1) {
        }

        d(CompletableFuture<T> completableFuture, Supplier<? extends T> supplier) {
            this.dep = completableFuture;
            this.fn = supplier;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final boolean exec() {
            run();
            return false;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            Supplier<? extends T> supplier;
            CompletableFuture<T> completableFuture = this.dep;
            if (completableFuture != 0 && (supplier = this.fn) != null) {
                this.dep = null;
                this.fn = null;
                if (completableFuture.b == null) {
                    try {
                        completableFuture.c((CompletableFuture<T>) supplier.get());
                    } catch (Throwable th) {
                        completableFuture.b(th);
                    }
                }
                completableFuture.b();
            }
        }
    }

    static <U> CompletableFuture<U> a(Executor executor, Supplier<U> supplier) {
        Objects.requireNonNull(supplier);
        CompletableFuture<U> completableFuture = new CompletableFuture<>();
        executor.execute(new d(completableFuture, supplier));
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static final class c extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask {
        CompletableFuture<Void> dep;
        Runnable fn;

        /* renamed from: a */
        public final Void getRawResult() {
            return null;
        }

        /* renamed from: a */
        public final void setRawResult(Void r1) {
        }

        c(CompletableFuture<Void> completableFuture, Runnable runnable) {
            this.dep = completableFuture;
            this.fn = runnable;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final boolean exec() {
            run();
            return false;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable;
            CompletableFuture<Void> completableFuture = this.dep;
            if (completableFuture != null && (runnable = this.fn) != null) {
                this.dep = null;
                this.fn = null;
                if (completableFuture.b == null) {
                    try {
                        runnable.run();
                        completableFuture.a();
                    } catch (Throwable th) {
                        completableFuture.b(th);
                    }
                }
                completableFuture.b();
            }
        }
    }

    static CompletableFuture<Void> a(Executor executor, Runnable runnable) {
        Objects.requireNonNull(runnable);
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        executor.execute(new c(completableFuture, runnable));
        return completableFuture;
    }

    /* loaded from: classes5.dex */
    public static final class t extends l implements ForkJoinPool.ManagedBlocker {
        final long deadline;
        boolean interrupted;
        final boolean interruptible;
        long nanos;
        volatile Thread thread = Thread.currentThread();

        t(boolean z, long j, long j2) {
            this.interruptible = z;
            this.nanos = j;
            this.deadline = j2;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final CompletableFuture<?> a(int i) {
            Thread thread = this.thread;
            if (thread != null) {
                this.thread = null;
                LockSupport.unpark(thread);
            }
            return null;
        }

        @Override // java8.util.concurrent.ForkJoinPool.ManagedBlocker
        public boolean isReleasable() {
            if (Thread.interrupted()) {
                this.interrupted = true;
            }
            if (this.interrupted && this.interruptible) {
                return true;
            }
            long j = this.deadline;
            if (j != 0) {
                if (this.nanos <= 0) {
                    return true;
                }
                long nanoTime = j - System.nanoTime();
                this.nanos = nanoTime;
                if (nanoTime <= 0) {
                    return true;
                }
            }
            return this.thread == null;
        }

        @Override // java8.util.concurrent.ForkJoinPool.ManagedBlocker
        public boolean block() {
            while (!isReleasable()) {
                if (this.deadline == 0) {
                    LockSupport.park(this);
                } else {
                    LockSupport.parkNanos(this, this.nanos);
                }
            }
            return true;
        }

        @Override // java8.util.concurrent.CompletableFuture.l
        final boolean a() {
            return this.thread != null;
        }
    }

    private Object a(boolean z2) {
        Object obj;
        boolean z3 = false;
        t tVar = null;
        while (true) {
            obj = this.b;
            if (obj == null) {
                if (tVar != null) {
                    if (z3) {
                        try {
                            ForkJoinPool.managedBlock(tVar);
                        } catch (InterruptedException unused) {
                            tVar.interrupted = true;
                        }
                        if (tVar.interrupted && z2) {
                            break;
                        }
                    } else {
                        z3 = a((l) tVar);
                    }
                } else {
                    tVar = new t(z2, 0L, 0L);
                    if (Thread.currentThread() instanceof ForkJoinWorkerThread) {
                        ForkJoinPool.a(defaultExecutor(), tVar);
                    }
                }
            } else {
                break;
            }
        }
        if (tVar != null && z3) {
            tVar.thread = null;
            if (!z2 && tVar.interrupted) {
                Thread.currentThread().interrupt();
            }
            if (obj == null) {
                c();
            }
        }
        if (!(obj == null && (obj = this.b) == null)) {
            b();
        }
        return obj;
    }

    private Object a(long j2) throws TimeoutException {
        Object obj;
        if (Thread.interrupted()) {
            return null;
        }
        if (j2 > 0) {
            long nanoTime = System.nanoTime() + j2;
            if (nanoTime == 0) {
                nanoTime = 1;
            }
            boolean z2 = false;
            t tVar = null;
            while (true) {
                obj = this.b;
                if (obj != null) {
                    break;
                } else if (tVar == null) {
                    tVar = new t(true, j2, nanoTime);
                    if (Thread.currentThread() instanceof ForkJoinWorkerThread) {
                        ForkJoinPool.a(defaultExecutor(), tVar);
                    }
                } else if (!z2) {
                    z2 = a((l) tVar);
                } else if (tVar.nanos <= 0) {
                    break;
                } else {
                    try {
                        ForkJoinPool.managedBlock(tVar);
                    } catch (InterruptedException unused) {
                        tVar.interrupted = true;
                    }
                    if (tVar.interrupted) {
                        break;
                    }
                }
            }
            if (tVar != null && z2) {
                tVar.thread = null;
                if (obj == null) {
                    c();
                }
            }
            if (!(obj == null && (obj = this.b) == null)) {
                b();
            }
            if (obj != null || (tVar != null && tVar.interrupted)) {
                return obj;
            }
        }
        throw new TimeoutException();
    }

    public CompletableFuture() {
    }

    CompletableFuture(Object obj) {
        this.b = obj;
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return a(e, supplier);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return a(a(executor), supplier);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return a(e, runnable);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return a(a(executor), runnable);
    }

    public static <U> CompletableFuture<U> completedFuture(U u2) {
        if (u2 == null) {
            u2 = (U) d;
        }
        return new CompletableFuture<>(u2);
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.b != null;
    }

    @Override // java.util.concurrent.Future
    public T get() throws InterruptedException, ExecutionException {
        Object obj = this.b;
        if (obj == null) {
            obj = a(true);
        }
        return (T) f(obj);
    }

    @Override // java.util.concurrent.Future
    public T get(long j2, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        long nanos = timeUnit.toNanos(j2);
        Object obj = this.b;
        if (obj == null) {
            obj = a(nanos);
        }
        return (T) f(obj);
    }

    public T join() {
        Object obj = this.b;
        if (obj == null) {
            obj = a(false);
        }
        return (T) g(obj);
    }

    public T getNow(T t2) {
        Object obj = this.b;
        return obj == null ? t2 : (T) g(obj);
    }

    public boolean complete(T t2) {
        boolean c2 = c((CompletableFuture<T>) t2);
        b();
        return c2;
    }

    public boolean completeExceptionally(Throwable th) {
        boolean a2 = a(new a((Throwable) Objects.requireNonNull(th)));
        b();
        return a2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> function) {
        return (CompletableFuture<U>) a((Executor) null, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> function) {
        return (CompletableFuture<U>) a(defaultExecutor(), function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> function, Executor executor) {
        return (CompletableFuture<U>) a(a(executor), function);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> thenAccept(Consumer<? super T> consumer) {
        return a((Executor) null, consumer);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> consumer) {
        return a(defaultExecutor(), consumer);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> consumer, Executor executor) {
        return a(a(executor), consumer);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> thenRun(Runnable runnable) {
        return b((Executor) null, runnable);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> thenRunAsync(Runnable runnable) {
        return b(defaultExecutor(), runnable);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> thenRunAsync(Runnable runnable, Executor executor) {
        return b(a(executor), runnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U, V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        return a((Executor) null, completionStage, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        return a(defaultExecutor(), completionStage, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction, Executor executor) {
        return a(a(executor), completionStage, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> completionStage, BiConsumer<? super T, ? super U> biConsumer) {
        return a((Executor) null, completionStage, biConsumer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> completionStage, BiConsumer<? super T, ? super U> biConsumer) {
        return a(defaultExecutor(), completionStage, biConsumer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> completionStage, BiConsumer<? super T, ? super U> biConsumer, Executor executor) {
        return a(a(executor), completionStage, biConsumer);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> runAfterBoth(CompletionStage<?> completionStage, Runnable runnable) {
        return a((Executor) null, completionStage, runnable);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> completionStage, Runnable runnable) {
        return a(defaultExecutor(), completionStage, runnable);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> completionStage, Runnable runnable, Executor executor) {
        return a(a(executor), completionStage, runnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> completionStage, Function<? super T, U> function) {
        return (CompletableFuture<U>) a((Executor) null, completionStage, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> completionStage, Function<? super T, U> function) {
        return (CompletableFuture<U>) a(defaultExecutor(), completionStage, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> completionStage, Function<? super T, U> function, Executor executor) {
        return (CompletableFuture<U>) a(a(executor), completionStage, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> completionStage, Consumer<? super T> consumer) {
        return a((Executor) null, completionStage, consumer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> completionStage, Consumer<? super T> consumer) {
        return a(defaultExecutor(), completionStage, consumer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> completionStage, Consumer<? super T> consumer, Executor executor) {
        return a(a(executor), completionStage, consumer);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> runAfterEither(CompletionStage<?> completionStage, Runnable runnable) {
        return b(null, completionStage, runnable);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> completionStage, Runnable runnable) {
        return b(defaultExecutor(), completionStage, runnable);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> completionStage, Runnable runnable, Executor executor) {
        return b(a(executor), completionStage, runnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> function) {
        return (CompletableFuture<U>) d(null, function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> function) {
        return (CompletableFuture<U>) d(defaultExecutor(), function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> function, Executor executor) {
        return (CompletableFuture<U>) d(a(executor), function);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> biConsumer) {
        return a((Executor) null, biConsumer);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> biConsumer) {
        return a(defaultExecutor(), biConsumer);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> biConsumer, Executor executor) {
        return a(a(executor), biConsumer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> biFunction) {
        return (CompletableFuture<U>) a((Executor) null, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> biFunction) {
        return (CompletableFuture<U>) a(defaultExecutor(), biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.concurrent.CompletionStage
    public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> biFunction, Executor executor) {
        return (CompletableFuture<U>) a(a(executor), biFunction);
    }

    @Override // java8.util.concurrent.CompletionStage
    public CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> function) {
        return b((Executor) null, function);
    }

    public CompletableFuture<T> exceptionallyAsync(Function<Throwable, ? extends T> function) {
        return b(defaultExecutor(), function);
    }

    public CompletableFuture<T> exceptionallyAsync(Function<Throwable, ? extends T> function, Executor executor) {
        return b(a(executor), function);
    }

    public CompletableFuture<T> exceptionallyCompose(Function<Throwable, ? extends CompletionStage<T>> function) {
        return c(null, function);
    }

    public CompletableFuture<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> function) {
        return c(defaultExecutor(), function);
    }

    public CompletableFuture<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> function, Executor executor) {
        return c(a(executor), function);
    }

    public static CompletableFuture<Void> allOf(CompletableFuture<?>... completableFutureArr) {
        return a(completableFutureArr, 0, completableFutureArr.length - 1);
    }

    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... completableFutureArr) {
        int length = completableFutureArr.length;
        int i2 = 0;
        if (length > 1) {
            for (CompletableFuture<?> completableFuture : completableFutureArr) {
                Object obj = completableFuture.b;
                if (obj != null) {
                    return new CompletableFuture<>(d(obj));
                }
            }
            CompletableFuture[] completableFutureArr2 = (CompletableFuture[]) completableFutureArr.clone();
            CompletableFuture<Object> completableFuture2 = new CompletableFuture<>();
            for (CompletableFuture completableFuture3 : completableFutureArr2) {
                completableFuture3.c((l) new b(completableFuture2, completableFuture3, completableFutureArr2));
            }
            if (completableFuture2.b != null) {
                int length2 = completableFutureArr2.length;
                while (i2 < length2) {
                    if (completableFutureArr2[i2].b != null) {
                        while (true) {
                            i2++;
                            if (i2 < length2) {
                                if (completableFutureArr2[i2].b == null) {
                                    completableFutureArr2[i2].c();
                                }
                            }
                        }
                    }
                    i2++;
                }
            }
            return completableFuture2;
        } else if (length == 0) {
            return new CompletableFuture<>();
        } else {
            return a((CompletableFuture) completableFutureArr[0]);
        }
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z2) {
        boolean z3 = this.b == null && a(new a(new CancellationException()));
        b();
        return z3 || isCancelled();
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        Object obj = this.b;
        return (obj instanceof a) && (((a) obj).a instanceof CancellationException);
    }

    public boolean isCompletedExceptionally() {
        Object obj = this.b;
        return (obj instanceof a) && obj != d;
    }

    public void obtrudeValue(T t2) {
        if (t2 == null) {
            t2 = (T) d;
        }
        this.b = t2;
        b();
    }

    public void obtrudeException(Throwable th) {
        this.b = new a((Throwable) Objects.requireNonNull(th));
        b();
    }

    public int getNumberOfDependents() {
        int i2 = 0;
        for (l lVar = this.c; lVar != null; lVar = lVar.next) {
            i2++;
        }
        return i2;
    }

    public String toString() {
        String str;
        Object obj = this.b;
        int i2 = 0;
        for (l lVar = this.c; lVar != null; lVar = lVar.next) {
            i2++;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        if (obj != null) {
            if (obj instanceof a) {
                a aVar = (a) obj;
                if (aVar.a != null) {
                    str = "[Completed exceptionally: " + aVar.a + "]";
                }
            }
            str = "[Completed normally]";
        } else if (i2 == 0) {
            str = "[Not completed]";
        } else {
            str = "[Not completed, " + i2 + " dependents]";
        }
        sb.append(str);
        return sb.toString();
    }

    public <U> CompletableFuture<U> newIncompleteFuture() {
        return new CompletableFuture<>();
    }

    public Executor defaultExecutor() {
        return e;
    }

    public CompletableFuture<T> copy() {
        return a((CompletableFuture) this);
    }

    public CompletionStage<T> minimalCompletionStage() {
        return d();
    }

    public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor) {
        if (supplier == null || executor == null) {
            throw new NullPointerException();
        }
        executor.execute(new d(this, supplier));
        return this;
    }

    public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier) {
        return completeAsync(supplier, defaultExecutor());
    }

    public CompletableFuture<T> orTimeout(long j2, TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        if (this.b == null) {
            whenComplete((BiConsumer) new j(o.a(new w(this), j2, timeUnit)));
        }
        return this;
    }

    public CompletableFuture<T> completeOnTimeout(T t2, long j2, TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        if (this.b == null) {
            whenComplete((BiConsumer) new j(o.a(new m(this, t2), j2, timeUnit)));
        }
        return this;
    }

    public static Executor delayedExecutor(long j2, TimeUnit timeUnit, Executor executor) {
        if (timeUnit != null && executor != null) {
            return new n(j2, timeUnit, executor);
        }
        throw new NullPointerException();
    }

    public static Executor delayedExecutor(long j2, TimeUnit timeUnit) {
        return new n(j2, (TimeUnit) Objects.requireNonNull(timeUnit), e);
    }

    public static <U> CompletionStage<U> completedStage(U u2) {
        if (u2 == null) {
            u2 = (U) d;
        }
        return new p(u2);
    }

    public static <U> CompletableFuture<U> failedFuture(Throwable th) {
        return new CompletableFuture<>(new a((Throwable) Objects.requireNonNull(th)));
    }

    public static <U> CompletionStage<U> failedStage(Throwable th) {
        return new p(new a((Throwable) Objects.requireNonNull(th)));
    }

    /* loaded from: classes5.dex */
    static final class o {
        static final ScheduledThreadPoolExecutor a = new ScheduledThreadPoolExecutor(1, new a());

        static ScheduledFuture<?> a(Runnable runnable, long j, TimeUnit timeUnit) {
            return a.schedule(runnable, j, timeUnit);
        }

        /* loaded from: classes5.dex */
        static final class a implements ThreadFactory {
            a() {
            }

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                thread.setName("CompletableFutureDelayScheduler");
                return thread;
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class n implements Executor {
        final long a;
        final TimeUnit b;
        final Executor c;

        n(long j, TimeUnit timeUnit, Executor executor) {
            this.a = j;
            this.b = timeUnit;
            this.c = executor;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            o.a(new u(this.c, runnable), this.a, this.b);
        }
    }

    /* loaded from: classes5.dex */
    static final class u implements Runnable {
        final Executor a;
        final Runnable b;

        u(Executor executor, Runnable runnable) {
            this.a = executor;
            this.b = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.execute(this.b);
        }
    }

    /* loaded from: classes5.dex */
    static final class w implements Runnable {
        final CompletableFuture<?> a;

        w(CompletableFuture<?> completableFuture) {
            this.a = completableFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            CompletableFuture<?> completableFuture = this.a;
            if (completableFuture != null && !completableFuture.isDone()) {
                this.a.completeExceptionally(new TimeoutException());
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class m<U> implements Runnable {
        final CompletableFuture<U> a;
        final U b;

        m(CompletableFuture<U> completableFuture, U u) {
            this.a = completableFuture;
            this.b = u;
        }

        @Override // java.lang.Runnable
        public void run() {
            CompletableFuture<U> completableFuture = this.a;
            if (completableFuture != null) {
                completableFuture.complete(this.b);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class j implements BiConsumer<Object, Throwable> {
        final Future<?> a;

        j(Future<?> future) {
            this.a = future;
        }

        /* renamed from: a */
        public void accept(Object obj, Throwable th) {
            Future<?> future;
            if (th == null && (future = this.a) != null && !future.isDone()) {
                this.a.cancel(false);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class p<T> extends CompletableFuture<T> {
        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage acceptEither(CompletionStage completionStage, Consumer consumer) {
            return CompletableFuture.super.acceptEither(completionStage, consumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage acceptEitherAsync(CompletionStage completionStage, Consumer consumer) {
            return CompletableFuture.super.acceptEitherAsync(completionStage, consumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage acceptEitherAsync(CompletionStage completionStage, Consumer consumer, Executor executor) {
            return CompletableFuture.super.acceptEitherAsync(completionStage, consumer, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage applyToEither(CompletionStage completionStage, Function function) {
            return CompletableFuture.super.applyToEither(completionStage, function);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage applyToEitherAsync(CompletionStage completionStage, Function function) {
            return CompletableFuture.super.applyToEitherAsync(completionStage, function);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage applyToEitherAsync(CompletionStage completionStage, Function function, Executor executor) {
            return CompletableFuture.super.applyToEitherAsync(completionStage, function, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage exceptionally(Function function) {
            return CompletableFuture.super.exceptionally(function);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage handle(BiFunction biFunction) {
            return CompletableFuture.super.handle(biFunction);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage handleAsync(BiFunction biFunction) {
            return CompletableFuture.super.handleAsync(biFunction);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage handleAsync(BiFunction biFunction, Executor executor) {
            return CompletableFuture.super.handleAsync(biFunction, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage runAfterBoth(CompletionStage completionStage, Runnable runnable) {
            return CompletableFuture.super.runAfterBoth((CompletionStage<?>) completionStage, runnable);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage runAfterBothAsync(CompletionStage completionStage, Runnable runnable) {
            return CompletableFuture.super.runAfterBothAsync((CompletionStage<?>) completionStage, runnable);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage runAfterBothAsync(CompletionStage completionStage, Runnable runnable, Executor executor) {
            return CompletableFuture.super.runAfterBothAsync((CompletionStage<?>) completionStage, runnable, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage runAfterEither(CompletionStage completionStage, Runnable runnable) {
            return CompletableFuture.super.runAfterEither((CompletionStage<?>) completionStage, runnable);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage runAfterEitherAsync(CompletionStage completionStage, Runnable runnable) {
            return CompletableFuture.super.runAfterEitherAsync((CompletionStage<?>) completionStage, runnable);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage runAfterEitherAsync(CompletionStage completionStage, Runnable runnable, Executor executor) {
            return CompletableFuture.super.runAfterEitherAsync((CompletionStage<?>) completionStage, runnable, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenAccept(Consumer consumer) {
            return CompletableFuture.super.thenAccept(consumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenAcceptAsync(Consumer consumer) {
            return CompletableFuture.super.thenAcceptAsync(consumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenAcceptAsync(Consumer consumer, Executor executor) {
            return CompletableFuture.super.thenAcceptAsync(consumer, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenAcceptBoth(CompletionStage completionStage, BiConsumer biConsumer) {
            return CompletableFuture.super.thenAcceptBoth(completionStage, biConsumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenAcceptBothAsync(CompletionStage completionStage, BiConsumer biConsumer) {
            return CompletableFuture.super.thenAcceptBothAsync(completionStage, biConsumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenAcceptBothAsync(CompletionStage completionStage, BiConsumer biConsumer, Executor executor) {
            return CompletableFuture.super.thenAcceptBothAsync(completionStage, biConsumer, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenApply(Function function) {
            return CompletableFuture.super.thenApply(function);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenApplyAsync(Function function) {
            return CompletableFuture.super.thenApplyAsync(function);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenApplyAsync(Function function, Executor executor) {
            return CompletableFuture.super.thenApplyAsync(function, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenCombine(CompletionStage completionStage, BiFunction biFunction) {
            return CompletableFuture.super.thenCombine(completionStage, biFunction);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenCombineAsync(CompletionStage completionStage, BiFunction biFunction) {
            return CompletableFuture.super.thenCombineAsync(completionStage, biFunction);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenCombineAsync(CompletionStage completionStage, BiFunction biFunction, Executor executor) {
            return CompletableFuture.super.thenCombineAsync(completionStage, biFunction, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenCompose(Function function) {
            return CompletableFuture.super.thenCompose(function);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenComposeAsync(Function function) {
            return CompletableFuture.super.thenComposeAsync(function);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenComposeAsync(Function function, Executor executor) {
            return CompletableFuture.super.thenComposeAsync(function, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenRun(Runnable runnable) {
            return CompletableFuture.super.thenRun(runnable);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenRunAsync(Runnable runnable) {
            return CompletableFuture.super.thenRunAsync(runnable);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage thenRunAsync(Runnable runnable, Executor executor) {
            return CompletableFuture.super.thenRunAsync(runnable, executor);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage whenComplete(BiConsumer biConsumer) {
            return CompletableFuture.super.whenComplete(biConsumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage whenCompleteAsync(BiConsumer biConsumer) {
            return CompletableFuture.super.whenCompleteAsync(biConsumer);
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public /* bridge */ /* synthetic */ CompletionStage whenCompleteAsync(BiConsumer biConsumer, Executor executor) {
            return CompletableFuture.super.whenCompleteAsync(biConsumer, executor);
        }

        p() {
        }

        p(Object obj) {
            super(obj);
        }

        @Override // java8.util.concurrent.CompletableFuture
        public <U> CompletableFuture<U> newIncompleteFuture() {
            return new p();
        }

        @Override // java8.util.concurrent.CompletableFuture, java.util.concurrent.Future
        public T get() {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture, java.util.concurrent.Future
        public T get(long j, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public T getNow(T t) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public T join() {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public boolean complete(T t) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public boolean completeExceptionally(Throwable th) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture, java.util.concurrent.Future
        public boolean cancel(boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public void obtrudeValue(T t) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public void obtrudeException(Throwable th) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture, java.util.concurrent.Future
        public boolean isDone() {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture, java.util.concurrent.Future
        public boolean isCancelled() {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public boolean isCompletedExceptionally() {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public int getNumberOfDependents() {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public CompletableFuture<T> orTimeout(long j, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture
        public CompletableFuture<T> completeOnTimeout(T t, long j, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        @Override // java8.util.concurrent.CompletableFuture, java8.util.concurrent.CompletionStage
        public CompletableFuture<T> toCompletableFuture() {
            Object obj = this.b;
            if (obj != null) {
                return new CompletableFuture<>(d(obj));
            }
            CompletableFuture<T> completableFuture = new CompletableFuture<>();
            c((l) new ae(completableFuture, this));
            return completableFuture;
        }
    }
}
