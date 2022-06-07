package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;

/* compiled from: RtpAacReader.java */
/* loaded from: classes2.dex */
final class a implements RtpPayloadReader {
    private final RtpPayloadFormat a;
    private final ParsableBitArray b = new ParsableBitArray();
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private long g;
    private TrackOutput h;
    private long i;

    public a(RtpPayloadFormat rtpPayloadFormat) {
        this.a = rtpPayloadFormat;
        this.c = this.a.clockRate;
        String str = (String) Assertions.checkNotNull(rtpPayloadFormat.fmtpParameters.get("mode"));
        if (Ascii.equalsIgnoreCase(str, "AAC-hbr")) {
            this.d = 13;
            this.e = 3;
        } else if (Ascii.equalsIgnoreCase(str, "AAC-lbr")) {
            this.d = 6;
            this.e = 2;
        } else {
            throw new UnsupportedOperationException("AAC mode not supported");
        }
        this.f = this.e + this.d;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i) {
        this.h = extractorOutput.track(i, 1);
        this.h.format(this.a.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j, int i) {
        this.g = j;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j, int i, boolean z) {
        Assertions.checkNotNull(this.h);
        short readShort = parsableByteArray.readShort();
        int i2 = readShort / this.f;
        long a = a(this.i, j, this.g, this.c);
        this.b.reset(parsableByteArray);
        if (i2 == 1) {
            int readBits = this.b.readBits(this.d);
            this.b.skipBits(this.e);
            this.h.sampleData(parsableByteArray, parsableByteArray.bytesLeft());
            if (z) {
                a(this.h, a, readBits);
                return;
            }
            return;
        }
        parsableByteArray.skipBytes((readShort + 7) / 8);
        for (int i3 = 0; i3 < i2; i3++) {
            int readBits2 = this.b.readBits(this.d);
            this.b.skipBits(this.e);
            this.h.sampleData(parsableByteArray, readBits2);
            a(this.h, a, readBits2);
            a += Util.scaleLargeTimestamp(i2, 1000000L, this.c);
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long j, long j2) {
        this.g = j;
        this.i = j2;
    }

    private static void a(TrackOutput trackOutput, long j, int i) {
        trackOutput.sampleMetadata(j, 1, i, 0, null);
    }

    private static long a(long j, long j2, long j3, int i) {
        return j + Util.scaleLargeTimestamp(j2 - j3, 1000000L, i);
    }
}
