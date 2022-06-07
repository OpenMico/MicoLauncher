package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.subscribers.DefaultSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public final class BlockingFlowableMostRecent<T> implements Iterable<T> {
    final Flowable<T> a;
    final T b;

    public BlockingFlowableMostRecent(Flowable<T> flowable, T t) {
        this.a = flowable;
        this.b = t;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        a aVar = new a(this.b);
        this.a.subscribe((FlowableSubscriber) aVar);
        return aVar.a();
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends DefaultSubscriber<T> {
        volatile Object a;

        a(T t) {
            this.a = NotificationLite.next(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a = NotificationLite.complete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a = NotificationLite.error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a = NotificationLite.next(t);
        }

        public a<T>.C0216a a() {
            return new C0216a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableMostRecent$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public final class C0216a implements Iterator<T> {
            private Object b;

            C0216a() {
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
