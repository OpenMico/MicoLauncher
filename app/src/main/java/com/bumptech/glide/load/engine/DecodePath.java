package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.util.Preconditions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class DecodePath<DataType, ResourceType, Transcode> {
    private final Class<DataType> a;
    private final List<? extends ResourceDecoder<DataType, ResourceType>> b;
    private final ResourceTranscoder<ResourceType, Transcode> c;
    private final Pools.Pool<List<Throwable>> d;
    private final String e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface a<ResourceType> {
        @NonNull
        Resource<ResourceType> a(@NonNull Resource<ResourceType> resource);
    }

    public DecodePath(Class<DataType> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<? extends ResourceDecoder<DataType, ResourceType>> list, ResourceTranscoder<ResourceType, Transcode> resourceTranscoder, Pools.Pool<List<Throwable>> pool) {
        this.a = cls;
        this.b = list;
        this.c = resourceTranscoder;
        this.d = pool;
        this.e = "Failed DecodePath{" + cls.getSimpleName() + "->" + cls2.getSimpleName() + "->" + cls3.getSimpleName() + "}";
    }

    public Resource<Transcode> decode(DataRewinder<DataType> dataRewinder, int i, int i2, @NonNull Options options, a<ResourceType> aVar) throws GlideException {
        return this.c.transcode(aVar.a(a(dataRewinder, i, i2, options)), options);
    }

    @NonNull
    private Resource<ResourceType> a(DataRewinder<DataType> dataRewinder, int i, int i2, @NonNull Options options) throws GlideException {
        List<Throwable> list = (List) Preconditions.checkNotNull(this.d.acquire());
        try {
            return a(dataRewinder, i, i2, options, list);
        } finally {
            this.d.release(list);
        }
    }

    @NonNull
    private Resource<ResourceType> a(DataRewinder<DataType> dataRewinder, int i, int i2, @NonNull Options options, List<Throwable> list) throws GlideException {
        int size = this.b.size();
        Resource<ResourceType> resource = null;
        for (int i3 = 0; i3 < size; i3++) {
            ResourceDecoder resourceDecoder = (ResourceDecoder) this.b.get(i3);
            try {
                if (resourceDecoder.handles(dataRewinder.rewindAndGet(), options)) {
                    resource = resourceDecoder.decode(dataRewinder.rewindAndGet(), i, i2, options);
                }
            } catch (IOException | OutOfMemoryError | RuntimeException e) {
                if (Log.isLoggable("DecodePath", 2)) {
                    Log.v("DecodePath", "Failed to decode data for " + resourceDecoder, e);
                }
                list.add(e);
            }
            if (resource != null) {
                break;
            }
        }
        if (resource != null) {
            return resource;
        }
        throw new GlideException(this.e, new ArrayList(list));
    }

    public String toString() {
        return "DecodePath{ dataClass=" + this.a + ", decoders=" + this.b + ", transcoder=" + this.c + '}';
    }
}
