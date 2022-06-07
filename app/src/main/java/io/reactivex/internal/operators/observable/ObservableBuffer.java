package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class ObservableBuffer<T, U extends Collection<? super T>> extends a<T, U> {
    final int a;
    final int b;
    final Callable<U> c;

    public ObservableBuffer(ObservableSource<T> observableSource, int i, int i2, Callable<U> callable) {
        super(observableSource);
        this.a = i;
        this.b = i2;
        this.c = callable;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super U> observer) {
        int i = this.b;
        int i2 = this.a;
        if (i == i2) {
            a aVar = new a(observer, i2, this.c);
            if (aVar.a()) {
                this.source.subscribe(aVar);
                return;
            }
            return;
        }
        this.source.subscribe(new b(observer, this.a, this.b, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        final Observer<? super U> a;
        final int b;
        final Callable<U> c;
        U d;
        int e;
        Disposable f;

        a(Observer<? super U> observer, int i, Callable<U> callable) {
            this.a = observer;
            this.b = i;
            this.c = callable;
        }

        boolean a() {
            try {
                this.d = (U) ((Collection) ObjectHelper.requireNonNull(this.c.call(), "Empty buffer supplied"));
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.d = null;
                Disposable disposable = this.f;
                if (disposable == null) {
                    EmptyDisposable.error(th, this.a);
                    return false;
                }
                disposable.dispose();
                this.a.onError(th);
                return false;
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            U u = this.d;
            if (u != null) {
                u.add(t);
                int i = this.e + 1;
                this.e = i;
                if (i >= this.b) {
                    this.a.onNext(u);
                    this.e = 0;
                    a();
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.d = null;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            U u = this.d;
            if (u != null) {
                this.d = null;
                if (!u.isEmpty()) {
                    this.a.onNext(u);
                }
                this.a.onComplete();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, U extends Collection<? super T>> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8223395059921494546L;
        final Callable<U> bufferSupplier;
        final ArrayDeque<U> buffers = new ArrayDeque<>();
        final int count;
        final Observer<? super U> downstream;
        long index;
        final int skip;
        Disposable upstream;

        b(Observer<? super U> observer, int i, int i2, Callable<U> callable) {
            this.downstream = observer;
            this.count = i;
            this.skip = i2;
            this.bufferSupplier = callable;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onNext(T t) {
            long j = this.index;
            this.index = 1 + j;
            if (j % this.skip == 0) {
                try {
                    this.buffers.offer((Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."));
                } catch (Throwable th) {
                    this.buffers.clear();
                    this.upstream.dispose();
                    this.downstream.onError(th);
                    return;
                }
            }
            Iterator<U> it = this.buffers.iterator();
            while (it.hasNext()) {
                U next = it.next();
                next.add(t);
                if (this.count <= next.size()) {
                    it.remove();
                    this.downstream.onNext(next);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.buffers.clear();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            while (!this.buffers.isEmpty()) {
                this.downstream.onNext(this.buffers.poll());
            }
            this.downstream.onComplete();
        }
    }
}
