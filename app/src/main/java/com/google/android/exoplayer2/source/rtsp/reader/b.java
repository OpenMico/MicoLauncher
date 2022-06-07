package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* compiled from: RtpH264Reader.java */
/* loaded from: classes2.dex */
final class b implements RtpPayloadReader {
    private final RtpPayloadFormat c;
    private TrackOutput d;
    private int e;
    private int h;
    private long i;
    private final ParsableByteArray b = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private final ParsableByteArray a = new ParsableByteArray();
    private long f = C.TIME_UNSET;
    private int g = -1;

    private static int a(int i) {
        return i == 5 ? 1 : 0;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j, int i) {
    }

    public b(RtpPayloadFormat rtpPayloadFormat) {
        this.c = rtpPayloadFormat;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i) {
        this.d = extractorOutput.track(i, 2);
        ((TrackOutput) Util.castNonNull(this.d)).format(this.c.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j, int i, boolean z) throws ParserException {
        try {
            int i2 = parsableByteArray.getData()[0] & Ascii.US;
            Assertions.checkStateNotNull(this.d);
            if (i2 > 0 && i2 < 24) {
                a(parsableByteArray);
            } else if (i2 == 24) {
                b(parsableByteArray);
            } else if (i2 == 28) {
                a(parsableByteArray, i);
            } else {
                throw ParserException.createForMalformedManifest(String.format("RTP H264 packetization mode [%d] not supported.", Integer.valueOf(i2)), null);
            }
            if (z) {
                if (this.f == C.TIME_UNSET) {
                    this.f = j;
                }
                this.d.sampleMetadata(a(this.i, j, this.f), this.e, this.h, 0, null);
                this.h = 0;
            }
            this.g = i;
        } catch (IndexOutOfBoundsException e) {
            throw ParserException.createForMalformedManifest(null, e);
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long j, long j2) {
        this.f = j;
        this.h = 0;
        this.i = j2;
    }

    @RequiresNonNull({"trackOutput"})
    private void a(ParsableByteArray parsableByteArray) {
        int bytesLeft = parsableByteArray.bytesLeft();
        this.h += a();
        this.d.sampleData(parsableByteArray, bytesLeft);
        this.h += bytesLeft;
        this.e = a(parsableByteArray.getData()[0] & Ascii.US);
    }

    @RequiresNonNull({"trackOutput"})
    private void b(ParsableByteArray parsableByteArray) {
        parsableByteArray.readUnsignedByte();
        while (parsableByteArray.bytesLeft() > 4) {
            int readUnsignedShort = parsableByteArray.readUnsignedShort();
            this.h += a();
            this.d.sampleData(parsableByteArray, readUnsignedShort);
            this.h += readUnsignedShort;
        }
        this.e = 0;
    }

    @RequiresNonNull({"trackOutput"})
    private void a(ParsableByteArray parsableByteArray, int i) {
        byte b = parsableByteArray.getData()[0];
        byte b2 = parsableByteArray.getData()[1];
        int i2 = (b & 224) | (b2 & Ascii.US);
        boolean z = (b2 & 128) > 0;
        boolean z2 = (b2 & SignedBytes.MAX_POWER_OF_TWO) > 0;
        if (z) {
            this.h += a();
            parsableByteArray.getData()[1] = (byte) i2;
            this.a.reset(parsableByteArray.getData());
            this.a.setPosition(1);
        } else {
            int i3 = (this.g + 1) % 65535;
            if (i != i3) {
                Log.w("RtpH264Reader", Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d. Dropping packet.", Integer.valueOf(i3), Integer.valueOf(i)));
                return;
            } else {
                this.a.reset(parsableByteArray.getData());
                this.a.setPosition(2);
            }
        }
        int bytesLeft = this.a.bytesLeft();
        this.d.sampleData(this.a, bytesLeft);
        this.h += bytesLeft;
        if (z2) {
            this.e = a(i2 & 31);
        }
    }

    private int a() {
        this.b.setPosition(0);
        int bytesLeft = this.b.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.d)).sampleData(this.b, bytesLeft);
        return bytesLeft;
    }

    private static long a(long j, long j2, long j3) {
        return j + Util.scaleLargeTimestamp(j2 - j3, 1000000L, 90000L);
    }
}
