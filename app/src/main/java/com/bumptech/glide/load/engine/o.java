package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.pool.GlideTrace;
import java.io.File;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ResourceCacheGenerator.java */
/* loaded from: classes.dex */
public class o implements DataFetcher.DataCallback<Object>, DataFetcherGenerator {
    private final DataFetcherGenerator.FetcherReadyCallback a;
    private final f<?> b;
    private int c;
    private int d = -1;
    private Key e;
    private List<ModelLoader<File, ?>> f;
    private int g;
    private volatile ModelLoader.LoadData<?> h;
    private File i;
    private p j;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(f<?> fVar, DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback) {
        this.b = fVar;
        this.a = fetcherReadyCallback;
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public boolean a() {
        GlideTrace.beginSection("ResourceCacheGenerator.startNext");
        try {
            List<Key> o = this.b.o();
            boolean z = false;
            if (o.isEmpty()) {
                return false;
            }
            List<Class<?>> l = this.b.l();
            if (!l.isEmpty()) {
                while (true) {
                    if (this.f != null && c()) {
                        this.h = null;
                        while (!z && c()) {
                            List<ModelLoader<File, ?>> list = this.f;
                            int i = this.g;
                            this.g = i + 1;
                            this.h = list.get(i).buildLoadData(this.i, this.b.g(), this.b.h(), this.b.e());
                            if (this.h != null && this.b.a(this.h.fetcher.getDataClass())) {
                                this.h.fetcher.loadData(this.b.d(), this);
                                z = true;
                            }
                        }
                        return z;
                    }
                    this.d++;
                    if (this.d >= l.size()) {
                        this.c++;
                        if (this.c >= o.size()) {
                            return false;
                        }
                        this.d = 0;
                    }
                    Key key = o.get(this.c);
                    Class<?> cls = l.get(this.d);
                    this.j = new p(this.b.i(), key, this.b.f(), this.b.g(), this.b.h(), this.b.c(cls), cls, this.b.e());
                    this.i = this.b.b().get(this.j);
                    if (this.i != null) {
                        this.e = key;
                        this.f = this.b.a(this.i);
                        this.g = 0;
                    }
                }
            } else if (File.class.equals(this.b.j())) {
                return false;
            } else {
                throw new IllegalStateException("Failed to find any load path from " + this.b.k() + " to " + this.b.j());
            }
        } finally {
            GlideTrace.endSection();
        }
    }

    private boolean c() {
        return this.g < this.f.size();
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    public void b() {
        ModelLoader.LoadData<?> loadData = this.h;
        if (loadData != null) {
            loadData.fetcher.cancel();
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
    public void onDataReady(Object obj) {
        this.a.onDataFetcherReady(this.e, obj, this.h.fetcher, DataSource.RESOURCE_DISK_CACHE, this.j);
    }

    @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
    public void onLoadFailed(@NonNull Exception exc) {
        this.a.onDataFetcherFailed(this.j, exc, this.h.fetcher, DataSource.RESOURCE_DISK_CACHE);
    }
}
