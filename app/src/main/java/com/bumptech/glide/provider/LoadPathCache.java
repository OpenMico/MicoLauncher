package com.bumptech.glide.provider;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.LoadPath;
import com.bumptech.glide.load.resource.transcode.UnitTranscoder;
import com.bumptech.glide.util.MultiClassKey;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class LoadPathCache {
    private static final LoadPath<?, ?, ?> a = new LoadPath<>(Object.class, Object.class, Object.class, Collections.singletonList(new DecodePath(Object.class, Object.class, Object.class, Collections.emptyList(), new UnitTranscoder(), null)), null);
    private final ArrayMap<MultiClassKey, LoadPath<?, ?, ?>> b = new ArrayMap<>();
    private final AtomicReference<MultiClassKey> c = new AtomicReference<>();

    public boolean isEmptyLoadPath(@Nullable LoadPath<?, ?, ?> loadPath) {
        return a.equals(loadPath);
    }

    @Nullable
    public <Data, TResource, Transcode> LoadPath<Data, TResource, Transcode> get(Class<Data> cls, Class<TResource> cls2, Class<Transcode> cls3) {
        LoadPath<Data, TResource, Transcode> loadPath;
        MultiClassKey a2 = a(cls, cls2, cls3);
        synchronized (this.b) {
            loadPath = (LoadPath<Data, TResource, Transcode>) this.b.get(a2);
        }
        this.c.set(a2);
        return loadPath;
    }

    public void put(Class<?> cls, Class<?> cls2, Class<?> cls3, @Nullable LoadPath<?, ?, ?> loadPath) {
        synchronized (this.b) {
            ArrayMap<MultiClassKey, LoadPath<?, ?, ?>> arrayMap = this.b;
            MultiClassKey multiClassKey = new MultiClassKey(cls, cls2, cls3);
            if (loadPath == null) {
                loadPath = a;
            }
            arrayMap.put(multiClassKey, loadPath);
        }
    }

    private MultiClassKey a(Class<?> cls, Class<?> cls2, Class<?> cls3) {
        MultiClassKey andSet = this.c.getAndSet(null);
        if (andSet == null) {
            andSet = new MultiClassKey();
        }
        andSet.set(cls, cls2, cls3);
        return andSet;
    }
}
