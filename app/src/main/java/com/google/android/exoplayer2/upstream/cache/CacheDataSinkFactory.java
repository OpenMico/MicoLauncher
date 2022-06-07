package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSink;

@Deprecated
/* loaded from: classes2.dex */
public final class CacheDataSinkFactory implements DataSink.Factory {
    private final Cache a;
    private final long b;
    private final int c;

    public CacheDataSinkFactory(Cache cache, long j) {
        this(cache, j, CacheDataSink.DEFAULT_BUFFER_SIZE);
    }

    public CacheDataSinkFactory(Cache cache, long j, int i) {
        this.a = cache;
        this.b = j;
        this.c = i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink.Factory
    public DataSink createDataSink() {
        return new CacheDataSink(this.a, this.b, this.c);
    }
}
