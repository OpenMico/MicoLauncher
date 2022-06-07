package autodispose2;

import autodispose2.observers.AutoDisposingCompletableObserver;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AutoDisposingCompletableObserverImpl.java */
/* loaded from: classes.dex */
final class l implements AutoDisposingCompletableObserver {
    final AtomicReference<Disposable> a = new AtomicReference<>();
    final AtomicReference<Disposable> b = new AtomicReference<>();
    private final CompletableSource c;
    private final CompletableObserver d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(CompletableSource completableSource, CompletableObserver completableObserver) {
        this.c = completableSource;
        this.d = completableObserver;
    }

    @Override // autodispose2.observers.AutoDisposingCompletableObserver
    public CompletableObserver delegateObserver() {
        return this.d;
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(Disposable disposable) {
        DisposableCompletableObserver disposableCompletableObserver = new DisposableCompletableObserver() { // from class: autodispose2.l.1
            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                l.this.b.lazySet(b.DISPOSED);
                l.this.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                l.this.b.lazySet(b.DISPOSED);
                b.a(l.this.a);
            }
        };
        if (e.a(this.b, disposableCompletableObserver, getClass())) {
            this.d.onSubscribe(this);
            this.c.subscribe(disposableCompletableObserver);
            e.a(this.a, disposable, getClass());
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.a.get() == b.DISPOSED;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        b.a(this.b);
        b.a(this.a);
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        if (!isDisposed()) {
            this.a.lazySet(b.DISPOSED);
            b.a(this.b);
            this.d.onComplete();
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable th) {
        if (!isDisposed()) {
            this.a.lazySet(b.DISPOSED);
            b.a(this.b);
            this.d.onError(th);
        }
    }
}
