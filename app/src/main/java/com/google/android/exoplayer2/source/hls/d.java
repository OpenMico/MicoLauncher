package com.google.android.exoplayer2.source.hls;

import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* compiled from: HlsSampleStream.java */
/* loaded from: classes2.dex */
final class d implements SampleStream {
    private final int a;
    private final HlsSampleStreamWrapper b;
    private int c = -1;

    public d(HlsSampleStreamWrapper hlsSampleStreamWrapper, int i) {
        this.b = hlsSampleStreamWrapper;
        this.a = i;
    }

    public void a() {
        Assertions.checkArgument(this.c == -1);
        this.c = this.b.a(this.a);
    }

    public void b() {
        if (this.c != -1) {
            this.b.b(this.a);
            this.c = -1;
        }
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public boolean isReady() {
        return this.c == -3 || (c() && this.b.c(this.c));
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public void maybeThrowError() throws IOException {
        int i = this.c;
        if (i == -2) {
            throw new SampleQueueMappingException(this.b.c().get(this.a).getFormat(0).sampleMimeType);
        } else if (i == -1) {
            this.b.g();
        } else if (i != -3) {
            this.b.d(i);
        }
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
        if (this.c == -3) {
            decoderInputBuffer.addFlag(4);
            return -4;
        } else if (c()) {
            return this.b.a(this.c, formatHolder, decoderInputBuffer, i);
        } else {
            return -3;
        }
    }

    @Override // com.google.android.exoplayer2.source.SampleStream
    public int skipData(long j) {
        if (c()) {
            return this.b.a(this.c, j);
        }
        return 0;
    }

    private boolean c() {
        int i = this.c;
        return (i == -1 || i == -3 || i == -2) ? false : true;
    }
}
