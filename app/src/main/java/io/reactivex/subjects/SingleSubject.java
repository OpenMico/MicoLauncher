package io.reactivex.subjects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleSubject<T> extends Single<T> implements SingleObserver<T> {
    static final a[] b = new a[0];
    static final a[] c = new a[0];
    T e;
    Throwable f;
    final AtomicBoolean d = new AtomicBoolean();
    final AtomicReference<a<T>[]> a = new AtomicReference<>(b);

    @CheckReturnValue
    @NonNull
    public static <T> SingleSubject<T> create() {
        return new SingleSubject<>();
    }

    SingleSubject() {
    }

    @Override // io.reactivex.SingleObserver
    public void onSubscribe(@NonNull Disposable disposable) {
        if (this.a.get() == c) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.SingleObserver
    public void onSuccess(@NonNull T t) {
        ObjectHelper.requireNonNull(t, "onSuccess called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.d.compareAndSet(false, true)) {
            this.e = t;
            for (a<T> aVar : this.a.getAndSet(c)) {
                aVar.downstream.onSuccess(t);
            }
        }
    }

    @Override // io.reactivex.SingleObserver
    public void onError(@NonNull Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.d.compareAndSet(false, true)) {
            this.f = th;
            for (a<T> aVar : this.a.getAndSet(c)) {
                aVar.downstream.onError(th);
            }
            return;
        }
        RxJavaPlugins.onError(th);
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(@NonNull SingleObserver<? super T> singleObserver) {
        a<T> aVar = new a<>(singleObserver, this);
        singleObserver.onSubscribe(aVar);
        if (!a(aVar)) {
            Throwable th = this.f;
            if (th != null) {
                singleObserver.onError(th);
            } else {
                singleObserver.onSuccess((T) this.e);
            }
        } else if (aVar.isDisposed()) {
            b(aVar);
        }
    }

    boolean a(@NonNull a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.a.get();
            if (aVarArr == c) {
                return false;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.a.compareAndSet(aVarArr, aVarArr2));
        return true;
    }

    void b(@NonNull a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.a.get();
            int length = aVarArr.length;
            if (length != 0) {
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (aVarArr[i2] == aVar) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        aVarArr2 = b;
                    } else {
                        a<T>[] aVarArr3 = new a[length - 1];
                        System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                        System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                        aVarArr2 = aVarArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.a.compareAndSet(aVarArr, aVarArr2));
    }

    @Nullable
    public T getValue() {
        if (this.a.get() == c) {
            return this.e;
        }
        return null;
    }

    public boolean hasValue() {
        return this.a.get() == c && this.e != null;
    }

    @Nullable
    public Throwable getThrowable() {
        if (this.a.get() == c) {
            return this.f;
        }
        return null;
    }

    public boolean hasThrowable() {
        return this.a.get() == c && this.f != null;
    }

    public boolean hasObservers() {
        return this.a.get().length != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicReference<SingleSubject<T>> implements Disposable {
        private static final long serialVersionUID = -7650903191002190468L;
        final SingleObserver<? super T> downstream;

        a(SingleObserver<? super T> singleObserver, SingleSubject<T> singleSubject) {
            this.downstream = singleObserver;
            lazySet(singleSubject);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SingleSubject<T> andSet = getAndSet(null);
            if (andSet != null) {
                andSet.b(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }
    }
}
