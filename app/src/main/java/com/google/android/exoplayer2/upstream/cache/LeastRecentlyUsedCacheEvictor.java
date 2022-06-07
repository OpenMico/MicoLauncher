package com.google.android.exoplayer2.upstream.cache;

import java.util.TreeSet;

/* loaded from: classes2.dex */
public final class LeastRecentlyUsedCacheEvictor implements CacheEvictor {
    private final long a;
    private final TreeSet<CacheSpan> b = new TreeSet<>($$Lambda$LeastRecentlyUsedCacheEvictor$SFjToJeCjizSddPR1uzegq6zsuY.INSTANCE);
    private long c;

    @Override // com.google.android.exoplayer2.upstream.cache.CacheEvictor
    public void onCacheInitialized() {
    }

    @Override // com.google.android.exoplayer2.upstream.cache.CacheEvictor
    public boolean requiresCacheSpanTouches() {
        return true;
    }

    public LeastRecentlyUsedCacheEvictor(long j) {
        this.a = j;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.CacheEvictor
    public void onStartFile(Cache cache, String str, long j, long j2) {
        if (j2 != -1) {
            a(cache, j2);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        this.b.add(cacheSpan);
        this.c += cacheSpan.length;
        a(cache, 0L);
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public void onSpanRemoved(Cache cache, CacheSpan cacheSpan) {
        this.b.remove(cacheSpan);
        this.c -= cacheSpan.length;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
        onSpanRemoved(cache, cacheSpan);
        onSpanAdded(cache, cacheSpan2);
    }

    private void a(Cache cache, long j) {
        while (this.c + j > this.a && !this.b.isEmpty()) {
            cache.removeSpan(this.b.first());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(CacheSpan cacheSpan, CacheSpan cacheSpan2) {
        if (cacheSpan.lastTouchTimestamp - cacheSpan2.lastTouchTimestamp == 0) {
            return cacheSpan.compareTo(cacheSpan2);
        }
        return cacheSpan.lastTouchTimestamp < cacheSpan2.lastTouchTimestamp ? -1 : 1;
    }
}
