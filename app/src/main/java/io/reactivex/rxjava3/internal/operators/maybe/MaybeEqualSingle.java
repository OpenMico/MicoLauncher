package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MaybeEqualSingle<T> extends Single<Boolean> {
    final MaybeSource<? extends T> a;
    final MaybeSource<? extends T> b;
    final BiPredicate<? super T, ? super T> c;

    public MaybeEqualSingle(MaybeSource<? extends T> maybeSource, MaybeSource<? extends T> maybeSource2, BiPredicate<? super T, ? super T> biPredicate) {
        this.a = maybeSource;
        this.b = maybeSource2;
        this.c = biPredicate;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        a aVar = new a(singleObserver, this.c);
        singleObserver.onSubscribe(aVar);
        aVar.a(this.a, this.b);
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements Disposable {
        final SingleObserver<? super Boolean> downstream;
        final BiPredicate<? super T, ? super T> isEqual;
        final b<T> observer1 = new b<>(this);
        final b<T> observer2 = new b<>(this);

        a(SingleObserver<? super Boolean> singleObserver, BiPredicate<? super T, ? super T> biPredicate) {
            super(2);
            this.downstream = singleObserver;
            this.isEqual = biPredicate;
        }

        void a(MaybeSource<? extends T> maybeSource, MaybeSource<? extends T> maybeSource2) {
            maybeSource.subscribe(this.observer1);
            maybeSource2.subscribe(this.observer2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.observer1.a();
            this.observer2.a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.observer1.get());
        }

        void a() {
            if (decrementAndGet() == 0) {
                Object obj = this.observer1.value;
                Object obj2 = this.observer2.value;
                if (obj == null || obj2 == null) {
                    this.downstream.onSuccess(Boolean.valueOf(obj == null && obj2 == null));
                    return;
                }
                try {
                    this.downstream.onSuccess(Boolean.valueOf(this.isEqual.test(obj, obj2)));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }
        }

        void a(b<T> bVar, Throwable th) {
            if (getAndSet(0) > 0) {
                b<T> bVar2 = this.observer1;
                if (bVar == bVar2) {
                    this.observer2.a();
                } else {
                    bVar2.a();
                }
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = -3031974433025990931L;
        final a<T> parent;
        Object value;

        b(a<T> aVar) {
            this.parent = aVar;
        }

        public void a() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            this.value = t;
            this.parent.a();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            this.parent.a(this, th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.parent.a();
        }
    }
}
