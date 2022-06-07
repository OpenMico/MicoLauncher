package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class AsyncProcessor<T> extends FlowableProcessor<T> {
    static final a[] b = new a[0];
    static final a[] c = new a[0];
    final AtomicReference<a<T>[]> d = new AtomicReference<>(b);
    Throwable e;
    T f;

    @CheckReturnValue
    @NonNull
    public static <T> AsyncProcessor<T> create() {
        return new AsyncProcessor<>();
    }

    AsyncProcessor() {
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.d.get() == c) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.d.get() != c) {
            this.f = t;
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        a<T>[] aVarArr = this.d.get();
        a<T>[] aVarArr2 = c;
        if (aVarArr == aVarArr2) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.f = null;
        this.e = th;
        for (a<T> aVar : this.d.getAndSet(aVarArr2)) {
            aVar.a(th);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        a<T>[] aVarArr = this.d.get();
        a<T>[] aVarArr2 = c;
        if (aVarArr != aVarArr2) {
            T t = this.f;
            a<T>[] andSet = this.d.getAndSet(aVarArr2);
            int i = 0;
            if (t == null) {
                int length = andSet.length;
                while (i < length) {
                    andSet[i].a();
                    i++;
                }
                return;
            }
            int length2 = andSet.length;
            while (i < length2) {
                andSet[i].complete(t);
                i++;
            }
        }
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.d.get().length != 0;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        return this.d.get() == c && this.e != null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        return this.d.get() == c && this.e == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    @Nullable
    public Throwable getThrowable() {
        if (this.d.get() == c) {
            return this.e;
        }
        return null;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a<T> aVar = new a<>(subscriber, this);
        subscriber.onSubscribe(aVar);
        if (!a(aVar)) {
            Throwable th = this.e;
            if (th != null) {
                subscriber.onError(th);
                return;
            }
            T t = this.f;
            if (t != null) {
                aVar.complete(t);
            } else {
                aVar.a();
            }
        } else if (aVar.isCancelled()) {
            b(aVar);
        }
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.d.get();
            if (aVarArr == c) {
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
            int length = aVarArr.length;
            if (length != 0) {
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
                        aVarArr2 = b;
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

    public boolean hasValue() {
        return this.d.get() == c && this.f != null;
    }

    @Nullable
    public T getValue() {
        if (this.d.get() == c) {
            return this.f;
        }
        return null;
    }

    @Deprecated
    public Object[] getValues() {
        T value = getValue();
        return value != null ? new Object[]{value} : new Object[0];
    }

    @Deprecated
    public T[] getValues(T[] tArr) {
        T value = getValue();
        if (value == null) {
            if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }
        if (tArr.length == 0) {
            tArr = (T[]) Arrays.copyOf(tArr, 1);
        }
        tArr[0] = value;
        if (tArr.length != 1) {
            tArr[1] = null;
        }
        return tArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends DeferredScalarSubscription<T> {
        private static final long serialVersionUID = 5629876084736248016L;
        final AsyncProcessor<T> parent;

        a(Subscriber<? super T> subscriber, AsyncProcessor<T> asyncProcessor) {
            super(subscriber);
            this.parent = asyncProcessor;
        }

        @Override // io.reactivex.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            if (super.tryCancel()) {
                this.parent.b(this);
            }
        }

        void a() {
            if (!isCancelled()) {
                this.downstream.onComplete();
            }
        }

        void a(Throwable th) {
            if (isCancelled()) {
                RxJavaPlugins.onError(th);
            } else {
                this.downstream.onError(th);
            }
        }
    }
}
