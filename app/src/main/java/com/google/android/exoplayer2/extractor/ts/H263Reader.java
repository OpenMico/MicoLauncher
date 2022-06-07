package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Collections;

/* loaded from: classes2.dex */
public final class H263Reader implements ElementaryStreamReader {
    private static final float[] a = {1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 1.0f};
    @Nullable
    private final f b;
    @Nullable
    private final ParsableByteArray c;
    private final boolean[] d;
    private final a e;
    @Nullable
    private final a f;
    private b g;
    private long h;
    private String i;
    private TrackOutput j;
    private boolean k;
    private long l;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public H263Reader() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public H263Reader(@Nullable f fVar) {
        this.b = fVar;
        this.d = new boolean[4];
        this.e = new a(128);
        this.l = C.TIME_UNSET;
        if (fVar != null) {
            this.f = new a(Opcodes.GETSTATIC, 128);
            this.c = new ParsableByteArray();
            return;
        }
        this.f = null;
        this.c = null;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.d);
        this.e.a();
        b bVar = this.g;
        if (bVar != null) {
            bVar.a();
        }
        a aVar = this.f;
        if (aVar != null) {
            aVar.a();
        }
        this.h = 0L;
        this.l = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.i = trackIdGenerator.getFormatId();
        this.j = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        this.g = new b(this.j);
        f fVar = this.b;
        if (fVar != null) {
            fVar.a(extractorOutput, trackIdGenerator);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.l = j;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.g);
        Assertions.checkStateNotNull(this.j);
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        byte[] data = parsableByteArray.getData();
        this.h += parsableByteArray.bytesLeft();
        this.j.sampleData(parsableByteArray, parsableByteArray.bytesLeft());
        while (true) {
            int findNalUnit = NalUnitUtil.findNalUnit(data, position, limit, this.d);
            if (findNalUnit == limit) {
                break;
            }
            int i = findNalUnit + 3;
            int i2 = parsableByteArray.getData()[i] & 255;
            int i3 = findNalUnit - position;
            int i4 = 0;
            if (!this.k) {
                if (i3 > 0) {
                    this.e.a(data, position, findNalUnit);
                }
                if (this.e.a(i2, i3 < 0 ? -i3 : 0)) {
                    TrackOutput trackOutput = this.j;
                    a aVar = this.e;
                    trackOutput.format(a(aVar, aVar.b, (String) Assertions.checkNotNull(this.i)));
                    this.k = true;
                }
            }
            this.g.a(data, position, findNalUnit);
            a aVar2 = this.f;
            if (aVar2 != null) {
                if (i3 > 0) {
                    aVar2.a(data, position, findNalUnit);
                } else {
                    i4 = -i3;
                }
                if (this.f.b(i4)) {
                    ((ParsableByteArray) Util.castNonNull(this.c)).reset(this.f.a, NalUnitUtil.unescapeStream(this.f.a, this.f.b));
                    ((f) Util.castNonNull(this.b)).a(this.l, this.c);
                }
                if (i2 == 178 && parsableByteArray.getData()[findNalUnit + 2] == 1) {
                    this.f.a(i2);
                }
            }
            int i5 = limit - findNalUnit;
            this.g.a(this.h - i5, i5, this.k);
            this.g.a(i2, this.l);
            position = i;
        }
        if (!this.k) {
            this.e.a(data, position, limit);
        }
        this.g.a(data, position, limit);
        a aVar3 = this.f;
        if (aVar3 != null) {
            aVar3.a(data, position, limit);
        }
    }

    private static Format a(a aVar, int i, String str) {
        byte[] copyOf = Arrays.copyOf(aVar.c, aVar.a);
        ParsableBitArray parsableBitArray = new ParsableBitArray(copyOf);
        parsableBitArray.skipBytes(i);
        parsableBitArray.skipBytes(4);
        parsableBitArray.skipBit();
        parsableBitArray.skipBits(8);
        if (parsableBitArray.readBit()) {
            parsableBitArray.skipBits(4);
            parsableBitArray.skipBits(3);
        }
        int readBits = parsableBitArray.readBits(4);
        float f = 1.0f;
        if (readBits == 15) {
            int readBits2 = parsableBitArray.readBits(8);
            int readBits3 = parsableBitArray.readBits(8);
            if (readBits3 == 0) {
                Log.w("H263Reader", "Invalid aspect ratio");
            } else {
                f = readBits2 / readBits3;
            }
        } else {
            float[] fArr = a;
            if (readBits < fArr.length) {
                f = fArr[readBits];
            } else {
                Log.w("H263Reader", "Invalid aspect ratio");
            }
        }
        if (parsableBitArray.readBit()) {
            parsableBitArray.skipBits(2);
            parsableBitArray.skipBits(1);
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(3);
                parsableBitArray.skipBits(11);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
            }
        }
        if (parsableBitArray.readBits(2) != 0) {
            Log.w("H263Reader", "Unhandled video object layer shape");
        }
        parsableBitArray.skipBit();
        int readBits4 = parsableBitArray.readBits(16);
        parsableBitArray.skipBit();
        if (parsableBitArray.readBit()) {
            if (readBits4 == 0) {
                Log.w("H263Reader", "Invalid vop_increment_time_resolution");
            } else {
                int i2 = 0;
                for (int i3 = readBits4 - 1; i3 > 0; i3 >>= 1) {
                    i2++;
                }
                parsableBitArray.skipBits(i2);
            }
        }
        parsableBitArray.skipBit();
        int readBits5 = parsableBitArray.readBits(13);
        parsableBitArray.skipBit();
        int readBits6 = parsableBitArray.readBits(13);
        parsableBitArray.skipBit();
        parsableBitArray.skipBit();
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.VIDEO_MP4V).setWidth(readBits5).setHeight(readBits6).setPixelWidthHeightRatio(f).setInitializationData(Collections.singletonList(copyOf)).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private static final byte[] d = {0, 0, 1};
        public int a;
        public int b;
        public byte[] c;
        private boolean e;
        private int f;

        public a(int i) {
            this.c = new byte[i];
        }

        public void a() {
            this.e = false;
            this.a = 0;
            this.f = 0;
        }

        public boolean a(int i, int i2) {
            switch (this.f) {
                case 0:
                    if (i == 176) {
                        this.f = 1;
                        this.e = true;
                        break;
                    }
                    break;
                case 1:
                    if (i == 181) {
                        this.f = 2;
                        break;
                    } else {
                        Log.w("H263Reader", "Unexpected start code value");
                        a();
                        break;
                    }
                case 2:
                    if (i <= 31) {
                        this.f = 3;
                        break;
                    } else {
                        Log.w("H263Reader", "Unexpected start code value");
                        a();
                        break;
                    }
                case 3:
                    if ((i & PsExtractor.VIDEO_STREAM_MASK) == 32) {
                        this.b = this.a;
                        this.f = 4;
                        break;
                    } else {
                        Log.w("H263Reader", "Unexpected start code value");
                        a();
                        break;
                    }
                case 4:
                    if (i == 179 || i == 181) {
                        this.a -= i2;
                        this.e = false;
                        return true;
                    }
                default:
                    throw new IllegalStateException();
            }
            byte[] bArr = d;
            a(bArr, 0, bArr.length);
            return false;
        }

        public void a(byte[] bArr, int i, int i2) {
            if (this.e) {
                int i3 = i2 - i;
                byte[] bArr2 = this.c;
                int length = bArr2.length;
                int i4 = this.a;
                if (length < i4 + i3) {
                    this.c = Arrays.copyOf(bArr2, (i4 + i3) * 2);
                }
                System.arraycopy(bArr, i, this.c, this.a, i3);
                this.a += i3;
            }
        }
    }

    /* loaded from: classes2.dex */
    private static final class b {
        private final TrackOutput a;
        private boolean b;
        private boolean c;
        private boolean d;
        private int e;
        private int f;
        private long g;
        private long h;

        public b(TrackOutput trackOutput) {
            this.a = trackOutput;
        }

        public void a() {
            this.b = false;
            this.c = false;
            this.d = false;
            this.e = -1;
        }

        public void a(int i, long j) {
            this.e = i;
            this.d = false;
            boolean z = true;
            this.b = i == 182 || i == 179;
            if (i != 182) {
                z = false;
            }
            this.c = z;
            this.f = 0;
            this.h = j;
        }

        public void a(byte[] bArr, int i, int i2) {
            if (this.c) {
                int i3 = this.f;
                int i4 = (i + 1) - i3;
                if (i4 < i2) {
                    this.d = ((bArr[i4] & 192) >> 6) == 0;
                    this.c = false;
                    return;
                }
                this.f = i3 + (i2 - i);
            }
        }

        public void a(long j, int i, boolean z) {
            if (this.e == 182 && z && this.b) {
                long j2 = this.h;
                if (j2 != C.TIME_UNSET) {
                    this.a.sampleMetadata(j2, this.d ? 1 : 0, (int) (j - this.g), i, null);
                }
            }
            if (this.e != 179) {
                this.g = j;
            }
        }
    }
}
