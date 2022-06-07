package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class MpegAudioReader implements ElementaryStreamReader {
    private final ParsableByteArray a;
    private final MpegAudioUtil.Header b;
    @Nullable
    private final String c;
    private TrackOutput d;
    private String e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;
    private long j;
    private int k;
    private long l;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public MpegAudioReader() {
        this(null);
    }

    public MpegAudioReader(@Nullable String str) {
        this.f = 0;
        this.a = new ParsableByteArray(4);
        this.a.getData()[0] = -1;
        this.b = new MpegAudioUtil.Header();
        this.l = C.TIME_UNSET;
        this.c = str;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.f = 0;
        this.g = 0;
        this.i = false;
        this.l = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.e = trackIdGenerator.getFormatId();
        this.d = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.l = j;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.d);
        while (parsableByteArray.bytesLeft() > 0) {
            switch (this.f) {
                case 0:
                    a(parsableByteArray);
                    break;
                case 1:
                    b(parsableByteArray);
                    break;
                case 2:
                    c(parsableByteArray);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private void a(ParsableByteArray parsableByteArray) {
        byte[] data = parsableByteArray.getData();
        int limit = parsableByteArray.limit();
        for (int position = parsableByteArray.getPosition(); position < limit; position++) {
            boolean z = (data[position] & 255) == 255;
            boolean z2 = this.i && (data[position] & 224) == 224;
            this.i = z;
            if (z2) {
                parsableByteArray.setPosition(position + 1);
                this.i = false;
                this.a.getData()[1] = data[position];
                this.g = 2;
                this.f = 1;
                return;
            }
        }
        parsableByteArray.setPosition(limit);
    }

    @RequiresNonNull({"output"})
    private void b(ParsableByteArray parsableByteArray) {
        int min = Math.min(parsableByteArray.bytesLeft(), 4 - this.g);
        parsableByteArray.readBytes(this.a.getData(), this.g, min);
        this.g += min;
        if (this.g >= 4) {
            this.a.setPosition(0);
            if (!this.b.setForHeaderData(this.a.readInt())) {
                this.g = 0;
                this.f = 1;
                return;
            }
            this.k = this.b.frameSize;
            if (!this.h) {
                this.j = (this.b.samplesPerFrame * 1000000) / this.b.sampleRate;
                this.d.format(new Format.Builder().setId(this.e).setSampleMimeType(this.b.mimeType).setMaxInputSize(4096).setChannelCount(this.b.channels).setSampleRate(this.b.sampleRate).setLanguage(this.c).build());
                this.h = true;
            }
            this.a.setPosition(0);
            this.d.sampleData(this.a, 4);
            this.f = 2;
        }
    }

    @RequiresNonNull({"output"})
    private void c(ParsableByteArray parsableByteArray) {
        int min = Math.min(parsableByteArray.bytesLeft(), this.k - this.g);
        this.d.sampleData(parsableByteArray, min);
        this.g += min;
        int i = this.g;
        int i2 = this.k;
        if (i >= i2) {
            long j = this.l;
            if (j != C.TIME_UNSET) {
                this.d.sampleMetadata(j, 1, i2, 0, null);
                this.l += this.j;
            }
            this.g = 0;
            this.f = 0;
        }
    }
}
