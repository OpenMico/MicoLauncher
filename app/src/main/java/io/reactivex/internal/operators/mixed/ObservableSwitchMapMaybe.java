package io.reactivex.internal.operators.mixed;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableSwitchMapMaybe<T, R> extends Observable<R> {
    final Observable<T> a;
    final Function<? super T, ? extends MaybeSource<? extends R>> b;
    final boolean c;

    public ObservableSwitchMapMaybe(Observable<T> observable, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
        this.a = observable;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        if (!a.a(this.a, this.b, observer)) {
            this.a.subscribe(new a(observer, this.b, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        static final C0251a<Object> a = new C0251a<>(null);
        private static final long serialVersionUID = -5402190102429853762L;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Observer<? super R> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicReference<C0251a<R>> inner = new AtomicReference<>();
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        Disposable upstream;

        a(Observer<? super R> observer, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z) {
            this.downstream = observer;
            this.mapper = function;
            this.delayErrors = z;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onNext(T t) {
            C0251a<R> aVar;
            C0251a<R> aVar2 = this.inner.get();
            if (aVar2 != null) {
                aVar2.a();
            }
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null MaybeSource");
                C0251a<R> aVar3 = new C0251a<>(this);
                do {
                    aVar = this.inner.get();
                    if (aVar == a) {
                        return;
                    }
                } while (!this.inner.compareAndSet(aVar, aVar3));
                maybeSource.subscribe(aVar3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                this.inner.getAndSet(a);
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    a();
                }
                this.done = true;
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            b();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a() {
            C0251a<Object> aVar = (C0251a) this.inner.getAndSet(a);
            if (aVar != null && aVar != a) {
                aVar.a();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            a();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a(C0251a<R> aVar, Throwable th) {
            if (!this.inner.compareAndSet(aVar, null) || !this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.delayErrors) {
                this.upstream.dispose();
                a();
            }
            b();
        }

        void a(C0251a<R> aVar) {
            if (this.inner.compareAndSet(aVar, null)) {
                b();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                Observer<? super R> observer = this.downstream;
                AtomicThrowable atomicThrowable = this.errors;
                AtomicReference<C0251a<R>> atomicReference = this.inner;
                int i = 1;
                while (!this.cancelled) {
                    if (atomicThrowable.get() == null || this.delayErrors) {
                        boolean z = this.done;
                        C0251a<R> aVar = atomicReference.get();
                        boolean z2 = aVar == null;
                        if (z && z2) {
                            Throwable terminate = atomicThrowable.terminate();
                            if (terminate != null) {
                                observer.onError(terminate);
                                return;
                            } else {
                                observer.onComplete();
                                return;
                            }
                        } else if (z2 || aVar.item == null) {
                            i = addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        } else {
                            atomicReference.compareAndSet(aVar, null);
                            observer.onNext((R) aVar.item);
                        }
                    } else {
                        observer.onError(atomicThrowable.terminate());
                        return;
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.mixed.ObservableSwitchMapMaybe$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class C0251a<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            private static final long serialVersionUID = 8042919737683345351L;
            volatile R item;
            final a<?, R> parent;

            C0251a(a<?, R> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSuccess(R r) {
                this.item = r;
                this.parent.b();
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(this, th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.a(this);
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
