package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Sets;
import com.umeng.analytics.pro.ai;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: AggregateFutureState.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class d {
    private static final a c;
    private static final Logger d = Logger.getLogger(d.class.getName());
    private volatile Set<Throwable> a = null;
    private volatile int b;

    abstract void a(Set<Throwable> set);

    static /* synthetic */ int b(d dVar) {
        int i = dVar.b;
        dVar.b = i - 1;
        return i;
    }

    static {
        a aVar;
        Throwable th = null;
        try {
            aVar = new b(AtomicReferenceFieldUpdater.newUpdater(d.class, Set.class, ai.at), AtomicIntegerFieldUpdater.newUpdater(d.class, "b"));
        } catch (Throwable th2) {
            th = th2;
            aVar = new c();
        }
        c = aVar;
        if (th != null) {
            d.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(int i) {
        this.b = i;
    }

    final Set<Throwable> d() {
        Set<Throwable> set = this.a;
        if (set != null) {
            return set;
        }
        Set<Throwable> newConcurrentHashSet = Sets.newConcurrentHashSet();
        a(newConcurrentHashSet);
        c.a(this, null, newConcurrentHashSet);
        return this.a;
    }

    final int e() {
        return c.a(this);
    }

    /* compiled from: AggregateFutureState.java */
    /* loaded from: classes2.dex */
    private static abstract class a {
        abstract int a(d dVar);

        abstract void a(d dVar, Set<Throwable> set, Set<Throwable> set2);

        private a() {
        }
    }

    /* compiled from: AggregateFutureState.java */
    /* loaded from: classes2.dex */
    private static final class b extends a {
        final AtomicReferenceFieldUpdater<d, Set<Throwable>> a;
        final AtomicIntegerFieldUpdater<d> b;

        b(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater) {
            super();
            this.a = atomicReferenceFieldUpdater;
            this.b = atomicIntegerFieldUpdater;
        }

        @Override // com.google.common.util.concurrent.d.a
        void a(d dVar, Set<Throwable> set, Set<Throwable> set2) {
            this.a.compareAndSet(dVar, set, set2);
        }

        @Override // com.google.common.util.concurrent.d.a
        int a(d dVar) {
            return this.b.decrementAndGet(dVar);
        }
    }

    /* compiled from: AggregateFutureState.java */
    /* loaded from: classes2.dex */
    private static final class c extends a {
        private c() {
            super();
        }

        @Override // com.google.common.util.concurrent.d.a
        void a(d dVar, Set<Throwable> set, Set<Throwable> set2) {
            synchronized (dVar) {
                if (dVar.a == set) {
                    dVar.a = set2;
                }
            }
        }

        @Override // com.google.common.util.concurrent.d.a
        int a(d dVar) {
            int i;
            synchronized (dVar) {
                d.b(dVar);
                i = dVar.b;
            }
            return i;
        }
    }
}
