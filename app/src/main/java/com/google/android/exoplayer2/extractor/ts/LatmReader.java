package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class LatmReader implements ElementaryStreamReader {
    @Nullable
    private final String a;
    private TrackOutput d;
    private String e;
    private Format f;
    private int g;
    private int h;
    private int i;
    private int j;
    private boolean l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private long q;
    private int r;
    private long s;
    private int t;
    @Nullable
    private String u;
    private final ParsableByteArray b = new ParsableByteArray(1024);
    private final ParsableBitArray c = new ParsableBitArray(this.b.getData());
    private long k = C.TIME_UNSET;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public LatmReader(@Nullable String str) {
        this.a = str;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.g = 0;
        this.k = C.TIME_UNSET;
        this.l = false;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.d = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
        this.e = trackIdGenerator.getFormatId();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.k = j;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) throws ParserException {
        Assertions.checkStateNotNull(this.d);
        while (parsableByteArray.bytesLeft() > 0) {
            switch (this.g) {
                case 0:
                    if (parsableByteArray.readUnsignedByte() != 86) {
                        break;
                    } else {
                        this.g = 1;
                        break;
                    }
                case 1:
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    if ((readUnsignedByte & 224) != 224) {
                        if (readUnsignedByte == 86) {
                            break;
                        } else {
                            this.g = 0;
                            break;
                        }
                    } else {
                        this.j = readUnsignedByte;
                        this.g = 2;
                        break;
                    }
                case 2:
                    this.i = ((this.j & (-225)) << 8) | parsableByteArray.readUnsignedByte();
                    if (this.i > this.b.getData().length) {
                        a(this.i);
                    }
                    this.h = 0;
                    this.g = 3;
                    break;
                case 3:
                    int min = Math.min(parsableByteArray.bytesLeft(), this.i - this.h);
                    parsableByteArray.readBytes(this.c.data, this.h, min);
                    this.h += min;
                    if (this.h != this.i) {
                        break;
                    } else {
                        this.c.setPosition(0);
                        a(this.c);
                        this.g = 0;
                        break;
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @RequiresNonNull({"output"})
    private void a(ParsableBitArray parsableBitArray) throws ParserException {
        if (!parsableBitArray.readBit()) {
            this.l = true;
            b(parsableBitArray);
        } else if (!this.l) {
            return;
        }
        if (this.m != 0) {
            throw ParserException.createForMalformedContainer(null, null);
        } else if (this.n == 0) {
            a(parsableBitArray, e(parsableBitArray));
            if (this.p) {
                parsableBitArray.skipBits((int) this.q);
            }
        } else {
            throw ParserException.createForMalformedContainer(null, null);
        }
    }

    @RequiresNonNull({"output"})
    private void b(ParsableBitArray parsableBitArray) throws ParserException {
        boolean readBit;
        int readBits = parsableBitArray.readBits(1);
        this.m = readBits == 1 ? parsableBitArray.readBits(1) : 0;
        if (this.m == 0) {
            if (readBits == 1) {
                f(parsableBitArray);
            }
            if (parsableBitArray.readBit()) {
                this.n = parsableBitArray.readBits(6);
                int readBits2 = parsableBitArray.readBits(4);
                int readBits3 = parsableBitArray.readBits(3);
                if (readBits2 == 0 && readBits3 == 0) {
                    if (readBits == 0) {
                        int position = parsableBitArray.getPosition();
                        int d = d(parsableBitArray);
                        parsableBitArray.setPosition(position);
                        byte[] bArr = new byte[(d + 7) / 8];
                        parsableBitArray.readBits(bArr, 0, d);
                        Format build = new Format.Builder().setId(this.e).setSampleMimeType(MimeTypes.AUDIO_AAC).setCodecs(this.u).setChannelCount(this.t).setSampleRate(this.r).setInitializationData(Collections.singletonList(bArr)).setLanguage(this.a).build();
                        if (!build.equals(this.f)) {
                            this.f = build;
                            this.s = 1024000000 / build.sampleRate;
                            this.d.format(build);
                        }
                    } else {
                        parsableBitArray.skipBits(((int) f(parsableBitArray)) - d(parsableBitArray));
                    }
                    c(parsableBitArray);
                    this.p = parsableBitArray.readBit();
                    this.q = 0L;
                    if (this.p) {
                        if (readBits == 1) {
                            this.q = f(parsableBitArray);
                        } else {
                            do {
                                readBit = parsableBitArray.readBit();
                                this.q = (this.q << 8) + parsableBitArray.readBits(8);
                            } while (readBit);
                        }
                    }
                    if (parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(8);
                        return;
                    }
                    return;
                }
                throw ParserException.createForMalformedContainer(null, null);
            }
            throw ParserException.createForMalformedContainer(null, null);
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    private void c(ParsableBitArray parsableBitArray) {
        this.o = parsableBitArray.readBits(3);
        switch (this.o) {
            case 0:
                parsableBitArray.skipBits(8);
                return;
            case 1:
                parsableBitArray.skipBits(9);
                return;
            case 2:
            default:
                throw new IllegalStateException();
            case 3:
            case 4:
            case 5:
                parsableBitArray.skipBits(6);
                return;
            case 6:
            case 7:
                parsableBitArray.skipBits(1);
                return;
        }
    }

    private int d(ParsableBitArray parsableBitArray) throws ParserException {
        int bitsLeft = parsableBitArray.bitsLeft();
        AacUtil.Config parseAudioSpecificConfig = AacUtil.parseAudioSpecificConfig(parsableBitArray, true);
        this.u = parseAudioSpecificConfig.codecs;
        this.r = parseAudioSpecificConfig.sampleRateHz;
        this.t = parseAudioSpecificConfig.channelCount;
        return bitsLeft - parsableBitArray.bitsLeft();
    }

    private int e(ParsableBitArray parsableBitArray) throws ParserException {
        int readBits;
        if (this.o == 0) {
            int i = 0;
            do {
                readBits = parsableBitArray.readBits(8);
                i += readBits;
            } while (readBits == 255);
            return i;
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    @RequiresNonNull({"output"})
    private void a(ParsableBitArray parsableBitArray, int i) {
        int position = parsableBitArray.getPosition();
        if ((position & 7) == 0) {
            this.b.setPosition(position >> 3);
        } else {
            parsableBitArray.readBits(this.b.getData(), 0, i * 8);
            this.b.setPosition(0);
        }
        this.d.sampleData(this.b, i);
        long j = this.k;
        if (j != C.TIME_UNSET) {
            this.d.sampleMetadata(j, 1, i, 0, null);
            this.k += this.s;
        }
    }

    private void a(int i) {
        this.b.reset(i);
        this.c.reset(this.b.getData());
    }

    private static long f(ParsableBitArray parsableBitArray) {
        return parsableBitArray.readBits((parsableBitArray.readBits(2) + 1) * 8);
    }
}
