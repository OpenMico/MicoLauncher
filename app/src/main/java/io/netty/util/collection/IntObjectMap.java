package io.netty.util.collection;

import java.util.Map;

/* loaded from: classes4.dex */
public interface IntObjectMap<V> extends Map<Integer, V> {

    /* loaded from: classes4.dex */
    public interface PrimitiveEntry<V> {
        int key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(int i);

    Iterable<PrimitiveEntry<V>> entries();

    V get(int i);

    V put(int i, V v);

    V remove(int i);
}
