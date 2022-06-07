package java8.util;

import java.util.Map;

/* compiled from: KeyValueHolder.java */
/* loaded from: classes5.dex */
final class w<K, V> implements Map.Entry<K, V> {
    final K a;
    final V b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public w(K k, V v) {
        this.a = (K) Objects.requireNonNull(k);
        this.b = (V) Objects.requireNonNull(v);
    }

    @Override // java.util.Map.Entry
    public K getKey() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public V getValue() {
        return this.b;
    }

    @Override // java.util.Map.Entry
    public V setValue(V v) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.a.equals(entry.getKey()) && this.b.equals(entry.getValue());
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    public String toString() {
        return this.a + "=" + this.b;
    }
}
