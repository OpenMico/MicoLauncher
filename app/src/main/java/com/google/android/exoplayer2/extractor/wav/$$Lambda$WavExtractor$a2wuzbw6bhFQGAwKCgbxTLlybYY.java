package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.extractor.wav.-$$Lambda$WavExtractor$a2wuzbw6bhFQGAwKCgbxTLlybYY  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$WavExtractor$a2wuzbw6bhFQGAwKCgbxTLlybYY implements ExtractorsFactory {
    public static final /* synthetic */ $$Lambda$WavExtractor$a2wuzbw6bhFQGAwKCgbxTLlybYY INSTANCE = new $$Lambda$WavExtractor$a2wuzbw6bhFQGAwKCgbxTLlybYY();

    private /* synthetic */ $$Lambda$WavExtractor$a2wuzbw6bhFQGAwKCgbxTLlybYY() {
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final Extractor[] createExtractors() {
        Extractor[] b;
        b = WavExtractor.b();
        return b;
    }
}
