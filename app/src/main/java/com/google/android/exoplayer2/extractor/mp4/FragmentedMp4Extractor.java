package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.CeaUtil;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp4.a;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Function;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/* loaded from: classes2.dex */
public class FragmentedMp4Extractor implements Extractor {
    public static final int FLAG_ENABLE_EMSG_TRACK = 4;
    public static final int FLAG_WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 16;
    public static final int FLAG_WORKAROUND_IGNORE_TFDT_BOX = 2;
    private long A;
    @Nullable
    private b B;
    private int C;
    private int D;
    private int E;
    private boolean F;
    private ExtractorOutput G;
    private TrackOutput[] H;
    private TrackOutput[] I;
    private boolean J;
    private final int c;
    @Nullable
    private final Track d;
    private final List<Format> e;
    private final SparseArray<b> f;
    private final ParsableByteArray g;
    private final ParsableByteArray h;
    private final ParsableByteArray i;
    private final byte[] j;
    private final ParsableByteArray k;
    @Nullable
    private final TimestampAdjuster l;
    private final EventMessageEncoder m;
    private final ParsableByteArray n;
    private final ArrayDeque<a.C0060a> o;
    private final ArrayDeque<a> p;
    @Nullable
    private final TrackOutput q;
    private int r;
    private int s;
    private long t;
    private int u;
    @Nullable
    private ParsableByteArray v;
    private long w;
    private int x;
    private long y;
    private long z;
    public static final ExtractorsFactory FACTORY = $$Lambda$FragmentedMp4Extractor$AZrpYc5LCOziWjdKPqv9yoNk.INSTANCE;
    private static final byte[] a = {-94, 57, 79, 82, 90, -101, 79, 20, -94, 68, 108, 66, 124, 100, -115, -12};
    private static final Format b = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_EMSG).build();

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    private static boolean b(int i) {
        return i == 1751411826 || i == 1835296868 || i == 1836476516 || i == 1936286840 || i == 1937011556 || i == 1937011827 || i == 1668576371 || i == 1937011555 || i == 1937011578 || i == 1937013298 || i == 1937007471 || i == 1668232756 || i == 1937011571 || i == 1952867444 || i == 1952868452 || i == 1953196132 || i == 1953654136 || i == 1953658222 || i == 1886614376 || i == 1935763834 || i == 1935763823 || i == 1936027235 || i == 1970628964 || i == 1935828848 || i == 1936158820 || i == 1701606260 || i == 1835362404 || i == 1701671783;
    }

    private static boolean c(int i) {
        return i == 1836019574 || i == 1953653099 || i == 1835297121 || i == 1835626086 || i == 1937007212 || i == 1836019558 || i == 1953653094 || i == 1836475768 || i == 1701082227;
    }

    @Nullable
    public Track modifyTrack(@Nullable Track track) {
        return track;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public static /* synthetic */ Extractor[] c() {
        return new Extractor[]{new FragmentedMp4Extractor()};
    }

    public FragmentedMp4Extractor() {
        this(0);
    }

    public FragmentedMp4Extractor(int i) {
        this(i, null);
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster) {
        this(i, timestampAdjuster, null, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track) {
        this(i, timestampAdjuster, track, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track, List<Format> list) {
        this(i, timestampAdjuster, track, list, null);
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track, List<Format> list, @Nullable TrackOutput trackOutput) {
        this.c = i;
        this.l = timestampAdjuster;
        this.d = track;
        this.e = Collections.unmodifiableList(list);
        this.q = trackOutput;
        this.m = new EventMessageEncoder();
        this.n = new ParsableByteArray(16);
        this.g = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.h = new ParsableByteArray(5);
        this.i = new ParsableByteArray();
        this.j = new byte[16];
        this.k = new ParsableByteArray(this.j);
        this.o = new ArrayDeque<>();
        this.p = new ArrayDeque<>();
        this.f = new SparseArray<>();
        this.z = C.TIME_UNSET;
        this.y = C.TIME_UNSET;
        this.A = C.TIME_UNSET;
        this.G = ExtractorOutput.PLACEHOLDER;
        this.H = new TrackOutput[0];
        this.I = new TrackOutput[0];
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return f.a(extractorInput);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.G = extractorOutput;
        a();
        b();
        Track track = this.d;
        if (track != null) {
            this.f.put(0, new b(extractorOutput.track(0, track.type), new h(this.d, new long[0], new int[0], 0, new long[0], new int[0], 0L), new c(0, 0, 0, 0)));
            this.G.endTracks();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        int size = this.f.size();
        for (int i = 0; i < size; i++) {
            this.f.valueAt(i).a();
        }
        this.p.clear();
        this.x = 0;
        this.y = j2;
        this.o.clear();
        a();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        while (true) {
            switch (this.r) {
                case 0:
                    if (a(extractorInput)) {
                        break;
                    } else {
                        return -1;
                    }
                case 1:
                    b(extractorInput);
                    break;
                case 2:
                    c(extractorInput);
                    break;
                default:
                    if (!d(extractorInput)) {
                        break;
                    } else {
                        return 0;
                    }
            }
        }
    }

    private void a() {
        this.r = 0;
        this.u = 0;
    }

    private boolean a(ExtractorInput extractorInput) throws IOException {
        if (this.u == 0) {
            if (!extractorInput.readFully(this.n.getData(), 0, 8, true)) {
                return false;
            }
            this.u = 8;
            this.n.setPosition(0);
            this.t = this.n.readUnsignedInt();
            this.s = this.n.readInt();
        }
        long j = this.t;
        if (j == 1) {
            extractorInput.readFully(this.n.getData(), 8, 8);
            this.u += 8;
            this.t = this.n.readUnsignedLongToLong();
        } else if (j == 0) {
            long length = extractorInput.getLength();
            if (length == -1 && !this.o.isEmpty()) {
                length = this.o.peek().b;
            }
            if (length != -1) {
                this.t = (length - extractorInput.getPosition()) + this.u;
            }
        }
        if (this.t >= this.u) {
            long position = extractorInput.getPosition() - this.u;
            int i = this.s;
            if ((i == 1836019558 || i == 1835295092) && !this.J) {
                this.G.seekMap(new SeekMap.Unseekable(this.z, position));
                this.J = true;
            }
            if (this.s == 1836019558) {
                int size = this.f.size();
                for (int i2 = 0; i2 < size; i2++) {
                    g gVar = this.f.valueAt(i2).b;
                    gVar.b = position;
                    gVar.d = position;
                    gVar.c = position;
                }
            }
            int i3 = this.s;
            if (i3 == 1835295092) {
                this.B = null;
                this.w = position + this.t;
                this.r = 2;
                return true;
            }
            if (c(i3)) {
                long position2 = (extractorInput.getPosition() + this.t) - 8;
                this.o.push(new a.C0060a(this.s, position2));
                if (this.t == this.u) {
                    a(position2);
                } else {
                    a();
                }
            } else if (b(this.s)) {
                if (this.u == 8) {
                    long j2 = this.t;
                    if (j2 <= 2147483647L) {
                        ParsableByteArray parsableByteArray = new ParsableByteArray((int) j2);
                        System.arraycopy(this.n.getData(), 0, parsableByteArray.getData(), 0, 8);
                        this.v = parsableByteArray;
                        this.r = 1;
                    } else {
                        throw ParserException.createForUnsupportedContainerFeature("Leaf atom with length > 2147483647 (unsupported).");
                    }
                } else {
                    throw ParserException.createForUnsupportedContainerFeature("Leaf atom defines extended atom size (unsupported).");
                }
            } else if (this.t <= 2147483647L) {
                this.v = null;
                this.r = 1;
            } else {
                throw ParserException.createForUnsupportedContainerFeature("Skipping atom with length > 2147483647 (unsupported).");
            }
            return true;
        }
        throw ParserException.createForUnsupportedContainerFeature("Atom size less than header length (unsupported).");
    }

    private void b(ExtractorInput extractorInput) throws IOException {
        int i = ((int) this.t) - this.u;
        ParsableByteArray parsableByteArray = this.v;
        if (parsableByteArray != null) {
            extractorInput.readFully(parsableByteArray.getData(), 8, i);
            a(new a.b(this.s, parsableByteArray), extractorInput.getPosition());
        } else {
            extractorInput.skipFully(i);
        }
        a(extractorInput.getPosition());
    }

    private void a(long j) throws ParserException {
        while (!this.o.isEmpty() && this.o.peek().b == j) {
            a(this.o.pop());
        }
        a();
    }

    private void a(a.b bVar, long j) throws ParserException {
        if (!this.o.isEmpty()) {
            this.o.peek().a(bVar);
        } else if (bVar.a == 1936286840) {
            Pair<Long, ChunkIndex> a2 = a(bVar.b, j);
            this.A = ((Long) a2.first).longValue();
            this.G.seekMap((SeekMap) a2.second);
            this.J = true;
        } else if (bVar.a == 1701671783) {
            a(bVar.b);
        }
    }

    private void a(a.C0060a aVar) throws ParserException {
        if (aVar.a == 1836019574) {
            b(aVar);
        } else if (aVar.a == 1836019558) {
            c(aVar);
        } else if (!this.o.isEmpty()) {
            this.o.peek().a(aVar);
        }
    }

    private void b(a.C0060a aVar) throws ParserException {
        boolean z = true;
        int i = 0;
        Assertions.checkState(this.d == null, "Unexpected moov box.");
        DrmInitData a2 = a(aVar.c);
        a.C0060a aVar2 = (a.C0060a) Assertions.checkNotNull(aVar.e(1836475768));
        SparseArray<c> sparseArray = new SparseArray<>();
        long j = C.TIME_UNSET;
        int size = aVar2.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            a.b bVar = aVar2.c.get(i2);
            if (bVar.a == 1953654136) {
                Pair<Integer, c> b2 = b(bVar.b);
                sparseArray.put(((Integer) b2.first).intValue(), (c) b2.second);
            } else if (bVar.a == 1835362404) {
                j = c(bVar.b);
            }
        }
        List<h> a3 = b.a(aVar, new GaplessInfoHolder(), j, a2, (this.c & 16) != 0, false, (Function<Track, Track>) new Function() { // from class: com.google.android.exoplayer2.extractor.mp4.-$$Lambda$fN3-4mToiYIj1aT5w0vidExaojw
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return FragmentedMp4Extractor.this.modifyTrack((Track) obj);
            }
        });
        int size2 = a3.size();
        if (this.f.size() == 0) {
            while (i < size2) {
                h hVar = a3.get(i);
                Track track = hVar.a;
                this.f.put(track.id, new b(this.G.track(i, track.type), hVar, a(sparseArray, track.id)));
                this.z = Math.max(this.z, track.durationUs);
                i++;
            }
            this.G.endTracks();
            return;
        }
        if (this.f.size() != size2) {
            z = false;
        }
        Assertions.checkState(z);
        while (i < size2) {
            h hVar2 = a3.get(i);
            Track track2 = hVar2.a;
            this.f.get(track2.id).a(hVar2, a(sparseArray, track2.id));
            i++;
        }
    }

    private c a(SparseArray<c> sparseArray, int i) {
        if (sparseArray.size() == 1) {
            return sparseArray.valueAt(0);
        }
        return (c) Assertions.checkNotNull(sparseArray.get(i));
    }

    private void c(a.C0060a aVar) throws ParserException {
        a(aVar, this.f, this.d != null, this.c, this.j);
        DrmInitData a2 = a(aVar.c);
        if (a2 != null) {
            int size = this.f.size();
            for (int i = 0; i < size; i++) {
                this.f.valueAt(i).a(a2);
            }
        }
        if (this.y != C.TIME_UNSET) {
            int size2 = this.f.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.f.valueAt(i2).a(this.y);
            }
            this.y = C.TIME_UNSET;
        }
    }

    private void b() {
        int i;
        this.H = new TrackOutput[2];
        TrackOutput trackOutput = this.q;
        if (trackOutput != null) {
            this.H[0] = trackOutput;
            i = 1;
        } else {
            i = 0;
        }
        int i2 = 100;
        if ((this.c & 4) != 0) {
            i++;
            i2 = 101;
            this.H[i] = this.G.track(100, 5);
        }
        this.H = (TrackOutput[]) Util.nullSafeArrayCopy(this.H, i);
        for (TrackOutput trackOutput2 : this.H) {
            trackOutput2.format(b);
        }
        this.I = new TrackOutput[this.e.size()];
        for (int i3 = 0; i3 < this.I.length; i3++) {
            i2++;
            TrackOutput track = this.G.track(i2, 3);
            track.format(this.e.get(i3));
            this.I[i3] = track;
        }
    }

    private void a(ParsableByteArray parsableByteArray) {
        long j;
        long j2;
        String str;
        String str2;
        long j3;
        long j4;
        if (this.H.length != 0) {
            parsableByteArray.setPosition(8);
            int a2 = a.a(parsableByteArray.readInt());
            switch (a2) {
                case 0:
                    str2 = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                    str = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                    long readUnsignedInt = parsableByteArray.readUnsignedInt();
                    j4 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000000L, readUnsignedInt);
                    long j5 = this.A;
                    j3 = j5 != C.TIME_UNSET ? j5 + j4 : -9223372036854775807L;
                    j2 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000L, readUnsignedInt);
                    j = parsableByteArray.readUnsignedInt();
                    break;
                case 1:
                    long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
                    j3 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedLongToLong(), 1000000L, readUnsignedInt2);
                    j2 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000L, readUnsignedInt2);
                    long readUnsignedInt3 = parsableByteArray.readUnsignedInt();
                    str2 = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                    str = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                    j = readUnsignedInt3;
                    j4 = -9223372036854775807L;
                    break;
                default:
                    StringBuilder sb = new StringBuilder(46);
                    sb.append("Skipping unsupported emsg version: ");
                    sb.append(a2);
                    Log.w("FragmentedMp4Extractor", sb.toString());
                    return;
            }
            byte[] bArr = new byte[parsableByteArray.bytesLeft()];
            parsableByteArray.readBytes(bArr, 0, parsableByteArray.bytesLeft());
            ParsableByteArray parsableByteArray2 = new ParsableByteArray(this.m.encode(new EventMessage(str2, str, j2, j, bArr)));
            int bytesLeft = parsableByteArray2.bytesLeft();
            TrackOutput[] trackOutputArr = this.H;
            for (TrackOutput trackOutput : trackOutputArr) {
                parsableByteArray2.setPosition(0);
                trackOutput.sampleData(parsableByteArray2, bytesLeft);
            }
            if (j3 == C.TIME_UNSET) {
                this.p.addLast(new a(j4, bytesLeft));
                this.x += bytesLeft;
                return;
            }
            TimestampAdjuster timestampAdjuster = this.l;
            if (timestampAdjuster != null) {
                j3 = timestampAdjuster.adjustSampleTimestamp(j3);
            }
            for (TrackOutput trackOutput2 : this.H) {
                trackOutput2.sampleMetadata(j3, 1, bytesLeft, 0, null);
            }
        }
    }

    private static Pair<Integer, c> b(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(12);
        return Pair.create(Integer.valueOf(parsableByteArray.readInt()), new c(parsableByteArray.readInt() - 1, parsableByteArray.readInt(), parsableByteArray.readInt(), parsableByteArray.readInt()));
    }

    private static long c(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return a.a(parsableByteArray.readInt()) == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
    }

    private static void a(a.C0060a aVar, SparseArray<b> sparseArray, boolean z, int i, byte[] bArr) throws ParserException {
        int size = aVar.d.size();
        for (int i2 = 0; i2 < size; i2++) {
            a.C0060a aVar2 = aVar.d.get(i2);
            if (aVar2.a == 1953653094) {
                b(aVar2, sparseArray, z, i, bArr);
            }
        }
    }

    private static void b(a.C0060a aVar, SparseArray<b> sparseArray, boolean z, int i, byte[] bArr) throws ParserException {
        b a2 = a(((a.b) Assertions.checkNotNull(aVar.d(1952868452))).b, sparseArray, z);
        if (a2 != null) {
            g gVar = a2.b;
            long j = gVar.r;
            boolean z2 = gVar.s;
            a2.a();
            a2.l = true;
            a.b d = aVar.d(1952867444);
            if (d == null || (i & 2) != 0) {
                gVar.r = j;
                gVar.s = z2;
            } else {
                gVar.r = d(d.b);
                gVar.s = true;
            }
            a(aVar, a2, i);
            TrackEncryptionBox sampleDescriptionEncryptionBox = a2.d.a.getSampleDescriptionEncryptionBox(((c) Assertions.checkNotNull(gVar.a)).a);
            a.b d2 = aVar.d(1935763834);
            if (d2 != null) {
                a((TrackEncryptionBox) Assertions.checkNotNull(sampleDescriptionEncryptionBox), d2.b, gVar);
            }
            a.b d3 = aVar.d(1935763823);
            if (d3 != null) {
                a(d3.b, gVar);
            }
            a.b d4 = aVar.d(1936027235);
            if (d4 != null) {
                b(d4.b, gVar);
            }
            a(aVar, sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null, gVar);
            int size = aVar.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                a.b bVar = aVar.c.get(i2);
                if (bVar.a == 1970628964) {
                    a(bVar.b, gVar, bArr);
                }
            }
        }
    }

    private static void a(a.C0060a aVar, b bVar, int i) throws ParserException {
        List<a.b> list = aVar.c;
        int size = list.size();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            a.b bVar2 = list.get(i4);
            if (bVar2.a == 1953658222) {
                ParsableByteArray parsableByteArray = bVar2.b;
                parsableByteArray.setPosition(12);
                int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                if (readUnsignedIntToInt > 0) {
                    i3 += readUnsignedIntToInt;
                    i2++;
                }
            }
        }
        bVar.h = 0;
        bVar.g = 0;
        bVar.f = 0;
        bVar.b.a(i2, i3);
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            a.b bVar3 = list.get(i7);
            if (bVar3.a == 1953658222) {
                i5++;
                i6 = a(bVar, i5, i, bVar3.b, i6);
            }
        }
    }

    private static void a(TrackEncryptionBox trackEncryptionBox, ParsableByteArray parsableByteArray, g gVar) throws ParserException {
        int i;
        int i2 = trackEncryptionBox.perSampleIvSize;
        parsableByteArray.setPosition(8);
        boolean z = true;
        if ((a.b(parsableByteArray.readInt()) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt <= gVar.f) {
            if (readUnsignedByte == 0) {
                boolean[] zArr = gVar.n;
                i = 0;
                for (int i3 = 0; i3 < readUnsignedIntToInt; i3++) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    i += readUnsignedByte2;
                    zArr[i3] = readUnsignedByte2 > i2;
                }
            } else {
                if (readUnsignedByte <= i2) {
                    z = false;
                }
                i = (readUnsignedByte * readUnsignedIntToInt) + 0;
                Arrays.fill(gVar.n, 0, readUnsignedIntToInt, z);
            }
            Arrays.fill(gVar.n, readUnsignedIntToInt, gVar.f, false);
            if (i > 0) {
                gVar.a(i);
                return;
            }
            return;
        }
        int i4 = gVar.f;
        StringBuilder sb = new StringBuilder(78);
        sb.append("Saiz sample count ");
        sb.append(readUnsignedIntToInt);
        sb.append(" is greater than fragment sample count");
        sb.append(i4);
        throw ParserException.createForMalformedContainer(sb.toString(), null);
    }

    private static void a(ParsableByteArray parsableByteArray, g gVar) throws ParserException {
        parsableByteArray.setPosition(8);
        int readInt = parsableByteArray.readInt();
        if ((a.b(readInt) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt == 1) {
            gVar.d += a.a(readInt) == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            return;
        }
        StringBuilder sb = new StringBuilder(40);
        sb.append("Unexpected saio entry count: ");
        sb.append(readUnsignedIntToInt);
        throw ParserException.createForMalformedContainer(sb.toString(), null);
    }

    @Nullable
    private static b a(ParsableByteArray parsableByteArray, SparseArray<b> sparseArray, boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        parsableByteArray.setPosition(8);
        int b2 = a.b(parsableByteArray.readInt());
        b valueAt = z ? sparseArray.valueAt(0) : sparseArray.get(parsableByteArray.readInt());
        if (valueAt == null) {
            return null;
        }
        if ((b2 & 1) != 0) {
            long readUnsignedLongToLong = parsableByteArray.readUnsignedLongToLong();
            valueAt.b.c = readUnsignedLongToLong;
            valueAt.b.d = readUnsignedLongToLong;
        }
        c cVar = valueAt.e;
        if ((b2 & 2) != 0) {
            i = parsableByteArray.readInt() - 1;
        } else {
            i = cVar.a;
        }
        if ((b2 & 8) != 0) {
            i2 = parsableByteArray.readInt();
        } else {
            i2 = cVar.b;
        }
        if ((b2 & 16) != 0) {
            i3 = parsableByteArray.readInt();
        } else {
            i3 = cVar.c;
        }
        if ((b2 & 32) != 0) {
            i4 = parsableByteArray.readInt();
        } else {
            i4 = cVar.d;
        }
        valueAt.b.a = new c(i, i2, i3, i4);
        return valueAt;
    }

    private static long d(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return a.a(parsableByteArray.readInt()) == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
    }

    private static int a(b bVar, int i, int i2, ParsableByteArray parsableByteArray, int i3) throws ParserException {
        int i4;
        b bVar2 = bVar;
        parsableByteArray.setPosition(8);
        int b2 = a.b(parsableByteArray.readInt());
        Track track = bVar2.d.a;
        g gVar = bVar2.b;
        c cVar = (c) Util.castNonNull(gVar.a);
        gVar.h[i] = parsableByteArray.readUnsignedIntToInt();
        gVar.g[i] = gVar.c;
        if ((b2 & 1) != 0) {
            long[] jArr = gVar.g;
            jArr[i] = jArr[i] + parsableByteArray.readInt();
        }
        boolean z = (b2 & 4) != 0;
        int i5 = cVar.d;
        if (z) {
            i5 = parsableByteArray.readInt();
        }
        boolean z2 = (b2 & 256) != 0;
        boolean z3 = (b2 & 512) != 0;
        boolean z4 = (b2 & 1024) != 0;
        boolean z5 = (b2 & 2048) != 0;
        long j = 0;
        if (track.editListDurations != null && track.editListDurations.length == 1 && track.editListDurations[0] == 0) {
            j = Util.scaleLargeTimestamp(((long[]) Util.castNonNull(track.editListMediaTimes))[0], 1000000L, track.timescale);
        }
        int[] iArr = gVar.i;
        int[] iArr2 = gVar.j;
        long[] jArr2 = gVar.k;
        boolean[] zArr = gVar.l;
        boolean z6 = track.type == 2 && (i2 & 1) != 0;
        int i6 = i3 + gVar.h[i];
        long j2 = track.timescale;
        long j3 = gVar.r;
        int i7 = i3;
        while (i7 < i6) {
            int a2 = a(z2 ? parsableByteArray.readInt() : cVar.b);
            if (z3) {
                z2 = z2;
                i4 = parsableByteArray.readInt();
            } else {
                z2 = z2;
                i4 = cVar.c;
            }
            int a3 = a(i4);
            if (z4) {
                z = z;
                i5 = parsableByteArray.readInt();
            } else if (i7 != 0 || !z) {
                z = z;
                i5 = cVar.d;
            } else {
                z = z;
            }
            if (z5) {
                z5 = z5;
                z3 = z3;
                z4 = z4;
                iArr2[i7] = (int) ((parsableByteArray.readInt() * 1000000) / j2);
            } else {
                z5 = z5;
                z3 = z3;
                z4 = z4;
                iArr2[i7] = 0;
            }
            jArr2[i7] = Util.scaleLargeTimestamp(j3, 1000000L, j2) - j;
            if (!gVar.s) {
                jArr2[i7] = jArr2[i7] + bVar2.d.h;
            }
            iArr[i7] = a3;
            zArr[i7] = ((i5 >> 16) & 1) == 0 && (!z6 || i7 == 0);
            j3 += a2;
            i7++;
            j2 = j2;
            bVar2 = bVar;
        }
        gVar.r = j3;
        return i6;
    }

    private static int a(int i) throws ParserException {
        if (i >= 0) {
            return i;
        }
        StringBuilder sb = new StringBuilder(38);
        sb.append("Unexpected negative value: ");
        sb.append(i);
        throw ParserException.createForMalformedContainer(sb.toString(), null);
    }

    private static void a(ParsableByteArray parsableByteArray, g gVar, byte[] bArr) throws ParserException {
        parsableByteArray.setPosition(8);
        parsableByteArray.readBytes(bArr, 0, 16);
        if (Arrays.equals(bArr, a)) {
            a(parsableByteArray, 16, gVar);
        }
    }

    private static void b(ParsableByteArray parsableByteArray, g gVar) throws ParserException {
        a(parsableByteArray, 0, gVar);
    }

    private static void a(ParsableByteArray parsableByteArray, int i, g gVar) throws ParserException {
        parsableByteArray.setPosition(i + 8);
        int b2 = a.b(parsableByteArray.readInt());
        if ((b2 & 1) == 0) {
            boolean z = (b2 & 2) != 0;
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (readUnsignedIntToInt == 0) {
                Arrays.fill(gVar.n, 0, gVar.f, false);
            } else if (readUnsignedIntToInt == gVar.f) {
                Arrays.fill(gVar.n, 0, readUnsignedIntToInt, z);
                gVar.a(parsableByteArray.bytesLeft());
                gVar.a(parsableByteArray);
            } else {
                int i2 = gVar.f;
                StringBuilder sb = new StringBuilder(80);
                sb.append("Senc sample count ");
                sb.append(readUnsignedIntToInt);
                sb.append(" is different from fragment sample count");
                sb.append(i2);
                throw ParserException.createForMalformedContainer(sb.toString(), null);
            }
        } else {
            throw ParserException.createForUnsupportedContainerFeature("Overriding TrackEncryptionBox parameters is unsupported.");
        }
    }

    private static void a(a.C0060a aVar, @Nullable String str, g gVar) throws ParserException {
        byte[] bArr;
        ParsableByteArray parsableByteArray = null;
        ParsableByteArray parsableByteArray2 = null;
        for (int i = 0; i < aVar.c.size(); i++) {
            a.b bVar = aVar.c.get(i);
            ParsableByteArray parsableByteArray3 = bVar.b;
            if (bVar.a == 1935828848) {
                parsableByteArray3.setPosition(12);
                if (parsableByteArray3.readInt() == 1936025959) {
                    parsableByteArray = parsableByteArray3;
                }
            } else if (bVar.a == 1936158820) {
                parsableByteArray3.setPosition(12);
                if (parsableByteArray3.readInt() == 1936025959) {
                    parsableByteArray2 = parsableByteArray3;
                }
            }
        }
        if (!(parsableByteArray == null || parsableByteArray2 == null)) {
            parsableByteArray.setPosition(8);
            int a2 = a.a(parsableByteArray.readInt());
            parsableByteArray.skipBytes(4);
            if (a2 == 1) {
                parsableByteArray.skipBytes(4);
            }
            if (parsableByteArray.readInt() == 1) {
                parsableByteArray2.setPosition(8);
                int a3 = a.a(parsableByteArray2.readInt());
                parsableByteArray2.skipBytes(4);
                if (a3 == 1) {
                    if (parsableByteArray2.readUnsignedInt() == 0) {
                        throw ParserException.createForUnsupportedContainerFeature("Variable length description in sgpd found (unsupported)");
                    }
                } else if (a3 >= 2) {
                    parsableByteArray2.skipBytes(4);
                }
                if (parsableByteArray2.readUnsignedInt() == 1) {
                    parsableByteArray2.skipBytes(1);
                    int readUnsignedByte = parsableByteArray2.readUnsignedByte();
                    int i2 = (readUnsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                    int i3 = readUnsignedByte & 15;
                    boolean z = parsableByteArray2.readUnsignedByte() == 1;
                    if (z) {
                        int readUnsignedByte2 = parsableByteArray2.readUnsignedByte();
                        byte[] bArr2 = new byte[16];
                        parsableByteArray2.readBytes(bArr2, 0, bArr2.length);
                        if (readUnsignedByte2 == 0) {
                            int readUnsignedByte3 = parsableByteArray2.readUnsignedByte();
                            byte[] bArr3 = new byte[readUnsignedByte3];
                            parsableByteArray2.readBytes(bArr3, 0, readUnsignedByte3);
                            bArr = bArr3;
                        } else {
                            bArr = null;
                        }
                        gVar.m = true;
                        gVar.o = new TrackEncryptionBox(z, str, readUnsignedByte2, bArr2, i2, i3, bArr);
                        return;
                    }
                    return;
                }
                throw ParserException.createForUnsupportedContainerFeature("Entry count in sgpd != 1 (unsupported).");
            }
            throw ParserException.createForUnsupportedContainerFeature("Entry count in sbgp != 1 (unsupported).");
        }
    }

    private static Pair<Long, ChunkIndex> a(ParsableByteArray parsableByteArray, long j) throws ParserException {
        long j2;
        long j3;
        parsableByteArray.setPosition(8);
        int a2 = a.a(parsableByteArray.readInt());
        parsableByteArray.skipBytes(4);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (a2 == 0) {
            long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
            j2 = j + parsableByteArray.readUnsignedInt();
            j3 = readUnsignedInt2;
        } else {
            long readUnsignedLongToLong = parsableByteArray.readUnsignedLongToLong();
            j2 = j + parsableByteArray.readUnsignedLongToLong();
            j3 = readUnsignedLongToLong;
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(j3, 1000000L, readUnsignedInt);
        parsableByteArray.skipBytes(2);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int[] iArr = new int[readUnsignedShort];
        long[] jArr = new long[readUnsignedShort];
        long[] jArr2 = new long[readUnsignedShort];
        long[] jArr3 = new long[readUnsignedShort];
        int i = 0;
        long j4 = j3;
        long j5 = scaleLargeTimestamp;
        while (i < readUnsignedShort) {
            int readInt = parsableByteArray.readInt();
            if ((readInt & Integer.MIN_VALUE) == 0) {
                long readUnsignedInt3 = parsableByteArray.readUnsignedInt();
                iArr[i] = readInt & Integer.MAX_VALUE;
                jArr[i] = j2;
                jArr3[i] = j5;
                j4 += readUnsignedInt3;
                j5 = Util.scaleLargeTimestamp(j4, 1000000L, readUnsignedInt);
                jArr2[i] = j5 - jArr3[i];
                parsableByteArray.skipBytes(4);
                j2 += iArr[i];
                i++;
                iArr = iArr;
                jArr3 = jArr3;
                jArr2 = jArr2;
                jArr = jArr;
                readUnsignedShort = readUnsignedShort;
            } else {
                throw ParserException.createForMalformedContainer("Unhandled indirect reference", null);
            }
        }
        return Pair.create(Long.valueOf(scaleLargeTimestamp), new ChunkIndex(iArr, jArr, jArr2, jArr3));
    }

    private void c(ExtractorInput extractorInput) throws IOException {
        int size = this.f.size();
        long j = Long.MAX_VALUE;
        b bVar = null;
        for (int i = 0; i < size; i++) {
            g gVar = this.f.valueAt(i).b;
            if (gVar.q && gVar.d < j) {
                long j2 = gVar.d;
                bVar = this.f.valueAt(i);
                j = j2;
            }
        }
        if (bVar == null) {
            this.r = 3;
            return;
        }
        int position = (int) (j - extractorInput.getPosition());
        if (position >= 0) {
            extractorInput.skipFully(position);
            bVar.b.a(extractorInput);
            return;
        }
        throw ParserException.createForMalformedContainer("Offset to encryption data was negative.", null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean d(ExtractorInput extractorInput) throws IOException {
        int i;
        int i2;
        b bVar = this.B;
        Throwable th = null;
        if (bVar == null) {
            bVar = a(this.f);
            if (bVar == null) {
                int position = (int) (this.w - extractorInput.getPosition());
                if (position >= 0) {
                    extractorInput.skipFully(position);
                    a();
                    return false;
                }
                throw ParserException.createForMalformedContainer("Offset to end of mdat was negative.", null);
            }
            int c = (int) (bVar.c() - extractorInput.getPosition());
            if (c < 0) {
                Log.w("FragmentedMp4Extractor", "Ignoring negative offset to sample data.");
                c = 0;
            }
            extractorInput.skipFully(c);
            this.B = bVar;
        }
        int i3 = 4;
        int i4 = 1;
        if (this.r == 3) {
            this.C = bVar.d();
            if (bVar.f < bVar.i) {
                extractorInput.skipFully(this.C);
                bVar.g();
                if (!bVar.f()) {
                    this.B = null;
                }
                this.r = 3;
                return true;
            }
            if (bVar.d.a.sampleTransformation == 1) {
                this.C -= 8;
                extractorInput.skipFully(8);
            }
            if (MimeTypes.AUDIO_AC4.equals(bVar.d.a.format.sampleMimeType)) {
                this.D = bVar.a(this.C, 7);
                Ac4Util.getAc4SampleHeader(this.C, this.k);
                bVar.a.sampleData(this.k, 7);
                this.D += 7;
            } else {
                this.D = bVar.a(this.C, 0);
            }
            this.C += this.D;
            this.r = 4;
            this.E = 0;
        }
        Track track = bVar.d.a;
        TrackOutput trackOutput = bVar.a;
        long b2 = bVar.b();
        TimestampAdjuster timestampAdjuster = this.l;
        long adjustSampleTimestamp = timestampAdjuster != null ? timestampAdjuster.adjustSampleTimestamp(b2) : b2;
        if (track.nalUnitLengthFieldLength == 0) {
            while (true) {
                int i5 = this.D;
                int i6 = this.C;
                if (i5 >= i6) {
                    break;
                }
                this.D += trackOutput.sampleData((DataReader) extractorInput, i6 - i5, false);
            }
        } else {
            byte[] data = this.h.getData();
            data[0] = 0;
            data[1] = 0;
            data[2] = 0;
            int i7 = track.nalUnitLengthFieldLength + 1;
            int i8 = 4 - track.nalUnitLengthFieldLength;
            while (this.D < this.C) {
                int i9 = this.E;
                if (i9 == 0) {
                    extractorInput.readFully(data, i8, i7);
                    this.h.setPosition(0);
                    int readInt = this.h.readInt();
                    if (readInt >= i4) {
                        this.E = readInt - 1;
                        this.g.setPosition(0);
                        trackOutput.sampleData(this.g, i3);
                        trackOutput.sampleData(this.h, i4);
                        this.F = (this.I.length <= 0 || !NalUnitUtil.isNalUnitSei(track.format.sampleMimeType, data[i3])) ? 0 : i4;
                        this.D += 5;
                        this.C += i8;
                    } else {
                        throw ParserException.createForMalformedContainer("Invalid NAL length", th);
                    }
                } else {
                    if (this.F) {
                        this.i.reset(i9);
                        extractorInput.readFully(this.i.getData(), 0, this.E);
                        trackOutput.sampleData(this.i, this.E);
                        i2 = this.E;
                        int unescapeStream = NalUnitUtil.unescapeStream(this.i.getData(), this.i.limit());
                        this.i.setPosition(MimeTypes.VIDEO_H265.equals(track.format.sampleMimeType) ? 1 : 0);
                        this.i.setLimit(unescapeStream);
                        CeaUtil.consume(adjustSampleTimestamp, this.i, this.I);
                    } else {
                        i2 = trackOutput.sampleData((DataReader) extractorInput, i9, false);
                    }
                    this.D += i2;
                    this.E -= i2;
                    th = null;
                    i3 = 4;
                    i4 = 1;
                }
            }
        }
        int e = bVar.e();
        TrackEncryptionBox h = bVar.h();
        trackOutput.sampleMetadata(adjustSampleTimestamp, e, this.C, 0, h != null ? h.cryptoData : null);
        b(adjustSampleTimestamp);
        if (!bVar.f()) {
            this.B = null;
            i = 3;
        } else {
            i = 3;
        }
        this.r = i;
        return true;
    }

    private void b(long j) {
        while (!this.p.isEmpty()) {
            a removeFirst = this.p.removeFirst();
            this.x -= removeFirst.b;
            long j2 = removeFirst.a + j;
            TimestampAdjuster timestampAdjuster = this.l;
            if (timestampAdjuster != null) {
                j2 = timestampAdjuster.adjustSampleTimestamp(j2);
            }
            for (TrackOutput trackOutput : this.H) {
                trackOutput.sampleMetadata(j2, 1, removeFirst.b, this.x, null);
            }
        }
    }

    @Nullable
    private static b a(SparseArray<b> sparseArray) {
        int size = sparseArray.size();
        b bVar = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            b valueAt = sparseArray.valueAt(i);
            if ((valueAt.l || valueAt.f != valueAt.d.b) && (!valueAt.l || valueAt.h != valueAt.b.e)) {
                long c = valueAt.c();
                if (c < j) {
                    bVar = valueAt;
                    j = c;
                }
            }
        }
        return bVar;
    }

    @Nullable
    private static DrmInitData a(List<a.b> list) {
        int size = list.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            a.b bVar = list.get(i);
            if (bVar.a == 1886614376) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                byte[] data = bVar.b.getData();
                UUID parseUuid = PsshAtomUtil.parseUuid(data);
                if (parseUuid == null) {
                    Log.w("FragmentedMp4Extractor", "Skipped pssh atom (failed to extract uuid)");
                } else {
                    arrayList.add(new DrmInitData.SchemeData(parseUuid, "video/mp4", data));
                }
            }
        }
        if (arrayList == null) {
            return null;
        }
        return new DrmInitData(arrayList);
    }

    /* loaded from: classes2.dex */
    public static final class a {
        public final long a;
        public final int b;

        public a(long j, int i) {
            this.a = j;
            this.b = i;
        }
    }

    /* loaded from: classes2.dex */
    public static final class b {
        public final TrackOutput a;
        public h d;
        public c e;
        public int f;
        public int g;
        public int h;
        public int i;
        private boolean l;
        public final g b = new g();
        public final ParsableByteArray c = new ParsableByteArray();
        private final ParsableByteArray j = new ParsableByteArray(1);
        private final ParsableByteArray k = new ParsableByteArray();

        public b(TrackOutput trackOutput, h hVar, c cVar) {
            this.a = trackOutput;
            this.d = hVar;
            this.e = cVar;
            a(hVar, cVar);
        }

        public void a(h hVar, c cVar) {
            this.d = hVar;
            this.e = cVar;
            this.a.format(hVar.a.format);
            a();
        }

        public void a(DrmInitData drmInitData) {
            TrackEncryptionBox sampleDescriptionEncryptionBox = this.d.a.getSampleDescriptionEncryptionBox(((c) Util.castNonNull(this.b.a)).a);
            this.a.format(this.d.a.format.buildUpon().setDrmInitData(drmInitData.copyWithSchemeType(sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null)).build());
        }

        public void a() {
            this.b.a();
            this.f = 0;
            this.h = 0;
            this.g = 0;
            this.i = 0;
            this.l = false;
        }

        public void a(long j) {
            for (int i = this.f; i < this.b.f && this.b.b(i) < j; i++) {
                if (this.b.l[i]) {
                    this.i = i;
                }
            }
        }

        public long b() {
            if (!this.l) {
                return this.d.f[this.f];
            }
            return this.b.b(this.f);
        }

        public long c() {
            if (!this.l) {
                return this.d.c[this.f];
            }
            return this.b.g[this.h];
        }

        public int d() {
            if (!this.l) {
                return this.d.d[this.f];
            }
            return this.b.i[this.f];
        }

        public int e() {
            int i;
            if (!this.l) {
                i = this.d.g[this.f];
            } else {
                i = this.b.l[this.f] ? 1 : 0;
            }
            return h() != null ? i | 1073741824 : i;
        }

        public boolean f() {
            this.f++;
            if (!this.l) {
                return false;
            }
            this.g++;
            int i = this.g;
            int[] iArr = this.b.h;
            int i2 = this.h;
            if (i != iArr[i2]) {
                return true;
            }
            this.h = i2 + 1;
            this.g = 0;
            return false;
        }

        public int a(int i, int i2) {
            ParsableByteArray parsableByteArray;
            int i3;
            TrackEncryptionBox h = h();
            if (h == null) {
                return 0;
            }
            if (h.perSampleIvSize != 0) {
                parsableByteArray = this.b.p;
                i3 = h.perSampleIvSize;
            } else {
                byte[] bArr = (byte[]) Util.castNonNull(h.defaultInitializationVector);
                this.k.reset(bArr, bArr.length);
                parsableByteArray = this.k;
                i3 = bArr.length;
            }
            boolean c = this.b.c(this.f);
            boolean z = c || i2 != 0;
            this.j.getData()[0] = (byte) ((z ? 128 : 0) | i3);
            this.j.setPosition(0);
            this.a.sampleData(this.j, 1, 1);
            this.a.sampleData(parsableByteArray, i3, 1);
            if (!z) {
                return i3 + 1;
            }
            if (!c) {
                this.c.reset(8);
                byte[] data = this.c.getData();
                data[0] = 0;
                data[1] = 1;
                data[2] = (byte) ((i2 >> 8) & 255);
                data[3] = (byte) (i2 & 255);
                data[4] = (byte) ((i >> 24) & 255);
                data[5] = (byte) ((i >> 16) & 255);
                data[6] = (byte) ((i >> 8) & 255);
                data[7] = (byte) (i & 255);
                this.a.sampleData(this.c, 8, 1);
                return i3 + 1 + 8;
            }
            ParsableByteArray parsableByteArray2 = this.b.p;
            int readUnsignedShort = parsableByteArray2.readUnsignedShort();
            parsableByteArray2.skipBytes(-2);
            int i4 = (readUnsignedShort * 6) + 2;
            if (i2 != 0) {
                this.c.reset(i4);
                byte[] data2 = this.c.getData();
                parsableByteArray2.readBytes(data2, 0, i4);
                int i5 = (((data2[2] & 255) << 8) | (data2[3] & 255)) + i2;
                data2[2] = (byte) ((i5 >> 8) & 255);
                data2[3] = (byte) (i5 & 255);
                parsableByteArray2 = this.c;
            }
            this.a.sampleData(parsableByteArray2, i4, 1);
            return i3 + 1 + i4;
        }

        public void g() {
            TrackEncryptionBox h = h();
            if (h != null) {
                ParsableByteArray parsableByteArray = this.b.p;
                if (h.perSampleIvSize != 0) {
                    parsableByteArray.skipBytes(h.perSampleIvSize);
                }
                if (this.b.c(this.f)) {
                    parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort() * 6);
                }
            }
        }

        @Nullable
        public TrackEncryptionBox h() {
            TrackEncryptionBox trackEncryptionBox;
            if (!this.l) {
                return null;
            }
            int i = ((c) Util.castNonNull(this.b.a)).a;
            if (this.b.o != null) {
                trackEncryptionBox = this.b.o;
            } else {
                trackEncryptionBox = this.d.a.getSampleDescriptionEncryptionBox(i);
            }
            if (trackEncryptionBox == null || !trackEncryptionBox.isEncrypted) {
                return null;
            }
            return trackEncryptionBox;
        }
    }
}
