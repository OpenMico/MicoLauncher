package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends a<TLeft, R> {
    final Publisher<? extends TRight> b;
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> c;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> d;
    final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface b {
        void a(d dVar);

        void a(Throwable th);

        void a(boolean z, c cVar);

        void a(boolean z, Object obj);

        void b(Throwable th);
    }

    public FlowableGroupJoin(Flowable<TLeft> flowable, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> biFunction) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = function2;
        this.e = biFunction;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        a aVar = new a(subscriber, this.c, this.d, this.e);
        subscriber.onSubscribe(aVar);
        d dVar = new d(aVar, true);
        aVar.disposables.add(dVar);
        d dVar2 = new d(aVar, false);
        aVar.disposables.add(dVar2);
        this.source.subscribe((FlowableSubscriber) dVar);
        this.b.subscribe(dVar2);
    }

    /* loaded from: classes4.dex */
    static final class a<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements b, Subscription {
        static final Integer a = 1;
        static final Integer b = 2;
        static final Integer c = 3;
        static final Integer d = 4;
        private static final long serialVersionUID = -6071216598687999801L;
        volatile boolean cancelled;
        final Subscriber<? super R> downstream;
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        int leftIndex;
        final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        int rightIndex;
        final AtomicLong requested = new AtomicLong();
        final CompositeDisposable disposables = new CompositeDisposable();
        final SpscLinkedArrayQueue<Object> queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final Map<Integer, UnicastProcessor<TRight>> lefts = new LinkedHashMap();
        final Map<Integer, TRight> rights = new LinkedHashMap();
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final AtomicInteger active = new AtomicInteger(2);

        a(Subscriber<? super R> subscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> biFunction) {
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
            for (UnicastProcessor<TRight> unicastProcessor : this.lefts.values()) {
                unicastProcessor.onError(terminate);
            }
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
                int i = 1;
                while (!this.cancelled) {
                    if (this.error.get() != null) {
                        spscLinkedArrayQueue.clear();
                        a();
                        a(subscriber);
                        return;
                    }
                    boolean z = this.active.get() == 0;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z2 = num == null;
                    if (z && z2) {
                        for (UnicastProcessor<TRight> unicastProcessor : this.lefts.values()) {
                            unicastProcessor.onComplete();
                        }
                        this.lefts.clear();
                        this.rights.clear();
                        this.disposables.dispose();
                        subscriber.onComplete();
                        return;
                    } else if (z2) {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        Object poll = spscLinkedArrayQueue.poll();
                        if (num == a) {
                            UnicastProcessor<TRight> create = UnicastProcessor.create();
                            int i2 = this.leftIndex;
                            this.leftIndex = i2 + 1;
                            this.lefts.put(Integer.valueOf(i2), create);
                            try {
                                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.leftEnd.apply(poll), "The leftEnd returned a null Publisher");
                                c cVar = new c(this, true, i2);
                                this.disposables.add(cVar);
                                publisher.subscribe(cVar);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(subscriber);
                                    return;
                                }
                                try {
                                    Object obj = (Object) ObjectHelper.requireNonNull(this.resultSelector.apply(poll, create), "The resultSelector returned a null value");
                                    if (this.requested.get() != 0) {
                                        subscriber.onNext(obj);
                                        BackpressureHelper.produced(this.requested, 1L);
                                        for (TRight tright : this.rights.values()) {
                                            create.onNext(tright);
                                        }
                                    } else {
                                        a(new MissingBackpressureException("Could not emit value due to lack of requests"), subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                } catch (Throwable th) {
                                    a(th, subscriber, spscLinkedArrayQueue);
                                    return;
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
                                Publisher publisher2 = (Publisher) ObjectHelper.requireNonNull(this.rightEnd.apply(poll), "The rightEnd returned a null Publisher");
                                c cVar2 = new c(this, false, i3);
                                this.disposables.add(cVar2);
                                publisher2.subscribe(cVar2);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(subscriber);
                                    return;
                                }
                                for (UnicastProcessor<TRight> unicastProcessor2 : this.lefts.values()) {
                                    unicastProcessor2.onNext(poll);
                                }
                            } catch (Throwable th3) {
                                a(th3, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == c) {
                            c cVar3 = (c) poll;
                            UnicastProcessor<TRight> remove = this.lefts.remove(Integer.valueOf(cVar3.index));
                            this.disposables.remove(cVar3);
                            if (remove != null) {
                                remove.onComplete();
                            }
                        } else if (num == d) {
                            c cVar4 = (c) poll;
                            this.rights.remove(Integer.valueOf(cVar4.index));
                            this.disposables.remove(cVar4);
                        }
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                this.active.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(d dVar) {
            this.disposables.delete(dVar);
            this.active.decrementAndGet();
            b();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(boolean z, Object obj) {
            synchronized (this) {
                this.queue.offer(z ? a : b, obj);
            }
            b();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void a(boolean z, c cVar) {
            synchronized (this) {
                this.queue.offer(z ? c : d, cVar);
            }
            b();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.b
        public void b(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class d extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final boolean isLeft;
        final b parent;

        /* JADX INFO: Access modifiers changed from: package-private */
        public d(b bVar, boolean z) {
            this.parent = bVar;
            this.isLeft = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.parent.a(this.isLeft, obj);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.a(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final int index;
        final boolean isLeft;
        final b parent;

        /* JADX INFO: Access modifiers changed from: package-private */
        public c(b bVar, boolean z, int i) {
            this.parent = bVar;
            this.isLeft = z;
            this.index = i;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (SubscriptionHelper.cancel(this)) {
                this.parent.a(this.isLeft, this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.b(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.a(this.isLeft, this);
        }
    }
}
