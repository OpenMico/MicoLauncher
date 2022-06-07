package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes2.dex */
public final class PassthroughSectionPayloadReader implements SectionPayloadReader {
    private Format a;
    private TimestampAdjuster b;
    private TrackOutput c;

    public PassthroughSectionPayloadReader(String str) {
        this.a = new Format.Builder().setSampleMimeType(str).build();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.b = timestampAdjuster;
        trackIdGenerator.generateNewId();
        this.c = extractorOutput.track(trackIdGenerator.getTrackId(), 5);
        this.c.format(this.a);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
    public void consume(ParsableByteArray parsableByteArray) {
        a();
        long lastAdjustedTimestampUs = this.b.getLastAdjustedTimestampUs();
        long timestampOffsetUs = this.b.getTimestampOffsetUs();
        if (lastAdjustedTimestampUs != C.TIME_UNSET && timestampOffsetUs != C.TIME_UNSET) {
            if (timestampOffsetUs != this.a.subsampleOffsetUs) {
                this.a = this.a.buildUpon().setSubsampleOffsetUs(timestampOffsetUs).build();
                this.c.format(this.a);
            }
            int bytesLeft = parsableByteArray.bytesLeft();
            this.c.sampleData(parsableByteArray, bytesLeft);
            this.c.sampleMetadata(lastAdjustedTimestampUs, 1, bytesLeft, 0, null);
        }
    }

    @EnsuresNonNull({"timestampAdjuster", "output"})
    private void a() {
        Assertions.checkStateNotNull(this.b);
        Util.castNonNull(this.c);
    }
}
