package com.jackeywong.varhandle;

/* loaded from: classes2.dex */
public class TokenHolder<K, V> implements ITokenHolder<K, V> {
    private final VarHolder<Pair<K, V>> a = new VarHolder<>();

    @Override // com.jackeywong.varhandle.ITokenHolder
    public V get(K k) {
        Pair<K, V> pair;
        if (k == null || (pair = this.a.get()) == null || !k.equals(pair.key)) {
            return null;
        }
        return pair.value;
    }

    @Override // com.jackeywong.varhandle.ITokenHolder
    public V set(K k, V v) {
        Pair<K, V> pair = this.a.set(Pair.create(k, v));
        if (pair != null) {
            return pair.value;
        }
        return null;
    }
}
