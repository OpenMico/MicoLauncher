package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* compiled from: TrimmingAudioProcessor.java */
/* loaded from: classes.dex */
final class f extends BaseAudioProcessor {
    private int a;
    private int b;
    private boolean c;
    private int d;
    private byte[] e = Util.EMPTY_BYTE_ARRAY;
    private int f;
    private long g;

    public void a(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public void a() {
        this.g = 0L;
    }

    public long b() {
        return this.g;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.encoding == 2) {
            this.c = true;
            return (this.a == 0 && this.b == 0) ? AudioProcessor.AudioFormat.NOT_SET : audioFormat;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i = limit - position;
        if (i != 0) {
            int min = Math.min(i, this.d);
            this.g += min / this.inputAudioFormat.bytesPerFrame;
            this.d -= min;
            byteBuffer.position(position + min);
            if (this.d <= 0) {
                int i2 = i - min;
                int length = (this.f + i2) - this.e.length;
                ByteBuffer replaceOutputBuffer = replaceOutputBuffer(length);
                int constrainValue = Util.constrainValue(length, 0, this.f);
                replaceOutputBuffer.put(this.e, 0, constrainValue);
                int constrainValue2 = Util.constrainValue(length - constrainValue, 0, i2);
                byteBuffer.limit(byteBuffer.position() + constrainValue2);
                replaceOutputBuffer.put(byteBuffer);
                byteBuffer.limit(limit);
                int i3 = i2 - constrainValue2;
                this.f -= constrainValue;
                byte[] bArr = this.e;
                System.arraycopy(bArr, constrainValue, bArr, 0, this.f);
                byteBuffer.get(this.e, this.f, i3);
                this.f += i3;
                replaceOutputBuffer.flip();
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor, com.google.android.exoplayer2.audio.AudioProcessor
    public ByteBuffer getOutput() {
        int i;
        if (super.isEnded() && (i = this.f) > 0) {
            replaceOutputBuffer(i).put(this.e, 0, this.f).flip();
            this.f = 0;
        }
        return super.getOutput();
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor, com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isEnded() {
        return super.isEnded() && this.f == 0;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onQueueEndOfStream() {
        if (this.c) {
            int i = this.f;
            if (i > 0) {
                this.g += i / this.inputAudioFormat.bytesPerFrame;
            }
            this.f = 0;
        }
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onFlush() {
        if (this.c) {
            this.c = false;
            this.e = new byte[this.b * this.inputAudioFormat.bytesPerFrame];
            this.d = this.a * this.inputAudioFormat.bytesPerFrame;
        }
        this.f = 0;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onReset() {
        this.e = Util.EMPTY_BYTE_ARRAY;
    }
}
