package io.reactivex.subjects;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MaybeSubject<T> extends Maybe<T> implements MaybeObserver<T> {
    static final a[] b = new a[0];
    static final a[] c = new a[0];
    T e;
    Throwable f;
    final AtomicBoolean d = new AtomicBoolean();
    final AtomicReference<a<T>[]> a = new AtomicReference<>(b);

    @CheckReturnValue
    @NonNull
    public static <T> MaybeSubject<T> create() {
        return new MaybeSubject<>();
    }

    MaybeSubject() {
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public void onSubscribe(Disposable disposable) {
        if (this.a.get() == c) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public void onSuccess(T t) {
        ObjectHelper.requireNonNull(t, "onSuccess called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.d.compareAndSet(false, true)) {
            this.e = t;
            for (a<T> aVar : this.a.getAndSet(c)) {
                aVar.downstream.onSuccess(t);
            }
        }
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public void onError(Throwable th) {
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

    @Override // io.reactivex.MaybeObserver
    public void onComplete() {
        if (this.d.compareAndSet(false, true)) {
            for (a<T> aVar : this.a.getAndSet(c)) {
                aVar.downstream.onComplete();
            }
        }
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        a<T> aVar = new a<>(maybeObserver, this);
        maybeObserver.onSubscribe(aVar);
        if (!a(aVar)) {
            Throwable th = this.f;
            if (th != null) {
                maybeObserver.onError(th);
                return;
            }
            Object obj = (T) this.e;
            if (obj == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(obj);
            }
        } else if (aVar.isDisposed()) {
            b(aVar);
        }
    }

    boolean a(a<T> aVar) {
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

    void b(a<T> aVar) {
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

    public boolean hasComplete() {
        return this.a.get() == c && this.e == null && this.f == null;
    }

    public boolean hasObservers() {
        return this.a.get().length != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicReference<MaybeSubject<T>> implements Disposable {
        private static final long serialVersionUID = -7650903191002190468L;
        final MaybeObserver<? super T> downstream;

        a(MaybeObserver<? super T> maybeObserver, MaybeSubject<T> maybeSubject) {
            this.downstream = maybeObserver;
            lazySet(maybeSubject);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            MaybeSubject<T> andSet = getAndSet(null);
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
