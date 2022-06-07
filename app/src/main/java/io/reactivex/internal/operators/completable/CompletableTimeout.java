package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class CompletableTimeout extends Completable {
    final CompletableSource a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final CompletableSource e;

    public CompletableTimeout(CompletableSource completableSource, long j, TimeUnit timeUnit, Scheduler scheduler, CompletableSource completableSource2) {
        this.a = completableSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = completableSource2;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        completableObserver.onSubscribe(compositeDisposable);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        compositeDisposable.add(this.d.scheduleDirect(new a(atomicBoolean, compositeDisposable, completableObserver), this.b, this.c));
        this.a.subscribe(new b(compositeDisposable, atomicBoolean, completableObserver));
    }

    /* loaded from: classes4.dex */
    static final class b implements CompletableObserver {
        private final CompositeDisposable a;
        private final AtomicBoolean b;
        private final CompletableObserver c;

        b(CompositeDisposable compositeDisposable, AtomicBoolean atomicBoolean, CompletableObserver completableObserver) {
            this.a = compositeDisposable;
            this.b = atomicBoolean;
            this.c = completableObserver;
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.a.add(disposable);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            if (this.b.compareAndSet(false, true)) {
                this.a.dispose();
                this.c.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            if (this.b.compareAndSet(false, true)) {
                this.a.dispose();
                this.c.onComplete();
            }
        }
    }

    /* loaded from: classes4.dex */
    final class a implements Runnable {
        final CompositeDisposable a;
        final CompletableObserver b;
        private final AtomicBoolean d;

        a(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, CompletableObserver completableObserver) {
            this.d = atomicBoolean;
            this.a = compositeDisposable;
            this.b = completableObserver;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.d.compareAndSet(false, true)) {
                this.a.clear();
                if (CompletableTimeout.this.e == null) {
                    this.b.onError(new TimeoutException(ExceptionHelper.timeoutMessage(CompletableTimeout.this.b, CompletableTimeout.this.c)));
                } else {
                    CompletableTimeout.this.e.subscribe(new C0215a());
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.completable.CompletableTimeout$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        final class C0215a implements CompletableObserver {
            C0215a() {
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                a.this.a.add(disposable);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a.this.a.dispose();
                a.this.b.onError(th);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a.this.a.dispose();
                a.this.b.onComplete();
            }
        }
    }
}
