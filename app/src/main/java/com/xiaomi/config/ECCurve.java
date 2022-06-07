package com.xiaomi.config;

import android.util.Log;
import com.xiaomi.config.utils.ECCPointConvert;
import com.xiaomi.config.utils.Hkdf;
import com.xiaomi.config.utils.SecurityChipUtil;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import javax.crypto.SecretKey;

/* loaded from: classes3.dex */
public enum ECCurve {
    SECP192R1(1, "secp192r1"),
    SECP224R1(2, "secp224r1"),
    SECP256R1(3, "secp256r1"),
    SECP384R1(4, "secp384r1"),
    SECP521R1(5, "secp521r1"),
    SECP192K1(10, "secp192k1"),
    SECP224K1(11, "secp224k1"),
    SECP256K1(12, "secp256k1");
    
    private KeyPairGenerator generator = null;
    private int index;
    private KeyPair keyPair;
    private String name;

    ECCurve(int i, String str) {
        this.name = str;
        this.index = i;
    }

    public int getIndex() {
        return this.index;
    }

    public static ECCurve search(int i) {
        ECCurve[] values = values();
        for (ECCurve eCCurve : values) {
            if (eCCurve.index == i) {
                try {
                    eCCurve.a();
                    return eCCurve;
                } catch (Exception unused) {
                    return null;
                }
            }
        }
        return null;
    }

    public KeyPair generateKeyPair() {
        if (this.generator == null) {
            try {
                a();
            } catch (Exception unused) {
                return null;
            }
        }
        this.keyPair = this.generator.generateKeyPair();
        return this.keyPair;
    }

    private void a() throws Exception {
        this.generator = KeyPairGenerator.getInstance("EC");
        this.generator.initialize(new ECGenParameterSpec(this.name));
    }

    public byte[] verify(SignType signType, String str, byte[] bArr, byte[] bArr2) {
        try {
            ECPublicKey eCPublicKey = (ECPublicKey) this.keyPair.getPublic();
            SecretKey secret = SecurityChipUtil.getSecret(ECCPointConvert.fromUncompressedPoint(bArr, eCPublicKey.getParams()), this.keyPair.getPrivate());
            Hkdf instance = Hkdf.getInstance("HmacSHA256");
            instance.init(secret.getEncoded(), str.getBytes());
            byte[] deriveKey = instance.deriveKey("".getBytes(), 16);
            if (SignType.HMACSHA256 == signType) {
                if (byteEquals(SecurityChipUtil.sha256HMAC(deriveKey, bArr), bArr2)) {
                    return deriveKey;
                }
                return null;
            } else if (SignType.ECDSASHA256 != signType) {
                return null;
            } else {
                Signature instance2 = Signature.getInstance("SHA256WithECDSA");
                instance2.initVerify(eCPublicKey);
                instance2.update(bArr);
                if (instance2.verify(bArr2)) {
                    return deriveKey;
                }
                return null;
            }
        } catch (Exception e) {
            Log.e("fatal", "ECCurve.verify", e);
            return null;
        }
    }

    public static boolean byteEquals(byte[] bArr, byte[] bArr2) {
        int length;
        if (bArr == null && bArr2 == null) {
            return true;
        }
        if (bArr == null || bArr2 == null || (length = bArr.length) != bArr2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    /* loaded from: classes3.dex */
    public enum SignType {
        ECDSASHA256(2),
        HMACSHA256(1);
        
        private final int type;

        SignType(int i) {
            this.type = i;
        }

        public static SignType index(int i) {
            switch (i) {
                case 1:
                    return HMACSHA256;
                case 2:
                    return ECDSASHA256;
                default:
                    return null;
            }
        }
    }
}
