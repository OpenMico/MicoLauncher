package org.conscrypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.ExternalSession;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.SSLParametersImpl;

/* loaded from: classes5.dex */
public class ConscryptFileDescriptorSocket extends OpenSSLSocketImpl implements NativeCrypto.SSLHandshakeCallbacks, SSLParametersImpl.AliasChooser, SSLParametersImpl.PSKCallbacks {
    private static final boolean DBG_STATE = false;
    private final ActiveSession activeSession;
    private OpenSSLKey channelIdPrivateKey;
    private SessionSnapshot closedSession;
    private SSLInputStream is;
    private SSLOutputStream os;
    private final NativeSsl ssl;
    private final SSLParametersImpl sslParameters;
    private int state = 0;
    private final Object guard = Platform.closeGuardGet();
    private final SSLSession externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() { // from class: org.conscrypt.ConscryptFileDescriptorSocket.1
        @Override // org.conscrypt.ExternalSession.Provider
        public ConscryptSession provideSession() {
            return ConscryptFileDescriptorSocket.this.provideSession();
        }
    }));
    private int writeTimeoutMilliseconds = 0;
    private int handshakeTimeoutMilliseconds = -1;

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public final long serverSessionRequested(byte[] bArr) {
        return 0L;
    }

    public ConscryptFileDescriptorSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        this.sslParameters = sSLParametersImpl;
        this.ssl = newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(String str, int i, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(str, i);
        this.sslParameters = sSLParametersImpl;
        this.ssl = newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(InetAddress inetAddress, int i, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, i);
        this.sslParameters = sSLParametersImpl;
        this.ssl = newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(String str, int i, InetAddress inetAddress, int i2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(str, i, inetAddress, i2);
        this.sslParameters = sSLParametersImpl;
        this.ssl = newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, i, inetAddress2, i2);
        this.sslParameters = sSLParametersImpl;
        this.ssl = newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    public ConscryptFileDescriptorSocket(Socket socket, String str, int i, boolean z, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(socket, str, i, z);
        this.sslParameters = sSLParametersImpl;
        this.ssl = newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    private static NativeSsl newSsl(SSLParametersImpl sSLParametersImpl, ConscryptFileDescriptorSocket conscryptFileDescriptorSocket) throws SSLException {
        return NativeSsl.newInstance(sSLParametersImpl, conscryptFileDescriptorSocket, conscryptFileDescriptorSocket, conscryptFileDescriptorSocket);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.conscrypt.NativeSsl] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Unknown variable types count: 2 */
    @Override // javax.net.ssl.SSLSocket
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startHandshake() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 365
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.conscrypt.ConscryptFileDescriptorSocket.startHandshake():void");
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public final void clientCertificateRequested(byte[] bArr, int[] iArr, byte[][] bArr2) throws CertificateEncodingException, SSLException {
        this.ssl.chooseClientCertificate(bArr, iArr, bArr2);
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public final int clientPSKKeyRequested(String str, byte[] bArr, byte[] bArr2) {
        return this.ssl.clientPSKKeyRequested(str, bArr, bArr2);
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public final int serverPSKKeyRequested(String str, String str2, byte[] bArr) {
        return this.ssl.serverPSKKeyRequested(str, str2, bArr);
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public final void onSSLStateChange(int i, int i2) {
        if (i == 32) {
            synchronized (this.ssl) {
                if (this.state != 8) {
                    transitionTo(5);
                    notifyHandshakeCompletedListeners();
                    synchronized (this.ssl) {
                        this.ssl.notifyAll();
                    }
                }
            }
        }
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public final void onNewSessionEstablished(long j) {
        try {
            NativeCrypto.SSL_SESSION_up_ref(j);
            sessionContext().cacheSession(NativeSslSession.newInstance(new NativeRef.SSL_SESSION(j), this.activeSession));
        } catch (Exception unused) {
        }
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public final void verifyCertificateChain(byte[][] bArr, String str) throws CertificateException {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    X509Certificate[] decodeX509CertificateChain = SSLUtils.decodeX509CertificateChain(bArr);
                    X509TrustManager x509TrustManager = this.sslParameters.getX509TrustManager();
                    if (x509TrustManager != null) {
                        this.activeSession.onPeerCertificatesReceived(getHostnameOrIP(), getPort(), decodeX509CertificateChain);
                        if (getUseClientMode()) {
                            Platform.checkServerTrusted(x509TrustManager, decodeX509CertificateChain, str, this);
                            return;
                        } else {
                            Platform.checkClientTrusted(x509TrustManager, decodeX509CertificateChain, decodeX509CertificateChain[0].getPublicKey().getAlgorithm(), this);
                            return;
                        }
                    } else {
                        throw new CertificateException("No X.509 TrustManager");
                    }
                }
            } catch (CertificateException e) {
                throw e;
            } catch (Exception e2) {
                throw new CertificateException(e2);
            }
        }
        throw new CertificateException("Peer sent no certificate");
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, java.net.Socket
    public final InputStream getInputStream() throws IOException {
        SSLInputStream sSLInputStream;
        checkOpen();
        synchronized (this.ssl) {
            if (this.state != 8) {
                if (this.is == null) {
                    this.is = new SSLInputStream();
                }
                sSLInputStream = this.is;
            } else {
                throw new SocketException("Socket is closed.");
            }
        }
        waitForHandshake();
        return sSLInputStream;
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, java.net.Socket
    public final OutputStream getOutputStream() throws IOException {
        SSLOutputStream sSLOutputStream;
        checkOpen();
        synchronized (this.ssl) {
            if (this.state != 8) {
                if (this.os == null) {
                    this.os = new SSLOutputStream();
                }
                sSLOutputStream = this.os;
            } else {
                throw new SocketException("Socket is closed.");
            }
        }
        waitForHandshake();
        return sSLOutputStream;
    }

    private void assertReadableOrWriteableState() {
        int i = this.state;
        if (i != 5 && i != 4) {
            throw new AssertionError("Invalid state: " + this.state);
        }
    }

    private void waitForHandshake() throws IOException {
        startHandshake();
        synchronized (this.ssl) {
            while (this.state != 5 && this.state != 4 && this.state != 8) {
                try {
                    this.ssl.wait();
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

    /* loaded from: classes5.dex */
    private class SSLInputStream extends InputStream {
        private final Object readLock = new Object();

        SSLInputStream() {
            ConscryptFileDescriptorSocket.this = r1;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) != -1) {
                return bArr[0] & 255;
            }
            return -1;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read;
            Platform.blockGuardOnNetwork();
            ConscryptFileDescriptorSocket.this.checkOpen();
            ArrayUtils.checkOffsetAndCount(bArr.length, i, i2);
            if (i2 == 0) {
                return 0;
            }
            synchronized (this.readLock) {
                synchronized (ConscryptFileDescriptorSocket.this.ssl) {
                    if (ConscryptFileDescriptorSocket.this.state == 8) {
                        throw new SocketException("socket is closed");
                    }
                }
                read = ConscryptFileDescriptorSocket.this.ssl.read(Platform.getFileDescriptor(ConscryptFileDescriptorSocket.this.socket), bArr, i, i2, ConscryptFileDescriptorSocket.this.getSoTimeout());
                if (read == -1) {
                    synchronized (ConscryptFileDescriptorSocket.this.ssl) {
                        if (ConscryptFileDescriptorSocket.this.state == 8) {
                            throw new SocketException("socket is closed");
                        }
                    }
                }
            }
            return read;
        }

        @Override // java.io.InputStream
        public int available() {
            return ConscryptFileDescriptorSocket.this.ssl.getPendingReadableBytes();
        }

        void awaitPendingOps() {
            synchronized (this.readLock) {
            }
        }
    }

    /* loaded from: classes5.dex */
    private class SSLOutputStream extends OutputStream {
        private final Object writeLock = new Object();

        SSLOutputStream() {
            ConscryptFileDescriptorSocket.this = r1;
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            write(new byte[]{(byte) (i & 255)});
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptFileDescriptorSocket.this.checkOpen();
            ArrayUtils.checkOffsetAndCount(bArr.length, i, i2);
            if (i2 != 0) {
                synchronized (this.writeLock) {
                    synchronized (ConscryptFileDescriptorSocket.this.ssl) {
                        if (ConscryptFileDescriptorSocket.this.state == 8) {
                            throw new SocketException("socket is closed");
                        }
                    }
                    ConscryptFileDescriptorSocket.this.ssl.write(Platform.getFileDescriptor(ConscryptFileDescriptorSocket.this.socket), bArr, i, i2, ConscryptFileDescriptorSocket.this.writeTimeoutMilliseconds);
                    synchronized (ConscryptFileDescriptorSocket.this.ssl) {
                        if (ConscryptFileDescriptorSocket.this.state == 8) {
                            throw new SocketException("socket is closed");
                        }
                    }
                }
            }
        }

        void awaitPendingOps() {
            synchronized (this.writeLock) {
            }
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public final SSLSession getSession() {
        return this.externalSession;
    }

    public ConscryptSession provideSession() {
        synchronized (this.ssl) {
            if (this.state == 8) {
                return this.closedSession != null ? this.closedSession : SSLNullSession.getNullSession();
            }
            boolean z = false;
            try {
                if (this.state >= 5) {
                    z = true;
                }
                if (!z && isConnected()) {
                    waitForHandshake();
                    z = true;
                }
            } catch (IOException unused) {
            }
            if (!z) {
                return SSLNullSession.getNullSession();
            }
            return this.activeSession;
        }
    }

    public ConscryptSession provideHandshakeSession() {
        ConscryptSession nullSession;
        synchronized (this.ssl) {
            nullSession = (this.state < 2 || this.state >= 5) ? SSLNullSession.getNullSession() : this.activeSession;
        }
        return nullSession;
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    final SSLSession getActiveSession() {
        return this.activeSession;
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, javax.net.ssl.SSLSocket
    public final SSLSession getHandshakeSession() {
        synchronized (this.ssl) {
            if (this.state < 2 || this.state >= 5) {
                return null;
            }
            return Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() { // from class: org.conscrypt.ConscryptFileDescriptorSocket.2
                @Override // org.conscrypt.ExternalSession.Provider
                public ConscryptSession provideSession() {
                    return ConscryptFileDescriptorSocket.this.provideHandshakeSession();
                }
            }));
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setEnableSessionCreation(boolean z) {
        this.sslParameters.setEnableSessionCreation(z);
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setEnabledCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLSocket
    public final String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setEnabledProtocols(strArr);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setUseSessionTickets(boolean z) {
        this.sslParameters.setUseSessionTickets(z);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setHostname(String str) {
        this.sslParameters.setUseSni(str != null);
        super.setHostname(str);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setChannelIdEnabled(boolean z) {
        if (!getUseClientMode()) {
            synchronized (this.ssl) {
                if (this.state != 0) {
                    throw new IllegalStateException("Could not enable/disable Channel ID after the initial handshake has begun.");
                }
            }
            this.sslParameters.channelIdEnabled = z;
            return;
        }
        throw new IllegalStateException("Client mode");
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final byte[] getChannelId() throws SSLException {
        if (!getUseClientMode()) {
            synchronized (this.ssl) {
                if (this.state != 5) {
                    throw new IllegalStateException("Channel ID is only available after handshake completes");
                }
            }
            return this.ssl.getTlsChannelId();
        }
        throw new IllegalStateException("Client mode");
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setChannelIdPrivateKey(PrivateKey privateKey) {
        if (getUseClientMode()) {
            synchronized (this.ssl) {
                if (this.state != 0) {
                    throw new IllegalStateException("Could not change Channel ID private key after the initial handshake has begun.");
                }
            }
            ECParameterSpec eCParameterSpec = null;
            if (privateKey == null) {
                this.sslParameters.channelIdEnabled = false;
                this.channelIdPrivateKey = null;
                return;
            }
            this.sslParameters.channelIdEnabled = true;
            try {
                if (privateKey instanceof ECKey) {
                    eCParameterSpec = ((ECKey) privateKey).getParams();
                }
                if (eCParameterSpec == null) {
                    eCParameterSpec = OpenSSLECGroupContext.getCurveByName("prime256v1").getECParameterSpec();
                }
                this.channelIdPrivateKey = OpenSSLKey.fromECPrivateKeyForTLSStackOnly(privateKey, eCParameterSpec);
            } catch (InvalidKeyException unused) {
            }
        } else {
            throw new IllegalStateException("Server mode");
        }
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public byte[] getTlsUnique() {
        return this.ssl.getTlsUnique();
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i) throws SSLException {
        synchronized (this.ssl) {
            if (this.state >= 3 && this.state != 8) {
                return this.ssl.exportKeyingMaterial(str, bArr, i);
            }
            return null;
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setUseClientMode(boolean z) {
        synchronized (this.ssl) {
            if (this.state != 0) {
                throw new IllegalArgumentException("Could not change the mode after the initial handshake has begun.");
            }
        }
        this.sslParameters.setUseClientMode(z);
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override // javax.net.ssl.SSLSocket
    public final boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setNeedClientAuth(boolean z) {
        this.sslParameters.setNeedClientAuth(z);
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setWantClientAuth(boolean z) {
        this.sslParameters.setWantClientAuth(z);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setSoWriteTimeout(int i) throws SocketException {
        this.writeTimeoutMilliseconds = i;
        Platform.setSocketWriteTimeout(this, i);
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final int getSoWriteTimeout() throws SocketException {
        return this.writeTimeoutMilliseconds;
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket
    public final void setHandshakeTimeout(int i) throws SocketException {
        this.handshakeTimeoutMilliseconds = i;
    }

    @Override // org.conscrypt.OpenSSLSocketImpl, org.conscrypt.AbstractConscryptSocket, java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        NativeSsl nativeSsl = this.ssl;
        if (nativeSsl != null) {
            synchronized (nativeSsl) {
                if (this.state != 8) {
                    int i = this.state;
                    transitionTo(8);
                    if (i == 0) {
                        free();
                        closeUnderlyingSocket();
                        this.ssl.notifyAll();
                    } else if (i == 5 || i == 4) {
                        this.ssl.notifyAll();
                        SSLInputStream sSLInputStream = this.is;
                        SSLOutputStream sSLOutputStream = this.os;
                        if (!(sSLInputStream == null && sSLOutputStream == null)) {
                            this.ssl.interrupt();
                        }
                        if (sSLInputStream != null) {
                            sSLInputStream.awaitPendingOps();
                        }
                        if (sSLOutputStream != null) {
                            sSLOutputStream.awaitPendingOps();
                        }
                        shutdownAndFreeSslNative();
                    } else {
                        this.ssl.interrupt();
                        this.ssl.notifyAll();
                    }
                }
            }
        }
    }

    private void shutdownAndFreeSslNative() throws IOException {
        try {
            Platform.blockGuardOnNetwork();
            this.ssl.shutdown(Platform.getFileDescriptor(this.socket));
        } catch (IOException unused) {
        } catch (Throwable th) {
            free();
            closeUnderlyingSocket();
            throw th;
        }
        free();
        closeUnderlyingSocket();
    }

    private void closeUnderlyingSocket() throws IOException {
        super.close();
    }

    private void free() {
        if (!this.ssl.isClosed()) {
            this.ssl.close();
            Platform.closeGuardClose(this.guard);
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.guard != null) {
                Platform.closeGuardWarnIfOpen(this.guard);
            }
            if (this.ssl != null) {
                synchronized (this.ssl) {
                    transitionTo(8);
                }
            }
        } finally {
            super.finalize();
        }
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector) {
        setApplicationProtocolSelector(applicationProtocolSelector == null ? null : new ApplicationProtocolSelectorAdapter(this, applicationProtocolSelector));
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.sslParameters.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public final void setApplicationProtocols(String[] strArr) {
        this.sslParameters.setApplicationProtocols(strArr);
    }

    @Override // org.conscrypt.AbstractConscryptSocket
    public final String[] getApplicationProtocols() {
        return this.sslParameters.getApplicationProtocols();
    }

    @Override // org.conscrypt.AbstractConscryptSocket, javax.net.ssl.SSLSocket
    public final String getApplicationProtocol() {
        return SSLUtils.toProtocolString(this.ssl.getApplicationProtocol());
    }

    @Override // org.conscrypt.AbstractConscryptSocket, javax.net.ssl.SSLSocket
    public final String getHandshakeApplicationProtocol() {
        String applicationProtocol;
        synchronized (this.ssl) {
            applicationProtocol = (this.state < 2 || this.state >= 5) ? null : getApplicationProtocol();
        }
        return applicationProtocol;
    }

    @Override // javax.net.ssl.SSLSocket
    public final SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = super.getSSLParameters();
        Platform.getSSLParameters(sSLParameters, this.sslParameters, this);
        return sSLParameters;
    }

    @Override // javax.net.ssl.SSLSocket
    public final void setSSLParameters(SSLParameters sSLParameters) {
        super.setSSLParameters(sSLParameters);
        Platform.setSSLParameters(sSLParameters, this.sslParameters, this);
    }

    @Override // org.conscrypt.SSLParametersImpl.AliasChooser
    public final String chooseServerAlias(X509KeyManager x509KeyManager, String str) {
        return x509KeyManager.chooseServerAlias(str, null, this);
    }

    @Override // org.conscrypt.SSLParametersImpl.AliasChooser
    public final String chooseClientAlias(X509KeyManager x509KeyManager, X500Principal[] x500PrincipalArr, String[] strArr) {
        return x509KeyManager.chooseClientAlias(strArr, x500PrincipalArr, this);
    }

    @Override // org.conscrypt.SSLParametersImpl.PSKCallbacks
    public final String chooseServerPSKIdentityHint(PSKKeyManager pSKKeyManager) {
        return pSKKeyManager.chooseServerKeyIdentityHint(this);
    }

    @Override // org.conscrypt.SSLParametersImpl.PSKCallbacks
    public final String chooseClientPSKIdentity(PSKKeyManager pSKKeyManager, String str) {
        return pSKKeyManager.chooseClientKeyIdentity(str, this);
    }

    @Override // org.conscrypt.SSLParametersImpl.PSKCallbacks
    public final SecretKey getPSKKey(PSKKeyManager pSKKeyManager, String str, String str2) {
        return pSKKeyManager.getKey(str, str2, this);
    }

    private ClientSessionContext clientSessionContext() {
        return this.sslParameters.getClientSessionContext();
    }

    private AbstractSessionContext sessionContext() {
        return this.sslParameters.getSessionContext();
    }

    private void transitionTo(int i) {
        int i2;
        if (i == 8 && !this.ssl.isClosed() && (i2 = this.state) >= 2 && i2 < 8) {
            this.closedSession = new SessionSnapshot(this.activeSession);
        }
        this.state = i;
    }
}
