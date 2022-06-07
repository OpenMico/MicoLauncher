package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class H264Reader implements ElementaryStreamReader {
    private final SeiReader a;
    private final boolean b;
    private final boolean c;
    private long g;
    private String i;
    private TrackOutput j;
    private a k;
    private boolean l;
    private boolean n;
    private final boolean[] h = new boolean[3];
    private final a d = new a(7, 128);
    private final a e = new a(8, 128);
    private final a f = new a(6, 128);
    private long m = C.TIME_UNSET;
    private final ParsableByteArray o = new ParsableByteArray();

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public H264Reader(SeiReader seiReader, boolean z, boolean z2) {
        this.a = seiReader;
        this.b = z;
        this.c = z2;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.g = 0L;
        this.n = false;
        this.m = C.TIME_UNSET;
        NalUnitUtil.clearPrefixFlags(this.h);
        this.d.a();
        this.e.a();
        this.f.a();
        a aVar = this.k;
        if (aVar != null) {
            aVar.b();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.i = trackIdGenerator.getFormatId();
        this.j = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        this.k = new a(this.j, this.b, this.c);
        this.a.createTracks(extractorOutput, trackIdGenerator);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.m = j;
        }
        this.n |= (i & 2) != 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        a();
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        byte[] data = parsableByteArray.getData();
        this.g += parsableByteArray.bytesLeft();
        this.j.sampleData(parsableByteArray, parsableByteArray.bytesLeft());
        while (true) {
            int findNalUnit = NalUnitUtil.findNalUnit(data, position, limit, this.h);
            if (findNalUnit == limit) {
                a(data, position, limit);
                return;
            }
            int nalUnitType = NalUnitUtil.getNalUnitType(data, findNalUnit);
            int i = findNalUnit - position;
            if (i > 0) {
                a(data, position, findNalUnit);
            }
            int i2 = limit - findNalUnit;
            long j = this.g - i2;
            a(j, i2, i < 0 ? -i : 0, this.m);
            a(j, nalUnitType, this.m);
            position = findNalUnit + 3;
        }
    }

    @RequiresNonNull({"sampleReader"})
    private void a(long j, int i, long j2) {
        if (!this.l || this.k.a()) {
            this.d.a(i);
            this.e.a(i);
        }
        this.f.a(i);
        this.k.a(j, i, j2);
    }

    @RequiresNonNull({"sampleReader"})
    private void a(byte[] bArr, int i, int i2) {
        if (!this.l || this.k.a()) {
            this.d.a(bArr, i, i2);
            this.e.a(bArr, i, i2);
        }
        this.f.a(bArr, i, i2);
        this.k.a(bArr, i, i2);
    }

    @RequiresNonNull({"output", "sampleReader"})
    private void a(long j, int i, int i2, long j2) {
        if (!this.l || this.k.a()) {
            this.d.b(i2);
            this.e.b(i2);
            if (!this.l) {
                if (this.d.b() && this.e.b()) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Arrays.copyOf(this.d.a, this.d.b));
                    arrayList.add(Arrays.copyOf(this.e.a, this.e.b));
                    NalUnitUtil.SpsData parseSpsNalUnit = NalUnitUtil.parseSpsNalUnit(this.d.a, 3, this.d.b);
                    NalUnitUtil.PpsData parsePpsNalUnit = NalUnitUtil.parsePpsNalUnit(this.e.a, 3, this.e.b);
                    this.j.format(new Format.Builder().setId(this.i).setSampleMimeType(MimeTypes.VIDEO_H264).setCodecs(CodecSpecificDataUtil.buildAvcCodecString(parseSpsNalUnit.profileIdc, parseSpsNalUnit.constraintsFlagsAndReservedZero2Bits, parseSpsNalUnit.levelIdc)).setWidth(parseSpsNalUnit.width).setHeight(parseSpsNalUnit.height).setPixelWidthHeightRatio(parseSpsNalUnit.pixelWidthAspectRatio).setInitializationData(arrayList).build());
                    this.l = true;
                    this.k.a(parseSpsNalUnit);
                    this.k.a(parsePpsNalUnit);
                    this.d.a();
                    this.e.a();
                }
            } else if (this.d.b()) {
                this.k.a(NalUnitUtil.parseSpsNalUnit(this.d.a, 3, this.d.b));
                this.d.a();
            } else if (this.e.b()) {
                this.k.a(NalUnitUtil.parsePpsNalUnit(this.e.a, 3, this.e.b));
                this.e.a();
            }
        }
        if (this.f.b(i2)) {
            this.o.reset(this.f.a, NalUnitUtil.unescapeStream(this.f.a, this.f.b));
            this.o.setPosition(4);
            this.a.consume(j2, this.o);
        }
        if (this.k.a(j, i, this.l, this.n)) {
            this.n = false;
        }
    }

    @EnsuresNonNull({"output", "sampleReader"})
    private void a() {
        Assertions.checkStateNotNull(this.j);
        Util.castNonNull(this.k);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private final TrackOutput a;
        private final boolean b;
        private final boolean c;
        private int h;
        private int i;
        private long j;
        private boolean k;
        private long l;
        private boolean o;
        private long p;
        private long q;
        private boolean r;
        private final SparseArray<NalUnitUtil.SpsData> d = new SparseArray<>();
        private final SparseArray<NalUnitUtil.PpsData> e = new SparseArray<>();
        private C0063a m = new C0063a();
        private C0063a n = new C0063a();
        private byte[] g = new byte[128];
        private final ParsableNalUnitBitArray f = new ParsableNalUnitBitArray(this.g, 0, 0);

        public a(TrackOutput trackOutput, boolean z, boolean z2) {
            this.a = trackOutput;
            this.b = z;
            this.c = z2;
            b();
        }

        public boolean a() {
            return this.c;
        }

        public void a(NalUnitUtil.SpsData spsData) {
            this.d.append(spsData.seqParameterSetId, spsData);
        }

        public void a(NalUnitUtil.PpsData ppsData) {
            this.e.append(ppsData.picParameterSetId, ppsData);
        }

        public void b() {
            this.k = false;
            this.o = false;
            this.n.a();
        }

        public void a(long j, int i, long j2) {
            this.i = i;
            this.l = j2;
            this.j = j;
            if (!this.b || this.i != 1) {
                if (this.c) {
                    int i2 = this.i;
                    if (!(i2 == 5 || i2 == 1 || i2 == 2)) {
                        return;
                    }
                } else {
                    return;
                }
            }
            C0063a aVar = this.m;
            this.m = this.n;
            this.n = aVar;
            this.n.a();
            this.h = 0;
            this.k = true;
        }

        public void a(byte[] bArr, int i, int i2) {
            boolean z;
            boolean z2;
            boolean z3;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            if (this.k) {
                int i8 = i2 - i;
                byte[] bArr2 = this.g;
                int length = bArr2.length;
                int i9 = this.h;
                if (length < i9 + i8) {
                    this.g = Arrays.copyOf(bArr2, (i9 + i8) * 2);
                }
                System.arraycopy(bArr, i, this.g, this.h, i8);
                this.h += i8;
                this.f.reset(this.g, 0, this.h);
                if (this.f.canReadBits(8)) {
                    this.f.skipBit();
                    int readBits = this.f.readBits(2);
                    this.f.skipBits(5);
                    if (this.f.canReadExpGolombCodedNum()) {
                        this.f.readUnsignedExpGolombCodedInt();
                        if (this.f.canReadExpGolombCodedNum()) {
                            int readUnsignedExpGolombCodedInt = this.f.readUnsignedExpGolombCodedInt();
                            if (!this.c) {
                                this.k = false;
                                this.n.a(readUnsignedExpGolombCodedInt);
                            } else if (this.f.canReadExpGolombCodedNum()) {
                                int readUnsignedExpGolombCodedInt2 = this.f.readUnsignedExpGolombCodedInt();
                                if (this.e.indexOfKey(readUnsignedExpGolombCodedInt2) < 0) {
                                    this.k = false;
                                    return;
                                }
                                NalUnitUtil.PpsData ppsData = this.e.get(readUnsignedExpGolombCodedInt2);
                                NalUnitUtil.SpsData spsData = this.d.get(ppsData.seqParameterSetId);
                                if (spsData.separateColorPlaneFlag) {
                                    if (this.f.canReadBits(2)) {
                                        this.f.skipBits(2);
                                    } else {
                                        return;
                                    }
                                }
                                if (this.f.canReadBits(spsData.frameNumLength)) {
                                    int readBits2 = this.f.readBits(spsData.frameNumLength);
                                    if (spsData.frameMbsOnlyFlag) {
                                        z3 = false;
                                        z2 = false;
                                        z = false;
                                    } else if (this.f.canReadBits(1)) {
                                        boolean readBit = this.f.readBit();
                                        if (!readBit) {
                                            z3 = readBit;
                                            z2 = false;
                                            z = false;
                                        } else if (this.f.canReadBits(1)) {
                                            z = this.f.readBit();
                                            z3 = readBit;
                                            z2 = true;
                                        } else {
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                    boolean z4 = this.i == 5;
                                    if (!z4) {
                                        i3 = 0;
                                    } else if (this.f.canReadExpGolombCodedNum()) {
                                        i3 = this.f.readUnsignedExpGolombCodedInt();
                                    } else {
                                        return;
                                    }
                                    if (spsData.picOrderCountType == 0) {
                                        if (this.f.canReadBits(spsData.picOrderCntLsbLength)) {
                                            int readBits3 = this.f.readBits(spsData.picOrderCntLsbLength);
                                            if (!ppsData.bottomFieldPicOrderInFramePresentFlag || z3) {
                                                i7 = readBits3;
                                                i6 = 0;
                                                i5 = i6;
                                                i4 = i5;
                                                this.n.a(spsData, readBits, readUnsignedExpGolombCodedInt, readBits2, readUnsignedExpGolombCodedInt2, z3, z2, z, z4, i3, i7, i6, i5, i4);
                                                this.k = false;
                                            } else if (this.f.canReadExpGolombCodedNum()) {
                                                i6 = this.f.readSignedExpGolombCodedInt();
                                                i7 = readBits3;
                                                i5 = 0;
                                                i4 = 0;
                                                this.n.a(spsData, readBits, readUnsignedExpGolombCodedInt, readBits2, readUnsignedExpGolombCodedInt2, z3, z2, z, z4, i3, i7, i6, i5, i4);
                                                this.k = false;
                                            }
                                        }
                                    } else if (spsData.picOrderCountType != 1 || spsData.deltaPicOrderAlwaysZeroFlag) {
                                        i7 = 0;
                                        i6 = 0;
                                        i5 = i6;
                                        i4 = i5;
                                        this.n.a(spsData, readBits, readUnsignedExpGolombCodedInt, readBits2, readUnsignedExpGolombCodedInt2, z3, z2, z, z4, i3, i7, i6, i5, i4);
                                        this.k = false;
                                    } else if (this.f.canReadExpGolombCodedNum()) {
                                        int readSignedExpGolombCodedInt = this.f.readSignedExpGolombCodedInt();
                                        if (!ppsData.bottomFieldPicOrderInFramePresentFlag || z3) {
                                            i5 = readSignedExpGolombCodedInt;
                                            i7 = 0;
                                            i6 = 0;
                                            i4 = 0;
                                        } else if (this.f.canReadExpGolombCodedNum()) {
                                            i4 = this.f.readSignedExpGolombCodedInt();
                                            i5 = readSignedExpGolombCodedInt;
                                            i7 = 0;
                                            i6 = 0;
                                        } else {
                                            return;
                                        }
                                        this.n.a(spsData, readBits, readUnsignedExpGolombCodedInt, readBits2, readUnsignedExpGolombCodedInt2, z3, z2, z, z4, i3, i7, i6, i5, i4);
                                        this.k = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public boolean a(long j, int i, boolean z, boolean z2) {
            boolean z3 = false;
            if (this.i == 9 || (this.c && this.n.a(this.m))) {
                if (z && this.o) {
                    a(i + ((int) (j - this.j)));
                }
                this.p = this.j;
                this.q = this.l;
                this.r = false;
                this.o = true;
            }
            if (this.b) {
                z2 = this.n.b();
            }
            boolean z4 = this.r;
            int i2 = this.i;
            if (i2 == 5 || (z2 && i2 == 1)) {
                z3 = true;
            }
            this.r = z4 | z3;
            return this.r;
        }

        private void a(int i) {
            long j = this.q;
            if (j != C.TIME_UNSET) {
                boolean z = this.r;
                this.a.sampleMetadata(j, z ? 1 : 0, (int) (this.j - this.p), i, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: com.google.android.exoplayer2.extractor.ts.H264Reader$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        public static final class C0063a {
            private boolean a;
            private boolean b;
            @Nullable
            private NalUnitUtil.SpsData c;
            private int d;
            private int e;
            private int f;
            private int g;
            private boolean h;
            private boolean i;
            private boolean j;
            private boolean k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            private C0063a() {
            }

            public void a() {
                this.b = false;
                this.a = false;
            }

            public void a(int i) {
                this.e = i;
                this.b = true;
            }

            public void a(NalUnitUtil.SpsData spsData, int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, boolean z4, int i5, int i6, int i7, int i8, int i9) {
                this.c = spsData;
                this.d = i;
                this.e = i2;
                this.f = i3;
                this.g = i4;
                this.h = z;
                this.i = z2;
                this.j = z3;
                this.k = z4;
                this.l = i5;
                this.m = i6;
                this.n = i7;
                this.o = i8;
                this.p = i9;
                this.a = true;
                this.b = true;
            }

            public boolean b() {
                int i;
                return this.b && ((i = this.e) == 7 || i == 2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean a(C0063a aVar) {
                int i;
                int i2;
                boolean z;
                if (!this.a) {
                    return false;
                }
                if (!aVar.a) {
                    return true;
                }
                NalUnitUtil.SpsData spsData = (NalUnitUtil.SpsData) Assertions.checkStateNotNull(this.c);
                NalUnitUtil.SpsData spsData2 = (NalUnitUtil.SpsData) Assertions.checkStateNotNull(aVar.c);
                return (this.f == aVar.f && this.g == aVar.g && this.h == aVar.h && (!this.i || !aVar.i || this.j == aVar.j) && (((i = this.d) == (i2 = aVar.d) || (i != 0 && i2 != 0)) && ((spsData.picOrderCountType != 0 || spsData2.picOrderCountType != 0 || (this.m == aVar.m && this.n == aVar.n)) && ((spsData.picOrderCountType != 1 || spsData2.picOrderCountType != 1 || (this.o == aVar.o && this.p == aVar.p)) && (z = this.k) == aVar.k && (!z || this.l == aVar.l))))) ? false : true;
            }
        }
    }
}
