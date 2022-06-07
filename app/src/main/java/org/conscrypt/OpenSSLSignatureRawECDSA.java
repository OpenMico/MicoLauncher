package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;

/* loaded from: classes5.dex */
public class OpenSSLSignatureRawECDSA extends SignatureSpi {
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private OpenSSLKey key;

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        return null;
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) throws InvalidParameterException {
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) {
        this.buffer.write(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.buffer.write(bArr, i, i2);
    }

    private static OpenSSLKey verifyKey(OpenSSLKey openSSLKey) throws InvalidKeyException {
        if (NativeCrypto.EVP_PKEY_type(openSSLKey.getNativeRef()) == 408) {
            return openSSLKey;
        }
        throw new InvalidKeyException("Non-EC key used to initialize EC signature.");
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        this.key = verifyKey(OpenSSLKey.fromPrivateKey(privateKey));
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        this.key = verifyKey(OpenSSLKey.fromPublicKey(publicKey));
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] bArr;
        OpenSSLKey openSSLKey = this.key;
        if (openSSLKey != null) {
            try {
                int ECDSA_size = NativeCrypto.ECDSA_size(openSSLKey.getNativeRef());
                byte[] bArr2 = new byte[ECDSA_size];
                try {
                    int ECDSA_sign = NativeCrypto.ECDSA_sign(this.buffer.toByteArray(), bArr2, this.key.getNativeRef());
                    if (ECDSA_sign >= 0) {
                        if (ECDSA_sign != ECDSA_size) {
                            bArr = new byte[ECDSA_sign];
                            System.arraycopy(bArr2, 0, bArr, 0, ECDSA_sign);
                        } else {
                            bArr = bArr2;
                        }
                        return bArr;
                    }
                    throw new SignatureException("Could not compute signature.");
                } catch (Exception e) {
                    throw new SignatureException(e);
                }
            } finally {
                this.buffer.reset();
            }
        } else {
            throw new SignatureException("No key provided");
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        if (this.key != null) {
            try {
                try {
                    int ECDSA_verify = NativeCrypto.ECDSA_verify(this.buffer.toByteArray(), bArr, this.key.getNativeRef());
                    if (ECDSA_verify != -1) {
                        boolean z = true;
                        if (ECDSA_verify != 1) {
                            z = false;
                        }
                        return z;
                    }
                    throw new SignatureException("Could not verify signature.");
                } catch (Exception e) {
                    throw new SignatureException(e);
                }
            } finally {
                this.buffer.reset();
            }
        } else {
            throw new SignatureException("No key provided");
        }
    }
}
