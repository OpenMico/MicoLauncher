package com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* compiled from: XingSeeker.java */
/* loaded from: classes3.dex */
final class e implements Seeker {
    private final long a;
    private final int b;
    private final long c;
    private final long d;
    private final long e;
    @Nullable
    private final long[] f;

    @Nullable
    public static e a(long j, long j2, MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        int readUnsignedIntToInt;
        int i = header.samplesPerFrame;
        int i2 = header.sampleRate;
        int readInt = parsableByteArray.readInt();
        if ((readInt & 1) != 1 || (readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt()) == 0) {
            return null;
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(readUnsignedIntToInt, i * 1000000, i2);
        if ((readInt & 6) != 6) {
            return new e(j2, header.frameSize, scaleLargeTimestamp);
        }
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        long[] jArr = new long[100];
        for (int i3 = 0; i3 < 100; i3++) {
            jArr[i3] = parsableByteArray.readUnsignedByte();
        }
        if (j != -1) {
            long j3 = j2 + readUnsignedInt;
            if (j != j3) {
                Log.w("XingSeeker", "XING data size mismatch: " + j + ", " + j3);
            }
        }
        return new e(j2, header.frameSize, scaleLargeTimestamp, readUnsignedInt, jArr);
    }

    private e(long j, int i, long j2) {
        this(j, i, j2, -1L, null);
    }

    private e(long j, int i, long j2, long j3, @Nullable long[] jArr) {
        this.a = j;
        this.b = i;
        this.c = j2;
        this.f = jArr;
        this.d = j3;
        this.e = j3 != -1 ? j + j3 : -1L;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.f != null;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        if (!isSeekable()) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.a + this.b));
        }
        long constrainValue = Util.constrainValue(j, 0L, this.c);
        double d = (constrainValue * 100.0d) / this.c;
        double d2 = 0.0d;
        if (d > 0.0d) {
            if (d >= 100.0d) {
                d2 = 256.0d;
            } else {
                int i = (int) d;
                long[] jArr = (long[]) Assertions.checkStateNotNull(this.f);
                double d3 = jArr[i];
                d2 = d3 + ((d - i) * ((i == 99 ? 256.0d : jArr[i + 1]) - d3));
            }
        }
        return new SeekMap.SeekPoints(new SeekPoint(constrainValue, this.a + Util.constrainValue(Math.round((d2 / 256.0d) * this.d), this.b, this.d - 1)));
    }

    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getTimeUs(long j) {
        long j2 = j - this.a;
        if (!isSeekable() || j2 <= this.b) {
            return 0L;
        }
        long[] jArr = (long[]) Assertions.checkStateNotNull(this.f);
        double d = (j2 * 256.0d) / this.d;
        int binarySearchFloor = Util.binarySearchFloor(jArr, (long) d, true, true);
        long a = a(binarySearchFloor);
        long j3 = jArr[binarySearchFloor];
        int i = binarySearchFloor + 1;
        long a2 = a(i);
        long j4 = binarySearchFloor == 99 ? 256L : jArr[i];
        return a + Math.round((j3 == j4 ? 0.0d : (d - j3) / (j4 - j3)) * (a2 - a));
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.c;
    }

    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.e;
    }

    private long a(int i) {
        return (this.c * i) / 100;
    }
}
