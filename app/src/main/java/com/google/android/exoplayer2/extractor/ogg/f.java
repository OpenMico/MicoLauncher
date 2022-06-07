package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.google.android.exoplayer2.extractor.ogg.g;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;
import okio.Utf8;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* compiled from: OpusReader.java */
/* loaded from: classes2.dex */
final class f extends g {
    private static final byte[] a = {79, 112, 117, 115, 72, 101, 97, 100};
    private boolean b;

    public static boolean a(ParsableByteArray parsableByteArray) {
        int bytesLeft = parsableByteArray.bytesLeft();
        byte[] bArr = a;
        if (bytesLeft < bArr.length) {
            return false;
        }
        byte[] bArr2 = new byte[bArr.length];
        parsableByteArray.readBytes(bArr2, 0, bArr.length);
        return Arrays.equals(bArr2, a);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.extractor.ogg.g
    public void a(boolean z) {
        super.a(z);
        if (z) {
            this.b = false;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.g
    protected long b(ParsableByteArray parsableByteArray) {
        return b(a(parsableByteArray.getData()));
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.g
    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    protected boolean a(ParsableByteArray parsableByteArray, long j, g.a aVar) {
        boolean z = true;
        if (!this.b) {
            byte[] copyOf = Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit());
            aVar.a = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_OPUS).setChannelCount(OpusUtil.getChannelCount(copyOf)).setSampleRate(OpusUtil.SAMPLE_RATE).setInitializationData(OpusUtil.buildInitializationData(copyOf)).build();
            this.b = true;
            return true;
        }
        Assertions.checkNotNull(aVar.a);
        if (parsableByteArray.readInt() != 1332770163) {
            z = false;
        }
        parsableByteArray.setPosition(0);
        return z;
    }

    private long a(byte[] bArr) {
        int i;
        int i2 = bArr[0] & 255;
        switch (i2 & 3) {
            case 0:
                i = 1;
                break;
            case 1:
            case 2:
                i = 2;
                break;
            default:
                i = bArr[1] & Utf8.REPLACEMENT_BYTE;
                break;
        }
        int i3 = i2 >> 3;
        int i4 = i3 & 3;
        return i * (i3 >= 16 ? DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS << i4 : i3 >= 12 ? 10000 << (i4 & 1) : i4 == 3 ? 60000 : 10000 << i4);
    }
}
