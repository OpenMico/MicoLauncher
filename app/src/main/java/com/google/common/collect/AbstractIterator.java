package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {
    private a a = a.NOT_READY;
    @NullableDecl
    private T b;

    /* loaded from: classes2.dex */
    public enum a {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    protected abstract T computeNext();

    @CanIgnoreReturnValue
    protected final T endOfData() {
        this.a = a.DONE;
        return null;
    }

    @Override // java.util.Iterator
    @CanIgnoreReturnValue
    public final boolean hasNext() {
        Preconditions.checkState(this.a != a.FAILED);
        switch (this.a) {
            case DONE:
                return false;
            case READY:
                return true;
            default:
                return a();
        }
    }

    private boolean a() {
        this.a = a.FAILED;
        this.b = computeNext();
        if (this.a == a.DONE) {
            return false;
        }
        this.a = a.READY;
        return true;
    }

    @Override // java.util.Iterator
    @CanIgnoreReturnValue
    public final T next() {
        if (hasNext()) {
            this.a = a.NOT_READY;
            T t = this.b;
            this.b = null;
            return t;
        }
        throw new NoSuchElementException();
    }

    public final T peek() {
        if (hasNext()) {
            return this.b;
        }
        throw new NoSuchElementException();
    }
}
