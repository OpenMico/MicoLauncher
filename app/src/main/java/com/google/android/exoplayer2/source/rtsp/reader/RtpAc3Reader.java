package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class RtpAc3Reader implements RtpPayloadReader {
    private final RtpPayloadFormat a;
    private TrackOutput c;
    private int d;
    private long f;
    private long g;
    private final ParsableBitArray b = new ParsableBitArray();
    private long e = C.TIME_UNSET;

    public RtpAc3Reader(RtpPayloadFormat rtpPayloadFormat) {
        this.a = rtpPayloadFormat;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i) {
        this.c = extractorOutput.track(i, 1);
        this.c.format(this.a.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j, int i) {
        Assertions.checkState(this.e == C.TIME_UNSET);
        this.e = j;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j, int i, boolean z) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte() & 3;
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte() & 255;
        long a = a(this.g, j, this.e, this.a.clockRate);
        switch (readUnsignedByte) {
            case 0:
                a();
                if (readUnsignedByte2 == 1) {
                    a(parsableByteArray, a);
                    return;
                } else {
                    a(parsableByteArray, readUnsignedByte2, a);
                    return;
                }
            case 1:
            case 2:
                a();
                break;
            case 3:
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(readUnsignedByte));
        }
        a(parsableByteArray, z, readUnsignedByte, a);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long j, long j2) {
        this.e = j;
        this.g = j2;
    }

    private void a(ParsableByteArray parsableByteArray, long j) {
        int bytesLeft = parsableByteArray.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.c)).sampleData(parsableByteArray, bytesLeft);
        ((TrackOutput) Util.castNonNull(this.c)).sampleMetadata(j, 1, bytesLeft, 0, null);
    }

    private void a(ParsableByteArray parsableByteArray, int i, long j) {
        this.b.reset(parsableByteArray.getData());
        this.b.skipBytes(2);
        for (int i2 = 0; i2 < i; i2++) {
            Ac3Util.SyncFrameInfo parseAc3SyncframeInfo = Ac3Util.parseAc3SyncframeInfo(this.b);
            ((TrackOutput) Assertions.checkNotNull(this.c)).sampleData(parsableByteArray, parseAc3SyncframeInfo.frameSize);
            ((TrackOutput) Util.castNonNull(this.c)).sampleMetadata(j, 1, parseAc3SyncframeInfo.frameSize, 0, null);
            j += (parseAc3SyncframeInfo.sampleCount / parseAc3SyncframeInfo.sampleRate) * 1000000;
            this.b.skipBytes(parseAc3SyncframeInfo.frameSize);
        }
    }

    private void a(ParsableByteArray parsableByteArray, boolean z, int i, long j) {
        int bytesLeft = parsableByteArray.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.c)).sampleData(parsableByteArray, bytesLeft);
        this.d += bytesLeft;
        this.f = j;
        if (z && i == 3) {
            b();
        }
    }

    private void a() {
        if (this.d > 0) {
            b();
        }
    }

    private void b() {
        ((TrackOutput) Util.castNonNull(this.c)).sampleMetadata(this.f, 1, this.d, 0, null);
        this.d = 0;
    }

    private static long a(long j, long j2, long j3, int i) {
        return j + Util.scaleLargeTimestamp(j2 - j3, 1000000L, i);
    }
}
