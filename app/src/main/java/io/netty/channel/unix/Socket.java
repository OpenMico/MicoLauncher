package io.netty.channel.unix;

import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import io.netty.channel.ChannelException;
import io.netty.util.CharsetUtil;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class Socket extends FileDescriptor {
    private static native int accept(int i, byte[] bArr);

    private static native int bind(int i, byte[] bArr, int i2, int i3);

    private static native int bindDomainSocket(int i, byte[] bArr);

    private static native int connect(int i, byte[] bArr, int i2, int i3);

    private static native int connectDomainSocket(int i, byte[] bArr);

    private static native int finishConnect(int i);

    private static native int getReceiveBufferSize(int i) throws IOException;

    private static native int getSendBufferSize(int i) throws IOException;

    private static native int getSoError(int i) throws IOException;

    private static native int getSoLinger(int i) throws IOException;

    private static native int getTcpDeferAccept(int i) throws IOException;

    private static native int isKeepAlive(int i) throws IOException;

    private static native int isTcpCork(int i) throws IOException;

    private static native int isTcpNoDelay(int i) throws IOException;

    private static native int isTcpQuickAck(int i) throws IOException;

    private static native int listen(int i, int i2);

    private static native byte[] localAddress(int i);

    private static native int newSocketDgramFd();

    private static native int newSocketDomainFd();

    private static native int newSocketStreamFd();

    private static native DatagramSocketAddress recvFrom(int i, ByteBuffer byteBuffer, int i2, int i3) throws IOException;

    private static native DatagramSocketAddress recvFromAddress(int i, long j, int i2, int i3) throws IOException;

    private static native byte[] remoteAddress(int i);

    private static native int sendTo(int i, ByteBuffer byteBuffer, int i2, int i3, byte[] bArr, int i4, int i5);

    private static native int sendToAddress(int i, long j, int i2, int i3, byte[] bArr, int i4, int i5);

    private static native int sendToAddresses(int i, long j, int i2, byte[] bArr, int i3, int i4);

    private static native void setKeepAlive(int i, int i2) throws IOException;

    private static native void setReceiveBufferSize(int i, int i2) throws IOException;

    private static native void setSendBufferSize(int i, int i2) throws IOException;

    private static native void setSoLinger(int i, int i2) throws IOException;

    private static native void setTcpCork(int i, int i2) throws IOException;

    private static native void setTcpDeferAccept(int i, int i2) throws IOException;

    private static native void setTcpNoDelay(int i, int i2) throws IOException;

    private static native void setTcpQuickAck(int i, int i2) throws IOException;

    private static native int shutdown(int i, boolean z, boolean z2);

    public Socket(int i) {
        super(i);
    }

    public void shutdown() throws IOException {
        shutdown(true, true);
    }

    public void shutdown(boolean z, boolean z2) throws IOException {
        int i;
        int d;
        do {
            i = this.a;
            d = (!z || b(i)) ? i : d(i);
            if (z2 && !c(d)) {
                d = e(d);
            }
            if (d == i) {
                return;
            }
        } while (!a(i, d));
        int shutdown = shutdown(this.b, z, z2);
        if (shutdown < 0) {
            Errors.ioResult("shutdown", shutdown, Errors.b);
        }
    }

    public boolean isShutdown() {
        int i = this.a;
        return b(i) && c(i);
    }

    public boolean isInputShutdown() {
        return b(this.a);
    }

    public boolean isOutputShutdown() {
        return c(this.a);
    }

    public int sendTo(ByteBuffer byteBuffer, int i, int i2, InetAddress inetAddress, int i3) throws IOException {
        int i4;
        byte[] bArr;
        if (inetAddress instanceof Inet6Address) {
            byte[] address = inetAddress.getAddress();
            i4 = ((Inet6Address) inetAddress).getScopeId();
            bArr = address;
        } else {
            i4 = 0;
            bArr = NativeInetAddress.ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        int sendTo = sendTo(this.b, byteBuffer, i, i2, bArr, i4, i3);
        return sendTo >= 0 ? sendTo : Errors.ioResult("sendTo", sendTo, Errors.f);
    }

    public int sendToAddress(long j, int i, int i2, InetAddress inetAddress, int i3) throws IOException {
        int i4;
        byte[] bArr;
        if (inetAddress instanceof Inet6Address) {
            byte[] address = inetAddress.getAddress();
            i4 = ((Inet6Address) inetAddress).getScopeId();
            bArr = address;
        } else {
            i4 = 0;
            bArr = NativeInetAddress.ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        int sendToAddress = sendToAddress(this.b, j, i, i2, bArr, i4, i3);
        return sendToAddress >= 0 ? sendToAddress : Errors.ioResult("sendToAddress", sendToAddress, Errors.f);
    }

    public int sendToAddresses(long j, int i, InetAddress inetAddress, int i2) throws IOException {
        int i3;
        byte[] bArr;
        if (inetAddress instanceof Inet6Address) {
            byte[] address = inetAddress.getAddress();
            i3 = ((Inet6Address) inetAddress).getScopeId();
            bArr = address;
        } else {
            i3 = 0;
            bArr = NativeInetAddress.ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        int sendToAddresses = sendToAddresses(this.b, j, i, bArr, i3, i2);
        return sendToAddresses >= 0 ? sendToAddresses : Errors.ioResult("sendToAddresses", sendToAddresses, Errors.g);
    }

    public DatagramSocketAddress recvFrom(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        return recvFrom(this.b, byteBuffer, i, i2);
    }

    public DatagramSocketAddress recvFromAddress(long j, int i, int i2) throws IOException {
        return recvFromAddress(this.b, j, i, i2);
    }

    public boolean connect(SocketAddress socketAddress) throws IOException {
        int i;
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
            NativeInetAddress newInstance = NativeInetAddress.newInstance(inetSocketAddress.getAddress());
            i = connect(this.b, newInstance.a, newInstance.b, inetSocketAddress.getPort());
        } else if (socketAddress instanceof DomainSocketAddress) {
            i = connectDomainSocket(this.b, ((DomainSocketAddress) socketAddress).path().getBytes(CharsetUtil.UTF_8));
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
        if (i >= 0) {
            return true;
        }
        if (i == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
            return false;
        }
        throw Errors.a("connect", i);
    }

    public boolean finishConnect() throws IOException {
        int finishConnect = finishConnect(this.b);
        if (finishConnect >= 0) {
            return true;
        }
        if (finishConnect == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
            return false;
        }
        throw Errors.a("finishConnect", finishConnect);
    }

    public void bind(SocketAddress socketAddress) throws IOException {
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
            NativeInetAddress newInstance = NativeInetAddress.newInstance(inetSocketAddress.getAddress());
            int bind = bind(this.b, newInstance.a, newInstance.b, inetSocketAddress.getPort());
            if (bind < 0) {
                throw Errors.newIOException(SchemaActivity.VALUE_REGISTER_BIND, bind);
            }
        } else if (socketAddress instanceof DomainSocketAddress) {
            int bindDomainSocket = bindDomainSocket(this.b, ((DomainSocketAddress) socketAddress).path().getBytes(CharsetUtil.UTF_8));
            if (bindDomainSocket < 0) {
                throw Errors.newIOException(SchemaActivity.VALUE_REGISTER_BIND, bindDomainSocket);
            }
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
    }

    public void listen(int i) throws IOException {
        int listen = listen(this.b, i);
        if (listen < 0) {
            throw Errors.newIOException("listen", listen);
        }
    }

    public int accept(byte[] bArr) throws IOException {
        int accept = accept(this.b, bArr);
        if (accept >= 0) {
            return accept;
        }
        if (accept == Errors.ERRNO_EAGAIN_NEGATIVE || accept == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return -1;
        }
        throw Errors.newIOException("accept", accept);
    }

    public InetSocketAddress remoteAddress() {
        byte[] remoteAddress = remoteAddress(this.b);
        if (remoteAddress == null) {
            return null;
        }
        return NativeInetAddress.address(remoteAddress, 0, remoteAddress.length);
    }

    public InetSocketAddress localAddress() {
        byte[] localAddress = localAddress(this.b);
        if (localAddress == null) {
            return null;
        }
        return NativeInetAddress.address(localAddress, 0, localAddress.length);
    }

    public int getReceiveBufferSize() throws IOException {
        return getReceiveBufferSize(this.b);
    }

    public int getSendBufferSize() throws IOException {
        return getSendBufferSize(this.b);
    }

    public boolean isKeepAlive() throws IOException {
        return isKeepAlive(this.b) != 0;
    }

    public boolean isTcpNoDelay() throws IOException {
        return isTcpNoDelay(this.b) != 0;
    }

    public boolean isTcpCork() throws IOException {
        return isTcpCork(this.b) != 0;
    }

    public int getSoLinger() throws IOException {
        return getSoLinger(this.b);
    }

    public int getTcpDeferAccept() throws IOException {
        return getTcpDeferAccept(this.b);
    }

    public boolean isTcpQuickAck() throws IOException {
        return isTcpQuickAck(this.b) != 0;
    }

    public int getSoError() throws IOException {
        return getSoError(this.b);
    }

    public void setKeepAlive(boolean z) throws IOException {
        setKeepAlive(this.b, z ? 1 : 0);
    }

    public void setReceiveBufferSize(int i) throws IOException {
        setReceiveBufferSize(this.b, i);
    }

    public void setSendBufferSize(int i) throws IOException {
        setSendBufferSize(this.b, i);
    }

    public void setTcpNoDelay(boolean z) throws IOException {
        setTcpNoDelay(this.b, z ? 1 : 0);
    }

    public void setTcpCork(boolean z) throws IOException {
        setTcpCork(this.b, z ? 1 : 0);
    }

    public void setSoLinger(int i) throws IOException {
        setSoLinger(this.b, i);
    }

    public void setTcpDeferAccept(int i) throws IOException {
        setTcpDeferAccept(this.b, i);
    }

    public void setTcpQuickAck(boolean z) throws IOException {
        setTcpQuickAck(this.b, z ? 1 : 0);
    }

    @Override // io.netty.channel.unix.FileDescriptor
    public String toString() {
        return "Socket{fd=" + this.b + '}';
    }

    public static Socket newSocketStream() {
        int newSocketStreamFd = newSocketStreamFd();
        if (newSocketStreamFd >= 0) {
            return new Socket(newSocketStreamFd);
        }
        throw new ChannelException(Errors.newIOException("newSocketStream", newSocketStreamFd));
    }

    public static Socket newSocketDgram() {
        int newSocketDgramFd = newSocketDgramFd();
        if (newSocketDgramFd >= 0) {
            return new Socket(newSocketDgramFd);
        }
        throw new ChannelException(Errors.newIOException("newSocketDgram", newSocketDgramFd));
    }

    public static Socket newSocketDomain() {
        int newSocketDomainFd = newSocketDomainFd();
        if (newSocketDomainFd >= 0) {
            return new Socket(newSocketDomainFd);
        }
        throw new ChannelException(Errors.newIOException("newSocketDomain", newSocketDomainFd));
    }
}
