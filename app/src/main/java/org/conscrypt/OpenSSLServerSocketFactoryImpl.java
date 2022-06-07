package org.conscrypt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.KeyManagementException;
import javax.net.ssl.SSLServerSocketFactory;

/* loaded from: classes5.dex */
final class OpenSSLServerSocketFactoryImpl extends SSLServerSocketFactory {
    private static boolean useEngineSocketByDefault = SSLUtils.USE_ENGINE_SOCKET_BY_DEFAULT;
    private IOException instantiationException;
    private SSLParametersImpl sslParameters;
    private boolean useEngineSocket = useEngineSocketByDefault;

    OpenSSLServerSocketFactoryImpl() {
        try {
            this.sslParameters = SSLParametersImpl.getDefault();
            this.sslParameters.setUseClientMode(false);
        } catch (KeyManagementException e) {
            this.instantiationException = new IOException("Delayed instantiation exception:");
            this.instantiationException.initCause(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLServerSocketFactoryImpl(SSLParametersImpl sSLParametersImpl) {
        this.sslParameters = (SSLParametersImpl) sSLParametersImpl.clone();
        this.sslParameters.setUseClientMode(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setUseEngineSocketByDefault(boolean z) {
        useEngineSocketByDefault = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setUseEngineSocket(boolean z) {
        this.useEngineSocket = z;
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket() throws IOException {
        return new ConscryptServerSocket((SSLParametersImpl) this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i) throws IOException {
        return new ConscryptServerSocket(i, (SSLParametersImpl) this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i, int i2) throws IOException {
        return new ConscryptServerSocket(i, i2, (SSLParametersImpl) this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i, int i2, InetAddress inetAddress) throws IOException {
        return new ConscryptServerSocket(i, i2, inetAddress, (SSLParametersImpl) this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }
}
