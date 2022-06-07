package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;

/* compiled from: EngineResource.java */
/* loaded from: classes.dex */
public class l<Z> implements Resource<Z> {
    private final boolean a;
    private final boolean b;
    private final Resource<Z> c;
    private final a d;
    private final Key e;
    private int f;
    private boolean g;

    /* compiled from: EngineResource.java */
    /* loaded from: classes.dex */
    public interface a {
        void onResourceReleased(Key key, l<?> lVar);
    }

    public l(Resource<Z> resource, boolean z, boolean z2, Key key, a aVar) {
        this.c = (Resource) Preconditions.checkNotNull(resource);
        this.a = z;
        this.b = z2;
        this.e = key;
        this.d = (a) Preconditions.checkNotNull(aVar);
    }

    public Resource<Z> a() {
        return this.c;
    }

    public boolean b() {
        return this.a;
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
        if (this.f > 0) {
            throw new IllegalStateException("Cannot recycle a resource while it is still acquired");
        } else if (!this.g) {
            this.g = true;
            if (this.b) {
                this.c.recycle();
            }
        } else {
            throw new IllegalStateException("Cannot recycle a resource that has already been recycled");
        }
    }

    public synchronized void c() {
        if (!this.g) {
            this.f++;
        } else {
            throw new IllegalStateException("Cannot acquire a recycled resource");
        }
    }

    public void d() {
        boolean z;
        synchronized (this) {
            if (this.f > 0) {
                z = true;
                int i = this.f - 1;
                this.f = i;
                if (i != 0) {
                    z = false;
                }
            } else {
                throw new IllegalStateException("Cannot release a recycled or not yet acquired resource");
            }
        }
        if (z) {
            this.d.onResourceReleased(this.e, this);
        }
    }

    public synchronized String toString() {
        return "EngineResource{isMemoryCacheable=" + this.a + ", listener=" + this.d + ", key=" + this.e + ", acquired=" + this.f + ", isRecycled=" + this.g + ", resource=" + this.c + '}';
    }
}
