package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class SingleSampleMediaChunk extends BaseMediaChunk {
    private final int a;
    private final Format b;
    private long c;
    private boolean d;

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void cancelLoad() {
    }

    public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, long j, long j2, long j3, int i2, Format format2) {
        super(dataSource, dataSpec, format, i, obj, j, j2, C.TIME_UNSET, C.TIME_UNSET, j3);
        this.a = i2;
        this.b = format2;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunk
    public boolean isLoadCompleted() {
        return this.d;
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void load() throws IOException {
        BaseMediaChunkOutput output = getOutput();
        output.setSampleOffsetUs(0L);
        TrackOutput track = output.track(0, this.a);
        track.format(this.b);
        try {
            long open = this.dataSource.open(this.dataSpec.subrange(this.c));
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.dataSource, this.c, open != -1 ? open + this.c : open);
            for (int i = 0; i != -1; i = track.sampleData((DataReader) defaultExtractorInput, Integer.MAX_VALUE, true)) {
                this.c += i;
            }
            track.sampleMetadata(this.startTimeUs, 1, (int) this.c, 0, null);
            Util.closeQuietly(this.dataSource);
            this.d = true;
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
            throw th;
        }
    }
}
