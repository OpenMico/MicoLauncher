package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableFromStream<T> extends Flowable<T> {
    final Stream<T> b;

    public FlowableFromStream(Stream<T> stream) {
        this.b = stream;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        subscribeStream(subscriber, this.b);
    }

    public static <T> void subscribeStream(Subscriber<? super T> subscriber, Stream<T> stream) {
        try {
            Iterator<T> it = stream.iterator();
            if (!it.hasNext()) {
                EmptySubscription.complete(subscriber);
                a(stream);
            } else if (subscriber instanceof ConditionalSubscriber) {
                subscriber.onSubscribe(new b((ConditionalSubscriber) subscriber, it, stream));
            } else {
                subscriber.onSubscribe(new c(subscriber, it, stream));
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
            a(stream);
        }
    }

    static void a(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }

    /* loaded from: classes4.dex */
    static abstract class a<T> extends AtomicLong implements QueueSubscription<T> {
        private static final long serialVersionUID = -9082954702547571853L;
        volatile boolean cancelled;
        AutoCloseable closeable;
        Iterator<T> iterator;
        boolean once;

        abstract void a(long j);

        a(Iterator<T> it, AutoCloseable autoCloseable) {
            this.iterator = it;
            this.closeable = autoCloseable;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                a(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            request(1L);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            lazySet(Long.MAX_VALUE);
            return 1;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(@NonNull T t) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(@NonNull T t, @NonNull T t2) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            Iterator<T> it = this.iterator;
            if (it == null) {
                return null;
            }
            if (!this.once) {
                this.once = true;
            } else if (!it.hasNext()) {
                clear();
                return null;
            }
            return (T) Objects.requireNonNull(this.iterator.next(), "The Stream's Iterator.next() returned a null value");
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            Iterator<T> it = this.iterator;
            if (it == null) {
                return true;
            }
            if (!this.once || it.hasNext()) {
                return false;
            }
            clear();
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.iterator = null;
            AutoCloseable autoCloseable = this.closeable;
            this.closeable = null;
            if (autoCloseable != null) {
                FlowableFromStream.a(autoCloseable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T> extends a<T> {
        private static final long serialVersionUID = -9082954702547571853L;
        final Subscriber<? super T> downstream;

        c(Subscriber<? super T> subscriber, Iterator<T> it, AutoCloseable autoCloseable) {
            super(it, autoCloseable);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.rxjava3.internal.jdk8.FlowableFromStream.a
        public void a(long j) {
            Iterator it = this.iterator;
            Subscriber<? super T> subscriber = this.downstream;
            long j2 = j;
            long j3 = 0;
            while (!this.cancelled) {
                try {
                    subscriber.onNext((Object) Objects.requireNonNull(it.next(), "The Stream's Iterator returned a null value"));
                    if (this.cancelled) {
                        continue;
                    } else {
                        try {
                            if (!it.hasNext()) {
                                subscriber.onComplete();
                                this.cancelled = true;
                            } else {
                                j3++;
                                if (j3 != j2) {
                                    continue;
                                } else {
                                    j2 = get();
                                    if (j3 != j2) {
                                        continue;
                                    } else if (!compareAndSet(j2, 0L)) {
                                        j2 = get();
                                    } else {
                                        return;
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            subscriber.onError(th);
                            this.cancelled = true;
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    subscriber.onError(th2);
                    this.cancelled = true;
                }
            }
            clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends a<T> {
        private static final long serialVersionUID = -9082954702547571853L;
        final ConditionalSubscriber<? super T> downstream;

        b(ConditionalSubscriber<? super T> conditionalSubscriber, Iterator<T> it, AutoCloseable autoCloseable) {
            super(it, autoCloseable);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.rxjava3.internal.jdk8.FlowableFromStream.a
        public void a(long j) {
            Iterator it = this.iterator;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            long j2 = j;
            long j3 = 0;
            while (!this.cancelled) {
                try {
                    if (conditionalSubscriber.tryOnNext((Object) Objects.requireNonNull(it.next(), "The Stream's Iterator returned a null value"))) {
                        j3++;
                    }
                    if (this.cancelled) {
                        continue;
                    } else {
                        try {
                            if (!it.hasNext()) {
                                conditionalSubscriber.onComplete();
                                this.cancelled = true;
                            } else if (j3 != j2) {
                                continue;
                            } else {
                                j2 = get();
                                if (j3 != j2) {
                                    continue;
                                } else if (!compareAndSet(j2, 0L)) {
                                    j2 = get();
                                } else {
                                    return;
                                }
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            conditionalSubscriber.onError(th);
                            this.cancelled = true;
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    conditionalSubscriber.onError(th2);
                    this.cancelled = true;
                }
            }
            clear();
        }
    }
}
