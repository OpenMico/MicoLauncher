package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes5.dex */
public final class ObservableBuffer<T, U extends Collection<? super T>> extends a<T, U> {
    final int a;
    final int b;
    final Supplier<U> c;

    public ObservableBuffer(ObservableSource<T> observableSource, int i, int i2, Supplier<U> supplier) {
        super(observableSource);
        this.a = i;
        this.b = i2;
        this.c = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
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

    /* loaded from: classes5.dex */
    static final class a<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        final Observer<? super U> a;
        final int b;
        final Supplier<U> c;
        U d;
        int e;
        Disposable f;

        a(Observer<? super U> observer, int i, Supplier<U> supplier) {
            this.a = observer;
            this.b = i;
            this.c = supplier;
        }

        boolean a() {
            try {
                this.d = (U) ((Collection) Objects.requireNonNull(this.c.get(), "Empty buffer supplied"));
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.f.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.d = null;
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
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

    /* loaded from: classes5.dex */
    static final class b<T, U extends Collection<? super T>> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8223395059921494546L;
        final Supplier<U> bufferSupplier;
        final ArrayDeque<U> buffers = new ArrayDeque<>();
        final int count;
        final Observer<? super U> downstream;
        long index;
        final int skip;
        Disposable upstream;

        b(Observer<? super U> observer, int i, int i2, Supplier<U> supplier) {
            this.downstream = observer;
            this.count = i;
            this.skip = i2;
            this.bufferSupplier = supplier;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            long j = this.index;
            this.index = 1 + j;
            if (j % this.skip == 0) {
                try {
                    this.buffers.offer((Collection) ExceptionHelper.nullCheck(this.bufferSupplier.get(), "The bufferSupplier returned a null Collection."));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.buffers.clear();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            while (!this.buffers.isEmpty()) {
                this.downstream.onNext(this.buffers.poll());
            }
            this.downstream.onComplete();
        }
    }
}
