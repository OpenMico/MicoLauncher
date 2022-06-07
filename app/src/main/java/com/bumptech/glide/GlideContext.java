package com.bumptech.glide;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.ImageView;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.bumptech.glide.request.target.ViewTarget;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class GlideContext extends ContextWrapper {
    @VisibleForTesting
    static final TransitionOptions<?, ?> a = new GenericTransitionOptions();
    private final ArrayPool b;
    private final Registry c;
    private final ImageViewTargetFactory d;
    private final Glide.RequestOptionsFactory e;
    private final List<RequestListener<Object>> f;
    private final Map<Class<?>, TransitionOptions<?, ?>> g;
    private final Engine h;
    private final GlideExperiments i;
    private final int j;
    @Nullable
    @GuardedBy("this")
    private RequestOptions k;

    public GlideContext(@NonNull Context context, @NonNull ArrayPool arrayPool, @NonNull Registry registry, @NonNull ImageViewTargetFactory imageViewTargetFactory, @NonNull Glide.RequestOptionsFactory requestOptionsFactory, @NonNull Map<Class<?>, TransitionOptions<?, ?>> map, @NonNull List<RequestListener<Object>> list, @NonNull Engine engine, @NonNull GlideExperiments glideExperiments, int i) {
        super(context.getApplicationContext());
        this.b = arrayPool;
        this.c = registry;
        this.d = imageViewTargetFactory;
        this.e = requestOptionsFactory;
        this.f = list;
        this.g = map;
        this.h = engine;
        this.i = glideExperiments;
        this.j = i;
    }

    public List<RequestListener<Object>> getDefaultRequestListeners() {
        return this.f;
    }

    public synchronized RequestOptions getDefaultRequestOptions() {
        if (this.k == null) {
            this.k = this.e.build().lock();
        }
        return this.k;
    }

    @NonNull
    public <T> TransitionOptions<?, T> getDefaultTransitionOptions(@NonNull Class<T> cls) {
        TransitionOptions<?, T> transitionOptions = (TransitionOptions<?, T>) this.g.get(cls);
        if (transitionOptions == null) {
            for (Map.Entry<Class<?>, TransitionOptions<?, ?>> entry : this.g.entrySet()) {
                if (entry.getKey().isAssignableFrom(cls)) {
                    transitionOptions = (TransitionOptions<?, T>) entry.getValue();
                }
            }
        }
        return transitionOptions == null ? (TransitionOptions<?, T>) a : transitionOptions;
    }

    @NonNull
    public <X> ViewTarget<ImageView, X> buildImageViewTarget(@NonNull ImageView imageView, @NonNull Class<X> cls) {
        return this.d.buildTarget(imageView, cls);
    }

    @NonNull
    public Engine getEngine() {
        return this.h;
    }

    @NonNull
    public Registry getRegistry() {
        return this.c;
    }

    public int getLogLevel() {
        return this.j;
    }

    @NonNull
    public ArrayPool getArrayPool() {
        return this.b;
    }

    public GlideExperiments getExperiments() {
        return this.i;
    }
}
