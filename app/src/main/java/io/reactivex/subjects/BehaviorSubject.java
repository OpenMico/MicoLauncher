package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes5.dex */
public final class BehaviorSubject<T> extends Subject<T> {
    final AtomicReference<Object> a;
    final AtomicReference<a<T>[]> b;
    final ReadWriteLock e;
    final Lock f;
    final Lock g;
    final AtomicReference<Throwable> h;
    long i;
    private static final Object[] j = new Object[0];
    static final a[] c = new a[0];
    static final a[] d = new a[0];

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorSubject<T> create() {
        return new BehaviorSubject<>();
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorSubject<T> createDefault(T t) {
        return new BehaviorSubject<>(t);
    }

    BehaviorSubject() {
        this.e = new ReentrantReadWriteLock();
        this.f = this.e.readLock();
        this.g = this.e.writeLock();
        this.b = new AtomicReference<>(c);
        this.a = new AtomicReference<>();
        this.h = new AtomicReference<>();
    }

    BehaviorSubject(T t) {
        this();
        this.a.lazySet(ObjectHelper.requireNonNull(t, "defaultValue is null"));
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        a<T> aVar = new a<>(observer, this);
        observer.onSubscribe(aVar);
        if (!a((a) aVar)) {
            Throwable th = this.h.get();
            if (th == ExceptionHelper.TERMINATED) {
                observer.onComplete();
            } else {
                observer.onError(th);
            }
        } else if (aVar.g) {
            b((a) aVar);
        } else {
            aVar.a();
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.h.get() != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.h.get() == null) {
            Object next = NotificationLite.next(t);
            b(next);
            for (a<T> aVar : this.b.get()) {
                aVar.a(next, this.i);
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.h.compareAndSet(null, th)) {
            RxJavaPlugins.onError(th);
            return;
        }
        Object error = NotificationLite.error(th);
        for (a<T> aVar : a(error)) {
            aVar.a(error, this.i);
        }
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        if (this.h.compareAndSet(null, ExceptionHelper.TERMINATED)) {
            Object complete = NotificationLite.complete();
            for (a<T> aVar : a(complete)) {
                aVar.a(complete, this.i);
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
        Object obj = this.a.get();
        if (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) NotificationLite.getValue(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Object[] getValues() {
        Object[] values = getValues(j);
        return values == j ? new Object[0] : values;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public T[] getValues(T[] tArr) {
        Object obj = this.a.get();
        if (obj == null || NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            if (tArr.length != 0) {
                tArr[0] = 0;
            }
            return tArr;
        }
        Object value = NotificationLite.getValue(obj);
        if (tArr.length != 0) {
            tArr[0] = value;
            if (tArr.length == 1) {
                return tArr;
            }
            tArr[1] = 0;
            return tArr;
        }
        T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1));
        tArr2[0] = value;
        return tArr2;
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
        Object obj = this.a.get();
        return obj != null && !NotificationLite.isComplete(obj) && !NotificationLite.isError(obj);
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.b.get();
            if (aVarArr == d) {
                return false;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.b.compareAndSet(aVarArr, aVarArr2));
        return true;
    }

    void b(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.b.get();
            int length = aVarArr.length;
            if (length != 0) {
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (aVarArr[i2] == aVar) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        aVarArr2 = c;
                    } else {
                        a<T>[] aVarArr3 = new a[length - 1];
                        System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                        System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                        aVarArr2 = aVarArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.b.compareAndSet(aVarArr, aVarArr2));
    }

    a<T>[] a(Object obj) {
        a<T>[] andSet = this.b.getAndSet(d);
        if (andSet != d) {
            b(obj);
        }
        return andSet;
    }

    void b(Object obj) {
        this.g.lock();
        this.i++;
        this.a.lazySet(obj);
        this.g.unlock();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> implements Disposable, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
        final Observer<? super T> a;
        final BehaviorSubject<T> b;
        boolean c;
        boolean d;
        AppendOnlyLinkedArrayList<Object> e;
        boolean f;
        volatile boolean g;
        long h;

        a(Observer<? super T> observer, BehaviorSubject<T> behaviorSubject) {
            this.a = observer;
            this.b = behaviorSubject;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.g) {
                this.g = true;
                this.b.b((a) this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.g;
        }

        void a() {
            if (!this.g) {
                synchronized (this) {
                    if (!this.g) {
                        if (!this.c) {
                            BehaviorSubject<T> behaviorSubject = this.b;
                            Lock lock = behaviorSubject.f;
                            lock.lock();
                            this.h = behaviorSubject.i;
                            Object obj = behaviorSubject.a.get();
                            lock.unlock();
                            this.d = obj != null;
                            this.c = true;
                            if (obj != null && !test(obj)) {
                                b();
                            }
                        }
                    }
                }
            }
        }

        void a(Object obj, long j) {
            if (!this.g) {
                if (!this.f) {
                    synchronized (this) {
                        if (!this.g) {
                            if (this.h != j) {
                                if (this.d) {
                                    AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.e;
                                    if (appendOnlyLinkedArrayList == null) {
                                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                                        this.e = appendOnlyLinkedArrayList;
                                    }
                                    appendOnlyLinkedArrayList.add(obj);
                                    return;
                                }
                                this.c = true;
                                this.f = true;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
                test(obj);
            }
        }

        @Override // io.reactivex.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.functions.Predicate
        public boolean test(Object obj) {
            return this.g || NotificationLite.accept(obj, this.a);
        }

        void b() {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            while (!this.g) {
                synchronized (this) {
                    appendOnlyLinkedArrayList = this.e;
                    if (appendOnlyLinkedArrayList == null) {
                        this.d = false;
                        return;
                    }
                    this.e = null;
                }
                appendOnlyLinkedArrayList.forEachWhile(this);
            }
        }
    }
}
