package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import java.io.File;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.Set;

/* loaded from: classes2.dex */
public interface Cache {
    public static final long UID_UNSET = -1;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onSpanAdded(Cache cache, CacheSpan cacheSpan);

        void onSpanRemoved(Cache cache, CacheSpan cacheSpan);

        void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2);
    }

    NavigableSet<CacheSpan> addListener(String str, Listener listener);

    @WorkerThread
    void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) throws CacheException;

    @WorkerThread
    void commitFile(File file, long j) throws CacheException;

    long getCacheSpace();

    long getCachedBytes(String str, long j, long j2);

    long getCachedLength(String str, long j, long j2);

    NavigableSet<CacheSpan> getCachedSpans(String str);

    ContentMetadata getContentMetadata(String str);

    Set<String> getKeys();

    long getUid();

    boolean isCached(String str, long j, long j2);

    @WorkerThread
    void release();

    void releaseHoleSpan(CacheSpan cacheSpan);

    void removeListener(String str, Listener listener);

    @WorkerThread
    void removeResource(String str);

    @WorkerThread
    void removeSpan(CacheSpan cacheSpan);

    @WorkerThread
    File startFile(String str, long j, long j2) throws CacheException;

    @WorkerThread
    CacheSpan startReadWrite(String str, long j, long j2) throws InterruptedException, CacheException;

    @Nullable
    @WorkerThread
    CacheSpan startReadWriteNonBlocking(String str, long j, long j2) throws CacheException;

    /* loaded from: classes2.dex */
    public static class CacheException extends IOException {
        public CacheException(String str) {
            super(str);
        }

        public CacheException(Throwable th) {
            super(th);
        }

        public CacheException(String str, Throwable th) {
            super(str, th);
        }
    }
}
