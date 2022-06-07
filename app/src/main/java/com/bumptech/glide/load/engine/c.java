package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.File;
import java.util.List;

/* compiled from: DataCacheGenerator.java */
/* loaded from: classes.dex */
public class c implements DataFetcher.DataCallback<Object>, DataFetcherGenerator {
    private final List<Key> a;
    private final f<?> b;
    private final DataFetcherGenerator.FetcherReadyCallback c;
    private int d;
    private Key e;
    private List<ModelLoader<File, ?>> f;
    private int g;
    private volatile ModelLoader.LoadData<?> h;
    private File i;

    public c(f<?> fVar, DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback) {
        this(fVar.o(), fVar, fetcherReadyCallback);
    }

    public c(List<Key> list, f<?> fVar, DataFetcherGenerator.FetcherReadyCallback fetcherReadyCallback) {
        this.d = -1;
        this.a = list;
        this.b = fVar;
        this.c = fetcherReadyCallback;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
        if (c() == false) goto L_0x0068;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
        r0 = r7.f;
        r3 = r7.g;
        r7.g = r3 + 1;
        r7.h = r0.get(r3).buildLoadData(r7.i, r7.b.g(), r7.b.h(), r7.b.e());
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0047, code lost:
        if (r7.h == null) goto L_0x0015;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0057, code lost:
        if (r7.b.a(r7.h.fetcher.getDataClass()) == false) goto L_0x0015;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:
        r7.h.fetcher.loadData(r7.b.d(), r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0066, code lost:
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006b, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0012, code lost:
        r7.h = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0015, code lost:
        if (r1 != false) goto L_0x0068;
     */
    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a() {
        /*
            r7 = this;
            java.lang.String r0 = "DataCacheGenerator.startNext"
            com.bumptech.glide.util.pool.GlideTrace.beginSection(r0)
        L_0x0005:
            java.util.List<com.bumptech.glide.load.model.ModelLoader<java.io.File, ?>> r0 = r7.f     // Catch: all -> 0x00b4
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x006c
            boolean r0 = r7.c()     // Catch: all -> 0x00b4
            if (r0 != 0) goto L_0x0012
            goto L_0x006c
        L_0x0012:
            r0 = 0
            r7.h = r0     // Catch: all -> 0x00b4
        L_0x0015:
            if (r1 != 0) goto L_0x0068
            boolean r0 = r7.c()     // Catch: all -> 0x00b4
            if (r0 == 0) goto L_0x0068
            java.util.List<com.bumptech.glide.load.model.ModelLoader<java.io.File, ?>> r0 = r7.f     // Catch: all -> 0x00b4
            int r3 = r7.g     // Catch: all -> 0x00b4
            int r4 = r3 + 1
            r7.g = r4     // Catch: all -> 0x00b4
            java.lang.Object r0 = r0.get(r3)     // Catch: all -> 0x00b4
            com.bumptech.glide.load.model.ModelLoader r0 = (com.bumptech.glide.load.model.ModelLoader) r0     // Catch: all -> 0x00b4
            java.io.File r3 = r7.i     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.f<?> r4 = r7.b     // Catch: all -> 0x00b4
            int r4 = r4.g()     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.f<?> r5 = r7.b     // Catch: all -> 0x00b4
            int r5 = r5.h()     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.f<?> r6 = r7.b     // Catch: all -> 0x00b4
            com.bumptech.glide.load.Options r6 = r6.e()     // Catch: all -> 0x00b4
            com.bumptech.glide.load.model.ModelLoader$LoadData r0 = r0.buildLoadData(r3, r4, r5, r6)     // Catch: all -> 0x00b4
            r7.h = r0     // Catch: all -> 0x00b4
            com.bumptech.glide.load.model.ModelLoader$LoadData<?> r0 = r7.h     // Catch: all -> 0x00b4
            if (r0 == 0) goto L_0x0015
            com.bumptech.glide.load.engine.f<?> r0 = r7.b     // Catch: all -> 0x00b4
            com.bumptech.glide.load.model.ModelLoader$LoadData<?> r3 = r7.h     // Catch: all -> 0x00b4
            com.bumptech.glide.load.data.DataFetcher<Data> r3 = r3.fetcher     // Catch: all -> 0x00b4
            java.lang.Class r3 = r3.getDataClass()     // Catch: all -> 0x00b4
            boolean r0 = r0.a(r3)     // Catch: all -> 0x00b4
            if (r0 == 0) goto L_0x0015
            com.bumptech.glide.load.model.ModelLoader$LoadData<?> r0 = r7.h     // Catch: all -> 0x00b4
            com.bumptech.glide.load.data.DataFetcher<Data> r0 = r0.fetcher     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.f<?> r1 = r7.b     // Catch: all -> 0x00b4
            com.bumptech.glide.Priority r1 = r1.d()     // Catch: all -> 0x00b4
            r0.loadData(r1, r7)     // Catch: all -> 0x00b4
            r1 = r2
            goto L_0x0015
        L_0x0068:
            com.bumptech.glide.util.pool.GlideTrace.endSection()
            return r1
        L_0x006c:
            int r0 = r7.d     // Catch: all -> 0x00b4
            int r0 = r0 + r2
            r7.d = r0     // Catch: all -> 0x00b4
            int r0 = r7.d     // Catch: all -> 0x00b4
            java.util.List<com.bumptech.glide.load.Key> r2 = r7.a     // Catch: all -> 0x00b4
            int r2 = r2.size()     // Catch: all -> 0x00b4
            if (r0 < r2) goto L_0x007f
            com.bumptech.glide.util.pool.GlideTrace.endSection()
            return r1
        L_0x007f:
            java.util.List<com.bumptech.glide.load.Key> r0 = r7.a     // Catch: all -> 0x00b4
            int r2 = r7.d     // Catch: all -> 0x00b4
            java.lang.Object r0 = r0.get(r2)     // Catch: all -> 0x00b4
            com.bumptech.glide.load.Key r0 = (com.bumptech.glide.load.Key) r0     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.d r2 = new com.bumptech.glide.load.engine.d     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.f<?> r3 = r7.b     // Catch: all -> 0x00b4
            com.bumptech.glide.load.Key r3 = r3.f()     // Catch: all -> 0x00b4
            r2.<init>(r0, r3)     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.f<?> r3 = r7.b     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.cache.DiskCache r3 = r3.b()     // Catch: all -> 0x00b4
            java.io.File r2 = r3.get(r2)     // Catch: all -> 0x00b4
            r7.i = r2     // Catch: all -> 0x00b4
            java.io.File r2 = r7.i     // Catch: all -> 0x00b4
            if (r2 == 0) goto L_0x0005
            r7.e = r0     // Catch: all -> 0x00b4
            com.bumptech.glide.load.engine.f<?> r0 = r7.b     // Catch: all -> 0x00b4
            java.io.File r2 = r7.i     // Catch: all -> 0x00b4
            java.util.List r0 = r0.a(r2)     // Catch: all -> 0x00b4
            r7.f = r0     // Catch: all -> 0x00b4
            r7.g = r1     // Catch: all -> 0x00b4
            goto L_0x0005
        L_0x00b4:
            r0 = move-exception
            com.bumptech.glide.util.pool.GlideTrace.endSection()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.c.a():boolean");
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
        this.c.onDataFetcherReady(this.e, obj, this.h.fetcher, DataSource.DATA_DISK_CACHE, this.e);
    }

    @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
    public void onLoadFailed(@NonNull Exception exc) {
        this.c.onDataFetcherFailed(this.e, exc, this.h.fetcher, DataSource.DATA_DISK_CACHE);
    }
}
