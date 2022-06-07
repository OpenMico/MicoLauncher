package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class SingleEquals<T> extends Single<Boolean> {
    final SingleSource<? extends T> a;
    final SingleSource<? extends T> b;

    public SingleEquals(SingleSource<? extends T> singleSource, SingleSource<? extends T> singleSource2) {
        this.a = singleSource;
        this.b = singleSource2;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        AtomicInteger atomicInteger = new AtomicInteger();
        Object[] objArr = {null, null};
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        singleObserver.onSubscribe(compositeDisposable);
        this.a.subscribe(new a(0, compositeDisposable, objArr, singleObserver, atomicInteger));
        this.b.subscribe(new a(1, compositeDisposable, objArr, singleObserver, atomicInteger));
    }

    /* loaded from: classes4.dex */
    static class a<T> implements SingleObserver<T> {
        final int a;
        final CompositeDisposable b;
        final Object[] c;
        final SingleObserver<? super Boolean> d;
        final AtomicInteger e;

        a(int i, CompositeDisposable compositeDisposable, Object[] objArr, SingleObserver<? super Boolean> singleObserver, AtomicInteger atomicInteger) {
            this.a = i;
            this.b = compositeDisposable;
            this.c = objArr;
            this.d = singleObserver;
            this.e = atomicInteger;
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.b.add(disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.c[this.a] = t;
            if (this.e.incrementAndGet() == 2) {
                SingleObserver<? super Boolean> singleObserver = this.d;
                Object[] objArr = this.c;
                singleObserver.onSuccess(Boolean.valueOf(ObjectHelper.equals(objArr[0], objArr[1])));
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            int i;
            do {
                i = this.e.get();
                if (i >= 2) {
                    RxJavaPlugins.onError(th);
                    return;
                }
            } while (!this.e.compareAndSet(i, 2));
            this.b.dispose();
            this.d.onError(th);
        }
    }
}
