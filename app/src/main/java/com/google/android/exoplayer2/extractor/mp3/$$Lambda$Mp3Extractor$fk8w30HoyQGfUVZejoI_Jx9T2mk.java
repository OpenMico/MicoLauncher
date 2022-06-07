package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.metadata.id3.Id3Decoder;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.extractor.mp3.-$$Lambda$Mp3Extractor$fk8w30HoyQGfUVZejoI_Jx9T2mk  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$Mp3Extractor$fk8w30HoyQGfUVZejoI_Jx9T2mk implements Id3Decoder.FramePredicate {
    public static final /* synthetic */ $$Lambda$Mp3Extractor$fk8w30HoyQGfUVZejoI_Jx9T2mk INSTANCE = new $$Lambda$Mp3Extractor$fk8w30HoyQGfUVZejoI_Jx9T2mk();

    private /* synthetic */ $$Lambda$Mp3Extractor$fk8w30HoyQGfUVZejoI_Jx9T2mk() {
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        boolean a;
        a = Mp3Extractor.a(i, i2, i3, i4, i5);
        return a;
    }
}
