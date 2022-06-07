package io.reactivex.internal.operators.mixed;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableSwitchMapCompletable<T> extends Completable {
    final Flowable<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final boolean c;

    public FlowableSwitchMapCompletable(Flowable<T> flowable, Function<? super T, ? extends CompletableSource> function, boolean z) {
        this.a = flowable;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe((FlowableSubscriber) new a(completableObserver, this.b, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Disposable {
        static final C0244a f = new C0244a(null);
        final CompletableObserver a;
        final Function<? super T, ? extends CompletableSource> b;
        final boolean c;
        final AtomicThrowable d = new AtomicThrowable();
        final AtomicReference<C0244a> e = new AtomicReference<>();
        volatile boolean g;
        Subscription h;

        a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.a = completableObserver;
            this.b = function;
            this.c = z;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            C0244a aVar;
            try {
                CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null CompletableSource");
                C0244a aVar2 = new C0244a(this);
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
            if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (this.c) {
                onComplete();
            } else {
                a();
                Throwable terminate = this.d.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.a.onError(terminate);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.g = true;
            if (this.e.get() == null) {
                Throwable terminate = this.d.terminate();
                if (terminate == null) {
                    this.a.onComplete();
                } else {
                    this.a.onError(terminate);
                }
            }
        }

        void a() {
            C0244a andSet = this.e.getAndSet(f);
            if (andSet != null && andSet != f) {
                andSet.a();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.h.cancel();
            a();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e.get() == f;
        }

        void a(C0244a aVar, Throwable th) {
            if (!this.e.compareAndSet(aVar, null) || !this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (!this.c) {
                dispose();
                Throwable terminate = this.d.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.a.onError(terminate);
                }
            } else if (this.g) {
                this.a.onError(this.d.terminate());
            }
        }

        void a(C0244a aVar) {
            if (this.e.compareAndSet(aVar, null) && this.g) {
                Throwable terminate = this.d.terminate();
                if (terminate == null) {
                    this.a.onComplete();
                } else {
                    this.a.onError(terminate);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.mixed.FlowableSwitchMapCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class C0244a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -8003404460084760287L;
            final a<?> parent;

            C0244a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(this, th);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                this.parent.a(this);
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
