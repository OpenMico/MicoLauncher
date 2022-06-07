package com.google.android.exoplayer2.source.hls;

import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class BundledHlsMediaChunkExtractor implements HlsMediaChunkExtractor {
    private static final PositionHolder b = new PositionHolder();
    @VisibleForTesting
    final Extractor a;
    private final Format c;
    private final TimestampAdjuster d;

    public BundledHlsMediaChunkExtractor(Extractor extractor, Format format, TimestampAdjuster timestampAdjuster) {
        this.a = extractor;
        this.c = format;
        this.d = timestampAdjuster;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public void init(ExtractorOutput extractorOutput) {
        this.a.init(extractorOutput);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public boolean read(ExtractorInput extractorInput) throws IOException {
        return this.a.read(extractorInput, b) == 0;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public boolean isPackedAudioExtractor() {
        Extractor extractor = this.a;
        return (extractor instanceof AdtsExtractor) || (extractor instanceof Ac3Extractor) || (extractor instanceof Ac4Extractor) || (extractor instanceof Mp3Extractor);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public boolean isReusable() {
        Extractor extractor = this.a;
        return (extractor instanceof TsExtractor) || (extractor instanceof FragmentedMp4Extractor);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public HlsMediaChunkExtractor recreate() {
        Extractor extractor;
        Assertions.checkState(!isReusable());
        Extractor extractor2 = this.a;
        if (extractor2 instanceof WebvttExtractor) {
            extractor = new WebvttExtractor(this.c.language, this.d);
        } else if (extractor2 instanceof AdtsExtractor) {
            extractor = new AdtsExtractor();
        } else if (extractor2 instanceof Ac3Extractor) {
            extractor = new Ac3Extractor();
        } else if (extractor2 instanceof Ac4Extractor) {
            extractor = new Ac4Extractor();
        } else if (extractor2 instanceof Mp3Extractor) {
            extractor = new Mp3Extractor();
        } else {
            String valueOf = String.valueOf(extractor2.getClass().getSimpleName());
            throw new IllegalStateException(valueOf.length() != 0 ? "Unexpected extractor type for recreation: ".concat(valueOf) : new String("Unexpected extractor type for recreation: "));
        }
        return new BundledHlsMediaChunkExtractor(extractor, this.c, this.d);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public void onTruncatedSegmentParsed() {
        this.a.seek(0L, 0L);
    }
}
