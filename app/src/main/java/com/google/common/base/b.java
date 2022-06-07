package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: AbstractIterator.java */
@GwtCompatible
/* loaded from: classes2.dex */
abstract class b<T> implements Iterator<T> {
    private a a = a.NOT_READY;
    @NullableDecl
    private T b;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractIterator.java */
    /* loaded from: classes2.dex */
    public enum a {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    protected abstract T a();

    @CanIgnoreReturnValue
    @NullableDecl
    protected final T b() {
        this.a = a.DONE;
        return null;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        Preconditions.checkState(this.a != a.FAILED);
        switch (this.a) {
            case READY:
                return true;
            case DONE:
                return false;
            default:
                return c();
        }
    }

    private boolean c() {
        this.a = a.FAILED;
        this.b = a();
        if (this.a == a.DONE) {
            return false;
        }
        this.a = a.READY;
        return true;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (hasNext()) {
            this.a = a.NOT_READY;
            T t = this.b;
            this.b = null;
            return t;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
