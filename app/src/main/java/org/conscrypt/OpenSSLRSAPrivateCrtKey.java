package org.conscrypt;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class OpenSSLRSAPrivateCrtKey extends OpenSSLRSAPrivateKey implements RSAPrivateCrtKey {
    private static final long serialVersionUID = 3785291944868707197L;
    private BigInteger crtCoefficient;
    private BigInteger primeExponentP;
    private BigInteger primeExponentQ;
    private BigInteger primeP;
    private BigInteger primeQ;
    private BigInteger publicExponent;

    OpenSSLRSAPrivateCrtKey(OpenSSLKey openSSLKey) {
        super(openSSLKey);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLRSAPrivateCrtKey(OpenSSLKey openSSLKey, byte[][] bArr) {
        super(openSSLKey, bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLRSAPrivateCrtKey(RSAPrivateCrtKeySpec rSAPrivateCrtKeySpec) throws InvalidKeySpecException {
        super(init(rSAPrivateCrtKeySpec));
    }

    private static OpenSSLKey init(RSAPrivateCrtKeySpec rSAPrivateCrtKeySpec) throws InvalidKeySpecException {
        BigInteger modulus = rSAPrivateCrtKeySpec.getModulus();
        BigInteger privateExponent = rSAPrivateCrtKeySpec.getPrivateExponent();
        if (modulus == null) {
            throw new InvalidKeySpecException("modulus == null");
        } else if (privateExponent != null) {
            try {
                BigInteger publicExponent = rSAPrivateCrtKeySpec.getPublicExponent();
                BigInteger primeP = rSAPrivateCrtKeySpec.getPrimeP();
                BigInteger primeQ = rSAPrivateCrtKeySpec.getPrimeQ();
                BigInteger primeExponentP = rSAPrivateCrtKeySpec.getPrimeExponentP();
                BigInteger primeExponentQ = rSAPrivateCrtKeySpec.getPrimeExponentQ();
                BigInteger crtCoefficient = rSAPrivateCrtKeySpec.getCrtCoefficient();
                byte[] byteArray = modulus.toByteArray();
                byte[] bArr = null;
                byte[] byteArray2 = publicExponent == null ? null : publicExponent.toByteArray();
                byte[] byteArray3 = privateExponent.toByteArray();
                byte[] byteArray4 = primeP == null ? null : primeP.toByteArray();
                byte[] byteArray5 = primeQ == null ? null : primeQ.toByteArray();
                byte[] byteArray6 = primeExponentP == null ? null : primeExponentP.toByteArray();
                byte[] byteArray7 = primeExponentQ == null ? null : primeExponentQ.toByteArray();
                if (crtCoefficient != null) {
                    bArr = crtCoefficient.toByteArray();
                }
                return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(byteArray, byteArray2, byteArray3, byteArray4, byteArray5, byteArray6, byteArray7, bArr));
            } catch (Exception e) {
                throw new InvalidKeySpecException(e);
            }
        } else {
            throw new InvalidKeySpecException("privateExponent == null");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static OpenSSLKey getInstance(RSAPrivateCrtKey rSAPrivateCrtKey) throws InvalidKeyException {
        if (rSAPrivateCrtKey.getFormat() == null) {
            return wrapPlatformKey(rSAPrivateCrtKey);
        }
        BigInteger modulus = rSAPrivateCrtKey.getModulus();
        BigInteger privateExponent = rSAPrivateCrtKey.getPrivateExponent();
        if (modulus == null) {
            throw new InvalidKeyException("modulus == null");
        } else if (privateExponent != null) {
            try {
                BigInteger publicExponent = rSAPrivateCrtKey.getPublicExponent();
                BigInteger primeP = rSAPrivateCrtKey.getPrimeP();
                BigInteger primeQ = rSAPrivateCrtKey.getPrimeQ();
                BigInteger primeExponentP = rSAPrivateCrtKey.getPrimeExponentP();
                BigInteger primeExponentQ = rSAPrivateCrtKey.getPrimeExponentQ();
                BigInteger crtCoefficient = rSAPrivateCrtKey.getCrtCoefficient();
                byte[] byteArray = modulus.toByteArray();
                byte[] bArr = null;
                byte[] byteArray2 = publicExponent == null ? null : publicExponent.toByteArray();
                byte[] byteArray3 = privateExponent.toByteArray();
                byte[] byteArray4 = primeP == null ? null : primeP.toByteArray();
                byte[] byteArray5 = primeQ == null ? null : primeQ.toByteArray();
                byte[] byteArray6 = primeExponentP == null ? null : primeExponentP.toByteArray();
                byte[] byteArray7 = primeExponentQ == null ? null : primeExponentQ.toByteArray();
                if (crtCoefficient != null) {
                    bArr = crtCoefficient.toByteArray();
                }
                return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(byteArray, byteArray2, byteArray3, byteArray4, byteArray5, byteArray6, byteArray7, bArr));
            } catch (Exception e) {
                throw new InvalidKeyException(e);
            }
        } else {
            throw new InvalidKeyException("privateExponent == null");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.conscrypt.OpenSSLRSAPrivateKey
    public synchronized void readParams(byte[][] bArr) {
        super.readParams(bArr);
        if (bArr[1] != null) {
            this.publicExponent = new BigInteger(bArr[1]);
        }
        if (bArr[3] != null) {
            this.primeP = new BigInteger(bArr[3]);
        }
        if (bArr[4] != null) {
            this.primeQ = new BigInteger(bArr[4]);
        }
        if (bArr[5] != null) {
            this.primeExponentP = new BigInteger(bArr[5]);
        }
        if (bArr[6] != null) {
            this.primeExponentQ = new BigInteger(bArr[6]);
        }
        if (bArr[7] != null) {
            this.crtCoefficient = new BigInteger(bArr[7]);
        }
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPublicExponent() {
        ensureReadParams();
        return this.publicExponent;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeP() {
        ensureReadParams();
        return this.primeP;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeQ() {
        ensureReadParams();
        return this.primeQ;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeExponentP() {
        ensureReadParams();
        return this.primeExponentP;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeExponentQ() {
        ensureReadParams();
        return this.primeExponentQ;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getCrtCoefficient() {
        ensureReadParams();
        return this.crtCoefficient;
    }

    @Override // org.conscrypt.OpenSSLRSAPrivateKey
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OpenSSLRSAPrivateKey) {
            return getOpenSSLKey().equals(((OpenSSLRSAPrivateKey) obj).getOpenSSLKey());
        }
        if (obj instanceof RSAPrivateCrtKey) {
            ensureReadParams();
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) obj;
            return getModulus().equals(rSAPrivateCrtKey.getModulus()) && this.publicExponent.equals(rSAPrivateCrtKey.getPublicExponent()) && getPrivateExponent().equals(rSAPrivateCrtKey.getPrivateExponent()) && this.primeP.equals(rSAPrivateCrtKey.getPrimeP()) && this.primeQ.equals(rSAPrivateCrtKey.getPrimeQ()) && this.primeExponentP.equals(rSAPrivateCrtKey.getPrimeExponentP()) && this.primeExponentQ.equals(rSAPrivateCrtKey.getPrimeExponentQ()) && this.crtCoefficient.equals(rSAPrivateCrtKey.getCrtCoefficient());
        } else if (!(obj instanceof RSAPrivateKey)) {
            return false;
        } else {
            ensureReadParams();
            RSAPrivateKey rSAPrivateKey = (RSAPrivateKey) obj;
            return getModulus().equals(rSAPrivateKey.getModulus()) && getPrivateExponent().equals(rSAPrivateKey.getPrivateExponent());
        }
    }

    @Override // org.conscrypt.OpenSSLRSAPrivateKey
    public final int hashCode() {
        int hashCode = super.hashCode();
        BigInteger bigInteger = this.publicExponent;
        return bigInteger != null ? hashCode ^ bigInteger.hashCode() : hashCode;
    }

    @Override // org.conscrypt.OpenSSLRSAPrivateKey
    public String toString() {
        StringBuilder sb = new StringBuilder("OpenSSLRSAPrivateCrtKey{");
        ensureReadParams();
        sb.append("modulus=");
        sb.append(getModulus().toString(16));
        if (this.publicExponent != null) {
            sb.append(StringUtil.COMMA);
            sb.append("publicExponent=");
            sb.append(this.publicExponent.toString(16));
        }
        sb.append('}');
        return sb.toString();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        byte[] byteArray = this.modulus.toByteArray();
        BigInteger bigInteger = this.publicExponent;
        byte[] bArr = null;
        byte[] byteArray2 = bigInteger == null ? null : bigInteger.toByteArray();
        byte[] byteArray3 = this.privateExponent.toByteArray();
        BigInteger bigInteger2 = this.primeP;
        byte[] byteArray4 = bigInteger2 == null ? null : bigInteger2.toByteArray();
        BigInteger bigInteger3 = this.primeQ;
        byte[] byteArray5 = bigInteger3 == null ? null : bigInteger3.toByteArray();
        BigInteger bigInteger4 = this.primeExponentP;
        byte[] byteArray6 = bigInteger4 == null ? null : bigInteger4.toByteArray();
        BigInteger bigInteger5 = this.primeExponentQ;
        byte[] byteArray7 = bigInteger5 == null ? null : bigInteger5.toByteArray();
        BigInteger bigInteger6 = this.crtCoefficient;
        if (bigInteger6 != null) {
            bArr = bigInteger6.toByteArray();
        }
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(byteArray, byteArray2, byteArray3, byteArray4, byteArray5, byteArray6, byteArray7, bArr));
        this.fetchedParams = true;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ensureReadParams();
        objectOutputStream.defaultWriteObject();
    }
}
