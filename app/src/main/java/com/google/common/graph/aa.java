package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UndirectedNetworkConnections.java */
/* loaded from: classes2.dex */
public final class aa<N, E> extends d<N, E> {
    protected aa(Map<E, N> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> aa<N, E> g() {
        return new aa<>(HashBiMap.create(2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <N, E> aa<N, E> a(Map<E, N> map) {
        return new aa<>(ImmutableBiMap.copyOf((Map) map));
    }

    @Override // com.google.common.graph.x
    public Set<N> a() {
        return Collections.unmodifiableSet(((BiMap) this.a).values());
    }

    @Override // com.google.common.graph.x
    public Set<E> c(N n) {
        return new n(((BiMap) this.a).inverse(), n);
    }
}
