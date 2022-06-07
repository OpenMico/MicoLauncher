package com.bumptech.glide;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideExperiments;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.load.engine.bitmap_recycle.LruArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.DefaultConnectivityMonitorFactory;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class GlideBuilder {
    private Engine c;
    private BitmapPool d;
    private ArrayPool e;
    private MemoryCache f;
    private GlideExecutor g;
    private GlideExecutor h;
    private DiskCache.Factory i;
    private MemorySizeCalculator j;
    private ConnectivityMonitorFactory k;
    @Nullable
    private RequestManagerRetriever.RequestManagerFactory n;
    private GlideExecutor o;
    private boolean p;
    @Nullable
    private List<RequestListener<Object>> q;
    private final Map<Class<?>, TransitionOptions<?, ?>> a = new ArrayMap();
    private final GlideExperiments.a b = new GlideExperiments.a();
    private int l = 4;
    private Glide.RequestOptionsFactory m = new Glide.RequestOptionsFactory() { // from class: com.bumptech.glide.GlideBuilder.1
        @Override // com.bumptech.glide.Glide.RequestOptionsFactory
        @NonNull
        public RequestOptions build() {
            return new RequestOptions();
        }
    };

    /* loaded from: classes.dex */
    public static final class LogRequestOrigins implements GlideExperiments.b {
    }

    @NonNull
    public GlideBuilder setBitmapPool(@Nullable BitmapPool bitmapPool) {
        this.d = bitmapPool;
        return this;
    }

    @NonNull
    public GlideBuilder setArrayPool(@Nullable ArrayPool arrayPool) {
        this.e = arrayPool;
        return this;
    }

    @NonNull
    public GlideBuilder setMemoryCache(@Nullable MemoryCache memoryCache) {
        this.f = memoryCache;
        return this;
    }

    @NonNull
    public GlideBuilder setDiskCache(@Nullable DiskCache.Factory factory) {
        this.i = factory;
        return this;
    }

    @Deprecated
    public GlideBuilder setResizeExecutor(@Nullable GlideExecutor glideExecutor) {
        return setSourceExecutor(glideExecutor);
    }

    @NonNull
    public GlideBuilder setSourceExecutor(@Nullable GlideExecutor glideExecutor) {
        this.g = glideExecutor;
        return this;
    }

    @NonNull
    public GlideBuilder setDiskCacheExecutor(@Nullable GlideExecutor glideExecutor) {
        this.h = glideExecutor;
        return this;
    }

    @NonNull
    public GlideBuilder setAnimationExecutor(@Nullable GlideExecutor glideExecutor) {
        this.o = glideExecutor;
        return this;
    }

    @NonNull
    public GlideBuilder setDefaultRequestOptions(@Nullable final RequestOptions requestOptions) {
        return setDefaultRequestOptions(new Glide.RequestOptionsFactory() { // from class: com.bumptech.glide.GlideBuilder.2
            @Override // com.bumptech.glide.Glide.RequestOptionsFactory
            @NonNull
            public RequestOptions build() {
                RequestOptions requestOptions2 = requestOptions;
                return requestOptions2 != null ? requestOptions2 : new RequestOptions();
            }
        });
    }

    @NonNull
    public GlideBuilder setDefaultRequestOptions(@NonNull Glide.RequestOptionsFactory requestOptionsFactory) {
        this.m = (Glide.RequestOptionsFactory) Preconditions.checkNotNull(requestOptionsFactory);
        return this;
    }

    @NonNull
    public <T> GlideBuilder setDefaultTransitionOptions(@NonNull Class<T> cls, @Nullable TransitionOptions<?, T> transitionOptions) {
        this.a.put(cls, transitionOptions);
        return this;
    }

    @NonNull
    public GlideBuilder setMemorySizeCalculator(@NonNull MemorySizeCalculator.Builder builder) {
        return setMemorySizeCalculator(builder.build());
    }

    @NonNull
    public GlideBuilder setMemorySizeCalculator(@Nullable MemorySizeCalculator memorySizeCalculator) {
        this.j = memorySizeCalculator;
        return this;
    }

    @NonNull
    public GlideBuilder setConnectivityMonitorFactory(@Nullable ConnectivityMonitorFactory connectivityMonitorFactory) {
        this.k = connectivityMonitorFactory;
        return this;
    }

    @NonNull
    public GlideBuilder setLogLevel(int i) {
        if (i < 2 || i > 6) {
            throw new IllegalArgumentException("Log level must be one of Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, or Log.ERROR");
        }
        this.l = i;
        return this;
    }

    @NonNull
    public GlideBuilder setIsActiveResourceRetentionAllowed(boolean z) {
        this.p = z;
        return this;
    }

    @NonNull
    public GlideBuilder addGlobalRequestListener(@NonNull RequestListener<Object> requestListener) {
        if (this.q == null) {
            this.q = new ArrayList();
        }
        this.q.add(requestListener);
        return this;
    }

    public GlideBuilder setLogRequestOrigins(boolean z) {
        this.b.a(new LogRequestOrigins(), z);
        return this;
    }

    public GlideBuilder setImageDecoderEnabledForBitmaps(boolean z) {
        this.b.a(new b(), z && Build.VERSION.SDK_INT >= 29);
        return this;
    }

    public GlideBuilder setEnableImageDecoderForAnimatedWebp(boolean z) {
        this.b.a(new a(), z);
        return this;
    }

    public void a(@Nullable RequestManagerRetriever.RequestManagerFactory requestManagerFactory) {
        this.n = requestManagerFactory;
    }

    @NonNull
    public Glide a(@NonNull Context context) {
        if (this.g == null) {
            this.g = GlideExecutor.newSourceExecutor();
        }
        if (this.h == null) {
            this.h = GlideExecutor.newDiskCacheExecutor();
        }
        if (this.o == null) {
            this.o = GlideExecutor.newAnimationExecutor();
        }
        if (this.j == null) {
            this.j = new MemorySizeCalculator.Builder(context).build();
        }
        if (this.k == null) {
            this.k = new DefaultConnectivityMonitorFactory();
        }
        if (this.d == null) {
            int bitmapPoolSize = this.j.getBitmapPoolSize();
            if (bitmapPoolSize > 0) {
                this.d = new LruBitmapPool(bitmapPoolSize);
            } else {
                this.d = new BitmapPoolAdapter();
            }
        }
        if (this.e == null) {
            this.e = new LruArrayPool(this.j.getArrayPoolSizeInBytes());
        }
        if (this.f == null) {
            this.f = new LruResourceCache(this.j.getMemoryCacheSize());
        }
        if (this.i == null) {
            this.i = new InternalCacheDiskCacheFactory(context);
        }
        if (this.c == null) {
            this.c = new Engine(this.f, this.i, this.h, this.g, GlideExecutor.newUnlimitedSourceExecutor(), this.o, this.p);
        }
        List<RequestListener<Object>> list = this.q;
        if (list == null) {
            this.q = Collections.emptyList();
        } else {
            this.q = Collections.unmodifiableList(list);
        }
        GlideExperiments a2 = this.b.a();
        return new Glide(context, this.c, this.f, this.d, this.e, new RequestManagerRetriever(this.n, a2), this.k, this.l, this.m, this.a, this.q, a2);
    }

    /* loaded from: classes.dex */
    public static final class WaitForFramesAfterTrimMemory implements GlideExperiments.b {
        private WaitForFramesAfterTrimMemory() {
        }
    }

    /* loaded from: classes.dex */
    public static final class b implements GlideExperiments.b {
        b() {
        }
    }

    /* loaded from: classes.dex */
    public static final class a implements GlideExperiments.b {
        a() {
        }
    }
}
