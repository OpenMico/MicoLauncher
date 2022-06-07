package org.eclipse.jetty.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class ChannelEndPoint implements EndPoint {
    private static final Logger LOG = Log.getLogger(ChannelEndPoint.class);
    protected final ByteChannel _channel;
    protected final ByteBuffer[] _gather2;
    private volatile boolean _ishut;
    protected final InetSocketAddress _local;
    protected volatile int _maxIdleTime;
    private volatile boolean _oshut;
    protected final InetSocketAddress _remote;
    protected final Socket _socket;

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockReadable(long j) throws IOException {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockWritable(long j) throws IOException {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void flush() throws IOException {
    }

    public ChannelEndPoint(ByteChannel byteChannel) throws IOException {
        this._gather2 = new ByteBuffer[2];
        this._channel = byteChannel;
        this._socket = byteChannel instanceof SocketChannel ? ((SocketChannel) byteChannel).socket() : null;
        Socket socket = this._socket;
        if (socket != null) {
            this._local = (InetSocketAddress) socket.getLocalSocketAddress();
            this._remote = (InetSocketAddress) this._socket.getRemoteSocketAddress();
            this._maxIdleTime = this._socket.getSoTimeout();
            return;
        }
        this._remote = null;
        this._local = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ChannelEndPoint(ByteChannel byteChannel, int i) throws IOException {
        this._gather2 = new ByteBuffer[2];
        this._channel = byteChannel;
        this._maxIdleTime = i;
        this._socket = byteChannel instanceof SocketChannel ? ((SocketChannel) byteChannel).socket() : null;
        Socket socket = this._socket;
        if (socket != null) {
            this._local = (InetSocketAddress) socket.getLocalSocketAddress();
            this._remote = (InetSocketAddress) this._socket.getRemoteSocketAddress();
            this._socket.setSoTimeout(this._maxIdleTime);
            return;
        }
        this._remote = null;
        this._local = null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isBlocking() {
        ByteChannel byteChannel = this._channel;
        return !(byteChannel instanceof SelectableChannel) || ((SelectableChannel) byteChannel).isBlocking();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOpen() {
        return this._channel.isOpen();
    }

    protected final void shutdownChannelInput() throws IOException {
        Socket socket;
        LOG.debug("ishut {}", this);
        this._ishut = true;
        if (this._channel.isOpen() && (socket = this._socket) != null) {
            try {
                try {
                    if (!socket.isInputShutdown()) {
                        this._socket.shutdownInput();
                    }
                    if (!this._oshut) {
                        return;
                    }
                } catch (SocketException e) {
                    LOG.debug(e.toString(), new Object[0]);
                    LOG.ignore(e);
                    if (!this._oshut) {
                        return;
                    }
                }
                close();
            } catch (Throwable th) {
                if (this._oshut) {
                    close();
                }
                throw th;
            }
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownInput() throws IOException {
        shutdownChannelInput();
    }

    protected final void shutdownChannelOutput() throws IOException {
        Socket socket;
        LOG.debug("oshut {}", this);
        this._oshut = true;
        if (this._channel.isOpen() && (socket = this._socket) != null) {
            try {
                try {
                    if (!socket.isOutputShutdown()) {
                        this._socket.shutdownOutput();
                    }
                    if (!this._ishut) {
                        return;
                    }
                } catch (SocketException e) {
                    LOG.debug(e.toString(), new Object[0]);
                    LOG.ignore(e);
                    if (!this._ishut) {
                        return;
                    }
                }
                close();
            } catch (Throwable th) {
                if (this._ishut) {
                    close();
                }
                throw th;
            }
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownOutput() throws IOException {
        shutdownChannelOutput();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOutputShutdown() {
        Socket socket;
        return this._oshut || !this._channel.isOpen() || ((socket = this._socket) != null && socket.isOutputShutdown());
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isInputShutdown() {
        Socket socket;
        return this._ishut || !this._channel.isOpen() || ((socket = this._socket) != null && socket.isInputShutdown());
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void close() throws IOException {
        LOG.debug("close {}", this);
        this._channel.close();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
        if (r3 >= 0) goto L_0x004d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0034, code lost:
        if (isOpen() == false) goto L_0x004d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
        if (isInputShutdown() != false) goto L_0x003f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003c, code lost:
        shutdownInput();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0043, code lost:
        if (isOutputShutdown() == false) goto L_0x004d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0045, code lost:
        r5._channel.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004b, code lost:
        r6 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0063, code lost:
        org.eclipse.jetty.io.nio.ChannelEndPoint.LOG.debug("Exception while filling", r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0070, code lost:
        if (r5._channel.isOpen() != false) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0072, code lost:
        r5._channel.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0078, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0079, code lost:
        org.eclipse.jetty.io.nio.ChannelEndPoint.LOG.ignore(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007e, code lost:
        if (r3 <= 0) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0080, code lost:
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0081, code lost:
        throw r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:?, code lost:
        return -1;
     */
    @Override // org.eclipse.jetty.io.EndPoint
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int fill(org.eclipse.jetty.io.Buffer r6) throws java.io.IOException {
        /*
            r5 = this;
            boolean r0 = r5._ishut
            r1 = -1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            org.eclipse.jetty.io.Buffer r0 = r6.buffer()
            boolean r2 = r0 instanceof org.eclipse.jetty.io.nio.NIOBuffer
            if (r2 == 0) goto L_0x0082
            org.eclipse.jetty.io.nio.NIOBuffer r0 = (org.eclipse.jetty.io.nio.NIOBuffer) r0
            java.nio.ByteBuffer r0 = r0.getByteBuffer()
            r2 = 0
            monitor-enter(r0)     // Catch: IOException -> 0x0061
            int r3 = r6.putIndex()     // Catch: all -> 0x0052
            r0.position(r3)     // Catch: all -> 0x0052
            java.nio.channels.ByteChannel r3 = r5._channel     // Catch: all -> 0x0052
            int r3 = r3.read(r0)     // Catch: all -> 0x0052
            int r4 = r0.position()     // Catch: all -> 0x004f
            r6.setPutIndex(r4)     // Catch: all -> 0x004f
            r0.position(r2)     // Catch: all -> 0x004f
            monitor-exit(r0)     // Catch: all -> 0x004f
            if (r3 >= 0) goto L_0x004d
            boolean r6 = r5.isOpen()     // Catch: IOException -> 0x004b
            if (r6 == 0) goto L_0x004d
            boolean r6 = r5.isInputShutdown()     // Catch: IOException -> 0x004b
            if (r6 != 0) goto L_0x003f
            r5.shutdownInput()     // Catch: IOException -> 0x004b
        L_0x003f:
            boolean r6 = r5.isOutputShutdown()     // Catch: IOException -> 0x004b
            if (r6 == 0) goto L_0x004d
            java.nio.channels.ByteChannel r6 = r5._channel     // Catch: IOException -> 0x004b
            r6.close()     // Catch: IOException -> 0x004b
            goto L_0x004d
        L_0x004b:
            r6 = move-exception
            goto L_0x0063
        L_0x004d:
            r1 = r3
            goto L_0x0080
        L_0x004f:
            r6 = move-exception
            r2 = r3
            goto L_0x005f
        L_0x0052:
            r3 = move-exception
            int r4 = r0.position()     // Catch: all -> 0x005e
            r6.setPutIndex(r4)     // Catch: all -> 0x005e
            r0.position(r2)     // Catch: all -> 0x005e
            throw r3     // Catch: all -> 0x005e
        L_0x005e:
            r6 = move-exception
        L_0x005f:
            monitor-exit(r0)     // Catch: all -> 0x005e
            throw r6     // Catch: IOException -> 0x0061
        L_0x0061:
            r6 = move-exception
            r3 = r2
        L_0x0063:
            org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.io.nio.ChannelEndPoint.LOG
            java.lang.String r2 = "Exception while filling"
            r0.debug(r2, r6)
            java.nio.channels.ByteChannel r0 = r5._channel     // Catch: Exception -> 0x0078
            boolean r0 = r0.isOpen()     // Catch: Exception -> 0x0078
            if (r0 == 0) goto L_0x007e
            java.nio.channels.ByteChannel r0 = r5._channel     // Catch: Exception -> 0x0078
            r0.close()     // Catch: Exception -> 0x0078
            goto L_0x007e
        L_0x0078:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.io.nio.ChannelEndPoint.LOG
            r2.ignore(r0)
        L_0x007e:
            if (r3 > 0) goto L_0x0081
        L_0x0080:
            return r1
        L_0x0081:
            throw r6
        L_0x0082:
            java.io.IOException r6 = new java.io.IOException
            java.lang.String r0 = "Not Implemented"
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.io.nio.ChannelEndPoint.fill(org.eclipse.jetty.io.Buffer):int");
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer) throws IOException {
        int i;
        Buffer buffer2 = buffer.buffer();
        if (buffer2 instanceof NIOBuffer) {
            ByteBuffer asReadOnlyBuffer = ((NIOBuffer) buffer2).getByteBuffer().asReadOnlyBuffer();
            asReadOnlyBuffer.position(buffer.getIndex());
            asReadOnlyBuffer.limit(buffer.putIndex());
            i = this._channel.write(asReadOnlyBuffer);
            if (i > 0) {
                buffer.skip(i);
            }
        } else if (buffer2 instanceof RandomAccessFileBuffer) {
            i = ((RandomAccessFileBuffer) buffer2).writeTo(this._channel, buffer.getIndex(), buffer.length());
            if (i > 0) {
                buffer.skip(i);
            }
        } else if (buffer.array() != null) {
            i = this._channel.write(ByteBuffer.wrap(buffer.array(), buffer.getIndex(), buffer.length()));
            if (i > 0) {
                buffer.skip(i);
            }
        } else {
            throw new IOException("Not Implemented");
        }
        return i;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        Buffer buffer4 = null;
        Buffer buffer5 = buffer == null ? null : buffer.buffer();
        if (buffer2 != null) {
            buffer4 = buffer2.buffer();
        }
        if ((this._channel instanceof GatheringByteChannel) && buffer != null && buffer.length() != 0 && (buffer5 instanceof NIOBuffer) && buffer2 != null && buffer2.length() != 0 && (buffer4 instanceof NIOBuffer)) {
            return gatheringFlush(buffer, ((NIOBuffer) buffer5).getByteBuffer(), buffer2, ((NIOBuffer) buffer4).getByteBuffer());
        }
        int flush = (buffer == null || buffer.length() <= 0) ? 0 : flush(buffer);
        if ((buffer == null || buffer.length() == 0) && buffer2 != null && buffer2.length() > 0) {
            flush += flush(buffer2);
        }
        return ((buffer == null || buffer.length() == 0) && (buffer2 == null || buffer2.length() == 0) && buffer3 != null && buffer3.length() > 0) ? flush(buffer3) + flush : flush;
    }

    protected int gatheringFlush(Buffer buffer, ByteBuffer byteBuffer, Buffer buffer2, ByteBuffer byteBuffer2) throws IOException {
        int write;
        synchronized (this) {
            ByteBuffer asReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
            asReadOnlyBuffer.position(buffer.getIndex());
            asReadOnlyBuffer.limit(buffer.putIndex());
            ByteBuffer asReadOnlyBuffer2 = byteBuffer2.asReadOnlyBuffer();
            asReadOnlyBuffer2.position(buffer2.getIndex());
            asReadOnlyBuffer2.limit(buffer2.putIndex());
            this._gather2[0] = asReadOnlyBuffer;
            this._gather2[1] = asReadOnlyBuffer2;
            write = (int) ((GatheringByteChannel) this._channel).write(this._gather2);
            int length = buffer.length();
            if (write > length) {
                buffer.clear();
                buffer2.skip(write - length);
            } else if (write > 0) {
                buffer.skip(write);
            }
        }
        return write;
    }

    public ByteChannel getChannel() {
        return this._channel;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalAddr() {
        if (this._socket == null) {
            return null;
        }
        InetSocketAddress inetSocketAddress = this._local;
        return (inetSocketAddress == null || inetSocketAddress.getAddress() == null || this._local.getAddress().isAnyLocalAddress()) ? StringUtil.ALL_INTERFACES : this._local.getAddress().getHostAddress();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalHost() {
        if (this._socket == null) {
            return null;
        }
        InetSocketAddress inetSocketAddress = this._local;
        return (inetSocketAddress == null || inetSocketAddress.getAddress() == null || this._local.getAddress().isAnyLocalAddress()) ? StringUtil.ALL_INTERFACES : this._local.getAddress().getCanonicalHostName();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getLocalPort() {
        if (this._socket == null) {
            return 0;
        }
        InetSocketAddress inetSocketAddress = this._local;
        if (inetSocketAddress == null) {
            return -1;
        }
        return inetSocketAddress.getPort();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteAddr() {
        InetSocketAddress inetSocketAddress;
        if (this._socket == null || (inetSocketAddress = this._remote) == null) {
            return null;
        }
        return inetSocketAddress.getAddress().getHostAddress();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteHost() {
        InetSocketAddress inetSocketAddress;
        if (this._socket == null || (inetSocketAddress = this._remote) == null) {
            return null;
        }
        return inetSocketAddress.getAddress().getCanonicalHostName();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getRemotePort() {
        if (this._socket == null) {
            return 0;
        }
        InetSocketAddress inetSocketAddress = this._remote;
        if (inetSocketAddress == null) {
            return -1;
        }
        return inetSocketAddress.getPort();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public Object getTransport() {
        return this._channel;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void setMaxIdleTime(int i) throws IOException {
        if (!(this._socket == null || i == this._maxIdleTime)) {
            this._socket.setSoTimeout(i > 0 ? i : 0);
        }
        this._maxIdleTime = i;
    }
}
