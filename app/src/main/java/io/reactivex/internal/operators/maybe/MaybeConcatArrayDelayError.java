package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class MaybeConcatArrayDelayError<T> extends Flowable<T> {
    final MaybeSource<? extends T>[] b;

    public MaybeConcatArrayDelayError(MaybeSource<? extends T>[] maybeSourceArr) {
        this.b = maybeSourceArr;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber, this.b);
        subscriber.onSubscribe(aVar);
        aVar.a();
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicInteger implements MaybeObserver<T>, Subscription {
        private static final long serialVersionUID = 3520831347801429610L;
        final Subscriber<? super T> downstream;
        int index;
        long produced;
        final MaybeSource<? extends T>[] sources;
        final AtomicLong requested = new AtomicLong();
        final SequentialDisposable disposables = new SequentialDisposable();
        final AtomicReference<Object> current = new AtomicReference<>(NotificationLite.COMPLETE);
        final AtomicThrowable errors = new AtomicThrowable();

        a(Subscriber<? super T> subscriber, MaybeSource<? extends T>[] maybeSourceArr) {
            this.downstream = subscriber;
            this.sources = maybeSourceArr;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.disposables.dispose();
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.disposables.replace(disposable);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.current.lazySet(t);
            a();
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.current.lazySet(NotificationLite.COMPLETE);
            if (this.errors.addThrowable(th)) {
                a();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.current.lazySet(NotificationLite.COMPLETE);
            a();
        }

        void a() {
            if (getAndIncrement() == 0) {
                AtomicReference<Object> atomicReference = this.current;
                Subscriber<? super T> subscriber = this.downstream;
                SequentialDisposable sequentialDisposable = this.disposables;
                while (!sequentialDisposable.isDisposed()) {
                    Object obj = atomicReference.get();
                    if (obj != null) {
                        boolean z = true;
                        if (obj != NotificationLite.COMPLETE) {
                            long j = this.produced;
                            if (j != this.requested.get()) {
                                this.produced = j + 1;
                                atomicReference.lazySet(null);
                                subscriber.onNext(obj);
                            } else {
                                z = false;
                            }
                        } else {
                            atomicReference.lazySet(null);
                        }
                        if (z && !sequentialDisposable.isDisposed()) {
                            int i = this.index;
                            MaybeSource<? extends T>[] maybeSourceArr = this.sources;
                            if (i != maybeSourceArr.length) {
                                this.index = i + 1;
                                maybeSourceArr[i].subscribe(this);
                            } else if (this.errors.get() != null) {
                                subscriber.onError(this.errors.terminate());
                                return;
                            } else {
                                subscriber.onComplete();
                                return;
                            }
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                atomicReference.lazySet(null);
            }
        }
    }
}