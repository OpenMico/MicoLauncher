package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowablePublish<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, FlowablePublishClassic<T> {
    final Flowable<T> b;
    final AtomicReference<c<T>> c;
    final int d;
    final Publisher<T> e;

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowablePublish(new a(atomicReference, i), flowable, atomicReference, i));
    }

    private FlowablePublish(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<c<T>> atomicReference, int i) {
        this.e = publisher;
        this.b = flowable;
        this.c = atomicReference;
        this.d = i;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamPublisher
    public Publisher<T> source() {
        return this.b;
    }

    @Override // io.reactivex.internal.operators.flowable.FlowablePublishClassic
    public int publishBufferSize() {
        return this.d;
    }

    @Override // io.reactivex.internal.operators.flowable.FlowablePublishClassic
    public Publisher<T> publishSource() {
        return this.b;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.e.subscribe(subscriber);
    }

    @Override // io.reactivex.flowables.ConnectableFlowable
    public void connect(Consumer<? super Disposable> consumer) {
        c<T> cVar;
        while (true) {
            cVar = this.c.get();
            if (cVar != null && !cVar.isDisposed()) {
                break;
            }
            c<T> cVar2 = new c<>(this.c, this.d);
            if (this.c.compareAndSet(cVar, cVar2)) {
                cVar = cVar2;
                break;
            }
        }
        boolean z = true;
        if (cVar.shouldConnect.get() || !cVar.shouldConnect.compareAndSet(false, true)) {
            z = false;
        }
        try {
            consumer.accept(cVar);
            if (z) {
                this.b.subscribe((FlowableSubscriber) cVar);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    /* loaded from: classes4.dex */
    static final class c<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        static final b[] a = new b[0];
        static final b[] b = new b[0];
        private static final long serialVersionUID = -202316842419149694L;
        final int bufferSize;
        final AtomicReference<c<T>> current;
        volatile SimpleQueue<T> queue;
        int sourceMode;
        volatile Object terminalEvent;
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicReference<b<T>[]> subscribers = new AtomicReference<>(a);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        c(AtomicReference<c<T>> atomicReference, int i) {
            this.current = atomicReference;
            this.bufferSize = i;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            b<T>[] bVarArr = this.subscribers.get();
            b<T>[] bVarArr2 = b;
            if (bVarArr != bVarArr2 && this.subscribers.getAndSet(bVarArr2) != b) {
                this.current.compareAndSet(this, null);
                SubscriptionHelper.cancel(this.upstream);
            }
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
                        this.terminalEvent = NotificationLite.complete();
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
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.error(th);
                a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.complete();
                a();
            }
        }

        boolean a(b<T> bVar) {
            b<T>[] bVarArr;
            b<T>[] bVarArr2;
            do {
                bVarArr = this.subscribers.get();
                if (bVarArr == b) {
                    return false;
                }
                int length = bVarArr.length;
                bVarArr2 = new b[length + 1];
                System.arraycopy(bVarArr, 0, bVarArr2, 0, length);
                bVarArr2[length] = bVar;
            } while (!this.subscribers.compareAndSet(bVarArr, bVarArr2));
            return true;
        }

        void b(b<T> bVar) {
            b<T>[] bVarArr;
            b<T>[] bVarArr2;
            do {
                bVarArr = this.subscribers.get();
                int length = bVarArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (bVarArr[i2].equals(bVar)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            bVarArr2 = a;
                        } else {
                            b<T>[] bVarArr3 = new b[length - 1];
                            System.arraycopy(bVarArr, 0, bVarArr3, 0, i);
                            System.arraycopy(bVarArr, i + 1, bVarArr3, i, (length - i) - 1);
                            bVarArr2 = bVarArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(bVarArr, bVarArr2));
        }

        boolean a(Object obj, boolean z) {
            int i = 0;
            if (obj != null) {
                if (!NotificationLite.isComplete(obj)) {
                    Throwable error = NotificationLite.getError(obj);
                    this.current.compareAndSet(this, null);
                    b<T>[] andSet = this.subscribers.getAndSet(b);
                    if (andSet.length != 0) {
                        int length = andSet.length;
                        while (i < length) {
                            andSet[i].child.onError(error);
                            i++;
                        }
                    } else {
                        RxJavaPlugins.onError(error);
                    }
                    return true;
                } else if (z) {
                    this.current.compareAndSet(this, null);
                    b<T>[] andSet2 = this.subscribers.getAndSet(b);
                    int length2 = andSet2.length;
                    while (i < length2) {
                        andSet2[i].child.onComplete();
                        i++;
                    }
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Code restructure failed: missing block: B:75:0x0142, code lost:
            if (r8 == 0) goto L_0x0155;
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x0144, code lost:
            r6 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x0147, code lost:
            if (r25.sourceMode == 1) goto L_0x0156;
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x0149, code lost:
            r25.upstream.get().request(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:79:0x0155, code lost:
            r6 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:81:0x015a, code lost:
            if (r14 == 0) goto L_0x0162;
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x015c, code lost:
            if (r0 != false) goto L_0x0162;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                Method dump skipped, instructions count: 372
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowablePublish.c.a():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        long emitted;
        volatile c<T> parent;

        b(Subscriber<? super T> subscriber) {
            this.child = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                c<T> cVar = this.parent;
                if (cVar != null) {
                    cVar.a();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            c<T> cVar;
            if (get() != Long.MIN_VALUE && getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE && (cVar = this.parent) != null) {
                cVar.b(this);
                cVar.a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> implements Publisher<T> {
        private final AtomicReference<c<T>> a;
        private final int b;

        a(AtomicReference<c<T>> atomicReference, int i) {
            this.a = atomicReference;
            this.b = i;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            c<T> cVar;
            b<T> bVar = new b<>(subscriber);
            subscriber.onSubscribe(bVar);
            while (true) {
                cVar = this.a.get();
                if (cVar == null || cVar.isDisposed()) {
                    c<T> cVar2 = new c<>(this.a, this.b);
                    if (!this.a.compareAndSet(cVar, cVar2)) {
                        continue;
                    } else {
                        cVar = cVar2;
                    }
                }
                if (cVar.a(bVar)) {
                    break;
                }
            }
            if (bVar.get() == Long.MIN_VALUE) {
                cVar.b(bVar);
            } else {
                bVar.parent = cVar;
            }
            cVar.a();
        }
    }
}
