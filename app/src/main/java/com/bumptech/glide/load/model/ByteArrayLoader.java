package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class ByteArrayLoader<Data> implements ModelLoader<byte[], Data> {
    private final Converter<Data> a;

    /* loaded from: classes.dex */
    public interface Converter<Data> {
        Data convert(byte[] bArr);

        Class<Data> getDataClass();
    }

    public boolean handles(@NonNull byte[] bArr) {
        return true;
    }

    public ByteArrayLoader(Converter<Data> converter) {
        this.a = converter;
    }

    public ModelLoader.LoadData<Data> buildLoadData(@NonNull byte[] bArr, int i, int i2, @NonNull Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(bArr), new a(bArr, this.a));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a<Data> implements DataFetcher<Data> {
        private final byte[] a;
        private final Converter<Data> b;

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cleanup() {
        }

        a(byte[] bArr, Converter<Data> converter) {
            this.a = bArr;
            this.b = converter;
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super Data> dataCallback) {
            dataCallback.onDataReady((Data) this.b.convert(this.a));
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public Class<Data> getDataClass() {
            return this.b.getDataClass();
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }
    }

    /* loaded from: classes.dex */
    public static class ByteBufferFactory implements ModelLoaderFactory<byte[], ByteBuffer> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<byte[], ByteBuffer> build(@NonNull MultiModelLoaderFactory multiModelLoaderFactory) {
            return new ByteArrayLoader(new Converter<ByteBuffer>() { // from class: com.bumptech.glide.load.model.ByteArrayLoader.ByteBufferFactory.1
                /* renamed from: a */
                public ByteBuffer convert(byte[] bArr) {
                    return ByteBuffer.wrap(bArr);
                }

                @Override // com.bumptech.glide.load.model.ByteArrayLoader.Converter
                public Class<ByteBuffer> getDataClass() {
                    return ByteBuffer.class;
                }
            });
        }
    }

    /* loaded from: classes.dex */
    public static class StreamFactory implements ModelLoaderFactory<byte[], InputStream> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<byte[], InputStream> build(@NonNull MultiModelLoaderFactory multiModelLoaderFactory) {
            return new ByteArrayLoader(new Converter<InputStream>() { // from class: com.bumptech.glide.load.model.ByteArrayLoader.StreamFactory.1
                /* renamed from: a */
                public InputStream convert(byte[] bArr) {
                    return new ByteArrayInputStream(bArr);
                }

                @Override // com.bumptech.glide.load.model.ByteArrayLoader.Converter
                public Class<InputStream> getDataClass() {
                    return InputStream.class;
                }
            });
        }
    }
}
