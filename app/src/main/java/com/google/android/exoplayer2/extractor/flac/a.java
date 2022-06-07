package com.google.android.exoplayer2.extractor.flac;

import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.FlacFrameReader;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import java.io.IOException;
import java.util.Objects;

/* compiled from: FlacBinarySearchSeeker.java */
/* loaded from: classes2.dex */
final class a extends BinarySearchSeeker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public a(final FlacStreamMetadata flacStreamMetadata, int i, long j, long j2) {
        super(new BinarySearchSeeker.SeekTimestampConverter() { // from class: com.google.android.exoplayer2.extractor.flac.-$$Lambda$RZnT0uiLg1lxLrLWgcUlQQYkZkE
            @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.SeekTimestampConverter
            public final long timeUsToTargetTime(long j3) {
                return FlacStreamMetadata.this.getSampleNumber(j3);
            }
        }, new C0058a(flacStreamMetadata, i), flacStreamMetadata.getDurationUs(), 0L, flacStreamMetadata.totalSamples, j, j2, flacStreamMetadata.getApproxBytesPerFrame(), Math.max(6, flacStreamMetadata.minFrameSize));
        Objects.requireNonNull(flacStreamMetadata);
    }

    /* compiled from: FlacBinarySearchSeeker.java */
    /* renamed from: com.google.android.exoplayer2.extractor.flac.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static final class C0058a implements BinarySearchSeeker.TimestampSeeker {
        private final FlacStreamMetadata a;
        private final int b;
        private final FlacFrameReader.SampleNumberHolder c;

        private C0058a(FlacStreamMetadata flacStreamMetadata, int i) {
            this.a = flacStreamMetadata;
            this.b = i;
            this.c = new FlacFrameReader.SampleNumberHolder();
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException {
            long position = extractorInput.getPosition();
            long a = a(extractorInput);
            long peekPosition = extractorInput.getPeekPosition();
            extractorInput.advancePeekPosition(Math.max(6, this.a.minFrameSize));
            long a2 = a(extractorInput);
            long peekPosition2 = extractorInput.getPeekPosition();
            if (a <= j && a2 > j) {
                return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(peekPosition);
            }
            if (a2 <= j) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(a2, peekPosition2);
            }
            return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(a, position);
        }

        private long a(ExtractorInput extractorInput) throws IOException {
            while (extractorInput.getPeekPosition() < extractorInput.getLength() - 6 && !FlacFrameReader.checkFrameHeaderFromPeek(extractorInput, this.a, this.b, this.c)) {
                extractorInput.advancePeekPosition(1);
            }
            if (extractorInput.getPeekPosition() < extractorInput.getLength() - 6) {
                return this.c.sampleNumber;
            }
            extractorInput.advancePeekPosition((int) (extractorInput.getLength() - extractorInput.getPeekPosition()));
            return this.a.totalSamples;
        }
    }
}
