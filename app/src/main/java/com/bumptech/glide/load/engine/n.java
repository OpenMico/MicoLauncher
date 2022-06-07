package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LockedResource.java */
/* loaded from: classes.dex */
public final class n<Z> implements Resource<Z>, FactoryPools.Poolable {
    private static final Pools.Pool<n<?>> a = FactoryPools.threadSafe(20, new FactoryPools.Factory<n<?>>() { // from class: com.bumptech.glide.load.engine.n.1
        /* renamed from: a */
        public n<?> create() {
            return new n<>();
        }
    });
    private final StateVerifier b = StateVerifier.newInstance();
    private Resource<Z> c;
    private boolean d;
    private boolean e;

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static <Z> n<Z> a(Resource<Z> resource) {
        n<Z> nVar = (n) Preconditions.checkNotNull(a.acquire());
        nVar.b(resource);
        return nVar;
    }

    n() {
    }

    private void b(Resource<Z> resource) {
        this.e = false;
        this.d = true;
        this.c = resource;
    }

    private void b() {
        this.c = null;
        a.release(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a() {
        this.b.throwIfRecycled();
        if (this.d) {
            this.d = false;
            if (this.e) {
                recycle();
            }
        } else {
            throw new IllegalStateException("Already unlocked");
        }
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<Z> getResourceClass() {
        return this.c.getResourceClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Z get() {
        return this.c.get();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return this.c.getSize();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public synchronized void recycle() {
        this.b.throwIfRecycled();
        this.e = true;
        if (!this.d) {
            this.c.recycle();
            b();
        }
    }

    @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
    @NonNull
    public StateVerifier getVerifier() {
        return this.b;
    }
}
