package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;

/* loaded from: classes5.dex */
public final class SingleInternalHelper {
    private SingleInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public enum a implements Supplier<NoSuchElementException> {
        INSTANCE;

        /* renamed from: a */
        public NoSuchElementException get() {
            return new NoSuchElementException();
        }
    }

    public static Supplier<NoSuchElementException> emptyThrower() {
        return a.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public enum b implements Function<SingleSource, Publisher> {
        INSTANCE;

        /* renamed from: a */
        public Publisher apply(SingleSource singleSource) {
            return new SingleToFlowable(singleSource);
        }
    }

    public static <T> Function<SingleSource<? extends T>, Publisher<? extends T>> toFlowable() {
        return b.INSTANCE;
    }

    /* loaded from: classes5.dex */
    static final class d<T> implements Iterator<Flowable<T>> {
        private final Iterator<? extends SingleSource<? extends T>> a;

        d(Iterator<? extends SingleSource<? extends T>> it) {
            this.a = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.a.hasNext();
        }

        /* renamed from: a */
        public Flowable<T> next() {
            return new SingleToFlowable((SingleSource) this.a.next());
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes5.dex */
    static final class c<T> implements Iterable<Flowable<T>> {
        private final Iterable<? extends SingleSource<? extends T>> a;

        c(Iterable<? extends SingleSource<? extends T>> iterable) {
            this.a = iterable;
        }

        @Override // java.lang.Iterable
        public Iterator<Flowable<T>> iterator() {
            return new d(this.a.iterator());
        }
    }

    public static <T> Iterable<? extends Flowable<T>> iterableToFlowable(Iterable<? extends SingleSource<? extends T>> iterable) {
        return new c(iterable);
    }
}
