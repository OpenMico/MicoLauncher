package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SilenceMediaSource extends BaseMediaSource {
    private final long d;
    private final MediaItem e;
    private static final Format a = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setChannelCount(2).setSampleRate(44100).setPcmEncoding(2).build();
    public static final String MEDIA_ID = "SilenceMediaSource";
    private static final MediaItem b = new MediaItem.Builder().setMediaId(MEDIA_ID).setUri(Uri.EMPTY).setMimeType(a.sampleMimeType).build();
    private static final byte[] c = new byte[Util.getPcmFrameSize(2, 2) * 1024];

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
    }

    /* loaded from: classes2.dex */
    public static final class Factory {
        private long a;
        @Nullable
        private Object b;

        public Factory setDurationUs(long j) {
            this.a = j;
            return this;
        }

        public Factory setTag(@Nullable Object obj) {
            this.b = obj;
            return this;
        }

        public SilenceMediaSource createMediaSource() {
            Assertions.checkState(this.a > 0);
            return new SilenceMediaSource(this.a, SilenceMediaSource.b.buildUpon().setTag(this.b).build());
        }
    }

    public SilenceMediaSource(long j) {
        this(j, b);
    }

    private SilenceMediaSource(long j, MediaItem mediaItem) {
        Assertions.checkArgument(j >= 0);
        this.d = j;
        this.e = mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        refreshSourceInfo(new SinglePeriodTimeline(this.d, true, false, false, (Object) null, this.e));
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        return new a(this.d);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.e;
    }

    /* loaded from: classes2.dex */
    private static final class a implements MediaPeriod {
        private static final TrackGroupArray a = new TrackGroupArray(new TrackGroup(SilenceMediaSource.a));
        private final long b;
        private final ArrayList<SampleStream> c = new ArrayList<>();

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long j) {
            return false;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long j, boolean z) {
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            return Long.MIN_VALUE;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            return Long.MIN_VALUE;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() {
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            return C.TIME_UNSET;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long j) {
        }

        public a(long j) {
            this.b = j;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long j) {
            callback.onPrepared(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return a;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
            long a2 = a(j);
            for (int i = 0; i < exoTrackSelectionArr.length; i++) {
                if (sampleStreamArr[i] != null && (exoTrackSelectionArr[i] == null || !zArr[i])) {
                    this.c.remove(sampleStreamArr[i]);
                    sampleStreamArr[i] = null;
                }
                if (sampleStreamArr[i] == null && exoTrackSelectionArr[i] != null) {
                    b bVar = new b(this.b);
                    bVar.a(a2);
                    this.c.add(bVar);
                    sampleStreamArr[i] = bVar;
                    zArr2[i] = true;
                }
            }
            return a2;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long j) {
            long a2 = a(j);
            for (int i = 0; i < this.c.size(); i++) {
                ((b) this.c.get(i)).a(a2);
            }
            return a2;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
            return a(j);
        }

        private long a(long j) {
            return Util.constrainValue(j, 0L, this.b);
        }
    }

    /* loaded from: classes2.dex */
    private static final class b implements SampleStream {
        private final long a;
        private boolean b;
        private long c;

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return true;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() {
        }

        public b(long j) {
            this.a = SilenceMediaSource.c(j);
            a(0L);
        }

        public void a(long j) {
            this.c = Util.constrainValue(SilenceMediaSource.c(j), 0L, this.a);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            if (!this.b || (i & 2) != 0) {
                formatHolder.format = SilenceMediaSource.a;
                this.b = true;
                return -5;
            }
            long j = this.a;
            long j2 = this.c;
            long j3 = j - j2;
            if (j3 == 0) {
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            decoderInputBuffer.timeUs = SilenceMediaSource.d(j2);
            decoderInputBuffer.addFlag(1);
            int min = (int) Math.min(SilenceMediaSource.c.length, j3);
            if ((i & 4) == 0) {
                decoderInputBuffer.ensureSpaceForWrite(min);
                decoderInputBuffer.data.put(SilenceMediaSource.c, 0, min);
            }
            if ((i & 1) == 0) {
                this.c += min;
            }
            return -4;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j) {
            long j2 = this.c;
            a(j);
            return (int) ((this.c - j2) / SilenceMediaSource.c.length);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long c(long j) {
        return Util.getPcmFrameSize(2, 2) * ((j * 44100) / 1000000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long d(long j) {
        return ((j / Util.getPcmFrameSize(2, 2)) * 1000000) / 44100;
    }
}
