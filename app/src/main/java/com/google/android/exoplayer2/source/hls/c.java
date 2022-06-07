package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.hls.HlsChunkSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* compiled from: HlsMediaChunk.java */
/* loaded from: classes2.dex */
public final class c extends MediaChunk {
    private static final AtomicInteger f = new AtomicInteger();
    private boolean A;
    private boolean B;
    public final int a;
    public final int b;
    public final Uri c;
    public final boolean d;
    public final int e;
    @Nullable
    private final DataSource g;
    @Nullable
    private final DataSpec h;
    @Nullable
    private final HlsMediaChunkExtractor i;
    private final boolean j;
    private final boolean k;
    private final TimestampAdjuster l;
    private final HlsExtractorFactory m;
    @Nullable
    private final List<Format> n;
    @Nullable
    private final DrmInitData o;
    private final Id3Decoder p;
    private final ParsableByteArray q;
    private final boolean r;
    private final boolean s;
    private HlsMediaChunkExtractor t;
    private HlsSampleStreamWrapper u;
    private int v;
    private boolean w;
    private volatile boolean x;
    private boolean y;
    private ImmutableList<Integer> z;

    public static c a(HlsExtractorFactory hlsExtractorFactory, DataSource dataSource, Format format, long j, HlsMediaPlaylist hlsMediaPlaylist, HlsChunkSource.d dVar, Uri uri, @Nullable List<Format> list, int i, @Nullable Object obj, boolean z, TimestampAdjusterProvider timestampAdjusterProvider, @Nullable c cVar, @Nullable byte[] bArr, @Nullable byte[] bArr2, boolean z2) {
        boolean z3;
        boolean z4;
        DataSpec dataSpec;
        DataSource dataSource2;
        ParsableByteArray parsableByteArray;
        Id3Decoder id3Decoder;
        HlsMediaChunkExtractor hlsMediaChunkExtractor;
        HlsMediaPlaylist.SegmentBase segmentBase = dVar.a;
        DataSpec build = new DataSpec.Builder().setUri(UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, segmentBase.url)).setPosition(segmentBase.byteRangeOffset).setLength(segmentBase.byteRangeLength).setFlags(dVar.d ? 8 : 0).build();
        boolean z5 = bArr != null;
        DataSource a = a(dataSource, bArr, z5 ? a((String) Assertions.checkNotNull(segmentBase.encryptionIV)) : null);
        HlsMediaPlaylist.Segment segment = segmentBase.initializationSegment;
        if (segment != null) {
            boolean z6 = bArr2 != null;
            byte[] a2 = z6 ? a((String) Assertions.checkNotNull(segment.encryptionIV)) : null;
            z3 = z5;
            dataSpec = new DataSpec(UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, segment.url), segment.byteRangeOffset, segment.byteRangeLength);
            dataSource2 = a(dataSource, bArr2, a2);
            z4 = z6;
        } else {
            z3 = z5;
            dataSource2 = null;
            dataSpec = null;
            z4 = false;
        }
        long j2 = j + segmentBase.relativeStartTimeUs;
        long j3 = j2 + segmentBase.durationUs;
        int i2 = hlsMediaPlaylist.discontinuitySequence + segmentBase.relativeDiscontinuitySequence;
        if (cVar != null) {
            DataSpec dataSpec2 = cVar.h;
            boolean z7 = dataSpec == dataSpec2 || (dataSpec != null && dataSpec2 != null && dataSpec.uri.equals(cVar.h.uri) && dataSpec.position == cVar.h.position);
            boolean z8 = uri.equals(cVar.c) && cVar.y;
            Id3Decoder id3Decoder2 = cVar.p;
            ParsableByteArray parsableByteArray2 = cVar.q;
            hlsMediaChunkExtractor = (!z7 || !z8 || cVar.A || cVar.b != i2) ? null : cVar.t;
            id3Decoder = id3Decoder2;
            parsableByteArray = parsableByteArray2;
        } else {
            id3Decoder = new Id3Decoder();
            parsableByteArray = new ParsableByteArray(10);
            hlsMediaChunkExtractor = null;
        }
        return new c(hlsExtractorFactory, a, build, format, z3, dataSource2, dataSpec, z4, uri, list, i, obj, j2, j3, dVar.b, dVar.c, !dVar.d, i2, segmentBase.hasGapTag, z, timestampAdjusterProvider.getAdjuster(i2), segmentBase.drmInitData, hlsMediaChunkExtractor, id3Decoder, parsableByteArray, z2);
    }

    public static boolean a(@Nullable c cVar, Uri uri, HlsMediaPlaylist hlsMediaPlaylist, HlsChunkSource.d dVar, long j) {
        if (cVar == null) {
            return false;
        }
        if (uri.equals(cVar.c) && cVar.y) {
            return false;
        }
        return !a(dVar, hlsMediaPlaylist) || j + dVar.a.relativeStartTimeUs < cVar.endTimeUs;
    }

    private c(HlsExtractorFactory hlsExtractorFactory, DataSource dataSource, DataSpec dataSpec, Format format, boolean z, @Nullable DataSource dataSource2, @Nullable DataSpec dataSpec2, boolean z2, Uri uri, @Nullable List<Format> list, int i, @Nullable Object obj, long j, long j2, long j3, int i2, boolean z3, int i3, boolean z4, boolean z5, TimestampAdjuster timestampAdjuster, @Nullable DrmInitData drmInitData, @Nullable HlsMediaChunkExtractor hlsMediaChunkExtractor, Id3Decoder id3Decoder, ParsableByteArray parsableByteArray, boolean z6) {
        super(dataSource, dataSpec, format, i, obj, j, j2, j3);
        this.r = z;
        this.e = i2;
        this.B = z3;
        this.b = i3;
        this.h = dataSpec2;
        this.g = dataSource2;
        this.w = dataSpec2 != null;
        this.s = z2;
        this.c = uri;
        this.j = z5;
        this.l = timestampAdjuster;
        this.k = z4;
        this.m = hlsExtractorFactory;
        this.n = list;
        this.o = drmInitData;
        this.i = hlsMediaChunkExtractor;
        this.p = id3Decoder;
        this.q = parsableByteArray;
        this.d = z6;
        this.z = ImmutableList.of();
        this.a = f.getAndIncrement();
    }

    public void a(HlsSampleStreamWrapper hlsSampleStreamWrapper, ImmutableList<Integer> immutableList) {
        this.u = hlsSampleStreamWrapper;
        this.z = immutableList;
    }

    public int a(int i) {
        Assertions.checkState(!this.d);
        if (i >= this.z.size()) {
            return 0;
        }
        return this.z.get(i).intValue();
    }

    public void a() {
        this.A = true;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunk
    public boolean isLoadCompleted() {
        return this.y;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void cancelLoad() {
        this.x = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void load() throws IOException {
        HlsMediaChunkExtractor hlsMediaChunkExtractor;
        Assertions.checkNotNull(this.u);
        if (this.t == null && (hlsMediaChunkExtractor = this.i) != null && hlsMediaChunkExtractor.isReusable()) {
            this.t = this.i;
            this.w = false;
        }
        d();
        if (!this.x) {
            if (!this.k) {
                e();
            }
            this.y = !this.x;
        }
    }

    public boolean b() {
        return this.B;
    }

    public void c() {
        this.B = true;
    }

    @RequiresNonNull({"output"})
    private void d() throws IOException {
        if (this.w) {
            Assertions.checkNotNull(this.g);
            Assertions.checkNotNull(this.h);
            a(this.g, this.h, this.s);
            this.v = 0;
            this.w = false;
        }
    }

    @RequiresNonNull({"output"})
    private void e() throws IOException {
        try {
            this.l.sharedInitializeOrWait(this.j, this.startTimeUs);
            a(this.dataSource, this.dataSpec, this.r);
        } catch (InterruptedException unused) {
            throw new InterruptedIOException();
        }
    }

    @RequiresNonNull({"output"})
    private void a(DataSource dataSource, DataSpec dataSpec, boolean z) throws IOException {
        DataSpec dataSpec2;
        long position;
        long j;
        boolean z2 = false;
        if (z) {
            if (this.v != 0) {
                z2 = true;
            }
            dataSpec2 = dataSpec;
        } else {
            dataSpec2 = dataSpec.subrange(this.v);
        }
        try {
            DefaultExtractorInput a = a(dataSource, dataSpec2);
            if (z2) {
                a.skipFully(this.v);
            }
            do {
                try {
                    if (this.x) {
                        break;
                    }
                } catch (EOFException e) {
                    if ((this.trackFormat.roleFlags & 16384) != 0) {
                        this.t.onTruncatedSegmentParsed();
                        position = a.getPosition();
                        j = dataSpec.position;
                    } else {
                        throw e;
                    }
                }
            } while (this.t.read(a));
            position = a.getPosition();
            j = dataSpec.position;
            this.v = (int) (position - j);
        } finally {
            Util.closeQuietly(dataSource);
        }
    }

    @EnsuresNonNull({"extractor"})
    @RequiresNonNull({"output"})
    private DefaultExtractorInput a(DataSource dataSource, DataSpec dataSpec) throws IOException {
        HlsMediaChunkExtractor hlsMediaChunkExtractor;
        long j;
        DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(dataSource, dataSpec.position, dataSource.open(dataSpec));
        if (this.t == null) {
            long a = a(defaultExtractorInput);
            defaultExtractorInput.resetPeekPosition();
            HlsMediaChunkExtractor hlsMediaChunkExtractor2 = this.i;
            if (hlsMediaChunkExtractor2 != null) {
                hlsMediaChunkExtractor = hlsMediaChunkExtractor2.recreate();
            } else {
                hlsMediaChunkExtractor = this.m.createExtractor(dataSpec.uri, this.trackFormat, this.n, this.l, dataSource.getResponseHeaders(), defaultExtractorInput);
            }
            this.t = hlsMediaChunkExtractor;
            if (this.t.isPackedAudioExtractor()) {
                HlsSampleStreamWrapper hlsSampleStreamWrapper = this.u;
                if (a != C.TIME_UNSET) {
                    j = this.l.adjustTsTimestamp(a);
                } else {
                    j = this.startTimeUs;
                }
                hlsSampleStreamWrapper.a(j);
            } else {
                this.u.a(0L);
            }
            this.u.h();
            this.t.init(this.u);
        }
        this.u.a(this.o);
        return defaultExtractorInput;
    }

    private long a(ExtractorInput extractorInput) throws IOException {
        extractorInput.resetPeekPosition();
        try {
            this.q.reset(10);
            extractorInput.peekFully(this.q.getData(), 0, 10);
            if (this.q.readUnsignedInt24() != 4801587) {
                return C.TIME_UNSET;
            }
            this.q.skipBytes(3);
            int readSynchSafeInt = this.q.readSynchSafeInt();
            int i = readSynchSafeInt + 10;
            if (i > this.q.capacity()) {
                byte[] data = this.q.getData();
                this.q.reset(i);
                System.arraycopy(data, 0, this.q.getData(), 0, 10);
            }
            extractorInput.peekFully(this.q.getData(), 10, readSynchSafeInt);
            Metadata decode = this.p.decode(this.q.getData(), readSynchSafeInt);
            if (decode == null) {
                return C.TIME_UNSET;
            }
            int length = decode.length();
            for (int i2 = 0; i2 < length; i2++) {
                Metadata.Entry entry = decode.get(i2);
                if (entry instanceof PrivFrame) {
                    PrivFrame privFrame = (PrivFrame) entry;
                    if ("com.apple.streaming.transportStreamTimestamp".equals(privFrame.owner)) {
                        System.arraycopy(privFrame.privateData, 0, this.q.getData(), 0, 8);
                        this.q.setPosition(0);
                        this.q.setLimit(8);
                        return this.q.readLong() & 8589934591L;
                    }
                }
            }
            return C.TIME_UNSET;
        } catch (EOFException unused) {
            return C.TIME_UNSET;
        }
    }

    private static byte[] a(String str) {
        if (Ascii.toLowerCase(str).startsWith("0x")) {
            str = str.substring(2);
        }
        byte[] byteArray = new BigInteger(str, 16).toByteArray();
        byte[] bArr = new byte[16];
        int length = byteArray.length > 16 ? byteArray.length - 16 : 0;
        System.arraycopy(byteArray, length, bArr, (bArr.length - byteArray.length) + length, byteArray.length - length);
        return bArr;
    }

    private static DataSource a(DataSource dataSource, @Nullable byte[] bArr, @Nullable byte[] bArr2) {
        if (bArr == null) {
            return dataSource;
        }
        Assertions.checkNotNull(bArr2);
        return new a(dataSource, bArr, bArr2);
    }

    private static boolean a(HlsChunkSource.d dVar, HlsMediaPlaylist hlsMediaPlaylist) {
        if (dVar.a instanceof HlsMediaPlaylist.Part) {
            return ((HlsMediaPlaylist.Part) dVar.a).isIndependent || (dVar.c == 0 && hlsMediaPlaylist.hasIndependentSegments);
        }
        return hlsMediaPlaylist.hasIndependentSegments;
    }
}
