package com.mijiasdk.bleserver.protocol.utils;

import com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1EncodableVector;
import com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Integer;
import com.mijiasdk.bleserver.protocol.bouncycastle.asn1.DEROutputStream;
import com.mijiasdk.bleserver.protocol.bouncycastle.asn1.DERSequence;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.InvalidCipherTextException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.engines.AESEngine;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.modes.CCMBlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params.AEADParameters;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params.KeyParameter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
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

    public static byte[] getRawPublicKey(PublicKey publicKey) {
        byte[] publicKeyToBytes = ECCPointConvert.publicKeyToBytes((ECPublicKey) publicKey);
        return publicKeyToBytes.length == 65 ? Arrays.copyOfRange(publicKeyToBytes, 1, 65) : publicKeyToBytes;
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

    public static AESEngine createAESCipher(SecretKey secretKey, boolean z) {
        AESEngine aESEngine = new AESEngine();
        aESEngine.init(z, new KeyParameter(secretKey.getEncoded()));
        return aESEngine;
    }

    private static CCMBlockCipher a(SecretKey secretKey, boolean z, byte[] bArr, byte[] bArr2) {
        CCMBlockCipher cCMBlockCipher = new CCMBlockCipher(createAESCipher(secretKey, z));
        cCMBlockCipher.init(z, new AEADParameters(new KeyParameter(secretKey.getEncoded()), 32, bArr, bArr2));
        return cCMBlockCipher;
    }

    public static AuthenticatedCipherText AESEncryptWithAuth(SecretKey secretKey, byte[] bArr, byte[] bArr2, byte[] bArr3) throws InvalidCipherTextException {
        CCMBlockCipher a = a(secretKey, true, bArr, bArr3);
        byte[] bArr4 = new byte[a.getOutputSize(bArr2.length)];
        int processBytes = a.processBytes(bArr2, 0, bArr2.length, bArr4, 0);
        int doFinal = (processBytes + a.doFinal(bArr4, processBytes)) - 4;
        byte[] bArr5 = new byte[doFinal];
        byte[] bArr6 = new byte[4];
        System.arraycopy(bArr4, 0, bArr5, 0, bArr5.length);
        System.arraycopy(bArr4, doFinal, bArr6, 0, bArr6.length);
        return new AuthenticatedCipherText(bArr5, bArr6);
    }

    public static byte[] AESDecryptWithAuth(SecretKey secretKey, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws InvalidCipherTextException {
        CCMBlockCipher a = a(secretKey, false, bArr, bArr3);
        byte[] bArr5 = new byte[bArr2.length + bArr4.length];
        System.arraycopy(bArr2, 0, bArr5, 0, bArr2.length);
        System.arraycopy(bArr4, 0, bArr5, bArr2.length, bArr4.length);
        byte[] bArr6 = new byte[a.getOutputSize(bArr5.length)];
        a.doFinal(bArr6, a.processBytes(bArr5, 0, bArr5.length, bArr6, 0));
        return bArr6;
    }

    public static byte[] AESEncrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return AESEncryptWithAuth(new SecretKeySpec(bArr, "AES"), bArr2, bArr3, null).getTotalData();
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] AESDecrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return AESDecryptWithAuth(new SecretKeySpec(bArr, "AES"), bArr2, Arrays.copyOfRange(bArr3, 0, bArr3.length - 4), null, Arrays.copyOfRange(bArr3, bArr3.length - 4, bArr3.length));
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getDERSignature(byte[] bArr) {
        byte[] copyOfRange = Arrays.copyOfRange(bArr, 0, bArr.length / 2);
        byte[] copyOfRange2 = Arrays.copyOfRange(bArr, bArr.length / 2, bArr.length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DEROutputStream dEROutputStream = new DEROutputStream(byteArrayOutputStream);
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(new BigInteger(1, copyOfRange)));
        aSN1EncodableVector.add(new ASN1Integer(new BigInteger(1, copyOfRange2)));
        try {
            dEROutputStream.writeObject(new DERSequence(aSN1EncodableVector));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            dEROutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return byteArray;
    }

    public static boolean isEcdsaSignatureValid(byte[] bArr, byte[] bArr2, PublicKey publicKey) {
        try {
            Signature instance = Signature.getInstance("SHA256WithECDSA");
            instance.initVerify(publicKey);
            instance.update(bArr);
            return instance.verify(bArr2);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return false;
        } catch (SignatureException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public static byte[] str2byteMac(String str) {
        byte[] bArr = new byte[6];
        int i = 0;
        for (String str2 : str.split("[:]")) {
            i++;
            bArr[i] = (byte) Integer.parseInt(str2, 16);
        }
        return bArr;
    }

    public static boolean verifyX509Certificate(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        try {
            x509Certificate2.verify(x509Certificate.getPublicKey());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyX509CertificateX(javax.security.cert.X509Certificate x509Certificate, javax.security.cert.X509Certificate x509Certificate2) {
        try {
            x509Certificate2.verify(x509Certificate.getPublicKey());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
