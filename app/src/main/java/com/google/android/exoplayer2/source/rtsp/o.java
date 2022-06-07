package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;

/* compiled from: TransferRtpDataChannelFactory.java */
/* loaded from: classes2.dex */
final class o implements RtpDataChannel.Factory {
    private final long a;

    public o(long j) {
        this.a = j;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel.Factory
    public RtpDataChannel createAndOpenDataChannel(int i) {
        n nVar = new n(this.a);
        nVar.open(RtpUtils.getIncomingRtpDataSpec(i * 2));
        return nVar;
    }
}
