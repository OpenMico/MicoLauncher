package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;

/* loaded from: classes.dex */
public class UnitModelLoader<Model> implements ModelLoader<Model, Model> {
    private static final UnitModelLoader<?> a = new UnitModelLoader<>();

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(@NonNull Model model) {
        return true;
    }

    public static <T> UnitModelLoader<T> getInstance() {
        return (UnitModelLoader<T>) a;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData<Model> buildLoadData(@NonNull Model model, int i, int i2, @NonNull Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(model), new a(model));
    }

    /* loaded from: classes.dex */
    private static class a<Model> implements DataFetcher<Model> {
        private final Model a;

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cancel() {
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cleanup() {
        }

        a(Model model) {
            this.a = model;
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super Model> dataCallback) {
            dataCallback.onDataReady((Model) this.a);
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public Class<Model> getDataClass() {
            return (Class<Model>) this.a.getClass();
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }
    }

    /* loaded from: classes.dex */
    public static class Factory<Model> implements ModelLoaderFactory<Model, Model> {
        private static final Factory<?> a = new Factory<>();

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        public static <T> Factory<T> getInstance() {
            return (Factory<T>) a;
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public ModelLoader<Model, Model> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return UnitModelLoader.getInstance();
        }
    }
}
