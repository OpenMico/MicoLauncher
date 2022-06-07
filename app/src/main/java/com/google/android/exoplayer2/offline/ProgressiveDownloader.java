package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class ProgressiveDownloader implements Downloader {
    private final Executor a;
    private final DataSpec b;
    private final CacheDataSource c;
    private final CacheWriter d;
    @Nullable
    private final PriorityTaskManager e;
    @Nullable
    private Downloader.ProgressListener f;
    private volatile RunnableFutureTask<Void, IOException> g;
    private volatile boolean h;

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory) {
        this(mediaItem, factory, $$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo.INSTANCE);
    }

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory, Executor executor) {
        this.a = (Executor) Assertions.checkNotNull(executor);
        Assertions.checkNotNull(mediaItem.playbackProperties);
        this.b = new DataSpec.Builder().setUri(mediaItem.playbackProperties.uri).setKey(mediaItem.playbackProperties.customCacheKey).setFlags(4).build();
        this.c = factory.createDataSourceForDownloading();
        this.d = new CacheWriter(this.c, this.b, null, new CacheWriter.ProgressListener() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$ProgressiveDownloader$6OZWgZ0hQxBkSHJkJaiynzVexUg
            @Override // com.google.android.exoplayer2.upstream.cache.CacheWriter.ProgressListener
            public final void onProgress(long j, long j2, long j3) {
                ProgressiveDownloader.this.a(j, j2, j3);
            }
        });
        this.e = factory.getUpstreamPriorityTaskManager();
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void download(@Nullable Downloader.ProgressListener progressListener) throws IOException, InterruptedException {
        this.f = progressListener;
        this.g = new RunnableFutureTask<Void, IOException>() { // from class: com.google.android.exoplayer2.offline.ProgressiveDownloader.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public Void doWork() throws IOException {
                ProgressiveDownloader.this.d.cache();
                return null;
            }

            @Override // com.google.android.exoplayer2.util.RunnableFutureTask
            protected void cancelWork() {
                ProgressiveDownloader.this.d.cancel();
            }
        };
        PriorityTaskManager priorityTaskManager = this.e;
        if (priorityTaskManager != null) {
            priorityTaskManager.add(-1000);
        }
        boolean z = false;
        while (!z) {
            try {
                if (this.h) {
                    break;
                }
                if (this.e != null) {
                    this.e.proceed(-1000);
                }
                this.a.execute(this.g);
                try {
                    this.g.get();
                    z = true;
                } catch (ExecutionException e) {
                    Throwable th = (Throwable) Assertions.checkNotNull(e.getCause());
                    if (!(th instanceof PriorityTaskManager.PriorityTooLowException)) {
                        if (!(th instanceof IOException)) {
                            Util.sneakyThrow(th);
                        } else {
                            throw ((IOException) th);
                        }
                    }
                }
            } finally {
                this.g.blockUntilFinished();
                PriorityTaskManager priorityTaskManager2 = this.e;
                if (priorityTaskManager2 != null) {
                    priorityTaskManager2.remove(-1000);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void cancel() {
        this.h = true;
        RunnableFutureTask<Void, IOException> runnableFutureTask = this.g;
        if (runnableFutureTask != null) {
            runnableFutureTask.cancel(true);
        }
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void remove() {
        this.c.getCache().removeResource(this.c.getCacheKeyFactory().buildCacheKey(this.b));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j, long j2, long j3) {
        if (this.f != null) {
            this.f.onProgress(j, j2, (j == -1 || j == 0) ? -1.0f : (((float) j2) * 100.0f) / ((float) j));
        }
    }
}
