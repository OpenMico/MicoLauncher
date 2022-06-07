package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowablePublishAlt<T> extends ConnectableFlowable<T> implements ResettableConnectable, HasUpstreamPublisher<T> {
    final Publisher<T> b;
    final int c;
    final AtomicReference<b<T>> d = new AtomicReference<>();

    public FlowablePublishAlt(Publisher<T> publisher, int i) {
        this.b = publisher;
        this.c = i;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.b;
    }

    public int publishBufferSize() {
        return this.c;
    }

    @Override // io.reactivex.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> consumer) {
        b<T> bVar;
        while (true) {
            bVar = this.d.get();
            if (bVar != null && !bVar.isDisposed()) {
                break;
            }
            b<T> bVar2 = new b<>(this.d, this.c);
            if (this.d.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        boolean z = true;
        if (bVar.connect.get() || !bVar.connect.compareAndSet(false, true)) {
            z = false;
        }
        try {
            consumer.accept(bVar);
            if (z) {
                this.b.subscribe(bVar);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        b<T> bVar;
        while (true) {
            bVar = this.d.get();
            if (bVar != null) {
                break;
            }
            b<T> bVar2 = new b<>(this.d, this.c);
            if (this.d.compareAndSet(bVar, bVar2)) {
                bVar = bVar2;
                break;
            }
        }
        a<T> aVar = new a<>(subscriber, bVar);
        subscriber.onSubscribe(aVar);
        if (!bVar.a(aVar)) {
            Throwable th = bVar.error;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
        } else if (aVar.a()) {
            bVar.b(aVar);
        } else {
            bVar.a();
        }
    }

    @Override // io.reactivex.internal.disposables.ResettableConnectable
    public void resetIf(Disposable disposable) {
        this.d.compareAndSet((b) disposable, null);
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        static final a[] a = new a[0];
        static final a[] b = new a[0];
        private static final long serialVersionUID = -1672047311619175801L;
        final int bufferSize;
        int consumed;
        final AtomicReference<b<T>> current;
        volatile boolean done;
        Throwable error;
        volatile SimpleQueue<T> queue;
        int sourceMode;
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicBoolean connect = new AtomicBoolean();
        final AtomicReference<a<T>[]> subscribers = new AtomicReference<>(a);

        b(AtomicReference<b<T>> atomicReference, int i) {
            this.current = atomicReference;
            this.bufferSize = i;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.subscribers.getAndSet(b);
            this.current.compareAndSet(this, null);
            SubscriptionHelper.cancel(this.upstream);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.subscribers.get() == b;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.upstream, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        subscription.request(this.bufferSize);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.bufferSize);
                subscription.request(this.bufferSize);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode != 0 || this.queue.offer(t)) {
                a();
            } else {
                onError(new MissingBackpressureException("Prefetch queue is full?!"));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            a();
        }

        /* JADX WARN: Code restructure failed: missing block: B:53:0x00d9, code lost:
            if (a(r19.done, r2.isEmpty()) == false) goto L_0x00dd;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00db, code lost:
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                Method dump skipped, instructions count: 241
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowablePublishAlt.b.a():void");
        }

        boolean a(boolean z, boolean z2) {
            if (!z || !z2) {
                return false;
            }
            Throwable th = this.error;
            if (th != null) {
                a(th);
                return true;
            }
            a<T>[] andSet = this.subscribers.getAndSet(b);
            for (a<T> aVar : andSet) {
                if (!aVar.a()) {
                    aVar.downstream.onComplete();
                }
            }
            return true;
        }

        void a(Throwable th) {
            a<T>[] andSet = this.subscribers.getAndSet(b);
            for (a<T> aVar : andSet) {
                if (!aVar.a()) {
                    aVar.downstream.onError(th);
                }
            }
        }

        boolean a(a<T> aVar) {
            a<T>[] aVarArr;
            a<T>[] aVarArr2;
            do {
                aVarArr = this.subscribers.get();
                if (aVarArr == b) {
                    return false;
                }
                int length = aVarArr.length;
                aVarArr2 = new a[length + 1];
                System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                aVarArr2[length] = aVar;
            } while (!this.subscribers.compareAndSet(aVarArr, aVarArr2));
            return true;
        }

        void b(a<T> aVar) {
            a<T>[] aVarArr;
            a<T>[] aVarArr2;
            do {
                aVarArr = this.subscribers.get();
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
                            aVarArr2 = a;
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
            } while (!this.subscribers.compareAndSet(aVarArr, aVarArr2));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = 2845000326761540265L;
        final Subscriber<? super T> downstream;
        long emitted;
        final b<T> parent;

        a(Subscriber<? super T> subscriber, b<T> bVar) {
            this.downstream = subscriber;
            this.parent = bVar;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.addCancel(this, j);
            this.parent.a();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.b(this);
                this.parent.a();
            }
        }

        public boolean a() {
            return get() == Long.MIN_VALUE;
        }
    }
}
