package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* loaded from: classes2.dex */
public class OggExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$OggExtractor$r_jiGk3AHNxZXK3m0SXrzXRWlek.INSTANCE;
    private ExtractorOutput a;
    private g b;
    private boolean c;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] a() {
        return new Extractor[]{new OggExtractor()};
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        try {
            return a(extractorInput);
        } catch (ParserException unused) {
            return false;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.a = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        g gVar = this.b;
        if (gVar != null) {
            gVar.a(j, j2);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        Assertions.checkStateNotNull(this.a);
        if (this.b == null) {
            if (a(extractorInput)) {
                extractorInput.resetPeekPosition();
            } else {
                throw ParserException.createForMalformedContainer("Failed to determine bitstream type", null);
            }
        }
        if (!this.c) {
            TrackOutput track = this.a.track(0, 1);
            this.a.endTracks();
            this.b.a(this.a, track);
            this.c = true;
        }
        return this.b.a(extractorInput, positionHolder);
    }

    @EnsuresNonNullIf(expression = {"streamReader"}, result = true)
    private boolean a(ExtractorInput extractorInput) throws IOException {
        d dVar = new d();
        if (!dVar.a(extractorInput, true) || (dVar.b & 2) != 2) {
            return false;
        }
        int min = Math.min(dVar.i, 8);
        ParsableByteArray parsableByteArray = new ParsableByteArray(min);
        extractorInput.peekFully(parsableByteArray.getData(), 0, min);
        if (b.a(a(parsableByteArray))) {
            this.b = new b();
        } else if (h.a(a(parsableByteArray))) {
            this.b = new h();
        } else if (!f.a(a(parsableByteArray))) {
            return false;
        } else {
            this.b = new f();
        }
        return true;
    }

    private static ParsableByteArray a(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(0);
        return parsableByteArray;
    }
}
