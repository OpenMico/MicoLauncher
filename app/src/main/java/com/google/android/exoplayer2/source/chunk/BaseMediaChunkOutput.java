package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.util.Log;

/* loaded from: classes2.dex */
public final class BaseMediaChunkOutput implements ChunkExtractor.TrackOutputProvider {
    private final int[] a;
    private final SampleQueue[] b;

    public BaseMediaChunkOutput(int[] iArr, SampleQueue[] sampleQueueArr) {
        this.a = iArr;
        this.b = sampleQueueArr;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.TrackOutputProvider
    public TrackOutput track(int i, int i2) {
        int i3 = 0;
        while (true) {
            int[] iArr = this.a;
            if (i3 >= iArr.length) {
                StringBuilder sb = new StringBuilder(36);
                sb.append("Unmatched track of type: ");
                sb.append(i2);
                Log.e("BaseMediaChunkOutput", sb.toString());
                return new DummyTrackOutput();
            } else if (i2 == iArr[i3]) {
                return this.b[i3];
            } else {
                i3++;
            }
        }
    }

    public int[] getWriteIndices() {
        int[] iArr = new int[this.b.length];
        int i = 0;
        while (true) {
            SampleQueue[] sampleQueueArr = this.b;
            if (i >= sampleQueueArr.length) {
                return iArr;
            }
            iArr[i] = sampleQueueArr[i].getWriteIndex();
            i++;
        }
    }

    public void setSampleOffsetUs(long j) {
        for (SampleQueue sampleQueue : this.b) {
            sampleQueue.setSampleOffsetUs(j);
        }
    }
}
