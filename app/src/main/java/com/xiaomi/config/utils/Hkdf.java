package com.xiaomi.config.utils;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public final class Hkdf {
    private static final byte[] a = new byte[0];
    private final String b;
    private final Provider c;
    private SecretKey d = null;

    public static Hkdf getInstance(String str) throws NoSuchAlgorithmException {
        Mac.getInstance(str);
        return new Hkdf(str);
    }

    public static Hkdf getInstance(String str, String str2) throws NoSuchAlgorithmException, NoSuchProviderException {
        return new Hkdf(str, Mac.getInstance(str, str2).getProvider());
    }

    public static Hkdf getInstance(String str, Provider provider) throws NoSuchAlgorithmException {
        return new Hkdf(str, Mac.getInstance(str, provider).getProvider());
    }

    public void init(byte[] bArr) {
        init(bArr, null);
    }

    public void init(byte[] bArr, byte[] bArr2) {
        Mac mac;
        byte[] bArr3 = bArr2 == null ? a : (byte[]) bArr2.clone();
        byte[] bArr4 = a;
        try {
            try {
                if (this.c != null) {
                    mac = Mac.getInstance(this.b, this.c);
                } else {
                    mac = Mac.getInstance(this.b);
                }
                if (bArr3.length == 0) {
                    bArr3 = new byte[mac.getMacLength()];
                    Arrays.fill(bArr3, (byte) 0);
                }
                mac.init(new SecretKeySpec(bArr3, this.b));
                bArr4 = mac.doFinal(bArr);
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArr4, this.b);
                Arrays.fill(bArr4, (byte) 0);
                unsafeInitWithoutKeyExtraction(secretKeySpec);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException("Unexpected exception", e);
            }
        } finally {
            Arrays.fill(bArr4, (byte) 0);
        }
    }

    public void unsafeInitWithoutKeyExtraction(SecretKey secretKey) throws InvalidKeyException {
        if (secretKey.getAlgorithm().equals(this.b)) {
            this.d = secretKey;
            return;
        }
        throw new InvalidKeyException("Algorithm for the provided key must match the algorithm for this Hkdf. Expected " + this.b + " but found " + secretKey.getAlgorithm());
    }

    private Hkdf(String str) {
        if (str.startsWith("Hmac")) {
            this.b = str;
            this.c = null;
            return;
        }
        throw new IllegalArgumentException("Invalid algorithm " + str + ". Hkdf may only be used with Hmac algorithms.");
    }

    private Hkdf(String str, Provider provider) {
        if (str.startsWith("Hmac")) {
            this.b = str;
            this.c = provider;
            return;
        }
        throw new IllegalArgumentException("Invalid algorithm " + str + ". Hkdf may only be used with Hmac algorithms.");
    }

    public byte[] deriveKey(String str, int i) throws IllegalStateException {
        return deriveKey(str != null ? str.getBytes(Charset.forName("UTF-8")) : null, i);
    }

    public byte[] deriveKey(byte[] bArr, int i) throws IllegalStateException {
        byte[] bArr2 = new byte[i];
        try {
            deriveKey(bArr, i, bArr2, 0);
            return bArr2;
        } catch (ShortBufferException e) {
            throw new RuntimeException(e);
        }
    }

    public void deriveKey(byte[] bArr, int i, byte[] bArr2, int i2) throws ShortBufferException, IllegalStateException {
        int i3;
        b();
        if (i < 0) {
            throw new IllegalArgumentException("Length must be a non-negative value.");
        } else if (bArr2.length >= i2 + i) {
            Mac a2 = a();
            if (i <= a2.getMacLength() * 255) {
                byte b = 1;
                byte[] bArr3 = a;
                for (int i4 = 0; i4 < i; i4 = i3) {
                    try {
                        a2.update(bArr3);
                        a2.update(bArr);
                        a2.update(b);
                        bArr3 = a2.doFinal();
                        i3 = i4;
                        int i5 = 0;
                        while (i5 < bArr3.length && i3 < i) {
                            bArr2[i3] = bArr3[i5];
                            i5++;
                            i3++;
                        }
                        b = (byte) (b + 1);
                    } finally {
                        Arrays.fill(bArr3, (byte) 0);
                    }
                }
                return;
            }
            throw new IllegalArgumentException("Requested keys may not be longer than 255 times the underlying HMAC length.");
        } else {
            throw new ShortBufferException();
        }
    }

    private Mac a() {
        Mac mac;
        try {
            if (this.c != null) {
                mac = Mac.getInstance(this.b, this.c);
            } else {
                mac = Mac.getInstance(this.b);
            }
            mac.init(this.d);
            return mac;
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }

    private void b() throws IllegalStateException {
        if (this.d == null) {
            throw new IllegalStateException("Hkdf has not been initialized");
        }
    }
}
