package com.google.android.exoplayer2.audio;

import androidx.annotation.CallSuper;
import com.google.android.exoplayer2.audio.AudioProcessor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public abstract class BaseAudioProcessor implements AudioProcessor {
    private boolean e;
    private ByteBuffer c = EMPTY_BUFFER;
    private ByteBuffer d = EMPTY_BUFFER;
    private AudioProcessor.AudioFormat a = AudioProcessor.AudioFormat.NOT_SET;
    private AudioProcessor.AudioFormat b = AudioProcessor.AudioFormat.NOT_SET;
    protected AudioProcessor.AudioFormat inputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
    protected AudioProcessor.AudioFormat outputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;

    protected void onFlush() {
    }

    protected void onQueueEndOfStream() {
    }

    protected void onReset() {
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public final AudioProcessor.AudioFormat configure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        this.a = audioFormat;
        this.b = onConfigure(audioFormat);
        return isActive() ? this.b : AudioProcessor.AudioFormat.NOT_SET;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return this.b != AudioProcessor.AudioFormat.NOT_SET;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public final void queueEndOfStream() {
        this.e = true;
        onQueueEndOfStream();
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    @CallSuper
    public ByteBuffer getOutput() {
        ByteBuffer byteBuffer = this.d;
        this.d = EMPTY_BUFFER;
        return byteBuffer;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    @CallSuper
    public boolean isEnded() {
        return this.e && this.d == EMPTY_BUFFER;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public final void flush() {
        this.d = EMPTY_BUFFER;
        this.e = false;
        this.inputAudioFormat = this.a;
        this.outputAudioFormat = this.b;
        onFlush();
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public final void reset() {
        flush();
        this.c = EMPTY_BUFFER;
        this.a = AudioProcessor.AudioFormat.NOT_SET;
        this.b = AudioProcessor.AudioFormat.NOT_SET;
        this.inputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
        this.outputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
        onReset();
    }

    protected final ByteBuffer replaceOutputBuffer(int i) {
        if (this.c.capacity() < i) {
            this.c = ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
        } else {
            this.c.clear();
        }
        ByteBuffer byteBuffer = this.c;
        this.d = byteBuffer;
        return byteBuffer;
    }

    protected final boolean hasPendingOutput() {
        return this.d.hasRemaining();
    }

    protected AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        return AudioProcessor.AudioFormat.NOT_SET;
    }
}
