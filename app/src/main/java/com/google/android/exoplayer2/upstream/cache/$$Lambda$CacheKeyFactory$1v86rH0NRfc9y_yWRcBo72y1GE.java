package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.upstream.cache.-$$Lambda$CacheKeyFactory$1v86rH0NRfc9y_y-WRcBo72y1GE  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$CacheKeyFactory$1v86rH0NRfc9y_yWRcBo72y1GE implements CacheKeyFactory {
    public static final /* synthetic */ $$Lambda$CacheKeyFactory$1v86rH0NRfc9y_yWRcBo72y1GE INSTANCE = new $$Lambda$CacheKeyFactory$1v86rH0NRfc9y_yWRcBo72y1GE();

    private /* synthetic */ $$Lambda$CacheKeyFactory$1v86rH0NRfc9y_yWRcBo72y1GE() {
    }

    @Override // com.google.android.exoplayer2.upstream.cache.CacheKeyFactory
    public final String buildCacheKey(DataSpec dataSpec) {
        String a;
        a = CacheKeyFactory.a(dataSpec);
        return a;
    }
}
