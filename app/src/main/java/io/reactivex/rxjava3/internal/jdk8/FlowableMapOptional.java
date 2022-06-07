package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import java.util.Objects;
import java.util.Optional;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableMapOptional<T, R> extends Flowable<R> {
    final Flowable<T> b;
    final Function<? super T, Optional<? extends R>> c;

    public FlowableMapOptional(Flowable<T> flowable, Function<? super T, Optional<? extends R>> function) {
        this.b = flowable;
        this.c = function;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.b.subscribe((FlowableSubscriber) new a((ConditionalSubscriber) subscriber, this.c));
        } else {
            this.b.subscribe((FlowableSubscriber) new b(subscriber, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, R> extends BasicFuseableSubscriber<T, R> implements ConditionalSubscriber<T> {
        final Function<? super T, Optional<? extends R>> a;

        b(Subscriber<? super R> subscriber, Function<? super T, Optional<? extends R>> function) {
            super(subscriber);
            this.a = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.upstream.request(1L);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return true;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                Optional optional = (Optional) Objects.requireNonNull(this.a.apply(t), "The mapper returned a null Optional");
                if (!optional.isPresent()) {
                    return false;
                }
                this.downstream.onNext(optional.get());
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public R poll() throws Throwable {
            while (true) {
                T poll = this.qs.poll();
                if (poll == null) {
                    return null;
                }
                Optional optional = (Optional) Objects.requireNonNull(this.a.apply(poll), "The mapper returned a null Optional");
                if (optional.isPresent()) {
                    return (R) optional.get();
                }
                if (this.sourceMode == 2) {
                    this.qs.request(1L);
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends BasicFuseableConditionalSubscriber<T, R> {
        final Function<? super T, Optional<? extends R>> a;

        a(ConditionalSubscriber<? super R> conditionalSubscriber, Function<? super T, Optional<? extends R>> function) {
            super(conditionalSubscriber);
            this.a = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.upstream.request(1L);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return true;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                Optional optional = (Optional) Objects.requireNonNull(this.a.apply(t), "The mapper returned a null Optional");
                if (optional.isPresent()) {
                    return this.downstream.tryOnNext(optional.get());
                }
                return false;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public R poll() throws Throwable {
            while (true) {
                T poll = this.qs.poll();
                if (poll == null) {
                    return null;
                }
                Optional optional = (Optional) Objects.requireNonNull(this.a.apply(poll), "The mapper returned a null Optional");
                if (optional.isPresent()) {
                    return (R) optional.get();
                }
                if (this.sourceMode == 2) {
                    this.qs.request(1L);
                }
            }
        }
    }
}
