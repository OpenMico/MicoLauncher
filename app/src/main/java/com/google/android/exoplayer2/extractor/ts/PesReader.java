package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class PesReader implements TsPayloadReader {
    private final ElementaryStreamReader a;
    private final ParsableBitArray b = new ParsableBitArray(new byte[10]);
    private int c = 0;
    private int d;
    private TimestampAdjuster e;
    private boolean f;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private boolean k;
    private long l;

    public PesReader(ElementaryStreamReader elementaryStreamReader) {
        this.a = elementaryStreamReader;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.e = timestampAdjuster;
        this.a.createTracks(extractorOutput, trackIdGenerator);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public final void seek() {
        this.c = 0;
        this.d = 0;
        this.h = false;
        this.a.seek();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public final void consume(ParsableByteArray parsableByteArray, int i) throws ParserException {
        Assertions.checkStateNotNull(this.e);
        if ((i & 1) != 0) {
            switch (this.c) {
                case 0:
                case 1:
                    break;
                case 2:
                    Log.w("PesReader", "Unexpected start indicator reading extended header");
                    break;
                case 3:
                    int i2 = this.j;
                    if (i2 != -1) {
                        StringBuilder sb = new StringBuilder(59);
                        sb.append("Unexpected start indicator: expected ");
                        sb.append(i2);
                        sb.append(" more bytes");
                        Log.w("PesReader", sb.toString());
                    }
                    this.a.packetFinished();
                    break;
                default:
                    throw new IllegalStateException();
            }
            a(1);
        }
        while (parsableByteArray.bytesLeft() > 0) {
            int i3 = 0;
            switch (this.c) {
                case 0:
                    parsableByteArray.skipBytes(parsableByteArray.bytesLeft());
                    break;
                case 1:
                    if (a(parsableByteArray, this.b.data, 9)) {
                        if (a()) {
                            i3 = 2;
                        }
                        a(i3);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (a(parsableByteArray, this.b.data, Math.min(10, this.i)) && a(parsableByteArray, null, this.i)) {
                        b();
                        if (this.k) {
                            i3 = 4;
                        }
                        i |= i3;
                        this.a.packetStarted(this.l, i);
                        a(3);
                        break;
                    }
                    break;
                case 3:
                    int bytesLeft = parsableByteArray.bytesLeft();
                    int i4 = this.j;
                    if (i4 != -1) {
                        i3 = bytesLeft - i4;
                    }
                    if (i3 > 0) {
                        bytesLeft -= i3;
                        parsableByteArray.setLimit(parsableByteArray.getPosition() + bytesLeft);
                    }
                    this.a.consume(parsableByteArray);
                    int i5 = this.j;
                    if (i5 == -1) {
                        break;
                    } else {
                        this.j = i5 - bytesLeft;
                        if (this.j != 0) {
                            break;
                        } else {
                            this.a.packetFinished();
                            a(1);
                            break;
                        }
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private void a(int i) {
        this.c = i;
        this.d = 0;
    }

    private boolean a(ParsableByteArray parsableByteArray, @Nullable byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.d);
        if (min <= 0) {
            return true;
        }
        if (bArr == null) {
            parsableByteArray.skipBytes(min);
        } else {
            parsableByteArray.readBytes(bArr, this.d, min);
        }
        this.d += min;
        return this.d == i;
    }

    private boolean a() {
        this.b.setPosition(0);
        int readBits = this.b.readBits(24);
        if (readBits != 1) {
            StringBuilder sb = new StringBuilder(41);
            sb.append("Unexpected start code prefix: ");
            sb.append(readBits);
            Log.w("PesReader", sb.toString());
            this.j = -1;
            return false;
        }
        this.b.skipBits(8);
        int readBits2 = this.b.readBits(16);
        this.b.skipBits(5);
        this.k = this.b.readBit();
        this.b.skipBits(2);
        this.f = this.b.readBit();
        this.g = this.b.readBit();
        this.b.skipBits(6);
        this.i = this.b.readBits(8);
        if (readBits2 == 0) {
            this.j = -1;
        } else {
            this.j = ((readBits2 + 6) - 9) - this.i;
            int i = this.j;
            if (i < 0) {
                StringBuilder sb2 = new StringBuilder(47);
                sb2.append("Found negative packet payload size: ");
                sb2.append(i);
                Log.w("PesReader", sb2.toString());
                this.j = -1;
            }
        }
        return true;
    }

    @RequiresNonNull({"timestampAdjuster"})
    private void b() {
        this.b.setPosition(0);
        this.l = C.TIME_UNSET;
        if (this.f) {
            this.b.skipBits(4);
            this.b.skipBits(1);
            this.b.skipBits(1);
            long readBits = (this.b.readBits(3) << 30) | (this.b.readBits(15) << 15) | this.b.readBits(15);
            this.b.skipBits(1);
            if (!this.h && this.g) {
                this.b.skipBits(4);
                this.b.skipBits(1);
                this.b.skipBits(1);
                this.b.skipBits(1);
                this.e.adjustTsTimestamp((this.b.readBits(3) << 30) | (this.b.readBits(15) << 15) | this.b.readBits(15));
                this.h = true;
            }
            this.l = this.e.adjustTsTimestamp(readBits);
        }
    }
}
