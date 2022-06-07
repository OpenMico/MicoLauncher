package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableDoAfterNext<T> extends a<T, T> {
    final Consumer<? super T> b;

    public FlowableDoAfterNext(Flowable<T> flowable, Consumer<? super T> consumer) {
        super(flowable);
        this.b = consumer;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new a((ConditionalSubscriber) subscriber, this.b));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b));
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends BasicFuseableSubscriber<T, T> {
        final Consumer<? super T> a;

        b(Subscriber<? super T> subscriber, Consumer<? super T> consumer) {
            super(subscriber);
            this.a = consumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                this.downstream.onNext(t);
                if (this.sourceMode == 0) {
                    try {
                        this.a.accept(t);
                    } catch (Throwable th) {
                        fail(th);
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T poll = this.qs.poll();
            if (poll != null) {
                this.a.accept(poll);
            }
            return poll;
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Consumer<? super T> a;

        a(ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super T> consumer) {
            super(conditionalSubscriber);
            this.a = consumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(t);
            if (this.sourceMode == 0) {
                try {
                    this.a.accept(t);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            boolean tryOnNext = this.downstream.tryOnNext(t);
            try {
                this.a.accept(t);
            } catch (Throwable th) {
                fail(th);
            }
            return tryOnNext;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T poll = this.qs.poll();
            if (poll != null) {
                this.a.accept(poll);
            }
            return poll;
        }
    }
}
