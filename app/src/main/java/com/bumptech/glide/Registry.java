package com.bumptech.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.data.DataRewinderRegistry;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.LoadPath;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.ModelLoaderRegistry;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.load.resource.transcode.TranscoderRegistry;
import com.bumptech.glide.provider.EncoderRegistry;
import com.bumptech.glide.provider.ImageHeaderParserRegistry;
import com.bumptech.glide.provider.LoadPathCache;
import com.bumptech.glide.provider.ModelToResourceClassCache;
import com.bumptech.glide.provider.ResourceDecoderRegistry;
import com.bumptech.glide.provider.ResourceEncoderRegistry;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class Registry {
    public static final String BUCKET_ANIMATION = "Animation";
    public static final String BUCKET_BITMAP = "Bitmap";
    public static final String BUCKET_BITMAP_DRAWABLE = "BitmapDrawable";
    @Deprecated
    public static final String BUCKET_GIF = "Animation";
    private final ModelToResourceClassCache h = new ModelToResourceClassCache();
    private final LoadPathCache i = new LoadPathCache();
    private final Pools.Pool<List<Throwable>> j = FactoryPools.threadSafeList();
    private final ModelLoaderRegistry a = new ModelLoaderRegistry(this.j);
    private final EncoderRegistry b = new EncoderRegistry();
    private final ResourceDecoderRegistry c = new ResourceDecoderRegistry();
    private final ResourceEncoderRegistry d = new ResourceEncoderRegistry();
    private final DataRewinderRegistry e = new DataRewinderRegistry();
    private final TranscoderRegistry f = new TranscoderRegistry();
    private final ImageHeaderParserRegistry g = new ImageHeaderParserRegistry();

    public Registry() {
        setResourceDecoderBucketPriorityList(Arrays.asList("Animation", BUCKET_BITMAP, BUCKET_BITMAP_DRAWABLE));
    }

    @NonNull
    @Deprecated
    public <Data> Registry register(@NonNull Class<Data> cls, @NonNull Encoder<Data> encoder) {
        return append(cls, encoder);
    }

    @NonNull
    public <Data> Registry append(@NonNull Class<Data> cls, @NonNull Encoder<Data> encoder) {
        this.b.append(cls, encoder);
        return this;
    }

    @NonNull
    public <Data> Registry prepend(@NonNull Class<Data> cls, @NonNull Encoder<Data> encoder) {
        this.b.prepend(cls, encoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry append(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull ResourceDecoder<Data, TResource> resourceDecoder) {
        append("legacy_append", cls, cls2, resourceDecoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry append(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull ResourceDecoder<Data, TResource> resourceDecoder) {
        this.c.append(str, resourceDecoder, cls, cls2);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry prepend(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull ResourceDecoder<Data, TResource> resourceDecoder) {
        prepend("legacy_prepend_all", cls, cls2, resourceDecoder);
        return this;
    }

    @NonNull
    public <Data, TResource> Registry prepend(@NonNull String str, @NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull ResourceDecoder<Data, TResource> resourceDecoder) {
        this.c.prepend(str, resourceDecoder, cls, cls2);
        return this;
    }

    @NonNull
    public final Registry setResourceDecoderBucketPriorityList(@NonNull List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        arrayList.add("legacy_prepend_all");
        for (String str : list) {
            arrayList.add(str);
        }
        arrayList.add("legacy_append");
        this.c.setBucketPriorityList(arrayList);
        return this;
    }

    @NonNull
    @Deprecated
    public <TResource> Registry register(@NonNull Class<TResource> cls, @NonNull ResourceEncoder<TResource> resourceEncoder) {
        return append((Class) cls, (ResourceEncoder) resourceEncoder);
    }

    @NonNull
    public <TResource> Registry append(@NonNull Class<TResource> cls, @NonNull ResourceEncoder<TResource> resourceEncoder) {
        this.d.append(cls, resourceEncoder);
        return this;
    }

    @NonNull
    public <TResource> Registry prepend(@NonNull Class<TResource> cls, @NonNull ResourceEncoder<TResource> resourceEncoder) {
        this.d.prepend(cls, resourceEncoder);
        return this;
    }

    @NonNull
    public Registry register(@NonNull DataRewinder.Factory<?> factory) {
        this.e.register(factory);
        return this;
    }

    @NonNull
    public <TResource, Transcode> Registry register(@NonNull Class<TResource> cls, @NonNull Class<Transcode> cls2, @NonNull ResourceTranscoder<TResource, Transcode> resourceTranscoder) {
        this.f.register(cls, cls2, resourceTranscoder);
        return this;
    }

    @NonNull
    public Registry register(@NonNull ImageHeaderParser imageHeaderParser) {
        this.g.add(imageHeaderParser);
        return this;
    }

    @NonNull
    public <Model, Data> Registry append(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<Model, Data> modelLoaderFactory) {
        this.a.append(cls, cls2, modelLoaderFactory);
        return this;
    }

    @NonNull
    public <Model, Data> Registry prepend(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<Model, Data> modelLoaderFactory) {
        this.a.prepend(cls, cls2, modelLoaderFactory);
        return this;
    }

    @NonNull
    public <Model, Data> Registry replace(@NonNull Class<Model> cls, @NonNull Class<Data> cls2, @NonNull ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        this.a.replace(cls, cls2, modelLoaderFactory);
        return this;
    }

    @Nullable
    public <Data, TResource, Transcode> LoadPath<Data, TResource, Transcode> getLoadPath(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        LoadPath<Data, TResource, Transcode> loadPath = this.i.get(cls, cls2, cls3);
        if (this.i.isEmptyLoadPath(loadPath)) {
            return null;
        }
        if (loadPath == null) {
            List<DecodePath<Data, TResource, Transcode>> a = a(cls, cls2, cls3);
            loadPath = a.isEmpty() ? null : new LoadPath<>(cls, cls2, cls3, a, this.j);
            this.i.put(cls, cls2, cls3, loadPath);
        }
        return loadPath;
    }

    @NonNull
    private <Data, TResource, Transcode> List<DecodePath<Data, TResource, Transcode>> a(@NonNull Class<Data> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        ArrayList arrayList = new ArrayList();
        for (Class cls4 : this.c.getResourceClasses(cls, cls2)) {
            for (Class cls5 : this.f.getTranscodeClasses(cls4, cls3)) {
                arrayList.add(new DecodePath(cls, cls4, cls5, this.c.getDecoders(cls, cls4), this.f.get(cls4, cls5), this.j));
            }
        }
        return arrayList;
    }

    @NonNull
    public <Model, TResource, Transcode> List<Class<?>> getRegisteredResourceClasses(@NonNull Class<Model> cls, @NonNull Class<TResource> cls2, @NonNull Class<Transcode> cls3) {
        List<Class<?>> list = this.h.get(cls, cls2, cls3);
        if (list == null) {
            list = new ArrayList<>();
            for (Class<?> cls4 : this.a.getDataClasses(cls)) {
                for (Class<?> cls5 : this.c.getResourceClasses(cls4, cls2)) {
                    if (!this.f.getTranscodeClasses(cls5, cls3).isEmpty() && !list.contains(cls5)) {
                        list.add(cls5);
                    }
                }
            }
            this.h.put(cls, cls2, cls3, Collections.unmodifiableList(list));
        }
        return list;
    }

    public boolean isResourceEncoderAvailable(@NonNull Resource<?> resource) {
        return this.d.get(resource.getResourceClass()) != null;
    }

    @NonNull
    public <X> ResourceEncoder<X> getResultEncoder(@NonNull Resource<X> resource) throws NoResultEncoderAvailableException {
        ResourceEncoder<X> resourceEncoder = this.d.get(resource.getResourceClass());
        if (resourceEncoder != null) {
            return resourceEncoder;
        }
        throw new NoResultEncoderAvailableException(resource.getResourceClass());
    }

    @NonNull
    public <X> Encoder<X> getSourceEncoder(@NonNull X x) throws NoSourceEncoderAvailableException {
        Encoder<X> encoder = this.b.getEncoder(x.getClass());
        if (encoder != null) {
            return encoder;
        }
        throw new NoSourceEncoderAvailableException(x.getClass());
    }

    @NonNull
    public <X> DataRewinder<X> getRewinder(@NonNull X x) {
        return this.e.build(x);
    }

    @NonNull
    public <Model> List<ModelLoader<Model, ?>> getModelLoaders(@NonNull Model model) {
        return this.a.getModelLoaders(model);
    }

    @NonNull
    public List<ImageHeaderParser> getImageHeaderParsers() {
        List<ImageHeaderParser> parsers = this.g.getParsers();
        if (!parsers.isEmpty()) {
            return parsers;
        }
        throw new NoImageHeaderParserException();
    }

    /* loaded from: classes.dex */
    public static class NoModelLoaderAvailableException extends MissingComponentException {
        public NoModelLoaderAvailableException(@NonNull Object obj) {
            super("Failed to find any ModelLoaders registered for model class: " + obj.getClass());
        }

        public <M> NoModelLoaderAvailableException(@NonNull M m, @NonNull List<ModelLoader<M, ?>> list) {
            super("Found ModelLoaders for model class: " + list + ", but none that handle this specific model instance: " + m);
        }

        public NoModelLoaderAvailableException(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            super("Failed to find any ModelLoaders for model: " + cls + " and data: " + cls2);
        }
    }

    /* loaded from: classes.dex */
    public static class NoResultEncoderAvailableException extends MissingComponentException {
        public NoResultEncoderAvailableException(@NonNull Class<?> cls) {
            super("Failed to find result encoder for resource class: " + cls + ", you may need to consider registering a new Encoder for the requested type or DiskCacheStrategy.DATA/DiskCacheStrategy.NONE if caching your transformed resource is unnecessary.");
        }
    }

    /* loaded from: classes.dex */
    public static class NoSourceEncoderAvailableException extends MissingComponentException {
        public NoSourceEncoderAvailableException(@NonNull Class<?> cls) {
            super("Failed to find source encoder for data class: " + cls);
        }
    }

    /* loaded from: classes.dex */
    public static class MissingComponentException extends RuntimeException {
        public MissingComponentException(@NonNull String str) {
            super(str);
        }
    }

    /* loaded from: classes.dex */
    public static final class NoImageHeaderParserException extends MissingComponentException {
        public NoImageHeaderParserException() {
            super("Failed to find image header parser.");
        }
    }
}
