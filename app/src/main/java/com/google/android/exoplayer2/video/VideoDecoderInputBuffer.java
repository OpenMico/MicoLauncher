package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;

/* loaded from: classes2.dex */
public class VideoDecoderInputBuffer extends DecoderInputBuffer {
    @Nullable
    public Format format;

    public VideoDecoderInputBuffer(int i) {
        super(i);
    }

    public VideoDecoderInputBuffer(int i, int i2) {
        super(i, i2);
    }
}
