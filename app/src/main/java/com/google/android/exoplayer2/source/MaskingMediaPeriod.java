package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class MaskingMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    private final long a;
    private final Allocator b;
    private MediaSource c;
    private MediaPeriod d;
    @Nullable
    private MediaPeriod.Callback e;
    @Nullable
    private PrepareListener f;
    private boolean g;
    private long h = C.TIME_UNSET;
    public final MediaSource.MediaPeriodId id;

    /* loaded from: classes2.dex */
    public interface PrepareListener {
        void onPrepareComplete(MediaSource.MediaPeriodId mediaPeriodId);

        void onPrepareError(MediaSource.MediaPeriodId mediaPeriodId, IOException iOException);
    }

    public MaskingMediaPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        this.id = mediaPeriodId;
        this.b = allocator;
        this.a = j;
    }

    public void setPrepareListener(PrepareListener prepareListener) {
        this.f = prepareListener;
    }

    public long getPreparePositionUs() {
        return this.a;
    }

    public void overridePreparePositionUs(long j) {
        this.h = j;
    }

    public long getPreparePositionOverrideUs() {
        return this.h;
    }

    public void setMediaSource(MediaSource mediaSource) {
        Assertions.checkState(this.c == null);
        this.c = mediaSource;
    }

    public void createPeriod(MediaSource.MediaPeriodId mediaPeriodId) {
        long a = a(this.a);
        this.d = ((MediaSource) Assertions.checkNotNull(this.c)).createPeriod(mediaPeriodId, this.b, a);
        if (this.e != null) {
            this.d.prepare(this, a);
        }
    }

    public void releasePeriod() {
        if (this.d != null) {
            ((MediaSource) Assertions.checkNotNull(this.c)).releasePeriod(this.d);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.e = callback;
        MediaPeriod mediaPeriod = this.d;
        if (mediaPeriod != null) {
            mediaPeriod.prepare(this, a(this.a));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        try {
            if (this.d != null) {
                this.d.maybeThrowPrepareError();
            } else if (this.c != null) {
                this.c.maybeThrowSourceInfoRefreshError();
            }
        } catch (IOException e) {
            PrepareListener prepareListener = this.f;
            if (prepareListener == null) {
                throw e;
            } else if (!this.g) {
                this.g = true;
                prepareListener.onPrepareError(this.id, e);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return ((MediaPeriod) Util.castNonNull(this.d)).getTrackGroups();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        long j2;
        long j3 = this.h;
        if (j3 == C.TIME_UNSET || j != this.a) {
            j2 = j;
        } else {
            this.h = C.TIME_UNSET;
            j2 = j3;
        }
        return ((MediaPeriod) Util.castNonNull(this.d)).selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j2);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        ((MediaPeriod) Util.castNonNull(this.d)).discardBuffer(j, z);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return ((MediaPeriod) Util.castNonNull(this.d)).readDiscontinuity();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return ((MediaPeriod) Util.castNonNull(this.d)).getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        return ((MediaPeriod) Util.castNonNull(this.d)).seekToUs(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return ((MediaPeriod) Util.castNonNull(this.d)).getAdjustedSeekPositionUs(j, seekParameters);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return ((MediaPeriod) Util.castNonNull(this.d)).getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        ((MediaPeriod) Util.castNonNull(this.d)).reevaluateBuffer(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        MediaPeriod mediaPeriod = this.d;
        return mediaPeriod != null && mediaPeriod.continueLoading(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        MediaPeriod mediaPeriod = this.d;
        return mediaPeriod != null && mediaPeriod.isLoading();
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Util.castNonNull(this.e)).onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Util.castNonNull(this.e)).onPrepared(this);
        PrepareListener prepareListener = this.f;
        if (prepareListener != null) {
            prepareListener.onPrepareComplete(this.id);
        }
    }

    private long a(long j) {
        long j2 = this.h;
        return j2 != C.TIME_UNSET ? j2 : j;
    }
}
