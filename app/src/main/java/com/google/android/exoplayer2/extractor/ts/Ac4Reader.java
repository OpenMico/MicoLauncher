package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class Ac4Reader implements ElementaryStreamReader {
    private final ParsableBitArray a;
    private final ParsableByteArray b;
    @Nullable
    private final String c;
    private String d;
    private TrackOutput e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;
    private long j;
    private Format k;
    private int l;
    private long m;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public Ac4Reader() {
        this(null);
    }

    public Ac4Reader(@Nullable String str) {
        this.a = new ParsableBitArray(new byte[16]);
        this.b = new ParsableByteArray(this.a.data);
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = false;
        this.m = C.TIME_UNSET;
        this.c = str;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = false;
        this.m = C.TIME_UNSET;
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
            this.m = j;
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
                        this.b.getData()[0] = -84;
                        this.b.getData()[1] = (byte) (this.i ? 65 : 64);
                        this.g = 2;
                        break;
                    }
                case 1:
                    if (!a(parsableByteArray, this.b.getData(), 16)) {
                        break;
                    } else {
                        a();
                        this.b.setPosition(0);
                        this.e.sampleData(this.b, 16);
                        this.f = 2;
                        break;
                    }
                case 2:
                    int min = Math.min(parsableByteArray.bytesLeft(), this.l - this.g);
                    this.e.sampleData(parsableByteArray, min);
                    this.g += min;
                    int i = this.g;
                    int i2 = this.l;
                    if (i != i2) {
                        break;
                    } else {
                        long j = this.m;
                        if (j != C.TIME_UNSET) {
                            this.e.sampleMetadata(j, 1, i2, 0, null);
                            this.m += this.j;
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
        boolean z;
        int readUnsignedByte;
        while (true) {
            z = false;
            if (parsableByteArray.bytesLeft() <= 0) {
                return false;
            }
            if (!this.h) {
                if (parsableByteArray.readUnsignedByte() == 172) {
                    z = true;
                }
                this.h = z;
            } else {
                readUnsignedByte = parsableByteArray.readUnsignedByte();
                this.h = readUnsignedByte == 172;
                if (readUnsignedByte == 64 || readUnsignedByte == 65) {
                    break;
                }
            }
        }
        if (readUnsignedByte == 65) {
            z = true;
        }
        this.i = z;
        return true;
    }

    @RequiresNonNull({"output"})
    private void a() {
        this.a.setPosition(0);
        Ac4Util.SyncFrameInfo parseAc4SyncframeInfo = Ac4Util.parseAc4SyncframeInfo(this.a);
        if (this.k == null || parseAc4SyncframeInfo.channelCount != this.k.channelCount || parseAc4SyncframeInfo.sampleRate != this.k.sampleRate || !MimeTypes.AUDIO_AC4.equals(this.k.sampleMimeType)) {
            this.k = new Format.Builder().setId(this.d).setSampleMimeType(MimeTypes.AUDIO_AC4).setChannelCount(parseAc4SyncframeInfo.channelCount).setSampleRate(parseAc4SyncframeInfo.sampleRate).setLanguage(this.c).build();
            this.e.format(this.k);
        }
        this.l = parseAc4SyncframeInfo.frameSize;
        this.j = (parseAc4SyncframeInfo.sampleCount * 1000000) / this.k.sampleRate;
    }
}
