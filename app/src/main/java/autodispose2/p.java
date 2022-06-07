package autodispose2;

import autodispose2.observers.AutoDisposingSubscriber;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* compiled from: AutoDisposingSubscriberImpl.java */
/* loaded from: classes.dex */
public final class p<T> extends AtomicInteger implements AutoDisposingSubscriber<T> {
    private final Subscriber<? super T> delegate;
    private final CompletableSource scope;
    final AtomicReference<Subscription> mainSubscription = new AtomicReference<>();
    final AtomicReference<Disposable> scopeDisposable = new AtomicReference<>();
    private final a error = new a();
    private final AtomicReference<Subscription> ref = new AtomicReference<>();
    private final AtomicLong requested = new AtomicLong();

    public p(CompletableSource completableSource, Subscriber<? super T> subscriber) {
        this.scope = completableSource;
        this.delegate = subscriber;
    }

    @Override // autodispose2.observers.AutoDisposingSubscriber
    public Subscriber<? super T> delegateSubscriber() {
        return this.delegate;
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        DisposableCompletableObserver disposableCompletableObserver = new DisposableCompletableObserver() { // from class: autodispose2.p.1
            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                p.this.scopeDisposable.lazySet(b.DISPOSED);
                p.this.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                p.this.scopeDisposable.lazySet(b.DISPOSED);
                q.a(p.this.mainSubscription);
            }
        };
        if (e.a(this.scopeDisposable, disposableCompletableObserver, getClass())) {
            this.delegate.onSubscribe(this);
            this.scope.subscribe(disposableCompletableObserver);
            if (e.a(this.mainSubscription, subscription, getClass())) {
                q.a(this.ref, this.requested, subscription);
            }
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        q.a(this.ref, this.requested, j);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        b.a(this.scopeDisposable);
        q.a(this.mainSubscription);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.mainSubscription.get() == q.CANCELLED;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        cancel();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (!isDisposed() && s.a(this.delegate, t, this, this.error)) {
            this.mainSubscription.lazySet(q.CANCELLED);
            b.a(this.scopeDisposable);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (!isDisposed()) {
            this.mainSubscription.lazySet(q.CANCELLED);
            b.a(this.scopeDisposable);
            s.a((Subscriber<?>) this.delegate, th, (AtomicInteger) this, this.error);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!isDisposed()) {
            this.mainSubscription.lazySet(q.CANCELLED);
            b.a(this.scopeDisposable);
            s.a(this.delegate, this, this.error);
        }
    }
}
