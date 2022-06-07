package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes4.dex */
public final class ObservableWithLatestFromMany<T, R> extends a<T, R> {
    @Nullable
    final ObservableSource<?>[] a;
    @Nullable
    final Iterable<? extends ObservableSource<?>> b;
    @NonNull
    final Function<? super Object[], R> c;

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> observableSource, @NonNull ObservableSource<?>[] observableSourceArr, @NonNull Function<? super Object[], R> function) {
        super(observableSource);
        this.a = observableSourceArr;
        this.b = null;
        this.c = function;
    }

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> observableSource, @NonNull Iterable<? extends ObservableSource<?>> iterable, @NonNull Function<? super Object[], R> function) {
        super(observableSource);
        this.a = null;
        this.b = iterable;
        this.c = function;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        int i;
        ObservableSource<?>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            try {
                i = 0;
                for (ObservableSource<?> observableSource : this.b) {
                    if (i == observableSourceArr.length) {
                        observableSourceArr = (ObservableSource[]) Arrays.copyOf(observableSourceArr, (i >> 1) + i);
                    }
                    i++;
                    observableSourceArr[i] = observableSource;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
                return;
            }
        } else {
            i = observableSourceArr.length;
        }
        if (i == 0) {
            new ObservableMap(this.source, new a()).subscribeActual(observer);
            return;
        }
        b bVar = new b(observer, this.c, i);
        observer.onSubscribe(bVar);
        bVar.a(observableSourceArr, i);
        this.source.subscribe(bVar);
    }

    /* loaded from: classes4.dex */
    static final class b<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 1577321883966341961L;
        final Function<? super Object[], R> combiner;
        volatile boolean done;
        final Observer<? super R> downstream;
        final AtomicThrowable error;
        final c[] observers;
        final AtomicReference<Disposable> upstream;
        final AtomicReferenceArray<Object> values;

        b(Observer<? super R> observer, Function<? super Object[], R> function, int i) {
            this.downstream = observer;
            this.combiner = function;
            c[] cVarArr = new c[i];
            for (int i2 = 0; i2 < i; i2++) {
                cVarArr[i2] = new c(this, i2);
            }
            this.observers = cVarArr;
            this.values = new AtomicReferenceArray<>(i);
            this.upstream = new AtomicReference<>();
            this.error = new AtomicThrowable();
        }

        void a(ObservableSource<?>[] observableSourceArr, int i) {
            c[] cVarArr = this.observers;
            AtomicReference<Disposable> atomicReference = this.upstream;
            for (int i2 = 0; i2 < i && !DisposableHelper.isDisposed(atomicReference.get()) && !this.done; i2++) {
                observableSourceArr[i2].subscribe(cVarArr[i2]);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.done) {
                AtomicReferenceArray<Object> atomicReferenceArray = this.values;
                int length = atomicReferenceArray.length();
                Object[] objArr = new Object[length + 1];
                int i = 0;
                objArr[0] = t;
                while (i < length) {
                    Object obj = atomicReferenceArray.get(i);
                    if (obj != null) {
                        i++;
                        objArr[i] = obj;
                    } else {
                        return;
                    }
                }
                try {
                    HalfSerializer.onNext(this.downstream, ObjectHelper.requireNonNull(this.combiner.apply(objArr), "combiner returned a null value"), this, this.error);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            a(-1);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                a(-1);
                HalfSerializer.onComplete(this.downstream, this, this.error);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            for (c cVar : this.observers) {
                cVar.a();
            }
        }

        void a(int i, Object obj) {
            this.values.set(i, obj);
        }

        void a(int i, Throwable th) {
            this.done = true;
            DisposableHelper.dispose(this.upstream);
            a(i);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        void a(int i, boolean z) {
            if (!z) {
                this.done = true;
                a(i);
                HalfSerializer.onComplete(this.downstream, this, this.error);
            }
        }

        void a(int i) {
            c[] cVarArr = this.observers;
            for (int i2 = 0; i2 < cVarArr.length; i2++) {
                if (i2 != i) {
                    cVarArr[i2].a();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c extends AtomicReference<Disposable> implements Observer<Object> {
        private static final long serialVersionUID = 3256684027868224024L;
        boolean hasValue;
        final int index;
        final b<?, ?> parent;

        c(b<?, ?> bVar, int i) {
            this.parent = bVar;
            this.index = i;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
            if (!this.hasValue) {
                this.hasValue = true;
            }
            this.parent.a(this.index, obj);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.parent.a(this.index, th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.parent.a(this.index, this.hasValue);
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }

    /* loaded from: classes4.dex */
    final class a implements Function<T, R> {
        a() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[], java.lang.Object] */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            return (R) ObjectHelper.requireNonNull(ObservableWithLatestFromMany.this.c.apply(new Object[]{t}), "The combiner returned a null value");
        }
    }
}
