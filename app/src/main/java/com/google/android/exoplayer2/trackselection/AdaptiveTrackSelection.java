package com.google.android.exoplayer2.trackselection;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public class AdaptiveTrackSelection extends BaseTrackSelection {
    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.7f;
    public static final float DEFAULT_BUFFERED_FRACTION_TO_LIVE_EDGE_FOR_QUALITY_INCREASE = 0.75f;
    public static final int DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS = 25000;
    public static final int DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS = 10000;
    public static final int DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS = 25000;
    private final BandwidthMeter a;
    private final long b;
    private final long c;
    private final long d;
    private final float e;
    private final float f;
    private final ImmutableList<AdaptationCheckpoint> g;
    private final Clock h;
    private float i;
    private int j;
    private int k;
    private long l;
    @Nullable
    private MediaChunk m;

    protected boolean canSelectFormat(Format format, int i, long j) {
        return ((long) i) <= j;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    @Nullable
    public Object getSelectionData() {
        return null;
    }

    /* loaded from: classes2.dex */
    public static class Factory implements ExoTrackSelection.Factory {
        private final int a;
        private final int b;
        private final int c;
        private final float d;
        private final float e;
        private final Clock f;

        public Factory() {
            this(10000, 25000, 25000, 0.7f, 0.75f, Clock.DEFAULT);
        }

        public Factory(int i, int i2, int i3, float f) {
            this(i, i2, i3, f, 0.75f, Clock.DEFAULT);
        }

        public Factory(int i, int i2, int i3, float f, float f2, Clock clock) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = f;
            this.e = f2;
            this.f = clock;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection.Factory
        public final ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            ExoTrackSelection exoTrackSelection;
            ImmutableList b = AdaptiveTrackSelection.b(definitionArr);
            ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
            for (int i = 0; i < definitionArr.length; i++) {
                ExoTrackSelection.Definition definition = definitionArr[i];
                if (!(definition == null || definition.tracks.length == 0)) {
                    if (definition.tracks.length == 1) {
                        exoTrackSelection = new FixedTrackSelection(definition.group, definition.tracks[0], definition.type);
                    } else {
                        exoTrackSelection = createAdaptiveTrackSelection(definition.group, definition.tracks, definition.type, bandwidthMeter, (ImmutableList) b.get(i));
                    }
                    exoTrackSelectionArr[i] = exoTrackSelection;
                }
            }
            return exoTrackSelectionArr;
        }

        protected AdaptiveTrackSelection createAdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, int i, BandwidthMeter bandwidthMeter, ImmutableList<AdaptationCheckpoint> immutableList) {
            return new AdaptiveTrackSelection(trackGroup, iArr, i, bandwidthMeter, this.a, this.b, this.c, this.d, this.e, immutableList, this.f);
        }
    }

    public AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, BandwidthMeter bandwidthMeter) {
        this(trackGroup, iArr, 0, bandwidthMeter, 10000L, 25000L, 25000L, 0.7f, 0.75f, ImmutableList.of(), Clock.DEFAULT);
    }

    protected AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, int i, BandwidthMeter bandwidthMeter, long j, long j2, long j3, float f, float f2, List<AdaptationCheckpoint> list, Clock clock) {
        super(trackGroup, iArr, i);
        if (j3 < j) {
            Log.w("AdaptiveTrackSelection", "Adjusting minDurationToRetainAfterDiscardMs to be at least minDurationForQualityIncreaseMs");
            j3 = j;
        }
        this.a = bandwidthMeter;
        this.b = j * 1000;
        this.c = j2 * 1000;
        this.d = j3 * 1000;
        this.e = f;
        this.f = f2;
        this.g = ImmutableList.copyOf((Collection) list);
        this.h = clock;
        this.i = 1.0f;
        this.k = 0;
        this.l = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    @CallSuper
    public void enable() {
        this.l = C.TIME_UNSET;
        this.m = null;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    @CallSuper
    public void disable() {
        this.m = null;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void onPlaybackSpeed(float f) {
        this.i = f;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
        long elapsedRealtime = this.h.elapsedRealtime();
        long a = a(mediaChunkIteratorArr, list);
        int i = this.k;
        if (i == 0) {
            this.k = 1;
            this.j = a(elapsedRealtime, a);
            return;
        }
        int i2 = this.j;
        int indexOf = list.isEmpty() ? -1 : indexOf(((MediaChunk) Iterables.getLast(list)).trackFormat);
        if (indexOf != -1) {
            i = ((MediaChunk) Iterables.getLast(list)).trackSelectionReason;
            i2 = indexOf;
        }
        int a2 = a(elapsedRealtime, a);
        if (!isBlacklisted(i2, elapsedRealtime)) {
            Format format = getFormat(i2);
            Format format2 = getFormat(a2);
            if ((format2.bitrate > format.bitrate && j2 < a(j3)) || (format2.bitrate < format.bitrate && j2 >= this.c)) {
                a2 = i2;
            }
        }
        if (a2 != i2) {
            i = 3;
        }
        this.k = i;
        this.j = a2;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int getSelectedIndex() {
        return this.j;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int getSelectionReason() {
        return this.k;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int evaluateQueueSize(long j, List<? extends MediaChunk> list) {
        long elapsedRealtime = this.h.elapsedRealtime();
        if (!shouldEvaluateQueueSize(elapsedRealtime, list)) {
            return list.size();
        }
        this.l = elapsedRealtime;
        this.m = list.isEmpty() ? null : (MediaChunk) Iterables.getLast(list);
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(((MediaChunk) list.get(size - 1)).startTimeUs - j, this.i);
        long minDurationToRetainAfterDiscardUs = getMinDurationToRetainAfterDiscardUs();
        if (playoutDurationForMediaDuration < minDurationToRetainAfterDiscardUs) {
            return size;
        }
        Format format = getFormat(a(elapsedRealtime, a(list)));
        for (int i = 0; i < size; i++) {
            MediaChunk mediaChunk = (MediaChunk) list.get(i);
            Format format2 = mediaChunk.trackFormat;
            if (Util.getPlayoutDurationForMediaDuration(mediaChunk.startTimeUs - j, this.i) >= minDurationToRetainAfterDiscardUs && format2.bitrate < format.bitrate && format2.height != -1 && format2.height < 720 && format2.width != -1 && format2.width < 1280 && format2.height < format.height) {
                return i;
            }
        }
        return size;
    }

    protected boolean shouldEvaluateQueueSize(long j, List<? extends MediaChunk> list) {
        long j2 = this.l;
        return j2 == C.TIME_UNSET || j - j2 >= 1000 || (!list.isEmpty() && !((MediaChunk) Iterables.getLast(list)).equals(this.m));
    }

    protected long getMinDurationToRetainAfterDiscardUs() {
        return this.d;
    }

    private int a(long j, long j2) {
        long b = b(j2);
        int i = 0;
        for (int i2 = 0; i2 < this.length; i2++) {
            if (j == Long.MIN_VALUE || !isBlacklisted(i2, j)) {
                Format format = getFormat(i2);
                if (canSelectFormat(format, format.bitrate, b)) {
                    return i2;
                }
                i = i2;
            }
        }
        return i;
    }

    private long a(long j) {
        if (j != C.TIME_UNSET && j <= this.b) {
            return ((float) j) * this.f;
        }
        return this.b;
    }

    private long a(MediaChunkIterator[] mediaChunkIteratorArr, List<? extends MediaChunk> list) {
        int i = this.j;
        if (i >= mediaChunkIteratorArr.length || !mediaChunkIteratorArr[i].next()) {
            for (MediaChunkIterator mediaChunkIterator : mediaChunkIteratorArr) {
                if (mediaChunkIterator.next()) {
                    return mediaChunkIterator.getChunkEndTimeUs() - mediaChunkIterator.getChunkStartTimeUs();
                }
            }
            return a(list);
        }
        MediaChunkIterator mediaChunkIterator2 = mediaChunkIteratorArr[this.j];
        return mediaChunkIterator2.getChunkEndTimeUs() - mediaChunkIterator2.getChunkStartTimeUs();
    }

    private long a(List<? extends MediaChunk> list) {
        if (list.isEmpty()) {
            return C.TIME_UNSET;
        }
        MediaChunk mediaChunk = (MediaChunk) Iterables.getLast(list);
        return (mediaChunk.startTimeUs == C.TIME_UNSET || mediaChunk.endTimeUs == C.TIME_UNSET) ? C.TIME_UNSET : mediaChunk.endTimeUs - mediaChunk.startTimeUs;
    }

    private long b(long j) {
        long c = c(j);
        if (this.g.isEmpty()) {
            return c;
        }
        int i = 1;
        while (i < this.g.size() - 1 && this.g.get(i).totalBandwidth < c) {
            i++;
        }
        AdaptationCheckpoint adaptationCheckpoint = this.g.get(i - 1);
        AdaptationCheckpoint adaptationCheckpoint2 = this.g.get(i);
        return adaptationCheckpoint.allocatedBandwidth + ((((float) (c - adaptationCheckpoint.totalBandwidth)) / ((float) (adaptationCheckpoint2.totalBandwidth - adaptationCheckpoint.totalBandwidth))) * ((float) (adaptationCheckpoint2.allocatedBandwidth - adaptationCheckpoint.allocatedBandwidth)));
    }

    private long c(long j) {
        long bitrateEstimate = ((float) this.a.getBitrateEstimate()) * this.e;
        long timeToFirstByteEstimateUs = this.a.getTimeToFirstByteEstimateUs();
        if (timeToFirstByteEstimateUs == C.TIME_UNSET || j == C.TIME_UNSET) {
            return ((float) bitrateEstimate) / this.i;
        }
        float f = (float) j;
        return (((float) bitrateEstimate) * Math.max((f / this.i) - ((float) timeToFirstByteEstimateUs), 0.0f)) / f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImmutableList<ImmutableList<AdaptationCheckpoint>> b(ExoTrackSelection.Definition[] definitionArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < definitionArr.length; i++) {
            if (definitionArr[i] == null || definitionArr[i].tracks.length <= 1) {
                arrayList.add(null);
            } else {
                ImmutableList.Builder builder = ImmutableList.builder();
                builder.add((ImmutableList.Builder) new AdaptationCheckpoint(0L, 0L));
                arrayList.add(builder);
            }
        }
        long[][] c = c(definitionArr);
        int[] iArr = new int[c.length];
        long[] jArr = new long[c.length];
        for (int i2 = 0; i2 < c.length; i2++) {
            jArr[i2] = c[i2].length == 0 ? 0L : c[i2][0];
        }
        a(arrayList, jArr);
        ImmutableList<Integer> a = a(c);
        for (int i3 = 0; i3 < a.size(); i3++) {
            int intValue = a.get(i3).intValue();
            int i4 = iArr[intValue] + 1;
            iArr[intValue] = i4;
            jArr[intValue] = c[intValue][i4];
            a(arrayList, jArr);
        }
        for (int i5 = 0; i5 < definitionArr.length; i5++) {
            if (arrayList.get(i5) != null) {
                jArr[i5] = jArr[i5] * 2;
            }
        }
        a(arrayList, jArr);
        ImmutableList.Builder builder2 = ImmutableList.builder();
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            ImmutableList.Builder builder3 = (ImmutableList.Builder) arrayList.get(i6);
            builder2.add((ImmutableList.Builder) (builder3 == null ? ImmutableList.of() : builder3.build()));
        }
        return builder2.build();
    }

    private static long[][] c(ExoTrackSelection.Definition[] definitionArr) {
        long[][] jArr = new long[definitionArr.length];
        for (int i = 0; i < definitionArr.length; i++) {
            ExoTrackSelection.Definition definition = definitionArr[i];
            if (definition == null) {
                jArr[i] = new long[0];
            } else {
                jArr[i] = new long[definition.tracks.length];
                for (int i2 = 0; i2 < definition.tracks.length; i2++) {
                    jArr[i][i2] = definition.group.getFormat(definition.tracks[i2]).bitrate;
                }
                Arrays.sort(jArr[i]);
            }
        }
        return jArr;
    }

    private static ImmutableList<Integer> a(long[][] jArr) {
        Multimap build = MultimapBuilder.treeKeys().arrayListValues().build();
        for (int i = 0; i < jArr.length; i++) {
            if (jArr[i].length > 1) {
                double[] dArr = new double[jArr[i].length];
                int i2 = 0;
                while (true) {
                    double d = 0.0d;
                    if (i2 >= jArr[i].length) {
                        break;
                    }
                    if (jArr[i][i2] != -1) {
                        d = Math.log(jArr[i][i2]);
                    }
                    dArr[i2] = d;
                    i2++;
                }
                double d2 = dArr[dArr.length - 1] - dArr[0];
                int i3 = 0;
                while (i3 < dArr.length - 1) {
                    double d3 = dArr[i3];
                    i3++;
                    build.put(Double.valueOf(d2 == 0.0d ? 1.0d : (((d3 + dArr[i3]) * 0.5d) - dArr[0]) / d2), Integer.valueOf(i));
                }
            }
        }
        return ImmutableList.copyOf(build.values());
    }

    private static void a(List<ImmutableList.Builder<AdaptationCheckpoint>> list, long[] jArr) {
        long j = 0;
        for (long j2 : jArr) {
            j += j2;
        }
        for (int i = 0; i < list.size(); i++) {
            ImmutableList.Builder<AdaptationCheckpoint> builder = list.get(i);
            if (builder != null) {
                builder.add((ImmutableList.Builder<AdaptationCheckpoint>) new AdaptationCheckpoint(j, jArr[i]));
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class AdaptationCheckpoint {
        public final long allocatedBandwidth;
        public final long totalBandwidth;

        public AdaptationCheckpoint(long j, long j2) {
            this.totalBandwidth = j;
            this.allocatedBandwidth = j2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdaptationCheckpoint)) {
                return false;
            }
            AdaptationCheckpoint adaptationCheckpoint = (AdaptationCheckpoint) obj;
            return this.totalBandwidth == adaptationCheckpoint.totalBandwidth && this.allocatedBandwidth == adaptationCheckpoint.allocatedBandwidth;
        }

        public int hashCode() {
            return (((int) this.totalBandwidth) * 31) + ((int) this.allocatedBandwidth);
        }
    }
}
