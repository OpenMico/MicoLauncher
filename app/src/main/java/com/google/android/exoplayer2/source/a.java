package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

/* compiled from: MergingMediaPeriod.java */
/* loaded from: classes2.dex */
final class a implements MediaPeriod, MediaPeriod.Callback {
    private final MediaPeriod[] a;
    private final CompositeSequenceableLoaderFactory c;
    @Nullable
    private MediaPeriod.Callback e;
    @Nullable
    private TrackGroupArray f;
    private SequenceableLoader h;
    private final ArrayList<MediaPeriod> d = new ArrayList<>();
    private final IdentityHashMap<SampleStream, Integer> b = new IdentityHashMap<>();
    private MediaPeriod[] g = new MediaPeriod[0];

    public a(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, long[] jArr, MediaPeriod... mediaPeriodArr) {
        this.c = compositeSequenceableLoaderFactory;
        this.a = mediaPeriodArr;
        this.h = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        for (int i = 0; i < mediaPeriodArr.length; i++) {
            if (jArr[i] != 0) {
                this.a[i] = new C0066a(mediaPeriodArr[i], jArr[i]);
            }
        }
    }

    public MediaPeriod a(int i) {
        MediaPeriod[] mediaPeriodArr = this.a;
        if (mediaPeriodArr[i] instanceof C0066a) {
            return ((C0066a) mediaPeriodArr[i]).a;
        }
        return mediaPeriodArr[i];
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.e = callback;
        Collections.addAll(this.d, this.a);
        for (MediaPeriod mediaPeriod : this.a) {
            mediaPeriod.prepare(this, j);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        for (MediaPeriod mediaPeriod : this.a) {
            mediaPeriod.maybeThrowPrepareError();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.f);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int[] iArr = new int[exoTrackSelectionArr.length];
        int[] iArr2 = new int[exoTrackSelectionArr.length];
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            Integer num = sampleStreamArr[i] == null ? null : this.b.get(sampleStreamArr[i]);
            iArr[i] = num == null ? -1 : num.intValue();
            iArr2[i] = -1;
            if (exoTrackSelectionArr[i] != null) {
                TrackGroup trackGroup = exoTrackSelectionArr[i].getTrackGroup();
                int i2 = 0;
                while (true) {
                    MediaPeriod[] mediaPeriodArr = this.a;
                    if (i2 >= mediaPeriodArr.length) {
                        break;
                    } else if (mediaPeriodArr[i2].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i] = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
            }
        }
        this.b.clear();
        SampleStream[] sampleStreamArr2 = new SampleStream[exoTrackSelectionArr.length];
        SampleStream[] sampleStreamArr3 = new SampleStream[exoTrackSelectionArr.length];
        ExoTrackSelection[] exoTrackSelectionArr2 = new ExoTrackSelection[exoTrackSelectionArr.length];
        ArrayList arrayList = new ArrayList(this.a.length);
        long j2 = j;
        int i3 = 0;
        while (i3 < this.a.length) {
            for (int i4 = 0; i4 < exoTrackSelectionArr.length; i4++) {
                sampleStreamArr3[i4] = iArr[i4] == i3 ? sampleStreamArr[i4] : null;
                exoTrackSelectionArr2[i4] = iArr2[i4] == i3 ? exoTrackSelectionArr[i4] : null;
            }
            long selectTracks = this.a[i3].selectTracks(exoTrackSelectionArr2, zArr, sampleStreamArr3, zArr2, j2);
            if (i3 == 0) {
                j2 = selectTracks;
            } else if (selectTracks != j2) {
                throw new IllegalStateException("Children enabled at different positions.");
            }
            boolean z = false;
            for (int i5 = 0; i5 < exoTrackSelectionArr.length; i5++) {
                boolean z2 = true;
                if (iArr2[i5] == i3) {
                    sampleStreamArr2[i5] = sampleStreamArr3[i5];
                    this.b.put((SampleStream) Assertions.checkNotNull(sampleStreamArr3[i5]), Integer.valueOf(i3));
                    z = true;
                } else if (iArr[i5] == i3) {
                    if (sampleStreamArr3[i5] != null) {
                        z2 = false;
                    }
                    Assertions.checkState(z2);
                }
            }
            if (z) {
                arrayList.add(this.a[i3]);
            }
            i3++;
            arrayList = arrayList;
            exoTrackSelectionArr2 = exoTrackSelectionArr2;
        }
        System.arraycopy(sampleStreamArr2, 0, sampleStreamArr, 0, sampleStreamArr2.length);
        this.g = (MediaPeriod[]) arrayList.toArray(new MediaPeriod[0]);
        this.h = this.c.createCompositeSequenceableLoader(this.g);
        return j2;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        for (MediaPeriod mediaPeriod : this.g) {
            mediaPeriod.discardBuffer(j, z);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        this.h.reevaluateBuffer(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        if (this.d.isEmpty()) {
            return this.h.continueLoading(j);
        }
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            this.d.get(i).continueLoading(j);
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.h.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.h.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        MediaPeriod[] mediaPeriodArr = this.g;
        long j = -9223372036854775807L;
        for (MediaPeriod mediaPeriod : mediaPeriodArr) {
            long readDiscontinuity = mediaPeriod.readDiscontinuity();
            if (readDiscontinuity != C.TIME_UNSET) {
                if (j == C.TIME_UNSET) {
                    MediaPeriod[] mediaPeriodArr2 = this.g;
                    for (MediaPeriod mediaPeriod2 : mediaPeriodArr2) {
                        if (mediaPeriod2 == mediaPeriod) {
                            break;
                        } else if (mediaPeriod2.seekToUs(readDiscontinuity) != readDiscontinuity) {
                            throw new IllegalStateException("Unexpected child seekToUs result.");
                        }
                    }
                    j = readDiscontinuity;
                } else if (readDiscontinuity != j) {
                    throw new IllegalStateException("Conflicting discontinuities.");
                }
            } else if (!(j == C.TIME_UNSET || mediaPeriod.seekToUs(j) == j)) {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.h.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        long seekToUs = this.g[0].seekToUs(j);
        int i = 1;
        while (true) {
            MediaPeriod[] mediaPeriodArr = this.g;
            if (i >= mediaPeriodArr.length) {
                return seekToUs;
            }
            if (mediaPeriodArr[i].seekToUs(seekToUs) == seekToUs) {
                i++;
            } else {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        MediaPeriod[] mediaPeriodArr = this.g;
        return (mediaPeriodArr.length > 0 ? mediaPeriodArr[0] : this.a[0]).getAdjustedSeekPositionUs(j, seekParameters);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod mediaPeriod) {
        this.d.remove(mediaPeriod);
        if (this.d.isEmpty()) {
            int i = 0;
            for (MediaPeriod mediaPeriod2 : this.a) {
                i += mediaPeriod2.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i];
            MediaPeriod[] mediaPeriodArr = this.a;
            int length = mediaPeriodArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                TrackGroupArray trackGroups = mediaPeriodArr[i2].getTrackGroups();
                int i4 = trackGroups.length;
                for (int i5 = 0; i5 < i4; i5++) {
                    i3++;
                    trackGroupArr[i3] = trackGroups.get(i5);
                }
                i2++;
                i3 = i3;
            }
            this.f = new TrackGroupArray(trackGroupArr);
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.e)).onPrepared(this);
        }
    }

    /* renamed from: a */
    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.e)).onContinueLoadingRequested(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MergingMediaPeriod.java */
    /* renamed from: com.google.android.exoplayer2.source.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static final class C0066a implements MediaPeriod, MediaPeriod.Callback {
        private final MediaPeriod a;
        private final long b;
        private MediaPeriod.Callback c;

        public C0066a(MediaPeriod mediaPeriod, long j) {
            this.a = mediaPeriod;
            this.b = j;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long j) {
            this.c = callback;
            this.a.prepare(this, j - this.b);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() throws IOException {
            this.a.maybeThrowPrepareError();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return this.a.getTrackGroups();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
            return this.a.getStreamKeys(list);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
            SampleStream[] sampleStreamArr2 = new SampleStream[sampleStreamArr.length];
            int i = 0;
            while (true) {
                SampleStream sampleStream = null;
                if (i >= sampleStreamArr.length) {
                    break;
                }
                b bVar = (b) sampleStreamArr[i];
                if (bVar != null) {
                    sampleStream = bVar.a();
                }
                sampleStreamArr2[i] = sampleStream;
                i++;
            }
            long selectTracks = this.a.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr2, zArr2, j - this.b);
            for (int i2 = 0; i2 < sampleStreamArr.length; i2++) {
                SampleStream sampleStream2 = sampleStreamArr2[i2];
                if (sampleStream2 == null) {
                    sampleStreamArr[i2] = null;
                } else if (sampleStreamArr[i2] == null || ((b) sampleStreamArr[i2]).a() != sampleStream2) {
                    sampleStreamArr[i2] = new b(sampleStream2, this.b);
                }
            }
            return selectTracks + this.b;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long j, boolean z) {
            this.a.discardBuffer(j - this.b, z);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            long readDiscontinuity = this.a.readDiscontinuity();
            return readDiscontinuity == C.TIME_UNSET ? C.TIME_UNSET : this.b + readDiscontinuity;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long j) {
            return this.a.seekToUs(j - this.b) + this.b;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
            return this.a.getAdjustedSeekPositionUs(j - this.b, seekParameters) + this.b;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            long bufferedPositionUs = this.a.getBufferedPositionUs();
            if (bufferedPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.b + bufferedPositionUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            long nextLoadPositionUs = this.a.getNextLoadPositionUs();
            if (nextLoadPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.b + nextLoadPositionUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long j) {
            return this.a.continueLoading(j - this.b);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return this.a.isLoading();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long j) {
            this.a.reevaluateBuffer(j - this.b);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.c)).onPrepared(this);
        }

        /* renamed from: a */
        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.c)).onContinueLoadingRequested(this);
        }
    }

    /* compiled from: MergingMediaPeriod.java */
    /* loaded from: classes2.dex */
    private static final class b implements SampleStream {
        private final SampleStream a;
        private final long b;

        public b(SampleStream sampleStream, long j) {
            this.a = sampleStream;
            this.b = j;
        }

        public SampleStream a() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return this.a.isReady();
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            this.a.maybeThrowError();
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            int readData = this.a.readData(formatHolder, decoderInputBuffer, i);
            if (readData == -4) {
                decoderInputBuffer.timeUs = Math.max(0L, decoderInputBuffer.timeUs + this.b);
            }
            return readData;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j) {
            return this.a.skipData(j - this.b);
        }
    }
}
