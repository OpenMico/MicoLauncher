package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;
import java.util.Queue;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract Queue<E> delegate();

    @CanIgnoreReturnValue
    public boolean offer(E e) {
        return delegate().offer(e);
    }

    @Override // java.util.Queue
    @CanIgnoreReturnValue
    public E poll() {
        return delegate().poll();
    }

    @Override // java.util.Queue
    @CanIgnoreReturnValue
    public E remove() {
        return delegate().remove();
    }

    @Override // java.util.Queue
    public E peek() {
        return delegate().peek();
    }

    @Override // java.util.Queue
    public E element() {
        return delegate().element();
    }

    protected boolean standardOffer(E e) {
        try {
            return add(e);
        } catch (IllegalStateException unused) {
            return false;
        }
    }

    protected E standardPeek() {
        try {
            return element();
        } catch (NoSuchElementException unused) {
            return null;
        }
    }

    protected E standardPoll() {
        try {
            return remove();
        } catch (NoSuchElementException unused) {
            return null;
        }
    }
}
