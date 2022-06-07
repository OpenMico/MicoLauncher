package org.conscrypt;

import javax.net.ssl.SSLSession;

/* loaded from: classes5.dex */
public interface SSLClientSessionCache {
    byte[] getSessionData(String str, int i);

    void putSessionData(SSLSession sSLSession, byte[] bArr);
}
