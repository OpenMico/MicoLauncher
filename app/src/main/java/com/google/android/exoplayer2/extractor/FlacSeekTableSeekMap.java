package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class FlacSeekTableSeekMap implements SeekMap {
    private final FlacStreamMetadata a;
    private final long b;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public FlacSeekTableSeekMap(FlacStreamMetadata flacStreamMetadata, long j) {
        this.a = flacStreamMetadata;
        this.b = j;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.a.getDurationUs();
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        Assertions.checkStateNotNull(this.a.seekTable);
        long[] jArr = this.a.seekTable.pointSampleNumbers;
        long[] jArr2 = this.a.seekTable.pointOffsets;
        int binarySearchFloor = Util.binarySearchFloor(jArr, this.a.getSampleNumber(j), true, false);
        long j2 = 0;
        long j3 = binarySearchFloor == -1 ? 0L : jArr[binarySearchFloor];
        if (binarySearchFloor != -1) {
            j2 = jArr2[binarySearchFloor];
        }
        SeekPoint a = a(j3, j2);
        if (a.timeUs == j || binarySearchFloor == jArr.length - 1) {
            return new SeekMap.SeekPoints(a);
        }
        int i = binarySearchFloor + 1;
        return new SeekMap.SeekPoints(a, a(jArr[i], jArr2[i]));
    }

    private SeekPoint a(long j, long j2) {
        return new SeekPoint((j * 1000000) / this.a.sampleRate, this.b + j2);
    }
}
