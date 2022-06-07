package org.conscrypt;

import java.security.cert.X509Certificate;
import java.util.Set;

/* loaded from: classes5.dex */
public interface ConscryptCertStore {
    Set<X509Certificate> findAllIssuers(X509Certificate x509Certificate);

    X509Certificate getTrustAnchor(X509Certificate x509Certificate);
}
