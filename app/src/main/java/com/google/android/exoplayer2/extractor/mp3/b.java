package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.Util;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IndexSeeker.java */
/* loaded from: classes2.dex */
public final class b implements Seeker {
    private final long a;
    private final LongArray b = new LongArray();
    private final LongArray c = new LongArray();
    private long d;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public b(long j, long j2, long j3) {
        this.d = j;
        this.a = j3;
        this.b.add(0L);
        this.c.add(j2);
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long j) {
        return this.b.get(Util.binarySearchFloor(this.c, j, true, true));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        int binarySearchFloor = Util.binarySearchFloor(this.b, j, true, true);
        SeekPoint seekPoint = new SeekPoint(this.b.get(binarySearchFloor), this.c.get(binarySearchFloor));
        if (seekPoint.timeUs == j || binarySearchFloor == this.b.size() - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = binarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.b.get(i), this.c.get(i)));
    }

    public void a(long j, long j2) {
        if (!a(j)) {
            this.b.add(j);
            this.c.add(j2);
        }
    }

    public boolean a(long j) {
        LongArray longArray = this.b;
        return j - longArray.get(longArray.size() - 1) < 100000;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(long j) {
        this.d = j;
    }
}
