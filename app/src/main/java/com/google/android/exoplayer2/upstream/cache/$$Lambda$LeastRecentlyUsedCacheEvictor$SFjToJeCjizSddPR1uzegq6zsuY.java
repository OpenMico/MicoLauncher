package com.google.android.exoplayer2.upstream.cache;

import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.upstream.cache.-$$Lambda$LeastRecentlyUsedCacheEvictor$SFjToJeCjizSddPR1uzegq6zsuY  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$LeastRecentlyUsedCacheEvictor$SFjToJeCjizSddPR1uzegq6zsuY implements Comparator {
    public static final /* synthetic */ $$Lambda$LeastRecentlyUsedCacheEvictor$SFjToJeCjizSddPR1uzegq6zsuY INSTANCE = new $$Lambda$LeastRecentlyUsedCacheEvictor$SFjToJeCjizSddPR1uzegq6zsuY();

    private /* synthetic */ $$Lambda$LeastRecentlyUsedCacheEvictor$SFjToJeCjizSddPR1uzegq6zsuY() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int a;
        a = LeastRecentlyUsedCacheEvictor.a((CacheSpan) obj, (CacheSpan) obj2);
        return a;
    }
}
