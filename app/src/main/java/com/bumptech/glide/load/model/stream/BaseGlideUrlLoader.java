package com.bumptech.glide.load.model.stream;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseGlideUrlLoader<Model> implements ModelLoader<Model, InputStream> {
    private final ModelLoader<GlideUrl, InputStream> a;
    @Nullable
    private final ModelCache<Model, GlideUrl> b;

    protected abstract String getUrl(Model model, int i, int i2, Options options);

    protected BaseGlideUrlLoader(ModelLoader<GlideUrl, InputStream> modelLoader) {
        this(modelLoader, null);
    }

    protected BaseGlideUrlLoader(ModelLoader<GlideUrl, InputStream> modelLoader, @Nullable ModelCache<Model, GlideUrl> modelCache) {
        this.a = modelLoader;
        this.b = modelCache;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    @Nullable
    public ModelLoader.LoadData<InputStream> buildLoadData(@NonNull Model model, int i, int i2, @NonNull Options options) {
        ModelCache<Model, GlideUrl> modelCache = this.b;
        GlideUrl glideUrl = modelCache != null ? modelCache.get(model, i, i2) : null;
        if (glideUrl == null) {
            String url = getUrl(model, i, i2, options);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            GlideUrl glideUrl2 = new GlideUrl(url, getHeaders(model, i, i2, options));
            ModelCache<Model, GlideUrl> modelCache2 = this.b;
            if (modelCache2 != null) {
                modelCache2.put(model, i, i2, glideUrl2);
            }
            glideUrl = glideUrl2;
        }
        List<String> alternateUrls = getAlternateUrls(model, i, i2, options);
        ModelLoader.LoadData<InputStream> buildLoadData = this.a.buildLoadData(glideUrl, i, i2, options);
        return (buildLoadData == null || alternateUrls.isEmpty()) ? buildLoadData : new ModelLoader.LoadData<>(buildLoadData.sourceKey, a(alternateUrls), buildLoadData.fetcher);
    }

    private static List<Key> a(Collection<String> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (String str : collection) {
            arrayList.add(new GlideUrl(str));
        }
        return arrayList;
    }

    protected List<String> getAlternateUrls(Model model, int i, int i2, Options options) {
        return Collections.emptyList();
    }

    @Nullable
    protected Headers getHeaders(Model model, int i, int i2, Options options) {
        return Headers.DEFAULT;
    }
}
