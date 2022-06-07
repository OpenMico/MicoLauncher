package io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;

/* compiled from: SoftReferenceMap.java */
/* loaded from: classes4.dex */
final class f<K, V> extends e<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public f(Map<K, Reference<V>> map) {
        super(map);
    }

    @Override // io.netty.handler.codec.serialization.e
    Reference<V> a(V v) {
        return new SoftReference(v);
    }
}
