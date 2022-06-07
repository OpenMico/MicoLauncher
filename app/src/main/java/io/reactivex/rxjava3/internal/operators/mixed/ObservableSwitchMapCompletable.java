package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableSwitchMapCompletable<T> extends Completable {
    final Observable<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final boolean c;

    public ObservableSwitchMapCompletable(Observable<T> observable, Function<? super T, ? extends CompletableSource> function, boolean z) {
        this.a = observable;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        if (!a.a(this.a, this.b, completableObserver)) {
            this.a.subscribe(new a(completableObserver, this.b, this.c));
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements Observer<T>, Disposable {
        static final C0315a f = new C0315a(null);
        final CompletableObserver a;
        final Function<? super T, ? extends CompletableSource> b;
        final boolean c;
        final AtomicThrowable d = new AtomicThrowable();
        final AtomicReference<C0315a> e = new AtomicReference<>();
        volatile boolean g;
        Disposable h;

        a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.a = completableObserver;
            this.b = function;
            this.c = z;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            C0315a aVar;
            try {
                CompletableSource completableSource = (CompletableSource) Objects.requireNonNull(this.b.apply(t), "The mapper returned a null CompletableSource");
                C0315a aVar2 = new C0315a(this);
                do {
                    aVar = this.e.get();
                    if (aVar == f) {
                        return;
                    }
                } while (!this.e.compareAndSet(aVar, aVar2));
                if (aVar != null) {
                    aVar.a();
                }
                completableSource.subscribe(aVar2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.h.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (!this.d.tryAddThrowableOrReport(th)) {
                return;
            }
            if (this.c) {
                onComplete();
                return;
            }
            a();
            this.d.tryTerminateConsumer(this.a);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.g = true;
            if (this.e.get() == null) {
                this.d.tryTerminateConsumer(this.a);
            }
        }

        void a() {
            C0315a andSet = this.e.getAndSet(f);
            if (andSet != null && andSet != f) {
                andSet.a();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.h.dispose();
            a();
            this.d.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.e.get() == f;
        }

        void a(C0315a aVar, Throwable th) {
            if (!this.e.compareAndSet(aVar, null)) {
                RxJavaPlugins.onError(th);
            } else if (!this.d.tryAddThrowableOrReport(th)) {
            } else {
                if (!this.c) {
                    this.h.dispose();
                    a();
                    this.d.tryTerminateConsumer(this.a);
                } else if (this.g) {
                    this.d.tryTerminateConsumer(this.a);
                }
            }
        }

        void a(C0315a aVar) {
            if (this.e.compareAndSet(aVar, null) && this.g) {
                this.d.tryTerminateConsumer(this.a);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.mixed.ObservableSwitchMapCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0315a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -8003404460084760287L;
            final a<?> parent;

            C0315a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                this.parent.a(this, th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.a(this);
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
