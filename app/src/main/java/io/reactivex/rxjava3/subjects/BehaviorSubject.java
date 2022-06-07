package io.reactivex.rxjava3.subjects;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes5.dex */
public final class BehaviorSubject<T> extends Subject<T> {
    static final a[] c = new a[0];
    static final a[] d = new a[0];
    final AtomicReference<Object> a;
    long i;
    final ReadWriteLock e = new ReentrantReadWriteLock();
    final Lock f = this.e.readLock();
    final Lock g = this.e.writeLock();
    final AtomicReference<a<T>[]> b = new AtomicReference<>(c);
    final AtomicReference<Throwable> h = new AtomicReference<>();

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorSubject<T> create() {
        return new BehaviorSubject<>(null);
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorSubject<T> createDefault(T t) {
        Objects.requireNonNull(t, "defaultValue is null");
        return new BehaviorSubject<>(t);
    }

    BehaviorSubject(T t) {
        this.a = new AtomicReference<>(t);
    }

    @Override // io.reactivex.rxjava3.core.Observable
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

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.h.get() != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        ExceptionHelper.nullCheck(t, "onNext called with a null value.");
        if (this.h.get() == null) {
            Object next = NotificationLite.next(t);
            b(next);
            for (a<T> aVar : this.b.get()) {
                aVar.a(next, this.i);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (!this.h.compareAndSet(null, th)) {
            RxJavaPlugins.onError(th);
            return;
        }
        Object error = NotificationLite.error(th);
        for (a<T> aVar : a(error)) {
            aVar.a(error, this.i);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (this.h.compareAndSet(null, ExceptionHelper.TERMINATED)) {
            Object complete = NotificationLite.complete();
            for (a<T> aVar : a(complete)) {
                aVar.a(complete, this.i);
            }
        }
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasObservers() {
        return this.b.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        Object obj = this.a.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @CheckReturnValue
    @Nullable
    public T getValue() {
        Object obj = this.a.get();
        if (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) NotificationLite.getValue(obj);
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasComplete() {
        return NotificationLite.isComplete(this.a.get());
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasThrowable() {
        return NotificationLite.isError(this.a.get());
    }

    @CheckReturnValue
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
        b(obj);
        return this.b.getAndSet(d);
    }

    void b(Object obj) {
        this.g.lock();
        this.i++;
        this.a.lazySet(obj);
        this.g.unlock();
    }

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

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.g) {
                this.g = true;
                this.b.b((a) this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
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

        @Override // io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.rxjava3.functions.Predicate
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
