package com.google.android.exoplayer2.trackselection;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes2.dex */
public abstract class MappingTrackSelector extends TrackSelector {
    @Nullable
    private MappedTrackInfo a;

    protected abstract Pair<RendererConfiguration[], ExoTrackSelection[]> selectTracks(MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException;

    /* loaded from: classes2.dex */
    public static final class MappedTrackInfo {
        public static final int RENDERER_SUPPORT_EXCEEDS_CAPABILITIES_TRACKS = 2;
        public static final int RENDERER_SUPPORT_NO_TRACKS = 0;
        public static final int RENDERER_SUPPORT_PLAYABLE_TRACKS = 3;
        public static final int RENDERER_SUPPORT_UNSUPPORTED_TRACKS = 1;
        private final int a;
        private final String[] b;
        private final int[] c;
        private final TrackGroupArray[] d;
        private final int[] e;
        private final int[][][] f;
        private final TrackGroupArray g;

        MappedTrackInfo(String[] strArr, int[] iArr, TrackGroupArray[] trackGroupArrayArr, int[] iArr2, int[][][] iArr3, TrackGroupArray trackGroupArray) {
            this.b = strArr;
            this.c = iArr;
            this.d = trackGroupArrayArr;
            this.f = iArr3;
            this.e = iArr2;
            this.g = trackGroupArray;
            this.a = iArr.length;
        }

        public int getRendererCount() {
            return this.a;
        }

        public String getRendererName(int i) {
            return this.b[i];
        }

        public int getRendererType(int i) {
            return this.c[i];
        }

        public TrackGroupArray getTrackGroups(int i) {
            return this.d[i];
        }

        public int getRendererSupport(int i) {
            int i2;
            int[][] iArr = this.f[i];
            int length = iArr.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                int i5 = i4;
                for (int i6 : iArr[i3]) {
                    switch (RendererCapabilities.getFormatSupport(i6)) {
                        case 0:
                        case 1:
                        case 2:
                            i2 = 1;
                            break;
                        case 3:
                            i2 = 2;
                            break;
                        case 4:
                            return 3;
                        default:
                            throw new IllegalStateException();
                    }
                    i5 = Math.max(i5, i2);
                }
                i3++;
                i4 = i5;
            }
            return i4;
        }

        public int getTypeSupport(int i) {
            int i2 = 0;
            for (int i3 = 0; i3 < this.a; i3++) {
                if (this.c[i3] == i) {
                    i2 = Math.max(i2, getRendererSupport(i3));
                }
            }
            return i2;
        }

        public int getTrackSupport(int i, int i2, int i3) {
            return RendererCapabilities.getFormatSupport(this.f[i][i2][i3]);
        }

        public int getAdaptiveSupport(int i, int i2, boolean z) {
            int i3 = this.d[i].get(i2).length;
            int[] iArr = new int[i3];
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                int trackSupport = getTrackSupport(i, i2, i5);
                if (trackSupport == 4 || (z && trackSupport == 3)) {
                    i4++;
                    iArr[i4] = i5;
                }
            }
            return getAdaptiveSupport(i, i2, Arrays.copyOf(iArr, i4));
        }

        public int getAdaptiveSupport(int i, int i2, int[] iArr) {
            int i3 = 16;
            String str = null;
            boolean z = false;
            int i4 = 0;
            for (int i5 = 0; i5 < iArr.length; i5++) {
                String str2 = this.d[i].get(i2).getFormat(iArr[i5]).sampleMimeType;
                i4++;
                if (i4 == 0) {
                    str = str2;
                } else {
                    z |= !Util.areEqual(str, str2);
                }
                i3 = Math.min(i3, RendererCapabilities.getAdaptiveSupport(this.f[i][i2][i5]));
            }
            return z ? Math.min(i3, this.e[i]) : i3;
        }

