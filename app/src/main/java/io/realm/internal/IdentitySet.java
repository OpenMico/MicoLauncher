package io.realm.internal;

import java.util.IdentityHashMap;

/* loaded from: classes5.dex */
public class IdentitySet<K> extends IdentityHashMap<K, Integer> {
    private static final Integer a = 0;

    public void add(K k) {
        put(k, a);
    }
}
