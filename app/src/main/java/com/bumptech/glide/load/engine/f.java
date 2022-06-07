package com.bumptech.glide.load.engine;

import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.UnitTransformation;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: DecodeHelper.java */
/* loaded from: classes.dex */
public final class f<Transcode> {
    private final List<ModelLoader.LoadData<?>> a = new ArrayList();
    private final List<Key> b = new ArrayList();
    private GlideContext c;
    private Object d;
    private int e;
    private int f;
    private Class<?> g;
    private g.d h;
    private Options i;
    private Map<Class<?>, Transformation<?>> j;
    private Class<Transcode> k;
    private boolean l;
    private boolean m;
    private Key n;
    private Priority o;
    private DiskCacheStrategy p;
    private boolean q;
    private boolean r;

    /* JADX WARN: Multi-variable type inference failed */
    public <R> void a(GlideContext glideContext, Object obj, Key key, int i, int i2, DiskCacheStrategy diskCacheStrategy, Class<?> cls, Class<R> cls2, Priority priority, Options options, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, g.d dVar) {
        this.c = glideContext;
        this.d = obj;
        this.n = key;
        this.e = i;
        this.f = i2;
        this.p = diskCacheStrategy;
        this.g = cls;
        this.h = dVar;
        this.k = cls2;
        this.o = priority;
        this.i = options;
        this.j = map;
        this.q = z;
        this.r = z2;
    }

    public void a() {
        this.c = null;
        this.d = null;
        this.n = null;
        this.g = null;
        this.k = null;
        this.i = null;
        this.o = null;
        this.j = null;
        this.p = null;
        this.a.clear();
        this.l = false;
        this.b.clear();
        this.m = false;
    }

    public DiskCache b() {
        return this.h.a();
    }

    public DiskCacheStrategy c() {
        return this.p;
    }

    public <T> DataRewinder<T> a(T t) {
        return this.c.getRegistry().getRewinder(t);
    }

    public Priority d() {
        return this.o;
    }

    public Options e() {
        return this.i;
    }

    public Key f() {
        return this.n;
    }

    public int g() {
        return this.e;
    }

    public int h() {
        return this.f;
    }

    public ArrayPool i() {
        return this.c.getArrayPool();
    }

    public Class<?> j() {
        return (Class<Transcode>) this.k;
    }

    public Class<?> k() {
        return this.d.getClass();
    }

    public List<Class<?>> l() {
        return this.c.getRegistry().getRegisteredResourceClasses(this.d.getClass(), this.g, this.k);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean a(Class<?> cls) {
        return b((Class) cls) != null;
    }

    public <Data> LoadPath<Data, ?, Transcode> b(Class<Data> cls) {
        return this.c.getRegistry().getLoadPath(cls, this.g, this.k);
    }

    public boolean m() {
        return this.r;
    }

    public <Z> Transformation<Z> c(Class<Z> cls) {
        Transformation<Z> transformation = (Transformation<Z>) this.j.get(cls);
        if (transformation == null) {
            Iterator<Map.Entry<Class<?>, Transformation<?>>> it = this.j.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<Class<?>, Transformation<?>> next = it.next();
                if (next.getKey().isAssignableFrom(cls)) {
                    transformation = (Transformation<Z>) next.getValue();
                    break;
                }
            }
        }
        if (transformation != null) {
            return transformation;
        }
        if (!this.j.isEmpty() || !this.q) {
            return UnitTransformation.get();
        }
        throw new IllegalArgumentException("Missing transformation for " + cls + ". If you wish to ignore unknown resource types, use the optional transformation methods.");
    }

    public boolean a(Resource<?> resource) {
        return this.c.getRegistry().isResourceEncoderAvailable(resource);
    }

    public <Z> ResourceEncoder<Z> b(Resource<Z> resource) {
        return this.c.getRegistry().getResultEncoder(resource);
    }

    public List<ModelLoader<File, ?>> a(File file) throws Registry.NoModelLoaderAvailableException {
        return this.c.getRegistry().getModelLoaders(file);
    }

    public boolean a(Key key) {
        List<ModelLoader.LoadData<?>> n = n();
        int size = n.size();
        for (int i = 0; i < size; i++) {
            if (n.get(i).sourceKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public List<ModelLoader.LoadData<?>> n() {
        if (!this.l) {
            this.l = true;
            this.a.clear();
            List modelLoaders = this.c.getRegistry().getModelLoaders(this.d);
            int size = modelLoaders.size();
            for (int i = 0; i < size; i++) {
                ModelLoader.LoadData<?> buildLoadData = ((ModelLoader) modelLoaders.get(i)).buildLoadData(this.d, this.e, this.f, this.i);
                if (buildLoadData != null) {
                    this.a.add(buildLoadData);
                }
            }
        }
        return this.a;
    }

    public List<Key> o() {
        if (!this.m) {
            this.m = true;
            this.b.clear();
            List<ModelLoader.LoadData<?>> n = n();
            int size = n.size();
            for (int i = 0; i < size; i++) {
                ModelLoader.LoadData<?> loadData = n.get(i);
                if (!this.b.contains(loadData.sourceKey)) {
                    this.b.add(loadData.sourceKey);
                }
                for (int i2 = 0; i2 < loadData.alternateKeys.size(); i2++) {
                    if (!this.b.contains(loadData.alternateKeys.get(i2))) {
                        this.b.add(loadData.alternateKeys.get(i2));
                    }
                }
            }
        }
        return this.b;
    }

    public <X> Encoder<X> b(X x) throws Registry.NoSourceEncoderAvailableException {
        return this.c.getRegistry().getSourceEncoder(x);
    }
}
