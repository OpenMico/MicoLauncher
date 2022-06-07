package io.reactivex.rxjava3.subjects;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class AsyncSubject<T> extends Subject<T> {
    static final a[] a = new a[0];
    static final a[] b = new a[0];
    final AtomicReference<a<T>[]> c = new AtomicReference<>(a);
    Throwable d;
    T e;

    @CheckReturnValue
    @NonNull
    public static <T> AsyncSubject<T> create() {
        return new AsyncSubject<>();
    }

    AsyncSubject() {
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.c.get() == b) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        ExceptionHelper.nullCheck(t, "onNext called with a null value.");
        if (this.c.get() != b) {
            this.e = t;
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        a<T>[] aVarArr = this.c.get();
        a<T>[] aVarArr2 = b;
        if (aVarArr == aVarArr2) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.e = null;
        this.d = th;
        for (a<T> aVar : this.c.getAndSet(aVarArr2)) {
            aVar.a(th);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        a<T>[] aVarArr = this.c.get();
        a<T>[] aVarArr2 = b;
        if (aVarArr != aVarArr2) {
            T t = this.e;
            a<T>[] andSet = this.c.getAndSet(aVarArr2);
            int i = 0;
            if (t == null) {
                int length = andSet.length;
                while (i < length) {
                    andSet[i].a();
                    i++;
                }
                return;
            }
            int length2 = andSet.length;
            while (i < length2) {
                andSet[i].complete(t);
                i++;
            }
        }
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasObservers() {
        return this.c.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.c.get() == b && this.d != null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasComplete() {
        return this.c.get() == b && this.d == null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public Throwable getThrowable() {
        if (this.c.get() == b) {
            return this.d;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        a<T> aVar = new a<>(observer, this);
        observer.onSubscribe(aVar);
        if (!a(aVar)) {
            Throwable th = this.d;
            if (th != null) {
                observer.onError(th);
                return;
            }
            T t = this.e;
            if (t != null) {
                aVar.complete(t);
            } else {
                aVar.a();
            }
        } else if (aVar.isDisposed()) {
            b(aVar);
        }
    }

    boolean a(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.c.get();
            if (aVarArr == b) {
                return false;
            }
            int length = aVarArr.length;
            aVarArr2 = new a[length + 1];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
            aVarArr2[length] = aVar;
        } while (!this.c.compareAndSet(aVarArr, aVarArr2));
        return true;
    }

    void b(a<T> aVar) {
        a<T>[] aVarArr;
        a<T>[] aVarArr2;
        do {
            aVarArr = this.c.get();
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
                        aVarArr2 = a;
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
        } while (!this.c.compareAndSet(aVarArr, aVarArr2));
    }

    @CheckReturnValue
    public boolean hasValue() {
        return this.c.get() == b && this.e != null;
    }

    @CheckReturnValue
    @Nullable
    public T getValue() {
        if (this.c.get() == b) {
            return this.e;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends DeferredScalarDisposable<T> {
        private static final long serialVersionUID = 5629876084736248016L;
        final AsyncSubject<T> parent;

        a(Observer<? super T> observer, AsyncSubject<T> asyncSubject) {
            super(observer);
            this.parent = asyncSubject;
        }

        @Override // io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable, io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (super.tryDispose()) {
                this.parent.b(this);
            }
        }

        void a() {
            if (!isDisposed()) {
                this.downstream.onComplete();
            }
        }

        void a(Throwable th) {
            if (isDisposed()) {
                RxJavaPlugins.onError(th);
            } else {
                this.downstream.onError(th);
            }
        }
    }
}
