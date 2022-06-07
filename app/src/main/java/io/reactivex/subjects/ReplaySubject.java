package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ReplaySubject<T> extends Subject<T> {
    static final c[] c = new c[0];
    static final c[] d = new c[0];
    private static final Object[] f = new Object[0];
    final b<T> a;
    final AtomicReference<c<T>[]> b = new AtomicReference<>(c);
    boolean e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public interface b<T> {
        int a();

        void a(c<T> cVar);

        void a(T t);

        T[] a(T[] tArr);

        @Nullable
        T b();

        void b(Object obj);

        void c();

        boolean compareAndSet(Object obj, Object obj2);

        Object get();
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> create() {
        return new ReplaySubject<>(new g(16));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> create(int i) {
        return new ReplaySubject<>(new g(i));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithSize(int i) {
        return new ReplaySubject<>(new e(i));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return new ReplaySubject<>(new d(Integer.MAX_VALUE, j, timeUnit, scheduler));
    }

    @CheckReturnValue
    @NonNull
    public static <T> ReplaySubject<T> createWithTimeAndSize(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        return new ReplaySubject<>(new d(i, j, timeUnit, scheduler));
    }

    ReplaySubject(b<T> bVar) {
        this.a = bVar;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        c<T> cVar = new c<>(observer, this);
        observer.onSubscribe(cVar);
        if (cVar.cancelled) {
            return;
        }
        if (!a((c) cVar) || !cVar.cancelled) {
            this.a.a((c) cVar);
        } else {
            b(cVar);
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.e) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.e) {
            b<T> bVar = this.a;
            bVar.a((b<T>) t);
            for (c<T> cVar : this.b.get()) {
                bVar.a((c) cVar);
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.e) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.e = true;
        Object error = NotificationLite.error(th);
        b<T> bVar = this.a;
        bVar.b(error);
        for (c<T> cVar : a(error)) {
            bVar.a((c) cVar);
        }
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        if (!this.e) {
            this.e = true;
            Object complete = NotificationLite.complete();
            b<T> bVar = this.a;
            bVar.b(complete);
            for (c<T> cVar : a(complete)) {
                bVar.a((c) cVar);
            }
        }
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasObservers() {
        return this.b.get().length != 0;
    }

    @Override // io.reactivex.subjects.Subject
    @Nullable
    public Throwable getThrowable() {
        Object obj = this.a.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @Nullable
    public T getValue() {
        return this.a.b();
    }

    public void cleanupBuffer() {
        this.a.c();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] values = getValues(f);
        return values == f ? new Object[0] : values;
    }

    public T[] getValues(T[] tArr) {
        return this.a.a((Object[]) tArr);
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasComplete() {
        return NotificationLite.isComplete(this.a.get());
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasThrowable() {
        return NotificationLite.isError(this.a.get());
    }

    public boolean hasValue() {
        return this.a.a() != 0;
    }

    boolean a(c<T> cVar) {
        c<T>[] cVarArr;
        c<T>[] cVarArr2;
        do {
            cVarArr = this.b.get();
            if (cVarArr == d) {
                return false;
            }
            int length = cVarArr.length;
            cVarArr2 = new c[length + 1];
            System.arraycopy(cVarArr, 0, cVarArr2, 0, length);
            cVarArr2[length] = cVar;
        } while (!this.b.compareAndSet(cVarArr, cVarArr2));
        return true;
    }

    void b(c<T> cVar) {
        c<T>[] cVarArr;
        c<T>[] cVarArr2;
        do {
            cVarArr = this.b.get();
            if (cVarArr != d && cVarArr != c) {
                int length = cVarArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (cVarArr[i2] == cVar) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        cVarArr2 = c;
                    } else {
                        c<T>[] cVarArr3 = new c[length - 1];
                        System.arraycopy(cVarArr, 0, cVarArr3, 0, i);
                        System.arraycopy(cVarArr, i + 1, cVarArr3, i, (length - i) - 1);
                        cVarArr2 = cVarArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.b.compareAndSet(cVarArr, cVarArr2));
    }

    c<T>[] a(Object obj) {
        if (this.a.compareAndSet(null, obj)) {
            return this.b.getAndSet(d);
        }
        return d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 466549804534799122L;
        volatile boolean cancelled;
        final Observer<? super T> downstream;
        Object index;
        final ReplaySubject<T> state;

        c(Observer<? super T> observer, ReplaySubject<T> replaySubject) {
            this.downstream = observer;
            this.state = replaySubject;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.state.b(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }
    }

    /* loaded from: classes5.dex */
    static final class g<T> extends AtomicReference<Object> implements b<T> {
        private static final long serialVersionUID = -733876083048047795L;
        final List<Object> buffer;
        volatile boolean done;
        volatile int size;

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void c() {
        }

        g(int i) {
            this.buffer = new ArrayList(ObjectHelper.verifyPositive(i, "capacityHint"));
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(T t) {
            this.buffer.add(t);
            this.size++;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void b(Object obj) {
            this.buffer.add(obj);
            c();
            this.size++;
            this.done = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        @Nullable
        public T b() {
            int i = this.size;
            if (i == 0) {
                return null;
            }
            List<Object> list = this.buffer;
            T t = (T) list.get(i - 1);
            if (!NotificationLite.isComplete(t) && !NotificationLite.isError(t)) {
                return t;
            }
            if (i == 1) {
                return null;
            }
            return (T) list.get(i - 2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.subjects.ReplaySubject.b
        public T[] a(T[] tArr) {
            int i = this.size;
            if (i == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<Object> list = this.buffer;
            Object obj = list.get(i - 1);
            if ((NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) && i - 1 == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            if (tArr.length < i) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
            }
            for (int i2 = 0; i2 < i; i2++) {
                tArr[i2] = list.get(i2);
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(c<T> cVar) {
            int i;
            int i2;
            if (cVar.getAndIncrement() == 0) {
                List<Object> list = this.buffer;
                Observer<? super T> observer = cVar.downstream;
                Integer num = (Integer) cVar.index;
                int i3 = 0;
                if (num != null) {
                    i3 = num.intValue();
                    i = 1;
                } else {
                    cVar.index = 0;
                    i = 1;
                }
                while (!cVar.cancelled) {
                    int i4 = this.size;
                    while (i4 != i3) {
                        if (cVar.cancelled) {
                            cVar.index = null;
                            return;
                        }
                        Object obj = list.get(i3);
                        if (this.done && (i2 = i3 + 1) == i4 && i2 == (i4 = this.size)) {
                            if (NotificationLite.isComplete(obj)) {
                                observer.onComplete();
                            } else {
                                observer.onError(NotificationLite.getError(obj));
                            }
                            cVar.index = null;
                            cVar.cancelled = true;
                            return;
                        }
                        observer.onNext(obj);
                        i3++;
                    }
                    if (i3 == this.size) {
                        cVar.index = Integer.valueOf(i3);
                        i = cVar.addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    }
                }
                cVar.index = null;
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public int a() {
            int i = this.size;
            if (i == 0) {
                return 0;
            }
            int i2 = i - 1;
            Object obj = this.buffer.get(i2);
            return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i2 : i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicReference<a<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T value;

        a(T t) {
            this.value = t;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class f<T> extends AtomicReference<f<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final long time;
        final T value;

        f(T t, long j) {
            this.value = t;
            this.time = j;
        }
    }

    /* loaded from: classes5.dex */
    static final class e<T> extends AtomicReference<Object> implements b<T> {
        private static final long serialVersionUID = 1107649250281456395L;
        volatile boolean done;
        volatile a<Object> head;
        final int maxSize;
        int size;
        a<Object> tail;

        e(int i) {
            this.maxSize = ObjectHelper.verifyPositive(i, "maxSize");
            a<Object> aVar = new a<>(null);
            this.tail = aVar;
            this.head = aVar;
        }

        void d() {
            int i = this.size;
            if (i > this.maxSize) {
                this.size = i - 1;
                this.head = this.head.get();
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(T t) {
            a<Object> aVar = new a<>(t);
            a<Object> aVar2 = this.tail;
            this.tail = aVar;
            this.size++;
            aVar2.set(aVar);
            d();
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void b(Object obj) {
            a<Object> aVar = new a<>(obj);
            a<Object> aVar2 = this.tail;
            this.tail = aVar;
            this.size++;
            aVar2.lazySet(aVar);
            c();
            this.done = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void c() {
            a<Object> aVar = this.head;
            if (aVar.value != null) {
                a<Object> aVar2 = new a<>(null);
                aVar2.lazySet(aVar.get());
                this.head = aVar2;
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        @Nullable
        public T b() {
            a<Object> aVar = this.head;
            a<Object> aVar2 = null;
            while (true) {
                a<T> aVar3 = aVar.get();
                if (aVar3 == null) {
                    break;
                }
                aVar2 = aVar;
                aVar = aVar3;
            }
            T t = (T) aVar.value;
            if (t == null) {
                return null;
            }
            return (NotificationLite.isComplete(t) || NotificationLite.isError(t)) ? (T) aVar2.value : t;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.subjects.ReplaySubject.b
        public T[] a(T[] tArr) {
            a<T> aVar = this.head;
            int a = a();
            if (a != 0) {
                if (tArr.length < a) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), a));
                }
                for (int i = 0; i != a; i++) {
                    aVar = aVar.get();
                    tArr[i] = aVar.value;
                }
                if (tArr.length > a) {
                    tArr[a] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(c<T> cVar) {
            int i;
            if (cVar.getAndIncrement() == 0) {
                Observer<? super T> observer = cVar.downstream;
                a<T> aVar = (a) cVar.index;
                if (aVar == null) {
                    aVar = this.head;
                    i = 1;
                } else {
                    i = 1;
                }
                while (!cVar.cancelled) {
                    a<T> aVar2 = aVar.get();
                    if (aVar2 != null) {
                        Object obj = (T) aVar2.value;
                        if (!this.done || aVar2.get() != null) {
                            observer.onNext(obj);
                            aVar = aVar2;
                        } else {
                            if (NotificationLite.isComplete(obj)) {
                                observer.onComplete();
                            } else {
                                observer.onError(NotificationLite.getError(obj));
                            }
                            cVar.index = null;
                            cVar.cancelled = true;
                            return;
                        }
                    } else if (aVar.get() != null) {
                        continue;
                    } else {
                        cVar.index = aVar;
                        i = cVar.addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    }
                }
                cVar.index = null;
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public int a() {
            a<Object> aVar = this.head;
            int i = 0;
            while (i != Integer.MAX_VALUE) {
                a<T> aVar2 = aVar.get();
                if (aVar2 == null) {
                    Object obj = aVar.value;
                    return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i - 1 : i;
                }
                i++;
                aVar = aVar2;
            }
            return i;
        }
    }

    /* loaded from: classes5.dex */
    static final class d<T> extends AtomicReference<Object> implements b<T> {
        private static final long serialVersionUID = -8056260896137901749L;
        volatile boolean done;
        volatile f<Object> head;
        final long maxAge;
        final int maxSize;
        final Scheduler scheduler;
        int size;
        f<Object> tail;
        final TimeUnit unit;

        d(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.maxSize = ObjectHelper.verifyPositive(i, "maxSize");
            this.maxAge = ObjectHelper.verifyPositive(j, "maxAge");
            this.unit = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
            this.scheduler = (Scheduler) ObjectHelper.requireNonNull(scheduler, "scheduler is null");
            f<Object> fVar = new f<>(null, 0L);
            this.tail = fVar;
            this.head = fVar;
        }

        void d() {
            int i = this.size;
            if (i > this.maxSize) {
                this.size = i - 1;
                this.head = this.head.get();
            }
            long now = this.scheduler.now(this.unit) - this.maxAge;
            f<Object> fVar = this.head;
            while (this.size > 1) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    this.head = fVar;
                    return;
                } else if (fVar2.time > now) {
                    this.head = fVar;
                    return;
                } else {
                    this.size--;
                    fVar = fVar2;
                }
            }
            this.head = fVar;
        }

        void e() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            f<Object> fVar = this.head;
            while (true) {
                f<T> fVar2 = fVar.get();
                if (fVar2.get() == null) {
                    if (fVar.value != null) {
                        f<Object> fVar3 = new f<>(null, 0L);
                        fVar3.lazySet(fVar.get());
                        this.head = fVar3;
                        return;
                    }
                    this.head = fVar;
                    return;
                } else if (fVar2.time <= now) {
                    fVar = fVar2;
                } else if (fVar.value != null) {
                    f<Object> fVar4 = new f<>(null, 0L);
                    fVar4.lazySet(fVar.get());
                    this.head = fVar4;
                    return;
                } else {
                    this.head = fVar;
                    return;
                }
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(T t) {
            f<Object> fVar = new f<>(t, this.scheduler.now(this.unit));
            f<Object> fVar2 = this.tail;
            this.tail = fVar;
            this.size++;
            fVar2.set(fVar);
            d();
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void b(Object obj) {
            f<Object> fVar = new f<>(obj, Long.MAX_VALUE);
            f<Object> fVar2 = this.tail;
            this.tail = fVar;
            this.size++;
            fVar2.lazySet(fVar);
            e();
            this.done = true;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void c() {
            f<Object> fVar = this.head;
            if (fVar.value != null) {
                f<Object> fVar2 = new f<>(null, 0L);
                fVar2.lazySet(fVar.get());
                this.head = fVar2;
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        @Nullable
        public T b() {
            T t;
            f<Object> fVar = this.head;
            f<Object> fVar2 = null;
            while (true) {
                f<T> fVar3 = fVar.get();
                if (fVar3 == null) {
                    break;
                }
                fVar2 = fVar;
                fVar = fVar3;
            }
            if (fVar.time >= this.scheduler.now(this.unit) - this.maxAge && (t = (T) fVar.value) != null) {
                return (NotificationLite.isComplete(t) || NotificationLite.isError(t)) ? (T) fVar2.value : t;
            }
            return null;
        }

        f<Object> f() {
            f<Object> fVar = this.head;
            long now = this.scheduler.now(this.unit) - this.maxAge;
            f<Object> fVar2 = fVar.get();
            f<Object> fVar3 = fVar;
            while (fVar2 != null && fVar2.time <= now) {
                fVar2 = fVar2.get();
                fVar3 = fVar2;
            }
            return fVar3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.subjects.ReplaySubject.b
        public T[] a(T[] tArr) {
            f<T> f = f();
            int a = a(f);
            if (a != 0) {
                if (tArr.length < a) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), a));
                }
                for (int i = 0; i != a; i++) {
                    f = f.get();
                    tArr[i] = f.value;
                }
                if (tArr.length > a) {
                    tArr[a] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public void a(c<T> cVar) {
            int i;
            if (cVar.getAndIncrement() == 0) {
                Observer<? super T> observer = cVar.downstream;
                f<T> fVar = (f) cVar.index;
                if (fVar == null) {
                    fVar = f();
                    i = 1;
                } else {
                    i = 1;
                }
                while (true) {
                    fVar = fVar;
                    if (cVar.cancelled) {
                        cVar.index = null;
                        return;
                    }
                    while (!cVar.cancelled) {
                        f<T> fVar2 = fVar.get();
                        if (fVar2 != null) {
                            Object obj = (T) fVar2.value;
                            if (!this.done || fVar2.get() != null) {
                                observer.onNext(obj);
                                fVar = fVar2;
                            } else {
                                if (NotificationLite.isComplete(obj)) {
                                    observer.onComplete();
                                } else {
                                    observer.onError(NotificationLite.getError(obj));
                                }
                                cVar.index = null;
                                cVar.cancelled = true;
                                return;
                            }
                        } else if (fVar.get() == null) {
                            cVar.index = fVar;
                            i = cVar.addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        }
                    }
                    cVar.index = null;
                    return;
                }
            }
        }

        @Override // io.reactivex.subjects.ReplaySubject.b
        public int a() {
            return a(f());
        }

        int a(f<Object> fVar) {
            int i = 0;
            while (i != Integer.MAX_VALUE) {
                f<T> fVar2 = fVar.get();
                if (fVar2 == null) {
                    Object obj = fVar.value;
                    return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i - 1 : i;
                }
                i++;
                fVar = fVar2;
            }
            return i;
        }
    }
}
