package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Util;

/* compiled from: WavSeekMap.java */
/* loaded from: classes2.dex */
final class c implements SeekMap {
    private final a a;
    private final int b;
    private final long c;
    private final long d;
    private final long e;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public c(a aVar, int i, long j, long j2) {
        this.a = aVar;
        this.b = i;
        this.c = j;
        this.d = (j2 - j) / aVar.e;
        this.e = a(this.d);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.e;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        long constrainValue = Util.constrainValue((this.a.c * j) / (this.b * 1000000), 0L, this.d - 1);
        long j2 = this.c + (this.a.e * constrainValue);
        long a = a(constrainValue);
        SeekPoint seekPoint = new SeekPoint(a, j2);
        if (a >= j || constrainValue == this.d - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        long j3 = constrainValue + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(a(j3), this.c + (this.a.e * j3)));
    }

    private long a(long j) {
        return Util.scaleLargeTimestamp(j * this.b, 1000000L, this.a.c);
    }
}
