package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TransformedIterator.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class cp<F, T> implements Iterator<T> {
    final Iterator<? extends F> c;

    abstract T a(F f);

    /* JADX INFO: Access modifiers changed from: package-private */
    public cp(Iterator<? extends F> it) {
        this.c = (Iterator) Preconditions.checkNotNull(it);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.c.hasNext();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Iterator
    public final T next() {
        return (T) a(this.c.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.c.remove();
    }
}
