package com.bumptech.glide.load.model.stream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.google.android.exoplayer2.DefaultLoadControl;
import java.io.InputStream;

/* loaded from: classes.dex */
public class HttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    public static final Option<Integer> TIMEOUT = Option.memory("com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout", Integer.valueOf((int) DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS));
    @Nullable
    private final ModelCache<GlideUrl, GlideUrl> a;

    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    public HttpGlideUrlLoader() {
        this(null);
    }

    public HttpGlideUrlLoader(@Nullable ModelCache<GlideUrl, GlideUrl> modelCache) {
        this.a = modelCache;
    }

    public ModelLoader.LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int i, int i2, @NonNull Options options) {
        ModelCache<GlideUrl, GlideUrl> modelCache = this.a;
        if (modelCache != null) {
            GlideUrl glideUrl2 = modelCache.get(glideUrl, 0, 0);
            if (glideUrl2 == null) {
                this.a.put(glideUrl, 0, 0, glideUrl);
            } else {
                glideUrl = glideUrl2;
            }
        }
        return new ModelLoader.LoadData<>(glideUrl, new HttpUrlFetcher(glideUrl, ((Integer) options.get(TIMEOUT)).intValue()));
    }

    /* loaded from: classes.dex */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private final ModelCache<GlideUrl, GlideUrl> a = new ModelCache<>(500);

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new HttpGlideUrlLoader(this.a);
        }
    }
}
