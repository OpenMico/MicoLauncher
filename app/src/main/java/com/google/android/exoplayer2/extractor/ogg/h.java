package com.google.android.exoplayer2.extractor.ogg;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.VorbisUtil;
import com.google.android.exoplayer2.extractor.ogg.g;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* compiled from: VorbisReader.java */
/* loaded from: classes2.dex */
final class h extends g {
    @Nullable
    private a a;
    private int b;
    private boolean c;
    @Nullable
    private VorbisUtil.VorbisIdHeader d;
    @Nullable
    private VorbisUtil.CommentHeader e;

    @VisibleForTesting
    static int a(byte b, int i, int i2) {
        return (b >> i2) & (255 >>> (8 - i));
    }

    public static boolean a(ParsableByteArray parsableByteArray) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, parsableByteArray, true);
        } catch (ParserException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.extractor.ogg.g
    public void a(boolean z) {
        super.a(z);
        if (z) {
            this.a = null;
            this.d = null;
            this.e = null;
        }
        this.b = 0;
        this.c = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.extractor.ogg.g
    public void c(long j) {
        super.c(j);
        int i = 0;
        this.c = j != 0;
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.d;
        if (vorbisIdHeader != null) {
            i = vorbisIdHeader.blockSize0;
        }
        this.b = i;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.g
    protected long b(ParsableByteArray parsableByteArray) {
        int i = 0;
        if ((parsableByteArray.getData()[0] & 1) == 1) {
            return -1L;
        }
        int a2 = a(parsableByteArray.getData()[0], (a) Assertions.checkStateNotNull(this.a));
        if (this.c) {
            i = (this.b + a2) / 4;
        }
        long j = i;
        a(parsableByteArray, j);
        this.c = true;
        this.b = a2;
        return j;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.g
    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    protected boolean a(ParsableByteArray parsableByteArray, long j, g.a aVar) throws IOException {
        if (this.a != null) {
            Assertions.checkNotNull(aVar.a);
            return false;
        }
        this.a = c(parsableByteArray);
        a aVar2 = this.a;
        if (aVar2 == null) {
            return true;
        }
        VorbisUtil.VorbisIdHeader vorbisIdHeader = aVar2.a;
        ArrayList arrayList = new ArrayList();
        arrayList.add(vorbisIdHeader.data);
        arrayList.add(aVar2.c);
        aVar.a = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_VORBIS).setAverageBitrate(vorbisIdHeader.bitrateNominal).setPeakBitrate(vorbisIdHeader.bitrateMaximum).setChannelCount(vorbisIdHeader.channels).setSampleRate(vorbisIdHeader.sampleRate).setInitializationData(arrayList).build();
        return true;
    }

    @Nullable
    @VisibleForTesting
    a c(ParsableByteArray parsableByteArray) throws IOException {
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.d;
        if (vorbisIdHeader == null) {
            this.d = VorbisUtil.readVorbisIdentificationHeader(parsableByteArray);
            return null;
        }
        VorbisUtil.CommentHeader commentHeader = this.e;
        if (commentHeader == null) {
            this.e = VorbisUtil.readVorbisCommentHeader(parsableByteArray);
            return null;
        }
        byte[] bArr = new byte[parsableByteArray.limit()];
        System.arraycopy(parsableByteArray.getData(), 0, bArr, 0, parsableByteArray.limit());
        VorbisUtil.Mode[] readVorbisModes = VorbisUtil.readVorbisModes(parsableByteArray, vorbisIdHeader.channels);
        return new a(vorbisIdHeader, commentHeader, bArr, readVorbisModes, VorbisUtil.iLog(readVorbisModes.length - 1));
    }

    @VisibleForTesting
    static void a(ParsableByteArray parsableByteArray, long j) {
        if (parsableByteArray.capacity() < parsableByteArray.limit() + 4) {
            parsableByteArray.reset(Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit() + 4));
        } else {
            parsableByteArray.setLimit(parsableByteArray.limit() + 4);
        }
        byte[] data = parsableByteArray.getData();
        data[parsableByteArray.limit() - 4] = (byte) (j & 255);
        data[parsableByteArray.limit() - 3] = (byte) ((j >>> 8) & 255);
        data[parsableByteArray.limit() - 2] = (byte) ((j >>> 16) & 255);
        data[parsableByteArray.limit() - 1] = (byte) ((j >>> 24) & 255);
    }

    private static int a(byte b, a aVar) {
        if (!aVar.d[a(b, aVar.e, 1)].blockFlag) {
            return aVar.a.blockSize0;
        }
        return aVar.a.blockSize1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VorbisReader.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public final VorbisUtil.VorbisIdHeader a;
        public final VorbisUtil.CommentHeader b;
        public final byte[] c;
        public final VorbisUtil.Mode[] d;
        public final int e;

        public a(VorbisUtil.VorbisIdHeader vorbisIdHeader, VorbisUtil.CommentHeader commentHeader, byte[] bArr, VorbisUtil.Mode[] modeArr, int i) {
            this.a = vorbisIdHeader;
            this.b = commentHeader;
            this.c = bArr;
            this.d = modeArr;
            this.e = i;
        }
    }
}
