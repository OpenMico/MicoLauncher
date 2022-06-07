package com.google.android.exoplayer2.extractor.mp4;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp4.a;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class Mp4Extractor implements Extractor, SeekMap {
    public static final ExtractorsFactory FACTORY = $$Lambda$Mp4Extractor$mVr8rUBlOIltBrg35QFplxx9w8.INSTANCE;
    public static final int FLAG_READ_MOTION_PHOTO_METADATA = 2;
    public static final int FLAG_READ_SEF_DATA = 4;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 1;
    private final int a;
    private final ParsableByteArray b;
    private final ParsableByteArray c;
    private final ParsableByteArray d;
    private final ParsableByteArray e;
    private final ArrayDeque<a.C0060a> f;
    private final e g;
    private final List<Metadata.Entry> h;
    private int i;
    private int j;
    private long k;
    private int l;
    @Nullable
    private ParsableByteArray m;
    private int n;
    private int o;
    private int p;
    private int q;
    private ExtractorOutput r;
    private a[] s;
    private long[][] t;
    private int u;
    private long v;
    private int w;
    @Nullable
    private MotionPhotoMetadata x;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    private static int a(int i) {
        if (i != 1751476579) {
            return i != 1903435808 ? 0 : 1;
        }
        return 2;
    }

    public static /* synthetic */ Track a(Track track) {
        return track;
    }

    private static boolean b(int i) {
        return i == 1835296868 || i == 1836476516 || i == 1751411826 || i == 1937011556 || i == 1937011827 || i == 1937011571 || i == 1668576371 || i == 1701606260 || i == 1937011555 || i == 1937011578 || i == 1937013298 || i == 1937007471 || i == 1668232756 || i == 1953196132 || i == 1718909296 || i == 1969517665 || i == 1801812339 || i == 1768715124;
    }

    private static boolean c(int i) {
        return i == 1836019574 || i == 1953653099 || i == 1835297121 || i == 1835626086 || i == 1937007212 || i == 1701082227 || i == 1835365473;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public static /* synthetic */ Extractor[] c() {
        return new Extractor[]{new Mp4Extractor()};
    }

    public Mp4Extractor() {
        this(0);
    }

    public Mp4Extractor(int i) {
        this.a = i;
        this.i = (i & 4) != 0 ? 3 : 0;
        this.g = new e();
        this.h = new ArrayList();
        this.e = new ParsableByteArray(16);
        this.f = new ArrayDeque<>();
        this.b = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.c = new ParsableByteArray(4);
        this.d = new ParsableByteArray();
        this.n = -1;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return f.a(extractorInput, (this.a & 2) != 0);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.r = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        this.f.clear();
        this.l = 0;
        this.n = -1;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        if (j == 0) {
            if (this.i != 3) {
                a();
                return;
            }
            this.g.a();
            this.h.clear();
        } else if (this.s != null) {
            c(j2);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        while (true) {
            switch (this.i) {
                case 0:
                    if (a(extractorInput)) {
                        break;
                    } else {
                        return -1;
                    }
                case 1:
                    if (!a(extractorInput, positionHolder)) {
                        break;
                    } else {
                        return 1;
                    }
                case 2:
                    return c(extractorInput, positionHolder);
                case 3:
                    return b(extractorInput, positionHolder);
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.v;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        long j2;
        long j3;
        long j4;
        int b;
        if (((a[]) Assertions.checkNotNull(this.s)).length == 0) {
            return new SeekMap.SeekPoints(SeekPoint.START);
        }
        int i = this.u;
        if (i != -1) {
            h hVar = this.s[i].b;
            int a2 = a(hVar, j);
            if (a2 == -1) {
                return new SeekMap.SeekPoints(SeekPoint.START);
            }
            long j5 = hVar.f[a2];
            j2 = hVar.c[a2];
            if (j5 >= j || a2 >= hVar.b - 1 || (b = hVar.b(j)) == -1 || b == a2) {
                j3 = -1;
                j4 = -9223372036854775807L;
            } else {
                j4 = hVar.f[b];
                j3 = hVar.c[b];
            }
            j = j5;
        } else {
            j2 = Long.MAX_VALUE;
            j3 = -1;
            j4 = -9223372036854775807L;
        }
        int i2 = 0;
        while (true) {
            a[] aVarArr = this.s;
            if (i2 >= aVarArr.length) {
                break;
            }
            if (i2 != this.u) {
                h hVar2 = aVarArr[i2].b;
                long a3 = a(hVar2, j, j2);
                if (j4 != C.TIME_UNSET) {
                    j3 = a(hVar2, j4, j3);
                    j2 = a3;
                } else {
                    j2 = a3;
                }
            }
            i2++;
        }
        SeekPoint seekPoint = new SeekPoint(j, j2);
        if (j4 == C.TIME_UNSET) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(j4, j3));
    }

    private void a() {
        this.i = 0;
        this.l = 0;
    }

    private boolean a(ExtractorInput extractorInput) throws IOException {
        a.C0060a peek;
        if (this.l == 0) {
            if (!extractorInput.readFully(this.e.getData(), 0, 8, true)) {
                b();
                return false;
            }
            this.l = 8;
            this.e.setPosition(0);
            this.k = this.e.readUnsignedInt();
            this.j = this.e.readInt();
        }
        long j = this.k;
        if (j == 1) {
            extractorInput.readFully(this.e.getData(), 8, 8);
            this.l += 8;
            this.k = this.e.readUnsignedLongToLong();
        } else if (j == 0) {
            long length = extractorInput.getLength();
            if (length == -1 && (peek = this.f.peek()) != null) {
                length = peek.b;
            }
            if (length != -1) {
                this.k = (length - extractorInput.getPosition()) + this.l;
            }
        }
        if (this.k >= this.l) {
            if (c(this.j)) {
                long position = extractorInput.getPosition();
                long j2 = this.k;
                int i = this.l;
                long j3 = (position + j2) - i;
                if (j2 != i && this.j == 1835365473) {
                    b(extractorInput);
                }
                this.f.push(new a.C0060a(this.j, j3));
                if (this.k == this.l) {
                    a(j3);
                } else {
                    a();
                }
            } else if (b(this.j)) {
                Assertions.checkState(this.l == 8);
                Assertions.checkState(this.k <= 2147483647L);
                ParsableByteArray parsableByteArray = new ParsableByteArray((int) this.k);
                System.arraycopy(this.e.getData(), 0, parsableByteArray.getData(), 0, 8);
                this.m = parsableByteArray;
                this.i = 1;
            } else {
                d(extractorInput.getPosition() - this.l);
                this.m = null;
                this.i = 1;
            }
            return true;
        }
        throw ParserException.createForUnsupportedContainerFeature("Atom size less than header length (unsupported).");
    }

    private boolean a(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        long j = this.k - this.l;
        long position = extractorInput.getPosition() + j;
        ParsableByteArray parsableByteArray = this.m;
        if (parsableByteArray != null) {
            extractorInput.readFully(parsableByteArray.getData(), this.l, (int) j);
            if (this.j == 1718909296) {
                this.w = a(parsableByteArray);
            } else if (!this.f.isEmpty()) {
                this.f.peek().a(new a.b(this.j, parsableByteArray));
            }
        } else if (j < PlaybackStateCompat.ACTION_SET_REPEAT_MODE) {
            extractorInput.skipFully((int) j);
        } else {
            positionHolder.position = extractorInput.getPosition() + j;
            z = true;
            a(position);
            return !z && this.i != 2;
        }
        z = false;
        a(position);
        if (!z) {
        }
    }

    private int b(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int a2 = this.g.a(extractorInput, positionHolder, this.h);
        if (a2 == 1 && positionHolder.position == 0) {
            a();
        }
        return a2;
    }

    private void a(long j) throws ParserException {
        while (!this.f.isEmpty() && this.f.peek().b == j) {
            a.C0060a pop = this.f.pop();
            if (pop.a == 1836019574) {
                a(pop);
                this.f.clear();
                this.i = 2;
            } else if (!this.f.isEmpty()) {
                this.f.peek().a(pop);
            }
        }
        if (this.i != 2) {
            a();
        }
    }

    private void a(a.C0060a aVar) throws ParserException {
        Metadata metadata;
        Metadata metadata2;
        ArrayList arrayList = new ArrayList();
        boolean z = this.w == 1;
        GaplessInfoHolder gaplessInfoHolder = new GaplessInfoHolder();
        a.b d = aVar.d(1969517665);
        if (d != null) {
            Pair<Metadata, Metadata> a2 = b.a(d);
            Metadata metadata3 = (Metadata) a2.first;
            metadata = (Metadata) a2.second;
            if (metadata3 != null) {
                gaplessInfoHolder.setFromMetadata(metadata3);
            }
            metadata2 = metadata3;
        } else {
            metadata2 = null;
            metadata = null;
        }
        a.C0060a e = aVar.e(1835365473);
        Metadata a3 = e != null ? b.a(e) : null;
        List<h> a4 = b.a(aVar, gaplessInfoHolder, (long) C.TIME_UNSET, (DrmInitData) null, (this.a & 1) != 0, z, $$Lambda$Mp4Extractor$VIxMNVANOgK0XmpPyEJqxs3ihA.INSTANCE);
        ExtractorOutput extractorOutput = (ExtractorOutput) Assertions.checkNotNull(this.r);
        int size = a4.size();
        int i = -1;
        long j = C.TIME_UNSET;
        for (int i2 = 0; i2 < size; i2++) {
            h hVar = a4.get(i2);
            if (hVar.b == 0) {
                a4 = a4;
                size = size;
                arrayList = arrayList;
            } else {
                Track track = hVar.a;
                arrayList = arrayList;
                long j2 = track.durationUs != C.TIME_UNSET ? track.durationUs : hVar.h;
                long max = Math.max(j, j2);
                a4 = a4;
                size = size;
                a aVar2 = new a(track, hVar, extractorOutput.track(i2, track.type));
                Format.Builder buildUpon = track.format.buildUpon();
                buildUpon.setMaxInputSize(hVar.e + 30);
                if (track.type == 2 && j2 > 0 && hVar.b > 1) {
                    buildUpon.setFrameRate(hVar.b / (((float) j2) / 1000000.0f));
                }
                d.a(track.type, gaplessInfoHolder, buildUpon);
                int i3 = track.type;
                Metadata[] metadataArr = new Metadata[2];
                metadataArr[0] = metadata;
                metadataArr[1] = this.h.isEmpty() ? null : new Metadata(this.h);
                d.a(i3, metadata2, a3, buildUpon, metadataArr);
                aVar2.c.format(buildUpon.build());
                if (track.type == 2) {
                    i = i;
                    if (i == -1) {
                        i = arrayList.size();
                    }
                } else {
                    i = i;
                }
                arrayList.add(aVar2);
                j = max;
            }
        }
        this.u = i;
        this.v = j;
        this.s = (a[]) arrayList.toArray(new a[0]);
        this.t = a(this.s);
        extractorOutput.endTracks();
        extractorOutput.seekMap(this);
    }

    private int c(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int i;
        long position = extractorInput.getPosition();
        if (this.n == -1) {
            this.n = b(position);
            if (this.n == -1) {
                return -1;
            }
        }
        a aVar = ((a[]) Util.castNonNull(this.s))[this.n];
        TrackOutput trackOutput = aVar.c;
        int i2 = aVar.d;
        long j = aVar.b.c[i2];
        int i3 = aVar.b.d[i2];
        long j2 = (j - position) + this.o;
        if (j2 < 0 || j2 >= PlaybackStateCompat.ACTION_SET_REPEAT_MODE) {
            positionHolder.position = j;
            return 1;
        }
        if (aVar.a.sampleTransformation == 1) {
            j2 += 8;
            i3 -= 8;
        }
        extractorInput.skipFully((int) j2);
        if (aVar.a.nalUnitLengthFieldLength != 0) {
            byte[] data = this.c.getData();
            data[0] = 0;
            data[1] = 0;
            data[2] = 0;
            int i4 = aVar.a.nalUnitLengthFieldLength;
            int i5 = 4 - aVar.a.nalUnitLengthFieldLength;
            while (this.p < i3) {
                int i6 = this.q;
                if (i6 == 0) {
                    extractorInput.readFully(data, i5, i4);
                    this.o += i4;
                    this.c.setPosition(0);
                    int readInt = this.c.readInt();
                    if (readInt >= 0) {
                        this.q = readInt;
                        this.b.setPosition(0);
                        trackOutput.sampleData(this.b, 4);
                        this.p += 4;
                        i3 += i5;
                    } else {
                        throw ParserException.createForMalformedContainer("Invalid NAL length", null);
                    }
                } else {
                    int sampleData = trackOutput.sampleData((DataReader) extractorInput, i6, false);
                    this.o += sampleData;
                    this.p += sampleData;
                    this.q -= sampleData;
                }
            }
            i = i3;
        } else {
            if (MimeTypes.AUDIO_AC4.equals(aVar.a.format.sampleMimeType)) {
                if (this.p == 0) {
                    Ac4Util.getAc4SampleHeader(i3, this.d);
                    trackOutput.sampleData(this.d, 7);
                    this.p += 7;
                }
                i3 += 7;
            }
            while (true) {
                int i7 = this.p;
                if (i7 >= i3) {
                    break;
                }
                int sampleData2 = trackOutput.sampleData((DataReader) extractorInput, i3 - i7, false);
                this.o += sampleData2;
                this.p += sampleData2;
                this.q -= sampleData2;
            }
            i = i3;
        }
        trackOutput.sampleMetadata(aVar.b.f[i2], aVar.b.g[i2], i, 0, null);
        aVar.d++;
        this.n = -1;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        return 0;
    }

    private int b(long j) {
        int i = -1;
        int i2 = -1;
        long j2 = Long.MAX_VALUE;
        boolean z = true;
        long j3 = Long.MAX_VALUE;
        boolean z2 = true;
        long j4 = Long.MAX_VALUE;
        for (int i3 = 0; i3 < ((a[]) Util.castNonNull(this.s)).length; i3++) {
            a aVar = this.s[i3];
            int i4 = aVar.d;
            if (i4 != aVar.b.b) {
                long j5 = aVar.b.c[i4];
                long j6 = ((long[][]) Util.castNonNull(this.t))[i3][i4];
                long j7 = j5 - j;
                boolean z3 = j7 < 0 || j7 >= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
                if ((!z3 && z2) || (z3 == z2 && j7 < j4)) {
                    z2 = z3;
                    i2 = i3;
                    j4 = j7;
                    j3 = j6;
                }
                if (j6 < j2) {
                    z = z3;
                    i = i3;
                    j2 = j6;
                }
            }
        }
        return (j2 == Long.MAX_VALUE || !z || j3 < j2 + 10485760) ? i2 : i;
    }

    @RequiresNonNull({"tracks"})
    private void c(long j) {
        a[] aVarArr = this.s;
        for (a aVar : aVarArr) {
            h hVar = aVar.b;
            int a2 = hVar.a(j);
            if (a2 == -1) {
                a2 = hVar.b(j);
            }
            aVar.d = a2;
        }
    }

    private void b() {
        if (this.w == 2 && (this.a & 2) != 0) {
            ExtractorOutput extractorOutput = (ExtractorOutput) Assertions.checkNotNull(this.r);
            TrackOutput track = extractorOutput.track(0, 4);
            MotionPhotoMetadata motionPhotoMetadata = this.x;
            track.format(new Format.Builder().setMetadata(motionPhotoMetadata == null ? null : new Metadata(motionPhotoMetadata)).build());
            extractorOutput.endTracks();
            extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
        }
    }

    private void b(ExtractorInput extractorInput) throws IOException {
        this.d.reset(8);
        extractorInput.peekFully(this.d.getData(), 0, 8);
        b.a(this.d);
        extractorInput.skipFully(this.d.getPosition());
        extractorInput.resetPeekPosition();
    }

    private void d(long j) {
        if (this.j == 1836086884) {
            int i = this.l;
            this.x = new MotionPhotoMetadata(0L, j, C.TIME_UNSET, j + i, this.k - i);
        }
    }

    private static long[][] a(a[] aVarArr) {
        long[][] jArr = new long[aVarArr.length];
        int[] iArr = new int[aVarArr.length];
        long[] jArr2 = new long[aVarArr.length];
        boolean[] zArr = new boolean[aVarArr.length];
        for (int i = 0; i < aVarArr.length; i++) {
            jArr[i] = new long[aVarArr[i].b.b];
            jArr2[i] = aVarArr[i].b.f[0];
        }
        long j = 0;
        int i2 = 0;
        while (i2 < aVarArr.length) {
            long j2 = Long.MAX_VALUE;
            int i3 = -1;
            for (int i4 = 0; i4 < aVarArr.length; i4++) {
                if (!zArr[i4] && jArr2[i4] <= j2) {
                    j2 = jArr2[i4];
                    i3 = i4;
                }
            }
            int i5 = iArr[i3];
            jArr[i3][i5] = j;
            j += aVarArr[i3].b.d[i5];
            int i6 = i5 + 1;
            iArr[i3] = i6;
            if (i6 < jArr[i3].length) {
                jArr2[i3] = aVarArr[i3].b.f[i6];
            } else {
                zArr[i3] = true;
                i2++;
            }
        }
        return jArr;
    }

    private static long a(h hVar, long j, long j2) {
        int a2 = a(hVar, j);
        return a2 == -1 ? j2 : Math.min(hVar.c[a2], j2);
    }

    private static int a(h hVar, long j) {
        int a2 = hVar.a(j);
        return a2 == -1 ? hVar.b(j) : a2;
    }

    private static int a(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        int a2 = a(parsableByteArray.readInt());
        if (a2 != 0) {
            return a2;
        }
        parsableByteArray.skipBytes(4);
        while (parsableByteArray.bytesLeft() > 0) {
            int a3 = a(parsableByteArray.readInt());
            if (a3 != 0) {
                return a3;
            }
        }
        return 0;
    }

    /* loaded from: classes2.dex */
    public static final class a {
        public final Track a;
        public final h b;
        public final TrackOutput c;
        public int d;

        public a(Track track, h hVar, TrackOutput trackOutput) {
            this.a = track;
            this.b = hVar;
            this.c = trackOutput;
        }
    }
}
