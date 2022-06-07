package com.google.android.exoplayer2.audio;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/* loaded from: classes.dex */
public final class SonicAudioProcessor implements AudioProcessor {
    public static final int SAMPLE_RATE_NO_CHANGE = -1;
    private boolean h;
    @Nullable
    private e i;
    private long m;
    private long n;
    private boolean o;
    private float b = 1.0f;
    private float c = 1.0f;
    private AudioProcessor.AudioFormat d = AudioProcessor.AudioFormat.NOT_SET;
    private AudioProcessor.AudioFormat e = AudioProcessor.AudioFormat.NOT_SET;
    private AudioProcessor.AudioFormat f = AudioProcessor.AudioFormat.NOT_SET;
    private AudioProcessor.AudioFormat g = AudioProcessor.AudioFormat.NOT_SET;
    private ByteBuffer j = EMPTY_BUFFER;
    private ShortBuffer k = this.j.asShortBuffer();
    private ByteBuffer l = EMPTY_BUFFER;
    private int a = -1;

    public void setSpeed(float f) {
        if (this.b != f) {
            this.b = f;
            this.h = true;
        }
    }

    public void setPitch(float f) {
        if (this.c != f) {
            this.c = f;
            this.h = true;
        }
    }

    public void setOutputSampleRateHz(int i) {
        this.a = i;
    }

    public long getMediaDuration(long j) {
        if (this.n < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return (long) (this.b * j);
        }
        long a = this.m - ((e) Assertions.checkNotNull(this.i)).a();
        if (this.g.sampleRate == this.f.sampleRate) {
            return Util.scaleLargeTimestamp(j, a, this.n);
        }
        return Util.scaleLargeTimestamp(j, a * this.g.sampleRate, this.n * this.f.sampleRate);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public AudioProcessor.AudioFormat configure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.encoding == 2) {
            int i = this.a;
            if (i == -1) {
                i = audioFormat.sampleRate;
            }
            this.d = audioFormat;
            this.e = new AudioProcessor.AudioFormat(i, audioFormat.channelCount, 2);
            this.h = true;
            return this.e;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return this.e.sampleRate != -1 && (Math.abs(this.b - 1.0f) >= 1.0E-4f || Math.abs(this.c - 1.0f) >= 1.0E-4f || this.e.sampleRate != this.d.sampleRate);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        if (byteBuffer.hasRemaining()) {
            ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
            int remaining = byteBuffer.remaining();
            this.m += remaining;
            ((e) Assertions.checkNotNull(this.i)).a(asShortBuffer);
            byteBuffer.position(byteBuffer.position() + remaining);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueEndOfStream() {
        e eVar = this.i;
        if (eVar != null) {
            eVar.b();
        }
        this.o = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public ByteBuffer getOutput() {
        int d;
        e eVar = this.i;
        if (eVar != null && (d = eVar.d()) > 0) {
            if (this.j.capacity() < d) {
                this.j = ByteBuffer.allocateDirect(d).order(ByteOrder.nativeOrder());
                this.k = this.j.asShortBuffer();
            } else {
                this.j.clear();
                this.k.clear();
            }
            eVar.b(this.k);
            this.n += d;
            this.j.limit(d);
            this.l = this.j;
        }
        ByteBuffer byteBuffer = this.l;
        this.l = EMPTY_BUFFER;
        return byteBuffer;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isEnded() {
        e eVar;
        return this.o && ((eVar = this.i) == null || eVar.d() == 0);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void flush() {
        if (isActive()) {
            this.f = this.d;
            this.g = this.e;
            if (this.h) {
                this.i = new e(this.f.sampleRate, this.f.channelCount, this.b, this.c, this.g.sampleRate);
            } else {
                e eVar = this.i;
                if (eVar != null) {
                    eVar.c();
                }
            }
        }
        this.l = EMPTY_BUFFER;
        this.m = 0L;
        this.n = 0L;
        this.o = false;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void reset() {
        this.b = 1.0f;
        this.c = 1.0f;
        this.d = AudioProcessor.AudioFormat.NOT_SET;
        this.e = AudioProcessor.AudioFormat.NOT_SET;
        this.f = AudioProcessor.AudioFormat.NOT_SET;
        this.g = AudioProcessor.AudioFormat.NOT_SET;
        this.j = EMPTY_BUFFER;
        this.k = this.j.asShortBuffer();
        this.l = EMPTY_BUFFER;
        this.a = -1;
        this.h = false;
        this.i = null;
        this.m = 0L;
        this.n = 0L;
        this.o = false;
    }
}
