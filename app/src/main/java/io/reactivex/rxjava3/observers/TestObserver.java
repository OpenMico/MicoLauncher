package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public class TestObserver<T> extends BaseTestConsumer<T, TestObserver<T>> implements CompletableObserver, MaybeObserver<T>, Observer<T>, SingleObserver<T>, Disposable {
    private final Observer<? super T> a;
    private final AtomicReference<Disposable> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public enum a implements Observer<Object> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
        }
    }

    @NonNull
    public static <T> TestObserver<T> create() {
        return new TestObserver<>();
    }

    @NonNull
    public static <T> TestObserver<T> create(@NonNull Observer<? super T> observer) {
        return new TestObserver<>(observer);
    }

    public TestObserver() {
        this(a.INSTANCE);
    }

    public TestObserver(@NonNull Observer<? super T> observer) {
        this.b = new AtomicReference<>();
        this.a = observer;
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(@NonNull Disposable disposable) {
        this.lastThread = Thread.currentThread();
        if (disposable == null) {
            this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
        } else if (!this.b.compareAndSet(null, disposable)) {
            disposable.dispose();
            if (this.b.get() != DisposableHelper.DISPOSED) {
                List list = this.errors;
                list.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + disposable));
            }
        } else {
            this.a.onSubscribe(disposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(@NonNull T t) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.b.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        this.lastThread = Thread.currentThread();
        this.values.add(t);
        if (t == null) {
            this.errors.add(new NullPointerException("onNext received a null value"));
        }
        this.a.onNext(t);
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(@NonNull Throwable th) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.b.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            if (th == null) {
                this.errors.add(new NullPointerException("onError received a null Throwable"));
            } else {
                this.errors.add(th);
            }
            this.a.onError(th);
        } finally {
            this.done.countDown();
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.b.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            this.completions++;
            this.a.onComplete();
        } finally {
            this.done.countDown();
        }
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this.b);
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return DisposableHelper.isDisposed(this.b.get());
    }

    public final boolean hasSubscription() {
        return this.b.get() != null;
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer
    @NonNull
    public final TestObserver<T> assertSubscribed() {
        if (this.b.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(@NonNull T t) {
        onNext(t);
        onComplete();
    }
}
