package org.conscrypt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;

/* loaded from: classes5.dex */
final class ConscryptServerSocket extends SSLServerSocket {
    private boolean channelIdEnabled;
    private final SSLParametersImpl sslParameters;
    private boolean useEngineSocket;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConscryptServerSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        this.sslParameters = sSLParametersImpl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConscryptServerSocket(int i, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(i);
        this.sslParameters = sSLParametersImpl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConscryptServerSocket(int i, int i2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(i, i2);
        this.sslParameters = sSLParametersImpl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConscryptServerSocket(int i, int i2, InetAddress inetAddress, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(i, i2, inetAddress);
        this.sslParameters = sSLParametersImpl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConscryptServerSocket setUseEngineSocket(boolean z) {
        this.useEngineSocket = z;
        return this;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setEnableSessionCreation(boolean z) {
        this.sslParameters.setEnableSessionCreation(z);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setEnabledProtocols(strArr);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    void setChannelIdEnabled(boolean z) {
        this.channelIdEnabled = z;
    }

    boolean isChannelIdEnabled() {
        return this.channelIdEnabled;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setEnabledCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setWantClientAuth(boolean z) {
        this.sslParameters.setWantClientAuth(z);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setNeedClientAuth(boolean z) {
        this.sslParameters.setNeedClientAuth(z);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setUseClientMode(boolean z) {
        this.sslParameters.setUseClientMode(z);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override // java.net.ServerSocket
    public Socket accept() throws IOException {
        AbstractConscryptSocket abstractConscryptSocket;
        if (this.useEngineSocket) {
            abstractConscryptSocket = Platform.createEngineSocket(this.sslParameters);
        } else {
            abstractConscryptSocket = Platform.createFileDescriptorSocket(this.sslParameters);
        }
        abstractConscryptSocket.setChannelIdEnabled(this.channelIdEnabled);
        implAccept(abstractConscryptSocket);
        return abstractConscryptSocket;
    }
}
