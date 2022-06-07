package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.cache.a;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: ReferenceEntry.java */
@GwtIncompatible
/* loaded from: classes2.dex */
interface e<K, V> {
    a.y<K, V> a();

    void a(long j);

    void a(a.y<K, V> yVar);

    void a(e<K, V> eVar);

    @NullableDecl
    e<K, V> b();

    void b(long j);

    void b(e<K, V> eVar);

    int c();

    void c(e<K, V> eVar);

    @NullableDecl
    K d();

    void d(e<K, V> eVar);

    long e();

    e<K, V> f();

    e<K, V> g();

    long h();

    e<K, V> i();

    e<K, V> j();
}
