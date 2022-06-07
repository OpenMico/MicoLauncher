package io.reactivex.internal.operators.mixed;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableSwitchMapCompletable<T> extends Completable {
    final Observable<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final boolean c;

    public ObservableSwitchMapCompletable(Observable<T> observable, Function<? super T, ? extends CompletableSource> function, boolean z) {
        this.a = observable;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        if (!a.a(this.a, this.b, completableObserver)) {
            this.a.subscribe(new a(completableObserver, this.b, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        static final C0250a f = new C0250a(null);
        final CompletableObserver a;
        final Function<? super T, ? extends CompletableSource> b;
        final boolean c;
        final AtomicThrowable d = new AtomicThrowable();
        final AtomicReference<C0250a> e = new AtomicReference<>();
        volatile boolean g;
        Disposable h;

        a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.a = completableObserver;
            this.b = function;
            this.c = z;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            C0250a aVar;
            try {
                CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null CompletableSource");
                C0250a aVar2 = new C0250a(this);
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

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (this.c) {
                onComplete();
            } else {
                a();
                Throwable terminate = this.d.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.a.onError(terminate);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.g = true;
            if (this.e.get() == null) {
                Throwable terminate = this.d.terminate();
                if (terminate == null) {
                    this.a.onComplete();
                } else {
                    this.a.onError(terminate);
                }
            }
        }

        void a() {
            C0250a andSet = this.e.getAndSet(f);
            if (andSet != null && andSet != f) {
                andSet.a();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.h.dispose();
            a();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e.get() == f;
        }

        void a(C0250a aVar, Throwable th) {
            if (!this.e.compareAndSet(aVar, null) || !this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (!this.c) {
                dispose();
                Throwable terminate = this.d.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.a.onError(terminate);
                }
            } else if (this.g) {
                this.a.onError(this.d.terminate());
            }
        }

        void a(C0250a aVar) {
            if (this.e.compareAndSet(aVar, null) && this.g) {
                Throwable terminate = this.d.terminate();
                if (terminate == null) {
                    this.a.onComplete();
                } else {
                    this.a.onError(terminate);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.mixed.ObservableSwitchMapCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class C0250a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -8003404460084760287L;
            final a<?> parent;

            C0250a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(this, th);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.a(this);
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
