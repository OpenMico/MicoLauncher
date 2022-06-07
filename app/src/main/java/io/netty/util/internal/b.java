package io.netty.util.internal;

import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseLinkedQueue.java */
/* loaded from: classes4.dex */
public abstract class b<E> extends c<E> {
    public int capacity() {
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, io.netty.util.internal.MessagePassingQueue
    public final int size() {
        j<E> c;
        j<E> lvConsumerNode = lvConsumerNode();
        j<E> lvProducerNode = lvProducerNode();
        int i = 0;
        while (lvConsumerNode != lvProducerNode && i < Integer.MAX_VALUE) {
            do {
                c = lvConsumerNode.c();
            } while (c == null);
            i++;
            lvConsumerNode = c;
        }
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, io.netty.util.internal.MessagePassingQueue
    public final boolean isEmpty() {
        return lvConsumerNode() == lvProducerNode();
    }
}
