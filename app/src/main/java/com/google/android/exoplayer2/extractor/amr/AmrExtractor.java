package com.google.android.exoplayer2.extractor.amr;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class AmrExtractor implements Extractor {
    public static final int FLAG_ENABLE_CONSTANT_BITRATE_SEEKING = 1;
    private final byte[] f;
    private final int g;
    private boolean h;
    private long i;
    private int j;
    private int k;
    private boolean l;
    private long m;
    private int n;
    private int o;
    private long p;
    private ExtractorOutput q;
    private TrackOutput r;
    private SeekMap s;
    private boolean t;
    public static final ExtractorsFactory FACTORY = $$Lambda$AmrExtractor$wGJzsyg8MwXvICm6cal0GIE2_Bk.INSTANCE;
    private static final int[] a = {13, 14, 16, 18, 20, 21, 27, 32, 6, 7, 6, 6, 1, 1, 1, 1};
    private static final int[] b = {18, 24, 33, 37, 41, 47, 51, 59, 61, 6, 1, 1, 1, 1, 1, 1};
    private static final byte[] c = Util.getUtf8Bytes("#!AMR\n");
    private static final byte[] d = Util.getUtf8Bytes("#!AMR-WB\n");
    private static final int e = b[8];

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] c() {
        return new Extractor[]{new AmrExtractor()};
    }

    public AmrExtractor() {
        this(0);
    }

    public AmrExtractor(int i) {
        this.g = i;
        this.f = new byte[1];
        this.n = -1;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return a(extractorInput);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.q = extractorOutput;
        this.r = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        b();
        if (extractorInput.getPosition() != 0 || a(extractorInput)) {
            a();
            int b2 = b(extractorInput);
            a(extractorInput.getLength(), b2);
            return b2;
        }
        throw ParserException.createForMalformedContainer("Could not find AMR header.", null);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        this.i = 0L;
        this.j = 0;
        this.k = 0;
        if (j != 0) {
            SeekMap seekMap = this.s;
            if (seekMap instanceof ConstantBitrateSeekMap) {
                this.p = ((ConstantBitrateSeekMap) seekMap).getTimeUsAtPosition(j);
                return;
            }
        }
        this.p = 0L;
    }

    private boolean a(ExtractorInput extractorInput) throws IOException {
        if (a(extractorInput, c)) {
            this.h = false;
            extractorInput.skipFully(c.length);
            return true;
        } else if (!a(extractorInput, d)) {
            return false;
        } else {
            this.h = true;
            extractorInput.skipFully(d.length);
            return true;
        }
    }

    private static boolean a(ExtractorInput extractorInput, byte[] bArr) throws IOException {
        extractorInput.resetPeekPosition();
        byte[] bArr2 = new byte[bArr.length];
        extractorInput.peekFully(bArr2, 0, bArr.length);
        return Arrays.equals(bArr2, bArr);
    }

    @RequiresNonNull({"trackOutput"})
    private void a() {
        if (!this.t) {
            this.t = true;
            this.r.format(new Format.Builder().setSampleMimeType(this.h ? MimeTypes.AUDIO_AMR_WB : "audio/3gpp").setMaxInputSize(e).setChannelCount(1).setSampleRate(this.h ? 16000 : 8000).build());
        }
    }

    @RequiresNonNull({"trackOutput"})
    private int b(ExtractorInput extractorInput) throws IOException {
        if (this.k == 0) {
            try {
                this.j = c(extractorInput);
                this.k = this.j;
                if (this.n == -1) {
                    this.m = extractorInput.getPosition();
                    this.n = this.j;
                }
                if (this.n == this.j) {
                    this.o++;
                }
            } catch (EOFException unused) {
                return -1;
            }
        }
        int sampleData = this.r.sampleData((DataReader) extractorInput, this.k, true);
        if (sampleData == -1) {
            return -1;
        }
        this.k -= sampleData;
        if (this.k > 0) {
            return 0;
        }
        this.r.sampleMetadata(this.p + this.i, 1, this.j, 0, null);
        this.i += 20000;
        return 0;
    }

    private int c(ExtractorInput extractorInput) throws IOException {
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.f, 0, 1);
        byte b2 = this.f[0];
        if ((b2 & 131) <= 0) {
            return a((b2 >> 3) & 15);
        }
        StringBuilder sb = new StringBuilder(42);
        sb.append("Invalid padding bits for frame header ");
        sb.append((int) b2);
        throw ParserException.createForMalformedContainer(sb.toString(), null);
    }

    private int a(int i) throws ParserException {
        if (b(i)) {
            return this.h ? b[i] : a[i];
        }
        String str = this.h ? "WB" : "NB";
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 35);
        sb.append("Illegal AMR ");
        sb.append(str);
        sb.append(" frame type ");
        sb.append(i);
        throw ParserException.createForMalformedContainer(sb.toString(), null);
    }

    private boolean b(int i) {
        return i >= 0 && i <= 15 && (c(i) || d(i));
    }

    private boolean c(int i) {
        return this.h && (i < 10 || i > 13);
    }

    private boolean d(int i) {
        return !this.h && (i < 12 || i > 14);
    }

    @RequiresNonNull({"extractorOutput"})
    private void a(long j, int i) {
        int i2;
        if (!this.l) {
            if ((this.g & 1) == 0 || j == -1 || !((i2 = this.n) == -1 || i2 == this.j)) {
                this.s = new SeekMap.Unseekable(C.TIME_UNSET);
                this.q.seekMap(this.s);
                this.l = true;
            } else if (this.o >= 20 || i == -1) {
                this.s = a(j);
                this.q.seekMap(this.s);
                this.l = true;
            }
        }
    }

    private SeekMap a(long j) {
        return new ConstantBitrateSeekMap(j, this.m, a(this.n, 20000L), this.n);
    }

    @EnsuresNonNull({"extractorOutput", "trackOutput"})
    private void b() {
        Assertions.checkStateNotNull(this.r);
        Util.castNonNull(this.q);
    }

    private static int a(int i, long j) {
        return (int) (((i * 8) * 1000000) / j);
    }
}
