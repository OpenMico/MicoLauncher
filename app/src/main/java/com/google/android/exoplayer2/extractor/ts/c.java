package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* compiled from: PsDurationReader.java */
/* loaded from: classes2.dex */
final class c {
    private boolean c;
    private boolean d;
    private boolean e;
    private final TimestampAdjuster a = new TimestampAdjuster(0);
    private long f = C.TIME_UNSET;
    private long g = C.TIME_UNSET;
    private long h = C.TIME_UNSET;
    private final ParsableByteArray b = new ParsableByteArray();

    public boolean a() {
        return this.c;
    }

    public TimestampAdjuster b() {
        return this.a;
    }

    public int a(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        if (!this.e) {
            return c(extractorInput, positionHolder);
        }
        if (this.g == C.TIME_UNSET) {
            return a(extractorInput);
        }
        if (!this.d) {
            return b(extractorInput, positionHolder);
        }
        long j = this.f;
        if (j == C.TIME_UNSET) {
            return a(extractorInput);
        }
        this.h = this.a.adjustTsTimestamp(this.g) - this.a.adjustTsTimestamp(j);
        long j2 = this.h;
        if (j2 < 0) {
            StringBuilder sb = new StringBuilder(65);
            sb.append("Invalid duration: ");
            sb.append(j2);
            sb.append(". Using TIME_UNSET instead.");
            Log.w("PsDurationReader", sb.toString());
            this.h = C.TIME_UNSET;
        }
        return a(extractorInput);
    }

    public long c() {
        return this.h;
    }

    public static long a(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        if (parsableByteArray.bytesLeft() < 9) {
            return C.TIME_UNSET;
        }
        byte[] bArr = new byte[9];
        parsableByteArray.readBytes(bArr, 0, bArr.length);
        parsableByteArray.setPosition(position);
        return !a(bArr) ? C.TIME_UNSET : b(bArr);
    }

    private int a(ExtractorInput extractorInput) {
        this.b.reset(Util.EMPTY_BYTE_ARRAY);
        this.c = true;
        extractorInput.resetPeekPosition();
        return 0;
    }

    private int b(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int min = (int) Math.min(20000L, extractorInput.getLength());
        long j = 0;
        if (extractorInput.getPosition() != j) {
            positionHolder.position = j;
            return 1;
        }
        this.b.reset(min);
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.b.getData(), 0, min);
        this.f = b(this.b);
        this.d = true;
        return 0;
    }

    private long b(ParsableByteArray parsableByteArray) {
        int limit = parsableByteArray.limit();
        for (int position = parsableByteArray.getPosition(); position < limit - 3; position++) {
            if (a(parsableByteArray.getData(), position) == 442) {
                parsableByteArray.setPosition(position + 4);
                long a = a(parsableByteArray);
                if (a != C.TIME_UNSET) {
                    return a;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private int c(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        long length = extractorInput.getLength();
        int min = (int) Math.min(20000L, length);
        long j = length - min;
        if (extractorInput.getPosition() != j) {
            positionHolder.position = j;
            return 1;
        }
        this.b.reset(min);
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.b.getData(), 0, min);
        this.g = c(this.b);
        this.e = true;
        return 0;
    }

    private long c(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        for (int limit = parsableByteArray.limit() - 4; limit >= position; limit--) {
            if (a(parsableByteArray.getData(), limit) == 442) {
                parsableByteArray.setPosition(limit + 4);
                long a = a(parsableByteArray);
                if (a != C.TIME_UNSET) {
                    return a;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private int a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    private static boolean a(byte[] bArr) {
        return (bArr[0] & 196) == 68 && (bArr[2] & 4) == 4 && (bArr[4] & 4) == 4 && (bArr[5] & 1) == 1 && (bArr[8] & 3) == 3;
    }

    private static long b(byte[] bArr) {
        return (((bArr[0] & 56) >> 3) << 30) | ((bArr[0] & 3) << 28) | ((bArr[1] & 255) << 20) | (((bArr[2] & 248) >> 3) << 15) | ((bArr[2] & 3) << 13) | ((bArr[3] & 255) << 5) | ((bArr[4] & 248) >> 3);
    }
}
