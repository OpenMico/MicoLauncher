package org.conscrypt;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes5.dex */
public class ConscryptEngineSocket extends OpenSSLSocketImpl {
    private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
    private final ConscryptEngine engine;
    private SSLInputStream in;
    private SSLOutputStream out;
    private final Object stateLock = new Object();
    private final Object handshakeLock = new Object();
    private BufferAllocator bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
    private int state = 0;

    public ConscryptEngineSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(String str, int i, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(str, i);
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(InetAddress inetAddress, int i, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, i);
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(String str, int i, InetAddress inetAddress, int i2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(str, i, inetAddress, i2);
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, i, inetAddress2, i2);
        this.engine = newEngine(sSLParametersImpl, this);
    }

    public ConscryptEngineSocket(Socket socket, String str, int i, boolean z, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(socket, str, i, z);
        this.engine = newEngine(sSLParametersImpl, this);
    }

    private static ConscryptEngine newEngine(SSLParametersImpl sSLParametersImpl, ConscryptEngineSocket conscryptEngineSocket) {
        ConscryptEngine conscryptEngine = new ConscryptEngine(Platform.supportsX509ExtendedTrustManager() ? sSLParametersImpl.cloneWithTrustManager(getDelegatingTrustManager(sSLParametersImpl.getX509TrustManager(), conscryptEngineSocket)) : sSLParametersImpl, conscryptEngineSocket.peerInfoProvider());
        conscryptEngine.setHandshakeListener(new HandshakeListener() { // from class: org.conscrypt.ConscryptEngineSocket.1
            @Override // org.conscrypt.HandshakeListener
            public void onHandshakeFinished() {
                ConscryptEngineSocket.this.onHandshakeFinished();
            }
        });
        conscryptEngine.setUseClientMode(sSLParametersImpl.getUseClientMode());
        return conscryptEngine;
    }

    private static X509TrustManager getDelegatingTrustManager(X509TrustManager x509TrustManager, final ConscryptEngineSocket conscryptEngineSocket) {
        if (!(x509TrustManager instanceof X509ExtendedTrustManager)) {
            return x509TrustManager;
        }
        final X509ExtendedTrustManager x509ExtendedTrustManager = (X509ExtendedTrustManager) x509TrustManager;
        return new X509ExtendedTrustManager() { // from class: org.conscrypt.ConscryptEngineSocket.2
            @Override // javax.net.ssl.X509ExtendedTrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
                throw new AssertionError("Should not be called");
            }

            @Override // javax.net.ssl.X509ExtendedTrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
                throw new AssertionError("Should not be called");
            }

