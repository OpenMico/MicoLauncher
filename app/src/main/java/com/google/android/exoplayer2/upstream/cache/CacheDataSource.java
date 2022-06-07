package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DummyDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.PriorityDataSource;
import com.google.android.exoplayer2.upstream.TeeDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class CacheDataSource implements DataSource {
    public static final int CACHE_IGNORED_REASON_ERROR = 0;
    public static final int CACHE_IGNORED_REASON_UNSET_LENGTH = 1;
    public static final int FLAG_BLOCK_ON_CACHE = 1;
    public static final int FLAG_IGNORE_CACHE_FOR_UNSET_LENGTH_REQUESTS = 4;
    public static final int FLAG_IGNORE_CACHE_ON_ERROR = 2;
    private final Cache a;
    private final DataSource b;
    @Nullable
    private final DataSource c;
    private final DataSource d;
    private final CacheKeyFactory e;
    @Nullable
    private final EventListener f;
    private final boolean g;
    private final boolean h;
    private final boolean i;
    @Nullable
    private Uri j;
    @Nullable
    private DataSpec k;
    @Nullable
    private DataSpec l;
    @Nullable
    private DataSource m;
    private long n;
    private long o;
    private long p;
    @Nullable
    private CacheSpan q;
    private boolean r;
    private boolean s;
    private long t;
    private long u;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface CacheIgnoredReason {
    }

    /* loaded from: classes2.dex */
    public interface EventListener {
        void onCacheIgnored(int i);

        void onCachedBytesRead(long j, long j2);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements DataSource.Factory {
        private Cache a;
        @Nullable
        private DataSink.Factory c;
        private boolean e;
        @Nullable
        private DataSource.Factory f;
        @Nullable
        private PriorityTaskManager g;
        private int h;
        private int i;
        @Nullable
        private EventListener j;
        private DataSource.Factory b = new FileDataSource.Factory();
        private CacheKeyFactory d = CacheKeyFactory.DEFAULT;

        public Factory setCache(Cache cache) {
            this.a = cache;
            return this;
        }

        @Nullable
        public Cache getCache() {
            return this.a;
        }

        public Factory setCacheReadDataSourceFactory(DataSource.Factory factory) {
            this.b = factory;
            return this;
        }

        public Factory setCacheWriteDataSinkFactory(@Nullable DataSink.Factory factory) {
            this.c = factory;
            this.e = factory == null;
            return this;
        }

        public Factory setCacheKeyFactory(CacheKeyFactory cacheKeyFactory) {
            this.d = cacheKeyFactory;
            return this;
        }

        public CacheKeyFactory getCacheKeyFactory() {
            return this.d;
        }

        public Factory setUpstreamDataSourceFactory(@Nullable DataSource.Factory factory) {
            this.f = factory;
            return this;
        }

        public Factory setUpstreamPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            this.g = priorityTaskManager;
            return this;
        }

        @Nullable
        public PriorityTaskManager getUpstreamPriorityTaskManager() {
            return this.g;
        }

        public Factory setUpstreamPriority(int i) {
            this.h = i;
            return this;
        }

        public Factory setFlags(int i) {
            this.i = i;
            return this;
        }

        public Factory setEventListener(@Nullable EventListener eventListener) {
            this.j = eventListener;
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
        public CacheDataSource createDataSource() {
            DataSource.Factory factory = this.f;
            return a(factory != null ? factory.createDataSource() : null, this.i, this.h);
        }

        public CacheDataSource createDataSourceForDownloading() {
            DataSource.Factory factory = this.f;
            return a(factory != null ? factory.createDataSource() : null, this.i | 1, -1000);
        }

        public CacheDataSource createDataSourceForRemovingDownload() {
            return a(null, this.i | 1, -1000);
        }

        private CacheDataSource a(@Nullable DataSource dataSource, int i, int i2) {
            DataSink dataSink;
            Cache cache = (Cache) Assertions.checkNotNull(this.a);
            if (this.e || dataSource == null) {
                dataSink = null;
            } else {
                DataSink.Factory factory = this.c;
                if (factory != null) {
                    dataSink = factory.createDataSink();
                } else {
                    dataSink = new CacheDataSink.Factory().setCache(cache).createDataSink();
                }
            }
            return new CacheDataSource(cache, dataSource, this.b.createDataSource(), dataSink, this.d, i, this.g, i2, this.j);
        }
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource) {
        this(cache, dataSource, 0);
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource, int i) {
        this(cache, dataSource, new FileDataSource(), new CacheDataSink(cache, CacheDataSink.DEFAULT_FRAGMENT_SIZE), i, null);
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource, DataSource dataSource2, @Nullable DataSink dataSink, int i, @Nullable EventListener eventListener) {
        this(cache, dataSource, dataSource2, dataSink, i, eventListener, null);
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource, DataSource dataSource2, @Nullable DataSink dataSink, int i, @Nullable EventListener eventListener, @Nullable CacheKeyFactory cacheKeyFactory) {
        this(cache, dataSource, dataSource2, dataSink, cacheKeyFactory, i, null, 0, eventListener);
    }

    private CacheDataSource(Cache cache, @Nullable DataSource dataSource, DataSource dataSource2, @Nullable DataSink dataSink, @Nullable CacheKeyFactory cacheKeyFactory, int i, @Nullable PriorityTaskManager priorityTaskManager, int i2, @Nullable EventListener eventListener) {
        this.a = cache;
        this.b = dataSource2;
        this.e = cacheKeyFactory == null ? CacheKeyFactory.DEFAULT : cacheKeyFactory;
        boolean z = false;
        this.g = (i & 1) != 0;
        this.h = (i & 2) != 0;
        this.i = (i & 4) != 0 ? true : z;
        TeeDataSource teeDataSource = null;
        if (dataSource != null) {
            dataSource = priorityTaskManager != null ? new PriorityDataSource(dataSource, priorityTaskManager, i2) : dataSource;
            this.d = dataSource;
            this.c = dataSink != null ? new TeeDataSource(dataSource, dataSink) : teeDataSource;
        } else {
            this.d = DummyDataSource.INSTANCE;
            this.c = null;
        }
        this.f = eventListener;
    }

    public Cache getCache() {
        return this.a;
    }

    public CacheKeyFactory getCacheKeyFactory() {
        return this.e;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.b.addTransferListener(transferListener);
        this.d.addTransferListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        long j;
        try {
            String buildCacheKey = this.e.buildCacheKey(dataSpec);
            DataSpec build = dataSpec.buildUpon().setKey(buildCacheKey).build();
            this.k = build;
            this.j = a(this.a, buildCacheKey, build.uri);
            this.o = dataSpec.position;
            int a = a(dataSpec);
            this.s = a != -1;
            if (this.s) {
                a(a);
            }
            if (this.s) {
                this.p = -1L;
            } else {
                this.p = ContentMetadata.getContentLength(this.a.getContentMetadata(buildCacheKey));
                if (this.p != -1) {
                    this.p -= dataSpec.position;
                    if (this.p < 0) {
                        throw new DataSourceException(2008);
                    }
                }
            }
            if (dataSpec.length != -1) {
                if (this.p == -1) {
                    j = dataSpec.length;
                } else {
                    j = Math.min(this.p, dataSpec.length);
                }
                this.p = j;
            }
            if (this.p > 0 || this.p == -1) {
                a(build, false);
            }
            return dataSpec.length != -1 ? dataSpec.length : this.p;
        } catch (Throwable th) {
            a(th);
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        if (this.p == 0) {
            return -1;
        }
        DataSpec dataSpec = (DataSpec) Assertions.checkNotNull(this.k);
        DataSpec dataSpec2 = (DataSpec) Assertions.checkNotNull(this.l);
        try {
            if (this.o >= this.u) {
                a(dataSpec, true);
            }
            int read = ((DataSource) Assertions.checkNotNull(this.m)).read(bArr, i, i2);
            if (read != -1) {
                if (c()) {
                    this.t += read;
                }
                long j = read;
                this.o += j;
                this.n += j;
                if (this.p != -1) {
                    this.p -= j;
                }
            } else if (!a() || (dataSpec2.length != -1 && this.n >= dataSpec2.length)) {
                if (this.p <= 0) {
                    if (this.p == -1) {
                    }
                }
                e();
                a(dataSpec, false);
                return read(bArr, i, i2);
            } else {
                a((String) Util.castNonNull(dataSpec.key));
            }
            return read;
        } catch (Throwable th) {
            a(th);
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.j;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        if (a()) {
            return this.d.getResponseHeaders();
        }
        return Collections.emptyMap();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws IOException {
        this.k = null;
        this.j = null;
        this.o = 0L;
        f();
        try {
            e();
        } catch (Throwable th) {
            a(th);
            throw th;
        }
    }

    private void a(DataSpec dataSpec, boolean z) throws IOException {
        CacheSpan cacheSpan;
        DataSpec dataSpec2;
        DataSource dataSource;
        long j;
        String str = (String) Util.castNonNull(dataSpec.key);
        Uri uri = null;
        if (this.s) {
            cacheSpan = null;
        } else if (this.g) {
            try {
                cacheSpan = this.a.startReadWrite(str, this.o, this.p);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            }
        } else {
            cacheSpan = this.a.startReadWriteNonBlocking(str, this.o, this.p);
        }
        if (cacheSpan == null) {
            dataSource = this.d;
            dataSpec2 = dataSpec.buildUpon().setPosition(this.o).setLength(this.p).build();
        } else if (cacheSpan.isCached) {
            Uri fromFile = Uri.fromFile((File) Util.castNonNull(cacheSpan.file));
            long j2 = cacheSpan.position;
            long j3 = this.o - j2;
            long j4 = cacheSpan.length - j3;
            long j5 = this.p;
            if (j5 != -1) {
                j4 = Math.min(j4, j5);
            }
            dataSpec2 = dataSpec.buildUpon().setUri(fromFile).setUriPositionOffset(j2).setPosition(j3).setLength(j4).build();
            dataSource = this.b;
        } else {
            if (cacheSpan.isOpenEnded()) {
                j = this.p;
            } else {
                j = cacheSpan.length;
                long j6 = this.p;
                if (j6 != -1) {
                    j = Math.min(j, j6);
                }
            }
            dataSpec2 = dataSpec.buildUpon().setPosition(this.o).setLength(j).build();
            dataSource = this.c;
            if (dataSource == null) {
                dataSource = this.d;
                this.a.releaseHoleSpan(cacheSpan);
                cacheSpan = null;
            }
        }
        this.u = (this.s || dataSource != this.d) ? Long.MAX_VALUE : this.o + 102400;
        if (z) {
            Assertions.checkState(b());
            if (dataSource != this.d) {
                try {
                    e();
                } catch (Throwable th) {
                    if (((CacheSpan) Util.castNonNull(cacheSpan)).isHoleSpan()) {
                        this.a.releaseHoleSpan(cacheSpan);
                    }
                    throw th;
                }
            } else {
                return;
            }
        }
        if (cacheSpan != null && cacheSpan.isHoleSpan()) {
            this.q = cacheSpan;
        }
        this.m = dataSource;
        this.l = dataSpec2;
        this.n = 0L;
        long open = dataSource.open(dataSpec2);
        ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
        if (dataSpec2.length == -1 && open != -1) {
            this.p = open;
            ContentMetadataMutations.setContentLength(contentMetadataMutations, this.o + this.p);
        }
        if (a()) {
            this.j = dataSource.getUri();
            if (!dataSpec.uri.equals(this.j)) {
                uri = this.j;
            }
            ContentMetadataMutations.setRedirectedUri(contentMetadataMutations, uri);
        }
        if (d()) {
            this.a.applyContentMetadataMutations(str, contentMetadataMutations);
        }
    }

    private void a(String str) throws IOException {
        this.p = 0L;
        if (d()) {
            ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
            ContentMetadataMutations.setContentLength(contentMetadataMutations, this.o);
            this.a.applyContentMetadataMutations(str, contentMetadataMutations);
        }
    }

    private static Uri a(Cache cache, String str, Uri uri) {
        Uri redirectedUri = ContentMetadata.getRedirectedUri(cache.getContentMetadata(str));
        return redirectedUri != null ? redirectedUri : uri;
    }

    private boolean a() {
        return !c();
    }

    private boolean b() {
        return this.m == this.d;
    }

    private boolean c() {
        return this.m == this.b;
    }

    private boolean d() {
        return this.m == this.c;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.exoplayer2.upstream.cache.CacheSpan, com.google.android.exoplayer2.upstream.DataSpec, com.google.android.exoplayer2.upstream.DataSource] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void e() throws java.io.IOException {
        /*
            r4 = this;
            com.google.android.exoplayer2.upstream.DataSource r0 = r4.m
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r1 = 0
            r0.close()     // Catch: all -> 0x0019
            r4.l = r1
            r4.m = r1
            com.google.android.exoplayer2.upstream.cache.CacheSpan r0 = r4.q
            if (r0 == 0) goto L_0x0018
            com.google.android.exoplayer2.upstream.cache.Cache r2 = r4.a
            r2.releaseHoleSpan(r0)
            r4.q = r1
        L_0x0018:
            return
        L_0x0019:
            r0 = move-exception
            r4.l = r1
            r4.m = r1
            com.google.android.exoplayer2.upstream.cache.CacheSpan r2 = r4.q
            if (r2 == 0) goto L_0x0029
            com.google.android.exoplayer2.upstream.cache.Cache r3 = r4.a
            r3.releaseHoleSpan(r2)
            r4.q = r1
        L_0x0029:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheDataSource.e():void");
    }

    private void a(Throwable th) {
        if (c() || (th instanceof Cache.CacheException)) {
            this.r = true;
        }
    }

    private int a(DataSpec dataSpec) {
        if (!this.h || !this.r) {
            return (!this.i || dataSpec.length != -1) ? -1 : 1;
        }
        return 0;
    }

    private void a(int i) {
        EventListener eventListener = this.f;
        if (eventListener != null) {
            eventListener.onCacheIgnored(i);
        }
    }

    private void f() {
        EventListener eventListener = this.f;
        if (eventListener != null && this.t > 0) {
            eventListener.onCachedBytesRead(this.a.getCacheSpace(), this.t);
            this.t = 0L;
        }
    }
}
