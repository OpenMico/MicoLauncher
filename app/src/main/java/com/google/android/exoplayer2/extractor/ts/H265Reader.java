package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class H265Reader implements ElementaryStreamReader {
    private final SeiReader a;
    private String b;
    private TrackOutput c;
    private a d;
    private boolean e;
    private long l;
    private final boolean[] f = new boolean[3];
    private final a g = new a(32, 128);
    private final a h = new a(33, 128);
    private final a i = new a(34, 128);
    private final a j = new a(39, 128);
    private final a k = new a(40, 128);
    private long m = C.TIME_UNSET;
    private final ParsableByteArray n = new ParsableByteArray();

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public H265Reader(SeiReader seiReader) {
        this.a = seiReader;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.l = 0L;
        this.m = C.TIME_UNSET;
        NalUnitUtil.clearPrefixFlags(this.f);
        this.g.a();
        this.h.a();
        this.i.a();
        this.j.a();
        this.k.a();
        a aVar = this.d;
        if (aVar != null) {
            aVar.a();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.b = trackIdGenerator.getFormatId();
        this.c = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        this.d = new a(this.c);
        this.a.createTracks(extractorOutput, trackIdGenerator);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.m = j;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        a();
        while (parsableByteArray.bytesLeft() > 0) {
            int position = parsableByteArray.getPosition();
            int limit = parsableByteArray.limit();
            byte[] data = parsableByteArray.getData();
            this.l += parsableByteArray.bytesLeft();
            this.c.sampleData(parsableByteArray, parsableByteArray.bytesLeft());
            while (position < limit) {
                int findNalUnit = NalUnitUtil.findNalUnit(data, position, limit, this.f);
                if (findNalUnit == limit) {
                    a(data, position, limit);
                    return;
                }
                int h265NalUnitType = NalUnitUtil.getH265NalUnitType(data, findNalUnit);
                int i = findNalUnit - position;
                if (i > 0) {
                    a(data, position, findNalUnit);
                }
                int i2 = limit - findNalUnit;
                long j = this.l - i2;
                b(j, i2, i < 0 ? -i : 0, this.m);
                a(j, i2, h265NalUnitType, this.m);
                position = findNalUnit + 3;
            }
        }
    }

    @RequiresNonNull({"sampleReader"})
    private void a(long j, int i, int i2, long j2) {
        this.d.a(j, i, i2, j2, this.e);
        if (!this.e) {
            this.g.a(i2);
            this.h.a(i2);
            this.i.a(i2);
        }
        this.j.a(i2);
        this.k.a(i2);
    }

    @RequiresNonNull({"sampleReader"})
    private void a(byte[] bArr, int i, int i2) {
        this.d.a(bArr, i, i2);
        if (!this.e) {
            this.g.a(bArr, i, i2);
            this.h.a(bArr, i, i2);
            this.i.a(bArr, i, i2);
        }
        this.j.a(bArr, i, i2);
        this.k.a(bArr, i, i2);
    }

    @RequiresNonNull({"output", "sampleReader"})
    private void b(long j, int i, int i2, long j2) {
        this.d.a(j, i, this.e);
        if (!this.e) {
            this.g.b(i2);
            this.h.b(i2);
            this.i.b(i2);
            if (this.g.b() && this.h.b() && this.i.b()) {
                this.c.format(a(this.b, this.g, this.h, this.i));
                this.e = true;
            }
        }
        if (this.j.b(i2)) {
            this.n.reset(this.j.a, NalUnitUtil.unescapeStream(this.j.a, this.j.b));
            this.n.skipBytes(5);
            this.a.consume(j2, this.n);
        }
        if (this.k.b(i2)) {
            this.n.reset(this.k.a, NalUnitUtil.unescapeStream(this.k.a, this.k.b));
            this.n.skipBytes(5);
            this.a.consume(j2, this.n);
        }
    }

    private static Format a(@Nullable String str, a aVar, a aVar2, a aVar3) {
        byte[] bArr = new byte[aVar.b + aVar2.b + aVar3.b];
        System.arraycopy(aVar.a, 0, bArr, 0, aVar.b);
        System.arraycopy(aVar2.a, 0, bArr, aVar.b, aVar2.b);
        System.arraycopy(aVar3.a, 0, bArr, aVar.b + aVar2.b, aVar3.b);
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(aVar2.a, 0, aVar2.b);
        parsableNalUnitBitArray.skipBits(44);
        int readBits = parsableNalUnitBitArray.readBits(3);
        parsableNalUnitBitArray.skipBit();
        parsableNalUnitBitArray.skipBits(88);
        parsableNalUnitBitArray.skipBits(8);
        int i = 0;
        for (int i2 = 0; i2 < readBits; i2++) {
            if (parsableNalUnitBitArray.readBit()) {
                i += 89;
            }
            if (parsableNalUnitBitArray.readBit()) {
                i += 8;
            }
        }
        parsableNalUnitBitArray.skipBits(i);
        if (readBits > 0) {
            parsableNalUnitBitArray.skipBits((8 - readBits) * 2);
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (readUnsignedExpGolombCodedInt == 3) {
            parsableNalUnitBitArray.skipBit();
        }
        int readUnsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int readUnsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit()) {
            int readUnsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int readUnsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int readUnsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            readUnsignedExpGolombCodedInt2 -= ((readUnsignedExpGolombCodedInt == 1 || readUnsignedExpGolombCodedInt == 2) ? 2 : 1) * (readUnsignedExpGolombCodedInt4 + readUnsignedExpGolombCodedInt5);
            readUnsignedExpGolombCodedInt3 -= (readUnsignedExpGolombCodedInt == 1 ? 2 : 1) * (readUnsignedExpGolombCodedInt6 + readUnsignedExpGolombCodedInt7);
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int readUnsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        for (int i3 = parsableNalUnitBitArray.readBit() ? 0 : readBits; i3 <= readBits; i3++) {
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
            a(parsableNalUnitBitArray);
        }
        parsableNalUnitBitArray.skipBits(2);
        if (parsableNalUnitBitArray.readBit()) {
            parsableNalUnitBitArray.skipBits(8);
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
        }
        b(parsableNalUnitBitArray);
        if (parsableNalUnitBitArray.readBit()) {
            for (int i4 = 0; i4 < parsableNalUnitBitArray.readUnsignedExpGolombCodedInt(); i4++) {
                parsableNalUnitBitArray.skipBits(readUnsignedExpGolombCodedInt8 + 4 + 1);
            }
        }
        parsableNalUnitBitArray.skipBits(2);
        float f = 1.0f;
        if (parsableNalUnitBitArray.readBit()) {
            if (parsableNalUnitBitArray.readBit()) {
                int readBits2 = parsableNalUnitBitArray.readBits(8);
                if (readBits2 == 255) {
                    int readBits3 = parsableNalUnitBitArray.readBits(16);
                    int readBits4 = parsableNalUnitBitArray.readBits(16);
                    if (!(readBits3 == 0 || readBits4 == 0)) {
                        f = readBits3 / readBits4;
                    }
                } else if (readBits2 < NalUnitUtil.ASPECT_RATIO_IDC_VALUES.length) {
                    f = NalUnitUtil.ASPECT_RATIO_IDC_VALUES[readBits2];
                } else {
                    StringBuilder sb = new StringBuilder(46);
                    sb.append("Unexpected aspect_ratio_idc value: ");
                    sb.append(readBits2);
                    Log.w("H265Reader", sb.toString());
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBit();
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBits(4);
                if (parsableNalUnitBitArray.readBit()) {
                    parsableNalUnitBitArray.skipBits(24);
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                readUnsignedExpGolombCodedInt3 *= 2;
            }
        }
        parsableNalUnitBitArray.reset(aVar2.a, 0, aVar2.b);
        parsableNalUnitBitArray.skipBits(24);
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.VIDEO_H265).setCodecs(CodecSpecificDataUtil.buildHevcCodecStringFromSps(parsableNalUnitBitArray)).setWidth(readUnsignedExpGolombCodedInt2).setHeight(readUnsignedExpGolombCodedInt3).setPixelWidthHeightRatio(f).setInitializationData(Collections.singletonList(bArr)).build();
    }

    private static void a(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        for (int i = 0; i < 4; i++) {
            int i2 = 0;
            while (i2 < 6) {
                if (!parsableNalUnitBitArray.readBit()) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                } else {
                    int min = Math.min(64, 1 << ((i << 1) + 4));
                    if (i > 1) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                    for (int i3 = 0; i3 < min; i3++) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                }
                int i4 = 3;
                if (i != 3) {
                    i4 = 1;
                }
                i2 += i4;
            }
        }
    }

    private static void b(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        int readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < readUnsignedExpGolombCodedInt; i2++) {
            if (i2 != 0) {
                z = parsableNalUnitBitArray.readBit();
            }
            if (z) {
                parsableNalUnitBitArray.skipBit();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                for (int i3 = 0; i3 <= i; i3++) {
                    if (parsableNalUnitBitArray.readBit()) {
                        parsableNalUnitBitArray.skipBit();
                    }
                }
            } else {
                int readUnsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int readUnsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                i = readUnsignedExpGolombCodedInt2 + readUnsignedExpGolombCodedInt3;
                for (int i4 = 0; i4 < readUnsignedExpGolombCodedInt2; i4++) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    parsableNalUnitBitArray.skipBit();
                }
                for (int i5 = 0; i5 < readUnsignedExpGolombCodedInt3; i5++) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    parsableNalUnitBitArray.skipBit();
                }
            }
        }
    }

    @EnsuresNonNull({"output", "sampleReader"})
    private void a() {
        Assertions.checkStateNotNull(this.c);
        Util.castNonNull(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private final TrackOutput a;
        private long b;
        private boolean c;
        private int d;
        private long e;
        private boolean f;
        private boolean g;
        private boolean h;
        private boolean i;
        private boolean j;
        private long k;
        private long l;
        private boolean m;

        private static boolean b(int i) {
            return (32 <= i && i <= 35) || i == 39;
        }

        private static boolean c(int i) {
            return i < 32 || i == 40;
        }

        public a(TrackOutput trackOutput) {
            this.a = trackOutput;
        }

        public void a() {
            this.f = false;
            this.g = false;
            this.h = false;
            this.i = false;
            this.j = false;
        }

        public void a(long j, int i, int i2, long j2, boolean z) {
            this.g = false;
            this.h = false;
            this.e = j2;
            this.d = 0;
            this.b = j;
            boolean z2 = true;
            if (!c(i2)) {
                if (this.i && !this.j) {
                    if (z) {
                        a(i);
                    }
                    this.i = false;
                }
                if (b(i2)) {
                    this.h = !this.j;
                    this.j = true;
                }
            }
            this.c = i2 >= 16 && i2 <= 21;
            if (!this.c && i2 > 9) {
                z2 = false;
            }
            this.f = z2;
        }

        public void a(byte[] bArr, int i, int i2) {
            if (this.f) {
                int i3 = this.d;
                int i4 = (i + 2) - i3;
                if (i4 < i2) {
                    this.g = (bArr[i4] & 128) != 0;
                    this.f = false;
                    return;
                }
                this.d = i3 + (i2 - i);
            }
        }

        public void a(long j, int i, boolean z) {
            if (this.j && this.g) {
                this.m = this.c;
                this.j = false;
            } else if (this.h || this.g) {
                if (z && this.i) {
                    a(i + ((int) (j - this.b)));
                }
                this.k = this.b;
                this.l = this.e;
                this.m = this.c;
                this.i = true;
            }
        }

        private void a(int i) {
            long j = this.l;
            if (j != C.TIME_UNSET) {
                boolean z = this.m;
                this.a.sampleMetadata(j, z ? 1 : 0, (int) (this.b - this.k), i, null);
            }
        }
    }
}
