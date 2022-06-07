package com.xiaomi.smarthome.library.crypto.rc4coder;

import com.xiaomi.smarthome.library.crypto.Base64Coder;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes4.dex */
public class RC4DropCoder {
    private static final byte[] b = new byte[1024];
    a a;

    static {
        Arrays.fill(b, (byte) 0);
    }

    private static boolean a(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public RC4DropCoder(byte[] bArr) throws SecurityException {
        if (a(bArr)) {
            throw new SecurityException("rc4 key is null");
        } else if (bArr.length == 32) {
            this.a = new a(bArr);
            decrypt(b);
        } else {
            throw new IllegalArgumentException("rc4Key length is invalid");
        }
    }

    public RC4DropCoder(String str) throws SecurityException {
        this(Base64Coder.decode(str));
    }

    public byte[] decrypt(byte[] bArr) throws SecurityException {
        try {
            if (bArr != null) {
                this.a.a(bArr);
                return bArr;
            }
            throw new IllegalBlockSizeException("no block data");
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException(e);
        }
    }

    public String decrypt(String str) {
        try {
            return new String(decrypt(Base64Coder.decode(str)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(e);
        }
    }

    public byte[] encrypt(byte[] bArr) throws SecurityException {
        try {
            if (bArr != null) {
                this.a.a(bArr);
                return bArr;
            }
            throw new IllegalBlockSizeException("no block data");
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException(e);
        }
    }

    public String encrypt(String str) {
        byte[] bArr;
        try {
            bArr = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            bArr = null;
        }
        return String.valueOf(Base64Coder.encode(encrypt(bArr)));
    }
}
