package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;

@Deprecated
/* loaded from: classes2.dex */
public final class CacheDataSourceFactory implements DataSource.Factory {
    private final Cache a;
    private final DataSource.Factory b;
    private final DataSource.Factory c;
    private final int d;
    @Nullable
    private final DataSink.Factory e;
    @Nullable
    private final CacheDataSource.EventListener f;
    @Nullable
    private final CacheKeyFactory g;

    public CacheDataSourceFactory(Cache cache, DataSource.Factory factory) {
        this(cache, factory, 0);
    }

    public CacheDataSourceFactory(Cache cache, DataSource.Factory factory, int i) {
        this(cache, factory, new FileDataSource.Factory(), new CacheDataSink.Factory().setCache(cache), i, null);
    }

    public CacheDataSourceFactory(Cache cache, DataSource.Factory factory, DataSource.Factory factory2, @Nullable DataSink.Factory factory3, int i, @Nullable CacheDataSource.EventListener eventListener) {
        this(cache, factory, factory2, factory3, i, eventListener, null);
    }

    public CacheDataSourceFactory(Cache cache, DataSource.Factory factory, DataSource.Factory factory2, @Nullable DataSink.Factory factory3, int i, @Nullable CacheDataSource.EventListener eventListener, @Nullable CacheKeyFactory cacheKeyFactory) {
        this.a = cache;
        this.b = factory;
        this.c = factory2;
        this.e = factory3;
        this.d = i;
        this.f = eventListener;
        this.g = cacheKeyFactory;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
    public CacheDataSource createDataSource() {
        Cache cache = this.a;
        DataSource createDataSource = this.b.createDataSource();
        DataSource createDataSource2 = this.c.createDataSource();
        DataSink.Factory factory = this.e;
        return new CacheDataSource(cache, createDataSource, createDataSource2, factory == null ? null : factory.createDataSink(), this.d, this.f, this.g);
    }
}
