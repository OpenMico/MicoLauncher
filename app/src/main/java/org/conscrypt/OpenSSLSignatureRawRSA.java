package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/* loaded from: classes5.dex */
public final class OpenSSLSignatureRawRSA extends SignatureSpi {
    private byte[] inputBuffer;
    private boolean inputIsTooLong;
    private int inputOffset;
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
        int i = this.inputOffset;
        this.inputOffset = i + 1;
        int i2 = this.inputOffset;
        byte[] bArr = this.inputBuffer;
        if (i2 > bArr.length) {
            this.inputIsTooLong = true;
        } else {
            bArr[i] = b;
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        int i3 = this.inputOffset;
        this.inputOffset = i3 + i2;
        int i4 = this.inputOffset;
        byte[] bArr2 = this.inputBuffer;
        if (i4 > bArr2.length) {
            this.inputIsTooLong = true;
        } else {
            System.arraycopy(bArr, i, bArr2, i3, i2);
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof OpenSSLRSAPrivateKey) {
            this.key = ((OpenSSLRSAPrivateKey) privateKey).getOpenSSLKey();
        } else if (privateKey instanceof RSAPrivateCrtKey) {
            this.key = OpenSSLRSAPrivateCrtKey.getInstance((RSAPrivateCrtKey) privateKey);
        } else if (privateKey instanceof RSAPrivateKey) {
            this.key = OpenSSLRSAPrivateKey.getInstance((RSAPrivateKey) privateKey);
        } else {
            throw new InvalidKeyException("Need RSA private key");
        }
        this.inputBuffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
        this.inputOffset = 0;
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        if (publicKey instanceof OpenSSLRSAPublicKey) {
            this.key = ((OpenSSLRSAPublicKey) publicKey).getOpenSSLKey();
        } else if (publicKey instanceof RSAPublicKey) {
            this.key = OpenSSLRSAPublicKey.getInstance((RSAPublicKey) publicKey);
        } else {
            throw new InvalidKeyException("Need RSA public key");
        }
        this.inputBuffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
        this.inputOffset = 0;
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        OpenSSLKey openSSLKey = this.key;
        if (openSSLKey == null) {
            throw new SignatureException("Need RSA private key");
        } else if (!this.inputIsTooLong) {
            byte[] bArr = this.inputBuffer;
            byte[] bArr2 = new byte[bArr.length];
            try {
                try {
                    NativeCrypto.RSA_private_encrypt(this.inputOffset, bArr, bArr2, openSSLKey.getNativeRef(), 1);
                    return bArr2;
                } catch (Exception e) {
                    throw new SignatureException(e);
                }
            } finally {
                this.inputOffset = 0;
            }
        } else {
            throw new SignatureException("input length " + this.inputOffset + " != " + this.inputBuffer.length + " (modulus size)");
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        OpenSSLKey openSSLKey = this.key;
        if (openSSLKey != null) {
            if (this.inputIsTooLong) {
                return false;
            }
            int length = bArr.length;
            byte[] bArr2 = this.inputBuffer;
            if (length <= bArr2.length) {
                try {
                    try {
                        byte[] bArr3 = new byte[bArr2.length];
                        try {
                            boolean z = true;
                            int RSA_public_decrypt = NativeCrypto.RSA_public_decrypt(bArr.length, bArr, bArr3, openSSLKey.getNativeRef(), 1);
                            if (RSA_public_decrypt != this.inputOffset) {
                                z = false;
                            }
                            for (int i = 0; i < RSA_public_decrypt; i++) {
                                if (this.inputBuffer[i] != bArr3[i]) {
                                    z = false;
                                }
                            }
                            return z;
                        } catch (SignatureException e) {
                            throw e;
                        } catch (Exception unused) {
                            return false;
                        }
                    } catch (Exception e2) {
                        throw new SignatureException(e2);
                    }
                } finally {
                    this.inputOffset = 0;
                }
            } else {
                throw new SignatureException("Input signature length is too large: " + bArr.length + " > " + this.inputBuffer.length);
            }
        } else {
            throw new SignatureException("Need RSA public key");
        }
    }
}
