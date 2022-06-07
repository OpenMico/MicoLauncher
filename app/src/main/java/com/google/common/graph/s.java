package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: GraphConnections.java */
/* loaded from: classes2.dex */
interface s<N, V> {
    @NullableDecl
    V a(N n);

    void a(N n, V v);

    @CanIgnoreReturnValue
    V b(N n, V v);

    Set<N> b();

    void b(N n);

    @CanIgnoreReturnValue
    V c(N n);

    Set<N> c();

    Set<N> d();
}
