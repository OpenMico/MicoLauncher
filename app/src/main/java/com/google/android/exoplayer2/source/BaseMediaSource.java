package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class BaseMediaSource implements MediaSource {
    private final ArrayList<MediaSource.MediaSourceCaller> a = new ArrayList<>(1);
    private final HashSet<MediaSource.MediaSourceCaller> b = new HashSet<>(1);
    private final MediaSourceEventListener.EventDispatcher c = new MediaSourceEventListener.EventDispatcher();
    private final DrmSessionEventListener.EventDispatcher d = new DrmSessionEventListener.EventDispatcher();
    @Nullable
    private Looper e;
    @Nullable
    private Timeline f;

    protected void disableInternal() {
    }

    protected void enableInternal() {
    }

    protected abstract void prepareSourceInternal(@Nullable TransferListener transferListener);

    protected abstract void releaseSourceInternal();

    protected final void refreshSourceInfo(Timeline timeline) {
        this.f = timeline;
        Iterator<MediaSource.MediaSourceCaller> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().onSourceInfoRefreshed(this, timeline);
        }
    }

    protected final MediaSourceEventListener.EventDispatcher createEventDispatcher(@Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        return this.c.withParameters(0, mediaPeriodId, 0L);
    }

    protected final MediaSourceEventListener.EventDispatcher createEventDispatcher(MediaSource.MediaPeriodId mediaPeriodId, long j) {
        Assertions.checkNotNull(mediaPeriodId);
        return this.c.withParameters(0, mediaPeriodId, j);
    }

    protected final MediaSourceEventListener.EventDispatcher createEventDispatcher(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long j) {
        return this.c.withParameters(i, mediaPeriodId, j);
    }

    protected final DrmSessionEventListener.EventDispatcher createDrmEventDispatcher(@Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        return this.d.withParameters(0, mediaPeriodId);
    }

    protected final DrmSessionEventListener.EventDispatcher createDrmEventDispatcher(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        return this.d.withParameters(i, mediaPeriodId);
    }

    protected final boolean isEnabled() {
        return !this.b.isEmpty();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(mediaSourceEventListener);
        this.c.addEventListener(handler, mediaSourceEventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void removeEventListener(MediaSourceEventListener mediaSourceEventListener) {
        this.c.removeEventListener(mediaSourceEventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void addDrmEventListener(Handler handler, DrmSessionEventListener drmSessionEventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(drmSessionEventListener);
        this.d.addEventListener(handler, drmSessionEventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void removeDrmEventListener(DrmSessionEventListener drmSessionEventListener) {
        this.d.removeEventListener(drmSessionEventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void prepareSource(MediaSource.MediaSourceCaller mediaSourceCaller, @Nullable TransferListener transferListener) {
        Looper myLooper = Looper.myLooper();
        Looper looper = this.e;
        Assertions.checkArgument(looper == null || looper == myLooper);
        Timeline timeline = this.f;
        this.a.add(mediaSourceCaller);
        if (this.e == null) {
            this.e = myLooper;
            this.b.add(mediaSourceCaller);
            prepareSourceInternal(transferListener);
        } else if (timeline != null) {
            enable(mediaSourceCaller);
            mediaSourceCaller.onSourceInfoRefreshed(this, timeline);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void enable(MediaSource.MediaSourceCaller mediaSourceCaller) {
        Assertions.checkNotNull(this.e);
        boolean isEmpty = this.b.isEmpty();
        this.b.add(mediaSourceCaller);
        if (isEmpty) {
            enableInternal();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void disable(MediaSource.MediaSourceCaller mediaSourceCaller) {
        boolean z = !this.b.isEmpty();
        this.b.remove(mediaSourceCaller);
        if (z && this.b.isEmpty()) {
            disableInternal();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void releaseSource(MediaSource.MediaSourceCaller mediaSourceCaller) {
        this.a.remove(mediaSourceCaller);
        if (this.a.isEmpty()) {
            this.e = null;
            this.f = null;
            this.b.clear();
            releaseSourceInternal();
            return;
        }
        disable(mediaSourceCaller);
    }
}
