package com.google.android.exoplayer2.source;

import android.content.Context;
import android.net.Uri;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public final class DefaultMediaSourceFactory implements MediaSourceFactory {
    private final DataSource.Factory a;
    private final SparseArray<MediaSourceFactory> b;
    private final int[] c;
    @Nullable
    private AdsLoaderProvider d;
    @Nullable
    private AdViewProvider e;
    @Nullable
    private LoadErrorHandlingPolicy f;
    private long g;
    private long h;
    private long i;
    private float j;
    private float k;

    /* loaded from: classes2.dex */
    public interface AdsLoaderProvider {
        @Nullable
        AdsLoader getAdsLoader(MediaItem.AdsConfiguration adsConfiguration);
    }

    public DefaultMediaSourceFactory(Context context) {
        this(new DefaultDataSourceFactory(context));
    }

    public DefaultMediaSourceFactory(Context context, ExtractorsFactory extractorsFactory) {
        this(new DefaultDataSourceFactory(context), extractorsFactory);
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory) {
        this(factory, new DefaultExtractorsFactory());
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
        this.a = factory;
        this.b = a(factory, extractorsFactory);
        this.c = new int[this.b.size()];
        for (int i = 0; i < this.b.size(); i++) {
            this.c[i] = this.b.keyAt(i);
        }
        this.g = C.TIME_UNSET;
        this.h = C.TIME_UNSET;
        this.i = C.TIME_UNSET;
        this.j = -3.4028235E38f;
        this.k = -3.4028235E38f;
    }

    public DefaultMediaSourceFactory setAdsLoaderProvider(@Nullable AdsLoaderProvider adsLoaderProvider) {
        this.d = adsLoaderProvider;
        return this;
    }

    public DefaultMediaSourceFactory setAdViewProvider(@Nullable AdViewProvider adViewProvider) {
        this.e = adViewProvider;
        return this;
    }

    public DefaultMediaSourceFactory setLiveTargetOffsetMs(long j) {
        this.g = j;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinOffsetMs(long j) {
        this.h = j;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxOffsetMs(long j) {
        this.i = j;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinSpeed(float f) {
        this.j = f;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxSpeed(float f) {
        this.k = f;
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).setDrmHttpDataSourceFactory(factory);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmUserAgent(@Nullable String str) {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).setDrmUserAgent(str);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).setDrmSessionManager(drmSessionManager);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).setDrmSessionManagerProvider(drmSessionManagerProvider);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        this.f = loadErrorHandlingPolicy;
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public DefaultMediaSourceFactory setStreamKeys(@Nullable List<StreamKey> list) {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).setStreamKeys(list);
        }
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public int[] getSupportedTypes() {
        int[] iArr = this.c;
        return Arrays.copyOf(iArr, iArr.length);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public MediaSource createMediaSource(MediaItem mediaItem) {
        long j;
        float f;
        float f2;
        long j2;
        long j3;
        Assertions.checkNotNull(mediaItem.playbackProperties);
        int inferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(mediaItem.playbackProperties.uri, mediaItem.playbackProperties.mimeType);
        MediaSourceFactory mediaSourceFactory = this.b.get(inferContentTypeForUriAndMimeType);
        StringBuilder sb = new StringBuilder(68);
        sb.append("No suitable media source factory found for content type: ");
        sb.append(inferContentTypeForUriAndMimeType);
        Assertions.checkNotNull(mediaSourceFactory, sb.toString());
        if ((mediaItem.liveConfiguration.targetOffsetMs == C.TIME_UNSET && this.g != C.TIME_UNSET) || ((mediaItem.liveConfiguration.minPlaybackSpeed == -3.4028235E38f && this.j != -3.4028235E38f) || ((mediaItem.liveConfiguration.maxPlaybackSpeed == -3.4028235E38f && this.k != -3.4028235E38f) || ((mediaItem.liveConfiguration.minOffsetMs == C.TIME_UNSET && this.h != C.TIME_UNSET) || (mediaItem.liveConfiguration.maxOffsetMs == C.TIME_UNSET && this.i != C.TIME_UNSET))))) {
            MediaItem.Builder buildUpon = mediaItem.buildUpon();
            if (mediaItem.liveConfiguration.targetOffsetMs == C.TIME_UNSET) {
                j = this.g;
            } else {
                j = mediaItem.liveConfiguration.targetOffsetMs;
            }
            MediaItem.Builder liveTargetOffsetMs = buildUpon.setLiveTargetOffsetMs(j);
            if (mediaItem.liveConfiguration.minPlaybackSpeed == -3.4028235E38f) {
                f = this.j;
            } else {
                f = mediaItem.liveConfiguration.minPlaybackSpeed;
            }
            MediaItem.Builder liveMinPlaybackSpeed = liveTargetOffsetMs.setLiveMinPlaybackSpeed(f);
            if (mediaItem.liveConfiguration.maxPlaybackSpeed == -3.4028235E38f) {
                f2 = this.k;
            } else {
                f2 = mediaItem.liveConfiguration.maxPlaybackSpeed;
            }
            MediaItem.Builder liveMaxPlaybackSpeed = liveMinPlaybackSpeed.setLiveMaxPlaybackSpeed(f2);
            if (mediaItem.liveConfiguration.minOffsetMs == C.TIME_UNSET) {
                j2 = this.h;
            } else {
                j2 = mediaItem.liveConfiguration.minOffsetMs;
            }
            MediaItem.Builder liveMinOffsetMs = liveMaxPlaybackSpeed.setLiveMinOffsetMs(j2);
            if (mediaItem.liveConfiguration.maxOffsetMs == C.TIME_UNSET) {
                j3 = this.i;
            } else {
                j3 = mediaItem.liveConfiguration.maxOffsetMs;
            }
            mediaItem = liveMinOffsetMs.setLiveMaxOffsetMs(j3).build();
        }
        MediaSource createMediaSource = mediaSourceFactory.createMediaSource(mediaItem);
        List<MediaItem.Subtitle> list = ((MediaItem.PlaybackProperties) Util.castNonNull(mediaItem.playbackProperties)).subtitles;
        if (!list.isEmpty()) {
            MediaSource[] mediaSourceArr = new MediaSource[list.size() + 1];
            int i = 0;
            mediaSourceArr[0] = createMediaSource;
            SingleSampleMediaSource.Factory loadErrorHandlingPolicy = new SingleSampleMediaSource.Factory(this.a).setLoadErrorHandlingPolicy(this.f);
            while (i < list.size()) {
                int i2 = i + 1;
                mediaSourceArr[i2] = loadErrorHandlingPolicy.createMediaSource(list.get(i), C.TIME_UNSET);
                i = i2;
            }
            createMediaSource = new MergingMediaSource(mediaSourceArr);
        }
        return b(mediaItem, a(mediaItem, createMediaSource));
    }

    private static MediaSource a(MediaItem mediaItem, MediaSource mediaSource) {
        return (mediaItem.clippingProperties.startPositionMs == 0 && mediaItem.clippingProperties.endPositionMs == Long.MIN_VALUE && !mediaItem.clippingProperties.relativeToDefaultPosition) ? mediaSource : new ClippingMediaSource(mediaSource, C.msToUs(mediaItem.clippingProperties.startPositionMs), C.msToUs(mediaItem.clippingProperties.endPositionMs), !mediaItem.clippingProperties.startsAtKeyFrame, mediaItem.clippingProperties.relativeToLiveWindow, mediaItem.clippingProperties.relativeToDefaultPosition);
    }

    private MediaSource b(MediaItem mediaItem, MediaSource mediaSource) {
        Object obj;
        Assertions.checkNotNull(mediaItem.playbackProperties);
        MediaItem.AdsConfiguration adsConfiguration = mediaItem.playbackProperties.adsConfiguration;
        if (adsConfiguration == null) {
            return mediaSource;
        }
        AdsLoaderProvider adsLoaderProvider = this.d;
        AdViewProvider adViewProvider = this.e;
        if (adsLoaderProvider == null || adViewProvider == null) {
            Log.w("DefaultMediaSourceFactory", "Playing media without ads. Configure ad support by calling setAdsLoaderProvider and setAdViewProvider.");
            return mediaSource;
        }
        AdsLoader adsLoader = adsLoaderProvider.getAdsLoader(adsConfiguration);
        if (adsLoader == null) {
            Log.w("DefaultMediaSourceFactory", "Playing media without ads, as no AdsLoader was provided.");
            return mediaSource;
        }
        DataSpec dataSpec = new DataSpec(adsConfiguration.adTagUri);
        if (adsConfiguration.adsId != null) {
            obj = adsConfiguration.adsId;
        } else {
            obj = ImmutableList.of((Uri) mediaItem.mediaId, mediaItem.playbackProperties.uri, adsConfiguration.adTagUri);
        }
        return new AdsMediaSource(mediaSource, dataSpec, obj, this, adsLoader, adViewProvider);
    }

    private static SparseArray<MediaSourceFactory> a(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
        SparseArray<MediaSourceFactory> sparseArray = new SparseArray<>();
        try {
            sparseArray.put(0, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.dash.DashMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(DataSource.Factory.class).newInstance(factory));
        } catch (Exception unused) {
        }
        try {
            sparseArray.put(1, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(DataSource.Factory.class).newInstance(factory));
        } catch (Exception unused2) {
        }
        try {
            sparseArray.put(2, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(DataSource.Factory.class).newInstance(factory));
        } catch (Exception unused3) {
        }
        try {
            sparseArray.put(3, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.rtsp.RtspMediaSource$Factory").asSubclass(MediaSourceFactory.class).getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (Exception unused4) {
        }
        sparseArray.put(4, new ProgressiveMediaSource.Factory(factory, extractorsFactory));
        return sparseArray;
    }
}
