package com.google.android.exoplayer2.extractor.ts;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Ascii;
import java.util.Arrays;
import java.util.Collections;

/* loaded from: classes2.dex */
public final class H262Reader implements ElementaryStreamReader {
    private static final double[] c = {23.976023976023978d, 24.0d, 25.0d, 29.97002997002997d, 30.0d, 50.0d, 59.94005994005994d, 60.0d};
    private String a;
    private TrackOutput b;
    @Nullable
    private final f d;
    @Nullable
    private final ParsableByteArray e;
    @Nullable
    private final a f;
    private final boolean[] g;
    private final a h;
    private long i;
    private boolean j;
    private boolean k;
    private long l;
    private long m;
    private long n;
    private long o;
    private boolean p;
    private boolean q;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public H262Reader() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public H262Reader(@Nullable f fVar) {
        this.d = fVar;
        this.g = new boolean[4];
        this.h = new a(128);
        if (fVar != null) {
            this.f = new a(Opcodes.GETSTATIC, 128);
            this.e = new ParsableByteArray();
        } else {
            this.f = null;
            this.e = null;
        }
        this.m = C.TIME_UNSET;
        this.o = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.g);
        this.h.a();
        a aVar = this.f;
        if (aVar != null) {
            aVar.a();
        }
        this.i = 0L;
        this.j = false;
        this.m = C.TIME_UNSET;
        this.o = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.a = trackIdGenerator.getFormatId();
        this.b = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        f fVar = this.d;
        if (fVar != null) {
            fVar.a(extractorOutput, trackIdGenerator);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        this.m = j;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0145  */
    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r21) {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H262Reader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    private static Pair<Format, Long> a(a aVar, String str) {
        float f;
        byte[] copyOf = Arrays.copyOf(aVar.c, aVar.a);
        int i = copyOf[5] & 255;
        int i2 = ((copyOf[4] & 255) << 4) | (i >> 4);
        int i3 = ((i & 15) << 8) | (copyOf[6] & 255);
        switch ((copyOf[7] & 240) >> 4) {
            case 2:
                f = (i3 * 4) / (i2 * 3);
                break;
            case 3:
                f = (i3 * 16) / (i2 * 9);
                break;
            case 4:
                f = (i3 * 121) / (i2 * 100);
                break;
            default:
                f = 1.0f;
                break;
        }
        Format build = new Format.Builder().setId(str).setSampleMimeType(MimeTypes.VIDEO_MPEG2).setWidth(i2).setHeight(i3).setPixelWidthHeightRatio(f).setInitializationData(Collections.singletonList(copyOf)).build();
        long j = 0;
        int i4 = (copyOf[7] & 15) - 1;
        if (i4 >= 0) {
            double[] dArr = c;
            if (i4 < dArr.length) {
                double d = dArr[i4];
                int i5 = aVar.b + 9;
                int i6 = (copyOf[i5] & 96) >> 5;
                int i7 = copyOf[i5] & Ascii.US;
                if (i6 != i7) {
                    d *= (i6 + 1.0d) / (i7 + 1);
                }
                j = (long) (1000000.0d / d);
            }
        }
        return Pair.create(build, Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private static final byte[] d = {0, 0, 1};
        public int a;
        public int b;
        public byte[] c;
        private boolean e;

        public a(int i) {
            this.c = new byte[i];
        }

        public void a() {
            this.e = false;
            this.a = 0;
            this.b = 0;
        }

        public boolean a(int i, int i2) {
            if (this.e) {
                this.a -= i2;
                if (this.b == 0 && i == 181) {
                    this.b = this.a;
                } else {
                    this.e = false;
                    return true;
                }
            } else if (i == 179) {
                this.e = true;
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
}
