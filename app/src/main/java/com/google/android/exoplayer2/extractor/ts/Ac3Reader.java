package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class Ac3Reader implements ElementaryStreamReader {
    private final ParsableBitArray a;
    private final ParsableByteArray b;
    @Nullable
    private final String c;
    private String d;
    private TrackOutput e;
    private int f;
    private int g;
    private boolean h;
    private long i;
    private Format j;
    private int k;
    private long l;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public Ac3Reader() {
        this(null);
    }

    public Ac3Reader(@Nullable String str) {
        this.a = new ParsableBitArray(new byte[128]);
        this.b = new ParsableByteArray(this.a.data);
        this.f = 0;
        this.l = C.TIME_UNSET;
        this.c = str;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.l = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.d = trackIdGenerator.getFormatId();
        this.e = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.l = j;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.e);
        while (parsableByteArray.bytesLeft() > 0) {
            switch (this.f) {
                case 0:
                    if (!a(parsableByteArray)) {
                        break;
                    } else {
                        this.f = 1;
                        this.b.getData()[0] = 11;
                        this.b.getData()[1] = 119;
                        this.g = 2;
                        break;
                    }
                case 1:
                    if (!a(parsableByteArray, this.b.getData(), 128)) {
                        break;
                    } else {
                        a();
                        this.b.setPosition(0);
                        this.e.sampleData(this.b, 128);
                        this.f = 2;
                        break;
                    }
                case 2:
                    int min = Math.min(parsableByteArray.bytesLeft(), this.k - this.g);
                    this.e.sampleData(parsableByteArray, min);
                    this.g += min;
                    int i = this.g;
                    int i2 = this.k;
                    if (i != i2) {
                        break;
                    } else {
                        long j = this.l;
                        if (j != C.TIME_UNSET) {
                            this.e.sampleMetadata(j, 1, i2, 0, null);
                            this.l += this.i;
                        }
                        this.f = 0;
                        break;
                    }
            }
        }
    }

    private boolean a(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.g);
        parsableByteArray.readBytes(bArr, this.g, min);
        this.g += min;
        return this.g == i;
    }

    private boolean a(ParsableByteArray parsableByteArray) {
        while (true) {
            boolean z = false;
            if (parsableByteArray.bytesLeft() <= 0) {
                return false;
            }
            if (!this.h) {
                if (parsableByteArray.readUnsignedByte() == 11) {
                    z = true;
                }
                this.h = z;
            } else {
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                if (readUnsignedByte == 119) {
                    this.h = false;
                    return true;
                }
                if (readUnsignedByte == 11) {
                    z = true;
                }
                this.h = z;
            }
        }
    }

    @RequiresNonNull({"output"})
    private void a() {
        this.a.setPosition(0);
        Ac3Util.SyncFrameInfo parseAc3SyncframeInfo = Ac3Util.parseAc3SyncframeInfo(this.a);
        if (this.j == null || parseAc3SyncframeInfo.channelCount != this.j.channelCount || parseAc3SyncframeInfo.sampleRate != this.j.sampleRate || !Util.areEqual(parseAc3SyncframeInfo.mimeType, this.j.sampleMimeType)) {
            this.j = new Format.Builder().setId(this.d).setSampleMimeType(parseAc3SyncframeInfo.mimeType).setChannelCount(parseAc3SyncframeInfo.channelCount).setSampleRate(parseAc3SyncframeInfo.sampleRate).setLanguage(this.c).build();
            this.e.format(this.j);
        }
        this.k = parseAc3SyncframeInfo.frameSize;
        this.i = (parseAc3SyncframeInfo.sampleCount * 1000000) / this.j.sampleRate;
    }
}
