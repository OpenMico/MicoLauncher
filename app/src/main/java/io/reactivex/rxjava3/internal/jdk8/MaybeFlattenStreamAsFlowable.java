package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class MaybeFlattenStreamAsFlowable<T, R> extends Flowable<R> {
    final Maybe<T> b;
    final Function<? super T, ? extends Stream<? extends R>> c;

    public MaybeFlattenStreamAsFlowable(Maybe<T> maybe, Function<? super T, ? extends Stream<? extends R>> function) {
        this.b = maybe;
        this.c = function;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(@NonNull Subscriber<? super R> subscriber) {
        this.b.subscribe(new a(subscriber, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends BasicIntQueueSubscription<R> implements MaybeObserver<T>, SingleObserver<T> {
        private static final long serialVersionUID = 7363336003027148283L;
        volatile boolean cancelled;
        AutoCloseable close;
        final Subscriber<? super R> downstream;
        long emitted;
        volatile Iterator<? extends R> iterator;
        final Function<? super T, ? extends Stream<? extends R>> mapper;
        boolean once;
        boolean outputFused;
        final AtomicLong requested = new AtomicLong();
        Disposable upstream;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends Stream<? extends R>> function) {
            this.downstream = subscriber;
            this.mapper = function;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(@NonNull Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(@NonNull T t) {
            try {
                Stream stream = (Stream) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Stream");
                Iterator<? extends R> it = stream.iterator();
                if (!it.hasNext()) {
                    this.downstream.onComplete();
                    a(stream);
                    return;
                }
                this.iterator = it;
                this.close = stream;
                a();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(@NonNull Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.dispose();
            if (!this.outputFused) {
                a();
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public R poll() throws Throwable {
            Iterator<? extends R> it = this.iterator;
            if (it == null) {
                return null;
            }
            if (!this.once) {
                this.once = true;
            } else if (!it.hasNext()) {
                clear();
                return null;
            }
            return (R) it.next();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            Iterator<? extends R> it = this.iterator;
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
            AutoCloseable autoCloseable = this.close;
            this.close = null;
            a(autoCloseable);
        }

        void a(AutoCloseable autoCloseable) {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.downstream;
                long j = this.emitted;
                long j2 = this.requested.get();
                Iterator<? extends R> it = this.iterator;
                int i = 1;
                while (true) {
                    if (this.cancelled) {
                        clear();
                    } else if (this.outputFused) {
                        if (it != null) {
                            subscriber.onNext(null);
                            subscriber.onComplete();
                        }
                    } else if (!(it == null || j == j2)) {
                        try {
                            Object obj = (Object) it.next();
                            if (!this.cancelled) {
                                subscriber.onNext(obj);
                                j++;
                                if (!this.cancelled) {
                                    try {
                                        boolean hasNext = it.hasNext();
                                        if (!this.cancelled && !hasNext) {
                                            subscriber.onComplete();
                                            this.cancelled = true;
                                        }
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        subscriber.onError(th);
                                        this.cancelled = true;
                                    }
                                }
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            subscriber.onError(th2);
                            this.cancelled = true;
                        }
                    }
                    this.emitted = j;
                    i = addAndGet(-i);
                    if (i != 0) {
                        j2 = this.requested.get();
                        if (it == null) {
                            it = this.iterator;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }
}
