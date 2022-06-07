package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes5.dex */
final class CryptoUpcalls {
    private static final Logger logger = Logger.getLogger(CryptoUpcalls.class.getName());

    private CryptoUpcalls() {
    }

    private static ArrayList<Provider> getExternalProviders(String str) {
        ArrayList<Provider> arrayList = new ArrayList<>(1);
        Provider[] providers = Security.getProviders(str);
        for (Provider provider : providers) {
            if (!Conscrypt.isConscrypt(provider)) {
                arrayList.add(provider);
            }
        }
        if (arrayList.isEmpty()) {
            logger.warning("Could not find external provider for algorithm: " + str);
        }
        return arrayList;
    }

    static byte[] ecSignDigestWithPrivateKey(PrivateKey privateKey, byte[] bArr) {
        if ("EC".equals(privateKey.getAlgorithm())) {
            return signDigestWithPrivateKey(privateKey, bArr, "NONEwithECDSA");
        }
        throw new RuntimeException("Unexpected key type: " + privateKey.toString());
    }

    private static byte[] signDigestWithPrivateKey(PrivateKey privateKey, byte[] bArr, String str) {
        Signature signature;
        try {
            signature = Signature.getInstance(str);
            signature.initSign(privateKey);
            if (Conscrypt.isConscrypt(signature.getProvider())) {
                signature = null;
            }
        } catch (InvalidKeyException e) {
            logger.warning("Preferred provider doesn't support key:");
            e.printStackTrace();
            signature = null;
        } catch (NoSuchAlgorithmException unused) {
            logger.warning("Unsupported signature algorithm: " + str);
            return null;
        }
        if (signature == null) {
            Iterator<Provider> it = getExternalProviders("Signature." + str).iterator();
            Signature signature2 = signature;
            RuntimeException e2 = null;
            while (it.hasNext()) {
                try {
                    signature2 = Signature.getInstance(str, it.next());
                    signature2.initSign(privateKey);
                    break;
                } catch (RuntimeException e3) {
                    e2 = e3;
                    if (e2 == null) {
                    }
                    signature2 = null;
                } catch (InvalidKeyException | NoSuchAlgorithmException unused2) {
                    signature2 = null;
                }
            }
            if (signature2 != null) {
                signature = signature2;
            } else if (e2 == null) {
                logger.warning("Could not find provider for algorithm: " + str);
                return null;
            } else {
                throw e2;
            }
        }
        try {
            signature.update(bArr);
            return signature.sign();
        } catch (Exception e4) {
            logger.log(Level.WARNING, "Exception while signing message with " + privateKey.getAlgorithm() + " private key:", (Throwable) e4);
            return null;
        }
    }

    static byte[] rsaSignDigestWithPrivateKey(PrivateKey privateKey, int i, byte[] bArr) {
        return rsaOpWithPrivateKey(privateKey, i, 1, bArr);
    }

    static byte[] rsaDecryptWithPrivateKey(PrivateKey privateKey, int i, byte[] bArr) {
        return rsaOpWithPrivateKey(privateKey, i, 2, bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static byte[] rsaOpWithPrivateKey(java.security.PrivateKey r5, int r6, int r7, byte[] r8) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.conscrypt.CryptoUpcalls.rsaOpWithPrivateKey(java.security.PrivateKey, int, int, byte[]):byte[]");
    }
}