            @Override // javax.net.ssl.X509ExtendedTrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
                x509ExtendedTrustManager.checkClientTrusted(x509CertificateArr, str, conscryptEngineSocket);
            }

            @Override // javax.net.ssl.X509ExtendedTrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
                x509ExtendedTrustManager.checkServerTrusted(x509CertificateArr, str, conscryptEngineSocket);
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                x509ExtendedTrustManager.checkClientTrusted(x509CertificateArr, str);
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                x509ExtendedTrustManager.checkServerTrusted(x509CertificateArr, str);
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return x509ExtendedTrustManager.getAcceptedIssuers();
            }
        };
    }

    @Override // javax.net.ssl.SSLSocket
    public final SSLParameters getSSLParameters() {
        return this.engine.getSSLParameters();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setSSLParameters(SSLParameters sSLParameters) {
        this.engine.setSSLParameters(sSLParameters);
    }

    @Override // javax.net.ssl.SSLSocket
    public final void startHandshake() throws IOException {
        checkOpen();
        try {
            synchronized (this.handshakeLock) {
                synchronized (this.stateLock) {
                    if (this.state == 0) {
                        this.state = 2;
                        this.engine.beginHandshake();
                        this.in = new SSLInputStream();
                        this.out = new SSLOutputStream();
                        doHandshake();
                    }
                }
            }
        } catch (SSLException e) {
            close();
            throw e;
        } catch (IOException e2) {
            close();
            throw e2;
        } catch (Exception e3) {
            close();
            throw SSLUtils.toSSLHandshakeException(e3);
        }
    }

    public void doHandshake() throws IOException {
        boolean z = false;
        while (!z) {
            try {
                switch (AnonymousClass3.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[this.engine.getHandshakeStatus().ordinal()]) {
                    case 1:
                        if (this.in.processDataFromSocket(EmptyArray.BYTE, 0, 0) >= 0) {
                            break;
                        } else {
                            throw SSLUtils.toSSLHandshakeException(new EOFException());
                        }
                    case 2:
                        this.out.writeInternal(EMPTY_BUFFER);
                        this.out.flushInternal();
                        break;
                    case 3:
                        throw new IllegalStateException("Engine tasks are unsupported");
                    case 4:
                    case 5:
                        z = true;
                        break;
                    default:
                        throw new IllegalStateException("Unknown handshake status: " + this.engine.getHandshakeStatus());
                }
            } catch (SSLException e) {
                drainOutgoingQueue();
                close();
                throw e;
            } catch (IOException e2) {
                close();
                throw e2;
            } catch (Exception e3) {
                close();
                throw SSLUtils.toSSLHandshakeException(e3);
            }
        }
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, java.net.Socket
    public final InputStream getInputStream() throws IOException {
        checkOpen();
        waitForHandshake();
        return this.in;
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, java.net.Socket
    public final OutputStream getOutputStream() throws IOException {
        checkOpen();
        waitForHandshake();
        return this.out;
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, javax.net.ssl.SSLSocket
    public final SSLSession getHandshakeSession() {
        return this.engine.handshakeSession();
    }

    @Override // javax.net.ssl.SSLSocket
    public final SSLSession getSession() {
        if (isConnected()) {
            try {
                waitForHandshake();
            } catch (IOException unused) {
            }
        }
        return this.engine.getSession();
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    final SSLSession getActiveSession() {
        return this.engine.getSession();
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getEnableSessionCreation() {
        return this.engine.getEnableSessionCreation();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setEnableSessionCreation(boolean z) {
        this.engine.setEnableSessionCreation(z);
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getSupportedCipherSuites() {
        return this.engine.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getEnabledCipherSuites() {
        return this.engine.getEnabledCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setEnabledCipherSuites(String[] strArr) {
        this.engine.setEnabledCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getSupportedProtocols() {
        return this.engine.getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getEnabledProtocols() {
        return this.engine.getEnabledProtocols();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setEnabledProtocols(String[] strArr) {
        this.engine.setEnabledProtocols(strArr);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setHostname(String str) {
        this.engine.setHostname(str);
        super.setHostname(str);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setUseSessionTickets(boolean z) {
        this.engine.setUseSessionTickets(z);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setChannelIdEnabled(boolean z) {
        this.engine.setChannelIdEnabled(z);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final byte[] getChannelId() throws SSLException {
        return this.engine.getChannelId();
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.engine.setChannelIdPrivateKey(privateKey);
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public byte[] getTlsUnique() {
        return this.engine.getTlsUnique();
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i) throws SSLException {
        return this.engine.exportKeyingMaterial(str, bArr, i);
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getUseClientMode() {
        return this.engine.getUseClientMode();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setUseClientMode(boolean z) {
        this.engine.setUseClientMode(z);
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getWantClientAuth() {
        return this.engine.getWantClientAuth();
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getNeedClientAuth() {
        return this.engine.getNeedClientAuth();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setNeedClientAuth(boolean z) {
        this.engine.setNeedClientAuth(z);
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setWantClientAuth(boolean z) {
        this.engine.setWantClientAuth(z);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        Object obj = this.stateLock;
        if (obj != null) {
            synchronized (obj) {
                if (this.state != 8) {
                    this.state = 8;
                    this.stateLock.notifyAll();
                    try {
                        super.close();
                    } finally {
                        this.engine.closeInbound();
                        this.engine.closeOutbound();
                        SSLInputStream sSLInputStream = this.in;
                        if (sSLInputStream != null) {
                            sSLInputStream.release();
                        }
                    }
                }
            }
        }
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public final void setApplicationProtocols(String[] strArr) {
        this.engine.setApplicationProtocols(strArr);
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public final String[] getApplicationProtocols() {
        return this.engine.getApplicationProtocols();
    }

    @Override // org.conscrypt.AbstractConscryptSocket, javax.net.ssl.SSLSocket
    public final String getApplicationProtocol() {
        return this.engine.getApplicationProtocol();
    }

    @Override // org.conscrypt.AbstractConscryptSocket, javax.net.ssl.SSLSocket
    public final String getHandshakeApplicationProtocol() {
        return this.engine.getHandshakeApplicationProtocol();
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector) {
        setApplicationProtocolSelector(applicationProtocolSelector == null ? null : new ApplicationProtocolSelectorAdapter(this, applicationProtocolSelector));
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.engine.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    public void setBufferAllocator(BufferAllocator bufferAllocator) {
        this.engine.setBufferAllocator(bufferAllocator);
        this.bufferAllocator = bufferAllocator;
    }

    public void onHandshakeFinished() {
        boolean z;
        synchronized (this.stateLock) {
            if (this.state != 8) {
                if (this.state == 2) {
                    this.state = 4;
                } else if (this.state == 3) {
                    this.state = 5;
                }
                this.stateLock.notifyAll();
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            notifyHandshakeCompletedListeners();
        }
    }

    private void waitForHandshake() throws IOException {
        startHandshake();
        synchronized (this.stateLock) {
            while (this.state != 5 && this.state != 4 && this.state != 8) {
                try {
                    this.stateLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted waiting for handshake", e);
                }
            }
            if (this.state == 8) {
                throw new SocketException("Socket is closed");
            }
        }
    }

    private void drainOutgoingQueue() {
        while (this.engine.pendingOutboundEncryptedBytes() > 0) {
            try {
                this.out.writeInternal(EMPTY_BUFFER);
                this.out.flushInternal();
            } catch (IOException unused) {
                return;
            }
        }
    }

    public OutputStream getUnderlyingOutputStream() throws IOException {
        return super.getOutputStream();
    }

    public InputStream getUnderlyingInputStream() throws IOException {
        return super.getInputStream();
    }

    /* loaded from: classes5.dex */
    public final class SSLOutputStream extends OutputStream {
        private OutputStream socketOutputStream;
        private final ByteBuffer target;
        private final int targetArrayOffset;
        private final Object writeLock = new Object();

        SSLOutputStream() {
            ConscryptEngineSocket.this = r2;
            this.target = ByteBuffer.allocate(r2.engine.getSession().getPacketBufferSize());
            this.targetArrayOffset = this.target.arrayOffset();
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            ConscryptEngineSocket.this.close();
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.writeLock) {
                write(new byte[]{(byte) i});
            }
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.writeLock) {
                writeInternal(ByteBuffer.wrap(bArr));
            }
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.writeLock) {
                writeInternal(ByteBuffer.wrap(bArr, i, i2));
            }
        }

        public void writeInternal(ByteBuffer byteBuffer) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptEngineSocket.this.checkOpen();
            init();
            int remaining = byteBuffer.remaining();
            do {
                this.target.clear();
                SSLEngineResult wrap = ConscryptEngineSocket.this.engine.wrap(byteBuffer, this.target);
                if (wrap.getStatus() != SSLEngineResult.Status.OK) {
                    throw new SSLException("Unexpected engine result " + wrap.getStatus());
                } else if (this.target.position() == wrap.bytesProduced()) {
                    remaining -= wrap.bytesConsumed();
                    if (remaining == byteBuffer.remaining()) {
                        this.target.flip();
                        writeToSocket();
                    } else {
                        throw new SSLException("Engine did not read the correct number of bytes");
                    }
                } else {
                    throw new SSLException("Engine bytesProduced " + wrap.bytesProduced() + " does not match bytes written " + this.target.position());
                }
            } while (remaining > 0);
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.writeLock) {
                flushInternal();
            }
        }

        public void flushInternal() throws IOException {
            ConscryptEngineSocket.this.checkOpen();
            init();
            this.socketOutputStream.flush();
        }

        private void init() throws IOException {
            if (this.socketOutputStream == null) {
                this.socketOutputStream = ConscryptEngineSocket.this.getUnderlyingOutputStream();
            }
        }

        private void writeToSocket() throws IOException {
            this.socketOutputStream.write(this.target.array(), this.targetArrayOffset, this.target.limit());
        }
    }

    /* loaded from: classes5.dex */
    public final class SSLInputStream extends InputStream {
        private final AllocatedBuffer allocatedBuffer;
        private final ByteBuffer fromEngine;
        private final ByteBuffer fromSocket;
        private final int fromSocketArrayOffset;
        private final Object readLock = new Object();
        private final byte[] singleByte = new byte[1];
        private InputStream socketInputStream;

        SSLInputStream() {
            ConscryptEngineSocket.this = r3;
            if (r3.bufferAllocator != null) {
                this.allocatedBuffer = r3.bufferAllocator.allocateDirectBuffer(r3.engine.getSession().getApplicationBufferSize());
                this.fromEngine = this.allocatedBuffer.nioBuffer();
            } else {
                this.allocatedBuffer = null;
                this.fromEngine = ByteBuffer.allocateDirect(r3.engine.getSession().getApplicationBufferSize());
            }
            this.fromEngine.flip();
            this.fromSocket = ByteBuffer.allocate(r3.engine.getSession().getPacketBufferSize());
            this.fromSocketArrayOffset = this.fromSocket.arrayOffset();
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            ConscryptEngineSocket.this.close();
        }

        void release() {
            synchronized (this.readLock) {
                if (this.allocatedBuffer != null) {
                    this.allocatedBuffer.release();
                }
            }
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.readLock) {
                int read = read(this.singleByte, 0, 1);
                if (read == -1) {
                    return -1;
                }
                if (read == 1) {
                    return this.singleByte[0];
                }
                throw new SSLException("read incorrect number of bytes " + read);
            }
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            int read;
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.readLock) {
                read = read(bArr, 0, bArr.length);
            }
            return read;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int readUntilDataAvailable;
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.readLock) {
                readUntilDataAvailable = readUntilDataAvailable(bArr, i, i2);
            }
            return readUntilDataAvailable;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            int remaining;
            ConscryptEngineSocket.this.startHandshake();
            synchronized (this.readLock) {
                init();
                remaining = this.fromEngine.remaining();
            }
            return remaining;
        }

        private boolean isHandshaking(SSLEngineResult.HandshakeStatus handshakeStatus) {
            switch (AnonymousClass3.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[handshakeStatus.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    return true;
                default:
                    return false;
            }
        }

        private int readUntilDataAvailable(byte[] bArr, int i, int i2) throws IOException {
            int processDataFromSocket;
            do {
                processDataFromSocket = processDataFromSocket(bArr, i, i2);
            } while (processDataFromSocket == 0);
            return processDataFromSocket;
        }

        public int processDataFromSocket(byte[] bArr, int i, int i2) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptEngineSocket.this.checkOpen();
            init();
            while (this.fromEngine.remaining() <= 0) {
                boolean z = true;
                this.fromSocket.flip();
                this.fromEngine.clear();
                boolean isHandshaking = isHandshaking(ConscryptEngineSocket.this.engine.getHandshakeStatus());
                SSLEngineResult unwrap = ConscryptEngineSocket.this.engine.unwrap(this.fromSocket, this.fromEngine);
                this.fromSocket.compact();
                this.fromEngine.flip();
                switch (AnonymousClass3.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[unwrap.getStatus().ordinal()]) {
                    case 1:
                        if (unwrap.bytesProduced() != 0) {
                            z = false;
                            break;
                        }
                        break;
                    case 2:
                        if (isHandshaking || !isHandshaking(unwrap.getHandshakeStatus()) || !isHandshakeFinished()) {
                            z = false;
                            break;
                        } else {
                            renegotiate();
                            return 0;
                        }
                        break;
                    case 3:
                        return -1;
                    default:
                        throw new SSLException("Unexpected engine result " + unwrap.getStatus());
                }
                if (!z && unwrap.bytesProduced() == 0) {
                    return 0;
                }
                if (z && readFromSocket() == -1) {
                    return -1;
                }
            }
            int min = Math.min(this.fromEngine.remaining(), i2);
            this.fromEngine.get(bArr, i, min);
            return min;
        }

        private boolean isHandshakeFinished() {
            boolean z;
            synchronized (ConscryptEngineSocket.this.stateLock) {
                z = ConscryptEngineSocket.this.state >= 4;
            }
            return z;
        }

        private void renegotiate() throws IOException {
            synchronized (ConscryptEngineSocket.this.handshakeLock) {
                ConscryptEngineSocket.this.doHandshake();
            }
        }

        private void init() throws IOException {
            if (this.socketInputStream == null) {
                this.socketInputStream = ConscryptEngineSocket.this.getUnderlyingInputStream();
            }
        }

        private int readFromSocket() throws IOException {
            try {
                int position = this.fromSocket.position();
                int read = this.socketInputStream.read(this.fromSocket.array(), this.fromSocketArrayOffset + position, this.fromSocket.limit() - position);
                if (read > 0) {
                    this.fromSocket.position(position + read);
                }
                return read;
            } catch (EOFException unused) {
                return -1;
            }
        }
    }

    /* renamed from: org.conscrypt.ConscryptEngineSocket$3 */
    /* loaded from: classes5.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status = new int[SSLEngineResult.Status.values().length];

        static {
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.BUFFER_UNDERFLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.OK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.CLOSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = new int[SSLEngineResult.HandshakeStatus.values().length];
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }
}
