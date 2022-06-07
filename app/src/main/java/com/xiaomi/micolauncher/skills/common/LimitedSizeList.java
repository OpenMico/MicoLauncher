package com.xiaomi.micolauncher.skills.common;

import java.util.LinkedList;

/* loaded from: classes3.dex */
public class LimitedSizeList<E> extends LinkedList<E> {
    private int capacity;

    public LimitedSizeList(int i) {
        this.capacity = 10;
        this.capacity = i;
    }

    @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public boolean add(E e) {
        if (size() >= this.capacity) {
            removeFirst();
        }
        return super.add(e);
    }
}
