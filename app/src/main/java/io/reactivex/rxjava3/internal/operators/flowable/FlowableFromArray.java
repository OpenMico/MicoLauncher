package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class FlowableFromArray<T> extends Flowable<T> {
    final T[] b;

    public FlowableFromArray(T[] tArr) {
        this.b = tArr;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new a((ConditionalSubscriber) subscriber, this.b));
        } else {
            subscriber.onSubscribe(new b(subscriber, this.b));
        }
    }

    /* loaded from: classes5.dex */
    static abstract class c<T> extends BasicQueueSubscription<T> {
        private static final long serialVersionUID = -2252972430506210021L;
        final T[] array;
        volatile boolean cancelled;
        int index;

        abstract void a();

        abstract void a(long j);

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public final int requestFusion(int i) {
            return i & 1;
        }

        c(T[] tArr) {
            this.array = tArr;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public final T poll() {
            int i = this.index;
            T[] tArr = this.array;
            if (i == tArr.length) {
                return null;
            }
            this.index = i + 1;
            return (T) Objects.requireNonNull(tArr[i], "array element is null");
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public final boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public final void clear() {
            this.index = this.array.length;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    a();
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

    /* loaded from: classes5.dex */
    static final class b<T> extends c<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super T> downstream;

        b(Subscriber<? super T> subscriber, T[] tArr) {
            super(tArr);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.c
        void a() {
            Object[] objArr = this.array;
            int length = objArr.length;
            Subscriber<? super T> subscriber = this.downstream;
            for (int i = this.index; i != length; i++) {
                if (!this.cancelled) {
                    Object obj = objArr[i];
                    if (obj == null) {
                        subscriber.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    subscriber.onNext(obj);
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                subscriber.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.c
        void a(long j) {
            Object[] objArr = this.array;
            int length = objArr.length;
            int i = this.index;
            Subscriber<? super T> subscriber = this.downstream;
            long j2 = j;
            long j3 = 0;
            while (true) {
                if (j3 == j2 || i == length) {
                    if (i != length) {
                        j2 = get();
                        if (j3 == j2) {
                            this.index = i;
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
                    Object obj = objArr[i];
                    if (obj == null) {
                        subscriber.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    subscriber.onNext(obj);
                    j3++;
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends c<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super T> downstream;

        a(ConditionalSubscriber<? super T> conditionalSubscriber, T[] tArr) {
            super(tArr);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.c
        void a() {
            Object[] objArr = this.array;
            int length = objArr.length;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            for (int i = this.index; i != length; i++) {
                if (!this.cancelled) {
                    Object obj = objArr[i];
                    if (obj == null) {
                        conditionalSubscriber.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    conditionalSubscriber.tryOnNext(obj);
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                conditionalSubscriber.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableFromArray.c
        void a(long j) {
            Object[] objArr = this.array;
            int length = objArr.length;
            int i = this.index;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            long j2 = j;
            long j3 = 0;
            while (true) {
                if (j3 == j2 || i == length) {
                    if (i != length) {
                        j2 = get();
                        if (j3 == j2) {
                            this.index = i;
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
                    Object obj = objArr[i];
                    if (obj == null) {
                        conditionalSubscriber.onError(new NullPointerException("The element at index " + i + " is null"));
                        return;
                    }
                    if (conditionalSubscriber.tryOnNext(obj)) {
                        j3++;
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
