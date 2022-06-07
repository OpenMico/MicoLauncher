package io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

/* compiled from: WeakReferenceMap.java */
/* loaded from: classes4.dex */
final class g<K, V> extends e<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public g(Map<K, Reference<V>> map) {
        super(map);
    }

    @Override // io.netty.handler.codec.serialization.e
    Reference<V> a(V v) {
        return new WeakReference(v);
    }
}
