package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.UdpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.io.IOException;

/* compiled from: UdpDataSourceRtpDataChannel.java */
/* loaded from: classes2.dex */
final class p implements RtpDataChannel {
    private final UdpDataSource a;
    @Nullable
    private p b;

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    @Nullable
    public RtspMessageChannel.InterleavedBinaryDataListener c() {
        return null;
    }

    public p(long j) {
        this.a = new UdpDataSource(2000, Ints.checkedCast(j));
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public String a() {
        int b = b();
        Assertions.checkState(b != -1);
        return Util.formatInvariant("RTP/AVP;unicast;client_port=%d-%d", Integer.valueOf(b), Integer.valueOf(b + 1));
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public int b() {
        int localPort = this.a.getLocalPort();
        if (localPort == -1) {
            return -1;
        }
        return localPort;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        this.a.addTransferListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        return this.a.open(dataSpec);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.a.getUri();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        this.a.close();
        p pVar = this.b;
        if (pVar != null) {
            pVar.close();
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        try {
            return this.a.read(bArr, i, i2);
        } catch (UdpDataSource.UdpDataSourceException e) {
            if (e.reason == 2002) {
                return -1;
            }
            throw e;
        }
    }

    public void a(p pVar) {
        Assertions.checkArgument(this != pVar);
        this.b = pVar;
    }
}
