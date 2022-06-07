package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.LogTime;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SourceGenerator.java */
/* loaded from: classes.dex */
public class r implements DataFetcherGenerator, DataFetcherGenerator.FetcherReadyCallback {
    private final f<?> a;
    private final DataFetcherGenerator.FetcherReadyCallback b;
    private volatile int c;
    private volatile c d;
    private volatile Object e;
    private volatile ModelLoader.LoadData<?> f;
    private volatile d g;

    /* JADX INFO: Access modifiers changed from: package-private */
    public r(f<?> fVar, DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback) {
        this.a = fVar;
        this.b = fetcherReadyCallback;
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public boolean a() {
        if (this.e != null) {
            Object obj = this.e;
            this.e = null;
            try {
                if (!a(obj)) {
                    return true;
                }
            } catch (IOException e) {
                if (Log.isLoggable("SourceGenerator", 3)) {
                    Log.d("SourceGenerator", "Failed to properly rewind or write data to cache", e);
                }
            }
        }
        if (this.d != null && this.d.a()) {
            return true;
        }
        this.d = null;
        this.f = null;
        boolean z = false;
        while (!z && c()) {
            List<ModelLoader.LoadData<?>> n = this.a.n();
            int i = this.c;
            this.c = i + 1;
            this.f = n.get(i);
            if (this.f != null && (this.a.c().isDataCacheable(this.f.fetcher.getDataSource()) || this.a.a(this.f.fetcher.getDataClass()))) {
                b(this.f);
                z = true;
            }
        }
        return z;
    }

    private void b(final ModelLoader.LoadData<?> loadData) {
        this.f.fetcher.loadData(this.a.d(), new DataFetcher.DataCallback<Object>() { // from class: com.bumptech.glide.load.engine.r.1
            @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
            public void onDataReady(@Nullable Object obj) {
                if (r.this.a(loadData)) {
                    r.this.a(loadData, obj);
                }
            }

            @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
            public void onLoadFailed(@NonNull Exception exc) {
                if (r.this.a(loadData)) {
                    r.this.a(loadData, exc);
                }
            }
        });
    }

    boolean a(ModelLoader.LoadData<?> loadData) {
        ModelLoader.LoadData<?> loadData2 = this.f;
        return loadData2 != null && loadData2 == loadData;
    }

    private boolean c() {
        return this.c < this.a.n().size();
    }

    private boolean a(Object obj) throws IOException {
        Throwable th;
        long logTime = LogTime.getLogTime();
        boolean z = false;
        try {
            DataRewinder<T> a = this.a.a((f<?>) obj);
            Object rewindAndGet = a.rewindAndGet();
            Encoder<X> b = this.a.b((f<?>) rewindAndGet);
            e eVar = new e(b, rewindAndGet, this.a.e());
            d dVar = new d(this.f.sourceKey, this.a.f());
            DiskCache b2 = this.a.b();
            b2.put(dVar, eVar);
            if (Log.isLoggable("SourceGenerator", 2)) {
                Log.v("SourceGenerator", "Finished encoding source to cache, key: " + dVar + ", data: " + obj + ", encoder: " + b + ", duration: " + LogTime.getElapsedMillis(logTime));
            }
            if (b2.get(dVar) != null) {
                this.g = dVar;
                this.d = new c(Collections.singletonList(this.f.sourceKey), this.a, this);
                this.f.fetcher.cleanup();
                return true;
            }
            if (Log.isLoggable("SourceGenerator", 3)) {
                Log.d("SourceGenerator", "Attempt to write: " + this.g + ", data: " + obj + " to the disk cache failed, maybe the disk cache is disabled? Trying to decode the data directly...");
            }
            try {
                this.b.onDataFetcherReady(this.f.sourceKey, a.rewindAndGet(), this.f.fetcher, this.f.fetcher.getDataSource(), this.f.sourceKey);
                return false;
            } catch (Throwable th2) {
                th = th2;
                z = true;
                if (!z) {
                    this.f.fetcher.cleanup();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public void b() {
        ModelLoader.LoadData<?> loadData = this.f;
        if (loadData != null) {
            loadData.fetcher.cancel();
        }
    }

    void a(ModelLoader.LoadData<?> loadData, Object obj) {
        DiskCacheStrategy c = this.a.c();
        if (obj == null || !c.isDataCacheable(loadData.fetcher.getDataSource())) {
            this.b.onDataFetcherReady(loadData.sourceKey, obj, loadData.fetcher, loadData.fetcher.getDataSource(), this.g);
            return;
        }
        this.e = obj;
        this.b.reschedule();
    }

    void a(ModelLoader.LoadData<?> loadData, @NonNull Exception exc) {
        this.b.onDataFetcherFailed(this.g, exc, loadData.fetcher, loadData.fetcher.getDataSource());
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void reschedule() {
        throw new UnsupportedOperationException();
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherReady(Key key, Object obj, DataFetcher<?> dataFetcher, DataSource dataSource, Key key2) {
        this.b.onDataFetcherReady(key, obj, dataFetcher, this.f.fetcher.getDataSource(), key);
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherFailed(Key key, Exception exc, DataFetcher<?> dataFetcher, DataSource dataSource) {
        this.b.onDataFetcherFailed(key, exc, dataFetcher, this.f.fetcher.getDataSource());
    }
}
