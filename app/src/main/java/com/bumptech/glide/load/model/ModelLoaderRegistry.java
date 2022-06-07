package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import com.bumptech.glide.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ModelLoaderRegistry {
    private final MultiModelLoaderFactory a;
    private final a b;

    public ModelLoaderRegistry(@NonNull Pools.Pool<List<Throwable>> pool) {
        this(new MultiModelLoaderFactory(pool));
    }

    private ModelLoaderRegistry(@NonNull MultiModelLoaderFactory multiModelLoaderFactory) {
        this.b = new a();
        this.a = multiModelLoaderFactory;
    }

    public synchronized <Model, Data> void append(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        this.a.a(cls, cls2, modelLoaderFactory);
        this.b.a();
    }

    public synchronized <Model, Data> void prepend(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        this.a.b(cls, cls2, modelLoaderFactory);
        this.b.a();
    }

    public synchronized <Model, Data> void remove(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        a((List) this.a.a(cls, cls2));
        this.b.a();
    }

    public synchronized <Model, Data> void replace(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        a((List) this.a.c(cls, cls2, modelLoaderFactory));
        this.b.a();
    }

    private <Model, Data> void a(@NonNull List<ModelLoaderFactory<? extends Model, ? extends Data>> list) {
        for (ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory : list) {
            modelLoaderFactory.teardown();
        }
    }

    @NonNull
    public <A> List<ModelLoader<A, ?>> getModelLoaders(@NonNull A a2) {
        List<ModelLoader<A, ?>> a3 = a((Class) a(a2));
        if (!a3.isEmpty()) {
            int size = a3.size();
            boolean z = true;
            List<ModelLoader<A, ?>> emptyList = Collections.emptyList();
            for (int i = 0; i < size; i++) {
                ModelLoader<A, ?> modelLoader = a3.get(i);
                if (modelLoader.handles(a2)) {
                    if (z) {
                        emptyList = new ArrayList<>(size - i);
                        z = false;
                    }
                    emptyList.add(modelLoader);
                }
            }
            if (!emptyList.isEmpty()) {
                return emptyList;
            }
            throw new Registry.NoModelLoaderAvailableException(a2, a3);
        }
        throw new Registry.NoModelLoaderAvailableException(a2);
    }

    public synchronized <Model, Data> ModelLoader<Model, Data> build(@NonNull Class<Model> cls, @NonNull Class<Data> cls2) {
        return this.a.build(cls, cls2);
    }

    @NonNull
    public synchronized List<Class<?>> getDataClasses(@NonNull Class<?> cls) {
        return this.a.b(cls);
    }

    @NonNull
    private synchronized <A> List<ModelLoader<A, ?>> a(@NonNull Class<A> cls) {
        List<ModelLoader<A, ?>> a2;
        a2 = this.b.a(cls);
        if (a2 == null) {
            a2 = Collections.unmodifiableList(this.a.a(cls));
            this.b.a(cls, a2);
        }
        return a2;
    }

    @NonNull
    private static <A> Class<A> a(@NonNull A a2) {
        return (Class<A>) a2.getClass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private final Map<Class<?>, C0043a<?>> a = new HashMap();

        a() {
        }

        public void a() {
            this.a.clear();
        }

        public <Model> void a(Class<Model> cls, List<ModelLoader<Model, ?>> list) {
            if (this.a.put(cls, new C0043a<>(list)) != null) {
                throw new IllegalStateException("Already cached loaders for model: " + cls);
            }
        }

        @Nullable
        public <Model> List<ModelLoader<Model, ?>> a(Class<Model> cls) {
            C0043a<?> aVar = this.a.get(cls);
            if (aVar == null) {
                return null;
            }
            return (List<ModelLoader<Model, ?>>) aVar.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: com.bumptech.glide.load.model.ModelLoaderRegistry$a$a  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public static class C0043a<Model> {
            final List<ModelLoader<Model, ?>> a;

            public C0043a(List<ModelLoader<Model, ?>> list) {
                this.a = list;
            }
        }
    }
}
