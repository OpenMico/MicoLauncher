package com.google.android.exoplayer2.extractor.wav;

import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.WavUtil;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes2.dex */
public final class WavExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$WavExtractor$a2wuzbw6bhFQGAwKCgbxTLlybYY.INSTANCE;
    private ExtractorOutput a;
    private TrackOutput b;
    private b c;
    private int d = -1;
    private long e = -1;

    /* loaded from: classes2.dex */
    private interface b {
        void a(int i, long j) throws ParserException;

        void a(long j);

        boolean a(ExtractorInput extractorInput, long j) throws IOException;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] b() {
        return new Extractor[]{new WavExtractor()};
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return b.a(extractorInput) != null;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.a = extractorOutput;
        this.b = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        b bVar = this.c;
        if (bVar != null) {
            bVar.a(j2);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        a();
        if (this.c == null) {
            a a2 = b.a(extractorInput);
            if (a2 == null) {
                throw ParserException.createForMalformedContainer("Unsupported or unrecognized wav header.", null);
            } else if (a2.a == 17) {
                this.c = new a(this.a, this.b, a2);
            } else if (a2.a == 6) {
                this.c = new c(this.a, this.b, a2, MimeTypes.AUDIO_ALAW, -1);
            } else if (a2.a == 7) {
                this.c = new c(this.a, this.b, a2, MimeTypes.AUDIO_MLAW, -1);
            } else {
                int pcmEncodingForType = WavUtil.getPcmEncodingForType(a2.a, a2.f);
                if (pcmEncodingForType != 0) {
                    this.c = new c(this.a, this.b, a2, MimeTypes.AUDIO_RAW, pcmEncodingForType);
                } else {
                    int i = a2.a;
                    StringBuilder sb = new StringBuilder(40);
                    sb.append("Unsupported WAV format type: ");
                    sb.append(i);
                    throw ParserException.createForUnsupportedContainerFeature(sb.toString());
                }
            }
        }
        if (this.d == -1) {
            Pair<Long, Long> b2 = b.b(extractorInput);
            this.d = ((Long) b2.first).intValue();
            this.e = ((Long) b2.second).longValue();
            this.c.a(this.d, this.e);
        } else if (extractorInput.getPosition() == 0) {
            extractorInput.skipFully(this.d);
        }
        Assertions.checkState(this.e != -1);
        return this.c.a(extractorInput, this.e - extractorInput.getPosition()) ? -1 : 0;
    }

    @EnsuresNonNull({"extractorOutput", "trackOutput"})
    private void a() {
        Assertions.checkStateNotNull(this.b);
        Util.castNonNull(this.a);
    }

    /* loaded from: classes2.dex */
    private static final class c implements b {
        private final ExtractorOutput a;
        private final TrackOutput b;
        private final a c;
        private final Format d;
        private final int e;
        private long f;
        private int g;
        private long h;

        public c(ExtractorOutput extractorOutput, TrackOutput trackOutput, a aVar, String str, int i) throws ParserException {
            this.a = extractorOutput;
            this.b = trackOutput;
            this.c = aVar;
            int i2 = (aVar.b * aVar.f) / 8;
            if (aVar.e == i2) {
                int i3 = aVar.c * i2 * 8;
                this.e = Math.max(i2, (aVar.c * i2) / 10);
                this.d = new Format.Builder().setSampleMimeType(str).setAverageBitrate(i3).setPeakBitrate(i3).setMaxInputSize(this.e).setChannelCount(aVar.b).setSampleRate(aVar.c).setPcmEncoding(i).build();
                return;
            }
            int i4 = aVar.e;
            StringBuilder sb = new StringBuilder(50);
            sb.append("Expected block size: ");
            sb.append(i2);
            sb.append("; got: ");
            sb.append(i4);
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.b
        public void a(long j) {
            this.f = j;
            this.g = 0;
            this.h = 0L;
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.b
        public void a(int i, long j) {
            this.a.seekMap(new c(this.c, 1, i, j));
            this.b.format(this.d);
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.b
        public boolean a(ExtractorInput extractorInput, long j) throws IOException {
            int i;
            int i2;
            int i3;
            long j2 = j;
            while (true) {
                i = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
                if (i <= 0 || (i2 = this.g) >= (i3 = this.e)) {
                    break;
                }
                int sampleData = this.b.sampleData((DataReader) extractorInput, (int) Math.min(i3 - i2, j2), true);
                if (sampleData == -1) {
                    j2 = 0;
                } else {
                    this.g += sampleData;
                    j2 -= sampleData;
                }
            }
            int i4 = this.c.e;
            int i5 = this.g / i4;
            if (i5 > 0) {
                int i6 = i5 * i4;
                int i7 = this.g - i6;
                this.b.sampleMetadata(this.f + Util.scaleLargeTimestamp(this.h, 1000000L, this.c.c), 1, i6, i7, null);
                this.h += i5;
                this.g = i7;
            }
            return i <= 0;
        }
    }

    /* loaded from: classes2.dex */
    private static final class a implements b {
        private static final int[] a = {-1, -1, -1, -1, 2, 4, 6, 8, -1, -1, -1, -1, 2, 4, 6, 8};
        private static final int[] b = {7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143, 157, 173, 190, 209, 230, 253, 279, 307, 337, 371, 408, 449, 494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767};
        private final ExtractorOutput c;
        private final TrackOutput d;
        private final a e;
        private final int f;
        private final byte[] g;
        private final ParsableByteArray h;
        private final int i;
        private final Format j;
        private int k;
        private long l;
        private int m;
        private long n;

        private static int a(int i, int i2) {
            return i * 2 * i2;
        }

        public a(ExtractorOutput extractorOutput, TrackOutput trackOutput, a aVar) throws ParserException {
            this.c = extractorOutput;
            this.d = trackOutput;
            this.e = aVar;
            this.i = Math.max(1, aVar.c / 10);
            ParsableByteArray parsableByteArray = new ParsableByteArray(aVar.g);
            parsableByteArray.readLittleEndianUnsignedShort();
            this.f = parsableByteArray.readLittleEndianUnsignedShort();
            int i = aVar.b;
            int i2 = (((aVar.e - (i * 4)) * 8) / (aVar.f * i)) + 1;
            int i3 = this.f;
            if (i3 == i2) {
                int ceilDivide = Util.ceilDivide(this.i, i3);
                this.g = new byte[aVar.e * ceilDivide];
                this.h = new ParsableByteArray(ceilDivide * a(this.f, i));
                int i4 = ((aVar.c * aVar.e) * 8) / this.f;
                this.j = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setAverageBitrate(i4).setPeakBitrate(i4).setMaxInputSize(a(this.i, i)).setChannelCount(aVar.b).setSampleRate(aVar.c).setPcmEncoding(2).build();
                return;
            }
            StringBuilder sb = new StringBuilder(56);
            sb.append("Expected frames per block: ");
            sb.append(i2);
            sb.append("; got: ");
            sb.append(i3);
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.b
        public void a(long j) {
            this.k = 0;
            this.l = j;
            this.m = 0;
            this.n = 0L;
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.b
        public void a(int i, long j) {
            this.c.seekMap(new c(this.e, this.f, i, j));
            this.d.format(this.j);
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.b
        public boolean a(ExtractorInput extractorInput, long j) throws IOException {
            int b2;
            int ceilDivide = Util.ceilDivide(this.i - b(this.m), this.f) * this.e.e;
            boolean z = j == 0;
            while (!z) {
                int i = this.k;
                if (i >= ceilDivide) {
                    break;
                }
                int read = extractorInput.read(this.g, this.k, (int) Math.min(ceilDivide - i, j));
                if (read == -1) {
                    z = true;
                } else {
                    this.k += read;
                }
            }
            int i2 = this.k / this.e.e;
            if (i2 > 0) {
                a(this.g, i2, this.h);
                this.k -= i2 * this.e.e;
                int limit = this.h.limit();
                this.d.sampleData(this.h, limit);
                this.m += limit;
                int b3 = b(this.m);
                int i3 = this.i;
                if (b3 >= i3) {
                    a(i3);
                }
            }
            if (z && (b2 = b(this.m)) > 0) {
                a(b2);
            }
            return z;
        }

        private void a(int i) {
            long scaleLargeTimestamp = this.l + Util.scaleLargeTimestamp(this.n, 1000000L, this.e.c);
            int c = c(i);
            this.d.sampleMetadata(scaleLargeTimestamp, 1, c, this.m - c, null);
            this.n += i;
            this.m -= c;
        }

        private void a(byte[] bArr, int i, ParsableByteArray parsableByteArray) {
            for (int i2 = 0; i2 < i; i2++) {
                for (int i3 = 0; i3 < this.e.b; i3++) {
                    a(bArr, i2, i3, parsableByteArray.getData());
                }
            }
            int c = c(this.f * i);
            parsableByteArray.setPosition(0);
            parsableByteArray.setLimit(c);
        }

        private void a(byte[] bArr, int i, int i2, byte[] bArr2) {
            int i3 = this.e.e;
            int i4 = this.e.b;
            int i5 = (i * i3) + (i2 * 4);
            int i6 = (i4 * 4) + i5;
            int i7 = (i3 / i4) - 4;
            int i8 = (short) (((bArr[i5 + 1] & 255) << 8) | (bArr[i5] & 255));
            int min = Math.min(bArr[i5 + 2] & 255, 88);
            int i9 = b[min];
            int i10 = ((i * this.f * i4) + i2) * 2;
            bArr2[i10] = (byte) (i8 & 255);
            bArr2[i10 + 1] = (byte) (i8 >> 8);
            int i11 = i10;
            for (int i12 = 0; i12 < i7 * 2; i12++) {
                int i13 = bArr[((i12 / 8) * i4 * 4) + i6 + ((i12 / 2) % 4)] & 255;
                int i14 = i12 % 2 == 0 ? i13 & 15 : i13 >> 4;
                int i15 = ((((i14 & 7) * 2) + 1) * i9) >> 3;
                if ((i14 & 8) != 0) {
                    i15 = -i15;
                }
                i8 = Util.constrainValue(i8 + i15, -32768, 32767);
                i11 += i4 * 2;
                bArr2[i11] = (byte) (i8 & 255);
                bArr2[i11 + 1] = (byte) (i8 >> 8);
                min = Util.constrainValue(min + a[i14], 0, b.length - 1);
                i9 = b[min];
            }
        }

        private int b(int i) {
            return i / (this.e.b * 2);
        }

        private int c(int i) {
            return a(i, this.e.b);
        }
    }
}
