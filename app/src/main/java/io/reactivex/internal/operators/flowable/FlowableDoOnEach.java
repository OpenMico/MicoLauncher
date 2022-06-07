package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableDoOnEach<T> extends a<T, T> {
    final Consumer<? super T> b;
    final Consumer<? super Throwable> c;
    final Action d;
    final Action e;

    public FlowableDoOnEach(Flowable<T> flowable, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
        super(flowable);
        this.b = consumer;
        this.c = consumer2;
        this.d = action;
        this.e = action2;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new a((ConditionalSubscriber) subscriber, this.b, this.c, this.d, this.e));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.c, this.d, this.e));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends BasicFuseableSubscriber<T, T> {
        final Consumer<? super T> a;
        final Consumer<? super Throwable> b;
        final Action c;
        final Action d;

        b(Subscriber<? super T> subscriber, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
            super(subscriber);
            this.a = consumer;
            this.b = consumer2;
            this.c = action;
            this.d = action2;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.downstream.onNext(null);
                    return;
                }
                try {
                    this.a.accept(t);
                    this.downstream.onNext(t);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            boolean z = true;
            this.done = true;
            try {
                this.b.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
                z = false;
            }
            if (z) {
                this.downstream.onError(th);
            }
            try {
                this.d.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                try {
                    this.c.run();
                    this.done = true;
                    this.downstream.onComplete();
                    try {
                        this.d.run();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        RxJavaPlugins.onError(th);
                    }
                } catch (Throwable th2) {
                    fail(th2);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            try {
                T poll = this.qs.poll();
                if (poll != null) {
                    try {
                        this.a.accept(poll);
                        this.d.run();
                    } catch (Throwable th) {
                        this.d.run();
                        throw th;
                    }
                } else if (this.sourceMode == 1) {
                    this.c.run();
                    this.d.run();
                }
                return poll;
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                try {
                    this.b.accept(th2);
                    throw ExceptionHelper.throwIfThrowable(th2);
                } catch (Throwable th3) {
                    throw new CompositeException(th2, th3);
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Consumer<? super T> a;
        final Consumer<? super Throwable> b;
        final Action c;
        final Action d;

        a(ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
            super(conditionalSubscriber);
            this.a = consumer;
            this.b = consumer2;
            this.c = action;
            this.d = action2;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.downstream.onNext(null);
                    return;
                }
                try {
                    this.a.accept(t);
                    this.downstream.onNext(t);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            try {
                this.a.accept(t);
                return this.downstream.tryOnNext(t);
            } catch (Throwable th) {
                fail(th);
                return false;
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            boolean z = true;
            this.done = true;
            try {
                this.b.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
                z = false;
            }
            if (z) {
                this.downstream.onError(th);
            }
            try {
                this.d.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                try {
                    this.c.run();
                    this.done = true;
                    this.downstream.onComplete();
                    try {
                        this.d.run();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        RxJavaPlugins.onError(th);
                    }
                } catch (Throwable th2) {
                    fail(th2);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            try {
                T poll = this.qs.poll();
                if (poll != null) {
                    try {
                        this.a.accept(poll);
                        this.d.run();
                    } catch (Throwable th) {
                        this.d.run();
                        throw th;
                    }
                } else if (this.sourceMode == 1) {
                    this.c.run();
                    this.d.run();
                }
                return poll;
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                try {
                    this.b.accept(th2);
                    throw ExceptionHelper.throwIfThrowable(th2);
                } catch (Throwable th3) {
                    throw new CompositeException(th2, th3);
                }
            }
        }
    }
}
