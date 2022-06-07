package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;

/* compiled from: NetworkConnections.java */
/* loaded from: classes2.dex */
interface x<N, E> {
    N a(E e);

    @CanIgnoreReturnValue
    N a(E e, boolean z);

    Set<N> a();

    void a(E e, N n);

    void a(E e, N n, boolean z);

    @CanIgnoreReturnValue
    N b(E e);

    Set<E> b();

    Set<E> c();

    Set<E> c(N n);

    Set<E> d();

    Set<N> e();

    Set<N> f();
}
