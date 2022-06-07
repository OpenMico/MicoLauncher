package com.google.android.exoplayer2.offline;

import android.util.SparseArray;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class DefaultDownloaderFactory implements DownloaderFactory {
    private static final SparseArray<Constructor<? extends Downloader>> a = a();
    private final CacheDataSource.Factory b;
    private final Executor c;

    @Deprecated
    public DefaultDownloaderFactory(CacheDataSource.Factory factory) {
        this(factory, $$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo.INSTANCE);
    }

    public DefaultDownloaderFactory(CacheDataSource.Factory factory, Executor executor) {
        this.b = (CacheDataSource.Factory) Assertions.checkNotNull(factory);
        this.c = (Executor) Assertions.checkNotNull(executor);
    }

    @Override // com.google.android.exoplayer2.offline.DownloaderFactory
    public Downloader createDownloader(DownloadRequest downloadRequest) {
        int inferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(downloadRequest.uri, downloadRequest.mimeType);
        if (inferContentTypeForUriAndMimeType == 4) {
            return new ProgressiveDownloader(new MediaItem.Builder().setUri(downloadRequest.uri).setCustomCacheKey(downloadRequest.customCacheKey).build(), this.b, this.c);
        }
        switch (inferContentTypeForUriAndMimeType) {
            case 0:
            case 1:
            case 2:
                return a(downloadRequest, inferContentTypeForUriAndMimeType);
            default:
                StringBuilder sb = new StringBuilder(29);
                sb.append("Unsupported type: ");
                sb.append(inferContentTypeForUriAndMimeType);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    private Downloader a(DownloadRequest downloadRequest, int i) {
        Constructor<? extends Downloader> constructor = a.get(i);
        if (constructor != null) {
            try {
                return (Downloader) constructor.newInstance(new MediaItem.Builder().setUri(downloadRequest.uri).setStreamKeys(downloadRequest.streamKeys).setCustomCacheKey(downloadRequest.customCacheKey).setDrmKeySetId(downloadRequest.keySetId).build(), this.b, this.c);
            } catch (Exception unused) {
                StringBuilder sb = new StringBuilder(61);
                sb.append("Failed to instantiate downloader for content type ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder(43);
            sb2.append("Module missing for content type ");
            sb2.append(i);
            throw new IllegalStateException(sb2.toString());
        }
    }

    private static SparseArray<Constructor<? extends Downloader>> a() {
        SparseArray<Constructor<? extends Downloader>> sparseArray = new SparseArray<>();
        try {
            sparseArray.put(0, a(Class.forName("com.google.android.exoplayer2.source.dash.offline.DashDownloader")));
        } catch (ClassNotFoundException unused) {
        }
        try {
            sparseArray.put(2, a(Class.forName("com.google.android.exoplayer2.source.hls.offline.HlsDownloader")));
        } catch (ClassNotFoundException unused2) {
        }
        try {
            sparseArray.put(1, a(Class.forName("com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloader")));
        } catch (ClassNotFoundException unused3) {
        }
        return sparseArray;
    }

    private static Constructor<? extends Downloader> a(Class<?> cls) {
        try {
            return cls.asSubclass(Downloader.class).getConstructor(MediaItem.class, CacheDataSource.Factory.class, Executor.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Downloader constructor missing", e);
        }
    }
}
