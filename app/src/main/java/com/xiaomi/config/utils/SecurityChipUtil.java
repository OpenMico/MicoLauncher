package com.xiaomi.config.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class SecurityChipUtil {
    public static final int AUTH_TAG_BIT_LENGTH = 32;

    public static KeyPair generateEcc256KeyPair() {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance("EC");
            instance.initialize(new ECGenParameterSpec("secp256r1"));
            return instance.generateKeyPair();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static SecretKey getSecret(PublicKey publicKey, PrivateKey privateKey) {
        try {
            KeyAgreement instance = KeyAgreement.getInstance("ECDH");
            instance.init(privateKey);
            instance.doPhase(publicKey, true);
            return instance.generateSecret("ECDH");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] sha256HMAC(byte[] bArr, byte[] bArr2) {
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(new SecretKeySpec(bArr, "HmacSHA256"));
            return instance.doFinal(bArr2);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
