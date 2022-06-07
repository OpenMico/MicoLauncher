package com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* compiled from: VbriSeeker.java */
/* loaded from: classes3.dex */
final class d implements Seeker {
    private final long[] a;
    private final long[] b;
    private final long c;
    private final long d;

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    @Nullable
    public static d a(long j, long j2, MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        int i;
        parsableByteArray.skipBytes(10);
        int readInt = parsableByteArray.readInt();
        if (readInt <= 0) {
            return null;
        }
        int i2 = header.sampleRate;
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(readInt, 1000000 * (i2 >= 32000 ? 1152 : 576), i2);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(2);
        long j3 = j2 + header.frameSize;
        long[] jArr = new long[readUnsignedShort];
        long[] jArr2 = new long[readUnsignedShort];
        int i3 = 0;
        long j4 = j2;
        while (i3 < readUnsignedShort) {
            jArr[i3] = (i3 * scaleLargeTimestamp) / readUnsignedShort;
            jArr2[i3] = Math.max(j4, j3);
            switch (readUnsignedShort3) {
                case 1:
                    i = parsableByteArray.readUnsignedByte();
                    break;
                case 2:
                    i = parsableByteArray.readUnsignedShort();
                    break;
                case 3:
                    i = parsableByteArray.readUnsignedInt24();
                    break;
                case 4:
                    i = parsableByteArray.readUnsignedIntToInt();
                    break;
                default:
                    return null;
            }
            j4 += i * readUnsignedShort2;
            i3++;
            readUnsignedShort2 = readUnsignedShort2;
        }
        if (!(j == -1 || j == j4)) {
            Log.w("VbriSeeker", "VBRI data size mismatch: " + j + ", " + j4);
        }
        return new d(jArr, jArr2, scaleLargeTimestamp, j4);
    }

    private d(long[] jArr, long[] jArr2, long j, long j2) {
        this.a = jArr;
        this.b = jArr2;
        this.c = j;
        this.d = j2;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        int binarySearchFloor = Util.binarySearchFloor(this.a, j, true, true);
        SeekPoint seekPoint = new SeekPoint(this.a[binarySearchFloor], this.b[binarySearchFloor]);
        if (seekPoint.timeUs < j) {
            long[] jArr = this.a;
            if (binarySearchFloor != jArr.length - 1) {
                int i = binarySearchFloor + 1;
                return new SeekMap.SeekPoints(seekPoint, new SeekPoint(jArr[i], this.b[i]));
            }
        }
        return new SeekMap.SeekPoints(seekPoint);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getTimeUs(long j) {
        return this.a[Util.binarySearchFloor(this.b, j, true, true)];
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.c;
    }

    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.d;
    }
}
