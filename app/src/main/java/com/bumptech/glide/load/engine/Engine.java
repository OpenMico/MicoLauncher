package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.load.engine.l;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.Map;
import java.util.concurrent.Executor;
import miuix.animation.internal.AnimTask;

/* loaded from: classes.dex */
public class Engine implements MemoryCache.ResourceRemovedListener, i, l.a {
    private static final boolean a = Log.isLoggable("Engine", 2);
    private final m b;
    private final k c;
    private final MemoryCache d;
    private final b e;
    private final q f;
    private final c g;
    private final a h;
    private final a i;

    public Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, boolean z) {
        this(memoryCache, factory, glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, null, null, null, null, null, null, z);
    }

    @VisibleForTesting
    Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, m mVar, k kVar, a aVar, b bVar, a aVar2, q qVar, boolean z) {
        this.d = memoryCache;
        this.g = new c(factory);
        a aVar3 = aVar == null ? new a(z) : aVar;
        this.i = aVar3;
        aVar3.a(this);
        this.c = kVar == null ? new k() : kVar;
        this.b = mVar == null ? new m() : mVar;
        this.e = bVar == null ? new b(glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, this, this) : bVar;
        this.h = aVar2 == null ? new a(this.g) : aVar2;
        this.f = qVar == null ? new q() : qVar;
        memoryCache.setResourceRemovedListener(this);
    }

    public <R> LoadStatus load(GlideContext glideContext, Object obj, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, Options options, boolean z3, boolean z4, boolean z5, boolean z6, ResourceCallback resourceCallback, Executor executor) {
        long logTime = a ? LogTime.getLogTime() : 0L;
        j a2 = this.c.a(obj, key, i, i2, map, cls, cls2, options);
        synchronized (this) {
            l<?> a3 = a(a2, z3, logTime);
            if (a3 == null) {
                return a(glideContext, obj, key, i, i2, cls, cls2, priority, diskCacheStrategy, map, z, z2, options, z3, z4, z5, z6, resourceCallback, executor, a2, logTime);
            }
            resourceCallback.onResourceReady(a3, DataSource.MEMORY_CACHE, false);
            return null;
        }
    }

    private <R> LoadStatus a(GlideContext glideContext, Object obj, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, Options options, boolean z3, boolean z4, boolean z5, boolean z6, ResourceCallback resourceCallback, Executor executor, j jVar, long j) {
        h<?> a2 = this.b.a(jVar, z6);
        if (a2 != null) {
            a2.a(resourceCallback, executor);
            if (a) {
                a("Added to existing load", j, jVar);
            }
            return new LoadStatus(resourceCallback, a2);
        }
        h<R> a3 = this.e.a(jVar, z3, z4, z5, z6);
        g<R> a4 = this.h.a(glideContext, obj, jVar, key, i, i2, cls, cls2, priority, diskCacheStrategy, map, z, z2, z6, options, a3);
        this.b.a((Key) jVar, (h<?>) a3);
        a3.a(resourceCallback, executor);
        a3.b(a4);
        if (a) {
            a("Started new load", j, jVar);
        }
        return new LoadStatus(resourceCallback, a3);
    }

    @Nullable
    private l<?> a(j jVar, boolean z, long j) {
        if (!z) {
            return null;
        }
        l<?> a2 = a(jVar);
        if (a2 != null) {
            if (a) {
                a("Loaded resource from active resources", j, jVar);
            }
            return a2;
        }
        l<?> b2 = b(jVar);
        if (b2 == null) {
            return null;
        }
        if (a) {
            a("Loaded resource from cache", j, jVar);
        }
        return b2;
    }

    private static void a(String str, long j, Key key) {
        Log.v("Engine", str + " in " + LogTime.getElapsedMillis(j) + "ms, key: " + key);
    }

    @Nullable
    private l<?> a(Key key) {
        l<?> b2 = this.i.b(key);
        if (b2 != null) {
            b2.c();
        }
        return b2;
    }

    private l<?> b(Key key) {
        l<?> c2 = c(key);
        if (c2 != null) {
            c2.c();
            this.i.a(key, c2);
        }
        return c2;
    }

    private l<?> c(Key key) {
        Resource<?> remove = this.d.remove(key);
        if (remove == null) {
            return null;
        }
        if (remove instanceof l) {
            return (l) remove;
        }
        return new l<>(remove, true, true, key, this);
    }

    public void release(Resource<?> resource) {
        if (resource instanceof l) {
            ((l) resource).d();
            return;
        }
        throw new IllegalArgumentException("Cannot release anything but an EngineResource");
    }

    @Override // com.bumptech.glide.load.engine.i
    public synchronized void onEngineJobComplete(h<?> hVar, Key key, l<?> lVar) {
        if (lVar != null) {
            if (lVar.b()) {
                this.i.a(key, lVar);
            }
        }
        this.b.b(key, hVar);
    }

    @Override // com.bumptech.glide.load.engine.i
    public synchronized void onEngineJobCancelled(h<?> hVar, Key key) {
        this.b.b(key, hVar);
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache.ResourceRemovedListener
    public void onResourceRemoved(@NonNull Resource<?> resource) {
        this.f.a(resource, true);
    }

    @Override // com.bumptech.glide.load.engine.l.a
    public void onResourceReleased(Key key, l<?> lVar) {
        this.i.a(key);
        if (lVar.b()) {
            this.d.put(key, lVar);
        } else {
            this.f.a(lVar, false);
        }
    }

    public void clearDiskCache() {
        this.g.a().clear();
    }

    @VisibleForTesting
    public void shutdown() {
        this.e.a();
        this.g.b();
        this.i.b();
    }

    /* loaded from: classes.dex */
    public class LoadStatus {
        private final h<?> b;
        private final ResourceCallback c;

        LoadStatus(ResourceCallback resourceCallback, h<?> hVar) {
            Engine.this = r1;
            this.c = resourceCallback;
            this.b = hVar;
        }

        public void cancel() {
            synchronized (Engine.this) {
                this.b.c(this.c);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class c implements g.d {
        private final DiskCache.Factory a;
        private volatile DiskCache b;

        c(DiskCache.Factory factory) {
            this.a = factory;
        }

        @VisibleForTesting
        synchronized void b() {
            if (this.b != null) {
                this.b.clear();
            }
        }

        @Override // com.bumptech.glide.load.engine.g.d
        public DiskCache a() {
            if (this.b == null) {
                synchronized (this) {
                    if (this.b == null) {
                        this.b = this.a.build();
                    }
                    if (this.b == null) {
                        this.b = new DiskCacheAdapter();
                    }
                }
            }
            return this.b;
        }
    }

    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class a {
        final g.d a;
        final Pools.Pool<g<?>> b = FactoryPools.threadSafe(AnimTask.MAX_PAGE_SIZE, new FactoryPools.Factory<g<?>>() { // from class: com.bumptech.glide.load.engine.Engine.a.1
            /* renamed from: a */
            public g<?> create() {
                return new g<>(a.this.a, a.this.b);
            }
        });
        private int c;

        a(g.d dVar) {
            this.a = dVar;
        }

        <R> g<R> a(GlideContext glideContext, Object obj, j jVar, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, boolean z3, Options options, g.a<R> aVar) {
            g gVar = (g) Preconditions.checkNotNull(this.b.acquire());
            int i3 = this.c;
            this.c = i3 + 1;
            return gVar.a(glideContext, obj, jVar, key, i, i2, cls, cls2, priority, diskCacheStrategy, map, z, z2, z3, options, aVar, i3);
        }
    }

    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class b {
        final GlideExecutor a;
        final GlideExecutor b;
        final GlideExecutor c;
        final GlideExecutor d;
        final i e;
        final l.a f;
        final Pools.Pool<h<?>> g = FactoryPools.threadSafe(AnimTask.MAX_PAGE_SIZE, new FactoryPools.Factory<h<?>>() { // from class: com.bumptech.glide.load.engine.Engine.b.1
            /* renamed from: a */
            public h<?> create() {
                return new h<>(b.this.a, b.this.b, b.this.c, b.this.d, b.this.e, b.this.f, b.this.g);
            }
        });

        b(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, i iVar, l.a aVar) {
            this.a = glideExecutor;
            this.b = glideExecutor2;
            this.c = glideExecutor3;
            this.d = glideExecutor4;
            this.e = iVar;
            this.f = aVar;
        }

        @VisibleForTesting
        void a() {
            Executors.shutdownAndAwaitTermination(this.a);
            Executors.shutdownAndAwaitTermination(this.b);
            Executors.shutdownAndAwaitTermination(this.c);
            Executors.shutdownAndAwaitTermination(this.d);
        }

        <R> h<R> a(Key key, boolean z, boolean z2, boolean z3, boolean z4) {
            return ((h) Preconditions.checkNotNull(this.g.acquire())).a(key, z, z2, z3, z4);
        }
    }
}
