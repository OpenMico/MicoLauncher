package org.eclipse.jetty.util.security;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CRL;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes5.dex */
public class CertificateUtils {
    public static KeyStore getKeyStore(InputStream inputStream, String str, String str2, String str3, String str4) throws Exception {
        KeyStore keyStore;
        char[] cArr = null;
        if (inputStream == null && str == null) {
            return null;
        }
        if (inputStream == null) {
            try {
                inputStream = Resource.newResource(str).getInputStream();
            } catch (Throwable th) {
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        }
        if (str3 != null) {
            keyStore = KeyStore.getInstance(str2, str3);
        } else {
            keyStore = KeyStore.getInstance(str2);
        }
        if (str4 != null) {
            cArr = str4.toCharArray();
        }
        keyStore.load(inputStream, cArr);
        if (inputStream == null) {
            return keyStore;
        }
        inputStream.close();
        return keyStore;
    }

    public static Collection<? extends CRL> loadCRL(String str) throws Exception {
        InputStream inputStream = null;
        if (str == null) {
            return null;
        }
        try {
            inputStream = Resource.newResource(str).getInputStream();
            Collection<? extends CRL> generateCRLs = CertificateFactory.getInstance("X.509").generateCRLs(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
