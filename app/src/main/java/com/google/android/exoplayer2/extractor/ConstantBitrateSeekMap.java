package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public class ConstantBitrateSeekMap implements SeekMap {
    private final long a;
    private final long b;
    private final int c;
    private final long d;
    private final int e;
    private final long f;

    public ConstantBitrateSeekMap(long j, long j2, int i, int i2) {
        this.a = j;
        this.b = j2;
        this.c = i2 == -1 ? 1 : i2;
        this.e = i;
        if (j == -1) {
            this.d = -1L;
            this.f = C.TIME_UNSET;
            return;
        }
        this.d = j - j2;
        this.f = a(j, j2, i);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.d != -1;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        if (this.d == -1) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.b));
        }
        long a = a(j);
        long timeUsAtPosition = getTimeUsAtPosition(a);
        SeekPoint seekPoint = new SeekPoint(timeUsAtPosition, a);
        if (timeUsAtPosition < j) {
            int i = this.c;
            if (i + a < this.a) {
                long j2 = a + i;
                return new SeekMap.SeekPoints(seekPoint, new SeekPoint(getTimeUsAtPosition(j2), j2));
            }
        }
        return new SeekMap.SeekPoints(seekPoint);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.f;
    }

    public long getTimeUsAtPosition(long j) {
        return a(j, this.b, this.e);
    }

    private static long a(long j, long j2, int i) {
        return ((Math.max(0L, j - j2) * 8) * 1000000) / i;
    }

    private long a(long j) {
        int i = this.c;
        return this.b + Util.constrainValue((((j * this.e) / 8000000) / i) * i, 0L, this.d - i);
    }
}
