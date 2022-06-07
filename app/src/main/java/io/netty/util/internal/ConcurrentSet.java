package io.netty.util.internal;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes4.dex */
public final class ConcurrentSet<E> extends AbstractSet<E> implements Serializable {
    private static final long serialVersionUID = -6761513279741915432L;
    private final ConcurrentMap<E, Boolean> map = PlatformDependent.newConcurrentHashMap();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.map.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(E e) {
        return this.map.putIfAbsent(e, Boolean.TRUE) == null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        return this.map.remove(obj) != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }
}
