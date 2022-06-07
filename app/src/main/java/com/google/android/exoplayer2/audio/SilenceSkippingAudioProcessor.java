package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class SilenceSkippingAudioProcessor extends BaseAudioProcessor {
    public static final long DEFAULT_MINIMUM_SILENCE_DURATION_US = 150000;
    public static final long DEFAULT_PADDING_SILENCE_US = 20000;
    public static final short DEFAULT_SILENCE_THRESHOLD_LEVEL = 1024;
    private final long a;
    private final long b;
    private final short c;
    private int d;
    private boolean e;
    private byte[] f;
    private byte[] g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private long l;

    public SilenceSkippingAudioProcessor() {
        this(DEFAULT_MINIMUM_SILENCE_DURATION_US, 20000L, DEFAULT_SILENCE_THRESHOLD_LEVEL);
    }

    public SilenceSkippingAudioProcessor(long j, long j2, short s) {
        Assertions.checkArgument(j2 <= j);
        this.a = j;
        this.b = j2;
        this.c = s;
        this.f = Util.EMPTY_BYTE_ARRAY;
        this.g = Util.EMPTY_BYTE_ARRAY;
    }

    public void setEnabled(boolean z) {
        this.e = z;
    }

    public long getSkippedFrames() {
        return this.l;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.encoding == 2) {
            return this.e ? audioFormat : AudioProcessor.AudioFormat.NOT_SET;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor, com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return this.e;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining() && !hasPendingOutput()) {
            switch (this.h) {
                case 0:
                    a(byteBuffer);
                    break;
                case 1:
                    b(byteBuffer);
                    break;
                case 2:
                    c(byteBuffer);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onQueueEndOfStream() {
        int i = this.i;
        if (i > 0) {
            a(this.f, i);
        }
        if (!this.k) {
            this.l += this.j / this.d;
        }
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onFlush() {
        if (this.e) {
            this.d = this.inputAudioFormat.bytesPerFrame;
            int a = a(this.a) * this.d;
            if (this.f.length != a) {
                this.f = new byte[a];
            }
            this.j = a(this.b) * this.d;
            int length = this.g.length;
            int i = this.j;
            if (length != i) {
                this.g = new byte[i];
            }
        }
        this.h = 0;
        this.l = 0L;
        this.i = 0;
        this.k = false;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onReset() {
        this.e = false;
        this.j = 0;
        this.f = Util.EMPTY_BYTE_ARRAY;
        this.g = Util.EMPTY_BYTE_ARRAY;
    }

    private void a(ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        byteBuffer.limit(Math.min(limit, byteBuffer.position() + this.f.length));
        int f = f(byteBuffer);
        if (f == byteBuffer.position()) {
            this.h = 1;
        } else {
            byteBuffer.limit(f);
            d(byteBuffer);
        }
        byteBuffer.limit(limit);
    }

    private void b(ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int e = e(byteBuffer);
        int position = e - byteBuffer.position();
        byte[] bArr = this.f;
        int length = bArr.length;
        int i = this.i;
        int i2 = length - i;
        if (e >= limit || position >= i2) {
            int min = Math.min(position, i2);
            byteBuffer.limit(byteBuffer.position() + min);
            byteBuffer.get(this.f, this.i, min);
            this.i += min;
            int i3 = this.i;
            byte[] bArr2 = this.f;
            if (i3 == bArr2.length) {
                if (this.k) {
                    a(bArr2, this.j);
                    this.l += (this.i - (this.j * 2)) / this.d;
                } else {
                    this.l += (i3 - this.j) / this.d;
                }
                a(byteBuffer, this.f, this.i);
                this.i = 0;
                this.h = 2;
            }
            byteBuffer.limit(limit);
            return;
        }
        a(bArr, i);
        this.i = 0;
        this.h = 0;
    }

    private void c(ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int e = e(byteBuffer);
        byteBuffer.limit(e);
        this.l += byteBuffer.remaining() / this.d;
        a(byteBuffer, this.g, this.j);
        if (e < limit) {
            a(this.g, this.j);
            this.h = 0;
            byteBuffer.limit(limit);
        }
    }

    private void a(byte[] bArr, int i) {
        replaceOutputBuffer(i).put(bArr, 0, i).flip();
        if (i > 0) {
            this.k = true;
        }
    }

    private void d(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        replaceOutputBuffer(remaining).put(byteBuffer).flip();
        if (remaining > 0) {
            this.k = true;
        }
    }

    private void a(ByteBuffer byteBuffer, byte[] bArr, int i) {
        int min = Math.min(byteBuffer.remaining(), this.j);
        int i2 = this.j - min;
        System.arraycopy(bArr, i - i2, this.g, 0, i2);
        byteBuffer.position(byteBuffer.limit() - min);
        byteBuffer.get(this.g, i2, min);
    }

    private int a(long j) {
        return (int) ((j * this.inputAudioFormat.sampleRate) / 1000000);
    }

    private int e(ByteBuffer byteBuffer) {
        for (int position = byteBuffer.position(); position < byteBuffer.limit(); position += 2) {
            if (Math.abs((int) byteBuffer.getShort(position)) > this.c) {
                int i = this.d;
                return i * (position / i);
            }
        }
        return byteBuffer.limit();
    }

    private int f(ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        while (true) {
            limit -= 2;
            if (limit < byteBuffer.position()) {
                return byteBuffer.position();
            }
            if (Math.abs((int) byteBuffer.getShort(limit)) > this.c) {
                int i = this.d;
                return ((limit / i) * i) + i;
            }
        }
    }
}
