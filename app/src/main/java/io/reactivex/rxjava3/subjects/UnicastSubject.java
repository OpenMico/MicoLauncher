package io.reactivex.rxjava3.subjects;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.observers.BasicIntQueueDisposable;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class UnicastSubject<T> extends Subject<T> {
    final SpscLinkedArrayQueue<T> a;
    final AtomicReference<Runnable> c;
    final boolean d;
    volatile boolean e;
    volatile boolean f;
    Throwable g;
    boolean j;
    final AtomicReference<Observer<? super T>> b = new AtomicReference<>();
    final AtomicBoolean h = new AtomicBoolean();
    final BasicIntQueueDisposable<T> i = new a();

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create() {
        return new UnicastSubject<>(bufferSize(), null, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int i) {
        ObjectHelper.verifyPositive(i, "capacityHint");
        return new UnicastSubject<>(i, null, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int i, @NonNull Runnable runnable) {
        ObjectHelper.verifyPositive(i, "capacityHint");
        Objects.requireNonNull(runnable, "onTerminate");
        return new UnicastSubject<>(i, runnable, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int i, @NonNull Runnable runnable, boolean z) {
        ObjectHelper.verifyPositive(i, "capacityHint");
        Objects.requireNonNull(runnable, "onTerminate");
        return new UnicastSubject<>(i, runnable, z);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(boolean z) {
        return new UnicastSubject<>(bufferSize(), null, z);
    }

    UnicastSubject(int i, Runnable runnable, boolean z) {
        this.a = new SpscLinkedArrayQueue<>(i);
        this.c = new AtomicReference<>(runnable);
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        if (this.h.get() || !this.h.compareAndSet(false, true)) {
            EmptyDisposable.error(new IllegalStateException("Only a single observer allowed."), observer);
            return;
        }
        observer.onSubscribe(this.i);
        this.b.lazySet(observer);
        if (this.e) {
            this.b.lazySet(null);
        } else {
            b();
        }
    }

    void a() {
        Runnable runnable = this.c.get();
        if (runnable != null && this.c.compareAndSet(runnable, null)) {
            runnable.run();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.f || this.e) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        ExceptionHelper.nullCheck(t, "onNext called with a null value.");
        if (!this.f && !this.e) {
            this.a.offer(t);
            b();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (this.f || this.e) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.g = th;
        this.f = true;
        a();
        b();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (!this.f && !this.e) {
            this.f = true;
            a();
            b();
        }
    }

    void a(Observer<? super T> observer) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.a;
        boolean z = !this.d;
        boolean z2 = true;
        int i = 1;
        while (!this.e) {
            boolean z3 = this.f;
            Object obj = (T) this.a.poll();
            boolean z4 = obj == null;
            if (z3) {
                if (z && z2) {
                    if (!a(spscLinkedArrayQueue, observer)) {
                        z2 = false;
                    } else {
                        return;
                    }
                }
                if (z4) {
                    c(observer);
                    return;
                }
            }
            if (z4) {
                i = this.i.addAndGet(-i);
                if (i == 0) {
                    return;
                }
            } else {
                observer.onNext(obj);
            }
        }
        this.b.lazySet(null);
        spscLinkedArrayQueue.clear();
    }

    void b(Observer<? super T> observer) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.a;
        int i = 1;
        boolean z = !this.d;
        while (!this.e) {
            boolean z2 = this.f;
            if (!z || !z2 || !a(spscLinkedArrayQueue, observer)) {
                observer.onNext(null);
                if (z2) {
                    c(observer);
                    return;
                }
                i = this.i.addAndGet(-i);
                if (i == 0) {
                    return;
                }
            } else {
                return;
            }
        }
        this.b.lazySet(null);
    }

    void c(Observer<? super T> observer) {
        this.b.lazySet(null);
        Throwable th = this.g;
        if (th != null) {
            observer.onError(th);
        } else {
            observer.onComplete();
        }
    }

    boolean a(SimpleQueue<T> simpleQueue, Observer<? super T> observer) {
        Throwable th = this.g;
        if (th == null) {
            return false;
        }
        this.b.lazySet(null);
        simpleQueue.clear();
        observer.onError(th);
        return true;
    }

    void b() {
        if (this.i.getAndIncrement() == 0) {
            Observer<? super T> observer = this.b.get();
            int i = 1;
            while (observer == null) {
                i = this.i.addAndGet(-i);
                if (i != 0) {
                    observer = this.b.get();
                } else {
                    return;
                }
            }
            if (this.j) {
                b(observer);
            } else {
                a(observer);
            }
        }
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasObservers() {
        return this.b.get() != null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        if (this.f) {
            return this.g;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.f && this.g != null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasComplete() {
        return this.f && this.g == null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public final class a extends BasicIntQueueDisposable<T> {
        private static final long serialVersionUID = 7926949470189395511L;

        a() {
            UnicastSubject.this = r1;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            UnicastSubject.this.j = true;
            return 2;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            return UnicastSubject.this.a.poll();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return UnicastSubject.this.a.isEmpty();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            UnicastSubject.this.a.clear();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!UnicastSubject.this.e) {
                UnicastSubject unicastSubject = UnicastSubject.this;
                unicastSubject.e = true;
                unicastSubject.a();
                UnicastSubject.this.b.lazySet(null);
                if (UnicastSubject.this.i.getAndIncrement() == 0) {
                    UnicastSubject.this.b.lazySet(null);
                    if (!UnicastSubject.this.j) {
                        UnicastSubject.this.a.clear();
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return UnicastSubject.this.e;
        }
    }
}
