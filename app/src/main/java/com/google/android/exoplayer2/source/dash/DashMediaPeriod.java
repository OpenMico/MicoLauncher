package com.google.android.exoplayer2.source.dash;

import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.Descriptor;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
final class DashMediaPeriod implements MediaPeriod, SequenceableLoader.Callback<ChunkSampleStream<DashChunkSource>>, ChunkSampleStream.ReleaseCallback<DashChunkSource> {
    private static final Pattern b = Pattern.compile("CC([1-4])=(.+)");
    private static final Pattern c = Pattern.compile("([1-4])=lang:(\\w+)(,.+)?");
    final int a;
    private final DashChunkSource.Factory d;
    @Nullable
    private final TransferListener e;
    private final DrmSessionManager f;
    private final LoadErrorHandlingPolicy g;
    private final BaseUrlExclusionList h;
    private final long i;
    private final LoaderErrorThrower j;
    private final Allocator k;
    private final TrackGroupArray l;
    private final TrackGroupInfo[] m;
    private final CompositeSequenceableLoaderFactory n;
    private final PlayerEmsgHandler o;
    private final MediaSourceEventListener.EventDispatcher q;
    private final DrmSessionEventListener.EventDispatcher r;
    @Nullable
    private MediaPeriod.Callback s;
    private SequenceableLoader v;
    private DashManifest w;
    private int x;
    private List<EventStream> y;
    private ChunkSampleStream<DashChunkSource>[] t = a(0);
    private a[] u = new a[0];
    private final IdentityHashMap<ChunkSampleStream<DashChunkSource>, PlayerEmsgHandler.PlayerTrackEmsgHandler> p = new IdentityHashMap<>();

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    public DashMediaPeriod(int i, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i2, DashChunkSource.Factory factory, @Nullable TransferListener transferListener, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, long j, LoaderErrorThrower loaderErrorThrower, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback) {
        this.a = i;
        this.w = dashManifest;
        this.h = baseUrlExclusionList;
        this.x = i2;
        this.d = factory;
        this.e = transferListener;
        this.f = drmSessionManager;
        this.r = eventDispatcher;
        this.g = loadErrorHandlingPolicy;
        this.q = eventDispatcher2;
        this.i = j;
        this.j = loaderErrorThrower;
        this.k = allocator;
        this.n = compositeSequenceableLoaderFactory;
        this.o = new PlayerEmsgHandler(dashManifest, playerEmsgCallback, allocator);
        this.v = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.t);
        Period period = dashManifest.getPeriod(i2);
        this.y = period.eventStreams;
        Pair<TrackGroupArray, TrackGroupInfo[]> a = a(drmSessionManager, period.adaptationSets, this.y);
        this.l = (TrackGroupArray) a.first;
        this.m = (TrackGroupInfo[]) a.second;
    }

    public void a(DashManifest dashManifest, int i) {
        this.w = dashManifest;
        this.x = i;
        this.o.updateManifest(dashManifest);
        ChunkSampleStream<DashChunkSource>[] chunkSampleStreamArr = this.t;
        if (chunkSampleStreamArr != null) {
            for (ChunkSampleStream<DashChunkSource> chunkSampleStream : chunkSampleStreamArr) {
                chunkSampleStream.getChunkSource().updateManifest(dashManifest, i);
            }
            this.s.onContinueLoadingRequested(this);
        }
        this.y = dashManifest.getPeriod(i).eventStreams;
        a[] aVarArr = this.u;
        for (a aVar : aVarArr) {
            Iterator<EventStream> it = this.y.iterator();
            while (true) {
                if (it.hasNext()) {
                    EventStream next = it.next();
                    if (next.id().equals(aVar.a())) {
                        boolean z = true;
                        int periodCount = dashManifest.getPeriodCount() - 1;
                        if (!dashManifest.dynamic || i != periodCount) {
                            z = false;
                        }
                        aVar.a(next, z);
                    }
                }
            }
        }
    }

    public void a() {
        this.o.release();
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.t) {
            chunkSampleStream.release(this);
        }
        this.s = null;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSampleStream.ReleaseCallback
    public synchronized void onSampleStreamReleased(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        PlayerEmsgHandler.PlayerTrackEmsgHandler remove = this.p.remove(chunkSampleStream);
        if (remove != null) {
            remove.release();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.s = callback;
        callback.onPrepared(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        this.j.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.l;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        List<AdaptationSet> list2 = this.w.getPeriod(this.x).adaptationSets;
        ArrayList arrayList = new ArrayList();
        for (ExoTrackSelection exoTrackSelection : list) {
            TrackGroupInfo trackGroupInfo = this.m[this.l.indexOf(exoTrackSelection.getTrackGroup())];
            if (trackGroupInfo.c == 0) {
                int[] iArr = trackGroupInfo.a;
                int[] iArr2 = new int[exoTrackSelection.length()];
                for (int i = 0; i < exoTrackSelection.length(); i++) {
                    iArr2[i] = exoTrackSelection.getIndexInTrackGroup(i);
                }
                Arrays.sort(iArr2);
                int size = list2.get(iArr[0]).representations.size();
                int i2 = 0;
                int i3 = 0;
                for (int i4 : iArr2) {
                    while (true) {
                        int i5 = i3 + size;
                        if (i4 >= i5) {
                            i2++;
                            size = list2.get(iArr[i2]).representations.size();
                            i3 = i5;
                        }
                    }
                    arrayList.add(new StreamKey(this.x, iArr[i2], i4 - i3));
                }
            }
        }
        return arrayList;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int[] a = a(exoTrackSelectionArr);
        a(exoTrackSelectionArr, zArr, sampleStreamArr);
        a(exoTrackSelectionArr, sampleStreamArr, a);
        a(exoTrackSelectionArr, sampleStreamArr, zArr2, j, a);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (SampleStream sampleStream : sampleStreamArr) {
            if (sampleStream instanceof ChunkSampleStream) {
                arrayList.add((ChunkSampleStream) sampleStream);
            } else if (sampleStream instanceof a) {
                arrayList2.add((a) sampleStream);
            }
        }
        this.t = a(arrayList.size());
        arrayList.toArray(this.t);
        this.u = new a[arrayList2.size()];
        arrayList2.toArray(this.u);
        this.v = this.n.createCompositeSequenceableLoader(this.t);
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.t) {
            chunkSampleStream.discardBuffer(j, z);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        this.v.reevaluateBuffer(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j) {
        return this.v.continueLoading(j);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.v.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.v.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.v.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j) {
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.t) {
            chunkSampleStream.seekToUs(j);
        }
        for (a aVar : this.u) {
            aVar.a(j);
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        ChunkSampleStream<DashChunkSource>[] chunkSampleStreamArr = this.t;
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : chunkSampleStreamArr) {
            if (chunkSampleStream.primaryTrackType == 2) {
                return chunkSampleStream.getAdjustedSeekPositionUs(j, seekParameters);
            }
        }
        return j;
    }

    /* renamed from: a */
    public void onContinueLoadingRequested(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        this.s.onContinueLoadingRequested(this);
    }

    private int[] a(ExoTrackSelection[] exoTrackSelectionArr) {
        int[] iArr = new int[exoTrackSelectionArr.length];
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            if (exoTrackSelectionArr[i] != null) {
                iArr[i] = this.l.indexOf(exoTrackSelectionArr[i].getTrackGroup());
            } else {
                iArr[i] = -1;
            }
        }
        return iArr;
    }

    private void a(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr) {
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            if (exoTrackSelectionArr[i] == null || !zArr[i]) {
                if (sampleStreamArr[i] instanceof ChunkSampleStream) {
                    ((ChunkSampleStream) sampleStreamArr[i]).release(this);
                } else if (sampleStreamArr[i] instanceof ChunkSampleStream.EmbeddedSampleStream) {
                    ((ChunkSampleStream.EmbeddedSampleStream) sampleStreamArr[i]).release();
                }
                sampleStreamArr[i] = null;
            }
        }
    }

    private void a(ExoTrackSelection[] exoTrackSelectionArr, SampleStream[] sampleStreamArr, int[] iArr) {
        boolean z;
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            if ((sampleStreamArr[i] instanceof EmptySampleStream) || (sampleStreamArr[i] instanceof ChunkSampleStream.EmbeddedSampleStream)) {
                int a = a(i, iArr);
                if (a == -1) {
                    z = sampleStreamArr[i] instanceof EmptySampleStream;
                } else {
                    z = (sampleStreamArr[i] instanceof ChunkSampleStream.EmbeddedSampleStream) && ((ChunkSampleStream.EmbeddedSampleStream) sampleStreamArr[i]).parent == sampleStreamArr[a];
                }
                if (!z) {
                    if (sampleStreamArr[i] instanceof ChunkSampleStream.EmbeddedSampleStream) {
                        ((ChunkSampleStream.EmbeddedSampleStream) sampleStreamArr[i]).release();
                    }
                    sampleStreamArr[i] = null;
                }
            }
        }
    }

    private void a(ExoTrackSelection[] exoTrackSelectionArr, SampleStream[] sampleStreamArr, boolean[] zArr, long j, int[] iArr) {
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i];
            if (exoTrackSelection != null) {
                if (sampleStreamArr[i] == null) {
                    zArr[i] = true;
                    TrackGroupInfo trackGroupInfo = this.m[iArr[i]];
                    if (trackGroupInfo.c == 0) {
                        sampleStreamArr[i] = a(trackGroupInfo, exoTrackSelection, j);
                    } else if (trackGroupInfo.c == 2) {
                        sampleStreamArr[i] = new a(this.y.get(trackGroupInfo.d), exoTrackSelection.getTrackGroup().getFormat(0), this.w.dynamic);
                    }
                } else if (sampleStreamArr[i] instanceof ChunkSampleStream) {
                    ((DashChunkSource) ((ChunkSampleStream) sampleStreamArr[i]).getChunkSource()).updateTrackSelection(exoTrackSelection);
                }
            }
        }
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            if (sampleStreamArr[i2] == null && exoTrackSelectionArr[i2] != null) {
                TrackGroupInfo trackGroupInfo2 = this.m[iArr[i2]];
                if (trackGroupInfo2.c == 1) {
                    int a = a(i2, iArr);
                    if (a == -1) {
                        sampleStreamArr[i2] = new EmptySampleStream();
                    } else {
                        sampleStreamArr[i2] = ((ChunkSampleStream) sampleStreamArr[a]).selectEmbeddedTrack(j, trackGroupInfo2.b);
                    }
                }
            }
        }
    }

    private int a(int i, int[] iArr) {
        int i2 = iArr[i];
        if (i2 == -1) {
            return -1;
        }
        int i3 = this.m[i2].e;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            int i5 = iArr[i4];
            if (i5 == i3 && this.m[i5].c == 0) {
                return i4;
            }
        }
        return -1;
    }

    private static Pair<TrackGroupArray, TrackGroupInfo[]> a(DrmSessionManager drmSessionManager, List<AdaptationSet> list, List<EventStream> list2) {
        int[][] a = a(list);
        int length = a.length;
        boolean[] zArr = new boolean[length];
        Format[][] formatArr = new Format[length];
        int a2 = a(length, list, a, zArr, formatArr) + length + list2.size();
        TrackGroup[] trackGroupArr = new TrackGroup[a2];
        TrackGroupInfo[] trackGroupInfoArr = new TrackGroupInfo[a2];
        a(list2, trackGroupArr, trackGroupInfoArr, a(drmSessionManager, list, a, length, zArr, formatArr, trackGroupArr, trackGroupInfoArr));
        return Pair.create(new TrackGroupArray(trackGroupArr), trackGroupInfoArr);
    }

    private static int[][] a(List<AdaptationSet> list) {
        int i;
        Descriptor b2;
        int size = list.size();
        SparseIntArray sparseIntArray = new SparseIntArray(size);
        ArrayList arrayList = new ArrayList(size);
        SparseArray sparseArray = new SparseArray(size);
        for (int i2 = 0; i2 < size; i2++) {
            sparseIntArray.put(list.get(i2).id, i2);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(Integer.valueOf(i2));
            arrayList.add(arrayList2);
            sparseArray.put(i2, arrayList2);
        }
        for (int i3 = 0; i3 < size; i3++) {
            AdaptationSet adaptationSet = list.get(i3);
            Descriptor c2 = c(adaptationSet.essentialProperties);
            if (c2 == null) {
                c2 = c(adaptationSet.supplementalProperties);
            }
            if (c2 == null || (i = sparseIntArray.get(Integer.parseInt(c2.value), -1)) == -1) {
                i = i3;
            }
            if (i == i3 && (b2 = b(adaptationSet.supplementalProperties)) != null) {
                int i4 = i;
                for (String str : Util.split(b2.value, Constants.ACCEPT_TIME_SEPARATOR_SP)) {
                    int i5 = sparseIntArray.get(Integer.parseInt(str), -1);
                    if (i5 != -1) {
                        i4 = Math.min(i4, i5);
                    }
                }
                i = i4;
            }
            if (i != i3) {
                List list2 = (List) sparseArray.get(i3);
                List list3 = (List) sparseArray.get(i);
                list3.addAll(list2);
                sparseArray.put(i3, list3);
                arrayList.remove(list2);
            }
        }
        int[][] iArr = new int[arrayList.size()];
        for (int i6 = 0; i6 < iArr.length; i6++) {
            iArr[i6] = Ints.toArray((Collection) arrayList.get(i6));
            Arrays.sort(iArr[i6]);
        }
        return iArr;
    }

    private static int a(int i, List<AdaptationSet> list, int[][] iArr, boolean[] zArr, Format[][] formatArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (a(list, iArr[i3])) {
                zArr[i3] = true;
                i2++;
            }
            formatArr[i3] = b(list, iArr[i3]);
            if (formatArr[i3].length != 0) {
                i2++;
            }
        }
        return i2;
    }

    private static int a(DrmSessionManager drmSessionManager, List<AdaptationSet> list, int[][] iArr, int i, boolean[] zArr, Format[][] formatArr, TrackGroup[] trackGroupArr, TrackGroupInfo[] trackGroupInfoArr) {
        int i2;
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            int[] iArr2 = iArr[i4];
            ArrayList arrayList = new ArrayList();
            for (int i5 : iArr2) {
                arrayList.addAll(list.get(i5).representations);
            }
            Format[] formatArr2 = new Format[arrayList.size()];
            for (int i6 = 0; i6 < formatArr2.length; i6++) {
                Format format = ((Representation) arrayList.get(i6)).format;
                formatArr2[i6] = format.copyWithExoMediaCryptoType(drmSessionManager.getExoMediaCryptoType(format));
            }
            AdaptationSet adaptationSet = list.get(iArr2[0]);
            int i7 = i3 + 1;
            if (zArr[i4]) {
                i2 = i7 + 1;
            } else {
                i2 = i7;
                i7 = -1;
            }
            if (formatArr[i4].length != 0) {
                i3 = i2 + 1;
            } else {
                i3 = i2;
                i2 = -1;
            }
            trackGroupArr[i3] = new TrackGroup(formatArr2);
            trackGroupInfoArr[i3] = TrackGroupInfo.a(adaptationSet.type, iArr2, i3, i7, i2);
            if (i7 != -1) {
                Format.Builder builder = new Format.Builder();
                int i8 = adaptationSet.id;
                StringBuilder sb = new StringBuilder(16);
                sb.append(i8);
                sb.append(":emsg");
                trackGroupArr[i7] = new TrackGroup(builder.setId(sb.toString()).setSampleMimeType(MimeTypes.APPLICATION_EMSG).build());
                trackGroupInfoArr[i7] = TrackGroupInfo.a(iArr2, i3);
            }
            if (i2 != -1) {
                trackGroupArr[i2] = new TrackGroup(formatArr[i4]);
                trackGroupInfoArr[i2] = TrackGroupInfo.b(iArr2, i3);
            }
        }
        return i3;
    }

    private static void a(List<EventStream> list, TrackGroup[] trackGroupArr, TrackGroupInfo[] trackGroupInfoArr, int i) {
        int i2 = i;
        for (int i3 = 0; i3 < list.size(); i3++) {
            trackGroupArr[i2] = new TrackGroup(new Format.Builder().setId(list.get(i3).id()).setSampleMimeType(MimeTypes.APPLICATION_EMSG).build());
            i2++;
            trackGroupInfoArr[i2] = TrackGroupInfo.a(i3);
        }
    }

    private ChunkSampleStream<DashChunkSource> a(TrackGroupInfo trackGroupInfo, ExoTrackSelection exoTrackSelection, long j) {
        TrackGroup trackGroup;
        int i;
        TrackGroup trackGroup2;
        int i2;
        boolean z = trackGroupInfo.f != -1;
        if (z) {
            trackGroup = this.l.get(trackGroupInfo.f);
            i = 1;
        } else {
            trackGroup = null;
            i = 0;
        }
        boolean z2 = trackGroupInfo.g != -1;
        if (z2) {
            trackGroup2 = this.l.get(trackGroupInfo.g);
            i += trackGroup2.length;
        } else {
            trackGroup2 = null;
        }
        Format[] formatArr = new Format[i];
        int[] iArr = new int[i];
        if (z) {
            formatArr[0] = trackGroup.getFormat(0);
            iArr[0] = 5;
            i2 = 1;
        } else {
            i2 = 0;
        }
        ArrayList arrayList = new ArrayList();
        if (z2) {
            for (int i3 = 0; i3 < trackGroup2.length; i3++) {
                formatArr[i2] = trackGroup2.getFormat(i3);
                iArr[i2] = 3;
                arrayList.add(formatArr[i2]);
                i2++;
            }
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler newPlayerTrackEmsgHandler = (!this.w.dynamic || !z) ? null : this.o.newPlayerTrackEmsgHandler();
        ChunkSampleStream<DashChunkSource> chunkSampleStream = new ChunkSampleStream<>(trackGroupInfo.b, iArr, formatArr, this.d.createDashChunkSource(this.j, this.w, this.h, this.x, trackGroupInfo.a, exoTrackSelection, trackGroupInfo.b, this.i, z, arrayList, newPlayerTrackEmsgHandler, this.e), this, this.k, j, this.f, this.r, this.g, this.q);
        synchronized (this) {
            this.p.put(chunkSampleStream, newPlayerTrackEmsgHandler);
        }
        return chunkSampleStream;
    }

    @Nullable
    private static Descriptor b(List<Descriptor> list) {
        return a(list, "urn:mpeg:dash:adaptation-set-switching:2016");
    }

    @Nullable
    private static Descriptor c(List<Descriptor> list) {
        return a(list, "http://dashif.org/guidelines/trickmode");
    }

    @Nullable
    private static Descriptor a(List<Descriptor> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            if (str.equals(descriptor.schemeIdUri)) {
                return descriptor;
            }
        }
        return null;
    }

    private static boolean a(List<AdaptationSet> list, int[] iArr) {
        for (int i : iArr) {
            List<Representation> list2 = list.get(i).representations;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if (!list2.get(i2).inbandEventStreams.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Format[] b(List<AdaptationSet> list, int[] iArr) {
        for (int i : iArr) {
            AdaptationSet adaptationSet = list.get(i);
            List<Descriptor> list2 = list.get(i).accessibilityDescriptors;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                Descriptor descriptor = list2.get(i2);
                if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri)) {
                    Format.Builder sampleMimeType = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA608);
                    int i3 = adaptationSet.id;
                    StringBuilder sb = new StringBuilder(18);
                    sb.append(i3);
                    sb.append(":cea608");
                    return a(descriptor, b, sampleMimeType.setId(sb.toString()).build());
                } else if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri)) {
                    Format.Builder sampleMimeType2 = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA708);
                    int i4 = adaptationSet.id;
                    StringBuilder sb2 = new StringBuilder(18);
                    sb2.append(i4);
                    sb2.append(":cea708");
                    return a(descriptor, c, sampleMimeType2.setId(sb2.toString()).build());
                }
            }
        }
        return new Format[0];
    }

    private static Format[] a(Descriptor descriptor, Pattern pattern, Format format) {
        String str = descriptor.value;
        if (str == null) {
            return new Format[]{format};
        }
        String[] split = Util.split(str, ";");
        Format[] formatArr = new Format[split.length];
        for (int i = 0; i < split.length; i++) {
            Matcher matcher = pattern.matcher(split[i]);
            if (!matcher.matches()) {
                return new Format[]{format};
            }
            int parseInt = Integer.parseInt(matcher.group(1));
            Format.Builder buildUpon = format.buildUpon();
            String str2 = format.id;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 12);
            sb.append(str2);
            sb.append(Constants.COLON_SEPARATOR);
            sb.append(parseInt);
            formatArr[i] = buildUpon.setId(sb.toString()).setAccessibilityChannel(parseInt).setLanguage(matcher.group(2)).build();
        }
        return formatArr;
    }

    private static ChunkSampleStream<DashChunkSource>[] a(int i) {
        return new ChunkSampleStream[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class TrackGroupInfo {
        public final int[] a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface TrackGroupCategory {
        }

        public static TrackGroupInfo a(int i, int[] iArr, int i2, int i3, int i4) {
            return new TrackGroupInfo(i, 0, iArr, i2, i3, i4, -1);
        }

        public static TrackGroupInfo a(int[] iArr, int i) {
            return new TrackGroupInfo(5, 1, iArr, i, -1, -1, -1);
        }

        public static TrackGroupInfo b(int[] iArr, int i) {
            return new TrackGroupInfo(3, 1, iArr, i, -1, -1, -1);
        }

        public static TrackGroupInfo a(int i) {
            return new TrackGroupInfo(5, 2, new int[0], -1, -1, -1, i);
        }

        private TrackGroupInfo(int i, int i2, int[] iArr, int i3, int i4, int i5, int i6) {
            this.b = i;
            this.a = iArr;
            this.c = i2;
            this.e = i3;
            this.f = i4;
            this.g = i5;
            this.d = i6;
        }
    }
}
