package com.google.android.exoplayer2.text;

import com.google.android.exoplayer2.decoder.OutputBuffer;

/* compiled from: SimpleSubtitleOutputBuffer.java */
/* loaded from: classes2.dex */
final class a extends SubtitleOutputBuffer {
    private final OutputBuffer.Owner<SubtitleOutputBuffer> a;

    public a(OutputBuffer.Owner<SubtitleOutputBuffer> owner) {
        this.a = owner;
    }

    @Override // com.google.android.exoplayer2.decoder.OutputBuffer
    public final void release() {
        this.a.releaseOutputBuffer(this);
    }
}
