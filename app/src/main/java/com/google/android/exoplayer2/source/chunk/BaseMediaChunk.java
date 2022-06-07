package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes2.dex */
public abstract class BaseMediaChunk extends MediaChunk {
    private BaseMediaChunkOutput a;
    private int[] b;
    public final long clippedEndTimeUs;
    public final long clippedStartTimeUs;

    public BaseMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, long j, long j2, long j3, long j4, long j5) {
        super(dataSource, dataSpec, format, i, obj, j, j2, j5);
        this.clippedStartTimeUs = j3;
        this.clippedEndTimeUs = j4;
    }

    public void init(BaseMediaChunkOutput baseMediaChunkOutput) {
        this.a = baseMediaChunkOutput;
        this.b = baseMediaChunkOutput.getWriteIndices();
    }

    public final int getFirstSampleIndex(int i) {
        return ((int[]) Assertions.checkStateNotNull(this.b))[i];
    }

    protected final BaseMediaChunkOutput getOutput() {
        return (BaseMediaChunkOutput) Assertions.checkStateNotNull(this.a);
    }
}
