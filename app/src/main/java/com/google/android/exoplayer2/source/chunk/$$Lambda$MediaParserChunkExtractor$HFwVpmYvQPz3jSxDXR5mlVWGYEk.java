package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.source.chunk.-$$Lambda$MediaParserChunkExtractor$HFwVpmYvQPz3jSxDXR5mlVWGYEk  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$MediaParserChunkExtractor$HFwVpmYvQPz3jSxDXR5mlVWGYEk implements ChunkExtractor.Factory {
    public static final /* synthetic */ $$Lambda$MediaParserChunkExtractor$HFwVpmYvQPz3jSxDXR5mlVWGYEk INSTANCE = new $$Lambda$MediaParserChunkExtractor$HFwVpmYvQPz3jSxDXR5mlVWGYEk();

    private /* synthetic */ $$Lambda$MediaParserChunkExtractor$HFwVpmYvQPz3jSxDXR5mlVWGYEk() {
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.Factory
    public final ChunkExtractor createProgressiveMediaExtractor(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
        ChunkExtractor a;
        a = MediaParserChunkExtractor.a(i, format, z, list, trackOutput);
        return a;
    }
}
