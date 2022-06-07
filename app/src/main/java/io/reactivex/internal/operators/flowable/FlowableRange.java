package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableRange extends Flowable<Integer> {
    final int b;
    final int c;

    public FlowableRange(int i, int i2) {
        this.b = i;
        this.c = i + i2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Integer> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new b((ConditionalSubscriber) subscriber, this.b, this.c));
        } else {
            subscriber.onSubscribe(new c(subscriber, this.b, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static abstract class a extends BasicQueueSubscription<Integer> {
        private static final long serialVersionUID = -2252972430506210021L;
        volatile boolean cancelled;
        final int end;
        int index;

        abstract void a(long j);

        abstract void b();

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public final int requestFusion(int i) {
            return i & 1;
        }

        a(int i, int i2) {
            this.index = i;
            this.end = i2;
        }

        @Nullable
        /* renamed from: a */
        public final Integer poll() {
            int i = this.index;
            if (i == this.end) {
                return null;
            }
            this.index = i + 1;
            return Integer.valueOf(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final boolean isEmpty() {
            return this.index == this.end;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final void clear() {
            this.index = this.end;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    b();
                } else {
                    a(j);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.cancelled = true;
        }
    }

    /* loaded from: classes4.dex */
    static final class c extends a {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super Integer> downstream;

        c(Subscriber<? super Integer> subscriber, int i, int i2) {
            super(i, i2);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRange.a
        void b() {
            int i = this.end;
            Subscriber<? super Integer> subscriber = this.downstream;
            for (int i2 = this.index; i2 != i; i2++) {
                if (!this.cancelled) {
                    subscriber.onNext(Integer.valueOf(i2));
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                subscriber.onComplete();
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRange.a
        void a(long j) {
            int i = this.end;
            int i2 = this.index;
            Subscriber<? super Integer> subscriber = this.downstream;
            long j2 = j;
            long j3 = 0;
            while (true) {
                if (j3 == j2 || i2 == i) {
                    if (i2 != i) {
                        j2 = get();
                        if (j3 == j2) {
                            this.index = i2;
                            j2 = addAndGet(-j3);
                            if (j2 != 0) {
                                j3 = 0;
                            } else {
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.cancelled) {
                        subscriber.onComplete();
                        return;
                    } else {
                        return;
                    }
                } else if (!this.cancelled) {
                    subscriber.onNext(Integer.valueOf(i2));
                    j3++;
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class b extends a {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super Integer> downstream;

        b(ConditionalSubscriber<? super Integer> conditionalSubscriber, int i, int i2) {
            super(i, i2);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRange.a
        void b() {
            int i = this.end;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.downstream;
            for (int i2 = this.index; i2 != i; i2++) {
                if (!this.cancelled) {
                    conditionalSubscriber.tryOnNext(Integer.valueOf(i2));
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                conditionalSubscriber.onComplete();
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRange.a
        void a(long j) {
            int i = this.end;
            int i2 = this.index;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.downstream;
            long j2 = j;
            long j3 = 0;
            while (true) {
                if (j3 == j2 || i2 == i) {
                    if (i2 != i) {
                        j2 = get();
                        if (j3 == j2) {
                            this.index = i2;
                            j2 = addAndGet(-j3);
                            if (j2 != 0) {
                                j3 = 0;
                            } else {
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.cancelled) {
                        conditionalSubscriber.onComplete();
                        return;
                    } else {
                        return;
                    }
                } else if (!this.cancelled) {
                    if (conditionalSubscriber.tryOnNext(Integer.valueOf(i2))) {
                        j3++;
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }
}
