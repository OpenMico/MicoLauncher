package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes2.dex */
public final class Id3Reader implements ElementaryStreamReader {
    private TrackOutput b;
    private boolean c;
    private int e;
    private int f;
    private final ParsableByteArray a = new ParsableByteArray(10);
    private long d = C.TIME_UNSET;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.c = false;
        this.d = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.b = extractorOutput.track(trackIdGenerator.getTrackId(), 5);
        this.b.format(new Format.Builder().setId(trackIdGenerator.getFormatId()).setSampleMimeType(MimeTypes.APPLICATION_ID3).build());
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if ((i & 4) != 0) {
            this.c = true;
            if (j != C.TIME_UNSET) {
                this.d = j;
            }
            this.e = 0;
            this.f = 0;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.b);
        if (this.c) {
            int bytesLeft = parsableByteArray.bytesLeft();
            int i = this.f;
            if (i < 10) {
                int min = Math.min(bytesLeft, 10 - i);
                System.arraycopy(parsableByteArray.getData(), parsableByteArray.getPosition(), this.a.getData(), this.f, min);
                if (this.f + min == 10) {
                    this.a.setPosition(0);
                    if (73 == this.a.readUnsignedByte() && 68 == this.a.readUnsignedByte() && 51 == this.a.readUnsignedByte()) {
                        this.a.skipBytes(3);
                        this.e = this.a.readSynchSafeInt() + 10;
                    } else {
                        Log.w("Id3Reader", "Discarding invalid ID3 tag");
                        this.c = false;
                        return;
                    }
                }
            }
            int min2 = Math.min(bytesLeft, this.e - this.f);
            this.b.sampleData(parsableByteArray, min2);
            this.f += min2;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
        int i;
        Assertions.checkStateNotNull(this.b);
        if (this.c && (i = this.e) != 0 && this.f == i) {
            long j = this.d;
            if (j != C.TIME_UNSET) {
                this.b.sampleMetadata(j, 1, i, 0, null);
            }
            this.c = false;
        }
    }
}
