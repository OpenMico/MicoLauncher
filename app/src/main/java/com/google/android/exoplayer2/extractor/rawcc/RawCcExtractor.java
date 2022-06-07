package com.google.android.exoplayer2.extractor.rawcc;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class RawCcExtractor implements Extractor {
    private final Format a;
    private TrackOutput c;
    private int e;
    private long f;
    private int g;
    private int h;
    private final ParsableByteArray b = new ParsableByteArray(9);
    private int d = 0;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public RawCcExtractor(Format format) {
        this.a = format;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
        this.c = extractorOutput.track(0, 3);
        this.c.format(this.a);
        extractorOutput.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        this.b.reset(8);
        extractorInput.peekFully(this.b.getData(), 0, 8);
        return this.b.readInt() == 1380139777;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        Assertions.checkStateNotNull(this.c);
        while (true) {
            switch (this.d) {
                case 0:
                    if (a(extractorInput)) {
                        this.d = 1;
                        break;
                    } else {
                        return -1;
                    }
                case 1:
                    if (b(extractorInput)) {
                        this.d = 2;
                        break;
                    } else {
                        this.d = 0;
                        return -1;
                    }
                case 2:
                    c(extractorInput);
                    this.d = 1;
                    return 0;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        this.d = 0;
    }

    private boolean a(ExtractorInput extractorInput) throws IOException {
        this.b.reset(8);
        if (!extractorInput.readFully(this.b.getData(), 0, 8, true)) {
            return false;
        }
        if (this.b.readInt() == 1380139777) {
            this.e = this.b.readUnsignedByte();
            return true;
        }
        throw new IOException("Input not RawCC");
    }

    private boolean b(ExtractorInput extractorInput) throws IOException {
        int i = this.e;
        if (i == 0) {
            this.b.reset(5);
            if (!extractorInput.readFully(this.b.getData(), 0, 5, true)) {
                return false;
            }
            this.f = (this.b.readUnsignedInt() * 1000) / 45;
        } else if (i == 1) {
            this.b.reset(9);
            if (!extractorInput.readFully(this.b.getData(), 0, 9, true)) {
                return false;
            }
            this.f = this.b.readLong();
        } else {
            StringBuilder sb = new StringBuilder(39);
            sb.append("Unsupported version number: ");
            sb.append(i);
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }
        this.g = this.b.readUnsignedByte();
        this.h = 0;
        return true;
    }

    @RequiresNonNull({"trackOutput"})
    private void c(ExtractorInput extractorInput) throws IOException {
        while (this.g > 0) {
            this.b.reset(3);
            extractorInput.readFully(this.b.getData(), 0, 3);
            this.c.sampleData(this.b, 3);
            this.h += 3;
            this.g--;
        }
        int i = this.h;
        if (i > 0) {
            this.c.sampleMetadata(this.f, 1, i, 0, null);
        }
    }
}
