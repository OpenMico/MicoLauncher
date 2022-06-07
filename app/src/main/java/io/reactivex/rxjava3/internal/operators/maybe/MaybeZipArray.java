package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMap;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MaybeZipArray<T, R> extends Maybe<R> {
    final MaybeSource<? extends T>[] a;
    final Function<? super Object[], ? extends R> b;

    public MaybeZipArray(MaybeSource<? extends T>[] maybeSourceArr, Function<? super Object[], ? extends R> function) {
        this.a = maybeSourceArr;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        MaybeSource<? extends T>[] maybeSourceArr = this.a;
        int length = maybeSourceArr.length;
        if (length == 1) {
            maybeSourceArr[0].subscribe(new MaybeMap.a(maybeObserver, new a()));
            return;
        }
        b bVar = new b(maybeObserver, length, this.b);
        maybeObserver.onSubscribe(bVar);
        for (int i = 0; i < length && !bVar.isDisposed(); i++) {
            MaybeSource<? extends T> maybeSource = maybeSourceArr[i];
            if (maybeSource == null) {
                bVar.a((Throwable) new NullPointerException("One of the sources is null"), i);
                return;
            } else {
                maybeSource.subscribe(bVar.observers[i]);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -5556924161382950569L;
        final MaybeObserver<? super R> downstream;
        final c<T>[] observers;
        final Object[] values;
        final Function<? super Object[], ? extends R> zipper;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(MaybeObserver<? super R> maybeObserver, int i, Function<? super Object[], ? extends R> function) {
            super(i);
            this.downstream = maybeObserver;
            this.zipper = function;
            c<T>[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c<>(this, i2);
            }
            this.observers = cVarArr;
            this.values = new Object[i];
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() <= 0;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(0) > 0) {
                for (c<T> cVar : this.observers) {
                    cVar.a();
                }
            }
        }

        void a(T t, int i) {
            this.values[i] = t;
            if (decrementAndGet() == 0) {
                try {
                    this.downstream.onSuccess(Objects.requireNonNull(this.zipper.apply(this.values), "The zipper returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }
        }

        void a(int i) {
            c<T>[] cVarArr = this.observers;
            int length = cVarArr.length;
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2].a();
            }
            while (true) {
                i++;
                if (i < length) {
                    cVarArr[i].a();
                } else {
                    return;
                }
            }
        }

        void a(Throwable th, int i) {
            if (getAndSet(0) > 0) {
                a(i);
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        void b(int i) {
            if (getAndSet(0) > 0) {
                a(i);
                this.downstream.onComplete();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 3323743579927613702L;
        final int index;
        final b<T, ?> parent;

        c(b<T, ?> bVar, int i) {
            this.parent = bVar;
            this.index = i;
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
            this.parent.a((b<T, ?>) t, this.index);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            this.parent.a(th, this.index);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.parent.b(this.index);
        }
    }

    /* loaded from: classes5.dex */
    final class a implements Function<T, R> {
        a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[], java.lang.Object] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t) throws Throwable {
            return (R) Objects.requireNonNull(MaybeZipArray.this.b.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }
}
