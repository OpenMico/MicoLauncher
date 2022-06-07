package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.upstream.BaseDataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* compiled from: TransferRtpDataChannel.java */
/* loaded from: classes2.dex */
final class n extends BaseDataSource implements RtpDataChannel, RtspMessageChannel.InterleavedBinaryDataListener {
    private final long b;
    private final LinkedBlockingQueue<byte[]> a = new LinkedBlockingQueue<>();
    private byte[] c = new byte[0];
    private int d = -1;

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public RtspMessageChannel.InterleavedBinaryDataListener c() {
        return this;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return null;
    }

    public n(long j) {
        super(true);
        this.b = j;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public String a() {
        Assertions.checkState(this.d != -1);
        return Util.formatInvariant("RTP/AVP/TCP;unicast;interleaved=%d-%d", Integer.valueOf(this.d), Integer.valueOf(this.d + 1));
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public int b() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) {
        this.d = dataSpec.uri.getPort();
        return -1L;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        int min = Math.min(i2, this.c.length);
        System.arraycopy(this.c, 0, bArr, i, min);
        int i3 = min + 0;
        byte[] bArr2 = this.c;
        this.c = Arrays.copyOfRange(bArr2, min, bArr2.length);
        if (i3 == i2) {
            return i3;
        }
        try {
            byte[] poll = this.a.poll(this.b, TimeUnit.MILLISECONDS);
            if (poll == null) {
                return -1;
            }
            int min2 = Math.min(i2 - i3, poll.length);
            System.arraycopy(poll, 0, bArr, i + i3, min2);
            if (min2 < poll.length) {
                this.c = Arrays.copyOfRange(poll, min2, poll.length);
            }
            return i3 + min2;
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.InterleavedBinaryDataListener
    public void onInterleavedBinaryDataReceived(byte[] bArr) {
        this.a.add(bArr);
    }
}
