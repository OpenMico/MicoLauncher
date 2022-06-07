package io.netty.util.internal;

import java.util.Iterator;

/* loaded from: classes4.dex */
public final class ReadOnlyIterator<T> implements Iterator<T> {
    private final Iterator<? extends T> a;

    public ReadOnlyIterator(Iterator<? extends T> it) {
        if (it != null) {
            this.a = it;
            return;
        }
        throw new NullPointerException("iterator");
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        return (T) this.a.next();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("read-only");
    }
}
