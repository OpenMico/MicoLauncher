package org.conscrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactorySpi;
import javax.net.ssl.ManagerFactoryParameters;

/* loaded from: classes5.dex */
public class KeyManagerFactoryImpl extends KeyManagerFactorySpi {
    private KeyStore keyStore;
    private char[] pwd;

    @Override // javax.net.ssl.KeyManagerFactorySpi
    protected void engineInit(KeyStore keyStore, char[] cArr) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (keyStore != null) {
            this.keyStore = keyStore;
            if (cArr != null) {
                this.pwd = (char[]) cArr.clone();
            } else {
                this.pwd = EmptyArray.CHAR;
            }
        } else {
            this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            String property = System.getProperty("javax.net.ssl.keyStore");
            if (property == null || property.equalsIgnoreCase("NONE") || property.isEmpty()) {
                try {
                    this.keyStore.load(null, null);
                } catch (IOException e) {
                    throw new KeyStoreException(e);
                } catch (CertificateException e2) {
                    throw new KeyStoreException(e2);
                }
            } else {
                String property2 = System.getProperty("javax.net.ssl.keyStorePassword");
                if (property2 == null) {
                    this.pwd = EmptyArray.CHAR;
                } else {
                    this.pwd = property2.toCharArray();
                }
                try {
                    this.keyStore.load(new FileInputStream(new File(property)), this.pwd);
                } catch (FileNotFoundException e3) {
                    throw new KeyStoreException(e3);
                } catch (IOException e4) {
                    throw new KeyStoreException(e4);
                } catch (CertificateException e5) {
                    throw new KeyStoreException(e5);
                }
            }
        }
    }

    @Override // javax.net.ssl.KeyManagerFactorySpi
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("ManagerFactoryParameters not supported");
    }

    @Override // javax.net.ssl.KeyManagerFactorySpi
    protected KeyManager[] engineGetKeyManagers() {
        KeyStore keyStore = this.keyStore;
        if (keyStore != null) {
            return new KeyManager[]{new KeyManagerImpl(keyStore, this.pwd)};
        }
        throw new IllegalStateException("KeyManagerFactory is not initialized");
    }
}
