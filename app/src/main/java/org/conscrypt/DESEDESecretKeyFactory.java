package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public class DESEDESecretKeyFactory extends SecretKeyFactorySpi {
    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec == null) {
            throw new InvalidKeySpecException("Null KeySpec");
        } else if (keySpec instanceof SecretKeySpec) {
            SecretKeySpec secretKeySpec = (SecretKeySpec) keySpec;
            try {
                if (DESedeKeySpec.isParityAdjusted(secretKeySpec.getEncoded(), 0)) {
                    return secretKeySpec;
                }
                throw new InvalidKeySpecException("SecretKeySpec is not a parity-adjusted DESEDE key");
            } catch (InvalidKeyException e) {
                throw new InvalidKeySpecException(e);
            }
        } else if (keySpec instanceof DESedeKeySpec) {
            return new SecretKeySpec(((DESedeKeySpec) keySpec).getKey(), "DESEDE");
        } else {
            throw new InvalidKeySpecException("Unsupported KeySpec class: " + keySpec.getClass().getName());
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected KeySpec engineGetKeySpec(SecretKey secretKey, Class cls) throws InvalidKeySpecException {
        if (secretKey == null) {
            throw new InvalidKeySpecException("Null SecretKey");
        } else if (cls == SecretKeySpec.class) {
            try {
                if (!DESedeKeySpec.isParityAdjusted(secretKey.getEncoded(), 0)) {
                    throw new InvalidKeySpecException("SecretKey is not a parity-adjusted DESEDE key");
                } else if (secretKey instanceof SecretKeySpec) {
                    return (KeySpec) secretKey;
                } else {
                    return new SecretKeySpec(secretKey.getEncoded(), "DESEDE");
                }
            } catch (InvalidKeyException e) {
                throw new InvalidKeySpecException(e);
            }
        } else if (cls == DESedeKeySpec.class) {
            try {
                return new DESedeKeySpec(secretKey.getEncoded());
            } catch (InvalidKeyException e2) {
                throw new InvalidKeySpecException(e2);
            }
        } else {
            throw new InvalidKeySpecException("Unsupported KeySpec class: " + cls);
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineTranslateKey(SecretKey secretKey) throws InvalidKeyException {
        if (secretKey != null) {
            return new SecretKeySpec(secretKey.getEncoded(), secretKey.getAlgorithm());
        }
        throw new InvalidKeyException("Null SecretKey");
    }
}
