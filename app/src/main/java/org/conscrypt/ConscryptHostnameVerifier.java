package org.conscrypt;

import javax.net.ssl.SSLSession;

/* loaded from: classes5.dex */
public interface ConscryptHostnameVerifier {
    boolean verify(String str, SSLSession sSLSession);
}
