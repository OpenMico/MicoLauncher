package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableDistinctUntilChanged<T, K> extends a<T, T> {
    final Function<? super T, K> b;
    final BiPredicate<? super K, ? super K> c;

    public FlowableDistinctUntilChanged(Flowable<T> flowable, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
        super(flowable);
        this.b = function;
        this.c = biPredicate;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new a((ConditionalSubscriber) subscriber, this.b, this.c));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, K> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        final Function<? super T, K> a;
        final BiPredicate<? super K, ? super K> b;
        K c;
        boolean d;

        b(Subscriber<? super T> subscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(subscriber);
            this.a = function;
            this.b = biPredicate;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.upstream.request(1L);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(t);
                return true;
            }
            try {
                K apply = this.a.apply(t);
                if (this.d) {
                    boolean test = this.b.test((K) this.c, apply);
                    this.c = apply;
                    if (test) {
                        return false;
                    }
                } else {
                    this.d = true;
                    this.c = apply;
                }
                this.downstream.onNext(t);
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            while (true) {
                T poll = this.qs.poll();
                if (poll == null) {
                    return null;
                }
                K apply = this.a.apply(poll);
                if (!this.d) {
                    this.d = true;
                    this.c = apply;
                    return poll;
                } else if (!this.b.test((K) this.c, apply)) {
                    this.c = apply;
                    return poll;
                } else {
                    this.c = apply;
                    if (this.sourceMode != 1) {
                        this.upstream.request(1L);
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, K> extends BasicFuseableConditionalSubscriber<T, T> {
        final Function<? super T, K> a;
        final BiPredicate<? super K, ? super K> b;
        K c;
        boolean d;

        a(ConditionalSubscriber<? super T> conditionalSubscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(conditionalSubscriber);
            this.a = function;
            this.b = biPredicate;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.upstream.request(1L);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                return this.downstream.tryOnNext(t);
            }
            try {
                K apply = this.a.apply(t);
                if (this.d) {
                    boolean test = this.b.test((K) this.c, apply);
                    this.c = apply;
                    if (test) {
                        return false;
                    }
                } else {
                    this.d = true;
                    this.c = apply;
                }
                this.downstream.onNext(t);
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            while (true) {
                T poll = this.qs.poll();
                if (poll == null) {
                    return null;
                }
                K apply = this.a.apply(poll);
                if (!this.d) {
                    this.d = true;
                    this.c = apply;
                    return poll;
                } else if (!this.b.test((K) this.c, apply)) {
                    this.c = apply;
                    return poll;
                } else {
                    this.c = apply;
                    if (this.sourceMode != 1) {
                        this.upstream.request(1L);
                    }
                }
            }
        }
    }
}
