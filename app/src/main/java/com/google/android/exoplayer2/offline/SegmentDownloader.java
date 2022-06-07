package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public abstract class SegmentDownloader<M extends FilterableManifest<M>> implements Downloader {
    private final DataSpec a;
    private final ParsingLoadable.Parser<M> b;
    private final ArrayList<StreamKey> c;
    private final CacheDataSource.Factory d;
    private final Cache e;
    private final CacheKeyFactory f;
    @Nullable
    private final PriorityTaskManager g;
    private final Executor h;
    private final ArrayList<RunnableFutureTask<?, ?>> i = new ArrayList<>();
    private volatile boolean j;

    protected abstract List<Segment> getSegments(DataSource dataSource, M m, boolean z) throws IOException, InterruptedException;

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class Segment implements Comparable<Segment> {
        public final DataSpec dataSpec;
        public final long startTimeUs;

        public Segment(long j, DataSpec dataSpec) {
            this.startTimeUs = j;
            this.dataSpec = dataSpec;
        }

        public int compareTo(Segment segment) {
            return Util.compareLong(this.startTimeUs, segment.startTimeUs);
        }
    }

    public SegmentDownloader(MediaItem mediaItem, ParsingLoadable.Parser<M> parser, CacheDataSource.Factory factory, Executor executor) {
        Assertions.checkNotNull(mediaItem.playbackProperties);
        this.a = getCompressibleDataSpec(mediaItem.playbackProperties.uri);
        this.b = parser;
        this.c = new ArrayList<>(mediaItem.playbackProperties.streamKeys);
        this.d = factory;
        this.h = executor;
        this.e = (Cache) Assertions.checkNotNull(factory.getCache());
        this.f = factory.getCacheKeyFactory();
        this.g = factory.getUpstreamPriorityTaskManager();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01ae A[LOOP:4: B:81:0x01a6->B:83:0x01ae, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01c7 A[LOOP:5: B:85:0x01c5->B:86:0x01c7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01dc  */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.google.android.exoplayer2.offline.SegmentDownloader] */
    @Override // com.google.android.exoplayer2.offline.Downloader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void download(@androidx.annotation.Nullable com.google.android.exoplayer2.offline.Downloader.ProgressListener r26) throws java.io.IOException, java.lang.InterruptedException {
        /*
            Method dump skipped, instructions count: 482
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.download(com.google.android.exoplayer2.offline.Downloader$ProgressListener):void");
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void cancel() {
        synchronized (this.i) {
            this.j = true;
            for (int i = 0; i < this.i.size(); i++) {
                this.i.get(i).cancel(true);
            }
        }
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public final void remove() {
        try {
            CacheDataSource createDataSourceForRemovingDownload = this.d.createDataSourceForRemovingDownload();
            try {
                List<Segment> segments = getSegments(createDataSourceForRemovingDownload, getManifest(createDataSourceForRemovingDownload, this.a, true), true);
                for (int i = 0; i < segments.size(); i++) {
                    this.e.removeResource(this.f.buildCacheKey(segments.get(i).dataSpec));
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (Exception unused2) {
            }
        } finally {
            this.e.removeResource(this.f.buildCacheKey(this.a));
        }
    }

    protected final M getManifest(final DataSource dataSource, final DataSpec dataSpec, boolean z) throws InterruptedException, IOException {
        return (M) ((FilterableManifest) execute(new RunnableFutureTask<M, IOException>() { // from class: com.google.android.exoplayer2.offline.SegmentDownloader.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public M doWork() throws IOException {
                return (M) ((FilterableManifest) ParsingLoadable.load(dataSource, SegmentDownloader.this.b, dataSpec, 4));
            }
        }, z));
    }

    /* JADX WARN: Can't wrap try/catch for region: R(5:22|36|23|(2:26|(2:28|44)(3:42|30|31))(2:25|45)|29) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0040, code lost:
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0042, code lost:
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0043, code lost:
        r0 = (java.lang.Throwable) com.google.android.exoplayer2.util.Assertions.checkNotNull(r4.getCause());
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004f, code lost:
        if ((r0 instanceof com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException) == false) goto L_0x0052;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0054, code lost:
        if ((r0 instanceof java.io.IOException) == false) goto L_0x0056;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0056, code lost:
        com.google.android.exoplayer2.util.Util.sneakyThrow(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0062, code lost:
        throw ((java.io.IOException) r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0063, code lost:
        r3.blockUntilFinished();
        b(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0069, code lost:
        throw r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected final <T> T execute(com.google.android.exoplayer2.util.RunnableFutureTask<T, ?> r3, boolean r4) throws java.lang.InterruptedException, java.io.IOException {
        /*
            r2 = this;
            if (r4 == 0) goto L_0x0020
            r3.run()
            java.lang.Object r3 = r3.get()     // Catch: ExecutionException -> 0x000a
            return r3
        L_0x000a:
            r4 = move-exception
            java.lang.Throwable r0 = r4.getCause()
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            boolean r1 = r0 instanceof java.io.IOException
            if (r1 != 0) goto L_0x001d
            com.google.android.exoplayer2.util.Util.sneakyThrow(r4)
            goto L_0x0020
        L_0x001d:
            java.io.IOException r0 = (java.io.IOException) r0
            throw r0
        L_0x0020:
            boolean r4 = r2.j
            if (r4 != 0) goto L_0x006a
            com.google.android.exoplayer2.util.PriorityTaskManager r4 = r2.g
            if (r4 == 0) goto L_0x002d
            r0 = -1000(0xfffffffffffffc18, float:NaN)
            r4.proceed(r0)
        L_0x002d:
            r2.a(r3)
            java.util.concurrent.Executor r4 = r2.h
            r4.execute(r3)
            java.lang.Object r4 = r3.get()     // Catch: ExecutionException -> 0x0042, all -> 0x0040
            r3.blockUntilFinished()
            r2.b(r3)
            return r4
        L_0x0040:
            r4 = move-exception
            goto L_0x0063
        L_0x0042:
            r4 = move-exception
            java.lang.Throwable r0 = r4.getCause()     // Catch: all -> 0x0040
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)     // Catch: all -> 0x0040
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch: all -> 0x0040
            boolean r1 = r0 instanceof com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException     // Catch: all -> 0x0040
            if (r1 == 0) goto L_0x0052
            goto L_0x0059
        L_0x0052:
            boolean r1 = r0 instanceof java.io.IOException     // Catch: all -> 0x0040
            if (r1 != 0) goto L_0x0060
            com.google.android.exoplayer2.util.Util.sneakyThrow(r4)     // Catch: all -> 0x0040
        L_0x0059:
            r3.blockUntilFinished()
            r2.b(r3)
            goto L_0x0020
        L_0x0060:
            java.io.IOException r0 = (java.io.IOException) r0     // Catch: all -> 0x0040
            throw r0     // Catch: all -> 0x0040
        L_0x0063:
            r3.blockUntilFinished()
            r2.b(r3)
            throw r4
        L_0x006a:
            java.lang.InterruptedException r3 = new java.lang.InterruptedException
            r3.<init>()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.execute(com.google.android.exoplayer2.util.RunnableFutureTask, boolean):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static DataSpec getCompressibleDataSpec(Uri uri) {
        return new DataSpec.Builder().setUri(uri).setFlags(1).build();
    }

    private <T> void a(RunnableFutureTask<T, ?> runnableFutureTask) throws InterruptedException {
        synchronized (this.i) {
            if (!this.j) {
                this.i.add(runnableFutureTask);
            } else {
                throw new InterruptedException();
            }
        }
    }

    private void b(RunnableFutureTask<?, ?> runnableFutureTask) {
        synchronized (this.i) {
            this.i.remove(runnableFutureTask);
        }
    }

    private void a(int i) {
        synchronized (this.i) {
            this.i.remove(i);
        }
    }

    private static void a(List<Segment> list, CacheKeyFactory cacheKeyFactory) {
        HashMap hashMap = new HashMap();
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Segment segment = list.get(i2);
            String buildCacheKey = cacheKeyFactory.buildCacheKey(segment.dataSpec);
            Integer num = (Integer) hashMap.get(buildCacheKey);
            Segment segment2 = num == null ? null : list.get(num.intValue());
            if (segment2 == null || segment.startTimeUs > segment2.startTimeUs + 20000000 || !a(segment2.dataSpec, segment.dataSpec)) {
                hashMap.put(buildCacheKey, Integer.valueOf(i));
                list.set(i, segment);
                i++;
            } else {
                long j = -1;
                if (segment.dataSpec.length != -1) {
                    j = segment2.dataSpec.length + segment.dataSpec.length;
                }
                list.set(((Integer) Assertions.checkNotNull(num)).intValue(), new Segment(segment2.startTimeUs, segment2.dataSpec.subrange(0L, j)));
            }
        }
        Util.removeRange(list, i, list.size());
    }

    private static boolean a(DataSpec dataSpec, DataSpec dataSpec2) {
        return dataSpec.uri.equals(dataSpec2.uri) && dataSpec.length != -1 && dataSpec.position + dataSpec.length == dataSpec2.position && Util.areEqual(dataSpec.key, dataSpec2.key) && dataSpec.flags == dataSpec2.flags && dataSpec.httpMethod == dataSpec2.httpMethod && dataSpec.httpRequestHeaders.equals(dataSpec2.httpRequestHeaders);
    }

    /* loaded from: classes2.dex */
    private static final class b extends RunnableFutureTask<Void, IOException> {
        public final Segment a;
        public final CacheDataSource b;
        public final byte[] c;
        @Nullable
        private final a d;
        private final CacheWriter e;

        public b(Segment segment, CacheDataSource cacheDataSource, @Nullable a aVar, byte[] bArr) {
            this.a = segment;
            this.b = cacheDataSource;
            this.d = aVar;
            this.c = bArr;
            this.e = new CacheWriter(cacheDataSource, segment.dataSpec, bArr, aVar);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Void doWork() throws IOException {
            this.e.cache();
            a aVar = this.d;
            if (aVar == null) {
                return null;
            }
            aVar.a();
            return null;
        }

        @Override // com.google.android.exoplayer2.util.RunnableFutureTask
        protected void cancelWork() {
            this.e.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a implements CacheWriter.ProgressListener {
        private final Downloader.ProgressListener a;
        private final long b;
        private final int c;
        private long d;
        private int e;

        public a(Downloader.ProgressListener progressListener, long j, int i, long j2, int i2) {
            this.a = progressListener;
            this.b = j;
            this.c = i;
            this.d = j2;
            this.e = i2;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CacheWriter.ProgressListener
        public void onProgress(long j, long j2, long j3) {
            this.d += j3;
            this.a.onProgress(this.b, this.d, b());
        }

        public void a() {
            this.e++;
            this.a.onProgress(this.b, this.d, b());
        }

        private float b() {
            long j = this.b;
            if (j != -1 && j != 0) {
                return (((float) this.d) * 100.0f) / ((float) j);
            }
            int i = this.c;
            if (i != 0) {
                return (this.e * 100.0f) / i;
            }
            return -1.0f;
        }
    }
}
