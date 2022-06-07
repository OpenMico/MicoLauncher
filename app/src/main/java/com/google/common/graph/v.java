package com.google.common.graph;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: MapRetrievalCache.java */
/* loaded from: classes2.dex */
class v<K, V> extends u<K, V> {
    @NullableDecl
    private transient a<K, V> a;
    @NullableDecl
    private transient a<K, V> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public v(Map<K, V> map) {
        super(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.u
    public V b(@NullableDecl Object obj) {
        V e = e(obj);
        if (e != null) {
            return e;
        }
        V c = c(obj);
        if (c != null) {
            b(obj, c);
        }
        return c;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.graph.u
    public V e(@NullableDecl Object obj) {
        V v = (V) super.e(obj);
        if (v != null) {
            return v;
        }
        a<K, V> aVar = this.a;
        if (aVar != null && aVar.a == obj) {
            return aVar.b;
        }
        a<K, V> aVar2 = this.b;
        if (aVar2 == null || aVar2.a != obj) {
            return null;
        }
        a((a) aVar2);
        return aVar2.b;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.graph.u
    public void b() {
        super.b();
        this.a = null;
        this.b = null;
    }

    private void b(K k, V v) {
        a((a) new a<>(k, v));
    }

    private void a(a<K, V> aVar) {
        this.b = this.a;
        this.a = aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MapRetrievalCache.java */
    /* loaded from: classes2.dex */
    public static final class a<K, V> {
        final K a;
        final V b;

        a(K k, V v) {
            this.a = k;
            this.b = v;
        }
    }
}
