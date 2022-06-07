package com.google.android.exoplayer2.source.rtsp;

import android.os.SystemClock;
import androidx.annotation.GuardedBy;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.source.rtsp.reader.DefaultRtpPayloadReaderFactory;
import com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.milink.base.contract.LockContract;
import java.io.IOException;

/* compiled from: RtpExtractor.java */
/* loaded from: classes2.dex */
final class a implements Extractor {
    private final RtpPayloadReader a;
    private final int d;
    private ExtractorOutput g;
    private boolean h;
    @GuardedBy(LockContract.Matcher.LOCK)
    private boolean k;
    private final ParsableByteArray b = new ParsableByteArray((int) RtpPacket.MAX_SIZE);
    private final ParsableByteArray c = new ParsableByteArray();
    private final Object e = new Object();
    private final b f = new b();
    private volatile long i = C.TIME_UNSET;
    private volatile int j = -1;
    @GuardedBy(LockContract.Matcher.LOCK)
    private long l = C.TIME_UNSET;
    @GuardedBy(LockContract.Matcher.LOCK)
    private long m = C.TIME_UNSET;

    private static long b(long j) {
        return j - 30;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public a(RtpPayloadFormat rtpPayloadFormat, int i) {
        this.d = i;
        this.a = (RtpPayloadReader) Assertions.checkNotNull(new DefaultRtpPayloadReaderFactory().createPayloadReader(rtpPayloadFormat));
    }

    public void a(long j) {
        this.i = j;
    }

    public void a(int i) {
        this.j = i;
    }

    public boolean a() {
        return this.h;
    }

    public void b() {
        synchronized (this.e) {
            this.k = true;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) {
        throw new UnsupportedOperationException("RTP packets are transmitted in a packet stream do not support sniffing.");
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.a.createTracks(extractorOutput, this.d);
        extractorOutput.endTracks();
        extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
        this.g = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        Assertions.checkNotNull(this.g);
        int read = extractorInput.read(this.b.getData(), 0, RtpPacket.MAX_SIZE);
        if (read == -1) {
            return -1;
        }
        if (read == 0) {
            return 0;
        }
        this.b.setPosition(0);
        this.b.setLimit(read);
        RtpPacket parse = RtpPacket.parse(this.b);
        if (parse == null) {
            return 0;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long b = b(elapsedRealtime);
        this.f.a(parse, elapsedRealtime);
        RtpPacket a = this.f.a(b);
        if (a == null) {
            return 0;
        }
        if (!this.h) {
            if (this.i == C.TIME_UNSET) {
                this.i = a.timestamp;
            }
            if (this.j == -1) {
                this.j = a.sequenceNumber;
            }
            this.a.onReceivingFirstPacket(this.i, this.j);
            this.h = true;
        }
        synchronized (this.e) {
            if (!this.k) {
                do {
                    this.c.reset(a.payloadData);
                    this.a.consume(this.c, a.timestamp, a.sequenceNumber, a.marker);
                    a = this.f.a(b);
                } while (a != null);
            } else if (!(this.l == C.TIME_UNSET || this.m == C.TIME_UNSET)) {
                this.f.a();
                this.a.seek(this.l, this.m);
                this.k = false;
                this.l = C.TIME_UNSET;
                this.m = C.TIME_UNSET;
            }
        }
        return 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        synchronized (this.e) {
            this.l = j;
            this.m = j2;
        }
    }
}
