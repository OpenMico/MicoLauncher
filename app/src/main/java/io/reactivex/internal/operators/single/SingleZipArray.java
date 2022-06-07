package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.single.SingleMap;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class SingleZipArray<T, R> extends Single<R> {
    final SingleSource<? extends T>[] a;
    final Function<? super Object[], ? extends R> b;

    public SingleZipArray(SingleSource<? extends T>[] singleSourceArr, Function<? super Object[], ? extends R> function) {
        this.a = singleSourceArr;
        this.b = function;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        SingleSource<? extends T>[] singleSourceArr = this.a;
        int length = singleSourceArr.length;
        if (length == 1) {
            singleSourceArr[0].subscribe(new SingleMap.a(singleObserver, new a()));
            return;
        }
        b bVar = new b(singleObserver, length, this.b);
        singleObserver.onSubscribe(bVar);
        for (int i = 0; i < length && !bVar.isDisposed(); i++) {
            SingleSource<? extends T> singleSource = singleSourceArr[i];
            if (singleSource == null) {
                bVar.a((Throwable) new NullPointerException("One of the sources is null"), i);
                return;
            } else {
                singleSource.subscribe(bVar.observers[i]);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -5556924161382950569L;
        final SingleObserver<? super R> downstream;
        final c<T>[] observers;
        final Object[] values;
        final Function<? super Object[], ? extends R> zipper;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(SingleObserver<? super R> singleObserver, int i, Function<? super Object[], ? extends R> function) {
            super(i);
            this.downstream = singleObserver;
            this.zipper = function;
            c<T>[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c<>(this, i2);
            }
            this.observers = cVarArr;
            this.values = new Object[i];
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() <= 0;
        }

        @Override // io.reactivex.disposables.Disposable
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
                    this.downstream.onSuccess(ObjectHelper.requireNonNull(this.zipper.apply(this.values), "The zipper returned a null value"));
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
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
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

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.parent.a((b<T, ?>) t, this.index);
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.parent.a(th, this.index);
        }
    }

    /* loaded from: classes4.dex */
    final class a implements Function<T, R> {
        a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[], java.lang.Object] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return (R) ObjectHelper.requireNonNull(SingleZipArray.this.b.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }
}
