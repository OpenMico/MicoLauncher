package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import java.util.List;

/* loaded from: classes2.dex */
public interface MediaSourceFactory {
    MediaSource createMediaSource(MediaItem mediaItem);

    int[] getSupportedTypes();

    @Deprecated
    MediaSourceFactory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory);

    @Deprecated
    MediaSourceFactory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager);

    MediaSourceFactory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider);

    @Deprecated
    MediaSourceFactory setDrmUserAgent(@Nullable String str);

    MediaSourceFactory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy);

    @Deprecated
    default MediaSourceFactory setStreamKeys(@Nullable List<StreamKey> list) {
        return this;
    }

    @Deprecated
    default MediaSource createMediaSource(Uri uri) {
        return createMediaSource(MediaItem.fromUri(uri));
    }
}
