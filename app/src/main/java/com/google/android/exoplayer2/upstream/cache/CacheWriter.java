package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.upstream.DataSpec;
import java.io.IOException;
import java.io.InterruptedIOException;

/* loaded from: classes2.dex */
public final class CacheWriter {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;
    private final CacheDataSource a;
    private final Cache b;
    private final DataSpec c;
    private final String d;
    private final byte[] e;
    @Nullable
    private final ProgressListener f;
    private long g;
    private long h;
    private long i;
    private volatile boolean j;

    /* loaded from: classes2.dex */
    public interface ProgressListener {
        void onProgress(long j, long j2, long j3);
    }

    public CacheWriter(CacheDataSource cacheDataSource, DataSpec dataSpec, @Nullable byte[] bArr, @Nullable ProgressListener progressListener) {
        this.a = cacheDataSource;
        this.b = cacheDataSource.getCache();
        this.c = dataSpec;
        this.e = bArr == null ? new byte[131072] : bArr;
        this.f = progressListener;
        this.d = cacheDataSource.getCacheKeyFactory().buildCacheKey(dataSpec);
        this.g = dataSpec.position;
    }

    public void cancel() {
        this.j = true;
    }

    @WorkerThread
    public void cache() throws IOException {
        b();
        this.i = this.b.getCachedBytes(this.d, this.c.position, this.c.length);
        if (this.c.length != -1) {
            this.h = this.c.position + this.c.length;
        } else {
            long contentLength = ContentMetadata.getContentLength(this.b.getContentMetadata(this.d));
            if (contentLength == -1) {
                contentLength = -1;
            }
            this.h = contentLength;
        }
        ProgressListener progressListener = this.f;
        if (progressListener != null) {
            progressListener.onProgress(a(), this.i, 0L);
        }
        while (true) {
            long j = this.h;
            if (j == -1 || this.g < j) {
                b();
                long j2 = this.h;
                long cachedLength = this.b.getCachedLength(this.d, this.g, j2 == -1 ? Long.MAX_VALUE : j2 - this.g);
                if (cachedLength > 0) {
                    this.g += cachedLength;
                } else {
                    long j3 = -cachedLength;
                    if (j3 == Long.MAX_VALUE) {
                        j3 = -1;
                    }
                    long j4 = this.g;
                    this.g = j4 + a(j4, j3);
                }
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006f A[Catch: IOException -> 0x0068, TryCatch #0 {IOException -> 0x0068, blocks: (B:24:0x0064, B:30:0x006f, B:32:0x007f, B:34:0x0087), top: B:39:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0087 A[Catch: IOException -> 0x0068, TRY_LEAVE, TryCatch #0 {IOException -> 0x0068, blocks: (B:24:0x0064, B:30:0x006f, B:32:0x007f, B:34:0x0087), top: B:39:0x0064 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long a(long r7, long r9) throws java.io.IOException {
        /*
            r6 = this;
            long r0 = r7 + r9
            long r2 = r6.h
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 1
            r2 = 0
            r3 = -1
            if (r0 == 0) goto L_0x0013
            int r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            r0 = r2
            goto L_0x0014
        L_0x0013:
            r0 = r1
        L_0x0014:
            int r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x0036
            com.google.android.exoplayer2.upstream.DataSpec r5 = r6.c
            com.google.android.exoplayer2.upstream.DataSpec$Builder r5 = r5.buildUpon()
            com.google.android.exoplayer2.upstream.DataSpec$Builder r5 = r5.setPosition(r7)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r9 = r5.setLength(r9)
            com.google.android.exoplayer2.upstream.DataSpec r9 = r9.build()
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r10 = r6.a     // Catch: IOException -> 0x0031
            long r9 = r10.open(r9)     // Catch: IOException -> 0x0031
            goto L_0x0038
        L_0x0031:
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r9 = r6.a
            com.google.android.exoplayer2.util.Util.closeQuietly(r9)
        L_0x0036:
            r1 = r2
            r9 = r3
        L_0x0038:
            if (r1 != 0) goto L_0x005d
            r6.b()
            com.google.android.exoplayer2.upstream.DataSpec r9 = r6.c
            com.google.android.exoplayer2.upstream.DataSpec$Builder r9 = r9.buildUpon()
            com.google.android.exoplayer2.upstream.DataSpec$Builder r9 = r9.setPosition(r7)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r9 = r9.setLength(r3)
            com.google.android.exoplayer2.upstream.DataSpec r9 = r9.build()
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r10 = r6.a     // Catch: IOException -> 0x0056
            long r9 = r10.open(r9)     // Catch: IOException -> 0x0056
            goto L_0x005d
        L_0x0056:
            r7 = move-exception
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r8 = r6.a
            com.google.android.exoplayer2.util.Util.closeQuietly(r8)
            throw r7
        L_0x005d:
            if (r0 == 0) goto L_0x006a
            int r1 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x006a
            long r9 = r9 + r7
            r6.a(r9)     // Catch: IOException -> 0x0068
            goto L_0x006a
        L_0x0068:
            r7 = move-exception
            goto L_0x008d
        L_0x006a:
            r9 = r2
            r10 = r9
        L_0x006c:
            r1 = -1
            if (r9 == r1) goto L_0x0085
            r6.b()     // Catch: IOException -> 0x0068
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r9 = r6.a     // Catch: IOException -> 0x0068
            byte[] r3 = r6.e     // Catch: IOException -> 0x0068
            byte[] r4 = r6.e     // Catch: IOException -> 0x0068
            int r4 = r4.length     // Catch: IOException -> 0x0068
            int r9 = r9.read(r3, r2, r4)     // Catch: IOException -> 0x0068
            if (r9 == r1) goto L_0x006c
            long r3 = (long) r9     // Catch: IOException -> 0x0068
            r6.b(r3)     // Catch: IOException -> 0x0068
            int r10 = r10 + r9
            goto L_0x006c
        L_0x0085:
            if (r0 == 0) goto L_0x0093
            long r0 = (long) r10     // Catch: IOException -> 0x0068
            long r7 = r7 + r0
            r6.a(r7)     // Catch: IOException -> 0x0068
            goto L_0x0093
        L_0x008d:
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r8 = r6.a
            com.google.android.exoplayer2.util.Util.closeQuietly(r8)
            throw r7
        L_0x0093:
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r7 = r6.a
            r7.close()
            long r7 = (long) r10
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheWriter.a(long, long):long");
    }

    private void a(long j) {
        if (this.h != j) {
            this.h = j;
            ProgressListener progressListener = this.f;
            if (progressListener != null) {
                progressListener.onProgress(a(), this.i, 0L);
            }
        }
    }

    private void b(long j) {
        this.i += j;
        ProgressListener progressListener = this.f;
        if (progressListener != null) {
            progressListener.onProgress(a(), this.i, j);
        }
    }

    private long a() {
        long j = this.h;
        if (j == -1) {
            return -1L;
        }
        return j - this.c.position;
    }

    private void b() throws InterruptedIOException {
        if (this.j) {
            throw new InterruptedIOException();
        }
    }
}
