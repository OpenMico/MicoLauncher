package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class BlockingObservableNext<T> implements Iterable<T> {
    final ObservableSource<T> a;

    public BlockingObservableNext(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new a(this.a, new b());
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements Iterator<T> {
        private final b<T> a;
        private final ObservableSource<T> b;
        private T c;
        private boolean d = true;
        private boolean e = true;
        private Throwable f;
        private boolean g;

        a(ObservableSource<T> observableSource, b<T> bVar) {
            this.b = observableSource;
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
            if (!this.g) {
                this.g = true;
                this.a.b();
                new ObservableMaterialize(this.b).subscribe(this.a);
            }
            try {
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
                this.f = a.getError();
                throw ExceptionHelper.wrapOrThrow(this.f);
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
    /* loaded from: classes5.dex */
    public static final class b<T> extends DisposableObserver<Notification<T>> {
        private final BlockingQueue<Notification<T>> b = new ArrayBlockingQueue(1);
        final AtomicInteger a = new AtomicInteger();

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
        }

        b() {
        }

        @Override // io.reactivex.rxjava3.core.Observer
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
