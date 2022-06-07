package com.xiaomi.passport.servicetoken;

import com.xiaomi.accountsdk.utils.Coder;

@Deprecated
/* loaded from: classes4.dex */
public class MD5Util {
    private MD5Util() {
    }

    public static String getMd5DigestUpperCase(String str) {
        return Coder.getMd5DigestUpperCase(str);
    }

    public static String getDataMd5Digest(byte[] bArr) {
        return Coder.getDataMd5Digest(bArr);
    }

    public static String getHexString(byte[] bArr) {
        return Coder.getHexString(bArr);
    }
}
