package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;

/* loaded from: classes2.dex */
public interface CacheKeyFactory {
    public static final CacheKeyFactory DEFAULT = $$Lambda$CacheKeyFactory$1v86rH0NRfc9y_yWRcBo72y1GE.INSTANCE;

    String buildCacheKey(DataSpec dataSpec);

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ String a(DataSpec dataSpec) {
        return dataSpec.key != null ? dataSpec.key : dataSpec.uri.toString();
    }
}
