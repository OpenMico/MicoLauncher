package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

/* loaded from: classes4.dex */
public final class ObservableFromArray<T> extends Observable<T> {
    final T[] a;

    public ObservableFromArray(T[] tArr) {
        this.a = tArr;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer, this.a);
        observer.onSubscribe(aVar);
        if (!aVar.d) {
            aVar.a();
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends BasicQueueDisposable<T> {
        final Observer<? super T> a;
        final T[] b;
        int c;
        boolean d;
        volatile boolean e;

        a(Observer<? super T> observer, T[] tArr) {
            this.a = observer;
            this.b = tArr;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.d = true;
            return 1;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            int i = this.c;
            T[] tArr = this.b;
            if (i == tArr.length) {
                return null;
            }
            this.c = i + 1;
            return (T) ObjectHelper.requireNonNull(tArr[i], "The array element is null");
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.c == this.b.length;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.c = this.b.length;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.e = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        void a() {
            T[] tArr = this.b;
            int length = tArr.length;
            for (int i = 0; i < length && !isDisposed(); i++) {
                T t = tArr[i];
                if (t == null) {
                    Observer<? super T> observer = this.a;
                    observer.onError(new NullPointerException("The element at index " + i + " is null"));
                    return;
                }
                this.a.onNext(t);
            }
            if (!isDisposed()) {
                this.a.onComplete();
            }
        }
    }
}
