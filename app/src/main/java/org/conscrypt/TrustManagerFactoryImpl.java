package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;

/* loaded from: classes5.dex */
public class TrustManagerFactoryImpl extends TrustManagerFactorySpi {
    private KeyStore keyStore;

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(KeyStore keyStore) throws KeyStoreException {
        if (keyStore != null) {
            this.keyStore = keyStore;
        } else {
            this.keyStore = Platform.getDefaultCertKeyStore();
        }
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("ManagerFactoryParameters not supported");
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public TrustManager[] engineGetTrustManagers() {
        KeyStore keyStore = this.keyStore;
        if (keyStore != null) {
            return new TrustManager[]{new TrustManagerImpl(keyStore)};
        }
        throw new IllegalStateException("TrustManagerFactory is not initialized");
    }
}
