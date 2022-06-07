package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class CompletableTakeUntilCompletable extends Completable {
    final Completable a;
    final CompletableSource b;

    public CompletableTakeUntilCompletable(Completable completable, CompletableSource completableSource) {
        this.a = completable;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        a aVar = new a(completableObserver);
        completableObserver.onSubscribe(aVar);
        this.b.subscribe(aVar.other);
        this.a.subscribe(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = 3533011714830024923L;
        final CompletableObserver downstream;
        final C0279a other = new C0279a(this);
        final AtomicBoolean once = new AtomicBoolean();

        a(CompletableObserver completableObserver) {
            this.downstream = completableObserver;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.once.compareAndSet(false, true)) {
                DisposableHelper.dispose(this);
                DisposableHelper.dispose(this.other);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.once.get();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            if (this.once.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.other);
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            if (this.once.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.other);
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        void a() {
            if (this.once.compareAndSet(false, true)) {
                DisposableHelper.dispose(this);
                this.downstream.onComplete();
            }
        }

        void a(Throwable th) {
            if (this.once.compareAndSet(false, true)) {
                DisposableHelper.dispose(this);
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.completable.CompletableTakeUntilCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0279a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = 5176264485428790318L;
            final a parent;

            C0279a(a aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.a();
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }
        }
    }
}
