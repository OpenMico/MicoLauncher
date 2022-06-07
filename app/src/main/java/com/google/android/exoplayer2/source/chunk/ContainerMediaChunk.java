package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes2.dex */
public class ContainerMediaChunk extends BaseMediaChunk {
    private final int a;
    private final long b;
    private final ChunkExtractor c;
    private long d;
    private volatile boolean e;
    private boolean f;

    protected ChunkExtractor.TrackOutputProvider getTrackOutputProvider(BaseMediaChunkOutput baseMediaChunkOutput) {
        return baseMediaChunkOutput;
    }

    public ContainerMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, long j, long j2, long j3, long j4, long j5, int i2, long j6, ChunkExtractor chunkExtractor) {
        super(dataSource, dataSpec, format, i, obj, j, j2, j3, j4, j5);
        this.a = i2;
        this.b = j6;
        this.c = chunkExtractor;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunk
    public long getNextChunkIndex() {
        return this.chunkIndex + this.a;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunk
    public boolean isLoadCompleted() {
        return this.f;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void cancelLoad() {
        this.e = true;
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void load() throws IOException {
        if (this.d == 0) {
            BaseMediaChunkOutput output = getOutput();
            output.setSampleOffsetUs(this.b);
            ChunkExtractor chunkExtractor = this.c;
            ChunkExtractor.TrackOutputProvider trackOutputProvider = getTrackOutputProvider(output);
            long j = this.clippedStartTimeUs;
            long j2 = C.TIME_UNSET;
            long j3 = j == C.TIME_UNSET ? -9223372036854775807L : this.clippedStartTimeUs - this.b;
            if (this.clippedEndTimeUs != C.TIME_UNSET) {
                j2 = this.clippedEndTimeUs - this.b;
            }
            chunkExtractor.init(trackOutputProvider, j3, j2);
        }
        try {
            DataSpec subrange = this.dataSpec.subrange(this.d);
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.dataSource, subrange.position, this.dataSource.open(subrange));
            while (!this.e && this.c.read(defaultExtractorInput)) {
            }
            this.d = defaultExtractorInput.getPosition() - this.dataSpec.position;
            Util.closeQuietly(this.dataSource);
            this.f = !this.e;
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
            throw th;
        }
    }
}
