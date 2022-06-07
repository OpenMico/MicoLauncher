package io.reactivex.rxjava3.internal.queue;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
    private final AtomicReference<a<T>> a = new AtomicReference<>();
    private final AtomicReference<a<T>> b = new AtomicReference<>();

    public MpscLinkedQueue() {
        a<T> aVar = new a<>();
        b(aVar);
        a(aVar);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(T t) {
        if (t != null) {
            a<T> aVar = new a<>(t);
            a(aVar).a(aVar);
            return true;
        }
        throw new NullPointerException("Null is not a valid element");
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    @Nullable
    public T poll() {
        a<T> c;
        a<T> c2 = c();
        a<T> c3 = c2.c();
        if (c3 != null) {
            T a2 = c3.a();
            b(c3);
            return a2;
        } else if (c2 == a()) {
            return null;
        } else {
            do {
                c = c2.c();
            } while (c == null);
            T a3 = c.a();
            b(c);
            return a3;
        }
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(T t, T t2) {
        offer(t);
        offer(t2);
        return true;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    a<T> a() {
        return this.a.get();
    }

    a<T> a(a<T> aVar) {
        return this.a.getAndSet(aVar);
    }

    a<T> b() {
        return this.b.get();
    }

    a<T> c() {
        return this.b.get();
    }

    void b(a<T> aVar) {
        this.b.lazySet(aVar);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return b() == a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<E> extends AtomicReference<a<E>> {
        private static final long serialVersionUID = 2404266111789071508L;
        private E value;

        a() {
        }

        a(E e) {
            a((a<E>) e);
        }

        public E a() {
            E b = b();
            a((a<E>) null);
            return b;
        }

        public E b() {
            return this.value;
        }

        public void a(E e) {
            this.value = e;
        }

        public void a(a<E> aVar) {
            lazySet(aVar);
        }

        public a<E> c() {
            return get();
        }
    }
}
