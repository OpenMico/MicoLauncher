package org.conscrypt;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

/* loaded from: classes5.dex */
public class OpenSSLRSAPublicKey implements RSAPublicKey, OpenSSLKeyHolder {
    private static final long serialVersionUID = 123125005824688292L;
    private transient boolean fetchedParams;
    private transient OpenSSLKey key;
    private BigInteger modulus;
    private BigInteger publicExponent;

    @Override // java.security.Key
    public String getAlgorithm() {
        return "RSA";
    }

    @Override // java.security.Key
    public String getFormat() {
        return "X.509";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLRSAPublicKey(OpenSSLKey openSSLKey) {
        this.key = openSSLKey;
    }

    @Override // org.conscrypt.OpenSSLKeyHolder
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLRSAPublicKey(RSAPublicKeySpec rSAPublicKeySpec) throws InvalidKeySpecException {
        try {
            this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(rSAPublicKeySpec.getModulus().toByteArray(), rSAPublicKeySpec.getPublicExponent().toByteArray(), null, null, null, null, null, null));
        } catch (Exception e) {
            throw new InvalidKeySpecException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static OpenSSLKey getInstance(RSAPublicKey rSAPublicKey) throws InvalidKeyException {
        try {
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(rSAPublicKey.getModulus().toByteArray(), rSAPublicKey.getPublicExponent().toByteArray(), null, null, null, null, null, null));
        } catch (Exception e) {
            throw new InvalidKeyException(e);
        }
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return NativeCrypto.EVP_marshal_public_key(this.key.getNativeRef());
    }

    private synchronized void ensureReadParams() {
        if (!this.fetchedParams) {
            byte[][] bArr = NativeCrypto.get_RSA_public_params(this.key.getNativeRef());
            this.modulus = new BigInteger(bArr[0]);
            this.publicExponent = new BigInteger(bArr[1]);
            this.fetchedParams = true;
        }
    }

    @Override // java.security.interfaces.RSAKey
    public BigInteger getModulus() {
        ensureReadParams();
        return this.modulus;
    }

    @Override // java.security.interfaces.RSAPublicKey
    public BigInteger getPublicExponent() {
        ensureReadParams();
        return this.publicExponent;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof OpenSSLRSAPublicKey) && this.key.equals(((OpenSSLRSAPublicKey) obj).getOpenSSLKey())) {
            return true;
        }
        if (!(obj instanceof RSAPublicKey)) {
            return false;
        }
        ensureReadParams();
        RSAPublicKey rSAPublicKey = (RSAPublicKey) obj;
        return this.modulus.equals(rSAPublicKey.getModulus()) && this.publicExponent.equals(rSAPublicKey.getPublicExponent());
    }

    public int hashCode() {
        ensureReadParams();
        return this.modulus.hashCode() ^ this.publicExponent.hashCode();
    }

    public String toString() {
        ensureReadParams();
        return "OpenSSLRSAPublicKey{modulus=" + this.modulus.toString(16) + StringUtil.COMMA + "publicExponent=" + this.publicExponent.toString(16) + '}';
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(this.modulus.toByteArray(), this.publicExponent.toByteArray(), null, null, null, null, null, null));
        this.fetchedParams = true;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ensureReadParams();
        objectOutputStream.defaultWriteObject();
    }
}
