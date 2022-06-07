package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import org.conscrypt.OpenSSLCipher;

/* loaded from: classes5.dex */
public class OpenSSLEvpCipherARC4 extends OpenSSLEvpCipher {
    @Override // org.conscrypt.OpenSSLCipher
    void checkSupportedKeySize(int i) throws InvalidKeyException {
    }

    @Override // org.conscrypt.OpenSSLCipher
    String getBaseCipherName() {
        return "ARCFOUR";
    }

    @Override // org.conscrypt.OpenSSLCipher
    int getCipherBlockSize() {
        return 0;
    }

    @Override // org.conscrypt.OpenSSLEvpCipher
    String getCipherName(int i, OpenSSLCipher.Mode mode) {
        return "rc4";
    }

    @Override // org.conscrypt.OpenSSLCipher
    boolean supportsVariableSizeKey() {
        return true;
    }

    public OpenSSLEvpCipherARC4() {
        super(OpenSSLCipher.Mode.ECB, OpenSSLCipher.Padding.NOPADDING);
    }

    @Override // org.conscrypt.OpenSSLCipher
    void checkSupportedMode(OpenSSLCipher.Mode mode) throws NoSuchAlgorithmException {
        if (mode != OpenSSLCipher.Mode.NONE && mode != OpenSSLCipher.Mode.ECB) {
            throw new NoSuchAlgorithmException("Unsupported mode " + mode.toString());
        }
    }

    @Override // org.conscrypt.OpenSSLCipher
    void checkSupportedPadding(OpenSSLCipher.Padding padding) throws NoSuchPaddingException {
        if (padding != OpenSSLCipher.Padding.NOPADDING) {
            throw new NoSuchPaddingException("Unsupported padding " + padding.toString());
        }
    }
}
