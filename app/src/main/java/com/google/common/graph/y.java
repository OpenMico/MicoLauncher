package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: UndirectedGraphConnections.java */
/* loaded from: classes2.dex */
final class y<N, V> implements s<N, V> {
    private final Map<N, V> a;

    private y(Map<N, V> map) {
        this.a = (Map) Preconditions.checkNotNull(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, V> y<N, V> a() {
        return new y<>(new HashMap(2, 1.0f));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, V> y<N, V> a(Map<N, V> map) {
        return new y<>(ImmutableMap.copyOf(map));
    }

    @Override // com.google.common.graph.s
    public Set<N> b() {
        return Collections.unmodifiableSet(this.a.keySet());
    }

    @Override // com.google.common.graph.s
    public Set<N> c() {
        return b();
    }

    @Override // com.google.common.graph.s
    public Set<N> d() {
        return b();
    }

    @Override // com.google.common.graph.s
    public V a(N n) {
        return this.a.get(n);
    }

    @Override // com.google.common.graph.s
    public void b(N n) {
        c(n);
    }

    @Override // com.google.common.graph.s
    public V c(N n) {
        return this.a.remove(n);
    }

    @Override // com.google.common.graph.s
    public void a(N n, V v) {
        b(n, v);
    }

    @Override // com.google.common.graph.s
    public V b(N n, V v) {
        return this.a.put(n, v);
    }
}
