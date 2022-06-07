package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableDistinct<T, K> extends a<T, T> {
    final Function<? super T, K> b;
    final Callable<? extends Collection<? super K>> c;

    public FlowableDistinct(Flowable<T> flowable, Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        super(flowable);
        this.b = function;
        this.c = callable;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, (Collection) ObjectHelper.requireNonNull(this.c.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, K> extends BasicFuseableSubscriber<T, T> {
        final Collection<? super K> a;
        final Function<? super T, K> b;

        a(Subscriber<? super T> subscriber, Function<? super T, K> function, Collection<? super K> collection) {
            super(subscriber);
            this.b = function;
            this.a = collection;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode == 0) {
                    try {
                        if (this.a.add(ObjectHelper.requireNonNull(this.b.apply(t), "The keySelector returned a null key"))) {
                            this.downstream.onNext(t);
                        } else {
                            this.upstream.request(1L);
                        }
                    } catch (Throwable th) {
                        fail(th);
                    }
                } else {
                    this.downstream.onNext(null);
                }
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.a.clear();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.a.clear();
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T poll;
            while (true) {
                poll = this.qs.poll();
                if (poll == null || this.a.add((Object) ObjectHelper.requireNonNull(this.b.apply(poll), "The keySelector returned a null key"))) {
                    break;
                } else if (this.sourceMode == 2) {
                    this.upstream.request(1L);
                }
            }
            return poll;
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.a.clear();
            super.clear();
        }
    }
}
