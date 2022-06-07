package com.google.android.exoplayer2.extractor.ogg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.SeekMap;
import java.io.IOException;

/* compiled from: OggSeeker.java */
/* loaded from: classes2.dex */
interface e {
    long a(ExtractorInput extractorInput) throws IOException;

    void a(long j);

    @Nullable
    SeekMap b();
}
