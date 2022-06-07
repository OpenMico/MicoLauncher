package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: LocalCache.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public class a<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final Logger a = Logger.getLogger(a.class.getName());
    static final y<Object, Object> u = new y<Object, Object>() { // from class: com.google.common.cache.a.1
        @Override // com.google.common.cache.a.y
        public int a() {
            return 0;
        }

        @Override // com.google.common.cache.a.y
        public y<Object, Object> a(ReferenceQueue<Object> referenceQueue, @NullableDecl Object obj, e<Object, Object> eVar) {
            return this;
        }

        @Override // com.google.common.cache.a.y
        public void a(Object obj) {
        }

        @Override // com.google.common.cache.a.y
        public e<Object, Object> b() {
            return null;
        }

        @Override // com.google.common.cache.a.y
        public boolean c() {
            return false;
        }

        @Override // com.google.common.cache.a.y
        public boolean d() {
            return false;
        }

        @Override // com.google.common.cache.a.y
        public Object e() {
            return null;
        }

        @Override // com.google.common.cache.a.y
        public Object get() {
            return null;
        }
    };
    static final Queue<?> v = new AbstractQueue<Object>() { // from class: com.google.common.cache.a.2
        @Override // java.util.Queue
        public boolean offer(Object obj) {
            return true;
        }

        @Override // java.util.Queue
        public Object peek() {
            return null;
        }

        @Override // java.util.Queue
        public Object poll() {
            return null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return ImmutableSet.of().iterator();
        }
    };
    final int b;
    final int c;
    final p<K, V>[] d;
    final int e;
    final Equivalence<Object> f;
    final Equivalence<Object> g;
    final r h;
    final r i;
    final long j;
    final Weigher<K, V> k;
    final long l;
    final long m;
    final long n;
    final Queue<RemovalNotification<K, V>> o;
    final RemovalListener<K, V> p;
    final Ticker q;
    final d r;
    final AbstractCache.StatsCounter s;
    @NullableDecl
    final CacheLoader<? super K, V> t;
    @MonotonicNonNullDecl
    Set<K> w;
    @MonotonicNonNullDecl
    Collection<V> x;
    @MonotonicNonNullDecl
    Set<Map.Entry<K, V>> y;

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public enum o implements e<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.e
        public y<Object, Object> a() {
            return null;
        }

        @Override // com.google.common.cache.e
        public void a(long j) {
        }

        @Override // com.google.common.cache.e
        public void a(y<Object, Object> yVar) {
        }

        @Override // com.google.common.cache.e
        public void a(e<Object, Object> eVar) {
        }

        @Override // com.google.common.cache.e
        public e<Object, Object> b() {
            return null;
        }

        @Override // com.google.common.cache.e
        public void b(long j) {
        }

        @Override // com.google.common.cache.e
        public void b(e<Object, Object> eVar) {
        }

        @Override // com.google.common.cache.e
        public int c() {
            return 0;
        }

        @Override // com.google.common.cache.e
        public void c(e<Object, Object> eVar) {
        }

        @Override // com.google.common.cache.e
        public Object d() {
            return null;
        }

        @Override // com.google.common.cache.e
        public void d(e<Object, Object> eVar) {
        }

        @Override // com.google.common.cache.e
        public long e() {
            return 0L;
        }

        @Override // com.google.common.cache.e
        public e<Object, Object> f() {
            return this;
        }

        @Override // com.google.common.cache.e
        public e<Object, Object> g() {
            return this;
        }

        @Override // com.google.common.cache.e
        public long h() {
            return 0L;
        }

        @Override // com.google.common.cache.e
        public e<Object, Object> i() {
            return this;
        }

        @Override // com.google.common.cache.e
        public e<Object, Object> j() {
            return this;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static abstract class r extends Enum<r> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract Equivalence<Object> a();

        abstract <K, V> y<K, V> a(p<K, V> pVar, e<K, V> eVar, V v, int i);
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public interface y<K, V> {
        int a();

        y<K, V> a(ReferenceQueue<V> referenceQueue, @NullableDecl V v, e<K, V> eVar);

        void a(@NullableDecl V v);

        @NullableDecl
        e<K, V> b();

        boolean c();

        boolean d();

        V e() throws ExecutionException;

        @NullableDecl
        V get();
    }

    static int a(int i2) {
        int i3 = i2 + ((i2 << 15) ^ (-12931));
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (i6 << 2) + (i6 << 14);
        return i7 ^ (i7 >>> 16);
    }

    a(CacheBuilder<? super K, ? super V> cacheBuilder, @NullableDecl CacheLoader<? super K, V> cacheLoader) {
        this.e = Math.min(cacheBuilder.e(), 65536);
        this.h = cacheBuilder.h();
        this.i = cacheBuilder.i();
        this.f = cacheBuilder.b();
        this.g = cacheBuilder.c();
        this.j = cacheBuilder.f();
        this.k = (Weigher<K, V>) cacheBuilder.g();
        this.l = cacheBuilder.k();
        this.m = cacheBuilder.j();
        this.n = cacheBuilder.l();
        this.p = (RemovalListener<K, V>) cacheBuilder.m();
        this.o = this.p == CacheBuilder.a.INSTANCE ? q() : new ConcurrentLinkedQueue<>();
        this.q = cacheBuilder.a(j());
        this.r = d.a(this.h, l(), k());
        this.s = (AbstractCache.StatsCounter) cacheBuilder.n().get();
        this.t = cacheLoader;
        int min = Math.min(cacheBuilder.d(), 1073741824);
        if (a() && !b()) {
            min = Math.min(min, (int) this.j);
        }
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        int i5 = 1;
        while (i5 < this.e && (!a() || i5 * 20 <= this.j)) {
            i4++;
            i5 <<= 1;
        }
        this.c = 32 - i4;
        this.b = i5 - 1;
        this.d = c(i5);
        int i6 = min / i5;
        while (i3 < (i6 * i5 < min ? i6 + 1 : i6)) {
            i3 <<= 1;
        }
        if (a()) {
            long j2 = this.j;
            long j3 = i5;
            long j4 = (j2 / j3) + 1;
            long j5 = j2 % j3;
            while (i2 < this.d.length) {
                if (i2 == j5) {
                    j4--;
                }
                this.d[i2] = a(i3, j4, (AbstractCache.StatsCounter) cacheBuilder.n().get());
                i2++;
            }
            return;
        }
        while (true) {
            p<K, V>[] pVarArr = this.d;
            if (i2 < pVarArr.length) {
                pVarArr[i2] = a(i3, -1L, (AbstractCache.StatsCounter) cacheBuilder.n().get());
                i2++;
            } else {
                return;
            }
        }
    }

    boolean a() {
        return this.j >= 0;
    }

    boolean b() {
        return this.k != CacheBuilder.b.INSTANCE;
    }

    boolean c() {
        return this.m > 0;
    }

    boolean d() {
        return this.l > 0;
    }

    boolean e() {
        return this.n > 0;
    }

    boolean f() {
        return d() || a();
    }

    boolean g() {
        return c();
    }

    boolean h() {
        return c() || e();
    }

    boolean i() {
        return d();
    }

    boolean j() {
        return h() || i();
    }

    boolean k() {
        return g() || h();
    }

    boolean l() {
        return f() || i();
    }

    boolean m() {
        return this.h != r.STRONG;
    }

    boolean n() {
        return this.i != r.STRONG;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static abstract class d extends Enum<d> {
        static final d[] i = {STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};

        abstract <K, V> e<K, V> a(p<K, V> pVar, K k, int i2, @NullableDecl e<K, V> eVar);

        /* JADX WARN: Multi-variable type inference failed */
        static d a(r rVar, boolean z, boolean z2) {
            int i2 = 0;
            int i3 = (rVar == r.WEAK ? 4 : 0) | (z ? 1 : 0);
            if (z2) {
                i2 = 2;
            }
            return i[i3 | i2];
        }

        <K, V> e<K, V> a(p<K, V> pVar, e<K, V> eVar, e<K, V> eVar2) {
            return a(pVar, eVar.d(), eVar.c(), eVar2);
        }

        <K, V> void a(e<K, V> eVar, e<K, V> eVar2) {
            eVar2.a(eVar.e());
            a.a(eVar.g(), eVar2);
            a.a(eVar2, eVar.f());
            a.b((e) eVar);
        }

        <K, V> void b(e<K, V> eVar, e<K, V> eVar2) {
            eVar2.b(eVar.h());
            a.b(eVar.j(), eVar2);
            a.b(eVar2, eVar.i());
            a.c((e) eVar);
        }
    }

    static <K, V> y<K, V> o() {
        return (y<K, V>) u;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static abstract class b<K, V> implements e<K, V> {
        b() {
        }

        @Override // com.google.common.cache.e
        public y<K, V> a() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public void a(y<K, V> yVar) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public e<K, V> b() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public int c() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public K d() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public long e() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public void a(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public e<K, V> f() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public void a(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public e<K, V> g() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public void b(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public long h() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public void b(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public e<K, V> i() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public void c(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public e<K, V> j() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public void d(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }
    }

    static <K, V> e<K, V> p() {
        return o.INSTANCE;
    }

    static <E> Queue<E> q() {
        return (Queue<E>) v;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class u<K, V> extends b<K, V> {
        final K g;
        final int h;
        @NullableDecl
        final e<K, V> i;
        volatile y<K, V> j = a.o();

        u(K k, int i, @NullableDecl e<K, V> eVar) {
            this.g = k;
            this.h = i;
            this.i = eVar;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public K d() {
            return this.g;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public y<K, V> a() {
            return this.j;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void a(y<K, V> yVar) {
            this.j = yVar;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public int c() {
            return this.h;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> b() {
            return this.i;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class s<K, V> extends u<K, V> {
        volatile long a = Long.MAX_VALUE;
        e<K, V> b = a.p();
        e<K, V> c = a.p();

        s(K k, int i, @NullableDecl e<K, V> eVar) {
            super(k, i, eVar);
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public long e() {
            return this.a;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void a(long j) {
            this.a = j;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> f() {
            return this.b;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void a(e<K, V> eVar) {
            this.b = eVar;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> g() {
            return this.c;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void b(e<K, V> eVar) {
            this.c = eVar;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class w<K, V> extends u<K, V> {
        volatile long a = Long.MAX_VALUE;
        e<K, V> b = a.p();
        e<K, V> c = a.p();

        w(K k, int i, @NullableDecl e<K, V> eVar) {
            super(k, i, eVar);
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public long h() {
            return this.a;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void b(long j) {
            this.a = j;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> i() {
            return this.b;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void c(e<K, V> eVar) {
            this.b = eVar;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> j() {
            return this.c;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void d(e<K, V> eVar) {
            this.c = eVar;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class t<K, V> extends u<K, V> {
        volatile long a = Long.MAX_VALUE;
        e<K, V> b = a.p();
        e<K, V> c = a.p();
        volatile long d = Long.MAX_VALUE;
        e<K, V> e = a.p();
        e<K, V> f = a.p();

        t(K k, int i, @NullableDecl e<K, V> eVar) {
            super(k, i, eVar);
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public long e() {
            return this.a;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void a(long j) {
            this.a = j;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> f() {
            return this.b;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void a(e<K, V> eVar) {
            this.b = eVar;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> g() {
            return this.c;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void b(e<K, V> eVar) {
            this.c = eVar;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public long h() {
            return this.d;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void b(long j) {
            this.d = j;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> i() {
            return this.e;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void c(e<K, V> eVar) {
            this.e = eVar;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public e<K, V> j() {
            return this.f;
        }

        @Override // com.google.common.cache.a.b, com.google.common.cache.e
        public void d(e<K, V> eVar) {
            this.f = eVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class ac<K, V> extends WeakReference<K> implements e<K, V> {
        final int g;
        @NullableDecl
        final e<K, V> h;
        volatile y<K, V> i = a.o();

        ac(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl e<K, V> eVar) {
            super(k, referenceQueue);
            this.g = i;
            this.h = eVar;
        }

        @Override // com.google.common.cache.e
        public K d() {
            return (K) get();
        }

        public long e() {
            throw new UnsupportedOperationException();
        }

        public void a(long j) {
            throw new UnsupportedOperationException();
        }

        public e<K, V> f() {
            throw new UnsupportedOperationException();
        }

        public void a(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }

        public e<K, V> g() {
            throw new UnsupportedOperationException();
        }

        public void b(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }

        public long h() {
            throw new UnsupportedOperationException();
        }

        public void b(long j) {
            throw new UnsupportedOperationException();
        }

        public e<K, V> i() {
            throw new UnsupportedOperationException();
        }

        public void c(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }

        public e<K, V> j() {
            throw new UnsupportedOperationException();
        }

        public void d(e<K, V> eVar) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.cache.e
        public y<K, V> a() {
            return this.i;
        }

        @Override // com.google.common.cache.e
        public void a(y<K, V> yVar) {
            this.i = yVar;
        }

        @Override // com.google.common.cache.e
        public int c() {
            return this.g;
        }

        @Override // com.google.common.cache.e
        public e<K, V> b() {
            return this.h;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class aa<K, V> extends ac<K, V> {
        volatile long a = Long.MAX_VALUE;
        e<K, V> b = a.p();
        e<K, V> c = a.p();

        aa(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl e<K, V> eVar) {
            super(referenceQueue, k, i, eVar);
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public long e() {
            return this.a;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void a(long j) {
            this.a = j;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> f() {
            return this.b;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void a(e<K, V> eVar) {
            this.b = eVar;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> g() {
            return this.c;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void b(e<K, V> eVar) {
            this.c = eVar;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class ae<K, V> extends ac<K, V> {
        volatile long a = Long.MAX_VALUE;
        e<K, V> b = a.p();
        e<K, V> c = a.p();

        ae(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl e<K, V> eVar) {
            super(referenceQueue, k, i, eVar);
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public long h() {
            return this.a;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void b(long j) {
            this.a = j;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> i() {
            return this.b;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void c(e<K, V> eVar) {
            this.b = eVar;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> j() {
            return this.c;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void d(e<K, V> eVar) {
            this.c = eVar;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class ab<K, V> extends ac<K, V> {
        volatile long a = Long.MAX_VALUE;
        e<K, V> b = a.p();
        e<K, V> c = a.p();
        volatile long d = Long.MAX_VALUE;
        e<K, V> e = a.p();
        e<K, V> f = a.p();

        ab(ReferenceQueue<K> referenceQueue, K k, int i, @NullableDecl e<K, V> eVar) {
            super(referenceQueue, k, i, eVar);
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public long e() {
            return this.a;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void a(long j) {
            this.a = j;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> f() {
            return this.b;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void a(e<K, V> eVar) {
            this.b = eVar;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> g() {
            return this.c;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void b(e<K, V> eVar) {
            this.c = eVar;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public long h() {
            return this.d;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void b(long j) {
            this.d = j;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> i() {
            return this.e;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void c(e<K, V> eVar) {
            this.e = eVar;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public e<K, V> j() {
            return this.f;
        }

        @Override // com.google.common.cache.a.ac, com.google.common.cache.e
        public void d(e<K, V> eVar) {
            this.f = eVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class ad<K, V> extends WeakReference<V> implements y<K, V> {
        final e<K, V> a;

        @Override // com.google.common.cache.a.y
        public int a() {
            return 1;
        }

        @Override // com.google.common.cache.a.y
        public void a(V v) {
        }

        @Override // com.google.common.cache.a.y
        public boolean c() {
            return false;
        }

        @Override // com.google.common.cache.a.y
        public boolean d() {
            return true;
        }

        ad(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar) {
            super(v, referenceQueue);
            this.a = eVar;
        }

        @Override // com.google.common.cache.a.y
        public e<K, V> b() {
            return this.a;
        }

        @Override // com.google.common.cache.a.y
        public y<K, V> a(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar) {
            return new ad(referenceQueue, v, eVar);
        }

        @Override // com.google.common.cache.a.y
        public V e() {
            return get();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class q<K, V> extends SoftReference<V> implements y<K, V> {
        final e<K, V> a;

        public int a() {
            return 1;
        }

        @Override // com.google.common.cache.a.y
        public void a(V v) {
        }

        @Override // com.google.common.cache.a.y
        public boolean c() {
            return false;
        }

        @Override // com.google.common.cache.a.y
        public boolean d() {
            return true;
        }

        q(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar) {
            super(v, referenceQueue);
            this.a = eVar;
        }

        @Override // com.google.common.cache.a.y
        public e<K, V> b() {
            return this.a;
        }

        public y<K, V> a(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar) {
            return new q(referenceQueue, v, eVar);
        }

        @Override // com.google.common.cache.a.y
        public V e() {
            return get();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class v<K, V> implements y<K, V> {
        final V a;

        @Override // com.google.common.cache.a.y
        public int a() {
            return 1;
        }

        @Override // com.google.common.cache.a.y
        public y<K, V> a(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar) {
            return this;
        }

        @Override // com.google.common.cache.a.y
        public void a(V v) {
        }

        @Override // com.google.common.cache.a.y
        public e<K, V> b() {
            return null;
        }

        @Override // com.google.common.cache.a.y
        public boolean c() {
            return false;
        }

        @Override // com.google.common.cache.a.y
        public boolean d() {
            return true;
        }

        v(V v) {
            this.a = v;
        }

        @Override // com.google.common.cache.a.y
        public V get() {
            return this.a;
        }

        @Override // com.google.common.cache.a.y
        public V e() {
            return get();
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class ah<K, V> extends ad<K, V> {
        final int b;

        ah(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar, int i) {
            super(referenceQueue, v, eVar);
            this.b = i;
        }

        @Override // com.google.common.cache.a.ad, com.google.common.cache.a.y
        public int a() {
            return this.b;
        }

        @Override // com.google.common.cache.a.ad, com.google.common.cache.a.y
        public y<K, V> a(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar) {
            return new ah(referenceQueue, v, eVar, this.b);
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class af<K, V> extends q<K, V> {
        final int b;

        af(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar, int i) {
            super(referenceQueue, v, eVar);
            this.b = i;
        }

        @Override // com.google.common.cache.a.q, com.google.common.cache.a.y
        public int a() {
            return this.b;
        }

        @Override // com.google.common.cache.a.q, com.google.common.cache.a.y
        public y<K, V> a(ReferenceQueue<V> referenceQueue, V v, e<K, V> eVar) {
            return new af(referenceQueue, v, eVar, this.b);
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class ag<K, V> extends v<K, V> {
        final int b;

        ag(V v, int i) {
            super(v);
            this.b = i;
        }

        @Override // com.google.common.cache.a.v, com.google.common.cache.a.y
        public int a() {
            return this.b;
        }
    }

    int a(@NullableDecl Object obj) {
        return a(this.f.hash(obj));
    }

    void a(y<K, V> yVar) {
        e<K, V> b2 = yVar.b();
        int c2 = b2.c();
        b(c2).a((p<K, V>) b2.d(), c2, (y<p<K, V>, V>) yVar);
    }

    void a(e<K, V> eVar) {
        int c2 = eVar.c();
        b(c2).a((e) eVar, c2);
    }

    p<K, V> b(int i2) {
        return this.d[(i2 >>> this.c) & this.b];
    }

    p<K, V> a(int i2, long j2, AbstractCache.StatsCounter statsCounter) {
        return new p<>(this, i2, j2, statsCounter);
    }

    @NullableDecl
    V a(e<K, V> eVar, long j2) {
        V v2;
        if (eVar.d() == null || (v2 = eVar.a().get()) == null || b(eVar, j2)) {
            return null;
        }
        return v2;
    }

    boolean b(e<K, V> eVar, long j2) {
        Preconditions.checkNotNull(eVar);
        if (!d() || j2 - eVar.e() < this.l) {
            return c() && j2 - eVar.h() >= this.m;
        }
        return true;
    }

    static <K, V> void a(e<K, V> eVar, e<K, V> eVar2) {
        eVar.a(eVar2);
        eVar2.b(eVar);
    }

    static <K, V> void b(e<K, V> eVar) {
        e<K, V> p2 = p();
        eVar.a(p2);
        eVar.b(p2);
    }

    static <K, V> void b(e<K, V> eVar, e<K, V> eVar2) {
        eVar.c(eVar2);
        eVar2.d(eVar);
    }

    static <K, V> void c(e<K, V> eVar) {
        e<K, V> p2 = p();
        eVar.c(p2);
        eVar.d(p2);
    }

    void r() {
        while (true) {
            RemovalNotification<K, V> poll = this.o.poll();
            if (poll != null) {
                try {
                    this.p.onRemoval(poll);
                } catch (Throwable th) {
                    a.log(Level.WARNING, "Exception thrown by removal listener", th);
                }
            } else {
                return;
            }
        }
    }

    final p<K, V>[] c(int i2) {
        return new p[i2];
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class p<K, V> extends ReentrantLock {
        @GuardedBy("this")
        final Queue<e<K, V>> accessQueue;
        volatile int count;
        @NullableDecl
        final ReferenceQueue<K> keyReferenceQueue;
        @Weak
        final a<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<e<K, V>> recencyQueue;
        final AbstractCache.StatsCounter statsCounter;
        @MonotonicNonNullDecl
        volatile AtomicReferenceArray<e<K, V>> table;
        int threshold;
        @GuardedBy("this")
        long totalWeight;
        @NullableDecl
        final ReferenceQueue<V> valueReferenceQueue;
        @GuardedBy("this")
        final Queue<e<K, V>> writeQueue;

        p(a<K, V> aVar, int i, long j, AbstractCache.StatsCounter statsCounter) {
            this.map = aVar;
            this.maxSegmentWeight = j;
            this.statsCounter = (AbstractCache.StatsCounter) Preconditions.checkNotNull(statsCounter);
            a(a(i));
            ReferenceQueue<V> referenceQueue = null;
            this.keyReferenceQueue = aVar.m() ? new ReferenceQueue<>() : null;
            this.valueReferenceQueue = aVar.n() ? new ReferenceQueue<>() : referenceQueue;
            this.recencyQueue = aVar.f() ? new ConcurrentLinkedQueue<>() : a.q();
            this.writeQueue = aVar.g() ? new ai<>() : a.q();
            this.accessQueue = aVar.f() ? new c<>() : a.q();
        }

        AtomicReferenceArray<e<K, V>> a(int i) {
            return new AtomicReferenceArray<>(i);
        }

        void a(AtomicReferenceArray<e<K, V>> atomicReferenceArray) {
            this.threshold = (atomicReferenceArray.length() * 3) / 4;
            if (!this.map.b()) {
                int i = this.threshold;
                if (i == this.maxSegmentWeight) {
                    this.threshold = i + 1;
                }
            }
            this.table = atomicReferenceArray;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @GuardedBy("this")
        e<K, V> a(K k, int i, @NullableDecl e<K, V> eVar) {
            return this.map.r.a(this, Preconditions.checkNotNull(k), i, eVar);
        }

        @GuardedBy("this")
        e<K, V> a(e<K, V> eVar, e<K, V> eVar2) {
            if (eVar.d() == null) {
                return null;
            }
            y<K, V> a = eVar.a();
            V v = a.get();
            if (v == null && a.d()) {
                return null;
            }
            e<K, V> a2 = this.map.r.a(this, eVar, eVar2);
            a2.a(a.a(this.valueReferenceQueue, v, a2));
            return a2;
        }

        @GuardedBy("this")
        void a(e<K, V> eVar, K k, V v, long j) {
            y<K, V> a = eVar.a();
            int weigh = this.map.k.weigh(k, v);
            Preconditions.checkState(weigh >= 0, "Weights must be non-negative");
            eVar.a(this.map.i.a(this, eVar, v, weigh));
            a((e) eVar, weigh, j);
            a.a(v);
        }

        V a(K k, int i, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            e<K, V> b;
            try {
                Preconditions.checkNotNull(k);
                Preconditions.checkNotNull(cacheLoader);
                try {
                    if (!(this.count == 0 || (b = b(k, i)) == null)) {
                        long read = this.map.q.read();
                        V c = c(b, read);
                        if (c != null) {
                            a(b, read);
                            this.statsCounter.recordHits(1);
                            return a(b, k, i, c, read, cacheLoader);
                        }
                        y<K, V> a = b.a();
                        if (a.c()) {
                            return a((e<e<K, V>, V>) b, (e<K, V>) k, (y<e<K, V>, V>) a);
                        }
                    }
                    return b((p<K, V>) k, i, (CacheLoader<? super p<K, V>, V>) cacheLoader);
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof Error) {
                        throw new ExecutionError((Error) cause);
                    } else if (cause instanceof RuntimeException) {
                        throw new UncheckedExecutionException(cause);
                    } else {
                        throw e;
                    }
                }
            } finally {
                l();
            }
        }

        @NullableDecl
        V a(Object obj, int i) {
            try {
                if (this.count != 0) {
                    long read = this.map.q.read();
                    e<K, V> a = a(obj, i, read);
                    if (a == null) {
                        return null;
                    }
                    V v = a.a().get();
                    if (v != null) {
                        a(a, read);
                        return a(a, a.d(), i, v, read, this.map.t);
                    }
                    a();
                }
                return null;
            } finally {
                l();
            }
        }

        V b(K k, int i, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            k<K, V> kVar;
            boolean z;
            y<K, V> yVar;
            V a;
            lock();
            try {
                long read = this.map.q.read();
                c(read);
                int i2 = this.count - 1;
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                e<K, V> eVar = atomicReferenceArray.get(length);
                e<K, V> eVar2 = eVar;
                while (true) {
                    kVar = null;
                    if (eVar2 == null) {
                        z = true;
                        yVar = null;
                        break;
                    }
                    K d = eVar2.d();
                    if (eVar2.c() != i || d == null || !this.map.f.equivalent(k, d)) {
                        eVar2 = eVar2.b();
                    } else {
                        y<K, V> a2 = eVar2.a();
                        if (a2.c()) {
                            z = false;
                            yVar = a2;
                        } else {
                            V v = a2.get();
                            if (v == null) {
                                a(d, i, v, a2.a(), RemovalCause.COLLECTED);
                            } else if (this.map.b(eVar2, read)) {
                                a(d, i, v, a2.a(), RemovalCause.EXPIRED);
                            } else {
                                b(eVar2, read);
                                this.statsCounter.recordHits(1);
                                return v;
                            }
                            this.writeQueue.remove(eVar2);
                            this.accessQueue.remove(eVar2);
                            this.count = i2;
                            z = true;
                            yVar = a2;
                        }
                    }
                }
                if (z) {
                    kVar = new k<>();
                    if (eVar2 == null) {
                        eVar2 = a((p<K, V>) k, i, (e<p<K, V>, V>) eVar);
                        eVar2.a(kVar);
                        atomicReferenceArray.set(length, eVar2);
                    } else {
                        eVar2.a(kVar);
                    }
                }
                if (!z) {
                    return a((e<e<K, V>, V>) eVar2, (e<K, V>) k, (y<e<K, V>, V>) yVar);
                }
                try {
                    synchronized (eVar2) {
                        a = a((p<K, V>) k, i, (k<p<K, V>, V>) kVar, (CacheLoader<? super p<K, V>, V>) cacheLoader);
                    }
                    return a;
                } finally {
                    this.statsCounter.recordMisses(1);
                }
            } finally {
                unlock();
                m();
            }
        }

        V a(e<K, V> eVar, K k, y<K, V> yVar) throws ExecutionException {
            if (yVar.c()) {
                Preconditions.checkState(!Thread.holdsLock(eVar), "Recursive load of: %s", k);
                try {
                    V e = yVar.e();
                    if (e != null) {
                        a(eVar, this.map.q.read());
                        return e;
                    }
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k + ".");
                } finally {
                    this.statsCounter.recordMisses(1);
                }
            } else {
                throw new AssertionError();
            }
        }

        V a(K k, int i, k<K, V> kVar, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            return a((p<K, V>) k, i, (k<p<K, V>, V>) kVar, (ListenableFuture) kVar.a(k, cacheLoader));
        }

        ListenableFuture<V> b(final K k, final int i, final k<K, V> kVar, CacheLoader<? super K, V> cacheLoader) {
            final ListenableFuture<V> a = kVar.a(k, cacheLoader);
            a.addListener(new Runnable() { // from class: com.google.common.cache.a.p.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        p.this.a((p) k, i, (k<p, V>) kVar, a);
                    } catch (Throwable th) {
                        a.a.log(Level.WARNING, "Exception thrown during refresh", th);
                        kVar.a(th);
                    }
                }
            }, MoreExecutors.directExecutor());
            return a;
        }

        V a(K k, int i, k<K, V> kVar, ListenableFuture<V> listenableFuture) throws ExecutionException {
            V v;
            Throwable th;
            try {
                v = (V) Uninterruptibles.getUninterruptibly(listenableFuture);
                try {
                    if (v != null) {
                        this.statsCounter.recordLoadSuccess(kVar.f());
                        a((p<K, V>) k, i, (k<p<K, V>, k<K, V>>) kVar, (k<K, V>) v);
                        if (v == null) {
                            this.statsCounter.recordLoadException(kVar.f());
                            a((p<K, V>) k, i, (k<p<K, V>, V>) kVar);
                        }
                        return v;
                    }
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + k + ".");
                } catch (Throwable th2) {
                    th = th2;
                    if (v == null) {
                        this.statsCounter.recordLoadException(kVar.f());
                        a((p<K, V>) k, i, (k<p<K, V>, V>) kVar);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                v = null;
            }
        }

        V a(e<K, V> eVar, K k, int i, V v, long j, CacheLoader<? super K, V> cacheLoader) {
            V a;
            return (!this.map.e() || j - eVar.h() <= this.map.n || eVar.a().c() || (a = a((p<K, V>) k, i, (CacheLoader<? super p<K, V>, V>) cacheLoader, true)) == null) ? v : a;
        }

        @NullableDecl
        V a(K k, int i, CacheLoader<? super K, V> cacheLoader, boolean z) {
            k<K, V> a = a((p<K, V>) k, i, z);
            if (a == null) {
                return null;
            }
            ListenableFuture<V> b = b(k, i, a, cacheLoader);
            if (b.isDone()) {
                try {
                    return (V) Uninterruptibles.getUninterruptibly(b);
                } catch (Throwable unused) {
                }
            }
            return null;
        }

        @NullableDecl
        k<K, V> a(K k, int i, boolean z) {
            lock();
            try {
                long read = this.map.q.read();
                c(read);
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null && this.map.f.equivalent(k, d)) {
                        y<K, V> a = eVar2.a();
                        if (!a.c() && (!z || read - eVar2.h() >= this.map.n)) {
                            this.modCount++;
                            k<K, V> kVar = new k<>(a);
                            eVar2.a(kVar);
                            return kVar;
                        }
                        return null;
                    }
                }
                this.modCount++;
                k<K, V> kVar2 = new k<>();
                e<K, V> a2 = a((p<K, V>) k, i, (e<p<K, V>, V>) eVar);
                a2.a(kVar2);
                atomicReferenceArray.set(length, a2);
                return kVar2;
            } finally {
                unlock();
                m();
            }
        }

        void a() {
            if (tryLock()) {
                try {
                    b();
                } finally {
                    unlock();
                }
            }
        }

        @GuardedBy("this")
        void b() {
            if (this.map.m()) {
                c();
            }
            if (this.map.n()) {
                d();
            }
        }

        @GuardedBy("this")
        void c() {
            int i = 0;
            do {
                Reference<? extends K> poll = this.keyReferenceQueue.poll();
                if (poll != null) {
                    this.map.a((e) ((e) poll));
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        @GuardedBy("this")
        void d() {
            int i = 0;
            do {
                Reference<? extends V> poll = this.valueReferenceQueue.poll();
                if (poll != null) {
                    this.map.a((y) ((y) poll));
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        void e() {
            if (this.map.m()) {
                f();
            }
            if (this.map.n()) {
                g();
            }
        }

        void f() {
            do {
            } while (this.keyReferenceQueue.poll() != null);
        }

        void g() {
            do {
            } while (this.valueReferenceQueue.poll() != null);
        }

        void a(e<K, V> eVar, long j) {
            if (this.map.i()) {
                eVar.a(j);
            }
            this.recencyQueue.add(eVar);
        }

        @GuardedBy("this")
        void b(e<K, V> eVar, long j) {
            if (this.map.i()) {
                eVar.a(j);
            }
            this.accessQueue.add(eVar);
        }

        @GuardedBy("this")
        void a(e<K, V> eVar, int i, long j) {
            h();
            this.totalWeight += i;
            if (this.map.i()) {
                eVar.a(j);
            }
            if (this.map.h()) {
                eVar.b(j);
            }
            this.accessQueue.add(eVar);
            this.writeQueue.add(eVar);
        }

        @GuardedBy("this")
        void h() {
            while (true) {
                e<K, V> poll = this.recencyQueue.poll();
                if (poll == null) {
                    return;
                }
                if (this.accessQueue.contains(poll)) {
                    this.accessQueue.add(poll);
                }
            }
        }

        void a(long j) {
            if (tryLock()) {
                try {
                    b(j);
                } finally {
                    unlock();
                }
            }
        }

        @GuardedBy("this")
        void b(long j) {
            e<K, V> peek;
            e<K, V> peek2;
            h();
            do {
                peek = this.writeQueue.peek();
                if (peek == null || !this.map.b(peek, j)) {
                    do {
                        peek2 = this.accessQueue.peek();
                        if (peek2 == null || !this.map.b(peek2, j)) {
                            return;
                        }
                    } while (a((e) peek2, peek2.c(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (a((e) peek, peek.c(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        @GuardedBy("this")
        void a(@NullableDecl K k, int i, @NullableDecl V v, int i2, RemovalCause removalCause) {
            this.totalWeight -= i2;
            if (removalCause.a()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.o != a.v) {
                this.map.o.offer(RemovalNotification.create(k, v, removalCause));
            }
        }

        @GuardedBy("this")
        void a(e<K, V> eVar) {
            if (this.map.a()) {
                h();
                if (eVar.a().a() <= this.maxSegmentWeight || a((e) eVar, eVar.c(), RemovalCause.SIZE)) {
                    while (this.totalWeight > this.maxSegmentWeight) {
                        e<K, V> i = i();
                        if (!a((e) i, i.c(), RemovalCause.SIZE)) {
                            throw new AssertionError();
                        }
                    }
                    return;
                }
                throw new AssertionError();
            }
        }

        @GuardedBy("this")
        e<K, V> i() {
            for (e<K, V> eVar : this.accessQueue) {
                if (eVar.a().a() > 0) {
                    return eVar;
                }
            }
            throw new AssertionError();
        }

        e<K, V> b(int i) {
            AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
            return atomicReferenceArray.get(i & (atomicReferenceArray.length() - 1));
        }

        @NullableDecl
        e<K, V> b(Object obj, int i) {
            for (e<K, V> b = b(i); b != null; b = b.b()) {
                if (b.c() == i) {
                    K d = b.d();
                    if (d == null) {
                        a();
                    } else if (this.map.f.equivalent(obj, d)) {
                        return b;
                    }
                }
            }
            return null;
        }

        @NullableDecl
        e<K, V> a(Object obj, int i, long j) {
            e<K, V> b = b(obj, i);
            if (b == null) {
                return null;
            }
            if (!this.map.b(b, j)) {
                return b;
            }
            a(j);
            return null;
        }

        V c(e<K, V> eVar, long j) {
            if (eVar.d() == null) {
                a();
                return null;
            }
            V v = eVar.a().get();
            if (v == null) {
                a();
                return null;
            } else if (!this.map.b(eVar, j)) {
                return v;
            } else {
                a(j);
                return null;
            }
        }

        boolean c(Object obj, int i) {
            try {
                boolean z = false;
                if (this.count == 0) {
                    return false;
                }
                e<K, V> a = a(obj, i, this.map.q.read());
                if (a == null) {
                    return false;
                }
                if (a.a().get() != null) {
                    z = true;
                }
                return z;
            } finally {
                l();
            }
        }

        @NullableDecl
        V a(K k, int i, V v, boolean z) {
            int i2;
            lock();
            try {
                long read = this.map.q.read();
                c(read);
                if (this.count + 1 > this.threshold) {
                    j();
                    int i3 = this.count;
                }
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null && this.map.f.equivalent(k, d)) {
                        y<K, V> a = eVar2.a();
                        V v2 = a.get();
                        if (v2 == null) {
                            this.modCount++;
                            if (a.d()) {
                                a(k, i, v2, a.a(), RemovalCause.COLLECTED);
                                a((e<e<K, V>, K>) eVar2, (e<K, V>) k, (K) v, read);
                                i2 = this.count;
                            } else {
                                a((e<e<K, V>, K>) eVar2, (e<K, V>) k, (K) v, read);
                                i2 = this.count + 1;
                            }
                            this.count = i2;
                            a(eVar2);
                            return null;
                        } else if (z) {
                            b(eVar2, read);
                            return v2;
                        } else {
                            this.modCount++;
                            a(k, i, v2, a.a(), RemovalCause.REPLACED);
                            a((e<e<K, V>, K>) eVar2, (e<K, V>) k, (K) v, read);
                            a(eVar2);
                            return v2;
                        }
                    }
                }
                this.modCount++;
                e<K, V> a2 = a((p<K, V>) k, i, (e<p<K, V>, V>) eVar);
                a((e<e<K, V>, K>) a2, (e<K, V>) k, (K) v, read);
                atomicReferenceArray.set(length, a2);
                this.count++;
                a(a2);
                return null;
            } finally {
                unlock();
                m();
            }
        }

        @GuardedBy("this")
        void j() {
            AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
            int length = atomicReferenceArray.length();
            if (length < 1073741824) {
                int i = this.count;
                AtomicReferenceArray<e<K, V>> a = a(length << 1);
                this.threshold = (a.length() * 3) / 4;
                int length2 = a.length() - 1;
                for (int i2 = 0; i2 < length; i2++) {
                    e<K, V> eVar = atomicReferenceArray.get(i2);
                    if (eVar != null) {
                        e<K, V> b = eVar.b();
                        int c = eVar.c() & length2;
                        if (b == null) {
                            a.set(c, eVar);
                        } else {
                            e<K, V> eVar2 = eVar;
                            while (b != null) {
                                int c2 = b.c() & length2;
                                if (c2 != c) {
                                    eVar2 = b;
                                    c = c2;
                                }
                                b = b.b();
                            }
                            a.set(c, eVar2);
                            while (eVar != eVar2) {
                                int c3 = eVar.c() & length2;
                                e<K, V> a2 = a(eVar, a.get(c3));
                                if (a2 != null) {
                                    a.set(c3, a2);
                                } else {
                                    b(eVar);
                                    i--;
                                }
                                eVar = eVar.b();
                            }
                        }
                    }
                }
                this.table = a;
                this.count = i;
            }
        }

        boolean a(K k, int i, V v, V v2) {
            lock();
            try {
                long read = this.map.q.read();
                c(read);
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null) {
                        if (this.map.f.equivalent(k, d)) {
                            y<K, V> a = eVar2.a();
                            V v3 = a.get();
                            if (v3 == null) {
                                if (a.d()) {
                                    int i2 = this.count;
                                    this.modCount++;
                                    atomicReferenceArray.set(length, a(eVar, eVar2, d, i, v3, a, RemovalCause.COLLECTED));
                                    this.count--;
                                }
                                return false;
                            } else if (this.map.g.equivalent(v, v3)) {
                                this.modCount++;
                                a(k, i, v3, a.a(), RemovalCause.REPLACED);
                                a((e<e<K, V>, K>) eVar2, (e<K, V>) k, (K) v2, read);
                                a(eVar2);
                                return true;
                            } else {
                                b(eVar2, read);
                                return false;
                            }
                        }
                    }
                }
                return false;
            } finally {
                unlock();
                m();
            }
        }

        @NullableDecl
        V a(K k, int i, V v) {
            lock();
            try {
                long read = this.map.q.read();
                c(read);
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null) {
                        if (this.map.f.equivalent(k, d)) {
                            y<K, V> a = eVar2.a();
                            V v2 = a.get();
                            if (v2 == null) {
                                if (a.d()) {
                                    int i2 = this.count;
                                    this.modCount++;
                                    atomicReferenceArray.set(length, a(eVar, eVar2, d, i, v2, a, RemovalCause.COLLECTED));
                                    this.count--;
                                }
                                return null;
                            }
                            this.modCount++;
                            a(k, i, v2, a.a(), RemovalCause.REPLACED);
                            a((e<e<K, V>, K>) eVar2, (e<K, V>) k, (K) v, read);
                            a(eVar2);
                            return v2;
                        }
                    }
                }
                return null;
            } finally {
                unlock();
                m();
            }
        }

        @NullableDecl
        V d(Object obj, int i) {
            RemovalCause removalCause;
            lock();
            try {
                c(this.map.q.read());
                int i2 = this.count;
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null && this.map.f.equivalent(obj, d)) {
                        y<K, V> a = eVar2.a();
                        V v = a.get();
                        if (v != null) {
                            removalCause = RemovalCause.EXPLICIT;
                        } else if (!a.d()) {
                            return null;
                        } else {
                            removalCause = RemovalCause.COLLECTED;
                        }
                        this.modCount++;
                        atomicReferenceArray.set(length, a(eVar, eVar2, d, i, v, a, removalCause));
                        this.count--;
                        return v;
                    }
                }
                return null;
            } finally {
                unlock();
                m();
            }
        }

        boolean b(Object obj, int i, Object obj2) {
            RemovalCause removalCause;
            lock();
            try {
                c(this.map.q.read());
                int i2 = this.count;
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                boolean z = true;
                int length = (atomicReferenceArray.length() - 1) & i;
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null && this.map.f.equivalent(obj, d)) {
                        y<K, V> a = eVar2.a();
                        V v = a.get();
                        if (this.map.g.equivalent(obj2, v)) {
                            removalCause = RemovalCause.EXPLICIT;
                        } else if (v != null || !a.d()) {
                            return false;
                        } else {
                            removalCause = RemovalCause.COLLECTED;
                        }
                        this.modCount++;
                        atomicReferenceArray.set(length, a(eVar, eVar2, d, i, v, a, removalCause));
                        this.count--;
                        if (removalCause != RemovalCause.EXPLICIT) {
                            z = false;
                        }
                        return z;
                    }
                }
                return false;
            } finally {
                unlock();
                m();
            }
        }

        boolean a(K k, int i, k<K, V> kVar, V v) {
            int i2;
            lock();
            try {
                long read = this.map.q.read();
                c(read);
                int i3 = this.count + 1;
                if (i3 > this.threshold) {
                    j();
                    i2 = this.count + 1;
                } else {
                    i2 = i3;
                }
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = i & (atomicReferenceArray.length() - 1);
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null && this.map.f.equivalent(k, d)) {
                        y<K, V> a = eVar2.a();
                        V v2 = a.get();
                        if (kVar != a && (v2 != null || a == a.u)) {
                            a(k, i, v, 0, RemovalCause.REPLACED);
                            return false;
                        }
                        this.modCount++;
                        if (kVar.d()) {
                            a(k, i, v2, kVar.a(), v2 == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED);
                            i2--;
                        }
                        a((e<e<K, V>, K>) eVar2, (e<K, V>) k, (K) v, read);
                        this.count = i2;
                        a(eVar2);
                        return true;
                    }
                }
                this.modCount++;
                e<K, V> a2 = a((p<K, V>) k, i, (e<p<K, V>, V>) eVar);
                a((e<e<K, V>, K>) a2, (e<K, V>) k, (K) v, read);
                atomicReferenceArray.set(length, a2);
                this.count = i2;
                a(a2);
                return true;
            } finally {
                unlock();
                m();
            }
        }

        void k() {
            RemovalCause removalCause;
            if (this.count != 0) {
                lock();
                try {
                    c(this.map.q.read());
                    AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                    for (int i = 0; i < atomicReferenceArray.length(); i++) {
                        for (e<K, V> eVar = atomicReferenceArray.get(i); eVar != null; eVar = eVar.b()) {
                            if (eVar.a().d()) {
                                K d = eVar.d();
                                V v = eVar.a().get();
                                if (!(d == null || v == null)) {
                                    removalCause = RemovalCause.EXPLICIT;
                                    a(d, eVar.c(), v, eVar.a().a(), removalCause);
                                }
                                removalCause = RemovalCause.COLLECTED;
                                a(d, eVar.c(), v, eVar.a().a(), removalCause);
                            }
                        }
                    }
                    for (int i2 = 0; i2 < atomicReferenceArray.length(); i2++) {
                        atomicReferenceArray.set(i2, null);
                    }
                    e();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                } finally {
                    unlock();
                    m();
                }
            }
        }

        @NullableDecl
        @GuardedBy("this")
        e<K, V> a(e<K, V> eVar, e<K, V> eVar2, @NullableDecl K k, int i, V v, y<K, V> yVar, RemovalCause removalCause) {
            a(k, i, v, yVar.a(), removalCause);
            this.writeQueue.remove(eVar2);
            this.accessQueue.remove(eVar2);
            if (!yVar.c()) {
                return b(eVar, eVar2);
            }
            yVar.a(null);
            return eVar;
        }

        @NullableDecl
        @GuardedBy("this")
        e<K, V> b(e<K, V> eVar, e<K, V> eVar2) {
            int i = this.count;
            e<K, V> b = eVar2.b();
            while (eVar != eVar2) {
                e<K, V> a = a(eVar, b);
                if (a != null) {
                    b = a;
                } else {
                    b(eVar);
                    i--;
                }
                eVar = eVar.b();
            }
            this.count = i;
            return b;
        }

        @GuardedBy("this")
        void b(e<K, V> eVar) {
            a(eVar.d(), eVar.c(), eVar.a().get(), eVar.a().a(), RemovalCause.COLLECTED);
            this.writeQueue.remove(eVar);
            this.accessQueue.remove(eVar);
        }

        boolean a(e<K, V> eVar, int i) {
            lock();
            try {
                int i2 = this.count;
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                e<K, V> eVar2 = atomicReferenceArray.get(length);
                for (e<K, V> eVar3 = eVar2; eVar3 != null; eVar3 = eVar3.b()) {
                    if (eVar3 == eVar) {
                        this.modCount++;
                        atomicReferenceArray.set(length, a(eVar2, eVar3, eVar3.d(), i, eVar3.a().get(), eVar3.a(), RemovalCause.COLLECTED));
                        this.count--;
                        return true;
                    }
                }
                return false;
            } finally {
                unlock();
                m();
            }
        }

        boolean a(K k, int i, y<K, V> yVar) {
            lock();
            try {
                int i2 = this.count;
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null && this.map.f.equivalent(k, d)) {
                        if (eVar2.a() == yVar) {
                            this.modCount++;
                            atomicReferenceArray.set(length, a(eVar, eVar2, d, i, yVar.get(), yVar, RemovalCause.COLLECTED));
                            this.count--;
                            return true;
                        } else {
                            unlock();
                            if (!isHeldByCurrentThread()) {
                                m();
                            }
                            return false;
                        }
                    }
                }
                unlock();
                if (!isHeldByCurrentThread()) {
                    m();
                }
                return false;
            } finally {
                unlock();
                if (!isHeldByCurrentThread()) {
                    m();
                }
            }
        }

        boolean a(K k, int i, k<K, V> kVar) {
            lock();
            try {
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                e<K, V> eVar = atomicReferenceArray.get(length);
                for (e<K, V> eVar2 = eVar; eVar2 != null; eVar2 = eVar2.b()) {
                    K d = eVar2.d();
                    if (eVar2.c() == i && d != null && this.map.f.equivalent(k, d)) {
                        if (eVar2.a() != kVar) {
                            return false;
                        } else {
                            if (kVar.d()) {
                                eVar2.a(kVar.g());
                            } else {
                                atomicReferenceArray.set(length, b(eVar, eVar2));
                            }
                            return true;
                        }
                    }
                }
                return false;
            } finally {
                unlock();
                m();
            }
        }

        @VisibleForTesting
        @GuardedBy("this")
        boolean a(e<K, V> eVar, int i, RemovalCause removalCause) {
            int i2 = this.count;
            AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.table;
            int length = (atomicReferenceArray.length() - 1) & i;
            e<K, V> eVar2 = atomicReferenceArray.get(length);
            for (e<K, V> eVar3 = eVar2; eVar3 != null; eVar3 = eVar3.b()) {
                if (eVar3 == eVar) {
                    this.modCount++;
                    atomicReferenceArray.set(length, a(eVar2, eVar3, eVar3.d(), i, eVar3.a().get(), eVar3.a(), removalCause));
                    this.count--;
                    return true;
                }
            }
            return false;
        }

        void l() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                n();
            }
        }

        @GuardedBy("this")
        void c(long j) {
            d(j);
        }

        void m() {
            o();
        }

        void n() {
            d(this.map.q.read());
            o();
        }

        void d(long j) {
            if (tryLock()) {
                try {
                    b();
                    b(j);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        void o() {
            if (!isHeldByCurrentThread()) {
                this.map.r();
            }
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class k<K, V> implements y<K, V> {
        volatile y<K, V> a;
        final SettableFuture<V> b;
        final Stopwatch c;

        @Override // com.google.common.cache.a.y
        public y<K, V> a(ReferenceQueue<V> referenceQueue, @NullableDecl V v, e<K, V> eVar) {
            return this;
        }

        @Override // com.google.common.cache.a.y
        public e<K, V> b() {
            return null;
        }

        @Override // com.google.common.cache.a.y
        public boolean c() {
            return true;
        }

        public k() {
            this(a.o());
        }

        public k(y<K, V> yVar) {
            this.b = SettableFuture.create();
            this.c = Stopwatch.createUnstarted();
            this.a = yVar;
        }

        @Override // com.google.common.cache.a.y
        public boolean d() {
            return this.a.d();
        }

        @Override // com.google.common.cache.a.y
        public int a() {
            return this.a.a();
        }

        public boolean b(@NullableDecl V v) {
            return this.b.set(v);
        }

        public boolean a(Throwable th) {
            return this.b.setException(th);
        }

        private ListenableFuture<V> b(Throwable th) {
            return Futures.immediateFailedFuture(th);
        }

        @Override // com.google.common.cache.a.y
        public void a(@NullableDecl V v) {
            if (v != null) {
                b((k<K, V>) v);
            } else {
                this.a = a.o();
            }
        }

        public ListenableFuture<V> a(K k, CacheLoader<? super K, V> cacheLoader) {
            try {
                this.c.start();
                V v = this.a.get();
                if (v == null) {
                    V load = cacheLoader.load(k);
                    return b((k<K, V>) load) ? this.b : Futures.immediateFuture(load);
                }
                ListenableFuture<V> reload = cacheLoader.reload(k, v);
                if (reload == null) {
                    return Futures.immediateFuture(null);
                }
                return Futures.transform(reload, new Function<V, V>() { // from class: com.google.common.cache.a.k.1
                    @Override // com.google.common.base.Function
                    public V apply(V v2) {
                        k.this.b((k) v2);
                        return v2;
                    }
                }, MoreExecutors.directExecutor());
            } catch (Throwable th) {
                ListenableFuture<V> b = a(th) ? this.b : b(th);
                if (th instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return b;
            }
        }

        public long f() {
            return this.c.elapsed(TimeUnit.NANOSECONDS);
        }

        @Override // com.google.common.cache.a.y
        public V e() throws ExecutionException {
            return (V) Uninterruptibles.getUninterruptibly(this.b);
        }

        @Override // com.google.common.cache.a.y
        public V get() {
            return this.a.get();
        }

        public y<K, V> g() {
            return this.a;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static final class ai<K, V> extends AbstractQueue<e<K, V>> {
        final e<K, V> a = new b<K, V>() { // from class: com.google.common.cache.a.ai.1
            e<K, V> a = this;
            e<K, V> b = this;

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public void b(long j) {
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public long h() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public e<K, V> i() {
                return this.a;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public void c(e<K, V> eVar) {
                this.a = eVar;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public e<K, V> j() {
                return this.b;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public void d(e<K, V> eVar) {
                this.b = eVar;
            }
        };

        ai() {
        }

        /* renamed from: a */
        public boolean offer(e<K, V> eVar) {
            a.b(eVar.j(), eVar.i());
            a.b(this.a.j(), eVar);
            a.b(eVar, this.a);
            return true;
        }

        /* renamed from: a */
        public e<K, V> peek() {
            e<K, V> i = this.a.i();
            if (i == this.a) {
                return null;
            }
            return i;
        }

        /* renamed from: b */
        public e<K, V> poll() {
            e<K, V> i = this.a.i();
            if (i == this.a) {
                return null;
            }
            remove(i);
            return i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            e eVar = (e) obj;
            e<K, V> j = eVar.j();
            e<K, V> i = eVar.i();
            a.b(j, i);
            a.c(eVar);
            return i != o.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((e) obj).i() != o.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.a.i() == this.a;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i = 0;
            for (e<K, V> i2 = this.a.i(); i2 != this.a; i2 = i2.i()) {
                i++;
            }
            return i;
        }

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            e<K, V> i = this.a.i();
            while (true) {
                e<K, V> eVar = this.a;
                if (i != eVar) {
                    e<K, V> i2 = i.i();
                    a.c((e) i);
                    i = i2;
                } else {
                    eVar.c(eVar);
                    e<K, V> eVar2 = this.a;
                    eVar2.d(eVar2);
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<e<K, V>> iterator() {
            return new AbstractSequentialIterator<e<K, V>>(peek()) { // from class: com.google.common.cache.a.ai.2
                /* renamed from: a */
                public e<K, V> computeNext(e<K, V> eVar) {
                    e<K, V> i = eVar.i();
                    if (i == ai.this.a) {
                        return null;
                    }
                    return i;
                }
            };
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static final class c<K, V> extends AbstractQueue<e<K, V>> {
        final e<K, V> a = new b<K, V>() { // from class: com.google.common.cache.a.c.1
            e<K, V> a = this;
            e<K, V> b = this;

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public void a(long j) {
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public long e() {
                return Long.MAX_VALUE;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public e<K, V> f() {
                return this.a;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public void a(e<K, V> eVar) {
                this.a = eVar;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public e<K, V> g() {
                return this.b;
            }

            @Override // com.google.common.cache.a.b, com.google.common.cache.e
            public void b(e<K, V> eVar) {
                this.b = eVar;
            }
        };

        c() {
        }

        /* renamed from: a */
        public boolean offer(e<K, V> eVar) {
            a.a(eVar.g(), eVar.f());
            a.a(this.a.g(), eVar);
            a.a(eVar, this.a);
            return true;
        }

        /* renamed from: a */
        public e<K, V> peek() {
            e<K, V> f = this.a.f();
            if (f == this.a) {
                return null;
            }
            return f;
        }

        /* renamed from: b */
        public e<K, V> poll() {
            e<K, V> f = this.a.f();
            if (f == this.a) {
                return null;
            }
            remove(f);
            return f;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            e eVar = (e) obj;
            e<K, V> g = eVar.g();
            e<K, V> f = eVar.f();
            a.a(g, f);
            a.b(eVar);
            return f != o.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return ((e) obj).f() != o.INSTANCE;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.a.f() == this.a;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            int i = 0;
            for (e<K, V> f = this.a.f(); f != this.a; f = f.f()) {
                i++;
            }
            return i;
        }

        @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            e<K, V> f = this.a.f();
            while (true) {
                e<K, V> eVar = this.a;
                if (f != eVar) {
                    e<K, V> f2 = f.f();
                    a.b((e) f);
                    f = f2;
                } else {
                    eVar.a(eVar);
                    e<K, V> eVar2 = this.a;
                    eVar2.b(eVar2);
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<e<K, V>> iterator() {
            return new AbstractSequentialIterator<e<K, V>>(peek()) { // from class: com.google.common.cache.a.c.2
                /* renamed from: a */
                public e<K, V> computeNext(e<K, V> eVar) {
                    e<K, V> f = eVar.f();
                    if (f == c.this.a) {
                        return null;
                    }
                    return f;
                }
            };
        }
    }

    public void s() {
        for (p<K, V> pVar : this.d) {
            pVar.n();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        p<K, V>[] pVarArr = this.d;
        long j2 = 0;
        for (int i2 = 0; i2 < pVarArr.length; i2++) {
            if (pVarArr[i2].count != 0) {
                return false;
            }
            j2 += pVarArr[i2].modCount;
        }
        if (j2 == 0) {
            return true;
        }
        for (int i3 = 0; i3 < pVarArr.length; i3++) {
            if (pVarArr[i3].count != 0) {
                return false;
            }
            j2 -= pVarArr[i3].modCount;
        }
        return j2 == 0;
    }

    long t() {
        long j2 = 0;
        for (p<K, V> pVar : this.d) {
            j2 += Math.max(0, pVar.count);
        }
        return j2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return Ints.saturatedCast(t());
    }

    @Override // java.util.AbstractMap, java.util.Map
    @NullableDecl
    public V get(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).a(obj, a2);
    }

    V a(K k2, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
        int a2 = a(Preconditions.checkNotNull(k2));
        return b(a2).a((p<K, V>) k2, a2, (CacheLoader<? super p<K, V>, V>) cacheLoader);
    }

    @NullableDecl
    public V b(Object obj) {
        int a2 = a(Preconditions.checkNotNull(obj));
        V a3 = b(a2).a(obj, a2);
        if (a3 == null) {
            this.s.recordMisses(1);
        } else {
            this.s.recordHits(1);
        }
        return a3;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    @NullableDecl
    public V getOrDefault(@NullableDecl Object obj, @NullableDecl V v2) {
        V v3 = get(obj);
        return v3 != null ? v3 : v2;
    }

    V c(K k2) throws ExecutionException {
        return a((a<K, V>) k2, (CacheLoader<? super a<K, V>, V>) this.t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    ImmutableMap<K, V> a(Iterable<?> iterable) {
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        int i2 = 0;
        int i3 = 0;
        for (Object obj : iterable) {
            V v2 = get(obj);
            if (v2 == null) {
                i3++;
            } else {
                newLinkedHashMap.put(obj, v2);
                i2++;
            }
        }
        this.s.recordHits(i2);
        this.s.recordMisses(i3);
        return ImmutableMap.copyOf(newLinkedHashMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    ImmutableMap<K, V> b(Iterable<? extends K> iterable) throws ExecutionException {
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        LinkedHashSet newLinkedHashSet = Sets.newLinkedHashSet();
        int i2 = 0;
        int i3 = 0;
        for (Object obj : iterable) {
            Object obj2 = get(obj);
            if (!newLinkedHashMap.containsKey(obj)) {
                newLinkedHashMap.put(obj, obj2);
                if (obj2 == null) {
                    i3++;
                    newLinkedHashSet.add(obj);
                } else {
                    i2++;
                }
            }
        }
        try {
            if (!newLinkedHashSet.isEmpty()) {
                try {
                    Map a2 = a((Set) newLinkedHashSet, (CacheLoader) this.t);
                    for (Object obj3 : newLinkedHashSet) {
                        Object obj4 = a2.get(obj3);
                        if (obj4 != null) {
                            newLinkedHashMap.put(obj3, obj4);
                        } else {
                            throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + obj3);
                        }
                    }
                } catch (CacheLoader.UnsupportedLoadingOperationException unused) {
                    for (Object obj5 : newLinkedHashSet) {
                        i3--;
                        newLinkedHashMap.put(obj5, a((a<K, V>) obj5, (CacheLoader<? super a<K, V>, Object>) this.t));
                    }
                }
            }
            return ImmutableMap.copyOf(newLinkedHashMap);
        } finally {
            this.s.recordHits(i2);
            this.s.recordMisses(i3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NullableDecl
    Map<K, V> a(Set<? extends K> set, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
        Throwable th;
        try {
            Preconditions.checkNotNull(cacheLoader);
            Preconditions.checkNotNull(set);
            Stopwatch createStarted = Stopwatch.createStarted();
            boolean z2 = false;
            try {
                try {
                    Map map = (Map<? super K, V>) cacheLoader.loadAll(set);
                    if (map != null) {
                        createStarted.stop();
                        for (Map.Entry<K, V> entry : map.entrySet()) {
                            K key = entry.getKey();
                            V value = entry.getValue();
                            if (key == null || value == null) {
                                z2 = true;
                            } else {
                                put(key, value);
                            }
                        }
                        if (!z2) {
                            this.s.recordLoadSuccess(createStarted.elapsed(TimeUnit.NANOSECONDS));
                            return map;
                        }
                        this.s.recordLoadException(createStarted.elapsed(TimeUnit.NANOSECONDS));
                        throw new CacheLoader.InvalidCacheLoadException(cacheLoader + " returned null keys or values from loadAll");
                    }
                    this.s.recordLoadException(createStarted.elapsed(TimeUnit.NANOSECONDS));
                    throw new CacheLoader.InvalidCacheLoadException(cacheLoader + " returned null map from loadAll");
                } catch (CacheLoader.UnsupportedLoadingOperationException e2) {
                    try {
                        throw e2;
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = true;
                        if (!z2) {
                            this.s.recordLoadException(createStarted.elapsed(TimeUnit.NANOSECONDS));
                        }
                        throw th;
                    }
                } catch (RuntimeException e3) {
                    throw new UncheckedExecutionException(e3);
                }
            } catch (Error e4) {
                throw new ExecutionError(e4);
            } catch (InterruptedException e5) {
                Thread.currentThread().interrupt();
                throw new ExecutionException(e5);
            } catch (Exception e6) {
                throw new ExecutionException(e6);
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    void d(K k2) {
        int a2 = a(Preconditions.checkNotNull(k2));
        b(a2).a((p<K, V>) k2, a2, (CacheLoader<? super p<K, V>, V>) this.t, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).c(obj, a2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v1, types: [int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        boolean z2 = false;
        if (obj == null) {
            return false;
        }
        long read = this.q.read();
        p<K, V>[] pVarArr = this.d;
        long j2 = -1;
        int i2 = 0;
        while (i2 < 3) {
            long j3 = 0;
            int length = pVarArr.length;
            int i3 = z2 ? 1 : 0;
            boolean z3 = z2 ? 1 : 0;
            boolean z4 = z2 ? 1 : 0;
            int i4 = i3;
            boolean z5 = z2;
            while (i4 < length) {
                p<K, V> pVar = pVarArr[i4];
                int i5 = pVar.count;
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = pVar.table;
                int i6 = z5;
                while (i6 < atomicReferenceArray.length()) {
                    e<K, V> eVar = atomicReferenceArray.get(i6 == true ? 1 : 0);
                    while (eVar != null) {
                        V c2 = pVar.c(eVar, read);
                        if (c2 != null) {
                            read = read;
                            if (this.g.equivalent(obj, c2)) {
                                return true;
                            }
                        } else {
                            read = read;
                        }
                        eVar = eVar.b();
                        pVarArr = pVarArr;
                    }
                    i6++;
                }
                j3 += pVar.modCount;
                i4++;
                read = read;
                z5 = false;
            }
            if (j3 == j2) {
                return false;
            }
            i2++;
            j2 = j3;
            pVarArr = pVarArr;
            read = read;
            z2 = false;
        }
        return z2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a(k2);
        return b(a2).a((p<K, V>) k2, a2, (int) v2, false);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V putIfAbsent(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a(k2);
        return b(a2).a((p<K, V>) k2, a2, (int) v2, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(@NullableDecl Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).d(obj, a2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).b(obj, a2, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(K k2, @NullableDecl V v2, V v3) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v3);
        if (v2 == null) {
            return false;
        }
        int a2 = a(k2);
        return b(a2).a((p<K, V>) k2, a2, v2, v3);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V replace(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a(k2);
        return b(a2).a((p<K, V>) k2, a2, (int) v2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        for (p<K, V> pVar : this.d) {
            pVar.k();
        }
    }

    void c(Iterable<?> iterable) {
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.w;
        if (set != null) {
            return set;
        }
        i iVar = new i(this);
        this.w = iVar;
        return iVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.x;
        if (collection != null) {
            return collection;
        }
        z zVar = new z(this);
        this.x = zVar;
        return zVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @GwtIncompatible
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.y;
        if (set != null) {
            return set;
        }
        f fVar = new f(this);
        this.y = fVar;
        return fVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public abstract class g<T> implements Iterator<T> {
        int b;
        int c = -1;
        @MonotonicNonNullDecl
        p<K, V> d;
        @MonotonicNonNullDecl
        AtomicReferenceArray<e<K, V>> e;
        @NullableDecl
        e<K, V> f;
        @NullableDecl
        a<K, V>.aj g;
        @NullableDecl
        a<K, V>.aj h;

        g() {
            a.this = r1;
            this.b = r1.d.length - 1;
            b();
        }

        final void b() {
            this.g = null;
            if (!c() && !d()) {
                while (this.b >= 0) {
                    p<K, V>[] pVarArr = a.this.d;
                    int i = this.b;
                    this.b = i - 1;
                    this.d = pVarArr[i];
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
            e<K, V> eVar = this.f;
            if (eVar == null) {
                return false;
            }
            while (true) {
                this.f = eVar.b();
                e<K, V> eVar2 = this.f;
                if (eVar2 == null) {
                    return false;
                }
                if (a(eVar2)) {
                    return true;
                }
                eVar = this.f;
            }
        }

        boolean d() {
            while (true) {
                int i = this.c;
                if (i < 0) {
                    return false;
                }
                AtomicReferenceArray<e<K, V>> atomicReferenceArray = this.e;
                this.c = i - 1;
                e<K, V> eVar = atomicReferenceArray.get(i);
                this.f = eVar;
                if (eVar != null && (a(this.f) || c())) {
                    return true;
                }
            }
        }

        boolean a(e<K, V> eVar) {
            p<K, V> pVar;
            try {
                long read = a.this.q.read();
                K d = eVar.d();
                Object a = a.this.a(eVar, read);
                if (a != null) {
                    this.g = new aj(d, a);
                    return true;
                }
                return false;
            } finally {
                this.d.l();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.g != null;
        }

        a<K, V>.aj e() {
            a<K, V>.aj ajVar = this.g;
            if (ajVar != null) {
                this.h = ajVar;
                b();
                return this.h;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            Preconditions.checkState(this.h != null);
            a.this.remove(this.h.getKey());
            this.h = null;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    final class h extends a<K, V>.g {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        h() {
            super();
            a.this = r1;
        }

        @Override // java.util.Iterator
        public K next() {
            return e().getKey();
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    final class x extends a<K, V>.g {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        x() {
            super();
            a.this = r1;
        }

        @Override // java.util.Iterator
        public V next() {
            return e().getValue();
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public final class aj implements Map.Entry<K, V> {
        final K a;
        V b;

        aj(K k, V v) {
            a.this = r1;
            this.a = k;
            this.b = v;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.a;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.b;
        }

        @Override // java.util.Map.Entry
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.a.equals(entry.getKey()) && this.b.equals(entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = (V) a.this.put(this.a, v);
            this.b = v;
            return v2;
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    final class e extends a<K, V>.g {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e() {
            super();
            a.this = r1;
        }

        /* renamed from: a */
        public Map.Entry<K, V> next() {
            return e();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* renamed from: com.google.common.cache.a$a */
    /* loaded from: classes2.dex */
    public abstract class AbstractC0086a<T> extends AbstractSet<T> {
        @Weak
        final ConcurrentMap<?, ?> a;

        AbstractC0086a(ConcurrentMap<?, ?> concurrentMap) {
            a.this = r1;
            this.a = concurrentMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.a.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.a.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return a.b((Collection) this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public <E> E[] toArray(E[] eArr) {
            return (E[]) a.b((Collection) this).toArray(eArr);
        }
    }

    public static <E> ArrayList<E> b(Collection<E> collection) {
        ArrayList<E> arrayList = new ArrayList<>(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    final class i extends a<K, V>.AbstractC0086a {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        i(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
            a.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new h();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return this.a.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return this.a.remove(obj) != null;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    final class z extends AbstractCollection<V> {
        private final ConcurrentMap<?, ?> b;

        z(ConcurrentMap<?, ?> concurrentMap) {
            a.this = r1;
            this.b = concurrentMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.b.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.b.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.b.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new x();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return this.b.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return a.b((Collection) this).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <E> E[] toArray(E[] eArr) {
            return (E[]) a.b((Collection) this).toArray(eArr);
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    final class f extends a<K, V>.AbstractC0086a {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
            a.this = r1;
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
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (obj2 = a.this.get(key)) != null && a.this.g.equivalent(entry.getValue(), obj2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && a.this.remove(key, entry.getValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class n<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        @MonotonicNonNullDecl
        transient Cache<K, V> b;
        final int concurrencyLevel;
        final long expireAfterAccessNanos;
        final long expireAfterWriteNanos;
        final Equivalence<Object> keyEquivalence;
        final r keyStrength;
        final CacheLoader<? super K, V> loader;
        final long maxWeight;
        final RemovalListener<? super K, ? super V> removalListener;
        @NullableDecl
        final Ticker ticker;
        final Equivalence<Object> valueEquivalence;
        final r valueStrength;
        final Weigher<K, V> weigher;

        n(a<K, V> aVar) {
            this(aVar.h, aVar.i, aVar.f, aVar.g, aVar.m, aVar.l, aVar.j, aVar.k, aVar.e, aVar.p, aVar.q, aVar.t);
        }

        private n(r rVar, r rVar2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long j, long j2, long j3, Weigher<K, V> weigher, int i, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> cacheLoader) {
            this.keyStrength = rVar;
            this.valueStrength = rVar2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.expireAfterWriteNanos = j;
            this.expireAfterAccessNanos = j2;
            this.maxWeight = j3;
            this.weigher = weigher;
            this.concurrencyLevel = i;
            this.removalListener = removalListener;
            this.ticker = (ticker == Ticker.systemTicker() || ticker == CacheBuilder.d) ? null : ticker;
            this.loader = cacheLoader;
        }

        CacheBuilder<K, V> a() {
            CacheBuilder<K, V> cacheBuilder = (CacheBuilder<K, V>) CacheBuilder.newBuilder().a(this.keyStrength).b(this.valueStrength).a(this.keyEquivalence).b(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener((RemovalListener<? super K, ? super V>) this.removalListener);
            cacheBuilder.e = false;
            long j = this.expireAfterWriteNanos;
            if (j > 0) {
                cacheBuilder.expireAfterWrite(j, TimeUnit.NANOSECONDS);
            }
            long j2 = this.expireAfterAccessNanos;
            if (j2 > 0) {
                cacheBuilder.expireAfterAccess(j2, TimeUnit.NANOSECONDS);
            }
            if (this.weigher != CacheBuilder.b.INSTANCE) {
                cacheBuilder.weigher(this.weigher);
                long j3 = this.maxWeight;
                if (j3 != -1) {
                    cacheBuilder.maximumWeight(j3);
                }
            } else {
                long j4 = this.maxWeight;
                if (j4 != -1) {
                    cacheBuilder.maximumSize(j4);
                }
            }
            Ticker ticker = this.ticker;
            if (ticker != null) {
                cacheBuilder.ticker(ticker);
            }
            return cacheBuilder;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.b = (Cache<K, V>) a().build();
        }

        private Object readResolve() {
            return this.b;
        }

        @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public Cache<K, V> delegate() {
            return this.b;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    static final class j<K, V> extends n<K, V> implements LoadingCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        @MonotonicNonNullDecl
        transient LoadingCache<K, V> a;

        j(a<K, V> aVar) {
            super(aVar);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.a = (LoadingCache<K, V>) a().build(this.loader);
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k) throws ExecutionException {
            return this.a.get(k);
        }

        @Override // com.google.common.cache.LoadingCache
        public V getUnchecked(K k) {
            return this.a.getUnchecked(k);
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.a.getAll(iterable);
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public final V apply(K k) {
            return this.a.apply(k);
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k) {
            this.a.refresh(k);
        }

        private Object readResolve() {
            return this.a;
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class m<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final a<K, V> localCache;

        public m(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new a(cacheBuilder, null));
        }

        private m(a<K, V> aVar) {
            this.localCache = aVar;
        }

        @Override // com.google.common.cache.Cache
        @NullableDecl
        public V getIfPresent(Object obj) {
            return this.localCache.b(obj);
        }

        @Override // com.google.common.cache.Cache
        public V get(K k, final Callable<? extends V> callable) throws ExecutionException {
            Preconditions.checkNotNull(callable);
            return this.localCache.a((a<K, V>) k, (CacheLoader<? super a<K, V>, V>) new CacheLoader<Object, V>() { // from class: com.google.common.cache.a.m.1
                @Override // com.google.common.cache.CacheLoader
                public V load(Object obj) throws Exception {
                    return (V) callable.call();
                }
            });
        }

        @Override // com.google.common.cache.Cache
        public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
            return this.localCache.a(iterable);
        }

        @Override // com.google.common.cache.Cache
        public void put(K k, V v) {
            this.localCache.put(k, v);
        }

        @Override // com.google.common.cache.Cache
        public void putAll(Map<? extends K, ? extends V> map) {
            this.localCache.putAll(map);
        }

        @Override // com.google.common.cache.Cache
        public void invalidate(Object obj) {
            Preconditions.checkNotNull(obj);
            this.localCache.remove(obj);
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll(Iterable<?> iterable) {
            this.localCache.c(iterable);
        }

        @Override // com.google.common.cache.Cache
        public void invalidateAll() {
            this.localCache.clear();
        }

        @Override // com.google.common.cache.Cache
        public long size() {
            return this.localCache.t();
        }

        @Override // com.google.common.cache.Cache
        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        @Override // com.google.common.cache.Cache
        public CacheStats stats() {
            AbstractCache.SimpleStatsCounter simpleStatsCounter = new AbstractCache.SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.localCache.s);
            for (p<K, V> pVar : this.localCache.d) {
                simpleStatsCounter.incrementBy(pVar.statsCounter);
            }
            return simpleStatsCounter.snapshot();
        }

        @Override // com.google.common.cache.Cache
        public void cleanUp() {
            this.localCache.s();
        }

        Object writeReplace() {
            return new n(this.localCache);
        }
    }

    /* compiled from: LocalCache.java */
    /* loaded from: classes2.dex */
    public static class l<K, V> extends m<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        public l(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
            super();
        }

        @Override // com.google.common.cache.LoadingCache
        public V get(K k) throws ExecutionException {
            return (V) this.localCache.c((a) k);
        }

        @Override // com.google.common.cache.LoadingCache
        public V getUnchecked(K k) {
            try {
                return get(k);
            } catch (ExecutionException e) {
                throw new UncheckedExecutionException(e.getCause());
            }
        }

        @Override // com.google.common.cache.LoadingCache
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.localCache.b((Iterable) iterable);
        }

        @Override // com.google.common.cache.LoadingCache
        public void refresh(K k) {
            this.localCache.d(k);
        }

        @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
        public final V apply(K k) {
            return getUnchecked(k);
        }

        @Override // com.google.common.cache.a.m
        Object writeReplace() {
            return new j(this.localCache);
        }
    }
}
