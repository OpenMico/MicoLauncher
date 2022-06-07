package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class IndexSeekMap implements SeekMap {
    private final long[] a;
    private final long[] b;
    private final long c;
    private final boolean d;

    public IndexSeekMap(long[] jArr, long[] jArr2, long j) {
        Assertions.checkArgument(jArr.length == jArr2.length);
        int length = jArr2.length;
        this.d = length > 0;
        if (!this.d || jArr2[0] <= 0) {
            this.a = jArr;
            this.b = jArr2;
        } else {
            int i = length + 1;
            this.a = new long[i];
            this.b = new long[i];
            System.arraycopy(jArr, 0, this.a, 1, length);
            System.arraycopy(jArr2, 0, this.b, 1, length);
        }
        this.c = j;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.c;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        if (!this.d) {
            return new SeekMap.SeekPoints(SeekPoint.START);
        }
        int binarySearchFloor = Util.binarySearchFloor(this.b, j, true, true);
        SeekPoint seekPoint = new SeekPoint(this.b[binarySearchFloor], this.a[binarySearchFloor]);
        if (seekPoint.timeUs != j) {
            long[] jArr = this.b;
            if (binarySearchFloor != jArr.length - 1) {
                int i = binarySearchFloor + 1;
                return new SeekMap.SeekPoints(seekPoint, new SeekPoint(jArr[i], this.a[i]));
            }
        }
        return new SeekMap.SeekPoints(seekPoint);
    }
}
