package com.google.android.exoplayer2.source;

import android.os.Handler;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UnknownNull;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;

/* loaded from: classes2.dex */
public abstract class CompositeMediaSource<T> extends BaseMediaSource {
    private final HashMap<T, b<T>> a = new HashMap<>();
    @Nullable
    private Handler b;
    @Nullable
    private TransferListener c;

    @Nullable
    protected MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(@UnknownNull T t, MediaSource.MediaPeriodId mediaPeriodId) {
        return mediaPeriodId;
    }

    protected long getMediaTimeForChildMediaTime(@UnknownNull T t, long j) {
        return j;
    }

    protected int getWindowIndexForChildWindowIndex(@UnknownNull T t, int i) {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: onChildSourceInfoRefreshed */
    public abstract void a(@UnknownNull T t, MediaSource mediaSource, Timeline timeline);

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.c = transferListener;
        this.b = Util.createHandlerForCurrentLooper();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    @CallSuper
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        for (b<T> bVar : this.a.values()) {
            bVar.a.maybeThrowSourceInfoRefreshError();
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    protected void enableInternal() {
        for (b<T> bVar : this.a.values()) {
            bVar.a.enable(bVar.b);
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    public void disableInternal() {
        for (b<T> bVar : this.a.values()) {
            bVar.a.disable(bVar.b);
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    public void releaseSourceInternal() {
        for (b<T> bVar : this.a.values()) {
            bVar.a.releaseSource(bVar.b);
            bVar.a.removeEventListener(bVar.c);
            bVar.a.removeDrmEventListener(bVar.c);
        }
        this.a.clear();
    }

    public final void prepareChildSource(@UnknownNull final T t, MediaSource mediaSource) {
        Assertions.checkArgument(!this.a.containsKey(t));
        MediaSource.MediaSourceCaller mediaSourceCaller = new MediaSource.MediaSourceCaller() { // from class: com.google.android.exoplayer2.source.-$$Lambda$CompositeMediaSource$6vZhOVDAG9qE3yNQpXywGMrUQUk
            @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
            public final void onSourceInfoRefreshed(MediaSource mediaSource2, Timeline timeline) {
                CompositeMediaSource.this.a(t, mediaSource2, timeline);
            }
        };
        a aVar = new a(t);
        this.a.put(t, new b<>(mediaSource, mediaSourceCaller, aVar));
        mediaSource.addEventListener((Handler) Assertions.checkNotNull(this.b), aVar);
        mediaSource.addDrmEventListener((Handler) Assertions.checkNotNull(this.b), aVar);
        mediaSource.prepareSource(mediaSourceCaller, this.c);
        if (!isEnabled()) {
            mediaSource.disable(mediaSourceCaller);
        }
    }

    protected final void enableChildSource(@UnknownNull T t) {
        b bVar = (b) Assertions.checkNotNull(this.a.get(t));
        bVar.a.enable(bVar.b);
    }

    protected final void disableChildSource(@UnknownNull T t) {
        b bVar = (b) Assertions.checkNotNull(this.a.get(t));
        bVar.a.disable(bVar.b);
    }

    public final void releaseChildSource(@UnknownNull T t) {
        b bVar = (b) Assertions.checkNotNull(this.a.remove(t));
        bVar.a.releaseSource(bVar.b);
        bVar.a.removeEventListener(bVar.c);
        bVar.a.removeDrmEventListener(bVar.c);
    }

    /* loaded from: classes2.dex */
    public static final class b<T> {
        public final MediaSource a;
        public final MediaSource.MediaSourceCaller b;
        public final CompositeMediaSource<T>.a c;

        public b(MediaSource mediaSource, MediaSource.MediaSourceCaller mediaSourceCaller, CompositeMediaSource<T>.a aVar) {
            this.a = mediaSource;
            this.b = mediaSourceCaller;
            this.c = aVar;
        }
    }

    /* loaded from: classes2.dex */
    public final class a implements DrmSessionEventListener, MediaSourceEventListener {
        @UnknownNull
        private final T b;
        private MediaSourceEventListener.EventDispatcher c;
        private DrmSessionEventListener.EventDispatcher d;

        public a(T t) {
            CompositeMediaSource.this = r3;
            this.c = r3.createEventDispatcher(null);
            this.d = r3.createDrmEventDispatcher(null);
            this.b = t;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.loadStarted(loadEventInfo, a(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.loadCompleted(loadEventInfo, a(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCanceled(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.loadCanceled(loadEventInfo, a(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            if (a(i, mediaPeriodId)) {
                this.c.loadError(loadEventInfo, a(mediaLoadData), iOException, z);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onUpstreamDiscarded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.upstreamDiscarded(a(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.c.downstreamFormatChanged(a(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i2) {
            if (a(i, mediaPeriodId)) {
                this.d.drmSessionAcquired(i2);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmKeysLoaded();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
            if (a(i, mediaPeriodId)) {
                this.d.drmSessionManagerError(exc);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmKeysRestored();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmKeysRemoved();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.d.drmSessionReleased();
            }
        }

        private boolean a(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            MediaSource.MediaPeriodId mediaPeriodId2;
            if (mediaPeriodId != null) {
                mediaPeriodId2 = CompositeMediaSource.this.getMediaPeriodIdForChildMediaPeriodId(this.b, mediaPeriodId);
                if (mediaPeriodId2 == null) {
                    return false;
                }
            } else {
                mediaPeriodId2 = null;
            }
            int windowIndexForChildWindowIndex = CompositeMediaSource.this.getWindowIndexForChildWindowIndex(this.b, i);
            if (this.c.windowIndex != windowIndexForChildWindowIndex || !Util.areEqual(this.c.mediaPeriodId, mediaPeriodId2)) {
                this.c = CompositeMediaSource.this.createEventDispatcher(windowIndexForChildWindowIndex, mediaPeriodId2, 0L);
            }
            if (this.d.windowIndex == windowIndexForChildWindowIndex && Util.areEqual(this.d.mediaPeriodId, mediaPeriodId2)) {
                return true;
            }
            this.d = CompositeMediaSource.this.createDrmEventDispatcher(windowIndexForChildWindowIndex, mediaPeriodId2);
            return true;
        }

        private MediaLoadData a(MediaLoadData mediaLoadData) {
            long mediaTimeForChildMediaTime = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.b, mediaLoadData.mediaStartTimeMs);
            long mediaTimeForChildMediaTime2 = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.b, mediaLoadData.mediaEndTimeMs);
            return (mediaTimeForChildMediaTime == mediaLoadData.mediaStartTimeMs && mediaTimeForChildMediaTime2 == mediaLoadData.mediaEndTimeMs) ? mediaLoadData : new MediaLoadData(mediaLoadData.dataType, mediaLoadData.trackType, mediaLoadData.trackFormat, mediaLoadData.trackSelectionReason, mediaLoadData.trackSelectionData, mediaTimeForChildMediaTime, mediaTimeForChildMediaTime2);
        }
    }
}
