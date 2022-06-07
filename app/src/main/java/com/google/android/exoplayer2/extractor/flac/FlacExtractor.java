package com.google.android.exoplayer2.extractor.flac;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.FlacFrameReader;
import com.google.android.exoplayer2.extractor.FlacMetadataReader;
import com.google.android.exoplayer2.extractor.FlacSeekTableSeekMap;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class FlacExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$FlacExtractor$wCw7XhJqnT1i_486XODvAJEuZB4.INSTANCE;
    public static final int FLAG_DISABLE_ID3_METADATA = 1;
    private final byte[] a;
    private final ParsableByteArray b;
    private final boolean c;
    private final FlacFrameReader.SampleNumberHolder d;
    private ExtractorOutput e;
    private TrackOutput f;
    private int g;
    @Nullable
    private Metadata h;
    private FlacStreamMetadata i;
    private int j;
    private int k;
    private a l;
    private int m;
    private long n;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] b() {
        return new Extractor[]{new FlacExtractor()};
    }

    public FlacExtractor() {
        this(0);
    }

    public FlacExtractor(int i) {
        this.a = new byte[42];
        this.b = new ParsableByteArray(new byte[32768], 0);
        this.c = (i & 1) == 0 ? false : true;
        this.d = new FlacFrameReader.SampleNumberHolder();
        this.g = 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        FlacMetadataReader.peekId3Metadata(extractorInput, false);
        return FlacMetadataReader.checkAndPeekStreamMarker(extractorInput);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.e = extractorOutput;
        this.f = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        switch (this.g) {
            case 0:
                a(extractorInput);
                return 0;
            case 1:
                b(extractorInput);
                return 0;
            case 2:
                c(extractorInput);
                return 0;
            case 3:
                d(extractorInput);
                return 0;
            case 4:
                e(extractorInput);
                return 0;
            case 5:
                return a(extractorInput, positionHolder);
            default:
                throw new IllegalStateException();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        long j3 = 0;
        if (j == 0) {
            this.g = 0;
        } else {
            a aVar = this.l;
            if (aVar != null) {
                aVar.setSeekTargetUs(j2);
            }
        }
        if (j2 != 0) {
            j3 = -1;
        }
        this.n = j3;
        this.m = 0;
        this.b.reset(0);
    }

    private void a(ExtractorInput extractorInput) throws IOException {
        this.h = FlacMetadataReader.readId3Metadata(extractorInput, !this.c);
        this.g = 1;
    }

    private void b(ExtractorInput extractorInput) throws IOException {
        byte[] bArr = this.a;
        extractorInput.peekFully(bArr, 0, bArr.length);
        extractorInput.resetPeekPosition();
        this.g = 2;
    }

    private void c(ExtractorInput extractorInput) throws IOException {
        FlacMetadataReader.readStreamMarker(extractorInput);
        this.g = 3;
    }

    private void d(ExtractorInput extractorInput) throws IOException {
        FlacMetadataReader.FlacStreamMetadataHolder flacStreamMetadataHolder = new FlacMetadataReader.FlacStreamMetadataHolder(this.i);
        boolean z = false;
        while (!z) {
            z = FlacMetadataReader.readMetadataBlock(extractorInput, flacStreamMetadataHolder);
            this.i = (FlacStreamMetadata) Util.castNonNull(flacStreamMetadataHolder.flacStreamMetadata);
        }
        Assertions.checkNotNull(this.i);
        this.j = Math.max(this.i.minFrameSize, 6);
        ((TrackOutput) Util.castNonNull(this.f)).format(this.i.getFormat(this.a, this.h));
        this.g = 4;
    }

    private void e(ExtractorInput extractorInput) throws IOException {
        this.k = FlacMetadataReader.getFrameStartMarker(extractorInput);
        ((ExtractorOutput) Util.castNonNull(this.e)).seekMap(a(extractorInput.getPosition(), extractorInput.getLength()));
        this.g = 5;
    }

    private int a(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        Assertions.checkNotNull(this.f);
        Assertions.checkNotNull(this.i);
        a aVar = this.l;
        if (aVar != null && aVar.isSeeking()) {
            return this.l.handlePendingSeek(extractorInput, positionHolder);
        }
        if (this.n == -1) {
            this.n = FlacFrameReader.getFirstSampleNumber(extractorInput, this.i);
            return 0;
        }
        int limit = this.b.limit();
        if (limit < 32768) {
            int read = extractorInput.read(this.b.getData(), limit, 32768 - limit);
            z = read == -1;
            if (!z) {
                this.b.setLimit(limit + read);
            } else if (this.b.bytesLeft() == 0) {
                a();
                return -1;
            }
        } else {
            z = false;
        }
        int position = this.b.getPosition();
        int i = this.m;
        int i2 = this.j;
        if (i < i2) {
            ParsableByteArray parsableByteArray = this.b;
            parsableByteArray.skipBytes(Math.min(i2 - i, parsableByteArray.bytesLeft()));
        }
        long a = a(this.b, z);
        int position2 = this.b.getPosition() - position;
        this.b.setPosition(position);
        this.f.sampleData(this.b, position2);
        this.m += position2;
        if (a != -1) {
            a();
            this.m = 0;
            this.n = a;
        }
        if (this.b.bytesLeft() < 16) {
            int bytesLeft = this.b.bytesLeft();
            System.arraycopy(this.b.getData(), this.b.getPosition(), this.b.getData(), 0, bytesLeft);
            this.b.setPosition(0);
            this.b.setLimit(bytesLeft);
        }
        return 0;
    }

    private SeekMap a(long j, long j2) {
        Assertions.checkNotNull(this.i);
        if (this.i.seekTable != null) {
            return new FlacSeekTableSeekMap(this.i, j);
        }
        if (j2 == -1 || this.i.totalSamples <= 0) {
            return new SeekMap.Unseekable(this.i.getDurationUs());
        }
        this.l = new a(this.i, this.k, j, j2);
        return this.l.getSeekMap();
    }

    private long a(ParsableByteArray parsableByteArray, boolean z) {
        Assertions.checkNotNull(this.i);
        int position = parsableByteArray.getPosition();
        while (position <= parsableByteArray.limit() - 16) {
            parsableByteArray.setPosition(position);
            if (FlacFrameReader.checkAndReadFrameHeader(parsableByteArray, this.i, this.k, this.d)) {
                parsableByteArray.setPosition(position);
                return this.d.sampleNumber;
            }
            position++;
        }
        if (z) {
            while (position <= parsableByteArray.limit() - this.j) {
                parsableByteArray.setPosition(position);
                boolean z2 = false;
                try {
                    z2 = FlacFrameReader.checkAndReadFrameHeader(parsableByteArray, this.i, this.k, this.d);
                } catch (IndexOutOfBoundsException unused) {
                    z2 = false;
                }
                if (parsableByteArray.getPosition() <= parsableByteArray.limit()) {
                }
                if (z2) {
                    parsableByteArray.setPosition(position);
                    return this.d.sampleNumber;
                }
                position++;
            }
            parsableByteArray.setPosition(parsableByteArray.limit());
            return -1L;
        }
        parsableByteArray.setPosition(position);
        return -1L;
    }

    private void a() {
        ((TrackOutput) Util.castNonNull(this.f)).sampleMetadata((this.n * 1000000) / ((FlacStreamMetadata) Util.castNonNull(this.i)).sampleRate, 1, this.m, 0, null);
    }
}
