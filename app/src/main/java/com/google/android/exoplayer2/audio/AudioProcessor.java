package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public interface AudioProcessor {
    public static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder());

    AudioFormat configure(AudioFormat audioFormat) throws UnhandledAudioFormatException;

    void flush();

    ByteBuffer getOutput();

    boolean isActive();

    boolean isEnded();

    void queueEndOfStream();

    void queueInput(ByteBuffer byteBuffer);

    void reset();

    /* loaded from: classes.dex */
    public static final class AudioFormat {
        public static final AudioFormat NOT_SET = new AudioFormat(-1, -1, -1);
        public final int bytesPerFrame;
        public final int channelCount;
        public final int encoding;
        public final int sampleRate;

        public AudioFormat(int i, int i2, int i3) {
            this.sampleRate = i;
            this.channelCount = i2;
            this.encoding = i3;
            this.bytesPerFrame = Util.isEncodingLinearPcm(i3) ? Util.getPcmFrameSize(i3, i2) : -1;
        }

        public String toString() {
            int i = this.sampleRate;
            int i2 = this.channelCount;
            int i3 = this.encoding;
            StringBuilder sb = new StringBuilder(83);
            sb.append("AudioFormat[sampleRate=");
            sb.append(i);
            sb.append(", channelCount=");
            sb.append(i2);
            sb.append(", encoding=");
            sb.append(i3);
            sb.append(']');
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    public static final class UnhandledAudioFormatException extends Exception {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public UnhandledAudioFormatException(com.google.android.exoplayer2.audio.AudioProcessor.AudioFormat r3) {
            /*
                r2 = this;
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r0 = java.lang.String.valueOf(r3)
                int r0 = r0.length()
                int r0 = r0 + 18
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>(r0)
                java.lang.String r0 = "Unhandled format: "
                r1.append(r0)
                r1.append(r3)
                java.lang.String r3 = r1.toString()
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioProcessor.UnhandledAudioFormatException.<init>(com.google.android.exoplayer2.audio.AudioProcessor$AudioFormat):void");
        }
    }
}
