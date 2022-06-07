package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.CompositeMediaSource;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MaskingMediaPeriod;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public final class AdsMediaSource extends CompositeMediaSource<MediaSource.MediaPeriodId> {
    private static final MediaSource.MediaPeriodId a = new MediaSource.MediaPeriodId(new Object());
    private final MediaSource b;
    private final MediaSourceFactory c;
    private final AdsLoader d;
    private final AdViewProvider e;
    private final DataSpec f;
    private final Object g;
    @Nullable
    private c j;
    @Nullable
    private Timeline k;
    @Nullable
    private AdPlaybackState l;
    private final Handler h = new Handler(Looper.getMainLooper());
    private final Timeline.Period i = new Timeline.Period();
    private a[][] m = new a[0];

    /* loaded from: classes2.dex */
    public static final class AdLoadException extends IOException {
        public static final int TYPE_AD = 0;
        public static final int TYPE_AD_GROUP = 1;
        public static final int TYPE_ALL_ADS = 2;
        public static final int TYPE_UNEXPECTED = 3;
        public final int type;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface Type {
        }

        public static AdLoadException createForAd(Exception exc) {
            return new AdLoadException(0, exc);
        }

        public static AdLoadException createForAdGroup(Exception exc, int i) {
            StringBuilder sb = new StringBuilder(35);
            sb.append("Failed to load ad group ");
            sb.append(i);
            return new AdLoadException(1, new IOException(sb.toString(), exc));
        }

        public static AdLoadException createForAllAds(Exception exc) {
            return new AdLoadException(2, exc);
        }

        public static AdLoadException createForUnexpected(RuntimeException runtimeException) {
            return new AdLoadException(3, runtimeException);
        }

        private AdLoadException(int i, Exception exc) {
            super(exc);
            this.type = i;
        }

        public RuntimeException getRuntimeExceptionForUnexpected() {
            Assertions.checkState(this.type == 3);
            return (RuntimeException) Assertions.checkNotNull(getCause());
        }
    }

    public AdsMediaSource(MediaSource mediaSource, DataSpec dataSpec, Object obj, MediaSourceFactory mediaSourceFactory, AdsLoader adsLoader, AdViewProvider adViewProvider) {
        this.b = mediaSource;
        this.c = mediaSourceFactory;
        this.d = adsLoader;
        this.e = adViewProvider;
        this.f = dataSpec;
        this.g = obj;
        adsLoader.setSupportedContentTypes(mediaSourceFactory.getSupportedTypes());
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.b.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        final c cVar = new c();
        this.j = cVar;
        prepareChildSource(a, this.b);
        this.h.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.-$$Lambda$AdsMediaSource$PpIdQS98YOLsopIDcC3VpFRScQc
            @Override // java.lang.Runnable
            public final void run() {
                AdsMediaSource.this.b(cVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(c cVar) {
        this.d.start(this, this.f, this.g, this.e, cVar);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        if (((AdPlaybackState) Assertions.checkNotNull(this.l)).adGroupCount <= 0 || !mediaPeriodId.isAd()) {
            MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j);
            maskingMediaPeriod.setMediaSource(this.b);
            maskingMediaPeriod.createPeriod(mediaPeriodId);
            return maskingMediaPeriod;
        }
        int i = mediaPeriodId.adGroupIndex;
        int i2 = mediaPeriodId.adIndexInAdGroup;
        a[][] aVarArr = this.m;
        if (aVarArr[i].length <= i2) {
            aVarArr[i] = (a[]) Arrays.copyOf(aVarArr[i], i2 + 1);
        }
        a aVar = this.m[i][i2];
        if (aVar == null) {
            aVar = new a(mediaPeriodId);
            this.m[i][i2] = aVar;
            a();
        }
        return aVar.a(mediaPeriodId, allocator, j);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        MaskingMediaPeriod maskingMediaPeriod = (MaskingMediaPeriod) mediaPeriod;
        MediaSource.MediaPeriodId mediaPeriodId = maskingMediaPeriod.id;
        if (mediaPeriodId.isAd()) {
            a aVar = (a) Assertions.checkNotNull(this.m[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup]);
            aVar.a(maskingMediaPeriod);
            if (aVar.d()) {
                aVar.b();
                this.m[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup] = null;
                return;
            }
            return;
        }
        maskingMediaPeriod.releasePeriod();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
        super.releaseSourceInternal();
        final c cVar = (c) Assertions.checkNotNull(this.j);
        this.j = null;
        cVar.a();
        this.k = null;
        this.l = null;
        this.m = new a[0];
        this.h.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.-$$Lambda$AdsMediaSource$hbb4uq5w80dElIF4NoGBqtEwrE4
            @Override // java.lang.Runnable
            public final void run() {
                AdsMediaSource.this.a(cVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(c cVar) {
        this.d.stop(this, cVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(MediaSource.MediaPeriodId mediaPeriodId, MediaSource mediaSource, Timeline timeline) {
        if (mediaPeriodId.isAd()) {
            int i = mediaPeriodId.adGroupIndex;
            ((a) Assertions.checkNotNull(this.m[i][mediaPeriodId.adIndexInAdGroup])).a(timeline);
        } else {
            boolean z = true;
            if (timeline.getPeriodCount() != 1) {
                z = false;
            }
            Assertions.checkArgument(z);
            this.k = timeline;
        }
        b();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(MediaSource.MediaPeriodId mediaPeriodId, MediaSource.MediaPeriodId mediaPeriodId2) {
        return mediaPeriodId.isAd() ? mediaPeriodId : mediaPeriodId2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AdPlaybackState adPlaybackState) {
        boolean z = false;
        if (this.l == null) {
            this.m = new a[adPlaybackState.adGroupCount];
            Arrays.fill(this.m, new a[0]);
        } else {
            if (adPlaybackState.adGroupCount == this.l.adGroupCount) {
                z = true;
            }
            Assertions.checkState(z);
        }
        this.l = adPlaybackState;
        a();
        b();
    }

    private void a() {
        Uri uri;
        AdPlaybackState adPlaybackState = this.l;
        if (adPlaybackState != null) {
            for (int i = 0; i < this.m.length; i++) {
                int i2 = 0;
                while (true) {
                    a[][] aVarArr = this.m;
                    if (i2 < aVarArr[i].length) {
                        a aVar = aVarArr[i][i2];
                        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
                        if (aVar != null && !aVar.c() && i2 < adGroup.uris.length && (uri = adGroup.uris[i2]) != null) {
                            MediaItem.Builder uri2 = new MediaItem.Builder().setUri(uri);
                            MediaItem.PlaybackProperties playbackProperties = this.b.getMediaItem().playbackProperties;
                            if (!(playbackProperties == null || playbackProperties.drmConfiguration == null)) {
                                MediaItem.DrmConfiguration drmConfiguration = playbackProperties.drmConfiguration;
                                uri2.setDrmUuid(drmConfiguration.uuid);
                                uri2.setDrmKeySetId(drmConfiguration.getKeySetId());
                                uri2.setDrmLicenseUri(drmConfiguration.licenseUri);
                                uri2.setDrmForceDefaultLicenseUri(drmConfiguration.forceDefaultLicenseUri);
                                uri2.setDrmLicenseRequestHeaders(drmConfiguration.requestHeaders);
                                uri2.setDrmMultiSession(drmConfiguration.multiSession);
                                uri2.setDrmPlayClearContentWithoutKey(drmConfiguration.playClearContentWithoutKey);
                                uri2.setDrmSessionForClearTypes(drmConfiguration.sessionForClearTypes);
                            }
                            aVar.a(this.c.createMediaSource(uri2.build()), uri);
                        }
                        i2++;
                    }
                }
            }
        }
    }

    private void b() {
        Timeline timeline = this.k;
        AdPlaybackState adPlaybackState = this.l;
        if (adPlaybackState != null && timeline != null) {
            if (adPlaybackState.adGroupCount == 0) {
                refreshSourceInfo(timeline);
                return;
            }
            this.l = this.l.withAdDurationsUs(c());
            refreshSourceInfo(new SinglePeriodAdTimeline(timeline, this.l));
        }
    }

    private long[][] c() {
        long[][] jArr = new long[this.m.length];
        int i = 0;
        while (true) {
            a[][] aVarArr = this.m;
            if (i >= aVarArr.length) {
                return jArr;
            }
            jArr[i] = new long[aVarArr[i].length];
            int i2 = 0;
            while (true) {
                a[][] aVarArr2 = this.m;
                if (i2 < aVarArr2[i].length) {
                    a aVar = aVarArr2[i][i2];
                    jArr[i][i2] = aVar == null ? C.TIME_UNSET : aVar.a();
                    i2++;
                }
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class c implements AdsLoader.EventListener {
        private final Handler b = Util.createHandlerForCurrentLooper();
        private volatile boolean c;

        public c() {
        }

        public void a() {
            this.c = true;
            this.b.removeCallbacksAndMessages(null);
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public void onAdPlaybackState(final AdPlaybackState adPlaybackState) {
            if (!this.c) {
                this.b.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.-$$Lambda$AdsMediaSource$c$mhEKw00B6KaI9ChWOwb7cM8iBZ8
                    @Override // java.lang.Runnable
                    public final void run() {
                        AdsMediaSource.c.this.a(adPlaybackState);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(AdPlaybackState adPlaybackState) {
            if (!this.c) {
                AdsMediaSource.this.a(adPlaybackState);
            }
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public void onAdLoadError(AdLoadException adLoadException, DataSpec dataSpec) {
            if (!this.c) {
                AdsMediaSource.this.createEventDispatcher(null).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), dataSpec, SystemClock.elapsedRealtime()), 6, (IOException) adLoadException, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class b implements MaskingMediaPeriod.PrepareListener {
        private final Uri b;

        public b(Uri uri) {
            this.b = uri;
        }

        @Override // com.google.android.exoplayer2.source.MaskingMediaPeriod.PrepareListener
        public void onPrepareComplete(final MediaSource.MediaPeriodId mediaPeriodId) {
            AdsMediaSource.this.h.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.-$$Lambda$AdsMediaSource$b$5saoFG8wUl30hz9KUagOHZ_V3EM
                @Override // java.lang.Runnable
                public final void run() {
                    AdsMediaSource.b.this.a(mediaPeriodId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(MediaSource.MediaPeriodId mediaPeriodId) {
            AdsMediaSource.this.d.handlePrepareComplete(AdsMediaSource.this, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        }

        @Override // com.google.android.exoplayer2.source.MaskingMediaPeriod.PrepareListener
        public void onPrepareError(final MediaSource.MediaPeriodId mediaPeriodId, final IOException iOException) {
            AdsMediaSource.this.createEventDispatcher(mediaPeriodId).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), new DataSpec(this.b), SystemClock.elapsedRealtime()), 6, (IOException) AdLoadException.createForAd(iOException), true);
            AdsMediaSource.this.h.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.-$$Lambda$AdsMediaSource$b$cqkjjwCrQVKbUO1uSsnCHcnpWgA
                @Override // java.lang.Runnable
                public final void run() {
                    AdsMediaSource.b.this.a(mediaPeriodId, iOException);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(MediaSource.MediaPeriodId mediaPeriodId, IOException iOException) {
            AdsMediaSource.this.d.handlePrepareError(AdsMediaSource.this, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, iOException);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a {
        private final MediaSource.MediaPeriodId b;
        private final List<MaskingMediaPeriod> c = new ArrayList();
        private Uri d;
        private MediaSource e;
        private Timeline f;

        public a(MediaSource.MediaPeriodId mediaPeriodId) {
            this.b = mediaPeriodId;
        }

        public void a(MediaSource mediaSource, Uri uri) {
            this.e = mediaSource;
            this.d = uri;
            for (int i = 0; i < this.c.size(); i++) {
                MaskingMediaPeriod maskingMediaPeriod = this.c.get(i);
                maskingMediaPeriod.setMediaSource(mediaSource);
                maskingMediaPeriod.setPrepareListener(new b(uri));
            }
            AdsMediaSource.this.prepareChildSource(this.b, mediaSource);
        }

        public MediaPeriod a(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
            MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j);
            this.c.add(maskingMediaPeriod);
            MediaSource mediaSource = this.e;
            if (mediaSource != null) {
                maskingMediaPeriod.setMediaSource(mediaSource);
                maskingMediaPeriod.setPrepareListener(new b((Uri) Assertions.checkNotNull(this.d)));
            }
            Timeline timeline = this.f;
            if (timeline != null) {
                maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(0), mediaPeriodId.windowSequenceNumber));
            }
            return maskingMediaPeriod;
        }

        public void a(Timeline timeline) {
            boolean z = true;
            if (timeline.getPeriodCount() != 1) {
                z = false;
            }
            Assertions.checkArgument(z);
            if (this.f == null) {
                Object uidOfPeriod = timeline.getUidOfPeriod(0);
                for (int i = 0; i < this.c.size(); i++) {
                    MaskingMediaPeriod maskingMediaPeriod = this.c.get(i);
                    maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(uidOfPeriod, maskingMediaPeriod.id.windowSequenceNumber));
                }
            }
            this.f = timeline;
        }

        public long a() {
            Timeline timeline = this.f;
            return timeline == null ? C.TIME_UNSET : timeline.getPeriod(0, AdsMediaSource.this.i).getDurationUs();
        }

        public void a(MaskingMediaPeriod maskingMediaPeriod) {
            this.c.remove(maskingMediaPeriod);
            maskingMediaPeriod.releasePeriod();
        }

        public void b() {
            if (c()) {
                AdsMediaSource.this.releaseChildSource(this.b);
            }
        }

        public boolean c() {
            return this.e != null;
        }

        public boolean d() {
            return this.c.isEmpty();
        }
    }
}
