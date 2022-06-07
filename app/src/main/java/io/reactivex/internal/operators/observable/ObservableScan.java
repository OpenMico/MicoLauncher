package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class ObservableScan<T> extends a<T, T> {
    final BiFunction<T, T, T> a;

    public ObservableScan(ObservableSource<T> observableSource, BiFunction<T, T, T> biFunction) {
        super(observableSource);
        this.a = biFunction;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final BiFunction<T, T, T> b;
        Disposable c;
        T d;
        boolean e;

        a(Observer<? super T> observer, BiFunction<T, T, T> biFunction) {
            this.a = observer;
            this.b = biFunction;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        /* JADX WARN: Type inference failed for: r4v3, types: [T, java.lang.Object] */
        /* JADX WARN: Unknown variable types count: 1 */
        @Override // io.reactivex.Observer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onNext(T r4) {
            /*
                r3 = this;
                boolean r0 = r3.e
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                io.reactivex.Observer<? super T> r0 = r3.a
                T r1 = r3.d
                if (r1 != 0) goto L_0x0011
                r3.d = r4
                r0.onNext(r4)
                goto L_0x0022
            L_0x0011:
                io.reactivex.functions.BiFunction<T, T, T> r2 = r3.b     // Catch: Throwable -> 0x0023
                java.lang.Object r4 = r2.apply(r1, r4)     // Catch: Throwable -> 0x0023
                java.lang.String r1 = "The value returned by the accumulator is null"
                java.lang.Object r4 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r4, r1)     // Catch: Throwable -> 0x0023
                r3.d = r4
                r0.onNext(r4)
            L_0x0022:
                return
            L_0x0023:
                r4 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                io.reactivex.disposables.Disposable r0 = r3.c
                r0.dispose()
                r3.onError(r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableScan.a.onNext(java.lang.Object):void");
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }
    }
}
