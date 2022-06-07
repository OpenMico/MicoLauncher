package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractIndexedListIterator.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class b<E> extends UnmodifiableListIterator<E> {
    private final int a;
    private int b;

    protected abstract E a(int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public b(int i) {
        this(i, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public b(int i, int i2) {
        Preconditions.checkPositionIndex(i2, i);
        this.a = i;
        this.b = i2;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.b < this.a;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final E next() {
        if (hasNext()) {
            int i = this.b;
            this.b = i + 1;
            return a(i);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.b;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b > 0;
    }

    @Override // java.util.ListIterator
    public final E previous() {
        if (hasPrevious()) {
            int i = this.b - 1;
            this.b = i;
            return a(i);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.b - 1;
    }
}
