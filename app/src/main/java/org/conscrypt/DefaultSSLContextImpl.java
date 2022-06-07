package org.conscrypt;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/* loaded from: classes5.dex */
public final class DefaultSSLContextImpl extends OpenSSLContextImpl {
    private static KeyManager[] KEY_MANAGERS;
    private static TrustManager[] TRUST_MANAGERS;

    /* JADX INFO: Access modifiers changed from: package-private */
    public KeyManager[] getKeyManagers() throws GeneralSecurityException, IOException {
        Throwable th;
        KeyManager[] keyManagerArr = KEY_MANAGERS;
        if (keyManagerArr != null) {
            return keyManagerArr;
        }
        String property = System.getProperty("javax.net.ssl.keyStore");
        BufferedInputStream bufferedInputStream = null;
        if (property == null) {
            return null;
        }
        String property2 = System.getProperty("javax.net.ssl.keyStorePassword");
        char[] charArray = property2 == null ? null : property2.toCharArray();
        KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(property));
            try {
                instance.load(bufferedInputStream2, charArray);
                bufferedInputStream2.close();
                KeyManagerFactory instance2 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                instance2.init(instance, charArray);
                KEY_MANAGERS = instance2.getKeyManagers();
                return KEY_MANAGERS;
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TrustManager[] getTrustManagers() throws GeneralSecurityException, IOException {
        Throwable th;
        TrustManager[] trustManagerArr = TRUST_MANAGERS;
        if (trustManagerArr != null) {
            return trustManagerArr;
        }
        String property = System.getProperty("javax.net.ssl.trustStore");
        BufferedInputStream bufferedInputStream = null;
        if (property == null) {
            return null;
        }
        String property2 = System.getProperty("javax.net.ssl.trustStorePassword");
        char[] charArray = property2 == null ? null : property2.toCharArray();
        KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(property));
            try {
                instance.load(bufferedInputStream2, charArray);
                bufferedInputStream2.close();
                TrustManagerFactory instance2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                instance2.init(instance);
                TRUST_MANAGERS = instance2.getTrustManagers();
                return TRUST_MANAGERS;
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    @Override // org.conscrypt.OpenSSLContextImpl, javax.net.ssl.SSLContextSpi
    public void engineInit(KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, SecureRandom secureRandom) throws KeyManagementException {
        throw new KeyManagementException("Do not init() the default SSLContext ");
    }
}
