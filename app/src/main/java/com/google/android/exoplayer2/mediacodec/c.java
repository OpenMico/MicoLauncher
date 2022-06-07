package com.google.android.exoplayer2.mediacodec;

import androidx.annotation.IntRange;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

/* compiled from: BatchBuffer.java */
/* loaded from: classes2.dex */
final class c extends DecoderInputBuffer {
    private long a;
    private int b;
    private int c = 32;

    public c() {
        super(2);
    }

    @Override // com.google.android.exoplayer2.decoder.DecoderInputBuffer, com.google.android.exoplayer2.decoder.Buffer
    public void clear() {
        super.clear();
        this.b = 0;
    }

    public void a(@IntRange(from = 1) int i) {
        Assertions.checkArgument(i > 0);
        this.c = i;
    }

    public long a() {
        return this.timeUs;
    }

    public long b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public boolean d() {
        return this.b > 0;
    }

    public boolean a(DecoderInputBuffer decoderInputBuffer) {
        Assertions.checkArgument(!decoderInputBuffer.isEncrypted());
        Assertions.checkArgument(!decoderInputBuffer.hasSupplementalData());
        Assertions.checkArgument(!decoderInputBuffer.isEndOfStream());
        if (!b(decoderInputBuffer)) {
            return false;
        }
        int i = this.b;
        this.b = i + 1;
        if (i == 0) {
            this.timeUs = decoderInputBuffer.timeUs;
            if (decoderInputBuffer.isKeyFrame()) {
                setFlags(1);
            }
        }
        if (decoderInputBuffer.isDecodeOnly()) {
            setFlags(Integer.MIN_VALUE);
        }
        ByteBuffer byteBuffer = decoderInputBuffer.data;
        if (byteBuffer != null) {
            ensureSpaceForWrite(byteBuffer.remaining());
            this.data.put(byteBuffer);
        }
        this.a = decoderInputBuffer.timeUs;
        return true;
    }

    private boolean b(DecoderInputBuffer decoderInputBuffer) {
        if (!d()) {
            return true;
        }
        if (this.b >= this.c || decoderInputBuffer.isDecodeOnly() != isDecodeOnly()) {
            return false;
        }
        ByteBuffer byteBuffer = decoderInputBuffer.data;
        return byteBuffer == null || this.data == null || this.data.position() + byteBuffer.remaining() <= 3072000;
    }
}
