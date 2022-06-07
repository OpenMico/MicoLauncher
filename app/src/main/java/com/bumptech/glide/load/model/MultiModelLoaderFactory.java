package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.Preconditions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class MultiModelLoaderFactory {
    private static final c a = new c();
    private static final ModelLoader<Object, Object> b = new a();
    private final List<b<?, ?>> c;
    private final c d;
    private final Set<b<?, ?>> e;
    private final Pools.Pool<List<Throwable>> f;

    public MultiModelLoaderFactory(@NonNull Pools.Pool<List<Throwable>> pool) {
        this(pool, a);
    }

    @VisibleForTesting
    MultiModelLoaderFactory(@NonNull Pools.Pool<List<Throwable>> pool, @NonNull c cVar) {
        this.c = new ArrayList();
        this.e = new HashSet();
        this.f = pool;
        this.d = cVar;
    }

    public synchronized <Model, Data> void a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        a(cls, cls2, modelLoaderFactory, true);
    }

    public synchronized <Model, Data> void b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        a(cls, cls2, modelLoaderFactory, false);
    }

    private <Model, Data> void a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory, boolean z) {
        b<?, ?> bVar = new b<>(cls, cls2, modelLoaderFactory);
        List<b<?, ?>> list = this.c;
        list.add(z ? list.size() : 0, bVar);
    }

    @NonNull
    public synchronized <Model, Data> List<ModelLoaderFactory<? extends Model, ? extends Data>> c(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        List<ModelLoaderFactory<? extends Model, ? extends Data>> a2;
        a2 = a(cls, cls2);
        a(cls, cls2, modelLoaderFactory);
        return a2;
    }

    @NonNull
    public synchronized <Model, Data> List<ModelLoaderFactory<? extends Model, ? extends Data>> a(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<b<?, ?>> it = this.c.iterator();
        while (it.hasNext()) {
            b<?, ?> next = it.next();
            if (next.a(cls, cls2)) {
                it.remove();
                arrayList.add(a(next));
            }
        }
        return arrayList;
    }

    @NonNull
    public synchronized <Model> List<ModelLoader<Model, ?>> a(@NonNull Class<Model> cls) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (b<?, ?> bVar : this.c) {
            if (!this.e.contains(bVar) && bVar.a(cls)) {
                this.e.add(bVar);
                arrayList.add(b(bVar));
                this.e.remove(bVar);
            }
        }
        return arrayList;
    }

    @NonNull
    public synchronized List<Class<?>> b(@NonNull Class<?> cls) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (b<?, ?> bVar : this.c) {
            if (!arrayList.contains(bVar.a) && bVar.a(cls)) {
                arrayList.add(bVar.a);
            }
        }
        return arrayList;
    }

    @NonNull
    public synchronized <Model, Data> ModelLoader<Model, Data> build(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (b<?, ?> bVar : this.c) {
            if (this.e.contains(bVar)) {
                z = true;
            } else if (bVar.a(cls, cls2)) {
                this.e.add(bVar);
                arrayList.add(b(bVar));
                this.e.remove(bVar);
            }
        }
        if (arrayList.size() > 1) {
            return this.d.a(arrayList, this.f);
        } else if (arrayList.size() == 1) {
            return (ModelLoader) arrayList.get(0);
        } else if (z) {
            return a();
        } else {
            throw new Registry.NoModelLoaderAvailableException((Class<?>) cls, (Class<?>) cls2);
        }
    }

    @NonNull
    private <Model, Data> ModelLoaderFactory<Model, Data> a(@NonNull b<?, ?> bVar) {
        return (ModelLoaderFactory<Model, Data>) bVar.b;
    }

    @NonNull
    private <Model, Data> ModelLoader<Model, Data> b(@NonNull b<?, ?> bVar) {
        return (ModelLoader) Preconditions.checkNotNull(bVar.b.build(this));
    }

    @NonNull
    private static <Model, Data> ModelLoader<Model, Data> a() {
        return (ModelLoader<Model, Data>) b;
    }

    /* loaded from: classes.dex */
    public static class b<Model, Data> {
        final Class<Data> a;
        final ModelLoaderFactory<? extends Model, ? extends Data> b;
        private final Class<Model> c;

        public b(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
            this.c = cls;
            this.a = cls2;
            this.b = modelLoaderFactory;
        }

        public boolean a(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return a(cls) && this.a.isAssignableFrom(cls2);
        }

        public boolean a(@NonNull Class<?> cls) {
            return this.c.isAssignableFrom(cls);
        }
    }

    /* loaded from: classes.dex */
    public static class c {
        c() {
        }

        @NonNull
        public <Model, Data> a<Model, Data> a(@NonNull List<ModelLoader<Model, Data>> list, @NonNull Pools.Pool<List<Throwable>> pool) {
            return new a<>(list, pool);
        }
    }

    /* loaded from: classes.dex */
    private static class a implements ModelLoader<Object, Object> {
        @Override // com.bumptech.glide.load.model.ModelLoader
        @Nullable
        public ModelLoader.LoadData<Object> buildLoadData(@NonNull Object obj, int i, int i2, @NonNull Options options) {
            return null;
        }

        @Override // com.bumptech.glide.load.model.ModelLoader
        public boolean handles(@NonNull Object obj) {
            return false;
        }

        a() {
        }
    }
}
