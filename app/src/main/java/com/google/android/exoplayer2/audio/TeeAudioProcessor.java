package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class TeeAudioProcessor extends BaseAudioProcessor {
    private final AudioBufferSink a;

    /* loaded from: classes.dex */
    public interface AudioBufferSink {
        void flush(int i, int i2, int i3);

        void handleBuffer(ByteBuffer byteBuffer);
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) {
        return audioFormat;
    }

    public TeeAudioProcessor(AudioBufferSink audioBufferSink) {
        this.a = (AudioBufferSink) Assertions.checkNotNull(audioBufferSink);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        if (remaining != 0) {
            this.a.handleBuffer(byteBuffer.asReadOnlyBuffer());
            replaceOutputBuffer(remaining).put(byteBuffer).flip();
        }
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onFlush() {
        a();
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onQueueEndOfStream() {
        a();
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onReset() {
        a();
    }

    private void a() {
        if (isActive()) {
            this.a.flush(this.inputAudioFormat.sampleRate, this.inputAudioFormat.channelCount, this.inputAudioFormat.encoding);
        }
    }

    /* loaded from: classes.dex */
    public static final class WavFileAudioBufferSink implements AudioBufferSink {
        private final String a;
        private final byte[] b = new byte[1024];
        private final ByteBuffer c = ByteBuffer.wrap(this.b).order(ByteOrder.LITTLE_ENDIAN);
        private int d;
        private int e;
        private int f;
        @Nullable
        private RandomAccessFile g;
        private int h;
        private int i;

        public WavFileAudioBufferSink(String str) {
            this.a = str;
        }

        @Override // com.google.android.exoplayer2.audio.TeeAudioProcessor.AudioBufferSink
        public void flush(int i, int i2, int i3) {
            try {
                b();
            } catch (IOException e) {
                Log.e("WaveFileAudioBufferSink", "Error resetting", e);
            }
            this.d = i;
            this.e = i2;
            this.f = i3;
        }

        @Override // com.google.android.exoplayer2.audio.TeeAudioProcessor.AudioBufferSink
        public void handleBuffer(ByteBuffer byteBuffer) {
            try {
                a();
                a(byteBuffer);
            } catch (IOException e) {
                Log.e("WaveFileAudioBufferSink", "Error writing data", e);
            }
        }

        private void a() throws IOException {
            if (this.g == null) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(c(), "rw");
                a(randomAccessFile);
                this.g = randomAccessFile;
                this.i = 44;
            }
        }

        private void a(RandomAccessFile randomAccessFile) throws IOException {
            randomAccessFile.writeInt(WavUtil.RIFF_FOURCC);
            randomAccessFile.writeInt(-1);
            randomAccessFile.writeInt(WavUtil.WAVE_FOURCC);
            randomAccessFile.writeInt(WavUtil.FMT_FOURCC);
            this.c.clear();
            this.c.putInt(16);
            this.c.putShort((short) WavUtil.getTypeForPcmEncoding(this.f));
            this.c.putShort((short) this.e);
            this.c.putInt(this.d);
            int pcmFrameSize = Util.getPcmFrameSize(this.f, this.e);
            this.c.putInt(this.d * pcmFrameSize);
            this.c.putShort((short) pcmFrameSize);
            this.c.putShort((short) ((pcmFrameSize * 8) / this.e));
            randomAccessFile.write(this.b, 0, this.c.position());
            randomAccessFile.writeInt(WavUtil.DATA_FOURCC);
            randomAccessFile.writeInt(-1);
        }

        private void a(ByteBuffer byteBuffer) throws IOException {
            RandomAccessFile randomAccessFile = (RandomAccessFile) Assertions.checkNotNull(this.g);
            while (byteBuffer.hasRemaining()) {
                int min = Math.min(byteBuffer.remaining(), this.b.length);
                byteBuffer.get(this.b, 0, min);
                randomAccessFile.write(this.b, 0, min);
                this.i += min;
            }
        }

        private void b() throws IOException {
            RandomAccessFile randomAccessFile = this.g;
            if (randomAccessFile != null) {
                try {
                    this.c.clear();
                    this.c.putInt(this.i - 8);
                    randomAccessFile.seek(4L);
                    randomAccessFile.write(this.b, 0, 4);
                    this.c.clear();
                    this.c.putInt(this.i - 44);
                    randomAccessFile.seek(40L);
                    randomAccessFile.write(this.b, 0, 4);
                } catch (IOException e) {
                    Log.w("WaveFileAudioBufferSink", "Error updating file size", e);
                }
                try {
                    randomAccessFile.close();
                } finally {
                    this.g = null;
                }
            }
        }

        private String c() {
            int i = this.h;
            this.h = i + 1;
            return Util.formatInvariant("%s-%04d.wav", this.a, Integer.valueOf(i));
        }
    }
}
