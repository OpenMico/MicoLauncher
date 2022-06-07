package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DefaultObserver;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public final class BlockingObservableMostRecent<T> implements Iterable<T> {
    final ObservableSource<T> a;
    final T b;

    public BlockingObservableMostRecent(ObservableSource<T> observableSource, T t) {
        this.a = observableSource;
        this.b = t;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        a aVar = new a(this.b);
        this.a.subscribe(aVar);
        return aVar.a();
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends DefaultObserver<T> {
        volatile Object a;

        a(T t) {
            this.a = NotificationLite.next(t);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a = NotificationLite.complete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a = NotificationLite.error(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a = NotificationLite.next(t);
        }

        public a<T>.C0253a a() {
            return new C0253a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.observable.BlockingObservableMostRecent$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public final class C0253a implements Iterator<T> {
            private Object b;

            C0253a() {
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                this.b = a.this.a;
                return !NotificationLite.isComplete(this.b);
            }

            @Override // java.util.Iterator
            public T next() {
                try {
                    if (this.b == null) {
                        this.b = a.this.a;
                    }
                    if (NotificationLite.isComplete(this.b)) {
                        throw new NoSuchElementException();
                    } else if (!NotificationLite.isError(this.b)) {
                        return (T) NotificationLite.getValue(this.b);
                    } else {
                        throw ExceptionHelper.wrapOrThrow(NotificationLite.getError(this.b));
                    }
                } finally {
                    this.b = null;
                }
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Read only iterator");
            }
        }
    }
}
