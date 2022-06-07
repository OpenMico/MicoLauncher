package autodispose2;

import autodispose2.observers.AutoDisposingObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AutoDisposingObserverImpl.java */
/* loaded from: classes.dex */
final class n<T> extends AtomicInteger implements AutoDisposingObserver<T> {
    private final Observer<? super T> delegate;
    private final CompletableSource scope;
    final AtomicReference<Disposable> mainDisposable = new AtomicReference<>();
    final AtomicReference<Disposable> scopeDisposable = new AtomicReference<>();
    private final a error = new a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(CompletableSource completableSource, Observer<? super T> observer) {
        this.scope = completableSource;
        this.delegate = observer;
    }

    @Override // autodispose2.observers.AutoDisposingObserver
    public Observer<? super T> delegateObserver() {
        return this.delegate;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
        DisposableCompletableObserver disposableCompletableObserver = new DisposableCompletableObserver() { // from class: autodispose2.n.1
            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                n.this.scopeDisposable.lazySet(b.DISPOSED);
                n.this.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                n.this.scopeDisposable.lazySet(b.DISPOSED);
                b.a(n.this.mainDisposable);
            }
        };
        if (e.a(this.scopeDisposable, disposableCompletableObserver, getClass())) {
            this.delegate.onSubscribe(this);
            this.scope.subscribe(disposableCompletableObserver);
            e.a(this.mainDisposable, disposable, getClass());
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.mainDisposable.get() == b.DISPOSED;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        b.a(this.scopeDisposable);
        b.a(this.mainDisposable);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        if (!isDisposed() && s.a(this.delegate, t, this, this.error)) {
            this.mainDisposable.lazySet(b.DISPOSED);
            b.a(this.scopeDisposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        if (!isDisposed()) {
            this.mainDisposable.lazySet(b.DISPOSED);
            b.a(this.scopeDisposable);
            s.a((Observer<?>) this.delegate, th, (AtomicInteger) this, this.error);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (!isDisposed()) {
            this.mainDisposable.lazySet(b.DISPOSED);
            b.a(this.scopeDisposable);
            s.a(this.delegate, this, this.error);
        }
    }
}
