package com.bumptech.glide.load.engine;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.load.engine.l;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: EngineJob.java */
/* loaded from: classes.dex */
public class h<R> implements g.a<R>, FactoryPools.Poolable {
    private static final c e = new c();
    final e a;
    DataSource b;
    GlideException c;
    l<?> d;
    private final StateVerifier f;
    private final l.a g;
    private final Pools.Pool<h<?>> h;
    private final c i;
    private final i j;
    private final GlideExecutor k;
    private final GlideExecutor l;
    private final GlideExecutor m;
    private final GlideExecutor n;
    private final AtomicInteger o;
    private Key p;
    private boolean q;
    private boolean r;
    private boolean s;
    private boolean t;
    private Resource<?> u;
    private boolean v;
    private boolean w;
    private g<R> x;
    private volatile boolean y;
    private boolean z;

    public h(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, i iVar, l.a aVar, Pools.Pool<h<?>> pool) {
        this(glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, iVar, aVar, pool, e);
    }

    @VisibleForTesting
    h(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, i iVar, l.a aVar, Pools.Pool<h<?>> pool, c cVar) {
        this.a = new e();
        this.f = StateVerifier.newInstance();
        this.o = new AtomicInteger();
        this.k = glideExecutor;
        this.l = glideExecutor2;
        this.m = glideExecutor3;
        this.n = glideExecutor4;
        this.j = iVar;
        this.g = aVar;
        this.h = pool;
        this.i = cVar;
    }

    @VisibleForTesting
    public synchronized h<R> a(Key key, boolean z, boolean z2, boolean z3, boolean z4) {
        this.p = key;
        this.q = z;
        this.r = z2;
        this.s = z3;
        this.t = z4;
        return this;
    }

    public synchronized void b(g<R> gVar) {
        this.x = gVar;
        (gVar.a() ? this.k : f()).execute(gVar);
    }

    public synchronized void a(ResourceCallback resourceCallback, Executor executor) {
        this.f.throwIfRecycled();
        this.a.a(resourceCallback, executor);
        boolean z = true;
        if (this.v) {
            a(1);
            executor.execute(new b(resourceCallback));
        } else if (this.w) {
            a(1);
            executor.execute(new a(resourceCallback));
        } else {
            if (this.y) {
                z = false;
            }
            Preconditions.checkArgument(z, "Cannot add callbacks to a cancelled EngineJob");
        }
    }

    @GuardedBy("this")
    void a(ResourceCallback resourceCallback) {
        try {
            resourceCallback.onResourceReady(this.d, this.b, this.z);
        } catch (Throwable th) {
            throw new b(th);
        }
    }

    @GuardedBy("this")
    void b(ResourceCallback resourceCallback) {
        try {
            resourceCallback.onLoadFailed(this.c);
        } catch (Throwable th) {
            throw new b(th);
        }
    }

    public synchronized void c(ResourceCallback resourceCallback) {
        boolean z;
        this.f.throwIfRecycled();
        this.a.a(resourceCallback);
        if (this.a.a()) {
            b();
            if (!this.v && !this.w) {
                z = false;
                if (z && this.o.get() == 0) {
                    h();
                }
            }
            z = true;
            if (z) {
                h();
            }
        }
    }

    public boolean a() {
        return this.t;
    }

    private GlideExecutor f() {
        if (this.r) {
            return this.m;
        }
        return this.s ? this.n : this.l;
    }

    void b() {
        if (!g()) {
            this.y = true;
            this.x.b();
            this.j.onEngineJobCancelled(this, this.p);
        }
    }

    private boolean g() {
        return this.w || this.v || this.y;
    }

    void c() {
        synchronized (this) {
            this.f.throwIfRecycled();
            if (this.y) {
                this.u.recycle();
                h();
            } else if (this.a.a()) {
                throw new IllegalStateException("Received a resource without any callbacks to notify");
            } else if (!this.v) {
                this.d = this.i.a(this.u, this.q, this.p, this.g);
                this.v = true;
                e d2 = this.a.d();
                a(d2.b() + 1);
                this.j.onEngineJobComplete(this, this.p, this.d);
                Iterator<d> it = d2.iterator();
                while (it.hasNext()) {
                    d next = it.next();
                    next.b.execute(new b(next.a));
                }
                d();
            } else {
                throw new IllegalStateException("Already have resource");
            }
        }
    }

    synchronized void a(int i) {
        Preconditions.checkArgument(g(), "Not yet complete!");
        if (this.o.getAndAdd(i) == 0 && this.d != null) {
            this.d.c();
        }
    }

