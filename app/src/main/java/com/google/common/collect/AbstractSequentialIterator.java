package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {
    @NullableDecl
    private T a;

    protected abstract T computeNext(T t);

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractSequentialIterator(@NullableDecl T t) {
        this.a = t;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a != null;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (hasNext()) {
            try {
                return this.a;
            } finally {
                this.a = computeNext(this.a);
            }
        } else {
            throw new NoSuchElementException();
        }
    }
}
