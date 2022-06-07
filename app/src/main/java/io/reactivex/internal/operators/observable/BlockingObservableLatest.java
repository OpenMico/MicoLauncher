package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class BlockingObservableLatest<T> implements Iterable<T> {
    final ObservableSource<T> a;

    public BlockingObservableLatest(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        a aVar = new a();
        Observable.wrap(this.a).materialize().subscribe(aVar);
        return aVar;
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends DisposableObserver<Notification<T>> implements Iterator<T> {
        Notification<T> a;
        final Semaphore b = new Semaphore(0);
        final AtomicReference<Notification<T>> c = new AtomicReference<>();

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        a() {
        }

        /* renamed from: a */
        public void onNext(Notification<T> notification) {
            if (this.c.getAndSet(notification) == null) {
                this.b.release();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Notification<T> notification = this.a;
            if (notification == null || !notification.isOnError()) {
                if (this.a == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.b.acquire();
                        Notification<T> andSet = this.c.getAndSet(null);
                        this.a = andSet;
                        if (andSet.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(andSet.getError());
                        }
                    } catch (InterruptedException e) {
                        dispose();
                        this.a = Notification.createOnError(e);
                        throw ExceptionHelper.wrapOrThrow(e);
                    }
                }
                return this.a.isOnNext();
            }
            throw ExceptionHelper.wrapOrThrow(this.a.getError());
        }

        @Override // java.util.Iterator
        public T next() {
            if (hasNext()) {
                T value = this.a.getValue();
                this.a = null;
                return value;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }
}
