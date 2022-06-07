package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class PublishProcessor<T> extends FlowableProcessor<T> {
    static final a[] b = new a[0];
    static final a[] c = new a[0];
    final AtomicReference<a<T>[]> d = new AtomicReference<>(c);
    Throwable e;

    @CheckReturnValue
    @NonNull
    public static <T> PublishProcessor<T> create() {
        return new PublishProcessor<>();
    }

    PublishProcessor() {
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(@NonNull Subscriber<? super T> subscriber) {
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        if (!a(aVar)) {
            Throwable th = this.e;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
        } else if (aVar.b()) {
            b(aVar);
        }
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
            if (aVarArr == b) {
                return false;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
        return true;
    }

    void b(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
            if (aVarArr != b && aVarArr != c) {
                int length = aVarArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (aVarArr[i2] == aVar) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        aVarArr2 = c;
                    } else {
                        a<T>[] aVarArr3 = new a[length - 1];
                        System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                        System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                        aVarArr2 = aVarArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.d.compareAndSet(aVarArr, aVarArr2));
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription subscription) {
        if (this.d.get() == b) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t) {
        ExceptionHelper.nullCheck(t, "onNext called with a null value.");
        for (a<T> aVar : this.d.get()) {
            aVar.a((a<T>) t);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        a<T>[] aVarArr = this.d.get();
        a<T>[] aVarArr2 = b;
        if (aVarArr == aVarArr2) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.e = th;
        for (a<T> aVar : this.d.getAndSet(aVarArr2)) {
            aVar.a(th);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        a<T>[] aVarArr = this.d.get();
        a<T>[] aVarArr2 = b;
        if (aVarArr != aVarArr2) {
            for (a<T> aVar : this.d.getAndSet(aVarArr2)) {
                aVar.a();
            }
        }
    }

    @CheckReturnValue
    public boolean offer(@NonNull T t) {
        ExceptionHelper.nullCheck(t, "offer called with a null value.");
        a<T>[] aVarArr = this.d.get();
        for (a<T> aVar : aVarArr) {
            if (aVar.c()) {
                return false;
            }
        }
        for (a<T> aVar2 : aVarArr) {
            aVar2.a((a<T>) t);
        }
        return true;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.d.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        if (this.d.get() == b) {
            return this.e;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.d.get() == b && this.e != null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        return this.d.get() == b && this.e == null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = 3562861878281475070L;
        final Subscriber<? super T> downstream;
        final PublishProcessor<T> parent;

        a(Subscriber<? super T> subscriber, PublishProcessor<T> publishProcessor) {
            this.downstream = subscriber;
            this.parent = publishProcessor;
        }

        public void a(T t) {
            long j = get();
            if (j != Long.MIN_VALUE) {
                if (j != 0) {
                    this.downstream.onNext(t);
                    BackpressureHelper.producedCancel(this, 1L);
                    return;
                }
                cancel();
                this.downstream.onError(new MissingBackpressureException("Could not emit value due to lack of requests"));
            }
        }

        public void a(Throwable th) {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void a() {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.b(this);
            }
        }

        public boolean b() {
            return get() == Long.MIN_VALUE;
        }

        boolean c() {
            return get() == 0;
        }
    }
}
