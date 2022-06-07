package autodispose2;

import autodispose2.observers.AutoDisposingSingleObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AutoDisposingSingleObserverImpl.java */
/* loaded from: classes.dex */
final class o<T> implements AutoDisposingSingleObserver<T> {
    final AtomicReference<Disposable> a = new AtomicReference<>();
    final AtomicReference<Disposable> b = new AtomicReference<>();
    private final CompletableSource c;
    private final SingleObserver<? super T> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(CompletableSource completableSource, SingleObserver<? super T> singleObserver) {
        this.c = completableSource;
        this.d = singleObserver;
    }

    @Override // autodispose2.observers.AutoDisposingSingleObserver
    public SingleObserver<? super T> delegateObserver() {
        return this.d;
    }

    @Override // io.reactivex.rxjava3.core.SingleObserver
    public void onSubscribe(Disposable disposable) {
        DisposableCompletableObserver disposableCompletableObserver = new DisposableCompletableObserver() { // from class: autodispose2.o.1
            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                o.this.b.lazySet(b.DISPOSED);
                o.this.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                o.this.b.lazySet(b.DISPOSED);
                b.a(o.this.a);
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

    @Override // io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(T t) {
        if (!isDisposed()) {
            this.a.lazySet(b.DISPOSED);
            b.a(this.b);
            this.d.onSuccess(t);
        }
    }

    @Override // io.reactivex.rxjava3.core.SingleObserver
    public void onError(Throwable th) {
        if (!isDisposed()) {
            this.a.lazySet(b.DISPOSED);
            b.a(this.b);
            this.d.onError(th);
        }
    }
}
