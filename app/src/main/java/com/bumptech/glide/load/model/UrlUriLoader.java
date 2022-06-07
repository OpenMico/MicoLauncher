package com.bumptech.glide.load.model;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class UrlUriLoader<Data> implements ModelLoader<Uri, Data> {
    private static final Set<String> a = Collections.unmodifiableSet(new HashSet(Arrays.asList("http", "https")));
    private final ModelLoader<GlideUrl, Data> b;

    public UrlUriLoader(ModelLoader<GlideUrl, Data> modelLoader) {
        this.b = modelLoader;
    }

    public ModelLoader.LoadData<Data> buildLoadData(@NonNull Uri uri, int i, int i2, @NonNull Options options) {
        return this.b.buildLoadData(new GlideUrl(uri.toString()), i, i2, options);
    }

    public boolean handles(@NonNull Uri uri) {
        return a.contains(uri.getScheme());
    }

    /* loaded from: classes.dex */
    public static class StreamFactory implements ModelLoaderFactory<Uri, InputStream> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<Uri, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new UrlUriLoader(multiModelLoaderFactory.build(GlideUrl.class, InputStream.class));
        }
    }
}
