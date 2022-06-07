package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.DtsUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class DtsReader implements ElementaryStreamReader {
    @Nullable
    private final String b;
    private String c;
    private TrackOutput d;
    private int f;
    private int g;
    private long h;
    private Format i;
    private int j;
    private final ParsableByteArray a = new ParsableByteArray(new byte[18]);
    private int e = 0;
    private long k = C.TIME_UNSET;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public DtsReader(@Nullable String str) {
        this.b = str;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.k = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.c = trackIdGenerator.getFormatId();
        this.d = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.k = j;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.d);
        while (parsableByteArray.bytesLeft() > 0) {
            switch (this.e) {
                case 0:
                    if (!a(parsableByteArray)) {
                        break;
                    } else {
                        this.e = 1;
                        break;
                    }
                case 1:
                    if (!a(parsableByteArray, this.a.getData(), 18)) {
                        break;
                    } else {
                        a();
                        this.a.setPosition(0);
                        this.d.sampleData(this.a, 18);
                        this.e = 2;
                        break;
                    }
                case 2:
                    int min = Math.min(parsableByteArray.bytesLeft(), this.j - this.f);
                    this.d.sampleData(parsableByteArray, min);
                    this.f += min;
                    int i = this.f;
                    int i2 = this.j;
                    if (i != i2) {
                        break;
                    } else {
                        long j = this.k;
                        if (j != C.TIME_UNSET) {
                            this.d.sampleMetadata(j, 1, i2, 0, null);
                            this.k += this.h;
                        }
                        this.e = 0;
                        break;
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private boolean a(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.f);
        parsableByteArray.readBytes(bArr, this.f, min);
        this.f += min;
        return this.f == i;
    }

    private boolean a(ParsableByteArray parsableByteArray) {
        while (parsableByteArray.bytesLeft() > 0) {
            this.g <<= 8;
            this.g |= parsableByteArray.readUnsignedByte();
            if (DtsUtil.isSyncWord(this.g)) {
                byte[] data = this.a.getData();
                int i = this.g;
                data[0] = (byte) ((i >> 24) & 255);
                data[1] = (byte) ((i >> 16) & 255);
                data[2] = (byte) ((i >> 8) & 255);
                data[3] = (byte) (i & 255);
                this.f = 4;
                this.g = 0;
                return true;
            }
        }
        return false;
    }

    @RequiresNonNull({"output"})
    private void a() {
        byte[] data = this.a.getData();
        if (this.i == null) {
            this.i = DtsUtil.parseDtsFormat(data, this.c, this.b, null);
            this.d.format(this.i);
        }
        this.j = DtsUtil.getDtsFrameSize(data);
        this.h = (int) ((DtsUtil.parseDtsAudioSampleCount(data) * 1000000) / this.i.sampleRate);
    }
}
