package com.xiaomi.micolauncher.common.player.base;

import android.content.Context;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.lang.ref.SoftReference;

/* loaded from: classes3.dex */
public class ExoPlayerUtil {
    private static volatile ExoPlayerUtil b;
    private SoftReference<MediaSourceFactory> a;

    public static ExoPlayerUtil getInstance() {
        if (b == null) {
            synchronized (ExoPlayerUtil.class) {
                if (b == null) {
                    b = new ExoPlayerUtil();
                }
            }
        }
        return b;
    }

    public MediaSourceFactory getMediaSourceFactory(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
        MediaSourceFactory mediaSourceFactory;
        SoftReference<MediaSourceFactory> softReference = this.a;
        if (softReference != null && (mediaSourceFactory = softReference.get()) != null) {
            return mediaSourceFactory;
        }
        DefaultMediaSourceFactory defaultMediaSourceFactory = new DefaultMediaSourceFactory(factory, extractorsFactory);
        this.a = new SoftReference<>(defaultMediaSourceFactory);
        return defaultMediaSourceFactory;
    }

    public static synchronized DefaultDataSourceFactory createDataSourceFactory(Context context, String str, TransferListener transferListener) {
        DefaultDataSourceFactory defaultDataSourceFactory;
        synchronized (ExoPlayerUtil.class) {
            defaultDataSourceFactory = new DefaultDataSourceFactory(context, transferListener, new DefaultHttpDataSourceFactory(str, transferListener, 8000, 8000, true));
        }
        return defaultDataSourceFactory;
    }
}
