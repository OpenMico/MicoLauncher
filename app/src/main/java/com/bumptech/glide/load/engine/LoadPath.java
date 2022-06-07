package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class LoadPath<Data, ResourceType, Transcode> {
    private final Class<Data> a;
    private final Pools.Pool<List<Throwable>> b;
    private final List<? extends DecodePath<Data, ResourceType, Transcode>> c;
    private final String d;

    public LoadPath(Class<Data> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<DecodePath<Data, ResourceType, Transcode>> list, Pools.Pool<List<Throwable>> pool) {
        this.a = cls;
        this.b = pool;
        this.c = (List) Preconditions.checkNotEmpty(list);
        this.d = "Failed LoadPath{" + cls.getSimpleName() + "->" + cls2.getSimpleName() + "->" + cls3.getSimpleName() + "}";
    }

    public Resource<Transcode> load(DataRewinder<Data> dataRewinder, @NonNull Options options, int i, int i2, DecodePath.a<ResourceType> aVar) throws GlideException {
        List<Throwable> list = (List) Preconditions.checkNotNull(this.b.acquire());
        try {
            return a(dataRewinder, options, i, i2, aVar, list);
        } finally {
            this.b.release(list);
        }
    }

    private Resource<Transcode> a(DataRewinder<Data> dataRewinder, @NonNull Options options, int i, int i2, DecodePath.a<ResourceType> aVar, List<Throwable> list) throws GlideException {
        int size = this.c.size();
        Resource<Transcode> resource = null;
        for (int i3 = 0; i3 < size; i3++) {
            try {
                resource = ((DecodePath) this.c.get(i3)).decode(dataRewinder, i, i2, options, aVar);
            } catch (GlideException e) {
                list.add(e);
            }
            if (resource != null) {
                break;
            }
        }
        if (resource != null) {
            return resource;
        }
        throw new GlideException(this.d, new ArrayList(list));
    }

    public Class<Data> getDataClass() {
        return this.a;
    }

    public String toString() {
        return "LoadPath{decodePaths=" + Arrays.toString(this.c.toArray()) + '}';
    }
}
