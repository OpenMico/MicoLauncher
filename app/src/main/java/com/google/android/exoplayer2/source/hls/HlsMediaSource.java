package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.FilteringHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class HlsMediaSource extends BaseMediaSource implements HlsPlaylistTracker.PrimaryPlaylistListener {
    public static final int METADATA_TYPE_EMSG = 3;
    public static final int METADATA_TYPE_ID3 = 1;
    private final HlsExtractorFactory a;
    private final MediaItem.PlaybackProperties b;
    private final HlsDataSourceFactory c;
    private final CompositeSequenceableLoaderFactory d;
    private final DrmSessionManager e;
    private final LoadErrorHandlingPolicy f;
    private final boolean g;
    private final int h;
    private final boolean i;
    private final HlsPlaylistTracker j;
    private final long k;
    private final MediaItem l;
    private MediaItem.LiveConfiguration m;
    @Nullable
    private TransferListener n;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface MetadataType {
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.hls");
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements MediaSourceFactory {
        private final HlsDataSourceFactory a;
        private HlsExtractorFactory b;
        private HlsPlaylistParserFactory c;
        private HlsPlaylistTracker.Factory d;
        private CompositeSequenceableLoaderFactory e;
        private boolean f;
        private DrmSessionManagerProvider g;
        private LoadErrorHandlingPolicy h;
        private boolean i;
        private int j;
        private boolean k;
        private List<StreamKey> l;
        @Nullable
        private Object m;
        private long n;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ DrmSessionManager a(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        public Factory(DataSource.Factory factory) {
            this(new DefaultHlsDataSourceFactory(factory));
        }

        public Factory(HlsDataSourceFactory hlsDataSourceFactory) {
            this.a = (HlsDataSourceFactory) Assertions.checkNotNull(hlsDataSourceFactory);
            this.g = new DefaultDrmSessionManagerProvider();
            this.c = new DefaultHlsPlaylistParserFactory();
            this.d = DefaultHlsPlaylistTracker.FACTORY;
            this.b = HlsExtractorFactory.DEFAULT;
            this.h = new DefaultLoadErrorHandlingPolicy();
            this.e = new DefaultCompositeSequenceableLoaderFactory();
            this.j = 1;
            this.l = Collections.emptyList();
            this.n = C.TIME_UNSET;
        }

        @Deprecated
        public Factory setTag(@Nullable Object obj) {
            this.m = obj;
            return this;
        }

        public Factory setExtractorFactory(@Nullable HlsExtractorFactory hlsExtractorFactory) {
            if (hlsExtractorFactory == null) {
                hlsExtractorFactory = HlsExtractorFactory.DEFAULT;
            }
            this.b = hlsExtractorFactory;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.h = loadErrorHandlingPolicy;
            return this;
        }

        public Factory setPlaylistParserFactory(@Nullable HlsPlaylistParserFactory hlsPlaylistParserFactory) {
            if (hlsPlaylistParserFactory == null) {
                hlsPlaylistParserFactory = new DefaultHlsPlaylistParserFactory();
            }
            this.c = hlsPlaylistParserFactory;
            return this;
        }

        public Factory setPlaylistTrackerFactory(@Nullable HlsPlaylistTracker.Factory factory) {
            if (factory == null) {
                factory = DefaultHlsPlaylistTracker.FACTORY;
            }
            this.d = factory;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(@Nullable CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            if (compositeSequenceableLoaderFactory == null) {
                compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            }
            this.e = compositeSequenceableLoaderFactory;
            return this;
        }

        public Factory setAllowChunklessPreparation(boolean z) {
            this.i = z;
            return this;
        }

        public Factory setMetadataType(int i) {
            this.j = i;
            return this;
        }

        public Factory setUseSessionKeys(boolean z) {
            this.k = z;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            if (drmSessionManagerProvider != null) {
                this.g = drmSessionManagerProvider;
                this.f = true;
            } else {
                this.g = new DefaultDrmSessionManagerProvider();
                this.f = false;
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManager(@Nullable final DrmSessionManager drmSessionManager) {
            if (drmSessionManager == null) {
                setDrmSessionManagerProvider((DrmSessionManagerProvider) null);
            } else {
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.hls.-$$Lambda$HlsMediaSource$Factory$olOJB_xLduaJ8vEaM67LrKXtgF0
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        DrmSessionManager a;
                        a = HlsMediaSource.Factory.a(DrmSessionManager.this, mediaItem);
                        return a;
                    }
                });
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            if (!this.f) {
                ((DefaultDrmSessionManagerProvider) this.g).setDrmHttpDataSourceFactory(factory);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmUserAgent(@Nullable String str) {
            if (!this.f) {
                ((DefaultDrmSessionManagerProvider) this.g).setDrmUserAgent(str);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setStreamKeys(@Nullable List<StreamKey> list) {
            if (list == null) {
                list = Collections.emptyList();
            }
            this.l = list;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public HlsMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_M3U8).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public HlsMediaSource createMediaSource(MediaItem mediaItem) {
            List<StreamKey> list;
            Assertions.checkNotNull(mediaItem.playbackProperties);
            HlsPlaylistParserFactory hlsPlaylistParserFactory = this.c;
            if (mediaItem.playbackProperties.streamKeys.isEmpty()) {
                list = this.l;
            } else {
                list = mediaItem.playbackProperties.streamKeys;
            }
            if (!list.isEmpty()) {
                hlsPlaylistParserFactory = new FilteringHlsPlaylistParserFactory(hlsPlaylistParserFactory, list);
            }
            boolean z = true;
            boolean z2 = mediaItem.playbackProperties.tag == null && this.m != null;
            if (!mediaItem.playbackProperties.streamKeys.isEmpty() || list.isEmpty()) {
                z = false;
            }
            MediaItem build = (!z2 || !z) ? z2 ? mediaItem.buildUpon().setTag(this.m).build() : z ? mediaItem.buildUpon().setStreamKeys(list).build() : mediaItem : mediaItem.buildUpon().setTag(this.m).setStreamKeys(list).build();
            HlsDataSourceFactory hlsDataSourceFactory = this.a;
            HlsExtractorFactory hlsExtractorFactory = this.b;
            CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory = this.e;
            DrmSessionManager drmSessionManager = this.g.get(build);
            LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.h;
            return new HlsMediaSource(build, hlsDataSourceFactory, hlsExtractorFactory, compositeSequenceableLoaderFactory, drmSessionManager, loadErrorHandlingPolicy, this.d.createTracker(this.a, loadErrorHandlingPolicy, hlsPlaylistParserFactory), this.n, this.i, this.j, this.k);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{2};
        }
    }

    private HlsMediaSource(MediaItem mediaItem, HlsDataSourceFactory hlsDataSourceFactory, HlsExtractorFactory hlsExtractorFactory, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistTracker hlsPlaylistTracker, long j, boolean z, int i, boolean z2) {
        this.b = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.l = mediaItem;
        this.m = mediaItem.liveConfiguration;
        this.c = hlsDataSourceFactory;
        this.a = hlsExtractorFactory;
        this.d = compositeSequenceableLoaderFactory;
        this.e = drmSessionManager;
        this.f = loadErrorHandlingPolicy;
        this.j = hlsPlaylistTracker;
        this.k = j;
        this.g = z;
        this.h = i;
        this.i = z2;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.l;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.n = transferListener;
        this.e.prepare();
        this.j.start(this.b.uri, createEventDispatcher(null), this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.j.maybeThrowPrimaryPlaylistRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        MediaSourceEventListener.EventDispatcher createEventDispatcher = createEventDispatcher(mediaPeriodId);
        return new HlsMediaPeriod(this.a, this.j, this.c, this.n, this.e, createDrmEventDispatcher(mediaPeriodId), this.f, createEventDispatcher, allocator, this.d, this.g, this.h, this.i);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((HlsMediaPeriod) mediaPeriod).release();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.j.stop();
        this.e.release();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PrimaryPlaylistListener
    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist hlsMediaPlaylist) {
        SinglePeriodTimeline singlePeriodTimeline;
        long usToMs = hlsMediaPlaylist.hasProgramDateTime ? C.usToMs(hlsMediaPlaylist.startTimeUs) : -9223372036854775807L;
        long j = (hlsMediaPlaylist.playlistType == 2 || hlsMediaPlaylist.playlistType == 1) ? usToMs : -9223372036854775807L;
        HlsManifest hlsManifest = new HlsManifest((HlsMasterPlaylist) Assertions.checkNotNull(this.j.getMasterPlaylist()), hlsMediaPlaylist);
        if (this.j.isLive()) {
            singlePeriodTimeline = a(hlsMediaPlaylist, j, usToMs, hlsManifest);
        } else {
            singlePeriodTimeline = b(hlsMediaPlaylist, j, usToMs, hlsManifest);
        }
        refreshSourceInfo(singlePeriodTimeline);
    }

    private SinglePeriodTimeline a(HlsMediaPlaylist hlsMediaPlaylist, long j, long j2, HlsManifest hlsManifest) {
        long j3;
        long initialStartTimeUs = hlsMediaPlaylist.startTimeUs - this.j.getInitialStartTimeUs();
        long j4 = hlsMediaPlaylist.hasEndTag ? initialStartTimeUs + hlsMediaPlaylist.durationUs : -9223372036854775807L;
        long a = a(hlsMediaPlaylist);
        if (this.m.targetOffsetMs != C.TIME_UNSET) {
            j3 = C.msToUs(this.m.targetOffsetMs);
        } else {
            j3 = b(hlsMediaPlaylist, a);
        }
        a(Util.constrainValue(j3, a, hlsMediaPlaylist.durationUs + a));
        long a2 = a(hlsMediaPlaylist, a);
        boolean z = true;
        if (hlsMediaPlaylist.playlistType != 2 || !hlsMediaPlaylist.hasPositiveStartOffset) {
            z = false;
        }
        return new SinglePeriodTimeline(j, j2, C.TIME_UNSET, j4, hlsMediaPlaylist.durationUs, initialStartTimeUs, a2, true, !hlsMediaPlaylist.hasEndTag, z, hlsManifest, this.l, this.m);
    }

    private SinglePeriodTimeline b(HlsMediaPlaylist hlsMediaPlaylist, long j, long j2, HlsManifest hlsManifest) {
        long j3;
        if (hlsMediaPlaylist.startOffsetUs == C.TIME_UNSET || hlsMediaPlaylist.segments.isEmpty()) {
            j3 = 0;
        } else if (hlsMediaPlaylist.preciseStart || hlsMediaPlaylist.startOffsetUs == hlsMediaPlaylist.durationUs) {
            j3 = hlsMediaPlaylist.startOffsetUs;
        } else {
            j3 = b(hlsMediaPlaylist.segments, hlsMediaPlaylist.startOffsetUs).relativeStartTimeUs;
        }
        return new SinglePeriodTimeline(j, j2, C.TIME_UNSET, hlsMediaPlaylist.durationUs, hlsMediaPlaylist.durationUs, 0L, j3, true, false, true, hlsManifest, this.l, null);
    }

    private long a(HlsMediaPlaylist hlsMediaPlaylist) {
        if (hlsMediaPlaylist.hasProgramDateTime) {
            return C.msToUs(Util.getNowUnixTimeMs(this.k)) - hlsMediaPlaylist.getEndTimeUs();
        }
        return 0L;
    }

    private long a(HlsMediaPlaylist hlsMediaPlaylist, long j) {
        long j2;
        if (hlsMediaPlaylist.startOffsetUs != C.TIME_UNSET) {
            j2 = hlsMediaPlaylist.startOffsetUs;
        } else {
            j2 = (hlsMediaPlaylist.durationUs + j) - C.msToUs(this.m.targetOffsetMs);
        }
        if (hlsMediaPlaylist.preciseStart) {
            return j2;
        }
        HlsMediaPlaylist.Part a = a(hlsMediaPlaylist.trailingParts, j2);
        if (a != null) {
            return a.relativeStartTimeUs;
        }
        if (hlsMediaPlaylist.segments.isEmpty()) {
            return 0L;
        }
        HlsMediaPlaylist.Segment b = b(hlsMediaPlaylist.segments, j2);
        HlsMediaPlaylist.Part a2 = a(b.parts, j2);
        if (a2 != null) {
            return a2.relativeStartTimeUs;
        }
        return b.relativeStartTimeUs;
    }

    private void a(long j) {
        long usToMs = C.usToMs(j);
        if (usToMs != this.m.targetOffsetMs) {
            this.m = this.l.buildUpon().setLiveTargetOffsetMs(usToMs).build().liveConfiguration;
        }
    }

    private static long b(HlsMediaPlaylist hlsMediaPlaylist, long j) {
        long j2;
        HlsMediaPlaylist.ServerControl serverControl = hlsMediaPlaylist.serverControl;
        if (hlsMediaPlaylist.startOffsetUs != C.TIME_UNSET) {
            j2 = hlsMediaPlaylist.durationUs - hlsMediaPlaylist.startOffsetUs;
        } else if (serverControl.partHoldBackUs != C.TIME_UNSET && hlsMediaPlaylist.partTargetDurationUs != C.TIME_UNSET) {
            j2 = serverControl.partHoldBackUs;
        } else if (serverControl.holdBackUs != C.TIME_UNSET) {
            j2 = serverControl.holdBackUs;
        } else {
            j2 = 3 * hlsMediaPlaylist.targetDurationUs;
        }
        return j2 + j;
    }

    @Nullable
    private static HlsMediaPlaylist.Part a(List<HlsMediaPlaylist.Part> list, long j) {
        HlsMediaPlaylist.Part part = null;
        for (int i = 0; i < list.size(); i++) {
            HlsMediaPlaylist.Part part2 = list.get(i);
            if (part2.relativeStartTimeUs <= j && part2.isIndependent) {
                part = part2;
            } else if (part2.relativeStartTimeUs > j) {
                break;
            }
        }
        return part;
    }

    private static HlsMediaPlaylist.Segment b(List<HlsMediaPlaylist.Segment> list, long j) {
        return list.get(Util.binarySearchFloor((List<? extends Comparable<? super Long>>) list, Long.valueOf(j), true, true));
    }
}
