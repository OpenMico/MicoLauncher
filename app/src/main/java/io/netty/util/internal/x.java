package io.netty.util.internal;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UnpaddedInternalThreadLocalMap.java */
/* loaded from: classes4.dex */
public class x {
    static final ThreadLocal<InternalThreadLocalMap> a = new ThreadLocal<>();
    static final AtomicInteger b = new AtomicInteger();
    Object[] c;
    int d;
    int e;
    Map<Class<?>, Boolean> f;
    IntegerHolder g;
    ThreadLocalRandom h;
    Map<Class<?>, TypeParameterMatcher> i;
    Map<Class<?>, Map<String, TypeParameterMatcher>> j;
    StringBuilder k;
    Map<Charset, CharsetEncoder> l;
    Map<Charset, CharsetDecoder> m;
    ArrayList<Object> n;

    /* JADX INFO: Access modifiers changed from: package-private */
    public x(Object[] objArr) {
        this.c = objArr;
    }
}
