package com.google.android.exoplayer2.mediacodec;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.nio.ByteBuffer;

/* compiled from: C2Mp3TimestampTracker.java */
/* loaded from: classes2.dex */
final class d {
    private long a;
    private long b;
    private boolean c;

    public void a() {
        this.a = 0L;
        this.b = 0L;
        this.c = false;
    }

    public long a(Format format, DecoderInputBuffer decoderInputBuffer) {
        if (this.b == 0) {
            this.a = decoderInputBuffer.timeUs;
        }
        if (this.c) {
            return decoderInputBuffer.timeUs;
        }
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.data);
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i = (i << 8) | (byteBuffer.get(i2) & 255);
        }
        int parseMpegAudioFrameSampleCount = MpegAudioUtil.parseMpegAudioFrameSampleCount(i);
        if (parseMpegAudioFrameSampleCount == -1) {
            this.c = true;
            this.b = 0L;
            this.a = decoderInputBuffer.timeUs;
            Log.w("C2Mp3TimestampTracker", "MPEG audio header is invalid.");
            return decoderInputBuffer.timeUs;
        }
        long a = a(format.sampleRate);
        this.b += parseMpegAudioFrameSampleCount;
        return a;
    }

    public long a(Format format) {
        return a(format.sampleRate);
    }

    private long a(long j) {
        return this.a + Math.max(0L, ((this.b - 529) * 1000000) / j);
    }
}
