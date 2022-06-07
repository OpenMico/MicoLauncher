package org.conscrypt;

import java.security.PublicKey;
import java.util.Arrays;

/* loaded from: classes5.dex */
final class X509PublicKey implements PublicKey {
    private static final long serialVersionUID = -8610156854731664298L;
    private final String algorithm;
    private final byte[] encoded;

    @Override // java.security.Key
    public String getFormat() {
        return "X.509";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public X509PublicKey(String str, byte[] bArr) {
        this.algorithm = str;
        this.encoded = bArr;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.encoded;
    }

    public String toString() {
        return "X509PublicKey [algorithm=" + this.algorithm + ", encoded=" + Arrays.toString(this.encoded) + "]";
    }

    public int hashCode() {
        String str = this.algorithm;
        return (((str == null ? 0 : str.hashCode()) + 31) * 31) + Arrays.hashCode(this.encoded);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        X509PublicKey x509PublicKey = (X509PublicKey) obj;
        String str = this.algorithm;
        if (str == null) {
            if (x509PublicKey.algorithm != null) {
                return false;
            }
        } else if (!str.equals(x509PublicKey.algorithm)) {
            return false;
        }
        return Arrays.equals(this.encoded, x509PublicKey.encoded);
    }
}
