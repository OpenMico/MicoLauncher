package com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.metadata.id3.MlltFrame;
import com.google.android.exoplayer2.util.Util;

/* compiled from: MlltSeeker.java */
/* loaded from: classes3.dex */
final class c implements Seeker {
    private final long[] a;
    private final long[] b;
    private final long c;

    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return -1L;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public static c a(long j, MlltFrame mlltFrame, long j2) {
        int length = mlltFrame.bytesDeviations.length;
        int i = length + 1;
        long[] jArr = new long[i];
        long[] jArr2 = new long[i];
        jArr[0] = j;
        long j3 = 0;
        jArr2[0] = 0;
        for (int i2 = 1; i2 <= length; i2++) {
            int i3 = i2 - 1;
            j += mlltFrame.bytesBetweenReference + mlltFrame.bytesDeviations[i3];
            j3 += mlltFrame.millisecondsBetweenReference + mlltFrame.millisecondsDeviations[i3];
            jArr[i2] = j;
            jArr2[i2] = j3;
        }
        return new c(jArr, jArr2, j2);
    }

    private c(long[] jArr, long[] jArr2, long j) {
        this.a = jArr;
        this.b = jArr2;
        this.c = j == C.TIME_UNSET ? C.msToUs(jArr2[jArr2.length - 1]) : j;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        Pair<Long, Long> a = a(C.usToMs(Util.constrainValue(j, 0L, this.c)), this.b, this.a);
        return new SeekMap.SeekPoints(new SeekPoint(C.msToUs(((Long) a.first).longValue()), ((Long) a.second).longValue()));
    }

    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getTimeUs(long j) {
        return C.msToUs(((Long) a(j, this.a, this.b).second).longValue());
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.c;
    }

    private static Pair<Long, Long> a(long j, long[] jArr, long[] jArr2) {
        int binarySearchFloor = Util.binarySearchFloor(jArr, j, true, true);
        long j2 = jArr[binarySearchFloor];
        long j3 = jArr2[binarySearchFloor];
        int i = binarySearchFloor + 1;
        if (i == jArr.length) {
            return Pair.create(Long.valueOf(j2), Long.valueOf(j3));
        }
        long j4 = jArr[i];
        return Pair.create(Long.valueOf(j), Long.valueOf(((long) ((j4 == j2 ? 0.0d : (j - j2) / (j4 - j2)) * (jArr2[i] - j3))) + j3));
    }
}
