package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* compiled from: UdpDataSourceRtpDataChannelFactory.java */
/* loaded from: classes2.dex */
final class q implements RtpDataChannel.Factory {
    private final long a;

    public q(long j) {
        this.a = j;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel.Factory
    public RtpDataChannel createAndOpenDataChannel(int i) throws IOException {
        p pVar = new p(this.a);
        p pVar2 = new p(this.a);
        boolean z = false;
        try {
            pVar.open(RtpUtils.getIncomingRtpDataSpec(0));
            int b = pVar.b();
            if (b % 2 == 0) {
                z = true;
            }
            pVar2.open(RtpUtils.getIncomingRtpDataSpec(z ? b + 1 : b - 1));
            if (z) {
                pVar.a(pVar2);
                return pVar;
            }
            pVar2.a(pVar);
            return pVar2;
        } catch (IOException e) {
            Util.closeQuietly(pVar);
            Util.closeQuietly(pVar2);
            throw e;
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel.Factory
    public RtpDataChannel.Factory createFallbackDataChannelFactory() {
        return new o(this.a);
    }
}
