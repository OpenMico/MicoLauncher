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
public final class InitializationChunk extends Chunk {
    private final ChunkExtractor a;
    private ChunkExtractor.TrackOutputProvider b;
    private long c;
    private volatile boolean d;

    public InitializationChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, ChunkExtractor chunkExtractor) {
        super(dataSource, dataSpec, 2, format, i, obj, C.TIME_UNSET, C.TIME_UNSET);
        this.a = chunkExtractor;
    }

    public void init(ChunkExtractor.TrackOutputProvider trackOutputProvider) {
        this.b = trackOutputProvider;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void cancelLoad() {
        this.d = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void load() throws IOException {
        if (this.c == 0) {
            this.a.init(this.b, C.TIME_UNSET, C.TIME_UNSET);
        }
        try {
            DataSpec subrange = this.dataSpec.subrange(this.c);
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.dataSource, subrange.position, this.dataSource.open(subrange));
            while (!this.d && this.a.read(defaultExtractorInput)) {
            }
            this.c = defaultExtractorInput.getPosition() - this.dataSpec.position;
        } finally {
            Util.closeQuietly(this.dataSource);
        }
    }
}
