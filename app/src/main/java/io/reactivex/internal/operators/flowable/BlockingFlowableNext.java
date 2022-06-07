package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

/* loaded from: classes4.dex */
public final class BlockingFlowableNext<T> implements Iterable<T> {
    final Publisher<? extends T> a;

    public BlockingFlowableNext(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new a(this.a, new b());
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Iterator<T> {
        private final b<T> a;
        private final Publisher<? extends T> b;
        private T c;
        private boolean d = true;
        private boolean e = true;
        private Throwable f;
        private boolean g;

        a(Publisher<? extends T> publisher, b<T> bVar) {
            this.b = publisher;
            this.a = bVar;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Throwable th = this.f;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            } else if (!this.d) {
                return false;
            } else {
                return !this.e || a();
            }
        }

        private boolean a() {
            try {
                if (!this.g) {
                    this.g = true;
                    this.a.b();
                    Flowable.fromPublisher(this.b).materialize().subscribe((FlowableSubscriber<? super Notification<T>>) this.a);
                }
                Notification<T> a = this.a.a();
                if (a.isOnNext()) {
                    this.e = false;
                    this.c = a.getValue();
                    return true;
                }
                this.d = false;
                if (a.isOnComplete()) {
                    return false;
                }
                if (a.isOnError()) {
                    this.f = a.getError();
                    throw ExceptionHelper.wrapOrThrow(this.f);
                }
                throw new IllegalStateException("Should not reach here");
            } catch (InterruptedException e) {
                this.a.dispose();
                this.f = e;
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }

        @Override // java.util.Iterator
        public T next() {
            Throwable th = this.f;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            } else if (hasNext()) {
                this.e = true;
                return this.c;
            } else {
                throw new NoSuchElementException("No more elements");
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read only iterator");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends DisposableSubscriber<Notification<T>> {
        private final BlockingQueue<Notification<T>> b = new ArrayBlockingQueue(1);
        final AtomicInteger a = new AtomicInteger();

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        b() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        /* renamed from: a */
        public void onNext(Notification<T> notification) {
            if (this.a.getAndSet(0) == 1 || !notification.isOnNext()) {
                while (!this.b.offer(notification)) {
                    Notification<T> poll = this.b.poll();
                    if (poll != null && !poll.isOnNext()) {
                        notification = poll;
                    }
                }
            }
        }

        public Notification<T> a() throws InterruptedException {
            b();
            BlockingHelper.verifyNonBlocking();
            return this.b.take();
        }

        void b() {
            this.a.set(1);
        }
    }
}
