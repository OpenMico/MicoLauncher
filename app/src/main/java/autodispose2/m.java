package autodispose2;

import autodispose2.observers.AutoDisposingMaybeObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AutoDisposingMaybeObserverImpl.java */
/* loaded from: classes.dex */
final class m<T> implements AutoDisposingMaybeObserver<T> {
    final AtomicReference<Disposable> a = new AtomicReference<>();
    final AtomicReference<Disposable> b = new AtomicReference<>();
    private final CompletableSource c;
    private final MaybeObserver<? super T> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(CompletableSource completableSource, MaybeObserver<? super T> maybeObserver) {
        this.c = completableSource;
        this.d = maybeObserver;
    }

    @Override // autodispose2.observers.AutoDisposingMaybeObserver
    public MaybeObserver<? super T> delegateObserver() {
        return this.d;
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSubscribe(Disposable disposable) {
        DisposableCompletableObserver disposableCompletableObserver = new DisposableCompletableObserver() { // from class: autodispose2.m.1
            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                m.this.b.lazySet(b.DISPOSED);
                m.this.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                m.this.b.lazySet(b.DISPOSED);
                b.a(m.this.a);
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

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(T t) {
        if (!isDisposed()) {
            this.a.lazySet(b.DISPOSED);
            b.a(this.b);
            this.d.onSuccess(t);
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onError(Throwable th) {
        if (!isDisposed()) {
            this.a.lazySet(b.DISPOSED);
            b.a(this.b);
            this.d.onError(th);
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onComplete() {
        if (!isDisposed()) {
            this.a.lazySet(b.DISPOSED);
            b.a(this.b);
            this.d.onComplete();
        }
    }
}
