package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends a<TLeft, R> {
    final Publisher<? extends TRight> b;
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> c;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> d;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> e;

    public FlowableJoin(Flowable<TLeft> flowable, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = function2;
        this.e = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        a aVar = new a(subscriber, this.c, this.d, this.e);
        subscriber.onSubscribe(aVar);
        FlowableGroupJoin.d dVar = new FlowableGroupJoin.d(aVar, true);
        aVar.disposables.add(dVar);
        FlowableGroupJoin.d dVar2 = new FlowableGroupJoin.d(aVar, false);
        aVar.disposables.add(dVar2);
        this.source.subscribe((FlowableSubscriber) dVar);
        this.b.subscribe(dVar2);
    }

    /* loaded from: classes5.dex */
    static final class a<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements FlowableGroupJoin.b, Subscription {
        static final Integer a = 1;
        static final Integer b = 2;
        static final Integer c = 3;
        static final Integer d = 4;
        private static final long serialVersionUID = -6071216598687999801L;
        volatile boolean cancelled;
        final Subscriber<? super R> downstream;
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        int leftIndex;
        final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        int rightIndex;
        final AtomicLong requested = new AtomicLong();
        final CompositeDisposable disposables = new CompositeDisposable();
        final SpscLinkedArrayQueue<Object> queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final Map<Integer, TLeft> lefts = new LinkedHashMap();
        final Map<Integer, TRight> rights = new LinkedHashMap();
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final AtomicInteger active = new AtomicInteger(2);

        a(Subscriber<? super R> subscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
            this.downstream = subscriber;
            this.leftEnd = function;
            this.rightEnd = function2;
            this.resultSelector = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        void a() {
            this.disposables.dispose();
        }

        void a(Subscriber<?> subscriber) {
            Throwable terminate = ExceptionHelper.terminate(this.error);
            this.lefts.clear();
            this.rights.clear();
            subscriber.onError(terminate);
        }

        void a(Throwable th, Subscriber<?> subscriber, SimpleQueue<?> simpleQueue) {
            Exceptions.throwIfFatal(th);
            ExceptionHelper.addThrowable(this.error, th);
            simpleQueue.clear();
            a();
            a(subscriber);
        }

        /* JADX WARN: Multi-variable type inference failed */
        void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
                Subscriber<? super R> subscriber = this.downstream;
                boolean z = true;
                int i = 1;
                while (!this.cancelled) {
                    if (this.error.get() != null) {
                        spscLinkedArrayQueue.clear();
                        a();
                        a(subscriber);
                        return;
                    }
                    boolean z2 = this.active.get() == 0 ? z : false;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z3 = num == null ? z : false;
                    if (z2 && z3) {
                        this.lefts.clear();
                        this.rights.clear();
                        this.disposables.dispose();
                        subscriber.onComplete();
                        return;
                    } else if (z3) {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        Object poll = spscLinkedArrayQueue.poll();
                        if (num == a) {
                            int i2 = this.leftIndex;
                            this.leftIndex = i2 + 1;
                            this.lefts.put(Integer.valueOf(i2), poll);
                            try {
                                Publisher publisher = (Publisher) Objects.requireNonNull(this.leftEnd.apply(poll), "The leftEnd returned a null Publisher");
                                FlowableGroupJoin.c cVar = new FlowableGroupJoin.c(this, z, i2);
                                this.disposables.add(cVar);
                                publisher.subscribe(cVar);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(subscriber);
                                    return;
                                }
                                long j = this.requested.get();
                                long j2 = 0;
                                for (TRight tright : this.rights.values()) {
                                    try {
                                        Object obj = (Object) Objects.requireNonNull(this.resultSelector.apply(poll, tright), "The resultSelector returned a null value");
                                        if (j2 != j) {
                                            subscriber.onNext(obj);
                                            j2++;
                                        } else {
                                            ExceptionHelper.addThrowable(this.error, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                            spscLinkedArrayQueue.clear();
                                            a();
                                            a(subscriber);
                                            return;
                                        }
                                    } catch (Throwable th) {
                                        a(th, subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                if (j2 != 0) {
                                    BackpressureHelper.produced(this.requested, j2);
                                }
                            } catch (Throwable th2) {
                                a(th2, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == b) {
                            int i3 = this.rightIndex;
                            this.rightIndex = i3 + 1;
                            this.rights.put(Integer.valueOf(i3), poll);
                            try {
                                Publisher publisher2 = (Publisher) Objects.requireNonNull(this.rightEnd.apply(poll), "The rightEnd returned a null Publisher");
                                FlowableGroupJoin.c cVar2 = new FlowableGroupJoin.c(this, false, i3);
                                this.disposables.add(cVar2);
                                publisher2.subscribe(cVar2);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(subscriber);
                                    return;
                                }
                                long j3 = this.requested.get();
                                long j4 = 0;
                                for (TLeft tleft : this.lefts.values()) {
                                    try {
                                        Object obj2 = (Object) Objects.requireNonNull(this.resultSelector.apply(tleft, poll), "The resultSelector returned a null value");
                                        if (j4 != j3) {
                                            subscriber.onNext(obj2);
                                            j4++;
                                        } else {
                                            ExceptionHelper.addThrowable(this.error, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                            spscLinkedArrayQueue.clear();
                                            a();
                                            a(subscriber);
                                            return;
                                        }
                                    } catch (Throwable th3) {
                                        a(th3, subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                if (j4 != 0) {
                                    BackpressureHelper.produced(this.requested, j4);
                                }
                            } catch (Throwable th4) {
                                a(th4, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == c) {
                            FlowableGroupJoin.c cVar3 = (FlowableGroupJoin.c) poll;
                            this.lefts.remove(Integer.valueOf(cVar3.index));
                            this.disposables.remove(cVar3);
                        } else {
                            FlowableGroupJoin.c cVar4 = (FlowableGroupJoin.c) poll;
                            this.rights.remove(Integer.valueOf(cVar4.index));
                            this.disposables.remove(cVar4);
                        }
                        z = true;
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.b
        public void a(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                this.active.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.b
        public void a(FlowableGroupJoin.d dVar) {
            this.disposables.delete(dVar);
            this.active.decrementAndGet();
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.b
        public void a(boolean z, Object obj) {
            synchronized (this) {
                this.queue.offer(z ? a : b, obj);
            }
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.b
        public void a(boolean z, FlowableGroupJoin.c cVar) {
            synchronized (this) {
                this.queue.offer(z ? c : d, cVar);
            }
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableGroupJoin.b
        public void b(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }
}
