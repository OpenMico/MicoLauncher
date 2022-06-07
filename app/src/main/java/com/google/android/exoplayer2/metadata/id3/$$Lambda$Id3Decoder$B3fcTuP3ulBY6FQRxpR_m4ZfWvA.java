package com.google.android.exoplayer2.metadata.id3;

import com.google.android.exoplayer2.metadata.id3.Id3Decoder;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.metadata.id3.-$$Lambda$Id3Decoder$B3fcTuP3ulBY6FQRxpR_m4ZfWvA  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$Id3Decoder$B3fcTuP3ulBY6FQRxpR_m4ZfWvA implements Id3Decoder.FramePredicate {
    public static final /* synthetic */ $$Lambda$Id3Decoder$B3fcTuP3ulBY6FQRxpR_m4ZfWvA INSTANCE = new $$Lambda$Id3Decoder$B3fcTuP3ulBY6FQRxpR_m4ZfWvA();

    private /* synthetic */ $$Lambda$Id3Decoder$B3fcTuP3ulBY6FQRxpR_m4ZfWvA() {
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        boolean b;
        b = Id3Decoder.b(i, i2, i3, i4, i5);
        return b;
    }
}
