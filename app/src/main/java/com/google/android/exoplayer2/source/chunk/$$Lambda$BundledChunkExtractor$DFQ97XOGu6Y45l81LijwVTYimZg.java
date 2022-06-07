package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.source.chunk.-$$Lambda$BundledChunkExtractor$DFQ97XOGu6Y45l81LijwVTYimZg  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$BundledChunkExtractor$DFQ97XOGu6Y45l81LijwVTYimZg implements ChunkExtractor.Factory {
    public static final /* synthetic */ $$Lambda$BundledChunkExtractor$DFQ97XOGu6Y45l81LijwVTYimZg INSTANCE = new $$Lambda$BundledChunkExtractor$DFQ97XOGu6Y45l81LijwVTYimZg();

    private /* synthetic */ $$Lambda$BundledChunkExtractor$DFQ97XOGu6Y45l81LijwVTYimZg() {
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.Factory
    public final ChunkExtractor createProgressiveMediaExtractor(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
        ChunkExtractor a;
        a = BundledChunkExtractor.a(i, format, z, list, trackOutput);
        return a;
    }
}
