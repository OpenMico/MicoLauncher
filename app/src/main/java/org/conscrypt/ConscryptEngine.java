package org.conscrypt;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.ExternalSession;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.NativeSsl;
import org.conscrypt.SSLParametersImpl;

/* loaded from: classes5.dex */
public final class ConscryptEngine extends AbstractConscryptEngine implements NativeCrypto.SSLHandshakeCallbacks, SSLParametersImpl.AliasChooser, SSLParametersImpl.PSKCallbacks {
    private ActiveSession activeSession;
    private BufferAllocator bufferAllocator;
    private OpenSSLKey channelIdPrivateKey;
    private SessionSnapshot closedSession;
    private final SSLSession externalSession;
    private boolean handshakeFinished;
    private HandshakeListener handshakeListener;
    private ByteBuffer lazyDirectBuffer;
    private int maxSealOverhead;
    private final NativeSsl.BioWrapper networkBio;
    private String peerHostname;
    private final PeerInfoProvider peerInfoProvider;
    private final ByteBuffer[] singleDstBuffer;
    private final ByteBuffer[] singleSrcBuffer;
    private final NativeSsl ssl;
    private final SSLParametersImpl sslParameters;
    private int state;
    private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    private static BufferAllocator defaultBufferAllocator = null;

    @Override // javax.net.ssl.SSLEngine
    public Runnable getDelegatedTask() {
        return null;
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public long serverSessionRequested(byte[] bArr) {
        return 0L;
    }

    public ConscryptEngine(SSLParametersImpl sSLParametersImpl) {
        this.bufferAllocator = defaultBufferAllocator;
        this.state = 0;
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() { // from class: org.conscrypt.ConscryptEngine.1
            @Override // org.conscrypt.ExternalSession.Provider
            public ConscryptSession provideSession() {
                return ConscryptEngine.this.provideSession();
            }
        }));
        this.singleSrcBuffer = new ByteBuffer[1];
        this.singleDstBuffer = new ByteBuffer[1];
        this.sslParameters = sSLParametersImpl;
        this.peerInfoProvider = PeerInfoProvider.nullProvider();
        this.ssl = newSsl(sSLParametersImpl, this);
        this.networkBio = this.ssl.newBio();
    }

    public ConscryptEngine(String str, int i, SSLParametersImpl sSLParametersImpl) {
        this.bufferAllocator = defaultBufferAllocator;
        this.state = 0;
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() { // from class: org.conscrypt.ConscryptEngine.1
            @Override // org.conscrypt.ExternalSession.Provider
            public ConscryptSession provideSession() {
                return ConscryptEngine.this.provideSession();
            }
        }));
        this.singleSrcBuffer = new ByteBuffer[1];
        this.singleDstBuffer = new ByteBuffer[1];
        this.sslParameters = sSLParametersImpl;
        this.peerInfoProvider = PeerInfoProvider.forHostAndPort(str, i);
        this.ssl = newSsl(sSLParametersImpl, this);
        this.networkBio = this.ssl.newBio();
    }

    public ConscryptEngine(SSLParametersImpl sSLParametersImpl, PeerInfoProvider peerInfoProvider) {
        this.bufferAllocator = defaultBufferAllocator;
        this.state = 0;
        this.externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() { // from class: org.conscrypt.ConscryptEngine.1
            @Override // org.conscrypt.ExternalSession.Provider
            public ConscryptSession provideSession() {
                return ConscryptEngine.this.provideSession();
            }
        }));
        this.singleSrcBuffer = new ByteBuffer[1];
        this.singleDstBuffer = new ByteBuffer[1];
        this.sslParameters = sSLParametersImpl;
        this.peerInfoProvider = (PeerInfoProvider) Preconditions.checkNotNull(peerInfoProvider, "peerInfoProvider");
        this.ssl = newSsl(sSLParametersImpl, this);
        this.networkBio = this.ssl.newBio();
    }

    private static NativeSsl newSsl(SSLParametersImpl sSLParametersImpl, ConscryptEngine conscryptEngine) {
        try {
            return NativeSsl.newInstance(sSLParametersImpl, conscryptEngine, conscryptEngine, conscryptEngine);
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDefaultBufferAllocator(BufferAllocator bufferAllocator) {
        defaultBufferAllocator = bufferAllocator;
    }

    public static BufferAllocator getDefaultBufferAllocator() {
        return defaultBufferAllocator;
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public void setBufferAllocator(BufferAllocator bufferAllocator) {
        synchronized (this.ssl) {
            if (!isHandshakeStarted()) {
                this.bufferAllocator = bufferAllocator;
            } else {
                throw new IllegalStateException("Could not set buffer allocator after the initial handshake has begun.");
            }
        }
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public int maxSealOverhead() {
        return this.maxSealOverhead;
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public void setChannelIdEnabled(boolean z) {
        synchronized (this.ssl) {
            if (getUseClientMode()) {
                throw new IllegalStateException("Not allowed in client mode");
            } else if (!isHandshakeStarted()) {
                this.sslParameters.channelIdEnabled = z;
            } else {
                throw new IllegalStateException("Could not enable/disable Channel ID after the initial handshake has begun.");
            }
        }
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public byte[] getChannelId() throws SSLException {
        byte[] tlsChannelId;
        synchronized (this.ssl) {
            if (getUseClientMode()) {
                throw new IllegalStateException("Not allowed in client mode");
            } else if (!isHandshakeStarted()) {
                tlsChannelId = this.ssl.getTlsChannelId();
            } else {
                throw new IllegalStateException("Channel ID is only available after handshake completes");
            }
        }
        return tlsChannelId;
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public void setChannelIdPrivateKey(PrivateKey privateKey) {
        if (getUseClientMode()) {
            synchronized (this.ssl) {
                if (!isHandshakeStarted()) {
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
                    return;
                }
                throw new IllegalStateException("Could not change Channel ID private key after the initial handshake has begun.");
            }
        }
        throw new IllegalStateException("Not allowed in server mode");
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public void setHandshakeListener(HandshakeListener handshakeListener) {
        synchronized (this.ssl) {
            if (!isHandshakeStarted()) {
                this.handshakeListener = handshakeListener;
            } else {
                throw new IllegalStateException("Handshake listener must be set before starting the handshake.");
            }
        }
    }

    private boolean isHandshakeStarted() {
        switch (this.state) {
            case 0:
            case 1:
                return false;
            default:
                return true;
        }
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public void setHostname(String str) {
        this.sslParameters.setUseSni(str != null);
        this.peerHostname = str;
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public String getHostname() {
        String str = this.peerHostname;
        return str != null ? str : this.peerInfoProvider.getHostname();
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public String getPeerHost() {
        String str = this.peerHostname;
        return str != null ? str : this.peerInfoProvider.getHostnameOrIP();
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public int getPeerPort() {
        return this.peerInfoProvider.getPort();
    }

    @Override // javax.net.ssl.SSLEngine
    public void beginHandshake() throws SSLException {
        synchronized (this.ssl) {
            beginHandshakeInternal();
        }
    }

    private void beginHandshakeInternal() throws SSLException {
        NativeSslSession cachedSession;
        int i = this.state;
        switch (i) {
            case 0:
                throw new IllegalStateException("Client/server mode must be set before handshake");
            case 1:
                try {
                    transitionTo(2);
                    try {
                        this.ssl.initialize(getHostname(), this.channelIdPrivateKey);
                        if (getUseClientMode() && (cachedSession = clientSessionContext().getCachedSession(getHostname(), getPeerPort(), this.sslParameters)) != null) {
                            cachedSession.offerToResume(this.ssl);
                        }
                        this.maxSealOverhead = this.ssl.getMaxSealOverhead();
                        handshake();
                        return;
                    } catch (IOException e) {
                        if (e.getMessage().contains("unexpected CCS")) {
                            Platform.logEvent(String.format("ssl_unexpected_ccs: host=%s", getPeerHost()));
                        }
                        throw SSLUtils.toSSLHandshakeException(e);
                    }
                } catch (Throwable th) {
                    closeAndFreeResources();
                    throw th;
                }
            default:
                switch (i) {
                    case 6:
                    case 7:
                    case 8:
                        throw new IllegalStateException("Engine has already been closed");
                    default:
                        return;
                }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public void closeInbound() {
        synchronized (this.ssl) {
            if (!(this.state == 8 || this.state == 6)) {
                if (isHandshakeStarted()) {
                    if (this.state == 7) {
                        transitionTo(8);
                    } else {
                        transitionTo(6);
                    }
                    freeIfDone();
                } else {
                    closeAndFreeResources();
                }
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public void closeOutbound() {
        synchronized (this.ssl) {
            if (!(this.state == 8 || this.state == 7)) {
                if (isHandshakeStarted()) {
                    if (this.state == 6) {
                        transitionTo(8);
                    } else {
                        transitionTo(7);
                    }
                    sendSSLShutdown();
                    freeIfDone();
                } else {
                    closeAndFreeResources();
                }
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = super.getSSLParameters();
        Platform.getSSLParameters(sSLParameters, this.sslParameters, this);
        return sSLParameters;
    }

    @Override // javax.net.ssl.SSLEngine
    public void setSSLParameters(SSLParameters sSLParameters) {
        super.setSSLParameters(sSLParameters);
        Platform.setSSLParameters(sSLParameters, this.sslParameters, this);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        SSLEngineResult.HandshakeStatus handshakeStatusInternal;
        synchronized (this.ssl) {
            handshakeStatusInternal = getHandshakeStatusInternal();
        }
        return handshakeStatusInternal;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatusInternal() {
        if (this.handshakeFinished) {
            return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        }
        switch (this.state) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            case 2:
                return pendingStatus(pendingOutboundEncryptedBytes());
            case 3:
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            default:
                throw new IllegalStateException("Unexpected engine state: " + this.state);
        }
    }

    public int pendingOutboundEncryptedBytes() {
        return this.networkBio.getPendingWrittenBytes();
    }

    private int pendingInboundCleartextBytes() {
        return this.ssl.getPendingReadableBytes();
    }

    private static SSLEngineResult.HandshakeStatus pendingStatus(int i) {
        return i > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public SSLSession handshakeSession() {
        synchronized (this.ssl) {
            if (this.state != 2) {
                return null;
            }
            return Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider() { // from class: org.conscrypt.ConscryptEngine.2
                @Override // org.conscrypt.ExternalSession.Provider
                public ConscryptSession provideSession() {
                    return ConscryptEngine.this.provideHandshakeSession();
                }
            }));
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLSession getSession() {
        return this.externalSession;
    }

    public ConscryptSession provideSession() {
        synchronized (this.ssl) {
            if (this.state == 8) {
                return this.closedSession != null ? this.closedSession : SSLNullSession.getNullSession();
            } else if (this.state < 3) {
                return SSLNullSession.getNullSession();
            } else {
                return this.activeSession;
            }
        }
    }

    public ConscryptSession provideHandshakeSession() {
        ConscryptSession nullSession;
        synchronized (this.ssl) {
            nullSession = this.state == 2 ? this.activeSession : SSLNullSession.getNullSession();
        }
        return nullSession;
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean isInboundDone() {
        boolean z;
        synchronized (this.ssl) {
            z = (this.state == 8 || this.state == 6 || this.ssl.wasShutdownReceived()) && pendingInboundCleartextBytes() == 0;
        }
        return z;
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean isOutboundDone() {
        boolean z;
        synchronized (this.ssl) {
            z = (this.state == 8 || this.state == 7 || this.ssl.wasShutdownSent()) && pendingOutboundEncryptedBytes() == 0;
        }
        return z;
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setEnabledCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setEnabledProtocols(strArr);
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnableSessionCreation(boolean z) {
        this.sslParameters.setEnableSessionCreation(z);
    }

    @Override // javax.net.ssl.SSLEngine
    public void setNeedClientAuth(boolean z) {
        this.sslParameters.setNeedClientAuth(z);
    }

    @Override // javax.net.ssl.SSLEngine
    public void setUseClientMode(boolean z) {
        synchronized (this.ssl) {
            if (!isHandshakeStarted()) {
                transitionTo(1);
                this.sslParameters.setUseClientMode(z);
            } else {
                throw new IllegalArgumentException("Can not change mode after handshake: state == " + this.state);
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public void setWantClientAuth(boolean z) {
        this.sslParameters.setWantClientAuth(z);
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult unwrap;
        synchronized (this.ssl) {
            unwrap = unwrap(singleSrcBuffer(byteBuffer), singleDstBuffer(byteBuffer2));
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
        }
        return unwrap;
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) throws SSLException {
        SSLEngineResult unwrap;
        synchronized (this.ssl) {
            unwrap = unwrap(singleSrcBuffer(byteBuffer), byteBufferArr);
            resetSingleSrcBuffer();
        }
        return unwrap;
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2) throws SSLException {
        SSLEngineResult unwrap;
        synchronized (this.ssl) {
            unwrap = unwrap(singleSrcBuffer(byteBuffer), 0, 1, byteBufferArr, i, i2);
            resetSingleSrcBuffer();
        }
        return unwrap;
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2) throws SSLException {
        boolean z = true;
        Preconditions.checkArgument(byteBufferArr != null, "srcs is null");
        if (byteBufferArr2 == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "dsts is null");
        return unwrap(byteBufferArr, 0, byteBufferArr.length, byteBufferArr2, 0, byteBufferArr2.length);
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer[] byteBufferArr2, int i3, int i4) throws SSLException {
        int i5;
        int i6;
        int i7;
        boolean z = true;
        int i8 = 0;
        Preconditions.checkArgument(byteBufferArr != null, "srcs is null");
        Preconditions.checkArgument(byteBufferArr2 != null, "dsts is null");
        int i9 = i2 + i;
        Preconditions.checkPositionIndexes(i, i9, byteBufferArr.length);
        int i10 = i3 + i4;
        Preconditions.checkPositionIndexes(i3, i10, byteBufferArr2.length);
        int calcDstsLength = calcDstsLength(byteBufferArr2, i3, i4);
        long calcSrcsLength = calcSrcsLength(byteBufferArr, i, i9);
        synchronized (this.ssl) {
            int i11 = this.state;
            if (i11 == 6 || i11 == 8) {
                freeIfDone();
                return new SSLEngineResult(SSLEngineResult.Status.CLOSED, getHandshakeStatusInternal(), 0, 0);
            }
            switch (i11) {
                case 0:
                    throw new IllegalStateException("Client/server mode must be set before calling unwrap");
                case 1:
                    beginHandshakeInternal();
                    break;
            }
            SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            if (!this.handshakeFinished) {
                handshakeStatus = handshake();
                if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                    return NEED_WRAP_OK;
                } else if (this.state == 8) {
                    return NEED_WRAP_CLOSED;
                }
            }
            if (pendingInboundCleartextBytes() > 0) {
                z = false;
            }
            if (calcSrcsLength <= 0 || !z) {
                if (z) {
                    return new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, getHandshakeStatus(), 0, 0);
                }
                i5 = 0;
            } else if (calcSrcsLength < 5) {
                return new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, getHandshakeStatus(), 0, 0);
            } else {
                i5 = SSLUtils.getEncryptedPacketLength(byteBufferArr, i);
                if (i5 < 0) {
                    throw new SSLException("Unable to parse TLS packet header");
                } else if (calcSrcsLength < i5) {
                    return new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, getHandshakeStatus(), 0, 0);
                }
            }
            if (i5 <= 0 || i >= i9) {
                i6 = 0;
            } else {
                i6 = 0;
                while (true) {
                    ByteBuffer byteBuffer = byteBufferArr[i];
                    int remaining = byteBuffer.remaining();
                    if (remaining == 0) {
                        i++;
                        continue;
                    } else {
                        int writeEncryptedData = writeEncryptedData(byteBuffer, Math.min(i5, remaining));
                        if (writeEncryptedData > 0) {
                            i6 += writeEncryptedData;
                            i5 -= writeEncryptedData;
                            if (i5 != 0 && writeEncryptedData == remaining) {
                                i++;
                                continue;
                            }
                        } else {
                            NativeCrypto.SSL_clear_error();
                        }
                    }
                    if (i >= i9) {
                    }
                }
            }
            try {
                try {
                    if (calcDstsLength > 0) {
                        i7 = 0;
                        while (i3 < i10) {
                            try {
                                ByteBuffer byteBuffer2 = byteBufferArr2[i3];
                                if (byteBuffer2.hasRemaining()) {
                                    int readPlaintextData = readPlaintextData(byteBuffer2);
                                    if (readPlaintextData > 0) {
                                        i7 += readPlaintextData;
                                        if (byteBuffer2.hasRemaining()) {
                                        }
                                    } else if (readPlaintextData != -6) {
                                        switch (readPlaintextData) {
                                            case -3:
                                            case -2:
                                                return newResult(i6, i7, handshakeStatus);
                                            default:
                                                sendSSLShutdown();
                                                throw newSslExceptionWithMessage("SSL_read");
                                        }
                                    } else {
                                        closeInbound();
                                        sendSSLShutdown();
                                        return new SSLEngineResult(SSLEngineResult.Status.CLOSED, pendingOutboundEncryptedBytes() > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, i6, i7);
                                    }
                                }
                                i3++;
                            } catch (InterruptedIOException unused) {
                                return newResult(i6, i7, handshakeStatus);
                            }
                        }
                    } else {
                        try {
                            this.ssl.forceRead();
                            i7 = 0;
                        } catch (InterruptedIOException unused2) {
                            i7 = 0;
                            return newResult(i6, i7, handshakeStatus);
                        }
                    }
                    if (this.handshakeFinished) {
                        i8 = pendingInboundCleartextBytes();
                    }
                    if (i8 > 0) {
                        SSLEngineResult.Status status = SSLEngineResult.Status.BUFFER_OVERFLOW;
                        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                            handshakeStatus = getHandshakeStatusInternal();
                        }
                        return new SSLEngineResult(status, mayFinishHandshake(handshakeStatus), i6, i7);
                    }
                    return newResult(i6, i7, handshakeStatus);
                } catch (EOFException e) {
                    closeAll();
                    throw convertException(e);
                }
            } catch (SSLException e2) {
                sendSSLShutdown();
                throw convertException(e2);
            } catch (IOException e3) {
                sendSSLShutdown();
                throw convertException(e3);
            }
        }
    }

    private static int calcDstsLength(ByteBuffer[] byteBufferArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < byteBufferArr.length; i4++) {
            ByteBuffer byteBuffer = byteBufferArr[i4];
            Preconditions.checkArgument(byteBuffer != null, "dsts[%d] is null", Integer.valueOf(i4));
            if (!byteBuffer.isReadOnly()) {
                if (i4 >= i && i4 < i + i2) {
                    i3 += byteBuffer.remaining();
                }
            } else {
                throw new ReadOnlyBufferException();
            }
        }
        return i3;
    }

    private static long calcSrcsLength(ByteBuffer[] byteBufferArr, int i, int i2) {
        long j = 0;
        while (i < i2) {
            ByteBuffer byteBuffer = byteBufferArr[i];
            if (byteBuffer != null) {
                j += byteBuffer.remaining();
                i++;
            } else {
                throw new IllegalArgumentException("srcs[" + i + "] is null");
            }
        }
        return j;
    }

    private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
        try {
            try {
                try {
                    switch (this.ssl.doHandshake()) {
                        case 2:
                            return pendingStatus(pendingOutboundEncryptedBytes());
                        case 3:
                            return SSLEngineResult.HandshakeStatus.NEED_WRAP;
                        default:
                            this.activeSession.onPeerCertificateAvailable(getPeerHost(), getPeerPort());
                            finishHandshake();
                            return SSLEngineResult.HandshakeStatus.FINISHED;
                    }
                } catch (SSLException e) {
                    sendSSLShutdown();
                    throw e;
                }
            } catch (IOException e2) {
                sendSSLShutdown();
                throw e2;
            }
        } catch (Exception e3) {
            throw SSLUtils.toSSLHandshakeException(e3);
        }
    }

    private void finishHandshake() throws SSLException {
        this.handshakeFinished = true;
        HandshakeListener handshakeListener = this.handshakeListener;
        if (handshakeListener != null) {
            handshakeListener.onHandshakeFinished();
        }
    }

    private int writePlaintextData(ByteBuffer byteBuffer, int i) throws SSLException {
        int i2;
        try {
            int position = byteBuffer.position();
            if (byteBuffer.isDirect()) {
                i2 = writePlaintextDataDirect(byteBuffer, position, i);
            } else {
                i2 = writePlaintextDataHeap(byteBuffer, position, i);
            }
            if (i2 > 0) {
                byteBuffer.position(position + i2);
            }
            return i2;
        } catch (Exception e) {
            throw convertException(e);
        }
    }

    private int writePlaintextDataDirect(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        return this.ssl.writeDirectByteBuffer(directByteBufferAddress(byteBuffer, i), i2);
    }

    private int writePlaintextDataHeap(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        ByteBuffer byteBuffer2;
        AllocatedBuffer allocatedBuffer = null;
        try {
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(i2);
                byteBuffer2 = allocatedBuffer.nioBuffer();
            } else {
                byteBuffer2 = getOrCreateLazyDirectBuffer();
            }
            int limit = byteBuffer.limit();
            int min = Math.min(i2, byteBuffer2.remaining());
            byteBuffer.limit(i + min);
            byteBuffer2.put(byteBuffer);
            byteBuffer2.flip();
            byteBuffer.limit(limit);
            byteBuffer.position(i);
            return writePlaintextDataDirect(byteBuffer2, 0, min);
        } finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private int readPlaintextData(ByteBuffer byteBuffer) throws IOException {
        try {
            int position = byteBuffer.position();
            int min = Math.min(16709, byteBuffer.limit() - position);
            if (!byteBuffer.isDirect()) {
                return readPlaintextDataHeap(byteBuffer, min);
            }
            int readPlaintextDataDirect = readPlaintextDataDirect(byteBuffer, position, min);
            if (readPlaintextDataDirect > 0) {
                byteBuffer.position(position + readPlaintextDataDirect);
            }
            return readPlaintextDataDirect;
        } catch (CertificateException e) {
            throw convertException(e);
        }
    }

    private int readPlaintextDataDirect(ByteBuffer byteBuffer, int i, int i2) throws IOException, CertificateException {
        return this.ssl.readDirectByteBuffer(directByteBufferAddress(byteBuffer, i), i2);
    }

    private int readPlaintextDataHeap(ByteBuffer byteBuffer, int i) throws IOException, CertificateException {
        ByteBuffer byteBuffer2;
        AllocatedBuffer allocatedBuffer = null;
        try {
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(i);
                byteBuffer2 = allocatedBuffer.nioBuffer();
            } else {
                byteBuffer2 = getOrCreateLazyDirectBuffer();
            }
            int readPlaintextDataDirect = readPlaintextDataDirect(byteBuffer2, 0, Math.min(i, byteBuffer2.remaining()));
            if (readPlaintextDataDirect > 0) {
                byteBuffer2.position(readPlaintextDataDirect);
                byteBuffer2.flip();
                byteBuffer.put(byteBuffer2);
            }
            return readPlaintextDataDirect;
        } finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private SSLException convertException(Throwable th) {
        if ((th instanceof SSLHandshakeException) || !this.handshakeFinished) {
            return SSLUtils.toSSLHandshakeException(th);
        }
        return SSLUtils.toSSLException(th);
    }

    private int writeEncryptedData(ByteBuffer byteBuffer, int i) throws SSLException {
        int i2;
        try {
            int position = byteBuffer.position();
            if (byteBuffer.isDirect()) {
                i2 = writeEncryptedDataDirect(byteBuffer, position, i);
            } else {
                i2 = writeEncryptedDataHeap(byteBuffer, position, i);
            }
            if (i2 > 0) {
                byteBuffer.position(position + i2);
            }
            return i2;
        } catch (IOException e) {
            throw new SSLException(e);
        }
    }

    private int writeEncryptedDataDirect(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        return this.networkBio.writeDirectByteBuffer(directByteBufferAddress(byteBuffer, i), i2);
    }

    private int writeEncryptedDataHeap(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        ByteBuffer byteBuffer2;
        AllocatedBuffer allocatedBuffer = null;
        try {
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(i2);
                byteBuffer2 = allocatedBuffer.nioBuffer();
            } else {
                byteBuffer2 = getOrCreateLazyDirectBuffer();
            }
            int limit = byteBuffer.limit();
            int min = Math.min(Math.min(limit - i, i2), byteBuffer2.remaining());
            byteBuffer.limit(i + min);
            byteBuffer2.put(byteBuffer);
            byteBuffer.limit(limit);
            byteBuffer.position(i);
            int writeEncryptedDataDirect = writeEncryptedDataDirect(byteBuffer2, 0, min);
            byteBuffer.position(i);
            return writeEncryptedDataDirect;
        } finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private ByteBuffer getOrCreateLazyDirectBuffer() {
        if (this.lazyDirectBuffer == null) {
            this.lazyDirectBuffer = ByteBuffer.allocateDirect(Math.max(16384, 16709));
        }
        this.lazyDirectBuffer.clear();
        return this.lazyDirectBuffer;
    }

    private long directByteBufferAddress(ByteBuffer byteBuffer, int i) {
        return NativeCrypto.getDirectBufferAddress(byteBuffer) + i;
    }

    private SSLEngineResult readPendingBytesFromBIO(ByteBuffer byteBuffer, int i, int i2, SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        try {
            int pendingOutboundEncryptedBytes = pendingOutboundEncryptedBytes();
            if (pendingOutboundEncryptedBytes <= 0) {
                return null;
            }
            if (byteBuffer.remaining() < pendingOutboundEncryptedBytes) {
                SSLEngineResult.Status status = SSLEngineResult.Status.BUFFER_OVERFLOW;
                if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                    handshakeStatus = getHandshakeStatus(pendingOutboundEncryptedBytes);
                }
                return new SSLEngineResult(status, mayFinishHandshake(handshakeStatus), i, i2);
            }
            int readEncryptedData = readEncryptedData(byteBuffer, pendingOutboundEncryptedBytes);
            if (readEncryptedData <= 0) {
                NativeCrypto.SSL_clear_error();
            } else {
                i2 += readEncryptedData;
                pendingOutboundEncryptedBytes -= readEncryptedData;
            }
            SSLEngineResult.Status engineStatus = getEngineStatus();
            if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                handshakeStatus = getHandshakeStatus(pendingOutboundEncryptedBytes);
            }
            return new SSLEngineResult(engineStatus, mayFinishHandshake(handshakeStatus), i, i2);
        } catch (Exception e) {
            throw convertException(e);
        }
    }

    private int readEncryptedData(ByteBuffer byteBuffer, int i) throws SSLException {
        try {
            int position = byteBuffer.position();
            if (byteBuffer.remaining() < i) {
                return 0;
            }
            int min = Math.min(i, byteBuffer.limit() - position);
            if (!byteBuffer.isDirect()) {
                return readEncryptedDataHeap(byteBuffer, min);
            }
            int readEncryptedDataDirect = readEncryptedDataDirect(byteBuffer, position, min);
            if (readEncryptedDataDirect <= 0) {
                return readEncryptedDataDirect;
            }
            byteBuffer.position(position + readEncryptedDataDirect);
            return readEncryptedDataDirect;
        } catch (Exception e) {
            throw convertException(e);
        }
    }

    private int readEncryptedDataDirect(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        return this.networkBio.readDirectByteBuffer(directByteBufferAddress(byteBuffer, i), i2);
    }

    private int readEncryptedDataHeap(ByteBuffer byteBuffer, int i) throws IOException {
        ByteBuffer byteBuffer2;
        AllocatedBuffer allocatedBuffer = null;
        try {
            if (this.bufferAllocator != null) {
                allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(i);
                byteBuffer2 = allocatedBuffer.nioBuffer();
            } else {
                byteBuffer2 = getOrCreateLazyDirectBuffer();
            }
            int readEncryptedDataDirect = readEncryptedDataDirect(byteBuffer2, 0, Math.min(i, byteBuffer2.remaining()));
            if (readEncryptedDataDirect > 0) {
                byteBuffer2.position(readEncryptedDataDirect);
                byteBuffer2.flip();
                byteBuffer.put(byteBuffer2);
            }
            return readEncryptedDataDirect;
        } finally {
            if (allocatedBuffer != null) {
                allocatedBuffer.release();
            }
        }
    }

    private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        return (this.handshakeFinished || handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) ? handshakeStatus : handshake();
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatus(int i) {
        return !this.handshakeFinished ? pendingStatus(i) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private SSLEngineResult.Status getEngineStatus() {
        switch (this.state) {
            case 6:
            case 7:
            case 8:
                return SSLEngineResult.Status.CLOSED;
            default:
                return SSLEngineResult.Status.OK;
        }
    }

    private void closeAll() {
        closeOutbound();
        closeInbound();
    }

    private void freeIfDone() {
        if (isInboundDone() && isOutboundDone()) {
            closeAndFreeResources();
        }
    }

    private SSLException newSslExceptionWithMessage(String str) {
        if (!this.handshakeFinished) {
            return new SSLException(str);
        }
        return new SSLHandshakeException(str);
    }

    private SSLEngineResult newResult(int i, int i2, SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        SSLEngineResult.Status engineStatus = getEngineStatus();
        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
            handshakeStatus = getHandshakeStatusInternal();
        }
        return new SSLEngineResult(engineStatus, mayFinishHandshake(handshakeStatus), i, i2);
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult wrap;
        synchronized (this.ssl) {
            wrap = wrap(singleSrcBuffer(byteBuffer), byteBuffer2);
            resetSingleSrcBuffer();
        }
        return wrap;
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer byteBuffer) throws SSLException {
        SSLEngineResult readPendingBytesFromBIO;
        Preconditions.checkArgument(byteBufferArr != null, "srcs is null");
        Preconditions.checkArgument(byteBuffer != null, "dst is null");
        int i3 = i2 + i;
        Preconditions.checkPositionIndexes(i, i3, byteBufferArr.length);
        if (!byteBuffer.isReadOnly()) {
            synchronized (this.ssl) {
                switch (this.state) {
                    case 0:
                        throw new IllegalStateException("Client/server mode must be set before calling wrap");
                    case 1:
                        beginHandshakeInternal();
                        break;
                    case 7:
                    case 8:
                        SSLEngineResult readPendingBytesFromBIO2 = readPendingBytesFromBIO(byteBuffer, 0, 0, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING);
                        if (readPendingBytesFromBIO2 == null) {
                            return new SSLEngineResult(SSLEngineResult.Status.CLOSED, getHandshakeStatusInternal(), 0, 0);
                        }
                        freeIfDone();
                        return readPendingBytesFromBIO2;
                }
                SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                if (!this.handshakeFinished) {
                    handshakeStatus = handshake();
                    if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                        return NEED_UNWRAP_OK;
                    }
                    if (this.state == 8) {
                        return NEED_UNWRAP_CLOSED;
                    }
                }
                int i4 = 0;
                for (int i5 = i; i5 < i3; i5++) {
                    ByteBuffer byteBuffer2 = byteBufferArr[i5];
                    if (byteBuffer2 != null) {
                        if (i4 != 16384 && ((i4 = i4 + byteBuffer2.remaining()) > 16384 || i4 < 0)) {
                            i4 = 16384;
                        }
                    } else {
                        throw new IllegalArgumentException("srcs[" + i5 + "] is null");
                    }
                }
                if (byteBuffer.remaining() < SSLUtils.calculateOutNetBufSize(i4)) {
                    return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatusInternal(), 0, 0);
                }
                int i6 = 0;
                int i7 = 0;
                while (i < i3) {
                    ByteBuffer byteBuffer3 = byteBufferArr[i];
                    Preconditions.checkArgument(byteBuffer3 != null, "srcs[%d] is null", Integer.valueOf(i));
                    while (byteBuffer3.hasRemaining()) {
                        int writePlaintextData = writePlaintextData(byteBuffer3, Math.min(byteBuffer3.remaining(), 16384 - i7));
                        if (writePlaintextData > 0) {
                            i7 += writePlaintextData;
                            SSLEngineResult readPendingBytesFromBIO3 = readPendingBytesFromBIO(byteBuffer, i7, i6, handshakeStatus);
                            if (readPendingBytesFromBIO3 != null) {
                                if (readPendingBytesFromBIO3.getStatus() != SSLEngineResult.Status.OK) {
                                    return readPendingBytesFromBIO3;
                                }
                                i6 = readPendingBytesFromBIO3.bytesProduced();
                                continue;
                            }
                            if (i7 == 16384) {
                                return (i7 == 0 || (readPendingBytesFromBIO = readPendingBytesFromBIO(byteBuffer, 0, i6, handshakeStatus)) == null) ? newResult(i7, i6, handshakeStatus) : readPendingBytesFromBIO;
                            }
                        } else {
                            int error = this.ssl.getError(writePlaintextData);
                            if (error != 6) {
                                switch (error) {
                                    case 2:
                                        SSLEngineResult readPendingBytesFromBIO4 = readPendingBytesFromBIO(byteBuffer, i7, i6, handshakeStatus);
                                        if (readPendingBytesFromBIO4 == null) {
                                            readPendingBytesFromBIO4 = new SSLEngineResult(getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_UNWRAP, i7, i6);
                                        }
                                        return readPendingBytesFromBIO4;
                                    case 3:
                                        SSLEngineResult readPendingBytesFromBIO5 = readPendingBytesFromBIO(byteBuffer, i7, i6, handshakeStatus);
                                        if (readPendingBytesFromBIO5 == null) {
                                            readPendingBytesFromBIO5 = NEED_WRAP_CLOSED;
                                        }
                                        return readPendingBytesFromBIO5;
                                    default:
                                        sendSSLShutdown();
                                        throw newSslExceptionWithMessage("SSL_write");
                                }
                            } else {
                                closeAll();
                                SSLEngineResult readPendingBytesFromBIO6 = readPendingBytesFromBIO(byteBuffer, i7, i6, handshakeStatus);
                                if (readPendingBytesFromBIO6 == null) {
                                    readPendingBytesFromBIO6 = CLOSED_NOT_HANDSHAKING;
                                }
                                return readPendingBytesFromBIO6;
                            }
                        }
                    }
                    i++;
                }
                if (i7 == 0) {
                }
            }
        } else {
            throw new ReadOnlyBufferException();
        }
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public int clientPSKKeyRequested(String str, byte[] bArr, byte[] bArr2) {
        return this.ssl.clientPSKKeyRequested(str, bArr, bArr2);
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public int serverPSKKeyRequested(String str, String str2, byte[] bArr) {
        return this.ssl.serverPSKKeyRequested(str, str2, bArr);
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public void onSSLStateChange(int i, int i2) {
        synchronized (this.ssl) {
            if (i == 16) {
                transitionTo(2);
            } else if (i == 32) {
                if (!(this.state == 2 || this.state == 4)) {
                    throw new IllegalStateException("Completed handshake while in mode " + this.state);
                }
                transitionTo(3);
            }
        }
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public void onNewSessionEstablished(long j) {
        try {
            NativeCrypto.SSL_SESSION_up_ref(j);
            sessionContext().cacheSession(NativeSslSession.newInstance(new NativeRef.SSL_SESSION(j), this.activeSession));
        } catch (Exception unused) {
        }
    }

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public void verifyCertificateChain(byte[][] bArr, String str) throws CertificateException {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    X509Certificate[] decodeX509CertificateChain = SSLUtils.decodeX509CertificateChain(bArr);
                    X509TrustManager x509TrustManager = this.sslParameters.getX509TrustManager();
                    if (x509TrustManager != null) {
                        this.activeSession.onPeerCertificatesReceived(getPeerHost(), getPeerPort(), decodeX509CertificateChain);
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

    @Override // org.conscrypt.NativeCrypto.SSLHandshakeCallbacks
    public void clientCertificateRequested(byte[] bArr, int[] iArr, byte[][] bArr2) throws CertificateEncodingException, SSLException {
        this.ssl.chooseClientCertificate(bArr, iArr, bArr2);
    }

    private void sendSSLShutdown() {
        try {
            this.ssl.shutdown();
        } catch (IOException unused) {
        }
    }

    private void closeAndFreeResources() {
        transitionTo(8);
        if (!this.ssl.isClosed()) {
            this.ssl.close();
            this.networkBio.close();
        }
    }

    protected void finalize() throws Throwable {
        try {
            transitionTo(8);
        } finally {
            super.finalize();
        }
    }

    @Override // org.conscrypt.SSLParametersImpl.AliasChooser
    public String chooseServerAlias(X509KeyManager x509KeyManager, String str) {
        if (x509KeyManager instanceof X509ExtendedKeyManager) {
            return ((X509ExtendedKeyManager) x509KeyManager).chooseEngineServerAlias(str, null, this);
        }
        return x509KeyManager.chooseServerAlias(str, null, null);
    }

    @Override // org.conscrypt.SSLParametersImpl.AliasChooser
    public String chooseClientAlias(X509KeyManager x509KeyManager, X500Principal[] x500PrincipalArr, String[] strArr) {
        if (x509KeyManager instanceof X509ExtendedKeyManager) {
            return ((X509ExtendedKeyManager) x509KeyManager).chooseEngineClientAlias(strArr, x500PrincipalArr, this);
        }
        return x509KeyManager.chooseClientAlias(strArr, x500PrincipalArr, null);
    }

    @Override // org.conscrypt.SSLParametersImpl.PSKCallbacks
    public String chooseServerPSKIdentityHint(PSKKeyManager pSKKeyManager) {
        return pSKKeyManager.chooseServerKeyIdentityHint(this);
    }

    @Override // org.conscrypt.SSLParametersImpl.PSKCallbacks
    public String chooseClientPSKIdentity(PSKKeyManager pSKKeyManager, String str) {
        return pSKKeyManager.chooseClientKeyIdentity(str, this);
    }

    @Override // org.conscrypt.SSLParametersImpl.PSKCallbacks
    public SecretKey getPSKKey(PSKKeyManager pSKKeyManager, String str, String str2) {
        return pSKKeyManager.getKey(str, str2, this);
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public void setUseSessionTickets(boolean z) {
        this.sslParameters.setUseSessionTickets(z);
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public String[] getApplicationProtocols() {
        return this.sslParameters.getApplicationProtocols();
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public void setApplicationProtocols(String[] strArr) {
        this.sslParameters.setApplicationProtocols(strArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.conscrypt.AbstractConscryptEngine
    public void setApplicationProtocolSelector(ApplicationProtocolSelector applicationProtocolSelector) {
        setApplicationProtocolSelector(applicationProtocolSelector == null ? null : new ApplicationProtocolSelectorAdapter(this, applicationProtocolSelector));
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public byte[] getTlsUnique() {
        return this.ssl.getTlsUnique();
    }

    @Override // org.conscrypt.AbstractConscryptEngine
    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i) throws SSLException {
        synchronized (this.ssl) {
            if (this.state >= 3 && this.state != 8) {
                return this.ssl.exportKeyingMaterial(str, bArr, i);
            }
            return null;
        }
    }

    public void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.sslParameters.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public String getApplicationProtocol() {
        return SSLUtils.toProtocolString(this.ssl.getApplicationProtocol());
    }

    @Override // org.conscrypt.AbstractConscryptEngine, javax.net.ssl.SSLEngine
    public String getHandshakeApplicationProtocol() {
        String applicationProtocol;
        synchronized (this.ssl) {
            applicationProtocol = this.state == 2 ? getApplicationProtocol() : null;
        }
        return applicationProtocol;
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.singleSrcBuffer;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.singleDstBuffer;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    private ClientSessionContext clientSessionContext() {
        return this.sslParameters.getClientSessionContext();
    }

    private AbstractSessionContext sessionContext() {
        return this.sslParameters.getSessionContext();
    }

    private void transitionTo(int i) {
        int i2;
        if (i == 2) {
            this.handshakeFinished = false;
            this.activeSession = new ActiveSession(this.ssl, this.sslParameters.getSessionContext());
        } else if (i == 8 && !this.ssl.isClosed() && (i2 = this.state) >= 2 && i2 < 8) {
            this.closedSession = new SessionSnapshot(this.activeSession);
        }
        this.state = i;
    }
}
