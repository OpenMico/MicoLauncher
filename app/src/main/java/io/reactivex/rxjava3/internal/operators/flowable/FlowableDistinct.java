package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableDistinct<T, K> extends a<T, T> {
    final Function<? super T, K> b;
    final Supplier<? extends Collection<? super K>> c;

    public FlowableDistinct(Flowable<T> flowable, Function<? super T, K> function, Supplier<? extends Collection<? super K>> supplier) {
        super(flowable);
        this.b = function;
        this.c = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, (Collection) ExceptionHelper.nullCheck(this.c.get(), "The collectionSupplier returned a null Collection.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    /* loaded from: classes5.dex */
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
                        if (this.a.add(Objects.requireNonNull(this.b.apply(t), "The keySelector returned a null key"))) {
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

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.a.clear();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.a.clear();
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T poll;
            while (true) {
                poll = this.qs.poll();
                if (poll == null || this.a.add((Object) Objects.requireNonNull(this.b.apply(poll), "The keySelector returned a null key"))) {
                    break;
                } else if (this.sourceMode == 2) {
                    this.upstream.request(1L);
                }
            }
            return poll;
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.a.clear();
            super.clear();
        }
    }
}
