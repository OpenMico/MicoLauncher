package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class Ac4Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$Ac4Extractor$WHTMtZtXxYyVwr97Dki4oK0eDE.INSTANCE;
    private final Ac4Reader a = new Ac4Reader();
    private final ParsableByteArray b = new ParsableByteArray(16384);
    private boolean c;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] a() {
        return new Extractor[]{new Ac4Extractor()};
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(10);
        int i = 0;
        while (true) {
            extractorInput.peekFully(parsableByteArray.getData(), 0, 10);
            parsableByteArray.setPosition(0);
            if (parsableByteArray.readUnsignedInt24() != 4801587) {
                break;
            }
            parsableByteArray.skipBytes(3);
            int readSynchSafeInt = parsableByteArray.readSynchSafeInt();
            i += readSynchSafeInt + 10;
            extractorInput.advancePeekPosition(readSynchSafeInt);
        }
        extractorInput.resetPeekPosition();
        extractorInput.advancePeekPosition(i);
        int i2 = 0;
        int i3 = i;
        while (true) {
            extractorInput.peekFully(parsableByteArray.getData(), 0, 7);
            parsableByteArray.setPosition(0);
            int readUnsignedShort = parsableByteArray.readUnsignedShort();
            if (readUnsignedShort == 44096 || readUnsignedShort == 44097) {
                i2++;
                if (i2 >= 4) {
                    return true;
                }
                int parseAc4SyncframeSize = Ac4Util.parseAc4SyncframeSize(parsableByteArray.getData(), readUnsignedShort);
                if (parseAc4SyncframeSize == -1) {
                    return false;
                }
                extractorInput.advancePeekPosition(parseAc4SyncframeSize - 7);
            } else {
                extractorInput.resetPeekPosition();
                i3++;
                if (i3 - i >= 8192) {
                    return false;
                }
                extractorInput.advancePeekPosition(i3);
                i2 = 0;
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.a.createTracks(extractorOutput, new TsPayloadReader.TrackIdGenerator(0, 1));
        extractorOutput.endTracks();
        extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        this.c = false;
        this.a.seek();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int read = extractorInput.read(this.b.getData(), 0, 16384);
        if (read == -1) {
            return -1;
        }
        this.b.setPosition(0);
        this.b.setLimit(read);
        if (!this.c) {
            this.a.packetStarted(0L, 4);
            this.c = true;
        }
        this.a.consume(this.b);
        return 0;
    }
}
