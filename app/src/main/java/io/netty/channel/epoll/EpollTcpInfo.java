package io.netty.channel.epoll;

/* loaded from: classes4.dex */
public final class EpollTcpInfo {
    final int[] a = new int[32];

    public int state() {
        return this.a[0] & 255;
    }

    public int caState() {
        return this.a[1] & 255;
    }

    public int retransmits() {
        return this.a[2] & 255;
    }

    public int probes() {
        return this.a[3] & 255;
    }

    public int backoff() {
        return this.a[4] & 255;
    }

    public int options() {
        return this.a[5] & 255;
    }

    public int sndWscale() {
        return this.a[6] & 255;
    }

    public int rcvWscale() {
        return this.a[7] & 255;
    }

    public long rto() {
        return this.a[8] & 4294967295L;
    }

    public long ato() {
        return this.a[9] & 4294967295L;
    }

    public long sndMss() {
        return this.a[10] & 4294967295L;
    }

    public long rcvMss() {
        return this.a[11] & 4294967295L;
    }

    public long unacked() {
        return this.a[12] & 4294967295L;
    }

    public long sacked() {
        return this.a[13] & 4294967295L;
    }

    public long lost() {
        return this.a[14] & 4294967295L;
    }

    public long retrans() {
        return this.a[15] & 4294967295L;
    }

    public long fackets() {
        return this.a[16] & 4294967295L;
    }

    public long lastDataSent() {
        return this.a[17] & 4294967295L;
    }

    public long lastAckSent() {
        return this.a[18] & 4294967295L;
    }

    public long lastDataRecv() {
        return this.a[19] & 4294967295L;
    }

    public long lastAckRecv() {
        return this.a[20] & 4294967295L;
    }

    public long pmtu() {
        return this.a[21] & 4294967295L;
    }

    public long rcvSsthresh() {
        return this.a[22] & 4294967295L;
    }

    public long rtt() {
        return this.a[23] & 4294967295L;
    }

    public long rttvar() {
        return this.a[24] & 4294967295L;
    }

    public long sndSsthresh() {
        return this.a[25] & 4294967295L;
    }

    public long sndCwnd() {
        return this.a[26] & 4294967295L;
    }

    public long advmss() {
        return this.a[27] & 4294967295L;
    }

    public long reordering() {
        return this.a[28] & 4294967295L;
    }

    public long rcvRtt() {
        return this.a[29] & 4294967295L;
    }

    public long rcvSpace() {
        return this.a[30] & 4294967295L;
    }

    public long totalRetrans() {
        return this.a[31] & 4294967295L;
    }
}
