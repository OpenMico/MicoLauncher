package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.signature.ObjectKey;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RequiresApi(29)
/* loaded from: classes.dex */
public final class QMediaStoreUriLoader<DataT> implements ModelLoader<Uri, DataT> {
    private final Context a;
    private final ModelLoader<File, DataT> b;
    private final ModelLoader<Uri, DataT> c;
    private final Class<DataT> d;

    QMediaStoreUriLoader(Context context, ModelLoader<File, DataT> modelLoader, ModelLoader<Uri, DataT> modelLoader2, Class<DataT> cls) {
        this.a = context.getApplicationContext();
        this.b = modelLoader;
        this.c = modelLoader2;
        this.d = cls;
    }

    public ModelLoader.LoadData<DataT> buildLoadData(@NonNull Uri uri, int i, int i2, @NonNull Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(uri), new b(this.a, this.b, this.c, uri, i, i2, options, this.d));
    }

    public boolean handles(@NonNull Uri uri) {
        return Build.VERSION.SDK_INT >= 29 && MediaStoreUtil.isMediaStoreUri(uri);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b<DataT> implements DataFetcher<DataT> {
        private static final String[] a = {"_data"};
        private final Context b;
        private final ModelLoader<File, DataT> c;
        private final ModelLoader<Uri, DataT> d;
        private final Uri e;
        private final int f;
        private final int g;
        private final Options h;
        private final Class<DataT> i;
        private volatile boolean j;
        @Nullable
        private volatile DataFetcher<DataT> k;

        b(Context context, ModelLoader<File, DataT> modelLoader, ModelLoader<Uri, DataT> modelLoader2, Uri uri, int i, int i2, Options options, Class<DataT> cls) {
            this.b = context.getApplicationContext();
            this.c = modelLoader;
            this.d = modelLoader2;
            this.e = uri;
            this.f = i;
            this.g = i2;
            this.h = options;
            this.i = cls;
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super DataT> dataCallback) {
            try {
                DataFetcher<DataT> a2 = a();
                if (a2 == null) {
                    dataCallback.onLoadFailed(new IllegalArgumentException("Failed to build fetcher for: " + this.e));
                    return;
                }
                this.k = a2;
                if (this.j) {
                    cancel();
                } else {
                    a2.loadData(priority, dataCallback);
                }
            } catch (FileNotFoundException e) {
                dataCallback.onLoadFailed(e);
            }
        }

        @Nullable
        private DataFetcher<DataT> a() throws FileNotFoundException {
            ModelLoader.LoadData<DataT> b = b();
            if (b != null) {
                return b.fetcher;
            }
            return null;
        }

        @Nullable
        private ModelLoader.LoadData<DataT> b() throws FileNotFoundException {
            if (Environment.isExternalStorageLegacy()) {
                return this.c.buildLoadData(a(this.e), this.f, this.g, this.h);
            }
            return this.d.buildLoadData(c() ? MediaStore.setRequireOriginal(this.e) : this.e, this.f, this.g, this.h);
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cleanup() {
            DataFetcher<DataT> dataFetcher = this.k;
            if (dataFetcher != null) {
                dataFetcher.cleanup();
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cancel() {
            this.j = true;
            DataFetcher<DataT> dataFetcher = this.k;
            if (dataFetcher != null) {
                dataFetcher.cancel();
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public Class<DataT> getDataClass() {
            return this.i;
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }

        @NonNull
        private File a(Uri uri) throws FileNotFoundException {
            Cursor cursor = null;
            try {
                cursor = this.b.getContentResolver().query(uri, a, null, null, null);
                if (cursor == null || !cursor.moveToFirst()) {
                    throw new FileNotFoundException("Failed to media store entry for: " + uri);
                }
                String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                if (!TextUtils.isEmpty(string)) {
                    return new File(string);
                }
                throw new FileNotFoundException("File path was empty in media store for: " + uri);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        private boolean c() {
            return this.b.checkSelfPermission("android.permission.ACCESS_MEDIA_LOCATION") == 0;
        }
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static final class InputStreamFactory extends a<InputStream> {
        public InputStreamFactory(Context context) {
            super(context, InputStream.class);
        }
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static final class FileDescriptorFactory extends a<ParcelFileDescriptor> {
        public FileDescriptorFactory(Context context) {
            super(context, ParcelFileDescriptor.class);
        }
    }

    /* loaded from: classes.dex */
    private static abstract class a<DataT> implements ModelLoaderFactory<Uri, DataT> {
        private final Context a;
        private final Class<DataT> b;

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public final void teardown() {
        }

        a(Context context, Class<DataT> cls) {
            this.a = context;
            this.b = cls;
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        @NonNull
        public final ModelLoader<Uri, DataT> build(@NonNull MultiModelLoaderFactory multiModelLoaderFactory) {
            return new QMediaStoreUriLoader(this.a, multiModelLoaderFactory.build(File.class, this.b), multiModelLoaderFactory.build(Uri.class, this.b), this.b);
        }
    }
}
