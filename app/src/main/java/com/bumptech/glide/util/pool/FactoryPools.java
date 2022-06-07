package com.bumptech.glide.util.pool;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class FactoryPools {
    private static final Resetter<Object> a = new Resetter<Object>() { // from class: com.bumptech.glide.util.pool.FactoryPools.1
        @Override // com.bumptech.glide.util.pool.FactoryPools.Resetter
        public void reset(@NonNull Object obj) {
        }
    };

    /* loaded from: classes.dex */
    public interface Factory<T> {
        T create();
    }

    /* loaded from: classes.dex */
    public interface Poolable {
        @NonNull
        StateVerifier getVerifier();
    }

    /* loaded from: classes.dex */
    public interface Resetter<T> {
        void reset(@NonNull T t);
    }

    private FactoryPools() {
    }

    @NonNull
    public static <T extends Poolable> Pools.Pool<T> simple(int i, @NonNull Factory<T> factory) {
        return a(new Pools.SimplePool(i), factory);
    }

    @NonNull
    public static <T extends Poolable> Pools.Pool<T> threadSafe(int i, @NonNull Factory<T> factory) {
        return a(new Pools.SynchronizedPool(i), factory);
    }

    @NonNull
    public static <T> Pools.Pool<List<T>> threadSafeList() {
        return threadSafeList(20);
    }

    @NonNull
    public static <T> Pools.Pool<List<T>> threadSafeList(int i) {
        return a(new Pools.SynchronizedPool(i), new Factory<List<T>>() { // from class: com.bumptech.glide.util.pool.FactoryPools.2
            @NonNull
            /* renamed from: a */
            public List<T> create() {
                return new ArrayList();
            }
        }, new Resetter<List<T>>() { // from class: com.bumptech.glide.util.pool.FactoryPools.3
            /* renamed from: a */
            public void reset(@NonNull List<T> list) {
                list.clear();
            }
        });
    }

    @NonNull
    private static <T extends Poolable> Pools.Pool<T> a(@NonNull Pools.Pool<T> pool, @NonNull Factory<T> factory) {
        return a(pool, factory, a());
    }

    @NonNull
    private static <T> Pools.Pool<T> a(@NonNull Pools.Pool<T> pool, @NonNull Factory<T> factory, @NonNull Resetter<T> resetter) {
        return new a(pool, factory, resetter);
    }

    @NonNull
    private static <T> Resetter<T> a() {
        return (Resetter<T>) a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a<T> implements Pools.Pool<T> {
        private final Factory<T> a;
        private final Resetter<T> b;
        private final Pools.Pool<T> c;

        a(@NonNull Pools.Pool<T> pool, @NonNull Factory<T> factory, @NonNull Resetter<T> resetter) {
            this.c = pool;
            this.a = factory;
            this.b = resetter;
        }

        @Override // androidx.core.util.Pools.Pool
        public T acquire() {
            T acquire = this.c.acquire();
            if (acquire == null) {
                acquire = this.a.create();
                if (Log.isLoggable("FactoryPools", 2)) {
                    Log.v("FactoryPools", "Created new " + acquire.getClass());
                }
            }
            if (acquire instanceof Poolable) {
                ((Poolable) acquire).getVerifier().a(false);
            }
            return acquire;
        }

        @Override // androidx.core.util.Pools.Pool
        public boolean release(@NonNull T t) {
            if (t instanceof Poolable) {
                ((Poolable) t).getVerifier().a(true);
            }
            this.b.reset(t);
            return this.c.release(t);
        }
    }
}
