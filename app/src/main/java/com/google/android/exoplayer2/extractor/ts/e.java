package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TsDurationReader.java */
/* loaded from: classes2.dex */
public final class e {
    private final int a;
    private boolean d;
    private boolean e;
    private boolean f;
    private final TimestampAdjuster b = new TimestampAdjuster(0);
    private long g = C.TIME_UNSET;
    private long h = C.TIME_UNSET;
    private long i = C.TIME_UNSET;
    private final ParsableByteArray c = new ParsableByteArray();

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(int i) {
        this.a = i;
    }

    public boolean a() {
        return this.d;
    }

    public int a(ExtractorInput extractorInput, PositionHolder positionHolder, int i) throws IOException {
        if (i <= 0) {
            return a(extractorInput);
        }
        if (!this.f) {
            return c(extractorInput, positionHolder, i);
        }
        if (this.h == C.TIME_UNSET) {
            return a(extractorInput);
        }
        if (!this.e) {
            return b(extractorInput, positionHolder, i);
        }
        long j = this.g;
        if (j == C.TIME_UNSET) {
            return a(extractorInput);
        }
        this.i = this.b.adjustTsTimestamp(this.h) - this.b.adjustTsTimestamp(j);
        long j2 = this.i;
        if (j2 < 0) {
            StringBuilder sb = new StringBuilder(65);
            sb.append("Invalid duration: ");
            sb.append(j2);
            sb.append(". Using TIME_UNSET instead.");
            Log.w("TsDurationReader", sb.toString());
            this.i = C.TIME_UNSET;
        }
        return a(extractorInput);
    }

    public long b() {
        return this.i;
    }

    public TimestampAdjuster c() {
        return this.b;
    }

    private int a(ExtractorInput extractorInput) {
        this.c.reset(Util.EMPTY_BYTE_ARRAY);
        this.d = true;
        extractorInput.resetPeekPosition();
        return 0;
    }

    private int b(ExtractorInput extractorInput, PositionHolder positionHolder, int i) throws IOException {
        int min = (int) Math.min(this.a, extractorInput.getLength());
        long j = 0;
        if (extractorInput.getPosition() != j) {
            positionHolder.position = j;
            return 1;
        }
        this.c.reset(min);
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.c.getData(), 0, min);
        this.g = a(this.c, i);
        this.e = true;
        return 0;
    }

    private long a(ParsableByteArray parsableByteArray, int i) {
        int limit = parsableByteArray.limit();
        for (int position = parsableByteArray.getPosition(); position < limit; position++) {
            if (parsableByteArray.getData()[position] == 71) {
                long readPcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, position, i);
                if (readPcrFromPacket != C.TIME_UNSET) {
                    return readPcrFromPacket;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private int c(ExtractorInput extractorInput, PositionHolder positionHolder, int i) throws IOException {
        long length = extractorInput.getLength();
        int min = (int) Math.min(this.a, length);
        long j = length - min;
        if (extractorInput.getPosition() != j) {
            positionHolder.position = j;
            return 1;
        }
        this.c.reset(min);
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.c.getData(), 0, min);
        this.h = b(this.c, i);
        this.f = true;
        return 0;
    }

    private long b(ParsableByteArray parsableByteArray, int i) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        for (int i2 = limit - 188; i2 >= position; i2--) {
            if (TsUtil.isStartOfTsPacket(parsableByteArray.getData(), position, limit, i2)) {
                long readPcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, i2, i);
                if (readPcrFromPacket != C.TIME_UNSET) {
                    return readPcrFromPacket;
                }
            }
        }
        return C.TIME_UNSET;
    }
}
