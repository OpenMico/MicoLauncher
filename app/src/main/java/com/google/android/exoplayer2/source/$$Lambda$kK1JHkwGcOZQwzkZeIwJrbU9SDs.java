package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.source.-$$Lambda$kK1JHkwGcOZQwzkZeIwJrbU9SDs */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$kK1JHkwGcOZQwzkZeIwJrbU9SDs implements ProgressiveMediaExtractor.Factory {
    public static final /* synthetic */ $$Lambda$kK1JHkwGcOZQwzkZeIwJrbU9SDs INSTANCE = new $$Lambda$kK1JHkwGcOZQwzkZeIwJrbU9SDs();

    private /* synthetic */ $$Lambda$kK1JHkwGcOZQwzkZeIwJrbU9SDs() {
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
    public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
        return new MediaParserExtractorAdapter();
    }
}
