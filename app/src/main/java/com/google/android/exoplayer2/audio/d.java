package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* compiled from: ResamplingAudioProcessor.java */
/* loaded from: classes.dex */
final class d extends BaseAudioProcessor {
    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        int i = audioFormat.encoding;
        if (i != 3 && i != 2 && i != 268435456 && i != 536870912 && i != 805306368 && i != 4) {
            throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
        } else if (i != 2) {
            return new AudioProcessor.AudioFormat(audioFormat.sampleRate, audioFormat.channelCount, 2);
        } else {
            return AudioProcessor.AudioFormat.NOT_SET;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i = limit - position;
        int i2 = this.inputAudioFormat.encoding;
        if (i2 != 268435456) {
            if (i2 != 536870912) {
                if (i2 != 805306368) {
                    switch (i2) {
                        case 3:
                            i *= 2;
                            break;
                        case 4:
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }
                i /= 2;
            } else {
                i = (i / 3) * 2;
            }
        }
        ByteBuffer replaceOutputBuffer = replaceOutputBuffer(i);
        int i3 = this.inputAudioFormat.encoding;
        if (i3 == 268435456) {
            while (position < limit) {
                replaceOutputBuffer.put(byteBuffer.get(position + 1));
                replaceOutputBuffer.put(byteBuffer.get(position));
                position += 2;
            }
        } else if (i3 == 536870912) {
            while (position < limit) {
                replaceOutputBuffer.put(byteBuffer.get(position + 1));
                replaceOutputBuffer.put(byteBuffer.get(position + 2));
                position += 3;
            }
        } else if (i3 != 805306368) {
            switch (i3) {
                case 3:
                    while (position < limit) {
                        replaceOutputBuffer.put((byte) 0);
                        replaceOutputBuffer.put((byte) ((byteBuffer.get(position) & 255) - 128));
                        position++;
                    }
                    break;
                case 4:
                    while (position < limit) {
                        short constrainValue = (short) (Util.constrainValue(byteBuffer.getFloat(position), -1.0f, 1.0f) * 32767.0f);
                        replaceOutputBuffer.put((byte) (constrainValue & 255));
                        replaceOutputBuffer.put((byte) ((constrainValue >> 8) & 255));
                        position += 4;
                    }
                    break;
                default:
                    throw new IllegalStateException();
            }
        } else {
            while (position < limit) {
                replaceOutputBuffer.put(byteBuffer.get(position + 2));
                replaceOutputBuffer.put(byteBuffer.get(position + 3));
                position += 4;
            }
        }
        byteBuffer.position(byteBuffer.limit());
        replaceOutputBuffer.flip();
    }
}
