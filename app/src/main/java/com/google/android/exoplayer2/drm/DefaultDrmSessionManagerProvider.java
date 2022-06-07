package com.google.android.exoplayer2.drm;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import com.milink.base.contract.LockContract;
import java.util.Map;

/* loaded from: classes2.dex */
public final class DefaultDrmSessionManagerProvider implements DrmSessionManagerProvider {
    private final Object a = new Object();
    @GuardedBy(LockContract.Matcher.LOCK)
    private MediaItem.DrmConfiguration b;
    @GuardedBy(LockContract.Matcher.LOCK)
    private DrmSessionManager c;
    @Nullable
    private HttpDataSource.Factory d;
    @Nullable
    private String e;

    public void setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
        this.d = factory;
    }

    public void setDrmUserAgent(@Nullable String str) {
        this.e = str;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
    public DrmSessionManager get(MediaItem mediaItem) {
        DrmSessionManager drmSessionManager;
        Assertions.checkNotNull(mediaItem.playbackProperties);
        MediaItem.DrmConfiguration drmConfiguration = mediaItem.playbackProperties.drmConfiguration;
        if (drmConfiguration == null || Util.SDK_INT < 18) {
            return DrmSessionManager.DRM_UNSUPPORTED;
        }
        synchronized (this.a) {
            if (!Util.areEqual(drmConfiguration, this.b)) {
                this.b = drmConfiguration;
                this.c = a(drmConfiguration);
            }
            drmSessionManager = (DrmSessionManager) Assertions.checkNotNull(this.c);
        }
        return drmSessionManager;
    }

    @RequiresApi(18)
    private DrmSessionManager a(MediaItem.DrmConfiguration drmConfiguration) {
        HttpDataSource.Factory factory = this.d;
        if (factory == null) {
            factory = new DefaultHttpDataSource.Factory().setUserAgent(this.e);
        }
        HttpMediaDrmCallback httpMediaDrmCallback = new HttpMediaDrmCallback(drmConfiguration.licenseUri == null ? null : drmConfiguration.licenseUri.toString(), drmConfiguration.forceDefaultLicenseUri, factory);
        for (Map.Entry<String, String> entry : drmConfiguration.requestHeaders.entrySet()) {
            httpMediaDrmCallback.setKeyRequestProperty(entry.getKey(), entry.getValue());
        }
        DefaultDrmSessionManager build = new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(drmConfiguration.uuid, FrameworkMediaDrm.DEFAULT_PROVIDER).setMultiSession(drmConfiguration.multiSession).setPlayClearSamplesWithoutKeys(drmConfiguration.playClearContentWithoutKey).setUseDrmSessionsForClearContent(Ints.toArray(drmConfiguration.sessionForClearTypes)).build(httpMediaDrmCallback);
        build.setMode(0, drmConfiguration.getKeySetId());
        return build;
    }
}
