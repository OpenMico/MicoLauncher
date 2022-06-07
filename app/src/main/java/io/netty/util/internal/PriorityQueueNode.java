package io.netty.util.internal;

/* loaded from: classes4.dex */
public interface PriorityQueueNode<T> extends Comparable<T> {
    public static final int INDEX_NOT_IN_QUEUE = -1;

    int priorityQueueIndex();

    void priorityQueueIndex(int i);
}