        public TrackGroupArray getUnmappedTrackGroups() {
            return this.g;
        }
    }

    @Nullable
    public final MappedTrackInfo getCurrentMappedTrackInfo() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public final void onSelectionActivated(@Nullable Object obj) {
        this.a = (MappedTrackInfo) obj;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public final TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException {
        int[] iArr;
        int[] iArr2 = new int[rendererCapabilitiesArr.length + 1];
        TrackGroup[][] trackGroupArr = new TrackGroup[rendererCapabilitiesArr.length + 1];
        int[][][] iArr3 = new int[rendererCapabilitiesArr.length + 1][];
        for (int i = 0; i < trackGroupArr.length; i++) {
            trackGroupArr[i] = new TrackGroup[trackGroupArray.length];
            iArr3[i] = new int[trackGroupArray.length];
        }
        int[] a = a(rendererCapabilitiesArr);
        for (int i2 = 0; i2 < trackGroupArray.length; i2++) {
            TrackGroup trackGroup = trackGroupArray.get(i2);
            int a2 = a(rendererCapabilitiesArr, trackGroup, iArr2, MimeTypes.getTrackType(trackGroup.getFormat(0).sampleMimeType) == 5);
            if (a2 == rendererCapabilitiesArr.length) {
                iArr = new int[trackGroup.length];
            } else {
                iArr = a(rendererCapabilitiesArr[a2], trackGroup);
            }
            int i3 = iArr2[a2];
            trackGroupArr[a2][i3] = trackGroup;
            iArr3[a2][i3] = iArr;
            iArr2[a2] = iArr2[a2] + 1;
        }
        TrackGroupArray[] trackGroupArrayArr = new TrackGroupArray[rendererCapabilitiesArr.length];
        String[] strArr = new String[rendererCapabilitiesArr.length];
        int[] iArr4 = new int[rendererCapabilitiesArr.length];
        for (int i4 = 0; i4 < rendererCapabilitiesArr.length; i4++) {
            int i5 = iArr2[i4];
            trackGroupArrayArr[i4] = new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[i4], i5));
            iArr3[i4] = (int[][]) Util.nullSafeArrayCopy(iArr3[i4], i5);
            strArr[i4] = rendererCapabilitiesArr[i4].getName();
            iArr4[i4] = rendererCapabilitiesArr[i4].getTrackType();
        }
        MappedTrackInfo mappedTrackInfo = new MappedTrackInfo(strArr, iArr4, trackGroupArrayArr, a, iArr3, new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[rendererCapabilitiesArr.length], iArr2[rendererCapabilitiesArr.length])));
        Pair<RendererConfiguration[], ExoTrackSelection[]> selectTracks = selectTracks(mappedTrackInfo, iArr3, a, mediaPeriodId, timeline);
        return new TrackSelectorResult((RendererConfiguration[]) selectTracks.first, (ExoTrackSelection[]) selectTracks.second, mappedTrackInfo);
    }

    private static int a(RendererCapabilities[] rendererCapabilitiesArr, TrackGroup trackGroup, int[] iArr, boolean z) throws ExoPlaybackException {
        int length = rendererCapabilitiesArr.length;
        boolean z2 = true;
        int i = 0;
        for (int i2 = 0; i2 < rendererCapabilitiesArr.length; i2++) {
            RendererCapabilities rendererCapabilities = rendererCapabilitiesArr[i2];
            int i3 = 0;
            for (int i4 = 0; i4 < trackGroup.length; i4++) {
                i3 = Math.max(i3, RendererCapabilities.getFormatSupport(rendererCapabilities.supportsFormat(trackGroup.getFormat(i4))));
            }
            boolean z3 = iArr[i2] == 0;
            if (i3 > i || (i3 == i && z && !z2 && z3)) {
                length = i2;
                z2 = z3;
                i = i3;
            }
        }
        return length;
    }

    private static int[] a(RendererCapabilities rendererCapabilities, TrackGroup trackGroup) throws ExoPlaybackException {
        int[] iArr = new int[trackGroup.length];
        for (int i = 0; i < trackGroup.length; i++) {
            iArr[i] = rendererCapabilities.supportsFormat(trackGroup.getFormat(i));
        }
        return iArr;
    }

    private static int[] a(RendererCapabilities[] rendererCapabilitiesArr) throws ExoPlaybackException {
        int[] iArr = new int[rendererCapabilitiesArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = rendererCapabilitiesArr[i].supportsMixedMimeTypeAdaptation();
        }
        return iArr;
    }
}
