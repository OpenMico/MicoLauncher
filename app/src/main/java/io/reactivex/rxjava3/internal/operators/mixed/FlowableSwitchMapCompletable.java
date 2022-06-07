package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableSwitchMapCompletable<T> extends Completable {
    final Flowable<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final boolean c;

    public FlowableSwitchMapCompletable(Flowable<T> flowable, Function<? super T, ? extends CompletableSource> function, boolean z) {
        this.a = flowable;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe((FlowableSubscriber) new a(completableObserver, this.b, this.c));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> implements FlowableSubscriber<T>, Disposable {
        static final C0309a f = new C0309a(null);
        final CompletableObserver a;
        final Function<? super T, ? extends CompletableSource> b;
        final boolean c;
        final AtomicThrowable d = new AtomicThrowable();
        final AtomicReference<C0309a> e = new AtomicReference<>();
        volatile boolean g;
        Subscription h;

        public a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.a = completableObserver;
            this.b = function;
            this.c = z;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            C0309a aVar;
            try {
                CompletableSource completableSource = (CompletableSource) Objects.requireNonNull(this.b.apply(t), "The mapper returned a null CompletableSource");
                C0309a aVar2 = new C0309a(this);
                do {
                    aVar = this.e.get();
                    if (aVar == f) {
                        return;
                    }
                } while (!this.e.compareAndSet(aVar, aVar2));
                if (aVar != null) {
                    aVar.a();
                }
                completableSource.subscribe(aVar2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.h.cancel();
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.d.tryAddThrowableOrReport(th)) {
                return;
            }
            if (this.c) {
                onComplete();
                return;
            }
            a();
            this.d.tryTerminateConsumer(this.a);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.g = true;
            if (this.e.get() == null) {
                this.d.tryTerminateConsumer(this.a);
            }
        }

        void a() {
            C0309a andSet = this.e.getAndSet(f);
            if (andSet != null && andSet != f) {
                andSet.a();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.h.cancel();
            a();
            this.d.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.e.get() == f;
        }

        void a(C0309a aVar, Throwable th) {
            if (!this.e.compareAndSet(aVar, null)) {
                RxJavaPlugins.onError(th);
            } else if (!this.d.tryAddThrowableOrReport(th)) {
            } else {
                if (!this.c) {
                    this.h.cancel();
                    a();
                    this.d.tryTerminateConsumer(this.a);
                } else if (this.g) {
                    this.d.tryTerminateConsumer(this.a);
                }
            }
        }

        void a(C0309a aVar) {
            if (this.e.compareAndSet(aVar, null) && this.g) {
                this.d.tryTerminateConsumer(this.a);
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapCompletable$a$a */
        /* loaded from: classes5.dex */
        public static final class C0309a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -8003404460084760287L;
            final a<?> parent;

            C0309a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                this.parent.a(this, th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.a(this);
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
