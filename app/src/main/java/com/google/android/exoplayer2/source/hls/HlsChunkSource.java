package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.DataChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class HlsChunkSource {
    private final HlsExtractorFactory a;
    private final DataSource b;
    private final DataSource c;
    private final TimestampAdjusterProvider d;
    private final Uri[] e;
    private final Format[] f;
    private final HlsPlaylistTracker g;
    private final TrackGroup h;
    @Nullable
    private final List<Format> i;
    private boolean k;
    @Nullable
    private IOException m;
    @Nullable
    private Uri n;
    private boolean o;
    private ExoTrackSelection p;
    private boolean r;
    private final b j = new b(4);
    private byte[] l = Util.EMPTY_BYTE_ARRAY;
    private long q = C.TIME_UNSET;

    /* loaded from: classes2.dex */
    public static final class HlsChunkHolder {
        @Nullable
        public Chunk chunk;
        public boolean endOfStream;
        @Nullable
        public Uri playlistUrl;

        public HlsChunkHolder() {
            clear();
        }

        public void clear() {
            this.chunk = null;
            this.endOfStream = false;
            this.playlistUrl = null;
        }
    }

    public HlsChunkSource(HlsExtractorFactory hlsExtractorFactory, HlsPlaylistTracker hlsPlaylistTracker, Uri[] uriArr, Format[] formatArr, HlsDataSourceFactory hlsDataSourceFactory, @Nullable TransferListener transferListener, TimestampAdjusterProvider timestampAdjusterProvider, @Nullable List<Format> list) {
        this.a = hlsExtractorFactory;
        this.g = hlsPlaylistTracker;
        this.e = uriArr;
        this.f = formatArr;
        this.d = timestampAdjusterProvider;
        this.i = list;
        this.b = hlsDataSourceFactory.createDataSource(1);
        if (transferListener != null) {
            this.b.addTransferListener(transferListener);
        }
        this.c = hlsDataSourceFactory.createDataSource(3);
        this.h = new TrackGroup(formatArr);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < uriArr.length; i++) {
            if ((formatArr[i].roleFlags & 16384) == 0) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        this.p = new c(this.h, Ints.toArray(arrayList));
    }

    public void a() throws IOException {
        IOException iOException = this.m;
        if (iOException == null) {
            Uri uri = this.n;
            if (uri != null && this.r) {
                this.g.maybeThrowPlaylistRefreshError(uri);
                return;
            }
            return;
        }
        throw iOException;
    }

    public TrackGroup b() {
        return this.h;
    }

    public void a(ExoTrackSelection exoTrackSelection) {
        this.p = exoTrackSelection;
    }

    public ExoTrackSelection c() {
        return this.p;
    }

    public void d() {
        this.m = null;
    }

    public void a(boolean z) {
        this.k = z;
    }

    public int a(c cVar) {
        List<HlsMediaPlaylist.Part> list;
        if (cVar.e == -1) {
            return 1;
        }
        HlsMediaPlaylist hlsMediaPlaylist = (HlsMediaPlaylist) Assertions.checkNotNull(this.g.getPlaylistSnapshot(this.e[this.h.indexOf(cVar.trackFormat)], false));
        int i = (int) (cVar.chunkIndex - hlsMediaPlaylist.mediaSequence);
        if (i < 0) {
            return 1;
        }
        if (i < hlsMediaPlaylist.segments.size()) {
            list = hlsMediaPlaylist.segments.get(i).parts;
        } else {
            list = hlsMediaPlaylist.trailingParts;
        }
        if (cVar.e >= list.size()) {
            return 2;
        }
        HlsMediaPlaylist.Part part = list.get(cVar.e);
        if (part.isPreload) {
            return 0;
        }
        return Util.areEqual(Uri.parse(UriUtil.resolve(hlsMediaPlaylist.baseUri, part.url)), cVar.dataSpec.uri) ? 1 : 2;
    }

    public void a(long j, long j2, List<c> list, boolean z, HlsChunkHolder hlsChunkHolder) {
        long j3;
        long j4;
        long j5;
        int i;
        Uri uri;
        boolean z2;
        c cVar = list.isEmpty() ? null : (c) Iterables.getLast(list);
        int indexOf = cVar == null ? -1 : this.h.indexOf(cVar.trackFormat);
        long j6 = j2 - j;
        long a2 = a(j);
        if (cVar == null || this.o) {
            j4 = j6;
            j3 = a2;
        } else {
            long durationUs = cVar.getDurationUs();
            long max = Math.max(0L, j6 - durationUs);
            if (a2 != C.TIME_UNSET) {
                j4 = max;
                j3 = Math.max(0L, a2 - durationUs);
            } else {
                j4 = max;
                j3 = a2;
            }
        }
        this.p.updateSelectedTrack(j, j4, j3, list, a(cVar, j2));
        int selectedIndexInTrackGroup = this.p.getSelectedIndexInTrackGroup();
        boolean z3 = false;
        if (indexOf != selectedIndexInTrackGroup) {
            z3 = true;
        }
        Uri uri2 = this.e[selectedIndexInTrackGroup];
        if (!this.g.isSnapshotValid(uri2)) {
            hlsChunkHolder.playlistUrl = uri2;
            this.r &= uri2.equals(this.n);
            this.n = uri2;
            return;
        }
        HlsMediaPlaylist playlistSnapshot = this.g.getPlaylistSnapshot(uri2, true);
        Assertions.checkNotNull(playlistSnapshot);
        this.o = playlistSnapshot.hasIndependentSegments;
        a(playlistSnapshot);
        long initialStartTimeUs = playlistSnapshot.startTimeUs - this.g.getInitialStartTimeUs();
        Pair<Long, Integer> a3 = a(cVar, z3, playlistSnapshot, initialStartTimeUs, j2);
        long longValue = ((Long) a3.first).longValue();
        int intValue = ((Integer) a3.second).intValue();
        if (longValue >= playlistSnapshot.mediaSequence || cVar == null || !z3) {
            j5 = initialStartTimeUs;
            uri = uri2;
            i = selectedIndexInTrackGroup;
        } else {
            Uri uri3 = this.e[indexOf];
            HlsMediaPlaylist playlistSnapshot2 = this.g.getPlaylistSnapshot(uri3, true);
            Assertions.checkNotNull(playlistSnapshot2);
            j5 = playlistSnapshot2.startTimeUs - this.g.getInitialStartTimeUs();
            Pair<Long, Integer> a4 = a(cVar, false, playlistSnapshot2, j5, j2);
            longValue = ((Long) a4.first).longValue();
            intValue = ((Integer) a4.second).intValue();
            i = indexOf;
            uri = uri3;
            playlistSnapshot = playlistSnapshot2;
        }
        if (longValue < playlistSnapshot.mediaSequence) {
            this.m = new BehindLiveWindowException();
            return;
        }
        d b2 = b(playlistSnapshot, longValue, intValue);
        if (b2 != null) {
            z2 = false;
        } else if (!playlistSnapshot.hasEndTag) {
            hlsChunkHolder.playlistUrl = uri;
            this.r &= uri.equals(this.n);
            this.n = uri;
            return;
        } else if (z || playlistSnapshot.segments.isEmpty()) {
            hlsChunkHolder.endOfStream = true;
            return;
        } else {
            b2 = new d((HlsMediaPlaylist.SegmentBase) Iterables.getLast(playlistSnapshot.segments), (playlistSnapshot.mediaSequence + playlistSnapshot.segments.size()) - 1, -1);
            z2 = false;
        }
        this.r = z2;
        this.n = null;
        Uri a5 = a(playlistSnapshot, b2.a.initializationSegment);
        hlsChunkHolder.chunk = a(a5, i);
        if (hlsChunkHolder.chunk == null) {
            Uri a6 = a(playlistSnapshot, b2.a);
            hlsChunkHolder.chunk = a(a6, i);
            if (hlsChunkHolder.chunk == null) {
                boolean a7 = c.a(cVar, uri, playlistSnapshot, b2, j5);
                if (!a7 || !b2.d) {
                    hlsChunkHolder.chunk = c.a(this.a, this.b, this.f[i], j5, playlistSnapshot, b2, uri, this.i, this.p.getSelectionReason(), this.p.getSelectionData(), this.k, this.d, cVar, this.j.a(a6), this.j.a(a5), a7);
                }
            }
        }
    }

    @Nullable
    private static d b(HlsMediaPlaylist hlsMediaPlaylist, long j, int i) {
        int i2 = (int) (j - hlsMediaPlaylist.mediaSequence);
        if (i2 == hlsMediaPlaylist.segments.size()) {
            if (i == -1) {
                i = 0;
            }
            if (i < hlsMediaPlaylist.trailingParts.size()) {
                return new d(hlsMediaPlaylist.trailingParts.get(i), j, i);
            }
            return null;
        }
        HlsMediaPlaylist.Segment segment = hlsMediaPlaylist.segments.get(i2);
        if (i == -1) {
            return new d(segment, j, -1);
        }
        if (i < segment.parts.size()) {
            return new d(segment.parts.get(i), j, i);
        }
        int i3 = i2 + 1;
        if (i3 < hlsMediaPlaylist.segments.size()) {
            return new d(hlsMediaPlaylist.segments.get(i3), j + 1, -1);
        }
        if (!hlsMediaPlaylist.trailingParts.isEmpty()) {
            return new d(hlsMediaPlaylist.trailingParts.get(0), j + 1, 0);
        }
        return null;
    }

    public void a(Chunk chunk) {
        if (chunk instanceof a) {
            a aVar = (a) chunk;
            this.l = aVar.getDataHolder();
            this.j.a(aVar.dataSpec.uri, (byte[]) Assertions.checkNotNull(aVar.a()));
        }
    }

    public boolean a(Chunk chunk, long j) {
        ExoTrackSelection exoTrackSelection = this.p;
        return exoTrackSelection.blacklist(exoTrackSelection.indexOf(this.h.indexOf(chunk.trackFormat)), j);
    }

    public boolean a(Uri uri, long j) {
        int indexOf;
        int i = 0;
        while (true) {
            Uri[] uriArr = this.e;
            if (i >= uriArr.length) {
                i = -1;
                break;
            } else if (uriArr[i].equals(uri)) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1 || (indexOf = this.p.indexOf(i)) == -1) {
            return true;
        }
        this.r |= uri.equals(this.n);
        return j == C.TIME_UNSET || (this.p.blacklist(indexOf, j) && this.g.excludeMediaPlaylist(uri, j));
    }

    public MediaChunkIterator[] a(@Nullable c cVar, long j) {
        int i;
        int indexOf = cVar == null ? -1 : this.h.indexOf(cVar.trackFormat);
        MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[this.p.length()];
        boolean z = false;
        int i2 = 0;
        while (i2 < mediaChunkIteratorArr.length) {
            int indexInTrackGroup = this.p.getIndexInTrackGroup(i2);
            Uri uri = this.e[indexInTrackGroup];
            if (!this.g.isSnapshotValid(uri)) {
                mediaChunkIteratorArr[i2] = MediaChunkIterator.EMPTY;
                i = i2;
            } else {
                HlsMediaPlaylist playlistSnapshot = this.g.getPlaylistSnapshot(uri, z);
                Assertions.checkNotNull(playlistSnapshot);
                long initialStartTimeUs = playlistSnapshot.startTimeUs - this.g.getInitialStartTimeUs();
                i = i2;
                Pair<Long, Integer> a2 = a(cVar, indexInTrackGroup != indexOf ? true : z, playlistSnapshot, initialStartTimeUs, j);
                mediaChunkIteratorArr[i] = new b(playlistSnapshot.baseUri, initialStartTimeUs, a(playlistSnapshot, ((Long) a2.first).longValue(), ((Integer) a2.second).intValue()));
            }
            i2 = i + 1;
            z = false;
        }
        return mediaChunkIteratorArr;
    }

    public int a(long j, List<? extends MediaChunk> list) {
        if (this.m != null || this.p.length() < 2) {
            return list.size();
        }
        return this.p.evaluateQueueSize(j, list);
    }

    public boolean a(long j, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.m != null) {
            return false;
        }
        return this.p.shouldCancelChunkLoad(j, chunk, list);
    }

    @VisibleForTesting
    static List<HlsMediaPlaylist.SegmentBase> a(HlsMediaPlaylist hlsMediaPlaylist, long j, int i) {
        int i2 = (int) (j - hlsMediaPlaylist.mediaSequence);
        if (i2 < 0 || hlsMediaPlaylist.segments.size() < i2) {
            return ImmutableList.of();
        }
        ArrayList arrayList = new ArrayList();
        if (i2 < hlsMediaPlaylist.segments.size()) {
            if (i != -1) {
                HlsMediaPlaylist.Segment segment = hlsMediaPlaylist.segments.get(i2);
                if (i == 0) {
                    arrayList.add(segment);
                } else if (i < segment.parts.size()) {
                    arrayList.addAll(segment.parts.subList(i, segment.parts.size()));
                }
                i2++;
            }
            arrayList.addAll(hlsMediaPlaylist.segments.subList(i2, hlsMediaPlaylist.segments.size()));
            i = 0;
        }
        if (hlsMediaPlaylist.partTargetDurationUs != C.TIME_UNSET) {
            if (i == -1) {
                i = 0;
            }
            if (i < hlsMediaPlaylist.trailingParts.size()) {
                arrayList.addAll(hlsMediaPlaylist.trailingParts.subList(i, hlsMediaPlaylist.trailingParts.size()));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public boolean a(Uri uri) {
        return Util.contains(this.e, uri);
    }

    private Pair<Long, Integer> a(@Nullable c cVar, boolean z, HlsMediaPlaylist hlsMediaPlaylist, long j, long j2) {
        List<HlsMediaPlaylist.Part> list;
        long j3;
        int i = -1;
        if (cVar == null || z) {
            long j4 = hlsMediaPlaylist.durationUs + j;
            if (cVar != null && !this.o) {
                j2 = cVar.startTimeUs;
            }
            if (!hlsMediaPlaylist.hasEndTag && j2 >= j4) {
                return new Pair<>(Long.valueOf(hlsMediaPlaylist.mediaSequence + hlsMediaPlaylist.segments.size()), -1);
            }
            long j5 = j2 - j;
            int i2 = 0;
            int binarySearchFloor = Util.binarySearchFloor((List<? extends Comparable<? super Long>>) hlsMediaPlaylist.segments, Long.valueOf(j5), true, !this.g.isLive() || cVar == null);
            long j6 = binarySearchFloor + hlsMediaPlaylist.mediaSequence;
            if (binarySearchFloor >= 0) {
                HlsMediaPlaylist.Segment segment = hlsMediaPlaylist.segments.get(binarySearchFloor);
                if (j5 < segment.relativeStartTimeUs + segment.durationUs) {
                    list = segment.parts;
                } else {
                    list = hlsMediaPlaylist.trailingParts;
                }
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    HlsMediaPlaylist.Part part = list.get(i2);
                    if (j5 >= part.relativeStartTimeUs + part.durationUs) {
                        i2++;
                    } else if (part.isIndependent) {
                        j6 += list == hlsMediaPlaylist.trailingParts ? 1L : 0L;
                        i = i2;
                    }
                }
            }
            return new Pair<>(Long.valueOf(j6), Integer.valueOf(i));
        } else if (!cVar.isLoadCompleted()) {
            return new Pair<>(Long.valueOf(cVar.chunkIndex), Integer.valueOf(cVar.e));
        } else {
            if (cVar.e == -1) {
                j3 = cVar.getNextChunkIndex();
            } else {
                j3 = cVar.chunkIndex;
            }
            Long valueOf = Long.valueOf(j3);
            if (cVar.e != -1) {
                i = cVar.e + 1;
            }
            return new Pair<>(valueOf, Integer.valueOf(i));
        }
    }

    private long a(long j) {
        return (this.q > C.TIME_UNSET ? 1 : (this.q == C.TIME_UNSET ? 0 : -1)) != 0 ? this.q - j : C.TIME_UNSET;
    }

    private void a(HlsMediaPlaylist hlsMediaPlaylist) {
        this.q = hlsMediaPlaylist.hasEndTag ? C.TIME_UNSET : hlsMediaPlaylist.getEndTimeUs() - this.g.getInitialStartTimeUs();
    }

    @Nullable
    private Chunk a(@Nullable Uri uri, int i) {
        if (uri == null) {
            return null;
        }
        byte[] b2 = this.j.b(uri);
        if (b2 != null) {
            this.j.a(uri, b2);
            return null;
        }
        return new a(this.c, new DataSpec.Builder().setUri(uri).setFlags(1).build(), this.f[i], this.p.getSelectionReason(), this.p.getSelectionData(), this.l);
    }

    @Nullable
    private static Uri a(HlsMediaPlaylist hlsMediaPlaylist, @Nullable HlsMediaPlaylist.SegmentBase segmentBase) {
        if (segmentBase == null || segmentBase.fullSegmentEncryptionKeyUri == null) {
            return null;
        }
        return UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, segmentBase.fullSegmentEncryptionKeyUri);
    }

    /* loaded from: classes2.dex */
    public static final class d {
        public final HlsMediaPlaylist.SegmentBase a;
        public final long b;
        public final int c;
        public final boolean d;

        public d(HlsMediaPlaylist.SegmentBase segmentBase, long j, int i) {
            this.a = segmentBase;
            this.b = j;
            this.c = i;
            this.d = (segmentBase instanceof HlsMediaPlaylist.Part) && ((HlsMediaPlaylist.Part) segmentBase).isPreload;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c extends BaseTrackSelection {
        private int a;

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        @Nullable
        public Object getSelectionData() {
            return null;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectionReason() {
            return 0;
        }

        public c(TrackGroup trackGroup, int[] iArr) {
            super(trackGroup, iArr);
            this.a = indexOf(trackGroup.getFormat(iArr[0]));
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (isBlacklisted(this.a, elapsedRealtime)) {
                for (int i = this.length - 1; i >= 0; i--) {
                    if (!isBlacklisted(i, elapsedRealtime)) {
                        this.a = i;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectedIndex() {
            return this.a;
        }
    }

    /* loaded from: classes2.dex */
    public static final class a extends DataChunk {
        private byte[] a;

        public a(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, byte[] bArr) {
            super(dataSource, dataSpec, 3, format, i, obj, bArr);
        }

        @Override // com.google.android.exoplayer2.source.chunk.DataChunk
        protected void consume(byte[] bArr, int i) {
            this.a = Arrays.copyOf(bArr, i);
        }

        @Nullable
        public byte[] a() {
            return this.a;
        }
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static final class b extends BaseMediaChunkIterator {
        private final List<HlsMediaPlaylist.SegmentBase> a;
        private final long b;
        private final String c;

        public b(String str, long j, List<HlsMediaPlaylist.SegmentBase> list) {
            super(0L, list.size() - 1);
            this.c = str;
            this.b = j;
            this.a = list;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            HlsMediaPlaylist.SegmentBase segmentBase = this.a.get((int) getCurrentIndex());
            return new DataSpec(UriUtil.resolveToUri(this.c, segmentBase.url), segmentBase.byteRangeOffset, segmentBase.byteRangeLength);
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.b + this.a.get((int) getCurrentIndex()).relativeStartTimeUs;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            checkInBounds();
            HlsMediaPlaylist.SegmentBase segmentBase = this.a.get((int) getCurrentIndex());
            return this.b + segmentBase.relativeStartTimeUs + segmentBase.durationUs;
        }
    }
}