    void d() {
        l<?> lVar;
        synchronized (this) {
            this.f.throwIfRecycled();
            Preconditions.checkArgument(g(), "Not yet complete!");
            int decrementAndGet = this.o.decrementAndGet();
            Preconditions.checkArgument(decrementAndGet >= 0, "Can't decrement below 0");
            if (decrementAndGet == 0) {
                lVar = this.d;
                h();
            } else {
                lVar = null;
            }
        }
        if (lVar != null) {
            lVar.d();
        }
    }

    private synchronized void h() {
        if (this.p != null) {
            this.a.c();
            this.p = null;
            this.d = null;
            this.u = null;
            this.w = false;
            this.y = false;
            this.v = false;
            this.z = false;
            this.x.a(false);
            this.x = null;
            this.c = null;
            this.b = null;
            this.h.release(this);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.load.engine.g.a
    public void a(Resource<R> resource, DataSource dataSource, boolean z) {
        synchronized (this) {
            this.u = resource;
            this.b = dataSource;
            this.z = z;
        }
        c();
    }

    @Override // com.bumptech.glide.load.engine.g.a
    public void a(GlideException glideException) {
        synchronized (this) {
            this.c = glideException;
        }
        e();
    }

    @Override // com.bumptech.glide.load.engine.g.a
    public void a(g<?> gVar) {
        f().execute(gVar);
    }

    void e() {
        synchronized (this) {
            this.f.throwIfRecycled();
            if (this.y) {
                h();
            } else if (this.a.a()) {
                throw new IllegalStateException("Received an exception without any callbacks to notify");
            } else if (!this.w) {
                this.w = true;
                Key key = this.p;
                e d2 = this.a.d();
                a(d2.b() + 1);
                this.j.onEngineJobComplete(this, key, null);
                Iterator<d> it = d2.iterator();
                while (it.hasNext()) {
                    d next = it.next();
                    next.b.execute(new a(next.a));
                }
                d();
            } else {
                throw new IllegalStateException("Already failed once");
            }
        }
    }

    @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
    @NonNull
    public StateVerifier getVerifier() {
        return this.f;
    }

    /* compiled from: EngineJob.java */
    /* loaded from: classes.dex */
    public class a implements Runnable {
        private final ResourceCallback b;

        a(ResourceCallback resourceCallback) {
            h.this = r1;
            this.b = resourceCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.b.getLock()) {
                synchronized (h.this) {
                    if (h.this.a.b(this.b)) {
                        h.this.b(this.b);
                    }
                    h.this.d();
                }
            }
        }
    }

    /* compiled from: EngineJob.java */
    /* loaded from: classes.dex */
    public class b implements Runnable {
        private final ResourceCallback b;

        b(ResourceCallback resourceCallback) {
            h.this = r1;
            this.b = resourceCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.b.getLock()) {
                synchronized (h.this) {
                    if (h.this.a.b(this.b)) {
                        h.this.d.c();
                        h.this.a(this.b);
                        h.this.c(this.b);
                    }
                    h.this.d();
                }
            }
        }
    }

    /* compiled from: EngineJob.java */
    /* loaded from: classes.dex */
    public static final class e implements Iterable<d> {
        private final List<d> a;

        e() {
            this(new ArrayList(2));
        }

        e(List<d> list) {
            this.a = list;
        }

        void a(ResourceCallback resourceCallback, Executor executor) {
            this.a.add(new d(resourceCallback, executor));
        }

        void a(ResourceCallback resourceCallback) {
            this.a.remove(c(resourceCallback));
        }

        boolean b(ResourceCallback resourceCallback) {
            return this.a.contains(c(resourceCallback));
        }

        boolean a() {
            return this.a.isEmpty();
        }

        int b() {
            return this.a.size();
        }

        void c() {
            this.a.clear();
        }

        e d() {
            return new e(new ArrayList(this.a));
        }

        private static d c(ResourceCallback resourceCallback) {
            return new d(resourceCallback, Executors.directExecutor());
        }

        @Override // java.lang.Iterable
        @NonNull
        public Iterator<d> iterator() {
            return this.a.iterator();
        }
    }

    /* compiled from: EngineJob.java */
    /* loaded from: classes.dex */
    public static final class d {
        final ResourceCallback a;
        final Executor b;

        d(ResourceCallback resourceCallback, Executor executor) {
            this.a = resourceCallback;
            this.b = executor;
        }

        public boolean equals(Object obj) {
            if (obj instanceof d) {
                return this.a.equals(((d) obj).a);
            }
            return false;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    /* compiled from: EngineJob.java */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class c {
        c() {
        }

        public <R> l<R> a(Resource<R> resource, boolean z, Key key, l.a aVar) {
            return new l<>(resource, z, true, key, aVar);
        }
    }
}
