package io.netty.util.internal;

import com.umeng.analytics.pro.ai;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LinkedQueueNode.java */
/* loaded from: classes4.dex */
public final class j<E> {
    private static final long a;
    private E b;
    private volatile j<E> c;

    static {
        try {
            a = w.a(j.class.getDeclaredField(ai.aD));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public j() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(E e) {
        a((j<E>) e);
    }

    public E a() {
        E b = b();
        a((j<E>) null);
        return b;
    }

    public E b() {
        return this.b;
    }

    public void a(E e) {
        this.b = e;
    }

    public void a(j<E> jVar) {
        w.a(this, a, jVar);
    }

    public j<E> c() {
        return this.c;
    }
}
