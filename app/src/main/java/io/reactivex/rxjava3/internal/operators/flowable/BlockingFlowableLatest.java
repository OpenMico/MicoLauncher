package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;

/* loaded from: classes5.dex */
public final class BlockingFlowableLatest<T> implements Iterable<T> {
    final Publisher<? extends T> a;

    public BlockingFlowableLatest(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        a aVar = new a();
        Flowable.fromPublisher(this.a).materialize().subscribe((FlowableSubscriber<? super Notification<T>>) aVar);
        return aVar;
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends DisposableSubscriber<Notification<T>> implements Iterator<T> {
        final Semaphore a = new Semaphore(0);
        final AtomicReference<Notification<T>> b = new AtomicReference<>();
        Notification<T> c;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        a() {
        }

        /* renamed from: a */
        public void onNext(Notification<T> notification) {
            if (this.b.getAndSet(notification) == null) {
                this.a.release();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Notification<T> notification = this.c;
            if (notification == null || !notification.isOnError()) {
                Notification<T> notification2 = this.c;
                if ((notification2 == null || notification2.isOnNext()) && this.c == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.a.acquire();
                        Notification<T> andSet = this.b.getAndSet(null);
                        this.c = andSet;
                        if (andSet.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(andSet.getError());
                        }
                    } catch (InterruptedException e) {
                        dispose();
                        this.c = Notification.createOnError(e);
                        throw ExceptionHelper.wrapOrThrow(e);
                    }
                }
                return this.c.isOnNext();
            }
            throw ExceptionHelper.wrapOrThrow(this.c.getError());
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext() || !this.c.isOnNext()) {
                throw new NoSuchElementException();
            }
            T value = this.c.getValue();
            this.c = null;
            return value;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }
}
