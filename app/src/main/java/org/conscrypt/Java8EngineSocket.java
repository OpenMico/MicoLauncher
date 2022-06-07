package org.conscrypt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class Java8EngineSocket extends ConscryptEngineSocket {
    private BiFunction<SSLSocket, List<String>, String> selector;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Java8EngineSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        super(sSLParametersImpl);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Java8EngineSocket(String str, int i, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(str, i, sSLParametersImpl);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Java8EngineSocket(InetAddress inetAddress, int i, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, i, sSLParametersImpl);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Java8EngineSocket(String str, int i, InetAddress inetAddress, int i2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(str, i, inetAddress, i2, sSLParametersImpl);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Java8EngineSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, i, inetAddress2, i2, sSLParametersImpl);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Java8EngineSocket(Socket socket, String str, int i, boolean z, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(socket, str, i, z, sSLParametersImpl);
    }

    @Override // javax.net.ssl.SSLSocket
    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> biFunction) {
        this.selector = biFunction;
        setApplicationProtocolSelector(toApplicationProtocolSelector(biFunction));
    }

    @Override // javax.net.ssl.SSLSocket
    public BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return this.selector;
    }

    private static ApplicationProtocolSelector toApplicationProtocolSelector(final BiFunction<SSLSocket, List<String>, String> biFunction) {
        if (biFunction == null) {
            return null;
        }
        return new ApplicationProtocolSelector() { // from class: org.conscrypt.Java8EngineSocket.1
            @Override // org.conscrypt.ApplicationProtocolSelector
            public String selectApplicationProtocol(SSLEngine sSLEngine, List<String> list) {
                throw new UnsupportedOperationException();
            }

            @Override // org.conscrypt.ApplicationProtocolSelector
            public String selectApplicationProtocol(SSLSocket sSLSocket, List<String> list) {
                return (String) biFunction.apply(sSLSocket, list);
            }
        };
    }
}
