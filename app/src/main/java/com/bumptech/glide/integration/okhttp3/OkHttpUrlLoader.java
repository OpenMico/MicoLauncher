package com.bumptech.glide.integration.okhttp3;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/* loaded from: classes.dex */
public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private final Call.Factory a;

    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    public OkHttpUrlLoader(@NonNull Call.Factory factory) {
        this.a = factory;
    }

    public ModelLoader.LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int i, int i2, @NonNull Options options) {
        return new ModelLoader.LoadData<>(glideUrl, new OkHttpStreamFetcher(this.a, glideUrl));
    }

    /* loaded from: classes.dex */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private static volatile Call.Factory a;
        private final Call.Factory b;

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        private static Call.Factory a() {
            if (a == null) {
                synchronized (Factory.class) {
                    if (a == null) {
                        a = new OkHttpClient();
                    }
                }
            }
            return a;
        }

        public Factory() {
            this(a());
        }

        public Factory(@NonNull Call.Factory factory) {
            this.b = factory;
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new OkHttpUrlLoader(this.b);
        }
    }
}
