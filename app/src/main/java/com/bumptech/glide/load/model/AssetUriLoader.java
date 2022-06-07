package com.bumptech.glide.load.model;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.FileDescriptorAssetPathFetcher;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import java.io.InputStream;

/* loaded from: classes.dex */
public class AssetUriLoader<Data> implements ModelLoader<Uri, Data> {
    private static final int a = 22;
    private final AssetManager b;
    private final AssetFetcherFactory<Data> c;

    /* loaded from: classes.dex */
    public interface AssetFetcherFactory<Data> {
        DataFetcher<Data> buildFetcher(AssetManager assetManager, String str);
    }

    public AssetUriLoader(AssetManager assetManager, AssetFetcherFactory<Data> assetFetcherFactory) {
        this.b = assetManager;
        this.c = assetFetcherFactory;
    }

    public ModelLoader.LoadData<Data> buildLoadData(@NonNull Uri uri, int i, int i2, @NonNull Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(uri), this.c.buildFetcher(this.b, uri.toString().substring(a)));
    }

    public boolean handles(@NonNull Uri uri) {
        return "file".equals(uri.getScheme()) && !uri.getPathSegments().isEmpty() && "android_asset".equals(uri.getPathSegments().get(0));
    }

    /* loaded from: classes.dex */
    public static class StreamFactory implements AssetFetcherFactory<InputStream>, ModelLoaderFactory<Uri, InputStream> {
        private final AssetManager a;

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        public StreamFactory(AssetManager assetManager) {
            this.a = assetManager;
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<Uri, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new AssetUriLoader(this.a, this);
        }

        @Override // com.bumptech.glide.load.model.AssetUriLoader.AssetFetcherFactory
        public DataFetcher<InputStream> buildFetcher(AssetManager assetManager, String str) {
            return new StreamAssetPathFetcher(assetManager, str);
        }
    }

    /* loaded from: classes.dex */
    public static class FileDescriptorFactory implements AssetFetcherFactory<AssetFileDescriptor>, ModelLoaderFactory<Uri, AssetFileDescriptor> {
        private final AssetManager a;

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        public FileDescriptorFactory(AssetManager assetManager) {
            this.a = assetManager;
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<Uri, AssetFileDescriptor> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new AssetUriLoader(this.a, this);
        }

        @Override // com.bumptech.glide.load.model.AssetUriLoader.AssetFetcherFactory
        public DataFetcher<AssetFileDescriptor> buildFetcher(AssetManager assetManager, String str) {
            return new FileDescriptorAssetPathFetcher(assetManager, str);
        }
    }
}
