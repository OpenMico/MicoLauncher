package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* compiled from: DirectedNetworkConnections.java */
/* loaded from: classes2.dex */
final class m<N, E> extends b<N, E> {
    protected m(Map<E, N> map, Map<E, N> map2, int i) {
        super(map, map2, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> m<N, E> g() {
        return new m<>(HashBiMap.create(2), HashBiMap.create(2), 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> m<N, E> a(Map<E, N> map, Map<E, N> map2, int i) {
        return new m<>(ImmutableBiMap.copyOf((Map) map), ImmutableBiMap.copyOf((Map) map2), i);
    }

    @Override // com.google.common.graph.x
    public Set<N> e() {
        return Collections.unmodifiableSet(((BiMap) this.a).values());
    }

    @Override // com.google.common.graph.x
    public Set<N> f() {
        return Collections.unmodifiableSet(((BiMap) this.b).values());
    }

    @Override // com.google.common.graph.x
    public Set<E> c(N n) {
        return new n(((BiMap) this.b).inverse(), n);
    }
}
