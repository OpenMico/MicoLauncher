package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.google.common.collect.bj.h;
import com.google.common.collect.bj.m;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MapMakerInternalMap.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public class bj<K, V, E extends h<K, V, E>, S extends m<K, V, E, S>> extends AbstractMap<K, V> implements Serializable, ConcurrentMap<K, V> {
    static final af<Object, Object, d> e = new af<Object, Object, d>() { // from class: com.google.common.collect.bj.1
        public af<Object, Object, d> a(ReferenceQueue<Object> referenceQueue, d dVar) {
            return this;
        }

        /* renamed from: a */
        public d b() {
            return null;
        }

        @Override // com.google.common.collect.bj.af
        public void clear() {
        }

        @Override // com.google.common.collect.bj.af
        public Object get() {
            return null;
        }
    };
    private static final long serialVersionUID = 5;
    final transient int a;
    final transient int b;
    final transient m<K, V, E, S>[] c;
    final int concurrencyLevel;
    final transient i<K, V, E, S> d;
    @MonotonicNonNullDecl
    transient Set<K> f;
    @MonotonicNonNullDecl
    transient Collection<V> g;
    @MonotonicNonNullDecl
    transient Set<Map.Entry<K, V>> h;
    final Equivalence<Object> keyEquivalence;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public interface ae<K, V, E extends h<K, V, E>> extends h<K, V, E> {
        af<K, V, E> d();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public interface af<K, V, E extends h<K, V, E>> {
        af<K, V, E> a(ReferenceQueue<V> referenceQueue, E e);

        E b();

        void clear();

        @NullableDecl
        V get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public interface h<K, V, E extends h<K, V, E>> {
        K a();

        int b();

        E c();

        V e();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public interface i<K, V, E extends h<K, V, E>, S extends m<K, V, E, S>> {
        E a(S s, E e, @NullableDecl E e2);

        E a(S s, K k, int i, @NullableDecl E e);

        S a(bj<K, V, E, S> bjVar, int i, int i2);

        o a();

        void a(S s, E e, V v);

        o b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public enum o {
        STRONG {
            @Override // com.google.common.collect.bj.o
            Equivalence<Object> a() {
                return Equivalence.equals();
            }
        },
        WEAK {
            @Override // com.google.common.collect.bj.o
            Equivalence<Object> a() {
                return Equivalence.identity();
            }
        };

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract Equivalence<Object> a();
    }

    /* loaded from: classes2.dex */
    interface v extends h {
    }

    static int a(int i2) {
        int i3 = i2 + ((i2 << 15) ^ (-12931));
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (i6 << 2) + (i6 << 14);
        return i7 ^ (i7 >>> 16);
    }

    private bj(MapMaker mapMaker, i<K, V, E, S> iVar) {
        this.concurrencyLevel = Math.min(mapMaker.c(), 65536);
        this.keyEquivalence = mapMaker.a();
        this.d = iVar;
        int min = Math.min(mapMaker.b(), 1073741824);
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        int i5 = 1;
        while (i5 < this.concurrencyLevel) {
            i4++;
            i5 <<= 1;
        }
        this.b = 32 - i4;
        this.a = i5 - 1;
        this.c = c(i5);
        int i6 = min / i5;
        while (i3 < (i5 * i6 < min ? i6 + 1 : i6)) {
            i3 <<= 1;
        }
        while (true) {
            m<K, V, E, S>[] mVarArr = this.c;
            if (i2 < mVarArr.length) {
                mVarArr[i2] = a(i3, -1);
                i2++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> bj<K, V, ? extends h<K, V, ?>, ?> a(MapMaker mapMaker) {
        if (mapMaker.d() == o.STRONG && mapMaker.e() == o.STRONG) {
            return new bj<>(mapMaker, r.a.c());
        }
        if (mapMaker.d() == o.STRONG && mapMaker.e() == o.WEAK) {
            return new bj<>(mapMaker, t.a.c());
        }
        if (mapMaker.d() == o.WEAK && mapMaker.e() == o.STRONG) {
            return new bj<>(mapMaker, aa.a.c());
        }
        if (mapMaker.d() == o.WEAK && mapMaker.e() == o.WEAK) {
            return new bj<>(mapMaker, ac.a.c());
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K> bj<K, MapMaker.a, ? extends h<K, MapMaker.a, ?>, ?> b(MapMaker mapMaker) {
        if (mapMaker.d() == o.STRONG && mapMaker.e() == o.STRONG) {
            return new bj<>(mapMaker, p.a.c());
        }
        if (mapMaker.d() == o.WEAK && mapMaker.e() == o.STRONG) {
            return new bj<>(mapMaker, y.a.c());
        }
        if (mapMaker.e() == o.WEAK) {
            throw new IllegalArgumentException("Map cannot have both weak and dummy values");
        }
        throw new AssertionError();
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    static abstract class b<K, V, E extends h<K, V, E>> implements h<K, V, E> {
        final K a;
        final int b;
        @NullableDecl
        final E c;

        b(K k, int i, @NullableDecl E e) {
            this.a = k;
            this.b = i;
            this.c = e;
        }

        @Override // com.google.common.collect.bj.h
        public K a() {
            return this.a;
        }

        @Override // com.google.common.collect.bj.h
        public int b() {
            return this.b;
        }

        @Override // com.google.common.collect.bj.h
        public E c() {
            return this.c;
        }
    }

    static <K, V, E extends h<K, V, E>> af<K, V, E> a() {
        return (af<K, V, E>) e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class r<K, V> extends b<K, V, r<K, V>> implements v<K, V, r<K, V>> {
        @NullableDecl
        private volatile V d = null;

        r(K k, int i, @NullableDecl r<K, V> rVar) {
            super(k, i, rVar);
        }

        @Override // com.google.common.collect.bj.h
        @NullableDecl
        public V e() {
            return this.d;
        }

        void a(V v) {
            this.d = v;
        }

        r<K, V> a(r<K, V> rVar) {
            r<K, V> rVar2 = new r<>(this.a, this.b, rVar);
            rVar2.d = this.d;
            return rVar2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: MapMakerInternalMap.java */
        /* loaded from: classes2.dex */
        public static final class a<K, V> implements i<K, V, r<K, V>, s<K, V>> {
            private static final a<?, ?> a = new a<>();

            a() {
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, h hVar, @NullableDecl h hVar2) {
                return a((s) ((s) mVar), (r) ((r) hVar), (r) ((r) hVar2));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, Object obj, int i, @NullableDecl h hVar) {
                return a((s<s<K, V>, V>) mVar, (s<K, V>) obj, i, (r<s<K, V>, V>) hVar);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ void a(m mVar, h hVar, Object obj) {
                a((s<K, r<K, V>>) mVar, (r<K, r<K, V>>) hVar, (r<K, V>) obj);
            }

            static <K, V> a<K, V> c() {
                return (a<K, V>) a;
            }

            @Override // com.google.common.collect.bj.i
            public o a() {
                return o.STRONG;
            }

            @Override // com.google.common.collect.bj.i
            public o b() {
                return o.STRONG;
            }

            /* renamed from: b */
            public s<K, V> a(bj<K, V, r<K, V>, s<K, V>> bjVar, int i, int i2) {
                return new s<>(bjVar, i, i2);
            }

            public r<K, V> a(s<K, V> sVar, r<K, V> rVar, @NullableDecl r<K, V> rVar2) {
                return rVar.a((r) rVar2);
            }

            public void a(s<K, V> sVar, r<K, V> rVar, V v) {
                rVar.a((r<K, V>) v);
            }

            public r<K, V> a(s<K, V> sVar, K k, int i, @NullableDecl r<K, V> rVar) {
                return new r<>(k, i, rVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class t<K, V> extends b<K, V, t<K, V>> implements ae<K, V, t<K, V>> {
        private volatile af<K, V, t<K, V>> d = bj.a();

        t(K k, int i, @NullableDecl t<K, V> tVar) {
            super(k, i, tVar);
        }

        @Override // com.google.common.collect.bj.h
        public V e() {
            return this.d.get();
        }

        void a(V v, ReferenceQueue<V> referenceQueue) {
            af<K, V, t<K, V>> afVar = this.d;
            this.d = new ag(referenceQueue, v, this);
            afVar.clear();
        }

        t<K, V> a(ReferenceQueue<V> referenceQueue, t<K, V> tVar) {
            t<K, V> tVar2 = new t<>(this.a, this.b, tVar);
            tVar2.d = this.d.a(referenceQueue, tVar2);
            return tVar2;
        }

        @Override // com.google.common.collect.bj.ae
        public af<K, V, t<K, V>> d() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: MapMakerInternalMap.java */
        /* loaded from: classes2.dex */
        public static final class a<K, V> implements i<K, V, t<K, V>, u<K, V>> {
            private static final a<?, ?> a = new a<>();

            a() {
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, h hVar, @NullableDecl h hVar2) {
                return a((u) ((u) mVar), (t) ((t) hVar), (t) ((t) hVar2));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, Object obj, int i, @NullableDecl h hVar) {
                return a((u<u<K, V>, V>) mVar, (u<K, V>) obj, i, (t<u<K, V>, V>) hVar);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ void a(m mVar, h hVar, Object obj) {
                a((u<K, t<K, V>>) mVar, (t<K, t<K, V>>) hVar, (t<K, V>) obj);
            }

            static <K, V> a<K, V> c() {
                return (a<K, V>) a;
            }

            @Override // com.google.common.collect.bj.i
            public o a() {
                return o.STRONG;
            }

            @Override // com.google.common.collect.bj.i
            public o b() {
                return o.WEAK;
            }

            /* renamed from: b */
            public u<K, V> a(bj<K, V, t<K, V>, u<K, V>> bjVar, int i, int i2) {
                return new u<>(bjVar, i, i2);
            }

            public t<K, V> a(u<K, V> uVar, t<K, V> tVar, @NullableDecl t<K, V> tVar2) {
                if (m.a(tVar)) {
                    return null;
                }
                return tVar.a(((u) uVar).queueForValues, tVar2);
            }

            public void a(u<K, V> uVar, t<K, V> tVar, V v) {
                tVar.a((t<K, V>) v, (ReferenceQueue<t<K, V>>) ((u) uVar).queueForValues);
            }

            public t<K, V> a(u<K, V> uVar, K k, int i, @NullableDecl t<K, V> tVar) {
                return new t<>(k, i, tVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class p<K> extends b<K, MapMaker.a, p<K>> implements v<K, MapMaker.a, p<K>> {
        p(K k, int i, @NullableDecl p<K> pVar) {
            super(k, i, pVar);
        }

        /* renamed from: d */
        public MapMaker.a e() {
            return MapMaker.a.VALUE;
        }

        p<K> a(p<K> pVar) {
            return new p<>(this.a, this.b, pVar);
        }

        /* compiled from: MapMakerInternalMap.java */
        /* loaded from: classes2.dex */
        static final class a<K> implements i<K, MapMaker.a, p<K>, q<K>> {
            private static final a<?> a = new a<>();

            public void a(q<K> qVar, p<K> pVar, MapMaker.a aVar) {
            }

            a() {
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, h hVar, @NullableDecl h hVar2) {
                return a((q) ((q) mVar), (p) ((p) hVar), (p) ((p) hVar2));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, Object obj, int i, @NullableDecl h hVar) {
                return a((q<q<K>>) mVar, (q<K>) obj, i, (p<q<K>>) hVar);
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ void a(m mVar, h hVar, MapMaker.a aVar) {
                a((q) ((q) mVar), (p) ((p) hVar), aVar);
            }

            static <K> a<K> c() {
                return (a<K>) a;
            }

            @Override // com.google.common.collect.bj.i
            public o a() {
                return o.STRONG;
            }

            @Override // com.google.common.collect.bj.i
            public o b() {
                return o.STRONG;
            }

            /* renamed from: b */
            public q<K> a(bj<K, MapMaker.a, p<K>, q<K>> bjVar, int i, int i2) {
                return new q<>(bjVar, i, i2);
            }

            public p<K> a(q<K> qVar, p<K> pVar, @NullableDecl p<K> pVar2) {
                return pVar.a(pVar2);
            }

            public p<K> a(q<K> qVar, K k, int i, @NullableDecl p<K> pVar) {
                return new p<>(k, i, pVar);
            }
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    static abstract class c<K, V, E extends h<K, V, E>> extends WeakReference<K> implements h<K, V, E> {
        final int a;
        @NullableDecl
        final E b;

        c(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl E e) {
            super(k, referenceQueue);
            this.a = i;
            this.b = e;
        }

        @Override // com.google.common.collect.bj.h
        public K a() {
            return (K) get();
        }

        @Override // com.google.common.collect.bj.h
        public int b() {
            return this.a;
        }

        @Override // com.google.common.collect.bj.h
        public E c() {
            return this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class y<K> extends c<K, MapMaker.a, y<K>> implements v<K, MapMaker.a, y<K>> {
        y(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl y<K> yVar) {
            super(referenceQueue, k, i, yVar);
        }

        /* renamed from: d */
        public MapMaker.a e() {
            return MapMaker.a.VALUE;
        }

        y<K> a(ReferenceQueue<K> referenceQueue, y<K> yVar) {
            return new y<>(referenceQueue, a(), this.a, yVar);
        }

        /* compiled from: MapMakerInternalMap.java */
        /* loaded from: classes2.dex */
        static final class a<K> implements i<K, MapMaker.a, y<K>, z<K>> {
            private static final a<?> a = new a<>();

            public void a(z<K> zVar, y<K> yVar, MapMaker.a aVar) {
            }

            a() {
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, h hVar, @NullableDecl h hVar2) {
                return a((z) ((z) mVar), (y) ((y) hVar), (y) ((y) hVar2));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, Object obj, int i, @NullableDecl h hVar) {
                return a((z<z<K>>) mVar, (z<K>) obj, i, (y<z<K>>) hVar);
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ void a(m mVar, h hVar, MapMaker.a aVar) {
                a((z) ((z) mVar), (y) ((y) hVar), aVar);
            }

            static <K> a<K> c() {
                return (a<K>) a;
            }

            @Override // com.google.common.collect.bj.i
            public o a() {
                return o.WEAK;
            }

            @Override // com.google.common.collect.bj.i
            public o b() {
                return o.STRONG;
            }

            /* renamed from: b */
            public z<K> a(bj<K, MapMaker.a, y<K>, z<K>> bjVar, int i, int i2) {
                return new z<>(bjVar, i, i2);
            }

            public y<K> a(z<K> zVar, y<K> yVar, @NullableDecl y<K> yVar2) {
                if (yVar.a() == null) {
                    return null;
                }
                return yVar.a(((z) zVar).queueForKeys, yVar2);
            }

            public y<K> a(z<K> zVar, K k, int i, @NullableDecl y<K> yVar) {
                return new y<>(((z) zVar).queueForKeys, k, i, yVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class aa<K, V> extends c<K, V, aa<K, V>> implements v<K, V, aa<K, V>> {
        @NullableDecl
        private volatile V c = null;

        aa(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl aa<K, V> aaVar) {
            super(referenceQueue, k, i, aaVar);
        }

        @Override // com.google.common.collect.bj.h
        @NullableDecl
        public V e() {
            return this.c;
        }

        void a(V v) {
            this.c = v;
        }

        aa<K, V> a(ReferenceQueue<K> referenceQueue, aa<K, V> aaVar) {
            aa<K, V> aaVar2 = new aa<>(referenceQueue, a(), this.a, aaVar);
            aaVar2.a(this.c);
            return aaVar2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: MapMakerInternalMap.java */
        /* loaded from: classes2.dex */
        public static final class a<K, V> implements i<K, V, aa<K, V>, ab<K, V>> {
            private static final a<?, ?> a = new a<>();

            a() {
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, h hVar, @NullableDecl h hVar2) {
                return a((ab) ((ab) mVar), (aa) ((aa) hVar), (aa) ((aa) hVar2));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, Object obj, int i, @NullableDecl h hVar) {
                return a((ab<ab<K, V>, V>) mVar, (ab<K, V>) obj, i, (aa<ab<K, V>, V>) hVar);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ void a(m mVar, h hVar, Object obj) {
                a((ab<K, aa<K, V>>) mVar, (aa<K, aa<K, V>>) hVar, (aa<K, V>) obj);
            }

            static <K, V> a<K, V> c() {
                return (a<K, V>) a;
            }

            @Override // com.google.common.collect.bj.i
            public o a() {
                return o.WEAK;
            }

            @Override // com.google.common.collect.bj.i
            public o b() {
                return o.STRONG;
            }

            /* renamed from: b */
            public ab<K, V> a(bj<K, V, aa<K, V>, ab<K, V>> bjVar, int i, int i2) {
                return new ab<>(bjVar, i, i2);
            }

            public aa<K, V> a(ab<K, V> abVar, aa<K, V> aaVar, @NullableDecl aa<K, V> aaVar2) {
                if (aaVar.a() == null) {
                    return null;
                }
                return aaVar.a(((ab) abVar).queueForKeys, aaVar2);
            }

            public void a(ab<K, V> abVar, aa<K, V> aaVar, V v) {
                aaVar.a(v);
            }

            public aa<K, V> a(ab<K, V> abVar, K k, int i, @NullableDecl aa<K, V> aaVar) {
                return new aa<>(((ab) abVar).queueForKeys, k, i, aaVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class ac<K, V> extends c<K, V, ac<K, V>> implements ae<K, V, ac<K, V>> {
        private volatile af<K, V, ac<K, V>> c = bj.a();

        ac(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl ac<K, V> acVar) {
            super(referenceQueue, k, i, acVar);
        }

        @Override // com.google.common.collect.bj.h
        public V e() {
            return this.c.get();
        }

        ac<K, V> a(ReferenceQueue<K> referenceQueue, ReferenceQueue<V> referenceQueue2, ac<K, V> acVar) {
            ac<K, V> acVar2 = new ac<>(referenceQueue, a(), this.a, acVar);
            acVar2.c = this.c.a(referenceQueue2, acVar2);
            return acVar2;
        }

        void a(V v, ReferenceQueue<V> referenceQueue) {
            af<K, V, ac<K, V>> afVar = this.c;
            this.c = new ag(referenceQueue, v, this);
            afVar.clear();
        }

        @Override // com.google.common.collect.bj.ae
        public af<K, V, ac<K, V>> d() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: MapMakerInternalMap.java */
        /* loaded from: classes2.dex */
        public static final class a<K, V> implements i<K, V, ac<K, V>, ad<K, V>> {
            private static final a<?, ?> a = new a<>();

            a() {
            }

            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, h hVar, @NullableDecl h hVar2) {
                return a((ad) ((ad) mVar), (ac) ((ac) hVar), (ac) ((ac) hVar2));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ h a(m mVar, Object obj, int i, @NullableDecl h hVar) {
                return a((ad<ad<K, V>, V>) mVar, (ad<K, V>) obj, i, (ac<ad<K, V>, V>) hVar);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.bj.i
            public /* bridge */ /* synthetic */ void a(m mVar, h hVar, Object obj) {
                a((ad<K, ac<K, V>>) mVar, (ac<K, ac<K, V>>) hVar, (ac<K, V>) obj);
            }

            static <K, V> a<K, V> c() {
                return (a<K, V>) a;
            }

            @Override // com.google.common.collect.bj.i
            public o a() {
                return o.WEAK;
            }

            @Override // com.google.common.collect.bj.i
            public o b() {
                return o.WEAK;
            }

            /* renamed from: b */
            public ad<K, V> a(bj<K, V, ac<K, V>, ad<K, V>> bjVar, int i, int i2) {
                return new ad<>(bjVar, i, i2);
            }

            public ac<K, V> a(ad<K, V> adVar, ac<K, V> acVar, @NullableDecl ac<K, V> acVar2) {
                if (acVar.a() != null && !m.a(acVar)) {
                    return acVar.a(((ad) adVar).queueForKeys, ((ad) adVar).queueForValues, acVar2);
                }
                return null;
            }

            public void a(ad<K, V> adVar, ac<K, V> acVar, V v) {
                acVar.a(v, ((ad) adVar).queueForValues);
            }

            public ac<K, V> a(ad<K, V> adVar, K k, int i, @NullableDecl ac<K, V> acVar) {
                return new ac<>(((ad) adVar).queueForKeys, k, i, acVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class d implements h<Object, Object, d> {
        private d() {
            throw new AssertionError();
        }

        /* renamed from: d */
        public d c() {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.bj.h
        public int b() {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.bj.h
        public Object a() {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.bj.h
        public Object e() {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class ag<K, V, E extends h<K, V, E>> extends WeakReference<V> implements af<K, V, E> {
        @Weak
        final E a;

        ag(ReferenceQueue<V> referenceQueue, V v, E e) {
            super(v, referenceQueue);
            this.a = e;
        }

        @Override // com.google.common.collect.bj.af
        public E b() {
            return this.a;
        }

        @Override // com.google.common.collect.bj.af
        public af<K, V, E> a(ReferenceQueue<V> referenceQueue, E e) {
            return new ag(referenceQueue, get(), e);
        }
    }

    int a(Object obj) {
        return a(this.keyEquivalence.hash(obj));
    }

    void a(af<K, V, E> afVar) {
        E b2 = afVar.b();
        int b3 = b2.b();
        b(b3).a((m<K, V, E, S>) ((K) b2.a()), b3, (af<m<K, V, E, S>, V, E>) afVar);
    }

    void a(E e2) {
        int b2 = e2.b();
        b(b2).a((m<K, V, E, S>) e2, b2);
    }

    m<K, V, E, S> b(int i2) {
        return this.c[(i2 >>> this.b) & this.a];
    }

    m<K, V, E, S> a(int i2, int i3) {
        return (S) this.d.a(this, i2, i3);
    }

    V b(E e2) {
        V v2;
        if (e2.a() == null || (v2 = (V) e2.e()) == null) {
            return null;
        }
        return v2;
    }

    final m<K, V, E, S>[] c(int i2) {
        return new m[i2];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static abstract class m<K, V, E extends h<K, V, E>, S extends m<K, V, E, S>> extends ReentrantLock {
        volatile int count;
        @Weak
        final bj<K, V, E, S> map;
        final int maxSegmentSize;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        @MonotonicNonNullDecl
        volatile AtomicReferenceArray<E> table;
        int threshold;

        abstract S a();

        @GuardedBy("this")
        void b() {
        }

        void c() {
        }

        m(bj<K, V, E, S> bjVar, int i, int i2) {
            this.map = bjVar;
            this.maxSegmentSize = i2;
            a(a(i));
        }

        void a(E e, V v) {
            this.map.d.a((i<K, V, E, S>) a(), (S) e, (E) v);
        }

        E a(E e, E e2) {
            return this.map.d.a((i<K, V, E, S>) a(), (h) e, (h) e2);
        }

        AtomicReferenceArray<E> a(int i) {
            return new AtomicReferenceArray<>(i);
        }

        void a(AtomicReferenceArray<E> atomicReferenceArray) {
            this.threshold = (atomicReferenceArray.length() * 3) / 4;
            int i = this.threshold;
            if (i == this.maxSegmentSize) {
                this.threshold = i + 1;
            }
            this.table = atomicReferenceArray;
        }

        void d() {
            if (tryLock()) {
                try {
                    b();
                } finally {
                    unlock();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @GuardedBy("this")
        void a(ReferenceQueue<K> referenceQueue) {
            int i = 0;
            do {
                Reference<? extends K> poll = referenceQueue.poll();
                if (poll != null) {
                    this.map.a((bj<K, V, E, S>) ((h) poll));
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        @GuardedBy("this")
        void b(ReferenceQueue<V> referenceQueue) {
            int i = 0;
            do {
                Reference<? extends V> poll = referenceQueue.poll();
                if (poll != null) {
                    this.map.a((af) ((af) poll));
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        <T> void c(ReferenceQueue<T> referenceQueue) {
            do {
            } while (referenceQueue.poll() != null);
        }

        E b(int i) {
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            return atomicReferenceArray.get(i & (atomicReferenceArray.length() - 1));
        }

        E a(Object obj, int i) {
            if (this.count == 0) {
                return null;
            }
            for (E b = b(i); b != null; b = (E) b.c()) {
                if (b.b() == i) {
                    Object a = b.a();
                    if (a == null) {
                        d();
                    } else if (this.map.keyEquivalence.equivalent(obj, a)) {
                        return b;
                    }
                }
            }
            return null;
        }

        E b(Object obj, int i) {
            return a(obj, i);
        }

        V c(Object obj, int i) {
            try {
                E b = b(obj, i);
                if (b == null) {
                    return null;
                }
                V v = (V) b.e();
                if (v == null) {
                    d();
                }
                return v;
            } finally {
                g();
            }
        }

        boolean d(Object obj, int i) {
            try {
                boolean z = false;
                if (this.count == 0) {
                    return false;
                }
                E b = b(obj, i);
                if (b != null) {
                    if (b.e() != null) {
                        z = true;
                    }
                }
                return z;
            } finally {
                g();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        V a(K k, int i, V v, boolean z) {
            lock();
            try {
                h();
                int i2 = this.count + 1;
                if (i2 > this.threshold) {
                    e();
                    i2 = this.count + 1;
                }
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                E e = atomicReferenceArray.get(length);
                for (h hVar = e; hVar != null; hVar = hVar.c()) {
                    Object a = hVar.a();
                    if (hVar.b() == i && a != null && this.map.keyEquivalence.equivalent(k, a)) {
                        V v2 = (V) hVar.e();
                        if (v2 == null) {
                            this.modCount++;
                            a((m<K, V, E, S>) hVar, (h) v);
                            this.count = this.count;
                            return null;
                        } else if (z) {
                            return v2;
                        } else {
                            this.modCount++;
                            a((m<K, V, E, S>) hVar, (h) v);
                            return v2;
                        }
                    }
                }
                this.modCount++;
                E a2 = this.map.d.a(a(), k, i, e);
                a((m<K, V, E, S>) a2, (E) v);
                atomicReferenceArray.set(length, a2);
                this.count = i2;
                return null;
            } finally {
                unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @GuardedBy("this")
        void e() {
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            int length = atomicReferenceArray.length();
            if (length < 1073741824) {
                int i = this.count;
                AtomicReferenceArray<E> atomicReferenceArray2 = (AtomicReferenceArray<E>) a(length << 1);
                this.threshold = (atomicReferenceArray2.length() * 3) / 4;
                int length2 = atomicReferenceArray2.length() - 1;
                for (int i2 = 0; i2 < length; i2++) {
                    E e = atomicReferenceArray.get(i2);
                    if (e != null) {
                        h c = e.c();
                        int b = e.b() & length2;
                        if (c == null) {
                            atomicReferenceArray2.set(b, e);
                        } else {
                            h hVar = e;
                            while (c != null) {
                                int b2 = c.b() & length2;
                                if (b2 != b) {
                                    hVar = c;
                                    b = b2;
                                }
                                c = c.c();
                            }
                            atomicReferenceArray2.set(b, hVar);
                            while (e != hVar) {
                                int b3 = e.b() & length2;
                                h a = a(e, (h) atomicReferenceArray2.get(b3));
                                if (a != null) {
                                    atomicReferenceArray2.set(b3, a);
                                } else {
                                    i--;
                                }
                                e = e.c();
                            }
                        }
                    }
                }
                this.table = atomicReferenceArray2;
                this.count = i;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        boolean a(K k, int i, V v, V v2) {
            lock();
            try {
                h();
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                E e = atomicReferenceArray.get(length);
                for (h hVar = e; hVar != null; hVar = hVar.c()) {
                    Object a = hVar.a();
                    if (hVar.b() == i && a != null && this.map.keyEquivalence.equivalent(k, a)) {
                        Object e2 = hVar.e();
                        if (e2 == null) {
                            if (a(hVar)) {
                                int i2 = this.count;
                                this.modCount++;
                                atomicReferenceArray.set(length, b(e, hVar));
                                this.count--;
                            }
                            return false;
                        } else if (!this.map.b().equivalent(v, e2)) {
                            return false;
                        } else {
                            this.modCount++;
                            a((m<K, V, E, S>) hVar, (h) v2);
                            return true;
                        }
                    }
                }
                return false;
            } finally {
                unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        V a(K k, int i, V v) {
            lock();
            try {
                h();
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                E e = atomicReferenceArray.get(length);
                for (h hVar = e; hVar != null; hVar = hVar.c()) {
                    Object a = hVar.a();
                    if (hVar.b() == i && a != null && this.map.keyEquivalence.equivalent(k, a)) {
                        V v2 = (V) hVar.e();
                        if (v2 == null) {
                            if (a(hVar)) {
                                int i2 = this.count;
                                this.modCount++;
                                atomicReferenceArray.set(length, b(e, hVar));
                                this.count--;
                            }
                            return null;
                        }
                        this.modCount++;
                        a((m<K, V, E, S>) hVar, (h) v);
                        return v2;
                    }
                }
                return null;
            } finally {
                unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        V e(Object obj, int i) {
            lock();
            try {
                h();
                int i2 = this.count;
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                E e = atomicReferenceArray.get(length);
                for (h hVar = e; hVar != null; hVar = hVar.c()) {
                    Object a = hVar.a();
                    if (hVar.b() == i && a != null && this.map.keyEquivalence.equivalent(obj, a)) {
                        V v = (V) hVar.e();
                        if (v == null && !a(hVar)) {
                            return null;
                        }
                        this.modCount++;
                        atomicReferenceArray.set(length, b(e, hVar));
                        this.count--;
                        return v;
                    }
                }
                return null;
            } finally {
                unlock();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x003f, code lost:
            if (r8.map.b().equivalent(r11, r4.e()) == false) goto L_0x0043;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0041, code lost:
            r5 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0047, code lost:
            if (a(r4) == false) goto L_0x005e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0049, code lost:
            r8.modCount++;
            r0.set(r1, b(r3, r4));
            r8.count--;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x005d, code lost:
            return r5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:
            return false;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        boolean b(java.lang.Object r9, int r10, java.lang.Object r11) {
            /*
                r8 = this;
                r8.lock()
                r8.h()     // Catch: all -> 0x006b
                int r0 = r8.count     // Catch: all -> 0x006b
                java.util.concurrent.atomic.AtomicReferenceArray<E extends com.google.common.collect.bj$h<K, V, E>> r0 = r8.table     // Catch: all -> 0x006b
                int r1 = r0.length()     // Catch: all -> 0x006b
                r2 = 1
                int r1 = r1 - r2
                r1 = r1 & r10
                java.lang.Object r3 = r0.get(r1)     // Catch: all -> 0x006b
                com.google.common.collect.bj$h r3 = (com.google.common.collect.bj.h) r3     // Catch: all -> 0x006b
                r4 = r3
            L_0x0018:
                r5 = 0
                if (r4 == 0) goto L_0x0067
                java.lang.Object r6 = r4.a()     // Catch: all -> 0x006b
                int r7 = r4.b()     // Catch: all -> 0x006b
                if (r7 != r10) goto L_0x0062
                if (r6 == 0) goto L_0x0062
                com.google.common.collect.bj<K, V, E extends com.google.common.collect.bj$h<K, V, E>, S extends com.google.common.collect.bj$m<K, V, E, S>> r7 = r8.map     // Catch: all -> 0x006b
                com.google.common.base.Equivalence<java.lang.Object> r7 = r7.keyEquivalence     // Catch: all -> 0x006b
                boolean r6 = r7.equivalent(r9, r6)     // Catch: all -> 0x006b
                if (r6 == 0) goto L_0x0062
                java.lang.Object r9 = r4.e()     // Catch: all -> 0x006b
                com.google.common.collect.bj<K, V, E extends com.google.common.collect.bj$h<K, V, E>, S extends com.google.common.collect.bj$m<K, V, E, S>> r10 = r8.map     // Catch: all -> 0x006b
                com.google.common.base.Equivalence r10 = r10.b()     // Catch: all -> 0x006b
                boolean r9 = r10.equivalent(r11, r9)     // Catch: all -> 0x006b
                if (r9 == 0) goto L_0x0043
                r5 = r2
                goto L_0x0049
            L_0x0043:
                boolean r9 = a(r4)     // Catch: all -> 0x006b
                if (r9 == 0) goto L_0x005e
            L_0x0049:
                int r9 = r8.modCount     // Catch: all -> 0x006b
                int r9 = r9 + r2
                r8.modCount = r9     // Catch: all -> 0x006b
                com.google.common.collect.bj$h r9 = r8.b(r3, r4)     // Catch: all -> 0x006b
                int r10 = r8.count     // Catch: all -> 0x006b
                int r10 = r10 - r2
                r0.set(r1, r9)     // Catch: all -> 0x006b
                r8.count = r10     // Catch: all -> 0x006b
                r8.unlock()
                return r5
            L_0x005e:
                r8.unlock()
                return r5
            L_0x0062:
                com.google.common.collect.bj$h r4 = r4.c()     // Catch: all -> 0x006b
                goto L_0x0018
            L_0x0067:
                r8.unlock()
                return r5
            L_0x006b:
                r9 = move-exception
                r8.unlock()
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.bj.m.b(java.lang.Object, int, java.lang.Object):boolean");
        }

        void f() {
            if (this.count != 0) {
                lock();
                try {
                    AtomicReferenceArray<E> atomicReferenceArray = this.table;
                    for (int i = 0; i < atomicReferenceArray.length(); i++) {
                        atomicReferenceArray.set(i, null);
                    }
                    c();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                } finally {
                    unlock();
                }
            }
        }

        @GuardedBy("this")
        E b(E e, E e2) {
            int i = this.count;
            E e3 = (E) e2.c();
            while (e != e2) {
                E a = a((h) e, (h) e3);
                if (a != null) {
                    e3 = a;
                } else {
                    i--;
                }
                e = (E) e.c();
            }
            this.count = i;
            return e3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        boolean a(E e, int i) {
            lock();
            try {
                int i2 = this.count;
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                E e2 = atomicReferenceArray.get(length);
                for (h hVar = e2; hVar != null; hVar = hVar.c()) {
                    if (hVar == e) {
                        this.modCount++;
                        atomicReferenceArray.set(length, b(e2, hVar));
                        this.count--;
                        return true;
                    }
                }
                return false;
            } finally {
                unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        boolean a(K k, int i, af<K, V, E> afVar) {
            lock();
            try {
                int i2 = this.count;
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                E e = atomicReferenceArray.get(length);
                for (h hVar = e; hVar != null; hVar = hVar.c()) {
                    Object a = hVar.a();
                    if (hVar.b() == i && a != null && this.map.keyEquivalence.equivalent(k, a)) {
                        if (((ae) hVar).d() != afVar) {
                            return false;
                        } else {
                            this.modCount++;
                            atomicReferenceArray.set(length, b(e, hVar));
                            this.count--;
                            return true;
                        }
                    }
                }
                return false;
            } finally {
                unlock();
            }
        }

        static <K, V, E extends h<K, V, E>> boolean a(E e) {
            return e.e() == null;
        }

        @NullableDecl
        V b(E e) {
            if (e.a() == null) {
                d();
                return null;
            }
            V v = (V) e.e();
            if (v != null) {
                return v;
            }
            d();
            return null;
        }

        void g() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                i();
            }
        }

        @GuardedBy("this")
        void h() {
            j();
        }

        void i() {
            j();
        }

        void j() {
            if (tryLock()) {
                try {
                    b();
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class s<K, V> extends m<K, V, r<K, V>, s<K, V>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: k */
        public s<K, V> a() {
            return this;
        }

        s(bj<K, V, r<K, V>, s<K, V>> bjVar, int i, int i2) {
            super(bjVar, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class u<K, V> extends m<K, V, t<K, V>, u<K, V>> {
        private final ReferenceQueue<V> queueForValues = new ReferenceQueue<>();

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: k */
        public u<K, V> a() {
            return this;
        }

        u(bj<K, V, t<K, V>, u<K, V>> bjVar, int i, int i2) {
            super(bjVar, i, i2);
        }

        @Override // com.google.common.collect.bj.m
        void b() {
            b(this.queueForValues);
        }

        @Override // com.google.common.collect.bj.m
        void c() {
            c((ReferenceQueue<V>) this.queueForValues);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class q<K> extends m<K, MapMaker.a, p<K>, q<K>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: k */
        public q<K> a() {
            return this;
        }

        q(bj<K, MapMaker.a, p<K>, q<K>> bjVar, int i, int i2) {
            super(bjVar, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class ab<K, V> extends m<K, V, aa<K, V>, ab<K, V>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue<>();

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: k */
        public ab<K, V> a() {
            return this;
        }

        ab(bj<K, V, aa<K, V>, ab<K, V>> bjVar, int i, int i2) {
            super(bjVar, i, i2);
        }

        @Override // com.google.common.collect.bj.m
        void b() {
            a(this.queueForKeys);
        }

        @Override // com.google.common.collect.bj.m
        void c() {
            c((ReferenceQueue<K>) this.queueForKeys);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class ad<K, V> extends m<K, V, ac<K, V>, ad<K, V>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue<>();
        private final ReferenceQueue<V> queueForValues = new ReferenceQueue<>();

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: k */
        public ad<K, V> a() {
            return this;
        }

        ad(bj<K, V, ac<K, V>, ad<K, V>> bjVar, int i, int i2) {
            super(bjVar, i, i2);
        }

        @Override // com.google.common.collect.bj.m
        void b() {
            a(this.queueForKeys);
            b(this.queueForValues);
        }

        @Override // com.google.common.collect.bj.m
        void c() {
            c((ReferenceQueue<K>) this.queueForKeys);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public static final class z<K> extends m<K, MapMaker.a, y<K>, z<K>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue<>();

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: k */
        public z<K> a() {
            return this;
        }

        z(bj<K, MapMaker.a, y<K>, z<K>> bjVar, int i, int i2) {
            super(bjVar, i, i2);
        }

        @Override // com.google.common.collect.bj.m
        void b() {
            a(this.queueForKeys);
        }

        @Override // com.google.common.collect.bj.m
        void c() {
            c((ReferenceQueue<K>) this.queueForKeys);
        }
    }

    @VisibleForTesting
    Equivalence<Object> b() {
        return this.d.b().a();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        m<K, V, E, S>[] mVarArr = this.c;
        long j2 = 0;
        for (int i2 = 0; i2 < mVarArr.length; i2++) {
            if (mVarArr[i2].count != 0) {
                return false;
            }
            j2 += mVarArr[i2].modCount;
        }
        if (j2 == 0) {
            return true;
        }
        for (int i3 = 0; i3 < mVarArr.length; i3++) {
            if (mVarArr[i3].count != 0) {
                return false;
            }
            j2 -= mVarArr[i3].modCount;
        }
        return j2 == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        long j2 = 0;
        for (m<K, V, E, S> mVar : this.c) {
            j2 += mVar.count;
        }
        return Ints.saturatedCast(j2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).c(obj, a2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public E b(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).a(obj, a2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).d(obj, a2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v1, types: [int] */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.google.common.collect.bj$m<K, V, E extends com.google.common.collect.bj$h<K, V, E>, S extends com.google.common.collect.bj$m<K, V, E, S>>[]] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.google.common.collect.bj$m] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // java.util.AbstractMap, java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean containsValue(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r17) {
        /*
            r16 = this;
            r0 = r17
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r2 = r16
            com.google.common.collect.bj$m<K, V, E extends com.google.common.collect.bj$h<K, V, E>, S extends com.google.common.collect.bj$m<K, V, E, S>>[] r3 = r2.c
            r4 = -1
            r5 = r4
            r4 = r1
        L_0x000e:
            r7 = 3
            if (r4 >= r7) goto L_0x005c
            r7 = 0
            int r9 = r3.length
            r10 = r7
            r7 = r1
        L_0x0016:
            if (r7 >= r9) goto L_0x0051
            r8 = r3[r7]
            int r12 = r8.count
            java.util.concurrent.atomic.AtomicReferenceArray<E extends com.google.common.collect.bj$h<K, V, E>> r12 = r8.table
            r13 = r1
        L_0x001f:
            int r14 = r12.length()
            if (r13 >= r14) goto L_0x0049
            java.lang.Object r14 = r12.get(r13)
            com.google.common.collect.bj$h r14 = (com.google.common.collect.bj.h) r14
        L_0x002b:
            if (r14 == 0) goto L_0x0045
            java.lang.Object r15 = r8.b(r14)
            if (r15 == 0) goto L_0x003f
            com.google.common.base.Equivalence r1 = r16.b()
            boolean r1 = r1.equivalent(r0, r15)
            if (r1 == 0) goto L_0x003f
            r0 = 1
            return r0
        L_0x003f:
            com.google.common.collect.bj$h r14 = r14.c()
            r1 = 0
            goto L_0x002b
        L_0x0045:
            int r13 = r13 + 1
            r1 = 0
            goto L_0x001f
        L_0x0049:
            int r1 = r8.modCount
            long r12 = (long) r1
            long r10 = r10 + r12
            int r7 = r7 + 1
            r1 = 0
            goto L_0x0016
        L_0x0051:
            int r1 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x0057
            r0 = 0
            goto L_0x005d
        L_0x0057:
            int r4 = r4 + 1
            r5 = r10
            r1 = 0
            goto L_0x000e
        L_0x005c:
            r0 = r1
        L_0x005d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.bj.containsValue(java.lang.Object):boolean");
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    public V put(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a(k2);
        return b(a2).a((m<K, V, E, S>) k2, a2, (int) v2, false);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public V putIfAbsent(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a(k2);
        return b(a2).a((m<K, V, E, S>) k2, a2, (int) v2, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).e(obj, a2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).b(obj, a2, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public boolean replace(K k2, @NullableDecl V v2, V v3) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v3);
        if (v2 == null) {
            return false;
        }
        int a2 = a(k2);
        return b(a2).a((m<K, V, E, S>) k2, a2, v2, v3);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @CanIgnoreReturnValue
    public V replace(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a(k2);
        return b(a2).a((m<K, V, E, S>) k2, a2, (int) v2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        for (m<K, V, E, S> mVar : this.c) {
            mVar.f();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.f;
        if (set != null) {
            return set;
        }
        k kVar = new k();
        this.f = kVar;
        return kVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.g;
        if (collection != null) {
            return collection;
        }
        x xVar = new x();
        this.g = xVar;
        return xVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.h;
        if (set != null) {
            return set;
        }
        f fVar = new f();
        this.h = fVar;
        return fVar;
    }

    /* JADX WARN: Incorrect field signature: TE; */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    abstract class g<T> implements Iterator<T> {
        int b;
        int c = -1;
        @MonotonicNonNullDecl
        m<K, V, E, S> d;
        @MonotonicNonNullDecl
        AtomicReferenceArray<E> e;
        @NullableDecl
        h f;
        @NullableDecl
        bj<K, V, E, S>.ah g;
        @NullableDecl
        bj<K, V, E, S>.ah h;

        g() {
            this.b = bj.this.c.length - 1;
            b();
        }

        final void b() {
            this.g = null;
            if (!c() && !d()) {
                while (this.b >= 0) {
                    m<K, V, E, S>[] mVarArr = bj.this.c;
                    int i = this.b;
                    this.b = i - 1;
                    this.d = mVarArr[i];
                    if (this.d.count != 0) {
                        this.e = this.d.table;
                        this.c = this.e.length() - 1;
                        if (d()) {
                            return;
                        }
                    }
                }
            }
        }

        boolean c() {
            h hVar = this.f;
            if (hVar == null) {
                return false;
            }
            while (true) {
                this.f = hVar.c();
                h hVar2 = this.f;
                if (hVar2 == null) {
                    return false;
                }
                if (a(hVar2)) {
                    return true;
                }
                hVar = this.f;
            }
        }

        boolean d() {
            while (true) {
                int i = this.c;
                if (i < 0) {
                    return false;
                }
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                this.c = i - 1;
                h hVar = (h) atomicReferenceArray.get(i);
                this.f = hVar;
                if (hVar != null && (a(this.f) || c())) {
                    return true;
                }
            }
        }

        boolean a(E e) {
            m<K, V, E, S> mVar;
            try {
                Object a = e.a();
                Object b = bj.this.b((bj) e);
                if (b != null) {
                    this.g = new ah(a, b);
                    return true;
                }
                return false;
            } finally {
                this.d.g();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.g != null;
        }

        bj<K, V, E, S>.ah e() {
            bj<K, V, E, S>.ah ahVar = this.g;
            if (ahVar != null) {
                this.h = ahVar;
                b();
                return this.h;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            t.a(this.h != null);
            bj.this.remove(this.h.getKey());
            this.h = null;
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    final class j extends bj<K, V, E, S>.g {
        j() {
            super();
        }

        @Override // java.util.Iterator
        public K next() {
            return e().getKey();
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    final class w extends bj<K, V, E, S>.g {
        w() {
            super();
        }

        @Override // java.util.Iterator
        public V next() {
            return e().getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    public final class ah extends f<K, V> {
        final K a;
        V b;

        ah(K k, V v) {
            this.a = k;
            this.b = v;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public K getKey() {
            return this.a;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V getValue() {
            return this.b;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.a.equals(entry.getKey()) && this.b.equals(entry.getValue());
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V setValue(V v) {
            V v2 = (V) bj.this.put(this.a, v);
            this.b = v;
            return v2;
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    final class e extends bj<K, V, E, S>.g {
        e() {
            super();
        }

        /* renamed from: a */
        public Map.Entry<K, V> next() {
            return e();
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    final class k extends l<K> {
        k() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new j();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return bj.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return bj.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return bj.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return bj.this.remove(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            bj.this.clear();
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    final class x extends AbstractCollection<V> {
        x() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new w();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return bj.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return bj.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return bj.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            bj.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return bj.b((Collection) this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) bj.b((Collection) this).toArray(tArr);
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    final class f extends l<Map.Entry<K, V>> {
        f() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new e();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (obj2 = bj.this.get(key)) != null && bj.this.b().equivalent(entry.getValue(), obj2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && bj.this.remove(key, entry.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return bj.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return bj.this.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            bj.this.clear();
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    private static abstract class l<E> extends AbstractSet<E> {
        private l() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return bj.b((Collection) this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) bj.b((Collection) this).toArray(tArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> ArrayList<E> b(Collection<E> collection) {
        ArrayList<E> arrayList = new ArrayList<>(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    Object writeReplace() {
        return new n(this.d.a(), this.d.b(), this.keyEquivalence, this.d.b().a(), this.concurrencyLevel, this);
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    static abstract class a<K, V> extends ForwardingConcurrentMap<K, V> implements Serializable {
        private static final long serialVersionUID = 3;
        transient ConcurrentMap<K, V> a;
        final int concurrencyLevel;
        final Equivalence<Object> keyEquivalence;
        final o keyStrength;
        final Equivalence<Object> valueEquivalence;
        final o valueStrength;

        a(o oVar, o oVar2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int i, ConcurrentMap<K, V> concurrentMap) {
            this.keyStrength = oVar;
            this.valueStrength = oVar2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.concurrencyLevel = i;
            this.a = concurrentMap;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingConcurrentMap, com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public ConcurrentMap<K, V> delegate() {
            return this.a;
        }

        void a(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeInt(this.a.size());
            for (Map.Entry<K, V> entry : this.a.entrySet()) {
                objectOutputStream.writeObject(entry.getKey());
                objectOutputStream.writeObject(entry.getValue());
            }
            objectOutputStream.writeObject(null);
        }

        MapMaker a(ObjectInputStream objectInputStream) throws IOException {
            return new MapMaker().initialCapacity(objectInputStream.readInt()).a(this.keyStrength).b(this.valueStrength).a(this.keyEquivalence).concurrencyLevel(this.concurrencyLevel);
        }

        /* JADX WARN: Multi-variable type inference failed */
        void b(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            while (true) {
                Object readObject = objectInputStream.readObject();
                if (readObject != null) {
                    this.a.put(readObject, objectInputStream.readObject());
                } else {
                    return;
                }
            }
        }
    }

    /* compiled from: MapMakerInternalMap.java */
    /* loaded from: classes2.dex */
    private static final class n<K, V> extends a<K, V> {
        private static final long serialVersionUID = 3;

        n(o oVar, o oVar2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int i, ConcurrentMap<K, V> concurrentMap) {
            super(oVar, oVar2, equivalence, equivalence2, i, concurrentMap);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            a(objectOutputStream);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.a = a(objectInputStream).makeMap();
            b(objectInputStream);
        }

        private Object readResolve() {
            return this.a;
        }
    }
}
