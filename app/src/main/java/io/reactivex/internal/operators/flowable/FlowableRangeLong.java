package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableRangeLong extends Flowable<Long> {
    final long b;
    final long c;

    public FlowableRangeLong(long j, long j2) {
        this.b = j;
        this.c = j + j2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new b((ConditionalSubscriber) subscriber, this.b, this.c));
        } else {
            subscriber.onSubscribe(new c(subscriber, this.b, this.c));
        }
    }

    /* loaded from: classes4.dex */
    static abstract class a extends BasicQueueSubscription<Long> {
        private static final long serialVersionUID = -2252972430506210021L;
        volatile boolean cancelled;
        final long end;
        long index;

        abstract void a(long j);

        abstract void b();

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public final int requestFusion(int i) {
            return i & 1;
        }

        a(long j, long j2) {
            this.index = j;
            this.end = j2;
        }

        @Nullable
        /* renamed from: a */
        public final Long poll() {
            long j = this.index;
            if (j == this.end) {
                return null;
            }
            this.index = 1 + j;
            return Long.valueOf(j);
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
        final Subscriber<? super Long> downstream;

        c(Subscriber<? super Long> subscriber, long j, long j2) {
            super(j, j2);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.a
        void b() {
            long j = this.end;
            Subscriber<? super Long> subscriber = this.downstream;
            for (long j2 = this.index; j2 != j; j2++) {
                if (!this.cancelled) {
                    subscriber.onNext(Long.valueOf(j2));
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                subscriber.onComplete();
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.a
        void a(long j) {
            long j2 = this.end;
            long j3 = this.index;
            Subscriber<? super Long> subscriber = this.downstream;
            long j4 = j3;
            long j5 = j;
            long j6 = 0;
            while (true) {
                if (j6 == j5 || j4 == j2) {
                    if (j4 != j2) {
                        j5 = get();
                        if (j6 == j5) {
                            this.index = j4;
                            j5 = addAndGet(-j6);
                            if (j5 != 0) {
                                j6 = 0;
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
                    subscriber.onNext(Long.valueOf(j4));
                    j6++;
                    j4++;
                } else {
                    return;
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class b extends a {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super Long> downstream;

        b(ConditionalSubscriber<? super Long> conditionalSubscriber, long j, long j2) {
            super(j, j2);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.a
        void b() {
            long j = this.end;
            ConditionalSubscriber<? super Long> conditionalSubscriber = this.downstream;
            for (long j2 = this.index; j2 != j; j2++) {
                if (!this.cancelled) {
                    conditionalSubscriber.tryOnNext(Long.valueOf(j2));
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                conditionalSubscriber.onComplete();
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.a
        void a(long j) {
            long j2 = this.end;
            long j3 = this.index;
            ConditionalSubscriber<? super Long> conditionalSubscriber = this.downstream;
            long j4 = j3;
            long j5 = j;
            long j6 = 0;
            while (true) {
                if (j6 == j5 || j4 == j2) {
                    if (j4 != j2) {
                        j5 = get();
                        if (j6 == j5) {
                            this.index = j4;
                            j5 = addAndGet(-j6);
                            if (j5 != 0) {
                                j6 = 0;
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
                    if (conditionalSubscriber.tryOnNext(Long.valueOf(j4))) {
                        j6++;
                    }
                    j4++;
                } else {
                    return;
                }
            }
        }
    }
}
