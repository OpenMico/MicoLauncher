package org.conscrypt;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/* loaded from: classes5.dex */
public abstract class OpenSSLContextImpl extends SSLContextSpi {
    private static DefaultSSLContextImpl defaultSslContextImpl;
    private final String[] algorithms;
    private final ClientSessionContext clientSessionContext;
    private final ServerSessionContext serverSessionContext;
    SSLParametersImpl sslParameters;

    public static OpenSSLContextImpl getPreferred() {
        return new TLSv13();
    }

    OpenSSLContextImpl(String[] strArr) {
        this.algorithms = strArr;
        this.clientSessionContext = new ClientSessionContext();
        this.serverSessionContext = new ServerSessionContext();
    }

    public OpenSSLContextImpl() throws GeneralSecurityException, IOException {
        synchronized (DefaultSSLContextImpl.class) {
            this.algorithms = null;
            if (defaultSslContextImpl == null) {
                this.clientSessionContext = new ClientSessionContext();
                this.serverSessionContext = new ServerSessionContext();
                defaultSslContextImpl = (DefaultSSLContextImpl) this;
            } else {
                this.clientSessionContext = (ClientSessionContext) defaultSslContextImpl.engineGetClientSessionContext();
                this.serverSessionContext = (ServerSessionContext) defaultSslContextImpl.engineGetServerSessionContext();
            }
            this.sslParameters = new SSLParametersImpl(defaultSslContextImpl.getKeyManagers(), defaultSslContextImpl.getTrustManagers(), (SecureRandom) null, this.clientSessionContext, this.serverSessionContext, this.algorithms);
        }
    }

    @Override // javax.net.ssl.SSLContextSpi
    public void engineInit(KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, SecureRandom secureRandom) throws KeyManagementException {
        this.sslParameters = new SSLParametersImpl(keyManagerArr, trustManagerArr, secureRandom, this.clientSessionContext, this.serverSessionContext, this.algorithms);
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLSocketFactory engineGetSocketFactory() {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            return Platform.wrapSocketFactoryIfNeeded(new OpenSSLSocketFactoryImpl(sSLParametersImpl));
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLServerSocketFactory engineGetServerSocketFactory() {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            return new OpenSSLServerSocketFactoryImpl(sSLParametersImpl);
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLEngine engineCreateSSLEngine(String str, int i) {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            SSLParametersImpl sSLParametersImpl2 = (SSLParametersImpl) sSLParametersImpl.clone();
            sSLParametersImpl2.setUseClientMode(false);
            return Platform.wrapEngine(new ConscryptEngine(str, i, sSLParametersImpl2));
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLEngine engineCreateSSLEngine() {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            SSLParametersImpl sSLParametersImpl2 = (SSLParametersImpl) sSLParametersImpl.clone();
            sSLParametersImpl2.setUseClientMode(false);
            return Platform.wrapEngine(new ConscryptEngine(sSLParametersImpl2));
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLSessionContext engineGetServerSessionContext() {
        return this.serverSessionContext;
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLSessionContext engineGetClientSessionContext() {
        return this.clientSessionContext;
    }

    /* loaded from: classes5.dex */
    public static final class TLSv13 extends OpenSSLContextImpl {
        public TLSv13() {
            super(NativeCrypto.TLSV13_PROTOCOLS);
        }
    }

    /* loaded from: classes5.dex */
    public static final class TLSv12 extends OpenSSLContextImpl {
        public TLSv12() {
            super(NativeCrypto.TLSV12_PROTOCOLS);
        }
    }

    /* loaded from: classes5.dex */
    public static final class TLSv11 extends OpenSSLContextImpl {
        public TLSv11() {
            super(NativeCrypto.TLSV11_PROTOCOLS);
        }
    }

    /* loaded from: classes5.dex */
    public static final class TLSv1 extends OpenSSLContextImpl {
        public TLSv1() {
            super(NativeCrypto.TLSV1_PROTOCOLS);
        }
    }
}
