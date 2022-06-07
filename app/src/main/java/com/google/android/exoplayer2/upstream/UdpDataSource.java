package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.PlaybackException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

/* loaded from: classes2.dex */
public final class UdpDataSource extends BaseDataSource {
    public static final int DEFAULT_MAX_PACKET_SIZE = 2000;
    public static final int DEFAULT_SOCKET_TIMEOUT_MILLIS = 8000;
    public static final int UDP_PORT_UNSET = -1;
    private final int a;
    private final byte[] b;
    private final DatagramPacket c;
    @Nullable
    private Uri d;
    @Nullable
    private DatagramSocket e;
    @Nullable
    private MulticastSocket f;
    @Nullable
    private InetAddress g;
    @Nullable
    private InetSocketAddress h;
    private boolean i;
    private int j;

    /* loaded from: classes2.dex */
    public static final class UdpDataSourceException extends DataSourceException {
        public UdpDataSourceException(Throwable th, int i) {
            super(th, i);
        }
    }

    public UdpDataSource() {
        this(2000);
    }

    public UdpDataSource(int i) {
        this(i, 8000);
    }

    public UdpDataSource(int i, int i2) {
        super(true);
        this.a = i2;
        this.b = new byte[i];
        this.c = new DatagramPacket(this.b, 0, i);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws UdpDataSourceException {
        this.d = dataSpec.uri;
        String host = this.d.getHost();
        int port = this.d.getPort();
        transferInitializing(dataSpec);
        try {
            this.g = InetAddress.getByName(host);
            this.h = new InetSocketAddress(this.g, port);
            if (this.g.isMulticastAddress()) {
                this.f = new MulticastSocket(this.h);
                this.f.joinGroup(this.g);
                this.e = this.f;
            } else {
                this.e = new DatagramSocket(this.h);
            }
            this.e.setSoTimeout(this.a);
            this.i = true;
            transferStarted(dataSpec);
            return -1L;
        } catch (IOException e) {
            throw new UdpDataSourceException(e, 2001);
        } catch (SecurityException e2) {
            throw new UdpDataSourceException(e2, PlaybackException.ERROR_CODE_IO_NO_PERMISSION);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws UdpDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        if (this.j == 0) {
            try {
                this.e.receive(this.c);
                this.j = this.c.getLength();
                bytesTransferred(this.j);
            } catch (SocketTimeoutException e) {
                throw new UdpDataSourceException(e, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT);
            } catch (IOException e2) {
                throw new UdpDataSourceException(e2, 2001);
            }
        }
        int length = this.c.getLength();
        int i3 = this.j;
        int min = Math.min(i3, i2);
        System.arraycopy(this.b, length - i3, bArr, i, min);
        this.j -= min;
        return min;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        this.d = null;
        MulticastSocket multicastSocket = this.f;
        if (multicastSocket != null) {
            try {
                multicastSocket.leaveGroup(this.g);
            } catch (IOException unused) {
            }
            this.f = null;
        }
        DatagramSocket datagramSocket = this.e;
        if (datagramSocket != null) {
            datagramSocket.close();
            this.e = null;
        }
        this.g = null;
        this.h = null;
        this.j = 0;
        if (this.i) {
            this.i = false;
            transferEnded();
        }
    }

    public int getLocalPort() {
        DatagramSocket datagramSocket = this.e;
        if (datagramSocket == null) {
            return -1;
        }
        return datagramSocket.getLocalPort();
    }
}
