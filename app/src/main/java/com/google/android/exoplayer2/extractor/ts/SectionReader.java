package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class SectionReader implements TsPayloadReader {
    private final SectionPayloadReader a;
    private final ParsableByteArray b = new ParsableByteArray(32);
    private int c;
    private int d;
    private boolean e;
    private boolean f;

    public SectionReader(SectionPayloadReader sectionPayloadReader) {
        this.a = sectionPayloadReader;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.a.init(timestampAdjuster, extractorOutput, trackIdGenerator);
        this.f = true;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public void seek() {
        this.f = true;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public void consume(ParsableByteArray parsableByteArray, int i) {
        boolean z = (i & 1) != 0;
        int readUnsignedByte = z ? parsableByteArray.readUnsignedByte() + parsableByteArray.getPosition() : -1;
        if (this.f) {
            if (z) {
                this.f = false;
                parsableByteArray.setPosition(readUnsignedByte);
                this.d = 0;
            } else {
                return;
            }
        }
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.d;
            if (i2 < 3) {
                if (i2 == 0) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    parsableByteArray.setPosition(parsableByteArray.getPosition() - 1);
                    if (readUnsignedByte2 == 255) {
                        this.f = true;
                        return;
                    }
                }
                int min = Math.min(parsableByteArray.bytesLeft(), 3 - this.d);
                parsableByteArray.readBytes(this.b.getData(), this.d, min);
                this.d += min;
                if (this.d == 3) {
                    this.b.setPosition(0);
                    this.b.setLimit(3);
                    this.b.skipBytes(1);
                    int readUnsignedByte3 = this.b.readUnsignedByte();
                    int readUnsignedByte4 = this.b.readUnsignedByte();
                    this.e = (readUnsignedByte3 & 128) != 0;
                    this.c = (((readUnsignedByte3 & 15) << 8) | readUnsignedByte4) + 3;
                    int capacity = this.b.capacity();
                    int i3 = this.c;
                    if (capacity < i3) {
                        this.b.ensureCapacity(Math.min(4098, Math.max(i3, this.b.capacity() * 2)));
                    }
                }
            } else {
                int min2 = Math.min(parsableByteArray.bytesLeft(), this.c - this.d);
                parsableByteArray.readBytes(this.b.getData(), this.d, min2);
                this.d += min2;
                int i4 = this.d;
                int i5 = this.c;
                if (i4 != i5) {
                    continue;
                } else {
                    if (!this.e) {
                        this.b.setLimit(i5);
                    } else if (Util.crc32(this.b.getData(), 0, this.c, -1) != 0) {
                        this.f = true;
                        return;
                    } else {
                        this.b.setLimit(this.c - 4);
                    }
                    this.b.setPosition(0);
                    this.a.consume(this.b);
                    this.d = 0;
                }
            }
        }
    }
}
