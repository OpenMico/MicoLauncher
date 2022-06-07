package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public final class CachedRegionTracker implements Cache.Listener {
    public static final int CACHED_TO_END = -2;
    public static final int NOT_CACHED = -1;
    private final Cache a;
    private final String b;
    private final ChunkIndex c;
    private final TreeSet<a> d = new TreeSet<>();
    private final a e = new a(0, 0);

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
    }

    public CachedRegionTracker(Cache cache, String str, ChunkIndex chunkIndex) {
        this.a = cache;
        this.b = str;
        this.c = chunkIndex;
        synchronized (this) {
            Iterator<CacheSpan> descendingIterator = cache.addListener(str, this).descendingIterator();
            while (descendingIterator.hasNext()) {
                a(descendingIterator.next());
            }
        }
    }

    public void release() {
        this.a.removeListener(this.b, this);
    }

    public synchronized int getRegionEndTimeMs(long j) {
        this.e.a = j;
        a floor = this.d.floor(this.e);
        if (!(floor == null || j > floor.b || floor.c == -1)) {
            int i = floor.c;
            if (i == this.c.length - 1) {
                if (floor.b == this.c.offsets[i] + this.c.sizes[i]) {
                    return -2;
                }
            }
            return (int) ((this.c.timesUs[i] + ((this.c.durationsUs[i] * (floor.b - this.c.offsets[i])) / this.c.sizes[i])) / 1000);
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public synchronized void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        a(cacheSpan);
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache.Listener
    public synchronized void onSpanRemoved(Cache cache, CacheSpan cacheSpan) {
        a aVar = new a(cacheSpan.position, cacheSpan.position + cacheSpan.length);
        a floor = this.d.floor(aVar);
        if (floor == null) {
            Log.e("CachedRegionTracker", "Removed a span we were not aware of");
            return;
        }
        this.d.remove(floor);
        if (floor.a < aVar.a) {
            a aVar2 = new a(floor.a, aVar.a);
            int binarySearch = Arrays.binarySearch(this.c.offsets, aVar2.b);
            if (binarySearch < 0) {
                binarySearch = (-binarySearch) - 2;
            }
            aVar2.c = binarySearch;
            this.d.add(aVar2);
        }
        if (floor.b > aVar.b) {
            a aVar3 = new a(aVar.b + 1, floor.b);
            aVar3.c = floor.c;
            this.d.add(aVar3);
        }
    }

    private void a(CacheSpan cacheSpan) {
        a aVar = new a(cacheSpan.position, cacheSpan.position + cacheSpan.length);
        a floor = this.d.floor(aVar);
        a ceiling = this.d.ceiling(aVar);
        boolean a2 = a(floor, aVar);
        if (a(aVar, ceiling)) {
            if (a2) {
                floor.b = ceiling.b;
                floor.c = ceiling.c;
            } else {
                aVar.b = ceiling.b;
                aVar.c = ceiling.c;
                this.d.add(aVar);
            }
            this.d.remove(ceiling);
        } else if (a2) {
            floor.b = aVar.b;
            int i = floor.c;
            while (i < this.c.length - 1) {
                int i2 = i + 1;
                if (this.c.offsets[i2] > floor.b) {
                    break;
                }
                i = i2;
            }
            floor.c = i;
        } else {
            int binarySearch = Arrays.binarySearch(this.c.offsets, aVar.b);
            if (binarySearch < 0) {
                binarySearch = (-binarySearch) - 2;
            }
            aVar.c = binarySearch;
            this.d.add(aVar);
        }
    }

    private boolean a(@Nullable a aVar, @Nullable a aVar2) {
        return (aVar == null || aVar2 == null || aVar.b != aVar2.a) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a implements Comparable<a> {
        public long a;
        public long b;
        public int c;

        public a(long j, long j2) {
            this.a = j;
            this.b = j2;
        }

        /* renamed from: a */
        public int compareTo(a aVar) {
            return Util.compareLong(this.a, aVar.a);
        }
    }
}
