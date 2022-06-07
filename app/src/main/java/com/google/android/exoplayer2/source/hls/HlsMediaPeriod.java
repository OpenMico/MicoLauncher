package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class HlsMediaPeriod implements MediaPeriod, HlsSampleStreamWrapper.Callback, HlsPlaylistTracker.PlaylistEventListener {
    private final HlsExtractorFactory a;
    private final HlsPlaylistTracker b;
    private final HlsDataSourceFactory c;
    @Nullable
    private final TransferListener d;
    private final DrmSessionManager e;
    private final DrmSessionEventListener.EventDispatcher f;
    private final LoadErrorHandlingPolicy g;
    private final MediaSourceEventListener.EventDispatcher h;
    private final Allocator i;
    private final CompositeSequenceableLoaderFactory l;
    private final boolean m;
    private final int n;
    private final boolean o;
    @Nullable
    private MediaPeriod.Callback p;
    private int q;
    private TrackGroupArray r;
    private int v;
    private SequenceableLoader w;
    private final IdentityHashMap<SampleStream, Integer> j = new IdentityHashMap<>();
    private final TimestampAdjusterProvider k = new TimestampAdjusterProvider();
    private HlsSampleStreamWrapper[] s = new HlsSampleStreamWrapper[0];
    private HlsSampleStreamWrapper[] t = new HlsSampleStreamWrapper[0];
    private int[][] u = new int[0];

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    public HlsMediaPeriod(HlsExtractorFactory hlsExtractorFactory, HlsPlaylistTracker hlsPlaylistTracker, HlsDataSourceFactory hlsDataSourceFactory, @Nullable TransferListener transferListener, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, boolean z, int i, boolean z2) {
        this.a = hlsExtractorFactory;
        this.b = hlsPlaylistTracker;
        this.c = hlsDataSourceFactory;
        this.d = transferListener;
        this.e = drmSessionManager;
        this.f = eventDispatcher;
        this.g = loadErrorHandlingPolicy;
        this.h = eventDispatcher2;
        this.i = allocator;
        this.l = compositeSequenceableLoaderFactory;
        this.m = z;
        this.n = i;
        this.o = z2;
        this.w = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
    }

    public void release() {
        this.b.removeListener(this);
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.s) {
            hlsSampleStreamWrapper.f();
        }
        this.p = null;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.p = callback;
        this.b.addListener(this);
        a(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.s) {
            hlsSampleStreamWrapper.b();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.r);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        TrackGroupArray trackGroupArray;
        int[] iArr;
        int i;
        HlsMediaPeriod hlsMediaPeriod = this;
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(hlsMediaPeriod.b.getMasterPlaylist());
        boolean z = !hlsMasterPlaylist.variants.isEmpty();
        int i2 = 0;
        int i3 = z ? 1 : 0;
        int length = hlsMediaPeriod.s.length - hlsMasterPlaylist.subtitles.size();
        if (z) {
            HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsMediaPeriod.s[0];
            iArr = hlsMediaPeriod.u[0];
            trackGroupArray = hlsSampleStreamWrapper.c();
            i = hlsSampleStreamWrapper.d();
        } else {
            iArr = new int[0];
            trackGroupArray = TrackGroupArray.EMPTY;
            i = 0;
        }
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        boolean z3 = false;
        for (ExoTrackSelection exoTrackSelection : list) {
            TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
            int indexOf = trackGroupArray.indexOf(trackGroup);
            if (indexOf == -1) {
                while (true) {
                    HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = hlsMediaPeriod.s;
                    if (i3 >= hlsSampleStreamWrapperArr.length) {
                        break;
                    } else if (hlsSampleStreamWrapperArr[i3].c().indexOf(trackGroup) != -1) {
                        int i4 = i3 < length ? 1 : 2;
                        int[] iArr2 = hlsMediaPeriod.u[i3];
                        for (int i5 = 0; i5 < exoTrackSelection.length(); i5++) {
                            arrayList.add(new StreamKey(i4, iArr2[exoTrackSelection.getIndexInTrackGroup(i5)]));
                        }
                    } else {
                        i3++;
                        hlsMediaPeriod = this;
                    }
                }
            } else if (indexOf == i) {
                for (int i6 = i2; i6 < exoTrackSelection.length(); i6++) {
                    arrayList.add(new StreamKey(i2, iArr[exoTrackSelection.getIndexInTrackGroup(i6)]));
                }
                z3 = true;
            } else {
                z2 = true;
            }
            hlsMediaPeriod = this;
            i2 = 0;
        }
        if (z2 && !z3) {
            int i7 = iArr[0];
            int i8 = hlsMasterPlaylist.variants.get(iArr[0]).format.bitrate;
            for (int i9 = 1; i9 < iArr.length; i9++) {
                int i10 = hlsMasterPlaylist.variants.get(iArr[i9]).format.bitrate;
                if (i10 < i8) {
                    i7 = iArr[i9];
                    i8 = i10;
                }
            }
            arrayList.add(new StreamKey(0, i7));
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x00df, code lost:
        if (r11 == r8[0]) goto L_0x00f3;
     */
    @Override // com.google.android.exoplayer2.source.MediaPeriod
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r21, boolean[] r22, com.google.android.exoplayer2.source.SampleStream[] r23, boolean[] r24, long r25) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.t) {
            hlsSampleStreamWrapper.a(j, z);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        this.w.reevaluateBuffer(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        if (this.r != null) {
            return this.w.continueLoading(j);
        }
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.s) {
            hlsSampleStreamWrapper.a();
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.w.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.w.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.w.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.t;
        if (hlsSampleStreamWrapperArr.length > 0) {
            boolean b = hlsSampleStreamWrapperArr[0].b(j, false);
            int i = 1;
            while (true) {
                HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr2 = this.t;
                if (i >= hlsSampleStreamWrapperArr2.length) {
                    break;
                }
                hlsSampleStreamWrapperArr2[i].b(j, b);
                i++;
            }
            if (b) {
                this.k.reset();
            }
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPrepared() {
        int i = this.q - 1;
        this.q = i;
        if (i <= 0) {
            int i2 = 0;
            for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.s) {
                i2 += hlsSampleStreamWrapper.c().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i2];
            HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.s;
            int length = hlsSampleStreamWrapperArr.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                HlsSampleStreamWrapper hlsSampleStreamWrapper2 = hlsSampleStreamWrapperArr[i3];
                int i5 = hlsSampleStreamWrapper2.c().length;
                for (int i6 = 0; i6 < i5; i6++) {
                    i4++;
                    trackGroupArr[i4] = hlsSampleStreamWrapper2.c().get(i6);
                }
                i3++;
                i4 = i4;
            }
            this.r = new TrackGroupArray(trackGroupArr);
            this.p.onPrepared(this);
        }
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPlaylistRefreshRequired(Uri uri) {
        this.b.refreshPlaylist(uri);
    }

    public void onContinueLoadingRequested(HlsSampleStreamWrapper hlsSampleStreamWrapper) {
        this.p.onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public void onPlaylistChanged() {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.s) {
            hlsSampleStreamWrapper.e();
        }
        this.p.onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public boolean onPlaylistError(Uri uri, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean z) {
        boolean z2 = true;
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.s) {
            z2 &= hlsSampleStreamWrapper.a(uri, loadErrorInfo, z);
        }
        this.p.onContinueLoadingRequested(this);
        return z2;
    }

    private void a(long j) {
        Map<String, DrmInitData> map;
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(this.b.getMasterPlaylist());
        if (this.o) {
            map = a(hlsMasterPlaylist.sessionKeyDrmInitData);
        } else {
            map = Collections.emptyMap();
        }
        boolean z = !hlsMasterPlaylist.variants.isEmpty();
        List<HlsMasterPlaylist.Rendition> list = hlsMasterPlaylist.audios;
        List<HlsMasterPlaylist.Rendition> list2 = hlsMasterPlaylist.subtitles;
        this.q = 0;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            a(hlsMasterPlaylist, j, arrayList, arrayList2, map);
        }
        a(j, list, arrayList, arrayList2, map);
        this.v = arrayList.size();
        for (int i = 0; i < list2.size(); i++) {
            HlsMasterPlaylist.Rendition rendition = list2.get(i);
            HlsSampleStreamWrapper a = a(3, new Uri[]{rendition.url}, new Format[]{rendition.format}, null, Collections.emptyList(), map, j);
            arrayList2.add(new int[]{i});
            arrayList.add(a);
            a.a(new TrackGroup[]{new TrackGroup(rendition.format)}, 0, new int[0]);
        }
        this.s = (HlsSampleStreamWrapper[]) arrayList.toArray(new HlsSampleStreamWrapper[0]);
        this.u = (int[][]) arrayList2.toArray(new int[0]);
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.s;
        this.q = hlsSampleStreamWrapperArr.length;
        hlsSampleStreamWrapperArr[0].a(true);
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.s) {
            hlsSampleStreamWrapper.a();
        }
        this.t = this.s;
    }

    private void a(HlsMasterPlaylist hlsMasterPlaylist, long j, List<HlsSampleStreamWrapper> list, List<int[]> list2, Map<String, DrmInitData> map) {
        boolean z;
        boolean z2;
        int[] iArr = new int[hlsMasterPlaylist.variants.size()];
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < hlsMasterPlaylist.variants.size(); i3++) {
            Format format = hlsMasterPlaylist.variants.get(i3).format;
            if (format.height > 0 || Util.getCodecsOfType(format.codecs, 2) != null) {
                iArr[i3] = 2;
                i++;
            } else if (Util.getCodecsOfType(format.codecs, 1) != null) {
                iArr[i3] = 1;
                i2++;
            } else {
                iArr[i3] = -1;
            }
        }
        int length = iArr.length;
        if (i > 0) {
            z = false;
            z2 = true;
        } else if (i2 < iArr.length) {
            i = iArr.length - i2;
            z2 = false;
            z = true;
        } else {
            z = false;
            i = length;
            z2 = false;
        }
        Uri[] uriArr = new Uri[i];
        Format[] formatArr = new Format[i];
        int[] iArr2 = new int[i];
        int i4 = 0;
        for (int i5 = 0; i5 < hlsMasterPlaylist.variants.size(); i5++) {
            if ((!z2 || iArr[i5] == 2) && (!z || iArr[i5] != 1)) {
                HlsMasterPlaylist.Variant variant = hlsMasterPlaylist.variants.get(i5);
                uriArr[i4] = variant.url;
                formatArr[i4] = variant.format;
                i4++;
                iArr2[i4] = i5;
            }
        }
        String str = formatArr[0].codecs;
        int codecCountOfType = Util.getCodecCountOfType(str, 2);
        int codecCountOfType2 = Util.getCodecCountOfType(str, 1);
        boolean z3 = codecCountOfType2 <= 1 && codecCountOfType <= 1 && codecCountOfType2 + codecCountOfType > 0;
        HlsSampleStreamWrapper a = a((z2 || codecCountOfType2 <= 0) ? 0 : 1, uriArr, formatArr, hlsMasterPlaylist.muxedAudioFormat, hlsMasterPlaylist.muxedCaptionFormats, map, j);
        list.add(a);
        list2.add(iArr2);
        if (this.m && z3) {
            ArrayList arrayList = new ArrayList();
            if (codecCountOfType > 0) {
                Format[] formatArr2 = new Format[i];
                for (int i6 = 0; i6 < formatArr2.length; i6++) {
                    formatArr2[i6] = a(formatArr[i6]);
                }
                arrayList.add(new TrackGroup(formatArr2));
                if (codecCountOfType2 > 0 && (hlsMasterPlaylist.muxedAudioFormat != null || hlsMasterPlaylist.audios.isEmpty())) {
                    arrayList.add(new TrackGroup(a(formatArr[0], hlsMasterPlaylist.muxedAudioFormat, false)));
                }
                List<Format> list3 = hlsMasterPlaylist.muxedCaptionFormats;
                if (list3 != null) {
                    for (int i7 = 0; i7 < list3.size(); i7++) {
                        arrayList.add(new TrackGroup(list3.get(i7)));
                    }
                }
            } else {
                Format[] formatArr3 = new Format[i];
                for (int i8 = 0; i8 < formatArr3.length; i8++) {
                    formatArr3[i8] = a(formatArr[i8], hlsMasterPlaylist.muxedAudioFormat, true);
                }
                arrayList.add(new TrackGroup(formatArr3));
            }
            TrackGroup trackGroup = new TrackGroup(new Format.Builder().setId("ID3").setSampleMimeType(MimeTypes.APPLICATION_ID3).build());
            arrayList.add(trackGroup);
            a.a((TrackGroup[]) arrayList.toArray(new TrackGroup[0]), 0, arrayList.indexOf(trackGroup));
        }
    }

    private void a(long j, List<HlsMasterPlaylist.Rendition> list, List<HlsSampleStreamWrapper> list2, List<int[]> list3, Map<String, DrmInitData> map) {
        ArrayList arrayList = new ArrayList(list.size());
        ArrayList arrayList2 = new ArrayList(list.size());
        ArrayList arrayList3 = new ArrayList(list.size());
        HashSet hashSet = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).name;
            if (hashSet.add(str)) {
                arrayList.clear();
                arrayList2.clear();
                arrayList3.clear();
                boolean z = true;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (Util.areEqual(str, list.get(i2).name)) {
                        HlsMasterPlaylist.Rendition rendition = list.get(i2);
                        arrayList3.add(Integer.valueOf(i2));
                        arrayList.add(rendition.url);
                        arrayList2.add(rendition.format);
                        z &= Util.getCodecCountOfType(rendition.format.codecs, 1) == 1;
                    }
                }
                HlsSampleStreamWrapper a = a(1, (Uri[]) arrayList.toArray((Uri[]) Util.castNonNullTypeArray(new Uri[0])), (Format[]) arrayList2.toArray(new Format[0]), null, Collections.emptyList(), map, j);
                list3.add(Ints.toArray(arrayList3));
                list2.add(a);
                if (this.m && z) {
                    a.a(new TrackGroup[]{new TrackGroup((Format[]) arrayList2.toArray(new Format[0]))}, 0, new int[0]);
                }
            }
        }
    }

    private HlsSampleStreamWrapper a(int i, Uri[] uriArr, Format[] formatArr, @Nullable Format format, @Nullable List<Format> list, Map<String, DrmInitData> map, long j) {
        return new HlsSampleStreamWrapper(i, this, new HlsChunkSource(this.a, this.b, uriArr, formatArr, this.c, this.d, this.k, list), map, this.i, j, format, this.e, this.f, this.g, this.h, this.n);
    }

    private static Map<String, DrmInitData> a(List<DrmInitData> list) {
        ArrayList arrayList = new ArrayList(list);
        HashMap hashMap = new HashMap();
        int i = 0;
        while (i < arrayList.size()) {
            DrmInitData drmInitData = list.get(i);
            String str = drmInitData.schemeType;
            i++;
            DrmInitData drmInitData2 = drmInitData;
            int i2 = i;
            while (i2 < arrayList.size()) {
                DrmInitData drmInitData3 = (DrmInitData) arrayList.get(i2);
                if (TextUtils.equals(drmInitData3.schemeType, str)) {
                    drmInitData2 = drmInitData2.merge(drmInitData3);
                    arrayList.remove(i2);
                } else {
                    i2++;
                }
            }
            hashMap.put(str, drmInitData2);
        }
        return hashMap;
    }

    private static Format a(Format format) {
        String codecsOfType = Util.getCodecsOfType(format.codecs, 2);
        return new Format.Builder().setId(format.id).setLabel(format.label).setContainerMimeType(format.containerMimeType).setSampleMimeType(MimeTypes.getMediaMimeType(codecsOfType)).setCodecs(codecsOfType).setMetadata(format.metadata).setAverageBitrate(format.averageBitrate).setPeakBitrate(format.peakBitrate).setWidth(format.width).setHeight(format.height).setFrameRate(format.frameRate).setSelectionFlags(format.selectionFlags).setRoleFlags(format.roleFlags).build();
    }

    private static Format a(Format format, @Nullable Format format2, boolean z) {
        String str;
        String str2;
        int i;
        Metadata metadata;
        int i2;
        String str3 = null;
        int i3 = 0;
        int i4 = -1;
        if (format2 != null) {
            String str4 = format2.codecs;
            Metadata metadata2 = format2.metadata;
            int i5 = format2.channelCount;
            i3 = format2.selectionFlags;
            i = format2.roleFlags;
            str2 = format2.language;
            str3 = format2.label;
            str = str4;
            metadata = metadata2;
            i2 = i5;
        } else {
            str = Util.getCodecsOfType(format.codecs, 1);
            metadata = format.metadata;
            if (z) {
                int i6 = format.channelCount;
                int i7 = format.selectionFlags;
                int i8 = format.roleFlags;
                String str5 = format.language;
                i2 = i6;
                str3 = format.label;
                str2 = str5;
                i = i8;
                i3 = i7;
            } else {
                str2 = null;
                i = 0;
                i2 = -1;
            }
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(str);
        int i9 = z ? format.averageBitrate : -1;
        if (z) {
            i4 = format.peakBitrate;
        }
        return new Format.Builder().setId(format.id).setLabel(str3).setContainerMimeType(format.containerMimeType).setSampleMimeType(mediaMimeType).setCodecs(str).setMetadata(metadata).setAverageBitrate(i9).setPeakBitrate(i4).setChannelCount(i2).setSelectionFlags(i3).setRoleFlags(i).setLanguage(str2).build();
    }
}
