package com.fasterxml.jackson.databind.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class ArrayIterator<T> implements Iterable<T>, Iterator<T> {
    private final T[] a;
    private int b = 0;

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return this;
    }

    public ArrayIterator(T[] tArr) {
        this.a = tArr;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.b < this.a.length;
    }

    @Override // java.util.Iterator
    public T next() {
        int i = this.b;
        T[] tArr = this.a;
        if (i < tArr.length) {
            this.b = i + 1;
            return tArr[i];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
