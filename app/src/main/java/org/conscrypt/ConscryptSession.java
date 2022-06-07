package org.conscrypt;

import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public interface ConscryptSession extends SSLSession {
    @Override // javax.net.ssl.SSLSession
    X509Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException;

    byte[] getPeerSignedCertificateTimestamp();

    String getRequestedServerName();

    List<byte[]> getStatusResponses();
}
